package com.example.burcakdemircioglu.wannabeer.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.data.InfoLoader;
import com.example.burcakdemircioglu.wannabeer.ui.MainActivity;

import java.util.Random;

/**
 * Created by burcakdemircioglu on 14/05/16.
 */
public class NotifyService extends Service implements Loader.OnLoadCompleteListener<Cursor> {
    NotificationManager manager;
    Notification myNotication;
    InfoLoader mCursorLoader;
    Notification.Builder builder;
    private static final String TAG = "MyService";

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        // Stop the cursor loader
        if (mCursorLoader != null) {
            mCursorLoader.unregisterListener(this);
            mCursorLoader.cancelLoad();
            mCursorLoader.stopLoading();
        }
    }
    @Override
    public void onLoadComplete(Loader<Cursor> loader, Cursor cursor) {
        // Bind data to UI, etc
        Log.e("notificationLoad", "complete");
        cursor.moveToFirst();

        int cursorUpperLimit=cursor.getCount();
        Random r = new Random();
        int randomPosition = r.nextInt(cursorUpperLimit);
        cursor.moveToPosition(randomPosition);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        builder = new Notification.Builder(getApplicationContext());

        builder.setAutoCancel(false);
        //builder.setTicker("this is ticker text");
        builder.setContentTitle("Have you tried?");
        builder.setContentText(cursor.getString(InfoLoader.Query.NAME)+" - "+cursor.getString(InfoLoader.Query.KIND));
        builder.setSmallIcon(R.drawable.like_button);
        /*
        try {
            InputStream in = new URL(cursor.getString(InfoLoader.Query.PHOTO)).openStream();
            Bitmap bmp = BitmapFactory.decodeStream(in);
            builder.setLargeIcon(bmp);

        }
       catch (IOException e){

       }
*/
        builder.setContentIntent(pendingIntent);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
    }
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        mCursorLoader = InfoLoader.newAllArticlesInstance(this);
        mCursorLoader.registerListener(0, this);
        mCursorLoader.startLoading();

        //Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart");
        //Note: You can start a new thread and use it for long background processing from here.







    }


}
