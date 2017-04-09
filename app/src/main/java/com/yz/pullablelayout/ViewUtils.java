package com.yz.pullablelayout;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by YangZhi on 2017/4/7 1:49.
 */

public class ViewUtils {

    public static <R> R findViewById(View view, @IdRes int id){
        return (R)view.findViewById(id);
    }
}
