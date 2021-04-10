/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016/3/10 ������ 11:13:25                       */
/*==============================================================*/


drop table CWM_DYNAMIC_TBOM cascade constraints;

drop table CWM_RELATION_TBOM cascade constraints;

drop table CWM_TBOM cascade constraints;

drop table CWM_TBOM_DIR cascade constraints;

drop table CWM_TBOM_FILE cascade constraints;

/*==============================================================*/
/* Table: CWM_DYNAMIC_TBOM                                      */
/*==============================================================*/
create table CWM_DYNAMIC_TBOM  (
   COLUMN_ID            VARCHAR2(500),
   DATA_SOURCE          VARCHAR2(500),
   ORDER_SIGN           VARCHAR2(10),
   TBOM_ID              VARCHAR2(38),
   TABLE_ID             VARCHAR2(38),
   VIEW_ID              VARCHAR2(38),
   URL                  VARCHAR2(500)                  default '',
   DISPLAY              VARCHAR2(10),
   PID                  VARCHAR2(38),
   EXP                  VARCHAR2(1000),
   SHOWTYPE             VARCHAR2(1),
   ORIGIN_EXP           VARCHAR2(1000)
);

comment on table CWM_DYNAMIC_TBOM is
'��̬TBOM�������';

comment on column CWM_DYNAMIC_TBOM.COLUMN_ID is
'��̬�ӽڵ���ֶ�ID������CWM_TAB_COLUMNS��';

comment on column CWM_DYNAMIC_TBOM.DATA_SOURCE is
'��̬�ӽڵ������Դ���ϣ��ԣ��ָ�';

comment on column CWM_DYNAMIC_TBOM.ORDER_SIGN is
'��̬�ӽڵ��˳��';

comment on column CWM_DYNAMIC_TBOM.TBOM_ID is
'��̬�ӽڵ�������Tbom�ڵ�ID';

comment on column CWM_DYNAMIC_TBOM.TABLE_ID is
'��̬�ӽڵ���ֶ�����������';

comment on column CWM_DYNAMIC_TBOM.VIEW_ID is
'��̬�ӽڵ��ϼ��ģ���һ������Դ����������Դ����ͼʱ�����ֶμ�¼��ͼ��ID';

comment on column CWM_DYNAMIC_TBOM.URL is
'URL';

comment on column CWM_DYNAMIC_TBOM.DISPLAY is
'��ʾ��';

comment on column CWM_DYNAMIC_TBOM.PID is
'parentId';

comment on column CWM_DYNAMIC_TBOM.EXP is
'��չ';

comment on column CWM_DYNAMIC_TBOM.ORIGIN_EXP is
'�ϵ���չ';

/*==============================================================*/
/* Table: CWM_RELATION_TBOM                                     */
/*==============================================================*/
create table CWM_RELATION_TBOM  (
   NODE_ID              VARCHAR2(38)                    not null,
   RELATION_ID          VARCHAR2(38)                    not null,
   ID                   VARCHAR2(38)                    not null,
   constraint PK_CWM_RELATION_TBOM primary key (ID)
);

/*==============================================================*/
/* Table: CWM_TBOM                                              */
/*==============================================================*/
create table CWM_TBOM  (
   ID                   VARCHAR2(38)                    not null,
   PID                  VARCHAR2(38),
   TABLE_ID             VARCHAR2(38),
   VIEW_ID              VARCHAR2(38),
   TYPE                 NUMBER(1),
   NAME                 VARCHAR2(100),
   DESCRIPTION          VARCHAR2(2000),
   DETAIL_TEXT          VARCHAR2(3000),
   BIG_IMAGE            VARCHAR2(500),
   NOR_IMAGE            VARCHAR2(500),
   SMA_IMAGE            VARCHAR2(500),
   IS_ROOT              NUMBER(1)                       not null,
   ORDER_SIGN           NUMBER(10),
   IS_VALID             NUMBER(1),
   XML_ID               VARCHAR2(4000),
   SCHEMA_ID            VARCHAR2(38),
   COLUMN_ID            VARCHAR2(38),
   COLUMN_NAME          VARCHAR2(100),
   EXP                  VARCHAR2(1000),
   ORIGIN_EXP           VARCHAR2(1000),
   URL                  VARCHAR2(500),
   SHOWTYPE             VARCHAR2(1),
   constraint PK_CWM_TBOM primary key (ID)
);

comment on table CWM_TBOM is
'TBOM������';

comment on column CWM_TBOM.ID is
'ID';

comment on column CWM_TBOM.PID is
'���ڵ�ID';

comment on column CWM_TBOM.TABLE_ID is
'ģ��ID';

comment on column CWM_TBOM.VIEW_ID is
'��ͼID';

comment on column CWM_TBOM.TYPE is
'1Ϊ������,0Ϊ������ͼ';

comment on column CWM_TBOM.NAME is
'�ڵ�����';

comment on column CWM_TBOM.DESCRIPTION is
'�ڵ�����';

comment on column CWM_TBOM.DETAIL_TEXT is
'��ϸ�ı�';

comment on column CWM_TBOM.BIG_IMAGE is
'�ڵ��ͼ��';

comment on column CWM_TBOM.NOR_IMAGE is
'����ͼ��';

comment on column CWM_TBOM.SMA_IMAGE is
'Сͼ��';

comment on column CWM_TBOM.IS_ROOT is
'�Ƿ���TBOM���ĸ��ڵ㣬0��ʾ��1��ʾ��';

comment on column CWM_TBOM.ORDER_SIGN is
'����';

comment on column CWM_TBOM.IS_VALID is
'�Ƿ���Ч';

comment on column CWM_TBOM.XML_ID is
'XML ID';

comment on column CWM_TBOM.SCHEMA_ID is
'schemaID';

comment on column CWM_TBOM.COLUMN_ID is
'�ֶ�ID';

comment on column CWM_TBOM.COLUMN_NAME is
'�ֶ���ʾ����';

comment on column CWM_TBOM.EXP is
'���˱��ʽ����SQLʹ�ã�';

comment on column CWM_TBOM.ORIGIN_EXP is
'���˱��ʽ����TBOM STUDIOʹ�ã�';

comment on column CWM_TBOM.URL is
'���ӵ�ַ';

/*==============================================================*/
/* Table: CWM_TBOM_DIR                                          */
/*==============================================================*/
create table CWM_TBOM_DIR  (
   ID                   VARCHAR2(38)                    not null,
   NAME                 VARCHAR2(100),
   SCHEMA_ID            VARCHAR2(38),
   IS_LOCK              NUMBER(1),
   USERNAME             VARCHAR2(50),
   MODIFIED_TIME        DATE,
   LOCK_MODIFIED_TIME   DATE,
   IS_DELETE            NUMBER(1),
   TYPE                 NUMBER(1),
   ORDER_SIGN           NUMBER(10)                     default 0,
   constraint PK_CWM_TBOM_DIR primary key (ID)
);

comment on table CWM_TBOM_DIR is
'TBOM�ļ���';

comment on column CWM_TBOM_DIR.ID is
'ID';

comment on column CWM_TBOM_DIR.NAME is
'DIR����';

comment on column CWM_TBOM_DIR.SCHEMA_ID is
'SCHEMAID';

comment on column CWM_TBOM_DIR.IS_LOCK is
'�Ƿ�����';

comment on column CWM_TBOM_DIR.USERNAME is
'�û���¼��';

comment on column CWM_TBOM_DIR.MODIFIED_TIME is
'�޸�ʱ��';

comment on column CWM_TBOM_DIR.LOCK_MODIFIED_TIME is
'�����޸�ʱ��';

comment on column CWM_TBOM_DIR.IS_DELETE is
'�Ƿ��Ѿ�ɾ��';

comment on column CWM_TBOM_DIR.TYPE is
'Tbom����(0���߿�:���ݲ鿴,1:�ļ��鿴).';

comment on column CWM_TBOM_DIR.ORDER_SIGN is
'����';

/*==============================================================*/
/* Table: CWM_TBOM_FILE                                         */
/*==============================================================*/
create table CWM_TBOM_FILE  (
   TBOM_ID              VARCHAR2(38),
   FILE_ID              VARCHAR2(38),
   ORDER_SIGN           NUMBER(10)
);

comment on table CWM_TBOM_FILE is
'TBOM file';

comment on column CWM_TBOM_FILE.TBOM_ID is
'����TBOM';

comment on column CWM_TBOM_FILE.FILE_ID is
'�ļ�ID';

comment on column CWM_TBOM_FILE.ORDER_SIGN is
'����';



CREATE TABLE CWM_DYNAMIC_TBOM_ROLE
(
  NODE_ID      VARCHAR2(38 BYTE),
  ROLE_ID      VARCHAR2(38 BYTE),
  DATA_SOURCE  VARCHAR2(500 BYTE)
);

CREATE TABLE CWM_TBOM_ROLE
(
  NODE_ID      VARCHAR2(38 BYTE),
  ROLE_ID      VARCHAR2(38 BYTE),
  DATA_SOURCE  VARCHAR2(500 BYTE)
);


