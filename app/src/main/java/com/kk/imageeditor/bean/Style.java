package com.kk.imageeditor.bean;

import android.text.TextUtils;

import com.kk.imageeditor.bean.data.BooleanElement;
import com.kk.imageeditor.bean.data.SelectElement;
import com.kk.imageeditor.bean.view.LayoutInfo;

import net.kk.xml.IXmlElement;
import net.kk.xml.annotations.XmlAttribute;
import net.kk.xml.annotations.XmlElement;
import net.kk.xml.annotations.XmlIgnore;

import java.util.ArrayList;
import java.util.List;

@XmlElement("style")
public class Style extends IXmlElement {
    @XmlElement("styleinfo")
    private StyleInfo mStyleInfo;
    @XmlElement("layout")
    private LayoutInfo mLayoutInfo;

    @XmlElement("data")
    private final List<SelectElement> mSelectElements;

    @XmlElement("bool")
    private final List<BooleanElement> mBooleanElements;

    @XmlAttribute("width")
    private int width;

    @XmlAttribute("height")
    private int height;

    @XmlAttribute("savename")
    private String savename;

    @XmlIgnore
    private String file;
    @XmlIgnore
    private float scale = 1.0f;

    public Style() {
        mSelectElements = new ArrayList<>();
        mBooleanElements = new ArrayList<>();
    }

    public boolean isEmpty() {
        return mLayoutInfo == null;
    }

    public String getSavename() {
        return savename;
    }

    public StyleInfo getStyleInfo() {
        return mStyleInfo;
    }

    public List<BooleanElement> getBooleanElements() {
        return mBooleanElements;
    }

    public List<SelectElement> getSelectElements() {
        return mSelectElements;
    }
    public SelectElement getDataElement(int i) {
        if (i >= 0 && i < mSelectElements.size()) {
            return mSelectElements.get(i);
        }
        return null;
    }

    public SelectElement getDataElement(String name) {
        for (SelectElement element : mSelectElements) {
            if (TextUtils.equals(element.getName(), name))
                return element;
        }
        return null;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String zipfile) {
        file = zipfile;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float pScale) {
        pScale = (int) (pScale * 10) / 10.0f;
//        pScale = ((int) (pScale / 0.05f)) * 0.05f;
        scale = pScale;
    }

    public LayoutInfo getLayoutInfo() {
        return mLayoutInfo;
    }

    @Override
    public String toString() {
        return "StyleElement{" +
                "mStyle=" + mStyleInfo +
                ", mLayoutInfo=" + mLayoutInfo +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
