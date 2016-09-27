package com.demo.freecats.util;

import android.app.Activity;
import android.graphics.Rect;

import com.demo.freecats.BaseApplication;

import java.lang.reflect.Field;

public class ScreenUtils {
    private static ScreenUtils instance = null;

    public static ScreenUtils getInstance() {
        if (instance == null) {
            synchronized (ScreenUtils.class) {
                if (instance == null) {
                    instance = new ScreenUtils();
                }
            }
        }
        return instance;
    }

    /**
     * get screen width
     *
     * @return screen width in px
     */
    public int getScreenWidth() {
        return BaseApplication.getGlobalContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * get screen height
     *
     * @return screen height in px
     */
    public int getScreenHeight() {
        return BaseApplication.getGlobalContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * get title bar height
     * @return title bar height
     */
    public int getTitlebarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int titlebarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            titlebarHeight = BaseApplication.getGlobalContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return titlebarHeight;
    }

    /**
     * get status bar height
     * @param activity instance of activity
     * @return status bar height
     */
    public int getStatusBarHeight(Activity activity) {
        if (null == activity) return 0;
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }
}
