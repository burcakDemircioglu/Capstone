package com.example.burcakdemircioglu.wannabeer.data;

import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.net.Uri;

/**
 * Created by burcakdemircioglu on 05/05/16.
 */
public class LikedBeersLoader extends CursorLoader {
    public static LikedBeersLoader newAllArticlesInstance(Context context) {
        return new LikedBeersLoader(context, BeersContract.LikedItems.buildDirUri());
    }

    public static LikedBeersLoader newInstanceForItemName(Context context, String name) {
        return new LikedBeersLoader(context, BeersContract.LikedItems.buildItemUri(name));
    }

    private LikedBeersLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, BeersContract.LikedItems.DEFAULT_SORT);
    }


    public interface Query {
        String[] PROJECTION = {
                BeersContract.LikedItems._ID,
                BeersContract.LikedItems.BEER_NAME,
        };

        int _ID = 0;
        int BEER_NAME=1;

    }
}