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
                + BeersContact.ItemsColumns.NAME + " TEXT NOT NULL,"
                + BeersContact.ItemsColumns.PHOTO + " TEXT NOT NULL,"
                + BeersContact.ItemsColumns.KIND + " TEXT NOT NULL,"
                + BeersContact.ItemsColumns.COUNTRY + " TEXT NOT NULL,"
                + BeersContact.ItemsColumns.ALCOHOL_PERCENTAGE + " REAL NOT NULL DEFAULT 1.5,"
                + BeersContact.ItemsColumns.DESCRIPTION + " TEXT NOT NULL"
                + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.ITEMS);
        onCreate(db);
    }
}
