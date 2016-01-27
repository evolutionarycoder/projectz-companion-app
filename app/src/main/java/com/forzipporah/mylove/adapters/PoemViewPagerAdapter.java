package com.forzipporah.mylove.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.forzipporah.mylove.fragments.poem.PoemFavouriteFragment;
import com.forzipporah.mylove.fragments.poem.PoemListFragment;

import java.util.ArrayList;

public class PoemViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{
            "Poems",
            "Favourites"
    };

    private ArrayList<Fragment> mFragments;

    public PoemViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        mFragments.add(new PoemListFragment());
        mFragments.add(new PoemFavouriteFragment());
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
