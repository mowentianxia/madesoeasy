package com.kk.imageeditor.bean.view;


import com.kk.imageeditor.bean.enums.LayoutType;

import net.kk.xml.annotations.XmlAttribute;
import net.kk.xml.annotations.XmlElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@XmlElement("layout")
public class LayoutInfo extends ViewInfo {

    public LayoutInfo() {
        super();
    }

    @XmlAttribute("type")
    protected LayoutType layoutType = LayoutType.frame;

    @XmlElement("text")
    protected TextInfo[] mTextElements;
    @XmlElement("image")
    protected ImageInfo[] mImageElements;
    @XmlElement("layout")
    protected LayoutInfo[] mLayoutInfos;

    public List<ViewInfo> getViews() {
        ArrayList<ViewInfo> list = new ArrayList<>();
        addAll(list, mTextElements);
        addAll(list, mImageElements);
        addAll(list, mLayoutInfos);
        Collections.sort(list, ViewInfo.ACS);
        return list;
    }

    public LayoutInfo[] getLayouts() {
        return mLayoutInfos;
    }

    public TextInfo[] getTexts() {
        return mTextElements;
    }

    public ImageInfo[] getImages() {
        return mImageElements;
    }

    private void addAll(ArrayList<ViewInfo> list, ViewInfo... pViewElements) {
        if (pViewElements != null) {
            for (ViewInfo e : pViewElements) {
                list.add(e);
            }
        }
    }

    public LayoutType getLayoutType() {
        return layoutType;
    }

    @Override
    public String toString() {
        return "LayoutInfo{" +
                "name='" + name + "'," +
                "layoutType=" + layoutType +
                ", sizes='" + Arrays.toString(sizes) + '\'' +
                ", gravity='" + Arrays.toString(gravityStr) + '\'' +
                ", visible='" + visible + "'" +
                ", index=" + index +
                ", click=" + clickName +
                ", background=" + background +
                '}';
    }
}

