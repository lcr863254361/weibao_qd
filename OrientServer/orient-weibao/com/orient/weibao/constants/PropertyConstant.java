package com.orient.weibao.constants;

import com.orient.config.ConfigInfo;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-12-11 14:24
 */
public class PropertyConstant extends ConfigInfo{

    public static String WEI_BAO_SCHEMA_ID;


    public static final String WEI_BAO_SCHEMA_NAME="维保";
    static {
        WEI_BAO_SCHEMA_ID=getPropertyValueConfigured("weiBao.schemaId",CONFIG_PROPERTIES,"");
    }
    //=================================================维保的数据模型====================================================
    public static final String SPARE_PARTS="T_SPARE_PARTS";
    public static final String SPARE_PARTS_INST="T_SPARE_PARTS_SHILI";
    public static final String CHECK_TYPE="T_CHECK_TYPE";
    public static final String CHECK_TEMP="T_CHECK_TEMP";
    public static final String CHECK_HEADER="T_CHECK_HEADER";
    public static final String CHECK_ROW="T_CHECK_ROW";
    public static final String CHECK_CELL="T_CHECK_CELL";

    public static final String CHECK_TEMP_INST="T_CHECK_TEMP_INST";
    public static final String CHECK_HEADER_INST="T_CHECK_HEADER_INST";
    public static final String CHECK_ROW_INST="T_CHECK_ROW_INST";
    public static final String CHECK_CELL_INST="T_CHECK_CELL_INST";

    public static final String HANGCI="T_HANGCI";
    public static final String HANGDUAN="T_HANGDUAN";
    public static final String DIVING_TASK="T_DIVING_TASK";
    public static final String NODE_DESIGN="T_NODE_DESIGN";

    public static final String REF_POST_NODE="T_REF_POST_NODE";
    public static final String POST_MGR="T_POST_MGR";

    public static final String PRODUCT_STRUCTURE="T_PRODUCT_STRUCTURE";
    public static final String SKILL_DOCUMENT="T_SKILL_DOCUMENT";

    public static final String CELL_INST_DATA="T_CELL_INST_DATA";

    public static final String TROUBLE_DEVICE_INST="T_TROUBLE_DEVICE_INS";

    public static final String ATTEND_PERSON="T_ATTEND_PERSON";

    public static final String WATER_DOWN_RECORD="T_WATER_DOWN_RECORD";

    public static final String DEVICE_LIFE_CYCLE="T_DEVICE_LIFE_CYCLE";

    public static final String OLD_DEVICE="T_OLD_DEVICE";

    public static final String CONSUME_MATERIAL="T_CONSUME_MATERIAL"; //耗材类别

    public static final String CONSUME_DETAIL="T_CONSUME_DETAIL";  //耗材

    public static final String FLOW_TEMP_TYPE="T_FLOW_TEMP_TYPE";	//流程模板类型

    public static final String INFORM_MGR="T_INFORM_MGR";	//通知管理

    public static final String HIS_INFORM_MGR="T_HIS_INFORM_MGR";	//历史通知管理

    public static final String INFORM_STATE="T_INFORM_STATE";	//通知状态管理

    public static final String DESTROY_TYPE="T_DESTROY_TYPE";	//拆解分类管理

    public static final String DESTROY_FLOW="T_DESTROY_FLOW";	//拆解流程管理

    public static final String DESTROY_TASK="T_DESTROY_TASK";	//拆解任务管理

    public static final String DIVING_STATISTICS="T_DIVING_STATISTICS";	//潜次统计管理

    public static final String DIVING_TYPE="T_DIVING_TYPE";	//潜次统计管理
    public static final String DIVING_CONFIG="T_DIVING_CONFIG";	//潜次统计管理
    public static final String DIVING_HOME="T_DIVING_HOME";	//潜次统计管理

    public static final String DIVING__PLAN_TABLE="T_DIVING__PLAN_TABLE";	//下潜计划表
    public static final String CARRY_TOOL="T_CARRY_TOOL";	//携带作业工具
    public static final String DIVING_DEVICE="T_DIVING_DEVICE";	//潜水器重量
    public static final String BALANCE_COUNT="T_BALANCE_COUNT";	//均衡计算表
    public static final String DIVING_REPORT="T_DIVING_REPORT";	//潜次报告表

    public static final String DEPTH_DESITY="T_DEPTH_DESITY";	//深度密度管理表

    public static final String PERSON_WEIGHT="T_PERSON_WEIGHT";	//人员体重管理表

    public static final String INFORM_LOG="T_INFORM_LOG";	//通知日志表

    public static final String SAMPLE_SITUATION="T_SAMPLE_SITUATION";   //采样情况表

    public static final String ROLE_STATISTICLINE="T_ROLE_STATISTICLINE";   //角色统计列表

    public static final String USER_STATISTICLINE="T_USER_STATISTICLINE";   //用户统计列表

    public static final String DEPTHDESITY_TYPE="T_DEPTH_DESITY_TYPE";   //深度密度分类

    public static final String CABIN_CARRY_TOOL="T_CABIN_CARRY_TOOL";   //舱内舱外携带的作业工具分类


    public static final String HANGDUAN_INFORM_LOG="T_HD_INFORM_LOG";   //航段通知日志

    public static final String STRUCT_SYSTEM="T_STRUCT_SYSTEM";   //产品结构系统

    public static final String STRUCT_DEVICE="T_STRUCT_DEVICE";   //产品结构设备

    public static final String STRUCT_PART="T_STRUCT_PART" ;      //产品结构零部件

    public static final String CYCLE_DEVICE="T_CYCLE_DEVICE";    //产品结构设备周期检查

    public static final String STRUCT_DEVICE_INS="T_STRUCT_DEVICE_INS";    //产品结构设备实例

    public static final String DEVICE_INS_EVENT="T_DEVICE_INS_EVENT";    //产品结构设备实例检查事件

    public static final String DAILY_WORK="T_DAILY_WORK";    //每日工作




    //===================================================配置的模板名字==================================================
    public static final String TPL_SPARE_PARTS="维保管理-备品备件管理-备品备件";
    public static final String TPL_SPARE_PARTS_INST="维保管理-备品备件管理-备品备件实例";
    public static final String TPL_SPARE_PARTS_CARRY_TOOL="维保管理-备品备件管理-携带的作业工具";

    public static final String TPL_HANGCI="维保管理-任务准备-航次管理";
    public static final String TPL_HANGDUAN="维保管理-任务准备-航段管理";
    public static final String TPL_DIVING_TASK="维保管理-任务准备-下潜任务管理";

    public static final String TPL_REF_POST_NODE="维保管理-任务准备-参与岗位";
    public static final String TPL_CHECK_TEMP_INST="维保管理-任务准备-检查表格";
    public static final String TPL_POST_MGR="维保管理-任务准备-岗位管理";

    public static final String TPL_CHECK_TEMP="维保管理-任务准备-检查表模板";
    public static final String TPL_SKILL_DOCUMENT="维保管理-任务准备-技术文档";

    public static final String TPL_CURRENT_CHECK_RECORD="维保管理-任务准备-当前检查记录";

    public static final String TPL_SHOW_CHECK_TEMP_INST="维保管理-当前任务-显示检查表模板实例";

    public static final String TPL_ATTEND_PERSON="维保管理-任务准备-参与人员";

    public static final String TPL_TROUBLE_DEVICE_INST="维保管理-备品备件管理-故障记录";

    public static final String TPL_WATER_DOWN_RECORD="维保管理-当前任务-水下记录单";

    public static final String TPL_WATER_RECORD="维保管理-当前任务-水面记录单";

    public static final String TPL_DEVICE_LIFE_CYCLE="维保管理-备品备件管理-设备寿命全周期";

    public static final String TPL_PRODUCT_STRUCTURE="维保管理-产品结构管理";

    public static final String TPL_CONSUME_DETAIL="维保管理-耗材管理";

    public static final String TPL_SELECT_CONSUME_DETAIL="维保管理-选择的耗材";

    public static final String TPL_FLOW_TEMP_TYPE="维保管理-流程模板类型";

    public static final String TPL_INFORM_MGR="维保管理-通知管理";

    public static final String TPL_DESTROY_FLOW="维保管理-拆解流程管理";

    public static final String TPL_DESTROY_TASK="维保管理-拆解任务管理";

    public static final String TPL_DIVING_STATISTICS="维保管理-潜次统计管理";

    public static final String TPL_DEVICE_INST_RESEARCH="维保管理-备品备件管理-入所检验";

    public static final String TPL_DEPTH_DESITY="维保管理-深度密度管理";	//深度密度管理表

    public static final String TPL_PERSON_WEIGHT="维保管理-人员体重管理";

    public static final String TPL_CHECK_CELL="维保管理-表单模板管理-表单单元格管理";


    public static final String TPL_DIVING_TYPE="无人潜水器管理-无人潜水器类型配置";
    public static final String TPL_DIVING_CONFIG="无人潜水器管理-无人潜水器配置";
    public static final String TPL_DIVING_HOME="无人潜水器管理-无人潜水器作业类型配置";

    public static final String EXCEL_STRUCT_HEADER="struct";

    public static final String TPL_DIVING__PLAN_TABLE="维保管理-科学家任务-科学家下潜计划管理";

    public static final String TPL_BALANCE_COUNT="维保管理-任务管理-配重管理";

    public static final String TPL_CABIN_OUT_TOOL="维保管理-携带的作业工具-舱外携带的作业工具";
    public static final String TPL_CABIN_IN_TOOL="维保管理-携带的作业工具-舱内携带的作业工具";

    public static final String TPL_STRUCT_SYSTEM="维保管理-产品结构树-产品结构系统";

    public static final String TPL_STRUCT_DEVICE="维保管理-产品结构树-产品结构设备";

    public static final String TPL_STRUCT_PART="维保管理-产品结构树-产品结构零部件";

    public static final String TPL_CYCLE_DEVICE="维保管理-产品结构树-产品结构设备-设备周期检查";    //结构设备周期检查

    public static final String TPL_CYCLE_DEVICE_REF_CHECK_TEMP_INST="维保管理-产品结构树-产品结构设备-设备周期检查-检查表实例"; //结构设备周期检查关联检查表

    public static final String TPL_STRUCT_DEVICE_INS="维保管理-产品结构树-产品结构设备-产品结构设备实例";  //产品结构设备实例

    public static final String TPL_DEVICE_INS_EVENT="维保管理-产品结构树-产品结构设备-产品结构设备实例-设备实例检查事件";  //设备实例检查事件

    public static final String TPL_CHECK_TYPE="维保管理-表单模板管理-表单模板类型";  //表单模板类型
    public static final String TPL_CHECK_TEMPLATE="维保管理-表单模板管理-表单模板";  //表单模板

    public static final String TPL_DAILY_WORK="维保管理-每日工作";
}
