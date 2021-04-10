package com.orient.devdataobj.util;

/**
 * Created by mengbin on 16/7/14.
 * Purpose:
 * Detail:
 */
public class DataObjVersionUtil {

    /**
     * 数据修改,最后一位数据值加1
     *
     * @param oldVersion
     * @return
     */
    public static String dataSubmitUpVersion(String oldVersion) {
        String[] versionNums = oldVersion.split("\\.");
        if (versionNums.length != 4) {
            return "";
        }
        return versionNums[0] + "." + versionNums[1] + "." + versionNums[2] + "." + String.valueOf(Integer.valueOf(versionNums[3]) + 1);
    }

    /**
     * 任务提交,倒数第二位数据值+1,末位设置为0
     *
     * @param oldVersion
     * @return
     */
    public static String dataTaskSubmit(String oldVersion) {
        String[] versionNums = oldVersion.split("\\.");
        if (versionNums.length != 4) {
            return "";
        }
        return versionNums[0] + "." + versionNums[1] + "." + String.valueOf(Integer.valueOf(versionNums[2]) + 1) + ".0";
    }

    /**
     * 计划提交后,第二位数据值+1,倒数第二位不变,末位设置为0
     *
     * @param oldVersion
     * @return
     */
    public static String jobSubmitUpVersion(String oldVersion) {
        String[] versionNums = oldVersion.split("\\.");
        if (versionNums.length != 4) {
            return "";
        }
        return versionNums[0] + "." + String.valueOf(Integer.valueOf(versionNums[1]) + 1) + "." + versionNums[2] + ".0";
    }

    /**
     * 项目提交后,第一位数据值+1,第二位和第三位不变,末位设置为0
     *
     * @param oldVersion
     * @return
     */
    public static String projectSubmitUpVersion(String oldVersion) {
        String[] versionNums = oldVersion.split("\\.");
        if (versionNums.length != 4) {
            return "";
        }
        return String.valueOf(Integer.valueOf(versionNums[0]) + 1) + "." + versionNums[1] + "." + versionNums[2] + ".0";
    }

    /**
     * 比较两个版本的大小
     *
     * @param verion1
     * @param version2
     * @return 1 : V1>V2   -1: v1<v2    0 v1==v2
     */
    public static int versionCompare(String verion1, String version2) {
        String[] versionNums1 = verion1.split("\\.");
        String[] versionNums2 = version2.split("\\.");
        for (int i = 0; i < 4; i++) {
            int v1 = Integer.valueOf(versionNums1[i]);
            int v2 = Integer.valueOf(versionNums2[i]);
            if (v1 > v2) {
                return 1;
            } else if (v1 < v2) {
                return -1;
            }
        }
        return 0;
    }

}
