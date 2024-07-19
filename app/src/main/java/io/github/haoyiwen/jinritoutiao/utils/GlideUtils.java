package io.github.haoyiwen.jinritoutiao.utils;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.github.haoyiwen.jinritoutiao.R;

// glide封装
public class GlideUtils {
    public static void load(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.ic_default);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    public static void load(Context context, String url, ImageView iv, int placeHolderResId) {
        if (placeHolderResId == -1) {
            Glide.with(context)
                    .load(url)
                    .into(iv);
            return;
        }

        RequestOptions options = new RequestOptions();
        options.placeholder(placeHolderResId);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }
}
