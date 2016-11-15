package com.kk.imageeditor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class CustomTextView extends View {
    private int mMaxLines = Integer.MAX_VALUE;
    private boolean mSingleLine = false;
    private float mMaxTextSize;
    private float mMinTextSize;
    private boolean mKeepWord = true;
    private float mLineSpacing = 0.0f;
    private float mLineSpacingMultiplier = 1.0f;
    private TextPaint mPaint;
    private int mGravity;
    private boolean mNeedScaleText;
    private CharSequence mText;
    private CharSequence mOriginalText;
    private boolean mIncludeFontPadding = true;
    private boolean mJustify;
    protected StaticLayout mLayout;
    private float mOriginalTextSize;

    private int mMaxWidth, mMaxHeight;
    //fit
    private boolean mNeedFit = true;
    private boolean mMeasured;
    private boolean mFittingText;
    protected static final float LIMIT = 0.001f;// 误差

    public CustomTextView(Context context) {
        this(context, null, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint = new TextPaint();
    }

    //region paint
    public TextPaint getPaint() {
        return mPaint;
    }

    public void setShadowLayer(float radius, float dx, float dy, int shadowColor) {
        getPaint().setShadowLayer(radius, dx, dy, shadowColor);
    }

    public void setTypeface(Typeface mTypeface) {
        mPaint.setTypeface(mTypeface);
    }

    public void setTextSize(int type, float mTextSize) {
        setTextSize(TypedValue.applyDimension(type, mTextSize, getResources().getDisplayMetrics()));
    }

    public void setTextSize(float mTextSize) {
        mOriginalTextSize = mTextSize;
        mPaint.setTextSize(mTextSize);
    }

    public float getTextSize() {
        return mPaint.getTextSize();
    }

    public Typeface getTypeface() {
        return mPaint.getTypeface();
    }

    public int getTextColor() {
        return mPaint.getColor();
    }

    public void setTextColor(int textColor) {
        mPaint.setColor(textColor);
    }

    /**
     * 设置粗体
     */
    public void setBoldText(boolean bold) {
        getPaint().setFakeBoldText(bold);
    }

    public boolean isBoldText() {
        return getPaint().isFakeBoldText();
    }

    /**
     * 设置斜体
     */
    public void setItalicText(boolean italic) {
        getPaint().setTextSkewX(italic ? -0.25f : 0f);
    }

    public boolean isItalicText() {
        return getPaint().getTextSkewX() != 0f;
    }
    //endregion

    public boolean isNeedFit() {
        return mNeedFit;
    }

    public void setNeedFit(boolean needFit) {
        mNeedFit = needFit;
    }

    public float getMinTextSize() {
        return mMinTextSize;
    }

    public boolean isJustify() {
        return mJustify;
    }

    public void setJustify(boolean justify) {
        mJustify = justify;
    }

    public void setMinTextSize(float mMinTextSize) {
        this.mMinTextSize = mMinTextSize;
    }

    public float getMaxTextSize() {
        return mMaxTextSize;
    }

    public void setMaxTextSize(float mMaxTextSize) {
        this.mMaxTextSize = mMaxTextSize;
    }

    public boolean isSingleLine() {
        return mSingleLine;
    }

    public void setSingleLine(boolean mSingleLine) {
        this.mSingleLine = mSingleLine;
    }

    public boolean isKeepWord() {
        return mKeepWord;
    }

    public boolean isIncludeFontPadding() {
        return mIncludeFontPadding;
    }

    public void setIncludeFontPadding(boolean includeFontPadding) {
        mIncludeFontPadding = includeFontPadding;
    }

    public void setKeepWord(boolean keepWord) {
        mKeepWord = keepWord;
    }

    public int getMaxLines() {
        return mMaxLines;
    }

    public void setMaxLines(int maxLines) {
        mMaxLines = maxLines;
    }

    //region space
    public float getLineSpacing() {
        return mLineSpacing;
    }


    public void setLineSpacing(float lineSpacing) {
        mLineSpacing = lineSpacing;
    }

    public void setLineSpacing(float lineSpacing, float lineSpacingMultiplier) {
        setLineSpacing(lineSpacing);
        setLineSpacingMultiplier(lineSpacingMultiplier);
    }

    public float getLineSpacingMultiplier() {
        return mLineSpacingMultiplier;
    }

    public float getLineSpacingExtraCompat() {
        return getLineSpacing();
    }

    public float getLineSpacingMultiplierCompat() {
        return getLineSpacingMultiplier();
    }

    public boolean getIncludeFontPaddingCompat() {
        return isIncludeFontPadding();
    }

    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        mLineSpacingMultiplier = lineSpacingMultiplier;
    }
    //endregion

    public int getGravity() {
        return mGravity;
    }

    public void setGravity(int gravity) {
        mGravity = gravity;
    }

    public boolean isNeedScaleText() {
        return mNeedScaleText;
    }

    public void setNeedScaleText(boolean needScaleText) {
        mNeedScaleText = needScaleText;
    }

    public CharSequence getText() {
        return mText;
    }

    public int getTextWidth() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    public int getTextHeight() {
        return getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        StaticLayout layout = getStaticLayout();
        layout.draw(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.UNSPECIFIED
                && heightMode == MeasureSpec.UNSPECIFIED) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mOriginalTextSize);
            mMeasured = false;
        } else {
            mMeasured = true;
            fitText(mOriginalText);
        }
    }

    public void setText(CharSequence text) {
        mOriginalText = text;
        mText = text;
        fitText(text);
    }

    //region alignment
    public Layout.Alignment getLayoutAlignment() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Layout.Alignment.ALIGN_NORMAL;
        }

        Layout.Alignment alignment;
        switch (getTextAlignment()) {
            case TextView.TEXT_ALIGNMENT_GRAVITY:
                switch (getGravity() & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
                    case Gravity.START:
                        alignment = Layout.Alignment.ALIGN_NORMAL;
                        break;
                    case Gravity.END:
                        alignment = Layout.Alignment.ALIGN_OPPOSITE;
                        break;
                    case Gravity.LEFT:
                        alignment = (getLayoutDirection() == TextView.LAYOUT_DIRECTION_RTL) ? Layout.Alignment.ALIGN_OPPOSITE
                                : Layout.Alignment.ALIGN_NORMAL;
                        break;
                    case Gravity.RIGHT:
                        alignment = (getLayoutDirection() == TextView.LAYOUT_DIRECTION_RTL) ? Layout.Alignment.ALIGN_NORMAL
                                : Layout.Alignment.ALIGN_OPPOSITE;
                        break;
                    case Gravity.CENTER_HORIZONTAL:
                        alignment = Layout.Alignment.ALIGN_CENTER;
                        break;
                    default:
                        alignment = Layout.Alignment.ALIGN_NORMAL;
                        break;
                }
                break;
            case TextView.TEXT_ALIGNMENT_TEXT_START:
                alignment = Layout.Alignment.ALIGN_NORMAL;
                break;
            case TextView.TEXT_ALIGNMENT_TEXT_END:
                alignment = Layout.Alignment.ALIGN_OPPOSITE;
                break;
            case TextView.TEXT_ALIGNMENT_CENTER:
                alignment = Layout.Alignment.ALIGN_CENTER;
                break;
            case TextView.TEXT_ALIGNMENT_VIEW_START:
                alignment = Layout.Alignment.ALIGN_NORMAL;
                break;
            case TextView.TEXT_ALIGNMENT_VIEW_END:
                alignment = Layout.Alignment.ALIGN_OPPOSITE;
                break;
            case TextView.TEXT_ALIGNMENT_INHERIT:
                //
            default:
                alignment = Layout.Alignment.ALIGN_NORMAL;
                break;
        }
        return alignment;
    }
    //endregion

    private void fitText(CharSequence text) {
        if (!mNeedFit) {
            return;
        }
        if (!mMeasured || mFittingText || mSingleLine || TextUtils.isEmpty(text))
            return;
        mFittingText = true;
        TextPaint oldPaint = getPaint();
        float size = fitTextSize(oldPaint, text, mMaxTextSize, mMinTextSize);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        setText(getLineBreaks(text, getPaint()));
        mFittingText = false;
    }

    private StaticLayout getLayout(CharSequence text, TextPaint paint) {
        mLayout = new StaticLayout(text, paint, getTextWidth(),
                getLayoutAlignment(), getLineSpacingMultiplierCompat(),
                getLineSpacingExtraCompat(), getIncludeFontPaddingCompat());
        return mLayout;
    }

    private StaticLayout getStaticLayout() {
        mLayout = new StaticLayout(mText, mPaint, getTextWidth(),
                getLayoutAlignment(), getLineSpacingMultiplierCompat(),
                getLineSpacingExtraCompat(), getIncludeFontPaddingCompat());
        return mLayout;
    }

    private float fitTextSize(TextPaint oldPaint, CharSequence text, float max, float min) {
        if (TextUtils.isEmpty(text)) {
            if (oldPaint != null) {
                return oldPaint.getTextSize();
            }
            return getTextSize();
        }
        float low = min;
        float high = max;
        TextPaint paint = new TextPaint(oldPaint);
        while (Math.abs(high - low) > LIMIT) {
            paint.setTextSize((low + high) / 2.0f);
            if (isFit(getLineBreaks(text, paint), paint)) {
                low = paint.getTextSize();
            } else {
                high = paint.getTextSize();
            }
        }
        return low;
    }

    private boolean isFit(CharSequence text, TextPaint paint) {
        // 自动换行
        boolean mSingleLine = isSingleLine();
        int maxLines = getMaxLines();
        float multi = getLineSpacingMultiplierCompat();
        float space = getLineSpacingExtraCompat();
        space = space * multi;
        int height = getTextHeight();
        if (!mSingleLine) {
            height += Math.round(space);
        }

        int lines = mSingleLine ? 1 : Math.max(1, maxLines);
        StaticLayout layout = getLayout(text, paint);
        return layout.getLineCount() <= lines && layout.getHeight() <= height;
    }

    /**
     * 拆入换行符，解决中英文的换行问题
     *
     * @param text  内容
     * @param paint 画笔
     * @return 调整后的内容
     */
    private CharSequence getLineBreaks(CharSequence text, TextPaint paint) {
        int width = getTextWidth();
        boolean keepWord = isKeepWord();
        if (width <= 0 || keepWord)
            return text;
        int length = text.length();
        int start = 0, end = 1;

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        while (end <= length) {
            CharSequence c = text.subSequence(end - 1, end);
//            char c = text.charAt(end - 1);// cs最后一个字符
//            boolean needCheck = false;
            if (TextUtils.equals(c, "\n")) {// 已经换行
                ssb.append(text, start, end);
                start = end;
//                needCheck = true;
            } else {
                float lw = paint.measureText(text, start, end);
                if (lw > width) {// 超出宽度，退回一个位置
                    ssb.append(text, start, end - 1);
                    start = end - 1;
                    if (end < length) {
                        CharSequence c2 = text.subSequence(end - 1, end);
                        if (!TextUtils.equals(c2, "\n"))
                            ssb.append('\n');
                    }
//                    needCheck = true;
                } else if (lw == width) {
                    ssb.append(text, start, end);
                    start = end;
                    if (end < length) {
                        CharSequence c2 = text.subSequence(end, end + 1);
                        if (!TextUtils.equals(c2, "\n"))
                            ssb.append('\n');
                    }
//                    needCheck = true;
                } else if (end == length) {
                    // 已经是最后一个字符
                    ssb.append(text, start, end);
                    start = end;
                }
            }
            end++;
        }
        return ssb;
    }
}
