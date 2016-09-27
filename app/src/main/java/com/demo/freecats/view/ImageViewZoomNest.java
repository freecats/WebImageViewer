package com.demo.freecats.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ImageViewZoom nest in another scrollable widget
 */
public class ImageViewZoomNest extends ImageViewZoom {

    public ImageViewZoomNest(Context context) {
        this(context, null, 0);
    }

    public ImageViewZoomNest(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewZoomNest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (null != getParent()) {
                    if (event.getPointerCount() > 1) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }
                return false;
            }
        });
    }

}

