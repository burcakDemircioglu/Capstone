package com.example.burcakdemircioglu.wannabeer.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.burcakdemircioglu.wannabeer.ui.favorites.dislike_tab;
import com.example.burcakdemircioglu.wannabeer.ui.favorites.like_tab;

/**
 * Created by burcakdemircioglu on 28/04/16.
 */
public class FavoritesPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public FavoritesPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                like_tab tab1 = new like_tab();
                return tab1;
            case 1:
                dislike_tab tab2 = new dislike_tab();

                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}