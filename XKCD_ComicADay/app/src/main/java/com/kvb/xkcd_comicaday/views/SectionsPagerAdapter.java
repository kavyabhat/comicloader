package com.kvb.xkcd_comicaday.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    int daysOfComics;
    long latestComicNum;

    public SectionsPagerAdapter(FragmentManager fm, int count, long comicNum) {
        super(fm);
        daysOfComics = count;
        latestComicNum = comicNum;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0)
        {
            return PlaceholderFragment.newInstance(position + 1, daysOfComics, 0);
        }else{
            //get comic number of 1st comic and calculate previous ones
            return PlaceholderFragment.newInstance(position + 1, daysOfComics, latestComicNum);
        }
    }

    @Override
    public int getCount() {
        // return count of total pages.
        return daysOfComics;
    }

    public void setLatestDay(long latestDayNum){
        latestComicNum = latestDayNum;
    }
}
