package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yz.pullable.PullableLayout;
import com.yz.pullablelayout.DemoConstants;
import com.yz.pullablelayout.behaviors.PullBehaviorImpl;
import com.yz.pullablelayout.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YangZhi on 2017/4/4 6:07.
 */

public class PagerFragment extends BaseFragment {

    public static PagerFragment newInstance(){
        return new PagerFragment();
    }

    PullableLayout pullableLayout;

    ViewGroup tab;

    ViewPager viewPager;

    View lastSelectdTab;

    Map<Integer,ListFragment> fragments=new HashMap<>();

    View ivImage;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager=findViewById(view, R.id.viewpager);
        pullableLayout =findViewById(view,R.id.pullableLayout);
        ivImage=findViewById(view,R.id.ivImage);
        tab=findViewById(view,R.id.tab);
        viewPager=findViewById(view,R.id.viewpager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {


            @Override
            public Fragment getItem(int position) {
                ListFragment fragment=null;
                switch (position){
                    case 0:
                        fragment= ListFragment.newInstance(DemoConstants.DEMO_NORMAL_LIST);
                        break;
                    case 1:
                        fragment= SwipeListFragment.newInstance();
                        break;
                    case 2:
                        fragment= ListFragment.newInstance(DemoConstants.DEMO_NORMAL_LIST);
                        break;
                }
                fragments.put(position,fragment);
                return fragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                View view=tab.getChildAt(position);
                view.setSelected(true);
                lastSelectdTab.setSelected(false);
                lastSelectdTab=view;
                if(position==1){
                    pullableLayout.getSetting().setOverScrollEnable(false);
                }else {
                    pullableLayout.getSetting().setOverScrollEnable(true);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        lastSelectdTab=tab.getChildAt(0);
        lastSelectdTab.setSelected(true);
//        pullableLayoutNest.setOverScrollForChild(true);
        pullableLayout.addBehavior(new PullBehaviorImpl(ivImage));
        pullableLayout.setChildScrollUpCallback(new PullableLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(PullableLayout parent, @Nullable View child) {
                RecyclerView recyclerView=getCurrentRecyclerView();
                if(recyclerView!=null){
                    return ViewCompat.canScrollVertically(recyclerView, -1);
                }
                return false;
            }
        });

        setUpTabs();
    }

    private void setUpTabs(){
        for (int i=0;i<=tab.getChildCount()-1;i++){
            final int index=i;
            tab.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
        }
    }

    private ListFragment getCurrentListFragment(){
        ListFragment listFragment=fragments.get(viewPager.getCurrentItem());
        return listFragment;
    }

    private RecyclerView getCurrentRecyclerView(){
        ListFragment listFragment=getCurrentListFragment();
        if(listFragment!=null)
            return listFragment.getRecyclerView();
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_viewpager;
    }
}
