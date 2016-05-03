package com.example.burcakdemircioglu.wannabeer.ui.categories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.ui.SearchActivity;
import com.example.burcakdemircioglu.wannabeer.ui.util.menuUtils;

public class CategoriesActivity extends AppCompatActivity {
    public static String[] categories={"PaleAle", "Lager","Pilsener", "WheatBeer", "GoldenAle", "DarkAle","Porter","Stout", "ImperialStout"};
    public CategoriesActivity CustomListView;
    private Activity activity=this;
    private DrawerLayout drawerLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomListView=this;
        setContentView(R.layout.activity_categories);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        menuUtils.setupActionBarAndNavigation(drawerLayout, "Categories", getSupportActionBar(), this, getResources());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*****************  This function used by adapter ****************/
/*
    public void onItemClick(int mPosition)
    {
        String tempValues =categories[mPosition];

        Intent intent=new Intent(this, CategoriesDetailActivity.class);
        intent.putExtra("kind", tempValues);
        startActivity(intent);
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
