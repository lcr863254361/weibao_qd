package com.orient.collabdev.constant;

import com.orient.config.ConfigInfo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-27 9:23 AM
 */
public class CollabDevConstants extends ConfigInfo {

    //node type
    public static final String NODE_TYPE_DIR = "dir";
    public static final String NODE_TYPE_PRJ = "prj";
    public static final String NODE_TYPE_PLAN = "plan";
    public static final String NODE_TYPE_TASK = "task";

    //business model name
    public static final String DIRECTORY = "CB_DIR";
    public static final String PROJECT = "CB_PROJECT";
    public static final String PLAN = "CB_PLAN";
    public static final String PLAN_HISTORY = "CB_PLAN_HIS";
    public static final String TASK = "CB_TASK";
    public static final String BASE_LINE = "CB_B_LINE";
    public static final String PLAN_DEPENDENCY = "CB_PLAN_R";
    //CB_PROJECT_SETTINGS ZhangSheng 2018.7.31
    public static final String PROJECT_SETTINGS = "CB_PROJECT_SETTINGS";
    //CB_SHARE_FILE ZhangSheng 2018.8.20
    public static final String SHARE_FILE = "CB_SHARE_FILE";
    public static final String SHARE_FOLDER = "CB_SHARE_FOLDER";

    public static final String DEFAULT_ROLE_OWNER = "owner";
    public static final String DEFAULT_ROLE_OWNGROUP = "ownGroup";

    //数据包完成量统计 ZhangSheng 2018.9.24
    public static final String COLLAB_TASK_UNSTARTED = "0";
    public static final String COLLAB_TASK_PROCESSING = "1";
    public static final String COLLAB_TASK_FINISHED = "2";

    public static final Map<String, String> STATUS_MAPPING = new LinkedHashMap<String, String>() {{
        put(CollabDevConstants.COLLAB_TASK_UNSTARTED, "未开始");
        put(CollabDevConstants.COLLAB_TASK_PROCESSING, "进行中");
        put(CollabDevConstants.COLLAB_TASK_FINISHED, "已完成");
    }};

    public static final Map<String, String> NODETYPE_MODELNAME_MAPPING = new HashMap<String, String>() {{
        put(NODE_TYPE_DIR, DIRECTORY);
        put(NODE_TYPE_PRJ, PROJECT);
        put(NODE_TYPE_PLAN, PLAN);
        put(NODE_TYPE_TASK, TASK);
    }};

    public static final Map<String, String> MODELNAME_NODETYPE_MAPPING = new HashMap<String, String>() {{
        put(DIRECTORY, NODE_TYPE_DIR);
        put(PROJECT, NODE_TYPE_PRJ);
        put(PLAN, NODE_TYPE_PLAN);
        put(TASK, NODE_TYPE_TASK);
    }};

    public static final String PLAN_TYPE_MILESTONE = "MILESTONE";
    public static final String PLAN_TYPE_NORMAL = "NORMAL";
    public static final String PLAN_TYPE_GROUP = "GROUP";

    //--------------------------------------------配置的模板----------------------------------------
    public static final String TPL_VIEWBOARD_PROJECT = "看板-项目基础信息";
    public static final String TPL_VIEWBOARD_PLAN = "看板-计划基础信息";
    public static final String TPL_VIEWBOARD_TASK = "看板-任务基础信息";
    public static final String TPL_PACKET_DECOMPOSITION = "协同研发-数据包分解";

    //"协同设计-项目设置模板" ZhangSheng 2018.7.31
    public static final String TPL_PROJECT_SETTING = "协同设计-项目设置模板";
    //"协同设计-数据内容模板" ZhangSheng 2018.8.20
    public static final String TPL_SHARE_FILE = "协同设计-数据内容模板";
}
