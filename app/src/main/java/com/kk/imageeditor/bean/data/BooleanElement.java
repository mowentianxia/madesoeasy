package com.kk.imageeditor.bean.data;


import net.kk.xml.annotations.XmlAttribute;

public class BooleanElement extends SubItem {

    @XmlAttribute("default")
    protected String defaultvalue;

    public String getDefault() {
        return defaultvalue;
    }

    @Override
    public String toString() {
        return "BooleanElement{" +

                "defaultvalue='" + defaultvalue + '\'' +
                ", name='" + name + "'" +
                ", value='" + value + "'" +
                '}';
    }
}
