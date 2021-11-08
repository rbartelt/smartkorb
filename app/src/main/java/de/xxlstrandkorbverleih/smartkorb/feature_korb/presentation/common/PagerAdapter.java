package de.xxlstrandkorbverleih.smartkorb.feature_korb.presentation.common;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public final List<Fragment> mFragmentList = new ArrayList<>();
    public final List<String> mFragmentTitelList = new ArrayList<>();
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String titel) {
        mFragmentList.add(fragment);
        mFragmentTitelList.add(titel);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
