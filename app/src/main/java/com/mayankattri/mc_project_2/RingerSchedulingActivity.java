package com.mayankattri.mc_project_2;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

public class RingerSchedulingActivity extends AppCompatActivity {

    Receiver alarm2 = new Receiver();
    public static String hour2, minute2;
    ArrayAdapter<String> adapterRinger;
    ArrayList<String> timeArray;
    public static EditText txtTime;
    public static SharedPreferences prefs1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringer_scheduling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button set = (Button) findViewById(R.id.B_ringer);
        txtTime = (EditText) findViewById(R.id.ET_ringer);
        ListView listView = (ListView) findViewById(R.id.listview_ringer);
        timeArray = new ArrayList<>();

        prefs1 = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs1 != null) {
            Map<String,?> keys = prefs1.getAll();
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.d("map values", entry.getKey() + ": " +
                        entry.getValue().toString());
                timeArray.add(entry.getValue().toString());
            }
        }

        adapterRinger = new ArrayAdapter<>(this, R.layout.list_item_ringer, R.id.textview_ringer, timeArray);
        listView.setAdapter(adapterRinger);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                final int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(RingerSchedulingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtTime.setText(hourOfDay + ":" + minute);
                                hour2 = String.valueOf(hourOfDay);
                                minute2 = String.valueOf(minute);
                                addData(hour2+":"+minute2);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
    }

    public void addData(String time) {
        adapterRinger.add(time);
        timeArray.add(time);
        adapterRinger.notifyDataSetChanged();
        Long key = System.currentTimeMillis();
        int code = key.intValue();
        prefs1.edit().putString(Long.toString(key), time).apply();
        alarm2.setAlarmRinger(RingerSchedulingActivity.this, code, time);
    }

}
