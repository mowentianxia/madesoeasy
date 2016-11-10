package com.kk.imageeditor;
import com.kk.imageeditor.utils.XmlUtils;

import net.kk.xml.IXmlElement;
import net.kk.xml.XmlReader;
import net.kk.xml.annotations.XmlElement;
import net.kk.xml.annotations.XmlElementText;
import net.kk.xml.annotations.XmlIgnore;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class XmlTest {
    static class C extends IXmlElement{
        @XmlElementText
        String name;
        @Override
        public String toString() {
            return "c["+index+":"+name+"]";
        }
    }
    static class B extends IXmlElement{
        @XmlElementText
        String age;

        @Override
        public String toString() {
            return "b["+index+":"+age+"]";
        }
    }
    static class A{
        @XmlElement("c")
        List<C> cList;
        @XmlElement("b")
        List<B> bList;
        @XmlIgnore
        List<IXmlElement> elements;

        public void make(){
            if(elements==null){
                elements=new ArrayList<>();
                elements.addAll(cList);
                elements.addAll(bList);
                Collections.sort(elements, IXmlElement.ASC);
            }
        }

        @Override
        public String toString() {
            return "A{" +
                    "cList=" + cList +
                    ", bList=" + bList +
                    ", elements=" + elements +
                    '}';
        }
    }
    @Test
    public void testList() throws Exception {
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+
                "<a>\n"+
                "<b>1</b>"+
                "<c>b</c>"+
                "<b>3</b>"+
                "<c>d</c>"+
                "<b>2</b>"+
                "<c>a</c>"+
                "<c>e</c>"+
                "</a>";
        A a = XmlUtils.getObject(A.class, xml);
        a.make();
        System.out.print(a);
    }
}
