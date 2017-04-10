package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yz.pullablelayout.R;
import com.yz.pullablelayout.viewholder.PullableLayoutViewHolder;

/**
 * 自定义过度下拉页面
 * Created by YangZhi on 2017/4/7 3:11.
 */

public class OverScrollListFragment extends NormalListFragment{


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPullableLayoutViewHolder().initNormal();
    }


    public PullableLayoutViewHolder getPullableLayoutViewHolder() {
        return pullableLayoutViewHolder;
    }

}
