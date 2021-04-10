/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016/3/10 星期四 11:06:05                       */
/*==============================================================*/


alter table JBPM4_DEPLOYPROP
   drop constraint FK_DEPLPROP_DEPL;

alter table JBPM4_EXECUTION
   drop constraint FK_EXEC_INSTANCE;

alter table JBPM4_EXECUTION
   drop constraint FK_EXEC_PARENT;

alter table JBPM4_EXECUTION
   drop constraint FK_EXEC_SUBPI;

alter table JBPM4_EXECUTION
   drop constraint FK_EXEC_SUPEREXEC;

alter table JBPM4_HIST_ACTINST
   drop constraint FK_HACTI_HPROCI;

alter table JBPM4_HIST_ACTINST
   drop constraint FK_HTI_HTASK;

alter table JBPM4_HIST_DETAIL
   drop constraint FK_HDETAIL_HACTI;

alter table JBPM4_HIST_DETAIL
   drop constraint FK_HDETAIL_HPROCI;

alter table JBPM4_HIST_DETAIL
   drop constraint FK_HDETAIL_HTASK;

alter table JBPM4_HIST_DETAIL
   drop constraint FK_HDETAIL_HVAR;

alter table JBPM4_HIST_TASK
   drop constraint FK_HSUPERT_SUB;

alter table JBPM4_HIST_VAR
   drop constraint FK_HVAR_HPROCI;

alter table JBPM4_HIST_VAR
   drop constraint FK_HVAR_HTASK;

alter table JBPM4_ID_GROUP
   drop constraint FK_GROUP_PARENT;

alter table JBPM4_ID_MEMBERSHIP
   drop constraint FK_MEM_GROUP;

alter table JBPM4_ID_MEMBERSHIP
   drop constraint FK_MEM_USER;

alter table JBPM4_LOB
   drop constraint FK_LOB_DEPLOYMENT;

alter table JBPM4_PARTICIPATION
   drop constraint FK_PART_SWIMLANE;

alter table JBPM4_PARTICIPATION
   drop constraint FK_PART_TASK;

alter table JBPM4_SWIMLANE
   drop constraint FK_SWIMLANE_EXEC;

alter table JBPM4_TASK
   drop constraint FK_TASK_SUPERTASK;

alter table JBPM4_TASK
   drop constraint FK_TASK_SWIML;

alter table JBPM4_VARIABLE
   drop constraint FK_VAR_EXECUTION;

alter table JBPM4_VARIABLE
   drop constraint FK_VAR_EXESYS;

alter table JBPM4_VARIABLE
   drop constraint FK_VAR_TASK;

drop table AUDIT_FLOW_DATA_BIND cascade constraints;

drop table AUDIT_FLOW_DATA_RELATION cascade constraints;

drop table AUDIT_FLOW_MODEL_BIND cascade constraints;

drop table CWM_COMPONENT cascade constraints;

drop table CWM_COMPONENT_RELATIONDATA cascade constraints;

drop table CWM_PROJECT_MODEL_RELATION cascade constraints;

drop table JBPM4_CONFIG_USER cascade constraints;

drop table JBPM4_COUNTERSIGN_INFO cascade constraints;

drop table JBPM4_DEPLOYMENT cascade constraints;

drop table JBPM4_DEPLOYPROP cascade constraints;

drop table JBPM4_EXECUTION cascade constraints;

drop table JBPM4_FLOW_BRANCH_RELATION cascade constraints;

drop table JBPM4_FLOW_RELATION cascade constraints;

drop table JBPM4_HIST_ACTINST cascade constraints;

drop table JBPM4_HIST_DETAIL cascade constraints;

drop table JBPM4_HIST_PROCINST cascade constraints;

drop table JBPM4_HIST_TASK cascade constraints;

drop table JBPM4_HIST_VAR cascade constraints;

drop table JBPM4_ID_GROUP cascade constraints;

drop table JBPM4_ID_MEMBERSHIP cascade constraints;

drop table JBPM4_ID_USER cascade constraints;

drop table JBPM4_JOB cascade constraints;

drop table JBPM4_LOB cascade constraints;

drop table JBPM4_OPINION cascade constraints;

drop table JBPM4_PARTICIPATION cascade constraints;

drop table JBPM4_PROPERTY cascade constraints;

drop table JBPM4_RDM_COURSE cascade constraints;

drop table JBPM4_RDM_PARAMMAPPING cascade constraints;

drop table JBPM4_RDM_TASK_VAR cascade constraints;

drop table JBPM4_RDM_VARIABLE cascade constraints;

drop table JBPM4_SWIMLANE cascade constraints;

drop table JBPM4_TASK cascade constraints;

drop table JBPM4_VARIABLE cascade constraints;

/*==============================================================*/
/* Table: AUDIT_FLOW_DATA_BIND                                  */
/*==============================================================*/
create table AUDIT_FLOW_DATA_BIND  (
   ID                   NUMBER(8)                       not null,
   SCHEMA_ID            VARCHAR2(20),
   TABLE_NAME           VARCHAR2(20),
   DATA_ID              VARCHAR2(20),
   PD_ID                VARCHAR2(50)
);

comment on table AUDIT_FLOW_DATA_BIND is
'模型审批模板绑定表';

comment on column AUDIT_FLOW_DATA_BIND.ID is
'ID';

comment on column AUDIT_FLOW_DATA_BIND.SCHEMA_ID is
'数据所属schema';

comment on column AUDIT_FLOW_DATA_BIND.TABLE_NAME is
'数据所属模型名称';

comment on column AUDIT_FLOW_DATA_BIND.DATA_ID is
'审批流程绑定数据ID';

comment on column AUDIT_FLOW_DATA_BIND.PD_ID is
'审批流程所属流程定义';

/*==============================================================*/
/* Table: AUDIT_FLOW_DATA_RELATION                              */
/*==============================================================*/
create table AUDIT_FLOW_DATA_RELATION  (
   ID                   NUMBER(8)                       not null,
   AUDIT_TYPE           VARCHAR2(30),
   TABLE_NAME           VARCHAR2(20),
   DATA_ID              VARCHAR2(20),
   PI_ID                VARCHAR2(50),
   CREATE_TIME          TIMESTAMP(6),
   FILTER               VARCHAR2(20),
   LAUNCHER             VARCHAR2(30)
);

comment on table AUDIT_FLOW_DATA_RELATION is
'流程与审批模型数据关系表';

comment on column AUDIT_FLOW_DATA_RELATION.ID is
'ID';

comment on column AUDIT_FLOW_DATA_RELATION.AUDIT_TYPE is
'审批流程类型';

comment on column AUDIT_FLOW_DATA_RELATION.TABLE_NAME is
'数据所属模型名称';

comment on column AUDIT_FLOW_DATA_RELATION.DATA_ID is
'审批数据ID';

comment on column AUDIT_FLOW_DATA_RELATION.PI_ID is
'审批流程实例ID';

comment on column AUDIT_FLOW_DATA_RELATION.CREATE_TIME is
'审批流程创建时间';

comment on column AUDIT_FLOW_DATA_RELATION.FILTER is
'审批数据过滤';

comment on column AUDIT_FLOW_DATA_RELATION.LAUNCHER is
'审批发起人';

/*==============================================================*/
/* Table: AUDIT_FLOW_MODEL_BIND                                 */
/*==============================================================*/
create table AUDIT_FLOW_MODEL_BIND  (
   ID                   NUMBER(8)                       not null,
   SCHEMA_ID            VARCHAR2(20),
   MODEL_NAME           VARCHAR2(80),
   FLOW_NAME            VARCHAR2(80),
   FLOW_VERSION         VARCHAR2(20),
   AUDIT_FORM           BLOB,
   USER_NAME            VARCHAR2(20),
   LAST_UPDATE_DATE     DATE
);

comment on table AUDIT_FLOW_MODEL_BIND is
'审批流程数据绑定';

comment on column AUDIT_FLOW_MODEL_BIND.ID is
'ID';

comment on column AUDIT_FLOW_MODEL_BIND.SCHEMA_ID is
'绑定模型所属schema';

comment on column AUDIT_FLOW_MODEL_BIND.MODEL_NAME is
'绑定模型名称';

comment on column AUDIT_FLOW_MODEL_BIND.FLOW_NAME is
'审批流程名称';

comment on column AUDIT_FLOW_MODEL_BIND.FLOW_VERSION is
'审批流程版本';

comment on column AUDIT_FLOW_MODEL_BIND.AUDIT_FORM is
'审批流程表单';

comment on column AUDIT_FLOW_MODEL_BIND.USER_NAME is
'绑定模型人';

comment on column AUDIT_FLOW_MODEL_BIND.LAST_UPDATE_DATE is
'上次更新时间';

/*==============================================================*/
/* Table: CWM_COMPONENT                                         */
/*==============================================================*/
create table CWM_COMPONENT  (
   ID                   NUMBER                          not null,
   COMPONENTNAME        VARCHAR2(100),
   CLASSNAME            VARCHAR2(200),
   REMARK               VARCHAR2(200),
   constraint PK_CWM_COMPONENT primary key (ID)
);

comment on table CWM_COMPONENT is
'定制组件';

comment on column CWM_COMPONENT.ID is
'组件数据传递表的唯一主键';

comment on column CWM_COMPONENT.COMPONENTNAME is
'组件名称';

comment on column CWM_COMPONENT.CLASSNAME is
'类的全名';

comment on column CWM_COMPONENT.REMARK is
'描述';

/*==============================================================*/
/* Table: CWM_COMPONENT_RELATIONDATA                            */
/*==============================================================*/
create table CWM_COMPONENT_RELATIONDATA  (
   ID                   NUMBER                          not null,
   PRJCODE              VARCHAR2(100),
   PARAMKEY             VARCHAR2(100),
   PARAMVALUE           VARCHAR2(200),
   constraint PK_CWM_COMPONENT_RELATIONDATA primary key (ID)
);

comment on table CWM_COMPONENT_RELATIONDATA is
'定制组建与项目关系表';

comment on column CWM_COMPONENT_RELATIONDATA.ID is
'组件数据传递表的唯一主键';

comment on column CWM_COMPONENT_RELATIONDATA.PRJCODE is
'项目编号';

comment on column CWM_COMPONENT_RELATIONDATA.PARAMKEY is
'参数关键字';

comment on column CWM_COMPONENT_RELATIONDATA.PARAMVALUE is
'参数值';

/*==============================================================*/
/* Table: CWM_PROJECT_MODEL_RELATION                            */
/*==============================================================*/
create table CWM_PROJECT_MODEL_RELATION  (
   ID                   NUMBER                          not null,
   MODELNAME            VARCHAR2(100),
   SCHEMAID             VARCHAR2(40),
   DATAID               VARCHAR2(40),
   PROJECTID            VARCHAR2(40),
   constraint PK_CWM_PROJECT_MODEL_RELATION primary key (ID)
);

comment on table CWM_PROJECT_MODEL_RELATION is
'项目绑定模型表';

comment on column CWM_PROJECT_MODEL_RELATION.ID is
'模型与试验项目关系主键';

comment on column CWM_PROJECT_MODEL_RELATION.MODELNAME is
'主模型名称';

comment on column CWM_PROJECT_MODEL_RELATION.SCHEMAID is
'主模型的schemaid';

comment on column CWM_PROJECT_MODEL_RELATION.DATAID is
'主模型数据id';

comment on column CWM_PROJECT_MODEL_RELATION.PROJECTID is
'项目id';

/*==============================================================*/
/* Table: JBPM4_CONFIG_USER                                     */
/*==============================================================*/
create table JBPM4_CONFIG_USER  (
   USERID_              VARCHAR2(20),
   CONFIG_USERID_       VARCHAR2(20),
   TYPE_                VARCHAR2(2),
   EXECUTIONID_         VARCHAR2(255),
   REMARK_              VARCHAR2(200)
);

comment on table JBPM4_CONFIG_USER is
'流程代办人员信息表';

comment on column JBPM4_CONFIG_USER.USERID_ is
'被代办的人员';

comment on column JBPM4_CONFIG_USER.CONFIG_USERID_ is
'指定的代办人员';

comment on column JBPM4_CONFIG_USER.TYPE_ is
'类型，预留';

comment on column JBPM4_CONFIG_USER.EXECUTIONID_ is
'指定流程ID，预留';

comment on column JBPM4_CONFIG_USER.REMARK_ is
'备注';

/*==============================================================*/
/* Table: JBPM4_COUNTERSIGN_INFO                                */
/*==============================================================*/
create table JBPM4_COUNTERSIGN_INFO  (
   EXECUTION_ID         VARCHAR2(255),
   TASKNAME             VARCHAR2(255),
   USERCOUNT            VARCHAR2(10),
   STRATEGY             VARCHAR2(20),
   STRATEGY_VALUE       VARCHAR2(10),
   REMARK               VARCHAR2(200)
);

comment on table JBPM4_COUNTERSIGN_INFO is
'会签任务详细表';

comment on column JBPM4_COUNTERSIGN_INFO.EXECUTION_ID is
'流程ID';

comment on column JBPM4_COUNTERSIGN_INFO.TASKNAME is
'节点任务名称';

comment on column JBPM4_COUNTERSIGN_INFO.USERCOUNT is
'该会签节点需要参与的人数';

comment on column JBPM4_COUNTERSIGN_INFO.STRATEGY is
'会签策略[在WorkFlow Studio中设定的策略]';

comment on column JBPM4_COUNTERSIGN_INFO.STRATEGY_VALUE is
'会签策略结果[根据会签总人数和会签策略计算出最终该会签节点最小的通过人数]';

comment on column JBPM4_COUNTERSIGN_INFO.REMARK is
'备注';

/*==============================================================*/
/* Table: JBPM4_DEPLOYMENT                                      */
/*==============================================================*/
create table JBPM4_DEPLOYMENT  (
   DBID_                NUMBER(19)                      not null,
   NAME_                CLOB,
   TIMESTAMP_           NUMBER(19),
   STATE_               VARCHAR2(255),
   constraint PK_JBPM4_DEPLOYMENT primary key (DBID_)
);

comment on table JBPM4_DEPLOYMENT is
'流程部署信息表';

comment on column JBPM4_DEPLOYMENT.DBID_ is
'流程部署DBID';

comment on column JBPM4_DEPLOYMENT.NAME_ is
'部署人';

comment on column JBPM4_DEPLOYMENT.TIMESTAMP_ is
'部署时间';

comment on column JBPM4_DEPLOYMENT.STATE_ is
'部署状态';

/*==============================================================*/
/* Table: JBPM4_DEPLOYPROP                                      */
/*==============================================================*/
create table JBPM4_DEPLOYPROP  (
   DBID_                NUMBER(19)                      not null,
   DEPLOYMENT_          NUMBER(19),
   OBJNAME_             VARCHAR2(255),
   KEY_                 VARCHAR2(255),
   STRINGVAL_           VARCHAR2(255),
   LONGVAL_             NUMBER(19),
   constraint PK_JBPM4_DEPLOYPROP primary key (DBID_)
);

comment on table JBPM4_DEPLOYPROP is
'流程部署描述';

comment on column JBPM4_DEPLOYPROP.DBID_ is
'部署描述DBID';

comment on column JBPM4_DEPLOYPROP.DEPLOYMENT_ is
'所属流程部署';

comment on column JBPM4_DEPLOYPROP.OBJNAME_ is
'属性分组';

comment on column JBPM4_DEPLOYPROP.KEY_ is
'部署属性key';

comment on column JBPM4_DEPLOYPROP.STRINGVAL_ is
'属性为String类型的值';

comment on column JBPM4_DEPLOYPROP.LONGVAL_ is
'属性为Long类型的值';

/*==============================================================*/
/* Table: JBPM4_EXECUTION                                       */
/*==============================================================*/
create table JBPM4_EXECUTION  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               VARCHAR2(255)                   not null,
   DBVERSION_           NUMBER(10)                      not null,
   ACTIVITYNAME_        VARCHAR2(255),
   PROCDEFID_           VARCHAR2(255),
   HASVARS_             NUMBER(1),
   NAME_                VARCHAR2(255),
   KEY_                 VARCHAR2(255),
   ID_                  VARCHAR2(255),
   STATE_               VARCHAR2(255),
   SUSPHISTSTATE_       VARCHAR2(255),
   PRIORITY_            NUMBER(10),
   HISACTINST_          NUMBER(19),
   PARENT_              NUMBER(19),
   INSTANCE_            NUMBER(19),
   SUPEREXEC_           NUMBER(19),
   SUBPROCINST_         NUMBER(19),
   PARENT_IDX_          NUMBER(10),
   constraint PK_JBPM4_EXECUTION primary key (DBID_),
   constraint AK_KEY_2_JBPM4_EX unique (ID_)
);

comment on table JBPM4_EXECUTION is
'执行中的流程';

comment on column JBPM4_EXECUTION.DBID_ is
'流程实例DBID';

comment on column JBPM4_EXECUTION.DBVERSION_ is
'流程实例版本';

comment on column JBPM4_EXECUTION.ACTIVITYNAME_ is
'当前流程实例中活动节点名称';

comment on column JBPM4_EXECUTION.PROCDEFID_ is
'所属流程定义ID';

comment on column JBPM4_EXECUTION.HASVARS_ is
'是否包含流程变量';

comment on column JBPM4_EXECUTION.NAME_ is
'自定义流程实例名称';

comment on column JBPM4_EXECUTION.KEY_ is
'自定义流程实例KEY';

comment on column JBPM4_EXECUTION.ID_ is
'流程实例ID';

comment on column JBPM4_EXECUTION.STATE_ is
'流程实例状态';

comment on column JBPM4_EXECUTION.SUSPHISTSTATE_ is
'暂停/恢复流程历史状态';

comment on column JBPM4_EXECUTION.PRIORITY_ is
'优先级';

comment on column JBPM4_EXECUTION.HISACTINST_ is
'所属历史流程实例';

comment on column JBPM4_EXECUTION.PARENT_ is
'父亲流程实例id（可能存在多层级流程实例关系）';

comment on column JBPM4_EXECUTION.INSTANCE_ is
'所属主线流程实例ID';

comment on column JBPM4_EXECUTION.SUPEREXEC_ is
'父流程实例ID（父子流程关系）';

comment on column JBPM4_EXECUTION.SUBPROCINST_ is
'子流程实例ID（父子流程关系）';

comment on column JBPM4_EXECUTION.PARENT_IDX_ is
'父亲流程实例下标';

/*==============================================================*/
/* Table: JBPM4_FLOW_BRANCH_RELATION                            */
/*==============================================================*/
create table JBPM4_FLOW_BRANCH_RELATION  (
   ID                   VARCHAR2(38),
   SUP_DBID_            NUMBER(19),
   SUP_EXC_ID_          VARCHAR2(255),
   SUB_DBID_            NUMBER(19),
   SUB_EXC_ID_          VARCHAR2(255)
);

comment on table JBPM4_FLOW_BRANCH_RELATION is
'分支主线流程实例关系';

comment on column JBPM4_FLOW_BRANCH_RELATION.ID is
'主键ID';

comment on column JBPM4_FLOW_BRANCH_RELATION.SUP_DBID_ is
'主流程的部署ID';

comment on column JBPM4_FLOW_BRANCH_RELATION.SUP_EXC_ID_ is
'主流程的EXECUTION的ID';

comment on column JBPM4_FLOW_BRANCH_RELATION.SUB_DBID_ is
'子流程的部署ID';

comment on column JBPM4_FLOW_BRANCH_RELATION.SUB_EXC_ID_ is
'子流程的EXECUTION的ID';

/*==============================================================*/
/* Table: JBPM4_FLOW_RELATION                                   */
/*==============================================================*/
create table JBPM4_FLOW_RELATION  (
   ID                   VARCHAR2(38),
   SUP_DBID_            NUMBER(19),
   SUP_EXC_ID_          VARCHAR2(255),
   SUB_DBID_            NUMBER(19),
   SUB_EXC_ID_          VARCHAR2(255),
   TASKID               VARCHAR2(255)
);

comment on table JBPM4_FLOW_RELATION is
'父子流程实例关系表';

comment on column JBPM4_FLOW_RELATION.ID is
'主键ID';

comment on column JBPM4_FLOW_RELATION.SUP_DBID_ is
'主流程的部署ID';

comment on column JBPM4_FLOW_RELATION.SUP_EXC_ID_ is
'主流程的EXECUTION的ID';

comment on column JBPM4_FLOW_RELATION.SUB_DBID_ is
'子流程的部署ID';

comment on column JBPM4_FLOW_RELATION.SUB_EXC_ID_ is
'子流程的EXECUTION的ID';

comment on column JBPM4_FLOW_RELATION.TASKID is
'？';

/*==============================================================*/
/* Table: JBPM4_HIST_ACTINST                                    */
/*==============================================================*/
create table JBPM4_HIST_ACTINST  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               VARCHAR2(255)                   not null,
   DBVERSION_           NUMBER(10)                      not null,
   HPROCI_              NUMBER(19),
   TYPE_                VARCHAR2(255),
   EXECUTION_           VARCHAR2(255),
   ACTIVITY_NAME_       VARCHAR2(255),
   START_               TIMESTAMP(6),
   END_                 TIMESTAMP(6),
   DURATION_            NUMBER(19),
   TRANSITION_          VARCHAR2(255),
   NEXTIDX_             NUMBER(10),
   HTASK_               NUMBER(19),
   constraint PK_JBPM4_HIST_ACTINST primary key (DBID_)
);

comment on table JBPM4_HIST_ACTINST is
'历史活动表 包含了人工任务';

comment on column JBPM4_HIST_ACTINST.DBID_ is
'历史活动DBID';

comment on column JBPM4_HIST_ACTINST.DBVERSION_ is
'历史活动版本';

comment on column JBPM4_HIST_ACTINST.HPROCI_ is
'所属主线流程实例ID';

comment on column JBPM4_HIST_ACTINST.TYPE_ is
'活动类型';

comment on column JBPM4_HIST_ACTINST.EXECUTION_ is
'所属流程实例ID';

comment on column JBPM4_HIST_ACTINST.ACTIVITY_NAME_ is
'历史活动名称';

comment on column JBPM4_HIST_ACTINST.START_ is
'开始时间';

comment on column JBPM4_HIST_ACTINST.END_ is
'结束时间';

comment on column JBPM4_HIST_ACTINST.DURATION_ is
'持续时间';

comment on column JBPM4_HIST_ACTINST.TRANSITION_ is
'完成活动时选择的下个任务流向';

comment on column JBPM4_HIST_ACTINST.NEXTIDX_ is
'下个下标';

comment on column JBPM4_HIST_ACTINST.HTASK_ is
'所属历史人工任务';

/*==============================================================*/
/* Table: JBPM4_HIST_DETAIL                                     */
/*==============================================================*/
create table JBPM4_HIST_DETAIL  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               VARCHAR2(255)                   not null,
   DBVERSION_           NUMBER(10)                      not null,
   USERID_              VARCHAR2(255),
   TIME_                TIMESTAMP(6),
   HPROCI_              NUMBER(19),
   HPROCIIDX_           NUMBER(10),
   HACTI_               NUMBER(19),
   HACTIIDX_            NUMBER(10),
   HTASK_               NUMBER(19),
   HTASKIDX_            NUMBER(10),
   HVAR_                NUMBER(19),
   HVARIDX_             NUMBER(10),
   MESSAGE_             CLOB,
   OLD_STR_             VARCHAR2(255),
   NEW_STR_             VARCHAR2(255),
   OLD_INT_             NUMBER(10),
   NEW_INT_             NUMBER(10),
   OLD_TIME_            TIMESTAMP(6),
   NEW_TIME_            TIMESTAMP(6),
   PARENT_              NUMBER(19),
   PARENT_IDX_          NUMBER(10),
   constraint PK_JBPM4_HIST_DETAIL primary key (DBID_)
);

comment on table JBPM4_HIST_DETAIL is
'历史详细表，主要用于流程实例合并';

comment on column JBPM4_HIST_DETAIL.DBID_ is
'历史详细DBID';

comment on column JBPM4_HIST_DETAIL.DBVERSION_ is
'历史详细版本';

comment on column JBPM4_HIST_DETAIL.USERID_ is
'变更人';

comment on column JBPM4_HIST_DETAIL.TIME_ is
'变更时间';

comment on column JBPM4_HIST_DETAIL.HPROCI_ is
'绑定的历史流程实例';

comment on column JBPM4_HIST_DETAIL.HPROCIIDX_ is
'绑定的历史流程实例下标';

comment on column JBPM4_HIST_DETAIL.HACTI_ is
'绑定的历史活动';

comment on column JBPM4_HIST_DETAIL.HACTIIDX_ is
'绑定历史活动下标';

comment on column JBPM4_HIST_DETAIL.HTASK_ is
'绑定的历史人工任务';

comment on column JBPM4_HIST_DETAIL.HTASKIDX_ is
'绑定的历史人工任务下标';

comment on column JBPM4_HIST_DETAIL.HVAR_ is
'绑定的历史变量';

comment on column JBPM4_HIST_DETAIL.HVARIDX_ is
'绑定的历史变量下标';

comment on column JBPM4_HIST_DETAIL.MESSAGE_ is
'备注';

comment on column JBPM4_HIST_DETAIL.OLD_STR_ is
'旧的String变量值';

comment on column JBPM4_HIST_DETAIL.NEW_STR_ is
'新的String变量值';

comment on column JBPM4_HIST_DETAIL.OLD_INT_ is
'旧的Int变量值';

comment on column JBPM4_HIST_DETAIL.NEW_INT_ is
'新的Int变量值';

comment on column JBPM4_HIST_DETAIL.OLD_TIME_ is
'旧的时间';

comment on column JBPM4_HIST_DETAIL.NEW_TIME_ is
'变更时间';

comment on column JBPM4_HIST_DETAIL.PARENT_ is
'父变更';

comment on column JBPM4_HIST_DETAIL.PARENT_IDX_ is
'父变更下标';

/*==============================================================*/
/* Table: JBPM4_HIST_PROCINST                                   */
/*==============================================================*/
create table JBPM4_HIST_PROCINST  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           NUMBER(10)                      not null,
   ID_                  VARCHAR2(255),
   PROCDEFID_           VARCHAR2(255),
   KEY_                 VARCHAR2(255),
   START_               TIMESTAMP(6),
   END_                 TIMESTAMP(6),
   DURATION_            NUMBER(19),
   STATE_               VARCHAR2(255),
   ENDACTIVITY_         VARCHAR2(255),
   NEXTIDX_             NUMBER(10),
   constraint PK_JBPM4_HIST_PROCINST primary key (DBID_)
);

comment on table JBPM4_HIST_PROCINST is
'历史流程实例表';

comment on column JBPM4_HIST_PROCINST.DBID_ is
'历史流程实例DBID';

comment on column JBPM4_HIST_PROCINST.DBVERSION_ is
'历史流程实例版本';

comment on column JBPM4_HIST_PROCINST.ID_ is
'历史流程实例ID';

comment on column JBPM4_HIST_PROCINST.PROCDEFID_ is
'所属流程定义ID';

comment on column JBPM4_HIST_PROCINST.KEY_ is
'历史流程key';

comment on column JBPM4_HIST_PROCINST.START_ is
'开始时间';

comment on column JBPM4_HIST_PROCINST.END_ is
'历史流程结束时间';

comment on column JBPM4_HIST_PROCINST.DURATION_ is
'历史流程持续时间';

comment on column JBPM4_HIST_PROCINST.STATE_ is
'历史流程实例状态';

comment on column JBPM4_HIST_PROCINST.ENDACTIVITY_ is
'历史流程结束活动';

comment on column JBPM4_HIST_PROCINST.NEXTIDX_ is
'下个下标';

/*==============================================================*/
/* Table: JBPM4_HIST_TASK                                       */
/*==============================================================*/
create table JBPM4_HIST_TASK  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           NUMBER(10)                      not null,
   EXECUTION_           VARCHAR2(255),
   OUTCOME_             VARCHAR2(255),
   ASSIGNEE_            VARCHAR2(255),
   PRIORITY_            NUMBER(10),
   STATE_               VARCHAR2(255),
   CREATE_              TIMESTAMP(6),
   END_                 TIMESTAMP(6),
   DURATION_            NUMBER(19),
   NEXTIDX_             NUMBER(10),
   SUPERTASK_           NUMBER(19),
   constraint PK_JBPM4_HIST_TASK primary key (DBID_)
);

comment on table JBPM4_HIST_TASK is
'历史人工任务表';

comment on column JBPM4_HIST_TASK.DBID_ is
'历史人工任务DBID';

comment on column JBPM4_HIST_TASK.DBVERSION_ is
'历史人工任务版本';

comment on column JBPM4_HIST_TASK.EXECUTION_ is
'历史人工任务所属流程实例';

comment on column JBPM4_HIST_TASK.OUTCOME_ is
'历史人工任务选择下个任务流向';

comment on column JBPM4_HIST_TASK.ASSIGNEE_ is
'历史人工任务执行人';

comment on column JBPM4_HIST_TASK.PRIORITY_ is
'历史人工任务优先级';

comment on column JBPM4_HIST_TASK.STATE_ is
'历史人工任务状态';

comment on column JBPM4_HIST_TASK.CREATE_ is
'历史人工任务开始时间';

comment on column JBPM4_HIST_TASK.END_ is
'历史人工任务结束时间';

comment on column JBPM4_HIST_TASK.DURATION_ is
'历史人工任务持续时间';

comment on column JBPM4_HIST_TASK.NEXTIDX_ is
'历史人工任务下个下标';

comment on column JBPM4_HIST_TASK.SUPERTASK_ is
'历史人工任务父亲任务';

/*==============================================================*/
/* Table: JBPM4_HIST_VAR                                        */
/*==============================================================*/
create table JBPM4_HIST_VAR  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           NUMBER(10)                      not null,
   PROCINSTID_          VARCHAR2(255),
   EXECUTIONID_         VARCHAR2(255),
   VARNAME_             VARCHAR2(255),
   VALUE_               VARCHAR2(255),
   HPROCI_              NUMBER(19),
   HTASK_               NUMBER(19),
   constraint PK_JBPM4_HIST_VAR primary key (DBID_)
);

comment on table JBPM4_HIST_VAR is
'历史变量表';

comment on column JBPM4_HIST_VAR.DBID_ is
'历史变量DBID';

comment on column JBPM4_HIST_VAR.DBVERSION_ is
'历史变量版本';

comment on column JBPM4_HIST_VAR.PROCINSTID_ is
'主线流程ID';

comment on column JBPM4_HIST_VAR.EXECUTIONID_ is
'历史变量所属流程实例ID';

comment on column JBPM4_HIST_VAR.VARNAME_ is
'历史变量名称';

comment on column JBPM4_HIST_VAR.VALUE_ is
'历史变量值';

comment on column JBPM4_HIST_VAR.HPROCI_ is
'历史变量所属历史流程实例';

comment on column JBPM4_HIST_VAR.HTASK_ is
'历史变量所属历史任务';

/*==============================================================*/
/* Table: JBPM4_ID_GROUP                                        */
/*==============================================================*/
create table JBPM4_ID_GROUP  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           NUMBER(10)                      not null,
   ID_                  VARCHAR2(255),
   NAME_                VARCHAR2(255),
   TYPE_                VARCHAR2(255),
   PARENT_              NUMBER(19),
   constraint PK_JBPM4_ID_GROUP primary key (DBID_)
);

comment on table JBPM4_ID_GROUP is
'流程分组';

comment on column JBPM4_ID_GROUP.DBID_ is
'流程分组DBID';

comment on column JBPM4_ID_GROUP.DBVERSION_ is
'流程分组版本';

comment on column JBPM4_ID_GROUP.ID_ is
'流程分组ID';

comment on column JBPM4_ID_GROUP.NAME_ is
'流程分组名称';

comment on column JBPM4_ID_GROUP.TYPE_ is
'流程分组类型';

comment on column JBPM4_ID_GROUP.PARENT_ is
'流程分组父分组';

/*==============================================================*/
/* Table: JBPM4_ID_MEMBERSHIP                                   */
/*==============================================================*/
create table JBPM4_ID_MEMBERSHIP  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           NUMBER(10)                      not null,
   USER_                NUMBER(19),
   GROUP_               NUMBER(19),
   NAME_                VARCHAR2(255),
   constraint PK_JBPM4_ID_MEMBERSHIP primary key (DBID_)
);

comment on table JBPM4_ID_MEMBERSHIP is
'流程用户与分组中间表';

comment on column JBPM4_ID_MEMBERSHIP.DBID_ is
'流程分组与用户中间表DBID';

comment on column JBPM4_ID_MEMBERSHIP.DBVERSION_ is
'流程分组与用户中间表版本';

comment on column JBPM4_ID_MEMBERSHIP.USER_ is
'流程分组与用户中间表绑定用户';

comment on column JBPM4_ID_MEMBERSHIP.GROUP_ is
'流程分组与用户中间表绑定分组';

comment on column JBPM4_ID_MEMBERSHIP.NAME_ is
'流程分组与用户中间表名称';

/*==============================================================*/
/* Table: JBPM4_ID_USER                                         */
/*==============================================================*/
create table JBPM4_ID_USER  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           NUMBER(10)                      not null,
   ID_                  VARCHAR2(255),
   PASSWORD_            VARCHAR2(255),
   GIVENNAME_           VARCHAR2(255),
   FAMILYNAME_          VARCHAR2(255),
   BUSINESSEMAIL_       VARCHAR2(255),
   constraint PK_JBPM4_ID_USER primary key (DBID_)
);

comment on table JBPM4_ID_USER is
'流程用户信息表
';

comment on column JBPM4_ID_USER.DBID_ is
'流程用户DBID';

comment on column JBPM4_ID_USER.DBVERSION_ is
'流程用户版本';

comment on column JBPM4_ID_USER.ID_ is
'流程用户ID';

comment on column JBPM4_ID_USER.PASSWORD_ is
'流程用户密码';

comment on column JBPM4_ID_USER.GIVENNAME_ is
'流程用户名称';

comment on column JBPM4_ID_USER.FAMILYNAME_ is
'流程用户家族名称';

comment on column JBPM4_ID_USER.BUSINESSEMAIL_ is
'流程用户工作邮箱';

/*==============================================================*/
/* Table: JBPM4_JOB                                             */
/*==============================================================*/
create table JBPM4_JOB  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               VARCHAR2(255)                   not null,
   DBVERSION_           NUMBER(10)                      not null,
   DUEDATE_             TIMESTAMP(6),
   STATE_               VARCHAR2(255),
   ISEXCLUSIVE_         NUMBER(1),
   LOCKOWNER_           VARCHAR2(255),
   LOCKEXPTIME_         TIMESTAMP(6),
   EXCEPTION_           CLOB,
   RETRIES_             NUMBER(10),
   PROCESSINSTANCE_     NUMBER(19),
   EXECUTION_           NUMBER(19),
   CFG_                 NUMBER(19),
   SIGNAL_              VARCHAR2(255),
   EVENT_               VARCHAR2(255),
   REPEAT_              VARCHAR2(255),
   constraint PK_JBPM4_JOB primary key (DBID_)
);

comment on table JBPM4_JOB is
'流程作业';

comment on column JBPM4_JOB.DBID_ is
'流程作业DBID';

comment on column JBPM4_JOB.DBVERSION_ is
'流程作业本本';

comment on column JBPM4_JOB.DUEDATE_ is
'流程作业持续时间';

comment on column JBPM4_JOB.STATE_ is
'作业状态';

comment on column JBPM4_JOB.ISEXCLUSIVE_ is
'流程作业是否可并行执行';

comment on column JBPM4_JOB.LOCKOWNER_ is
'锁定人';

comment on column JBPM4_JOB.LOCKEXPTIME_ is
'锁定到期时间';

comment on column JBPM4_JOB.EXCEPTION_ is
'流程作业异常信息';

comment on column JBPM4_JOB.RETRIES_ is
'重试次数';

comment on column JBPM4_JOB.PROCESSINSTANCE_ is
'所属主线流程实例';

comment on column JBPM4_JOB.EXECUTION_ is
'流程作业所属流程实例';

comment on column JBPM4_JOB.CFG_ is
'作业初始化信息';

comment on column JBPM4_JOB.SIGNAL_ is
'作业触发条件';

comment on column JBPM4_JOB.EVENT_ is
'流程作业绑定事件';

comment on column JBPM4_JOB.REPEAT_ is
'重复次数';

/*==============================================================*/
/* Table: JBPM4_LOB                                             */
/*==============================================================*/
create table JBPM4_LOB  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           NUMBER(10)                      not null,
   BLOB_VALUE_          BLOB,
   DEPLOYMENT_          NUMBER(19),
   NAME_                CLOB,
   constraint PK_JBPM4_LOB primary key (DBID_)
);

comment on table JBPM4_LOB is
'流程部署详细 包括了流程定义文件 流程表单 流程图片等信息';

comment on column JBPM4_LOB.DBID_ is
'DBID';

comment on column JBPM4_LOB.DBVERSION_ is
'版本';

comment on column JBPM4_LOB.BLOB_VALUE_ is
'部署对象';

comment on column JBPM4_LOB.DEPLOYMENT_ is
'所属流程部署';

comment on column JBPM4_LOB.NAME_ is
'部署对象名称';

/*==============================================================*/
/* Table: JBPM4_OPINION                                         */
/*==============================================================*/
create table JBPM4_OPINION  (
   ID                   VARCHAR2(38),
   FLOWID               VARCHAR2(200 CHAR)              not null,
   ACTIVITY             VARCHAR2(200 CHAR)              not null,
   HANDLETIME           DATE                            not null,
   HANDLEUSER           VARCHAR2(38)                    not null,
   HANDLESTATUS         VARCHAR2(200 CHAR)              not null,
   LABEL                VARCHAR2(200 CHAR),
   VALUE                CLOB
);

comment on table JBPM4_OPINION is
'流程审批一件汇总表';

comment on column JBPM4_OPINION.FLOWID is
'所属流程实例ID';

comment on column JBPM4_OPINION.ACTIVITY is
'活动名称';

comment on column JBPM4_OPINION.HANDLETIME is
'审批时间';

comment on column JBPM4_OPINION.HANDLEUSER is
'审批人';

comment on column JBPM4_OPINION.HANDLESTATUS is
'审批操作';

comment on column JBPM4_OPINION.LABEL is
'审批意见名称';

comment on column JBPM4_OPINION.VALUE is
'审批意见值';

/*==============================================================*/
/* Table: JBPM4_PARTICIPATION                                   */
/*==============================================================*/
create table JBPM4_PARTICIPATION  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           NUMBER(10)                      not null,
   GROUPID_             VARCHAR2(255),
   USERID_              VARCHAR2(255),
   TYPE_                VARCHAR2(255),
   TASK_                NUMBER(19),
   SWIMLANE_            NUMBER(19),
   constraint PK_JBPM4_PARTICIPATION primary key (DBID_)
);

comment on table JBPM4_PARTICIPATION is
'流程分配中间表';

comment on column JBPM4_PARTICIPATION.DBID_ is
'任务候选人DBID';

comment on column JBPM4_PARTICIPATION.DBVERSION_ is
'任务候选人版本';

comment on column JBPM4_PARTICIPATION.GROUPID_ is
'任务候选人所属分组';

comment on column JBPM4_PARTICIPATION.USERID_ is
'任务候选人所属用户';

comment on column JBPM4_PARTICIPATION.TYPE_ is
'任务候选人类型';

comment on column JBPM4_PARTICIPATION.TASK_ is
'任务候选人所属任务';

comment on column JBPM4_PARTICIPATION.SWIMLANE_ is
'任务候选人所属泳道';

/*==============================================================*/
/* Table: JBPM4_PROPERTY                                        */
/*==============================================================*/
create table JBPM4_PROPERTY  (
   KEY_                 VARCHAR2(255)                   not null,
   VERSION_             NUMBER(10)                      not null,
   VALUE_               VARCHAR2(255),
   constraint PK_JBPM4_PROPERTY primary key (KEY_)
);

comment on table JBPM4_PROPERTY is
'JBPM属性';

comment on column JBPM4_PROPERTY.KEY_ is
'流程属性key';

comment on column JBPM4_PROPERTY.VERSION_ is
'流程属性版本';

comment on column JBPM4_PROPERTY.VALUE_ is
'流程属性值';

/*==============================================================*/
/* Table: JBPM4_RDM_COURSE                                      */
/*==============================================================*/
create table JBPM4_RDM_COURSE  (
   ID                   NUMBER(38)                      not null,
   MAIN_PIID            VARCHAR2(38)                    not null,
   PROJECT_ID           VARCHAR2(38)                    not null,
   FLOW_TASK_ID         VARCHAR2(38)                    not null,
   PRJ_TASK_ID          VARCHAR2(38)                    not null,
   OUTCOME              VARCHAR2(38)                    not null,
   constraint PK_JBPM4_EDM_COURSE primary key (ID)
);

comment on table JBPM4_RDM_COURSE is
'节点之间可见控制';

comment on column JBPM4_RDM_COURSE.ID is
'ID';

comment on column JBPM4_RDM_COURSE.MAIN_PIID is
'主流程实例ID';

comment on column JBPM4_RDM_COURSE.PROJECT_ID is
'所属项目ID';

comment on column JBPM4_RDM_COURSE.FLOW_TASK_ID is
'流程任务ID';

comment on column JBPM4_RDM_COURSE.PRJ_TASK_ID is
'项目任务ID';

comment on column JBPM4_RDM_COURSE.OUTCOME is
'任务走向';

/*==============================================================*/
/* Table: JBPM4_RDM_PARAMMAPPING                                */
/*==============================================================*/
create table JBPM4_RDM_PARAMMAPPING  (
   ID                   VARCHAR2(38),
   PDID                 VARCHAR2(38),
   PROJECTID            VARCHAR2(38),
   SRCTASKID            VARCHAR2(38),
   TARGETTASKID         VARCHAR2(38),
   SRCPARAMID           VARCHAR2(38),
   TARGETPARAMID        VARCHAR2(38)
);

comment on table JBPM4_RDM_PARAMMAPPING is
'流程数据映射关系表';

comment on column JBPM4_RDM_PARAMMAPPING.ID is
'ID';

comment on column JBPM4_RDM_PARAMMAPPING.PDID is
'流程定义ID';

comment on column JBPM4_RDM_PARAMMAPPING.PROJECTID is
'所属项目ID';

comment on column JBPM4_RDM_PARAMMAPPING.SRCTASKID is
'源任务ID';

comment on column JBPM4_RDM_PARAMMAPPING.TARGETTASKID is
'目的地任务ID';

comment on column JBPM4_RDM_PARAMMAPPING.SRCPARAMID is
'源参数ID';

comment on column JBPM4_RDM_PARAMMAPPING.TARGETPARAMID is
'目的地参数ID';

/*==============================================================*/
/* Table: JBPM4_RDM_TASK_VAR                                    */
/*==============================================================*/
create table JBPM4_RDM_TASK_VAR  (
   ID                   VARCHAR2(38)                    not null,
   VALUE                BLOB,
   constraint PK_JBPM4_EDM_TASK_VAR primary key (ID)
);

comment on table JBPM4_RDM_TASK_VAR is
'序列化流程任务实例描述';

comment on column JBPM4_RDM_TASK_VAR.ID is
'ID';

comment on column JBPM4_RDM_TASK_VAR.VALUE is
'序列化流程变量';

/*==============================================================*/
/* Table: JBPM4_RDM_VARIABLE                                    */
/*==============================================================*/
create table JBPM4_RDM_VARIABLE  (
   ID                   VARCHAR2(38)                    not null,
   PROCESSNAME          VARCHAR2(38),
   PIID                 VARCHAR2(38),
   TASKNAME             VARCHAR2(38),
   TASKID               VARCHAR2(38),
   VARIABLETYPE         VARCHAR2(38),
   KEY                  VARCHAR2(38),
   VALUE                VARCHAR2(500),
   TASKBELONGED         VARCHAR2(800),
   constraint PK_JBPM4_EDM_VARIABLE primary key (ID)
);

comment on table JBPM4_RDM_VARIABLE is
'自定义流程变量表';

comment on column JBPM4_RDM_VARIABLE.ID is
'ID';

comment on column JBPM4_RDM_VARIABLE.PROCESSNAME is
'流程名称';

comment on column JBPM4_RDM_VARIABLE.PIID is
'流程实例ID';

comment on column JBPM4_RDM_VARIABLE.TASKNAME is
'任务名称';

comment on column JBPM4_RDM_VARIABLE.TASKID is
'所属流程任务';

comment on column JBPM4_RDM_VARIABLE.VARIABLETYPE is
'参数类型';

comment on column JBPM4_RDM_VARIABLE.KEY is
'参数key';

comment on column JBPM4_RDM_VARIABLE.VALUE is
'参数值';

comment on column JBPM4_RDM_VARIABLE.TASKBELONGED is
'所属自定义任务';

/*==============================================================*/
/* Table: JBPM4_SWIMLANE                                        */
/*==============================================================*/
create table JBPM4_SWIMLANE  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           NUMBER(10)                      not null,
   NAME_                VARCHAR2(255),
   ASSIGNEE_            VARCHAR2(255),
   EXECUTION_           NUMBER(19),
   constraint PK_JBPM4_SWIMLANE primary key (DBID_)
);

comment on table JBPM4_SWIMLANE is
'流程泳道';

comment on column JBPM4_SWIMLANE.DBID_ is
'泳道DBID';

comment on column JBPM4_SWIMLANE.DBVERSION_ is
'泳道版本';

comment on column JBPM4_SWIMLANE.NAME_ is
'泳道所属名称';

comment on column JBPM4_SWIMLANE.ASSIGNEE_ is
'泳道执行人';

comment on column JBPM4_SWIMLANE.EXECUTION_ is
'泳道所属流程实例';

/*==============================================================*/
/* Table: JBPM4_TASK                                            */
/*==============================================================*/
create table JBPM4_TASK  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               CHAR(1)                         not null,
   DBVERSION_           NUMBER(10)                      not null,
   NAME_                VARCHAR2(255),
   DESCR_               CLOB,
   STATE_               VARCHAR2(255),
   SUSPHISTSTATE_       VARCHAR2(255),
   ASSIGNEE_            VARCHAR2(255),
   FORM_                VARCHAR2(255),
   PRIORITY_            NUMBER(10),
   CREATE_              TIMESTAMP(6),
   DUEDATE_             TIMESTAMP(6),
   PROGRESS_            NUMBER(10),
   SIGNALLING_          NUMBER(1),
   EXECUTION_ID_        VARCHAR2(255),
   ACTIVITY_NAME_       VARCHAR2(255),
   HASVARS_             NUMBER(1),
   SUPERTASK_           NUMBER(19),
   EXECUTION_           NUMBER(19),
   PROCINST_            NUMBER(19),
   SWIMLANE_            NUMBER(19),
   TASKDEFNAME_         VARCHAR2(255),
   constraint PK_JBPM4_TASK primary key (DBID_)
);

comment on table JBPM4_TASK is
'执行中人工任务表';

comment on column JBPM4_TASK.DBID_ is
'人工任务DBID';

comment on column JBPM4_TASK.DBVERSION_ is
'人工任务版本';

comment on column JBPM4_TASK.NAME_ is
'人工任务名称';

comment on column JBPM4_TASK.DESCR_ is
'人工任务描述';

comment on column JBPM4_TASK.STATE_ is
'任务状态';

comment on column JBPM4_TASK.SUSPHISTSTATE_ is
'暂停/恢复历史状态';

comment on column JBPM4_TASK.ASSIGNEE_ is
'人工任务执行人';

comment on column JBPM4_TASK.FORM_ is
'人工任务绑定表单';

comment on column JBPM4_TASK.PRIORITY_ is
'人工任务优先级';

comment on column JBPM4_TASK.CREATE_ is
'人工任务创建时间';

comment on column JBPM4_TASK.DUEDATE_ is
'人工任务持续时间';

comment on column JBPM4_TASK.PROGRESS_ is
'人工任务完成百分比';

comment on column JBPM4_TASK.SIGNALLING_ is
'是否等待完成中';

comment on column JBPM4_TASK.EXECUTION_ID_ is
'人工任务所属流程实例ID';

comment on column JBPM4_TASK.ACTIVITY_NAME_ is
'人工任务名称';

comment on column JBPM4_TASK.HASVARS_ is
'人工任务是否包含流程变量';

comment on column JBPM4_TASK.SUPERTASK_ is
'父任务';

comment on column JBPM4_TASK.EXECUTION_ is
'人工任务所属流程实例DBID';

comment on column JBPM4_TASK.PROCINST_ is
'人工任务所属主线流程实例';

comment on column JBPM4_TASK.SWIMLANE_ is
'泳道';

comment on column JBPM4_TASK.TASKDEFNAME_ is
'任务定义名称';

/*==============================================================*/
/* Table: JBPM4_VARIABLE                                        */
/*==============================================================*/
create table JBPM4_VARIABLE  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               VARCHAR2(255)                   not null,
   DBVERSION_           NUMBER(10)                      not null,
   KEY_                 VARCHAR2(255),
   CONVERTER_           VARCHAR2(255),
   HIST_                NUMBER(1),
   EXECUTION_           NUMBER(19),
   TASK_                NUMBER(19),
   LOB_                 NUMBER(19),
   DATE_VALUE_          TIMESTAMP(6),
   DOUBLE_VALUE_        FLOAT,
   CLASSNAME_           VARCHAR2(255),
   LONG_VALUE_          NUMBER(19),
   STRING_VALUE_        VARCHAR2(255),
   TEXT_VALUE_          CLOB,
   EXESYS_              NUMBER(19),
   constraint PK_JBPM4_VARIABLE primary key (DBID_)
);

comment on table JBPM4_VARIABLE is
'执行中流程变量表';

comment on column JBPM4_VARIABLE.DBID_ is
'参数DBID';

comment on column JBPM4_VARIABLE.DBVERSION_ is
'参数版本';

comment on column JBPM4_VARIABLE.KEY_ is
'参数KEY';

comment on column JBPM4_VARIABLE.CONVERTER_ is
'转化器';

comment on column JBPM4_VARIABLE.EXECUTION_ is
'所属流程实例ID';

comment on column JBPM4_VARIABLE.TASK_ is
'所属人工任务';

comment on column JBPM4_VARIABLE.LOB_ is
'lob类型参数值';

comment on column JBPM4_VARIABLE.DATE_VALUE_ is
'日期类型参数值';

comment on column JBPM4_VARIABLE.DOUBLE_VALUE_ is
'DOUBLE类型参数值';

comment on column JBPM4_VARIABLE.LONG_VALUE_ is
'LONG类型参数值';

comment on column JBPM4_VARIABLE.STRING_VALUE_ is
'STRING类型参数值';

comment on column JBPM4_VARIABLE.TEXT_VALUE_ is
'TEXT类型参数值';

alter table JBPM4_DEPLOYPROP
   add constraint FK_DEPLPROP_DEPL foreign key (DEPLOYMENT_)
      references JBPM4_DEPLOYMENT (DBID_) disable;

alter table JBPM4_EXECUTION
   add constraint FK_EXEC_INSTANCE foreign key (INSTANCE_)
      references JBPM4_EXECUTION (DBID_) disable;

alter table JBPM4_EXECUTION
   add constraint FK_EXEC_PARENT foreign key (PARENT_)
      references JBPM4_EXECUTION (DBID_) disable;

alter table JBPM4_EXECUTION
   add constraint FK_EXEC_SUBPI foreign key (SUBPROCINST_)
      references JBPM4_EXECUTION (DBID_) disable;

alter table JBPM4_EXECUTION
   add constraint FK_EXEC_SUPEREXEC foreign key (SUPEREXEC_)
      references JBPM4_EXECUTION (DBID_) disable;

alter table JBPM4_HIST_ACTINST
   add constraint FK_HACTI_HPROCI foreign key (HPROCI_)
      references JBPM4_HIST_PROCINST (DBID_) disable;

alter table JBPM4_HIST_ACTINST
   add constraint FK_HTI_HTASK foreign key (HTASK_)
      references JBPM4_HIST_TASK (DBID_) disable;

alter table JBPM4_HIST_DETAIL
   add constraint FK_HDETAIL_HACTI foreign key (HACTI_)
      references JBPM4_HIST_ACTINST (DBID_) disable;

alter table JBPM4_HIST_DETAIL
   add constraint FK_HDETAIL_HPROCI foreign key (HPROCI_)
      references JBPM4_HIST_PROCINST (DBID_) disable;

alter table JBPM4_HIST_DETAIL
   add constraint FK_HDETAIL_HTASK foreign key (HTASK_)
      references JBPM4_HIST_TASK (DBID_) disable;

alter table JBPM4_HIST_DETAIL
   add constraint FK_HDETAIL_HVAR foreign key (HVAR_)
      references JBPM4_HIST_VAR (DBID_) disable;

alter table JBPM4_HIST_TASK
   add constraint FK_HSUPERT_SUB foreign key (SUPERTASK_)
      references JBPM4_HIST_TASK (DBID_) disable;

alter table JBPM4_HIST_VAR
   add constraint FK_HVAR_HPROCI foreign key (HPROCI_)
      references JBPM4_HIST_PROCINST (DBID_) disable;

alter table JBPM4_HIST_VAR
   add constraint FK_HVAR_HTASK foreign key (HTASK_)
      references JBPM4_HIST_TASK (DBID_) disable;

alter table JBPM4_ID_GROUP
   add constraint FK_GROUP_PARENT foreign key (PARENT_)
      references JBPM4_ID_GROUP (DBID_) disable;

alter table JBPM4_ID_MEMBERSHIP
   add constraint FK_MEM_GROUP foreign key (GROUP_)
      references JBPM4_ID_GROUP (DBID_) disable;

alter table JBPM4_ID_MEMBERSHIP
   add constraint FK_MEM_USER foreign key (USER_)
      references JBPM4_ID_USER (DBID_) disable;

alter table JBPM4_LOB
   add constraint FK_LOB_DEPLOYMENT foreign key (DEPLOYMENT_)
      references JBPM4_DEPLOYMENT (DBID_) disable;

alter table JBPM4_PARTICIPATION
   add constraint FK_PART_SWIMLANE foreign key (SWIMLANE_)
      references JBPM4_SWIMLANE (DBID_) disable;

alter table JBPM4_PARTICIPATION
   add constraint FK_PART_TASK foreign key (TASK_)
      references JBPM4_TASK (DBID_) disable;

alter table JBPM4_SWIMLANE
   add constraint FK_SWIMLANE_EXEC foreign key (EXECUTION_)
      references JBPM4_EXECUTION (DBID_) disable;

alter table JBPM4_TASK
   add constraint FK_TASK_SUPERTASK foreign key (SUPERTASK_)
      references JBPM4_TASK (DBID_) disable;

alter table JBPM4_TASK
   add constraint FK_TASK_SWIML foreign key (SWIMLANE_)
      references JBPM4_SWIMLANE (DBID_) disable;

alter table JBPM4_VARIABLE
   add constraint FK_VAR_EXECUTION foreign key (EXECUTION_)
      references JBPM4_EXECUTION (DBID_) disable;

alter table JBPM4_VARIABLE
   add constraint FK_VAR_EXESYS foreign key (EXESYS_)
      references JBPM4_EXECUTION (DBID_) disable;

alter table JBPM4_VARIABLE
   add constraint FK_VAR_TASK foreign key (TASK_)
      references JBPM4_TASK (DBID_) disable;

