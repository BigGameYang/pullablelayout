package com.yz.pullablelayout.behaviors;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.yz.pullable.PullBehavior;
import com.yz.pullable.PullableLayout;

/**
 * Created by yangzhi on 17/1/12.
 */

public class PullBehaviorImpl implements PullBehavior{

    private static final String TAG="Pullable";

    private View view;

    private int minHeight=-1;

    private int minWidth=-1;

    public PullBehaviorImpl(View view){
        this.view=view;
    }

    @Override
    public void onPull(PullableLayout pullableLayout, int transHeight) {

    }

    @Override
    public void onPullEnd(PullableLayout pullableLayout) {

    }

    @Override
    public void onOverPullDown(PullableLayout pullableLayout, int overPullHeight) {
        if(minHeight==-1){
            minHeight=view.getMeasuredHeight();
            minWidth=view.getMeasuredWidth();
        }
        Log.d(TAG,"onOverPullDown overPullHeight="+overPullHeight+",minWidth="+minWidth+",minHeight="+minHeight);
        changeImageWH(overPullHeight);
    }

    private void changeImageWH(int offsetWH){
        ViewGroup.MarginLayoutParams layoutParams=(ViewGroup.MarginLayoutParams)view.getLayoutParams();
        layoutParams.height=minHeight+offsetWH;
        layoutParams.width=minWidth+offsetWH;
        layoutParams.topMargin=-offsetWH;
        layoutParams.leftMargin=-offsetWH;
        view.requestLayout();
    }

    @Override
    public void startFling(View target, int velocityX, int velocityY) {
        if(target instanceof RecyclerView){
            final RecyclerView recyclerView=(RecyclerView)target;
            recyclerView.fling(velocityX,velocityY);
            recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
                @Override
                public boolean onFling(int velocityX, int velocityY) {
                    Log.d("PullBehaviorImpl","fling velocityY="+velocityY+",firtViewHolder="+recyclerView.findViewHolderForAdapterPosition(0));
                    return false;
                }
            });
        }
    }
}
