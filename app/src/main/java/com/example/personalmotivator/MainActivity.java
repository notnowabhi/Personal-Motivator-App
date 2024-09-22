package com.example.personalmotivator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import androidx.health.connect.client.records.StepsRecord; // <-- Add this import

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String[]> permissionLauncher;
    private Button button_start, button_read_steps;
    private int fake_steps = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start button to navigate to AddTaskActivity
        button_start = findViewById(R.id.button_start);
        button_start.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        // Request permissions
        requestHealthConnectPermissions();

        // Button to read step records
        button_read_steps = findViewById(R.id.button_read_steps);
        button_read_steps.setOnClickListener(v -> readStepsByTimeRange());

        button_read_steps.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this, "Steps: " + fake_steps + " steps today!", Toast.LENGTH_SHORT).show();
                fake_steps += 5;
                return true;
            }
        });
    }

    // Function to read step records using Kotlin coroutine
    private void readStepsByTimeRange() {
        // Access the Kotlin singleton 'HealthConnectUtils' using '.INSTANCE'
        HealthConnectUtils.INSTANCE.readStepsInCoroutine(this, stepRecords -> {
            if (stepRecords != null && !stepRecords.isEmpty()) {
                for (StepsRecord record : stepRecords) {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Steps: " + record.getCount(), Toast.LENGTH_SHORT).show());
                }
            } else if (stepRecords != null && stepRecords.isEmpty()) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Step Record is empty. Please walk.", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to read steps", Toast.LENGTH_SHORT).show());
            }
            return null;
        });
    }

    // Request Health Connect permissions
    private void requestHealthConnectPermissions() {
        String[] requiredPermissions = {
                "android.permission.health.READ_HEART_RATE",
                "android.permission.health.WRITE_HEART_RATE",
                "android.permission.health.READ_STEPS",
                "android.permission.health.WRITE_STEPS"
        };
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
            boolean allGranted = result.values().stream().allMatch(Boolean::booleanValue);
            if (allGranted) {
                Toast.makeText(MainActivity.this, "Permissions granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permissions denied!", Toast.LENGTH_SHORT).show();
            }
        });
        permissionLauncher.launch(requiredPermissions);
    }
}
