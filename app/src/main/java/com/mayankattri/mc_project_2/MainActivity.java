package com.mayankattri.mc_project_2;

import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static int tbValue = 0;

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
        CardView unmute = (CardView) findViewById(R.id.cv6);

        ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton2);

        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbValue = 1;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.MI_SMS) {
            Intent intent = new Intent(this, SmsSchedulingAcivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.MI_ringer) {
            Intent intent = new Intent(this, RingerSchedulingActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.MI_brightness) {
            Intent intent = new Intent(this, BrightnessSchedulingActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.MI_geofencing) {
            Intent intent = new Intent(this, GeofencingActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.MI_weather) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.MI_readSMS) {

//            Uri mSmsQueryUri = Uri.parse("content://sms/inbox");
//            ArrayList<String> messages = new ArrayList<String>();
//
//            Cursor cursor = null;
//            try {
//                cursor = getContentResolver().query(mSmsQueryUri, null, null, null, null);
//                if (cursor == null) {
//                    Log.i("Read SMS", "cursor is null. uri: " + mSmsQueryUri);
//
//                }
//                for (boolean hasData = cursor.moveToFirst(); hasData; hasData = cursor.moveToNext()) {
//                    final String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
//                    messages.add(body);
//                }
//            } catch (Exception e) {
//                Log.e("Read SMS", e.getMessage());
//            } finally {
//                cursor.close();
//            }
//
//            for (String s : messages) {
//                System.out.println(s);
//            }

//            Receiver receiver = new Receiver();
//            receiver.setAlarmUnmute(MainActivity.this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
