package com.orient.sysmodel.domain.role;

import com.orient.sysmodel.operationinterface.IOperation;
// default package


/**
 * Operation entity. @author MyEclipse Persistence Tools
 */
public class Operation extends AbstractOperation implements java.io.Serializable, IOperation {

    public final static String OPERATOR_ADD = "新增";
    public final static String OPERATOR_UPDATE = "修改";
    public final static String OPERATOR_DETAIL = "详细";
    public final static String OPERATOR_QUERY = "查询";
    public final static String OPERATOR_DELETE = "删除";
    public final static String OPERATOR_EXPORT = "导出";
    public final static String OPERATOR_IMPORT = "导入";
    public final static String OPERATOR_FILE = "附件";
    public final static String OPERATOR_START_FLOW = "启动流程";
    public final static String OPERATOR_DETAIL_FLOW = "查看流程";
    public final static String OPERATOR_END_FLOW = "关闭流程";

    // Constructors

    /**
     * default constructor
     */
    public Operation() {
    }


    /**
     * full constructor
     */
    public Operation(String name) {
        super(name);
    }

}
