package com.example.alarmclock;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.alarmclock.Alarm.AlarmMain.AlarmMainFragment;
import com.example.alarmclock.StopWatch.StopWatchFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter( FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return AlarmMainFragment.getInstance();
            case 1: return StopWatchFragment.getInstance();
            case 2: return CDTimer.getInstance();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}