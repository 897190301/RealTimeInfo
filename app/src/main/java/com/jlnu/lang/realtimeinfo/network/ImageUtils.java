package com.jlnu.lang.realtimeinfo.network;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jlnu.lang.realtimeinfo.application.App;

/**
 * Created by qn on 2017/1/11.
 */

public class ImageUtils {

    /**
     * 判断是否为gif
     * @param url
     * @return
     */
    public static boolean isGif(String url) {
        return !TextUtils.isEmpty(url) && url.endsWith(".gif");
    }

    public static void loadToImageView(String url, ImageView imageView) {
        if (isGif(url)) {
            loadToImageViewStaticGif(url, imageView);
        } else {
            Glide.with(App.getInstance())
                    .load(url)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);
        }
    }

    public static void loadToImageView(String url, ImageView imageView,int backgroundPic){
        if (isGif(url)) {
            loadToImageViewStaticGif(url, imageView);
        } else {
            Glide.with(App.getInstance())
                    .load(url)
                    .placeholder(backgroundPic)
                    .centerCrop()
                    //.crossFade()           //增加过度效果
                    .into(imageView);
        }
    }

    public static void loadToImageViewStaticGif(String url, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(url)
                .asBitmap()
                .centerCrop()
                .into(imageView);
    }
}
