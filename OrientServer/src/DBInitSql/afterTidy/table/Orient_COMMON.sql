/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016/3/10 ������ 11:18:48                       */
/*==============================================================*/


drop table CWM_DATAOBJECT cascade constraints;

drop table CWM_DATAOBJECT_OLDVERSION cascade constraints;

drop table CWM_DATARELATION cascade constraints;

drop table CWM_DATASUBTYPE cascade constraints;

drop table CWM_DATATYPE cascade constraints;

drop table CWM_FILE cascade constraints;

drop table CWM_FILE_GROUP cascade constraints;

drop table CWM_FOLDER cascade constraints;

/*==============================================================*/
/* Table: CWM_DATAOBJECT                                        */
/*==============================================================*/
create table CWM_DATAOBJECT  (
   ID                   VARCHAR2(40)                    not null,
   DATATYPE_ID          VARCHAR2(50),
   DATAOBJECTNAME       VARCHAR2(200)                   not null,
   ISREF                NUMBER(8)                      default 0,
   PROJECTID            VARCHAR2(40),
   DIMENSION            VARCHAR2(50)                   default '1',
   VALUE                VARCHAR2(500),
   PARENTDATAOBJECTID   VARCHAR2(40)                   default '0' not null,
   ORDERNUMBER          NUMBER(9)                      default 0,
   SUBTYPEID            VARCHAR2(40)                   default '0' not null,
   SUBTYPEPARENTID      VARCHAR2(1000),
   CREATERID            VARCHAR2(40),
   CREATETIME           DATE                           default SYSDATE,
   MODIFIERID           VARCHAR2(40),
   MODIFYTIME           DATE                           default SYSDATE,
   VERSION              VARCHAR2(10)                   default '1.0.0.0',
   FILEID               VARCHAR2(40),
   TASKID               VARCHAR2(40),
   EXECUTIONID          VARCHAR2(50)                   default '0',
   SRCDATAOBJECTID      VARCHAR2(50)                   default '0',
   SRCDATAOBJECTVERSION VARCHAR2(50),
   INOUT                NUMBER(9)                      default 0,
   UNIT                 VARCHAR2(50),
   DESCRIPTION          VARCHAR2(500),
   EXECUTIONTASKID      VARCHAR2(50),
   EXECUTIONTASKTYPE    NUMBER(4)                      default 1,
   WBSPROJECTID         VARCHAR2(50),
   constraint PK_DATAOBJECTID primary key (ID)
);

comment on table CWM_DATAOBJECT is
'����ʵ����';

comment on column CWM_DATAOBJECT.ID is
'ʵ��id';

comment on column CWM_DATAOBJECT.DATATYPE_ID is
'��������ID����������Ϊ(string,file.....)';

comment on column CWM_DATAOBJECT.DATAOBJECTNAME is
'����ʵ������';

comment on column CWM_DATAOBJECT.ISREF is
'ʵ���������ͣ� 1	�������ͣ�2 �������ͣ�4 �����8 ����ṹ��16 ö�����ͣ�32 ��չ���� ';

comment on column CWM_DATAOBJECT.PROJECTID is
'��Ŀid';

comment on column CWM_DATAOBJECT.DIMENSION is
'ά��';

comment on column CWM_DATAOBJECT.VALUE is
'����ֵ���ļ���';

comment on column CWM_DATAOBJECT.PARENTDATAOBJECTID is
'��ʵ������id';

comment on column CWM_DATAOBJECT.ORDERNUMBER is
'ͬ�����ݵ����';

comment on column CWM_DATAOBJECT.SUBTYPEID is
'ָ��CWM_SUB_TYPE��ID,�����������ͺ���չ���ͣ��������ͣ���IDһ��';

comment on column CWM_DATAOBJECT.SUBTYPEPARENTID is
'��ʵ����¼�ĸ�ʵ����¼ID';

comment on column CWM_DATAOBJECT.CREATERID is
'������';

comment on column CWM_DATAOBJECT.CREATETIME is
'����ʱ��';

comment on column CWM_DATAOBJECT.MODIFIERID is
'�޸���';

comment on column CWM_DATAOBJECT.MODIFYTIME is
'�޸�ʱ��';

comment on column CWM_DATAOBJECT.VERSION is
'�汾(��ʼ�汾Ϊ1.0.0.0),�޸�һ�α��棬ĩλ�汾��1 �����ύһ�Σ������ڶ�λ�汾��һ��1.0.2.0, ';

comment on column CWM_DATAOBJECT.FILEID is
'�ļ�id';

comment on column CWM_DATAOBJECT.TASKID is
'��������ID����ĿID ';

comment on column CWM_DATAOBJECT.EXECUTIONID is
'������������ʵ��ID';

comment on column CWM_DATAOBJECT.SRCDATAOBJECTID is
'����Դ��ʵ��ID';

comment on column CWM_DATAOBJECT.SRCDATAOBJECTVERSION is
'����Դ�İ汾 ';

comment on column CWM_DATAOBJECT.INOUT is
'�������������־��0��ʾ���룬1��ʾ����ȣ�2��ʾ�������';

comment on column CWM_DATAOBJECT.UNIT is
'������λ';

comment on column CWM_DATAOBJECT.DESCRIPTION is
'��������';

comment on column CWM_DATAOBJECT.EXECUTIONTASKID is
'��������ʵ��ID�����޸�����ʵ��ID,.....';

comment on column CWM_DATAOBJECT.EXECUTIONTASKTYPE is
'1:��ִͨ����������2.�޸�����';

comment on column CWM_DATAOBJECT.WBSPROJECTID is
'��������';

/*==============================================================*/
/* Table: CWM_DATAOBJECT_OLDVERSION                             */
/*==============================================================*/
create table CWM_DATAOBJECT_OLDVERSION  (
   ID                   VARCHAR2(40)                    not null,
   DATAOBJECTID         VARCHAR2(40)                    not null,
   DATATYPEID           VARCHAR2(50),
   DATAOBJECTNAME       VARCHAR2(200)                   not null,
   ISREF                NUMBER(8)                      default 0,
   PROJECTID            VARCHAR2(40),
   DIMENSION            VARCHAR2(50)                   default '1',
   VALUE                VARCHAR2(500),
   PARENTDATAOBJECTID   VARCHAR2(40)                   default '0' not null,
   ORDERNUMBER          NUMBER(9)                      default 0,
   SUBTYPEID            VARCHAR2(40)                   default '0' not null,
   SUBTYPEPARENTID      VARCHAR2(1000),
   CREATERID            VARCHAR2(40),
   CREATETIME           DATE                           default SYSDATE,
   MODIFIERID           VARCHAR2(40),
   MODIFYTIME           DATE                           default SYSDATE,
   VERSION              VARCHAR2(10)                   default '1.0.0.0',
   FILEID               VARCHAR2(40),
   ISDELETED            NUMBER(9)                      default 0 not null,
   TASKID               VARCHAR2(40),
   EXECUTIONID          VARCHAR2(50),
   SRCDATAOBJECTID      VARCHAR2(50)                   default '0',
   SRCDATAOBJECTVERSION VARCHAR2(50),
   INOUT                NUMBER(9)                      default 0,
   UNIT                 VARCHAR2(50),
   DESCRIPTION          VARCHAR2(500),
   EXECUTIONTASKID      VARCHAR2(50),
   EXECUTIONTASKTYPE    NUMBER(4)                      default 1,
   WBSPROJECTID         VARCHAR2(50)
);

comment on table CWM_DATAOBJECT_OLDVERSION is
'����ʵ����ʷ�汾��';

comment on column CWM_DATAOBJECT_OLDVERSION.ID is
'Ψһid';

comment on column CWM_DATAOBJECT_OLDVERSION.DATAOBJECTID is
'����ʵ��id';

comment on column CWM_DATAOBJECT_OLDVERSION.DATATYPEID is
'��������id����������Ϊ string��file....';

comment on column CWM_DATAOBJECT_OLDVERSION.DATAOBJECTNAME is
'����ʵ������';

comment on column CWM_DATAOBJECT_OLDVERSION.ISREF is
'ʵ���������ͣ� 1	�������ͣ�2 �������ͣ�4 �����8 ����ṹ��16 ö�����ͣ�32 ��չ���� ';

comment on column CWM_DATAOBJECT_OLDVERSION.PROJECTID is
'��Ŀid';

comment on column CWM_DATAOBJECT_OLDVERSION.DIMENSION is
'ά��';

comment on column CWM_DATAOBJECT_OLDVERSION.VALUE is
'����ֵ���ļ���';

comment on column CWM_DATAOBJECT_OLDVERSION.PARENTDATAOBJECTID is
'������id';

comment on column CWM_DATAOBJECT_OLDVERSION.ORDERNUMBER is
'ͬ�����ݵ����';

comment on column CWM_DATAOBJECT_OLDVERSION.SUBTYPEID is
'ָ��CWM_SUB_TYPE��ID,�����������ͺ���չ���ͣ��������ͣ���IDһ��';

comment on column CWM_DATAOBJECT_OLDVERSION.SUBTYPEPARENTID is
'��ʵ����¼�ĸ�ʵ����¼ID';

comment on column CWM_DATAOBJECT_OLDVERSION.CREATERID is
'������';

comment on column CWM_DATAOBJECT_OLDVERSION.CREATETIME is
'����ʱ��';

comment on column CWM_DATAOBJECT_OLDVERSION.MODIFIERID is
'�޸���';

comment on column CWM_DATAOBJECT_OLDVERSION.MODIFYTIME is
'�޸�ʱ��';

comment on column CWM_DATAOBJECT_OLDVERSION.VERSION is
'�汾(��ʼ�汾Ϊ1.0.0.0),�޸�һ�α��棬ĩλ�汾��1 �����ύһ�Σ������ڶ�λ�汾��һ��1.0.2.0,';

comment on column CWM_DATAOBJECT_OLDVERSION.FILEID is
'�ļ�id';

comment on column CWM_DATAOBJECT_OLDVERSION.ISDELETED is
'ɾ����־��0δɾ����1ɾ����';

comment on column CWM_DATAOBJECT_OLDVERSION.TASKID is
'��������ʵ������ID';

comment on column CWM_DATAOBJECT_OLDVERSION.EXECUTIONID is
'����������ʵ��ID';

comment on column CWM_DATAOBJECT_OLDVERSION.SRCDATAOBJECTID is
'����Դ��ʵ��ID';

comment on column CWM_DATAOBJECT_OLDVERSION.SRCDATAOBJECTVERSION is
'����Դ�İ汾';

comment on column CWM_DATAOBJECT_OLDVERSION.INOUT is
'�������������־��0��ʾ���룬1��ʾ�����2��ʾ���������';

comment on column CWM_DATAOBJECT_OLDVERSION.UNIT is
'������λ';

comment on column CWM_DATAOBJECT_OLDVERSION.DESCRIPTION is
'��������';

comment on column CWM_DATAOBJECT_OLDVERSION.EXECUTIONTASKID is
'��������ʵ��ID�����޸�����ʵ��ID,.....';

comment on column CWM_DATAOBJECT_OLDVERSION.EXECUTIONTASKTYPE is
'1:��ִͨ����������2.�޸�����';

comment on column CWM_DATAOBJECT_OLDVERSION.WBSPROJECTID is
'��������';

/*==============================================================*/
/* Table: CWM_DATARELATION                                      */
/*==============================================================*/
create table CWM_DATARELATION  (
   ID                   VARCHAR2(40)                    not null,
   PROJECTID            VARCHAR2(40),
   SRCDATAOBJECTID      VARCHAR2(40),
   SRCDATAOBJECTVESION  VARCHAR2(50),
   DESTDATAOBJECTID     VARCHAR2(40),
   DESTDATAOBJECTVERSION VARCHAR2(50),
   CREATETIME           DATE                           default SYSDATE,
   USERID               NUMBER(19),
   SRCTASKID            VARCHAR2(40),
   DESTTASKID           VARCHAR2(40),
   ISNEWEST             NUMBER(9)                      default 1 not null,
   TRANSTR              VARCHAR2(500),
   ISDELETED            NUMBER(8)                      default 0,
   constraint PK_DATARELATION_ID primary key (ID)
);

comment on table CWM_DATARELATION is
'����ʵ��������ϵ��';

comment on column CWM_DATARELATION.ID is
'Ψһid';

comment on column CWM_DATARELATION.PROJECTID is
'��Ŀid';

comment on column CWM_DATARELATION.SRCDATAOBJECTID is
'Դ����id';

comment on column CWM_DATARELATION.SRCDATAOBJECTVESION is
'Դ����ʵ���İ汾';

comment on column CWM_DATARELATION.DESTDATAOBJECTID is
'Ŀ������id';

comment on column CWM_DATARELATION.DESTDATAOBJECTVERSION is
'Ŀ������ʵ���İ汾';

comment on column CWM_DATARELATION.CREATETIME is
'����ʱ��';

comment on column CWM_DATARELATION.USERID is
'������id';

comment on column CWM_DATARELATION.SRCTASKID is
'Դ����ID';

comment on column CWM_DATARELATION.DESTTASKID is
'Ŀ������ID';

comment on column CWM_DATARELATION.ISNEWEST is
'ӳ���Ƿ�����,1--�ǣ�0����';

comment on column CWM_DATARELATION.TRANSTR is
'����ӳ��ת������';

comment on column CWM_DATARELATION.ISDELETED is
'0��ʾδɾ����1��ʾɾ��';

/*==============================================================*/
/* Table: CWM_DATASUBTYPE                                       */
/*==============================================================*/
create table CWM_DATASUBTYPE  (
   ID                   VARCHAR2(50)                    not null,
   SUBTYPECODE          VARCHAR2(40)                    not null,
   DATATYPE             VARCHAR2(50)                    not null,
   DATASUBNAME          VARCHAR2(200)                   not null,
   ISREF                NUMBER(8)                      default 0 not null,
   DATATYPE_ID          VARCHAR2(40)                    not null,
   DIMENSION            VARCHAR2(50)                   default '1' not null,
   VALUE                VARCHAR2(4000),
   ORDERNUMBER          NUMBER(9)                      default 0 not null,
   CREATETIME           DATE                           default SYSDATE,
   USERID               NUMBER(19),
   STATUS               NUMBER(20)                     default 0,
   VERSION              NUMBER(19)                     default 1 not null,
   ISNEWEST             NUMBER(9)                      default 0 not null,
   FILEID               VARCHAR2(40),
   UNIT                 VARCHAR2(100),
   constraint PK_CWM_DATASUBTYPE primary key (ID)
);

comment on table CWM_DATASUBTYPE is
'�������͵��ӱ�';

comment on column CWM_DATASUBTYPE.ID is
'��������';

comment on column CWM_DATASUBTYPE.SUBTYPECODE is
'�����Ӷ������';

comment on column CWM_DATASUBTYPE.DATATYPE is
'��������id  ������ �������͵�(string,boolean....)';

comment on column CWM_DATASUBTYPE.DATASUBNAME is
'��������������';

comment on column CWM_DATASUBTYPE.ISREF is
'������:   1 �������� ��2 ��չ���ͣ� 4 ö������ ��8�������� ��16 ���飻';

comment on column CWM_DATASUBTYPE.DATATYPE_ID is
'������CWM_DATATYPE��ID';

comment on column CWM_DATASUBTYPE.DIMENSION is
'ά��';

comment on column CWM_DATASUBTYPE.VALUE is
'Ĭ��ֵ';

comment on column CWM_DATASUBTYPE.ORDERNUMBER is
'ͬ������������';

comment on column CWM_DATASUBTYPE.CREATETIME is
'����ʱ��';

comment on column CWM_DATASUBTYPE.USERID is
'������id';

comment on column CWM_DATASUBTYPE.STATUS is
'����״̬��0�����У�1�����У�2�ѷ�����3�޸��У�4�ѷ���';

comment on column CWM_DATASUBTYPE.VERSION is
'�������Ͱ汾';

comment on column CWM_DATASUBTYPE.ISNEWEST is
'�Ƿ����·����档1���°�';

comment on column CWM_DATASUBTYPE.FILEID is
'�ļ�id';

comment on column CWM_DATASUBTYPE.UNIT is
'��λ����Ӣ�����ơ�';

/*==============================================================*/
/* Table: CWM_DATATYPE                                          */
/*==============================================================*/
create table CWM_DATATYPE  (
   ID                   NUMBER(8)                       not null,
   DATATYPECODE         VARCHAR2(20)                    not null,
   DATATYPENAME         VARCHAR2(200)                   not null,
   DATATYPE             VARCHAR2(100)                   not null,
   DESCRIPTION          VARCHAR2(500),
   RANK                 NUMBER(4)                      default 4 not null,
   ICON                 VARCHAR2(4000),
   VERSION              NUMBER(19)                     default 1 not null,
   CREATETIME           DATE,
   USERID               NUMBER(19),
   ISNEWEST             NUMBER(9)                      default 0 not null,
   CHECKSTR             VARCHAR2(500),
   STATUS               NUMBER(20)                     default 0 not null,
   BIND_PD_ID           VARCHAR2(500),
   constraint PK_CWM_DATATYPE primary key (ID)
);

comment on table CWM_DATATYPE is
'�������ͱ�';

comment on column CWM_DATATYPE.ID is
'��������';

comment on column CWM_DATATYPE.DATATYPECODE is
'��������code';

comment on column CWM_DATATYPE.DATATYPENAME is
'����';

comment on column CWM_DATATYPE.DATATYPE is
'���ͣ�string �ַ�����integer ���Σ�boolean �������ͣ�file �ļ����ͣ�date �������ͣ�double ˫���ȸ�����ֵ���ͣ�dataset ����ṹ����';

comment on column CWM_DATATYPE.DESCRIPTION is
'������Ϣ';

comment on column CWM_DATATYPE.RANK is
'��������: 1 �������ͣ�2 ��չ���ͣ�4ö�����ͣ�8 ��������';

comment on column CWM_DATATYPE.ICON is
'ͼ�ꡣ��ͼƬ��ַ�������زĿ⣬���زĿ�ѡȡ��';

comment on column CWM_DATATYPE.VERSION is
'�汾';

comment on column CWM_DATATYPE.CREATETIME is
'����ʱ��';

comment on column CWM_DATATYPE.USERID is
'������id';

comment on column CWM_DATATYPE.ISNEWEST is
'�Ƿ����·����档1���°�';

comment on column CWM_DATATYPE.CHECKSTR is
'ֵ������ʽ��������������ʽ����ʽ��';

comment on column CWM_DATATYPE.STATUS is
'����״̬��0 �����У�1 �����У�2 �ѷ�����3 �޸��У�4 �ѷ���';

comment on column CWM_DATATYPE.BIND_PD_ID is
'�����̶���ID';

/*==============================================================*/
/* Table: CWM_FILE                                              */
/*==============================================================*/
create table CWM_FILE  (
   SCHEMAID             VARCHAR2(38),
   TABLEID              VARCHAR2(38),
   FILEID               VARCHAR2(16)                    not null,
   FILENAME             VARCHAR2(100),
   FILEDESCRIPTION      VARCHAR2(200),
   FILETYPE             VARCHAR2(100),
   FILELOCATION         VARCHAR2(200),
   FILESIZE             NUMBER,
   PARSE_RULE           VARCHAR2(2),
   UPLOAD_USER_ID       VARCHAR2(20),
   UPLOAD_DATE          DATE,
   DELETE_USER_ID       VARCHAR2(20),
   DELETE_DATE          DATE,
   DATAID               VARCHAR2(38),
   FINALNAME            VARCHAR2(100),
   EDITION              VARCHAR2(10),
   IS_VALID             NUMBER(1),
   FILESECRECY          VARCHAR2(50),
   UPLOAD_USER_MAC      VARCHAR2(17),
   UPLOAD_STATUS        VARCHAR2(2),
   FILE_FOLDER          VARCHAR2(1000),
   IS_FOLD_FILE         NUMBER                         default 0,
   IS_WHOLE_SEARCH      NUMBER(1)                      default 0,
   IS_DATA_FILE         NUMBER(1)                      default 0,
   CWM_FOLDER_ID        VARCHAR2(38),
   CONVER_STATE         VARCHAR2(38),
   constraint PK_CWM_FILE primary key (FILEID)
);

comment on table CWM_FILE is
'������';

comment on column CWM_FILE.SCHEMAID is
'�ļ������������͵�ID';

comment on column CWM_FILE.TABLEID is
'�ļ������������ID';

comment on column CWM_FILE.FILEID is
'����ID';

comment on column CWM_FILE.FILENAME is
'�ļ�����';

comment on column CWM_FILE.FILEDESCRIPTION is
'����';

comment on column CWM_FILE.FILETYPE is
'�ļ���׺��';

comment on column CWM_FILE.FILELOCATION is
'�ļ����Ŀ¼';

comment on column CWM_FILE.FILESIZE is
'�ļ���С';

comment on column CWM_FILE.PARSE_RULE is
'����';

comment on column CWM_FILE.UPLOAD_USER_ID is
'�ϴ��û�ID';

comment on column CWM_FILE.UPLOAD_DATE is
'�ϴ�ʱ��';

comment on column CWM_FILE.DELETE_USER_ID is
'ɾ���û�ID';

comment on column CWM_FILE.DELETE_DATE is
'ɾ��ʱ��';

comment on column CWM_FILE.DATAID is
'�ļ��������ݼ�¼��ID';

comment on column CWM_FILE.FINALNAME is
'�ļ���ŵ������ļ���';

comment on column CWM_FILE.EDITION is
'�ļ��汾';

comment on column CWM_FILE.IS_VALID is
'�Ƿ���Ч';

comment on column CWM_FILE.FILESECRECY is
'�ļ��ܼ�';

comment on column CWM_FILE.UPLOAD_USER_MAC is
'�ϴ��ͻ��˵�MAC��ַ';

comment on column CWM_FILE.UPLOAD_STATUS is
'�ϴ�״̬��0��ʾδ��ɣ�1��ʾ�ϴ����';

comment on column CWM_FILE.FILE_FOLDER is
'�ļ�Ŀ¼��Ϣ';

comment on column CWM_FILE.IS_FOLD_FILE is
'�Ƿ�����ļ�Ŀ¼��Ϣ��Ĭ��0��1��ʾ���ļ������ļ�Ŀ¼��Ϣ';

comment on column CWM_FILE.IS_WHOLE_SEARCH is
'�ļ��Ƿ����ȫ�ļ�����0Ϊ���ܣ�1Ϊ�ܣ�Ĭ��Ϊ0';

comment on column CWM_FILE.IS_DATA_FILE is
'�ļ��Ƿ��ܽ������ݴ���0Ϊ���ܣ�1Ϊ�ܣ�Ĭ��Ϊ0';

comment on column CWM_FILE.CWM_FOLDER_ID is
'�ļ���Id';

comment on column CWM_FILE.CONVER_STATE is
'ת��״̬';

/*==============================================================*/
/* Table: CWM_FILE_GROUP                                        */
/*==============================================================*/
create table CWM_FILE_GROUP  (
   ID                   VARCHAR2(38),
   GROUP_NAME           VARCHAR2(38),
   GROUP_TYPE           VARCHAR2(38),
   IS_SHOW              NUMBER(1)
);

comment on table CWM_FILE_GROUP is
'���������';

comment on column CWM_FILE_GROUP.ID is
'����ID';

comment on column CWM_FILE_GROUP.GROUP_NAME is
'��������';

comment on column CWM_FILE_GROUP.GROUP_TYPE is
'��������';

comment on column CWM_FILE_GROUP.IS_SHOW is
'�Ƿ�չ��';

/*==============================================================*/
/* Table: CWM_FOLDER                                            */
/*==============================================================*/
create table CWM_FOLDER  (
   ID                   VARCHAR2(38),
   DEL_FLAG             NUMBER(1),
   ADD_FLAG             NUMBER(1),
   EDIT_FLAG            NUMBER(1),
   CWM_FOLDER_ID        VARCHAR2(38),
   CWM_TABLES_ID        VARCHAR2(38),
   RECORD_ID            VARCHAR2(38),
   FOLDER_NAME          VARCHAR2(200)
);

comment on table CWM_FOLDER is
'�ļ��б�';

comment on column CWM_FOLDER.ID is
'ID';

comment on column CWM_FOLDER.DEL_FLAG is
'�Ƿ����ɾ��';

comment on column CWM_FOLDER.ADD_FLAG is
'�Ƿ�����������ļ���';

comment on column CWM_FOLDER.EDIT_FLAG is
'�Ƿ�����޸�';

comment on column CWM_FOLDER.CWM_FOLDER_ID is
'�ļ��еĸ��ļ���';

comment on column CWM_FOLDER.CWM_TABLES_ID is
'�ļ�������Table';

comment on column CWM_FOLDER.RECORD_ID is
'�ļ���������¼';

comment on column CWM_FOLDER.FOLDER_NAME is
'�ļ�������';

