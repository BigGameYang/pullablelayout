package com.yz.pullablelayout.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yz.pullablelayout.DemoConstants;
import com.yz.pullablelayout.R;
import com.yz.pullablelayout.fragments.BaseFragment;

/**
 * Created by YangZhi on 2017/4/7 2:20.
 */

public class ContainerActivity extends AppCompatActivity{

    static final String STR_PARAM_FRAGMENT_CLASS_NAME="param_fragment_class_name";

    static final String STR_PARAM_TITLE_NAME="param_title_name";

    public static void goPage(Context context,String title,Class fragmentClazz){
        goPage(context,title,null,fragmentClazz);
    }

    public static void goPage(Context context,String title,Bundle arguments,Class fragmentClazz){
        Intent intent=new Intent(context,ContainerActivity.class);
        intent.putExtra(STR_PARAM_FRAGMENT_CLASS_NAME,fragmentClazz.getName());
        intent.putExtra(STR_PARAM_TITLE_NAME,title);
        if(arguments!=null)
            intent.putExtras(arguments);
        context.startActivity(intent);
    }


    private Fragment fragment;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra(STR_PARAM_TITLE_NAME));
        setSupportActionBar(toolbar);
        boolean isMarginTop=getIntent().getBooleanExtra(DemoConstants.STR_PARAM_CONTENT_MARGIN_TOP,true);
        if(!isMarginTop){
            View content=findViewById(R.id.content);
            ViewGroup.MarginLayoutParams layoutParams=(ViewGroup.MarginLayoutParams) content.getLayoutParams();
            layoutParams.topMargin=0;
        }
        fragment=onCreateFragment();
        if(fragment instanceof BaseFragment){
            BaseFragment baseFragment=(BaseFragment) fragment;
            baseFragment.setToolbar(toolbar);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,fragment)
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
