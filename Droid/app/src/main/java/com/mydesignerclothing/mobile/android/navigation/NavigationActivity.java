package com.mydesignerclothing.mobile.android.navigation;


import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mydesignerclothing.mobile.android.BaseFragment;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.databinding.ActivityNavigationBinding;
import com.mydesignerclothing.mobile.android.navigation.adapter.ViewPagerAdapter;
import com.mydesignerclothing.mobile.android.uikit.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class NavigationActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemReselectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private LinkedHashMap<Integer, Integer> integerLinkedHashMap = new LinkedHashMap<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private Stack<Integer> backStack = new Stack<>();
    private ActivityNavigationBinding activityNavigationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNavigationBinding = DataBindingUtil.setContentView(this, R.layout.activity_navigation);

        fragmentList.add(BaseFragment.newInstance(R.layout.content_home_base, R.id.toolbar_home, R.id.nav_host_home));
        fragmentList.add(BaseFragment.newInstance(R.layout.content_shop_base, R.id.toolbar_shop, R.id.nav_host_shop));
        fragmentList.add(BaseFragment.newInstance(R.layout.content_create_base, R.id.toolbar_create, R.id.nav_host_create));
        fragmentList.add(BaseFragment.newInstance(R.layout.content_basket_base, R.id.toolbar_basket, R.id.nav_host_basket));
        fragmentList.add(BaseFragment.newInstance(R.layout.content_about_base, R.id.toolbar_about, R.id.nav_host_about));

        integerLinkedHashMap.put(R.id.home, 0);
        integerLinkedHashMap.put(R.id.shopping, 1);
        integerLinkedHashMap.put(R.id.create, 2);
        integerLinkedHashMap.put(R.id.basket, 3);
        integerLinkedHashMap.put(R.id.about, 4);

        activityNavigationBinding.mainPager.addOnPageChangeListener(this);
        activityNavigationBinding.mainPager.setAdapter(new MyDesignerClothingPagerAdapter(getSupportFragmentManager()));
        activityNavigationBinding.mainPager.setOffscreenPageLimit(1);

        activityNavigationBinding.bottomNav.setOnNavigationItemSelectedListener(this);
        activityNavigationBinding.bottomNav.setOnNavigationItemReselectedListener(this);

        // initialize backStack with elements
        if (backStack.empty()) {
            backStack.push(0);
        }
    }
    @Override
    public void onBackPressed() {
        BaseFragment fragment = (BaseFragment) fragmentList.get(activityNavigationBinding.mainPager.getCurrentItem());
        boolean hadNestedFragment = fragment.onBackPressed();

        if (!hadNestedFragment) {
            if (backStack.size() > 1) {
                // remove current position from stack
                backStack.pop();
                // set the next item in stack as current
                activityNavigationBinding.mainPager.setCurrentItem(backStack.peek());

            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int itemId = 0;
        for (Map.Entry<Integer, Integer> entry : integerLinkedHashMap.entrySet()) {
            if (position == entry.getValue()) {
                itemId = entry.getKey();
                break;
            } else {
                itemId = R.id.home;
            }
        }
        if (activityNavigationBinding.bottomNav.getSelectedItemId() != itemId) {
            activityNavigationBinding.bottomNav.setSelectedItemId(itemId);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        int position = integerLinkedHashMap.get(item.getItemId());
        BaseFragment fragment = (BaseFragment) fragmentList.get(position);
        fragment.popToRoot();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int position = integerLinkedHashMap.get(item.getItemId());
        if (activityNavigationBinding.mainPager.getCurrentItem() != position) {
            setItem(position);
        }
        return true;
    }

    private void setItem(int position) {
        activityNavigationBinding.mainPager.setCurrentItem(position);
        backStack.push(position);
    }

    private class MyDesignerClothingPagerAdapter extends ViewPagerAdapter {
        MyDesignerClothingPagerAdapter(FragmentManager fm) {
            super(fm, 0);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
