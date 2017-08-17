package br.com.quaddro.quaddro100h.lab15.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for (int i = 0; i < messages.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }

        String message = String.format(Locale.getDefault(), "%s %s",
                messages[0].getOriginatingAddress(),
                messages[0].getMessageBody());

        Log.i("LAB", message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
