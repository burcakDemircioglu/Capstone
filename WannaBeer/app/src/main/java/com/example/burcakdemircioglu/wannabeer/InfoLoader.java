package com.example.burcakdemircioglu.wannabeer;

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
                BeersContact.Items.TITLE,
                BeersContact.Items.PUBLISHED_DATE,
                BeersContact.Items.AUTHOR,
                BeersContact.Items.THUMB_URL,
                BeersContact.Items.PHOTO_URL,
                BeersContact.Items.ASPECT_RATIO,
                BeersContact.Items.BODY,
        };

        int _ID = 0;
        int TITLE = 1;
        int PUBLISHED_DATE = 2;
        int AUTHOR = 3;
        int THUMB_URL = 4;
        int PHOTO_URL = 5;
        int ASPECT_RATIO = 6;
        int BODY = 7;
    }
}
