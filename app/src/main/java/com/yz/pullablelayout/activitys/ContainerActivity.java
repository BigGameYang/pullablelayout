package com.yz.pullablelayout.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * Created by YangZhi on 2017/4/7 2:20.
 */

public class ContainerActivity extends FragmentActivity{

    static final String STR_PARAM_FRAGMENT_CLASS_NAME="param_fragment_class_name";

    public static void goPage(Context context,Class fragmentClazz){
        goPage(context,null,fragmentClazz);
    }

    public static void goPage(Context context,Bundle arguments,Class fragmentClazz){
        Intent intent=new Intent(context,ContainerActivity.class);
        intent.putExtra(STR_PARAM_FRAGMENT_CLASS_NAME,fragmentClazz.getName());
        if(arguments!=null)
            intent.putExtras(arguments);
        context.startActivity(intent);
    }


    private Fragment fragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment=onCreateFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(Window.ID_ANDROID_CONTENT,fragment)
                .commitNowAllowingStateLoss();

    }


    protected Fragment onCreateFragment(){
        Fragment fragment=null;
        String fragmentName=getIntent().getStringExtra(STR_PARAM_FRAGMENT_CLASS_NAME);
        try {
            fragment=(Fragment) Class.forName(fragmentName).newInstance();
            fragment.setArguments(getIntent().getExtras());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return fragment;
        }
    }


}
