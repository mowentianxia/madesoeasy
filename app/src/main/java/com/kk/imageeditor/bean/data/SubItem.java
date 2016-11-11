package com.kk.imageeditor.bean.data;

import net.kk.xml.annotations.XmlAttribute;

public class SubItem {
    @XmlAttribute("name")
    protected String name;
    @XmlAttribute("value")
    protected String value;

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public SubItem(){

    }

    public SubItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "SubItem{" +
                "value='" + value + "'," +
                "name='" + name + '\'' +
                '}';
    }
}
