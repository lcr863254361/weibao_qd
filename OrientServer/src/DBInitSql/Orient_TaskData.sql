--  -------------------------------------------------- 
--  研发数据类型定义库结构
--  Created On : 星期三, 04 六月, 2014 
--  Author       : zhulongchao 
--  -------------------------------------------------- 
DROP TABLE CWM_DATATYPE CASCADE CONSTRAINTS ;
CREATE TABLE CWM_DATATYPE ( 
	ID NUMBER(8) NOT NULL,    -- 自增主键 
	DATATYPECODE VARCHAR2(20) NOT NULL,
	DATATYPENAME VARCHAR2(200) NOT NULL,    -- 名称 
	DATATYPE VARCHAR2(100) NOT NULL,    -- 类型：string 字符串；integer 整形；boolean 布尔类型；file 文件类型；date 日期类型，double 双精度浮点数值类型；dataset 物理结构类型 
	DESCRIPTION VARCHAR2(500),    -- 描述信息 
	RANK NUMBER(4) DEFAULT 4 NOT NULL,    -- 数据类型: 1 基本类型；2 扩展类型；4枚举类型；8 复杂类型
	ICON VARCHAR2(4000),    -- 图标。（图片地址，建立素材库，从素材库选取） 
	VERSION NUMBER(8) DEFAULT 1 NOT NULL,    -- 版本 
	CREATETIME DATE,    -- 创建时间 
	USERID VARCHAR2(40),    -- 创建人id 
	ISNEWEST NUMBER(9) DEFAULT 0 NOT NULL,    -- 是否最新发布版。1最新版 
	CHECKSTR VARCHAR2(500),    -- 值域检查表达式：可以是正则表达式，公式等 
	STATUS NUMBER(4) DEFAULT 0 NOT NULL    -- 数据状态。0 编制中；1 审批中；2 已发布；3 修改中；4 已废弃 
) ;
COMMENT ON TABLE CWM_DATATYPE
    IS '数据类型表' ;

COMMENT ON COLUMN CWM_DATATYPE.ID
    IS '自增主键' ;

COMMENT ON COLUMN CWM_DATATYPE.DATATYPENAME
    IS '名称' ;

COMMENT ON COLUMN CWM_DATATYPE.DATATYPE
    IS '类型：string 字符串；integer 整形；boolean 布尔类型；file 文件类型；date 日期类型，double 双精度浮点数值类型；dataset 物理结构类型' ;

COMMENT ON COLUMN CWM_DATATYPE.DESCRIPTION
    IS '描述信息' ;


COMMENT ON COLUMN CWM_DATATYPE.RANK
    IS '数据类型: 1 基本类型；2 扩展类型；4枚举类型；8 复杂类型' ;
    
COMMENT ON COLUMN CWM_DATATYPE.ICON
    IS '图标。（图片地址，建立素材库，从素材库选取）' ;

COMMENT ON COLUMN CWM_DATATYPE.VERSION
    IS '版本' ;

COMMENT ON COLUMN CWM_DATATYPE.CREATETIME
    IS '创建时间' ;

COMMENT ON COLUMN CWM_DATATYPE.USERID
    IS '创建人id' ;

COMMENT ON COLUMN CWM_DATATYPE.ISNEWEST
    IS '是否最新发布版。1最新版' ;

COMMENT ON COLUMN CWM_DATATYPE.CHECKSTR
    IS '值域检查表达式：可以是正则表达式，公式等' ;

COMMENT ON COLUMN CWM_DATATYPE.STATUS
    IS '数据状态。0 编制中；1 审批中；2 已发布；3 修改中；4 已废弃' ;

ALTER TABLE CWM_DATATYPE ADD CONSTRAINT PK_CWM_DATATYPE 
	PRIMARY KEY (ID) ;
	
CREATE SEQUENCE SEQ_CWM_DATATYPE
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;	
	
DROP TABLE CWM_DATASUBTYPE CASCADE CONSTRAINTS ;
CREATE TABLE CWM_DATASUBTYPE ( 
	ID VARCHAR2(50) NOT NULL,    -- 自增主键 
	SUBTYPECODE VARCHAR2(40) NOT NULL,    -- 数据子对象编码 
	DATATYPE VARCHAR2(50) NOT NULL,    -- 数据类型id  或者是 基本类型的(string,boolean....) 
	DATASUBNAME VARCHAR2(200) NOT NULL,    -- 数据子类型名称 
	ISREF NUMBER(8) DEFAULT 0 NOT NULL,    -- 子类型:   1 基础类型 ；2 扩展类型； 4 枚举类型 ；8物理类型 ；16 数组；	  
	DATATYPE_ID VARCHAR2(40) NOT NULL,    -- 关联的CWM_DATATYPE的ID 
	DIMENSION VARCHAR2(50) DEFAULT 1 NOT NULL,    -- 维数 
	VALUE VARCHAR2(4000),    -- 默认值 
	ORDERNUMBER NUMBER(9) DEFAULT 0 NOT NULL,    -- 同级参数的排序 
	CREATETIME DATE DEFAULT sysdate,    -- 创建时间 
	USERID VARCHAR2(40),    -- 创建人id 
	STATUS NUMBER(4) DEFAULT 0,    -- 数据状态。0编制中，1审批中，2已发布，3修改中，4已废弃 
	VERSION NUMBER(8) DEFAULT 1 NOT NULL,    -- 数据类型版本 
	ISNEWEST NUMBER(9) DEFAULT 0 NOT NULL,    -- 是否最新发布版。1最新版 
	FILEID VARCHAR2(40),    -- 文件id 
	UNIT VARCHAR2(100)    -- 单位。是英文名称。 
) ;
COMMENT ON TABLE CWM_DATASUBTYPE
    IS '数据类型的子表' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.ID
    IS '自增主键' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.SUBTYPECODE
    IS '数据子对象编码' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.DATATYPE
    IS '数据类型id  或者是 基本类型的(string,boolean....)' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.DATASUBNAME
    IS '数据子类型名称' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.ISREF
    IS '子类型:   1 基础类型 ；2 扩展类型； 4 枚举类型 ；8物理类型 ；16 数组；' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.DATATYPE_ID
    IS '关联的CWM_DATATYPE的ID' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.DIMENSION
    IS '维数' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.VALUE
    IS '默认值' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.ORDERNUMBER
    IS '同级参数的排序' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.CREATETIME
    IS '创建时间' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.USERID
    IS '创建人id' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.STATUS
    IS '数据状态。0编制中，1审批中，2已发布，3修改中，4已废弃' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.VERSION
    IS '数据类型版本' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.ISNEWEST
    IS '是否最新发布版。1最新版' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.FILEID
    IS '文件id' ;

COMMENT ON COLUMN CWM_DATASUBTYPE.UNIT
    IS '单位。是英文名称。' ;

ALTER TABLE CWM_DATASUBTYPE ADD CONSTRAINT PK_CWM_DATASUBTYPE 
	PRIMARY KEY (ID) ;

CREATE SEQUENCE SEQ_CWM_DATASUBTYPE
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
  
SET DEFINE OFF;
Insert into CWM_DATATYPE
   (ID, DATATYPECODE, DATATYPENAME, DATATYPE, DESCRIPTION, RANK, VERSION, ISNEWEST, STATUS)
 Values 
   (1,'string', '字符串', 'string', '字符串', 1, 1, 1, 2);
Insert into CWM_DATATYPE
   (ID, DATATYPECODE, DATATYPENAME, DATATYPE, DESCRIPTION, RANK, VERSION, ISNEWEST, STATUS)
 Values
   (2,'integer', '整数', 'integer', '整数', 1, 1, 1, 2);
Insert into CWM_DATATYPE
   (ID, DATATYPECODE, DATATYPENAME, DATATYPE, DESCRIPTION, RANK, VERSION, ISNEWEST, STATUS)
 Values
   (3,'boolean', '布尔', 'boolean', '布尔数，只有两种取值', 1, 1, 1, 2);
Insert into CWM_DATATYPE
   (ID, DATATYPECODE, DATATYPENAME, DATATYPE, DESCRIPTION, RANK, VERSION, ISNEWEST, STATUS)
 Values
   (4,'double', '实数', 'double', '支持双精度类型', 1, 1, 1, 2);
Insert into CWM_DATATYPE
   (ID, DATATYPECODE, DATATYPENAME, DATATYPE, DESCRIPTION, RANK, VERSION, ISNEWEST, STATUS)
 Values
   (5,'file', '文件', 'file', '文件类型', 1, 1, 1, 2);
Insert into CWM_DATATYPE
   (ID, DATATYPECODE, DATATYPENAME, DATATYPE, DESCRIPTION, RANK, VERSION, ISNEWEST, STATUS)
 Values
   (6,'date', '日期', 'date', '日期', 1, 1, 1, 2);
COMMIT;

--  -------------------------------------------------- 
--  研发数据实例库结构
--  Created On : 星期三, 04 六月, 2014 
--  Author       : zhulongchao 
--  -------------------------------------------------- 
DROP TABLE CWM_DATAOBJECT CASCADE CONSTRAINTS ;
CREATE TABLE CWM_DATAOBJECT ( 
	ID VARCHAR2(40) NOT NULL,    -- 实例id 
	DATATYPE_ID VARCHAR2(50),    -- 数据类型ID，基本类型为(string,file.....) 
	DATAOBJECTNAME VARCHAR2(200) NOT NULL,    -- 数据实例名称 
	ISREF NUMBER(8) DEFAULT 0,    -- 实例引用类型： 1	基本类型；2 数组类型；4 数组项；8 物理结构；16 枚举类型；32 扩展属性  
	PROJECTID VARCHAR2(40),    -- 项目id 
	DIMENSION VARCHAR2(50) DEFAULT 1,    -- 维数 
	VALUE VARCHAR2(500),    -- 参数值或文件名 
	PARENTDATAOBJECTID VARCHAR2(40) DEFAULT 0 NOT NULL,    -- 父实例对象id 
	ORDERNUMBER NUMBER(9) DEFAULT 0,    -- 同级数据的序号 
	SUBTYPEID VARCHAR2(40) DEFAULT 0 NOT NULL,    -- 指向CWM_SUB_TYPE的ID,用于物理类型和扩展类型，基本类型，与ID一致 
	SUBTYPEPARENTID VARCHAR2(1000),    -- 子实例记录的父实例记录ID 
	CREATERID VARCHAR2(40),    -- 创建人 
	CREATETIME DATE DEFAULT sysdate,    -- 创建时间 
	MODIFIERID VARCHAR2(40),    -- 修改人 
	MODIFYTIME DATE DEFAULT sysdate,    -- 修改时间 
	VERSION VARCHAR(10) DEFAULT '1.0.0.0',    -- 版本(初始版本为1.0.0.0),修改一次保存，末位版本增1 任务提交一次，倒数第二位版本升一，1.0.2.0,  
	FILEID VARCHAR2(40),    -- 文件id 
	TASKID VARCHAR2(40),    -- 关联任务ID或项目ID （projectdatas_29421799/taskdatas_29421759) 
	EXECUTIONID VARCHAR2(50) DEFAULT 0,    -- 所属流程任务实例ID 
	SRCDATAOBJECTID VARCHAR2(50) DEFAULT 0,    -- 数据源的实例ID 
	SRCDATAOBJECTVERSION VARCHAR2(50),    -- 数据源的版本  
	INOUT NUMBER(9) DEFAULT 0,    -- 数据输入输出标志。0表示输入，1表示输出等，2表示输入输出 
    UNIT VARCHAR2(50),
    DESCRIPTION VARCHAR2(500),
    EXECUTIONTASKID VARCHAR2(50),
    EXECUTIONTASKTYPE NUMBER(4) DEFAULT 1
) ;
COMMENT ON TABLE CWM_DATAOBJECT
    IS '数据实例表' ;

COMMENT ON COLUMN CWM_DATAOBJECT.ID
    IS '实例id' ;

COMMENT ON COLUMN CWM_DATAOBJECT.DATATYPE_ID
    IS '数据类型ID，基本类型为(string,file.....)' ;

COMMENT ON COLUMN CWM_DATAOBJECT.DATAOBJECTNAME
    IS '数据实例名称' ;

COMMENT ON COLUMN CWM_DATAOBJECT.ISREF
    IS '实例引用类型： 1	基本类型；2 数组类型；4 数组项；8 物理结构；16 枚举类型；32 扩展属性 ' ;

COMMENT ON COLUMN CWM_DATAOBJECT.PROJECTID
    IS '项目id' ;

COMMENT ON COLUMN CWM_DATAOBJECT.DIMENSION
    IS '维数' ;

COMMENT ON COLUMN CWM_DATAOBJECT.VALUE
    IS '参数值或文件名' ;

COMMENT ON COLUMN CWM_DATAOBJECT.PARENTDATAOBJECTID
    IS '父实例对象id' ;

COMMENT ON COLUMN CWM_DATAOBJECT.ORDERNUMBER
    IS '同级数据的序号' ;

COMMENT ON COLUMN CWM_DATAOBJECT.SUBTYPEID
    IS '指向CWM_SUB_TYPE的ID,用于物理类型和扩展类型，基本类型，与ID一致' ;

COMMENT ON COLUMN CWM_DATAOBJECT.SUBTYPEPARENTID
    IS '子实例记录的父实例记录ID' ;

COMMENT ON COLUMN CWM_DATAOBJECT.CREATERID
    IS '创建人' ;

COMMENT ON COLUMN CWM_DATAOBJECT.CREATETIME
    IS '创建时间' ;

COMMENT ON COLUMN CWM_DATAOBJECT.MODIFIERID
    IS '修改人' ;

COMMENT ON COLUMN CWM_DATAOBJECT.MODIFYTIME
    IS '修改时间' ;

COMMENT ON COLUMN CWM_DATAOBJECT.VERSION
    IS '版本(初始版本为1.0.0.0),修改一次保存，末位版本增1 任务提交一次，倒数第二位版本升一，1.0.2.0, ' ;

COMMENT ON COLUMN CWM_DATAOBJECT.FILEID
    IS '文件id' ;

COMMENT ON COLUMN CWM_DATAOBJECT.TASKID
    IS '关联任务ID或项目ID （projectdatas_29421799/taskdatas_29421759)' ;

COMMENT ON COLUMN CWM_DATAOBJECT.EXECUTIONID
    IS '所属流程任务实例ID' ;

COMMENT ON COLUMN CWM_DATAOBJECT.SRCDATAOBJECTID
    IS '数据源的实例ID' ;

COMMENT ON COLUMN CWM_DATAOBJECT.SRCDATAOBJECTVERSION
    IS '数据源的版本 ' ;

COMMENT ON COLUMN CWM_DATAOBJECT.INOUT
    IS '数据输入输出标志。0表示输入，1表示输出等，2表示输入输出' ;
    
COMMENT ON COLUMN CWM_DATAOBJECT.EXECUTIONTASKID
    IS '流程任务实例ID或者修复任务实例ID,.....' ;

COMMENT ON COLUMN CWM_DATAOBJECT.EXECUTIONTASKTYPE
    IS '1:普通执行流程任务，2.修复任务' ;

ALTER TABLE CWM_DATAOBJECT ADD CONSTRAINT PK_DATAOBJECTID 
	PRIMARY KEY (ID) ;

DROP SEQUENCE SEQ_CWM_DATAOBJECT;	
CREATE SEQUENCE SEQ_CWM_DATAOBJECT
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;	

DROP TABLE CWM_DATAOBJECT_OLDVERSION CASCADE CONSTRAINTS ;
CREATE TABLE CWM_DATAOBJECT_OLDVERSION ( 
	ID VARCHAR2(40) NOT NULL,    -- 唯一id 
	DATAOBJECTID VARCHAR2(40) NOT NULL,    -- 数据实例id 
	DATATYPEID VARCHAR2(50),    -- 数据类型id，基础类型为 string，file.... 
	DATAOBJECTNAME VARCHAR2(200) NOT NULL,    -- 数据实例名称 
	ISREF NUMBER(8) DEFAULT 0,    -- 实例引用类型： 1	基本类型；2 数组类型；4 数组项；8 物理结构；16 枚举类型；32 扩展属性  
	PROJECTID VARCHAR2(40),    -- 项目id 
	DIMENSION VARCHAR2(50) DEFAULT 1,    -- 维数 
	VALUE VARCHAR2(500),    -- 参数值或文件名 
	PARENTDATAOBJECTID VARCHAR2(40) DEFAULT 0 NOT NULL,    -- 父对象id 
	ORDERNUMBER NUMBER(9) DEFAULT 0,    -- 同级数据的序号 
	SUBTYPEID VARCHAR2(40) DEFAULT 0 NOT NULL,    -- 指向CWM_SUB_TYPE的ID,用于物理类型和扩展类型，基本类型，与ID一致 
	SUBTYPEPARENTID VARCHAR2(1000),    -- 子实例记录的父实例记录ID 
	CREATERID VARCHAR2(40),    -- 创建人 
	CREATETIME DATE DEFAULT sysdate,    -- 创建时间 
	MODIFIERID VARCHAR2(40),    -- 修改人 
	MODIFYTIME DATE DEFAULT sysdate,    -- 修改时间 
	VERSION VARCHAR2(10) DEFAULT '1.0.0.0',    -- 版本(初始版本为1.0.0.0),修改一次保存，末位版本增1 任务提交一次，倒数第二位版本升一，1.0.2.0, 
	FILEID VARCHAR2(40),    -- 文件id 
	ISDELETED NUMBER(9) DEFAULT 0 NOT NULL,    -- 删除标志，0未删除，1删除。 
	TASKID VARCHAR2(40),    -- 关联任务ID或项目ID （projectdatas_29421799/taskdatas_29421759) 
	EXECUTIONID VARCHAR2(50),    -- 所属流程实例任务ID 
	SRCDATAOBJECTID VARCHAR2(50) DEFAULT 0,    -- 数据源的实例ID 
	SRCDATAOBJECTVERSION VARCHAR2(50),    -- 数据源的版本 
	INOUT NUMBER(9) DEFAULT 0,    -- 数据输入输出标志。0表示输入，1表示输出，2表示输入输出等。 
    UNIT VARCHAR2(50),
    DESCRIPTION VARCHAR2(500),
    EXECUTIONTASKID VARCHAR2(50),
    EXECUTIONTASKTYPE NUMBER(4) DEFAULT 1
) ;
COMMENT ON TABLE CWM_DATAOBJECT_OLDVERSION
    IS '数据实例历史版本表' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.ID
    IS '唯一id' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.DATAOBJECTID
    IS '数据实例id' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.DATATYPEID
    IS '数据类型id，基础类型为 string，file....' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.DATAOBJECTNAME
    IS '数据实例名称' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.ISREF
    IS '实例引用类型： 1	基本类型；2 数组类型；4 数组项；8 物理结构；16 枚举类型；32 扩展属性 ' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.PROJECTID
    IS '项目id' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.DIMENSION
    IS '维数' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.VALUE
    IS '参数值或文件名' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.PARENTDATAOBJECTID
    IS '父对象id' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.ORDERNUMBER
    IS '同级数据的序号' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.SUBTYPEID
    IS '指向CWM_SUB_TYPE的ID,用于物理类型和扩展类型，基本类型，与ID一致' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.SUBTYPEPARENTID
    IS '子实例记录的父实例记录ID' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.CREATERID
    IS '创建人' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.CREATETIME
    IS '创建时间' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.MODIFIERID
    IS '修改人' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.MODIFYTIME
    IS '修改时间' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.VERSION
    IS '版本(初始版本为1.0.0.0),修改一次保存，末位版本增1 任务提交一次，倒数第二位版本升一，1.0.2.0,' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.FILEID
    IS '文件id' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.ISDELETED
    IS '删除标志，0未删除，1删除。' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.TASKID
    IS '关联任务ID或项目ID （projectdatas_29421799/tas{"id":null,"totalProperty":4,"results":[{"id":"23","datatypeId":"2","dataobjectname":"e","isref":1,"projectid":null,"dimension":"1","value":null,"parentdataobjectid":"0","ordernumber":7,"subtypeid":"0","subtypeparentid":"0","createrid":null,"createtime":1403513821000,"modifierid":null,"modifytime":1403513821000,"version":"1.0.0.0","fileid":null,"taskid":"-1","executionid":null,"srcdataobjectid":null,"srcdataobjectversion":null,"inout":0,"unit":null,"description":null,"dataTypeShowName":"字符串","leaf":true,"isArray":false,"updateStatus":null,"delist":null,"delistJson":null,"children":null,"text":"e","uiProvider":"col","iconCls":"","parent":null,"disableCheck":false,"anode":null,"extendsTypeRealName":null,"realIsRef":null,"start":0,"limit":0,"datetime":null,"startDate":null,"endDate":null,"checkStr":null,"createrName":"","modifierName":"","isArrayItemChild":null,"unitAbbreviation":null,"unitLocaleName":null,"filterInsId":null,"dataObjectId":"23"},{"id":"22","datatypeId":"2","dataobjectname":"c","isref":1,"projectid":null,"dimension":"1","value":null,"parentdataobjectid":"0","ordernumber":6,"subtypeid":"0","subtypeparentid":"0","createrid":null,"createtime":1403513817000,"modifierid":null,"modifytime":1403513817000,"version":"1.0.0.0","fileid":null,"taskid":"-1","executionid":null,"srcdataobjectid":null,"srcdataobjectversion":null,"inout":0,"unit":null,"description":null,"dataTypeShowName":"字符串","leaf":true,"isArray":false,"updateStatus":null,"delist":null,"delistJson":null,"children":null,"text":"c","uiProvider":"col","iconCls":"","parent":null,"disableCheck":false,"anode":null,"extendsTypeRealName":null,"realIsRef":null,"start":0,"limit":0,"datetime":null,"startDate":null,"endDate":null,"checkStr":null,"createrName":"","modifierName":"","isArrayItemChild":null,"unitAbbreviation":null,"unitLocaleName":null,"filterInsId":null,"dataObjectId":"22"},{"id":"21","datatypeId":"2","dataobjectname":"a","isref":1,"projectid":null,"dimension":"1","value":null,"parentdataobjectid":"0","ordernumber":5,"subtypeid":"0","subtypeparentid":"0","createrid":null,"createtime":1403513795000,"modifierid":null,"modifytime":1403513795000,"version":"1.0.0.0","fileid":null,"taskid":"-1","executionid":null,"srcdataobjectid":null,"srcdataobjectversion":null,"inout":0,"unit":null,"description":null,"dataTypeShowName":"字符串","leaf":true,"isArray":false,"updateStatus":null,"delist":null,"delistJson":null,"children":null,"text":"a","uiProvider":"col","iconCls":"","parent":null,"disableCheck":false,"anode":null,"extendsTypeRealName":null,"realIsRef":null,"start":0,"limit":0,"datetime":null,"startDate":null,"endDate":null,"checkStr":null,"createrName":"","modifierName":"","isArrayItemChild":null,"unitAbbreviation":null,"unitLocaleName":null,"filterInsId":null,"dataObjectId":"21"},{"id":"20","datatypeId":"2","dataobjectname":"b","isref":1,"projectid":null,"dimension":"1","value":null,"parentdataobjectid":"0","ordernumber":4,"subtypeid":"0","subtypeparentid":"0","createrid":null,"createtime":1403513795000,"modifierid":null,"modifytime":1403513795000,"version":"1.0.0.0","fileid":null,"taskid":"-1","executionid":null,"srcdataobjectid":null,"srcdataobjectversion":null,"inout":0,"unit":null,"description":null,"dataTypeShowName":"字符CWM_DATAOBJECT_OLDVERSIONMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.EXECUTIONID
    IS '所属流程实例任务ID' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.SRCDATAOBJECTID
    IS '数据源的实例ID' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.SRCDATAOBJECTVERSION
    IS '数据源的版本' ;

COMMENT ON COLUMN CWM_DATAOBJECT_OLDVERSION.INOUT
    IS '数据输入输出标志。0表示输入，1表示输出，2表示输入输出等。' ;

ALTER TABLE CWM_DATAOBJECT_OLDVERSION ADD CONSTRAINT PK_DATAOBJECT_OLDREVISION_ID 
	PRIMARY KEY (ID) ;
	
DROP SEQUENCE SEQ_CWM_DATAOBJECT_OLDVERSION;	
CREATE SEQUENCE SEQ_CWM_DATAOBJECT_OLDVERSION
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
 


DROP TABLE CWM_DATARELATION CASCADE CONSTRAINTS ;
CREATE TABLE CWM_DATARELATION ( 
	ID VARCHAR2(40) NOT NULL,    -- 唯一id 
	PROJECTID VARCHAR2(40),    -- 项目id 
	SRCDATAOBJECTID VARCHAR2(40) NOT NULL,    -- 源数据id 
	SRCDATAOBJECTVESION VARCHAR2(50),    -- 源数据实例的版本 
	DESTDATAOBJECTID VARCHAR2(40),    -- 目标数据id 
	DESTDATAOBJECTVERSION VARCHAR2(50),    -- 目标数据实例的版本 
	CREATETIME DATE DEFAULT sysdate,    -- 创建时间 
	USERID VARCHAR2(40),    -- 创建人id 
	SRCTASKID VARCHAR2(40),    -- 源任务ID 
	DESTTASKID VARCHAR2(40),    -- 目标任务ID 
	ISNEWEST NUMBER(9) DEFAULT 1 NOT NULL,    -- 映射是否最新,1--是；0－否 
	TRANSTR VARCHAR2(500),    -- 数据映射转换规则 
	ISDELETED NUMBER(8) DEFAULT 0    -- 0表示未删除，1表示删除 
) ;


COMMENT ON TABLE CWM_DATARELATION
    IS '数据实例关联关系表' ;


COMMENT ON COLUMN CWM_DATARELATION.ID
    IS '唯一id' ;

COMMENT ON COLUMN CWM_DATARELATION.PROJECTID
    IS '项目id' ;

COMMENT ON COLUMN CWM_DATARELATION.SRCDATAOBJECTID
    IS '源数据id' ;

COMMENT ON COLUMN CWM_DATARELATION.SRCDATAOBJECTVESION
    IS '源数据实例的版本' ;

COMMENT ON COLUMN CWM_DATARELATION.DESTDATAOBJECTID
    IS '目标数据id' ;

COMMENT ON COLUMN CWM_DATARELATION.DESTDATAOBJECTVERSION
    IS '目标数据实例的版本' ;

COMMENT ON COLUMN CWM_DATARELATION.CREATETIME
    IS '创建时间' ;

COMMENT ON COLUMN CWM_DATARELATION.USERID
    IS '创建人id' ;

COMMENT ON COLUMN CWM_DATARELATION.SRCTASKID
    IS '源任务ID' ;

COMMENT ON COLUMN CWM_DATARELATION.DESTTASKID
    IS '目标任务ID' ;

COMMENT ON COLUMN CWM_DATARELATION.ISNEWEST
    IS '映射是否最新,1--是；0－否' ;

COMMENT ON COLUMN CWM_DATARELATION.TRANSTR
    IS '数据映射转换规则' ;

COMMENT ON COLUMN CWM_DATARELATION.ISDELETED
    IS '0表示未删除，1表示删除' ;

ALTER TABLE CWM_DATARELATION ADD CONSTRAINT PK_DATARELATION_ID 
	PRIMARY KEY (ID) ;

DROP SEQUENCE SEQ_CWM_DATARELATION;	
CREATE SEQUENCE SEQ_CWM_DATARELATION
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

DROP SEQUENCE SEQ_ORDER;	
CREATE SEQUENCE SEQ_ORDER
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
  
Insert into ORIENTEDM0701.CWM_SYS_FUNCTION
   (FUNCTIONID, CODE, NAME, PARENTID, URL, ADD_FLG, DEL_FLG, EDIT_FLG, POSITION, LOGTYPE, LOGSHOW, IS_SHOW, TBOM_FLG)
 Values
   (802, 'DataType', '研发数据类型', 8, '/OrientEDM/edmext/jsp/loadjscenter.jsp?dataType', '1', '1', '1', 5, '000000000', '111111111', 1, '0');
COMMIT;
 CREATE TABLE JBPM4_RDM_PARAMMAPPING
(
  ID            VARCHAR2(38),
  PDID          VARCHAR2(38),
  PROJECTID     VARCHAR2(38),
  SRCTASKID     VARCHAR2(38),
  TARGETTASKID  VARCHAR2(38),
  SRCPARAMID    VARCHAR2(38),
  TARGETPARAMID VARCHAR2(38)
)
