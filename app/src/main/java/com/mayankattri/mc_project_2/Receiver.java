package com.mayankattri.mc_project_2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mayank on 22/10/16.
 */
public class Receiver extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr1;
    private AlarmManager alarmMgrBrightness;
    private AlarmManager alarmMgrSMS;
    private AlarmManager alarmMgrCalendar;
    private AlarmManager alarmMgrWeather;
    private SmsManager alarmMgrUnmute;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent1;
    private PendingIntent alarmIntentBrightness;
    private PendingIntent alarmIntentSMS;
    private PendingIntent alarmIntentCalendar;
    private PendingIntent alarmIntentWeather;
    private PendingIntent alarmIntentUnmute;

    @Override
    public void onReceive(Context context, Intent intent) {
        // BEGIN_INCLUDE(alarm_onreceive)
        /*
         * If your receiver intent includes extras that need to be passed along to the
         * service, use setComponent() to indicate that the service should handle the
         * receiver's intent. For example:
         *
         * ComponentName comp = new ComponentName(context.getPackageName(),
         *      MyService.class.getName());
         *
         * // This intent passed in this call will include the wake lock extra as well as
         * // the receiver intent contents.
         * startWakefulService(context, (intent.setComponent(comp)));
         *
         * In this example, we simply create a new intent to deliver to the service.
         * This intent holds an extra identifying the wake lock.
         */

        System.out.println("in onRecieve()");
        // Start the service, keeping the device awake while it is launching.
        if(intent.getStringExtra("RingerAlarm") != null && intent.getStringExtra("RingerAlarm").equals("Ringer")) {
            Intent serviceRinger = new Intent(context, Service.class);
            serviceRinger.putExtra("RingerAlarm", "Ringer");
            startWakefulService(context, serviceRinger);
        }
        if(intent.getStringExtra("BrightnessAlarm") != null && intent.getStringExtra("BrightnessAlarm").equals("Brightness")) {
            Intent serviceBrightness = new Intent(context, Service.class);
            serviceBrightness.putExtra("BrightnessAlarm", "Brightness");
            startWakefulService(context, serviceBrightness);
        }
        if(intent.getStringExtra("SMSAlarm") != null && intent.getStringExtra("SMSAlarm").equals("SMS")) {
            Intent serviceSMS = new Intent(context, Service.class);
            serviceSMS.putExtra("SMSAlarm", "SMS");
            startWakefulService(context, serviceSMS);
        }
        if(intent.getStringExtra("CalendarAlarm") != null && intent.getStringExtra("CalendarAlarm").equals("Calendar")) {
            Intent serviceCalendar = new Intent(context, Service.class);
            serviceCalendar.putExtra("CalendarAlarm", "Calendar");
            startWakefulService(context, serviceCalendar);
        }
        if(intent.getStringExtra("WeatherAlarm") != null && intent.getStringExtra("WeatherAlarm").equals("Weather")) {
            Intent serviceWeather = new Intent(context, Service.class);
            serviceWeather.putExtra("WeatherAlarm", "Weather");
            startWakefulService(context, serviceWeather);
        }
        // END_INCLUDE(alarm_onreceive)
    }

    public void setAlarmRinger(Context context) {
        alarmMgr1 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Receiver.class);
        intent.putExtra("RingerAlarm", "Ringer");
        alarmIntent1 = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int hour = Integer.parseInt(RingerSchedulingActivity.getHour());
        int minute = Integer.parseInt(RingerSchedulingActivity.getMinute());

//        ArrayList<Integer> hours = new ArrayList<>();
//        ArrayList<Integer> minutes = new ArrayList<>();

        // Set the alarm's trigger time.
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 5);

//        hours.add(hour);hours.add(hour);hours.add(hour);
//        minutes.add(minute+1);minutes.add(minute+2);minutes.add(minute+3);

//        alarmMgr1.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent1);

        alarmMgr1.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent1);

//        for(int i = 0; i < hours.size(); i++) {
//            calendar.set(Calendar.HOUR_OF_DAY, hours.get(i));
//            calendar.set(Calendar.MINUTE, minutes.get(i));
//            calendar.set(Calendar.SECOND, 5);
//
//            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, i+1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
////            alarmMgr1.setInexactRepeating(AlarmManager.RTC_WAKEUP,
////                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
//
//            alarmMgr1.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
//        }
    }

    public void setAlarmBrightness(Context context) {
        alarmMgrBrightness = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Receiver.class);
        intent.putExtra("BrightnessAlarm", "Brightness");
        alarmIntentBrightness = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

//        int hour = Integer.parseInt(RingerSchedulingActivityFragment.getHour());
//        int minute = Integer.parseInt(RingerSchedulingActivityFragment.getMinute());

        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 5);

//        alarmMgrBrightness.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentBrightness);

        alarmMgr1.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntentBrightness);
    }

    public void setAlarmSMS(Context context) {
        System.out.println("in setAlarmSMS");
        alarmMgrSMS = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Receiver.class);
        intent.putExtra("SMSAlarm", "SMS");
        alarmIntentSMS = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int hour = Integer.parseInt(SmsSchedulingAcivity.getHour());
        int minute = Integer.parseInt(SmsSchedulingAcivity.getMinute());

        // Set the alarm's trigger time to 8:30 a.m.
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 5);

        /*
         * If you don't have precise time requirements, use an inexact repeating alarm
         * the minimize the drain on the device battery.
         *
         * The call below specifies the alarm type, the trigger time, the interval at
         * which the alarm is fired, and the alarm's associated PendingIntent.
         * It uses the alarm type RTC_WAKEUP ("Real Time Clock" wake up), which wakes up
         * the device and triggers the alarm according to the time of the device's clock.
         *
         * Alternatively, you can use the alarm type ELAPSED_REALTIME_WAKEUP to trigger
         * an alarm based on how much time has elapsed since the device was booted. This
         * is the preferred choice if your alarm is based on elapsed time--for example, if
         * you simply want your alarm to fire every 60 minutes. You only need to use
         * RTC_WAKEUP if you want your alarm to fire at a particular date/time. Remember
         * that clock-based time may not translate well to other locales, and that your
         * app's behavior could be affected by the user changing the device's time setting.
         *
         * Here are some examples of ELAPSED_REALTIME_WAKEUP:
         *
         * // Wake up the device to fire a one-time alarm in one minute.
         * alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
         *         SystemClock.elapsedRealtime() +
         *         60*1000, alarmIntent);
         *
         * // Wake up the device to fire the alarm in 30 minutes, and every 30 minutes
         * // after that.
         * alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
         *         AlarmManager.INTERVAL_HALF_HOUR,
         *         AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);
         */

        // Set the alarm to fire at approximately 8:30 a.m., according to the device's
        // clock, and to repeat once a day.

//        alarmMgrSMS.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentSMS);

        alarmMgr1.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntentSMS);

        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
//        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
    }

    public void setAlarmCalendar(Context context, ArrayList<Long> unixtime) {
        alarmMgrCalendar = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Receiver.class);
        intent.putExtra("CalendarAlarm", "Calendar");
        alarmIntentCalendar = PendingIntent.getBroadcast(context, 3, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();

        for(int i = 0; i < unixtime.size(); i++) {
            Date date = new Date(unixtime.get(i));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30")); // give a timezone reference for formating (see comment at the bottom
            String formattedDate = sdf.format(date);

            String[] dt = formattedDate.split(" ");
            String[] d = dt[0].split("-");
            String[] t = dt[1].split(":");
            int year = Integer.parseInt(d[0]);
            int _month = Integer.parseInt(d[1]);
            int day = Integer.parseInt(d[2]);
            int hour = Integer.parseInt(t[0]);
            int minute = Integer.parseInt(t[1]);
            int month;
            if(_month==1) month = Calendar.JANUARY;
            else if(_month==2) month = Calendar.FEBRUARY;
            else if(_month==3) month = Calendar.MARCH;
            else if(_month==4) month = Calendar.APRIL;
            else if(_month==5) month = Calendar.MAY;
            else if(_month==6) month = Calendar.JUNE;
            else if(_month==7) month = Calendar.JULY;
            else if(_month==8) month = Calendar.AUGUST;
            else if(_month==9) month = Calendar.SEPTEMBER;
            else if(_month==10) month = Calendar.OCTOBER;
            else if(_month==11) month = Calendar.NOVEMBER;
            else month = Calendar.DECEMBER;
            System.out.println(year+" "+month+" "+day+" "+hour+" "+minute);

            // Set the alarm's trigger time.
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY, 3);
            calendar.set(Calendar.MINUTE, 57);
            calendar.set(Calendar.SECOND, 5);

            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            System.out.println(calendar.getTimeInMillis());

            alarmMgrCalendar.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }

    public void setAlarmWeather(Context context) {
        alarmMgrWeather = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Receiver.class);
        intent.putExtra("WeatherAlarm", "Weather");
        alarmIntentWeather = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

//        int hour = Integer.parseInt(RingerSchedulingActivity.getHour());
//        int minute = Integer.parseInt(RingerSchedulingActivity.getMinute());

        // Set the alarm's trigger time.
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 34);
        calendar.set(Calendar.SECOND, 5);

        alarmMgrWeather.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntentWeather);
    }
}
