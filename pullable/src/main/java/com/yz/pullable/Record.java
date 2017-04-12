package com.yz.pullable;

/**
 * 滑动过程的相关记录类
 * Created by YangZhi on 2017/4/3 16:50.
 */

public class Record {

    /**
     * 初次按下的Y值
     */
    private float initDownY;

    /**
     * 初次按下的X值
     */
    private float initDownX;

    /**
     * 开始拖动的的Y值
     */
    private float initDragY;

    /**
     * 开始拖动的Y值
     */
    private float initDragX;

    /**
     * 最后一次触摸的X值
     */
    private float lastX;

    /**
     * 最后一次触摸的Y值
     */
    private float lastY;

    /**
     * 是否已开始竖向滑动
     */
    private boolean isBeginVeticalDrag;

    /**
     * 是否已开始横向滑动
     */
    private boolean isBeginHorizontalDrag;

    /**
     * 已过度下拉的距离
     */
    private int overScrollHeight;


    public float getInitDownY() {
        return initDownY;
    }

    public void setInitDownY(float initDownY) {
        this.initDownY = initDownY;
    }

    public float getInitDownX() {
        return initDownX;
    }

    public void setInitDownX(float initDownX) {
        this.initDownX = initDownX;
    }

    public float getLastX() {
        return lastX;
    }

    public void setLastX(float lastX) {
        this.lastX = lastX;
    }

    public float getLastY() {
        return lastY;
    }

    public void setLastY(float lastY) {
        this.lastY = lastY;
    }

    public boolean isBeginVeticalDrag() {
        return isBeginVeticalDrag;
    }

    public void setBeginVeticalDrag(boolean beginVeticalDrag) {
        isBeginVeticalDrag = beginVeticalDrag;
    }

    public boolean isBeginHorizontalDrag() {
        return isBeginHorizontalDrag;
    }

    public void setBeginHorizontalDrag(boolean beginHorizontalDrag) {
        isBeginHorizontalDrag = beginHorizontalDrag;
    }

    public void setInitDragX(float initDragX) {
        this.initDragX = initDragX;
    }

    public float getInitDragX() {
        return initDragX;
    }

    public void setInitDragY(float initDragY) {
        this.initDragY = initDragY;
    }

    public float getInitDragY() {
        return initDragY;
    }

    public void setOverScrollHeight(int overScrollHeight) {
        this.overScrollHeight = overScrollHeight;
    }

    public int getOverScrollHeight() {
        return overScrollHeight;
    }
}
