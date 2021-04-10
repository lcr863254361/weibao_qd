package com.orient.web.modelDesc.operator.builder.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.web.modelDesc.operator.builder.IColumnDescBuilder;
import com.orient.web.modelDesc.column.*;
import org.springframework.stereotype.Component;

/**
 * 表单元素构造器
 *
 * @author enjoy
 * @creare 2016-03-30 10:35
 */
@Component
public class ColumnDescBuilder implements IColumnDescBuilder {

    @Override
    public ColumnDesc buildColumnDesc(IBusinessColumn iBusinessColumn) {
        ColumnDesc retVal;
        EnumInter.BusinessModelEnum.BusinessColumnEnum colEnum = (EnumInter.BusinessModelEnum.BusinessColumnEnum) iBusinessColumn.getColType();
        switch (colEnum) {
            case C_Integer:
                //整数型
                retVal = new NumberColumnDesc().init(iBusinessColumn);
                break;
            case C_BigInteger:
                //数值型
                retVal = new NumberColumnDesc().init(iBusinessColumn);
                break;
            case C_Decimal:
                //数值型
                retVal = new NumberColumnDesc().init(iBusinessColumn);
                break;
            case C_Double:
                //数值型
                retVal = new NumberColumnDesc().init(iBusinessColumn);
                break;
            case C_Float:
                //数值型
                retVal = new NumberColumnDesc().init(iBusinessColumn);
                break;
            case C_Relation:
                retVal = new RelationColumnDesc().init(iBusinessColumn);
                break;
            case C_Date:
                //日期
                retVal = new DateColumnDesc().init(iBusinessColumn);
                break;
            case C_DateTime:
                //时间
                retVal = new DateTimeColumnDesc().init(iBusinessColumn);
                break;
            case C_Boolean:
                //是否
                retVal = new BooleanColumnDesc().init(iBusinessColumn);
                break;
            case C_SingleEnum:
                //单选框
                retVal = new SingleEnumColumnDesc().init(iBusinessColumn);
                break;
            case C_MultiEnum:
                //多选框
                retVal = new MultiEnumColumnDesc().init(iBusinessColumn);
                break;
            case C_SingleTableEnum:
                //单选表枚举
                retVal = new SingleTableEnumColumnDesc().init(iBusinessColumn);
                break;
            case C_MultiTableEnum:
                //多选表枚举
                retVal = new MultiTableEnumColumnDesc().init(iBusinessColumn);
                break;
            case C_Text:
                //大文本
                retVal = new TextColumnDesc().init(iBusinessColumn);
                break;
            case C_NameValue:
                retVal = new DynamicFormGridDesc().init(iBusinessColumn);
                break;
            case C_SubTable:
                retVal = new DynamicFormGridDesc().init(iBusinessColumn);
                break;
            case C_File:
                //附件
                retVal = new FileColumnDesc().init(iBusinessColumn);
                break;
            case C_Ods:
                //附件
                retVal = new FileColumnDesc().init(iBusinessColumn);
                break;
            case C_Hadoop:
                //附件
                retVal = new FileColumnDesc().init(iBusinessColumn);
                break;
            case C_Simple:
                //单行文本
                retVal = new SimpleColumnDesc().init(iBusinessColumn);
                break;
            case C_Check:
                //单行文本
                retVal = new CheckColumnDesc().init(iBusinessColumn);
                break;
            default:
                //默认为单行文本框
                retVal = new SimpleColumnDesc().init(iBusinessColumn);
        }
        return retVal;
    }
}
