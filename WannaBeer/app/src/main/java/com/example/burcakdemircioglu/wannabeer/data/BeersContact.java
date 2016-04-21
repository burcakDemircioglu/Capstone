package com.example.burcakdemircioglu.wannabeer.data;

import android.net.Uri;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class BeersContact {
    public static final String CONTENT_AUTHORITY = "com.example.burcakdemircioglu.wannabeer";
    public static final Uri BASE_URI = Uri.parse("content://com.example.burcakdemircioglu.wannabeer");

    interface ItemsColumns {
        /** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
        String _ID = "_id";
        /** Type: TEXT */
        String SERVER_ID = "server_id";
        /** Type: TEXT NOT NULL */
        String NAME = "name";
        /** Type: TEXT NOT NULL */
        String PHOTO = "photo";
        /** Type: TEXT NOT NULL */
        String KIND = "kind";
        /** Type: TEXT NOT NULL */
        String COUNTRY = "country";
        /** Type: REAL NOT NULL DEFAULT 1.5 */
        String ALCOHOL_PERCENTAGE = "alcoholPercentage";
        /** Type: TEXT NOT NULL */
        String DESCRIPTION = "description";
    }

    public static class Items implements ItemsColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.burcakdemircioglu.wannabeer.items";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.burcakdemircioglu.wannabeer.items";

        public static final String DEFAULT_SORT = NAME + " ASC";

        /** Matches: /items/ */
        public static Uri buildDirUri() {
            return BASE_URI.buildUpon().appendPath("items").build();
        }

        /** Matches: /items/[_id]/ */
        public static Uri buildItemUri(long _id) {
            return BASE_URI.buildUpon().appendPath("items").appendPath(Long.toString(_id)).build();
        }

        /** Read item ID item detail URI. */
        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }
    }

    //private BeerContract() {
    //}
}
