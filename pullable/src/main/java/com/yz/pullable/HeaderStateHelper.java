package com.yz.pullable;

import android.view.View;

/**
 * 头部状态辅助接口
 * Created by YangZhi on 2017/4/3 1:33.
 */

public interface HeaderStateHelper {

    /**
     * 判断头部是否还在显示中
     * @return
     */
    boolean isHeadOnShow();

    /**
     * 判断头部是否处于过度下拉状态
     * @return
     */
    boolean isHeadOnOverPull();

    /**
     * 判断头部是否全部显示
     * @return
     */
    boolean isHeadShowFull();

    /**
     * 设置头部View
     * @param view
     */
    void setHeadView(View view);

    /**
     * 获取头部View
     * @return
     */
    View getHeadView();

    /**
     * 设置头部测量后的高度
     * @param headHeight
     */
    void setHeadHeight(int headHeight);

    /**
     * 拿到头部测量后的高度
     * @return
     */
    int getHeadHeight();

}
