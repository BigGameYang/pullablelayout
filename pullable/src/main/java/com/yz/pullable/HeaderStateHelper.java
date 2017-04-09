package com.yz.pullable;

import android.view.View;

/**
 * Created by YangZhi on 2017/4/3 1:33.
 */

public interface HeaderStateHelper {

    boolean isHeadOnShow();

    boolean isHeadOnOverPull();

    boolean isHeadShowFull();

    void setHeadView(View view);

    View getHeadView();

    void setHeadHeight(int headHeight);

    int getHeadHeight();

}
