package com.example.burcakdemircioglu.wannabeer.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.burcakdemircioglu.wannabeer.R;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.placeholder_image);
        //RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),icon);
        //drawable.setCircular(true);
        ImageView listImage=(ImageView)findViewById(R.id.list_image);
        //listImage.setImageDrawable(drawable);
    }

}
