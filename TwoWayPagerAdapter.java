package com.app.ordering.orderingsystem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Administrator on 5/6/2016.
 */
public class TwoWayPagerAdapter extends FragmentStatePagerAdapter {

    public TwoWayPagerAdapter(FragmentManager fm){
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0:
                frag = new Promotion();
                break;
            case 1:
                frag = new OrderSelected();
                break;
        }

        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Promotion";
                break;
            case 1:
                title = "Order";
                break;
        }
        return title;
    }
}
