package com.kk.imageeditor.bean;

import net.kk.xml.IXmlElement;
import net.kk.xml.annotations.XmlAttribute;

public class IName extends IXmlElement {
    @XmlAttribute("name")
    protected String name;

    public String getName() {
        return name;
    }
}
