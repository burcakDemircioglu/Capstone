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
                + BeersContract.ItemsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BeersContract.ItemsColumns.SERVER_ID + " TEXT,"
                + BeersContract.ItemsColumns.NAME + " TEXT NOT NULL,"
                + BeersContract.ItemsColumns.PHOTO + " TEXT NOT NULL,"
                + BeersContract.ItemsColumns.KIND + " TEXT NOT NULL,"
                + BeersContract.ItemsColumns.BOTTLE + " TEXT NOT NULL,"
                + BeersContract.ItemsColumns.COUNTRY + " TEXT NOT NULL,"
                + BeersContract.ItemsColumns.ALCOHOL_PERCENTAGE + " REAL NOT NULL DEFAULT 1.5,"
                + BeersContract.ItemsColumns.LOCATION + " TEXT,"
                + BeersContract.ItemsColumns.DESCRIPTION + " TEXT NOT NULL"
                + ")" );

        db.execSQL("CREATE TABLE " + Tables.LIKEDITEMS + " ("
                + BeersContract.FavoritesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BeersContract.FavoritesColumns.BEER_NAME + " TEXT UNIQUE ON CONFLICT REPLACE"
                + ")" );

        db.execSQL("CREATE TABLE " + Tables.DISLIKEDITEMS + " ("
                + BeersContract.FavoritesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BeersContract.FavoritesColumns.BEER_NAME + " TEXT UNIQUE ON CONFLICT REPLACE"
                + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.ITEMS);
        onCreate(db);
    }
}
