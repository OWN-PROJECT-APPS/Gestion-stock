package com.bank.gestionstock.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;

public class AdapterViewPager extends FragmentPagerAdapter {
    public ArrayList<Fragment> fragments;
    public ArrayList<String> fragmentTitle ;

    public AdapterViewPager(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> fragments, ArrayList<String> fragmentTitle) {
        super(fm, behavior);
        this.fragments = fragments;
        this.fragmentTitle = fragmentTitle;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public String getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
