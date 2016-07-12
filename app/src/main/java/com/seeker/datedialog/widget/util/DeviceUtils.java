package com.seeker.datedialog.widget.util;

import android.content.Context;

/**
 * @author zhengxiaobin <gybin02@Gmail.com>
 * @since 2016/7/12
 */
public class DeviceUtils {

    public static int getScreenWidth(Context context) {
        try {
            return context.getResources().getDisplayMetrics().widthPixels;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 480;
        }
    }

    public static int getScreenHeight(Context context) {
        try {
            return context.getResources().getDisplayMetrics().heightPixels;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 800;
        }
    }

    public static float getDeviceDensity(Context context) {
        try {
            float ex = context.getResources().getDisplayMetrics().density;
            return ex;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 480.0F;
        }
    }

    public static int getDeviceDensityValue(Context context) {
        try {
            int ex = context.getResources().getDisplayMetrics().densityDpi;
            return ex;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 480;
        }
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }

    public static int getMarginTopWithoutWave(Context context) {
        return dip2px(context, 20.0F);
    }

    public static int sp2px(Context context, float spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * scale + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

}
