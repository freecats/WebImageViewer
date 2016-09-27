package com.demo.freecats.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.demo.freecats.BuildConfig;
import com.demo.freecats.R;
import com.demo.freecats.adapter.ImageShowPagerAdapter;

import java.util.ArrayList;


/**
 * <strong>multiple image viewer activity</strong>
 * <br><font color="#00EE00"><strong>if only one image needed to be show，
 * use {@link ImagesShowActivity#KEY_URL} to deliver an image url</strong></font>
 * <br>such as：bundle.putString(ImagesShowActivity.KEY_URL, url);
 * <br><font color="#00EE00"><strong>if multiple image needed to be show，
 * use {@link ImagesShowActivity#KEY_URLS} to deliver an {@code  ArrayList<String>} as urls</strong></font>
 * <br>such as：bundle.putStringArrayList(ImagesShowActivity.KEY_URLS, mLists);
 * <br><font color="#00EE00"><strong>in multiple case，if you need to set current index，
 * use {@link ImagesShowActivity#KEY_INDEX} to deliver an {@code  int} as index</strong></font>
 * <br>such as：bundle.putInt(ImagesShowActivity.KEY_INDEX, 2);
 * <br>
 */
public class ImagesShowActivity extends AppCompatActivity {

    ViewPager viewPager;
    ImageShowPagerAdapter mAdapter;
    private ArrayList<String> mLists = new ArrayList<>();
    private int index = 0;

    public final static String KEY_URL = "KEY_URL";
    public final static String KEY_INDEX = "KEY_INDEX";
    public final static String KEY_URLS = "KEY_URLS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getIntent() && null != getIntent().getExtras()) {
            if (null != getIntent().getExtras().getStringArrayList(KEY_URLS)
                    && 0 < getIntent().getExtras().getStringArrayList(KEY_URLS).size()) {
                mLists.addAll(getIntent().getExtras().getStringArrayList(KEY_URLS));
            } else if (null != getIntent().getExtras().getString(KEY_URL)) {
                mLists.add(getIntent().getExtras().getString(KEY_URL));
            } else {
                if (BuildConfig.DEBUG)
                    Log.e("oops", "i got nothing");
                finish();
                return;
            }

            if (-1 != getIntent().getIntExtra(KEY_INDEX, -1)) {
                index = getIntent().getIntExtra(KEY_INDEX, -1);
                if (index < 0 || index >= mLists.size()) {
                    index = -1;
                }
            }
        } else {
            if (BuildConfig.DEBUG)
                Log.e("oops", "i got nothing");
            finish();
        }

        setContentView(R.layout.activity_images_show);
        if (null != getSupportActionBar())
            getSupportActionBar().setTitle((index + 1) + "/" + mLists.size());

        viewPager = (ViewPager) findViewById(R.id.vp);
        mAdapter = new ImageShowPagerAdapter(getSupportFragmentManager(), mLists);
        viewPager.setOffscreenPageLimit(mLists.size());
        viewPager.setAdapter(mAdapter);
        //set current index
        if (-1 != index) {
            viewPager.setCurrentItem(index);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (null != getSupportActionBar())
                    getSupportActionBar().setTitle((position + 1) + "/" + mLists.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
