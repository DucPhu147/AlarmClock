package com.example.alarmclock.Alarm.AlarmClass;

import java.io.Serializable;
import java.util.UUID;

public class Alarm implements Serializable  {
    private UUID alarmID;
    private int hour;
    private int minute;
    private int music;
    private int cancelMethod;
    private int[] repeatDay;
    private boolean vibrate;
    private boolean status;
    private String label;
    private int shakeTime;
    private int mathDifficult;
    private int shakeDifficult; //0 là dễ, 1 là bth, 2 là khó

    public Alarm() {
        alarmID = UUID.randomUUID();
        hour = 0;
        minute = 0;
        music = 0;
        cancelMethod = 0;
        label = "";
        status = true;
        vibrate = false;
        repeatDay = new int[7];
        shakeTime = 0;
        mathDifficult=0;
        shakeDifficult=0;
    }

    public Alarm(UUID id, int hour, int minute, int music, int method, boolean vibrate, int[] repeatday,boolean status, String label, int shakeTime,int mathDifficult,int shakeDifficult) {
        this.alarmID = id;
        this.hour = hour;
        this.minute = minute;
        this.music = music;
        this.cancelMethod = method;
        this.vibrate = vibrate;
        this.repeatDay = repeatday;
        this.status = status;
        this.label = label;
        this.shakeTime = shakeTime;
        this.mathDifficult=mathDifficult;
        this.shakeDifficult=shakeDifficult;
    }

    public void setAlarmID(UUID id) {
        this.alarmID = id;
    }

    public void setLabel(String lb) {
        this.label = lb;
    }

    public void setHour(int h) {
        this.hour = h;
    }

    public void setMinute(int m) {
        this.minute = m;
    }

    public void setMusic(int n) {
        this.music = n;
    }

    public void setCancelMethod(int c) {
        this.cancelMethod = c;
    }

    public void setVibrate(boolean v) {
        this.vibrate = v;
    }

    public void setRepeatday(int[] d) {
        this.repeatDay = d;
    }

    public void setStatus(boolean s) {
        this.status = s;
    }

    public void setShakeTime(int shakeTime) {
        this.shakeTime = shakeTime;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public boolean getVibrate() {
        return this.vibrate;
    }

    public int getMusic() {
        return this.music;
    }

    public int getCancelMethod() {
        return this.cancelMethod;
    }

    public int[] getRepeatday() {
        return this.repeatDay;
    }

    public boolean getStatus() {
        return this.status;
    }

    public String getLabel() {
        return this.label;
    }

    public UUID getAlarmID() {
        return alarmID;
    }

    public int getShakeTime() {
        return shakeTime;
    }

    public int getMathDifficult() {
        return mathDifficult;
    }

    public void setMathDifficult(int mathDifficult) {
        this.mathDifficult = mathDifficult;
    }

    public int getShakeDifficult() {
        return shakeDifficult;
    }

    public void setShakeDifficult(int shakeDifficult) {
        this.shakeDifficult = shakeDifficult;
    }
}
