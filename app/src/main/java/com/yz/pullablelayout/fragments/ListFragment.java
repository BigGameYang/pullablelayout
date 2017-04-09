package com.yz.pullablelayout.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yz.pullablelayout.DemoConstants;
import com.yz.pullablelayout.viewholder.ListViewHolder;

/**
 * Created by YangZhi on 2017/4/4 5:55.
 */

public class ListFragment extends BaseFragment {

    public static final String STR_PARAM_LIST_TYPE="param_list_type";

    public static ListFragment newInstance(int type){
        ListFragment listFragment=new ListFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(STR_PARAM_LIST_TYPE,type);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    private ListViewHolder listViewHolder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewHolder=new ListViewHolder(view,getDemoType());
    }

    protected int getDemoType(){
        return getArguments().getInt(STR_PARAM_LIST_TYPE, DemoConstants.DEMO_NONE);
    }

    protected RecyclerView getRecyclerView(){
        return listViewHolder.getRecyclerView();
    }


}
