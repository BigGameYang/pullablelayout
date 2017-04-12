package com.yz.pullable;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 行为分发辅助类
 * Created by YangZhi on 2017/4/3 22:38.
 */

public class BehaviorHelper {

    private List<PullBehavior> behaviors=new ArrayList<>();


    public void dispatchOnPullEnd(PullableLayout pullableLayout){
        for (PullBehavior behavior:behaviors){
            behavior.onPullEnd(pullableLayout);
        }
    }


    public void dispatchOnOverScrollDown(PullableLayout pullableLayout, int overPullHeight){
        for (PullBehavior behavior:behaviors){
            behavior.onOverPullDown(pullableLayout,overPullHeight);
        }
    }

    public void dispatchOnPull(PullableLayout pullableLayout, int pullHeight){
        for (PullBehavior behavior:behaviors){
            behavior.onPull(pullableLayout,pullHeight);
        }
    }

    public void dispatchFling(View target,int velocityX, int velocityY){
        for (PullBehavior behavior:behaviors){
            behavior.startFling(target,velocityX,velocityY);
        }
    }

    public void addBehavior(PullBehavior behavior){
        if(behavior==null)
            return;
        this.behaviors.add(behavior);
    }

    public void removeBehavior(PullBehavior behavior){
        this.behaviors.remove(behavior);
    }

    public void clearBehavior(){
        this.behaviors.clear();
    }


}
