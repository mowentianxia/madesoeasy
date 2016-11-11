package com.kk.imageeditor.utils;

public class StringUtil {
    /**
     * 半角转全角
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
            } else if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    public static String dealPath(String str){
        return str.replace("<","").replace(">", "")
                .replace("?", "").replace("*", "")
                .replace("!", "").replace("|<", "")
                .replace("\"","");
    }

    public static String toString(Object object) {
        if (object == null) return "";
        if (object instanceof byte[]) {
            return new String((byte[]) object);
        }
        return String.valueOf(object);
    }
}
