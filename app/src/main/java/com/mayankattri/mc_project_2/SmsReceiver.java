package com.mayankattri.mc_project_2;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by mayank on 6/11/16.
 */
public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_EXTRA_NAME = "pdus";

    public void onReceive(Context context, Intent intent) {
        // Get SMS map from Intent
        Bundle extras = intent.getExtras();

        String messages = "";

        if (extras != null) {
            // Get received SMS array
            Object[] smsExtra = (Object[]) extras.get(SMS_EXTRA_NAME);

            // Get ContentResolver object for pushing encrypted SMS to incoming folder
            ContentResolver contentResolver = context.getContentResolver();

            for (int i = 0; i < smsExtra.length; ++i) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);

                String body = sms.getMessageBody().toString();
                String address = sms.getOriginatingAddress();

                messages += "SMS from " + address + " :\n";
                messages += body + "\n";

                // Here you can add any your code to work with incoming SMS
                if (body.contains("FIND MY PHONE") && MainActivity.tbValue == 1) {
                    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    Receiver.completeWakefulIntent(intent);
                }
            }

            // Display SMS message
            Toast.makeText(context, messages, Toast.LENGTH_SHORT).show();
        }

        // WARNING!!!
        // If you uncomment next line then received SMS will not be put to incoming.
        // Be careful!
        // this.abortBroadcast();
    }
}
