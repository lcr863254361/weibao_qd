/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016/3/10 ������ 11:06:05                       */
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
'ģ������ģ��󶨱�';

comment on column AUDIT_FLOW_DATA_BIND.ID is
'ID';

comment on column AUDIT_FLOW_DATA_BIND.SCHEMA_ID is
'��������schema';

comment on column AUDIT_FLOW_DATA_BIND.TABLE_NAME is
'��������ģ������';

comment on column AUDIT_FLOW_DATA_BIND.DATA_ID is
'�������̰�����ID';

comment on column AUDIT_FLOW_DATA_BIND.PD_ID is
'���������������̶���';

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
'����������ģ�����ݹ�ϵ��';

comment on column AUDIT_FLOW_DATA_RELATION.ID is
'ID';

comment on column AUDIT_FLOW_DATA_RELATION.AUDIT_TYPE is
'������������';

comment on column AUDIT_FLOW_DATA_RELATION.TABLE_NAME is
'��������ģ������';

comment on column AUDIT_FLOW_DATA_RELATION.DATA_ID is
'��������ID';

comment on column AUDIT_FLOW_DATA_RELATION.PI_ID is
'��������ʵ��ID';

comment on column AUDIT_FLOW_DATA_RELATION.CREATE_TIME is
'�������̴���ʱ��';

comment on column AUDIT_FLOW_DATA_RELATION.FILTER is
'�������ݹ���';

comment on column AUDIT_FLOW_DATA_RELATION.LAUNCHER is
'����������';

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
'�����������ݰ�';

comment on column AUDIT_FLOW_MODEL_BIND.ID is
'ID';

comment on column AUDIT_FLOW_MODEL_BIND.SCHEMA_ID is
'��ģ������schema';

comment on column AUDIT_FLOW_MODEL_BIND.MODEL_NAME is
'��ģ������';

comment on column AUDIT_FLOW_MODEL_BIND.FLOW_NAME is
'������������';

comment on column AUDIT_FLOW_MODEL_BIND.FLOW_VERSION is
'�������̰汾';

comment on column AUDIT_FLOW_MODEL_BIND.AUDIT_FORM is
'�������̱�';

comment on column AUDIT_FLOW_MODEL_BIND.USER_NAME is
'��ģ����';

comment on column AUDIT_FLOW_MODEL_BIND.LAST_UPDATE_DATE is
'�ϴθ���ʱ��';

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
'�������';

comment on column CWM_COMPONENT.ID is
'������ݴ��ݱ��Ψһ����';

comment on column CWM_COMPONENT.COMPONENTNAME is
'�������';

comment on column CWM_COMPONENT.CLASSNAME is
'���ȫ��';

comment on column CWM_COMPONENT.REMARK is
'����';

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
'�����齨����Ŀ��ϵ��';

comment on column CWM_COMPONENT_RELATIONDATA.ID is
'������ݴ��ݱ��Ψһ����';

comment on column CWM_COMPONENT_RELATIONDATA.PRJCODE is
'��Ŀ���';

comment on column CWM_COMPONENT_RELATIONDATA.PARAMKEY is
'�����ؼ���';

comment on column CWM_COMPONENT_RELATIONDATA.PARAMVALUE is
'����ֵ';

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
'��Ŀ��ģ�ͱ�';

comment on column CWM_PROJECT_MODEL_RELATION.ID is
'ģ����������Ŀ��ϵ����';

comment on column CWM_PROJECT_MODEL_RELATION.MODELNAME is
'��ģ������';

comment on column CWM_PROJECT_MODEL_RELATION.SCHEMAID is
'��ģ�͵�schemaid';

comment on column CWM_PROJECT_MODEL_RELATION.DATAID is
'��ģ������id';

comment on column CWM_PROJECT_MODEL_RELATION.PROJECTID is
'��Ŀid';

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
'���̴�����Ա��Ϣ��';

comment on column JBPM4_CONFIG_USER.USERID_ is
'���������Ա';

comment on column JBPM4_CONFIG_USER.CONFIG_USERID_ is
'ָ���Ĵ�����Ա';

comment on column JBPM4_CONFIG_USER.TYPE_ is
'���ͣ�Ԥ��';

comment on column JBPM4_CONFIG_USER.EXECUTIONID_ is
'ָ������ID��Ԥ��';

comment on column JBPM4_CONFIG_USER.REMARK_ is
'��ע';

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
'��ǩ������ϸ��';

comment on column JBPM4_COUNTERSIGN_INFO.EXECUTION_ID is
'����ID';

comment on column JBPM4_COUNTERSIGN_INFO.TASKNAME is
'�ڵ���������';

comment on column JBPM4_COUNTERSIGN_INFO.USERCOUNT is
'�û�ǩ�ڵ���Ҫ���������';

comment on column JBPM4_COUNTERSIGN_INFO.STRATEGY is
'��ǩ����[��WorkFlow Studio���趨�Ĳ���]';

comment on column JBPM4_COUNTERSIGN_INFO.STRATEGY_VALUE is
'��ǩ���Խ��[���ݻ�ǩ�������ͻ�ǩ���Լ�������ոû�ǩ�ڵ���С��ͨ������]';

comment on column JBPM4_COUNTERSIGN_INFO.REMARK is
'��ע';

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
'���̲�����Ϣ��';

comment on column JBPM4_DEPLOYMENT.DBID_ is
'���̲���DBID';

comment on column JBPM4_DEPLOYMENT.NAME_ is
'������';

comment on column JBPM4_DEPLOYMENT.TIMESTAMP_ is
'����ʱ��';

comment on column JBPM4_DEPLOYMENT.STATE_ is
'����״̬';

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
'���̲�������';

comment on column JBPM4_DEPLOYPROP.DBID_ is
'��������DBID';

comment on column JBPM4_DEPLOYPROP.DEPLOYMENT_ is
'�������̲���';

comment on column JBPM4_DEPLOYPROP.OBJNAME_ is
'���Է���';

comment on column JBPM4_DEPLOYPROP.KEY_ is
'��������key';

comment on column JBPM4_DEPLOYPROP.STRINGVAL_ is
'����ΪString���͵�ֵ';

comment on column JBPM4_DEPLOYPROP.LONGVAL_ is
'����ΪLong���͵�ֵ';

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
'ִ���е�����';

comment on column JBPM4_EXECUTION.DBID_ is
'����ʵ��DBID';

comment on column JBPM4_EXECUTION.DBVERSION_ is
'����ʵ���汾';

comment on column JBPM4_EXECUTION.ACTIVITYNAME_ is
'��ǰ����ʵ���л�ڵ�����';

comment on column JBPM4_EXECUTION.PROCDEFID_ is
'�������̶���ID';

comment on column JBPM4_EXECUTION.HASVARS_ is
'�Ƿ�������̱���';

comment on column JBPM4_EXECUTION.NAME_ is
'�Զ�������ʵ������';

comment on column JBPM4_EXECUTION.KEY_ is
'�Զ�������ʵ��KEY';

comment on column JBPM4_EXECUTION.ID_ is
'����ʵ��ID';

comment on column JBPM4_EXECUTION.STATE_ is
'����ʵ��״̬';

comment on column JBPM4_EXECUTION.SUSPHISTSTATE_ is
'��ͣ/�ָ�������ʷ״̬';

comment on column JBPM4_EXECUTION.PRIORITY_ is
'���ȼ�';

comment on column JBPM4_EXECUTION.HISACTINST_ is
'������ʷ����ʵ��';

comment on column JBPM4_EXECUTION.PARENT_ is
'��������ʵ��id�����ܴ��ڶ�㼶����ʵ����ϵ��';

comment on column JBPM4_EXECUTION.INSTANCE_ is
'������������ʵ��ID';

comment on column JBPM4_EXECUTION.SUPEREXEC_ is
'������ʵ��ID���������̹�ϵ��';

comment on column JBPM4_EXECUTION.SUBPROCINST_ is
'������ʵ��ID���������̹�ϵ��';

comment on column JBPM4_EXECUTION.PARENT_IDX_ is
'��������ʵ���±�';

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
'��֧��������ʵ����ϵ';

comment on column JBPM4_FLOW_BRANCH_RELATION.ID is
'����ID';

comment on column JBPM4_FLOW_BRANCH_RELATION.SUP_DBID_ is
'�����̵Ĳ���ID';

comment on column JBPM4_FLOW_BRANCH_RELATION.SUP_EXC_ID_ is
'�����̵�EXECUTION��ID';

comment on column JBPM4_FLOW_BRANCH_RELATION.SUB_DBID_ is
'�����̵Ĳ���ID';

comment on column JBPM4_FLOW_BRANCH_RELATION.SUB_EXC_ID_ is
'�����̵�EXECUTION��ID';

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
'��������ʵ����ϵ��';

comment on column JBPM4_FLOW_RELATION.ID is
'����ID';

comment on column JBPM4_FLOW_RELATION.SUP_DBID_ is
'�����̵Ĳ���ID';

comment on column JBPM4_FLOW_RELATION.SUP_EXC_ID_ is
'�����̵�EXECUTION��ID';

comment on column JBPM4_FLOW_RELATION.SUB_DBID_ is
'�����̵Ĳ���ID';

comment on column JBPM4_FLOW_RELATION.SUB_EXC_ID_ is
'�����̵�EXECUTION��ID';

comment on column JBPM4_FLOW_RELATION.TASKID is
'��';

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
'��ʷ��� �������˹�����';

comment on column JBPM4_HIST_ACTINST.DBID_ is
'��ʷ�DBID';

comment on column JBPM4_HIST_ACTINST.DBVERSION_ is
'��ʷ��汾';

comment on column JBPM4_HIST_ACTINST.HPROCI_ is
'������������ʵ��ID';

comment on column JBPM4_HIST_ACTINST.TYPE_ is
'�����';

comment on column JBPM4_HIST_ACTINST.EXECUTION_ is
'��������ʵ��ID';

comment on column JBPM4_HIST_ACTINST.ACTIVITY_NAME_ is
'��ʷ�����';

comment on column JBPM4_HIST_ACTINST.START_ is
'��ʼʱ��';

comment on column JBPM4_HIST_ACTINST.END_ is
'����ʱ��';

comment on column JBPM4_HIST_ACTINST.DURATION_ is
'����ʱ��';

comment on column JBPM4_HIST_ACTINST.TRANSITION_ is
'��ɻʱѡ����¸���������';

comment on column JBPM4_HIST_ACTINST.NEXTIDX_ is
'�¸��±�';

comment on column JBPM4_HIST_ACTINST.HTASK_ is
'������ʷ�˹�����';

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
'��ʷ��ϸ����Ҫ��������ʵ���ϲ�';

comment on column JBPM4_HIST_DETAIL.DBID_ is
'��ʷ��ϸDBID';

comment on column JBPM4_HIST_DETAIL.DBVERSION_ is
'��ʷ��ϸ�汾';

comment on column JBPM4_HIST_DETAIL.USERID_ is
'�����';

comment on column JBPM4_HIST_DETAIL.TIME_ is
'���ʱ��';

comment on column JBPM4_HIST_DETAIL.HPROCI_ is
'�󶨵���ʷ����ʵ��';

comment on column JBPM4_HIST_DETAIL.HPROCIIDX_ is
'�󶨵���ʷ����ʵ���±�';

comment on column JBPM4_HIST_DETAIL.HACTI_ is
'�󶨵���ʷ�';

comment on column JBPM4_HIST_DETAIL.HACTIIDX_ is
'����ʷ��±�';

comment on column JBPM4_HIST_DETAIL.HTASK_ is
'�󶨵���ʷ�˹�����';

comment on column JBPM4_HIST_DETAIL.HTASKIDX_ is
'�󶨵���ʷ�˹������±�';

comment on column JBPM4_HIST_DETAIL.HVAR_ is
'�󶨵���ʷ����';

comment on column JBPM4_HIST_DETAIL.HVARIDX_ is
'�󶨵���ʷ�����±�';

comment on column JBPM4_HIST_DETAIL.MESSAGE_ is
'��ע';

comment on column JBPM4_HIST_DETAIL.OLD_STR_ is
'�ɵ�String����ֵ';

comment on column JBPM4_HIST_DETAIL.NEW_STR_ is
'�µ�String����ֵ';

comment on column JBPM4_HIST_DETAIL.OLD_INT_ is
'�ɵ�Int����ֵ';

comment on column JBPM4_HIST_DETAIL.NEW_INT_ is
'�µ�Int����ֵ';

comment on column JBPM4_HIST_DETAIL.OLD_TIME_ is
'�ɵ�ʱ��';

comment on column JBPM4_HIST_DETAIL.NEW_TIME_ is
'���ʱ��';

comment on column JBPM4_HIST_DETAIL.PARENT_ is
'�����';

comment on column JBPM4_HIST_DETAIL.PARENT_IDX_ is
'������±�';

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
'��ʷ����ʵ����';

comment on column JBPM4_HIST_PROCINST.DBID_ is
'��ʷ����ʵ��DBID';

comment on column JBPM4_HIST_PROCINST.DBVERSION_ is
'��ʷ����ʵ���汾';

comment on column JBPM4_HIST_PROCINST.ID_ is
'��ʷ����ʵ��ID';

comment on column JBPM4_HIST_PROCINST.PROCDEFID_ is
'�������̶���ID';

comment on column JBPM4_HIST_PROCINST.KEY_ is
'��ʷ����key';

comment on column JBPM4_HIST_PROCINST.START_ is
'��ʼʱ��';

comment on column JBPM4_HIST_PROCINST.END_ is
'��ʷ���̽���ʱ��';

comment on column JBPM4_HIST_PROCINST.DURATION_ is
'��ʷ���̳���ʱ��';

comment on column JBPM4_HIST_PROCINST.STATE_ is
'��ʷ����ʵ��״̬';

comment on column JBPM4_HIST_PROCINST.ENDACTIVITY_ is
'��ʷ���̽����';

comment on column JBPM4_HIST_PROCINST.NEXTIDX_ is
'�¸��±�';

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
'��ʷ�˹������';

comment on column JBPM4_HIST_TASK.DBID_ is
'��ʷ�˹�����DBID';

comment on column JBPM4_HIST_TASK.DBVERSION_ is
'��ʷ�˹�����汾';

comment on column JBPM4_HIST_TASK.EXECUTION_ is
'��ʷ�˹�������������ʵ��';

comment on column JBPM4_HIST_TASK.OUTCOME_ is
'��ʷ�˹�����ѡ���¸���������';

comment on column JBPM4_HIST_TASK.ASSIGNEE_ is
'��ʷ�˹�����ִ����';

comment on column JBPM4_HIST_TASK.PRIORITY_ is
'��ʷ�˹��������ȼ�';

comment on column JBPM4_HIST_TASK.STATE_ is
'��ʷ�˹�����״̬';

comment on column JBPM4_HIST_TASK.CREATE_ is
'��ʷ�˹�����ʼʱ��';

comment on column JBPM4_HIST_TASK.END_ is
'��ʷ�˹��������ʱ��';

comment on column JBPM4_HIST_TASK.DURATION_ is
'��ʷ�˹��������ʱ��';

comment on column JBPM4_HIST_TASK.NEXTIDX_ is
'��ʷ�˹������¸��±�';

comment on column JBPM4_HIST_TASK.SUPERTASK_ is
'��ʷ�˹�����������';

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
'��ʷ������';

comment on column JBPM4_HIST_VAR.DBID_ is
'��ʷ����DBID';

comment on column JBPM4_HIST_VAR.DBVERSION_ is
'��ʷ�����汾';

comment on column JBPM4_HIST_VAR.PROCINSTID_ is
'��������ID';

comment on column JBPM4_HIST_VAR.EXECUTIONID_ is
'��ʷ������������ʵ��ID';

comment on column JBPM4_HIST_VAR.VARNAME_ is
'��ʷ��������';

comment on column JBPM4_HIST_VAR.VALUE_ is
'��ʷ����ֵ';

comment on column JBPM4_HIST_VAR.HPROCI_ is
'��ʷ����������ʷ����ʵ��';

comment on column JBPM4_HIST_VAR.HTASK_ is
'��ʷ����������ʷ����';

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
'���̷���';

comment on column JBPM4_ID_GROUP.DBID_ is
'���̷���DBID';

comment on column JBPM4_ID_GROUP.DBVERSION_ is
'���̷���汾';

comment on column JBPM4_ID_GROUP.ID_ is
'���̷���ID';

comment on column JBPM4_ID_GROUP.NAME_ is
'���̷�������';

comment on column JBPM4_ID_GROUP.TYPE_ is
'���̷�������';

comment on column JBPM4_ID_GROUP.PARENT_ is
'���̷��鸸����';

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
'�����û�������м��';

comment on column JBPM4_ID_MEMBERSHIP.DBID_ is
'���̷������û��м��DBID';

comment on column JBPM4_ID_MEMBERSHIP.DBVERSION_ is
'���̷������û��м��汾';

comment on column JBPM4_ID_MEMBERSHIP.USER_ is
'���̷������û��м����û�';

comment on column JBPM4_ID_MEMBERSHIP.GROUP_ is
'���̷������û��м��󶨷���';

comment on column JBPM4_ID_MEMBERSHIP.NAME_ is
'���̷������û��м������';

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
'�����û���Ϣ��
';

comment on column JBPM4_ID_USER.DBID_ is
'�����û�DBID';

comment on column JBPM4_ID_USER.DBVERSION_ is
'�����û��汾';

comment on column JBPM4_ID_USER.ID_ is
'�����û�ID';

comment on column JBPM4_ID_USER.PASSWORD_ is
'�����û�����';

comment on column JBPM4_ID_USER.GIVENNAME_ is
'�����û�����';

comment on column JBPM4_ID_USER.FAMILYNAME_ is
'�����û���������';

comment on column JBPM4_ID_USER.BUSINESSEMAIL_ is
'�����û���������';

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
'������ҵ';

comment on column JBPM4_JOB.DBID_ is
'������ҵDBID';

comment on column JBPM4_JOB.DBVERSION_ is
'������ҵ����';

comment on column JBPM4_JOB.DUEDATE_ is
'������ҵ����ʱ��';

comment on column JBPM4_JOB.STATE_ is
'��ҵ״̬';

comment on column JBPM4_JOB.ISEXCLUSIVE_ is
'������ҵ�Ƿ�ɲ���ִ��';

comment on column JBPM4_JOB.LOCKOWNER_ is
'������';

comment on column JBPM4_JOB.LOCKEXPTIME_ is
'��������ʱ��';

comment on column JBPM4_JOB.EXCEPTION_ is
'������ҵ�쳣��Ϣ';

comment on column JBPM4_JOB.RETRIES_ is
'���Դ���';

comment on column JBPM4_JOB.PROCESSINSTANCE_ is
'������������ʵ��';

comment on column JBPM4_JOB.EXECUTION_ is
'������ҵ��������ʵ��';

comment on column JBPM4_JOB.CFG_ is
'��ҵ��ʼ����Ϣ';

comment on column JBPM4_JOB.SIGNAL_ is
'��ҵ��������';

comment on column JBPM4_JOB.EVENT_ is
'������ҵ���¼�';

comment on column JBPM4_JOB.REPEAT_ is
'�ظ�����';

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
'���̲�����ϸ ���������̶����ļ� ���̱� ����ͼƬ����Ϣ';

comment on column JBPM4_LOB.DBID_ is
'DBID';

comment on column JBPM4_LOB.DBVERSION_ is
'�汾';

comment on column JBPM4_LOB.BLOB_VALUE_ is
'�������';

comment on column JBPM4_LOB.DEPLOYMENT_ is
'�������̲���';

comment on column JBPM4_LOB.NAME_ is
'�����������';

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
'��������һ�����ܱ�';

comment on column JBPM4_OPINION.FLOWID is
'��������ʵ��ID';

comment on column JBPM4_OPINION.ACTIVITY is
'�����';

comment on column JBPM4_OPINION.HANDLETIME is
'����ʱ��';

comment on column JBPM4_OPINION.HANDLEUSER is
'������';

comment on column JBPM4_OPINION.HANDLESTATUS is
'��������';

comment on column JBPM4_OPINION.LABEL is
'�����������';

comment on column JBPM4_OPINION.VALUE is
'�������ֵ';

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
'���̷����м��';

comment on column JBPM4_PARTICIPATION.DBID_ is
'�����ѡ��DBID';

comment on column JBPM4_PARTICIPATION.DBVERSION_ is
'�����ѡ�˰汾';

comment on column JBPM4_PARTICIPATION.GROUPID_ is
'�����ѡ����������';

comment on column JBPM4_PARTICIPATION.USERID_ is
'�����ѡ�������û�';

comment on column JBPM4_PARTICIPATION.TYPE_ is
'�����ѡ������';

comment on column JBPM4_PARTICIPATION.TASK_ is
'�����ѡ����������';

comment on column JBPM4_PARTICIPATION.SWIMLANE_ is
'�����ѡ������Ӿ��';

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
'JBPM����';

comment on column JBPM4_PROPERTY.KEY_ is
'��������key';

comment on column JBPM4_PROPERTY.VERSION_ is
'�������԰汾';

comment on column JBPM4_PROPERTY.VALUE_ is
'��������ֵ';

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
'�ڵ�֮��ɼ�����';

comment on column JBPM4_RDM_COURSE.ID is
'ID';

comment on column JBPM4_RDM_COURSE.MAIN_PIID is
'������ʵ��ID';

comment on column JBPM4_RDM_COURSE.PROJECT_ID is
'������ĿID';

comment on column JBPM4_RDM_COURSE.FLOW_TASK_ID is
'��������ID';

comment on column JBPM4_RDM_COURSE.PRJ_TASK_ID is
'��Ŀ����ID';

comment on column JBPM4_RDM_COURSE.OUTCOME is
'��������';

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
'��������ӳ���ϵ��';

comment on column JBPM4_RDM_PARAMMAPPING.ID is
'ID';

comment on column JBPM4_RDM_PARAMMAPPING.PDID is
'���̶���ID';

comment on column JBPM4_RDM_PARAMMAPPING.PROJECTID is
'������ĿID';

comment on column JBPM4_RDM_PARAMMAPPING.SRCTASKID is
'Դ����ID';

comment on column JBPM4_RDM_PARAMMAPPING.TARGETTASKID is
'Ŀ�ĵ�����ID';

comment on column JBPM4_RDM_PARAMMAPPING.SRCPARAMID is
'Դ����ID';

comment on column JBPM4_RDM_PARAMMAPPING.TARGETPARAMID is
'Ŀ�ĵز���ID';

/*==============================================================*/
/* Table: JBPM4_RDM_TASK_VAR                                    */
/*==============================================================*/
create table JBPM4_RDM_TASK_VAR  (
   ID                   VARCHAR2(38)                    not null,
   VALUE                BLOB,
   constraint PK_JBPM4_EDM_TASK_VAR primary key (ID)
);

comment on table JBPM4_RDM_TASK_VAR is
'���л���������ʵ������';

comment on column JBPM4_RDM_TASK_VAR.ID is
'ID';

comment on column JBPM4_RDM_TASK_VAR.VALUE is
'���л����̱���';

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
'�Զ������̱�����';

comment on column JBPM4_RDM_VARIABLE.ID is
'ID';

comment on column JBPM4_RDM_VARIABLE.PROCESSNAME is
'��������';

comment on column JBPM4_RDM_VARIABLE.PIID is
'����ʵ��ID';

comment on column JBPM4_RDM_VARIABLE.TASKNAME is
'��������';

comment on column JBPM4_RDM_VARIABLE.TASKID is
'������������';

comment on column JBPM4_RDM_VARIABLE.VARIABLETYPE is
'��������';

comment on column JBPM4_RDM_VARIABLE.KEY is
'����key';

comment on column JBPM4_RDM_VARIABLE.VALUE is
'����ֵ';

comment on column JBPM4_RDM_VARIABLE.TASKBELONGED is
'�����Զ�������';

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
'����Ӿ��';

comment on column JBPM4_SWIMLANE.DBID_ is
'Ӿ��DBID';

comment on column JBPM4_SWIMLANE.DBVERSION_ is
'Ӿ���汾';

comment on column JBPM4_SWIMLANE.NAME_ is
'Ӿ����������';

comment on column JBPM4_SWIMLANE.ASSIGNEE_ is
'Ӿ��ִ����';

comment on column JBPM4_SWIMLANE.EXECUTION_ is
'Ӿ����������ʵ��';

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
'ִ�����˹������';

comment on column JBPM4_TASK.DBID_ is
'�˹�����DBID';

comment on column JBPM4_TASK.DBVERSION_ is
'�˹�����汾';

comment on column JBPM4_TASK.NAME_ is
'�˹���������';

comment on column JBPM4_TASK.DESCR_ is
'�˹���������';

comment on column JBPM4_TASK.STATE_ is
'����״̬';

comment on column JBPM4_TASK.SUSPHISTSTATE_ is
'��ͣ/�ָ���ʷ״̬';

comment on column JBPM4_TASK.ASSIGNEE_ is
'�˹�����ִ����';

comment on column JBPM4_TASK.FORM_ is
'�˹�����󶨱�';

comment on column JBPM4_TASK.PRIORITY_ is
'�˹��������ȼ�';

comment on column JBPM4_TASK.CREATE_ is
'�˹����񴴽�ʱ��';

comment on column JBPM4_TASK.DUEDATE_ is
'�˹��������ʱ��';

comment on column JBPM4_TASK.PROGRESS_ is
'�˹�������ɰٷֱ�';

comment on column JBPM4_TASK.SIGNALLING_ is
'�Ƿ�ȴ������';

comment on column JBPM4_TASK.EXECUTION_ID_ is
'�˹�������������ʵ��ID';

comment on column JBPM4_TASK.ACTIVITY_NAME_ is
'�˹���������';

comment on column JBPM4_TASK.HASVARS_ is
'�˹������Ƿ�������̱���';

comment on column JBPM4_TASK.SUPERTASK_ is
'������';

comment on column JBPM4_TASK.EXECUTION_ is
'�˹�������������ʵ��DBID';

comment on column JBPM4_TASK.PROCINST_ is
'�˹�����������������ʵ��';

comment on column JBPM4_TASK.SWIMLANE_ is
'Ӿ��';

comment on column JBPM4_TASK.TASKDEFNAME_ is
'����������';

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
'ִ�������̱�����';

comment on column JBPM4_VARIABLE.DBID_ is
'����DBID';

comment on column JBPM4_VARIABLE.DBVERSION_ is
'�����汾';

comment on column JBPM4_VARIABLE.KEY_ is
'����KEY';

comment on column JBPM4_VARIABLE.CONVERTER_ is
'ת����';

comment on column JBPM4_VARIABLE.EXECUTION_ is
'��������ʵ��ID';

comment on column JBPM4_VARIABLE.TASK_ is
'�����˹�����';

comment on column JBPM4_VARIABLE.LOB_ is
'lob���Ͳ���ֵ';

comment on column JBPM4_VARIABLE.DATE_VALUE_ is
'�������Ͳ���ֵ';

comment on column JBPM4_VARIABLE.DOUBLE_VALUE_ is
'DOUBLE���Ͳ���ֵ';

comment on column JBPM4_VARIABLE.LONG_VALUE_ is
'LONG���Ͳ���ֵ';

comment on column JBPM4_VARIABLE.STRING_VALUE_ is
'STRING���Ͳ���ֵ';

comment on column JBPM4_VARIABLE.TEXT_VALUE_ is
'TEXT���Ͳ���ֵ';

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

