package com.example.burcakdemircioglu.wannabeer.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.data.InfoLoader;
import com.example.burcakdemircioglu.wannabeer.ui.adapters.BeerListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class CountryDetailActivityFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private String mCountry ="null";
    private Cursor mCursor;
    private View mRootView;
    public CountryDetailActivityFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity().getIntent().getExtras()!=null)
            mCountry =getActivity().getIntent().getExtras().getString("country");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getActivity().getIntent().getExtras()!=null)
            mCountry =getActivity().getIntent().getExtras().getString("country");
        getLoaderManager().initLoader(0, null,  this);

        mRootView=inflater.inflate(R.layout.fragment_countries_detail, container, false);
        bindViews();
        return mRootView;
    }
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
        bindViews();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return InfoLoader.newInstanceForCountry(getActivity(), mCountry);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (!isAdded()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }

        mCursor = cursor;
        if (mCursor != null && !mCursor.moveToFirst()) {
            Log.e("CountriesDetail", "Error reading item detail cursor");
            mCursor.close();
            mCursor = null;
        }
        if(mCursor==null)
            Log.e("country", "Cursor is null!!");
        bindViews();
    }
    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        if (mCursor != null) {
            mRootView.setAlpha(0);
            mRootView.setVisibility(View.VISIBLE);
            mRootView.animate().alpha(1);
            ListView beerList=(ListView) mRootView.findViewById(R.id.categories_detail_list_view);

            beerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            beerList.setAdapter(beerAdapter);

        } else {
            mRootView.setVisibility(View.GONE);
        }
    }


}
