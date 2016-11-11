package com.kk.imageeditor.bean.data;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageData extends  ViewData{
    public String file_src;
    private Drawable src;

    public ImageView.ScaleType scaleType= ImageView.ScaleType.FIT_CENTER;


    public Drawable getSrc() {
        return src;
    }

    public boolean outFile = false;

    public void setSrc(Drawable pSrc) {
        src = pSrc;
    }
}
