package com.example.burcakdemircioglu.wannabeer.ui;

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
import com.example.burcakdemircioglu.wannabeer.ui.util.menuUtils;

public class CountriesActivity extends AppCompatActivity {

    public static String[] countries={"Belgium", "Colorado - USA","Czech Republic", "Denmark", "Germany", "Ireland","Italy","Japan", "Mexico","Netherlands", "Scotland", "Turkey", "United Kingdom", "USA"};
    public CountriesActivity CustomListView;
    private Activity activity=this;
    private DrawerLayout drawerLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomListView=this;
        setContentView(R.layout.activity_countries);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        menuUtils.setupActionBarAndNavigation(drawerLayout, "Countries", getSupportActionBar(), this, getResources());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
