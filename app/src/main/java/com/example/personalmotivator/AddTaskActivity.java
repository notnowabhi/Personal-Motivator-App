package com.example.personalmotivator;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Task");

        // Inflate and set the layout for the dialog
        final EditText input = new EditText(this);
        input.setHint("Enter task description");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String taskDescription = input.getText().toString();
            if (!taskDescription.isEmpty()) {
                // Create an Intent to send the task description to TaskListActivity
                //Intent intent = new Intent(this, TaskListActivity.class);
                //intent.putExtra("TASK_DESCRIPTION", taskDescription);
                //startActivity(intent);
            } else {
                Toast.makeText(this, "Task description cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void openTaskList() {
        // Intent to open TaskListActivity
        Intent intent = new Intent(this, TaskListActivity.class);
        startActivity(intent);
    }
}
