package com.kk.imageeditor.bean;

import com.kk.imageeditor.bean.data.SelectElement;

import net.kk.xml.annotations.XmlElement;

import java.util.ArrayList;
import java.util.List;

@XmlElement("datas")
public class StyleData {
    @XmlElement("data")
    private final List<SelectElement> mSelectElements;

    public StyleData() {
        mSelectElements = new ArrayList<>();
    }

}
