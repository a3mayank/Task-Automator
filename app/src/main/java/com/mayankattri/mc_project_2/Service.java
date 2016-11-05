package com.mayankattri.mc_project_2;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by mayank on 22/10/16.
 */
public class Service extends IntentService {

    public Service() {
        super("Service");
    }

    public static final String TAG = "Scheduling Demo";
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;
    // The string the app searches for in the Google home page content. If the app finds
    // the string, it indicates the presence of a doodle.
    public static final String SEARCH_STRING = "doodle";
    // The Google home page URL from which the app fetches content.
    // You can find a list of other Google domains with possible doodles here:
    // http://en.wikipedia.org/wiki/List_of_Google_domains
    public static final String URL = "http://www.google.com";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.getStringExtra("RingerAlarm") != null && intent.getStringExtra("RingerAlarm").equals("Ringer")) {
            ringer(intent);
            System.out.println("RingerService");
        }
        if(intent.getStringExtra("BrightnessAlarm") != null && intent.getStringExtra("BrightnessAlarm").equals("Brightness")) {
            brightness(intent);
            System.out.println("BrightnessService");
        }
        if(intent.getStringExtra("SMSAlarm") != null && intent.getStringExtra("SMSAlarm").equals("SMS")) {
            SMS(intent);
            System.out.println("SMSService");
        }
    }

    public void ringer(Intent intent) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        Receiver.completeWakefulIntent(intent);
    }

    public void brightness(Intent intent) {
        //Get the content resolver
        ContentResolver cResolver = getContentResolver();

        //Get the current window
        Window window = BrightnessSchedulingActivity.window;

        try {
            //sets manual mode and brightnes 255
            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);  //this will set the manual mode (set the automatic mode off)
            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, 1);  //this will set the brightness to maximum (255)

            //refreshes the screen
            int br = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.screenBrightness = (float) br / 1;
            window.setAttributes(lp);

        } catch (Exception e) {}

        Receiver.completeWakefulIntent(intent);
    }

    public void SMS(Intent intent) {
        String number = SmsSchedulingAcivity.getContact();
        String message = SmsSchedulingAcivity.getText();
        String msg = "SMS sent to " + number;

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("SMS Sent")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);

        Receiver.completeWakefulIntent(intent);
    }
}
