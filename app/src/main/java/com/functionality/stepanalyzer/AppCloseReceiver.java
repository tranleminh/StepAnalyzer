package com.functionality.stepanalyzer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AppCloseReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //context.startService(new Intent(context, DailyStepService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, DailyStepService.class));
        } else {
            context.startService(new Intent(context, DailyStepService.class));
        }
    }
}

