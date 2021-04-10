/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016/3/10 ������ 11:12:32                       */
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
'��̬ģ�͹�ϵ��';

comment on column CWM_DYNAMIC_RELATION.ID is
'����';

comment on column CWM_DYNAMIC_RELATION.FILE_COL is
'�����ļ�����';

comment on column CWM_DYNAMIC_RELATION.DB_COL is
'��������Ӧ�Ķ�̬����ֶ�';

comment on column CWM_DYNAMIC_RELATION.COL_REMARK is
'������ע��Ϣ';

comment on column CWM_DYNAMIC_RELATION.TABLE_NAME is
'��̬�������Ӧ��������';

comment on column CWM_DYNAMIC_RELATION.GK_ID is
'��̬�������Ӧ������ �� ��¼��ID';

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
'ETL��ҵ';

comment on column CWM_ETL_JOB.ID is
'���ݵ���JOB ID';

comment on column CWM_ETL_JOB.USER_NAME is
'�����û���';

comment on column CWM_ETL_JOB.JOB_TIME is
'���ݵ���ʱ��';

comment on column CWM_ETL_JOB.STATUS is
'���ݵ���״̬(1:�ɹ�)';

comment on column CWM_ETL_JOB.DDL is
'�����ⲿ��SQL�ű�';

comment on column CWM_ETL_JOB.DML is
'��ѯ�ⲿ��SQL�ű�';

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
'ETL������־';

comment on column CWM_ETL_LOG.LOG_INFO is
'��־����';

comment on column CWM_ETL_LOG.USER_ID is
'�������ݵ��û�ID';

comment on column CWM_ETL_LOG.START_TIME is
'��ʼʱ��';

comment on column CWM_ETL_LOG.TABLE_ID is
'����ģ�͵�ID';

comment on column CWM_ETL_LOG.STATUS is
'������';

comment on column CWM_ETL_LOG.DATA_AMOUNT is
'������������';

comment on column CWM_ETL_LOG.FILE_NAME is
'�����ļ�����';

comment on column CWM_ETL_LOG.TABLE_DISNAME is
'����ģ����ʾ����';

comment on column CWM_ETL_LOG.END_TIME is
'����ʱ��';

comment on column CWM_ETL_LOG.RIGHT_DATA is
'������ȷ��������';

comment on column CWM_ETL_LOG.WRONG_DATA is
'���������������';

comment on column CWM_ETL_LOG.TIME1 is
'��ʼʱ��';

comment on column CWM_ETL_LOG.TIME2 is
'����ʱ��';

comment on column CWM_ETL_LOG.IS_DELETE is
'�Ƿ��Ѿ�ɾ��';

comment on column CWM_ETL_LOG.JOB_ID is
'��ҵID';

comment on column CWM_ETL_LOG.JOB_RESULT is
'��ҵ���';

comment on column CWM_ETL_LOG.TABLE_NAME is
'����ģ������';

comment on column CWM_ETL_LOG.FILE_SIZE is
'�����ļ���С';

comment on column CWM_ETL_LOG.LOGDDL is
'��¼����SQL���';

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
'ETL����ű�';

comment on column CWM_ETL_SCRIPT.ID is
'����ID';

comment on column CWM_ETL_SCRIPT.SCRIPTNAME is
'�ű�����';

comment on column CWM_ETL_SCRIPT.FILENAME is
'�����ļ�����';

comment on column CWM_ETL_SCRIPT.FILETYPE is
'�����ļ�����';

comment on column CWM_ETL_SCRIPT.DATAINDEX is
'������ʼ��   -1 ����ʹ���ⲿת����';

comment on column CWM_ETL_SCRIPT.LINESPLIT is
'�зָ���   ���ⲿת��������';

comment on column CWM_ETL_SCRIPT.USERNAME is
'�û�ID';

comment on column CWM_ETL_SCRIPT.ERRORSOLVE is
'������ʽ';

comment on column CWM_ETL_SCRIPT.JOBTYPE is
'ETL����ʽ�������������ʱ����';

comment on column CWM_ETL_SCRIPT.FILEPATH is
'�����ļ�·��';

comment on column CWM_ETL_SCRIPT.FILELENGTH is
'�����ļ���С';

comment on column CWM_ETL_SCRIPT.FILELASTMOD is
'�����ļ�����޸�ʱ��';

comment on column CWM_ETL_SCRIPT.JOBTIME is
'����ʱ��';

comment on column CWM_ETL_SCRIPT.SRCCOLUMN is
'����Դ����';

comment on column CWM_ETL_SCRIPT.IMPORT_TYPE is
'��������';

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
'ETL����ת����';

comment on column CWM_ETL_TRANSLATOR.ID is
'������ID';

comment on column CWM_ETL_TRANSLATOR.TABLENAME is
'�����ID+��ʾ����';

comment on column CWM_ETL_TRANSLATOR.TABLEID is
'�������ʵ����';

comment on column CWM_ETL_TRANSLATOR.SCRIPTID is
'���ݵ���ű�ID';

comment on column CWM_ETL_TRANSLATOR.TABLECOLUMN is
'������ֶ�����';

comment on column CWM_ETL_TRANSLATOR.TABLESYSNAME is
'ģ��SName';

comment on column CWM_ETL_TRANSLATOR.TRANSLATOR is
'ת����';

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
'ETL���붯̬������';

comment on column CWM_PARTITION_REMARK.TABLE_NAME is
'��̬��(���������ݱ�)����';

comment on column CWM_PARTITION_REMARK.GK_ID is
'��̬�������Ӧ������ �� ��¼��ID';

comment on column CWM_PARTITION_REMARK.IMP_TIME is
'����ʱ��';

comment on column CWM_PARTITION_REMARK.JOB_ID is
'JOB_ID';

comment on column CWM_PARTITION_REMARK.REMARK is
'���뱸ע';

comment on column CWM_PARTITION_REMARK.COLS is
'�ļ��е���������';

