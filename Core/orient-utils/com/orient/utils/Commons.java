/*---------------------------------------------------------------------------------------------------------
 *
 *   Developer：廉福业 [Hacker]
 * Create Date：2008-4-14 下午04:38:24
 * Description：1.公共方法
 *              2.
 *              3.
 *   Copyright (C) 2008 Hacker. All rights reserved.
 *
 *---------------------------------------------------------------------------------------------------------
 */

package com.orient.utils;

import java.io.IOException;
import java.util.Date;

public final class Commons {

    // 打印
    public static void println(Object obj, String type, String str) {
        String className = "className";
        String printInfo = "printInfo";
        System.out.println("-----------------------------------------------------------");
        if ("test".equals(type)) {
            System.out.println(className + " -->" + obj);
            System.out.println(printInfo + " -->" + str);
        } else if ("sql".equals(type)) {
            System.out.println(className + " -->" + obj);
            System.out.println(printInfo + " -->" + str);
        } else if ("exception".equals(type)) {
            System.out.println(className + " -->" + obj);
            System.out.println(printInfo + " -->" + str);
        } else if ("other".equals(type)) {
            System.out.println(className + " -->" + obj);
            System.out.println(printInfo + " -->" + str);
        }
    }

    // 项目名称
    public static String getProjectName(String lang) {
        String cn = "全文检索管理系统";
        String en = "OrientSearch Application";
        String projectName = "";
        projectName = cn;
        if ("en".equals(lang)) {
            projectName = en;
        } else if ("all".equals(lang)) {
            projectName = cn + " (" + en + ")";
        }
        return projectName;
    }

    // 去前后空格 + 转换空符串
    public static String getStringNull(String str) {
        if (null != str && !"".equals(str)) {
            return str.trim();
        }
        return "";
    }

    // 转换字符集，按 GBK 转换成 iso-8859-1
    public static String getGBKToISO(String str) {
        str = Commons.getStringNull(str);
        if (!"".equals(str)) {
            try {
                // Commons.println("Commons", "test", "转码前(GBK)："+str);
                byte[] tmp = str.getBytes("GBK");
                str = new String(tmp, "iso-8859-1");
                // Commons.println("Commons", "test", "转码后(iso-8859-1)："+str);
            } catch (Exception e) {
                Commons.println("Commons", "exception", "字符集转码：" + e.getMessage());
            }
        }
        return str;
    }

    // 转换字符集，按 iso-8859-1 转换成 GBK
    public static String getISOToGBK(String str) {
        str = Commons.getStringNull(str);
        if (!"".equals(str)) {
            try {
                // Commons.println("Commons", "test", "转码前(iso-8859-1)："+str);
                byte[] tmp = str.getBytes("iso-8859-1");
                str = new String(tmp, "GBK");
                // Commons.println("Commons", "test", "转码后(GBK)："+str);
            } catch (Exception e) {
                Commons.println("Commons", "exception", "字符集转码：" + e.getMessage());
            }
        }
        return str;
    }

    // 转换字符集，按 iso-8859-1 转换成 UTF
    public static String getISOToUTF(String str) {
        str = Commons.getStringNull(str);
        if (!"".equals(str)) {
            try {
                // Commons.println("Commons", "test", "转码前(iso-8859-1)："+str);
                byte[] tmp = str.getBytes("iso-8859-1");
                str = new String(tmp, "UTF-8");
                // Commons.println("Commons", "test", "转码后(UTF-8)："+str);
            } catch (Exception e) {
                Commons.println("Commons", "exception", "字符集转码：" + e.getMessage());
            }
        }
        return str;
    }

    // 转换字符集，按 utf-8 转换成 ISO
    public static String getUTFToISO(String str) {
        str = Commons.getStringNull(str);
        if (!"".equals(str)) {
            try {
                // Commons.println("Commons", "test", "转码前(utf-8)："+str);
                byte[] tmp = str.getBytes("UTF-8");
                str = new String(tmp, "iso-8859-1");
                // Commons.println("Commons", "test", "转码后(ISO)："+str);
            } catch (Exception e) {
                Commons.println("Commons", "exception", "字符集转码：" + e.getMessage());
            }
        }
        return str;
    }

    // 转换字符集，按 GBK 转换成 UTF
    public static String getGBKToUTF(String str) {
        str = Commons.getStringNull(str);
        if (!"".equals(str)) {
            try {
                // Commons.println("Commons", "test", "转码前(GBK)："+str);
                byte[] tmp = str.getBytes("GBK");
                str = new String(tmp, "UTF-8");
                // Commons.println("Commons", "test", "转码后(UTF)："+str);
            } catch (Exception e) {
                Commons.println("Commons", "exception", "字符集转码：" + e.getMessage());
            }
        }
        return str;
    }

    public static String getUTFToGBK(String str) {
        str = Commons.getStringNull(str);
        if (!"".equals(str)) {
            try {
                // Commons.println("Commons", "test", "转码前(UTF)："+str);
                byte[] tmp = str.getBytes("UTF-8");
                str = new String(tmp, "GBK");
                // Commons.println("Commons", "test", "转码后(GBK)："+str);
            } catch (Exception e) {
                Commons.println("Commons", "exception", "字符集转码：" + e.getMessage());
            }
        }
        return str;
    }

    // String 转换成 Int
    public static int getStringToInt(String str) {
        str = Commons.getStringNull(str);
        int num = 0;
        // 验证数字
        boolean flag = Commons.getValidateChar(str, "0123456789");
        if (!"".equals(str) && flag) {
            num = Integer.parseInt(str);
        }
        return num;
    }

    // Int 转换成 String
    public static String getIntToString(int i) {
        return String.valueOf(i);
    }

    // String 转换成 Float
    public static float getStringToFloat(String str) {
        float f = 0;
        boolean flag = false;
        // 去空
        str = Commons.getStringNull(str);
        // 统计小数点
        int count = Commons.getCountChar(".", str);
        // 验证数字
        if (count < 2) {
            flag = Commons.getValidateChar(str, "0123456789.");
        }
        if (!"".equals(str) && flag) {
            f = Float.parseFloat(str);
        }
        return f;
    }

    // Float 转换成 String
    public static String getFloatToString(float f) {
        return String.valueOf(f);
    }

    // 判断 str 是否在 strString 中出现(true有、false没有)
    public static boolean getValidateChar(String str, String strString) {
        boolean flag = true;
        str = Commons.getStringNull(str);
        strString = Commons.getStringNull(strString);
        if (!"".equals(str) && !"".equals(strString)) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (strString.indexOf(c) == -1) {
                    flag = false;
                    break;
                }
            }
        } else if ("".equals(str) && "".equals(strString)) {
            flag = false;
        }
        return flag;
    }

    // 统计 str 在 strString 中出现的次数
    public static int getCountChar(String str, String strString) {
        int count = 0;
        str = Commons.getStringNull(str);
        strString = Commons.getStringNull(strString);
        if (!"".equals(str) && !"".equals(strString)) {
            for (int i = 0; i < strString.length(); i++) {
                char c = strString.charAt(i);
                if (str.indexOf(c) != -1) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 转换时间单位
     *
     * @param size
     * @return
     * @throws IOException
     */
    public static String getTime(long time, String style) throws IOException {
        String timeStyle = "";
        long h = time / 3600000;
        long m = (time % 3600000) / 60000;
        long s = (time % 60000) / 1000;
        long ms = (time % 1000) / 1;
        String ph = h < 10 ? "0" : "";
        String pm = m < 10 ? "0" : "";
        String ps = s < 10 ? "0" : "";
        String pms = ms < 10 ? "0" : "";
        if ("A".equals(style)) {
            timeStyle = ph + h + ":" + pm + m + ":" + ps + s + "." + pms + ms;
        } else if ("B".equals(style)) {
            timeStyle = ph + h + "时 " + pm + m + "分 " + ps + s + "秒 " + pms + ms + "毫秒";
        } else if ("C".equals(style)) {
            if (h > 0) {
                timeStyle = h + "时 " + m + "分 " + s + "秒 " + ms + "毫秒";
            } else if (m > 0) {
                timeStyle = m + "分 " + s + "秒 " + ms + "毫秒";
            } else if (s > 0) {
                timeStyle = s + "秒 " + ms + "毫秒";
            } else {
                timeStyle = ms + "毫秒";
            }
        }
        return timeStyle;
    }

    /**
     * 转换 GB/MB/KB 单位
     *
     * @param size
     * @return
     * @throws IOException
     */
    public static String getSize(double size) throws IOException {
        if (size > 1073741824) {
            return Commons.round((float) (size / 1073741824), 2) + " GB";
        } else if (size > 1048576) {
            return Commons.round((float) (size / 1048576), 2) + " MB";
        } else if (size > 1024) {
            return Commons.round((float) (size / 1024), 2) + " KB";
        } else {
            return size + " Bytes";
        }
    }

    /**
     * 保留小数点后几位
     *
     * @param f     数
     * @param scale 保留后几位
     * @return float
     */
    public static float round(float f, int scale) {
        if (scale == 1) {
            f = Float.valueOf(Math.round(f * 10)) / 10;
        } else if (scale == 2) {
            f = Float.valueOf(Math.round(f * 100)) / 100;
        } else if (scale == 3) {
            f = Float.valueOf(Math.round(f * 1000)) / 1000;
        } else {
            f = Float.valueOf(Math.round(f));
        }
        return f;
    }

    public static Date getSysDate() {
        Date date = new Date();
        return date;
    }
}
