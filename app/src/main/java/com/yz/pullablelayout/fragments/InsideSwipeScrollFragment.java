package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yz.pullablelayout.R;
import com.yz.pullablelayout.viewholder.PullableLayoutViewHolder;

/**
 * Created by YangZhi on 2017/4/7 3:00.
 */

public class InsideSwipeScrollFragment extends SwipeListFragment{

    PullableLayoutViewHolder pullableLayoutViewHolder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullableLayoutViewHolder=new PullableLayoutViewHolder(view);
        pullableLayoutViewHolder.initNormal();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_swiperefresh_nestedscroll;
    }
}
