package com.example.alarmclock.Alarm.AlarmClass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.alarmclock.Alarm.CancelAlarmScreen.CancelAlarmScreenActivity;
import com.example.alarmclock.Alarm.Other.AlarmMusic;
import com.example.alarmclock.Alarm.Other.AlarmVibration;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Intent myIntent2 = new Intent(context, CancelAlarmScreenActivity.class);
        myIntent2.putExtras(bundle);
        myIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent2);
        final boolean vib = bundle.getBoolean("isVibrate");
        final int music = bundle.getInt("Selected music");
        AlarmMusic.getInstance().TurnOnMusic(context, music);
        if (vib)
            AlarmVibration.getInstance().TurnOnVibrate(context);
    }
}
