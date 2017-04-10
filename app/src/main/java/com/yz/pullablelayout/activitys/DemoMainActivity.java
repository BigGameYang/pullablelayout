package com.yz.pullablelayout.activitys;

import android.support.v4.app.Fragment;

import com.yz.pullablelayout.DemoConstants;
import com.yz.pullablelayout.fragments.ListFragment;

/**
 * Demo 主页面
 * Created by YangZhi on 2017/4/7 1:49.
 */

public class DemoMainActivity extends ContainerActivity{

    @Override
    protected Fragment onCreateFragment() {
        return ListFragment.newInstance(DemoConstants.DEMO_MAIN);
    }
}
