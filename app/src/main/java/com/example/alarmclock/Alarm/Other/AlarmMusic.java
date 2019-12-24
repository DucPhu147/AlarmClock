package com.example.alarmclock.Alarm.Other;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;


public class AlarmMusic {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private int currentVolume,settingVolume;
    private CountDownTimer musicTimer;
    private boolean isVolumeIncreaseOverTime=false;
    private boolean isPlay=false;

    private static final AlarmMusic ourInstance = new AlarmMusic();
    public static AlarmMusic getInstance() {
        return ourInstance;
    }

    private AlarmMusic() {
    }
    public void TurnOnMusic(Context context,int music)
    {
        audioManager=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        SharedPreferences preferences = context.getSharedPreferences("MyAlarm", Context.MODE_PRIVATE);
        settingVolume= preferences.getInt("alarmVolume", currentVolume);
        isVolumeIncreaseOverTime = preferences.getBoolean("isIncreaseVolumeOverTime", false);
        if(isVolumeIncreaseOverTime) {
            final float[] volume = {-0.5f};
            musicTimer = new CountDownTimer(30000, 1000) {
                @Override                   //Đến giây thứ 30 thì âm thanh sẽ to nhất
                public void onTick(long l) {
                    if (volume[0] < 15&& volume[0] < settingVolume) {
                        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, (int) volume[0], 0);
                        volume[0] += 0.5;
                    }
                }
                @Override
                public void onFinish() {
                }
            }.start();
        }else
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM,settingVolume,0);
        try
        {
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.setDataSource(context, Uri.parse("android.resource://"+context.getPackageName()+"/"+music));
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
            isPlay = true;
        } catch (Exception ex) {
            Log.e("music", ex.toString());
        }
    }
    public void TurnOffMusic()
    {
        if(isPlay) {
            if(isVolumeIncreaseOverTime) {
                musicTimer.cancel();
            }
            mediaPlayer.stop();
            mediaPlayer.release();
            isPlay=false;
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM,currentVolume,0);
        }
    }
}
