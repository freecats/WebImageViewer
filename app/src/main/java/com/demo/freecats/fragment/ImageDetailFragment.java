package com.demo.freecats.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.freecats.R;
import com.demo.freecats.interfaces.ImageLoadedListener;
import com.demo.freecats.util.ImageLoaderUtils;
import com.demo.freecats.util.ScreenUtils;

/**
 * Zoomable image viewer fragment
 */
public class ImageDetailFragment extends Fragment {

    private static final String KEY_URL = "KEY_URL";
    ImageView imageView;

    public static ImageDetailFragment newInstance(String url) {
        ImageDetailFragment approvalFragment = new ImageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URL, url);
        approvalFragment.setArguments(bundle);
        return approvalFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
        imageView = (ImageView) view.findViewById(R.id.iv_image);
        init();
        return view;
    }

    private void init() {
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        loadImage();

    }

    void loadImage() {

        ImageLoaderUtils.loadImageWithListener(getArguments().getString(KEY_URL), imageView, new ImageLoadedListener() {
            @Override
            public void onSuccess(ImageView imageView, Drawable drawable) {
                //in case of some image is too large, so we resize it to fit our phone size
                if (null != imageView && null != imageView.getDrawable()) {
                    int w = (int) (ScreenUtils.getInstance().getScreenWidth() * 0.9);
                    int h = (int) (w * Float.valueOf(imageView.getDrawable().getIntrinsicHeight()) / Float.valueOf(imageView.getDrawable().getIntrinsicWidth()));
                    ImageLoaderUtils.loadImage(getArguments().getString(KEY_URL), imageView, w, h);

                    ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                    if (null != lp) {
                        lp.height = h > ScreenUtils.getInstance().getScreenHeight() ? h
                                : ScreenUtils.getInstance().getScreenHeight()
                                - ScreenUtils.getInstance().getTitlebarHeight()
                                - ScreenUtils.getInstance().getStatusBarHeight(getActivity());
                        lp.width = w > ScreenUtils.getInstance().getScreenWidth() ? w : ScreenUtils.getInstance().getScreenWidth();
                        imageView.setLayoutParams(lp);
                    }
                }


            }

            @Override
            public void onError(ImageView imageView) {

            }
        });
    }

}
