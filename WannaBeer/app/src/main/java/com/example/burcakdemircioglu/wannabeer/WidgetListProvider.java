package com.example.burcakdemircioglu.wannabeer;

import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.burcakdemircioglu.wannabeer.data.BeersContract;

import java.util.ArrayList;

/**
 * Created by burcakdemircioglu on 16/05/16.
 */
public class WidgetListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<WidgetListItem> listItemList = new ArrayList<WidgetListItem>();
    private Context context = null;
    private int appWidgetId;
    public static final int COL_ID = 0;
    public static final int COL_NAME = 1;


    private final ContentResolver mContentResolver;

    public WidgetListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        mContentResolver = context.getContentResolver();
        populateListItem();
    }

    private void populateListItem() {

        Uri uri= BeersContract.LikedItems.buildDirUri();
        Log.v("widgetUri", uri.toString());


        Cursor cursor = mContentResolver.query(
                BeersContract.LikedItems.buildDirUri(),
                new String[] { BeersContract.LikedItems._ID, BeersContract.LikedItems.BEER_NAME},
                null,
                null,
                null
        );

        if(cursor != null && cursor.getCount() > 0) {
           // Log.e("widget", "Liked Beers found: " + cursor.getCount());

            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                WidgetListItem listItem = new WidgetListItem();

                listItem.heading = cursor.getString(COL_NAME);
                listItem.content = cursor.getString(COL_ID);

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

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_row);
        WidgetListItem listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.heading, listItem.heading);
        remoteView.setTextViewText(R.id.content, listItem.content);

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
