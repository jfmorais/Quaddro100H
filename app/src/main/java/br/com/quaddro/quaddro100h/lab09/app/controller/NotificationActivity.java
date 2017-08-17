package br.com.quaddro.quaddro100h.lab09.app.controller;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import br.com.quaddro.quaddro100h.R;
import br.com.quaddro.quaddro100h.lab01.app.controller.QuaddroActivity;
import br.com.quaddro.quaddro100h.lab01.app.controller.SplashActivity;

public class NotificationActivity extends QuaddroActivity {

    NotificationManagerCompat nm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notification_view);

        nm = NotificationManagerCompat.from(this);
    }

    public void notificarSimples(View v) {
        Notification n;
        PendingIntent pi;
        Intent i;

        i = new Intent(this, SplashActivity.class);
        pi = PendingIntent.getActivity(this, 0, i, 0);
        n = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Titulo Simples")
                .setContentText("texto Tipo Uber simples")
                .setVibrate(new long[]{ 800, 400, 800, 400 })
                .setAutoCancel(true)
                .setContentIntent(pi)
                .build();

        nm.notify(100, n);
    }

    public void notificarCompleta(View v) {
        Notification n;
        PendingIntent piContent, piDelete, piAction;
        Intent iContent, iDelete, iAction;
        Bitmap largeIcon = BitmapFactory.decodeResource(
                getResources(), R.drawable.wilson);

        iContent = new Intent(this, SplashActivity.class);
        iDelete = new Intent("quaddro.intent.action.DELETE");
        iAction = new Intent("quaddro.intent.action.CUSTOM");
        piContent = PendingIntent.getActivity(this, 0, iContent, 0);
        piDelete = PendingIntent.getBroadcast(this, 0, iDelete, 0);
        piAction = PendingIntent.getBroadcast(this, 0, iAction, 0);
        n = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon)
                .setContentTitle("Titulo Completa")
                .setContentText("texto Tipo Uber Completa")
                .setAutoCancel(true)
                .setContentIntent(piContent)
                .setDeleteIntent(piDelete)
                .addAction(android.R.drawable.ic_lock_idle_alarm,
                        "Minha ação",
                        piAction)
                .setLights(Color.GRAY, 1000, 5000)
                .setWhen(System.currentTimeMillis())
                .setTicker("Tipo uber Ticker")
                .setColor(Color.RED)
                .setSound(Uri.parse(
                        "android.resource://" + getPackageName() + "/" + R.raw.msn_001))
                .build();

        nm.notify(101, n);
    }

    public void notificarTipoUber(View v) {
        NotificationCompat.InboxStyle inbox;
        Notification n;
        PendingIntent pi;
        Intent i;

        inbox = new NotificationCompat.InboxStyle();
        inbox.setBigContentTitle("Tipo Uber");
        for (int ii = 0; ii < 15; ii++) {
            inbox.addLine("texto " + ii);
        }
        inbox.setSummaryText("Resumindo...");

        i = new Intent(this, SplashActivity.class);
        pi = PendingIntent.getActivity(this, 0, i, 0);
        n = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Titulo com estilo")
                .setContentText("texto Tipo Uber estilo")
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setNumber(15)
                .setStyle(inbox)
                .build();

        nm.notify(102, n);
    }

    private BroadcastReceiver rDelete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Chamado pelo delete");
            Toast.makeText(getApplicationContext(),
                    "Delete OK!",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };

    private BroadcastReceiver rAction = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Chamado pela action");
            Toast.makeText(getApplicationContext(),
                    "Action OK!",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(rDelete, new IntentFilter("quaddro.intent.action.DELETE"));
        registerReceiver(rAction, new IntentFilter("quaddro.intent.action.CUSTOM"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(rDelete);
        unregisterReceiver(rAction);
    }
}
