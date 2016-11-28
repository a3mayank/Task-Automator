package com.mayankattri.mc_project_2;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    public static int tb2Value = 0, tb3Value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardView geofence1 = (CardView) findViewById(R.id.cv);
        CardView geofence2 = (CardView) findViewById(R.id.cv5);
        CardView weather = (CardView) findViewById(R.id.cv2);
        CardView mute = (CardView) findViewById(R.id.cv3);
        CardView sms = (CardView) findViewById(R.id.cv4);
        CardView unmute = (CardView) findViewById(R.id.cv7);
        CardView calendar = (CardView) findViewById(R.id.cv8);
        CardView email = (CardView) findViewById(R.id.cv9);

        ToggleButton tb2 = (ToggleButton) findViewById(R.id.toggleButton2);
        ToggleButton tb3 = (ToggleButton) findViewById(R.id.toggleButton3);
        ToggleButton tb4 = (ToggleButton) findViewById(R.id.toggleButton4);

        tb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tb2Value = 1;
                } else {
                    tb2Value = 0;
                }
            }
        });

        tb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tb3Value = 1;
                } else {
                    tb3Value = 0;
                }
            }
        });

        geofence1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GeofencingActivity.class);
                startActivity(intent);
            }
        });

        geofence2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GeofencingActivity.class);
                startActivity(intent);
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RingerSchedulingActivity.class);
                startActivity(intent);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SmsSchedulingAcivity.class);
                startActivity(intent);
            }
        });

        unmute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UnmuteBySmsActivity.class);
                startActivity(intent);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmailActivity.class);
                startActivity(intent);
            }
        });
    }

    public void calendar() {
        System.out.println("On Create called here");

        String[] projection =
                new String[]{
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.ACCOUNT_TYPE};

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {


        }
        Cursor calCursor = getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, projection, CalendarContract.Calendars.VISIBLE + " = 1", null, CalendarContract.Calendars._ID + " ASC");
        if (calCursor.moveToFirst()) {
            do {
                long id = calCursor.getLong(0);
                String displayName = calCursor.getString(1);
                String act=calCursor.getString(2);
                String actype=calCursor.getString(3);
                System.out.println(id+" "+displayName+" "+act+" "+actype);
            } while (calCursor.moveToNext());
        }
        System.out.println("\n---------------------\n");
        //**********************Viewing of events start here *******************************************//
        //id 1
        //displayName: kunal14054@iiitd.ac.in
        /** Events table columns */
        String[] EVENTS_COLUMNS = new String[] {
                CalendarContract.Events._ID,
                CalendarContract.Events.CALENDAR_ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.EVENT_TIMEZONE,
                CalendarContract.Events.HAS_ALARM,
                CalendarContract.Events.ALL_DAY,
                CalendarContract.Events.AVAILABILITY,
                CalendarContract.Events.ACCESS_LEVEL,
                CalendarContract.Events.STATUS,
        };
        Cursor cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI,EVENTS_COLUMNS,null,null,"1",null);
        //Converting UTC epoch time to the human readable format date and time

        System.out.println("Current UnixTime : "+System.currentTimeMillis());
        ArrayList<Long> unixtime = new ArrayList<>();

        //Traverses on events in the reverse order
        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            // will give all events
            //Converting UTC epoch time to the human readable format date and time
//            SimpleDateFormat df = new SimpleDateFormat("hh:ss MM/dd/yyyy");
//            df.setTimeZone(TimeZone.getTimeZone("GMT"));
//            String startdate = df.format((Integer.parseInt(cursor.getString(5))));
//            String enddate = df.format((Integer.parseInt(cursor.getString(6))));
            //String startdate = new SimpleDateFormat("MMM dd hh:mm:ss z yyyy", Locale.ENGLISH).format(new Date (Integer.parseInt(cursor.getString(5))));
            //String enddate = new SimpleDateFormat("MMM dd hh:mm:ss z yyyy", Locale.ENGLISH).format(new Date (Integer.parseInt(cursor.getString(6))));
            if(cursor.getString(1).equals("1") || cursor.getString(1).equals("4")) {
                if (Long.parseLong(cursor.getString(5)) >= System.currentTimeMillis()) {
                    System.out.println(cursor.getString(0) + " " + cursor.getString(1) + " " +
                            cursor.getString(2) + " " + cursor.getString(3) + " " +
                            cursor.getString(5) + " " + cursor.getString(6) + " ");
                    unixtime.add(Long.parseLong(cursor.getString(5)));
                }
            }
        }

//        for (int i = 0; i < unixtime.size(); i++) {
//            Date date = new Date(unixtime.get(i));
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
//            sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30")); // give a timezone reference for formating (see comment at the bottom
//            String formattedDate = sdf.format(date);
//            System.out.println(formattedDate);
//            String[] dt = formattedDate.split(" ");
//            String[] d = dt[0].split("-");
//            String[] t = dt[0].split(":");
//            String year = d[0];
//            String month = d[1];
//            String day = d[2];
//            String hour = t[0];
//            String minute = t[1];
//        }

        Receiver alarmCalendar = new Receiver();
        alarmCalendar.setAlarmCalendar(this, unixtime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
