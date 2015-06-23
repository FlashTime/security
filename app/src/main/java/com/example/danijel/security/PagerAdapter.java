package com.example.danijel.security;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Danijel on 6/23/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private Fragment f = null;

    @Override
    public Fragment getItem(int position) { // Returns Fragment based on position
        switch (position) {
            case 0:
                f = new FragmentPageOne();
                break;
            case 1:
                f = new FragmentPageTwo();
                break;
        }
        return f;
    }

    @Override
    public int getCount() { // Return the number of pages
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) { // Set the tab text
        if (position == 0) {
            return "Fragment One";
        }
        if (position == 1) {
            return "Fragment Two";
        }
        return getPageTitle(position);
    }

}
