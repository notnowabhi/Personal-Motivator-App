package com.example.personalmotivator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private TextView tokenDisplay;
    private Button addTaskButton;
    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private List<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tokenDisplay = findViewById(R.id.tokenDisplay);
        addTaskButton = findViewById(R.id.addTaskButton);
        taskRecyclerView = findViewById(R.id.taskRecyclerView);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRecyclerView.setAdapter(taskAdapter);

        addTaskButton.setOnClickListener(v -> showTaskInputDialog());
    }

    private void showTaskInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Task");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_task_input, null);
        final EditText input = viewInflated.findViewById(R.id.taskInput);

        builder.setView(viewInflated);
        builder.setPositiveButton("Add", (dialog, which) -> {
            String task = input.getText().toString();
            if (!task.isEmpty()) {
                taskList.add(task);
                taskAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
