package com.example.burcakdemircioglu.wannabeer.data;

import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.net.Uri;

/**
 * Created by burcakdemircioglu on 05/05/16.
 */
public class DislikedBeersLoader extends CursorLoader {
    public static DislikedBeersLoader newAllArticlesInstance(Context context) {
        return new DislikedBeersLoader(context, BeersContract.DislikedItems.buildDirUri());
    }

    public static DislikedBeersLoader newInstanceForItemName(Context context, String name) {
        return new DislikedBeersLoader(context, BeersContract.DislikedItems.buildItemUri(name));
    }


    private DislikedBeersLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, BeersContract.DislikedItems.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                BeersContract.DislikedItems._ID,
                BeersContract.DislikedItems.BEER_NAME,
        };

        int _ID = 0;
        int BEER_NAME=1;
    }
}