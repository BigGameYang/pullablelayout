package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yz.pullablelayout.R;
import com.yz.pullablelayout.viewholder.PullableLayoutViewHolder;

/**
 * Created by YangZhi on 2017/4/7 3:11.
 */

public class NormalListFragment extends ListFragment{

    PullableLayoutViewHolder pullableLayoutViewHolder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullableLayoutViewHolder=new PullableLayoutViewHolder(view);
        pullableLayoutViewHolder.initNormal();
    }


    public PullableLayoutViewHolder getPullableLayoutViewHolder() {
        return pullableLayoutViewHolder;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_nestedscroll_list;
    }
}
