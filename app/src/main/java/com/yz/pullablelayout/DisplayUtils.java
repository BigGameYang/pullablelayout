package com.yz.pullablelayout;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by YangZhi on 2017/4/7 2:07.
 */

public class DisplayUtils {

    public static int convertDIP2PX(Context context, float dip) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    public static int convertPX2DIP(Context context,float px) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }
}
