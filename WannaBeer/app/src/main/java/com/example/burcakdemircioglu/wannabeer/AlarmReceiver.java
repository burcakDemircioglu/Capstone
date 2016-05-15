package com.example.burcakdemircioglu.wannabeer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by burcakdemircioglu on 14/05/16.
 */
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, final Intent intent) {
        context.startService(new Intent(context, NotifyService.class));
    }


    /**
     * Schedule the next update
     *
     * @param context
     *            the current application context
     */
    public static void scheduleServiceUpdates(final Context context) {
        // create intent for our alarm receiver (or update it if it exists)
        final Intent intent = new Intent(context, AlarmReceiver.class);
        final PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // compute first call time 1 minute from now
        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.MINUTE, 1);

        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND, 00);

        long trigger = calendar.getTimeInMillis();

        // set delay between each call : 24 Hours
        long delay = 24 * 60 * 60 * 1000;

        // Set alarm
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, trigger, delay, pending);
        // you can use RTC_WAKEUP instead of RTC to wake up the device
    }
}