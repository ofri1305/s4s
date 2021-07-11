package com.example.try2.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.try2.degreePageFragments.FragmentChats;
import com.example.try2.degreePageFragments.FragmentMaterial;
import com.example.try2.degreePageFragments.FragmentMemes;
import com.example.try2.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1,R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;
    private String nameOfCourse;

    public SectionsPagerAdapter(Context context, FragmentManager fm,String nameOfCourse){
        super(fm);
        mContext = context;
        this.nameOfCourse=nameOfCourse;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment= null;
        Bundle bundle = new Bundle();
        bundle.putString("nameOfCourse", nameOfCourse);
// set Fragmentclass Arguments

        switch (position){
            case 0:
                fragment = new FragmentChats();
                fragment.setArguments(bundle);

                break;
            case 1:
                fragment = new FragmentMaterial();
                fragment.setArguments(bundle);

                break;
            case 2:
                fragment = new FragmentMemes();
                fragment.setArguments(bundle);

                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}
