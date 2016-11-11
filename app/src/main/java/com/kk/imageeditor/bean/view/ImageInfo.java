package com.kk.imageeditor.bean.view;

import android.widget.ImageView;

import com.kk.imageeditor.bean.data.Value;

import net.kk.xml.annotations.XmlAttribute;
import net.kk.xml.annotations.XmlElement;

import java.util.Arrays;

@XmlElement("image")
public class ImageInfo extends ViewInfo {
    public ImageInfo() {
        super();
    }

    @XmlElement("src")
    protected Value[] src;
    @XmlAttribute("scaleType")
    protected ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_XY;

    public ImageView.ScaleType getScaleType() {
        return mScaleType;
    }

    public Value[] getSrc() {
        return src;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "name='" + name + "'" +
                ", sizes='" + Arrays.toString(sizes) + '\'' +
                ", gravity='" + Arrays.toString(gravityStr) + '\'' +
                ", visible='" + visible + "'" +
                ", index=" + index +
                ", click=" + clickName +
                ", src=" + Arrays.toString(src) +
                ", mScaleType=" + mScaleType +
                '}';
    }
}
