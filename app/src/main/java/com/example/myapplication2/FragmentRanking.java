package com.example.myapplication2;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class FragmentRanking extends Fragment{
    private TabLayout tabLayout;
    private ViewPager viewpager;
    public boolean isFirstInit = true;
    ArrayList fragmentList = new ArrayList<Fragment>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
        tabLayout = rootView.findViewById(R.id.mytab);
        viewpager = rootView.findViewById(R.id.Ranking_viewPager);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // fragment中嵌套fragment, Manager需要用(getChildFragmentManager())
        MPagerAdapter mPagerAdapter = new MPagerAdapter(getChildFragmentManager());
        if(isFirstInit) {
            initFragment();
            isFirstInit = false;
        }
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(mPagerAdapter);
        tabLayout.getTabAt(0).setText("类型一");
        tabLayout.getTabAt(1).setText("类型二");
        tabLayout.getTabAt(2).setText("类型三");
        tabLayout.getTabAt(3).setText("类型四");
        tabLayout.getTabAt(4).setText("类型五");
        tabLayout.getTabAt(5).setText("类型六");
        tabLayout.getTabAt(6).setText("类型七");
    }

    private void initFragment() {
        fragmentList.add(new Rankingfrag_1());
        fragmentList.add(new Rankingfrag_2());
        fragmentList.add(new Rankingfrag_3());
        fragmentList.add(new Rankingfrag_4());
        fragmentList.add(new Rankingfrag_5());
        fragmentList.add(new Rankingfrag_6());
        fragmentList.add(new Rankingfrag_7());
    }


    class MPagerAdapter extends FragmentStatePagerAdapter {

        public MPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }
}