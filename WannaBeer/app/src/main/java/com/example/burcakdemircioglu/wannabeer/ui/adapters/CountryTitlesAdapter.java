package com.example.burcakdemircioglu.wannabeer.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.ui.util.ImageLoaderHelper;

/**
 * Created by burcakdemircioglu on 26/04/16.
 */
public class CountryTitlesAdapter extends BaseAdapter {
    private Activity activity;
    private String[] data;
    private static LayoutInflater inflater=null;
    public Resources res;
    private String CountryTitle;

    public CountryTitlesAdapter(Activity a, String[] d, Resources resLocal) {
        activity = a;
        data=d;
        res = resLocal;

        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        if(data.length<=0)
            return 1;
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        public TextView text;
        public ImageView image;
    }

    public View getView(int position, View listItem, ViewGroup parent) {

        View list_item_view = listItem;
        final ViewHolder holder;

        if(list_item_view==null){

             list_item_view= inflater.inflate(R.layout.category_title_list_item, null);

            holder = new ViewHolder();
            holder.text = (TextView) list_item_view.findViewById(R.id.categories_title);
            holder.image=(ImageView)list_item_view.findViewById(R.id.categories_image);

            list_item_view.setTag( holder );
        }
        else
            holder=(ViewHolder)list_item_view.getTag();

        if(data.length<=0)
            holder.text.setText("No Data");

        else
        {
            CountryTitle = data[position];
            holder.text.setText(CountryTitle);

            String categoriesURL= "https://dl.dropboxusercontent.com/u/58097303/wannabeer/images/flags/"+CountryTitle+".png";
            ImageLoaderHelper.getInstance(activity).getImageLoader()
                    .get(categoriesURL, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                            Bitmap bitmap = imageContainer.getBitmap();

                            //RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(res,bitmap);
                            //drawable.setCircular(true);
                            //listImage.setImageDrawable(drawable);

                            if (bitmap != null) {
                                //holder.image.setImageDrawable(drawable);
                                 holder.image.setImageBitmap(imageContainer.getBitmap());
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });

            //list_item_view.setOnClickListener(new OnItemClickListener( position ));
        }
        return list_item_view;
    }


}