package com.yz.pullablelayout.viewholder;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.yz.pullablelayout.R;
import com.yz.pullablelayout.ViewUtils;

/**
 * Created by YangZhi on 2017/4/9 3:16.
 */

public class SwipeRefreshLayoutViewHolder {

    SwipeRefreshLayout refreshLayout;


    public SwipeRefreshLayoutViewHolder(View rootView){
        refreshLayout = ViewUtils.findViewById(rootView, R.id.refrshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            };
            @Override
            public void onRefresh() {
                refreshLayout.removeCallbacks(runnable);
                refreshLayout.postDelayed(runnable,2000);
            }
        });
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }
}
