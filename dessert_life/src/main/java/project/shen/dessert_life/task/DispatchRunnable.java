package project.shen.dessert_life.task;

import android.os.Looper;
import android.os.Process;

import androidx.core.os.TraceCompat;

import project.shen.dessert_life.task.Utils.DebugLog;
import project.shen.dessert_life.task.stat.TaskStat;

/**
 * created by shen at 2019/10/24
 */
public class DispatchRunnable implements Runnable {

    private Task task;
    private TaskDispatcher taskDispatcher;

    public DispatchRunnable(Task task) {
        this.task = task;
    }

    public DispatchRunnable(Task task, TaskDispatcher dispatcher) {
        this.task = task;
        this.taskDispatcher = dispatcher;
    }

    @Override
    public void run() {
        TraceCompat.beginSection(task.getClass().getSimpleName());
        DebugLog.logD(task.getClass().getSimpleName(), "begin run" + "  Situation  " + TaskStat.getCurrentSituation());

        Process.setThreadPriority(task.priority());

        long startTime = System.currentTimeMillis();

        task.setWaiting(true);
        task.waitToSatisfy();

        long waitTime = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 执行Task
        task.setRunning(true);
        task.run();

        // 执行Task的尾部任务
        Runnable tailRunnable = task.getTailRunnable();
        if (tailRunnable != null) {
            tailRunnable.run();
        }

        if (!task.needCall() || !task.runOnMainThread()) {
            printTaskLog(startTime, waitTime);

            TaskStat.markTaskDone();
            task.setFinish(true);
            if (taskDispatcher != null) {
                taskDispatcher.satisfyChildren(task);
                taskDispatcher.makeTaskDone(task);
            }
            DebugLog.logD(task.getClass().getSimpleName(), " finish");
        }
        TraceCompat.endSection();
    }

    /**
     * 打印出来Task执行的日志
     *
     * @param startTime
     * @param waitTime
     */
    private void printTaskLog(long startTime, long waitTime) {
        long runTime = System.currentTimeMillis() - startTime;
        if (DebugLog.isDebug) {
            DebugLog.logD(task.getClass().getSimpleName(), "  wait " + waitTime + "    run "
                    + runTime + "   isMain " + (Looper.getMainLooper() == Looper.myLooper())
                    + "  needWait " + (task.needWait() || (Looper.getMainLooper() == Looper.myLooper()))
                    + "  ThreadId " + Thread.currentThread().getId()
                    + "  ThreadName " + Thread.currentThread().getName()
                    + "  Situation  " + TaskStat.getCurrentSituation()
            );
        }
    }

}
