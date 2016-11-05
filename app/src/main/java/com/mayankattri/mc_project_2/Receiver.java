package com.mayankattri.mc_project_2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Calendar;

/**
 * Created by mayank on 22/10/16.
 */
public class Receiver extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr1;
    private AlarmManager alarmMgrBrightness;
    private AlarmManager alarmMgrSMS;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent1;
    private PendingIntent alarmIntentBrightness;
    private PendingIntent alarmIntentSMS;

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
        // END_INCLUDE(alarm_onreceive)
    }

    public void setAlarmRinger(Context context) {
        alarmMgr1 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Receiver.class);
        intent.putExtra("RingerAlarm", "Ringer");
        alarmIntent1 = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int hour = Integer.parseInt(RingerSchedulingActivity.getHour());
        int minute = Integer.parseInt(RingerSchedulingActivity.getMinute());

        // Set the alarm's trigger time to 8:30 a.m.
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        alarmMgr1.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent1);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute+1);

        alarmMgr1.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
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

        alarmMgrBrightness.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentBrightness);
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
        alarmMgrSMS.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentSMS);

        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
//        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
    }
}
