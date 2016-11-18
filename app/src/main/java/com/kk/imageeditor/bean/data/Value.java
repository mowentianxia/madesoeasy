package com.kk.imageeditor.bean.data;


import net.kk.xml.annotations.XmlElementText;

public class Value extends When<String> {
    @XmlElementText
    protected String value;

    public String getValue() {
        return value;
    }

    public Value() {

    }

    @Override
    public String get() {
        return value;
    }

    @Override
    public String toString() {
        return ""+value;
    }
}