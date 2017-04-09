package com.yz.pullablelayout;

import android.os.Bundle;

import com.yz.pullablelayout.fragments.CustomRefreshFragment;
import com.yz.pullablelayout.fragments.ListFragment;
import com.yz.pullablelayout.fragments.NormalListFragment;
import com.yz.pullablelayout.fragments.OutsideSwipeFragment;
import com.yz.pullablelayout.fragments.PagerFragment;
import com.yz.pullablelayout.fragments.InsideSwipeScrollFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YangZhi on 2017/4/7 2:14.
 */

public class DemoProvider {



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

    public static List<Demo> getMainListDemo(){
        List<Demo> demos=new ArrayList<>();
        Bundle bundle=new Bundle();
        bundle.putInt(ListFragment.STR_PARAM_LIST_TYPE,DemoConstants.DEMO_NORMAL_LIST);
        Demo demo=Demo.obtain("头部和列表滑动 Demo",bundle,NormalListFragment.class);
        demos.add(demo);
        demo=Demo.obtain("外部嵌套下拉刷新 DEMO",bundle,InsideSwipeScrollFragment.class);
        demos.add(demo);
        demo=Demo.obtain("内部嵌套下拉刷新 DEMO",bundle,OutsideSwipeFragment.class);
        demos.add(demo);
        demo=Demo.obtain("自定义下拉刷新 DEMO",bundle,CustomRefreshFragment.class);
        demos.add(demo);
        demo=Demo.obtain("ViewPager DEMO",bundle,PagerFragment.class);
        demos.add(demo);
        return demos;
    }

    public static List<Demo> getNormalListDemo(){
        List<Demo> demos=new ArrayList<>();
        for (int i=1;i<=100;i++){
            demos.add(Demo.obtain("第"+i+"个Item","点击了第"+i+"个"));
        }
        return demos;
    }
}
