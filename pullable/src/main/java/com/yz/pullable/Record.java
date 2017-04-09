package com.yz.pullable;

/**
 * Created by YangZhi on 2017/4/3 16:50.
 */

public class Record {

    private float initDownY;

    private float initDownX;

    private float initDragY;

    private float initDragX;

    private float lastX;

    private float lastY;

    private boolean isBeginVeticalDrag;

    private boolean isBeginHorizontalDrag;

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
