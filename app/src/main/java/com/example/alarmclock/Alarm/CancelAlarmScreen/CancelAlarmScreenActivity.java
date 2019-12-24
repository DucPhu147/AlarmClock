package com.example.alarmclock.Alarm.CancelAlarmScreen;

import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alarmclock.Alarm.AlarmClass.AlarmHelper;
import com.example.alarmclock.Alarm.Other.AlarmCountDownTimer;
import com.example.alarmclock.Alarm.Other.AlarmMusic;
import com.example.alarmclock.Alarm.Other.AlarmNotification;
import com.example.alarmclock.Alarm.Other.AlarmVibration;
import com.example.alarmclock.R;

import java.util.Calendar;

public class CancelAlarmScreenActivity extends AppCompatActivity implements TurnOffAlarm {
    Button btnCancel, btnDelay;
    Bundle bundle;
    TextView txtHienGio,txtlabel;
    boolean isTurnOff=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_cancel_screen_activity);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Log.e("cancel alarm screen", "create");
        AlarmNotification.getInstance().cancelNotification();
        bundle = getIntent().getExtras();
        AlarmCountDownTimer.getInstance().StartTimer(CancelAlarmScreenActivity.this,bundle);

        final int method=bundle.getInt("Selected method");
        final String label=bundle.getString("Label");
        final int mathDiff=bundle.getInt("Math difficult");
        final int shakeDiff=bundle.getInt("Shake difficult");
        final int shakeTime=bundle.getInt("Shake time");

        String hour, minute;
        txtHienGio = findViewById(R.id.txtAlarmTime);
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 0)
            hour = "0" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        else
            hour = "" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (Calendar.getInstance().get(Calendar.MINUTE) < 10)
            minute = "0" + Calendar.getInstance().get(Calendar.MINUTE);
        else
            minute = "" + Calendar.getInstance().get(Calendar.MINUTE);
        txtHienGio.setText(hour + ":" + minute);

        txtlabel= findViewById(R.id.txtCancelLabel);
        if(!label.equals(""))
            txtlabel.setText(label);

        //Nút tắt báo thức
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (method == 0) //Nếu là mặc định
                {
                    onTurnOffAlarm();
                } else if (method == 1)  //Nếu là giải toán
                {
                    SolveMathDialog solveMathDialog =new SolveMathDialog(CancelAlarmScreenActivity.this,mathDiff);
                    solveMathDialog.show(getSupportFragmentManager(),"solve math");
                }
                else if(method==2)
                {
                    ShakeDialog shakeDialog =new ShakeDialog(CancelAlarmScreenActivity.this,shakeDiff,shakeTime);
                    shakeDialog.show(getSupportFragmentManager(),"shake");
                }
            }
        });
        //Khi nhấn nút báo lại
        btnDelay = findViewById(R.id.btnDelay);
        btnDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmHelper.getInstance().setAlarmSnooze(CancelAlarmScreenActivity.this,bundle);
                onTurnOffAlarm();
            }
        });
    }
    boolean back = false;
    @Override
    public void onBackPressed() {
        if (back) {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        Log.e("cancel alarm screen", "finish");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("cancel alarm screen", "onPause");
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) { //Nếu API min lớn hơn 20
            isScreenOn = powerManager.isInteractive();
        } else {
            isScreenOn = powerManager.isScreenOn();//Nếu API min nhỏ hơn 20
        }
        if (isScreenOn) {
            if (!isTurnOff&&AlarmCountDownTimer.getInstance().isTimerRunning()) {
                AlarmNotification.getInstance().sendAlarmNotification(CancelAlarmScreenActivity.this, bundle);
            }
            Log.d("cancel screen","screen on");
            finish();
        }
        else
            Log.d("cancel screen","screen off");
    }
    @Override
    public void onTurnOffAlarm() {
        isTurnOff=true;
        AlarmMusic.getInstance().TurnOffMusic();
        AlarmVibration.getInstance().TurnOffVibrate();
        AlarmCountDownTimer.getInstance().CancelTimer();
        finish();
    }
}