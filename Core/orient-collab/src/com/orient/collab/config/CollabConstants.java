package com.orient.collab.config;

import com.orient.config.ConfigInfo;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * constants in collab module
 *
 * @author Seraph
 * 2016-07-06 下午1:52
 */
public class CollabConstants extends ConfigInfo {

    public static final String MODEL_NAME = "modelName";
    public static final String DIRECTORY = "CB_DIR";
    public static final String PROJECT = "CB_PROJECT";
    public static final String PLAN = "CB_PLAN";
    public static final String TASK = "CB_TASK";

    public static final String BASE_LINE = "CB_B_LINE";
    public static final String PLAN_DEPENDENCY = "CB_PLAN_R";
    public static final String PLAN_DEPENDENCY_HISTORY = "CB_PLAN_R_HIS";

    public static final String ROLE_CANDIDATE = "候选人";
    public static final String ROLE_EXECUTOR = "执行人";
    public static final String ROLE_TEAM = "工作组";
    public static final String ROLE_LEADER = "领导";

    public static final String ROLE_FUNCTION_TEAM = "工作组";

    public static final String NODE_ID_SPLIT = "_";

    public static final String PLAN_TYPE_MILESTONE = "MILESTONE";
    public static final String PLAN_TYPE_NORMAL = "NORMAL";
    public static final String PLAN_TYPE_GROUP = "GROUP";

    public static final String TASK_TYPE_COUNTERSIGN = "会签任务";

    public static final String STATUS_UNSTARTED = "NOT_STARTED";
    public static final String STATUS_PROCESSING = "PROCESSING";
    public static final String STATUS_FINISHED = "COMPLETED";
    public static final String STATUS_SUSPENDED = "SUSPENDED";
    public static final String STATUS_AUDITING = "AUDITING";
    public static final String STATUS_IGNORED = "IGNORED";

    public static final String COLLAB_SCHEMA_NAME = "协同模型";

    public static final String FUNC_MODULE_PROJECT_MNG = "projectMng";
    public static final String FUNC_MODULE_PLAN_MNG = "planMng";

    public static final boolean COLLAB_WBS_ENABLED;
    public static final boolean COLLAB_RESEARCH_DATA_ENABLED;
    public static final boolean COLLAB_MODEL_DATA_ENABLED;
    public static final boolean COLLAB_CHECK_DATA_ENABLED;
    public static final boolean COLLAB_KNOWLEDGE_ENABLED;
    //功能控制区域
    public static final boolean COLLAB_AUTO_EXECUTE_PLAN;
    public static final boolean COLLAB_ENABLE_PLAN_BREAK;
    public static final boolean COLLAB_ENABLE_PVMDATA;
    //是否是debug模式
    public static final boolean COLLAB_DEBUG_MODEL;

    public static final String COLLAB_PD_NAME_SPERATOR = "_";

    public static final String DISPLAY_ORDER_COL = "DISPLAY_ORDER_";

    static public final String BASELINE_AUDIT_NAME;

    static public final String BASELINE_CHANGE_AUDIT_NAME;

    static public final String USERID_SPERATOR = ",";

    public static final Map<String, String> STATUS_MAPPING = new LinkedHashMap<String, String>() {{
        put(CollabConstants.STATUS_UNSTARTED, "未开始");
        put(CollabConstants.STATUS_PROCESSING, "进行中");
        put(CollabConstants.STATUS_FINISHED, "已完成");
        put(CollabConstants.STATUS_SUSPENDED, "暂停中");
        put(CollabConstants.STATUS_AUDITING, "审批中");
        put(CollabConstants.STATUS_IGNORED, "已忽略");
    }};

    static {
        BASELINE_AUDIT_NAME = getPropertyValueConfigured("BASELINE.CREATE_AUDIT", "config.properties", "项目基线审批流程");
        BASELINE_CHANGE_AUDIT_NAME = getPropertyValueConfigured("BASELINE.UPDATE_AUDIT", "config.properties", "项目基线修改审批流程");
        COLLAB_WBS_ENABLED = Boolean.valueOf(getPropertyValueConfigured("collab.wbsEnabled", "config.properties", "true"));
        COLLAB_RESEARCH_DATA_ENABLED = Boolean.valueOf(getPropertyValueConfigured("collab.researchDataEnabled", "config.properties", "true"));
        COLLAB_MODEL_DATA_ENABLED = Boolean.valueOf(getPropertyValueConfigured("collab.modelDataEnabled", "config.properties", "true"));
        COLLAB_CHECK_DATA_ENABLED = Boolean.valueOf(getPropertyValueConfigured("collab.checkDataEnabled", "config.properties", "true"));
        COLLAB_KNOWLEDGE_ENABLED = Boolean.valueOf(getPropertyValueConfigured("collab.knowledgeEnabled", "config.properties", "false"));
        COLLAB_AUTO_EXECUTE_PLAN = Boolean.valueOf(getPropertyValueConfigured("collab.autoExecutePlan", "config.properties", "false"));
        COLLAB_ENABLE_PLAN_BREAK = Boolean.valueOf(getPropertyValueConfigured("collab.enablePlanBreak", "config.properties", "false"));
        COLLAB_ENABLE_PVMDATA = Boolean.valueOf(getPropertyValueConfigured("collab.enablePVMData", "config.properties", "false"));
        COLLAB_DEBUG_MODEL = Boolean.valueOf(getPropertyValueConfigured("collab.debugmodel", "config.properties", "false"));
    }
}
