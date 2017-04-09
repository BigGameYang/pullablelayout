package com.yz.pullablelayout.behaviors;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yz.pullable.PullableLayout;
import com.yz.pullable.support.BaseRefreshHeaderBehavior;
import com.yz.pullablelayout.DisplayUtils;
import com.yz.pullablelayout.R;
import com.yz.pullablelayout.ViewUtils;

/**
 * Created by YangZhi on 2017/4/9 3:54.
 */

public class CustomRefreshBehaviorImpl extends BaseRefreshHeaderBehavior implements Runnable{


    int height;

    View view;

    View progress;

    TextView textView;


    public CustomRefreshBehaviorImpl(PullableLayout pullableLayout,View view){
        super(pullableLayout);
        this.view=view;
        this.progress= ViewUtils.findViewById(view, R.id.progress);
        this.textView= ViewUtils.findViewById(view,R.id.tv_text);
        height = DisplayUtils.convertDIP2PX(view.getContext(),100);
        changeTopMargin(-height);
    }

    @Override
    protected void onNormal() {
        progress.setVisibility(View.GONE);
        textView.setText("向下拉刷新更多");
    }

    @Override
    protected void onCanRefresh() {
        progress.setVisibility(View.GONE);
        textView.setText("松开刷新数据");
    }

    @Override
    protected void onRefreshing() {
        progress.setVisibility(View.VISIBLE);
        textView.setText("正在刷新");
        view.removeCallbacks(this);
        view.postDelayed(this,2000);
    }

    private void changeTopMargin(int topMargin){
        ViewGroup.MarginLayoutParams marginLayoutParams=(ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if(topMargin==marginLayoutParams.topMargin)
            return;
        marginLayoutParams.topMargin=topMargin;
        view.setLayoutParams(marginLayoutParams);
    }

    @Override
    public void changeHeaderShowHeight(int headerShowHeight) {
        int topMargin=-height+headerShowHeight;
        changeTopMargin(topMargin);
    }

    @Override
    public int getCanRefreshHeight() {
        return height;
    }

    @Override
    public void run() {
        stopRefresh();
    }
}
