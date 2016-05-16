package com.example.burcakdemircioglu.wannabeer;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.example.burcakdemircioglu.wannabeer.data.BeersContract;
import com.example.burcakdemircioglu.wannabeer.data.BeersProvider;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by burcakdemircioglu on 16/05/16.
 */
public class WidgetListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<WidgetListItem> listItemList = new ArrayList<WidgetListItem>();
    private Context context = null;
    private int appWidgetId;
    private AppWidgetManager widgetManager;

    public static final int COL_ID = 0;
    public static final int COL_ID2 = 1;
    public static final int COL_NAME = 2;
    public static final int COL_PHOTO = 3;
    public static final int COL_KIND = 4;
    public static final int COL_BOTTLE = 5;
    public static final int COL_COUNTRY = 6;
    public static final int COL_ALCOHOLPERCENTAGE = 7;
    public static final int COL_LOCATION = 8;
    public static final int COL_DESCRIPTION = 9;


    public WidgetListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


        populateListItem();
    }

    private void populateListItem() {

        Uri uri= BeersContract.LikedItems.buildDirUri();
        Log.v("widgetUri", uri.toString());

        BeersProvider provider=new BeersProvider();
        Cursor cursor=provider.queryJOIN(context, "items", "likeditems", "name", "beer_name");



        if(cursor != null && cursor.getCount() > 0) {
           // Log.e("widget", "Liked Beers found: " + cursor.getCount());

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                WidgetListItem listItem = new WidgetListItem();

                listItem.heading = cursor.getString(COL_NAME);
                listItem.content = cursor.getString(COL_COUNTRY)+" - "+ cursor.getString(COL_KIND);
                listItem.photo = cursor.getString(COL_PHOTO);

                listItemList.add(listItem);
                cursor.moveToNext();
            }
        }
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.list_row);
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        final WidgetListItem listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.beer_name, listItem.heading);
        remoteView.setTextViewText(R.id.beer_country, listItem.content);
        Bitmap bitmap=null;

        try {
            bitmap = Glide.
                    with(context).
                    load(listItem.photo).
                    asBitmap().
                    into(-1,-1).
                    get();
        } catch (final ExecutionException e) {

        } catch (final InterruptedException e) {

        }
        if(bitmap!=null) {
            // The full bitmap should be available here
            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
            drawable.setCircular(true);

            remoteView.setImageViewBitmap(R.id.beer_logo, drawableToBitmap(drawable));
         }

        Bundle extras = new Bundle();
        extras.putInt(WidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteView.setOnClickFillInIntent(R.id.beer_name, fillInIntent);

        // Make it possible to distinguish the individual on-click
        // action of a given item
        remoteView.setOnClickFillInIntent(R.layout.list_row, fillInIntent);
        return remoteView;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }
}
