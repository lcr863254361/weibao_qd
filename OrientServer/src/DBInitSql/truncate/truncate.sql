TRUNCATE TABLE ALARM_INFO;
TRUNCATE TABLE ALARM_NOTICE;
TRUNCATE TABLE ALARM_USER;
TRUNCATE TABLE ALARM_USER_HIST;
TRUNCATE TABLE AUDIT_FLOW_MODEL_BIND;
TRUNCATE TABLE AUDIT_FLOW_OPINION_SETTING;
TRUNCATE TABLE AUDIT_FLOW_TASK_SETTING;

TRUNCATE TABLE CB_B_LINE_240;
TRUNCATE TABLE CB_DIR_240;
TRUNCATE TABLE CB_PLAN_240;
TRUNCATE TABLE CB_PLAN_R_240;
TRUNCATE TABLE CB_PROJECT_240;
TRUNCATE TABLE CB_TASK_240;

TRUNCATE TABLE COLLAB_DATA_FLOW;
TRUNCATE TABLE COLLAB_DATATASK;
TRUNCATE TABLE COLLAB_DATATASK_HIS;
TRUNCATE TABLE COLLAB_FLOW_RELATION;
TRUNCATE TABLE COLLAB_JOB_CARVEPOS;
TRUNCATE TABLE COLLAB_PRJ_MODEL_RELATION;
TRUNCATE TABLE COLLAB_ROLE;
TRUNCATE TABLE COLLAB_ROLE_FUNCTION;
TRUNCATE TABLE COLLAB_ROLE_USER;
--TRUNCATE TABLE COLLAB_TEMPLATE;
--TRUNCATE TABLE COLLAB_TEMPLATE_NODE;
TRUNCATE TABLE FLOW_DATA_RELATION;
TRUNCATE TABLE TASK_DATA_RELATION;

TRUNCATE TABLE JBPM4_CONFIG_USER;
TRUNCATE TABLE JBPM4_COUNTERSIGN_INFO;
TRUNCATE TABLE JBPM4_DEPLOYMENT;
TRUNCATE TABLE JBPM4_DEPLOYPROP;
TRUNCATE TABLE JBPM4_EXECUTION;
TRUNCATE TABLE JBPM4_FLOW_BRANCH_RELATION;
TRUNCATE TABLE JBPM4_FLOW_RELATION;
TRUNCATE TABLE JBPM4_HIST_ACTINST;
TRUNCATE TABLE JBPM4_HIST_DETAIL;
TRUNCATE TABLE JBPM4_HIST_PROCINST;
TRUNCATE TABLE JBPM4_HIST_TASK;
TRUNCATE TABLE JBPM4_HIST_VAR;
TRUNCATE TABLE JBPM4_ID_GROUP;
TRUNCATE TABLE JBPM4_ID_MEMBERSHIP;
TRUNCATE TABLE JBPM4_ID_USER;
TRUNCATE TABLE JBPM4_JOB;
TRUNCATE TABLE JBPM4_LOB;
TRUNCATE TABLE JBPM4_OPINION;
TRUNCATE TABLE JBPM4_PARTICIPATION;
TRUNCATE TABLE JBPM4_PROPERTY;
TRUNCATE TABLE JBPM4_RDM_COURSE;
TRUNCATE TABLE JBPM4_RDM_PARAMMAPPING;
TRUNCATE TABLE JBPM4_RDM_TASK_VAR;
TRUNCATE TABLE JBPM4_RDM_VARIABLE;
TRUNCATE TABLE JBPM4_SWIMLANE;
TRUNCATE TABLE JBPM4_TASK;
TRUNCATE TABLE JBPM4_VARIABLE;

TRUNCATE TABLE CWM_BACK;
TRUNCATE TABLE CWM_DATAOBJECT;
TRUNCATE TABLE CWM_DATAOBJECT_OLDVERSION;
TRUNCATE TABLE CWM_FILE;
TRUNCATE TABLE CWM_MSG;
TRUNCATE TABLE CWM_SYS_HIS_TASK;
TRUNCATE TABLE CWM_SYS_LOG;
TRUNCATE TABLE CWM_SYS_USERLOGINHISTORY;

TRUNCATE TABLE T_DEV_TYPE_420;
TRUNCATE TABLE T_DEVICE_420;
TRUNCATE TABLE T_HJFL_420;
TRUNCATE TABLE T_JLFL_420;
TRUNCATE TABLE T_JLSB_420;
TRUNCATE TABLE T_RYFL_420;
TRUNCATE TABLE T_SBBQ_420;
TRUNCATE TABLE T_SBGZJL_420;
TRUNCATE TABLE T_SBJLJL_420;
TRUNCATE TABLE T_SBSYJL_420;
TRUNCATE TABLE T_SBWXJL_420;
TRUNCATE TABLE T_SYHJ_420;
TRUNCATE TABLE T_SYJ_420;
TRUNCATE TABLE T_SYJFL_420;
TRUNCATE TABLE T_SYRY_420;
TRUNCATE TABLE T_SYTD_420;
TRUNCATE TABLE T_SYXT_420;
TRUNCATE TABLE T_SYYP_420;
TRUNCATE TABLE T_TD_RY_420;
TRUNCATE TABLE T_YPFL_420;

INSERT INTO "JBPM4_PROPERTY" ("KEY_", "VERSION_", "VALUE_") VALUES ('next.dbid', '0', '1');
INSERT INTO "CB_DIR_240" ("ID", "NAME_343") VALUES ('-1', '所有项目');
