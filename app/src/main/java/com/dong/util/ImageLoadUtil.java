package com.dong.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by zengwendong on 16/8/12.
 */
public class ImageLoadUtil {

    public static void display(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }

}
