package com.example.alarmclock.Alarm.Other;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.alarmclock.Alarm.CancelAlarmScreen.CancelAlarmScreenActivity;
import com.example.alarmclock.R;

public class AlarmNotification {
    private static final AlarmNotification ourInstance = new AlarmNotification();

    public static AlarmNotification getInstance() {
        return ourInstance;
    }
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private boolean isSend=false;
    String CHANNEL_ID = "my_channel_01";
    CharSequence name = "my_channel";
    private AlarmNotification() {
    }
    public void sendAlarmNotification(Context context, Bundle bundle)
    {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        isSend=true;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }else{
            //notificationManager.createNotificationChannel();
        }
        Intent intent=new Intent(context, NotificationReceiver.class);
        intent.putExtra("cancel alarm using notify","true");

        Intent intent2=new Intent(context, CancelAlarmScreenActivity.class);
        intent2.putExtras(bundle);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0424, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context,0425, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_channel_01")
                .setContentTitle("Báo thức")
                .setContentText("BÁO THỨC ĐANG KÊU")
                .setContentIntent(pendingIntent2)
                .setSmallIcon(R.drawable.ic_access_alarms_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_access_alarms_black_24dp,"TẮT",pendingIntent);
        String label=bundle.getString("Label");
        if(!label.equals(""))
            builder.setContentText(label);
        notificationManager.notify(846752,builder.build());
        Log.d("notify", "cancel");
    }
    public void sendSnoozeNotification(Context context)
    {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        isSend=true;
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }*/
        Intent intent=new Intent(context, NotificationReceiver.class);
        intent.putExtra("cancel alarm using notify","snooze");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0426, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        SharedPreferences preferences = context.getSharedPreferences("MyAlarm", Context.MODE_PRIVATE);
        long snoozeTime= preferences.getLong("alarmSnoozeTime", 60000);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_channel_02")
                .setContentTitle("Báo thức")
                .setContentText("Báo thức sẽ kêu sau "+snoozeTime/60000+" phút nữa")
                .setSmallIcon(R.drawable.ic_access_alarms_black_24dp)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_access_alarms_black_24dp,"BỎ QUA",pendingIntent);

        notificationManager.notify(846753,builder.build());
        Log.d("notify", "snooze");
    }
    public void cancelNotification()
    {
        if(isSend) {
            notificationManager.cancelAll();
            isSend=false;
            Log.d("notify","cancel all");
        }
    }
}
