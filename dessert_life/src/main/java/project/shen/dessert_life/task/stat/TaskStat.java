package project.shen.dessert_life.task.stat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import project.shen.dessert_life.task.Utils.DebugLog;

public class TaskStat {

    private static volatile String currentSituation = "";
    private static List<TaskStatBean> beans = new ArrayList<>();
    private static AtomicInteger taskDoneCount = new AtomicInteger();
    private static boolean openLaunchStat = false;// 是否开启统计

    public static String getCurrentSituation() {
        return currentSituation;
    }

    public static void setCurrentSituation(String currentSituation) {
        if (!openLaunchStat) {
            return;
        }
        DebugLog.logD("currentSituation", currentSituation);
        TaskStat.currentSituation = currentSituation;
        setLaunchStat();
    }

    public static void markTaskDone() {
        taskDoneCount.getAndIncrement();
    }

    public static void setLaunchStat() {
        TaskStatBean bean = new TaskStatBean();
        bean.setSituation(currentSituation);
        bean.setCount(taskDoneCount.get());
        beans.add(bean);
        taskDoneCount = new AtomicInteger(0);
    }

}
