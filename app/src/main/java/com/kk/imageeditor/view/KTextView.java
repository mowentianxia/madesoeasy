package com.kk.imageeditor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.kk.common.ui.fittext.FitTextView;
import com.kk.common.utils.BitmapUtil;
import com.kk.imageeditor.bean.data.SelectElement;
import com.kk.imageeditor.bean.data.TextData;
import com.kk.imageeditor.bean.view.TextInfo;

public class KTextView extends FitTextView implements IKView<TextData, TextInfo, View> {
    protected SelectElement mSelectElement;
    protected TextInfo mViewElement;
    protected TextData mTextData;

    public KTextView(Context context) {
        this(context, null, 0);
    }

    public KTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void bind(SelectElement item, TextInfo pViewElement) {
        this.mViewElement = pViewElement;
        this.mSelectElement = item;
    }

    @Override
    public TextInfo getViewElement() {
        return mViewElement;
    }

    @Override
    public SelectElement getSelectElement() {
        return mSelectElement;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public boolean update(TextData item) {
        this.mTextData = item;
        initData(item);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public TextData getDataItem() {
        return mTextData;
    }

    @SuppressWarnings("deprecation")
    protected void initData(TextData item) {
        // 自定义字体
        Typeface face = item.font;
        if (face != null) {
            setTypeface(face);
        }

        setIncludeFontPadding(item.fontPadding);
        // 单行
        if (item.singleline) {
            setSingleLine(true);
            setNeedFit(false);
            setMinTextSize(getTextSize());
            setKeepWord(true);
            setLineSpacing(0.0f, 1.0f);
        } else {
            setKeepWord(item.keepWord);
            setNeedFit(true);
             setSingleLine(false);
            // 行间距
            float linespace = item.lineSpace;
            float linespacescale = item.lineSpaceScale;
            if (linespace > 0) {
                setLineSpacing(linespace, getLineSpacingMultiplierCompat());
            }
            if (linespacescale > 0) {
                setLineSpacing(getLineSpacingExtraCompat(), linespacescale);
            }
        }
        int sty = item.fontstyle;
        if (sty == Typeface.BOLD || sty == Typeface.BOLD_ITALIC) {
            setBoldText(true);
        } else {
            setBoldText(false);
        }
        if (sty == Typeface.ITALIC || sty == Typeface.BOLD_ITALIC) {
            setItalicText(true);
        } else {
            setItalicText(false);
        }
        getPaint().setAntiAlias(true);
        // 字体颜色
        if (item.visible)
            setVisibility(View.VISIBLE);
        else {
            setVisibility(View.GONE);
            return;
        }
        setJustify(item.justify);
        if (item.shadowColor != Color.TRANSPARENT) {
            setShadowLayer(item.shadowRadius, item.shadowDx, item.shadowDy, item.shadowColor);
        } else {
            getPaint().clearShadowLayer();
        }
        setTextColor(item.color);
        setGravity(item.align);
        if (item.singleline && item.width > 0) {
            setNeedScaleText(true);
        } else {
            setNeedScaleText(false);
        }
        setText(item.text);
        if (item.needRotate) {
            setRotation(item.angle);
        }
        Drawable old = getBackground();
        if (old != null) {
            if (old.equals(item.getBackground())) {
                return;
            } else {
                BitmapUtil.destroy(old);
            }
        }
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(item.getBackground());
        }
        else {
            setBackgroundDrawable(item.getBackground());
        }
    }
}
