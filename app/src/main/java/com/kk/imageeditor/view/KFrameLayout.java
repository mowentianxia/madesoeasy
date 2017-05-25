package com.kk.imageeditor.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.bean.data.LayoutData;
import com.kk.imageeditor.bean.data.SelectElement;
import com.kk.imageeditor.bean.view.LayoutInfo;
import com.kk.imageeditor.utils.BitmapUtil;


public class KFrameLayout extends FrameLayout implements IKLayout<LayoutData, LayoutInfo, FrameLayout> {

    protected SelectElement mSelectElement;
    protected LayoutData mLayoutData;
    protected LayoutInfo mViewElement;

    public KFrameLayout(Context context) {
        this(context, null, 0);
    }

    public KFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        this.setWillNotDraw(false);
    }

    public int getReadHeight() {
        return mLayoutData == null ? 0 : (int) mLayoutData.width;
    }

    public int getReadWidth() {
        return mLayoutData == null ? 0 : (int) mLayoutData.height;
    }

    @Override
    public SelectElement getSelectElement() {
        return mSelectElement;
    }

    @Override
    public void bind(SelectElement item, LayoutInfo pViewElement) {
        this.mViewElement = pViewElement;
        this.mSelectElement = item;
    }

    @Override
    public LayoutInfo getViewElement() {
        return mViewElement;
    }

    @Override
    public LayoutData getDataItem() {
        return mLayoutData;
    }

    @Override
    public FrameLayout getView() {
        return this;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean update(LayoutData item) {
        mLayoutData = item;
        Drawable old = getBackground();
        if (old != null) {
            if (old.equals(mLayoutData.getBackground())) {
                return false;
            } else {
                BitmapUtil.destroy(old);
            }
        }
        if (item.needRotate) {
            setRotation(item.angle);
        }
        if (item.visible) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
            return true;
        }
        if (Build.VERSION.SDK_INT >= 16)
            setBackground(mLayoutData.getBackground());
        else {
            setBackgroundDrawable(mLayoutData.getBackground());
        }
        return true;
    }
}
