package com.example.alarmclock.Alarm.AlarmMain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.alarmclock.Alarm.AlarmClass.Alarm;
import com.example.alarmclock.Alarm.AlarmClass.AlarmHelper;
import com.example.alarmclock.Alarm.AlarmSetting.AlarmSettingActivity;
import com.example.alarmclock.R;

import java.util.ArrayList;

//Lớp adapter
public class AlarmListAdapter extends ArrayAdapter<Alarm> {
    private Context context;
    private int resource;
    private ArrayList<Alarm> objects;
    public AlarmListAdapter(Context context, int resource, ArrayList<Alarm> objects) {
        super(context, resource, objects);
        this.context = context;
            this.resource = resource;
        this.objects = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View row = LayoutInflater.from(context).inflate(this.resource, null);
        final TextView txtTime = row.findViewById(R.id.txtTime);
        final TextView txtRepeat = row.findViewById(R.id.txtRepeatDay);
        final TextView txtLabel = row.findViewById(R.id.txtLabel);
        final ImageView imageView = row.findViewById(R.id.imgCancelMethod);
        final ImageView imageView2 = row.findViewById(R.id.imgRemove);
        Switch status = row.findViewById(R.id.status);
        final ConstraintLayout constraintLayout = row.findViewById(R.id.alarm_item_layout);
        final Alarm alarm = objects.get(position);
        //Thời gian
        String hour = "" + alarm.getHour(), minute = "" + alarm.getMinute();
        if (alarm.getHour() < 10)
            hour = "0" + alarm.getHour();
        if (alarm.getMinute() < 10)
            minute = "0" + alarm.getMinute();
        txtTime.setText(hour + ":" + minute);
        //Cách tắt
        switch (alarm.getCancelMethod()) {
            case 0:
                imageView.setImageResource(R.drawable.ic_access_alarms_black_24dp);
                break;
            case 1:
                imageView.setImageResource(R.drawable.ic_calculator_black_24dp);
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_vibration_black_24dp);
                break;
        }
        //Ngày lặp
        String day = "";
        int dem = 0;
        int[] repeat = alarm.getRepeatday();
        for (int i = 0; i < repeat.length - 1; i++) {
            if (repeat[i] != 0) {
                day = day + "T" + repeat[i] + " ";
                dem++;
            }
        }
        if (repeat[6] != 0) {
            day = day + "CN";
            dem++;
        }
        if (dem == 7)
            day = "Hằng ngày";
        else if (dem == 0)
            day = "Một lần";
        txtRepeat.setText(day);
        //Nhãn báo thức
        txtLabel.setText(alarm.getLabel());
        //Nút status
        if (!alarm.getStatus()) {
            txtTime.setTextColor(context.getResources().getColor(R.color.Grey));
            txtRepeat.setTextColor(context.getResources().getColor(R.color.Grey));
            txtLabel.setTextColor(context.getResources().getColor(R.color.Grey));
            imageView.setImageAlpha(70);
            imageView2.setImageAlpha(70);
            status.setChecked(false);
        }
        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlarmHelper.getInstance().createAlarm(context,alarm);
                    alarm.setStatus(true);
                    txtTime.setTextColor(context.getResources().getColor(R.color.Black));
                    txtRepeat.setTextColor(context.getResources().getColor(R.color.Black));
                    txtLabel.setTextColor(context.getResources().getColor(R.color.Black));
                    imageView.setColorFilter(context.getResources().getColor(R.color.Black));
                    imageView2.setColorFilter(context.getResources().getColor(R.color.Black));
                } else {
                    AlarmHelper.getInstance().removeAlarm(context,alarm);
                    alarm.setStatus(false);
                    txtTime.setTextColor(context.getResources().getColor(R.color.Grey));
                    txtRepeat.setTextColor(context.getResources().getColor(R.color.Grey));
                    txtLabel.setTextColor(context.getResources().getColor(R.color.Grey));
                    imageView.setColorFilter(context.getResources().getColor(R.color.Grey));
                    imageView2.setColorFilter(context.getResources().getColor(R.color.Grey));
                }
            }
        });
        //Nút 3 chấm
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.getMenuInflater().inflate(R.menu.alarm_item_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.remove) {
                            AlarmHelper.getInstance().removeAlarm(context,alarm);
                            objects.remove(alarm);
                            notifyDataSetChanged();
                        }
                        return true;
                    }
                });
            }
        });
        //Khi bấm vào 1 báo thức
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(row.getContext(), AlarmSettingActivity.class);
                intent.putExtra("Action", "edit");
                intent.putExtra("Alarmy", alarm);
                ((Activity)context).startActivityForResult(intent, 2);
            }
        });
        return row;
    }
}
