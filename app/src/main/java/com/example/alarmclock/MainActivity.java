package com.example.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.alarmclock.Alarm.AlarmClass.Alarm;
import com.example.alarmclock.Alarm.AlarmMain.AlarmMainFragment;
import com.example.alarmclock.Alarm.AlarmSetting.AlarmSettingActivity;
import com.example.alarmclock.MainSetting.MainSettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getSupportActionBar().setElevation(3);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.navigation_alarm)
                {
                    viewPager.setCurrentItem(0);
                    return true;
                }
                else if(menuItem.getItemId()==R.id.navigation_stopwatch)
                {
                    viewPager.setCurrentItem(1);
                    return true;
                }
                else if(menuItem.getItemId()==R.id.navigation_timer)
                {
                    viewPager.setCurrentItem(2);
                    return true;
                }
                else
                    return false;
            }
        });
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmSettingActivity.class);
                intent.putExtra("Action", "add");
                startActivityForResult(intent, 1);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0) {
                    fab.show();
                    navView.setSelectedItemId(R.id.navigation_alarm);
                }
                else if(position==1) {
                    fab.hide();
                    navView.setSelectedItemId(R.id.navigation_stopwatch);
                }else
                {
                    fab.hide();
                    navView.setSelectedItemId(R.id.navigation_timer);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting)
        {
            Intent myIntent = new Intent(MainActivity.this, MainSettingActivity.class);
            startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            Alarm alarm = (Alarm) data.getSerializableExtra("Alarmy");
            if (requestCode == 1) {
                AlarmMainFragment.getInstance().AddAlarm(alarm);
            } else if (requestCode == 2) {
                AlarmMainFragment.getInstance().UpdateAlarm(alarm);
            }
        }
    }
}