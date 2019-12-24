package com.example.alarmclock.Alarm.SelectCancelAlarmMethod;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.alarmclock.Alarm.AlarmClass.Alarm;
import com.example.alarmclock.R;

public class SelectSolveMathMethodDialog extends DialogFragment {
    private Context context;
    private int mathdifficult;
    private String action;
    private Button btnHuy,btnXacNhan;
    private TextView txtDifficult;
    private SetData listener;
    private Alarm alarm;
    RadioButton radioMathEasy,radioMathNormal,radioMathHard;
    public SelectSolveMathMethodDialog(Context context, String action, Alarm alarm) {
        this.context=context;
        this.action=action;
        this.alarm=alarm;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.alarm_scm_math_difficult_dialog);
        txtDifficult=dialog.findViewById(R.id.txtMathDiff);
        btnHuy=dialog.findViewById(R.id.btnMathHuy);
        btnXacNhan=dialog.findViewById(R.id.btnMathXacNhan);
        radioMathEasy=dialog.findViewById(R.id.radioMathEasy);
        radioMathNormal=dialog.findViewById(R.id.radioMathNormal);
        radioMathHard=dialog.findViewById(R.id.radioMathHard);
        if(action.equals("edit")) {
            if(alarm.getCancelMethod()==1) {
                if (alarm.getMathDifficult() == 0) {
                    radioMathEasy.setChecked(true);
                    txtDifficult.setText("14 + 25 = ???");
                } else if (alarm.getMathDifficult() == 1) {
                    radioMathNormal.setChecked(true);
                    txtDifficult.setText("68 + 150 = ???");
                } else if (alarm.getMathDifficult() == 2) {
                    radioMathHard.setChecked(true);
                    txtDifficult.setText("670 + 321= ???");
                }
            }
        }
        radioMathEasy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(radioMathEasy.isChecked())
                    txtDifficult.setText("14 + 25 = ???");
            }
        });
        radioMathNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(radioMathNormal.isChecked())
                    txtDifficult.setText("68 + 150 = ???");
            }
        });
        radioMathHard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(radioMathHard.isChecked())
                    txtDifficult.setText("670 + 321= ???");
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
                if(radioMathEasy.isChecked())
                    mathdifficult=0;
                else if(radioMathNormal.isChecked())
                    mathdifficult=1;
                else if(radioMathHard.isChecked())
                    mathdifficult=2;
                dismiss();
                listener.setMathMethod(mathdifficult);
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
