package com.yz.pullable;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.OverScroller;

/**
 * Created by YangZhi on 2017/4/3 1:24.
 */

public class PullableLayout extends FrameLayout implements NestedScrollingParent,NestedScrollingChild{

    private static final String TAG = "Pullable";

    private static final int INVALID_POINTER = -1;

    private NestedScrollingParentHelper nestedScrollingParentHelper;

    private NestedScrollingChildHelper nestedScrollingChildHelper;

    private HeaderStateHelper headerStateHelper;

    private TargetStateHelper targetStateHelper;

    private OverScroller flingScroller;

    private ValueAnimator resetOverScrollAnimator;

    private Record record;

    private BehaviorHelper behaviorHelper;

    private VelocityTracker mVelocityTracker;

    private int mTouchSlop;

    private int mMinFlingVelocity;

    private int mMaxFlingVelocity;

    private int mMinFlingTargetVelocity;

    private int mActivePointerId = INVALID_POINTER;

    private boolean mNestedScrollInProgress;

    private View mNestedFlingTarget;

    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];

    int scrollViewId;

    int headerViewId;

    private PullSetting setting;

    private OnChildScrollUpCallback mChildScrollUpCallback;


    public PullableLayout(Context context) {
        this(context, null);
    }

    public PullableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        flingScroller = new OverScroller(getContext());
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        record = new Record();
        setting=new PullSetting();
        headerStateHelper = new SimpleHeaderStateHelper(this,setting,record);
        targetStateHelper = new SimpleTargetStateHelper(this);
        behaviorHelper = new BehaviorHelper();
        ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mMinFlingTargetVelocity =mMaxFlingVelocity/4;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PullableLayout);
        scrollViewId = typedArray.getResourceId(R.styleable.PullableLayout_scrollViewId, -1);
        headerViewId = typedArray.getResourceId(R.styleable.PullableLayout_headerViewId, -1);

        typedArray.recycle();
    }


    private void ensureTargetAndHead(){
        View target=targetStateHelper.getTargetView();
        View head=headerStateHelper.getHeadView();
        if(target!=null&&target!=null)
            return;
        if (target==null&&scrollViewId != -1) {
            target = findViewById(scrollViewId);
            targetStateHelper.setTargetView(target);
        }
        if (head==null&&headerViewId != -1) {
            head = findViewById(headerViewId);
            headerStateHelper.setHeadView(head);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ensureTargetAndHead();
        if (targetStateHelper.getTargetView() == null) {
            int childCount = getChildCount();
            if (childCount == 1) {
                targetStateHelper.setTargetView(getChildAt(0));
            } else if (childCount == 2) {
                headerStateHelper.setHeadView(getChildAt(0));
                targetStateHelper.setTargetView(getChildAt(1));
            }
        }
    }

    public void setTarget(View target){
        targetStateHelper.setTargetView(target);
    }

    public void setHead(View head){
        headerStateHelper.setHeadView(head);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureTargetAndHead();
        if(headerStateHelper.getHeadHeight()<=0) {
            headerStateHelper.setHeadHeight(headerStateHelper.getHeadView().getMeasuredHeight());
        }

        MarginLayoutParams layoutParams=(MarginLayoutParams) targetStateHelper.getTargetView().getLayoutParams();
        final int targetTopMargin=layoutParams.topMargin;
        final int otherHeight=targetTopMargin-headerStateHelper.getHeadHeight();

        targetStateHelper.getTargetView().measure(MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom()-otherHeight, MeasureSpec.EXACTLY));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTargetAndHead();
        int pointerIndex = -1;
        final int action = MotionEventCompat.getActionMasked(ev);

        if (!isEnabled() || canScrollUp(targetStateHelper.getTargetView()) || mNestedScrollInProgress) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                record.setBeginVeticalDrag(false);
                record.setBeginHorizontalDrag(false);

                mActivePointerId = ev.getPointerId(0);
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    log("ACTION_DOWN pointerIndex<0");
                    return false;
                }
                record.setInitDownY(ev.getY(pointerIndex));
                record.setInitDownX(ev.getX());
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    log("ACTION_MOVE pointerIndex<0");
                    return false;
                }
                float y = ev.getY(pointerIndex);
                float x = ev.getX();
                beginHorizontalDrag(x, y);
                if (record.isBeginHorizontalDrag() && !record.isBeginVeticalDrag()) {
                    return false;
                }
                beginVeticalDrag(y);
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                onTouchEnd();
                break;
        }

        return record.isBeginVeticalDrag();
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex = -1;

        if (!isEnabled() || canScrollUp(targetStateHelper.getTargetView()) || mNestedScrollInProgress) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        boolean eventAddedToVelocityTracker=false;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                record.setBeginVeticalDrag(false);
                record.setBeginHorizontalDrag(false);
                record.setLastY(record.getInitDownY());
                record.setLastX(record.getInitDownX());
                int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE|ViewCompat.SCROLL_AXIS_VERTICAL;
                startNestedScroll(nestedScrollAxis);
                break;

            case MotionEvent.ACTION_MOVE: {
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    log("Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }


                final float y = ev.getY(pointerIndex);
                beginVeticalDrag(y);

                if (record.isBeginVeticalDrag()) {
                    final int diffY = (int) (record.getLastY() - y);
                    dispatchNestedPreScroll(0,diffY,mScrollConsumed,mScrollOffset);
                    moveUpdate(diffY);
                }
                record.setLastY(y);
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex < 0) {
                    log("Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                mActivePointerId = ev.getPointerId(pointerIndex);
                record.setInitDragY(ev.getY(pointerIndex));
                record.setLastY(ev.getY(pointerIndex));
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    log("ACTION_POINTER_UP pointerIndex<0");
                    return true;
                }
                record.setInitDragY(ev.getY(pointerIndex));
                record.setLastY(ev.getY(pointerIndex));
                break;

            case MotionEvent.ACTION_UP: {
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    log("Got ACTION_UP event but don't have an active pointer id.");
                    resetTouch();
                    return false;
                }
                eventAddedToVelocityTracker = true;
                behaviorHelper.dispatchOnPullEnd(this);

                if(record.isBeginVeticalDrag()&&!headerStateHelper.isHeadOnOverPull()){
                    mVelocityTracker.addMovement(ev);
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                    float velocityY=-mVelocityTracker.getYVelocity(pointerIndex);
                    fling((int)velocityY);
                }

                mActivePointerId = INVALID_POINTER;
                resetTouch();
                break;
            }
            case MotionEvent.ACTION_CANCEL:
                cancelTouch();
                break;
        }

        if(!eventAddedToVelocityTracker){
            mVelocityTracker.addMovement(ev);
        }


        return true;
    }


    private void resetTouch() {
        if (record.isBeginVeticalDrag() && headerStateHelper.isHeadOnOverPull()) {
            if(getSetting().isAutoEndOverScroll()) {
                endOverScroll();
            }
        }
        record.setBeginVeticalDrag(false);
        record.setBeginHorizontalDrag(false);
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
        }
    }

    private void cancelTouch() {
        resetTouch();
    }

    private void onTouchEnd() {
        mActivePointerId = INVALID_POINTER;
    }

    private void beginVeticalDrag(float y) {
        final float diffY = y - record.getInitDownY();
        if (Math.abs(diffY) > mTouchSlop && headerStateHelper.isHeadOnShow() && !record.isBeginVeticalDrag()) {
            record.setInitDragY(y);
            record.setBeginVeticalDrag(true);
        }
    }

    private void beginHorizontalDrag(float x, float y) {
        final float diffY = y - record.getInitDownY();
        final float diffX = x - record.getInitDownX();
        if (Math.abs(diffX) > mTouchSlop && Math.abs(diffX) > Math.abs(diffY)) {
            record.setInitDragX(x);
            record.setBeginHorizontalDrag(true);
        }
    }


    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = ev.getPointerId(newPointerIndex);
        }
    }


    float rate = 1f;

    private void caculateRate() {
        int radioHeight = getSetting().getOverScrollDoublingHeight();

        int currentOverScrollHeight=getSetting().isCustomOverScroll()?record.getOverScrollHeight():-getScrollY();

        int radio = (int) (1f * currentOverScrollHeight / radioHeight);
        rate = 1f;

        for (int i = 0; i < radio; i++) {
            rate = Math.max(0f, rate * getSetting().getOverScrollRate());
        }
    }


    /**
     * 滑动更新
     *
     * @param dy
     */
    private boolean moveUpdate(int dy) {
        boolean isOnHeadPullDown=dy < 0 && (headerStateHelper.isHeadShowFull());
        boolean isOnOverPull=headerStateHelper.isHeadOnOverPull();
        if(getSetting().isOverScrollEnable()){
            if(isOnOverPull||isOnHeadPullDown){
                overScrollByHead(dy);
                return !getSetting().isOverScrollForChild();
            }
        }

        if(isOnHeadPullDown){
            return false;
        }

        moveHead(dy);
        return true;
    }


    /**
     * 过度下拉
     *
     * @param dy
     */
    private void overScrollByHead(int dy) {
        caculateRate();
        int scrollY = (int) (-record.getOverScrollHeight() + dy * rate);
        scrollY=Math.min(0,scrollY);
        overScrollToHead(scrollY);
    }

    private void overScrollToHead(int overScrollY){
        int overScrollHeight;
        if(!getSetting().isCustomOverScroll()) {
            scrollTo(0, overScrollY);
            overScrollHeight=-getScrollY();
        }else {
            overScrollHeight=-overScrollY;
        }
        record.setOverScrollHeight(overScrollHeight);
        behaviorHelper.dispatchOnOverScrollDown(this,overScrollHeight);
    }



    /**
     * 移动头部
     *
     * @param dy last y 减去 current y
     */
    private void moveHead(int dy) {
        scrollBy(0, dy);
        behaviorHelper.dispatchOnPull(this,getScrollY());
    }

    //NestedScrollingParent

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        log("NestedScrollingParent " + "onStartNestedScroll");
        return isEnabled()&&(nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        log("NestedScrollingParent " + "onNestedScrollAccepted");
        mNestedScrollInProgress = true;
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
        cancelResetOverScrollAnim();

    }

    @Override
    public void onStopNestedScroll(View target) {
        log("NestedScrollingParent " + "onStopNestedScroll");
        mNestedScrollInProgress = false;
        nestedScrollingParentHelper.onStopNestedScroll(target);
        behaviorHelper.dispatchOnPullEnd(this);
        if (headerStateHelper.isHeadOnOverPull()&&getSetting().isAutoEndOverScroll()) {
//            fling(1000);
            endOverScroll();
        }

    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        log("NestedScrollingParent " + "onNestedScroll");
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                mScrollOffset);
    }

    /**
     * @param target
     * @param dx       last X 减去current X
     * @param dy       last Y 减去current Y  小于0 下拉  大于0上拉
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        log("NestedScrollingParent " + "onNestedPreScroll dx="+dx+",dy="+dy+",consumedX="+consumed[0]+",consumedY="+consumed[1]);
        if (canScrollUp(target)) {
            log("NestedScrollingParent " + "onNestedPreScroll child canscrollup");
            return;
        }
        log("NestedScrollingParent " + "onNestedPreScroll isHeadOnShow="+headerStateHelper.isHeadOnShow());
        boolean scrollSelf = headerStateHelper.isHeadOnShow() || (!headerStateHelper.isHeadOnShow() && dy < 0);

        if (scrollSelf) {
            log("NestedScrollingParent " + "onNestedPreScroll scrollSelf");
            if(moveUpdate(dy)) {
                log("NestedScrollingParent " + "onNestedPreScroll consumed");
                consumed[1] = dy;
            }
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        log("NestedScrollingParent " + "onNestedPreFling velocityX=" + velocityX + ",velocityY=" + velocityY);
        mNestedFlingTarget = null;
        if (!headerStateHelper.isHeadOnShow()) {
//            return dispatchNestedPreFling(velocityX, velocityY);
            return false;
        }

        if ((!getSetting().isOverScrollEnable()||!headerStateHelper.isHeadOnOverPull()) && Math.abs(velocityY) > mMinFlingVelocity) {
            mNestedFlingTarget = target;
            velocityY = Math.min(mMaxFlingVelocity, velocityY);
            fling((int) velocityY);
        }
        return true;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        log("NestedScrollingParent " + "onNestedFling velocityX=" + velocityX + ",velocityY=" + velocityY + ",consumed=" + consumed);
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public int getNestedScrollAxes() {
        log("NestedScrollingParent " + "getNestedScrollAxes");
        return nestedScrollingParentHelper.getNestedScrollAxes();
    }



    // NestedScrollingChild

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        log("NestedScrollingChild " + "setNestedScrollingEnabled enable="+enabled);
        nestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        log("NestedScrollingChild " + "isNestedScrollingEnabled");
        return nestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        log("NestedScrollingChild " + "startNestedScroll axes="+axes);
        return nestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        log("NestedScrollingChild " + "stopNestedScroll");
        nestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return nestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        log("NestedScrollingChild " + "dispatchNestedScroll");
        return nestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        log("NestedScrollingChild " + "dispatchNestedPreScroll");
        return nestedScrollingChildHelper.dispatchNestedPreScroll(
                dx, dy, consumed, offsetInWindow);
    }



    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        log("NestedScrollingChild " + "dispatchNestedFling");
        return nestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        log("NestedScrollingChild " + "dispatchNestedPreFling");
        return nestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }


    public void fling(int velocityY) {
        log("fling " + "start fling velocityY=" + velocityY + ",mNestedFlingTarget=" + mNestedFlingTarget);
        cancelResetOverScrollAnim();
        flingScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, headerStateHelper.getHeadHeight());
        invalidate();
    }




    /**
     * 自动将 OverScroll 回滚到预设的偏移状态
     */
    public void endOverScroll(int finalOverScrollHeight) {
        cancelResetOverScrollAnim();
        final int currentOverScrollHeight=getSetting().isCustomOverScroll()?record.getOverScrollHeight():-getScrollY();
        resetOverScrollAnimator =ValueAnimator.ofInt(-currentOverScrollHeight,finalOverScrollHeight);
        resetOverScrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int lastValue=currentOverScrollHeight;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value=(int)animation.getAnimatedValue();
                if(value!=lastValue){
                    overScrollToHead(value);
                    lastValue=value;
                }
            }
        });
        resetOverScrollAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                resetOverScrollAnimator.removeAllUpdateListeners();
                resetOverScrollAnimator.removeAllListeners();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        resetOverScrollAnimator.setInterpolator(new DecelerateInterpolator());
        resetOverScrollAnimator.setDuration(getSetting().getOverScrollRestTime());
        resetOverScrollAnimator.start();

    }

    private void cancelResetOverScrollAnim(){
        if(resetOverScrollAnimator !=null&& resetOverScrollAnimator.isStarted()){
            resetOverScrollAnimator.cancel();
        }
    }


    /**
     * 自动将 OverScroll 回滚到初始状态
     */
    public void endOverScroll() {
//        int dy=getSetting().isCustomOverScroll()?record.getOverScrollHeight():-getScrollY();
        endOverScroll(0);
    }

    @Override
    public void scrollTo(int x, int y) {
//        log("scrollTo y="+y);
        if(y<0){
            if(getSetting().isOverScrollEnable()&&!getSetting().isCustomOverScroll()){
                if (y < -headerStateHelper.getHeadHeight())
                {
                    y = -headerStateHelper.getHeadHeight();
                }
            }else {
                y=0;
            }
        }

        final int maxScrollHeadDistance= getSetting().getMaxHeadScrollDistance()!=-1?getSetting().getMaxHeadScrollDistance():headerStateHelper.getHeadHeight();

        if (y > maxScrollHeadDistance) {
            y = maxScrollHeadDistance;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        log("onOverScrolled scrollX=" + scrollX + ",scrollY=" + scrollY + ",clampedX=" + clampedX + ",clampedY=" + clampedY);
    }

    @Override
    public void computeScroll() {

        boolean computeFlingOffset = flingScroller.computeScrollOffset();
        log("computeScroll flingScroller.computeScrollOffset()=" + computeFlingOffset + ",scrollY=" + getScrollY() + ",currentY=" + flingScroller.getCurrY() + ",currentVelocity" + flingScroller.getCurrVelocity() + ",isHeadOnShow=" + headerStateHelper.isHeadOnShow());
        if (!computeFlingOffset) {
            return;
        }
        if (getScrollY() != flingScroller.getCurrY() || headerStateHelper.isHeadOnShow()) {
            log("computeScroll flingScroller scrollTo");
            scrollTo(0, flingScroller.getCurrY());
            behaviorHelper.dispatchOnPull(this,getScrollY());
            invalidate();
            return;
        }

        int currentVelocity = (int) flingScroller.getCurrVelocity();
        log("fling mNestedFlingTarget=" + mNestedFlingTarget + ",currentVelocity=" + currentVelocity + ",mMinFlingVelocity=" + mMinFlingVelocity+",mMinFlingTargetVelocity="+mMinFlingTargetVelocity);
        if (mNestedFlingTarget != null && currentVelocity > mMinFlingTargetVelocity) {
            log("fling dispatchFling");
            behaviorHelper.dispatchFling(mNestedFlingTarget, 0, currentVelocity);
        }
        mNestedFlingTarget = null;
        flingScroller.forceFinished(true);
    }

    public void addBehavior(PullBehavior behavior) {
        behaviorHelper.addBehavior(behavior);
    }

    public void removeBehavior(PullBehavior behavior) {
        behaviorHelper.removeBehavior(behavior);
    }

    public void clearBehavior() {
        behaviorHelper.clearBehavior();
    }


    @Override
    public boolean canScrollVertically(int direction) {
        if(direction<0){
            return !headerStateHelper.isHeadShowFull();
        }else {
            return super.canScrollVertically(direction);
        }
    }



    public PullSetting getSetting() {
        return setting;
    }

    private void log(String msg) {
        if (getSetting().isDebug()) {
            Log.d(TAG, msg);
        }
    }


    /**
     * 内部嵌套滑动的 View 是否还可以下拉(判断内部嵌套滑动的 View 是否已经滑动到顶部了)
     * @param target
     * @return true 代表还可以下拉 false 代表已经不能再下拉了，已经滑动到顶部了
     */
    public boolean canScrollUp(View target) {
        if (mChildScrollUpCallback != null) {
            return mChildScrollUpCallback.canChildScrollUp(this, target);
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (target instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) target;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(target, -1) || target.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(target, -1);
        }
    }


    public void setChildScrollUpCallback(OnChildScrollUpCallback mChildScrollUpCallback) {
        this.mChildScrollUpCallback = mChildScrollUpCallback;
    }


    /**
     * 自定义嵌套滑动的 View 是否还可以下拉
     */
    public interface OnChildScrollUpCallback {

        /**
         * 内部嵌套滑动的 View 是否还可以下拉(判断内部嵌套滑动的 View 是否已经滑动到顶部了)
         * @param parent
         * @param child
         * @return true 代表还没有滑动到顶部 false 已经滑动到顶部了
         */
        boolean canChildScrollUp(PullableLayout parent, @Nullable View child);
    }


}
