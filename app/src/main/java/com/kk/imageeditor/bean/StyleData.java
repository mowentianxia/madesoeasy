package com.kk.imageeditor.bean;

import com.kk.imageeditor.bean.data.BooleanElement;
import com.kk.imageeditor.bean.data.SelectElement;

import net.kk.xml.annotations.XmlElement;

import java.util.ArrayList;
import java.util.List;

@XmlElement("datas")
public class StyleData {
    @XmlElement("data")
    private final List<SelectElement> mSelectElements;

    @XmlElement("bool")
    private final List<BooleanElement> mBooleanElements;

    public StyleData() {
        mSelectElements = new ArrayList<>();
        mBooleanElements = new ArrayList<>();
    }

    public List<SelectElement> getSelectElements() {
        return mSelectElements;
    }

    public List<BooleanElement> getBooleanElements() {
        return mBooleanElements;
    }
}
