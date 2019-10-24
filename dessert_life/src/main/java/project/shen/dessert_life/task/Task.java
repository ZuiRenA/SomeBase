package project.shen.dessert_life.task;

import android.content.Context;
import android.os.Process;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import project.shen.dessert_life.task.Utils.DispatcherExcutor;

public abstract class Task implements ITask {
    protected String tag = getClass().getSimpleName();

    protected Context context = TaskDispatcher.getContext();

    protected boolean isMainProcess = TaskDispatcher.isMainProcess();

    // 是否正在等待
    private volatile boolean isWaiting;

    // 是否正在执行
    private volatile boolean isRunning;

    // Task 是否执行完成
    private volatile boolean isFinish;

    // Task 是否已经被纷发
    private volatile boolean isSend;

    // 当前Task依赖的Task数量（需要等待被依赖的Task执行完毕才能执行自己），默认没有依赖
    private CountDownLatch depends = new CountDownLatch(dependsOn() == null ? 0 : dependsOn().size());

    /**
     * 当前Task等待，让依赖的Task先执行
     */
    public void waitToSatisfy() {
        try {
            depends.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 依赖的Task执行完一个
     */
    public void satisfy() {
        depends.countDown();
    }

    /**
     * 是否需要优先执行，解决特殊场景的问题：一个 Task 耗时非常多，但是优先级却一般，很有可能开始的时间比较晚
     * 只适合可以把它尽早开始
     */
    public boolean needRunAsSoon() {
        return false;
    }

    /**
     * Task的优先级，运行在主线程则不要去改优先级
     *
     */
    @Override
    public int priority() {
        return Process.THREAD_PRIORITY_BACKGROUND;
    }

    /**
     * Task执行在哪个线程池，默认在IO的线程池；
     * CPU 密集型的一定要切换到DispatcherExecutor.getCPUExecutor();
     *
     */
    @Override
    public ExecutorService runOn() {
        return DispatcherExcutor.getIOExcutor();
    }

    /**
     * 异步线程执行的Task是否需要在被调用await的时候等待，默认不需要
     *
     */
    @Override
    public boolean needWait() {
        return false;
    }

    /**
     * 当前Task依赖的Task集合（需要等待被依赖的Task执行完毕才能执行自己），默认没有依赖
     */
    @Override
    public List<Class<? extends Task>> dependsOn() {
        return null;
    }

    /**
     * 是否在主线程进行，默认不在
     */
    @Override
    public boolean runOnMainThread() {
        return false;
    }

    @Override
    public Runnable getTailRunnable() {
        return null;
    }

    @Override
    public void setTaskCallback(TaskCallback callback) {

    }

    @Override
    public boolean needCall() {
        return false;
    }

    /**
     * 是否只在主进程，默认是
     */
    @Override
    public boolean onlyInMainProcess() {
        return true;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
