package com.mayankattri.mc_project_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class SmsSchedulingAcivity extends AppCompatActivity {

    Receiver alarm = new Receiver();
    DatePicker date;
    TimePicker time;
    EditText text, contact;
    public static String year, month, day, hour, minute;
    public static String contactNo, smsText;

    public static String getContact() {
        return contactNo;
    }
    public static String getText() {
        return smsText;
    }
    public static String getHour() {
        return hour;
    }
    public static String getMinute() {
        return minute;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_scheduling_acivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contact = (EditText) findViewById(R.id.ET_contact);
        text = (EditText) findViewById(R.id.ET_smsText);
//        date = (DatePicker) rootView.findViewById(R.id.datePicker);
        time = (TimePicker) findViewById(R.id.timePicker);
        Button done = (Button) findViewById(R.id.B_done);

        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minuteOfHour) {
                hour = String.valueOf(hourOfDay);
                minute = String.valueOf(minuteOfHour);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set();
            }
        });
    }

    public void set() {
//                year = Integer.toString(date.getYear());
//                month = Integer.toString(date.getMonth());
//                day = Integer.toString(date.getDayOfMonth());
//        this.hour = Integer.toString(time.getHour());
//        this.minute = Integer.toString(time.getMinute());

        this.smsText = text.getText().toString();
        this.contactNo = contact.getText().toString();
        Log.d("time : ", hour+":"+minute);
        Log.d("contact : ", contactNo);
        Log.d("text : ", smsText);
        alarm.setAlarmSMS(this);
    }

}
