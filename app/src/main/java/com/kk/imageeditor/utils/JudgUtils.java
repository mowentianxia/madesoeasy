package com.kk.imageeditor.utils;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JudgUtils {
    static final boolean TEST = false;
    List<String> strMaps = new ArrayList<>();
    List<String> list = new ArrayList<>();
    static final String AND = "and";
    static final String OR = "or";
    static final String[] C_SEPS = new String[]{"<", "<=", ">", ">=", "==", "!="};
    static final String SYMBOL = "$";
    static final char SEP_LEFT = '(';
    static final char SEP_RIGHT = ')';
    Calculator calculator = new Calculator();

    JudgUtils() {

    }

    public static boolean getBoolean(String str, boolean def) {
        if (str == null || str.length() == 0)
            return def;
        JudgUtils judgUtils = new JudgUtils();
        return judgUtils.convertBool(str, def);
    }

    // 仅支持一个逻辑符号
    boolean convertBool(String str, boolean def) {
        //
        if (str == null || str.length() == 0)
            return def;
        strMaps.clear();
        list.clear();
        //提取字符串
        str = dealStr(str);

        if (TEST)
            System.out.println("---start");
        return all2Boolean(str, def);
    }

    boolean all2Boolean(String str, boolean def) {
        String[] vals = str.split("and|or");
        int len = 0;
        boolean rs = def;
        if (vals.length == 1) {
            return str2Boolean(vals[0]);
        }
        for (int i = 0; i < vals.length; i++) {
            String val = vals[i];
            len += val.length();
            String bt = ((len + 2) < str.length()) ? str.substring(len, len + 2) : "";
            rs = str2Boolean(val);
            if (TEST)
                System.out.println(val + " " + bt + " " + rs);
            if (rs) {
                if (OR.equals(bt)) {
                    return true;
                }
                len += 3;
            } else {
                if (AND.equals(bt) || bt.startsWith("an")) {
                    return false;
                }
                len += 2;
            }
        }
        return rs;
    }

    String dealStr(String str) {
        str = findStr(str, "\"");
        str = findStr(str, "'");
        str = trim(str);
        if (TEST)
            System.out.println(str);
        str = findbreak(str);
        return str;
    }

    String findStr(String str, String sep) {
        int i = str.indexOf(sep);
        int start = i + 1;
        int end = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while (i >= 0) {
            int e = str.indexOf(sep, start);
            if (e >= 0) {
                if (e == str.indexOf("\\", start) + 1) {
                    //转移的"
                    start = e + 1;
                } else {
                    stringBuffer.append(str.substring(end, i + 1));
                    String s = str.substring(i + 1, e);
                    strMaps.add(s);
                    int size = strMaps.size();
                    // System.out.println((size - 1) + "=" + s);
                    stringBuffer.append((size - 1));
                    end = e;
                    i = str.indexOf(sep, e + 1);
                    start = i + 1;
                }
            } else {
                break;
            }
        }
        if (end < str.length()) {
            stringBuffer.append(str.substring(end));
        }
        return stringBuffer.toString();
    }

    /**
     * 括号
     *
     * @param s
     * @return
     */
    String findbreak(String s) {
        int fri;
        int end;
        if ((fri = s.lastIndexOf(SEP_LEFT)) != -1) {
            for (int i = fri; i < s.length(); i++) {

                if (s.charAt(i) == SEP_RIGHT) {
                    end = i;
                    String str = s.substring(fri + 1, end);
                    String turnstr = s.substring(0, fri) + SYMBOL + list.size()
                            + s.substring(end + 1, s.length());
                    list.add(str);
                    if (TEST)
                        System.out.println("findbreak:" + turnstr);
                    return findbreak(turnstr);
                }
            }
        }
        return s;
    }

    boolean str2Boolean(String s) {
        if (TEST)
            System.out.println("str2Boolean:1:" + s);
        if (isBoolean(s)) {
            return Boolean.parseBoolean(s);
        }
        if (s.startsWith(SYMBOL)) {
            String tmp = list.get(Integer.parseInt(s.substring(1)));
            boolean b2 = all2Boolean(tmp, false);
            if (TEST)
                System.out.println("str2Boolean:2:" + tmp + "   " + b2);
            return b2;
        }
        for (String sep : C_SEPS) {
            if (s.contains(sep)) {
                String[] vals = s.split(sep);
                int len = vals[0].length();
                String bt = s.substring(len, len + sep.length());
                if (TEST)
                    System.out.println("str2Boolean:3:" + vals[0] + bt + vals[1]);
                return tob(vals[0], vals[1], bt);
            }
        }
        return false;
    }

    boolean tob(String s1, String s2, String bt) {
        if (s1.startsWith(SYMBOL)) {
            String tmp = list.get(Integer.parseInt(s1.substring(1)));
            s1 = "" + all2Boolean(tmp, false);
            if (TEST)
                System.out.println("tob:" + tmp + "=" + s1);
        }
        if (s2.startsWith(SYMBOL)) {
            String tmp = list.get(Integer.parseInt(s2.substring(1)));
            s2 = "" + all2Boolean(tmp, false);
            if (TEST)
                System.out.println("tob:" + tmp + "=" + s2);
        }
        //str
        boolean str = false;
        if (s1.startsWith("'") && s1.endsWith("'")) {
            s1 = strMaps.get(Integer.parseInt(s1.replace("'", "")));
            str = true;
        } else if (s1.startsWith("\"") && s1.endsWith("\"")) {
            s1 = strMaps.get(Integer.parseInt(s1.replace("\"", "")));
            str = true;
        }
        if (s2.startsWith("'") && s2.endsWith("'")) {
            s2 = strMaps.get(Integer.parseInt(s2.replace("'", "")));
            str = true;
        } else if (s1.startsWith("\"") && s2.endsWith("\"")) {
            s2 = strMaps.get(Integer.parseInt(s2.replace("\"", "")));
            str = true;
        }
        if (str) {
            if ("==".equals(bt)) {
                boolean rs = s1.equals(s2);
                if (TEST)
                    System.out.println("tob:" + s1 + "==" + s2 + "  " + rs);
                return rs;
            } else if ("!=".equals(bt)) {
                boolean rs = !s1.equals(s2);
                if (TEST)
                    System.out.println("tob:" + s1 + "!=" + s2 + "  " + rs);
                return rs;
            }
            return false;
        }
        if (isBoolean(s1) || isBoolean(s2)) {
            return s1.equals(s2);
        }
        //num
        //bool
        double d1 = calculator.getDoubleResult(s1);
        double d2 = calculator.getDoubleResult(s2);
        if (TEST)
            System.out.println("tob:" + s1 + bt + s2 + "   " + d1 + bt + d2);
        if ("<".equals(bt)) {
            return d1 < d2;
        }
        if (">".equals(bt)) {
            return d1 > d2;
        }
        if ("<=".equals(bt)) {
            return d1 <= d2;
        }
        if (">=".equals(bt)) {
            return d1 >= d2;
        }
        if ("!=".equals(bt)) {
            return d1 != d2;
        }
        if ("==".equals(bt)) {
            return d1 == d2;
        }
        return false;
    }

    boolean isBoolean(String s) {
        s = s.toLowerCase(Locale.US);
        return "true".equals(s) || "false".equals(s);
    }

    //去掉多余的空白符号
    String trim(String str) {
        str = str.replace("\r", "");
        str = str.replace("\n", "");
        str = str.replace("\t", "");
        str = str.replace(" ", "");
        return str.trim();
    }
}