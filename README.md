# PullableLayout

一个嵌套下拉滑动的辅助控件 , 不需要使用者关心嵌套的触摸事件传递问题 ，可简单自定义各种头部滑动中的 UI 行为 。

### 已提供功能支持 :

* 过度下拉，可自定义过度下拉头部行为 。

* NestedScroll 嵌套滑动机制 。

* 下拉刷新 。

* 滑动过程中自定义头部UI行为 。

* Fling 惯性滑动状态的嵌套传递 。

### 接入 :

根目录 build.gradle 引入 jcenter 仓库

```
    repositories {
            jcenter()
            maven { url "https://jitpack.io" }
    }
```

Module build.gradle 依赖

```
compile 'com.yz:PullableLayout:0.0.2'

```

### 基础使用:

以 [**PullableLayout**](https://github.com/BigGameYang/pullablelayout/blob/master/pullable/src/main/java/com/yz/pullable/PullableLayout.java) 为嵌套滑动 scrollView 控件的外层布局 ，

Demo 中 scrollView 为 RecyclerView

[**PullableLayout**](https://github.com/BigGameYang/pullablelayout/blob/master/pullable/src/main/java/com/yz/pullable/PullableLayout.java) 增加如下属性

```
xmlns:app="http://schemas.android.com/apk/res-auto"
app:headerViewId="@+id/ivImage"
app:scrollViewId="@+id/rvList"

```

headerViewId 为头部控件的 id , scrollViewId 为 嵌套滑动 scrollView 的 id 。

XML :

```
<com.yz.pullable.PullableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/pullableLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/white"
    app:headerViewId="@+id/ivImage"
    app:scrollViewId="@+id/rvList">

    <ImageView
        android:id="@+id/ivImage"
        android:layout_width="match_parent"
        android:layout_height="250dp"
        android:src="@mipmap/bg_img"
        android:scaleType="centerCrop"
        />

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rvList"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/ivImage"
        android:layout_marginTop="250dp" />
</com.yz.pullable.PullableLayout>

```

Java 代码 :

如果只是需要过度下拉 ，则不用配置任何 Java 代码 ，会有默认的过度下拉实现 。

如果需要自定义头部过度下拉则需要实现 [**PullBehavior**](https://github.com/BigGameYang/pullablelayout/blob/master/pullable/src/main/java/com/yz/pullable/PullBehavior.java) 接口 , 该接口为滑动中的各种 UI 行为实现 。

```
public interface PullBehavior {

    /**
     * 当滑动结束回调该方法 （ 触摸事件 UP 时 会调用 ）
     * @param pullableLayout
     */
    void onPullEnd(PullableLayout pullableLayout);


    /**
     * 当过度下拉时回调该方法
     * @param pullableLayout
     * @param overPullHeight 当前已过度下拉的高度 , 最小值为 0
     */
    void onOverPullDown(PullableLayout pullableLayout, int overPullHeight);


    /**
     * 当头部还在显示中，上滑的时候会回调该方法
     * @param pullableLayout
     * @param transHeight transHeight 已经滑动的距离
     */
    void onPull(PullableLayout pullableLayout, int transHeight);


    /**
     * 当有惯性滑动时,如果头部已经滑完，仍有惯性速度会回调该方法,如果需要惯性滑动给子 View , 需要实现该方法
     * @param target 嵌套的可滑动子 View
     * @param velocityX 横向剩余速度
     * @param velocityY 竖向剩余速度
     */
    void startFling(View target,int velocityX,int velocityY);

}

```
Demo 中实现了当过度下拉时头部图片围绕中心点放大的效果 。

具体代码查看 [**PullBehaviorImpl**](https://github.com/BigGameYang/pullablelayout/blob/master/app/src/main/java/com/yz/pullablelayout/behaviors/PullBehaviorImpl.java) 类

实现 [**PullBehavior**](https://github.com/BigGameYang/pullablelayout/blob/master/pullable/src/main/java/com/yz/pullable/PullBehavior.java) 后通过以下代码设置给 [**PullableLayout**](https://github.com/BigGameYang/pullablelayout/blob/master/pullable/src/main/java/com/yz/pullable/PullableLayout.java) :

```
PullBehavior behavior = new PullBehaviorImpl(rootView.findViewById(R.id.ivImage));
pullableLayout.addBehavior(behavior);
```
为了让滑动中的行为更模块化 , [**PullableLayout**](https://github.com/BigGameYang/pullablelayout/blob/master/pullable/src/main/java/com/yz/pullable/PullableLayout.java) 支持重复 addBehavior 以同时支持多种不同的 UI 行为 。

### 自定义配置:

可以通过如下代码获取到 [**PullableLayout**](https://github.com/BigGameYang/pullablelayout/blob/master/pullable/src/main/java/com/yz/pullable/PullableLayout.java) 的配置对象

```
PullSetting setting= pullableLayout.getSetting();

```

可配置方法为 :

```
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

```

### 下拉刷新支持

Demo 中已写了下拉刷新支持的写法

* 如果使用 SwipeRefreshLayout 做为下拉刷新控件 或支持 NestScroll 机制的控件均可以直接在 该下拉刷新控件中嵌套 [**PullableLayout**](https://github.com/BigGameYang/pullablelayout/blob/master/pullable/src/main/java/com/yz/pullable/PullableLayout.java)

    例如:

    ```
    <android.support.v4.widget.SwipeRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/refrshlayout"
        android:orientation="vertical" android:layout_width="match_parent"
        android:layout_height="match_parent">
        <com.yz.pullable.PullableLayout
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/pullableLayout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@android:color/white"
            app:headerViewId="@+id/ivImage"
            app:scrollViewId="@+id/rvList">

            <ImageView
                android:id="@+id/ivImage"
                android:layout_width="match_parent"
                android:layout_height="250dp"
                android:src="@mipmap/bg_img"
                android:scaleType="centerCrop"
                />

            <android.support.v7.widget.RecyclerView
                android:id="@+id/rvList"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_below="@id/ivImage"
                android:layout_marginTop="250dp" />
        </com.yz.pullable.PullableLayout>
    </android.support.v4.widget.SwipeRefreshLayout>

    ```

* 如果需要一些特别的 UI 需求的下拉刷新实现 ， 可写一个 [**BaseRefreshHeaderBehavior**](https://github.com/BigGameYang/pullablelayout/blob/master/pullable/src/main/java/com/yz/pullable/support/BaseRefreshHeaderBehavior.java) 的实现类 。

  可参照 Demo 中的一个简单实现 [**CustomRefreshBehaviorImpl**](https://github.com/BigGameYang/pullablelayout/blob/master/app/src/main/java/com/yz/pullablelayout/behaviors/CustomRefreshBehaviorImpl.java)


