package com.yz.pullable.support;

import com.yz.pullable.PullBehavior;

/**
 * Created by YangZhi on 2017/4/9 20:21.
 */

public interface RefreshHeaderBehavior extends PullBehavior{

    int STATE_NORMAL=0;

    int STATE_CAN_REFRESH=1;

    int STATE_REFRESHING=2;

    void changeState(int state);

    void changeHeaderShowHeight(int headerShowHeight);

    int getCurrentState();

    void scrollToHeight(int finalHeight);

    int getCanRefreshHeight();
}
