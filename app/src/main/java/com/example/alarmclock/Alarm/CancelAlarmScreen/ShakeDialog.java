package com.example.alarmclock.Alarm.CancelAlarmScreen;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.alarmclock.Alarm.Other.AlarmCountDownTimer;
import com.example.alarmclock.R;

public class ShakeDialog extends DialogFragment implements SensorEventListener {
    private TextView txtShakeCount;
    private SensorManager sensorManager;
    private Sensor camBienGiaToc;
    private Context context;
    private float lastX, lastY, lastZ;
    private long lastTime = 0;
    private int ShakeTime;
    private int ShakeDifficult; //Lắc trong 2 giây mới đc tính
    private boolean isDismiss=false;
    private TurnOffAlarm listener;
    public ShakeDialog(final Context context, int shakeDifficult, int shakeTime) {
        this.context=context;
        if(shakeDifficult==0)
            ShakeDifficult = 2000;
        else if(shakeDifficult==1)
            ShakeDifficult = 4000;
        else if(shakeDifficult==2)
            ShakeDifficult = 5500;
        ShakeTime=shakeTime;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlarmCountDownTimer.getInstance().PauseTimer();
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.alarm_cancel_screen_shake_dialog);
        txtShakeCount=dialog.findViewById(R.id.txtShakeCount);
        txtShakeCount.setText("Bạn phải lắc " + ShakeTime + " lần");
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        camBienGiaToc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (camBienGiaToc == null ){ //Nếu thiết bị ko hỗ trợ cảm biến
            isDismiss=true;
            dismiss();
            listener.onTurnOffAlarm();
            dismiss();
        }else{
            sensorManager.registerListener(this, camBienGiaToc, SensorManager.SENSOR_DELAY_FASTEST);
        }
        return dialog;
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        camBienGiaToc = sensorEvent.sensor;
        if (camBienGiaToc.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastTime) > 100) {
                long diffTime = currentTime - lastTime;
                lastTime = currentTime;
                float speed = Math.abs((x + y + z - lastX - lastY - lastZ) / diffTime * 10000);
                if (speed > ShakeDifficult) {
                    ShakeTime--;
                    txtShakeCount.setText("Bạn phải lắc " + ShakeTime + " lần");
                    if (ShakeTime == 0) {
                        isDismiss=true;
                        dismiss();
                        sensorManager.unregisterListener(this,camBienGiaToc);
                        listener.onTurnOffAlarm();
                    }
                }
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(!isDismiss)
            AlarmCountDownTimer.getInstance().ResumeTimer(context);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(TurnOffAlarm) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }
}
