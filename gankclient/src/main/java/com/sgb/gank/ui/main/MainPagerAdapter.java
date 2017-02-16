package com.sgb.gank.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panda on 16/8/25 下午3:10.
 */
class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private String[] mTitleList;

    MainPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titleList) {
        super(fm);
        mFragmentList.clear();
        mFragmentList.addAll(fragmentList);
        mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList[position];
    }
}
