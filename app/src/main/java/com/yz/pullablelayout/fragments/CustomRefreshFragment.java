package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yz.pullable.PullSetting;
import com.yz.pullablelayout.DisplayUtils;
import com.yz.pullablelayout.R;
import com.yz.pullablelayout.behaviors.CustomRefreshBehaviorImpl;

/**
 * Created by YangZhi on 2017/4/9 4:21.
 */

public class CustomRefreshFragment extends NormalListFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View customRefreshHeader=view.findViewById(R.id.layout_custom_refresh_header);
        CustomRefreshBehaviorImpl behavior=new CustomRefreshBehaviorImpl(getPullableLayoutViewHolder().getPullableLayout(),customRefreshHeader);
        getPullableLayoutViewHolder().getPullableLayout().clearBehavior();
        getPullableLayoutViewHolder().getPullableLayout().addBehavior(behavior);
        PullSetting setting=getPullableLayoutViewHolder().getPullableLayout().getSetting();
        setting.setOverScrollRate(0.3f);
        setting.setOverScrollDoublingHeight(DisplayUtils.convertDIP2PX(view.getContext(),100));
//        setting.setCustomOverScroll(true);
//        setting.setAutoEndOverScroll(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_custom_refresh_layout;
    }
}
