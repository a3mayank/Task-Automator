package com.mayankattri.mc_project_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class RingerSchedulingActivity extends AppCompatActivity {

    Receiver alarm2 = new Receiver();
    TimePicker time2;
    public static String year2, month2, day2, hour2, minute2;

    public static String getHour() {
        return hour2;
    }
    public static String getMinute() {
        return minute2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringer_scheduling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        time2 = (TimePicker) findViewById(R.id.timePicker2);
        Button done2 = (Button) findViewById(R.id.B_done2);

        time2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minuteOfHour) {
                hour2 = String.valueOf(hourOfDay);
                minute2 = String.valueOf(minuteOfHour);
            }
        });

        done2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm2.setAlarmRinger(RingerSchedulingActivity.this);
            }
        });
    }

}
