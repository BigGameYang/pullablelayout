package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yz.pullablelayout.R;
import com.yz.pullablelayout.viewholder.PullableLayoutViewHolder;

/**
 * 普通默认 Demo 页面
 * Created by YangZhi on 2017/4/10 11:13.
 */

public class NormalListFragment extends ListFragment{


    PullableLayoutViewHolder pullableLayoutViewHolder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullableLayoutViewHolder=new PullableLayoutViewHolder(view);
    }


    public PullableLayoutViewHolder getPullableLayoutViewHolder() {
        return pullableLayoutViewHolder;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.layout_image_header_list;
    }
}
