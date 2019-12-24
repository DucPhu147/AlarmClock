package com.example.alarmclock.Alarm.SelectCancelAlarmMethod;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.alarmclock.Alarm.AlarmClass.Alarm;
import com.example.alarmclock.R;

public class SelectShakeMethodDialog extends DialogFragment {
    private Context context;
    private int shakeDiff,shakeTime;
    private String cancel,action;
    private Button btnHuy,btnXacNhan;
    private TextView txtShakeTime;
    private NumberPicker numberPicker;
    private SetData listener;
    private Alarm alarm;
    RadioButton radioShakeEasy,radioShakeNormal,radioShakeHard;
    public SelectShakeMethodDialog(Context context, String action, Alarm alarm) {
        this.context=context;
        this.action=action;
        this.alarm=alarm;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.alarm_scm_shake_difficult_dialog);
        btnHuy=dialog.findViewById(R.id.btnShakeHuy);
        btnXacNhan=dialog.findViewById(R.id.btnShakeXacNhan);
        txtShakeTime=dialog.findViewById(R.id.txtShakeTime);
        radioShakeEasy=dialog.findViewById(R.id.radioShakeEasy);
        radioShakeNormal=dialog.findViewById(R.id.radioShakeNormal);
        radioShakeHard=dialog.findViewById(R.id.radioShakeHard);
        numberPicker=dialog.findViewById(R.id.shakeNumberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(19);
        final String[] numpickerValue=new String[]{"5", "10", "15", "20", "25", "30", "35",
                "40", "45", "50", "55", "60", "65", "70",
                "75", "80", "85", "90", "95", "100"};
        numberPicker.setDisplayedValues(numpickerValue);
        if(action.equals("edit")) {
            if (alarm.getCancelMethod()== 2) {
                if (alarm.getShakeDifficult()== 0)
                    radioShakeEasy.setChecked(true);
                else if (alarm.getShakeDifficult()== 1)
                    radioShakeNormal.setChecked(true);
                else if (alarm.getShakeDifficult() == 2)
                    radioShakeHard.setChecked(true);
                for(int i=0;i<numpickerValue.length;i++)
                {
                    if(Integer.parseInt(numpickerValue[i])==alarm.getShakeTime())
                    {
                        numberPicker.setValue(i);
                        txtShakeTime.setText("Lắc điện thoại "+numpickerValue[i]+" lần");
                        break;
                    }
                }
            }
        }
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtShakeTime.setText("Lắc điện thoại "+numpickerValue[numberPicker.getValue()]+" lần");
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel="Lắc điện thoại";
                if(radioShakeEasy.isChecked())
                    shakeDiff=0;
                else if(radioShakeNormal.isChecked())
                    shakeDiff=1;
                else if(radioShakeHard.isChecked())
                    shakeDiff=2;
                shakeTime=Integer.parseInt(numpickerValue[numberPicker.getValue()]);
                dismiss();
                listener.setShakeMethod(shakeTime,shakeDiff);

            }
        });
        return dialog;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(SetData) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }
}
