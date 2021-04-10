--*******************************************--
--              工作流相关                    --
--*******************************************--

CREATE TABLE JBPM4_CONFIG_USER
(
    USERID_                        VARCHAR2(20),
    CONFIG_USERID_                 VARCHAR2(20),
    TYPE_                          VARCHAR2(2),
    EXECUTIONID_                   VARCHAR2(255),
    REMARK_                        VARCHAR2(200)
);
COMMENT ON COLUMN JBPM4_CONFIG_USER.USERID_ IS '被代办的人员';
COMMENT ON COLUMN JBPM4_CONFIG_USER.CONFIG_USERID_ IS '指定的代办人员';
COMMENT ON COLUMN JBPM4_CONFIG_USER.TYPE_ IS '类型，预留';
COMMENT ON COLUMN JBPM4_CONFIG_USER.EXECUTIONID_ IS '指定流程ID，预留';
COMMENT ON COLUMN JBPM4_CONFIG_USER.REMARK_ IS '备注';

CREATE TABLE JBPM4_COUNTERSIGN_INFO
(
    EXECUTION_ID                   VARCHAR2(255),
    TASKNAME                       VARCHAR2(255),
    USERCOUNT                      VARCHAR2(10),
    STRATEGY                       VARCHAR2(20),
    STRATEGY_VALUE                 VARCHAR2(10),
    REMARK                         VARCHAR2(200)
);
COMMENT ON COLUMN JBPM4_COUNTERSIGN_INFO.EXECUTION_ID IS '流程ID';
COMMENT ON COLUMN JBPM4_COUNTERSIGN_INFO.TASKNAME IS '节点任务名称';
COMMENT ON COLUMN JBPM4_COUNTERSIGN_INFO.USERCOUNT IS '该会签节点需要参与的人数';
COMMENT ON COLUMN JBPM4_COUNTERSIGN_INFO.STRATEGY IS '会签策略[在WorkFlow Studio中设定的策略]';
COMMENT ON COLUMN JBPM4_COUNTERSIGN_INFO.STRATEGY_VALUE IS '会签策略结果[根据会签总人数和会签策略计算出最终该会签节点最小的通过人数]';
COMMENT ON COLUMN JBPM4_COUNTERSIGN_INFO.REMARK IS '备注';

CREATE TABLE JBPM4_DEPLOYMENT
(
    DBID_                          NUMBER(19,0) NOT NULL,
    NAME_                          CLOB,
    TIMESTAMP_                     NUMBER(19,0),
    STATE_                         VARCHAR2(255),
    CONSTRAINT SYS_C0011325 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE JBPM4_DEPLOYPROP
(
    DBID_                          NUMBER(19,0) NOT NULL,
    DEPLOYMENT_                    NUMBER(19,0),
    OBJNAME_                       VARCHAR2(255),
    KEY_                           VARCHAR2(255),
    STRINGVAL_                     VARCHAR2(255),
    LONGVAL_                       NUMBER(19,0),
    CONSTRAINT SYS_C0011327 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_DEPLPROP_DEPL FOREIGN KEY (DEPLOYMENT_) REFERENCES JBPM4_DEPLOYMENT (DBID_) DISABLE
);

CREATE TABLE JBPM4_EXECUTION
(
    DBID_                          NUMBER(19,0) NOT NULL,
    CLASS_                         VARCHAR2(255) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    ACTIVITYNAME_                  VARCHAR2(255),
    PROCDEFID_                     VARCHAR2(255),
    HASVARS_                       NUMBER(1,0),
    NAME_                          VARCHAR2(255),
    KEY_                           VARCHAR2(255),
    ID_                            VARCHAR2(255),
    STATE_                         VARCHAR2(255),
    SUSPHISTSTATE_                 VARCHAR2(255),
    PRIORITY_                      NUMBER(10,0),
    HISACTINST_                    NUMBER(19,0),
    PARENT_                        NUMBER(19,0),
    INSTANCE_                      NUMBER(19,0),
    SUPEREXEC_                     NUMBER(19,0),
    SUBPROCINST_                   NUMBER(19,0),
    PARENT_IDX_                    NUMBER(10,0),
    CONSTRAINT SYS_C0011331 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_EXEC_INSTANCE FOREIGN KEY (INSTANCE_) REFERENCES JBPM4_EXECUTION (DBID_) DISABLE,
    CONSTRAINT FK_EXEC_PARENT FOREIGN KEY (PARENT_) REFERENCES JBPM4_EXECUTION (DBID_) DISABLE,
    CONSTRAINT FK_EXEC_SUBPI FOREIGN KEY (SUBPROCINST_) REFERENCES JBPM4_EXECUTION (DBID_) DISABLE,
    CONSTRAINT FK_EXEC_SUPEREXEC FOREIGN KEY (SUPEREXEC_) REFERENCES JBPM4_EXECUTION (DBID_) DISABLE,
    CONSTRAINT SYS_C0011332 UNIQUE (ID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE JBPM4_FLOW_RELATION
(
    ID                             VARCHAR2(38),
    SUP_DBID_                      NUMBER(19,0),
    SUP_EXC_ID_                    VARCHAR2(255),
    SUB_DBID_                      NUMBER(19,0),
    SUB_EXC_ID_                    VARCHAR2(255)
);
COMMENT ON COLUMN JBPM4_FLOW_RELATION.ID IS '主键ID';
COMMENT ON COLUMN JBPM4_FLOW_RELATION.SUP_DBID_ IS '主流程的部署ID';
COMMENT ON COLUMN JBPM4_FLOW_RELATION.SUP_EXC_ID_ IS '主流程的EXECUTION的ID';
COMMENT ON COLUMN JBPM4_FLOW_RELATION.SUB_DBID_ IS '子流程的部署ID';
COMMENT ON COLUMN JBPM4_FLOW_RELATION.SUB_EXC_ID_ IS '子流程的EXECUTION的ID';

CREATE TABLE JBPM4_HIST_PROCINST
(
    DBID_                          NUMBER(19,0) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    ID_                            VARCHAR2(255),
    PROCDEFID_                     VARCHAR2(255),
    KEY_                           VARCHAR2(255),
    START_                         TIMESTAMP(6),
    END_                           TIMESTAMP(6),
    DURATION_                      NUMBER(19,0),
    STATE_                         VARCHAR2(255),
    ENDACTIVITY_                   VARCHAR2(255),
    NEXTIDX_                       NUMBER(10,0),
    CONSTRAINT SYS_C0011343 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE JBPM4_HIST_TASK
(
    DBID_                          NUMBER(19,0) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    EXECUTION_                     VARCHAR2(255),
    OUTCOME_                       VARCHAR2(255),
    ASSIGNEE_                      VARCHAR2(255),
    PRIORITY_                      NUMBER(10,0),
    STATE_                         VARCHAR2(255),
    CREATE_                        TIMESTAMP(6),
    END_                           TIMESTAMP(6),
    DURATION_                      NUMBER(19,0),
    NEXTIDX_                       NUMBER(10,0),
    SUPERTASK_                     NUMBER(19,0),
    CONSTRAINT SYS_C0011346 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_HSUPERT_SUB FOREIGN KEY (SUPERTASK_) REFERENCES JBPM4_HIST_TASK (DBID_) DISABLE
);

CREATE TABLE JBPM4_HIST_ACTINST
(
    DBID_                          NUMBER(19,0) NOT NULL,
    CLASS_                         VARCHAR2(255) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    HPROCI_                        NUMBER(19,0),
    TYPE_                          VARCHAR2(255),
    EXECUTION_                     VARCHAR2(255),
    ACTIVITY_NAME_                 VARCHAR2(255),
    START_                         TIMESTAMP(6),
    END_                           TIMESTAMP(6),
    DURATION_                      NUMBER(19,0),
    TRANSITION_                    VARCHAR2(255),
    NEXTIDX_                       NUMBER(10,0),
    HTASK_                         NUMBER(19,0),
    CONSTRAINT SYS_C0011336 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_HACTI_HPROCI FOREIGN KEY (HPROCI_) REFERENCES JBPM4_HIST_PROCINST (DBID_) DISABLE,
    CONSTRAINT FK_HTI_HTASK FOREIGN KEY (HTASK_) REFERENCES JBPM4_HIST_TASK (DBID_) DISABLE
);

CREATE TABLE JBPM4_HIST_VAR
(
    DBID_                          NUMBER(19,0) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    PROCINSTID_                    VARCHAR2(255),
    EXECUTIONID_                   VARCHAR2(255),
    VARNAME_                       VARCHAR2(255),
    VALUE_                         VARCHAR2(255),
    HPROCI_                        NUMBER(19,0),
    HTASK_                         NUMBER(19,0),
    CONSTRAINT SYS_C0011349 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_HVAR_HPROCI FOREIGN KEY (HPROCI_) REFERENCES JBPM4_HIST_PROCINST (DBID_) DISABLE,
    CONSTRAINT FK_HVAR_HTASK FOREIGN KEY (HTASK_) REFERENCES JBPM4_HIST_TASK (DBID_) DISABLE
);

CREATE TABLE JBPM4_HIST_DETAIL
(
    DBID_                          NUMBER(19,0) NOT NULL,
    CLASS_                         VARCHAR2(255) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    USERID_                        VARCHAR2(255),
    TIME_                          TIMESTAMP(6),
    HPROCI_                        NUMBER(19,0),
    HPROCIIDX_                     NUMBER(10,0),
    HACTI_                         NUMBER(19,0),
    HACTIIDX_                      NUMBER(10,0),
    HTASK_                         NUMBER(19,0),
    HTASKIDX_                      NUMBER(10,0),
    HVAR_                          NUMBER(19,0),
    HVARIDX_                       NUMBER(10,0),
    MESSAGE_                       CLOB,
    OLD_STR_                       VARCHAR2(255),
    NEW_STR_                       VARCHAR2(255),
    OLD_INT_                       NUMBER(10,0),
    NEW_INT_                       NUMBER(10,0),
    OLD_TIME_                      TIMESTAMP(6),
    NEW_TIME_                      TIMESTAMP(6),
    PARENT_                        NUMBER(19,0),
    PARENT_IDX_                    NUMBER(10,0),
    CONSTRAINT SYS_C0011340 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_HDETAIL_HACTI FOREIGN KEY (HACTI_) REFERENCES JBPM4_HIST_ACTINST (DBID_) DISABLE,
    CONSTRAINT FK_HDETAIL_HPROCI FOREIGN KEY (HPROCI_) REFERENCES JBPM4_HIST_PROCINST (DBID_) DISABLE,
    CONSTRAINT FK_HDETAIL_HTASK FOREIGN KEY (HTASK_) REFERENCES JBPM4_HIST_TASK (DBID_) DISABLE,
    CONSTRAINT FK_HDETAIL_HVAR FOREIGN KEY (HVAR_) REFERENCES JBPM4_HIST_VAR (DBID_) DISABLE
);

CREATE TABLE JBPM4_ID_GROUP
(
    DBID_                          NUMBER(19,0) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    ID_                            VARCHAR2(255),
    NAME_                          VARCHAR2(255),
    TYPE_                          VARCHAR2(255),
    PARENT_                        NUMBER(19,0),
    CONSTRAINT SYS_C0011352 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_GROUP_PARENT FOREIGN KEY (PARENT_) REFERENCES JBPM4_ID_GROUP (DBID_) DISABLE
);

CREATE TABLE JBPM4_ID_USER
(
    DBID_                          NUMBER(19,0) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    ID_                            VARCHAR2(255),
    PASSWORD_                      VARCHAR2(255),
    GIVENNAME_                     VARCHAR2(255),
    FAMILYNAME_                    VARCHAR2(255),
    BUSINESSEMAIL_                 VARCHAR2(255),
    CONSTRAINT SYS_C0011358 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE JBPM4_ID_MEMBERSHIP
(
    DBID_                          NUMBER(19,0) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    USER_                          NUMBER(19,0),
    GROUP_                         NUMBER(19,0),
    NAME_                          VARCHAR2(255),
    CONSTRAINT SYS_C0011355 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_MEM_GROUP FOREIGN KEY (GROUP_) REFERENCES JBPM4_ID_GROUP (DBID_) DISABLE,
    CONSTRAINT FK_MEM_USER FOREIGN KEY (USER_) REFERENCES JBPM4_ID_USER (DBID_) DISABLE
);

CREATE TABLE JBPM4_JOB
(
    DBID_                          NUMBER(19,0) NOT NULL,
    CLASS_                         VARCHAR2(255) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    DUEDATE_                       TIMESTAMP(6),
    STATE_                         VARCHAR2(255),
    ISEXCLUSIVE_                   NUMBER(1,0),
    LOCKOWNER_                     VARCHAR2(255),
    LOCKEXPTIME_                   TIMESTAMP(6),
    EXCEPTION_                     CLOB,
    RETRIES_                       NUMBER(10,0),
    PROCESSINSTANCE_               NUMBER(19,0),
    EXECUTION_                     NUMBER(19,0),
    CFG_                           NUMBER(19,0),
    SIGNAL_                        VARCHAR2(255),
    EVENT_                         VARCHAR2(255),
    REPEAT_                        VARCHAR2(255),
    CONSTRAINT SYS_C0011362 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE JBPM4_LOB
(
    DBID_                          NUMBER(19,0) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    BLOB_VALUE_                    BLOB,
    DEPLOYMENT_                    NUMBER(19,0),
    NAME_                          CLOB,
    CONSTRAINT SYS_C0011365 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_LOB_DEPLOYMENT FOREIGN KEY (DEPLOYMENT_) REFERENCES JBPM4_DEPLOYMENT (DBID_) DISABLE
);

CREATE TABLE JBPM4_PROPERTY
(
    KEY_                           VARCHAR2(255) NOT NULL,
    VERSION_                       NUMBER(10,0) NOT NULL,
    VALUE_                         VARCHAR2(255),
    CONSTRAINT SYS_C0011371 PRIMARY KEY (KEY_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE JBPM4_SWIMLANE
(
    DBID_                          NUMBER(19,0) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    NAME_                          VARCHAR2(255),
    ASSIGNEE_                      VARCHAR2(255),
    EXECUTION_                     NUMBER(19,0),
    CONSTRAINT SYS_C0011374 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_SWIMLANE_EXEC FOREIGN KEY (EXECUTION_) REFERENCES JBPM4_EXECUTION (DBID_) DISABLE
);

CREATE TABLE JBPM4_TASK
(
    DBID_                          NUMBER(19,0) NOT NULL,
    CLASS_                         CHAR(1) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    NAME_                          VARCHAR2(255),
    DESCR_                         CLOB,
    STATE_                         VARCHAR2(255),
    SUSPHISTSTATE_                 VARCHAR2(255),
    ASSIGNEE_                      VARCHAR2(255),
    FORM_                          VARCHAR2(255),
    PRIORITY_                      NUMBER(10,0),
    CREATE_                        TIMESTAMP(6),
    DUEDATE_                       TIMESTAMP(6),
    PROGRESS_                      NUMBER(10,0),
    SIGNALLING_                    NUMBER(1,0),
    EXECUTION_ID_                  VARCHAR2(255),
    ACTIVITY_NAME_                 VARCHAR2(255),
    HASVARS_                       NUMBER(1,0),
    SUPERTASK_                     NUMBER(19,0),
    EXECUTION_                     NUMBER(19,0),
    PROCINST_                      NUMBER(19,0),
    SWIMLANE_                      NUMBER(19,0),
    TASKDEFNAME_                   VARCHAR2(255),
    CONSTRAINT SYS_C0011378 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_TASK_SUPERTASK FOREIGN KEY (SUPERTASK_) REFERENCES JBPM4_TASK (DBID_) DISABLE,
    CONSTRAINT FK_TASK_SWIML FOREIGN KEY (SWIMLANE_) REFERENCES JBPM4_SWIMLANE (DBID_) DISABLE
);

CREATE TABLE JBPM4_PARTICIPATION
(
    DBID_                          NUMBER(19,0) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    GROUPID_                       VARCHAR2(255),
    USERID_                        VARCHAR2(255),
    TYPE_                          VARCHAR2(255),
    TASK_                          NUMBER(19,0),
    SWIMLANE_                      NUMBER(19,0),
    CONSTRAINT SYS_C0011368 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_PART_SWIMLANE FOREIGN KEY (SWIMLANE_) REFERENCES JBPM4_SWIMLANE (DBID_) DISABLE,
    CONSTRAINT FK_PART_TASK FOREIGN KEY (TASK_) REFERENCES JBPM4_TASK (DBID_) DISABLE
);

CREATE TABLE JBPM4_VARIABLE
(
    DBID_                          NUMBER(19,0) NOT NULL,
    CLASS_                         VARCHAR2(255) NOT NULL,
    DBVERSION_                     NUMBER(10,0) NOT NULL,
    KEY_                           VARCHAR2(255),
    CONVERTER_                     VARCHAR2(255),
    HIST_                          NUMBER(1,0),
    EXECUTION_                     NUMBER(19,0),
    TASK_                          NUMBER(19,0),
    LOB_                           NUMBER(19,0),
    DATE_VALUE_                    TIMESTAMP(6),
    DOUBLE_VALUE_                  FLOAT(126),
    CLASSNAME_                     VARCHAR2(255),
    LONG_VALUE_                    NUMBER(19,0),
    STRING_VALUE_                  VARCHAR2(255),
    TEXT_VALUE_                    CLOB,
    EXESYS_                        NUMBER(19,0),
    CONSTRAINT SYS_C0011382 PRIMARY KEY (DBID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_VAR_EXECUTION FOREIGN KEY (EXECUTION_) REFERENCES JBPM4_EXECUTION (DBID_) DISABLE,
    CONSTRAINT FK_VAR_EXESYS FOREIGN KEY (EXESYS_) REFERENCES JBPM4_EXECUTION (DBID_) DISABLE,
    CONSTRAINT FK_VAR_TASK FOREIGN KEY (TASK_) REFERENCES JBPM4_TASK (DBID_) DISABLE
);

--Orient自定义持久化分支流程与主流程的关系
CREATE TABLE "JBPM4_FLOW_BRANCH_RELATION" 
(	"ID" 				VARCHAR2(38), 
	"SUP_DBID_" 		NUMBER(19,0), 
	"SUP_EXC_ID_" 		VARCHAR2(255), 
	"SUB_DBID_" 		NUMBER(19,0), 
	"SUB_EXC_ID_" 		VARCHAR2(255)
); 
COMMENT ON COLUMN "JBPM4_FLOW_BRANCH_RELATION"."ID" IS '主键ID'; 
COMMENT ON COLUMN "JBPM4_FLOW_BRANCH_RELATION"."SUP_DBID_" IS '主流程的部署ID'; 
COMMENT ON COLUMN "JBPM4_FLOW_BRANCH_RELATION"."SUP_EXC_ID_" IS '主流程的EXECUTION的ID'; 
COMMENT ON COLUMN "JBPM4_FLOW_BRANCH_RELATION"."SUB_DBID_" IS '子流程的部署ID';
COMMENT ON COLUMN "JBPM4_FLOW_BRANCH_RELATION"."SUB_EXC_ID_" IS '子流程的EXECUTION的ID';   
   
   

-- Orient自定义 定义任务数据的序列化的实例
create table JBPM4_EDM_TASK_VAR
(
  id    varchar2(38) not null,
  value blob,
  CONSTRAINT PK_JBPM4_EDM_TASK_VAR primary key (id)
);

-- Orient自定义 定义流程运行过程中的中间数据
CREATE TABLE "JBPM4_EDM_VARIABLE" 
(	
	"ID" 				VARCHAR2(38) NOT NULL ENABLE, 
	"PROCESSNAME" 		VARCHAR2(38), 
	"PIID" 				VARCHAR2(38), 
	"TASKNAME" 			VARCHAR2(38), 
	"TASKID" 			VARCHAR2(38), 
	"VARIABLETYPE" 		VARCHAR2(38), 
	"KEY" 				VARCHAR2(38), 
	"VALUE" 			VARCHAR2(500), 
	"TASKBELONGED"		VARCHAR2(800),
	CONSTRAINT "PK_JBPM4_EDM_VARIABLE" PRIMARY KEY ("ID")
   ) ;
 
create table JBPM4_EDM_COURSE
(
  id           NUMBER(38) not null,
  main_piid    VARCHAR2(38) not null,
  project_id   VARCHAR2(38) not null,
  flow_task_id VARCHAR2(38) not null,
  prj_task_id  VARCHAR2(38) not null,
  outcome      VARCHAR2(38) not null,
  CONSTRAINT "PK_JBPM4_EDM_COURSE" PRIMARY KEY ("ID")
);

comment on column JBPM4_EDM_COURSE.main_piid
  is '主流程实例ID';
comment on column JBPM4_EDM_COURSE.project_id
  is '所属项目ID';
comment on column JBPM4_EDM_COURSE.flow_task_id
  is '流程任务ID';
comment on column JBPM4_EDM_COURSE.prj_task_id
  is '项目任务ID';
comment on column JBPM4_EDM_COURSE.outcome
  is '任务走向';

--*******************************************--
--             SEQUENCE 相关                 --
--*******************************************--
CREATE SEQUENCE JBPM4_HIST_CWM_VAR_SEQ
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE SEQ_JBPM4_FLOW_RELATION
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE  SEQ_JBPM4_FLOW_BRANCH_RELATION
MINVALUE 1 
MAXVALUE 1000000000000000000000000000 
INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;


create sequence SEQ_JBPM4_EDM_TASK_VAR
minvalue 1
maxvalue 9999999999999999999999
start with 1
increment by 1
cache 20;

CREATE SEQUENCE  SEQ_JBPM4_EDM_VARIABLE  
MINVALUE 1 
MAXVALUE 9999999999999999999999999 
INCREMENT BY 1 
START WITH 1 
NOCACHE  NOORDER  NOCYCLE ;

CREATE SEQUENCE  SEQ_JBPM4_EDM_COURSE  
MINVALUE 0 MAXVALUE 99999999999999999999999 
INCREMENT BY 1 START WITH 1
CACHE 20 NOORDER  NOCYCLE ;