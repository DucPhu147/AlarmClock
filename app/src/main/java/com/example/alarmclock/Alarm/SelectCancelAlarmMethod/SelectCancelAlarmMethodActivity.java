package com.example.alarmclock.Alarm.SelectCancelAlarmMethod;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.alarmclock.Alarm.AlarmClass.Alarm;
import com.example.alarmclock.R;

public class SelectCancelAlarmMethodActivity extends AppCompatActivity implements SetData {
    String cancel;
    int method=0,shakedifficult=0,mathdifficult=0,shakeTime=0;
    ConstraintLayout cancelGiaiToan,cancelLacDT,cancelMacDinh;
    Alarm alarm;
    ImageView imgDefault1,imgShake1,imgMath1,imgDefault2,imgShake2,imgMath2;
    TextView txtDefault,txtMath,txtShake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_scm_activity);
        getSupportActionBar().setElevation(3);
        getSupportActionBar().setTitle("Cách tắt báo thức");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtDefault=findViewById(R.id.labelDefault);
        txtMath=findViewById(R.id.labelMath);
        txtShake=findViewById(R.id.labelShake);
        cancelGiaiToan= findViewById(R.id.cancelGiaiToan);
        cancelLacDT= findViewById(R.id.cancelLacDT);
        cancelMacDinh= findViewById(R.id.cancelMacDinh);
        imgDefault1=findViewById(R.id.imgDefault1);
        imgMath1=findViewById(R.id.imgMath1);
        imgShake1=findViewById(R.id.imgShake1);
        imgDefault2=findViewById(R.id.imgDefault2);
        imgMath2=findViewById(R.id.imgMath2);
        imgShake2=findViewById(R.id.imgShake2);
        if(getIntent().getStringExtra("Action").equals("edit"))
        {
            alarm= (Alarm) getIntent().getSerializableExtra("Alarmy");
            if(alarm.getCancelMethod()==0)
            {
                txtDefault.setTextColor(getResources().getColor(R.color.colorAccent));
                imgDefault1.setColorFilter(getResources().getColor(R.color.colorAccent));
                imgDefault2.setColorFilter(getResources().getColor(R.color.colorAccent));
            }
            else if(alarm.getCancelMethod()==1)
            {
                txtMath.setTextColor(getResources().getColor(R.color.colorAccent));
                imgMath1.setColorFilter(getResources().getColor(R.color.colorAccent));
                imgMath2.setColorFilter(getResources().getColor(R.color.colorAccent));
            }
            else if(alarm.getCancelMethod()==2)
            {
                txtShake.setTextColor(getResources().getColor(R.color.colorAccent));
                imgShake1.setColorFilter(getResources().getColor(R.color.colorAccent));
                imgShake2.setColorFilter(getResources().getColor(R.color.colorAccent));
            }
        }
        cancelMacDinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel="Mặc định";
                method=0;
                Intent intent=new Intent();
                intent.putExtra("Selected method text",cancel);
                intent.putExtra("Selected method",method);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        cancelGiaiToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectSolveMathMethodDialog dialog=new SelectSolveMathMethodDialog(SelectCancelAlarmMethodActivity.this,getIntent().getStringExtra("Action"),alarm);
                dialog.show(getSupportFragmentManager(),"solve math method");
            }
        });
        cancelLacDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectShakeMethodDialog dialog=new SelectShakeMethodDialog(SelectCancelAlarmMethodActivity.this,getIntent().getStringExtra("Action"),alarm);
                dialog.show(getSupportFragmentManager(),"shake method");
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }
    @Override
    public void setMathMethod(int mathDiff) {
        mathdifficult=mathDiff;
        Intent intent=new Intent();
        intent.putExtra("Selected method text","Giải toán");
        intent.putExtra("Selected method",1);
        intent.putExtra("Math difficult",mathdifficult);
        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    public void setShakeMethod(int shaketime, int shakeDiff) {
        Intent intent=new Intent();
        shakedifficult=shakeDiff;
        shakeTime=shaketime;
        intent.putExtra("Selected method text","Lắc điện thoại");
        intent.putExtra("Selected method",2);
        intent.putExtra("Shake difficult",shakedifficult);
        intent.putExtra("Shake time",shakeTime);
        setResult(RESULT_OK,intent);
        finish();
    }
}
