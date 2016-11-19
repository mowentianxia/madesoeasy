package com.kk.imageeditor.bean;

import net.kk.xml.IXmlElement;
import net.kk.xml.annotations.XmlElement;

import java.util.HashMap;
import java.util.Map;

@XmlElement("save")
public class SaveInfo extends IXmlElement{
    @XmlElement("style")
    public StyleInfo style;
    @XmlElement("data")
    public final Map<String, String> values;

    @Override
    public String toString() {
        return "SaveInfo{" +
                "style=" + style +
                ", values=" + values +
                '}';
    }

    public SaveInfo() {
        values = new HashMap<>();
    }
}
