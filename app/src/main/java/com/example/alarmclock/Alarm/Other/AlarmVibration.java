package com.example.alarmclock.Alarm.Other;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class AlarmVibration {
    private static final AlarmVibration ourInstance = new AlarmVibration();
    private Vibrator vibrator;
    private boolean isVibrate=false;
    public static AlarmVibration getInstance() {
        return ourInstance;
    }
    private AlarmVibration() {
    }
    public void TurnOnVibrate(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] vib = new long[]{0, 500, 500}; //đỗ trễ ban đầu là 0s, sau đó rung 0,75s sau đó lại dừng 0.55s
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(vib,0));
                isVibrate = true;
            } else {
                vibrator.vibrate(vib, 0);
                isVibrate = true;
            }
        }
    }
    public void TurnOffVibrate()
    {
        if(isVibrate) {
            vibrator.cancel();
            isVibrate=false;
        }
    }
    public void ShortVibrate(Context context)
    {
        if(!isVibrate) {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                    isVibrate = true;
                } else {
                    vibrator.vibrate(50);
                    isVibrate = true;
                }
            }
        }
    }

}
