package com.example.burcakdemircioglu.wannabeer.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.data.InfoLoader;
import com.example.burcakdemircioglu.wannabeer.ui.adapters.BeerListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    View mRootView;
    ListView mBeerList;
    EditText mEditText;
    String mInputBeerName;
    LoaderManager.LoaderCallbacks<Cursor> callback=this;
    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.fragment_search, container, false);
        mEditText=(EditText) mRootView.findViewById(R.id.search_beer_name);

        mEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                mInputBeerName = mEditText.getText().toString();
                if (mInputBeerName.length()!=0) {
                    Loader<Object> loader = getLoaderManager().getLoader(0);

                    if (loader != null && !loader.isReset()) {
                        getLoaderManager().restartLoader(0, null, callback);
                    } else {
                        getLoaderManager().initLoader(0, null, callback);
                    }
                }
                else
                    mBeerList.setAdapter(null);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        return mRootView;
    }
    private void bindViews(final Cursor mCursor) {
        if (mRootView == null) {
            return;
        }

        if (mCursor != null) {
            Log.e("SearchLoader","cursor is not null!");

            mRootView.setAlpha(0);
            mRootView.setVisibility(View.VISIBLE);
            mRootView.animate().alpha(1);
            mBeerList=(ListView) mRootView.findViewById(R.id.listView1);

            mBeerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    mCursor.moveToPosition(position);
                    Intent intent=new Intent(getActivity(), BeerDetailActivityWithoutPager.class);
                    intent.putExtra("ItemId", mCursor.getLong(InfoLoader.Query._ID));
                    //BeersContract.Items.buildItemUri(mCursor.getInt(InfoLoader.Query._ID)));
                    startActivity(intent);
                }
            });

            BeerListAdapter beerAdapter=new BeerListAdapter(getActivity(), mCursor);
            mBeerList.setAdapter(beerAdapter);

        } else {
            Log.e("SearchLoader","cursor is null!");

            mRootView.setVisibility(View.GONE);
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.e("SearchLoader","creating");
        return InfoLoader.newInstanceForItemName(getActivity(),mInputBeerName);
    }



    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if(mBeerList!=null)
            mBeerList.setAdapter(null);
        bindViews(cursor);

        if(cursor==null)
            Log.e("onLoadFinished","cursor is null");
        else {
            Log.e("onLoadFinished", "cursor is not null"+String.valueOf(cursor.getCount()));

        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}
