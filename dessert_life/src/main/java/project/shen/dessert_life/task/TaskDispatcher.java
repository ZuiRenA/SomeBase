package project.shen.dessert_life.task;

import android.content.Context;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import project.shen.dessert_life.task.Utils.DebugLog;
import project.shen.dessert_life.task.Utils.Utils;
import project.shen.dessert_life.task.sort.TaskSortUtil;
import project.shen.dessert_life.task.stat.TaskStat;

/**
 * 启动器调用类
 */
public class TaskDispatcher {

    private long startTime;
    private static final int WAITTING_TIME = 10000;
    private static Context context;
    private static boolean isMainProcess;
    private List<Future> futures = new ArrayList<>();
    private static volatile boolean hasInit;
    private List<Task> allTasks = new ArrayList<>();
    private List<Class<? extends Task>> clsAllTask = new ArrayList<>();
    private volatile List<Task> mainThreadTask = new ArrayList<>();
    private CountDownLatch countDownLatch;

    /**
     * 需要的等待的任务数
     */
    private AtomicInteger needWaitCount = new AtomicInteger();

    /**
     * 调用了 await 还没结束且需要等待 Task 列表
     */
    private List<Task> needWaitTask = new ArrayList<>();

    /**
     * 已经结束的 Task
     */
    private volatile List<Class<? extends Task>> finishTasks = new ArrayList<>(100);

    private HashMap<Class<? extends Task>, ArrayList<Task>> dependedHashMap = new HashMap<>();

    /**
     * 启动器分析的次数，统计下分析的耗时；
     */
    private AtomicInteger analyseCount = new AtomicInteger();

    public static void init(@Nullable Context context) {
        if (context != null) {
            TaskDispatcher.context = context;
            hasInit = true;
            isMainProcess = Utils.isMainProcess(context);
        }
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isMainProcess() {
        return isMainProcess;
    }

    private TaskDispatcher() {

    }

    public static class Lazy {
        public static TaskDispatcher getInstance() {
            if (!hasInit) {
                throw new RuntimeException("must call TaskDispatcher.init first");
            }
            return new TaskDispatcher();
        }
    }

    public TaskDispatcher addTask(Task task) {
        if (task != null) {
            collectDepends(task);
            allTasks.add(task);
            clsAllTask.add(task.getClass());
            // 非主线程且需要wait的，主线程不需要CountDownLatch也是同步的
            if (ifNeedWait(task)) {
                needWaitTask.add(task);
                needWaitCount.getAndIncrement();
            }
        }

        return this;
    }

    @UiThread
    public void start() {
        startTime = System.currentTimeMillis();
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("must be called from UiThread");
        }
        if (!allTasks.isEmpty()) {
            analyseCount.getAndIncrement();
            printDependedMsg();
            allTasks = TaskSortUtil.getSortResult(allTasks, clsAllTask);
            countDownLatch = new CountDownLatch(needWaitCount.get());
            sendAndExecuteAsyncTasks();

            DebugLog.logD("task analyse duration", (System.currentTimeMillis() - startTime) + "  begin main ");
            executeTaskMain();
        }
    }

    public void cancel() {
        for (Future future : futures) {
            future.cancel(true);
        }
    }

    @UiThread
    public void await() {
        try {
            if (DebugLog.isDebug) {
                DebugLog.logD("still has", needWaitCount.get());
                for (Task task : needWaitTask) {
                    DebugLog.logD("needWait", task.getClass().getSimpleName());
                }
            }

            if (needWaitCount.get() > 0) {
                if (countDownLatch == null) {
                    throw new RuntimeException("You have to call start() before call await()");
                }
                countDownLatch.await(WAITTING_TIME, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            //ignore
        }
    }

    private void executeTaskMain() {
        startTime = System.currentTimeMillis();
        for (Task task : mainThreadTask) {
            long time = System.currentTimeMillis();
            new DispatchRunnable(task, this).run();
            DebugLog.logD(task.getClass().getSimpleName(), "real main " + (System.currentTimeMillis() - time));
        }
        DebugLog.logD("main task duration", System.currentTimeMillis() - startTime);
    }

    public void executeTask(Task task) {
        if (ifNeedWait(task)) {
            needWaitCount.getAndIncrement();
        }
        task.runOn().execute(new DispatchRunnable(task,this));
    }

    private void sendAndExecuteAsyncTasks() {
        for (Task task : allTasks) {
            if (task.onlyInMainProcess() && !isMainProcess()) {
                makeTaskDone(task);
            } else {
                sendTaskReal(task);
            }
            task.setSend(true);
        }
    }

    private void sendTaskReal(final Task task) {
        if (task.runOnMainThread()) {
            mainThreadTask.add(task);
            if (task.needCall()) {
                task.setTaskCallback(new TaskCallback() {
                    @Override
                    public void Call() {
                        TaskStat.markTaskDone();
                        task.setFinish(true);
                        makeTaskDone(task);
                        DebugLog.logD(task.getClass().getSimpleName(), "finish");
                    }
                });
            }
        } else {
            Future future = task.runOn().submit(new DispatchRunnable(task, this));
            futures.add(future);
        }
    }

    public void makeTaskDone(Task task) {
        if (ifNeedWait(task)) {
            finishTasks.add(task.getClass());
            needWaitTask.remove(task);
            countDownLatch.countDown();
            needWaitCount.getAndDecrement();
        }
    }

    /**
     * 通知Children一个前置任务已完成
     */
    public void satisfyChildren(Task launchTask) {
        ArrayList<Task> arrayList = dependedHashMap.get(launchTask.getClass());
        if (arrayList != null && !arrayList.isEmpty()) {
            for (Task task : arrayList) {
                task.satisfy();
            }
        }
    }

    private void collectDepends(Task task) {
        if (task.dependsOn() != null && !task.dependsOn().isEmpty()) {
            for (Class<? extends Task> cls : task.dependsOn()) {
                if (dependedHashMap.get(cls) == null) {
                    dependedHashMap.put(cls, new ArrayList<Task>());
                }

                dependedHashMap.get(cls).add(task);
                if (finishTasks.contains(cls)) {
                    task.satisfy();
                }
            }
        }
    }

    private boolean ifNeedWait(Task task) {
        return !task.runOnMainThread() && task.needWait();
    }

    /**
     * 查看被依赖的信息
     */
    private void printDependedMsg() {
        DebugLog.logD(getClass().getSimpleName(), needWaitCount.get());
        if (DebugLog.isDebug) {
            for (Class<? extends Task> cls : dependedHashMap.keySet()) {
                DebugLog.logD("cls " + cls.getSimpleName(), dependedHashMap.get(cls).size());
                for (Task task : dependedHashMap.get(cls)) {
                    DebugLog.logD("cls", task.getClass().getSimpleName());
                }
            }
        }
    }
}
