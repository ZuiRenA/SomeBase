package project.shen.dessert_life.task.Utils;

import android.os.Looper;
import android.os.MessageQueue;
import java.util.LinkedList;
import java.util.Queue;

import project.shen.dessert_life.task.DispatchRunnable;
import project.shen.dessert_life.task.Task;


public class DelayInitDispatcher {
    private Queue<Task> delayTasks = new LinkedList<>();

    private MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            if(delayTasks.size()>0){
                Task task = delayTasks.poll();
                new DispatchRunnable(task).run();
            }
            return !delayTasks.isEmpty();
        }
    };

    public DelayInitDispatcher addTask(Task task){
        delayTasks.add(task);
        return this;
    }

    public void start(){
        Looper.myQueue().addIdleHandler(idleHandler);
    }
}
