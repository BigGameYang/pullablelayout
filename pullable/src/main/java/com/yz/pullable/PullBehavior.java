package com.yz.pullable;

import android.view.View;

/**
 * Created by yangzhi on 17/1/11.
 */

public interface PullBehavior {

    void onPullEnd(PullableLayout pullableLayout);

    void onOverPullDown(PullableLayout pullableLayout, int overPullHeight);

    void onPull(PullableLayout pullableLayout, int transHeight);

    void startFling(View target,int velocityX,int velocityY);

}
