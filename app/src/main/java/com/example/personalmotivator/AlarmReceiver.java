package com.example.personalmotivator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // This is called when the alarm goes off
        String message = "Alarm received!";
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        // You can add more actions here, like sending a notification
    }
}
