package com.example.burcakdemircioglu.wannabeer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.ui.util.menuUtils;

public class CountryDetailActivity extends AppCompatActivity {
    public static String mCountry;
    private Toolbar mToolbar;
    private Activity activity=this;

    String getCountry(){
        return mCountry;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries_detail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });

        if(getIntent().getExtras()!=null)
            mCountry =getIntent().getExtras().getString("country");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ab_back_material);

        //menuUtils.initActionBar(getSupportActionBar());
        menuUtils.setupCollapsingToolbar(mCountry, this, getResources());
    }



}
