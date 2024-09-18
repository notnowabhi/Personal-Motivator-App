package com.example.personalmotivator;

import java.util.ArrayList;
import java.util.List;

public class TaskData {
    private static TaskData instance;
    private List<String> tasks;

    private TaskData() {
        tasks = new ArrayList<>();
    }

    public static synchronized TaskData getInstance() {
        if (instance == null) {
            instance = new TaskData();
        }
        return instance;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void addTask(String task) {
        tasks.add(task);
    }
}
