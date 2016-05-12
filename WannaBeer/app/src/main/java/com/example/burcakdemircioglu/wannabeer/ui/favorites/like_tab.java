package com.example.burcakdemircioglu.wannabeer.ui.favorites;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.data.BeersProvider;
import com.example.burcakdemircioglu.wannabeer.ui.adapters.BeerLikedListAdapter;

public class like_tab extends Fragment {
    private View mRootView;
    public like_tab() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        LikedLoader loader=new LikedLoader();
        loader.execute("");
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView=inflater.inflate(R.layout.fragment_categories_detail, container, false);
        return mRootView;
    }

    private void bindViews(final Cursor mCursor) {
        if (mRootView == null) {
            return;
        }

        if (mCursor != null) {
            mRootView.setAlpha(0);
            mRootView.setVisibility(View.VISIBLE);
            mRootView.animate().alpha(1);
            ListView beerList=(ListView) mRootView.findViewById(R.id.categories_detail_list_view);
/*
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
            */
            BeerLikedListAdapter beerAdapter=new BeerLikedListAdapter(getActivity(), mCursor);
            beerList.setAdapter(beerAdapter);

        } else {
            mRootView.setVisibility(View.GONE);
        }
    }

    private class LikedLoader extends AsyncTask<String, Void, String> {
        private Cursor mCursor;
        @Override
        protected String doInBackground(String... params) {
            BeersProvider provider=new BeersProvider();
            mCursor=provider.queryJOIN(getActivity(), "items", "likeditems", "name", "beer_name");
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            bindViews(mCursor);
            //TextView txt = (TextView) findViewById(R.id.output);
            //txt.setText("Executed"); // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}