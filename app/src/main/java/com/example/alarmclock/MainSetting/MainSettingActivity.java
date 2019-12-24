package com.example.alarmclock.MainSetting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.alarmclock.R;

public class MainSettingActivity extends AppCompatActivity implements SetData {
    private ConstraintLayout layoutVolume,layoutIncreaseVolume,layoutSnoozeTime,layoutActiveTime;
    private Switch swIncreaseVolume;
    private SharedPreferences sharedPreferences;
    private boolean isIncreaseVolume=false;
    private SharedPreferences.Editor editor;
    private TextView txtSnooze;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_setting_activity);
        getSupportActionBar().setElevation(3);
        getSupportActionBar().setTitle("Cài đặt");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layoutIncreaseVolume=findViewById(R.id.layoutAlarmIncreaseVolume);
        layoutSnoozeTime=findViewById(R.id.layoutSnoozeTime);
        layoutVolume=findViewById(R.id.layoutAlarmVolume);
        layoutActiveTime=findViewById(R.id.layoutAlarmActiveTime);
        swIncreaseVolume=findViewById(R.id.swAlarmIncreaseVolume);
        txtSnooze=findViewById(R.id.txtSnooze);

        sharedPreferences = getSharedPreferences("MyAlarm", MODE_PRIVATE);
        editor=sharedPreferences.edit();

        long snoozeTime=sharedPreferences.getLong("alarmSnoozeTime",60000);
        int snoozeCount=sharedPreferences.getInt("alarmSnoozeCount",3);
        txtSnooze.setText("Mỗi "+snoozeTime/60000+" phút | Kêu "+snoozeCount+" lần");
        isIncreaseVolume=sharedPreferences.getBoolean("isIncreaseVolumeOverTime", false);
        if(isIncreaseVolume)
            swIncreaseVolume.setChecked(true);
        else
            swIncreaseVolume.setChecked(false);

        layoutVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectVolumeDialog dialog=new SelectVolumeDialog(MainSettingActivity.this);
                dialog.show(getSupportFragmentManager(),"select volume");
            }
        });
        layoutSnoozeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectSnoozeTimeDialog dialog=new SelectSnoozeTimeDialog(MainSettingActivity.this);
                dialog.show(getSupportFragmentManager(),"select snooze time");
            }
        });
        layoutActiveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        layoutIncreaseVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!swIncreaseVolume.isChecked()) {
                    swIncreaseVolume.setChecked(true);
                    isIncreaseVolume=true;
                }
                else {
                    swIncreaseVolume.setChecked(false);
                    isIncreaseVolume=false;
                }
                editor.putBoolean("isIncreaseVolumeOverTime",isIncreaseVolume);
                editor.commit();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getVolume(int volume) {
        editor.putInt("alarmVolume",volume);
        editor.commit();
    }

    @Override
    public void getSnoozeTime(long snoozeTime,int snoozeCount) {
        txtSnooze.setText("Mỗi "+snoozeTime/60000+" phút | Kêu "+snoozeCount+" lần");
        editor.putLong("alarmSnoozeTime",snoozeTime);
        editor.putInt("alarmSnoozeCount",snoozeCount);
        editor.commit();
    }
}
