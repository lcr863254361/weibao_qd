package com.orient.workflow;

import com.orient.config.ConfigInfo;

public class WorkFlowConstants extends ConfigInfo {

    static public String STATE_NOT_STARTED = "notStarted";
    static public String STATE_ACTIVE = "active";
    static public String STATE_END_CANCEL = "cancel";
    static public String STATE_END_ERROR = "error";
    static public String STATE_END = "end";

    static public String AUDIT_FLOW = "AUDIT";

    static public String USERPARA = "USERPARA";

    public static String FORMFILE_SUFFIX = "form.xml";    //表单后缀名
    public static String JPDLFILE_SUFFIX = ".jpdl.xml"; //流程定义后缀
    public static String ACTIVITY_TYPE_TASK = "task";    //task 类型
    public static String ACTIVITY_TYPE_CUSTOM = "custom";    //会签

    public static String PROCESS_NAME_VERSION_CONNECTER = "-";    //流程名称与流程版本号的连接符
    public static String PROCESS_MAIN_SUB_CONNECTER = "_";    //流程名称与流程版本号的连接符
    public static String PROCESS_NOT_DEF = "0";        //流程为定义

}
