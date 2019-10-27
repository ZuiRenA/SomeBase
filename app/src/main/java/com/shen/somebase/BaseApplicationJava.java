package com.shen.somebase;

import android.app.Application;

import com.shen.somebase.task.TaskBuilder;
import com.shen.somebase.task.TaskOne;
import com.shen.somebase.task.TaskThree;
import com.shen.somebase.task.TaskTwo;

import project.shen.dessert_life.dessert_task.DessertDispatcher;

/**
 * created by shen on 2019/10/27
 * at 21:53
 **/
public class BaseApplicationJava extends Application {
    public static BaseApplicationJava context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        DessertDispatcher.init(context);
        DessertDispatcher.getInstance()
                .addTask(new TaskBuilder())
                .addTask(new TaskOne())
                .addTask(new TaskTwo())
                .addTask(new TaskThree())
                .start();
    }
}
