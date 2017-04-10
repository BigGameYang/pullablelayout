package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yz.pullable.PullableLayout;
import com.yz.pullablelayout.DisplayUtils;
import com.yz.pullablelayout.R;
import com.yz.pullablelayout.behaviors.ChangeToolBarBehavior;
import com.yz.pullablelayout.behaviors.PullBehaviorImpl;

/**
 * 滑动改变 ToolBar 显示页面
 * Created by YangZhi on 2017/4/10 12:14.
 */

public class HideToolBarFragment extends NormalListFragment{


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar=getToolbar();
        PullableLayout pullableLayout=getPullableLayoutViewHolder().getPullableLayout();
        View imageView=view.findViewById(R.id.ivImage);
        PullBehaviorImpl changeImageBehavior=new PullBehaviorImpl(imageView);
        pullableLayout.addBehavior(changeImageBehavior);


        int maxHeadScrollDistance=DisplayUtils.convertDIP2PX(view.getContext(),250)-toolbar.getLayoutParams().height;
        ChangeToolBarBehavior changeToolBarBehavior=new ChangeToolBarBehavior(toolbar, maxHeadScrollDistance);
        pullableLayout.addBehavior(changeToolBarBehavior);

        pullableLayout.getSetting().setMaxHeadScrollDistance(maxHeadScrollDistance);
    }

}
