package com.example.burcakdemircioglu.wannabeer.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageView;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.data.BeersContract;
import com.example.burcakdemircioglu.wannabeer.data.InfoLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by burcakdemircioglu on 21/04/16.
 */
public class BeerDetailActivity extends AppCompatActivity
            implements LoaderManager.LoaderCallbacks<Cursor> {

        private Cursor mCursor;
        private long mStartId;

        private long mSelectedItemId;
        private int mSelectedItemUpButtonFloor = Integer.MAX_VALUE;
        private int mTopInset;

        private boolean mIsReturning;
        private BeerDetailFragment mCurrentDetailsFragment;
        private int mCurrentPosition;
        private int mStartingPosition;

        private ViewPager mPager;
        private MyPagerAdapter mPagerAdapter;
        private View mUpButtonContainer;
        private View mUpButton;
        static final String EXTRA_STARTING_ALBUM_POSITION = "extra_starting_item_position";
        static final String EXTRA_CURRENT_ALBUM_POSITION = "extra_current_item_position";
        private static final String STATE_CURRENT_PAGE_POSITION = "state_current_page_position";

        private final SharedElementCallback mCallback = new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (mIsReturning) {
                    ImageView sharedElement = mCurrentDetailsFragment.getPhotoView();
                    if (sharedElement == null) {
                        // If shared element is null, then it has been scrolled off screen and
                        // no longer visible. In this case we cancel the shared element transition by
                        // removing the shared element from the shared elements map.
                        names.clear();
                        sharedElements.clear();
                    } else if (mStartingPosition != mCurrentPosition) {
                        // If the user has swiped to a different ViewPager page, then we need to
                        // remove the old shared element and replace it with the new shared element
                        // that should be transitioned instead.
                        names.clear();
                        names.add(sharedElement.getTransitionName());
                        sharedElements.clear();
                        sharedElements.put(sharedElement.getTransitionName(), sharedElement);
                    }
                }
            }
        };
        @Override
        public void finishAfterTransition() {
            mIsReturning = true;
            Intent data = new Intent();
            data.putExtra(EXTRA_STARTING_ALBUM_POSITION, mStartingPosition);
            data.putExtra(EXTRA_CURRENT_ALBUM_POSITION, mCurrentPosition);
            setResult(RESULT_OK, data);
            super.finishAfterTransition();
        }
        @Override
        protected void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt(STATE_CURRENT_PAGE_POSITION, mCurrentPosition);
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setEnterSharedElementCallback(mCallback);

            mStartingPosition = getIntent().getIntExtra(EXTRA_STARTING_ALBUM_POSITION, 0);
            if (savedInstanceState == null) {
                mCurrentPosition = mStartingPosition;
            } else {
                mCurrentPosition = savedInstanceState.getInt(STATE_CURRENT_PAGE_POSITION);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            setContentView(R.layout.beer_detail);

            getLoaderManager().initLoader(0, null, this);

            mPagerAdapter = new MyPagerAdapter(getFragmentManager());
            mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setAdapter(mPagerAdapter);
            mPager.setPageMargin((int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
            mPager.setPageMarginDrawable(new ColorDrawable(0x22000000));

            mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                    mUpButton.animate()
                            .alpha((state == ViewPager.SCROLL_STATE_IDLE) ? 1f : 0f)
                            .setDuration(300);
                }

                @Override
                public void onPageSelected(int position) {
                    if (mCursor != null) {
                        mCursor.moveToPosition(position);
                    }
                    mSelectedItemId = mCursor.getLong(InfoLoader.Query._ID);
                    updateUpButtonPosition();

                    mCurrentPosition = position;
                    //Log.e("positionChangePage", Integer.toString(mCurrentPosition));
                }
            });

            mUpButtonContainer = findViewById(R.id.up_container);

            mUpButton = findViewById(R.id.action_up);
            mUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSupportNavigateUp();
                    supportFinishAfterTransition();
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mUpButtonContainer.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        view.onApplyWindowInsets(windowInsets);
                        mTopInset = windowInsets.getSystemWindowInsetTop();
                        mUpButtonContainer.setTranslationY(mTopInset);
                        updateUpButtonPosition();
                        return windowInsets;
                    }
                });
            }

            if (savedInstanceState == null) {
                if (getIntent() != null && getIntent().getData() != null) {
                    mStartId = BeersContract.Items.getItemId(getIntent().getData());
                    mSelectedItemId = mStartId;
                }
            }
        }

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            return InfoLoader.newAllArticlesInstance(this);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
            mCursor = cursor;
            mPagerAdapter.notifyDataSetChanged();

            // Select the start ID
            if (mStartId > 0) {
                mCursor.moveToFirst();
                // TODO: optimize
                while (!mCursor.isAfterLast()) {
                    if (mCursor.getLong(InfoLoader.Query._ID) == mStartId) {
                        final int position = mCursor.getPosition();
                        mPager.setCurrentItem(position, false);
                        break;
                    }
                    mCursor.moveToNext();
                }
                mStartId = 0;
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> cursorLoader) {
            mCursor = null;
            mPagerAdapter.notifyDataSetChanged();
        }

        public void onUpButtonFloorChanged(long itemId, BeerDetailFragment fragment) {
            if (itemId == mSelectedItemId) {
                mSelectedItemUpButtonFloor = fragment.getUpButtonFloor();
                updateUpButtonPosition();
            }
        }

        private void updateUpButtonPosition() {
            int upButtonNormalBottom = mTopInset + mUpButton.getHeight();
            mUpButton.setTranslationY(Math.min(mSelectedItemUpButtonFloor - upButtonNormalBottom, 0));
        }

        private class MyPagerAdapter extends FragmentStatePagerAdapter {
            public MyPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
                BeerDetailFragment fragment = (BeerDetailFragment) object;
                mCurrentDetailsFragment=fragment;
                if (fragment != null) {
                    mSelectedItemUpButtonFloor = fragment.getUpButtonFloor();
                    updateUpButtonPosition();
                }
            }

            @Override
            public Fragment getItem(int position) {
                mCursor.moveToPosition(position);
                //Log.e("positionFromPager", Integer.toString(position));
                return BeerDetailFragment.newInstance(mCursor.getLong(InfoLoader.Query._ID), mStartingPosition, position);
            }

            @Override
            public int getCount() {
                return (mCursor != null) ? mCursor.getCount() : 0;
            }
        }
}
