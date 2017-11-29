package com.example.myfirstapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by User on 2017/10/3.
 */

public class Dashboard_ViewPagerAdapter extends FragmentPagerAdapter {


    public Dashboard_ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return Dashboard_ActionList.newInstance(0);
            case 1:
                return Dashboard_Timeline.newInstance(1);
            case 2:
                return Dashboard_Timeline.newInstance(2);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Action List";
            case 1:
                return "本周";
            case 2:
                return "本月";
        }
        return null;
    }
}
