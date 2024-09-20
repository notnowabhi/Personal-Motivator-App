package com.example.personalmotivator;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.health.connect.client.HealthConnectClient;

public class MainActivity extends AppCompatActivity {

    private HealthConnectClient healthConnectClient;
    private ActivityResultLauncher<String[]> permissionLauncher;

    private Button button_start;
    private Button button_request_permission;  // Added button for requesting exact alarm permission

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect start button for navigating to AddTaskActivity
        button_start = findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        // Initialize the button for requesting Exact Alarm Permission
        button_request_permission = findViewById(R.id.button_request_permission);
        button_request_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    requestExactAlarmPermission();
                } else {
                    Toast.makeText(MainActivity.this, "Exact Alarm permission not required for this Android version", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Initialize Health Connect Client
        healthConnectClient = HealthConnectClient.getOrCreate(this);

        // Initialize the permission launcher for Health Connect
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            boolean allGranted = result.values().stream().allMatch(Boolean::booleanValue);
            if (allGranted) {
                Toast.makeText(MainActivity.this, "Permissions granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permissions denied!", Toast.LENGTH_SHORT).show();
            }
        });

        // Request permissions for Health Connect
        requestHealthConnectPermissions();
    }

    // Function to request Health Connect permissions
    private void requestHealthConnectPermissions() {
        String[] requiredPermissions = {
                "android.permission.health.READ_HEART_RATE",
                "android.permission.health.WRITE_HEART_RATE",
                "android.permission.health.READ_STEPS",
                "android.permission.health.WRITE_STEPS"
        };

        // Launch permission request
        permissionLauncher.launch(requiredPermissions);
    }

    // Function to request Exact Alarm Permission for Android 12+
    @RequiresApi(api = Build.VERSION_CODES.S)
    private void requestExactAlarmPermission() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Check if the app already has the exact alarm permission
        if (!alarmManager.canScheduleExactAlarms()) {
            // Launch the settings screen to request the permission
            Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Exact Alarm permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
}
