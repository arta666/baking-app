package com.arman.baking.util;

import android.media.Image;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoader {

    public static void load(ImageView view,String url){
        if (url != null && !url.equals("")){
            Picasso.with(view.getContext())
                    .load(url)
                    .into(view);
        }
    }
}
