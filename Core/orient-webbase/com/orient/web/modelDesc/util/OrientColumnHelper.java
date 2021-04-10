package com.orient.web.modelDesc.util;

import com.orient.businessmodel.Util.EnumInter;

/**
 * Created by enjoy on 2016/3/21 0021.
 */
public class OrientColumnHelper {

    /**
     * 根据字段类型 获取字段前段展现形式
     *
     * @param colEnum
     * @return
     */
    public static Integer getColumnControlType(EnumInter.BusinessModelEnum.BusinessColumnEnum colEnum) {
        Integer retVal;
        switch (colEnum) {
            case C_Integer:
                //整数型
                retVal = 1;
                break;
            case C_BigInteger:
                //数值型
                retVal = 2;
                break;
            case C_Decimal:
                //数值型
                retVal = 3;
                break;
            case C_Float:
                //数值型
                retVal = 4;
                break;
            case C_Relation:
                //关系属性
                retVal = 5;
                break;
            case C_Date:
                //日期
                retVal = 6;
                break;
            case C_DateTime:
                //时间
                retVal = 7;
                break;
            case C_Boolean:
                //是否
                retVal = 8;
                break;
            case C_SingleEnum:
                //单选框
                retVal = 9;
                break;
            case C_MultiEnum:
                //多选框
                retVal = 10;
                break;
            case C_SingleTableEnum:
                //单选表枚举
                retVal = 11;
                break;
            case C_MultiTableEnum:
                //多选表枚举
                retVal = 12;
                break;
            case C_Text:
                //大文本
                retVal = 13;
                break;
            case C_File:
                //附件
                retVal = 14;
                break;
            case C_Ods:
                //附件
                retVal = 15;
                break;
            case C_Hadoop:
                //附件
                retVal = 16;
                break;
            case C_Check:
                //附件
                retVal = 17;
                break;
            case C_NameValue:
                //键值对
                retVal = 18;
                break;
            case C_SubTable:
                //子表
                retVal = 19;
                break;
            case C_Simple:
                //单行文本
                retVal = 0;
                break;
            default:
                //默认为单行文本框
                retVal = 0;
        }
        return retVal;
    }
}
