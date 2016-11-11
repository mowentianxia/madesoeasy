package com.kk.imageeditor.utils;

import java.util.ArrayList;

public class Calculator {
    ArrayList<String> list = new ArrayList<>();

    Calculator() {

    }

    static final String[] ss = new String[]{
            "+", "-", "*", "/", "%", "-", "√",
            "sin", "cos", "tan", "&", "|", "^", ">>", "<<",
            };

    public static double getResult(String s) {
        return new Calculator().getDoubleResult(s);
    }

    public double getDoubleResult(String s) {
        if (s == null || s.length() == 0)
            return 0;
        s = s.replace(" ", "");
        list.clear();
        double d = 0d;
        try {
            d = f(parse(s));
        } catch (ArithmeticException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    // 该方法（）的内容由$在list中进行索引
    String parse(String s) throws ArithmeticException {
        if (s.matches("[+-//*/]{2,}")) {
            throw new ArithmeticException();
        }
        int fri;
        int end;
        if ((fri = s.lastIndexOf("(")) != -1) {
            for (int i = fri; i < s.length(); i++) {

                if (s.charAt(i) == ')') {
                    end = i;
                    String str = s.substring(fri + 1, end);
                    String turnstr = s.substring(0, fri) + "$" + list.size()
                            + s.substring(end + 1, s.length());
                    // System.out.println(turnstr);
                    list.add(str);
                    return parse(turnstr);
                }
            }
        }
        return s;
    }

    // f方法递归运算法则求解
    double f(String s) throws ArithmeticException {

        try {
            // 递归求解符号优先级自上往下----优先级
            if (s.lastIndexOf("+") != -1 || s.lastIndexOf("-") != -1) {
                return jia_jian(s);
            }
            if (s.lastIndexOf("*") != -1 || s.lastIndexOf("/") != -1
                    || s.lastIndexOf("%") != -1) {
                return cheng_chu_rest(s);
            }
            if (s.lastIndexOf("&") != -1) {
                return yu(s);
            }
            if (s.lastIndexOf("|") != -1) {
                return huo(s);
            }
            if (s.lastIndexOf(">>") != -1) {
                return right(s);
            }
            if (s.lastIndexOf("<<") != -1) {
                return left(s);
            }
            // 符号运算
            if (s.indexOf("√") == 0) {
                return sqrt(s);
            }
            if (s.indexOf("0x") == 0) {
                return hex(s);
            }
            if (s.indexOf("sin") == 0) {
                return sin(s);
            }
            if (s.indexOf("cos") == 0) {
                return cos(s);
            }
            if (s.indexOf("tan") == 0) {
                return tan(s);
            }

            // 最高优先级，将()看做一个字符串处理，由$索引在List中引用
            if (s.indexOf("$") == 0) {
                // System.out.println();
                return tag(s);
            }
            // 运算将表达式拆解到无任何符号则直接返回该值
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new ArithmeticException(s);
        } catch (Exception e) {
            throw new ArithmeticException(s);
        }
    }

    double jia_jian(String s) {
        if (s.lastIndexOf("+") - s.lastIndexOf("-") > 0) {
            int i = s.lastIndexOf("+");
            return (s.substring(0, i).equals("") ? 0 : f(s.substring(0, i)))
                    + f(s.substring(i + 1, s.length()));
        } else {

            int i = s.lastIndexOf("-");
            return (s.substring(0, i).equals("") ? 0 : f(s.substring(0, i)))
                    - f(s.substring(i + 1, s.length()));
        }
    }

    int yu(String s) {
        int i = s.indexOf("&");
        return (int) Math.round(f(s.substring(0, i)))
                & (int) Math.round(f(s.substring(i + 1, s.length())));
    }

    int huo(String s) {
        int i = s.indexOf("|");
        return (int) Math.round(f(s.substring(0, i)))
                | (int) Math.round(f(s.substring(i + 1, s.length())));
    }

    int left(String s) {
        int i = s.indexOf("<<");
        return (int) Math.round(f(s.substring(0, i)))
                << (int) Math.round(f(s.substring(i + 2, s.length())));
    }

    int right(String s) {
        int i = s.indexOf(">>");
        return (int) Math.round(f(s.substring(0, i)))
                >> (int) Math.round(f(s.substring(i + 2, s.length())));
    }

    double add(String s) {
        String[] str = s.split("\\+");
        double d = str[0].equals("") ? 0 : f(str[0]);
        for (int i = 1; i < str.length; i++) {
            d += f(str[i]);
        }
        return d;
    }

    double subt(String s) {
        String[] str = s.split("-");
        double d = str[0].equals("") ? 0 : f(str[0]);
        for (int i = 1; i < str.length; i++) {
            d -= f(str[i]);
        }
        return d;
    }

    double cheng_chu_rest(String s) {
        int i1 = s.lastIndexOf("*");
        int i2 = s.lastIndexOf("/");
        int i3 = s.lastIndexOf("%");
        if (i1 > i2 && i1 > i3) {
            return f(s.substring(0, i1))
                    * f(s.substring(i1 + 1, s.length()));
        } else if (i2 > i1 && i2 > i3) {
            // System.out.println("/");
            return f(s.substring(0, i2))
                    / f(s.substring(i2 + 1, s.length()));
        } else {
            // System.out.println("%");
            return f(s.substring(0, i3))
                    % f(s.substring(i3 + 1, s.length()));
        }
    }

//    double multi(String s) {
//        String[] str = s.split("\\*");
//        double d = f(str[0]);
//        for (int i = 1; i < str.length; i++) {
//            d *= f(str[i]);
//        }
//        return d;
//    }
//
//    double quoteint(String s) {
//        String[] str = s.split("\\/");
//        double d = f(str[0]);
//        for (int i = 1; i < str.length; i++) {
//            d /= f(str[i]);
//        }
//        return d;
//    }

    double sqrt(String s) {
        String[] str = s.split("√");
        return f("" + Math.sqrt(f(str[1])));
    }

    double tag(String s) {
        // 将$解析，在ArrayList的索引中取出字符加入到f方法中递归
        String[] str = s.split("\\$");
        return f(list.get(Integer.parseInt(str[1])));
    }

    private double sin(String s) {
        String[] str = s.split("sin");
        return Math.sin(Math.PI / 180 * f(str[1]));
    }

    private double cos(String s) {
        String[] str = s.split("cos");
        return Math.cos(Math.PI / 180 * f(str[1]));
    }

    private double tan(String s) {
        String[] str = s.split("tan");
        return Math.tan(Math.PI / 180 * f(str[1]));
    }

    private int hex(String s) {
        return Integer.parseInt(s.replace("0x", ""), 16);
    }
}