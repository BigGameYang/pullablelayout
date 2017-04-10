package com.yz.pullablelayout.viewholder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yz.pullable.PullSetting;
import com.yz.pullable.PullableLayout;
import com.yz.pullablelayout.R;
import com.yz.pullablelayout.ViewUtils;
import com.yz.pullablelayout.behaviors.FlingBehaviorImpl;
import com.yz.pullablelayout.behaviors.PullBehaviorImpl;

/**
 * Created by YangZhi on 2017/4/9 3:14.
 */

public class PullableLayoutViewHolder {

    View rootView;

    PullableLayout pullableLayout;

    public PullableLayoutViewHolder(View rootView){
        this.rootView=rootView;
        pullableLayout = ViewUtils.findViewById(rootView, R.id.pullableLayout);
        pullableLayout.addBehavior(new FlingBehaviorImpl());
        pullableLayout.getSetting().setDebug(true);
    }

    public void initNormal(){
        pullableLayout.addBehavior(new PullBehaviorImpl(rootView.findViewById(R.id.ivImage)));
        PullSetting setting= pullableLayout.getSetting();
        setting.setOverScrollEnable(true);
    }

    public void initNestedSwipeRefresh(final RecyclerView recyclerView){
        getPullableLayout().addBehavior(new PullBehaviorImpl(rootView.findViewById(R.id.ivImage)));
        PullSetting setting=getPullableLayout().getSetting();
//        setting.setOverScrollForChild(true);
        setting.setOverScrollEnable(false);
        getPullableLayout().setChildScrollUpCallback(new PullableLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(PullableLayout parent, @Nullable View child) {
                return recyclerView.canScrollVertically(-1);
            }
        });
    }

    public PullableLayout getPullableLayout() {
        return pullableLayout;
    }
}
