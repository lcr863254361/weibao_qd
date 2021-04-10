package com.orient.workflow.bean;

public interface JBPMInfo {

    //上一個流程结点执行人的名称
    public static String LAST_TASK_USER = "LAST_TASK_USER";
    //上一個流程結點的名称
    public static String LAST_TASK_NAME = "LAST_TASK_NAME";
    //上一個流程跳转对象的名称
    public static String LAST_TRANSITION_NAME = "LAST_TRANSITION_NAME";
    //启动流程实例人的名称
    public static String START_INSTANCE_USER = "START_INSTANCE_USER";
    //	//完成任务人的名称
//	public static String COMPLETE_TASK_USER = "COMPLETE_TASK_USER";
    //数据表和对应数据id
    public static String JBPM_TABLE_DATA = "JBPM_TABLE_DATA";
    //对应表单数据
    public static String JBPM_FORM_DATA = "JBPM_FORM_DATA";
    //已经执行过的表单类
    public static String USED_FORM_TABLE = "USED_FORM_TABLE";

    public static String DynamicUserAssign = "Dynamic_User_Assign";

    String FLOW_DATA_RELATION = "FLOW_DATA_RELATION";

    String COLLAB_TASK_ID = "COLLAB_TASK_ID";

}
