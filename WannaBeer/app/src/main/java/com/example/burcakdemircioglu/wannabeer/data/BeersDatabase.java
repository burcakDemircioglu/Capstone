package com.example.burcakdemircioglu.wannabeer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.burcakdemircioglu.wannabeer.data.BeersProvider.Tables;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class BeersDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "wannabeer.db";
    private static final int DATABASE_VERSION = 1;

    public BeersDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.ITEMS + " ("
                + BeersContact.ItemsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BeersContact.ItemsColumns.SERVER_ID + " TEXT,"
                + BeersContact.ItemsColumns.TITLE + " TEXT NOT NULL,"
                + BeersContact.ItemsColumns.AUTHOR + " TEXT NOT NULL,"
                + BeersContact.ItemsColumns.BODY + " TEXT NOT NULL,"
                + BeersContact.ItemsColumns.THUMB_URL + " TEXT NOT NULL,"
                + BeersContact.ItemsColumns.PHOTO_URL + " TEXT NOT NULL,"
                + BeersContact.ItemsColumns.ASPECT_RATIO + " REAL NOT NULL DEFAULT 1.5,"
                + BeersContact.ItemsColumns.PUBLISHED_DATE + " INTEGER NOT NULL DEFAULT 0"
                + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.ITEMS);
        onCreate(db);
    }
}
