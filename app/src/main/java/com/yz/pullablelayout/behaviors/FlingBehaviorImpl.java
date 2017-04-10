package com.yz.pullablelayout.behaviors;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yz.pullable.PullBehavior;
import com.yz.pullable.PullableLayout;

/**
 * Created by YangZhi on 2017/4/10 11:42.
 */

public class FlingBehaviorImpl implements PullBehavior{

    @Override
    public void onPullEnd(PullableLayout pullableLayout) {

    }

    @Override
    public void onOverPullDown(PullableLayout pullableLayout, int overPullHeight) {

    }

    @Override
    public void onPull(PullableLayout pullableLayout, int transHeight) {

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
