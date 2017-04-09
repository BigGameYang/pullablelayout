package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yz.pullablelayout.DisplayUtils;
import com.yz.pullablelayout.R;
import com.yz.pullablelayout.viewholder.PullableLayoutViewHolder;

/**
 * Created by YangZhi on 2017/4/7 18:18.
 */

public class OutsideSwipeFragment extends SwipeListFragment{

    PullableLayoutViewHolder pullableLayoutViewHolder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView=getRecyclerView();
        recyclerView.setPadding(0, DisplayUtils.convertDIP2PX(getContext(),250),0,0);

        pullableLayoutViewHolder=new PullableLayoutViewHolder(view);
        pullableLayoutViewHolder.initNestedSwipeRefresh(recyclerView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nested_swipe_layout;
    }
}
