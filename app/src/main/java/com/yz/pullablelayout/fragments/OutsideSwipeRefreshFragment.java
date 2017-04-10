package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yz.pullablelayout.R;
import com.yz.pullablelayout.viewholder.PullableLayoutViewHolder;

/**
 * 外部嵌套 SwipeRefreshLayout 页面
 * Created by YangZhi on 2017/4/7 3:00.
 */

public class OutsideSwipeRefreshFragment extends SwipeListFragment{

    PullableLayoutViewHolder pullableLayoutViewHolder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullableLayoutViewHolder=new PullableLayoutViewHolder(view);
        pullableLayoutViewHolder.initNormal();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_outside_swipe_refresh;
    }
}
