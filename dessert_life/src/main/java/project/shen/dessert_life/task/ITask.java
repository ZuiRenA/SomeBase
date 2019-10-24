package project.shen.dessert_life.task;

import android.os.Process;
import androidx.annotation.IntRange;

import java.util.List;
import java.util.concurrent.Executor;

public interface ITask {

    /**
     * 优先级范围, 可根据 Task 重要程度和工作量指定；之后根据实际情况决定是否有必要放更大
     */
    @IntRange(from = Process.THREAD_PRIORITY_FOREGROUND, to = Process.THREAD_PRIORITY_LOWEST)
    int priority();

    void run();

    /**
     * Task 执行所在的线程池，可以指定，一般默认
     */
    Executor runOn();

    /**
     * 依赖关系
     */
    List<Class<? extends Task>> dependsOn();

    /**
     * 异步线程执行的 Task 是否需要在被调用 await 时进行等待，默认不需要
     */
    boolean needWait();

    /**
     * 是否在主线程执行
     */
    boolean runOnMainThread();

    /**
     * 只是在主线程进行
     */
    boolean onlyInMainProcess();

    /**
     * Task 主任务执行完成之后需要执行的任务
     */
    Runnable getTailRunnable();

    void setTaskCallback(TaskCallback callback);

    boolean needCall();
}
