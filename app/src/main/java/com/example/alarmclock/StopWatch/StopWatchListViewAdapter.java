package com.example.alarmclock.StopWatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.alarmclock.R;

import java.util.ArrayList;

public class StopWatchListViewAdapter extends ArrayAdapter {
    private ArrayList<String> items;
    private Context context;
    private int resource;
    public StopWatchListViewAdapter(@NonNull Context context, int resource, ArrayList<String> items) {
        super(context, resource,items);
        this.items=items;
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View row = LayoutInflater.from(context).inflate(this.resource, null);
        final TextView txtSWtime=row.findViewById(R.id.txtSWtime);
        final TextView txtSWindex=row.findViewById(R.id.txtSWindex);
        txtSWindex.setText(items.get(position).split(" ")[0]);
        txtSWtime.setText(items.get(position).split(" ")[1]);
        return row;
    }
}
