package com.example.burcakdemircioglu.wannabeer.data;

import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.net.Uri;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class InfoLoader extends CursorLoader {
    public static InfoLoader newAllArticlesInstance(Context context) {
        return new InfoLoader(context, BeersContract.Items.buildDirUri());
    }

    public static InfoLoader newInstanceForItemId(Context context, long itemId) {
        return new InfoLoader(context, BeersContract.Items.buildItemUri(itemId));
    }
    public static InfoLoader newInstanceForItemName(Context context, String itemName) {
        return new InfoLoader(context, BeersContract.Items.buildItemUri(itemName));
    }

    public static InfoLoader newInstanceForKind(Context context, String kind) {
        return new InfoLoader(context, BeersContract.Items.buildKindUri(kind));
    }

    private InfoLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, BeersContract.Items.DEFAULT_SORT);
    }


    public interface Query {
        String[] PROJECTION = {
                BeersContract.Items._ID,
                BeersContract.Items.NAME,
                BeersContract.Items.PHOTO,
                BeersContract.Items.KIND,
                BeersContract.Items.BOTTLE,
                BeersContract.Items.COUNTRY,
                BeersContract.Items.ALCOHOL_PERCENTAGE,
                BeersContract.Items.LOCATION,
                BeersContract.Items.DESCRIPTION,
        };

        int _ID = 0;
        int NAME = 1;
        int PHOTO = 2;
        int KIND = 3;
        int BOTTLE=4;
        int COUNTRY = 5;
        int ALCOHOL_PERCENTAGE = 6;
        int LOCATION=7;
        int DESCRIPTION = 8;

    }
}
