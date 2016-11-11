package com.kk.imageeditor.utils;

import android.text.TextUtils;
import android.view.Gravity;

@SuppressWarnings("RtlHardcoded")
public class GravityUtil {
    public static final String ALIGN_LEFT = "left";
    public static final String ALIGN_RIGHT = "right";
    public static final String ALIGN_CENTER = "center";
    public static final String ALIGN_VCENTER = "vcenter";
    public static final String ALIGN_HCENTER = "hcenter";
    public static final String ALIGN_VCENTER2 = "center_vertical";
    public static final String ALIGN_HCENTER2 = "center_horizontal";

    public static int getTextAlign(String alignStr) {
        int align = Gravity.NO_GRAVITY;
        if (TextUtils.isEmpty(alignStr))
            return align;
        String[] vals = alignStr.split("\\|");
        for (String val : vals) {
            align |= toAlign(val);
        }
        return align;
    }

    private static int toAlign(String str) {
        if (GravityUtil.ALIGN_LEFT.equalsIgnoreCase(str)) {
            return Gravity.LEFT;
        } else if (GravityUtil.ALIGN_RIGHT.equalsIgnoreCase(str)) {
            return Gravity.RIGHT;
        } else if (GravityUtil.ALIGN_CENTER.equalsIgnoreCase(str)) {
            return Gravity.CENTER;
        } else if (GravityUtil.ALIGN_HCENTER.equalsIgnoreCase(str) || GravityUtil.ALIGN_HCENTER2.equalsIgnoreCase(str)) {
            return Gravity.CENTER_HORIZONTAL;
        } else if (GravityUtil.ALIGN_VCENTER.equalsIgnoreCase(str) || GravityUtil.ALIGN_VCENTER2.equalsIgnoreCase(str)) {
            return Gravity.CENTER_VERTICAL;
        }
        return 0;
    }

    public static int getAlign(String alignStr) {
        int align = Gravity.NO_GRAVITY;
        if (TextUtils.isEmpty(alignStr))
            return align;
        String[] vals = alignStr.split("\\|");
        for (String val : vals) {
            align |= toAlign(val);
        }
        return align;
    }
}