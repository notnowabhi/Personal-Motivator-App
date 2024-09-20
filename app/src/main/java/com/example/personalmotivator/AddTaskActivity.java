package com.example.personalmotivator;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private Button btnAddAlarm;
    private Button btnViewTasks;
    private Button btnAddTasks;
    private TextView textAlarmTime;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btnAddAlarm = findViewById(R.id.button_add_alarm);
        btnViewTasks = findViewById(R.id.button_view_tasks);
        btnAddTasks = findViewById(R.id.button_add_task);
        textAlarmTime = findViewById(R.id.text_alarm_time);

        calendar = Calendar.getInstance();

        btnAddAlarm.setOnClickListener(view -> showTimePicker());
        btnViewTasks.setOnClickListener(view -> openTaskList());
        btnAddTasks.setOnClickListener(view -> showAddTaskDialog());
    }

    private void showTimePicker() {
        android.app.TimePickerDialog.OnTimeSetListener timeSetListener =
                (timePicker, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    String formattedTime = timeFormat.format(calendar.getTime());
                    textAlarmTime.setText("Alarm set for: " + formattedTime);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                            startActivity(intent);
                            return;
                        }
                    }

                    setAlarm(calendar);
                };

        new android.app.TimePickerDialog(
                AddTaskActivity.this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        ).show();
    }

    private void setAlarm(Calendar alarmTime) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Alarm is set!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Task");

        final EditText input = new EditText(this);
        input.setHint("Enter task description");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String taskDescription = input.getText().toString();
            if (!taskDescription.isEmpty()) {
                // Add task to TaskData singleton
                TaskData.getInstance().addTask(taskDescription);
                Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task description cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void openTaskList() {
        Intent intent = new Intent(this, TaskListActivity.class);
        startActivity(intent);
    }
}
