package com.example.burcakdemircioglu.wannabeer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.burcakdemircioglu.wannabeer.R;

public class CategoriesActivity extends AppCompatActivity {
    public static String[] categories={"DarkAle", "GoldenAle", "ImperialStout", "Lager", "PaleAle","Pilsener","Porter","Stout","WheatBeer"};
    public CategoriesActivity CustomListView;
    private Activity activity=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomListView=this;
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, SearchActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        String tempValues =categories[mPosition];


        // SHOW ALERT

        Toast.makeText(CustomListView,
                ""+tempValues+"Image:"+"Url:",
        Toast.LENGTH_LONG)
        .show();
    }

}
