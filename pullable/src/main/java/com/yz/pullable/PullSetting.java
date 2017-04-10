package com.yz.pullable;

/**
 * Created by YangZhi on 2017/4/9 2:48.
 */

public class PullSetting {

    private static final int OVER_SCROLL_REST_TIME = 400;

    private static final float DRAG_RATE = .65f;

    private int overScrollRestTime=OVER_SCROLL_REST_TIME;

    private float overScrollRate=DRAG_RATE;

    private int overScrollDoublingHeight=100;

    private boolean isCustomOverScroll;

    private boolean isEnableOverScroll=true;

    private boolean isOverScrollForChild;

    private boolean isAutoEndOverScroll=true;

    private boolean debug;


    /**
     * 设置自定义的过度下拉
     * 默认过度下拉是调用 PullableLayout 的 scrollTo 方法 垂直方向滑动到负值来实现
     * 如果设置该配置为 true 时，将不会使用默认过度下拉实现，而是需要使用者自定义的 PullBehavior onOverPullDown() 方法回调时写自己的实现
     * 默认为 false
     * @param customOverScroll true 为自定义实现  false 为默认实现
     */
    public void setCustomOverScroll(boolean customOverScroll) {
        isCustomOverScroll = customOverScroll;
    }

    /**
     * 设置是否允许过度下拉
     * 该方法设置为 false 时将不会有过度下拉效果，且不会回调 PullBehavior onOverPullDown() 方法
     * 默认为 true
     * @param enableOverScroll
     */
    public void setOverScrollEnable(boolean enableOverScroll) {
        isEnableOverScroll = enableOverScroll;
    }

    /**
     * 设置过度下拉时是否将滑动消费传递给 NestedScrollChild
     * 该方法设置仅针对实现了 NestedScrollChild 嵌套滑动机制的子控件
     * 该方法设置为 true 时 , 过度下拉将不会消费当前的触摸事件
     * 默认为 false
     * @param overScrollForChild
     */
    public void setOverScrollForChild(boolean overScrollForChild) {
        isOverScrollForChild = overScrollForChild;
    }

    /**
     * 设置过度下拉的每次翻倍的比率
     * 为了达到类似于 iOS 过度下拉越拉越难拉的效果 设置该值将每多滑动 overScrollDoublingHeight 高度后将触摸的滑动距离会乘以该比率
     * @param overScrollRate 取值为 0 - 1f ，值越小将越难以滑动
     */
    public void setOverScrollRate(float overScrollRate) {
        this.overScrollRate = overScrollRate;
    }




    /**
     * 设置过度下拉翻倍比率的高度值
     * @param overScrollDoublingHeight 默认为 100 px
     */
    public void setOverScrollDoublingHeight(int overScrollDoublingHeight) {
        this.overScrollDoublingHeight = overScrollDoublingHeight;
    }

    /**
     * 设置过度下拉自动重置的滚动时间
     * @param overScrollRestTime 时间单位为毫秒
     */
    public void setOverScrollRestTime(int overScrollRestTime) {
        this.overScrollRestTime = overScrollRestTime;
    }

    /**
     * 设置是否需要在触摸事件 UP 后，让过度下拉自动重置
     * 默认为 true
     * @param autoEndOverScroll true 为自动重置  false 为不自动重置
     */
    public void setAutoEndOverScroll(boolean autoEndOverScroll) {
        isAutoEndOverScroll = autoEndOverScroll;
    }

    /**
     * 设置 Debug 状态 为 true 将有日志打印
     * 默认为 false
     * @param debug
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }


    public boolean isCustomOverScroll() {
        return isCustomOverScroll;
    }



    public boolean isOverScrollEnable() {
        return isEnableOverScroll;
    }



    public boolean isOverScrollForChild() {
        return isOverScrollForChild;
    }



    public float getOverScrollRate() {
        return overScrollRate;
    }


    public int getOverScrollRestTime() {
        return overScrollRestTime;
    }

    public int getOverScrollDoublingHeight() {
        return overScrollDoublingHeight;
    }



    public boolean isAutoEndOverScroll() {
        return isAutoEndOverScroll;
    }


    public boolean isDebug() {
        return debug;
    }
}
