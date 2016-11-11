package com.kk.imageeditor.bean.data;


import net.kk.xml.annotations.XmlAttribute;

public class ShadowInfo extends When<ShadowInfo> {
    @XmlAttribute("color")
    public String color;//阴影的颜色
    @XmlAttribute("dx")
    public float dx;//水平方向上的偏移量
    @XmlAttribute("dy")
    public float dy;//垂直方向上的偏移量
    @XmlAttribute("radius")
    public float radius;//是阴影的的半径大少

    @Override
    public ShadowInfo get() {
        return this;
    }
}
