package com.kk.imageeditor.widgets;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class LongTextView extends TextView{
    @Override
    public boolean isFocused() {
        return true;
    }

    public LongTextView(Context context) {
        this(context, null);
    }

    public LongTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LongTextView(Context context, AttributeSet attrs,
                        int defStyle) {
        super(context, attrs, defStyle);
        this.setEllipsize(TruncateAt.MARQUEE);
        this.setMarqueeRepeatLimit(-1);
        this.setSingleLine(true);
    }
}