package com.example.alarmclock.StopWatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alarmclock.R;

import java.util.ArrayList;

public class StopWatchFragment extends Fragment {

    private static final StopWatchFragment ourInstance = new StopWatchFragment();

    public static StopWatchFragment getInstance() {
        return ourInstance;
    }

    private Button btnReset,btnStart, btnPause;
    private TextView txtTimer;
    private long lStartTime, lPauseTime, lSystemTime;
    private Handler handler = new Handler();
    private ListView listview;
    private int i=1;
    private ArrayList<String> stopwatchList=new ArrayList<>();
    private StopWatchListViewAdapter adapter;
    boolean isRun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.stop_watch_activity, container, false);
        btnStart = root.findViewById(R.id.btnStart);
        btnReset = root.findViewById(R.id.btnReset);
        btnPause = root.findViewById(R.id.btnPause);
        txtTimer = root.findViewById(R.id.txtStopWatchTime);

        listview=root.findViewById(R.id.listViewSW);
        adapter = new StopWatchListViewAdapter(getContext(), R.layout.stop_watch_listview_item, stopwatchList);
        listview.setAdapter(adapter);

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRun)
                    return;
                isRun = false;
                lPauseTime += lSystemTime;
                btnStart.setText("TIẾP TỤC");
                handler.removeCallbacks(runnable);
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRun) {
                    long SelectTime=0;
                    SelectTime+=lSystemTime;
                    long secs = SelectTime/1000;
                    long mins= secs/60;
                    secs = secs %60;
                    long milliseconds = (SelectTime%1000) /10;
                    String time =(String.format("%02d",mins)+":" + String.format("%02d",secs) + ":" + String.format("%02d",milliseconds));
                    stopwatchList.add(String.format("%02d",i)+" "+time);
                    //adapter.insert(i+". "+time,0);

                    i=i+1;
                    adapter.notifyDataSetChanged();
                    listview.smoothScrollToPosition(0);
                }else {
                    isRun = true;
                    btnStart.setText("CHỌN");
                    lStartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRun = false;
                lPauseTime = 0;
                txtTimer.setText("00:00:00");
                btnStart.setText("BẮT ĐẦU");
                stopwatchList.clear();
                i=1;
                adapter.notifyDataSetChanged();
                handler.removeCallbacks(runnable);
            }
        });
        return root;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            lSystemTime = SystemClock.uptimeMillis() - lStartTime;
            long lUpdateTime = lPauseTime + lSystemTime;
            long secs = lUpdateTime/1000;
            long mins= secs/60;
            secs = secs %60;
            long milliseconds = (lUpdateTime%1000) /10;
            txtTimer.setText(String.format("%02d",mins)+":" + String.format("%02d",secs) + ":" + String.format("%02d",milliseconds));
            handler.postDelayed(this,0);
        }
    };
}
