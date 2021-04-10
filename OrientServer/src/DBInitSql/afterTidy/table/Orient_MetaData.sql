/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016/3/10 ������ 11:11:48                       */
/*==============================================================*/


drop table CWM_ARITH_ATTRIBUTE cascade constraints;

drop table CWM_ARITH_VIEW_CANSHUXIANG cascade constraints;

drop table CWM_CONS_EXPRESSION cascade constraints;

drop table CWM_ENUM cascade constraints;

drop table CWM_RELATION_COLUMNS cascade constraints;

drop table CWM_RELATION_DATA cascade constraints;

drop table CWM_RELATION_TABLE_ENUM cascade constraints;

drop table CWM_RESTRICTION cascade constraints;

drop table CWM_SCHEMA cascade constraints;

drop table CWM_SEQGENERATOR cascade constraints;

drop table CWM_TABLES cascade constraints;

drop table CWM_TABLE_COLUMN cascade constraints;

drop table CWM_TABLE_ENUM cascade constraints;

drop table CWM_TAB_COLUMNS cascade constraints;

drop table CWM_VIEWS cascade constraints;

drop table CWM_VIEW_PAIXU_COLUMN cascade constraints;

drop table CWM_VIEW_RELATIONTABLE cascade constraints;

drop table CWM_VIEW_RETURN_COLUMN cascade constraints;

/*==============================================================*/
/* Table: CWM_ARITH_ATTRIBUTE                                   */
/*==============================================================*/
create table CWM_ARITH_ATTRIBUTE  (
   ID                   VARCHAR2(38)                    not null,
   TYPE                 VARCHAR2(50),
   ARITH_COLUMN_ID      VARCHAR2(38),
   VALUE                VARCHAR2(100),
   COLUMN_ID            VARCHAR2(38),
   ORDER_SIGN           NUMBER(30),
   constraint PK_CWM_ARITH_ATTRIBUTE primary key (ID)
);

comment on table CWM_ARITH_ATTRIBUTE is
'�㷨���Ա�';

comment on column CWM_ARITH_ATTRIBUTE.ID is
'�㷨��������ID';

comment on column CWM_ARITH_ATTRIBUTE.TYPE is
'��������������';

comment on column CWM_ARITH_ATTRIBUTE.ARITH_COLUMN_ID is
'��������ͳ������';

comment on column CWM_ARITH_ATTRIBUTE.VALUE is
'����ֵ�����ֶ�����һ���൱�ڳ�������';

comment on column CWM_ARITH_ATTRIBUTE.COLUMN_ID is
'�ֶ�ID���Ͳ���ֵ����һ�����൱�ڱ�������';

comment on column CWM_ARITH_ATTRIBUTE.ORDER_SIGN is
'������˳���';

/*==============================================================*/
/* Table: CWM_ARITH_VIEW_CANSHUXIANG                            */
/*==============================================================*/
create table CWM_ARITH_VIEW_CANSHUXIANG  (
   ID                   VARCHAR2(38)                    not null,
   NAME                 VARCHAR2(100),
   ISFORARITH           VARCHAR2(10),
   COLUMN_ID            VARCHAR2(38),
   ORDER_SIGN           NUMBER(30),
   VIEW_ID              VARCHAR2(38),
   DISPLAY_NAME         VARCHAR2(100),
   constraint PK_CWM_ARITH_VIEW_CSX primary key (ID)
);

comment on table CWM_ARITH_VIEW_CANSHUXIANG is
'ͳ����ͼͳ�Ʋ�������Ϣ��';

comment on column CWM_ARITH_VIEW_CANSHUXIANG.ID is
'ͳ����ͼͳ�Ʋ����������ID';

comment on column CWM_ARITH_VIEW_CANSHUXIANG.NAME is
'����������';

comment on column CWM_ARITH_VIEW_CANSHUXIANG.ISFORARITH is
'�Ƿ�ͳ���1��ʾ�ǣ�0��ʾ��';

comment on column CWM_ARITH_VIEW_CANSHUXIANG.COLUMN_ID is
'��������ֶ�ID,����ͳ��������ͳ�����ԣ�������������ͨ����';

comment on column CWM_ARITH_VIEW_CANSHUXIANG.ORDER_SIGN is
'ͳ�Ʋ������˳���';

comment on column CWM_ARITH_VIEW_CANSHUXIANG.VIEW_ID is
'ͳ�Ʋ�����������ͳ����ͼ';

comment on column CWM_ARITH_VIEW_CANSHUXIANG.DISPLAY_NAME is
'ͳ����ͼ��ͳ�Ʋ��������ʾ��';

/*==============================================================*/
/* Table: CWM_CONS_EXPRESSION                                   */
/*==============================================================*/
create table CWM_CONS_EXPRESSION  (
   TABLE_ID             VARCHAR2(38),
   EXPRESSION           NVARCHAR2(1000),
   RESULT               NVARCHAR2(200),
   PRI                  NUMBER(10),
   ID                   VARCHAR2(38)                    not null,
   ORDER_SIGN           NUMBER(10),
   constraint PK_CWM_CONS_EXPRESSION primary key (ID)
);

comment on table CWM_CONS_EXPRESSION is
'ģ��Լ��������';

comment on column CWM_CONS_EXPRESSION.TABLE_ID is
'����ģ��';

comment on column CWM_CONS_EXPRESSION.EXPRESSION is
'����Լ���������ʽ';

comment on column CWM_CONS_EXPRESSION.RESULT is
'���û���������ʱΥ��Լ������ʱ���ص���ʾ��Ϣ';

comment on column CWM_CONS_EXPRESSION.PRI is
'���ȼ����Ӹߵ�������У��';

comment on column CWM_CONS_EXPRESSION.ID is
'ID';

comment on column CWM_CONS_EXPRESSION.ORDER_SIGN is
'����Լ��˳��';

/*==============================================================*/
/* Table: CWM_ENUM                                              */
/*==============================================================*/
create table CWM_ENUM  (
   RESTRICTION_ID       VARCHAR2(38)                    not null,
   VALUE                VARCHAR2(50)                    not null,
   DISPLAY_VALUE        VARCHAR2(100)                   not null,
   IMAGE_URL            VARCHAR2(100),
   DESCRIPTION          VARCHAR2(1000),
   ID                   VARCHAR2(38)                    not null,
   ORDER_SIGN           NUMBER(10),
   IS_OPEN              NUMBER(1),
   constraint PK_CWM_ENUM primary key (ID)
);

comment on table CWM_ENUM is
'ö��������';

comment on column CWM_ENUM.RESTRICTION_ID is
'Լ��ID';

comment on column CWM_ENUM.VALUE is
'���ݿ�洢ֵ';

comment on column CWM_ENUM.DISPLAY_VALUE is
'��ʾ����';

comment on column CWM_ENUM.IMAGE_URL is
'ͼ��URL';

comment on column CWM_ENUM.DESCRIPTION is
'ö������';

comment on column CWM_ENUM.ID is
'ID';

comment on column CWM_ENUM.ORDER_SIGN is
'����';

comment on column CWM_ENUM.IS_OPEN is
'�Ƿ���Ч';

/*==============================================================*/
/* Table: CWM_RELATION_COLUMNS                                  */
/*==============================================================*/
create table CWM_RELATION_COLUMNS  (
   ID            VARCHAR2(38)                    not null,
   COLUMN_ID            VARCHAR2(38),
   RELATIONTYPE         NUMBER(1),
   OWNERSHIP            NUMBER(1),
   IS_FK                NUMBER(1),
   REF_TABLE_ID         VARCHAR2(38),
   REF_COLUMN_NAME      VARCHAR2(100),
   TABLE_ID             VARCHAR2(38),
   SUB_COLUMN_ID        VARCHAR2(38),
   CATEGORY             VARCHAR2(100),
   ISLISTDISPLAY        VARCHAR2(10),
   constraint PK_CWM_RELATION_COLUMNS primary key (ID)
);

comment on table CWM_RELATION_COLUMNS is
'��ϵ����������';

comment on column CWM_RELATION_COLUMNS.COLUMN_ID is
'�ֶ�ID';

comment on column CWM_RELATION_COLUMNS.RELATIONTYPE is
'1��һ��һ��2��һ�Զ࣬3�Ƕ��һ��4�Ƕ�Զ�';

comment on column CWM_RELATION_COLUMNS.OWNERSHIP is
'����Ȩ��1:����ϣ�2�����У�3������ϣ�4��������Ȩ';

comment on column CWM_RELATION_COLUMNS.IS_FK is
'��ϵ�ֶ��Ƿ��ڵ�ǰ����';

comment on column CWM_RELATION_COLUMNS.REF_TABLE_ID is
'�����ֶ�����ģ��';

comment on column CWM_RELATION_COLUMNS.REF_COLUMN_NAME is
'�����ֶ�����';

comment on column CWM_RELATION_COLUMNS.TABLE_ID is
'��ǰģ��ID';

comment on column CWM_RELATION_COLUMNS.SUB_COLUMN_ID is
'�����ֶ�ID';

comment on column CWM_RELATION_COLUMNS.CATEGORY is
'��ϵ��������';

comment on column CWM_RELATION_COLUMNS.ISLISTDISPLAY is
'�Ƿ������б���ʾ��False��ն�Ϊ����������ʾ��TrueΪ�����б���ʾ';

/*==============================================================*/
/* Table: CWM_RELATION_DATA                                     */
/*==============================================================*/
create table CWM_RELATION_DATA  (
   ID                   VARCHAR2(38)                    not null,
   MAIN_TABLE_NAME      VARCHAR2(100),
   MAIN_DATA_ID         VARCHAR2(38),
   SUB_TABLE_NAME       VARCHAR2(100),
   SUB_DATA_ID          VARCHAR2(38),
   constraint PK_CWM_RELATION_DATA primary key (ID)
);

comment on table CWM_RELATION_DATA is
'��Զ�ģ�ͼ�����������';

comment on column CWM_RELATION_DATA.ID is
'ID';

comment on column CWM_RELATION_DATA.MAIN_TABLE_NAME is
'��ģ������';

comment on column CWM_RELATION_DATA.MAIN_DATA_ID is
'��ģ������ID';

comment on column CWM_RELATION_DATA.SUB_TABLE_NAME is
'����ģ��ID';

comment on column CWM_RELATION_DATA.SUB_DATA_ID is
'����ģ������ID';

/*==============================================================*/
/* Table: CWM_RELATION_TABLE_ENUM                               */
/*==============================================================*/
create table CWM_RELATION_TABLE_ENUM  (
   TABLE_ID             VARCHAR2(38),
   ID                   VARCHAR2(38)                    not null,
   TABLE_ENUM_ID        VARCHAR2(38),
   ORDER_SIGN           NUMBER(10),
   ORIGIN_TABLE_ID      VARCHAR2(38),
   TYPE                 VARCHAR2(10),
   FROM_TABLE_ID        VARCHAR2(38),
   TO_TABLE_ID          VARCHAR2(38),
   constraint PK_CWM_RELATION_TABLE_ENUM primary key (ID)
);

comment on table CWM_RELATION_TABLE_ENUM is
'������ö��Լ���Ĺ�����������Ϣ��';

comment on column CWM_RELATION_TABLE_ENUM.TABLE_ID is
'ö��ֵ����������';

comment on column CWM_RELATION_TABLE_ENUM.ID is
'ID';

comment on column CWM_RELATION_TABLE_ENUM.TABLE_ENUM_ID is
'����������ö��Լ��';

comment on column CWM_RELATION_TABLE_ENUM.ORDER_SIGN is
'�����������ǰ��˳��';

comment on column CWM_RELATION_TABLE_ENUM.ORIGIN_TABLE_ID is
'Դ�����ࣨ���ĸ����������������';

comment on column CWM_RELATION_TABLE_ENUM.TYPE is
'��������';

comment on column CWM_RELATION_TABLE_ENUM.FROM_TABLE_ID is
'��ϵ����������������';

comment on column CWM_RELATION_TABLE_ENUM.TO_TABLE_ID is
'��ϵ������������������';

/*==============================================================*/
/* Table: CWM_RESTRICTION                                       */
/*==============================================================*/
create table CWM_RESTRICTION  (
   ID                   VARCHAR2(38)                    not null,
   NAME                 VARCHAR2(30)                    not null,
   DISPLAY_NAME         VARCHAR2(100)                   not null,
   TYPE                 NUMBER(1)                       not null,
   IS_MULTI_SELECTED    VARCHAR2(10),
   ERROR_INFO           VARCHAR2(1000),
   DESCRIPTION          VARCHAR2(1000),
   DISPLAY_TYPE         NUMBER(1),
   MAX_LENGTH           NUMBER(20,10),
   MIN_LENGTH           NUMBER(20,10),
   SCHEMA_ID            VARCHAR2(38),
   IS_VALID             NUMBER(1)                       not null,
   DATATYPE             VARCHAR2(20),
   ORDER_SIGN           NUMBER(10),
   CITE                 VARCHAR2(100),
   constraint PK_CWM_RESTRICTION primary key (ID)
);

comment on table CWM_RESTRICTION is
'Լ��������';

comment on column CWM_RESTRICTION.ID is
'ID';

comment on column CWM_RESTRICTION.NAME is
'����';

comment on column CWM_RESTRICTION.DISPLAY_NAME is
'Լ����ʾ����';

comment on column CWM_RESTRICTION.TYPE is
'����Լ�������ͣ�1��ʾö��Լ����2��ʾ���ݱ�ö��Լ����3��ʾ��ΧԼ��4.��̬��ΧԼ��';

comment on column CWM_RESTRICTION.IS_MULTI_SELECTED is
'�Ƿ��ѡ';

comment on column CWM_RESTRICTION.ERROR_INFO is
'������Ϣ���û����ִ�����������Ĵ�����Ϣ';

comment on column CWM_RESTRICTION.DESCRIPTION is
'Լ������';

comment on column CWM_RESTRICTION.DISPLAY_TYPE is
' ö��Լ������ʾ��ʽ��0Ϊ������ʾ��1ΪͼƬ��ʾ����ֻ���ö��Լ��';

comment on column CWM_RESTRICTION.MAX_LENGTH is
'��󳤶�';

comment on column CWM_RESTRICTION.MIN_LENGTH is
'��С����';

comment on column CWM_RESTRICTION.SCHEMA_ID is
'����SCHEMA';

comment on column CWM_RESTRICTION.IS_VALID is
'�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч';

comment on column CWM_RESTRICTION.DATATYPE is
'��������';

comment on column CWM_RESTRICTION.ORDER_SIGN is
'˳��';

comment on column CWM_RESTRICTION.CITE is
'���ڴ�ģ��ʱ�������õ���Ϣ';

/*==============================================================*/
/* Table: CWM_SCHEMA                                            */
/*==============================================================*/
create table CWM_SCHEMA  (
   ID                   VARCHAR2(38)                    not null,
   NAME                 VARCHAR2(50)                    not null,
   VERSION              VARCHAR2(15)                    not null,
   DESCRIPTION          VARCHAR2(200),
   MODIFIED_TIME        DATE,
   IS_LOCK              NUMBER(1),
   USERID               VARCHAR2(100),
   LOCK_MODIFIED_TIME   DATE,
   IS_DELETE            NUMBER(1)                      default 1,
   SECRECY_SET          VARCHAR2(100),
   TYPE                 VARCHAR2(1)                    default '0'
);

comment on table CWM_SCHEMA is
'SCHEMA������';

comment on column CWM_SCHEMA.ID is
'ID';

comment on column CWM_SCHEMA.NAME is
'SCHEMA����';

comment on column CWM_SCHEMA.VERSION is
'SCHEMA�汾';

comment on column CWM_SCHEMA.DESCRIPTION is
'SCHEMA����';

comment on column CWM_SCHEMA.MODIFIED_TIME is
'���޸�ʱ��';

comment on column CWM_SCHEMA.IS_LOCK is
'�Ƿ�����';

comment on column CWM_SCHEMA.USERID is
'�����޸�SCHEMA�û�ID';

comment on column CWM_SCHEMA.LOCK_MODIFIED_TIME is
'������ʱ��';

comment on column CWM_SCHEMA.IS_DELETE is
'�Ƿ��Ѿ�ɾ��';

comment on column CWM_SCHEMA.SECRECY_SET is
'����ģ�͵��ܼ�������Ϣ���ܼ���,�ָ�';

comment on column CWM_SCHEMA.TYPE is
'�Ƿ��ǹ�������ģ�ͣ�0��ʾ���ǹ�������ģ�ͣ�1��ʾ�ǹ�������ģ��';

/*==============================================================*/
/* Table: CWM_SEQGENERATOR                                      */
/*==============================================================*/
create table CWM_SEQGENERATOR  (
   ID                   NUMBER                          not null,
   NAME                 VARCHAR2(100),
   CONTENT              CLOB,
   ENABLE               NUMBER(1),
   RETURN_TYPE          VARCHAR2(50),
   CHANGED              VARCHAR2(10),
   constraint PK_CWM_SEQGENERATOR primary key (ID)
);

comment on table CWM_SEQGENERATOR is
'��������������������';

comment on column CWM_SEQGENERATOR.ID is
'�ű�ID';

comment on column CWM_SEQGENERATOR.NAME is
'�ű�����';

comment on column CWM_SEQGENERATOR.CONTENT is
'�ű�����';

comment on column CWM_SEQGENERATOR.ENABLE is
'�Ƿ����';

comment on column CWM_SEQGENERATOR.RETURN_TYPE is
'��������';

comment on column CWM_SEQGENERATOR.CHANGED is
'�Ƿ��޸�(TRUE��ʾ�Ѿ����޸�,��Ҫ���±���,FALSE��֮).';

/*==============================================================*/
/* Table: CWM_TABLES                                            */
/*==============================================================*/
create table CWM_TABLES  (
   ID                   VARCHAR2(38)                    not null,
   S_NAME               VARCHAR2(30)                    not null,
   DISPLAY_NAME         VARCHAR2(100)                   not null,
   S_TABLE_NAME         VARCHAR2(100),
   PID                  VARCHAR2(38),
   SCHEMA_ID            VARCHAR2(38)                    not null,
   PAIXU_FX             VARCHAR2(10),
   IS_CONNECT_TABLE     VARCHAR2(10),
   IS_SHOW              VARCHAR2(10),
   DETAIL_TEXT          VARCHAR2(3000),
   DESCRIPTION          VARCHAR2(2000),
   BIG_IMAGE            VARCHAR2(100),
   NOR_IMAGE            VARCHAR2(100),
   SMA_IMAGE            VARCHAR2(100),
   CATEGORY             VARCHAR2(20),
   IS_VALID             NUMBER(1)                       not null,
   ORDER_SIGN           NUMBER(10),
   CITE                 VARCHAR2(100),
   SECRECY              VARCHAR2(100),
   SECRECYABLE          VARCHAR2(30),
   MAP_TABLE            VARCHAR2(38),
   SHARE_TYPE           VARCHAR2(1)                    default '0',
   SHAREABLE            VARCHAR2(10),
   COL_SUM              NUMBER(4)                      default 2,
   constraint PK_CWM_TABLES primary key (ID)
);

comment on table CWM_TABLES is
'ģ��������';

comment on column CWM_TABLES.ID is
'ģ��ID';

comment on column CWM_TABLES.S_NAME is
'��ģʱģ��Ӣ������';

comment on column CWM_TABLES.DISPLAY_NAME is
'ģ����ʾ����';

comment on column CWM_TABLES.S_TABLE_NAME is
'���ݿ�����ʵģ�͵�����';

comment on column CWM_TABLES.PID is
'��ģ��ID';

comment on column CWM_TABLES.SCHEMA_ID is
'����SCHEMA';

comment on column CWM_TABLES.PAIXU_FX is
'������';

comment on column CWM_TABLES.IS_CONNECT_TABLE is
'�Ƿ������ࣨӦ�������ã�';

comment on column CWM_TABLES.IS_SHOW is
'�Ƿ���ʾ';

comment on column CWM_TABLES.DETAIL_TEXT is
'��ϸ';

comment on column CWM_TABLES.DESCRIPTION is
'ģ������';

comment on column CWM_TABLES.BIG_IMAGE is
'��ͼ��';

comment on column CWM_TABLES.NOR_IMAGE is
'ģ��Сͼ��';

comment on column CWM_TABLES.SMA_IMAGE is
'Сͼ��';

comment on column CWM_TABLES.CATEGORY is
'ģ�͵�����';

comment on column CWM_TABLES.IS_VALID is
'�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч';

comment on column CWM_TABLES.ORDER_SIGN is
'ģ����Ѱ';

comment on column CWM_TABLES.CITE is
'ģ����schema�ľ���·����ͨ����·���ɶ�λģ��';

comment on column CWM_TABLES.SECRECY is
'�������Ĭ���ܼ�';

comment on column CWM_TABLES.SECRECYABLE is
'�ܼ��Ƿ����ã�TrueΪ���ã�FalseΪ���ã�ȱʡΪFalse';

comment on column CWM_TABLES.MAP_TABLE is
'ӳ�������࣬������ӳ�䵽��������ģ���е��������ϵͳ��';

comment on column CWM_TABLES.SHARE_TYPE is
'�������ͣ�0��ʾ��ͨ�����࣬1��ʾ���ù��������ࣨ�̻�����2��ʾ����ϵͳ��';

comment on column CWM_TABLES.SHAREABLE is
'�����ú�Ĺ���������������Ƿ���';

comment on column CWM_TABLES.COL_SUM is
'��ϸ������ʾ������';

/*==============================================================*/
/* Table: CWM_TABLE_COLUMN                                      */
/*==============================================================*/
create table CWM_TABLE_COLUMN  (
   ID                   VARCHAR2(38)                    not null,
   TABLE_ID             VARCHAR2(38),
   COLUMN_ID            VARCHAR2(38),
   TYPE                 NUMBER(1),
   ORDER_SIGN           NUMBER(10),
   constraint PK_CWM_TABLE_UNIQUE primary key (ID)
);

comment on table CWM_TABLE_COLUMN is
'ģ�����ֶ��м��';

comment on column CWM_TABLE_COLUMN.ID is
'ID';

comment on column CWM_TABLE_COLUMN.TABLE_ID is
'�������ID';

comment on column CWM_TABLE_COLUMN.COLUMN_ID is
'�ֶε�id����������ͨ����Ҳ�����ǹ�ϵ����';

comment on column CWM_TABLE_COLUMN.TYPE is
'0��ʾΪ������ʾֵ��1��ʾΪΨһ�����Լ����2��ʾΪ�������ԣ�3��ʾΪ���ݱ��ֶ�ǰ��չ��˳��';

comment on column CWM_TABLE_COLUMN.ORDER_SIGN is
'�ֶ�˳��';

/*==============================================================*/
/* Table: CWM_TABLE_ENUM                                        */
/*==============================================================*/
create table CWM_TABLE_ENUM  (
   RESTRICTION_ID       VARCHAR2(38)                    not null,
   COLUMN_ID            VARCHAR2(38),
   EXPRESSION           VARCHAR2(500),
   TABLE_ID             VARCHAR2(38),
   TABLE_ENUM_SQL       VARCHAR2(3000),
   ID                   VARCHAR2(38)                    not null,
   MIN_TABLE            VARCHAR2(38),
   MAX_TABLE            VARCHAR2(38),
   MIN_COLUMN           VARCHAR2(38),
   MAX_COLUMN           VARCHAR2(38),
   constraint PK_CWM_TABLE_ENUM primary key (ID)
);

comment on table CWM_TABLE_ENUM is
'��ö��������';

comment on column CWM_TABLE_ENUM.RESTRICTION_ID is
'����Լ��ID';

comment on column CWM_TABLE_ENUM.COLUMN_ID is
'��ö��Լ�������ֶ�ID';

comment on column CWM_TABLE_ENUM.EXPRESSION is
'Լ�����ʽ';

comment on column CWM_TABLE_ENUM.TABLE_ID is
'����ģ��ID';

comment on column CWM_TABLE_ENUM.TABLE_ENUM_SQL is
'��ö��Լ����SQL���';

comment on column CWM_TABLE_ENUM.ID is
'ID';

comment on column CWM_TABLE_ENUM.MIN_TABLE is
'��̬��ΧԼ���Ķ�̬��Сֵ���������࣬��¼�����������ID';

comment on column CWM_TABLE_ENUM.MAX_TABLE is
'��̬��ΧԼ���Ķ�̬���ֵ���������࣬��¼�����������ID';

comment on column CWM_TABLE_ENUM.MIN_COLUMN is
'��̬��ΧԼ���Ķ�̬��Сֵ�����ֶΣ���¼�����ֶε�ID';

comment on column CWM_TABLE_ENUM.MAX_COLUMN is
'��̬��ΧԼ���Ķ�̬���ֵ�����ֶΣ���¼�����ֶε�ID';

/*==============================================================*/
/* Table: CWM_TAB_COLUMNS                                       */
/*==============================================================*/
create table CWM_TAB_COLUMNS  (
   ID                   VARCHAR2(38)                    not null,
   S_NAME               VARCHAR2(30)                    not null,
   DISPLAY_NAME         VARCHAR2(100)                   not null,
   CATEGORY             NUMBER(1)                       not null,
   DESCRIPTION          VARCHAR2(2000),
   S_COLUMN_NAME        VARCHAR2(100),
   TABLE_ID             VARCHAR2(38),
   IS_WHO_SEARCH        VARCHAR2(10),
   IS_FOR_SEARCH        VARCHAR2(10),
   IS_INDEX             VARCHAR2(10),
   OPERATE_SIGN         VARCHAR2(10),
   PURPOSE              VARCHAR2(10),
   CASESENSITIVE        VARCHAR2(15),
   DEFAULT_VALUE        VARCHAR2(30),
   IS_NULLABLE          VARCHAR2(10),
   IS_ONLY              VARCHAR2(10),
   IS_PK                NUMBER(10),
   IS_AUTOINCREMENT     VARCHAR2(10),
   RESTRICTION_ID       VARCHAR2(38),
   TYPE                 VARCHAR2(20),
   SEQUENCE_NAME        VARCHAR2(100),
   MAX_LENGTH           NUMBER(38),
   MIN_LENGTH           NUMBER(38),
   IS_SHOW              VARCHAR2(10),
   IS_WRAP              VARCHAR2(10),
   PROPERTY_PARAGRAPH   VARCHAR2(30),
   PROPERTY_CATEGORY    VARCHAR2(30),
   LINAGE               NUMBER(38),
   IS_VALID             NUMBER(1)                       not null,
   IS_MUTI_UK           NUMBER(10),
   IS_USED_PAIXU        NUMBER(10),
   IS_NEED              VARCHAR2(10),
   ORDER_SIGN           NUMBER(10),
   CITE                 VARCHAR2(100),
   NUM_LENGTH           NUMBER(38),
   NUM_PRECISION        NUMBER(38),
   REF_VIEW_ID          VARCHAR2(38),
   ARITH_ID             VARCHAR2(100),
   ARITH_NAME           VARCHAR2(100),
   ARITH_METHOD         VARCHAR2(100),
   ARITH_TYPE           VARCHAR2(100),
   MAP_COLUMN           VARCHAR2(38),
   SEQ_VALUE            NUMBER(38),
   EDITABLE             VARCHAR2(10),
   SEQ_INTERVAL         NUMBER(38),
   SELECTOR             VARCHAR2(200),
   UNIT             VARCHAR2(100),
   constraint PK_CWM_TAB_COLUMNS primary key (ID)
);

comment on table CWM_TAB_COLUMNS is
'�ֶ���ϸ������';

comment on column CWM_TAB_COLUMNS.ID is
'ID';

comment on column CWM_TAB_COLUMNS.S_NAME is
'��ģʱ�ֶ�Ӣ������';

comment on column CWM_TAB_COLUMNS.DISPLAY_NAME is
'�ֶ���ʾ����';

comment on column CWM_TAB_COLUMNS.CATEGORY is
'�ֶε����1��ʾ��ͨ���ԣ�2��ʾ��ϵ���� 3.��ʾͳ������';

comment on column CWM_TAB_COLUMNS.DESCRIPTION is
'�ֶ�������Ϣ';

comment on column CWM_TAB_COLUMNS.S_COLUMN_NAME is
'���ݿ�����ʵ�ֶε����ƣ�S_NAME_ + MODELID��';

comment on column CWM_TAB_COLUMNS.TABLE_ID is
'����ģ��ID';

comment on column CWM_TAB_COLUMNS.IS_FOR_SEARCH is
'�Ƿ�������ȫ�ļ��� True��ʾ���ã�False��ʾ��������';

comment on column CWM_TAB_COLUMNS.IS_INDEX is
'�Ƿ���������';

comment on column CWM_TAB_COLUMNS.OPERATE_SIGN is
'����������ȡ�����ȡ����ƣ���Ҫ�������ݲ�ѯ';

comment on column CWM_TAB_COLUMNS.PURPOSE is
'��;��ȷ���ֶ��ڲ�ͬ����ҳ���Ƿ�չ��';

comment on column CWM_TAB_COLUMNS.CASESENSITIVE is
'��Сд�涨 CaseSensitive��ʾ�����ִ�Сд����Upper��ʾ��ȫ��д����Lower��ʾ��ȫСд����CaseInsensitive��ʾ�������ִ�Сд��';

comment on column CWM_TAB_COLUMNS.DEFAULT_VALUE is
'Ĭ��ֵ';

comment on column CWM_TAB_COLUMNS.IS_NULLABLE is
'�Ƿ����Ϊ��';

comment on column CWM_TAB_COLUMNS.IS_ONLY is
'�Ƿ�Ψһ';

comment on column CWM_TAB_COLUMNS.IS_AUTOINCREMENT is
'�Ƿ��������ֶ�';

comment on column CWM_TAB_COLUMNS.RESTRICTION_ID is
'����Լ��ID';

comment on column CWM_TAB_COLUMNS.TYPE is
'�ֶ�����';

comment on column CWM_TAB_COLUMNS.SEQUENCE_NAME is
'������������';

comment on column CWM_TAB_COLUMNS.MAX_LENGTH is
'��󳤶�';

comment on column CWM_TAB_COLUMNS.MIN_LENGTH is
'��С����';

comment on column CWM_TAB_COLUMNS.IS_SHOW is
'�Ƿ���ʾ';

comment on column CWM_TAB_COLUMNS.IS_WRAP is
'�Ƿ������ʾ';

comment on column CWM_TAB_COLUMNS.PROPERTY_PARAGRAPH is
'���Զ���';

comment on column CWM_TAB_COLUMNS.PROPERTY_CATEGORY is
'��ʾ���Ե��޸ĵ�λ��
      * 0����ʾ���޸ģ� 
      * 1������ȫ�ļ�������Ϊȫ�ļ��� 
      * 2���޸�����������  
��ʾ���Ե��޸ĵ�λ��';

comment on column CWM_TAB_COLUMNS.LINAGE is
'������ʾʱ����';

comment on column CWM_TAB_COLUMNS.IS_VALID is
'�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч';

comment on column CWM_TAB_COLUMNS.IS_MUTI_UK is
'�Ƿ���Լ������';

comment on column CWM_TAB_COLUMNS.IS_USED_PAIXU is
'�Ƿ�����������';

comment on column CWM_TAB_COLUMNS.IS_NEED is
'�Ƿ����';

comment on column CWM_TAB_COLUMNS.ORDER_SIGN is
'���ڱ�������ֶε�ǰ��˳��';

comment on column CWM_TAB_COLUMNS.CITE is
'���ڴ�ģ��ʱ�������õ���Ϣ';

comment on column CWM_TAB_COLUMNS.NUM_LENGTH is
'�������ֵ���ͣ�����ֵ����';

comment on column CWM_TAB_COLUMNS.NUM_PRECISION is
'��ֵ�����ֶξ���';

comment on column CWM_TAB_COLUMNS.REF_VIEW_ID is
'����ͳ����ͼID���û���������ͳ����ͼʱ��ͳ�Ʋ������е�ͳ�����Ա�����ֶ�ģʽ������column��';

comment on column CWM_TAB_COLUMNS.ARITH_ID is
'ͳ�����Ե��㷨ID';

comment on column CWM_TAB_COLUMNS.ARITH_NAME is
'ͳ�������㷨������';

comment on column CWM_TAB_COLUMNS.ARITH_METHOD is
'ͳ�������㷨�Ĺ�ʽ';

comment on column CWM_TAB_COLUMNS.ARITH_TYPE is
'ͳ�����Ե���������ݿ������㷨�����Զ����㷨��0��ʾ���ã�1��ʾ�Զ����㷨';

comment on column CWM_TAB_COLUMNS.MAP_COLUMN is
'ӳ���ֶΣ���ǰ�ֶ�����ӳ��Ĺ�����е��ֶ���Ϣ';

comment on column CWM_TAB_COLUMNS.SEQ_VALUE is
'�������Գ�ʼֵ,Ĭ��ֵΪ1';

comment on column CWM_TAB_COLUMNS.EDITABLE is
'���ݲ���,0Ϊ�����ƣ�1Ϊ�����޸ģ�2Ϊ���ƴ����޸�';

comment on column CWM_TAB_COLUMNS.SEQ_INTERVAL is
'�������е����������Ĭ��Ϊ1����СΪ1��������';

/*==============================================================*/
/* Table: CWM_VIEWS                                             */
/*==============================================================*/
create table CWM_VIEWS  (
   ID                   VARCHAR2(38)                    not null,
   NAME                 VARCHAR2(30)                    not null,
   DISPLAY_NAME         VARCHAR2(100)                   not null,
   DESCRIPTION          VARCHAR2(1000),
   TABLE_ID             VARCHAR2(38)                    not null,
   EXPRESSION           VARCHAR2(1000),
   TYPE                 NUMBER(1)                       not null,
   SCHEMA_ID            VARCHAR2(38),
   IS_VALID             NUMBER(1)                       not null,
   VIEW_SQL             VARCHAR2(3000),
   PAIXU_FX             NUMBER(1),
   ORDER_SIGN           NUMBER(10),
   CITE                 VARCHAR2(100),
   VIEW_NAME            VARCHAR2(100),
   REF_VIEW_ID          VARCHAR2(200),
   JOIN_TYPE            NUMBER(1),
   constraint PK_CWM_VIEWS primary key (ID)
);

comment on table CWM_VIEWS is
'��ͼ������';

comment on column CWM_VIEWS.ID is
'ID';

comment on column CWM_VIEWS.NAME is
'��ͼӢ������';

comment on column CWM_VIEWS.DISPLAY_NAME is
'��ͼ��ʾ����';

comment on column CWM_VIEWS.DESCRIPTION is
'��ͼ����';

comment on column CWM_VIEWS.TABLE_ID is
'����ģ��';

comment on column CWM_VIEWS.EXPRESSION is
'������ѯ����';

comment on column CWM_VIEWS.TYPE is
'��ͼ����';

comment on column CWM_VIEWS.SCHEMA_ID is
'����SCHEMA';

comment on column CWM_VIEWS.IS_VALID is
'�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч';

comment on column CWM_VIEWS.VIEW_SQL is
'������ͼ��SQL���';

comment on column CWM_VIEWS.PAIXU_FX is
'������0��ʾΪ����1��ʾΪ����';

comment on column CWM_VIEWS.ORDER_SIGN is
'�����־λ';

comment on column CWM_VIEWS.CITE is
'xml��·��';

comment on column CWM_VIEWS.VIEW_NAME is
'��ͼ����';

comment on column CWM_VIEWS.REF_VIEW_ID is
'������ͼ';

comment on column CWM_VIEWS.JOIN_TYPE is
'0Ϊ�����ӣ�1Ϊ�����ӣ�2Ϊ�����ӣ�3Ϊȫ����';

/*==============================================================*/
/* Table: CWM_VIEW_PAIXU_COLUMN                                 */
/*==============================================================*/
create table CWM_VIEW_PAIXU_COLUMN  (
   VIEW_ID              VARCHAR2(38),
   ID                   VARCHAR2(38)                    not null,
   PAIXU_COLUMN_ID      VARCHAR2(38),
   ORDER_SIGN           NUMBER(10),
   constraint PK_CWM_VIEW_PAIXU_COLUMN primary key (ID)
);

comment on table CWM_VIEW_PAIXU_COLUMN is
'��ͼ�����ֶ�����';

comment on column CWM_VIEW_PAIXU_COLUMN.VIEW_ID is
'������ͼ';

comment on column CWM_VIEW_PAIXU_COLUMN.ID is
'ID';

comment on column CWM_VIEW_PAIXU_COLUMN.PAIXU_COLUMN_ID is
'�����ֶ�ID';

comment on column CWM_VIEW_PAIXU_COLUMN.ORDER_SIGN is
'�����־λ';

/*==============================================================*/
/* Table: CWM_VIEW_RELATIONTABLE                                */
/*==============================================================*/
create table CWM_VIEW_RELATIONTABLE  (
   VIEW_ID              VARCHAR2(38)                    not null,
   TABLE_ID             VARCHAR2(38),
   ID                   VARCHAR2(38)                    not null,
   ORDER_SIGN           NUMBER(10),
   ORIGIN_TABLE_ID      VARCHAR2(38),
   TYPE                 VARCHAR2(10),
   FROM_TABLE_ID        VARCHAR2(38),
   TO_TABLE_ID          VARCHAR2(38),
   ORIGIN_TO_VIEW_ID    VARCHAR2(38),
   constraint PK_CWM_VIEW_RELATIONTABLE primary key (ID)
);

comment on table CWM_VIEW_RELATIONTABLE is
'��ͼ��ģ�͹�ϵ��
';

comment on column CWM_VIEW_RELATIONTABLE.VIEW_ID is
'������ͼID';

comment on column CWM_VIEW_RELATIONTABLE.TABLE_ID is
'����ģ��ID';

comment on column CWM_VIEW_RELATIONTABLE.ID is
'ID';

comment on column CWM_VIEW_RELATIONTABLE.ORDER_SIGN is
'�����־λ';

comment on column CWM_VIEW_RELATIONTABLE.ORIGIN_TABLE_ID is
'Դ�����ࣨ���ĸ����������������';

comment on column CWM_VIEW_RELATIONTABLE.TYPE is
'��ϵ����';

comment on column CWM_VIEW_RELATIONTABLE.FROM_TABLE_ID is
'��ϵ����Դͷ����ģ��ID';

comment on column CWM_VIEW_RELATIONTABLE.TO_TABLE_ID is
'��ϵ����Ŀ�ĵ�����ģ��ID';

comment on column CWM_VIEW_RELATIONTABLE.ORIGIN_TO_VIEW_ID is
'Դ��ͼ��';

/*==============================================================*/
/* Table: CWM_VIEW_RETURN_COLUMN                                */
/*==============================================================*/
create table CWM_VIEW_RETURN_COLUMN  (
   VIEW_ID              VARCHAR2(38)                    not null,
   RETURN_COLUMN_ID     VARCHAR2(38)                    not null,
   ID                   VARCHAR2(38)                    not null,
   ORDER_SIGN           NUMBER(10),
   constraint PK_CWM_VIEW_RETURN_COLUMN primary key (ID)
);

comment on table CWM_VIEW_RETURN_COLUMN is
'��ͼ�ķ�������';

comment on column CWM_VIEW_RETURN_COLUMN.VIEW_ID is
'������ͼ';

comment on column CWM_VIEW_RETURN_COLUMN.RETURN_COLUMN_ID is
'���ص��ֶ�ID';

comment on column CWM_VIEW_RETURN_COLUMN.ID is
'ID';

comment on column CWM_VIEW_RETURN_COLUMN.ORDER_SIGN is
'�����־λ';

