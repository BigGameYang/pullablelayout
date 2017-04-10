package com.yz.pullablelayout;

import android.os.Bundle;

/**
 * Created by YangZhi on 2017/4/7 1:59.
 */

public class Demo {
    private String name;

    private Class fragmentClass;

    private Bundle arguments;

    private String onClickText;

    private boolean isStartOther;

    public static Demo obtain(String name,String onClickText){
        Demo demo=new Demo();
        demo.name=name;
        demo.onClickText=onClickText;
        demo.isStartOther=false;
        return demo;
    }


    public static Demo obtain(String name,Class fragmentClass){
        return obtain(name,true,fragmentClass);
    }

    public static Demo obtain(String name,boolean isContentMarginTop,Class fragmentClass){
        Bundle bundle=new Bundle();
        bundle.putInt(DemoConstants.STR_PARAM_LIST_TYPE,DemoConstants.DEMO_NORMAL_LIST);
        bundle.putBoolean(DemoConstants.STR_PARAM_CONTENT_MARGIN_TOP,isContentMarginTop);
        return obtain(name,bundle,fragmentClass);
    }

    public static Demo obtain(String name,Bundle arguments,Class fragmentClass){
        Demo demo=new Demo();
        demo.name=name;
        if(arguments!=null) {
            demo.arguments = new Bundle(arguments);
        }
        demo.fragmentClass=fragmentClass;
        demo.isStartOther=true;
        return demo;
    }

    public String getName() {
        return name;
    }

    public Class getFragmentClass() {
        return fragmentClass;
    }

    public boolean isStartOther() {
        return isStartOther;
    }

    public String getOnClickText() {
        return onClickText;
    }

    public Bundle getArguments() {
        return arguments;
    }
}
