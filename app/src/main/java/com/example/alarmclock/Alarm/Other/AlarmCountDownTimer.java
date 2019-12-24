package com.example.alarmclock.Alarm.Other;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.alarmclock.Alarm.AlarmClass.AlarmHelper;

public class AlarmCountDownTimer {
    private static final AlarmCountDownTimer ourInstance = new AlarmCountDownTimer();


    public static AlarmCountDownTimer getInstance() {
        return ourInstance;
    }

    private AlarmCountDownTimer( ) {
    }
    private CountDownTimer timer;
    private int millisRemaining=0;
    private boolean isRunning=false;
    private Bundle bundle=new Bundle();
    public void StartTimer(final Context context, final Bundle bundle)
    {
        this.bundle=bundle;
        PauseTimer();

        isRunning=true;
        if(millisRemaining!=0)
            ResumeTimer(context);
        else
        //Nếu quá 30 giây thì activity sẽ ngưng
        {
            timer = new CountDownTimer(30000, 1000) {
                public void onTick(long millisUntilFinished) {
                    millisRemaining = (int)millisUntilFinished;
                }
                public void onFinish() {
                    try {
                        CancelAlarm(context);
                        millisRemaining=0;
                        isRunning = false;
                    } catch (Exception ex) {
                        Log.e("timer", ex.toString());
                    }
                }
            }.start();
            Log.d("timer", "start");
        }
    }
    public void CancelTimer( ){
        if(isRunning) {
            isRunning = false;
            timer.cancel();
            millisRemaining = 0;
        }
    }
    public void PauseTimer()  {
        if(isRunning) {
            timer.cancel();
        }
    }
    public void ResumeTimer(final Context context){
        timer = new CountDownTimer(millisRemaining,1000) {
            @Override
            public void onTick(long time) {
                millisRemaining=(int)time;
            }
            @Override
            public void onFinish() {
                CancelAlarm(context);
                isRunning = false;
                millisRemaining=0;
            }
        }.start();
        Log.d("timer", "resume");
    }
    private void CancelAlarm(Context context)
    {
        AlarmMusic.getInstance().TurnOffMusic();
        AlarmVibration.getInstance().TurnOffVibrate();
        AlarmNotification.getInstance().cancelNotification();
        AlarmHelper.getInstance().setAlarmSnooze(context, bundle);
        ((Activity) context).finish();
    }
    public boolean isTimerRunning()
    {
        return isRunning;
    }
}
