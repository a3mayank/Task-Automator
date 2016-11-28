package com.mayankattri.mc_project_2;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.view.Window;
import android.view.WindowManager;

import java.util.concurrent.ExecutionException;

import static com.mayankattri.mc_project_2.WeatherActivity.*;

/**
 * Created by mayank on 22/10/16.
 */
public class Service extends IntentService {

    public Service() {
        super("Service");
    }

    public static final int NOTIFICATION_ID_SMS = 1;
    public static final int NOTIFICATION_ID_WEATHER = 2;
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
        if(intent.getStringExtra("CalendarAlarm") != null && intent.getStringExtra("CalendarAlarm").equals("Calendar")) {
            Calendar(intent);
            System.out.println("CalendarService");
        }
        if(intent.getStringExtra("WeatherAlarm") != null && intent.getStringExtra("WeatherAlarm").equals("Weather")) {
            try {
                Weather(intent);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("WeatherService");
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
        mNotificationManager.notify(NOTIFICATION_ID_SMS, mBuilder.build());

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);

        Receiver.completeWakefulIntent(intent);
    }

    public void Calendar(Intent intent) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        Receiver.completeWakefulIntent(intent);
    }

    public void Weather(Intent intent) throws ExecutionException, InterruptedException {
        SharedPreferences prefs = getSharedPreferences("weather", MODE_PRIVATE);
        String restoredText = prefs.getString("zip", null);
        if (restoredText != null) {
            restoredText = prefs.getString("zip", "110092");
        }

        WeatherActivity.FetchWeatherTask task = new WeatherActivity.FetchWeatherTask();
        String weather = task.execute(restoredText).get();

        while (weather == null) {
        }

        weatherNotification(weather);

//        mNotificationManager = (NotificationManager)
//                this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 1,
//                new Intent(this, MainActivity.class), 0);
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("Weather Report")
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                .bigText("Details"))
//                        .setContentText(weather);
//
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify(NOTIFICATION_ID_WEATHER, mBuilder.build());

        Receiver.completeWakefulIntent(intent);
    }

    public void weatherNotification(String weather) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("notificationWeather", weather);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent dismissIntent = new Intent(this, Service.class);
        dismissIntent.setAction("dismiss");
        PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);

        // Constructs the Builder object.
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Daily Weather Report")
                        .setContentText(weather)
                        .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
        /*
         * Sets the big view "big text" style and supplies the
         * text (the user's reminder message) that will be displayed
         * in the detail area of the expanded notification.
         * These calls are ignored by the support library for
         * pre-4.1 devices.
         */
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(weather));
//                        .addAction (getString(R.string.dismiss), piDismiss);

        // Because clicking the notification launches a new ("special") activity,
        // there's no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // This sets the pending intent that should be fired when the user clicks the
        // notification. Clicking the notification launches a new activity.
        builder.setContentIntent(resultPendingIntent);
        mNotificationManager.notify(NOTIFICATION_ID_WEATHER, builder.build());

    }
}
