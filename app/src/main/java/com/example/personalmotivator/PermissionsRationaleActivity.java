package com.example.personalmotivator;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class PermissionsRationaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_rationale);

        // Show a rationale message or privacy policy to the user
        TextView rationaleTextView = findViewById(R.id.rationale_text);
        rationaleTextView.setText(R.string.permissions_rationale_text);
    }
}
