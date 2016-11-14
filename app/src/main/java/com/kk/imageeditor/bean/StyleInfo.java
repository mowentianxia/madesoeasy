package com.kk.imageeditor.bean;

import net.kk.xml.IXmlElement;
import net.kk.xml.annotations.XmlElement;

@XmlElement("styleinfo")
public class StyleInfo extends IXmlElement {
    @XmlElement("name")
    private String name;
    @XmlElement("desc")
    private String desc;
    @XmlElement("author")
    private String author;
    @XmlElement("version")
    private long version = 0;
    @XmlElement("app-version")
    private long appversion = -1;
    @XmlElement("icon")
    private String icon;

    /**
     * style file path
     */
    @XmlElement("url")
    private String filepath;

    public String getFilepath() {
        return filepath;
    }

    public StyleInfo() {
        author = "author name";
        name = "style name";
    }

    public long getAppversion() {
        return appversion;
    }

    public String getAuthor() {
        return author;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public long getVersion() {
        return version;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "StyleInfo{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", author='" + author + '\'' +
                ", version=" + version +
                ", appversion=" + appversion +
                ", icon='" + icon + '\'' +
                ", filepath='" + filepath + '\'' +
                '}';
    }
}