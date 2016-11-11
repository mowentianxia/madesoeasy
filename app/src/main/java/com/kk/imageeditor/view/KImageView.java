package com.kk.imageeditor.view;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kk.imageeditor.bean.data.ImageData;
import com.kk.imageeditor.bean.data.SelectElement;
import com.kk.imageeditor.bean.view.ImageInfo;
import com.kk.imageeditor.utils.BitmapUtil;


public class KImageView extends ImageView implements IKView<ImageData, ImageInfo, ImageView> {
    public static final int DEFAULT_COLOR = 0x383838;
    protected ImageInfo mViewElement;
    protected SelectElement mSelectElement;
    protected ImageData mImageData;

    public KImageView(Context context) {
        this(context, null, 0);
    }

    public KImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setScaleType(ScaleType.FIT_CENTER);// 设置图片为按比例缩放
    }

    @Override
    public SelectElement getSelectElement() {
        return mSelectElement;
    }

    @Override
    public ImageView getView() {
        return this;
    }

    @Override
    public void bind(SelectElement item, ImageInfo pViewElement) {
        this.mViewElement = pViewElement;
        this.mSelectElement = item;
    }

    @Override
    public ImageInfo getViewElement() {
        return mViewElement;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean update(ImageData item) {
        this.mImageData = item;
        if (item == null) return false;
        setScaleType(item.scaleType);
        if (item.visible) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
            return true;
        }
        if (item.needRotate) {
            setRotation(item.angle);
        }
        Drawable old = getDrawable();
        if (old != null) {
            if (old.equals(item.getSrc())) {
                Log.v("kk", "src is same");
                return false;
            } else {
                BitmapUtil.destroy(old);
            }
        }
        setImageDrawable(item.getSrc());
        old = getBackground();
        if (old != null) {
            if (old.equals(mImageData.getBackground())) {
                Log.v("kk", "bg is same");
                return false;
            } else {
                BitmapUtil.destroy(old);
            }
        }
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(mImageData.getBackground());
        } else {
            setBackgroundDrawable(mImageData.getBackground());
        }
        return true;
    }

    @Override
    public ImageData getDataItem() {
        return mImageData;
    }
}