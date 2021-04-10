/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016/3/10 星期四 11:12:32                       */
/*==============================================================*/


drop table CWM_DYNAMIC_RELATION cascade constraints;

drop table CWM_ETL_JOB cascade constraints;

drop table CWM_ETL_LOG cascade constraints;

drop table CWM_ETL_SCRIPT cascade constraints;

drop table CWM_ETL_TRANSLATOR cascade constraints;

drop table CWM_PARTITION_REMARK cascade constraints;

/*==============================================================*/
/* Table: CWM_DYNAMIC_RELATION                                  */
/*==============================================================*/
create table CWM_DYNAMIC_RELATION  (
   ID                   VARCHAR2(50),
   FILE_COL             VARCHAR2(50),
   DB_COL               VARCHAR2(10),
   COL_REMARK           VARCHAR2(200),
   TABLE_NAME           VARCHAR2(50),
   GK_ID                VARCHAR2(50),
   JOB_ID               VARCHAR2(50)
);

comment on table CWM_DYNAMIC_RELATION is
'动态模型关系表';

comment on column CWM_DYNAMIC_RELATION.ID is
'主键';

comment on column CWM_DYNAMIC_RELATION.FILE_COL is
'数据文件列名';

comment on column CWM_DYNAMIC_RELATION.DB_COL is
'列名所对应的动态表的字段';

comment on column CWM_DYNAMIC_RELATION.COL_REMARK is
'列名备注信息';

comment on column CWM_DYNAMIC_RELATION.TABLE_NAME is
'动态数据类对应的数据类';

comment on column CWM_DYNAMIC_RELATION.GK_ID is
'动态数据类对应数据类 的 记录的ID';

comment on column CWM_DYNAMIC_RELATION.JOB_ID is
'ID';

/*==============================================================*/
/* Table: CWM_ETL_JOB                                           */
/*==============================================================*/
create table CWM_ETL_JOB  (
   ID                   VARCHAR2(38)                    not null,
   USER_NAME            VARCHAR2(50),
   JOB_TIME             DATE,
   STATUS               VARCHAR2(50),
   DDL                  CLOB,
   DML                  CLOB,
   LOADSQL              CLOB,
   constraint PK_CWM_ETL_JOB primary key (ID)
);

comment on table CWM_ETL_JOB is
'ETL作业';

comment on column CWM_ETL_JOB.ID is
'数据导入JOB ID';

comment on column CWM_ETL_JOB.USER_NAME is
'导入用户名';

comment on column CWM_ETL_JOB.JOB_TIME is
'数据导入时间';

comment on column CWM_ETL_JOB.STATUS is
'数据导入状态(1:成功)';

comment on column CWM_ETL_JOB.DDL is
'创建外部表SQL脚本';

comment on column CWM_ETL_JOB.DML is
'查询外部表SQL脚本';

comment on column CWM_ETL_JOB.LOADSQL is
'loadsql';

/*==============================================================*/
/* Table: CWM_ETL_LOG                                           */
/*==============================================================*/
create table CWM_ETL_LOG  (
   LOG_INFO             VARCHAR2(600),
   USER_ID              VARCHAR2(38),
   START_TIME           DATE,
   TABLE_ID             VARCHAR2(38),
   STATUS               VARCHAR2(50),
   DATA_AMOUNT          VARCHAR2(50),
   FILE_NAME            VARCHAR2(200),
   TABLE_DISNAME        VARCHAR2(100),
   END_TIME             DATE,
   RIGHT_DATA           VARCHAR2(30),
   WRONG_DATA           VARCHAR2(30),
   TIME1                DATE,
   TIME2                DATE,
   IS_DELETE            NUMBER(1)                      default 1,
   JOB_ID               VARCHAR2(38),
   JOB_RESULT           VARCHAR2(1)                    default '1',
   TABLE_NAME           VARCHAR2(100),
   FILE_SIZE            VARCHAR2(50),
   LOGDDL               CLOB
);

comment on table CWM_ETL_LOG is
'ETL导入日志';

comment on column CWM_ETL_LOG.LOG_INFO is
'日志描述';

comment on column CWM_ETL_LOG.USER_ID is
'导入数据的用户ID';

comment on column CWM_ETL_LOG.START_TIME is
'开始时间';

comment on column CWM_ETL_LOG.TABLE_ID is
'导入模型的ID';

comment on column CWM_ETL_LOG.STATUS is
'导入结果';

comment on column CWM_ETL_LOG.DATA_AMOUNT is
'导入数据总量';

comment on column CWM_ETL_LOG.FILE_NAME is
'导入文件名称';

comment on column CWM_ETL_LOG.TABLE_DISNAME is
'导入模型显示名称';

comment on column CWM_ETL_LOG.END_TIME is
'结束时间';

comment on column CWM_ETL_LOG.RIGHT_DATA is
'导入正确数据数量';

comment on column CWM_ETL_LOG.WRONG_DATA is
'导入错误数据数量';

comment on column CWM_ETL_LOG.TIME1 is
'开始时间';

comment on column CWM_ETL_LOG.TIME2 is
'结束时间';

comment on column CWM_ETL_LOG.IS_DELETE is
'是否已经删除';

comment on column CWM_ETL_LOG.JOB_ID is
'作业ID';

comment on column CWM_ETL_LOG.JOB_RESULT is
'作业结果';

comment on column CWM_ETL_LOG.TABLE_NAME is
'导入模型名称';

comment on column CWM_ETL_LOG.FILE_SIZE is
'导入文件大小';

comment on column CWM_ETL_LOG.LOGDDL is
'记录操作SQL语句';

/*==============================================================*/
/* Table: CWM_ETL_SCRIPT                                        */
/*==============================================================*/
create table CWM_ETL_SCRIPT  (
   ID                   VARCHAR2(20)                    not null,
   SCRIPTNAME           VARCHAR2(100),
   FILENAME             VARCHAR2(100),
   FILETYPE             VARCHAR2(10),
   DATAINDEX            VARCHAR2(10),
   LINESPLIT            VARCHAR2(60),
   USERNAME             VARCHAR2(50),
   ERRORSOLVE           VARCHAR2(10),
   JOBTYPE              VARCHAR2(20),
   FILEPATH             VARCHAR2(200),
   FILELENGTH           VARCHAR2(1000),
   FILELASTMOD          VARCHAR2(1000),
   JOBTIME              VARCHAR2(40),
   SRCCOLUMN            VARCHAR2(1500),
   IMPORT_TYPE          VARCHAR2(20),
   constraint PK_CWM_ETL_SCRIPT primary key (ID)
);

comment on table CWM_ETL_SCRIPT is
'ETL导入脚本';

comment on column CWM_ETL_SCRIPT.ID is
'主键ID';

comment on column CWM_ETL_SCRIPT.SCRIPTNAME is
'脚本名称';

comment on column CWM_ETL_SCRIPT.FILENAME is
'导入文件名称';

comment on column CWM_ETL_SCRIPT.FILETYPE is
'导入文件类型';

comment on column CWM_ETL_SCRIPT.DATAINDEX is
'数据起始行   -1 代表使用外部转换器';

comment on column CWM_ETL_SCRIPT.LINESPLIT is
'行分隔符   或外部转换器名称';

comment on column CWM_ETL_SCRIPT.USERNAME is
'用户ID';

comment on column CWM_ETL_SCRIPT.ERRORSOLVE is
'错误处理方式';

comment on column CWM_ETL_SCRIPT.JOBTYPE is
'ETL处理方式：立即导入或延时导入';

comment on column CWM_ETL_SCRIPT.FILEPATH is
'导入文件路径';

comment on column CWM_ETL_SCRIPT.FILELENGTH is
'导入文件大小';

comment on column CWM_ETL_SCRIPT.FILELASTMOD is
'导入文件最后修改时间';

comment on column CWM_ETL_SCRIPT.JOBTIME is
'导入时间';

comment on column CWM_ETL_SCRIPT.SRCCOLUMN is
'数据源列名';

comment on column CWM_ETL_SCRIPT.IMPORT_TYPE is
'导入类型';

/*==============================================================*/
/* Table: CWM_ETL_TRANSLATOR                                    */
/*==============================================================*/
create table CWM_ETL_TRANSLATOR  (
   ID                   VARCHAR2(20)                    not null,
   TABLENAME            VARCHAR2(50),
   TABLEID              VARCHAR2(20),
   SCRIPTID             VARCHAR2(20),
   TABLECOLUMN          VARCHAR2(4000),
   TABLESYSNAME         VARCHAR2(50),
   TRANSLATOR           CLOB,
   constraint PK_CWM_ETL_TRANSLATOR primary key (ID)
);

comment on table CWM_ETL_TRANSLATOR is
'ETL导入转化表';

comment on column CWM_ETL_TRANSLATOR.ID is
'表主键ID';

comment on column CWM_ETL_TRANSLATOR.TABLENAME is
'导入表ID+显示名称';

comment on column CWM_ETL_TRANSLATOR.TABLEID is
'导入表真实名称';

comment on column CWM_ETL_TRANSLATOR.SCRIPTID is
'数据导入脚本ID';

comment on column CWM_ETL_TRANSLATOR.TABLECOLUMN is
'导入表字段名称';

comment on column CWM_ETL_TRANSLATOR.TABLESYSNAME is
'模型SName';

comment on column CWM_ETL_TRANSLATOR.TRANSLATOR is
'转化器';

/*==============================================================*/
/* Table: CWM_PARTITION_REMARK                                  */
/*==============================================================*/
create table CWM_PARTITION_REMARK  (
   TABLE_NAME           VARCHAR2(50),
   GK_ID                VARCHAR2(50),
   IMP_TIME             VARCHAR2(50),
   JOB_ID               VARCHAR2(50),
   REMARK               VARCHAR2(200),
   COLS                 VARCHAR2(4000)
);

comment on table CWM_PARTITION_REMARK is
'ETL导入动态表描述';

comment on column CWM_PARTITION_REMARK.TABLE_NAME is
'动态表(被导入数据表)名称';

comment on column CWM_PARTITION_REMARK.GK_ID is
'动态数据类对应数据类 的 记录的ID';

comment on column CWM_PARTITION_REMARK.IMP_TIME is
'导入时间';

comment on column CWM_PARTITION_REMARK.JOB_ID is
'JOB_ID';

comment on column CWM_PARTITION_REMARK.REMARK is
'导入备注';

comment on column CWM_PARTITION_REMARK.COLS is
'文件中的数据列名';

