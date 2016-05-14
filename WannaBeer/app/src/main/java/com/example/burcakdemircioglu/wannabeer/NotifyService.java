package com.example.burcakdemircioglu.wannabeer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.burcakdemircioglu.wannabeer.ui.MainActivity;

/**
 * Created by burcakdemircioglu on 14/05/16.
 */
public class NotifyService extends Service {
    NotificationManager manager;
    Notification myNotication;
    private static final String TAG = "MyService";

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart");
        //Note: You can start a new thread and use it for long background processing from here.
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        builder.setAutoCancel(false);
        //builder.setTicker("this is ticker text");
        builder.setContentTitle("Have you tried?");
        builder.setContentText("Beer Name"+" - "+"Category");
        builder.setSmallIcon(R.drawable.like_button);
        builder.setContentIntent(pendingIntent);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
    }
}
