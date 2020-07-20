package com.example.alarmclock.Alarm.AlarmSetting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.alarmclock.Alarm.AlarmClass.Alarm;
import com.example.alarmclock.Alarm.SelectCancelAlarmMethod.SelectCancelAlarmMethodActivity;
import com.example.alarmclock.R;

public class AlarmSettingActivity extends AppCompatActivity implements SelectCancelDayDialog.GetRepeatDay{
    TextView txtSelectCancelMethod,txtSelectMusic,txtSelectRepeatDay;
    TimePicker timePicker;
    EditText editLabel;
    Switch vibSwitch;
    int selectmusic=R.raw.macdinh,cancelMethod=0,shakeDifficult=0,mathDifficult=0,shakeTime=0;
    int[] repeatDay=new int[7];
    ConstraintLayout constraintLayoutSelectMusic,constraintLayoutSelectCancelMethod,constraintLayoutVibrator,constraintLayoutRepeat;
    Alarm alarm=new Alarm();
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_setting_activity);
        getSupportActionBar().setElevation(3);
        getSupportActionBar().setTitle("Thêm báo thức");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        for(int i=0;i<7;i++)
            repeatDay[i]=0;
        txtSelectCancelMethod = findViewById(R.id.txtselectcancelalarm);
        txtSelectMusic = findViewById(R.id.txtselectmusic);
        txtSelectRepeatDay= findViewById(R.id.txtselectrepeat);
        timePicker = findViewById(R.id.timePicker);
        vibSwitch = findViewById(R.id.swVibrator);
        constraintLayoutSelectMusic = findViewById(R.id.layoutselectmusic);
        constraintLayoutSelectCancelMethod = findViewById(R.id.layoutselectcancelalarm);
        constraintLayoutVibrator= findViewById(R.id.layoutvibrator);
        constraintLayoutRepeat= findViewById(R.id.layoutselectrepeat);
        editLabel= findViewById(R.id.editLabel);
        timePicker.setIs24HourView(true);
        //Nếu người dùng sửa báo thức
        if(getIntent().getStringExtra("Action").equals("edit")) {
            getSupportActionBar().setTitle("Sửa báo thức");
            alarm = (Alarm) getIntent().getSerializableExtra("Alarmy");
            repeatDay = alarm.getRepeatday();
            vibSwitch.setChecked(alarm.getVibrate());
            selectmusic = alarm.getMusic();
            cancelMethod = alarm.getCancelMethod();
            timePicker.setCurrentHour(alarm.getHour());
            timePicker.setCurrentMinute(alarm.getMinute());
            editLabel.setText(alarm.getLabel());
            if (selectmusic == R.raw.macdinh)
                txtSelectMusic.setText("Mặc định");
            else if (selectmusic == R.raw.tiengsoi)
                txtSelectMusic.setText("Sói hú");
            else if (selectmusic == R.raw.tiengchim)
                txtSelectMusic.setText("Chim hót");
            else if (selectmusic == R.raw.guitar)
                txtSelectMusic.setText("Guitar");
            if (cancelMethod == 0)
                txtSelectCancelMethod.setText("Mặc định");
            else if (cancelMethod == 1)
                txtSelectCancelMethod.setText("Giải toán");
            else if (cancelMethod == 2)
                txtSelectCancelMethod.setText("Lắc điện thoại");
            setRepeatDayText();
        }
        constraintLayoutSelectCancelMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmSettingActivity.this, SelectCancelAlarmMethodActivity.class);
                intent.putExtra("Alarmy",alarm);
                intent.putExtra("Action","edit");
                startActivityForResult(intent,100);
            }
        });
        constraintLayoutVibrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vibSwitch.isChecked()) {
                    vibSwitch.setChecked(true);
                }
                else {
                    vibSwitch.setChecked(false);
                }
            }
        });
        constraintLayoutRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCancelDayDialog dialog=new SelectCancelDayDialog(repeatDay);
                dialog.show(getSupportFragmentManager(),"cancel day");
            }
        });
        constraintLayoutSelectMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSelectMusic();
            }
        });
    }
    public void DialogSelectMusic()
    {
        final Dialog dialog = new Dialog(AlarmSettingActivity.this);
        dialog.setContentView(R.layout.alarm_setting_music_dialog);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        LinearLayout musicMacDinh = dialog.findViewById(R.id.musicMacDinh);
        LinearLayout musicSoi = dialog.findViewById(R.id.musicSoi);
        LinearLayout musicChim = dialog.findViewById(R.id.musicChim);
        LinearLayout musicGuitar = dialog.findViewById(R.id.musicGuitar);
        Button btnMusicHuy = dialog.findViewById(R.id.btnMusicHuy);
        musicMacDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSelectMusic.setText("Mặc định");
                selectmusic=R.raw.macdinh;
                dialog.cancel();
            }
        });
        musicSoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSelectMusic.setText("Sói hú");
                selectmusic=R.raw.tiengsoi;
                dialog.cancel();
            }
        });
        musicChim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSelectMusic.setText("Chim hót");
                selectmusic=R.raw.tiengchim;
                dialog.cancel();
            }
        });
        musicGuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSelectMusic.setText("Guitar");
                selectmusic=R.raw.guitar;
                dialog.cancel();
            }
        });
        btnMusicHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save)
        {
            alarm.setHour(timePicker.getCurrentHour());
            alarm.setMinute(timePicker.getCurrentMinute());
            alarm.setMusic(selectmusic);
            alarm.setRepeatday(repeatDay);
            alarm.setVibrate(vibSwitch.isChecked());
            alarm.setLabel(editLabel.getText().toString());
            alarm.setStatus(true);
            Intent myIntent = new Intent();
            myIntent.putExtra("Alarmy",alarm);
            setResult(RESULT_OK, myIntent);
            finish();
        }
        else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                cancelMethod = data.getIntExtra("Selected method", 0);
                shakeDifficult = data.getIntExtra("Shake difficult", 0);
                mathDifficult = data.getIntExtra("Math difficult", 0);
                shakeTime = data.getIntExtra("Shake time", 5);
                txtSelectCancelMethod.setText(data.getStringExtra("Selected method text"));
                alarm.setShakeTime(shakeTime);
                alarm.setMathDifficult(mathDifficult);
                alarm.setShakeDifficult(shakeDifficult);
                alarm.setCancelMethod(cancelMethod);
            }
        }
    }
    public void setRepeatDayText()
    {
        int dem = 0;
        String day = "";
        for (int i = 0; i < repeatDay.length - 1; i++) {
            if (repeatDay[i] != 0) {
                day = day + "T" + repeatDay[i] + " ";
                dem++;
            }
        }
        if (repeatDay[6] != 0) {
            day = day + "CN";
            dem++;
        }
        if (dem == 0)
            day = " Một lần";
        else if (dem == 7)
            day = "Hằng ngày";
        txtSelectRepeatDay.setText(day);
    }
    @Override
    public void getRepeatDay(int[] repeatDay) {
        this.repeatDay=repeatDay;
        setRepeatDayText();
    }
}