package com.example.alarmclock.MainSetting;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.alarmclock.R;

public class SelectSnoozeTimeDialog extends DialogFragment {
    private Context context;
    private NumberPicker NPsnoozeTime,NPsnoozeCount;
    private Button btnXacNhan,btnHuy;
    private int snoozeCount;
    private long snoozeTime;
    private SetData listener;
    public SelectSnoozeTimeDialog(Context context)
    {
        this.context=context;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.main_setting_select_snooze_time_dialog);
        btnHuy=dialog.findViewById(R.id.btnSnoozeTimeHuy);
        btnXacNhan=dialog.findViewById(R.id.btnSnoozeTimeXacNhan);
        NPsnoozeCount=dialog.findViewById(R.id.NPsnoozeCount);
        NPsnoozeTime=dialog.findViewById(R.id.NPsnoozeTime);
        NPsnoozeTime.setMaxValue(7);
        NPsnoozeTime.setMinValue(0);
        NPsnoozeTime.setFocusable(false);
        final String[] snoozeTimeValue=new String[]{"1 phút", "3 phút", "5 phút", "10 phút","15 phút","20 phút", "25 phút","30 phút"};
        NPsnoozeTime.setDisplayedValues(snoozeTimeValue);

        NPsnoozeCount.setMaxValue(4);
        NPsnoozeCount.setMinValue(0);
        NPsnoozeCount.setFocusable(false);
        final String[] snoozeCountValue=new String[]{"1 lần", "2 lần", "3 lần", "5 lần","10 lần"};
        NPsnoozeCount.setDisplayedValues(snoozeCountValue);

        SharedPreferences preferences = context.getSharedPreferences("MyAlarm", Context.MODE_PRIVATE);
        snoozeTime=preferences.getLong("alarmSnoozeTime",60000);
        snoozeCount=preferences.getInt("alarmSnoozeCount",3);
        for(int i=0;i<snoozeTimeValue.length;i++)
        {
            if(snoozeTime/60000==Integer.parseInt(snoozeTimeValue[i].split(" ")[0]))
                NPsnoozeTime.setValue(i);
        }
        for(int i=0;i<snoozeCountValue.length;i++)
        {
            if(snoozeCount==Integer.parseInt(snoozeCountValue[i].split(" ")[0]))
                NPsnoozeCount.setValue(i);
        }
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value=Integer.parseInt(snoozeTimeValue[NPsnoozeTime.getValue()].split(" ")[0]); //Xóa dấu cách và lấy số trước dấu cách

                snoozeTime=value*60000; //nhân với 60000 milisec
                snoozeCount=Integer.parseInt(snoozeCountValue[NPsnoozeCount.getValue()].split(" ")[0]); //Xóa dấu cách và lấy số trước dấu cách
                listener.getSnoozeTime(snoozeTime,snoozeCount);
                dismiss();
            }
        });
        return dialog;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(SetData)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }
}
