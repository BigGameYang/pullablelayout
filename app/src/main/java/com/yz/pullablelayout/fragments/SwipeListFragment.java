package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yz.pullablelayout.DemoConstants;
import com.yz.pullablelayout.R;
import com.yz.pullablelayout.viewholder.SwipeRefreshLayoutViewHolder;

/**
 * 普通的下拉刷新滑动页面
 * Created by YangZhi on 2017/4/4 6:01.
 */

public class SwipeListFragment extends ListFragment{

    public static SwipeListFragment newInstance(){
        return new SwipeListFragment();
    }

    private SwipeRefreshLayoutViewHolder refreshLayoutViewHolder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayoutViewHolder=new SwipeRefreshLayoutViewHolder(view);
    }

    protected int getDemoType() {
        return DemoConstants.DEMO_NORMAL_LIST;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_refresh_list;
    }

}
