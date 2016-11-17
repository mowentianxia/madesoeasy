package com.kk.imageeditor.bean.data;

import com.kk.imageeditor.bean.IName;
import com.kk.imageeditor.bean.enums.DataType;

import net.kk.xml.annotations.XmlAttribute;
import net.kk.xml.annotations.XmlElement;

import java.util.ArrayList;
import java.util.List;

public class SelectElement extends IName {
    public SelectElement() {
        items = new ArrayList<>();
    }

    /**
     * 默认值
     */
    @XmlAttribute("default")
    protected String defaultvalue;

    @XmlAttribute("desc")
    protected String desc;

    @XmlAttribute("type")
    protected DataType type = DataType.select;

    @XmlElement("hide")
    private boolean hide = false;

    @XmlElement("item")
    protected final List<SubItem> items;

    public String getDefault() {
        return defaultvalue;
    }

    public List<SubItem> getItems() {
        return items;
    }

    public DataType getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isHide() {
        return hide;
    }


    @Override
    public String toString() {
        return "SelectElement{" +
                "defaultvalue='" + defaultvalue + '\'' +
                ", name='" + name + "'" +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                ", hide=" + hide +
                ", items=" + items +
                "}\n";
    }
}