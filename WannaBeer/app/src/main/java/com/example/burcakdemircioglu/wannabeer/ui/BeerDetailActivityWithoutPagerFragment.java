package com.example.burcakdemircioglu.wannabeer.ui;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.graphics.Palette;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.data.BeersContract;
import com.example.burcakdemircioglu.wannabeer.data.DislikedBeersLoader;
import com.example.burcakdemircioglu.wannabeer.data.InfoLoader;
import com.example.burcakdemircioglu.wannabeer.data.LikedBeersLoader;
import com.example.burcakdemircioglu.wannabeer.ui.util.DrawInsetsFrameLayout;
import com.example.burcakdemircioglu.wannabeer.ui.util.ImageLoaderHelper;
import com.example.burcakdemircioglu.wannabeer.ui.util.ObservableScrollView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class BeerDetailActivityWithoutPagerFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private static final String TAG = "BeerDetailFragment";

    private Cursor mCursor;
    private Cursor mCursorLiked;
    private Cursor mCursorDisliked;

    private long mItemId;
    private String mItemName;
    private View mRootView;
    private int mTopInset;
    private View mPhotoContainerView;
    private static final float PARALLAX_FACTOR = 1.25f;


    private ImageView mFlagView;
    private ImageView mPhotoView;
    private ImageButton mLikeButton;
    private ImageButton mDislikeButton;

    private int likeDislikeInteraction;
    private static final int LIKE=0;
    private static final int DISLIKE=1;
    private static final int NO_INTERACTION =2;

    private int mMutedColor = 0xFF333333;
    private ObservableScrollView mScrollView;
    private DrawInsetsFrameLayout mDrawInsetsFrameLayout;
    private ColorDrawable mStatusBarColorDrawable;
    private int mScrollY;
    private boolean mIsCard = false;
    private int mStatusBarFullOpacityBottom;
    public BeerDetailActivityWithoutPagerFragment() {
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // In support library r8, calling initLoader for a fragment in a FragmentPagerAdapter in
        // the fragment's onCreate may cause the same LoaderManager to be dealt to multiple
        // fragments because their mIndex is -1 (haven't been added to the activity yet). Thus,
        // we do this in onActivityCreated.
        getLoaderManager().initLoader(0, null, this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getActivity().getIntent().getExtras()!=null) {
            mItemId = getActivity().getIntent().getExtras().getLong("ItemId");
            Log.e("mItemId", String.valueOf(mItemId));
        }
        else
            Log.e("mItemId", "null");

        mIsCard = getResources().getBoolean(R.bool.detail_is_card);
        mStatusBarFullOpacityBottom = getResources().getDimensionPixelSize(
                R.dimen.detail_card_top_margin);
        setHasOptionsMenu(true);

    }
    public BeerDetailActivityWithoutPager getActivityCast() {
        return (BeerDetailActivityWithoutPager) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.fragment_beer_detail_activity_without_pager, container, false);
        mDrawInsetsFrameLayout = (DrawInsetsFrameLayout)
                mRootView.findViewById(R.id.draw_insets_frame_layout);
        mDrawInsetsFrameLayout.setOnInsetsCallback(new DrawInsetsFrameLayout.OnInsetsCallback() {
            @Override
            public void onInsetsChanged(Rect insets) {
                mTopInset = insets.top;
            }
        });

        mScrollView = (ObservableScrollView) mRootView.findViewById(R.id.scrollview);

        mScrollView.setCallbacks(new ObservableScrollView.Callbacks() {
            @Override
            public void onScrollChanged() {
                mScrollY = mScrollView.getScrollY();
                //getActivityCast().onUpButtonFloorChanged(mItemId, BeerDetailFragment.this);
                mPhotoContainerView.setTranslationY((int) (mScrollY - mScrollY / PARALLAX_FACTOR));
                updateStatusBar();
            }
        });

        mPhotoView = (ImageView) mRootView.findViewById(R.id.photo);
       // mPhotoView.setTransitionName(Integer.toString(mPosition));
        //Log.e("onClickTransNameDetail", Integer.toString(mPosition));

        mLikeButton= (ImageButton)mRootView.findViewById(R.id.like_button);
        mDislikeButton=(ImageButton)mRootView.findViewById(R.id.dislike_button);

        mPhotoContainerView = mRootView.findViewById(R.id.photo_container);

        mStatusBarColorDrawable = new ColorDrawable(0);

        mRootView.findViewById(R.id.share_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText("Did you tried the beer "+ mCursor.getString(InfoLoader.Query.NAME) + "? Found it on #WannaBeer =)")
                        .getIntent(), getString(R.string.action_share)));
            }
        });

        bindViews();
        updateStatusBar();
        return mRootView;
    }

    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        TextView titleView = (TextView) mRootView.findViewById(R.id.article_title);
        TextView bylineView = (TextView) mRootView.findViewById(R.id.article_byline);
        TextView locationView=(TextView) mRootView.findViewById(R.id.article_location);
        TextView countryView=(TextView)mRootView.findViewById(R.id.article_country);
        bylineView.setMovementMethod(new LinkMovementMethod());
        TextView bodyView = (TextView) mRootView.findViewById(R.id.article_body);
        bodyView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Light.ttf"));
        locationView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Light.ttf"));
        titleView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Light.ttf"));
        bylineView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Light.ttf"));
        countryView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Roboto-Light.ttf"));
        mFlagView=(ImageView)mRootView.findViewById(R.id.article_country_flag);

        if (mCursor != null) {
            mRootView.setAlpha(0);
            mRootView.setVisibility(View.VISIBLE);
            mRootView.animate().alpha(1);
            titleView.setText(mCursor.getString(InfoLoader.Query.NAME));
            countryView.setText(mCursor.getString(InfoLoader.Query.COUNTRY));
            String byLine=mCursor.getString(InfoLoader.Query.KIND)+" - "+mCursor.getString(InfoLoader.Query.ALCOHOL_PERCENTAGE)+"%";
            bylineView.setText(byLine);

            bodyView.setText(mCursor.getString(InfoLoader.Query.DESCRIPTION));
            String location= "Location: "+mCursor.getString(InfoLoader.Query.LOCATION);
            locationView.setText(location);
            if (likeDislikeInteraction==LIKE){
                mLikeButton.setImageDrawable(getResources().getDrawable(R.drawable.like_button_filter));
                mDislikeButton.setImageDrawable(getResources().getDrawable(R.drawable.dislike_button));

            }
            else if(likeDislikeInteraction==DISLIKE){
                mLikeButton.setImageDrawable(getResources().getDrawable(R.drawable.like_button));
                mDislikeButton.setImageDrawable(getResources().getDrawable(R.drawable.dislike_button_filter));
            }
            else{
                mLikeButton.setImageDrawable(getResources().getDrawable(R.drawable.like_button));
                mDislikeButton.setImageDrawable(getResources().getDrawable(R.drawable.dislike_button));
            }

            mLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();
                    Uri dirUri = BeersContract.LikedItems.buildDirUri();
                    ContentValues values = new ContentValues();
                    values.put(BeersContract.LikedItems.BEER_NAME, mCursor.getString(InfoLoader.Query.NAME));

                    if (likeDislikeInteraction==NO_INTERACTION) {
                        likeDislikeInteraction=LIKE;
                        cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
                        Log.e("LikeDislikeButtons",mCursor.getString(InfoLoader.Query.NAME)+" added to Like Table");
                    }
                    else if (likeDislikeInteraction==DISLIKE) {
                        likeDislikeInteraction=LIKE;
                        cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
                        Log.e("LikeDislikeButtons",mCursor.getString(InfoLoader.Query.NAME)+" added to Like Table");

                        Uri dirUriDislike = BeersContract.DislikedItems.buildDirUri();

                        String[] args = new String[] {mCursor.getString(InfoLoader.Query.NAME)};
                        cpo.add(ContentProviderOperation.newDelete(dirUriDislike).withSelection(BeersContract.DislikedItems.BEER_NAME + "=?", args).build());
                        Log.e("LikeDislikeButtons",mCursor.getString(InfoLoader.Query.NAME)+"deleted from Dislike Table");
                    }
                    else {
                        likeDislikeInteraction=NO_INTERACTION;
                        String[] args = new String[] {mCursor.getString(InfoLoader.Query.NAME)};
                        cpo.add(ContentProviderOperation.newDelete(dirUri).withSelection(BeersContract.LikedItems.BEER_NAME + "=?", args).build());
                        Log.e("LikeDislikeButtons",mCursor.getString(InfoLoader.Query.NAME)+" deleted from Like Table");


                    }
                    try {
                        getActivity().getContentResolver().applyBatch(BeersContract.CONTENT_AUTHORITY, cpo);

                    }catch (RemoteException | OperationApplicationException e) {
                        Log.e(TAG, "Error updating content.", e);
                    }

                    bindViews();
                    Toast.makeText(getActivity(), "Added to lov'd List", Toast.LENGTH_SHORT).show();


                }
            });
            mDislikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();
                    Uri dirUri = BeersContract.DislikedItems.buildDirUri();
                    ContentValues values = new ContentValues();
                    values.put(BeersContract.DislikedItems.BEER_NAME, mCursor.getString(InfoLoader.Query.NAME));

                    if(likeDislikeInteraction==NO_INTERACTION){
                        likeDislikeInteraction=DISLIKE;
                        cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
                        Log.e("LikeDislikeButtons",mCursor.getString(InfoLoader.Query.NAME)+"added to Dislike Table");
                        Toast.makeText(getActivity(), "Added to meh.. List", Toast.LENGTH_SHORT).show();

                    }
                    else if(likeDislikeInteraction==LIKE){
                        likeDislikeInteraction=DISLIKE;
                        cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
                        Log.e("LikeDislikeButtons",mCursor.getString(InfoLoader.Query.NAME)+"added to Dislike Table");
                        Toast.makeText(getActivity(), "Added to meh.. List", Toast.LENGTH_SHORT).show();

                        Uri dirUriLike = BeersContract.LikedItems.buildDirUri();

                        String[] args = new String[] {mCursor.getString(InfoLoader.Query.NAME)};
                        cpo.add(ContentProviderOperation.newDelete(dirUriLike).withSelection(BeersContract.LikedItems.BEER_NAME + "=?", args).build());
                        Log.e("LikeDislikeButtons",mCursor.getString(InfoLoader.Query.NAME)+" deleted from Like Table");

                    }
                    else {
                        likeDislikeInteraction=NO_INTERACTION;
                        String[] args = new String[] {mCursor.getString(InfoLoader.Query.NAME)};
                        cpo.add(ContentProviderOperation.newDelete(dirUri).withSelection(BeersContract.DislikedItems.BEER_NAME + "=?", args).build());
                        Log.e("LikeDislikeButtons",mCursor.getString(InfoLoader.Query.NAME)+"deleted from Dislike Table");

                    }

                    try {
                        getActivity().getContentResolver().applyBatch(BeersContract.CONTENT_AUTHORITY, cpo);

                    } catch (RemoteException | OperationApplicationException e) {
                        Log.e(TAG, "Error updating content.", e);
                    }

                    bindViews();
                }
            });
            ImageLoaderHelper.getInstance(getActivity()).getImageLoader()
                    .get(mCursor.getString(InfoLoader.Query.BOTTLE), new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                            Bitmap bitmap = imageContainer.getBitmap();
                            if (bitmap != null) {
                                Palette p = Palette.generate(bitmap, 12);
                                mMutedColor = p.getDarkMutedColor(0xFF333333);
                                mPhotoView.setImageBitmap(imageContainer.getBitmap());
                                mRootView.findViewById(R.id.meta_bar)
                                        .setBackgroundColor(mMutedColor);
                                updateStatusBar();
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });

            String flagURL= "https://dl.dropboxusercontent.com/u/58097303/wannabeer/images/flags/"+mCursor.getString(InfoLoader.Query.COUNTRY)+".png";
            ImageLoaderHelper.getInstance(getActivity()).getImageLoader()
                    .get(flagURL, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                            Bitmap bitmap = imageContainer.getBitmap();
                            if (bitmap != null) {
                                mFlagView.setImageBitmap(imageContainer.getBitmap());
                                updateStatusBar();
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
        } else {
            mRootView.setVisibility(View.GONE);
            titleView.setText("N/A");
            bylineView.setText("N/A" );
            bodyView.setText("N/A");
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if (i==0)
            return InfoLoader.newInstanceForItemId(getActivity(), mItemId);
        else if(i==1)
            return LikedBeersLoader.newInstanceForItemName(getActivity(), mItemName);
        else
            return DislikedBeersLoader.newInstanceForItemName(getActivity(),mItemName);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (!isAdded()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }

        if(cursorLoader.getId()==0) {
            mCursor = cursor;
            if (mCursor != null && !mCursor.moveToFirst()) {
                Log.e(TAG, "Error reading item detail cursor");
                mCursor.close();
                mCursor = null;
            }
            if(mCursor!=null) {
                mItemName = mCursor.getString(InfoLoader.Query.NAME);
                Log.e("onLoadFinished",mItemName);
                getLoaderManager().initLoader(1, null, this);
                getLoaderManager().initLoader(2, null, this);
            }
        }
        if(cursorLoader.getId()==1) {
            mCursorLiked = cursor;
            if (mCursorLiked != null && !mCursorLiked.moveToFirst()) {
                Log.e(TAG, "Error reading item detail cursor");
                mCursorLiked.close();
                mCursorLiked = null;
            }
            if(mCursorLiked!=null)
                likeDislikeInteraction=LIKE;
        }
        if(cursorLoader.getId()==2) {
            mCursorDisliked = cursor;
            if (mCursorDisliked != null && !mCursorDisliked.moveToFirst()) {
                Log.e(TAG, "Error reading item detail cursor");
                mCursorDisliked.close();
                mCursorDisliked = null;
            }
            if(mCursorDisliked!=null)
                likeDislikeInteraction=DISLIKE;
        }

        bindViews();
    }
    static float progress(float v, float min, float max) {
        return constrain((v - min) / (max - min), 0, 1);
    }

    static float constrain(float val, float min, float max) {
        if (val < min) {
            return min;
        } else if (val > max) {
            return max;
        } else {
            return val;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
        bindViews();
    }
    private void updateStatusBar() {
        int color = 0;
        if (mPhotoView != null && mTopInset != 0 && mScrollY > 0) {
            float f = progress(mScrollY,
                    mStatusBarFullOpacityBottom - mTopInset * 3,
                    mStatusBarFullOpacityBottom - mTopInset);
            color = Color.argb((int) (255 * f),
                    (int) (Color.red(mMutedColor) * 0.9),
                    (int) (Color.green(mMutedColor) * 0.9),
                    (int) (Color.blue(mMutedColor) * 0.9));
        }
        mStatusBarColorDrawable.setColor(color);
        mDrawInsetsFrameLayout.setInsetBackground(mStatusBarColorDrawable);
    }
    public int getUpButtonFloor() {
        if (mPhotoContainerView == null || mPhotoView.getHeight() == 0) {
            return Integer.MAX_VALUE;
        }

        // account for parallax
        return mIsCard
                ? (int) mPhotoContainerView.getTranslationY() + mPhotoView.getHeight() - mScrollY
                : mPhotoView.getHeight() - mScrollY;
    }
}
