package com.example.burcakdemircioglu.wannabeer.data;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class InfoLoader extends CursorLoader {
    public static InfoLoader newAllArticlesInstance(Context context) {
        return new InfoLoader(context, BeersContact.Items.buildDirUri());
    }

    public static InfoLoader newInstanceForItemId(Context context, long itemId) {
        return new InfoLoader(context, BeersContact.Items.buildItemUri(itemId));
    }

    private InfoLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, BeersContact.Items.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                BeersContact.Items._ID,
                BeersContact.Items.NAME,
                BeersContact.Items.PHOTO,
                BeersContact.Items.KIND,
                BeersContact.Items.COUNTRY,
                BeersContact.Items.ALCOHOL_PERCENTAGE,
                BeersContact.Items.DESCRIPTION,
        };

        int _ID = 0;
        int NAME = 1;
        int PHOTO = 2;
        int KIND = 3;
        int COUNTRY = 4;
        int ALCOHOL_PERCENTAGE = 5;
        int DESCRIPTION = 6;
    }
}
