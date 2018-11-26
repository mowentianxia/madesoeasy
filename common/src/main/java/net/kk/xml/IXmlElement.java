package net.kk.xml;

import java.util.Comparator;

import net.kk.xml.annotations.XmlIgnore;

public class IXmlElement {
    public static final String FIELD_POS = "pos";
    /** 同级元素的位置 */
    @XmlIgnore
    protected int pos;

    public int getPos() {
        return pos;
    }
    public static final Comparator<Object> ASC= new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            if(o1 instanceof IXmlElement && o2 instanceof IXmlElement){
                IXmlElement e1 = (IXmlElement)o1;
                IXmlElement e2 = (IXmlElement)o2;
                return e1.getPos() - e2.getPos();
            }
            return 0;
        }
    };
}
