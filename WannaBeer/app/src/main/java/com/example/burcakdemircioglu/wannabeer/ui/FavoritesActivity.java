package com.example.burcakdemircioglu.wannabeer.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.burcakdemircioglu.wannabeer.R;

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

    }

}
