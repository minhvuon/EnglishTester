package com.example.englishtester.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.englishtester.FragmentQuestion;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragmentQuestion = new FragmentQuestion();
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", position);
        fragmentQuestion.setArguments(bundle);
        return fragmentQuestion;
    }

    @Override
    public int getCount() {
        return 10;
    }


}
