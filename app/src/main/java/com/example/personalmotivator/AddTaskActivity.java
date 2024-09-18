package com.example.personalmotivator;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private Button btnAddAlarm;
    private Button btnViewTasks;
    private Button btnAddTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btnAddAlarm = findViewById(R.id.button_add_alarm);
        btnViewTasks = findViewById(R.id.button_view_tasks);
        btnAddTasks = findViewById(R.id.button_add_task);

        btnAddAlarm.setOnClickListener(view -> showTimePicker());
        btnViewTasks.setOnClickListener(view -> openTaskList());
        btnAddTasks.setOnClickListener(view -> showAddTaskDialog());
    }
    //change 1

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (TimePicker view, int hourOfDay, int minute) -> setAlarm(hourOfDay, minute), 12, 0, false);
        timePickerDialog.show();
    }

    private void setAlarm(int hourOfDay, int minute) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_HOUR, hourOfDay)
                .putExtra(AlarmClock.EXTRA_MINUTES, minute)
                .putExtra(AlarmClock.EXTRA_MESSAGE, "Personal Motivator Alarm");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            Toast.makeText(this, "Alarm set for: " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No alarm app found on this device", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddTaskDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Add New Task")
                .setPositiveButton("Add", (dialog, which) -> {
                    EditText editTextTask = dialogView.findViewById(R.id.edit_text_task);
                    String task = editTextTask.getText().toString().trim();
                    if (!task.isEmpty()) {
                        // Handle the task addition here
                        Toast.makeText(this, "Task Added: " + task, Toast.LENGTH_SHORT).show();
                        // Add code to save task to a list or database if needed
                    } else {
                        Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void openTaskList() {
        Intent intent = new Intent(this, TaskListActivity.class);
        startActivity(intent);
    }
}
