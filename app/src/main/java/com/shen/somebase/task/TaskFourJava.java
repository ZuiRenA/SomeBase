package com.shen.somebase.task;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.shen.dessert_life.dessert_task.DessertTask;
import project.shen.dessert_life.utils.DebugLog;

/**
 * created by shen on 2019/10/27
 * at 22:01
 **/
public class TaskFourJava extends DessertTask {

    @Nullable
    @Override
    public List<Class<? extends DessertTask>> getDependOn() {
        return new ArrayList<>(Arrays.asList(
                TaskBuilder.class, TaskOne.class
        ));
    }

    @Override
    public void run() {
        int[] intArray = new int[10];
        DebugLog.logE("init", "four");
    }
}
