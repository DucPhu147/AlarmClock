package com.example.alarmclock.Alarm.Other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.alarmclock.Alarm.AlarmClass.AlarmHelper;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getStringExtra("cancel alarm using notify").equals("true")) {
            AlarmMusic.getInstance().TurnOffMusic();
            AlarmVibration.getInstance().TurnOffVibrate();
            AlarmNotification.getInstance().cancelNotification();
            AlarmCountDownTimer.getInstance().CancelTimer();
        }else if(intent.getStringExtra("cancel alarm using notify").equals("snooze"))
        {
            AlarmHelper.getInstance().cancelAlarmSnooze(context);
        }
    }
}
