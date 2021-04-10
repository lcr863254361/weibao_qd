--*******************************************--
--              系统管理相关表格-                    --
--*******************************************--
CREATE TABLE CWM_SYS_ACCOUNT_STRATEGY
(
    ID                             NUMBER NOT NULL,
    STRATEGY_NAME                  VARCHAR2(100),
    STRATEGY_NOTE                  VARCHAR2(1000),
    STRATEGY_VALUE1                VARCHAR2(100),
    STRATEGY_VALUE2                VARCHAR2(100),
    IS_USE                         VARCHAR2(1),
    TYPE                           VARCHAR2(1),
    STRATEGY_VALUE                 VARCHAR2(100),
    CONSTRAINT SYS_C0011226 PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_SYS_DEPARTMENT
(
    ID                             VARCHAR2(38) NOT NULL,
    PID                            VARCHAR2(38) NOT NULL,
    NAME                           VARCHAR2(100) NOT NULL,
    FUNCTION                       VARCHAR2(500),
    NOTES                          VARCHAR2(1000),
    ADD_FLG                        VARCHAR2(1),
    DEL_FLG                        VARCHAR2(1),
    EDIT_FLG                       VARCHAR2(1),
    CONSTRAINT PK_CWM_SYS_DEPARTMENT PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_SYS_DEPARTMENT.ID IS '部门编号';
COMMENT ON COLUMN CWM_SYS_DEPARTMENT.PID IS '上级部门编号';
COMMENT ON COLUMN CWM_SYS_DEPARTMENT.NAME IS '部门名称';
COMMENT ON COLUMN CWM_SYS_DEPARTMENT.FUNCTION IS '部门职能';
COMMENT ON COLUMN CWM_SYS_DEPARTMENT.NOTES IS '备注';

CREATE TABLE CWM_SYS_FILE
(
    ID_                            NUMBER NOT NULL,
    NAME_                          VARCHAR2(100),
    TYPE_                          VARCHAR2(10),
    BLOB_VALUE_                    BLOB,
    CLOB_VALUE_                    CLOB,
    SB_ID_                         VARCHAR2(20),
    CONSTRAINT PK_CWM_SYS_FILE PRIMARY KEY (ID_) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_SYS_FILE.ID_ IS '主键';
COMMENT ON COLUMN CWM_SYS_FILE.NAME_ IS '名字';
COMMENT ON COLUMN CWM_SYS_FILE.TYPE_ IS '类型';
COMMENT ON COLUMN CWM_SYS_FILE.BLOB_VALUE_ IS '2进制文件';
COMMENT ON COLUMN CWM_SYS_FILE.CLOB_VALUE_ IS '大文本文件';
COMMENT ON COLUMN CWM_SYS_FILE.SB_ID_ IS 'CWM_SYS_SYSBG表的外键';

CREATE TABLE CWM_SYS_FUNCTION
(
    FUNCTIONID                     NUMBER NOT NULL,
    CODE                           VARCHAR2(10),
    NAME                           VARCHAR2(100),
    PARENTID                       NUMBER NOT NULL,
    URL                            VARCHAR2(400) NOT NULL,
    NOTES                          VARCHAR2(400),
    ADD_FLG                        VARCHAR2(1),
    DEL_FLG                        VARCHAR2(1),
    EDIT_FLG                       VARCHAR2(1),
    POSITION                       NUMBER,
    LOGTYPE                        VARCHAR2(15),
    LOGSHOW                        VARCHAR2(15),
    IS_SHOW                        NUMBER(1,0) DEFAULT 1,
	TBOM_FLG 					   VARCHAR2(1 BYTE) DEFAULT 0,
    CONSTRAINT PK_CWM_FUNCTION PRIMARY KEY (FUNCTIONID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_SYS_LOG
(
    ID                             NUMBER NOT NULL,
    OP_TYPE_ID                     VARCHAR2(2),
    OP_USER_ID                     VARCHAR2(20),
    OP_IP_ADDRESS                  VARCHAR2(15),
    OP_DATE                        DATE,
    OP_TARGET                      VARCHAR2(200),
    OP_REMARK                      VARCHAR2(500),
    OP_RESULT                      VARCHAR2(20),
    CONSTRAINT PK_CWM_SYS_LOG PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_SYS_OPERATION
(
    ID                             NUMBER NOT NULL,
    NAME                           VARCHAR2(100) NOT NULL,
    CONSTRAINT PK_CWM_SYS_OPERATION PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_CODETONAME
(
    ID                             VARCHAR2(20),
    NAME                           VARCHAR2(100),
    TYPEID                         VARCHAR2(10),
    TYPENAME                       VARCHAR2(100),
    REMARK                         VARCHAR2(200)
);


CREATE TABLE CWM_SYS_OVERALLOPERATONS
(
    ROLE_ID                        VARCHAR2(20) NOT NULL,
    OPERATION_IDS                  VARCHAR2(100) NOT NULL
);

CREATE TABLE CWM_SYS_PARAMETER
(
    ID                             NUMBER(8,0) NOT NULL,
    NAME                           VARCHAR2(50),
    DATATYPE                       VARCHAR2(50),
    VALUE                          VARCHAR2(100),
    DESCRIPTION                    VARCHAR2(150),
    CONSTRAINT SYS_C0011242 PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_SYS_PARTOPERATIONS
(
    ID                             NUMBER NOT NULL,
    ROLE_ID                        VARCHAR2(20),
    TABLE_ID                       VARCHAR2(20),
    COLUMN_ID                      VARCHAR2(20),
    OPERATIONS_ID                  VARCHAR2(100),
    FILTER                         VARCHAR2(4000),
    IS_TABLE                       VARCHAR2(1) NOT NULL,
    CONSTRAINT PK_CWM_SYS_PARTOPERATIONS PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_SYS_ROLE
(
    ID                             NUMBER NOT NULL,
    NAME                           VARCHAR2(100) NOT NULL,
    MEMO                           VARCHAR2(4000),
    TYPE                           VARCHAR2(1),
    STATUS                         VARCHAR2(1),
    FLG                            VARCHAR2(1),
    CONSTRAINT PK_CWM_SYS_ROLE PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_SYS_ROLE_ARITH
(
    ROLE_ID                        VARCHAR2(30),
    ARITH_ID                       VARCHAR2(30)
);

CREATE TABLE CWM_SYS_ROLE_FUNCTION_TBOM
(
  --角色ID
 	ROLE_ID            VARCHAR2(38 BYTE)          NOT NULL,
  --功能点ID
 	FUNCTION_ID            VARCHAR2(38 BYTE)          NOT NULL,
  --Tbom树ID
 	TBOM_ID            VARCHAR2(38 BYTE) ,
 	CONSTRAINT PK_CWM_SYS_ROLE_FUNCTION_TBOM PRIMARY KEY (ROLE_ID, FUNCTION_ID,TBOM_ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_SYS_ROLE_FUNCTION.ROLE_ID IS '角色ID';
COMMENT ON COLUMN CWM_SYS_ROLE_FUNCTION.FUNCTION_ID IS '功能点ID';
COMMENT ON COLUMN CWM_SYS_ROLE_FUNCTION.SORT_FLG IS 'web展现时，是否以此功能点为分类点';

CREATE TABLE CWM_SYS_ROLE_SCHEMA
(
    ROLE_ID                        VARCHAR2(100) NOT NULL,
    SCHEMA_ID                      VARCHAR2(100) NOT NULL
);

CREATE TABLE CWM_SYS_ROLE_TBOM
(
    ROLE_ID                        VARCHAR2(100) NOT NULL,
    TBOM_ID                        VARCHAR2(100) NOT NULL,
    ID                             VARCHAR2(38) NOT NULL
);

CREATE TABLE CWM_SYS_ROLE_USER
(
    ROLE_ID                        VARCHAR2(100) NOT NULL,
    USER_ID                        VARCHAR2(100) NOT NULL,
    CONSTRAINT PK_CWM_SYS_ROLE_USER PRIMARY KEY (ROLE_ID, USER_ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_SYS_USER
(
    ID                             VARCHAR2(20) NOT NULL,
    USER_NAME                      VARCHAR2(50) NOT NULL,
    ALL_NAME                       VARCHAR2(100) NOT NULL,
    PASSWORD                       VARCHAR2(100) NOT NULL,
    SEX                            VARCHAR2(5),
    PHONE                          VARCHAR2(15),
    POST                           VARCHAR2(50),
    SPECIALTY                      VARCHAR2(50),
    GRADE                          VARCHAR2(50),
    CREATE_TIME                    DATE NOT NULL,
    CREATE_USER                    VARCHAR2(50) NOT NULL,
    UPDATE_TIME                    DATE,
    UPDATE_USER                    VARCHAR2(50),
    NOTES                          VARCHAR2(1000),
    STATE                          VARCHAR2(1) NOT NULL,
    BIRTHDAY                       DATE,
    MOBILE                         VARCHAR2(15),
    FLG                            VARCHAR2(1),
    DEP_ID                         VARCHAR2(100),
    IS_DEL                         VARCHAR2(1),
    E_MAIL                         VARCHAR2(50),
    PASSWORD_SET_TIME              DATE,
    LOCK_STATE                     VARCHAR2(1) DEFAULT 0,
    LOCK_TIME                      DATE,
    LOGIN_FAILURES                 VARCHAR2(20) DEFAULT 0,
    LAST_FAILURE_TIME              DATE,
    CONSTRAINT PK_CWM_SYS_USER PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_SYS_USER.ID IS '用户ID';
COMMENT ON COLUMN CWM_SYS_USER.USER_NAME IS '用户名称';
COMMENT ON COLUMN CWM_SYS_USER.ALL_NAME IS '真实姓名';
COMMENT ON COLUMN CWM_SYS_USER.PASSWORD IS '密码';
COMMENT ON COLUMN CWM_SYS_USER.SEX IS '性别 1：男  0：女';
COMMENT ON COLUMN CWM_SYS_USER.PHONE IS '办公电话';
COMMENT ON COLUMN CWM_SYS_USER.POST IS '职务';
COMMENT ON COLUMN CWM_SYS_USER.SPECIALTY IS '专业';
COMMENT ON COLUMN CWM_SYS_USER.GRADE IS '密级';
COMMENT ON COLUMN CWM_SYS_USER.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN CWM_SYS_USER.CREATE_USER IS '创建操作人员';
COMMENT ON COLUMN CWM_SYS_USER.UPDATE_TIME IS '修改时间';
COMMENT ON COLUMN CWM_SYS_USER.UPDATE_USER IS '修改操作人员';
COMMENT ON COLUMN CWM_SYS_USER.NOTES IS '备注';
COMMENT ON COLUMN CWM_SYS_USER.STATE IS '启用标志 1：启用 0：禁止';
COMMENT ON COLUMN CWM_SYS_USER.BIRTHDAY IS '出生年月日';
COMMENT ON COLUMN CWM_SYS_USER.MOBILE IS '手机电话';
COMMENT ON COLUMN CWM_SYS_USER.FLG IS '固化标志 1：表示为固化数据';
COMMENT ON COLUMN CWM_SYS_USER.DEP_ID IS '部门ID';
COMMENT ON COLUMN CWM_SYS_USER.IS_DEL IS '是否已删除';
COMMENT ON COLUMN CWM_SYS_USER.E_MAIL IS '邮箱';

CREATE TABLE CWM_SYS_PASSWORD_HISTORY
(
    ID                             NUMBER NOT NULL,
    USER_ID                        VARCHAR2(20) NOT NULL,
    PASSWORD                       VARCHAR2(100),
    PASSWORD_SET_TIME              DATE,
    CONSTRAINT SYS_C0011247 PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255,
    CONSTRAINT FK_CWM_SYS_PASSWORD_HISTORY FOREIGN KEY (USER_ID) REFERENCES CWM_SYS_USER (ID)
);


CREATE TABLE CWM_SYS_USER_COLUMNS
(
    ID                             VARCHAR2(38) NOT NULL,
    DISPLAY_NAME                   VARCHAR2(100) NOT NULL,
    S_COLUMN_NAME                  VARCHAR2(30) NOT NULL,
    IS_FOR_SEARCH                  VARCHAR2(10),
    IS_NULLABLE                    VARCHAR2(10),
    IS_ONLY                        VARCHAR2(10),
    IS_PK                          VARCHAR2(10),
    ENMU_ID                        VARCHAR2(38),
    COL_TYPE                       VARCHAR2(20) NOT NULL,
    SEQUENCE_NAME                  VARCHAR2(30),
    IS_AUTOINCREMENT               VARCHAR2(10),
    MAX_LENGTH                     NUMBER(38,0),
    MIN_LENGTH                     NUMBER(38,0),
    IS_WRAP                        VARCHAR2(10),
    CHECK_TYPE                     VARCHAR2(10),
    IS_MULTI_SELECTED              VARCHAR2(10),
    DEFAULT_VALUE                  VARCHAR2(30),
    DISPLAY_SHOW                   VARCHAR2(5),
    EDIT_SHOW                      VARCHAR2(5),
    SHOT                           NUMBER,
    INPUT_TYPE                     VARCHAR2(50),
    IS_READONLY                    VARCHAR2(1),
    REF_TABLE                      VARCHAR2(1000),
    REF_TABLE_COLUMN_ID            VARCHAR2(1000),
    REF_TABLE_COLUMN_SHOWNAME      VARCHAR2(2000),
    POP_WINDOW_FUNCTION            VARCHAR2(100),
    IS_FOR_INFOSEARCH              VARCHAR2(10),
    IS_DISPALYINFO_SHOW            VARCHAR2(10),
    IS_VIEWINFO_SHOW               VARCHAR2(10),
    CONSTRAINT PK_CWM_SYS_USER_COLUMNS PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.ID IS '字段表的唯一主键';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.DISPLAY_NAME IS '字段显示名';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.S_COLUMN_NAME IS '用户表中的字段名';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_FOR_SEARCH IS '是否做为检索条件';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_NULLABLE IS '是否为空';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_ONLY IS '是否唯一';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_PK IS '是否是主键';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.ENMU_ID IS '枚举类型';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.COL_TYPE IS '数据类型,用于queryList() 方法中查询条件类型判断';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.SEQUENCE_NAME IS '自增生成器';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_AUTOINCREMENT IS '是否自增';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.MAX_LENGTH IS '最大长度';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.MIN_LENGTH IS '最小长度';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_WRAP IS '是否是多行';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.CHECK_TYPE IS '校验类型 1:是否唯一.2:是否为数字.3:最大长度.4:最小长度.5:是否不为空';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_MULTI_SELECTED IS '枚举类型前提下是否多选 1：多选';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.DEFAULT_VALUE IS '初始值';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.DISPLAY_SHOW IS '检索画面显示标志';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.EDIT_SHOW IS '新建画面与编辑画面显示标志';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.SHOT IS '页面表示顺序';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.INPUT_TYPE IS '页面输入类型 1:文本框 2:大文本框 3:下拉列表:4:日期控件 5:复选框 6:单选框 7:弹出窗口 8:密码';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_READONLY IS '是否只读 1:只读 0:读写';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.REF_TABLE IS '关联表';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.REF_TABLE_COLUMN_ID IS '关联表字段';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.REF_TABLE_COLUMN_SHOWNAME IS '关联表字段显示数据';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.POP_WINDOW_FUNCTION IS '弹出窗口调用的页面js';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_FOR_INFOSEARCH IS '是否用于用户角色信息查询条件';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_DISPALYINFO_SHOW IS '是否用于用户角色信息列表显示';
COMMENT ON COLUMN CWM_SYS_USER_COLUMNS.IS_VIEWINFO_SHOW IS '是否用于用户角色信息详细页面显示';

CREATE TABLE CWM_SYS_USER_DEPT
(
    USER_ID                        VARCHAR2(20) NOT NULL,
    DEPT_ID                        VARCHAR2(20) NOT NULL
);

CREATE TABLE CWM_SYS_USER_ENUM
(
    ENUM_ID                        VARCHAR2(38) NOT NULL,
    VALUE                          VARCHAR2(30) NOT NULL,
    DISPLAY_VALUE                  VARCHAR2(100) NOT NULL,
    IMAGE_URL                      VARCHAR2(100),
    DESCRIPTION                    VARCHAR2(1000),
    ID                             VARCHAR2(38) NOT NULL,
    CONSTRAINT PK_CWM_SYS_USER_ENUM PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);


CREATE TABLE CWM_SYS_USERLOGINHISTORY
(
    ID                             NUMBER NOT NULL,
    USER_NAME                      VARCHAR2(100) NOT NULL,
    USER_DISPALYNAME               VARCHAR2(100) NOT NULL,
    USER_IP                        VARCHAR2(20) NOT NULL,
    LOGIN_TIME                     DATE NOT NULL,
    OP_TYPE                        VARCHAR2(100) NOT NULL,
    OP_MESSAGE                     VARCHAR2(100) NOT NULL,
    USER_DEPTNAME                  VARCHAR2(100),
    LOGOUT_TIME                    DATE,
    USER_DEPTID                    NUMBER,
    CONSTRAINT CWM_SYS_USERLOGINHISTORY_PK PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_SYS_USERLOGINHISTORY.ID IS 'ID';
COMMENT ON COLUMN CWM_SYS_USERLOGINHISTORY.USER_NAME IS '用户登录名';
COMMENT ON COLUMN CWM_SYS_USERLOGINHISTORY.USER_DISPALYNAME IS '用户显示名';
COMMENT ON COLUMN CWM_SYS_USERLOGINHISTORY.USER_IP IS '用户登陆ip';
COMMENT ON COLUMN CWM_SYS_USERLOGINHISTORY.LOGIN_TIME IS '登陆/退出时间';
COMMENT ON COLUMN CWM_SYS_USERLOGINHISTORY.OP_TYPE IS '操作标识 1:登陆,2:正常退出,3:关闭浏览器退出,4:session失效退出,rcp1-Design Studio,rcp2-TBOM Studio,rcp3-ETL Studio,rcp4-WorkFlow Studio';
COMMENT ON COLUMN CWM_SYS_USERLOGINHISTORY.OP_MESSAGE IS '操作消息';


--*******************************************--
--             定时备份 相关                 --
--*******************************************--
CREATE TABLE CWM_TIME_INFO
(
  ID               NUMBER                       NOT NULL,
  ISSTARTTIMEBACK  VARCHAR2(100 BYTE),
  BACKNAME         VARCHAR2(200 BYTE),
  ISBACKDATA       VARCHAR2(1 BYTE),
  ISDAYBACK        VARCHAR2(1 BYTE),
  DAYBACKTIME      VARCHAR2(20 BYTE),
  ISMONTHBACK      VARCHAR2(1 BYTE),
  MONTHBACKDAY     VARCHAR2(10 BYTE),
  MONTHBACKTIME    VARCHAR2(20 BYTE),
  ISWEEKBACK       VARCHAR2(1 BYTE),
  WEEKBACKDAY      VARCHAR2(10 BYTE),
  WEEKBACKTIME     VARCHAR2(20 BYTE),
  BACKTYPE         VARCHAR2(1 BYTE)
);
--*******************************************--
--             帐户策略 相关                 --
--*******************************************--
CREATE TABLE CWM_SYS_ACCOUNT_STRATEGY
(
  ID               NUMBER                       primary key,
  --策略名称
  STRATEGY_NAME    VARCHAR2(100 BYTE),
  --策略说明
  STRATEGY_NOTE    VARCHAR2(1000 BYTE),
  --基于时间的安全认证策略的起始时间
  STRATEGY_VALUE1  VARCHAR2(100 BYTE),
  --基于时间的安全认证策略的终止时间
  STRATEGY_VALUE2  VARCHAR2(100 BYTE),
  --是否启用标志，0 不启用，1 启用
  IS_USE           VARCHAR2(1 BYTE),
  --策略类型，0 帐号密码策略，1 帐户锁定策略 
  TYPE             VARCHAR2(1 BYTE),
  --显示的策略值
  STRATEGY_VALUE   VARCHAR2(100 BYTE)
);


CREATE TABLE CWM_SYS_PASSWORD_HISTORY
(
  ID                 NUMBER                     primary key,
  --用户标志
  USER_ID            VARCHAR2(20 BYTE)          NOT NULL,
  --密码
  PASSWORD           VARCHAR2(100 BYTE),
  --密码设定时间
  PASSWORD_SET_TIME  DATE
);
ALTER TABLE CWM_SYS_PASSWORD_HISTORY ADD (
  CONSTRAINT FK_CWM_SYS_PASSWORD_HISTORY 
 FOREIGN KEY (USER_ID) 
 REFERENCES CWM_SYS_USER (ID));

 --工具集成信息表--
CREATE TABLE CWM_SYS_TOOLS
(
	ID					NUMBER						primary key,
	TOOL_ICON			VARCHAR2(100BYTE)			
	TOOL_NAME			VARCHAR2(100BYTE)			NOT NULL,
	TOOL_VERSION		VARCHAR2(100BYTE),
	TOOL_DESCRIPTION 	VARCHAR2(200BYTE),
	TOOL_CODE			VARCHAR2(50BYTE),
	TOOL_TYPE			VARCHAR2(50BYTE),
	GROUP_ID			VARCHAR2(100BYTE)
);
COMMENT ON COLUMN CWM_SYS_TOOLS.ID IS '工具表的唯一主键';
COMMENT ON COLUMN CWM_SYS_TOOLS.TOOL_ICON IS '工具图标';
COMMENT ON COLUMN CWM_SYS_TOOLS.TOOL_NAME IS '工具显示名';
COMMENT ON COLUMN CWM_SYS_TOOLS.TOOL_VERSION IS '工具版本';
COMMENT ON COLUMN CWM_SYS_TOOLS.TOOL_DESCRIPTION IS '工具描述';
COMMENT ON COLUMN CWM_SYS_TOOLS.TOOL_CODE IS '工具编号';
COMMENT ON COLUMN CWM_SYS_TOOLS.TOOL_TYPE IS '工具类型';
COMMENT ON COLUMN CWM_SYS_TOOLS.GROUP_ID IS '工具所属分组ID';

--工具列表--
CREATE TABLE　CWM_SYS_TOOLS_COLUMNS
(
	ID                             VARCHAR2(38) NOT NULL,
    DISPLAY_NAME                   VARCHAR2(100) NOT NULL,
    S_COLUMN_NAME                  VARCHAR2(30) NOT NULL,
    IS_FOR_SEARCH                  VARCHAR2(10),
    IS_NULLABLE                    VARCHAR2(10),
    IS_ONLY                        VARCHAR2(10),
    IS_PK                          VARCHAR2(10),
    ENMU_ID                        VARCHAR2(38),
    COL_TYPE                       VARCHAR2(20) NOT NULL,
    SEQUENCE_NAME                  VARCHAR2(30),
    IS_AUTOINCREMENT               VARCHAR2(10),
    MAX_LENGTH                     NUMBER(38,0),
    MIN_LENGTH                     NUMBER(38,0),
    IS_WRAP                        VARCHAR2(10),
    CHECK_TYPE                     VARCHAR2(10),
    IS_MULTI_SELECTED              VARCHAR2(10),
    DEFAULT_VALUE                  VARCHAR2(30),
    DISPLAY_SHOW                   VARCHAR2(5),
    EDIT_SHOW                      VARCHAR2(5),
    SHOT                           NUMBER,
    INPUT_TYPE                     VARCHAR2(50),
    IS_READONLY                    VARCHAR2(1),
    REF_TABLE                      VARCHAR2(1000),
    REF_TABLE_COLUMN_ID            VARCHAR2(1000),
    REF_TABLE_COLUMN_SHOWNAME      VARCHAR2(2000),
    POP_WINDOW_FUNCTION            VARCHAR2(100),
    IS_FOR_INFOSEARCH              VARCHAR2(10),
    IS_DISPALYINFO_SHOW            VARCHAR2(10),
    IS_VIEWINFO_SHOW               VARCHAR2(10),
    CONSTRAINT CWM_SYS_TOOLS_COLUMNS PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.ID IS '字段表的唯一主键';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.DISPLAY_NAME IS '字段显示名';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.S_COLUMN_NAME IS '用户表中的字段名';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_FOR_SEARCH IS '是否做为检索条件';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_NULLABLE IS '是否为空';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_ONLY IS '是否唯一';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_PK IS '是否是主键';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.ENMU_ID IS '枚举类型';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.COL_TYPE IS '数据类型,用于queryList() 方法中查询条件类型判断';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.SEQUENCE_NAME IS '自增生成器';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_AUTOINCREMENT IS '是否自增';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.MAX_LENGTH IS '最大长度';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.MIN_LENGTH IS '最小长度';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_WRAP IS '是否是多行';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.CHECK_TYPE IS '校验类型 1:是否唯一.2:是否为数字.3:最大长度.4:最小长度.5:是否不为空';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_MULTI_SELECTED IS '枚举类型前提下是否多选 1：多选';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.DEFAULT_VALUE IS '初始值';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.DISPLAY_SHOW IS '检索画面显示标志';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.EDIT_SHOW IS '新建画面与编辑画面显示标志';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.SHOT IS '页面表示顺序';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.INPUT_TYPE IS '页面输入类型 1:文本框 2:大文本框 3:下拉列表:4:日期控件 5:复选框 6:单选框 7:弹出窗口 8:密码';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_READONLY IS '是否只读 1:只读 0:读写';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.REF_TABLE IS '关联表';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.REF_TABLE_COLUMN_ID IS '关联表字段';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.REF_TABLE_COLUMN_SHOWNAME IS '关联表字段显示数据';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.POP_WINDOW_FUNCTION IS '弹出窗口调用的页面js';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_FOR_INFOSEARCH IS '是否用于用户角色信息查询条件';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_DISPALYINFO_SHOW IS '是否用于用户角色信息列表显示';
COMMENT ON COLUMN CWM_SYS_TOOLS_COLUMNS.IS_VIEWINFO_SHOW IS '是否用于用户角色信息详细页面显示';


--工具集成分组--
CREATE　TABLE CWM_SYS_TOOLS_GROUP
(
	ID								NUMBER  		primary key,
	GROUP_NAME						VARCHAR2(100BYTE),
	GROUP_TYPE						VARCHAR2(100BYTE)
);
COMMENT ON COLUMN CWM_SYS_TOOLS_GROUP.ID IS '工具分组表的唯一主键';
COMMENT ON COLUMN CWM_SYS_TOOLS_GROUP.GROUP_NAME IS '分组显示名';
COMMENT ON COLUMN CWM_SYS_TOOLS_GROUP.GROUP_TYPE IS '分组类型';

--用户工具关系表--
CREATE TABLE CWM_USER_TOOL
(
	ID								NUMBER				primary key,
	USER_ID							VARCHAR2(100BYTE)	NOT NULL,
	TOOL_ID							VARCHAR2(100BYTE)	NOT NULL,
	TOOL_PATH						VARCHAR2(100BYTE)
);

CREATE OR REPLACE VIEW VIEW_USER_SCHEMA
    (USER_ID,ROLE_ID,SCHEMA_ID)
AS
SELECT
   A.ID USER_ID,C.ROLE_ID ROLE_ID, D.ID SCHEMA_ID  FROM CWM_SYS_USER A, CWM_SYS_ROLE_USER B, CWM_SYS_ROLE_SCHEMA C,
   CWM_SCHEMA D
   WHERE A.ID = B.USER_ID AND B.ROLE_ID = C.ROLE_ID AND C.SCHEMA_ID = D.ID;


--模型/数据备份 
CREATE TABLE CWM_BACK
(
    ID                             VARCHAR2(38),
    SCHEMA_ID                      VARCHAR2(38),
    USER_ID                        VARCHAR2(20),
    BACK_DATE                      DATE,
    FILE_PATH                      VARCHAR2(200),
    BACK_MODEL                     VARCHAR2(1),
    BACK_DATA                      VARCHAR2(1),
    AUTO_BACK                      VARCHAR2(1),
    AUTO_BACK_DATE                 DATE,
    AUTO_BACK_ZQ                   VARCHAR2(3),
    REMARK                         VARCHAR2(200),
    TYPE                           NUMBER(1,0),
    TABLE_ID                       VARCHAR2(38)
);

--组件传递数据表
CREATE　TABLE CWM_COMPONENT_RELATIONDATA
(
	ID								NUMBER  		primary key,
	PRJCODE							VARCHAR2(100BYTE),
	PARAMKEY						VARCHAR2(100BYTE),
	PARAMVALUE						VARCHAR2(200BYTE)
);
COMMENT ON COLUMN CWM_COMPONENT_RELATIONDATA.ID IS '组件数据传递表的唯一主键';
COMMENT ON COLUMN CWM_COMPONENT_RELATIONDATA.PRJCODE IS '协同试验项目编号';
COMMENT ON COLUMN CWM_COMPONENT_RELATIONDATA.PARAMKEY IS '参数关键字';
COMMENT ON COLUMN CWM_COMPONENT_RELATIONDATA.PARAMVALUE IS '参数值';

--模型与试验项目管理
CREATE　TABLE CWM_MODEL_PROJECT_RELATION(
  ID                NUMBER      primary key,
  MODELNAME              VARCHAR2(100BYTE),
  SCHEMAID            VARCHAR2(40BYTE),
  DATAID            VARCHAR2(40BYTE),
  PROJECTID         VARCHAR2(40BYTE)
);
COMMENT ON COLUMN CWM_MODEL_PROJECT_RELATION.ID IS '模型与试验项目关系主键';
COMMENT ON COLUMN CWM_MODEL_PROJECT_RELATION.MODELNAME IS '主模型名称';
COMMENT ON COLUMN CWM_MODEL_PROJECT_RELATION.SCHEMAID IS '主模型的schemaid';
COMMENT ON COLUMN CWM_MODEL_PROJECT_RELATION.DATAID IS '主模型数据id';
COMMENT ON COLUMN CWM_MODEL_PROJECT_RELATION.PROJECTID IS '项目id';

--组件管理表
CREATE　TABLE CWM_COMPONENT
(
	ID							NUMBER  		primary key,
	COMPONENTNAME			    VARCHAR2(100BYTE),
	CLASSNAME					VARCHAR2(200BYTE),
	REMARK						VARCHAR2(200BYTE)
);
COMMENT ON COLUMN CWM_COMPONENT.ID IS '组件表的唯一主键';
COMMENT ON COLUMN CWM_COMPONENT.COMPONENTNAME IS '组件名称';
COMMENT ON COLUMN CWM_COMPONENT.CLASSNAME IS '类的全名';
COMMENT ON COLUMN CWM_COMPONENT.REMARK IS '描述';

--组件数据查看输入参数表
CREATE　TABLE CWM_COMPONENT_DASHBOARD
(
	ID							NUMBER  		primary key,
	PROJID					    VARCHAR2(100BYTE),
	PROJTASKID					VARCHAR2(200BYTE),
	VALUE						VARCHAR2(200BYTE)
);
COMMENT ON COLUMN CWM_COMPONENT_DASHBOARD.ID IS '组件数据查看输入参数表的唯一主键';
COMMENT ON COLUMN CWM_COMPONENT_DASHBOARD.PROJID IS '协同项目ID';
COMMENT ON COLUMN CWM_COMPONENT_DASHBOARD.PROJTASKID IS '协同任务ID';
COMMENT ON COLUMN CWM_COMPONENT_DASHBOARD.VALUE IS '值';



--脚本
CREATE　TABLE CWM_SCRIPT
(
	ID							NUMBER  		primary key,
	NAME					    VARCHAR2(100BYTE),
	SCRIPT						VARCHAR2(1024BYTE),
	CATEGORY					VARCHAR2(200BYTE),
	MEMO						VARCHAR2(200BYTE),
	RETURNTYPE					VARCHAR2(200BYTE)
);
COMMENT ON COLUMN CWM_SCRIPT.ID  IS '唯一主键';
COMMENT ON COLUMN CWM_SCRIPT.NAME IS '脚本名称';
COMMENT ON COLUMN CWM_SCRIPT.SCRIPT IS '脚本内容';
COMMENT ON COLUMN CWM_SCRIPT.CATEGORY IS '脚本类型';
COMMENT ON COLUMN CWM_SCRIPT.MEMO IS '脚本描述';

--脚本参数
CREATE　TABLE CWM_SCRIPTPARAM
(
	ID							NUMBER  		primary key,
	SCRIPTID					VARCHAR2(20BYTE),
	PARMNAME					VARCHAR2(100BYTE),
	PARAMTYPE					VARCHAR2(200BYTE),
	DEFAULTVALUE				VARCHAR2(200BYTE),
	ORDERNUM					VARCHAR2(100BYTE)
);
COMMENT ON COLUMN CWM_SCRIPTPARAM.ID  IS '唯一主键';
COMMENT ON COLUMN CWM_SCRIPTPARAM.SCRIPTID IS '所属脚本Id';
COMMENT ON COLUMN CWM_SCRIPTPARAM.PARMNAME IS '参数名称';
COMMENT ON COLUMN CWM_SCRIPTPARAM.PARAMTYPE IS '参数类型';
COMMENT ON COLUMN CWM_SCRIPTPARAM.DEFAULTVALUE IS '参数默认值';
COMMENT ON COLUMN CWM_SCRIPTPARAM.ORDERNUM IS '参数顺序';



--单位数据字段
create table CWM_SYS_NUMBERUNIT
(
  ID                    VARCHAR2(38 CHAR) not null,
  NAME              VARCHAR2(25 CHAR),
  SHOW_NAME           VARCHAR2(50 CHAR),
  UNIT          VARCHAR2(25 CHAR),
  IS_BASE       VARCHAR2(1 CHAR),
  FORMULA_IN      VARCHAR2(100 CHAR),
  FORMULA_OUT     VARCHAR2(100 CHAR),
  POSITION 		NUMBER
);

COMMENT ON COLUMN CWM_SYS_NUMBERUNIT.NAME IS '名称：length';
COMMENT ON COLUMN CWM_SYS_NUMBERUNIT.SHOW_NAME IS '显示名称：长度';
COMMENT ON COLUMN CWM_SYS_NUMBERUNIT.UNIT IS '单位：cm,m,km';
COMMENT ON COLUMN CWM_SYS_NUMBERUNIT.IS_BASE IS '是否为基准单位，基准单位是保存在数据库中的值';
COMMENT ON COLUMN CWM_SYS_NUMBERUNIT.FORMULA_IN IS '将输入值转换为基准单位值的公式';
COMMENT ON COLUMN CWM_SYS_NUMBERUNIT.FORMULA_OUT IS '将基准值转换为显示值的公式';
COMMENT ON COLUMN CWM_SYS_NUMBERUNIT.POSITION IS '排序属性';

CREATE SEQUENCE SEQ_CWM_SYS_NUMBERUNIT
INCREMENT BY 1
START WITH 1
MAXVALUE 9999999999999999999999999
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

--*******************************************--
--             SEQUENCE 相关                 --
--*******************************************--
CREATE SEQUENCE SEQ_CWM_SYS_DEPARTMENT
INCREMENT BY 1
START WITH 2
MAXVALUE 9999999999999999999999999
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE SEQ_CWM_SYS_FUNCTION_ID
INCREMENT BY 1
START WITH 782
MAXVALUE 9999999999999999999999999
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE SEQ_CWM_SYS_LOG
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 0
NOCYCLE 
NOCACHE
NOORDER ;

CREATE SEQUENCE SEQ_CWM_SYS_PARTOPERATIONS
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
NOCACHE
NOORDER ;

CREATE SEQUENCE SEQ_CWM_SYS_PASSWORD_HISTORY
INCREMENT BY 1
START WITH 1
MAXVALUE 99999999999999999999999999
NOMINVALUE
NOCYCLE 
CACHE 10
NOORDER ;

CREATE SEQUENCE SEQ_CWM_SYS_ROLE
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
NOCACHE
NOORDER ;

CREATE SEQUENCE SEQ_CWM_SYS_ROLE_TBOM
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 0
NOCYCLE 
NOCACHE
NOORDER ;

CREATE SEQUENCE SEQ_CWM_SYS_USER
INCREMENT BY 1
START WITH 2
MAXVALUE 9999999999999999999999999
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE SEQ_CWM_SYS_USERLOGINHISTORY
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOMINVALUE
NOCYCLE 
NOCACHE
NOORDER ;

--定时备份索引
CREATE SEQUENCE CWM_TIME_INFO_SEQ
  START WITH 311
  MAXVALUE 99999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 10
  NOORDER;

--模型/数据备份表序列
CREATE SEQUENCE SEQ_CWM_BACK
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 0
NOCYCLE 
NOCACHE
NOORDER ;  

--设置密码历史信息表的id自增
CREATE SEQUENCE SEQ_CWM_SYS_PASSWORD_HISTORY
  START WITH 1
  MAXVALUE 99999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 10
  NOORDER;

--设置工具集成表的id自增--
CREATE SEQUENCE SEQ_CWM_SYS_TOOLS
	START WITH 1
	MAXVALUE 999999999999999999999999
	MINVALUE 1
	NOCYCLE
	CACHE 10
	NOORDER;
	
--工具集成分组的id自增--
CREATE SEQUENCE SEQ_CWM_SYS_TOOLS_GROUP
	START WITH 	1
	MAXVALUE	9999999999999999999999
	MINVALUE	1
	NOCYCLE
	CACHE	10
	NOORDER;

--用户工具关系表的id自增--
CREATE SEQUENCE SEQ_CWM_USER_TOOL
	START WITH 	1
	MAXVALUE	999999999999999999999
	MINVALUE	1
	NOCYCLE
	CACHE	10
	NOORDER;
	
--模板
CREATE TABLE CWM_TEMPLATE(
  ID            VARCHAR2(38 BYTE),
  NAME			VARCHAR2(38 BYTE),
  TYPE			VARCHAR2(38 BYTE),
  DATAID		VARCHAR2(38 BYTE),
  DISPLAY_GROUP	VARCHAR2(38 BYTE),
  DESCRIPTION	VARCHAR2(300 BYTE),
  VERSION		INTEGER,  
  CREATE_TIME   DATE,
  DATAID		VARCHAR2(38 BYTE),		--根据type的类型，确定为WBS项目的ID还是协同项目的ID
  MODIFY_TIME   DATE,
  CREATE_USER_ID VARCHAR2(38 BYTE),
  LAST_MODIFY_USER_ID  VARCHAR2(38 BYTE),
  MAP_CONTENT   BLOB
);

CREATE SEQUENCE SEQ_CWM_TEMPLATE
	START WITH 1
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 999999999999999999999999999
	CACHE 20
	NOCYCLE 
	NOORDER;

--组件传递数据表的id自增--
CREATE SEQUENCE SEQ_CWM_COMPONENT_RELATIONDATA
	START WITH 	1
	MAXVALUE	999999999999999999999
	MINVALUE	1
	NOCYCLE
	CACHE	10
	NOORDER;

--模型创建试验项目关系表	
CREATE SEQUENCE SEQ_CWM_MODEL_PROJECT_RELATION
	START WITH 	1
	MAXVALUE	999999999999999999999
	MINVALUE	1
	NOCYCLE
	CACHE	10
	NOORDER;
	
CREATE SEQUENCE SEQ_CWM_COMPONENT
	START WITH 	1
	MAXVALUE	999999999999999999999
	MINVALUE	1
	NOCYCLE
	CACHE	10
	NOORDER;
	
	
	
	
CREATE SEQUENCE SEQ_CWM_COMPONENT_DASHBOARD
START WITH 	1
MAXVALUE	999999999999999999999
MINVALUE	1
NOCYCLE
CACHE	10
NOORDER;

CREATE SEQUENCE SEQ_CWM_SCRIPT
START WITH 	1
MAXVALUE	999999999999999999999
MINVALUE	1
NOCYCLE
CACHE	10
NOORDER;
                
CREATE SEQUENCE SEQ_CWM_SCRIPTPARAM
START WITH 	1
MAXVALUE	999999999999999999999
MINVALUE	1
NOCYCLE
CACHE	10
NOORDER;
