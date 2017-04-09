package com.yz.pullable;

import android.view.View;

/**
 * Created by YangZhi on 2017/4/3 2:00.
 */

public class SimpleHeaderStateHelper implements HeaderStateHelper{

    PullableLayout pullableLayout;

    PullSetting setting;

    Record record;

    View headView;

    int headHeight;

    public SimpleHeaderStateHelper(PullableLayout pullableLayout, PullSetting setting, Record record){
        this.pullableLayout = pullableLayout;
        this.setting=setting;
        this.record=record;
    }

    @Override
    public void setHeadView(View headView) {
        this.headView = headView;
    }

    @Override
    public View getHeadView() {
        return headView;
    }

    @Override
    public boolean isHeadOnShow() {
        return getScrollY()<getHeadHeight();
    }

    @Override
    public boolean isHeadOnOverPull() {
        return getSetting().isCustomOverScroll()?getRecord().getOverScrollHeight()>0:getScrollY()<0;
    }

    @Override
    public boolean isHeadShowFull() {
        return getScrollY()<=0;
    }

    @Override
    public void setHeadHeight(int headHeight) {
        this.headHeight=headHeight;
    }

    @Override
    public int getHeadHeight() {
        return headHeight;
    }

    public int getScrollY(){
        return pullableLayout.getScrollY();
    }

    public Record getRecord() {
        return record;
    }

    public PullSetting getSetting() {
        return setting;
    }
}
