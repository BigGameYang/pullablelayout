package com.yz.pullablelayout;

import android.os.Bundle;

import com.yz.pullablelayout.fragments.CustomRefreshFragment;
import com.yz.pullablelayout.fragments.HideToolBarFragment;
import com.yz.pullablelayout.fragments.ListFragment;
import com.yz.pullablelayout.fragments.NormalListFragment;
import com.yz.pullablelayout.fragments.OverScrollListFragment;
import com.yz.pullablelayout.fragments.InsideSwipeRefreshFragment;
import com.yz.pullablelayout.fragments.PagerFragment;
import com.yz.pullablelayout.fragments.OutsideSwipeRefreshFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YangZhi on 2017/4/7 2:14.
 */

public class DemoProvider {


    /**
     * 通过 Type 获取 Demo 列表
     * @param type
     * @return
     */
    public static List<Demo> getListDemoByType(int type){
        List<Demo> demos=null;
        switch (type){
            case DemoConstants.DEMO_MAIN:
                demos=getMainListDemo();
                break;
            case DemoConstants.DEMO_NORMAL_LIST:
                demos=getNormalListDemo();
                break;
            default:
                break;
        }
        return demos;
    }

    /**
     * 获取 Demo 主页列表
     * @return
     */
    public static List<Demo> getMainListDemo(){
        List<Demo> demos=new ArrayList<>();
        Demo demo=Demo.obtain("普通默认 Demo",NormalListFragment.class);
        demos.add(demo);
        demo=Demo.obtain("自定义过度下拉 Demo",OverScrollListFragment.class);
        demos.add(demo);
        demo=Demo.obtain("滑动改变 ToolBar 显示 Demo",false,HideToolBarFragment.class);
        demos.add(demo);
        demo=Demo.obtain("外部嵌套下拉刷新 DEMO",OutsideSwipeRefreshFragment.class);
        demos.add(demo);
        demo=Demo.obtain("内部嵌套下拉刷新 DEMO",InsideSwipeRefreshFragment.class);
        demos.add(demo);
        demo=Demo.obtain("自定义下拉刷新 DEMO",CustomRefreshFragment.class);
        demos.add(demo);
        demo=Demo.obtain("ViewPager DEMO",PagerFragment.class);
        demos.add(demo);
        return demos;
    }

    /**
     * 获取普通列表数据
     * @return
     */
    public static List<Demo> getNormalListDemo(){
        List<Demo> demos=new ArrayList<>();
        for (int i=1;i<=100;i++){
            demos.add(Demo.obtain("第"+i+"个Item","点击了第"+i+"个"));
        }
        return demos;
    }
}
