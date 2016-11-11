package com.kk.imageeditor.bean.data;

import android.graphics.Color;
import android.graphics.Typeface;

public class TextData extends ViewData{
    public Typeface font;
    public float font_size;
    public int fontstyle = FontInfo.Style.normal.ordinal();

    public int color;

    public int align;

    public int shadowColor= Color.TRANSPARENT;//阴影的颜色
    public float shadowDx;//水平方向上的偏移量
    public float shadowDy;//垂直方向上的偏移量
    public float shadowRadius;//是阴影的的半径大少

    public boolean singleline=true;
    public boolean keepWord = true;
    public float lineSpace;
    public float lineSpaceScale;
    public String text;
}
