package com.managerapp.personnelmanagerapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.managerapp.personnelmanagerapp.R;

public class ImageLoaderUtils {
    public static void loadAvatar(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_broken_image)
                .into(imageView);
    }
}
