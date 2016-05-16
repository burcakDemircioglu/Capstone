package com.example.burcakdemircioglu.wannabeer;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by burcakdemircioglu on 16/05/16.
 */
public class WidgetService extends RemoteViewsService {
	/*
	 * So pretty simple just defining the Adapter of the listview
	 * here Adapter is ListProvider
	 * */

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetListProvider(this.getApplicationContext(), intent));
    }

}