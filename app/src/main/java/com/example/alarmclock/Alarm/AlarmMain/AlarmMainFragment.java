package com.example.alarmclock.Alarm.AlarmMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.alarmclock.Alarm.AlarmClass.Alarm;
import com.example.alarmclock.Alarm.AlarmClass.AlarmHelper;
import com.example.alarmclock.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AlarmMainFragment extends Fragment {

    private ListView listAlarm;
    private ArrayList<Alarm> alarmList = new ArrayList<>();
    private AlarmListAdapter alarmListAdapter;

    private static final AlarmMainFragment ourInstance = new AlarmMainFragment();

    public static AlarmMainFragment getInstance() {
        return ourInstance;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.alarm_main_fragment, container, false);
        listAlarm = root.findViewById(R.id.listAlarm);
        alarmListAdapter = new AlarmListAdapter(getContext(), R.layout.alarm_main_object, alarmList);
        listAlarm.setAdapter(alarmListAdapter);

        //Đọc từ file
        try {
            FileInputStream fin = getContext().openFileInput("Alarm.txt");
            ObjectInputStream in = new ObjectInputStream(fin);
            alarmList = (ArrayList<Alarm>) in.readObject();
            alarmListAdapter = new AlarmListAdapter(getContext(), R.layout.alarm_main_object, alarmList);
            listAlarm.setAdapter(alarmListAdapter);
            in.close();
            fin.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return root;
    }
    public void AddAlarm(Alarm alarm)
    {
        AlarmHelper.getInstance().createAlarm(getContext(),alarm);
        alarmList.add(alarm);
        alarmListAdapter.notifyDataSetChanged();
    }
    public void UpdateAlarm(Alarm alarm)
    {
        for (int i = 0; i < alarmList.size(); i++) {
            if (alarmList.get(i).getAlarmID().hashCode() == alarm.getAlarmID().hashCode()) {
                alarmList.get(i).setHour(alarm.getHour());
                alarmList.get(i).setMinute(alarm.getMinute());
                alarmList.get(i).setMusic(alarm.getMusic());
                alarmList.get(i).setCancelMethod(alarm.getCancelMethod());
                alarmList.get(i).setRepeatday(alarm.getRepeatday());
                alarmList.get(i).setVibrate(alarm.getVibrate());
                alarmList.get(i).setLabel(alarm.getLabel());
                alarmList.get(i).setShakeDifficult(alarm.getShakeDifficult());
                alarmList.get(i).setShakeTime(alarm.getShakeTime());
                alarmList.get(i).setMathDifficult(alarm.getMathDifficult());
                alarmList.get(i).setStatus(true);
                AlarmHelper.getInstance().createAlarm(getContext(),alarmList.get(i));
            }
        }
        alarmListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {//Ghi file
        try {
            FileOutputStream fout = getContext().openFileOutput("Alarm.txt", getContext().MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(alarmList);
            out.close();
            fout.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onPause();
    }

}