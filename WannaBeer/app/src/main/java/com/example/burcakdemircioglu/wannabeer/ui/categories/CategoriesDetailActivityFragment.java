package com.example.burcakdemircioglu.wannabeer.ui.categories;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.data.InfoLoader;
import com.example.burcakdemircioglu.wannabeer.ui.BeerDetailActivityWithoutPager;
import com.example.burcakdemircioglu.wannabeer.ui.util.ImageLoaderHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoriesDetailActivityFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private String mKind="null";
    private Cursor mCursor;
    private View mRootView;
    public CategoriesDetailActivityFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity().getIntent().getExtras()!=null)
            mKind=getActivity().getIntent().getExtras().getString("kind");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getActivity().getIntent().getExtras()!=null)
            mKind=getActivity().getIntent().getExtras().getString("kind");
        getLoaderManager().initLoader(0, null,  this);

        mRootView=inflater.inflate(R.layout.fragment_categories_detail, container, false);
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
        return InfoLoader.newInstanceForKind(getActivity(), mKind);
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
            Log.e("CategoriesDetail", "Error reading item detail cursor");
            mCursor.close();
            mCursor = null;
        }
        if(mCursor==null)
            Log.e("kind", "Cursor is null!!");
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
    public static class ViewHolder{
        public TextView nameView;
        public TextView countryView;
        public ImageView imageView;
    }
    public class BeerListAdapter extends CursorAdapter {
        public BeerListAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View root=LayoutInflater.from(context).inflate(R.layout.beer_list_item, parent, false);
            return root;
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            final ViewHolder holder=new ViewHolder();
            holder.nameView = (TextView) view.findViewById(R.id.beer_name);
            holder.countryView = (TextView) view.findViewById(R.id.beer_country);
            holder.imageView=(ImageView) view.findViewById(R.id.beer_logo);
            ImageLoaderHelper.getInstance(getActivity()).getImageLoader()
                    .get(cursor.getString(InfoLoader.Query.PHOTO), new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                            Bitmap bitmap = imageContainer.getBitmap();
                            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
                            drawable.setCircular(true);
                            if (bitmap != null) {
                                holder.imageView.setImageDrawable(drawable);
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
            // Extract properties from cursor
            String name = cursor.getString(InfoLoader.Query.NAME);
            String country = cursor.getString(InfoLoader.Query.COUNTRY);
            // Populate fields with extracted properties
            holder.nameView.setText(name);
            holder.countryView.setText(String.valueOf(country));
        }

    }
}
