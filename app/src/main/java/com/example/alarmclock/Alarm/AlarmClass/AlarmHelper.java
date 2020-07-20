package com.example.alarmclock.Alarm.AlarmClass;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.alarmclock.Alarm.Other.AlarmNotification;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmHelper {
    private static final AlarmHelper ourInstance = new AlarmHelper();
    public static AlarmHelper getInstance() {
        return ourInstance;
    }

    private AlarmHelper() {
    }

    public void createAlarm(Context context,Alarm alarm) {
        Bundle bundle = new Bundle();
        bundle.putInt("Shake time", alarm.getShakeTime());
        bundle.putInt("Shake difficult", alarm.getShakeDifficult());
        bundle.putInt("Math difficult", alarm.getMathDifficult());
        bundle.putInt("Selected music", alarm.getMusic());
        bundle.putInt("Selected method", alarm.getCancelMethod());
        bundle.putBoolean("isVibrate", alarm.getVibrate());
        bundle.putString("Label", alarm.getLabel());
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtras(bundle);
        intent.putExtra("cancel alarm using notify", "false");
        int count = 0;
        for (int i = 0; i < alarm.getRepeatday().length; i++) {
            cancelAlarm(context, alarm.getAlarmID().hashCode() + i); //nếu mà sửa báo thức thì sẽ xóa những báo thức đã đặt từ trước
            if (alarm.getRepeatday()[i] != 0) {
                count++;
            }
        }
        if (count == 7) {
            setAlarmRepeatDaily(context, intent, alarm);
            Log.d("alarm", "repeat dayly");
        } else if (count > 0) {
            for (int i = 0; i < alarm.getRepeatday().length; i++) {
                if (alarm.getRepeatday()[i] != 0) {
                    setAlarmRepeatSpecificDay(context, alarm.getRepeatday()[i], alarm.getAlarmID().hashCode() + i, intent, alarm);
                    Log.d("alarm", "repeat specific day");
                }
            }
        } else
        {
            setAlarmNoRepeat(context, intent, alarm);
            Log.d("alarm", "no repeat");
        }
    }

    public void removeAlarm(Context context,Alarm alarm) {
        for (int i = 0; i < alarm.getRepeatday().length; i++) {
            cancelAlarm(context, alarm.getAlarmID().hashCode() + i);
        }
    }

    public void setAlarmNoRepeat(Context context,Intent intent,Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.before(Calendar.getInstance()))
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour() + 24);
        pendingIntent = PendingIntent.getBroadcast(context, alarm.getAlarmID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void setAlarmRepeatDaily(Context context, Intent intent,Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.before(Calendar.getInstance()))
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour() + 24);
        pendingIntent = PendingIntent.getBroadcast(context, alarm.getAlarmID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void setAlarmRepeatSpecificDay(Context context, int day, int requestCode, Intent intent,Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
        pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), (AlarmManager.INTERVAL_DAY) * 7, pendingIntent);

    }

    public void cancelAlarm(Context context, int requestCode) {
        try {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            PendingIntent pendingIntent;

            Intent intent;
            intent = new Intent(context, AlarmBroadcastReceiver.class);

            pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setAlarmSnooze(Context context,Bundle bundle)
    {
        AlarmNotification.getInstance().sendSnoozeNotification(context);//Gửi notify là báo thức sắp kêu
        PendingIntent pendingIntent;
        AlarmManager alarmManager;
        Intent myIntent = new Intent(context, AlarmBroadcastReceiver.class);
        myIntent.putExtras(bundle);
        myIntent.putExtra("cancel alarm using notify","false");
        Calendar calendar = Calendar.getInstance();

        SharedPreferences preferences = context.getSharedPreferences("MyAlarm", Context.MODE_PRIVATE);
        long settingVolume= preferences.getLong("alarmSnoozeTime", 60000);
        Toast.makeText(context, "Báo thức sẽ kêu sau "+settingVolume/60000+" phút nữa", Toast.LENGTH_SHORT).show();
        pendingIntent = PendingIntent.getBroadcast(context, 1245687, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+settingVolume , pendingIntent);
    }
    public void cancelAlarmSnooze(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent;

        Intent intent;
        intent = new Intent(context, AlarmBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 1245687, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        AlarmNotification.getInstance().cancelNotification();
    }
}
