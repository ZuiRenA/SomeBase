package project.shen.dessert_life.task.sort;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import project.shen.dessert_life.task.Task;
import project.shen.dessert_life.task.Utils.DebugLog;

/**
 * created by shen at 2019/10/24
 */
public class TaskSortUtil {

    //高优先级 Task
    private static List<Task> newTaskHigh = new ArrayList<>();

    //任务的有向无环图的拓扑排序
    public static synchronized List<Task> getSortResult(List<Task> originTasks,
                                                        List<Class<? extends Task>> clsLaunchTasks) {
        long makeTime = System.currentTimeMillis();

        Set<Integer> dependSet = new ArraySet<>();
        Graph graph = new Graph(originTasks.size());
        for (int i = 0; i < originTasks.size(); i++) {
            Task task = originTasks.get(i);
            if (task.isSend() || task.dependsOn() == null || task.dependsOn().size() == 0) {
                continue;
            }
            for (Class cls : task.dependsOn()) {
                int indexOfDepend = getIndexOfTask(originTasks, clsLaunchTasks, cls);
                if (indexOfDepend < 0) {
                    throw new IllegalStateException(task.getClass().getSimpleName() +
                            " depends on " + cls.getSimpleName() + " can not be found in task list ");
                }
                dependSet.add(indexOfDepend);
                graph.addEdge(indexOfDepend, i);
            }
        }
        List<Integer> indexList = graph.topologicalSort();
        List<Task> newTasksAll = getResultTasks(originTasks, dependSet, indexList);

        DebugLog.logD("task analyse cost makeTime", System.currentTimeMillis() - makeTime);
        printAllTaskName(newTasksAll);
        return newTasksAll;
    }

    @NonNull
    private static List<Task> getResultTasks(List<Task> originTasks,
                                             Set<Integer> dependSet, List<Integer> indexList) {
        List<Task> newTasksAll = new ArrayList<>(originTasks.size());
        List<Task> newTasksDepended = new ArrayList<>();// 被别人依赖的
        List<Task> newTasksWithOutDepend = new ArrayList<>();// 没有依赖的
        List<Task> newTasksRunAsSoon = new ArrayList<>();// 需要提升自己优先级的，先执行（这个先是相对于没有依赖的先）
        for (int index : indexList) {
            if (dependSet.contains(index)) {
                newTasksDepended.add(originTasks.get(index));
            } else {
                Task task = originTasks.get(index);
                if (task.needRunAsSoon()) {
                    newTasksRunAsSoon.add(task);
                } else {
                    newTasksWithOutDepend.add(task);
                }
            }
        }
        // 顺序：被别人依赖的————》需要提升自己优先级的————》需要被等待的————》没有依赖的
        newTaskHigh.addAll(newTasksDepended);
        newTaskHigh.addAll(newTasksRunAsSoon);
        newTasksAll.addAll(newTaskHigh);
        newTasksAll.addAll(newTasksWithOutDepend);
        return newTasksAll;
    }

    private static void printAllTaskName(List<Task> newTasksAll) {
        if (!DebugLog.isDebug) {
            return;
        }
        for (Task task : newTasksAll) {
            DebugLog.logD("Task name", task.getClass().getSimpleName());
        }
    }

    @Contract(pure = true)
    public static List<Task> getTasksHigh() {
        return newTaskHigh;
    }

    /**
     * 获取任务在任务列表中的下标
     */
    private static int getIndexOfTask(List<Task> originTasks,
                                      List<Class<? extends Task>> clsLaunchTasks,
                                      Class cls) {
        int index = clsLaunchTasks.indexOf(cls);
        if (index >= 0) {
            return index;
        }

        // 仅仅是保护性代码
        final int size = originTasks.size();
        for (int i = 0; i < size; i++) {
            if (cls.getSimpleName().equals(originTasks.get(i).getClass().getSimpleName())) {
                return i;
            }
        }
        return index;
    }
}
