package com.sgb.gank.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sgb.gank.R;
import com.sgb.gank.databinding.ActivityGankMainBinding;
import com.sgb.gank.ui.BaseActivity;
import com.sgb.gank.ui.main.fragment.AndroidListFragment;
import com.sgb.gank.ui.main.fragment.DiDiDiFragment;
import com.sgb.gank.ui.main.fragment.IOSListFragment;
import com.sgb.gank.ui.search.SearchActivity;
import com.sgb.gank.ui.setting.SettingActivity;
import com.sgb.gank.util.ActivityUtils;
import com.sgb.gank.util.SystemBarHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGankMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_gank_main);

        Toolbar toolbar = binding.toolbar;
        NavigationView navigationView = binding.navView;
        TabLayout tabLayout = binding.tablayout;
        ViewPager viewPager = binding.viewpager;
        mDrawerLayout = binding.drawerLayout;

        setSupportActionBar(toolbar);

        binding.setFabClick(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            AndroidListFragment mAndroidTechFragment = AndroidListFragment.newInstance();
            IOSListFragment mIOSTechFragment = IOSListFragment.newInstance();
            DiDiDiFragment mDiDiDiFragment = DiDiDiFragment.newInstance("", "");

            List<Fragment> fragmentList = new ArrayList<>();
            fragmentList.add(mAndroidTechFragment);
            fragmentList.add(mIOSTechFragment);
            fragmentList.add(mDiDiDiFragment);

            viewPager.setOffscreenPageLimit(fragmentList.size() - 1);
            viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragmentList, getResources().getStringArray(R.array.main_tab_titles)));
            tabLayout.setupWithViewPager(viewPager);
        } else {
            // TODO: 2016/11/14
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SystemBarHelper.tintStatusBarForDrawer(this, mDrawerLayout, getResources().getColor(R.color.colorPrimary));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
    }

//    private void createFragment(int position) {
//        switch (position) {
//            case 0:
//                AndroidTechFragment mAndroidTechFragment = AndroidTechFragment.newInstance("", "");
//                break;
//            case 1:
//                IOSTechFragment mIOSTechFragment = IOSTechFragment.newInstance("", "");
//                break;
//            case 2:
//                DiDiDiFragment mDiDiDiFragment = DiDiDiFragment.newInstance("", "");
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void setStatusBar() {
        //do nothing
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                ActivityUtils.startActivity(this, SearchActivity.class);
                return true;
            case R.id.action_settings:
                ActivityUtils.startActivity(this, SettingActivity.class);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                break;
            default:
                break;
        }
    }
}



