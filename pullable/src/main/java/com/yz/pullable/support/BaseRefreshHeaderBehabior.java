package com.yz.pullable.support;

import android.animation.ValueAnimator;
import android.view.View;

import com.yz.pullable.PullableLayout;

/**
 * Created by YangZhi on 2017/4/9 20:23.
 */

public abstract class BaseRefreshHeaderBehabior implements RefreshHeaderBehavior{

    private int currentState=-1;

    private int overPullHeight;

    private ValueAnimator scrollAnimator;

    private PullableLayout pullableLayout;

    private RefreshListener refreshListener;

    public BaseRefreshHeaderBehabior(PullableLayout pullableLayout){
        this.pullableLayout=pullableLayout;
        pullableLayout.getSetting().setOverScrollEnable(true);
        pullableLayout.getSetting().setCustomOverScroll(true);
    }

    @Override
    public void onPullEnd(PullableLayout pullableLayout) {
        switch (getCurrentState()){
            case STATE_CAN_REFRESH:
                changeState(STATE_REFRESHING);
                changeOverScrollEnable(false);
                break;
            default:
                break;
        }
    }

    @Override
    public final void onOverPullDown(PullableLayout pullableLayout, int overPullHeight) {
        if (getCurrentState() == STATE_REFRESHING) {
            if(overPullHeight<getCanRefreshHeight())
                overPullHeight=getCanRefreshHeight();
        }else if(overPullHeight>getCanRefreshHeight()){
            changeState(STATE_CAN_REFRESH);
        }else {
            changeState(STATE_NORMAL);
        }
        this.overPullHeight=overPullHeight;
        changeHeaderShowHeight(overPullHeight);
    }


    @Override
    public void changeState(int state) {
        if(state==getCurrentState())
            return;
        if(getCurrentState()==STATE_REFRESHING&&state!=STATE_NORMAL)
            return;
        this.currentState=state;
        switch (state){
            case STATE_NORMAL:
                onNormal();
                break;
            case STATE_CAN_REFRESH:
                onCanRefresh();
                break;
            case STATE_REFRESHING:
                onRefreshing();
                if(refreshListener!=null){
                    refreshListener.onRefresh();
                }
                changeOverScrollEnable(false);
                break;
            default:
                break;
        }

    }

    @Override
    public final int getCurrentState() {
        return currentState;
    }

    public final int getOverPullHeight(){
        return overPullHeight;
    }

    @Override
    public void scrollToHeight(int finalHeight) {
        cancelAnim();
        final int currentOverPullHeight=getOverPullHeight();
        scrollAnimator=ValueAnimator.ofInt(getOverPullHeight(),finalHeight);
        scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int lastValue=currentOverPullHeight;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue=(int)animation.getAnimatedValue();
                if(lastValue==currentValue){
                    return;
                }
                changeHeaderShowHeight(currentValue);
                lastValue=currentValue;
            }
        });
        scrollAnimator.start();
    }


    public void cancelAnim(){
        if(scrollAnimator!=null&&scrollAnimator.isRunning()){
            scrollAnimator.cancel();
        }
    }

    private void changeOverScrollEnable(boolean isEnable){
        if(pullableLayout==null)
            return;
        pullableLayout.getSetting().setOverScrollEnable(isEnable);
    }

    public void stopRefresh(){
        changeState(STATE_NORMAL);
        scrollToHeight(0);
        changeOverScrollEnable(true);
    }

    @Override
    public void onPull(PullableLayout pullableLayout, int transHeight) {

    }

    @Override
    public void startFling(View target, int velocityX, int velocityY) {

    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    protected abstract void onRefreshing();

    protected abstract void onCanRefresh();

    protected abstract void onNormal();

    public interface RefreshListener{
        void onRefresh();
    }
}
