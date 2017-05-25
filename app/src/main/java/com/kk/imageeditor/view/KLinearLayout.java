package com.kk.imageeditor.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kk.imageeditor.bean.data.LayoutData;
import com.kk.imageeditor.bean.data.SelectElement;
import com.kk.imageeditor.bean.data.ViewData;
import com.kk.imageeditor.bean.enums.LayoutType;
import com.kk.imageeditor.bean.view.LayoutInfo;
import com.kk.imageeditor.bean.view.ViewInfo;
import com.kk.imageeditor.utils.BitmapUtil;


public class KLinearLayout extends LinearLayout implements IKLayout<LayoutData, LayoutInfo, LinearLayout> {
    protected LayoutInfo mViewInfo;
    protected SelectElement mSelectElement;
    protected LayoutData mLayoutData;

    public KLinearLayout(Context context) {
        this(context, null, 0);
    }

    public KLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        this.setWillNotDraw(false);
    }
//    public void bind(LayoutElement item) {
//        this.mItem = item;
//        List<ViewInfo> items = mItem.getViewInfos();
//        for (ViewInfo ViewInfo : items) {
//            IKView<?> kview = null;
//            if (ViewInfo instanceof TextElement) {
//
//            } else if (ViewInfo instanceof ImageElement) {
//                KImageView view = new KImageView(getContext());
//                view.bind((ImageElement) ViewInfo);
//                kview = view;
//            }
//            if (kview != null) {
//                View view = kview.getView();
//                addView(view);
//                if (view instanceof KImageView) {
//                    mImageList.add((KImageView) view);
//                }
//                if (view instanceof IKView)
//                    mUpdateList.add((IKView) view);
//
//                if (action != null && action.length() > 0 && isEdit) {
//                    view.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                        }
//                    });
//                }
//            }
//        }
//    }

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
    public void bind(SelectElement item, LayoutInfo pViewInfo) {
        this.mViewInfo = pViewInfo;
        this.mSelectElement = item;
    }

    @Override
    public LayoutInfo getViewElement() {
        return mViewInfo;
    }

    @Override
    public LinearLayout getView() {
        return this;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean update(LayoutData item) {
        mLayoutData = item;
        if (item.layoutType == LayoutType.vertical) {
            setOrientation(VERTICAL);
        } else {
            setOrientation(HORIZONTAL);
        }
        if (item.visible) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
            return true;
        }
        if (item.needRotate) {
            setRotation(item.angle);
        }
        Drawable old = getBackground();
        if (old != null) {
            if (old.equals(mLayoutData.getBackground())) {
                return false;
            } else {
                BitmapUtil.destroy(old);
            }
        }
        if (Build.VERSION.SDK_INT >= 16)
            setBackground(mLayoutData.getBackground());
        else {
            setBackgroundDrawable(mLayoutData.getBackground());
        }
        return true;
    }

    @Override
    public LayoutData getDataItem() {
        return mLayoutData;
    }
}
