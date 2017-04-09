package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yz.pullablelayout.R;

/**
 * Created by YangZhi on 2017/4/4 6:08.
 */

public class BaseFragment extends Fragment{

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(container.getContext()).inflate(getLayoutId(),container,false);
        rootView=view;
        return view;
    }

    public View getRootView() {
        return rootView;
    }

    protected int getLayoutId(){
        return R.layout.layout_recyclerview;
    }

    protected <R> R findViewById(View view,@IdRes int id){
        return (R)view.findViewById(id);
    }
}
