package com.example.burcakdemircioglu.wannabeer.data;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.RemoteException;
import android.text.format.Time;
import android.util.Log;

import com.example.burcakdemircioglu.wannabeer.remote.RemoteEndpointUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class UpdaterService extends IntentService {
    private static final String TAG = "UpdaterService";

    public static final String BROADCAST_ACTION_STATE_CHANGE
            = "com.example.burcakdemircioglu.wannabeer.intent.action.STATE_CHANGE";
    public static final String EXTRA_REFRESHING
            = "com.example.burcakdemircioglu.wannabeer.intent.extra.REFRESHING";

    public UpdaterService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Time time = new Time();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null || !ni.isConnected()) {
            Log.w(TAG, "Not online, not refreshing.");
            return;
        }

        sendStickyBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, true));

        // Don't even inspect the intent, we only do one thing, and that's fetch content.
        ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();

        Uri dirUri = BeersContract.Items.buildDirUri();

        // Delete all items
        cpo.add(ContentProviderOperation.newDelete(dirUri).build());

        try {
            JSONArray array = RemoteEndpointUtil.fetchJsonArray();
            if (array == null) {
                throw new JSONException("Invalid parsed item array" );
            }

            for (int i = 0; i < array.length(); i++) {
                ContentValues values = new ContentValues();
                JSONObject object = array.getJSONObject(i);
                values.put(BeersContract.Items.SERVER_ID, object.getString("id" ));
                values.put(BeersContract.Items.NAME, object.getString("name" ));
                values.put(BeersContract.Items.PHOTO, object.getString("photo" ));
                values.put(BeersContract.Items.KIND, object.getString("kind" ));
                values.put(BeersContract.Items.BOTTLE, object.getString("bottle" ));
                values.put(BeersContract.Items.COUNTRY, object.getString("country" ));
                values.put(BeersContract.Items.ALCOHOL_PERCENTAGE, object.getString("alcoholPercentage" ));
                values.put(BeersContract.Items.LOCATION, object.getString("location" ));
                values.put(BeersContract.Items.DESCRIPTION, object.getString("description" ));

                cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
                //Log.v("json",object.getString("description"));
            }

            getContentResolver().applyBatch(BeersContract.CONTENT_AUTHORITY, cpo);

        } catch (JSONException | RemoteException | OperationApplicationException e) {
            Log.e(TAG, "Error updating content.", e);
        }

        sendStickyBroadcast(
                new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, false));
    }
}
