package com.kk.imageeditor.draw;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kk.imageeditor.bean.data.TextData;
import com.kk.imageeditor.bean.data.ViewData;
import com.kk.imageeditor.view.IKView;
import com.kk.imageeditor.view.KTextView;


class ScaleHelper {

    public static float getScale(float srcWidth, float srcHeight, float destWidth, float destHeight) {
        float sx = srcWidth / destWidth;
        float sy = srcHeight == 0 ? (sx + 1.0f) : (srcHeight / destHeight);
        return Math.min(sx, sy);
    }

    public static ViewGroup.LayoutParams getLayoutParams(IKView view, ViewParent viewParent, float sc) {
        return getLayoutParams(view.getView(), view.getDataItem(), viewParent, sc);
    }

    public static ViewGroup.LayoutParams getLayoutParams(View view, ViewData item, ViewParent viewParent, float sc) {
        if (view == null || item == null) {
            return new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        ViewGroup.LayoutParams params;
        if (viewParent instanceof FrameLayout) {
            params = getFrameParams(view, item, sc);
        } else if (viewParent instanceof LinearLayout) {
            params = getLinearParams(view, item, sc);
        } else {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp == null) {
                lp = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            }
            lp.width = (int) (item.width > 0 ? Math.max(1, item.width * sc) : item.width);
            lp.height = (int) (item.height > 0 ? Math.max(1, item.height * sc) : item.height);
            params = lp;
        }
        if (view instanceof KTextView && item instanceof TextData) {
            setFitTextView((KTextView) view, (TextData) item, sc);
        }
        return params;
    }

    private static ViewGroup.LayoutParams getLinearParams(View view, ViewData item, float sc) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (lp == null) {
            lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        if (item.weight > 0) {
            lp.weight = item.weight;
        }
        lp.width = (int) (item.width > 0 ? Math.max(1, item.width * sc) : item.width);
        lp.height = (int) (item.height > 0 ? Math.max(1, item.height * sc) : item.height);
        lp.leftMargin = (int) (item.left * sc);
        lp.topMargin = (int) (item.top * sc);
        lp.rightMargin = (int) (item.right * sc);
        lp.bottomMargin = (int) (item.bottom * sc);
        lp.gravity = item.gravity;
        return lp;
    }

    private static ViewGroup.LayoutParams getFrameParams(View view, ViewData item, float sc) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
        if (lp == null) {
            lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        lp.width = (int) (item.width > 0 ? Math.max(1, item.width * sc) : item.width);
        lp.height = (int) (item.height > 0 ? Math.max(1, item.height * sc) : item.height);
        lp.leftMargin = (int) (item.left * sc);
        lp.topMargin = (int) (item.top * sc);
        lp.rightMargin = (int) (item.right * sc);
        lp.bottomMargin = (int) (item.bottom * sc);
        lp.gravity = item.gravity;
        return lp;
    }

    private static void setFitTextView(KTextView tv, TextData data, float sc) {
        if (tv == null || data == null || sc <= 0) return;
        if (data.font_size > 0) {
            float size = data.font_size * sc;
//            CharSequence text = tv.getText();
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            tv.setMaxTextSize(size);
            if (data.singleline)
                tv.setMinTextSize(size);
            else
                tv.setMinTextSize(size / 2.0f);
//            tv.setTextEx(text);
        }
    }
}