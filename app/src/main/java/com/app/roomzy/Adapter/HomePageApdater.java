package com.app.roomzy.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.roomzy.Fragments.HomeFragment;
import com.app.roomzy.Fragments.ProfileFragment;
import com.app.roomzy.Fragments.ProposeFragment;
import com.app.roomzy.Fragments.SearchFragment;

public class HomePageApdater extends FragmentPagerAdapter {

    public HomePageApdater(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {

            case 0:
                return new HomeFragment();
            case 1:
                return new ProposeFragment();
            case 2:
                return new SearchFragment();
            case 3:
                return new ProfileFragment();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
