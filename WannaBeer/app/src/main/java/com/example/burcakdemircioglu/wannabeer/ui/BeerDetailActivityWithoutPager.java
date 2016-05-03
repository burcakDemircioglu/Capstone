package com.example.burcakdemircioglu.wannabeer.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowInsets;

import com.example.burcakdemircioglu.wannabeer.R;

public class BeerDetailActivityWithoutPager extends AppCompatActivity {
    private View mUpButtonContainer;
    private View mUpButton;
    private int mTopInset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail_activity_without_pager);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
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
                    //mTopInset = windowInsets.getSystemWindowInsetTop();
                    //mUpButtonContainer.setTranslationY(mTopInset);
                    //updateUpButtonPosition();
                    return windowInsets;
                }
            });
        }
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
