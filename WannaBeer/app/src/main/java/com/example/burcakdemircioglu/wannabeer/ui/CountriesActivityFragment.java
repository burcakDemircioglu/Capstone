package com.example.burcakdemircioglu.wannabeer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.ui.adapters.CountryTitlesAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class CountriesActivityFragment extends Fragment {

    CountryTitlesAdapter adapter;

    private Activity activity=getActivity();

    public CountriesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_categories, container, false);

        final ListView list=(ListView)rootView.findViewById(R.id.search_listView);

        adapter=new CountryTitlesAdapter(getActivity(), CountriesActivity.countries, getResources());

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String tempValues =CountriesActivity.countries[position];
                Intent intent=new Intent(getActivity(), CountryDetailActivity.class);
                intent.putExtra("country", tempValues);
                startActivity(intent);
            }
        });
        list.setAdapter(adapter);


        return rootView;
    }
}
