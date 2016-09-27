package com.demo.freecats.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.demo.freecats.fragment.ImageDetailFragment;

import java.util.ArrayList;

/**
 * ViewPager Adapter
 */
public class ImageShowPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mLists = new ArrayList<>();


    public ImageShowPagerAdapter(FragmentManager fm, ArrayList<String> urls) {
        super(fm);
        if (null != urls)
            for (String url : urls) {
                Fragment f = ImageDetailFragment.newInstance(url);
                mLists.add(f);
            }
    }

    public Fragment getItem(int i) {
        return mLists.get(i);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public int getCount() {
        return mLists.size();
    }
}
