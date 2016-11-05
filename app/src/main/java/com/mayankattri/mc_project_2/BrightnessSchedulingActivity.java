package com.mayankattri.mc_project_2;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class BrightnessSchedulingActivity extends AppCompatActivity {

    Receiver alarm3 = new Receiver();
    public static Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness_scheduling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setOn();
                } else {
                    setOff();
                }
            }
        });
    }

    public void setOn() {
        //Get the content resolver
        ContentResolver cResolver = getContentResolver();

        //Get the current window
        window = getWindow();

        alarm3.setAlarmBrightness(this);

//        try {
//            //sets manual mode and brightnes 255
//            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);  //this will set the manual mode (set the automatic mode off)
//            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, 1);  //this will set the brightness to maximum (255)
//
//            //refreshes the screen
//            int br = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
//            WindowManager.LayoutParams lp = window.getAttributes();
//            lp.screenBrightness = (float) br / 1;
//            window.setAttributes(lp);
//
//        } catch (Exception e) {}
    }

    public void setOff() {
        //Get the content resolver
        ContentResolver cResolver = getContentResolver();

        //Get the current window
//        window = getActivity().getWindow();
//
//        try {
//            //sets manual mode and brightnes 255
//            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);  //this will set the manual mode (set the automatic mode off)
//            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, 255);  //this will set the brightness to maximum (255)
//
//            //refreshes the screen
//            int br = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
//            WindowManager.LayoutParams lp = window.getAttributes();
//            lp.screenBrightness = (float) br / 255;
//            window.setAttributes(lp);
//
//        } catch (Exception e) {}
    }

}
