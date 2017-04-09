package com.yz.pullable;

/**
 * Created by YangZhi on 2017/4/9 2:48.
 */

public class PullSetting {

    private static final int OVER_SCROLL_REST_TIME = 400;

    private static final float DRAG_RATE = .65f;

    private long overScrollRestTime=OVER_SCROLL_REST_TIME;

    private float overScrollRate=DRAG_RATE;

    private int overScrollDoublingHeight=100;

    private boolean isCustomOverScroll;

    private boolean isEnableOverScroll=true;

    private boolean isOverScrollForChild;

    private boolean isAutoEndOverScroll=true;

    private boolean debug=true;


    public boolean isCustomOverScroll() {
        return isCustomOverScroll;
    }

    public void setCustomOverScroll(boolean customOverScroll) {
        isCustomOverScroll = customOverScroll;
    }

    public boolean isOverScrollEnable() {
        return isEnableOverScroll;
    }

    public void setOverScrollEnable(boolean enableOverScroll) {
        isEnableOverScroll = enableOverScroll;
    }

    public boolean isOverScrollForChild() {
        return isOverScrollForChild;
    }

    public void setOverScrollForChild(boolean overScrollForChild) {
        isOverScrollForChild = overScrollForChild;
    }

    public float getOverScrollRate() {
        return overScrollRate;
    }

    public void setOverScrollRate(float overScrollRate) {
        this.overScrollRate = overScrollRate;
    }

    public static int getOverScrollRestTime() {
        return OVER_SCROLL_REST_TIME;
    }

    public void setOverScrollRestTime(long overScrollRestTime) {
        this.overScrollRestTime = overScrollRestTime;
    }

    public void setOverScrollDoublingHeight(int overScrollDoublingHeight) {
        this.overScrollDoublingHeight = overScrollDoublingHeight;
    }

    public int getOverScrollDoublingHeight() {
        return overScrollDoublingHeight;
    }

    public void setAutoEndOverScroll(boolean autoEndOverScroll) {
        isAutoEndOverScroll = autoEndOverScroll;
    }

    public boolean isAutoEndOverScroll() {
        return isAutoEndOverScroll;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }
}
