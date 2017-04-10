package com.yz.pullable;

import android.view.View;

/**
 * Created by yangzhi on 17/1/11.
 */

public interface PullBehavior {

    /**
     * 当滑动结束回调该方法 （ 触摸事件 UP 时 会调用 ）
     * @param pullableLayout
     */
    void onPullEnd(PullableLayout pullableLayout);

    /**
     * 当过度下拉时回调该方法
     * @param pullableLayout
     * @param overPullHeight 当前已过度下拉的高度 , 最小值为 0
     */
    void onOverPullDown(PullableLayout pullableLayout, int overPullHeight);

    /**
     * 当头部还在显示中，上滑的时候会回调该方法
     * @param pullableLayout
     * @param transHeight transHeight 已经滑动的距离
     */
    void onPull(PullableLayout pullableLayout, int transHeight);

    /**
     * 当有惯性滑动时,如果头部已经滑完，仍有惯性速度会回调该方法,如果需要惯性滑动给子 View , 需要实现该方法
     * @param target 嵌套的可滑动子 View
     * @param velocityX 横向剩余速度
     * @param velocityY 竖向剩余速度
     */
    void startFling(View target,int velocityX,int velocityY);

}
