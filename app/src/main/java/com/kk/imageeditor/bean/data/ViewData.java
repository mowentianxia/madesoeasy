package com.kk.imageeditor.bean.data;

import android.graphics.drawable.Drawable;

public class ViewData {

    public float left;
    public float top;
    public float right;
    public float bottom;
    public float width;
    public float height;

    public int weight;

    public int gravity;

    public int index;

    public boolean visible;

    public boolean needRotate;
    public float angle;

    public String file_background;
    private Drawable background;

    public Drawable getBackground() {
        return background;
    }

    public void setBackground(Drawable pBackground) {
        background = pBackground;
    }

}
