package com.example.burcakdemircioglu.wannabeer.ui.categories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.ui.SearchActivity;
import com.example.burcakdemircioglu.wannabeer.ui.util.menuUtils;

public class CategoriesDetailActivity extends AppCompatActivity {
    public static String mKind;
    private Toolbar mToolbar;
    private Activity activity=this;

    String getKind(){
        return mKind;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_detail);
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
            mKind=getIntent().getExtras().getString("kind");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ab_back_material);

        //menuUtils.initActionBar(getSupportActionBar());
        menuUtils.setupCollapsingToolbar(mKind, this, getResources());
    }



}
