package com.yz.pullable;

import android.view.View;

/**
 * Created by YangZhi on 2017/4/3 2:11.
 */

public class SimpleTargetStateHelper implements TargetStateHelper{

    PullableLayout pullableLayout;

    View targetView;




    public SimpleTargetStateHelper(PullableLayout pullableLayout){
        this.pullableLayout = pullableLayout;
    }

    @Override
    public void setTargetView(View targetView) {
        this.targetView=targetView;
        if(this.targetView!=null){
            this.targetView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    @Override
    public View getTargetView() {
        return targetView;
    }




}
