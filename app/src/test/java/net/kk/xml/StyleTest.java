package net.kk.xml;

import com.kk.imageeditor.bean.Style;
import com.kk.imageeditor.bean.data.BooleanElement;


import net.kk.xml.annotations.XmlElement;
import net.kk.xml.bean.TagObject;

import org.junit.Test;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.util.List;

public class StyleTest {
    static class Bool {
        @XmlElement("bool")
        List<BooleanElement> bools;

        @Override
        public String toString() {
            return "Bool{" +
                    "bools=" + bools +
                    '}';
        }
    }

    @Test
    public void testbool() throws Exception {
        String xml = "\t<bool name=\"image_is_normal\" \tvalue=\"'{{image_type}}' == ''\"/>\n";

        XmlOptions DEFAULT = new XmlOptions.Builder()
                .enableSameAsList()
                .ignoreNoAnnotation()
                .dontUseSetMethod()
                .useSpace()
                .build();
        XmlReader reader = new XmlReader(XmlPullParserFactory.newInstance().newPullParser(), DEFAULT);
        TagObject root = reader.parseTags(new ByteArrayInputStream(xml.getBytes()), null);
        System.out.println("\r" + root);
        BooleanElement a = reader.fromTag(BooleanElement.class, root);
        System.out.println("\r" + a);

    }


    @Test
    public void test() throws Exception {
        XmlOptions DEFAULT = new XmlOptions.Builder()
                .enableSameAsList()
                .ignoreNoAnnotation()
                .dontUseSetMethod()
                .useSpace()
                .build();
        XmlReader reader = new XmlReader(XmlPullParserFactory.newInstance().newPullParser(), DEFAULT);
        TagObject root = reader.parseTags(new ByteArrayInputStream(BOOL.getBytes()), null);
        System.out.println("\r" + root);
        Bool a = reader.fromTag(Bool.class, root);
        System.out.println("\r" + a);
    }

    @Test
    public void testStyle() throws Exception {
        XmlOptions DEFAULT = new XmlOptions.Builder()
                .enableSameAsList()
                .ignoreNoAnnotation()
                .dontUseSetMethod()
                .useSpace()
                .build();
        XmlReader reader = new XmlReader(XmlPullParserFactory.newInstance().newPullParser(), DEFAULT);
        TagObject root = reader.parseTags(new ByteArrayInputStream(XML.getBytes()), null);
//        System.out.println("\r" + root);
        Style a = reader.fromTag(Style.class, root);
        System.out.println("\r" + a.getLayoutInfo());
    }
    static final String BOOL = "<root>" + "\t<bool name=\"cardborder_is_gold\" value=\"'{{cardborder}}' == 'gold'\"/>\n" +
            "\t<bool name=\"is_pendulum\" \t\tvalue=\"'{{pendulum}}' == 'big' or '{{pendulum}}' == 'normal' or '{{pendulum}}' == 'small'\"/>\n" +
            "\t<bool name=\"image_is_normal\" \tvalue=\"'{{image_type}}' == ''\"/>\n" +
            "\t<bool name=\"image_is_noframe\" \tvalue=\"'{{image_type}}' == 'noframe'\"/>\n" +
            "\t<bool name=\"image_is_full\" \t\tvalue=\"'{{image_type}}' == 'full'\"/>\n" +
            "\t<bool name=\"pimage_is_normal\" \tvalue=\"'{{pimagetype}}' != 'rect'\"/>\n" +
            "\t<bool name=\"pimage_is_rect\" \tvalue=\"'{{pimagetype}}' == 'rect'\"/>\n" +
            "\t<bool name=\"pendulum_is_normal\" value=\"'{{pendulum}}' == 'normal'\"/>\n" +
            "\t<bool name=\"pendulum_is_big\" \tvalue=\"'{{pendulum}}' == 'big'\"/>\n" +
            "\t<bool name=\"pendulum_is_small\" \tvalue=\"'{{pendulum}}' == 'small'\"/>\n" +
            "\t<bool name=\"pendulum_nots_big\" \tvalue=\"{{pendulum_is_small}} or {{pendulum_is_normal}}\"/>\n" +
            "\t<bool name=\"pendulum_nots_small\" value=\"{{pendulum_is_normal}} or {{pendulum_is_big}}\"/>\n" +
            "\t<bool name=\"is_spelltrap\" \t\tvalue=\"'{{cardtype}}' == 'spell' or '{{cardtype}}' == 'trap'\" />\n" +
            "\t<bool name=\"rare_type_is_normal\" value=\"'{{rare_type}}' == ''\"/>\n" +
            "\t<bool name=\"rare_type_is_middle\" value=\"'{{rare_type}}' == 'middle'\"/>\n" +
            "\t<bool name=\"rare_type_is_full\" \tvalue=\"'{{rare_type}}' == 'full'\"/>\n" +
            "\t<bool name=\"card_is_syz\" \t\tvalue=\"'{{cardtype}}' == 'xyz'\"/>\n" +
            "\t<bool name=\"card_name_is_white\" value=\"{{is_spelltrap}} or {{card_is_syz}}\"/>\n" +
            "\t<bool name=\"card_is_black\" \t\tvalue=\"{{card_is_syz}}\"/>\n" +
            "\t<bool name=\"has_name_color\" \tvalue=\"'{{namecolor}}' != ''\"/>\n"
            + "</root>";
    static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<style width=\"421\" height=\"610\" savename=\"{{cardname}}\">\n" +
            "\t<layout>\n" +
            "\t\t<size left=\"0\" top=\"0\" width=\"421\" height=\"610\"/>\n" +
            "\t\t<image index=\"-1\" click=\"cardbg\">\n" +
            "\t\t\t<size left=\"0\" top=\"0\" width=\"421\" height=\"610\"/>\n" +
            "\t\t\t<src>bg/card-{{cardtype}}.png</src>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 黄金边框 -->\n" +
            "\t\t<image index=\"2\">\n" +
            "\t\t\t<size left=\"0\" top=\"0\" width=\"421\" height=\"610\"/>\n" +
            "\t\t\t<src>foil/border.png</src>\n" +
            "\t\t\t<visible>{{cardborder_is_gold}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 摇摆背景 -->\n" +
            "\t\t<image index=\"-1\">\n" +
            "\t\t\t<size left=\"13.5\" top=\"272\" width=\"394\" height=\"325\"/>\n" +
            "\t\t\t<src>pendulum/pendulum.png</src>\n" +
            "\t\t\t<visible>{{is_pendulum}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 摇摆背景 -->\n" +
            "\t\t<image index=\"-1\">\n" +
            "\t\t\t<size left=\"20\" top=\"89\" width=\"381\" height=\"17\"/>\n" +
            "\t\t\t<src>pendulum/maxLines-{{cardtype}}.png</src>\n" +
            "\t\t\t<visible>{{is_pendulum}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- P怪中间背景图白色 -->\n" +
            "\t\t<image index=\"-1\" background=\"#ffffff\">\n" +
            "\t\t\t<size left=\"30\" top=\"106\" width=\"363\" height=\"360\"/>\n" +
            "\t\t\t<visible>{{is_pendulum}} and {{pimage_is_rect}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 中间图-->\n" +
            "\t\t<image index=\"-1\" click=\"cardcode_image\" background=\"#ffffff\">\n" +
            "\t\t\t<size when=\"{{pimage_is_normal}} and {{pendulum_nots_big}}\"\n" +
            "\t\t\t\tleft=\"29.5\" top=\"105.5\" width=\"363\" height=\"345\"/>\n" +
            "\t\t\t<size when=\"{{pimage_is_normal}} and {{pendulum_is_big}}\"\n" +
            "\t\t\t\tleft=\"29.5\" top=\"105.5\" width=\"363\" height=\"360\"/>\n" +
            "\t\t\t<size when=\"{{pimage_is_rect}} and {{pendulum_nots_small}}\"\n" +
            "\t\t\t\tleft=\"29.5\" top=\"105.5\" width=\"363\" height=\"275\"/>\n" +
            "\t\t\t<size when=\"{{pimage_is_rect}} and {{pendulum_is_small}}\"\n" +
            "\t\t\t\tleft=\"29.5\" top=\"105.5\" width=\"363\" height=\"298\"/>\n" +
            "\t\t\t<size when=\"{{image_is_noframe}}\"\n" +
            "\t\t\t\tleft=\"41\" top=\"103\" width=\"339\" height=\"334\"/>\n" +
            "\t\t\t<size when=\"{{image_is_full}}\"\n" +
            "\t\t\t\tleft=\"14\" top=\"14\" width=\"393\" height=\"582\"/>\n" +
            "\t\t\t<size left=\"51.5\" top=\"112\" width=\"319.5\" height=\"319\"/>\n" +
            "\t\t\t<src>{{cardcode_image}}</src>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 中间图镀膜 -->\n" +
            "\t\t<image index=\"3\">\n" +
            "\t\t\t<size when=\"{{rare_type_is_full}}\"\n" +
            "\t\t\t\tleft=\"0\" top =\"0\" width=\"421\" height=\"610\" />\n" +
            "\t\t\t<size when=\"{{image_is_full}}\"\n" +
            "\t\t\t\tleft=\"14\" top=\"14\" width=\"393\" height=\"582\"/>\n" +
            "\t\t\t<size when=\"{{pendulum_nots_small}}\"\n" +
            "\t\t\t\tleft=\"29.5\" top=\"105.5\" width=\"363\" height=\"275\" />\n" +
            "\t\t\t<size when=\"{{pendulum_is_small}}\"\n" +
            "\t\t\t\tleft=\"29.5\" top=\"105.5\" width=\"363\" height=\"294\" />\n" +
            "\t\t\t<size when=\"{{image_is_noframe}}\"\n" +
            "\t\t\t\tleft=\"41\" top=\"103\" width=\"339\" height=\"334\" />\n" +
            "\t\t\t<size left=\"51.5\" top=\"112\" width=\"319.5\" height=\"318\" />\n" +
            "\t\t\t<src>foil/{{cardrare}}.png</src>\n" +
            "\t\t\t<visible>{{rare_type_is_normal}} == false</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 卡名背景（透明） -->\n" +
            "\t\t<image index=\"1\">\n" +
            "\t\t\t<size left=\"18\" top =\"25\" width=\"382\" height=\"50\"/>\n" +
            "\t\t\t<src>name.png</src>\n" +
            "\t\t\t<visible>{{image_is_full}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 效果背景（透明） -->\n" +
            "\t\t<image index=\"1\">\n" +
            "\t\t\t<size left=\"23\" top =\"451\" width=\"373\" height=\"128\"/>\n" +
            "\t\t\t<src>desc.png</src>\n" +
            "\t\t\t<visible>{{image_is_full}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 分割线（透明） -->\n" +
            "\t\t<image index=\"1\">\n" +
            "\t\t\t<size left=\"35\" top =\"553\" width=\"350\" height=\"1\" />\n" +
            "\t\t\t<src>bar.png</src>\n" +
            "\t\t\t<visible>{{is_spelltrap}} == false and {{image_is_full}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- ATK（透明） -->\n" +
            "\t\t<text index=\"2\" singleline=\"true\">\n" +
            "\t\t\t<size left=\"226\" top=\"554\" width=\"56\" height=\"21\"/>\n" +
            "\t\t\t<align>vcenter</align>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font name=\"font/MatrixBoldSmallCaps.ttf\" style=\"normal\" size=\"18.5\"/>\n" +
            "\t\t\t<text>ATK/</text>\n" +
            "\t\t\t<visible>{{is_spelltrap}} == false and {{image_is_full}}</visible>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- DEF（透明） -->\n" +
            "\t\t<text index=\"2\" singleline=\"true\">\n" +
            "\t\t\t<size left=\"313\" top=\"554\" width=\"56\" height=\"21\"/>\n" +
            "\t\t\t<align>vcenter</align>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font name=\"font/MatrixBoldSmallCaps.ttf\" style=\"normal\" size=\"18.5\"/>\n" +
            "\t\t\t<text>DEF/</text>\n" +
            "\t\t\t<visible>{{is_spelltrap}} == false and {{image_is_full}}</visible>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- 中间图金边 -->\n" +
            "\t\t<image index=\"1\">\n" +
            "\t\t\t<size left=\"45\" top=\"106\" width=\"333\" height=\"331\" />\n" +
            "\t\t\t<src>foil/image.png</src>\n" +
            "\t\t\t<visible>{{is_pendulum}} == false and {{cardborder_is_gold}} and {{image_is_normal}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- P怪边框 镀膜 -->\n" +
            "\t\t<image index=\"1\">\n" +
            "\t\t\t<size left=\"20\" top=\"99\" width=\"382\" height=\"485\"/>\n" +
            "\t\t\t<src>pendulum/pendulum_{{pendulum}}.png</src>\n" +
            "\t\t\t<visible>{{is_pendulum}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- P怪边框（金边） 镀膜 -->\n" +
            "\t\t<image index=\"1\">\n" +
            "\t\t\t<size left=\"26\" top=\"103.5\" width=\"373\" height=\"476\"/>\n" +
            "\t\t\t<src>foil/pend_{{pendulum}}.png</src>\n" +
            "\t\t\t<visible>{{is_pendulum}} and {{cardborder_is_gold}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 卡名 -->\n" +
            "\t\t<text index=\"2\" singleline=\"true\" click=\"cardname\">\n" +
            "\t\t\t<size left=\"31.5\" top=\"30\" width=\"318\" height=\"36\"/>\n" +
            "\t\t\t<align>vcenter</align>\n" +
            "\t\t\t<color when=\"{{has_name_color}}\">{{namecolor}}</color>\n" +
            "\t\t\t<color when=\"{{card_name_is_white}}\">#ffffff</color>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font name=\"font/YGODIY_Chinese.ttf\" style=\"normal\" size=\"30\"/>\n" +
            "\t\t\t<text>{{cardname}}</text>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- 属性 -->\n" +
            "\t\t<image index=\"2\" click=\"cardattr\">\n" +
            "\t\t\t<size left=\"350\" top=\"29\" width=\"38\" height=\"38\"/>\n" +
            "\t\t\t<src when=\"'{{cardtype}}' == 'spell'\">attribute/spell_cn.png</src>\n" +
            "\t\t\t<src when=\"'{{cardtype}}' == 'trap'\">attribute/trap_cn.png</src>\n" +
            "\t\t\t<src>attribute/{{cardattr}}_cn.png</src>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 等级 -->\n" +
            "\t\t<image index=\"2\" scaleType=\"FIT_CENTER\" click=\"cardlevel\">\n" +
            "\t\t\t<size left=\"0\" top=\"75\" width=\"421\" height=\"25\"/>\n" +
            "\t\t\t<src when=\"{{card_is_syz}}\">rank/rank-{{cardlevel}}.png</src>\n" +
            "\t\t\t<src>level/level-{{cardlevel}}.png</src>\n" +
            "\t\t\t<visible>{{is_spelltrap}} == false</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 魔法标志 -->\n" +
            "\t\t<image index=\"2\" scaleType=\"FIT_CENTER\" click=\"stmark\">\n" +
            "\t\t\t<size left=\"245\" top=\"75\" width=\"132\" height=\"24\"/>\n" +
            "\t\t\t<src>jpmark/{{stmark}}.png</src>\n" +
            "\t\t\t<visible>{{is_spelltrap}}</visible>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- 种族/效果/效果/效果 -->\n" +
            "\t\t<layout index=\"2\" type=\"horizontal\" click=\"cardmonster\">\n" +
            "\t\t\t<size when=\"{{pendulum_is_big}}\"\n" +
            "\t\t\t\tleft=\"25\" top=\"475\" width=\"300\" height=\"24\"/>\n" +
            "\t\t\t<size left=\"25\" top=\"460\" width=\"300\" height=\"24\"/>\n" +
            "\t\t\t<text>\n" +
            "\t\t\t\t<align>vcenter</align>\n" +
            "\t\t\t\t<color>#000000</color>\n" +
            "\t\t\t\t<font name=\"font/YGODIY_Chinese.ttf\" style=\"normal\" size=\"18\"/>\n" +
            "\t\t\t\t<text>【</text>\n" +
            "\t\t\t</text>\n" +
            "\t\t\t<text>\n" +
            "\t\t\t\t<align>vcenter</align>\n" +
            "\t\t\t\t<color>#000000</color>\n" +
            "\t\t\t\t<font name=\"font/YGODIY_Chinese.ttf\" style=\"normal\" size=\"18\"/>\n" +
            "\t\t\t\t<text>{{race}}</text>\n" +
            "\t\t\t</text>\n" +
            "\t\t\t<text>\n" +
            "\t\t\t\t<align>vcenter</align>\n" +
            "\t\t\t\t<color>#000000</color>\n" +
            "\t\t\t\t<font name=\"font/YGODIY_Chinese.ttf\" style=\"normal\" size=\"18\"/>\n" +
            "\t\t\t\t<text>/{{effect1}}</text>\n" +
            "\t\t\t\t<visible>'{{effect1}}' != ''</visible>\n" +
            "\t\t\t</text>\n" +
            "\t\t\t<text>\n" +
            "\t\t\t\t<align>vcenter</align>\n" +
            "\t\t\t\t<color>#000000</color>\n" +
            "\t\t\t\t<font name=\"font/YGODIY_Chinese.ttf\" style=\"normal\" size=\"18\"/>\n" +
            "\t\t\t\t<text>/{{effect2}}</text>\n" +
            "\t\t\t\t<visible>'{{effect2}}' != ''</visible>\n" +
            "\t\t\t</text>\n" +
            "\t\t\t<text>\n" +
            "\t\t\t\t<align>vcenter</align>\n" +
            "\t\t\t\t<color>#000000</color>\n" +
            "\t\t\t\t<font name=\"font/YGODIY_Chinese.ttf\" style=\"normal\" size=\"18\"/>\n" +
            "\t\t\t\t<text>/{{effect3}}</text>\n" +
            "\t\t\t\t<visible>'{{effect3}}' != ''</visible>\n" +
            "\t\t\t</text>\n" +
            "\t\t\t<text>\n" +
            "\t\t\t\t<align>vcenter</align>\n" +
            "\t\t\t\t<color>#000000</color>\n" +
            "\t\t\t\t<font name=\"font/YGODIY_Chinese.ttf\" style=\"normal\" size=\"18\"/>\n" +
            "\t\t\t\t<text>】</text>\n" +
            "\t\t\t</text>\n" +
            "\t\t\t<visible>{{is_spelltrap}} == false</visible>\n" +
            "\t\t</layout>\n" +
            "\t\t<!-- ATK -->\n" +
            "\t\t<text index=\"2\" singleline=\"true\" click=\"cardatk\">\n" +
            "\t\t\t<size left=\"262\" top=\"555\" width=\"40\" height=\"20\"/>\n" +
            "\t\t\t<align>right|vcenter</align>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font when=\"{{cardatk}} == 0 and '{{cardatk}}' != '0'\"\n" +
            "\t\t\t\tname=\"font/ITCStoneSerif.ttf\" style=\"bold\" size=\"15\"/>\n" +
            "\t\t\t<font name=\"font/MatrixBoldSmallCaps.ttf\" style=\"normal\" size=\"18.5\"/>\n" +
            "\t\t\t<text>{{cardatk}}</text>\n" +
            "\t\t\t<visible>{{is_spelltrap}} == false</visible>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- DEF -->\n" +
            "\t\t<text index=\"2\" singleline=\"true\" click=\"carddef\">\n" +
            "\t\t\t<size left=\"346\" top=\"555\" width=\"40\" height=\"20\"/>\n" +
            "\t\t\t<align>right|vcenter</align>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font when=\"{{carddef}} == 0 and '{{carddef}}' != '0'\"\n" +
            "\t\t\t\tname=\"font/ITCStoneSerif.ttf\" style=\"bold\" size=\"15\"/>\n" +
            "\t\t\t<font name=\"font/MatrixBoldSmallCaps.ttf\" style=\"normal\" size=\"18.5\"/>\n" +
            "\t\t\t<text>{{carddef}}</text>\n" +
            "\t\t\t<visible>{{is_spelltrap}} == false</visible>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- P怪 左刻度 -->\n" +
            "\t\t<text index=\"2\" click=\"cardpleft\">\n" +
            "\t\t\t<size when=\"{{pendulum_is_big}}\"\n" +
            "\t\t\t\tleft=\"31\" top=\"422\" width=\"30\" height=\"36\"/>\n" +
            "\t\t\t<size when=\"{{pendulum_is_small}}\"\n" +
            "\t\t\t\tleft=\"31\" top=\"420\" width=\"30\" height=\"36\"/>\n" +
            "\t\t\t<size\n" +
            "\t\t\t\tleft=\"31\" top=\"413\" width=\"30\" height=\"36\"/>\n" +
            "\t\t\t<align>center</align>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font name=\"font/MatrixBoldSmallCaps.ttf\" style=\"normal\" size=\"27\"/>\n" +
            "\t\t\t<text>{{cardpleft}}</text>\n" +
            "\t\t\t<visible>{{is_pendulum}}</visible>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- P怪 右刻度 -->\n" +
            "\t\t<text index=\"2\" click=\"cardpright\">\n" +
            "\t\t\t<size when=\"{{pendulum_is_big}}\"\n" +
            "\t\t\t\tleft=\"362\" top=\"422\" width=\"30\" height=\"36\"/>\n" +
            "\t\t\t<size when=\"{{pendulum_is_small}}\"\n" +
            "\t\t\t\tleft=\"362\" top=\"420\" width=\"30\" height=\"36\"/>\n" +
            "\t\t\t<size left=\"362\" top=\"413\" width=\"30\" height=\"36\"/>\n" +
            "\t\t\t<align>center</align>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font name=\"font/MatrixBoldSmallCaps.ttf\" style=\"normal\" size=\"27\"/>\n" +
            "\t\t\t<text>{{cardpright}}</text>\n" +
            "\t\t\t<visible>{{is_pendulum}}</visible>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- 卡片密码 -->\n" +
            "\t\t<text index=\"2\" singleline=\"true\" click=\"cardcode\">\n" +
            "\t\t\t<size left=\"19\" top=\"580\" width=\"60\" height=\"17\"/>\n" +
            "\t\t\t<align>vcenter</align>\n" +
            "\t\t\t<color when=\"{{card_is_black}}\">#ffffff</color>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font name=\"font/ITCStoneSerif.ttf\" style=\"normal\" size=\"11\"/>\n" +
            "\t\t\t<text>{{cardcode}}</text>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- 版权图片 -->\n" +
            "\t\t<image index=\"2\" scaleType=\"FIT_CENTER\" click=\"cardfoil\">\n" +
            "\t\t\t<size left=\"224\" top=\"583\" width=\"160\" height=\"10\"/>\n" +
            "\t\t\t<src when=\"{{card_is_black}}\">copyright/{{cardcopy}}1.png</src>\n" +
            "\t\t\t<src>copyright/{{cardcopy}}0.png</src>\n" +
            "\t\t</image>\n" +
            "\t\t<!-- P怪效果 -->\n" +
            "\t\t<text index=\"2\" maxLines-space-scale=\"1.0\"  singleline=\"false\" click=\"cardptext\" keepWord=\"false\" >\n" +
            "\t\t\t<size when=\"{{pendulum_is_small}}\"\n" +
            "\t\t\t\tleft=\"67\" top=\"405\" width=\"288\" height=\"46\"/>\n" +
            "\t\t\t<size when=\"{{pendulum_is_big}}\"\n" +
            "\t\t\t\tleft=\"67\" top=\"388\" width=\"288\" height=\"78\"/>\n" +
            "\t\t\t<size left=\"67\" top=\"388\" width=\"288\" height=\"63\"/>\n" +
            "\t\t\t<align>left</align>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font name=\"font/YGODIY_Chinese.ttf\" style=\"normal\" size=\"13\"/>\n" +
            "\t\t\t<text>{{cardptext}}</text>\n" +
            "\t\t\t<visible>{{is_pendulum}}</visible>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- 效果-->\n" +
            "\t\t<text index=\"2\" maxLines-space-scale=\"1.0\" singleline=\"false\" click=\"cardtext\"  keepWord=\"false\">\n" +
            "\t\t\t<size when=\"{{pendulum_is_big}}\"\n" +
            "\t\t\t\tleft=\"34.5\" top=\"493\" width=\"353\" height=\"58\"/>\n" +
            "\t\t\t<size when=\"{{is_spelltrap}}\"\n" +
            "\t\t\t\tleft=\"34.5\" top=\"461\" width=\"353\" height=\"110\"/>\n" +
            "\t\t\t<size left=\"34.5\" top=\"480\" width=\"353\" height=\"73\"/>\n" +
            "\t\t\t<align>left</align>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font when=\"{{is_spelltrap}}\"\n" +
            "\t\t\t\t  name=\"font/YGODIY_Chinese.ttf\" style=\"normal\" size=\"14\"/>\n" +
            "\t\t\t<font name=\"font/YGODIY_Chinese.ttf\" style=\"normal\" size=\"13\"/>\n" +
            "\t\t\t<text>{{cardtext}}</text>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- 卡包编号 -->\n" +
            "\t\t<text index=\"2\" singleline=\"true\" click=\"cardnum\">\n" +
            "\t\t\t<size when=\"{{is_pendulum}}\"\n" +
            "\t\t\t\tleft=\"37\" top=\"555\" width=\"140\" height=\"18\"/>\n" +
            "\t\t\t<size left=\"237\" top=\"436.5\" width=\"140\" height=\"18\"/>\n" +
            "\t\t\t<align  when=\"{{is_pendulum}}\">vcenter</align>\n" +
            "\t\t\t<align>right|vcenter</align>\n" +
            "\t\t\t<color when=\"{{card_is_black}}\">#ffffff</color>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font name=\"font/ITCStoneSerif.ttf\" style=\"normal\" size=\"12\"/>\n" +
            "\t\t\t<text>{{cardnum}}</text>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- edition -->\n" +
            "\t\t<text index=\"2\" singleline=\"true\" click=\"edition\">\n" +
            "\t\t\t<size left=\"78\" top=\"580\" width=\"110\" height=\"16\"/>\n" +
            "\t\t\t<align>left|vcenter</align>\n" +
            "\t\t\t<color when=\"{{card_is_black}}\">#ffffff</color>\n" +
            "\t\t\t<color>#000000</color>\n" +
            "\t\t\t<font name=\"font/ITCStoneSerif.ttf\" style=\"normal\" size=\"12\"/>\n" +
            "\t\t\t<text>{{edition}}</text>\n" +
            "\t\t</text>\n" +
            "\t\t<!-- 防伪 -->\n" +
            "\t\t<image index=\"2\" click=\"cardfoil\">\n" +
            "\t\t\t<size left=\"389\" top=\"576\" width=\"21\" height=\"21\"/>\n" +
            "\t\t\t<src>{{corner}}.png</src>\n" +
            "\t\t</image>\n" +
            "\t</layout>\n" +
            "</style>";
}
