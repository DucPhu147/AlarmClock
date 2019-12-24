package com.example.alarmclock.MainSetting;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.alarmclock.R;

public class SelectVolumeDialog extends DialogFragment {
    private Context context;
    private SetData listener;
    private Button btnXacNhan,btnHuy;
    private SeekBar seekBar;
    private int currentVolume=0;
    private AudioManager audioManager;
    public SelectVolumeDialog(final Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.main_setting_select_volume_dialog);
        btnHuy=dialog.findViewById(R.id.btnVolumeHuy);
        btnXacNhan=dialog.findViewById(R.id.btnVolumeXacNhan);
        seekBar=dialog.findViewById(R.id.seekbarVolume);

        audioManager=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        SharedPreferences preferences = context.getSharedPreferences("MyAlarm", Context.MODE_PRIVATE);
        currentVolume = preferences.getInt("alarmVolume", audioManager.getStreamVolume(AudioManager.STREAM_ALARM));
        seekBar.setProgress(currentVolume);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.getVolume(currentVolume);
                dismiss();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentVolume=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
