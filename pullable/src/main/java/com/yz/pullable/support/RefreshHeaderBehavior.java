package com.yz.pullable.support;

import com.yz.pullable.PullBehavior;

/**
 * 下拉刷新的行为接口
 * Created by YangZhi on 2017/4/9 20:21.
 */

public interface RefreshHeaderBehavior extends PullBehavior{

    /**
     * 普通状态(默认状态)
     */
    int STATE_NORMAL=0;

    /**
     * 可以刷新的状态
     */
    int STATE_CAN_REFRESH=1;

    /**
     * 刷新中的状态
     */
    int STATE_REFRESHING=2;

    /**
     * 改变下拉刷新的状态
     * @param state
     */
    void changeState(int state);

    /**
     * 改变头部显示的高度
     * @param headerShowHeight
     */
    void changeHeaderShowHeight(int headerShowHeight);

    /**
     * 获取当前下拉刷新的状态
     * @return
     */
    int getCurrentState();

    /**
     * 滑动到指定高度
     * @param finalHeight
     */
    void scrollToHeight(int finalHeight);

    /**
     * 获取能松开刷新的高度
     * @return
     */
    int getCanRefreshHeight();
}
