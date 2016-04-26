package com.example.burcakdemircioglu.wannabeer.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.burcakdemircioglu.wannabeer.R;
import com.example.burcakdemircioglu.wannabeer.ui.CategoriesActivity;

/**
 * Created by burcakdemircioglu on 26/04/16.
 */
public class CategoryTitlesAdapter extends BaseAdapter implements View.OnClickListener{
    private Activity activity;
    private String[] data;
    private static LayoutInflater inflater=null;
    public Resources res;
    private String CategoryTitle;

    public CategoryTitlesAdapter(Activity a, String[] d,Resources resLocal) {
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

             list_item_view= inflater.inflate(R.layout.list_item, null);

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
            CategoryTitle = data[position];
            holder.text.setText(CategoryTitle);

            String categoriesURL= "https://dl.dropboxusercontent.com/u/58097303/wannabeer/images/categories/"+CategoryTitle+".jpg";
            ImageLoaderHelper.getInstance(activity).getImageLoader()
                    .get(categoriesURL, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                            Bitmap bitmap = imageContainer.getBitmap();

                            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(res,bitmap);
                            drawable.setCircular(true);
                            //listImage.setImageDrawable(drawable);

                            if (bitmap != null) {
                                holder.image.setImageDrawable(drawable);
                                // holder.image.setImageBitmap(imageContainer.getBitmap());
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });

            list_item_view.setOnClickListener(new OnItemClickListener( position ));
        }
        return list_item_view;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "A category title is clicked");
    }

    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {

            CategoriesActivity categoriesActivity = (CategoriesActivity) activity;
            categoriesActivity.onItemClick(mPosition);
        }
    }
}