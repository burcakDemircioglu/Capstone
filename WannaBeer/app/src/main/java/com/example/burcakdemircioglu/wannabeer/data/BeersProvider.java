package com.example.burcakdemircioglu.wannabeer.data;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class BeersProvider extends ContentProvider

    {
        private SQLiteOpenHelper mOpenHelper;

        interface Tables {
            String ITEMS = "items";
            String LIKEDITEMS="likeditems";
            String DISLIKEDITEMS="dislikeditems";
        }

        private static final int ITEMS = 0;
        private static final int ITEMS__ID = 1;
        private static final int KIND=2;
        private static final int LIKEDITEMS_ID=3;
        private static final int DISLIKEDITEMS_ID=4;
        private static final int LIKEDITEMS=5;
        private static final int DISLIKEDITEMS=6;
        private static final int ITEMS__NAME = 7;
        private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BeersContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "items", ITEMS);
        matcher.addURI(authority, "items/#", ITEMS__ID);
        matcher.addURI(authority, "items/kinds/*", KIND);
        matcher.addURI(authority, "likeditems/*", LIKEDITEMS_ID);
        matcher.addURI(authority, "dislikeditems/*", DISLIKEDITEMS_ID);
        matcher.addURI(authority, "likeditems", LIKEDITEMS);
        matcher.addURI(authority, "dislikeditems", DISLIKEDITEMS);
        matcher.addURI(authority, "items/*", ITEMS__NAME);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new BeersDatabase(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return BeersContract.Items.CONTENT_TYPE;
            case ITEMS__ID:
                return BeersContract.Items.CONTENT_ITEM_TYPE;
            case ITEMS__NAME:
                return BeersContract.Items.CONTENT_ITEM_TYPE;
            case KIND:
                return BeersContract.Items.CONTENT_ITEM_TYPE;
            case LIKEDITEMS:
                return BeersContract.LikedItems.CONTENT_TYPE;
            case DISLIKEDITEMS:
                return BeersContract.DislikedItems.CONTENT_TYPE;
            case LIKEDITEMS_ID:
                return BeersContract.LikedItems.CONTENT_ITEM_TYPE;
            case DISLIKEDITEMS_ID:
                return BeersContract.DislikedItems.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        Cursor cursor = builder.where(selection, selectionArgs).query(db, projection, sortOrder);
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }
    public Cursor queryJOIN(Activity activity, String table1, String table2, String table1Column, String table2Column) {
        if(mOpenHelper==null) mOpenHelper = new BeersDatabase(activity);

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final String MY_QUERY = "SELECT * FROM "+table1+" INNER JOIN "+table2+" ON "+table1+"."+table1Column+"="+table2+"."+table2Column;

        Cursor cursor=db.rawQuery(MY_QUERY,new String[]{});
        if (cursor != null) {
            Log.e("queryJOIN", "cursor is null");
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS: {
                final long _id = db.insertOrThrow(Tables.ITEMS, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return BeersContract.Items.buildItemUri(_id);
            }
            case LIKEDITEMS:{
                try {
                    final long _id = db.insertOrThrow(Tables.LIKEDITEMS, null, values);
                    getContext().getContentResolver().notifyChange(uri, null);
                    return BeersContract.LikedItems.buildItemUri(_id);
                }catch (SQLiteConstraintException e){
                    return BeersContract.LikedItems.buildItemUri(0);

                }

            }
            case DISLIKEDITEMS:{
                try {
                    final long _id = db.insertOrThrow(Tables.DISLIKEDITEMS, null, values);
                    getContext().getContentResolver().notifyChange(uri, null);
                    return BeersContract.DislikedItems.buildItemUri(_id);
                }catch (SQLiteConstraintException e){
                    return BeersContract.DislikedItems.buildItemUri(0);

                }

            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).update(db, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).delete(db);
    }


    private SelectionBuilder buildSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        return buildSelection(uri, match, builder);
    }

    private SelectionBuilder buildSelection(Uri uri, int match, SelectionBuilder builder) {
        final List<String> paths = uri.getPathSegments();

        switch (match) {
            case ITEMS: {
                return builder.table(Tables.ITEMS);
            }
            case ITEMS__ID: {
                final String _id = paths.get(1);
                return builder.table(Tables.ITEMS).where(BeersContract.Items._ID + "=?", _id);
            }

            case ITEMS__NAME: {
                final String name = paths.get(1);
                return builder.table(Tables.ITEMS).where(BeersContract.Items.NAME + "=?", name);
            }

            case KIND:{
                final String kind=paths.get(2);
                return builder.table(Tables.ITEMS).where(BeersContract.Items.KIND+ "=?",kind);
            }
            case LIKEDITEMS: {
                return builder.tables(Tables.LIKEDITEMS, Tables.ITEMS);
            }
            case DISLIKEDITEMS: {
                return builder.tables(Tables.DISLIKEDITEMS, Tables.ITEMS);
            }
            case LIKEDITEMS_ID: {
                final String name=paths.get(1);
                return builder.table(Tables.LIKEDITEMS).where(BeersContract.LikedItems.BEER_NAME + "=?", name);
            }
            case DISLIKEDITEMS_ID: {
                final String name=paths.get(1);
                return builder.table(Tables.DISLIKEDITEMS).where(BeersContract.DislikedItems.BEER_NAME + "=?", name);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    /**
     * Apply the given set of {@link ContentProviderOperation}, executing inside
     * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
     * any single one fails.
     */
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }
}
