package com.yz.pullable;

import android.view.View;

/**
 * 内部嵌套View的辅助接口
 * Created by YangZhi on 2017/4/3 2:10.
 */

public interface TargetStateHelper {

    void setTargetView(View targetView);

    View getTargetView();

}
