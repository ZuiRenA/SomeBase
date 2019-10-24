package project.shen.dessert_life.task.Utils;


import androidx.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by shen at 2019/10/24
 */
public class DispatcherExcutor {

    // cpu 密集型任务的线程池
    private static ThreadPoolExecutor cpuThreadPoolExcuter;

    // io 密集型任务的线程池
    private static ExecutorService ioThreadPoolExcuter;

    // cpu 核数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    //线程池的数量
    public static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 5));

    //线程池线程数的最大值
    private static final int MAXIMUM_POOL_SIZE = CORE_POOL_SIZE;

    private static final int KEEP_ALIVE_SECONDS = 5;

    private static final BlockingQueue<Runnable> poolWorkQueue = new LinkedBlockingDeque<>();

    /**
     * 线程工厂
     */
    private static final DefaultThreadFactory threadFactory = new DefaultThreadFactory();

    // 一般不会到这儿
    private static final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            Executors.newCachedThreadPool().execute(runnable);
        }
    };

    // 获取cup线程池
    public static ThreadPoolExecutor getCpuExecutor() {
        return cpuThreadPoolExcuter;
    }

    public static ExecutorService getIOExcutor() {
        return ioThreadPoolExcuter;
    }

    //default thread factory
    private static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager manager = System.getSecurityManager();
            group = (manager != null) ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "TaskDispatcherPool-" + poolNumber.getAndIncrement() + "-Thread-";
        }

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            Thread t = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement()
                    , 0);

            if (t.isDaemon()) {
                t.setDaemon(false);
            }

            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }

            return t;
        }
    }

    static {
        cpuThreadPoolExcuter = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS,
                TimeUnit.SECONDS, poolWorkQueue, threadFactory, handler);
        cpuThreadPoolExcuter.allowCoreThreadTimeOut(true);
        ioThreadPoolExcuter = Executors.newCachedThreadPool(threadFactory);
    }
}
