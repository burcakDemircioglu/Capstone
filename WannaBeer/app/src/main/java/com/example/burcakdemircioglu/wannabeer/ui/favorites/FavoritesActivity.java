package com.example.burcakdemircioglu.wannabeer.ui.favorites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.ui.adapters.FavoritesPagerAdapter;

public class FavoritesActivity extends AppCompatActivity {
    ImageButton mUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Window window=getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView profileForeground = (ImageView) findViewById(R.id.profile_foreground);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_foreground);

        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        drawable.setCircular(true);
        profileForeground.setImageDrawable(drawable);

        mUpButton = (ImageButton) findViewById(R.id.action_up);
        mUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();
                supportFinishAfterTransition();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("lov'd!"));
        tabLayout.addTab(tabLayout.newTab().setText("meh.."));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.text_color_subtitle));
        tabLayout.setTabTextColors(getResources().getColor(R.color.text_color),getResources().getColor(R.color.text_color));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final FavoritesPagerAdapter adapter = new FavoritesPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
