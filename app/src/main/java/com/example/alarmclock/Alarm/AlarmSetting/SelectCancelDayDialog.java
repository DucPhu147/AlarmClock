package com.example.alarmclock.Alarm.AlarmSetting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.example.alarmclock.R;

public class SelectCancelDayDialog extends DialogFragment {
    private int[] repeatDay=new int[7];
    private  GetRepeatDay listener;
    public SelectCancelDayDialog(int[] repeatDay)
    {
        this.repeatDay=repeatDay;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.alarm_setting_repeat_day_dialog);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        Button btnxacnhan= dialog.findViewById(R.id.btnRepeatDayXacNhan);
        Button btnhuy= dialog.findViewById(R.id.btnRepeatDayHuy);
        ConstraintLayout mon= dialog.findViewById(R.id.monday);
        ConstraintLayout tue= dialog.findViewById(R.id.tuesday);
        ConstraintLayout wed= dialog.findViewById(R.id.wednesday);
        ConstraintLayout thu= dialog.findViewById(R.id.thursday);
        ConstraintLayout fri= dialog.findViewById(R.id.friday);
        ConstraintLayout sat= dialog.findViewById(R.id.saturday);
        ConstraintLayout sun= dialog.findViewById(R.id.sunday);
        final CheckBox ck2= dialog.findViewById(R.id.checkMonday);
        final CheckBox ck3= dialog.findViewById(R.id.checkTuesday);
        final CheckBox ck4= dialog.findViewById(R.id.checkWednesday);
        final CheckBox ck5= dialog.findViewById(R.id.checkThursday);
        final CheckBox ck6= dialog.findViewById(R.id.checkFriday);
        final CheckBox ck7= dialog.findViewById(R.id.checkSaturday);
        final CheckBox ck8= dialog.findViewById(R.id.checkSunday);
        if(repeatDay[0]!=0)
            ck2.setChecked(true);
        if(repeatDay[1]!=0)
            ck3.setChecked(true);
        if(repeatDay[2]!=0)
            ck4.setChecked(true);
        if(repeatDay[3]!=0)
            ck5.setChecked(true);
        if(repeatDay[4]!=0)
            ck6.setChecked(true);
        if(repeatDay[5]!=0)
            ck7.setChecked(true);
        if(repeatDay[6]!=0)
            ck8.setChecked(true);
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ck2.isChecked())
                    ck2.setChecked(false);
                else
                    ck2.setChecked(true);
            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ck3.isChecked())
                    ck3.setChecked(false);
                else
                    ck3.setChecked(true);
            }
        });
        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ck4.isChecked())
                    ck4.setChecked(false);
                else
                    ck4.setChecked(true);
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ck5.isChecked())
                    ck5.setChecked(false);
                else
                    ck5.setChecked(true);

            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ck6.isChecked())
                    ck6.setChecked(false);
                else
                    ck6.setChecked(true);
            }
        });
        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ck7.isChecked())
                    ck7.setChecked(false);
                else
                    ck7.setChecked(true);
            }
        });
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ck8.isChecked())
                    ck8.setChecked(false);
                else
                    ck8.setChecked(true);
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ck2.isChecked())
                    repeatDay[0]=2;
                else
                    repeatDay[0]=0;
                if(ck3.isChecked())
                    repeatDay[1]=3;
                else
                    repeatDay[1]=0;
                if(ck4.isChecked())
                    repeatDay[2]=4;
                else
                    repeatDay[2]=0;
                if(ck5.isChecked())
                    repeatDay[3]=5;
                else
                    repeatDay[3]=0;
                if(ck6.isChecked())
                    repeatDay[4]=6;
                else
                    repeatDay[4]=0;
                if(ck7.isChecked())
                    repeatDay[5]=7;
                else
                    repeatDay[5]=0;
                if(ck8.isChecked())
                    repeatDay[6]=1;
                else
                    repeatDay[6]=0;
                listener.getRepeatDay(repeatDay);
                dismiss();
            }
        });
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener=(GetRepeatDay) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    public interface GetRepeatDay
    {
        void getRepeatDay(int[] repeatDay);
    }
}
