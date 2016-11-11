package com.kk.imageeditor.bean.data;

import android.view.ViewGroup;

import net.kk.xml.annotations.XmlAttribute;

public class Size extends When<Size> {
    @XmlAttribute("left")
    public float left;
    @XmlAttribute("top")
    public float top;
    @XmlAttribute("right")
    public float right;
    @XmlAttribute("bottom")
    public float bottom;
    @XmlAttribute("width")
    public float width = ViewGroup.LayoutParams.WRAP_CONTENT;
    @XmlAttribute("height")
    public float height = ViewGroup.LayoutParams.WRAP_CONTENT;
    @XmlAttribute("weight")
    public int weight;

    @Override
    public Size get() {
        return this;
    }

    @Override
    public String toString() {
        return "Size{" +
                "bottom=" + bottom +
                ", left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", width=" + width +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Size size = (Size) o;

        if (Float.compare(size.left, left) != 0) return false;
        if (Float.compare(size.top, top) != 0) return false;
        if (Float.compare(size.width, width) != 0) return false;
        if (Float.compare(size.height, height) != 0) return false;
        return weight == size.weight;

    }

    @Override
    public int hashCode() {
        int result = (left != +0.0f ? Float.floatToIntBits(left) : 0);
        result = 31 * result + (top != +0.0f ? Float.floatToIntBits(top) : 0);
        result = 31 * result + (width != +0.0f ? Float.floatToIntBits(width) : 0);
        result = 31 * result + (height != +0.0f ? Float.floatToIntBits(height) : 0);
        result = 31 * result + weight;
        return result;
    }
}