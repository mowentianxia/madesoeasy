package com.kk.common.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;

public class MarqueeRepeatTextView extends AppCompatTextView{
    @Override
    public boolean isFocused() {
        return true;
    }

    public MarqueeRepeatTextView(Context context) {
        this(context, null);
    }

    public MarqueeRepeatTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeRepeatTextView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        this.setEllipsize(TruncateAt.MARQUEE);
        this.setMarqueeRepeatLimit(-1);
        this.setSingleLine(true);
    }
}