/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016/3/10 ������ 11:17:14                       */
/*==============================================================*/


drop table ALARM_INFO cascade constraints;

drop table ALARM_NOTICE cascade constraints;

drop table ALARM_USER cascade constraints;

drop table ALARM_USER_HIST cascade constraints;

drop table CWM_ARITHMETIC cascade constraints;

drop table CWM_BACK cascade constraints;

drop table CWM_CODETONAME cascade constraints;

drop table CWM_PORTAL cascade constraints;

drop table CWM_REOPRT_BOOKMARK cascade constraints;

drop table CWM_REPORTS cascade constraints;

drop table CWM_REPORT_ITEMS cascade constraints;

drop table CWM_STATISTIC_CONFIG cascade constraints;

drop table CWM_SYS_ACCOUNT_STRATEGY cascade constraints;

drop table CWM_SYS_DEPARTMENT cascade constraints;

drop table CWM_SYS_FUNCTION cascade constraints;

drop table CWM_SYS_LOG cascade constraints;

drop table CWM_SYS_OPERATION cascade constraints;

drop table CWM_SYS_OVERALLOPERATONS cascade constraints;

drop table CWM_SYS_PARAMETER cascade constraints;

drop table CWM_SYS_PARTOPERATIONS cascade constraints;

drop table CWM_SYS_PASSWORD_HISTORY cascade constraints;

drop table CWM_SYS_ROLE cascade constraints;

drop table CWM_SYS_ROLE_ARITH cascade constraints;

drop table CWM_SYS_ROLE_FUNCTION_TBOM cascade constraints;

drop table CWM_SYS_ROLE_SCHEMA cascade constraints;

drop table CWM_SYS_ROLE_USER cascade constraints;

drop table CWM_SYS_TOOLS cascade constraints;

drop table CWM_SYS_TOOLS_COLUMNS cascade constraints;

drop table CWM_SYS_TOOLS_GROUP cascade constraints;

drop table CWM_SYS_USER cascade constraints;

drop table CWM_SYS_USERLOGINHISTORY cascade constraints;

drop table CWM_SYS_USER_COLUMNS cascade constraints;

drop table CWM_SYS_USER_DEPT cascade constraints;

drop table CWM_SYS_USER_ENUM cascade constraints;

drop table CWM_TIME_INFO cascade constraints;

drop table CWM_USER_TOOL cascade constraints;

drop table CWM_USER_VIEW cascade constraints;

drop table FREEMARK_TEMPLATE cascade constraints;

drop table MODEL_BTN_TYPE cascade constraints;

drop table MODEL_COLUMN_RULE cascade constraints;

drop table MODEL_FORM_VIEW cascade constraints;

drop table MODEL_GRID_VIEW cascade constraints;

drop table MODEL_QUERY cascade constraints;

/*==============================================================*/
/* Table: ALARM_INFO                                            */
/*==============================================================*/
create table ALARM_INFO  (
   ID                   VARCHAR2(30 CHAR)               not null,
   NLEVEL               NUMBER(2),
   CLASSNAME            VARCHAR2(100 CHAR),
   PARAMS               VARCHAR2(500 CHAR),
   ISALARM              NUMBER(1)                      default 0,
   constraint ALARM_INFO_PK primary key (ID)
);

comment on table ALARM_INFO is
'Ԥ����Ϣ';

comment on column ALARM_INFO.ID is
'ID';

comment on column ALARM_INFO.NLEVEL is
'Ԥ���ȼ�';

comment on column ALARM_INFO.CLASSNAME is
'Ԥ������У����';

comment on column ALARM_INFO.PARAMS is
'Ԥ������У�����';

comment on column ALARM_INFO.ISALARM is
'�Ƿ��Ѳ���Ԥ��';

/*==============================================================*/
/* Table: ALARM_NOTICE                                          */
/*==============================================================*/
create table ALARM_NOTICE  (
   ID                   VARCHAR2(30 CHAR)               not null,
   NOTICETYPE           NUMBER(8),
   TRIGGERTYPE          VARCHAR2(1),
   INTERVAL             INTEGER,
   REPEAT               INTEGER,
   YEAR                 VARCHAR2(4),
   MONTH                VARCHAR2(2),
   WEEKDAY              VARCHAR2(1),
   TIME                 VARCHAR2(10),
   MONTHDAY             VARCHAR2(2),
   constraint PK_ALARM_NOTICE primary key (ID)
);

comment on table ALARM_NOTICE is
'Ԥ����ʾ';

comment on column ALARM_NOTICE.ID is
'ID';

comment on column ALARM_NOTICE.NOTICETYPE is
'֪ͨ��ʽ���ʼ�00000001 ����00000010 �ʼ�+����00000011';

comment on column ALARM_NOTICE.TRIGGERTYPE is
'֪ͨ���ͣ�0 �ط�֪ͨ 1 ��ʱ֪ͨ';

comment on column ALARM_NOTICE.INTERVAL is
'�ظ�����ʱ���� ����';

comment on column ALARM_NOTICE.REPEAT is
'�ظ����ʹ���';

comment on column ALARM_NOTICE.YEAR is
'��ʱ���ͷ�ʽ��ָ�����';

comment on column ALARM_NOTICE.MONTH is
'��ʱ���ͷ�ʽ��ָ���·�';

comment on column ALARM_NOTICE.WEEKDAY is
'��ʱ���ͷ�ʽ��ָ�����죬��ÿ��1';

comment on column ALARM_NOTICE.TIME is
'��ʱ���ͷ�ʽ��ʱ�� 12:10:10';

comment on column ALARM_NOTICE.MONTHDAY is
'��ʱ���ͷ�ʽ��ָ������';

/*==============================================================*/
/* Table: ALARM_USER                                            */
/*==============================================================*/
create table ALARM_USER  (
   ALARMID              VARCHAR2(30 CHAR)               not null,
   USERID               VARCHAR2(30 CHAR)               not null,
   TYPE                 NUMBER(1),
   constraint ALARM_USER_PK primary key (USERID, ALARMID)
);

comment on table ALARM_USER is
'Ԥ���û��м��';

comment on column ALARM_USER.ALARMID is
'Ԥ��ID';

comment on column ALARM_USER.USERID is
'����Ԥ���û�';

comment on column ALARM_USER.TYPE is
'Ԥ������';

/*==============================================================*/
/* Table: ALARM_USER_HIST                                       */
/*==============================================================*/
create table ALARM_USER_HIST  (
   ALARMID              VARCHAR2(30 CHAR)               not null,
   USERID               VARCHAR2(30 CHAR)               not null,
   TYPE                 NUMBER(1),
   constraint ALARM_USER_HIST_PK primary key (USERID, ALARMID)
);

comment on table ALARM_USER_HIST is
'Ԥ����ʷ';

comment on column ALARM_USER_HIST.ALARMID is
'��ʷԤ��ID';

comment on column ALARM_USER_HIST.USERID is
'��ʷ����Ԥ���û�';

comment on column ALARM_USER_HIST.TYPE is
'��ʷԤ������';

/*==============================================================*/
/* Table: CWM_ARITHMETIC                                        */
/*==============================================================*/
create table CWM_ARITHMETIC  (
   ID                   VARCHAR2(100),
   NAME                 VARCHAR2(100),
   TYPE                 NUMBER(1),
   CATEGORY             VARCHAR2(100),
   DESCRIPTION          VARCHAR2(1000),
   FILE_NAME            VARCHAR2(100),
   METHOD_NAME          VARCHAR2(100),
   PARA_NUMBER          NUMBER(10),
   PARA_TYPE            VARCHAR2(400),
   REF_LIB              VARCHAR2(100),
   DATA_TYPE            VARCHAR2(30),
   IS_VALID             NUMBER(1),
   ARITH_TYPE           NUMBER(1),
   LEAST_NUMBER         NUMBER(10),
   CLASS_NAME           VARCHAR2(100),
   CLASS_PACKAGE        VARCHAR2(100),
   FILE_NUMBER          NUMBER(10),
   ARITH_METHOD         VARCHAR2(100),
   PID                  VARCHAR2(38),
   MAIN_JAR             NUMBER(1),
   FILE_LOCATION        VARCHAR2(200)
);

comment on table CWM_ARITHMETIC is
'�Զ����㷨��';

comment on column CWM_ARITHMETIC.ID is
'ΨһID';

comment on column CWM_ARITHMETIC.NAME is
'�㷨����';

comment on column CWM_ARITHMETIC.TYPE is
'���ͣ������ݿ��Դ��㷨��0�����Զ����㷨��1��';

comment on column CWM_ARITHMETIC.CATEGORY is
'����㷨�������';

comment on column CWM_ARITHMETIC.DESCRIPTION is
'�㷨����';

comment on column CWM_ARITHMETIC.FILE_NAME is
'�Զ����㷨���ļ���(���Զ��)���ԡ������ָ�';

comment on column CWM_ARITHMETIC.METHOD_NAME is
'�Զ����㷨�ķ�����';

comment on column CWM_ARITHMETIC.PARA_NUMBER is
'�㷨�Ĳ�������';

comment on column CWM_ARITHMETIC.PARA_TYPE is
'�㷨�Ĳ������ͣ��ԡ������ָ�';

comment on column CWM_ARITHMETIC.REF_LIB is
'���õ�lib����';

comment on column CWM_ARITHMETIC.DATA_TYPE is
'�㷨��������';

comment on column CWM_ARITHMETIC.IS_VALID is
'�Ƿ����ã��Ƿ���Ч��1��ʾ��Ч�����ã�0��ʾ��Ч��ɾ��';

comment on column CWM_ARITHMETIC.ARITH_TYPE is
'�㷨����Ӧ�ĺ����ǵ��к������Ǿۼ�������0��ʾ���к�����1��ʾ�ۼ�����';

comment on column CWM_ARITHMETIC.LEAST_NUMBER is
'�����Ʋ����Ĳ���������Сֵ���������ж��ٸ�������';

comment on column CWM_ARITHMETIC.CLASS_NAME is
'�Զ����㷨��������';

comment on column CWM_ARITHMETIC.CLASS_PACKAGE is
'�Զ����㷨������İ���';

comment on column CWM_ARITHMETIC.FILE_NUMBER is
'�Զ����㷨���ļ���';

comment on column CWM_ARITHMETIC.ARITH_METHOD is
'���ݿ������㷨��ʽ';

comment on column CWM_ARITHMETIC.PID is
'�����Զ����㷨��������֯';

comment on column CWM_ARITHMETIC.MAIN_JAR is
'���ڽ�������jar����ʶ';

comment on column CWM_ARITHMETIC.FILE_LOCATION is
'�㷨λ��';

/*==============================================================*/
/* Table: CWM_BACK                                              */
/*==============================================================*/
create table CWM_BACK  (
   ID                   VARCHAR2(38),
   SCHEMA_ID            VARCHAR2(38),
   USER_ID              VARCHAR2(20),
   BACK_DATE            DATE,
   FILE_PATH            VARCHAR2(200),
   BACK_MODEL           VARCHAR2(1),
   BACK_DATA            VARCHAR2(1),
   AUTO_BACK            VARCHAR2(1),
   AUTO_BACK_DATE       DATE,
   AUTO_BACK_ZQ         VARCHAR2(3),
   REMARK               VARCHAR2(200),
   TYPE                 NUMBER(1),
   TABLE_ID             VARCHAR2(38)
);

comment on table CWM_BACK is
'ϵͳ���ݱ�';

comment on column CWM_BACK.ID is
'ID';

comment on column CWM_BACK.SCHEMA_ID is
'����schemaId';

comment on column CWM_BACK.USER_ID is
'�����û�';

comment on column CWM_BACK.BACK_DATE is
'����ʱ��';

comment on column CWM_BACK.FILE_PATH is
'����·��';

comment on column CWM_BACK.BACK_MODEL is
'�Ƿ񱸷�ģ��';

comment on column CWM_BACK.BACK_DATA is
'�Ƿ񱸷�����';

comment on column CWM_BACK.AUTO_BACK is
'�Զ�����';

comment on column CWM_BACK.AUTO_BACK_DATE is
'�Զ�����ʱ��';

comment on column CWM_BACK.REMARK is
'���ݱ�ע';

comment on column CWM_BACK.TYPE is
'��������';

comment on column CWM_BACK.TABLE_ID is
'����ģ��ID';

/*==============================================================*/
/* Table: CWM_CODETONAME                                        */
/*==============================================================*/
create table CWM_CODETONAME  (
   ID                   VARCHAR2(20),
   NAME                 VARCHAR2(100),
   TYPEID               VARCHAR2(10),
   TYPENAME             VARCHAR2(100),
   REMARK               VARCHAR2(200)
);

comment on table CWM_CODETONAME is
'ϵͳ�ڲ�����ά����';

comment on column CWM_CODETONAME.ID is
'ID';

comment on column CWM_CODETONAME.NAME is
'��������';

comment on column CWM_CODETONAME.TYPEID is
'��������';

comment on column CWM_CODETONAME.TYPENAME is
'����Ӣ������';

comment on column CWM_CODETONAME.REMARK is
'������ע';

/*==============================================================*/
/* Table: CWM_PORTAL                                            */
/*==============================================================*/
create table CWM_PORTAL  (
   ID                   VARCHAR2(38)                    not null,
   URL          VARCHAR2(38),
   TITLE                VARCHAR2(50),
   JSPATH        VARCHAR2(2000),
   constraint PK_CWM_PORTAL primary key (ID)
);

comment on table CWM_PORTAL is
'��ҳ��������';

comment on column CWM_PORTAL.ID is
'ID';

comment on column CWM_PORTAL.FUNCTION_ID is
'�������ܵ�ID';

comment on column CWM_PORTAL.TITLE is
'����';

comment on column CWM_PORTAL.SPRINGMVC_URL is
'SPRINGMVC·��';

comment on column CWM_PORTAL.SEA_URL is
'SEA·��';

comment on column CWM_PORTAL.IS_DEL is
'�Ƿ��Ѿ�ɾ��';

/*==============================================================*/
/* Table: CWM_REOPRT_BOOKMARK                                   */
/*==============================================================*/
create table CWM_REOPRT_BOOKMARK  (
   ID                   VARCHAR2(8),
   NAME                 VARCHAR2(500)
);

comment on table CWM_REOPRT_BOOKMARK is
'����������ǩ��';

comment on column CWM_REOPRT_BOOKMARK.ID is
'����������ǩID';

comment on column CWM_REOPRT_BOOKMARK.NAME is
'����������ǩ����';

/*==============================================================*/
/* Table: CWM_REPORTS                                           */
/*==============================================================*/
create table CWM_REPORTS  (
   ID                   VARCHAR2(38)                    not null,
   SCHEMA_ID            VARCHAR2(38),
   TABLE_ID             VARCHAR2(200),
   VIEWS_ID             VARCHAR2(38),
   COLUMN_ID            VARCHAR2(38),
   CONTENT              CLOB,
   FILEPATH             VARCHAR2(200),
   PID                  VARCHAR2(38),
   TYPE                 NUMBER(1),
   ORDERS               NUMBER(1),
   CREATE_USER          VARCHAR2(100),
   CREATE_TIME          DATE,
   NAME                 VARCHAR2(100),
   FILTERJSON           VARCHAR2(4000),
   DATA_ENTRY           VARCHAR2(38),
   constraint PK_CWM_REPORTS primary key (ID)
);

comment on table CWM_REPORTS is
'����ģ���';

comment on column CWM_REPORTS.ID is
'ID';

comment on column CWM_REPORTS.SCHEMA_ID is
'��SCHEMA';

comment on column CWM_REPORTS.TABLE_ID is
'��ģ�ͼ���';

comment on column CWM_REPORTS.VIEWS_ID is
'����ͼ����';

comment on column CWM_REPORTS.COLUMN_ID is
'����ģ����ǩ��ģ���ֶ�ID';

comment on column CWM_REPORTS.CONTENT is
'����ģ����ǩ����';

comment on column CWM_REPORTS.FILEPATH is
'ģ��·��';

comment on column CWM_REPORTS.PID is
'��';

comment on column CWM_REPORTS.TYPE is
'ģ��';

comment on column CWM_REPORTS.ORDERS is
'ģ������';

comment on column CWM_REPORTS.CREATE_USER is
'������';

comment on column CWM_REPORTS.CREATE_TIME is
'����ʱ��';

comment on column CWM_REPORTS.NAME is
'ģ������';

comment on column CWM_REPORTS.FILTERJSON is
'���˱��ʽ';

comment on column CWM_REPORTS.DATA_ENTRY is
'����ģ��';

/*==============================================================*/
/* Table: CWM_REPORT_ITEMS                                      */
/*==============================================================*/
create table CWM_REPORT_ITEMS  (
   REPORT_ID            VARCHAR2(38),
   TABLE_ID             VARCHAR2(38),
   COLUMN_NAME          VARCHAR2(38),
   RELATIONS            VARCHAR2(100),
   TYPE                 VARCHAR2(2)
);

comment on table CWM_REPORT_ITEMS is
'����ģ����ϸ��';

comment on column CWM_REPORT_ITEMS.REPORT_ID is
'��������ģ��';

comment on column CWM_REPORT_ITEMS.TABLE_ID is
'ģ��ID';

comment on column CWM_REPORT_ITEMS.COLUMN_NAME is
'���������ֶ�����';

comment on column CWM_REPORT_ITEMS.RELATIONS is
'�������ɹ�ϵ';

comment on column CWM_REPORT_ITEMS.TYPE is
'��������';

/*==============================================================*/
/* Table: CWM_STATISTIC_CONFIG                                  */
/*==============================================================*/
create table CWM_STATISTIC_CONFIG  (
   ID                   NUMBER(8)                       not null,
   CONFIG_NAME          VARCHAR2(40),
   CONDITIONS           VARCHAR2(200),
   SQLS                 VARCHAR2(200),
   SCRIPT               VARCHAR2(1000),
   REPORTCONFIG         VARCHAR2(200),
   CONFIG_DESC          VARCHAR2(100)
);

comment on table CWM_STATISTIC_CONFIG is
'ͳ�Ƴ�ʼ����';

comment on column CWM_STATISTIC_CONFIG.ID is
'ID';

comment on column CWM_STATISTIC_CONFIG.CONFIG_NAME is
'ͳ������';

comment on column CWM_STATISTIC_CONFIG.CONDITIONS is
'ͳ������';

comment on column CWM_STATISTIC_CONFIG.SQLS is
'ͳ������Դ׼��';

comment on column CWM_STATISTIC_CONFIG.SCRIPT is
'����ű�';

comment on column CWM_STATISTIC_CONFIG.REPORTCONFIG is
'ͼ������';

comment on column CWM_STATISTIC_CONFIG.CONFIG_DESC is
'����';

/*==============================================================*/
/* Table: CWM_SYS_ACCOUNT_STRATEGY                              */
/*==============================================================*/
create table CWM_SYS_ACCOUNT_STRATEGY  (
   ID                   NUMBER                          not null,
   STRATEGY_NAME        VARCHAR2(100),
   STRATEGY_NOTE        VARCHAR2(1000),
   STRATEGY_VALUE1      VARCHAR2(100),
   STRATEGY_VALUE2      VARCHAR2(100),
   IS_USE               VARCHAR2(1),
   TYPE                 VARCHAR2(1),
   STRATEGY_VALUE       VARCHAR2(100),
   constraint PK_CWM_SYS_ACCOUNT_STRATEGY primary key (ID)
);

comment on table CWM_SYS_ACCOUNT_STRATEGY is
'�û����Ա�';

comment on column CWM_SYS_ACCOUNT_STRATEGY.ID is
'ID';

comment on column CWM_SYS_ACCOUNT_STRATEGY.STRATEGY_NAME is
'��������';

comment on column CWM_SYS_ACCOUNT_STRATEGY.STRATEGY_NOTE is
'���Ա�ע';

comment on column CWM_SYS_ACCOUNT_STRATEGY.STRATEGY_VALUE1 is
'����ֵ��';

comment on column CWM_SYS_ACCOUNT_STRATEGY.STRATEGY_VALUE2 is
'����ֵ��';

comment on column CWM_SYS_ACCOUNT_STRATEGY.IS_USE is
'�Ƿ�����';

comment on column CWM_SYS_ACCOUNT_STRATEGY.TYPE is
'��������';

comment on column CWM_SYS_ACCOUNT_STRATEGY.STRATEGY_VALUE is
'����ֵһ';

/*==============================================================*/
/* Table: CWM_SYS_DEPARTMENT                                    */
/*==============================================================*/
create table CWM_SYS_DEPARTMENT  (
   ID                   VARCHAR2(38)                    not null,
   PID                  VARCHAR2(38)                    not null,
   NAME                 VARCHAR2(100)                   not null,
   FUNCTION             VARCHAR2(500),
   NOTES                VARCHAR2(1000),
   ADD_FLG              VARCHAR2(1),
   DEL_FLG              VARCHAR2(1),
   EDIT_FLG             VARCHAR2(1),
   constraint PK_CWM_SYS_DEPARTMENT primary key (ID)
);

comment on table CWM_SYS_DEPARTMENT is
'���ű�';

comment on column CWM_SYS_DEPARTMENT.ID is
'���ű��';

comment on column CWM_SYS_DEPARTMENT.PID is
'�ϼ����ű��';

comment on column CWM_SYS_DEPARTMENT.NAME is
'��������';

comment on column CWM_SYS_DEPARTMENT.FUNCTION is
'����ְ��';

comment on column CWM_SYS_DEPARTMENT.NOTES is
'��ע';

comment on column CWM_SYS_DEPARTMENT.ADD_FLG is
'�Ƿ��������Ӳ���';

comment on column CWM_SYS_DEPARTMENT.DEL_FLG is
'�Ƿ����ɾ��';

comment on column CWM_SYS_DEPARTMENT.EDIT_FLG is
'�Ƿ���Ա༭';

/*==============================================================*/
/* Table: CWM_SYS_FUNCTION                                      */
/*==============================================================*/
create table CWM_SYS_FUNCTION  (
   FUNCTIONID           NUMBER                          not null,
   CODE                 VARCHAR2(10),
   NAME                 VARCHAR2(100),
   PARENTID             NUMBER                          not null,
   URL                  VARCHAR2(400)                   not null,
   NOTES                VARCHAR2(400),
   ADD_FLG              VARCHAR2(1),
   DEL_FLG              VARCHAR2(1),
   EDIT_FLG             VARCHAR2(1),
   POSITION             NUMBER,
   LOGTYPE              VARCHAR2(15),
   LOGSHOW              VARCHAR2(15),
   IS_SHOW              NUMBER(1)                      default 1,
   TBOM_FLG             VARCHAR2(1)                    default '0',
   constraint PK_CWM_FUNCTION primary key (FUNCTIONID)
);

comment on table CWM_SYS_FUNCTION is
'���ܵ��';

comment on column CWM_SYS_FUNCTION.FUNCTIONID is
'���ܵ�ID';

comment on column CWM_SYS_FUNCTION.CODE is
'���ܵ���';

comment on column CWM_SYS_FUNCTION.NAME is
'���ܵ�����';

comment on column CWM_SYS_FUNCTION.PARENTID is
'�����ܵ�ID';

comment on column CWM_SYS_FUNCTION.URL is
'���ܵ�URL';

comment on column CWM_SYS_FUNCTION.NOTES is
'���ܵ㱸ע';

comment on column CWM_SYS_FUNCTION.ADD_FLG is
'�Ƿ��������ӹ��ܵ�';

comment on column CWM_SYS_FUNCTION.DEL_FLG is
'�Ƿ����ɾ��';

comment on column CWM_SYS_FUNCTION.EDIT_FLG is
'�Ƿ���Ա༭';

comment on column CWM_SYS_FUNCTION.POSITION is
'���ܵ�λ��';

comment on column CWM_SYS_FUNCTION.LOGTYPE is
'��¼��־����';

comment on column CWM_SYS_FUNCTION.IS_SHOW is
'�Ƿ�չ��';

comment on column CWM_SYS_FUNCTION.TBOM_FLG is
'�Ƿ����TBOM';

ALTER TABLE "ORIENTTDM"."CWM_SYS_FUNCTION"
ADD ( "JS" VARCHAR2(400 BYTE) NULL  )
ADD ( "ICON" VARCHAR2(400 BYTE) NULL  ) ;

COMMENT ON COLUMN "ORIENTTDM"."CWM_SYS_FUNCTION"."JS" IS '功能点JS类';

COMMENT ON COLUMN "ORIENTTDM"."CWM_SYS_FUNCTION"."ICON" IS '功能点图标';


/*==============================================================*/
/* Table: CWM_SYS_LOG                                           */
/*==============================================================*/
create table CWM_SYS_LOG  (
   ID                   NUMBER                          not null,
   OP_TYPE_ID           VARCHAR2(100),
   OP_USER_ID           VARCHAR2(20),
   OP_IP_ADDRESS        VARCHAR2(15),
   OP_DATE              DATE,
   OP_TARGET            VARCHAR2(200),
   OP_REMARK            VARCHAR2(500),
   OP_RESULT            VARCHAR2(20),
   constraint PK_CWM_SYS_LOG primary key (ID)
);

comment on table CWM_SYS_LOG is
'��־��';

comment on column CWM_SYS_LOG.ID is
'ID';

comment on column CWM_SYS_LOG.OP_TYPE_ID is
'��������ID';

comment on column CWM_SYS_LOG.OP_USER_ID is
'������ID';

comment on column CWM_SYS_LOG.OP_IP_ADDRESS is
'������IP';

comment on column CWM_SYS_LOG.OP_DATE is
'��������';

comment on column CWM_SYS_LOG.OP_TARGET is
'����Ŀ��';

comment on column CWM_SYS_LOG.OP_REMARK is
'������ע';

comment on column CWM_SYS_LOG.OP_RESULT is
'�������';

/*==============================================================*/
/* Table: CWM_SYS_OPERATION                                     */
/*==============================================================*/
create table CWM_SYS_OPERATION  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(100)                   not null,
   constraint PK_CWM_SYS_OPERATION primary key (ID)
);

comment on table CWM_SYS_OPERATION is
'�û�Ȩ��Ԫ���ݱ�';

comment on column CWM_SYS_OPERATION.ID is
'ID';

comment on column CWM_SYS_OPERATION.NAME is
'Ȩ������';

/*==============================================================*/
/* Table: CWM_SYS_OVERALLOPERATONS                              */
/*==============================================================*/
create table CWM_SYS_OVERALLOPERATONS  (
   ROLE_ID              VARCHAR2(20)                    not null,
   OPERATION_IDS        VARCHAR2(100)                   not null
);

comment on table CWM_SYS_OVERALLOPERATONS is
'�û�Ȩ�ޱ�';

comment on column CWM_SYS_OVERALLOPERATONS.ROLE_ID is
'��ɫID';

comment on column CWM_SYS_OVERALLOPERATONS.OPERATION_IDS is
'ӵ��Ȩ��ID����';

/*==============================================================*/
/* Table: CWM_SYS_PARAMETER                                     */
/*==============================================================*/
create table CWM_SYS_PARAMETER  (
   ID                   NUMBER(8)                       not null,
   NAME                 VARCHAR2(50),
   DATATYPE             VARCHAR2(50),
   VALUE                VARCHAR2(100),
   DESCRIPTION          VARCHAR2(150),
   constraint PK_CWM_SYS_PARAMETER primary key (ID)
);

comment on table CWM_SYS_PARAMETER is
'ϵͳ������';

comment on column CWM_SYS_PARAMETER.ID is
'ID';

comment on column CWM_SYS_PARAMETER.NAME is
'��������';

comment on column CWM_SYS_PARAMETER.DATATYPE is
'��������';

comment on column CWM_SYS_PARAMETER.VALUE is
'����ֵ';

comment on column CWM_SYS_PARAMETER.DESCRIPTION is
'��������';

/*==============================================================*/
/* Table: CWM_SYS_PARTOPERATIONS                                */
/*==============================================================*/
create table CWM_SYS_PARTOPERATIONS  (
   ID                   NUMBER                          not null,
   ROLE_ID              VARCHAR2(20),
   TABLE_ID             VARCHAR2(20),
   COLUMN_ID            VARCHAR2(20),
   OPERATIONS_ID        VARCHAR2(100),
   FILTER               VARCHAR2(4000),
   IS_TABLE             VARCHAR2(1)                     not null,
   constraint PK_CWM_SYS_PARTOPERATIONS primary key (ID)
);

comment on table CWM_SYS_PARTOPERATIONS is
'�û�ģ��Ȩ���м��';

comment on column CWM_SYS_PARTOPERATIONS.ID is
'ID';

comment on column CWM_SYS_PARTOPERATIONS.ROLE_ID is
'��ɫID';

comment on column CWM_SYS_PARTOPERATIONS.TABLE_ID is
'ģ��ID';

comment on column CWM_SYS_PARTOPERATIONS.COLUMN_ID is
'ģ���ֶ�ID';

comment on column CWM_SYS_PARTOPERATIONS.OPERATIONS_ID is
'���Բ���Ȩ��ID����';

comment on column CWM_SYS_PARTOPERATIONS.FILTER is
'����SQL���';

comment on column CWM_SYS_PARTOPERATIONS.IS_TABLE is
'�Ƿ���ģ��';

/*==============================================================*/
/* Table: CWM_SYS_PASSWORD_HISTORY                              */
/*==============================================================*/
create table CWM_SYS_PASSWORD_HISTORY  (
   ID                   NUMBER                          not null,
   PASSWORD             VARCHAR2(100),
   USER_ID             VARCHAR2(20),
   PASSWORD_SET_TIME    DATE,
   constraint PK_CWM_SYS_PASSWORD_HISTORY primary key (ID)
);

comment on table CWM_SYS_PASSWORD_HISTORY is
'�û�������ʷ';

comment on column CWM_SYS_PASSWORD_HISTORY.ID is
'ID';

comment on column CWM_SYS_PASSWORD_HISTORY.PASSWORD is
'��ʷ����';

comment on column CWM_SYS_PASSWORD_HISTORY.PASSWORD_SET_TIME is
'��ʷ��������ʱ��';

/*==============================================================*/
/* Table: CWM_SYS_ROLE                                          */
/*==============================================================*/
create table CWM_SYS_ROLE  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(100)                   not null,
   MEMO                 VARCHAR2(4000),
   TYPE                 VARCHAR2(1),
   STATUS               VARCHAR2(1),
   FLG                  VARCHAR2(1),
   constraint PK_CWM_SYS_ROLE primary key (ID)
);

comment on table CWM_SYS_ROLE is
'��ɫ��';

comment on column CWM_SYS_ROLE.ID is
'��ɫID';

comment on column CWM_SYS_ROLE.NAME is
'��ɫ����';

comment on column CWM_SYS_ROLE.MEMO is
'��ɫ��ע';

comment on column CWM_SYS_ROLE.TYPE is
'��ɫ����';

comment on column CWM_SYS_ROLE.STATUS is
'��ɫ״̬';

comment on column CWM_SYS_ROLE.FLG is
'�Ƿ���Ч';

/*==============================================================*/
/* Table: CWM_SYS_ROLE_ARITH                                    */
/*==============================================================*/
create table CWM_SYS_ROLE_ARITH  (
   ROLE_ID              VARCHAR2(30),
   ARITH_ID             VARCHAR2(30)
);

comment on table CWM_SYS_ROLE_ARITH is
'��ɫ�㷨�м��';

comment on column CWM_SYS_ROLE_ARITH.ROLE_ID is
'��ɫID';

comment on column CWM_SYS_ROLE_ARITH.ARITH_ID is
'�Զ����㷨ID';

/*==============================================================*/
/* Table: CWM_SYS_ROLE_FUNCTION_TBOM                            */
/*==============================================================*/
create table CWM_SYS_ROLE_FUNCTION_TBOM  (
   ROLE_ID              VARCHAR2(38)                    not null,
   FUNCTION_ID          VARCHAR2(38)                    not null,
   TBOM_ID              VARCHAR2(38)                    not null,
   constraint PK_CWM_SYS_ROLE_FUNCTION_TBOM primary key (ROLE_ID, FUNCTION_ID, TBOM_ID)
);

comment on table CWM_SYS_ROLE_FUNCTION_TBOM is
'��ɫ���ܵ��м��';

comment on column CWM_SYS_ROLE_FUNCTION_TBOM.ROLE_ID is
'��ɫID';

comment on column CWM_SYS_ROLE_FUNCTION_TBOM.FUNCTION_ID is
'���ܵ�ID';

comment on column CWM_SYS_ROLE_FUNCTION_TBOM.TBOM_ID is
'TBOM ID';

/*==============================================================*/
/* Table: CWM_SYS_ROLE_SCHEMA                                   */
/*==============================================================*/
create table CWM_SYS_ROLE_SCHEMA  (
   ROLE_ID              VARCHAR2(100)                   not null,
   SCHEMA_ID            VARCHAR2(100)                   not null
);

comment on table CWM_SYS_ROLE_SCHEMA is
'��ɫschema�м��';

comment on column CWM_SYS_ROLE_SCHEMA.ROLE_ID is
'��ɫID';

comment on column CWM_SYS_ROLE_SCHEMA.SCHEMA_ID is
'SCHEMAID';

/*==============================================================*/
/* Table: CWM_SYS_ROLE_USER                                     */
/*==============================================================*/
create table CWM_SYS_ROLE_USER  (
   ROLE_ID              VARCHAR2(100)                   not null,
   USER_ID              VARCHAR2(100)                   not null,
   constraint PK_CWM_SYS_ROLE_USER primary key (ROLE_ID, USER_ID)
);

comment on table CWM_SYS_ROLE_USER is
'��ɫ�û��м��';

comment on column CWM_SYS_ROLE_USER.ROLE_ID is
'��ɫID';

comment on column CWM_SYS_ROLE_USER.USER_ID is
'�û�ID';

/*==============================================================*/
/* Table: CWM_SYS_TOOLS                                         */
/*==============================================================*/
create table CWM_SYS_TOOLS  (
   ID                   NUMBER                          not null,
   TOOL_ICON            VARCHAR2(200),
   TOOL_NAME            VARCHAR2(100)                   not null,
   TOOL_VERSION         VARCHAR2(200),
   TOOL_DESCRIPTION     VARCHAR2(400),
   TOOL_CODE            VARCHAR2(100),
   TOOL_TYPE            VARCHAR2(50),
   GROUP_ID             VARCHAR2(200),
   constraint PK_CWM_SYS_TOOLS primary key (ID)
);

comment on table CWM_SYS_TOOLS is
'���߱�';

comment on column CWM_SYS_TOOLS.ID is
'���߱��Ψһ����';

comment on column CWM_SYS_TOOLS.TOOL_ICON is
'����ͼ��';

comment on column CWM_SYS_TOOLS.TOOL_NAME is
'������ʾ��';

comment on column CWM_SYS_TOOLS.TOOL_VERSION is
'���߰汾';

comment on column CWM_SYS_TOOLS.TOOL_DESCRIPTION is
'��������';

comment on column CWM_SYS_TOOLS.TOOL_CODE is
'���߱��';

comment on column CWM_SYS_TOOLS.TOOL_TYPE is
'��������';

comment on column CWM_SYS_TOOLS.GROUP_ID is
'������������ID';

/*==============================================================*/
/* Table: CWM_SYS_TOOLS_COLUMNS                                 */
/*==============================================================*/
create table CWM_SYS_TOOLS_COLUMNS  (
   ID                   VARCHAR2(38)                    not null,
   DISPLAY_NAME         VARCHAR2(100)                   not null,
   S_COLUMN_NAME        VARCHAR2(30)                    not null,
   IS_FOR_SEARCH        VARCHAR2(10),
   IS_NULLABLE          VARCHAR2(10),
   IS_ONLY              VARCHAR2(10),
   IS_PK                VARCHAR2(10),
   ENMU_ID              VARCHAR2(38),
   COL_TYPE             VARCHAR2(20)                    not null,
   SEQUENCE_NAME        VARCHAR2(30),
   IS_AUTOINCREMENT     VARCHAR2(10),
   MAX_LENGTH           NUMBER(38),
   MIN_LENGTH           NUMBER(38),
   IS_WRAP              VARCHAR2(10),
   CHECK_TYPE           VARCHAR2(10),
   IS_MULTI_SELECTED    VARCHAR2(10),
   DEFAULT_VALUE        VARCHAR2(30),
   DISPLAY_SHOW         VARCHAR2(5),
   EDIT_SHOW            VARCHAR2(5),
   SHOT                 NUMBER,
   INPUT_TYPE           VARCHAR2(50),
   IS_READONLY          VARCHAR2(1),
   REF_TABLE            VARCHAR2(1000),
   REF_TABLE_COLUMN_ID  VARCHAR2(1000),
   REF_TABLE_COLUMN_SHOWNAME VARCHAR2(2000),
   POP_WINDOW_FUNCTION  VARCHAR2(100),
   IS_FOR_INFOSEARCH    VARCHAR2(10),
   IS_DISPALYINFO_SHOW  VARCHAR2(10),
   IS_VIEWINFO_SHOW     VARCHAR2(10),
   constraint CWM_SYS_TOOLS_COLUMNS primary key (ID)
);

comment on table CWM_SYS_TOOLS_COLUMNS is
'�������߱�';

comment on column CWM_SYS_TOOLS_COLUMNS.ID is
'�ֶα��Ψһ����';

comment on column CWM_SYS_TOOLS_COLUMNS.DISPLAY_NAME is
'�ֶ���ʾ��';

comment on column CWM_SYS_TOOLS_COLUMNS.S_COLUMN_NAME is
'�û����е��ֶ���';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_FOR_SEARCH is
'�Ƿ���Ϊ��������';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_NULLABLE is
'�Ƿ�Ϊ��';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_ONLY is
'�Ƿ�Ψһ';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_PK is
'�Ƿ�������';

comment on column CWM_SYS_TOOLS_COLUMNS.ENMU_ID is
'ö������';

comment on column CWM_SYS_TOOLS_COLUMNS.COL_TYPE is
'��������,����queryList() �����в�ѯ���������ж�';

comment on column CWM_SYS_TOOLS_COLUMNS.SEQUENCE_NAME is
'����������';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_AUTOINCREMENT is
'�Ƿ�����';

comment on column CWM_SYS_TOOLS_COLUMNS.MAX_LENGTH is
'��󳤶�';

comment on column CWM_SYS_TOOLS_COLUMNS.MIN_LENGTH is
'��С����';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_WRAP is
'�Ƿ��Ƕ���';

comment on column CWM_SYS_TOOLS_COLUMNS.CHECK_TYPE is
'У������ 1:�Ƿ�Ψһ.2:�Ƿ�Ϊ����.3:��󳤶�.4:��С����.5:�Ƿ�Ϊ��';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_MULTI_SELECTED is
'ö������ǰ�����Ƿ��ѡ 1����ѡ';

comment on column CWM_SYS_TOOLS_COLUMNS.DEFAULT_VALUE is
'��ʼֵ';

comment on column CWM_SYS_TOOLS_COLUMNS.DISPLAY_SHOW is
'����������ʾ��־';

comment on column CWM_SYS_TOOLS_COLUMNS.EDIT_SHOW is
'�½�������༭������ʾ��־';

comment on column CWM_SYS_TOOLS_COLUMNS.SHOT is
'ҳ���ʾ˳��';

comment on column CWM_SYS_TOOLS_COLUMNS.INPUT_TYPE is
'ҳ���������� 1:�ı��� 2:���ı��� 3:�����б�:4:���ڿؼ� 5:��ѡ�� 6:��ѡ�� 7:�������� 8:����';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_READONLY is
'�Ƿ�ֻ�� 1:ֻ�� 0:��д';

comment on column CWM_SYS_TOOLS_COLUMNS.REF_TABLE is
'������';

comment on column CWM_SYS_TOOLS_COLUMNS.REF_TABLE_COLUMN_ID is
'�������ֶ�';

comment on column CWM_SYS_TOOLS_COLUMNS.REF_TABLE_COLUMN_SHOWNAME is
'�������ֶ���ʾ����';

comment on column CWM_SYS_TOOLS_COLUMNS.POP_WINDOW_FUNCTION is
'�������ڵ��õ�ҳ��js';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_FOR_INFOSEARCH is
'�Ƿ������û���ɫ��Ϣ��ѯ����';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_DISPALYINFO_SHOW is
'�Ƿ������û���ɫ��Ϣ�б���ʾ';

comment on column CWM_SYS_TOOLS_COLUMNS.IS_VIEWINFO_SHOW is
'�Ƿ������û���ɫ��Ϣ��ϸҳ����ʾ';

/*==============================================================*/
/* Table: CWM_SYS_TOOLS_GROUP                                   */
/*==============================================================*/
create table CWM_SYS_TOOLS_GROUP  (
   ID                   NUMBER                          not null,
   GROUP_NAME           VARCHAR2(100),
   GROUP_TYPE           VARCHAR2(100),
   constraint PK_CWM_SYS_TOOLS_GROUP primary key (ID)
);

comment on table CWM_SYS_TOOLS_GROUP is
'���߷����';

comment on column CWM_SYS_TOOLS_GROUP.ID is
'���߷�����Ψһ����';

comment on column CWM_SYS_TOOLS_GROUP.GROUP_NAME is
'������ʾ��';

comment on column CWM_SYS_TOOLS_GROUP.GROUP_TYPE is
'��������';

/*==============================================================*/
/* Table: CWM_SYS_USER                                          */
/*==============================================================*/
create table CWM_SYS_USER  (
   ID                   VARCHAR2(20)                    not null,
   USER_NAME            VARCHAR2(50)                    not null,
   ALL_NAME             VARCHAR2(100)                   not null,
   PASSWORD             VARCHAR2(100)                   not null,
   SEX                  VARCHAR2(5),
   PHONE                VARCHAR2(15),
   POST                 VARCHAR2(50),
   SPECIALTY            VARCHAR2(50),
   GRADE                VARCHAR2(50),
   CREATE_TIME          DATE                            not null,
   CREATE_USER          VARCHAR2(50)                    not null,
   UPDATE_TIME          DATE,
   UPDATE_USER          VARCHAR2(50),
   NOTES                VARCHAR2(1000),
   STATE                VARCHAR2(1)                     not null,
   BIRTHDAY             DATE,
   MOBILE               VARCHAR2(15),
   FLG                  VARCHAR2(1),
   DEP_ID               VARCHAR2(100),
   IS_DEL               VARCHAR2(1),
   E_MAIL               VARCHAR2(50),
   PASSWORD_SET_TIME    DATE,
   LOCK_STATE           VARCHAR2(1)                    default '0',
   LOCK_TIME            DATE,
   LOGIN_FAILURES       VARCHAR2(20)                   default '0',
   LAST_FAILURE_TIME    DATE,
   constraint PK_CWM_SYS_USER primary key (ID)
);

comment on table CWM_SYS_USER is
'�û���';

comment on column CWM_SYS_USER.ID is
'�û�ID';

comment on column CWM_SYS_USER.USER_NAME is
'�û�����';

comment on column CWM_SYS_USER.ALL_NAME is
'��ʵ����';

comment on column CWM_SYS_USER.PASSWORD is
'����';

comment on column CWM_SYS_USER.SEX is
'�Ա� 1����  0��Ů';

comment on column CWM_SYS_USER.PHONE is
'�칫�绰';

comment on column CWM_SYS_USER.POST is
'ְ��';

comment on column CWM_SYS_USER.SPECIALTY is
'רҵ';

comment on column CWM_SYS_USER.GRADE is
'�ܼ�';

comment on column CWM_SYS_USER.CREATE_TIME is
'����ʱ��';

comment on column CWM_SYS_USER.CREATE_USER is
'����������Ա';

comment on column CWM_SYS_USER.UPDATE_TIME is
'�޸�ʱ��';

comment on column CWM_SYS_USER.UPDATE_USER is
'�޸Ĳ�����Ա';

comment on column CWM_SYS_USER.NOTES is
'��ע';

comment on column CWM_SYS_USER.STATE is
'���ñ�־ 1������ 0����ֹ';

comment on column CWM_SYS_USER.BIRTHDAY is
'����������';

comment on column CWM_SYS_USER.MOBILE is
'�ֻ��绰';

comment on column CWM_SYS_USER.FLG is
'�̻���־ 1����ʾΪ�̻�����';

comment on column CWM_SYS_USER.DEP_ID is
'����ID';

comment on column CWM_SYS_USER.IS_DEL is
'�Ƿ���ɾ��';

comment on column CWM_SYS_USER.E_MAIL is
'����';

comment on column CWM_SYS_USER.PASSWORD_SET_TIME is
'��������ʱ��';

comment on column CWM_SYS_USER.LOCK_STATE is
'����״̬';

comment on column CWM_SYS_USER.LOCK_TIME is
'����ʱ��';

comment on column CWM_SYS_USER.LOGIN_FAILURES is
'��¼ʧ�ܴ���';

comment on column CWM_SYS_USER.LAST_FAILURE_TIME is
'�ϴε�¼ʧ��ʱ��';

/*==============================================================*/
/* Table: CWM_SYS_USERLOGINHISTORY                              */
/*==============================================================*/
create table CWM_SYS_USERLOGINHISTORY  (
   ID                   NUMBER                          not null,
   USER_NAME            VARCHAR2(100)                   not null,
   USER_DISPALYNAME     VARCHAR2(100)                   not null,
   USER_IP              VARCHAR2(20)                    not null,
   LOGIN_TIME           DATE                            not null,
   OP_TYPE              VARCHAR2(100)                   not null,
   OP_MESSAGE           VARCHAR2(100)                   not null,
   USER_DEPTNAME        VARCHAR2(100),
   LOGOUT_TIME          DATE,
   USER_DEPTID          NUMBER,
   constraint CWM_SYS_USERLOGINHISTORY_PK primary key (ID)
);

comment on table CWM_SYS_USERLOGINHISTORY is
'�û���¼��ʷ��';

comment on column CWM_SYS_USERLOGINHISTORY.ID is
'ID';

comment on column CWM_SYS_USERLOGINHISTORY.USER_NAME is
'�û���¼��';

comment on column CWM_SYS_USERLOGINHISTORY.USER_DISPALYNAME is
'�û���ʾ��';

comment on column CWM_SYS_USERLOGINHISTORY.USER_IP is
'�û���½ip';

comment on column CWM_SYS_USERLOGINHISTORY.LOGIN_TIME is
'��½/�˳�ʱ��';

comment on column CWM_SYS_USERLOGINHISTORY.OP_TYPE is
'������ʶ 1:��½,2:�����˳�,3:�ر�������˳�,4:sessionʧЧ�˳�,rcp1-Design Studio,rcp2-TBOM Studio,rcp3-ETL Studio,rcp4-WorkFlow Studio';

comment on column CWM_SYS_USERLOGINHISTORY.OP_MESSAGE is
'������Ϣ';

comment on column CWM_SYS_USERLOGINHISTORY.USER_DEPTNAME is
'�û���������';

comment on column CWM_SYS_USERLOGINHISTORY.LOGOUT_TIME is
'ע��ʱ��';

comment on column CWM_SYS_USERLOGINHISTORY.USER_DEPTID is
'�û�����ID';

/*==============================================================*/
/* Table: CWM_SYS_USER_COLUMNS                                  */
/*==============================================================*/
create table CWM_SYS_USER_COLUMNS  (
   ID                   VARCHAR2(38)                    not null,
   DISPLAY_NAME         VARCHAR2(100)                   not null,
   S_COLUMN_NAME        VARCHAR2(30)                    not null,
   IS_FOR_SEARCH        VARCHAR2(10),
   IS_NULLABLE          VARCHAR2(10),
   IS_ONLY              VARCHAR2(10),
   IS_PK                VARCHAR2(10),
   ENMU_ID              VARCHAR2(38),
   COL_TYPE             VARCHAR2(20)                    not null,
   SEQUENCE_NAME        VARCHAR2(30),
   IS_AUTOINCREMENT     VARCHAR2(10),
   MAX_LENGTH           NUMBER(38),
   MIN_LENGTH           NUMBER(38),
   IS_WRAP              VARCHAR2(10),
   CHECK_TYPE           VARCHAR2(10),
   IS_MULTI_SELECTED    VARCHAR2(10),
   DEFAULT_VALUE        VARCHAR2(30),
   DISPLAY_SHOW         VARCHAR2(5),
   EDIT_SHOW            VARCHAR2(5),
   SHOT                 NUMBER,
   INPUT_TYPE           VARCHAR2(50),
   IS_READONLY          VARCHAR2(1),
   REF_TABLE            VARCHAR2(1000),
   REF_TABLE_COLUMN_ID  VARCHAR2(1000),
   REF_TABLE_COLUMN_SHOWNAME VARCHAR2(2000),
   POP_WINDOW_FUNCTION  VARCHAR2(100),
   IS_FOR_INFOSEARCH    VARCHAR2(10),
   IS_DISPALYINFO_SHOW  VARCHAR2(10),
   IS_VIEWINFO_SHOW     VARCHAR2(10),
   constraint PK_CWM_SYS_USER_COLUMNS primary key (ID)
);

comment on table CWM_SYS_USER_COLUMNS is
'�����û���';

comment on column CWM_SYS_USER_COLUMNS.ID is
'�ֶα��Ψһ����';

comment on column CWM_SYS_USER_COLUMNS.DISPLAY_NAME is
'�ֶ���ʾ��';

comment on column CWM_SYS_USER_COLUMNS.S_COLUMN_NAME is
'�û����е��ֶ���';

comment on column CWM_SYS_USER_COLUMNS.IS_FOR_SEARCH is
'�Ƿ���Ϊ��������';

comment on column CWM_SYS_USER_COLUMNS.IS_NULLABLE is
'�Ƿ�Ϊ��';

comment on column CWM_SYS_USER_COLUMNS.IS_ONLY is
'�Ƿ�Ψһ';

comment on column CWM_SYS_USER_COLUMNS.IS_PK is
'�Ƿ�������';

comment on column CWM_SYS_USER_COLUMNS.ENMU_ID is
'ö������';

comment on column CWM_SYS_USER_COLUMNS.COL_TYPE is
'��������,����queryList() �����в�ѯ���������ж�';

comment on column CWM_SYS_USER_COLUMNS.SEQUENCE_NAME is
'����������';

comment on column CWM_SYS_USER_COLUMNS.IS_AUTOINCREMENT is
'�Ƿ�����';

comment on column CWM_SYS_USER_COLUMNS.MAX_LENGTH is
'��󳤶�';

comment on column CWM_SYS_USER_COLUMNS.MIN_LENGTH is
'��С����';

comment on column CWM_SYS_USER_COLUMNS.IS_WRAP is
'�Ƿ��Ƕ���';

comment on column CWM_SYS_USER_COLUMNS.CHECK_TYPE is
'У������ 1:�Ƿ�Ψһ.2:�Ƿ�Ϊ����.3:��󳤶�.4:��С����.5:�Ƿ�Ϊ��';

comment on column CWM_SYS_USER_COLUMNS.IS_MULTI_SELECTED is
'ö������ǰ�����Ƿ��ѡ 1����ѡ';

comment on column CWM_SYS_USER_COLUMNS.DEFAULT_VALUE is
'��ʼֵ';

comment on column CWM_SYS_USER_COLUMNS.DISPLAY_SHOW is
'����������ʾ��־';

comment on column CWM_SYS_USER_COLUMNS.EDIT_SHOW is
'�½�������༭������ʾ��־';

comment on column CWM_SYS_USER_COLUMNS.SHOT is
'ҳ���ʾ˳��';

comment on column CWM_SYS_USER_COLUMNS.INPUT_TYPE is
'ҳ���������� 1:�ı��� 2:���ı��� 3:�����б�:4:���ڿؼ� 5:��ѡ�� 6:��ѡ�� 7:�������� 8:����';

comment on column CWM_SYS_USER_COLUMNS.IS_READONLY is
'�Ƿ�ֻ�� 1:ֻ�� 0:��д';

comment on column CWM_SYS_USER_COLUMNS.REF_TABLE is
'������';

comment on column CWM_SYS_USER_COLUMNS.REF_TABLE_COLUMN_ID is
'�������ֶ�';

comment on column CWM_SYS_USER_COLUMNS.REF_TABLE_COLUMN_SHOWNAME is
'�������ֶ���ʾ����';

comment on column CWM_SYS_USER_COLUMNS.POP_WINDOW_FUNCTION is
'�������ڵ��õ�ҳ��js';

comment on column CWM_SYS_USER_COLUMNS.IS_FOR_INFOSEARCH is
'�Ƿ������û���ɫ��Ϣ��ѯ����';

comment on column CWM_SYS_USER_COLUMNS.IS_DISPALYINFO_SHOW is
'�Ƿ������û���ɫ��Ϣ�б���ʾ';

comment on column CWM_SYS_USER_COLUMNS.IS_VIEWINFO_SHOW is
'�Ƿ������û���ɫ��Ϣ��ϸҳ����ʾ';

/*==============================================================*/
/* Table: CWM_SYS_USER_DEPT                                     */
/*==============================================================*/
create table CWM_SYS_USER_DEPT  (
   USER_ID              VARCHAR2(20)                    not null,
   DEPT_ID              VARCHAR2(20)                    not null
);

comment on table CWM_SYS_USER_DEPT is
'�û������м��';

comment on column CWM_SYS_USER_DEPT.USER_ID is
'�û�ID';

comment on column CWM_SYS_USER_DEPT.DEPT_ID is
'����ID';

/*==============================================================*/
/* Table: CWM_SYS_USER_ENUM                                     */
/*==============================================================*/
create table CWM_SYS_USER_ENUM  (
   ENUM_ID              VARCHAR2(38)                    not null,
   VALUE                VARCHAR2(30)                    not null,
   DISPLAY_VALUE        VARCHAR2(100)                   not null,
   IMAGE_URL            VARCHAR2(100),
   DESCRIPTION          VARCHAR2(1000),
   ID                   VARCHAR2(38)                    not null,
   constraint PK_CWM_SYS_USER_ENUM primary key (ID)
);

comment on table CWM_SYS_USER_ENUM is
'�û�ö���м��';

comment on column CWM_SYS_USER_ENUM.ENUM_ID is
'ö��ID';

comment on column CWM_SYS_USER_ENUM.VALUE is
'ö����ʵֵ';

comment on column CWM_SYS_USER_ENUM.DISPLAY_VALUE is
'ö����ʾֵ';

comment on column CWM_SYS_USER_ENUM.IMAGE_URL is
'ͼ��URL';

comment on column CWM_SYS_USER_ENUM.DESCRIPTION is
'�û�ö������';

comment on column CWM_SYS_USER_ENUM.ID is
'ID';

/*==============================================================*/
/* Table: CWM_TIME_INFO                                         */
/*==============================================================*/
create table CWM_TIME_INFO  (
   ID                   NUMBER                          not null,
   ISSTARTTIMEBACK      VARCHAR2(100),
   BACKNAME             VARCHAR2(200),
   ISBACKDATA           VARCHAR2(1),
   ISDAYBACK            VARCHAR2(1),
   DAYBACKTIME          VARCHAR2(20),
   ISMONTHBACK          VARCHAR2(1),
   MONTHBACKDAY         VARCHAR2(10),
   MONTHBACKTIME        VARCHAR2(20),
   ISWEEKBACK           VARCHAR2(1),
   WEEKBACKDAY          VARCHAR2(10),
   WEEKBACKTIME         VARCHAR2(20),
   BACKTYPE             VARCHAR2(1)
);

comment on table CWM_TIME_INFO is
'ϵͳ���ݲ��Ա�';

comment on column CWM_TIME_INFO.ID is
'ID';

comment on column CWM_TIME_INFO.ISSTARTTIMEBACK is
'�Ƿ����ö�ʱ����';

comment on column CWM_TIME_INFO.BACKNAME is
'��������';

comment on column CWM_TIME_INFO.ISBACKDATA is
'�Ƿ񱸷�����';

comment on column CWM_TIME_INFO.ISDAYBACK is
'�Ƿ�ÿ�챸��';

comment on column CWM_TIME_INFO.DAYBACKTIME is
'������';

comment on column CWM_TIME_INFO.ISMONTHBACK is
'�Ƿ�ÿ�±���';

comment on column CWM_TIME_INFO.MONTHBACKDAY is
'ÿ�±��ݵ���һ�죨1����31��';

comment on column CWM_TIME_INFO.MONTHBACKTIME is
'ÿ�±��ݾ���ʱ��';

comment on column CWM_TIME_INFO.ISWEEKBACK is
'�Ƿ�ÿ�ܱ���';

comment on column CWM_TIME_INFO.WEEKBACKDAY is
'ÿ�ܱ��ݵ���һ�죨��һ�����գ�';

comment on column CWM_TIME_INFO.WEEKBACKTIME is
'ÿ�ܱ��ݾ���ʱ��';

comment on column CWM_TIME_INFO.BACKTYPE is
'��������';

/*==============================================================*/
/* Table: CWM_USER_TOOL                                         */
/*==============================================================*/
create table CWM_USER_TOOL  (
   ID                   NUMBER                          not null,
   USER_ID              VARCHAR2(100)                   not null,
   TOOL_ID              VARCHAR2(100)                   not null,
   TOOL_PATH            VARCHAR2(100),
   constraint PK_CWM_USER_TOOL primary key (ID)
);

comment on table CWM_USER_TOOL is
'�û����߿��м��';

comment on column CWM_USER_TOOL.ID is
'ID';

comment on column CWM_USER_TOOL.USER_ID is
'�û�ID';

comment on column CWM_USER_TOOL.TOOL_ID is
'����ID';

comment on column CWM_USER_TOOL.TOOL_PATH is
'�����ڱ���·��';

/*==============================================================*/
/* Table: CWM_USER_VIEW                                         */
/*==============================================================*/
create table CWM_USER_VIEW  (
   ID                   varchar2(38)                    not null,
   USER_ID              varchar2(38),
   PORTAL_ID            varchar2(38),
   COL_NUM              number,
   ROW_NUM              number,
   constraint PK_CWM_USER_VIEW primary key (ID)
);

comment on table CWM_USER_VIEW is
'�û���ҳ��������';

comment on column CWM_USER_VIEW.ID is
'ID';

comment on column CWM_USER_VIEW.USER_ID is
'�����û�ID';

comment on column CWM_USER_VIEW.PORTAL_ID is
'����PORTALID';

comment on column CWM_USER_VIEW.COL_NUM is
'������';

comment on column CWM_USER_VIEW.ROW_NUM is
'������';

/*==============================================================*/
/* Table: FREEMARK_TEMPLATE                                     */
/*==============================================================*/
create table FREEMARK_TEMPLATE  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(200),
   ALIAS                VARCHAR2(200),
   TYPE                 VARCHAR2(200),
   MACRO_ALIAS          VARCHAR2(200),
   HTML                 CLOB,
   CANEDIT              NUMBER,
   "DESCR"               VARCHAR2(400),
   constraint PK_FREEMARK_TEMPLATE primary key (ID)
);

comment on table FREEMARK_TEMPLATE is
'����freemark��д��ģ�壨�����Ǳ�Ҳ�����Ǳ��';

comment on column FREEMARK_TEMPLATE.ID is
'ID';

comment on column FREEMARK_TEMPLATE.NAME is
'����';

comment on column FREEMARK_TEMPLATE.ALIAS is
'����';

comment on column FREEMARK_TEMPLATE.TYPE is
'ģ������';

comment on column FREEMARK_TEMPLATE.MACRO_ALIAS is
'������ģ�����';

comment on column FREEMARK_TEMPLATE.HTML is
'HTML';

comment on column FREEMARK_TEMPLATE.CANEDIT is
'�Ƿ���Ա༭';

comment on column FREEMARK_TEMPLATE."DESCR" is
'����';

/*==============================================================*/
/* Table: MODEL_BTN_TYPE                                        */
/*==============================================================*/
create table MODEL_BTN_TYPE  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(100),
   ISSYSTEM             NUMBER,
   CODE                 VARCHAR2(100),
   constraint PK_MODEL_BTN_TYPE primary key (ID)
);

comment on table MODEL_BTN_TYPE is
'ģ�Ͱ�ť����';

comment on column MODEL_BTN_TYPE.ID is
'ID';

comment on column MODEL_BTN_TYPE.NAME is
'��ť����';

comment on column MODEL_BTN_TYPE.ISSYSTEM is
'�Ƿ�ϵͳ�Դ�';

/*==============================================================*/
/* Table: MODEL_COLUMN_RULE                                     */
/*==============================================================*/
create table MODEL_COLUMN_RULE  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(100),
   REGULATION           VARCHAR2(400),
   "DESCR"               VARCHAR2(400),
   ERRINFO              VARCHAR2(400),
   constraint PK_MODEL_COLUMN_RULE primary key (ID)
);

comment on table MODEL_COLUMN_RULE is
'ģ���ֶ���֤����';

comment on column MODEL_COLUMN_RULE.ID is
'ID';

comment on column MODEL_COLUMN_RULE.NAME is
'����';

comment on column MODEL_COLUMN_RULE.REGULATION is
'������ʽ';

comment on column MODEL_COLUMN_RULE."DESCR" is
'�ֶ���֤����';

comment on column MODEL_COLUMN_RULE.ERRINFO is
'��֤����ʱ��ʾ��Ϣ';

/*==============================================================*/
/* Table: MODEL_FORM_VIEW                                       */
/*==============================================================*/
create table MODEL_FORM_VIEW  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(100),
   "DESCR"               VARCHAR2(100),
   CATEGORYID           NUMBER,
   TITLE                VARCHAR2(100),
   HTML                 CLOB,
   TEMPLATE             CLOB,
   VERSIONNO            NUMBER,
   ISDEFAULT            NUMBER,
   ISPUBLISHED          NUMBER,
   PUBLISHEDBY          VARCHAR2(100),
   PUBLISHTIME          DATE,
   CREATEBY             VARCHAR2(100),
   CREATETIME           DATE,
   DESIGNTYPE           NUMBER,
   MODELID              NUMBER,
   TEMPLATEID           NUMBER,
   constraint PK_MODEL_FORM_VIEW primary key (ID)
);

comment on table MODEL_FORM_VIEW is
'ģ�ͱ���ʽ����';

comment on column MODEL_FORM_VIEW.ID is
'ID';

comment on column MODEL_FORM_VIEW.NAME is
'����';

comment on column MODEL_FORM_VIEW."DESCR" is
'����';

comment on column MODEL_FORM_VIEW.CATEGORYID is
'������';

comment on column MODEL_FORM_VIEW.TITLE is
'����';

comment on column MODEL_FORM_VIEW.HTML is
'HTML';

comment on column MODEL_FORM_VIEW.TEMPLATE is
'FREEMARKERģ��';

comment on column MODEL_FORM_VIEW.VERSIONNO is
'�汾';

comment on column MODEL_FORM_VIEW.ISDEFAULT is
'�Ƿ�Ĭ��';

comment on column MODEL_FORM_VIEW.ISPUBLISHED is
'�Ƿ��Ѿ�����';

comment on column MODEL_FORM_VIEW.PUBLISHEDBY is
'������';

comment on column MODEL_FORM_VIEW.PUBLISHTIME is
'����ʱ��';

comment on column MODEL_FORM_VIEW.CREATEBY is
'������';

comment on column MODEL_FORM_VIEW.CREATETIME is
'����ʱ��';

comment on column MODEL_FORM_VIEW.MODELID is
'����ģ��ID';

comment on column MODEL_FORM_VIEW.TEMPLATEID is
'����ģ��ID';

/*==============================================================*/
/* Table: MODEL_GRID_VIEW                                       */
/*==============================================================*/
  CREATE TABLE MODEL_GRID_VIEW
(
  ID INTEGER PRIMARY KEY NOT NULL,
  NAME VARCHAR2(100),
  ALIAS VARCHAR2(100),
  STYLE INTEGER,
  NEEDPAGE INTEGER,
  PAGESIZE INTEGER,
  DISPLAYFIELD CLOB,
  ADDFIELD CLOB,
  EDITFIELD CLOB,
  BTNS CLOB,
  DETAILFIELD CLOB,
  MODELID INTEGER,
  TEMPLATEID INTEGER,
  VERSIONNO INTEGER,
  ISDEFAULT INTEGER,
  QUERYFIELD CLOB,
  EXTENDCLASS VARCHAR2(100),
  constraint PK_MODEL_GRID_VIEW primary key (ID)
);

/*==============================================================*/
/* Table: MODEL_QUERY                                           */
/*==============================================================*/
create table MODEL_QUERY  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(100),
   CODE                 VARCHAR2(100),
   MODELID              NUMBER,
   MODELNAME            VARCHAR2(100),
   NEEDPAGE             NUMBER,
   CONDITIONFIELD       CLOB,
   RESULTFIELD          CLOB,
   SORTFIELD            CLOB,
   constraint PK_MODEL_QUERY primary key (ID)
);

comment on table MODEL_QUERY is
'ģ��ͨ�ò�ѯ';

comment on column MODEL_QUERY.ID is
'ID';

comment on column MODEL_QUERY.NAME is
'����';

comment on column MODEL_QUERY.CODE is
'���';

comment on column MODEL_QUERY.MODELID is
'����ģ��';

comment on column MODEL_QUERY.MODELNAME is
'ģ������';

comment on column MODEL_QUERY.NEEDPAGE is
'�Ƿ���Ҫ��ҳ';

comment on column MODEL_QUERY.CONDITIONFIELD is
'�����ֶ�����';

comment on column MODEL_QUERY.RESULTFIELD is
'���ؽ������';

comment on column MODEL_QUERY.SORTFIELD is
'�����ֶ�����';

drop table MODEL_BTN_INSTANCE cascade constraints;

/*==============================================================*/
/* Table: MODEL_BTN_INSTANCE                                    */
/*==============================================================*/
drop table MODEL_BTN_INSTANCE cascade constraints;

/*==============================================================*/
/* Table: MODEL_BTN_INSTANCE                                    */
/*==============================================================*/
create table MODEL_BTN_INSTANCE  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(100),
   BTN_TYPE_ID          NUMBER,
   FORM_VIEW_ID         NUMBER,
   JSPATH               VARCHAR(100),
   constraint PK_MODEL_BTN_INSTANCE primary key (ID)
);

comment on table MODEL_BTN_INSTANCE is
'模型按钮实例';

comment on column MODEL_BTN_INSTANCE.ID is
'ID';

comment on column MODEL_BTN_INSTANCE.NAME is
'按钮名称';

comment on column MODEL_BTN_INSTANCE.BTN_TYPE_ID is
'是否系统自带';


CREATE TABLE CWM_ODS_FILE
(
    ID INTEGER PRIMARY KEY NOT NULL,
    CWM_FILE_ID INTEGER NOT NULL,
    FOLDER_PATH VARCHAR2(4000)
);
CREATE TABLE CWM_HADOOP_FILE
(
    ID INTEGER PRIMARY KEY NOT NULL,
    CWM_FILE_ID INTEGER NOT NULL,
    STATE INTEGER DEFAULT 0

);


CREATE TABLE CWM_SYS_USER_LINK
(
    ID INTEGER PRIMARY KEY NOT NULL,
    USER_ID INTEGER NOT NULL,
    FUNCTION_ID INTEGER NOT NULL,
    LINK_ORDER INTEGER NOT NULL
);

CREATE TABLE CWM_SYS_USER_PORTAL
(
    ID INTEGER PRIMARY KEY NOT NULL,
    USER_ID INTEGER NOT NULL,
    PORTAL_ID INTEGER NOT NULL,
    PORTAL_ORDER INTEGER NOT NULL
);

CREATE TABLE CWM_SYS_ROLE_PORTAL
(
   ID INTEGER PRIMARY KEY NOT NULL,
    ROLE_ID VARCHAR2(100) NOT NULL,
    PORTAL_ID VARCHAR2(100) NOT NULL
);

--检查模型设置表
CREATE TABLE CWM_SYS_CHECKMODELSET
(
    ID VARCHAR2(4000) PRIMARY KEY NOT NULL,
    CHECK_TYPE NUMBER NOT NULL,
    MODEL_ID VARCHAR2(4000) NOT NULL,
    COLUMN_ID VARCHAR2(4000) NOT NULL
);

-- 报告生成相关表

-- 报告处理类
CREATE TABLE CWM_DOC_HANDLER
(
    id NUMBER PRIMARY KEY,
    show_name VARCHAR2(38) NOT NULL,
    bean_name VARCHAR2(38) NOT NULL
);
COMMENT ON TABLE CWM_DOC_HANDLER IS '报告生成处理类';

-- 处理类适用范围
CREATE TABLE CWM_DOC_COLUMN_SCOPE
(
    ID NUMBER PRIMARY KEY,
    BELONG_HANDLER NUMBER,
    COLUMN_TYPE VARCHAR2(38) NOT NULL,
    CONSTRAINT FK_DOC_HANDLER FOREIGN KEY (BELONG_HANDLER) REFERENCES CWM_DOC_HANDLER (ID)
);

-- 删除原来的报告结构
DROP TABLE ORIENTTDM.CWM_REPORTS;
DROP TABLE ORIENTTDM.CWM_DOC_REPORT_ITEMS;
DROP TABLE ORIENTTDM.CWM_REOPRT_BOOKMARK;
DROP SEQUENCE SEQ_CWM_REPORTS;
DROP SEQUENCE SEQ_CWM_REPORT_ITEMS;
DROP SEQUENCE SEQ_CWM_REOPRT_BOOKMARK;

-- new doc reports table
CREATE TABLE CWM_DOC_REPORTS
(
    ID NUMBER PRIMARY KEY,
    REPORT_NAME VARCHAR2(38) NOT NULL,
    MODEL_ID VARCHAR2(38),
    IS_VIEW NUMBER DEFAULT 0,
    FILE_PATH VARCHAR2(100) NOT NULL,
    CREATE_USER VARCHAR2(38) NOT NULL,
    CREATE_TIME TIMESTAMP NOT NULL
);


CREATE TABLE CWM_DOC_REPORT_ITEMS
(
    ID NUMBER PRIMARY KEY,
    BELONG_REPORT NUMBER,
    BOOKMARK_NAME VARCHAR2(100) NOT NULL,
    DOC_HANDLER_ID NUMBER NOT NULL
);

