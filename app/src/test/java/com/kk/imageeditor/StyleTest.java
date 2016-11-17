package com.kk.imageeditor;

import com.kk.imageeditor.bean.Style;
import com.kk.imageeditor.utils.XmlUtils;

import org.junit.Test;

public class StyleTest {

    @Test
    public void test() throws Exception {
        long time = System.currentTimeMillis();
        Style style = XmlUtils.getStyleUtils().getObject(Style.class, XML);
        System.out.println((System.currentTimeMillis() - time));
        System.out.print(style.getBooleanElements());

    }

    static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<style width=\"421\" height=\"610\" savename=\"{{cardname}}\">\n" +
            "\t<styleinfo>\n" +
            "\t\t<name>yugioh-9-cn</name>\n" +
            "\t\t<desc>游戏王第九期</desc>\n" +
            "\t\t<author>菜菜</author>\n" +
            "\t\t<version>20160222</version>\n" +
            "\t\t<app-version>1</app-version>\n" +
            "\t\t<icon>icon.png</icon>\n" +
            "\t\t<url>yugioh-9.zip</url>\n" +
            "\t</styleinfo>\n" +
            "\n" +
            "\t<data name=\"image_type\" default=\"\"  desc=\"图片类型\" >\n" +
            "\t\t<item name=\"默认\" value=\"\" />\n" +
            "\t\t<item name=\"无边框\" value=\"noframe\" />\n" +
            "\t\t<item name=\"透明卡\" value=\"full\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"cardattr\" default=\"none\" desc=\"属性\" >\n" +
            "\t\t<item name=\"无\" value=\"none\" />\n" +
            "\t\t<item name=\"暗\" value=\"dark\" />\n" +
            "\t\t<item name=\"神\" value=\"divine\" />\n" +
            "\t\t<item name=\"地\" value=\"earth\" />\n" +
            "\t\t<item name=\"炎\" value=\"fire\" />\n" +
            "\t\t<item name=\"光\" value=\"light\" />\n" +
            "\t\t<item name=\"水\" value=\"water\" />\n" +
            "\t\t<item name=\"风\" value=\"wind\" />\n" +
            "\t\t<item name=\"魔法\" value=\"spell\" />\n" +
            "\t\t<item name=\"陷阱\" value=\"trap\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"cardtype\" default=\"normal\" desc=\"卡片类型\" >\n" +
            "\t\t<item name=\"无\" value=\"none\" />\n" +
            "\t\t<item name=\"通常\" value=\"normal\" />\n" +
            "\t\t<item name=\"效果\" value=\"effect\" />\n" +
            "\t\t<item name=\"融合\" value=\"fusion\" />\n" +
            "\t\t<item name=\"仪式\" value=\"ritual\" />\n" +
            "\t\t<item name=\"同调\" value=\"synchro\" />\n" +
            "\t\t<item name=\"衍生物\" value=\"token\" />\n" +
            "\t\t<item name=\"超量\" value=\"xyz\" />\n" +
            "\t\t<item name=\"魔法\" value=\"spell\" />\n" +
            "\t\t<item name=\"陷阱\" value=\"trap\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"cardname\" desc=\"卡片名称\" type=\"text\" default=\"点击输入卡片名称\" />\n" +
            "\t<data name=\"cardcode\" desc=\"卡片密码\" type=\"text\"  default=\"00000000\" />\n" +
            "\t<data name=\"cardtext\" desc=\"效果文本\" type=\"text\"  default=\"点击输入效果文本\" />\n" +
            "\t<data name=\"cardptext\" desc=\"P效果文本\" type=\"text\" default=\"点击输入P效果文本\" />\n" +
            "\t<data name=\"cardpleft\" desc=\"P蓝刻度\"  type=\"text\" default=\"0\"  />\n" +
            "\t<data name=\"cardpright\" desc=\"P红刻度\" type=\"text\"  default=\"0\"  />\n" +
            "\t<data name=\"edition\" desc=\"标注\"  type=\"text\" default=\"\" />\n" +
            "\t<data name=\"cardnum\" desc=\"卡包编号\"  type=\"text\" default=\"DT01-CN001\" />\n" +
            "\t<data name=\"cardatk\"  desc=\"攻击力\" type=\"text\" default=\"0\"  />\n" +
            "\t<data name=\"carddef\"  desc=\"防御力\" type=\"text\" default=\"0\"  />\n" +
            "\t<data name=\"cardlevel\" desc=\"卡片等级/阶级\" type=\"text\" default=\"0\" />\n" +
            "\t<data name=\"cardcode_image\" desc=\"中间图\" type=\"image\"  default=\"{{cardcode}}.png\" />\n" +
            "\n" +
            "\t<data name=\"pimagetype\" default=\"\"  desc=\"摇摆怪图片类型\">\n" +
            "\t\t<item name=\"默认\" value=\"\" />\n" +
            "\t\t<item name=\"长方形\" value=\"rect\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"cardcopy\" default=\"\"  desc=\"版权图片\">\n" +
            "\t\t<item name=\"默认\" value=\"\" />\n" +
            "\t\t<item name=\"英文\" value=\"enCopyriht\" />\n" +
            "\t\t<item name=\"日文\" value=\"jpCopyriht\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"cardborder\" default=\"\"  desc=\"边框颜色\">\n" +
            "\t\t<item name=\"默认\" value=\"\" />\n" +
            "\t\t<item name=\"金色边框\" value=\"gold\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"namecolor\" default=\"\"  desc=\"卡名颜色\">\n" +
            "\t\t<item name=\"默认\" value=\"\" />\n" +
            "\t\t<item name=\"银字\" value=\"#E6E6E6\" />\n" +
            "\t\t<item name=\"金字\" value=\"#D8C735\" />\n" +
            "\t\t<item name=\"红字\" value=\"#3C0000\" />\n" +
            "\t\t<item name=\"白字\" value=\"#FFFFFF\" />\n" +
            "\t\t<item name=\"黑字\" value=\"#000000\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"cardrare\" default=\"none\" desc=\"镀膜类型\">\n" +
            "\t\t<item name=\"默认\" value=\"none\" />\n" +
            "\t\t<item name=\"面闪\" value=\"superfoil\" />\n" +
            "\t\t<item name=\"纹理\" value=\"secretfoil\" />\n" +
            "\t\t<item name=\"竖纹\" value=\"parallelfoil\" />\n" +
            "\t\t<item name=\"十字碎点\" value=\"mosaicfoil\" />\n" +
            "\t\t<item name=\"星碎\" value=\"starfoil\" />\n" +
            "\t\t<item name=\"螺旋纹\" value=\"ultimatefoil\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"rare_type\" default=\"\"  desc=\"镀膜大小\">\n" +
            "\t\t<item name=\"无\" value=\"\" />\n" +
            "\t\t<item name=\"中间图\" value=\"middle\" />\n" +
            "\t\t<item name=\"全图\" value=\"full\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"corner\" default=\"none\"  desc=\"防伪标志\" >\n" +
            "\t\t<item name=\"无\" value=\"none\" />\n" +
            "\t\t<item name=\"默认\" value=\"cornerdefault\" />\n" +
            "\t\t<item name=\"金色\" value=\"cornerfirst\" />\n" +
            "\t\t<item name=\"银色\" value=\"cornerunlimited\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"pendulum\" default=\"none\"  desc=\"摇摆类型\">\n" +
            "\t\t<item name=\"无\" value=\"none\" />\n" +
            "\t\t<item name=\"大\" value=\"big\" />\n" +
            "\t\t<item name=\"中\" value=\"normal\" />\n" +
            "\t\t<item name=\"小\" value=\"small\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"stmark\" default=\"\"  desc=\"魔法陷阱标识\" >\n" +
            "\t\t<item name=\"无\" value=\"\" />\n" +
            "\t\t<item name=\"通常魔法\" value=\"normalspell\" />\n" +
            "\t\t<item name=\"通常陷阱\" value=\"normaltrap\" />\n" +
            "\t\t<item name=\"速攻魔法\" value=\"quickspell\" />\n" +
            "\t\t<item name=\"仪式魔法\" value=\"ritualspell\" />\n" +
            "\t\t<item name=\"永续魔法\" value=\"continuousspell\" />\n" +
            "\t\t<item name=\"装备魔法\" value=\"equipspell\" />\n" +
            "\t\t<item name=\"场地魔法\" value=\"fieldspell\" />\n" +
            "\t\t<item name=\"反击陷阱\" value=\"countertrap\" />\n" +
            "\t\t<item name=\"永续陷阱\" value=\"continuoustrap\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"race\" desc=\"种族\" default=\"\" hide=\"true\">\n" +
            "\t\t<item value=\"\"/>\n" +
            "\t\t<item value=\"战士族\"/>\n" +
            "\t\t<item value=\"魔法师族\"/>\n" +
            "\t\t<item value=\"天使族\"/>\n" +
            "\t\t<item value=\"恶魔族\"/>\n" +
            "\t\t<item value=\"不死族\"/>\n" +
            "\t\t<item value=\"机械族\"/>\n" +
            "\t\t<item value=\"水族\"/>\n" +
            "\t\t<item value=\"炎族\"/>\n" +
            "\t\t<item value=\"岩石族\"/>\n" +
            "\t\t<item value=\"鸟兽族\"/>\n" +
            "\t\t<item value=\"植物族\"/>\n" +
            "\t\t<item value=\"昆虫族\"/>\n" +
            "\t\t<item value=\"雷族\"/>\n" +
            "\t\t<item value=\"龙族\"/>\n" +
            "\t\t<item value=\"兽族\"/>\n" +
            "\t\t<item value=\"兽战士族\"/>\n" +
            "\t\t<item value=\"恐龙族\"/>\n" +
            "\t\t<item value=\"鱼族\"/>\n" +
            "\t\t<item value=\"海龙族\"/>\n" +
            "\t\t<item value=\"爬虫类族\"/>\n" +
            "\t\t<item value=\"念动力族\"/>\n" +
            "\t\t<item value=\"幻神兽族\"/>\n" +
            "\t\t<item value=\"创造神族\"/>\n" +
            "\t\t<item value=\"幻龙族\"/>\n" +
            "\t</data>\n" +
            "\t<data name=\"effect\" desc=\"效果类型\" default=\"\" hide=\"true\">\n" +
            "\t\t<item value=\"\"/>\n" +
            "\t\t<item value=\"同调\"/>\n" +
            "\t\t<item value=\"超量\"/>\n" +
            "\t\t<item value=\"融合\"/>\n" +
            "\t\t<item value=\"仪式\"/>\n" +
            "\t\t<item value=\"效果\"/>\n" +
            "\t\t<item value=\"调整\"/>\n" +
            "\t\t<item value=\"灵魂\"/>\n" +
            "\t\t<item value=\"卡通\"/>\n" +
            "\t\t<item value=\"二重\"/>\n" +
            "\t\t<item value=\"同盟\"/>\n" +
            "\t\t<item value=\"灵摆\"/>\n" +
            "\t\t<item value=\"反转\"/>\n" +
            "\t\t<item value=\"黑暗同调\"/>\n" +
            "\t\t<item value=\"黑暗调整\"/>\n" +
            "\t\t<item value=\"特殊召唤\"/>\n" +
            "\t\t<item value=\"衍生物\"/>\n" +
            "\t</data>\n" +
            "\t<data name=\"cardmonster\" desc=\"种族/效果类型\" type=\"multi_select\" >\n" +
            "\t\t<item name=\"race\" \t\tvalue=\"race\"   />\n" +
            "\t\t<item name=\"effect1\"\tvalue=\"effect\" />\n" +
            "\t\t<item name=\"effect2\" \tvalue=\"effect\" />\n" +
            "\t\t<item name=\"effect3\" \tvalue=\"effect\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"cardfoil\" desc=\"罕贵度\" type=\"multi_select\" >\n" +
            "\t\t<item name=\"cardrare\" \tvalue=\"cardrare\"\t/>\n" +
            "\t\t<item name=\"rare_type\" \tvalue=\"rare_type\"\t/>\n" +
            "\t\t<item name=\"cardborder\" value=\"cardborder\"\t/>\n" +
            "\t\t<item name=\"corner\" \tvalue=\"corner\"\t\t/>\n" +
            "\t\t<item name=\"cardcopy\" \tvalue=\"cardcopy\" />\n" +
            "\t</data>\n" +
            "\t<data name=\"cardbg\" desc=\"样式设置\" type=\"multi_select\" >\n" +
            "\t\t<item name=\"cardtype\" \tvalue=\"cardtype\"\t/>\n" +
            "\t\t<item name=\"image_type\" value=\"image_type\"\t/>\n" +
            "\t\t<item name=\"pendulum\" \tvalue=\"pendulum\"\t/>\n" +
            "\t\t<item name=\"pimagetype\" value=\"pimagetype\"\t/>\n" +
            "\t\t<item name=\"namecolor\" \tvalue=\"namecolor\"\t/>\n" +
            "\t</data>\n" +
            "\t<bool name=\"cardborder_is_gold\" value=\"'{{cardborder}}' == 'gold'\"/>\n" +
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
            "\t<bool name=\"has_name_color\" \tvalue=\"'{{namecolor}}' != ''\"/>\n" +
            "\n" +
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
