package com.yz.pullablelayout.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yz.pullablelayout.DemoAdapter;
import com.yz.pullablelayout.DemoProvider;
import com.yz.pullablelayout.R;
import com.yz.pullablelayout.ViewUtils;

/**
 * Created by YangZhi on 2017/4/9 3:11.
 */

public class ListViewHolder {

    private RecyclerView recyclerView;

    private DemoAdapter demoAdapter;

    public ListViewHolder(View rootView,int type){
        recyclerView = ViewUtils.findViewById(rootView, R.id.rvList);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.VERTICAL,false));
        DemoAdapter demoAdapter=new DemoAdapter();
        recyclerView.setAdapter(demoAdapter);
        demoAdapter.setData(DemoProvider.getListDemoByType(type));
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public DemoAdapter getDemoAdapter() {
        return demoAdapter;
    }
}
