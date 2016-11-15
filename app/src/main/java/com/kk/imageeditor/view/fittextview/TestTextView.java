package com.kk.imageeditor.view.fittextview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TestTextView extends TextView {
    protected boolean mJustify;

    public TestTextView(Context context) {
        this(context, null, 0);
    }

    public TestTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected float getLineSpacingExtraCompat() {
        return 0.0f;
    }

    public void setMaxTextSize(float size){

    }
    protected float getLineSpacingMultiplierCompat(){
        return 1.0f;
    }

    public void setMinTextSize(float size) {

    }

    public void setBoldText(boolean k) {

    }

    public void setNeedScaleText(boolean k) {

    }

    public void setItalicText(boolean k) {

    }

    public void setKeepWord(boolean k) {

    }
}
