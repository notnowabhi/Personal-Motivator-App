package com.example.personalmotivator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        recyclerView = findViewById(R.id.recycler_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        // Handle incoming task data
        handleIncomingTaskData();
    }

    private void handleIncomingTaskData() {
        // Check if this activity was started with a task description
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TASK_DESCRIPTION")) {
            String taskDescription = intent.getStringExtra("TASK_DESCRIPTION");
            if (taskDescription != null) {
                // Add the new task to the list
                taskList.add(taskDescription);
                taskAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
            }
        }
    }
}
