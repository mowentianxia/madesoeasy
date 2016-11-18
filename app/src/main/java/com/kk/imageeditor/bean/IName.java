package com.kk.imageeditor.bean;

import net.kk.xml.annotations.XmlAttribute;
import net.kk.xml.IXmlElement;

public class IName extends IXmlElement {
    @XmlAttribute("name")
    protected String name;

    public String getName() {
        return name;
    }
}
