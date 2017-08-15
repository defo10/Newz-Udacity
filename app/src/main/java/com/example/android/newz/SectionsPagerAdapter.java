package com.example.android.newz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.newz.sciencenews.ScienceFragment;
import com.example.android.newz.technews.TechFragment;
import com.example.android.newz.worldnews.WorldNewsFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return WorldNewsFragment.newInstance();
            case 1:
                return TechFragment.newInstance();
            case 2:
                return ScienceFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "World-News";
            case 1:
                return "Tech";
            case 2:
                return "Science";
        }
        return null;
    }
}
