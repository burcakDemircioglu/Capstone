package com.example.burcakdemircioglu.wannabeer.ui.util;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.ui.CategoriesActivity;
import com.example.burcakdemircioglu.wannabeer.ui.FavoritesActivity;
import com.example.burcakdemircioglu.wannabeer.ui.MainActivity;
import com.example.burcakdemircioglu.wannabeer.ui.SettingsActivity;

/**
 * Created by burcakdemircioglu on 26/04/16.
 */
public abstract class menuUtils {


    public static void setupActionBarAndNavigation(DrawerLayout drawerLayout,
                                                   String title, ActionBar actionBar, Activity activity, Resources res){
        menuUtils.initActionBar(actionBar);
        menuUtils.setupDrawerLayout(drawerLayout, activity, res);
        menuUtils.setupCollapsingToolbar(title, activity, res);

    }

    public static void setupCollapsingToolbar(String title, Activity activity, Resources res){
        CollapsingToolbarLayout mCollapsingToolbarLayout=(CollapsingToolbarLayout)activity.findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle(title);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(Typeface.createFromAsset(res.getAssets(), "Chalkduster.ttf"));
        mCollapsingToolbarLayout.setExpandedTitleTypeface(Typeface.createFromAsset(res.getAssets(), "Chalkduster.ttf"));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(res.getColor(R.color.image_background));
        mCollapsingToolbarLayout.setExpandedTitleColor(res.getColor(R.color.image_background));

    }

    public static void initActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_moreoverflow_holo_light);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static void setupDrawerLayout(final DrawerLayout drawerLayout, final Activity activity, final Resources res) {

        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
                if(menuItem.getTitle().toString().equals(res.getString(R.string.navigation_home))){
                    Intent intent=new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                }

                if(menuItem.getTitle().toString().equals(res.getString(R.string.navigation_favorites))){
                    Intent intent=new Intent(activity, FavoritesActivity.class);
                    activity.startActivity(intent);
                }

                if(menuItem.getTitle().toString().equals(res.getString(R.string.navigation_categories))){
                    Intent intent=new Intent(activity, CategoriesActivity.class);
                    activity.startActivity(intent);
                }

                if(menuItem.getTitle().toString().equals(res.getString(R.string.navigation_settings))){
                    Intent intent=new Intent(activity, SettingsActivity.class);
                    activity.startActivity(intent);
                }
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
