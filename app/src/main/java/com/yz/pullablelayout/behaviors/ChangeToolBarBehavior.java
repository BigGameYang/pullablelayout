package com.yz.pullablelayout.behaviors;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yz.pullable.PullBehavior;
import com.yz.pullable.PullableLayout;

/**
 * Created by YangZhi on 2017/4/10 12:16.
 */

public class ChangeToolBarBehavior implements PullBehavior{

    private int maxChangeHeight;

    private Toolbar toolbar;


    public ChangeToolBarBehavior(Toolbar toolbar,int maxChangeHeight){
        this.maxChangeHeight=maxChangeHeight;
        this.toolbar=toolbar;
        this.toolbar.setAlpha(0f);
    }

    @Override
    public void onPull(PullableLayout pullableLayout, int transHeight) {
        float rate=1f*transHeight/maxChangeHeight;
        toolbar.setAlpha(rate);
    }


    @Override
    public void onPullEnd(PullableLayout pullableLayout) {

    }

    @Override
    public void onOverPullDown(PullableLayout pullableLayout, int overPullHeight) {

    }

    @Override
    public void startFling(View target, int velocityX, int velocityY) {

    }
}
