package com.example.burcakdemircioglu.wannabeer.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.data.InfoLoader;
import com.example.burcakdemircioglu.wannabeer.ui.util.ImageLoaderHelper;

/**
 * Created by burcakdemircioglu on 10/05/16.
 */
public class BeerLikedListAdapter extends CursorAdapter {
    Context activity;
    public BeerLikedListAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        activity=context;
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View root= LayoutInflater.from(context).inflate(R.layout.beer_list_item, parent, false);
        return root;
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        final BeerListItemViewHolder holder=new BeerListItemViewHolder();
        holder.nameView = (TextView) view.findViewById(R.id.beer_name);
        holder.countryView = (TextView) view.findViewById(R.id.beer_country);
        holder.imageView=(ImageView) view.findViewById(R.id.beer_logo);

        ImageLoaderHelper.getInstance(activity).getImageLoader()
                .get(cursor.getString(InfoLoader.Query.PHOTO+1), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                        Bitmap bitmap = imageContainer.getBitmap();
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(activity.getResources(),bitmap);
                        drawable.setCircular(true);
                        if (bitmap != null) {
                            holder.imageView.setImageDrawable(drawable);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        // Extract properties from cursor
        String name = cursor.getString(InfoLoader.Query.NAME+1);
        String country = cursor.getString(InfoLoader.Query.COUNTRY+1);
        // Populate fields with extracted properties
        holder.nameView.setText(name);
        holder.countryView.setText(String.valueOf(country));
    }

}