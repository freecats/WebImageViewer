package com.demo.freecats.util;

import android.text.TextUtils;
import android.widget.ImageView;

import com.demo.freecats.BaseApplication;
import com.demo.freecats.R;
import com.demo.freecats.interfaces.ImageLoadedListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static android.text.TextUtils.isEmpty;

public class ImageLoaderUtils {

    public static void loadImageWithListener(final String imageUrl, final ImageView imageView, final ImageLoadedListener listener) {
        if (isEmpty(imageUrl) || null == imageView) return;
        Picasso.with(BaseApplication.getGlobalContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (null != listener && null != imageView && null != imageView.getDrawable())
                            listener.onSuccess(imageView, imageView.getDrawable());
                    }

                    @Override
                    public void onError() {
                        if (null != listener && null != imageView) listener.onError(imageView);
                    }
                });
    }

    public static void loadImage(String imageUrl, ImageView imageView, int width, int height) {
        if (null == imageView || TextUtils.isEmpty(imageUrl) || width < 0 || height < 0) {
            return;
        }

        Picasso.with(BaseApplication.getGlobalContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .resize(width, height)
                .into(imageView);
    }


}
