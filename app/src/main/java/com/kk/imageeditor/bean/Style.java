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
    @XmlElement("layout")
    private LayoutInfo mLayoutInfo;

    @XmlAttribute("width")
    private int width;

    @XmlAttribute("height")
    private int height;

    @XmlAttribute("savename")
    private String savename;

    @XmlIgnore
    private StyleInfo info;

    @XmlIgnore
    private StyleData data;

    @XmlIgnore
    private float scale;

    public Style() {
    }

    public boolean isEmpty() {
        return mLayoutInfo == null;
    }

    public String getSavename() {
        return savename;
    }

    public StyleInfo getInfo() {
        return info;
    }

    public StyleInfo getStyleInfo(){
        return info;
    }

    public String getDataFile(){
        return info.getDataPath();
    }

    public List<BooleanElement> getBooleanElements() {
        return data.getBooleanElements();
    }

    public List<SelectElement> getSelectElements() {
        return data.getSelectElements();
    }

    public SelectElement getDataElement(String name) {
        List<SelectElement> selectElements = getSelectElements();
        for (SelectElement selectElement : selectElements) {
            if (TextUtils.equals(name, selectElement.getName())) {
                return selectElement;
            }
        }
        return null;
    }

    public void setInfo(StyleInfo info) {
        this.info = info;
    }

    public StyleData getData() {
        return data;
    }

    public void setData(StyleData data) {
        this.data = data;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float pScale) {
        pScale = (int) (pScale * 10) / 10.0f;
        scale = pScale;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public LayoutInfo getLayoutInfo() {
        return mLayoutInfo;
    }

    @Override
    public String toString() {
        return "Style{" +
                "mLayoutInfo=" + mLayoutInfo +
                ", width=" + width +
                ", height=" + height +
                ", savename='" + savename + '\'' +
                '}';
    }
}
