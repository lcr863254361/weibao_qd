
--*******************************************--
--              附件相关                    --
--*******************************************--
CREATE TABLE CWM_FOLDER
(
  ID             				VARCHAR2(38 BYTE),
  DEL_FLAG       			NUMBER(1),
  ADD_FLAG       			NUMBER(1),
  EDIT_FLAG      			NUMBER(1),
  CWM_FOLDER_ID  	VARCHAR2(38 BYTE),
  CWM_TABLES_ID   	VARCHAR2(38 BYTE),
  RECORD_ID      		VARCHAR2(38 BYTE),
  FOLDER_NAME    		VARCHAR2(200 BYTE)
);

COMMENT ON COLUMN CWM_FOLDER.CWM_FOLDER_ID IS '文件夹的父文件夹';
COMMENT ON COLUMN CWM_FOLDER.CWM_TABLES_ID IS '文件夹所属Table';
COMMENT ON COLUMN CWM_FOLDER.RECORD_ID IS 		  '文件夹所属记录';
COMMENT ON COLUMN CWM_FOLDER.FOLDER_NAME IS    '文件夹名称';

CREATE TABLE CWM_FILE
(
    SCHEMAID                       	VARCHAR2(38),
    TABLEID                        		VARCHAR2(38),
    FILEID                         		VARCHAR2(16) NOT NULL,
    FILENAME                       	VARCHAR2(100),
    FILEDESCRIPTION               VARCHAR2(200),
    FILETYPE                       		VARCHAR2(50),
    FILELOCATION                   	VARCHAR2(200),
    FILESIZE                       		NUMBER,
    PARSE_RULE                     	VARCHAR2(2),
    UPLOAD_USER_ID               VARCHAR2(20),
    UPLOAD_DATE                    DATE,
    DELETE_USER_ID                VARCHAR2(20),
    DELETE_DATE                    	DATE,
    DATAID                         		VARCHAR2(38),
    FINALNAME                      	VARCHAR2(100),
    EDITION                        		VARCHAR2(10),
    IS_VALID                       		NUMBER(1,0),
    FILESECRECY                    	VARCHAR2(50),
    UPLOAD_USER_MAC            	VARCHAR2(17),
    UPLOAD_STATUS                 VARCHAR2(1),
    FILE_FOLDER                    	VARCHAR2(1000),
    IS_FOLD_FILE                   	NUMBER DEFAULT 0,
    IS_WHOLE_SEARCH             NUMBER(1,0) DEFAULT 0,
    IS_DATA_FILE                   	NUMBER(1,0) DEFAULT 0,
    CWM_FOLDER_ID 			  	VARCHAR2(38 BYTE),
    CONVER_STATE  				  	VARCHAR2(38 BYTE),
    CONSTRAINT PK_CWM_FILE PRIMARY KEY (FILEID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_FILE.SCHEMAID IS '文件所属数据类型的ID';
COMMENT ON COLUMN CWM_FILE.TABLEID IS '文件所属数据类的ID';
COMMENT ON COLUMN CWM_FILE.FILEID IS '主键ID';
COMMENT ON COLUMN CWM_FILE.FILENAME IS '文件名称';
COMMENT ON COLUMN CWM_FILE.FILEDESCRIPTION IS '描述';
COMMENT ON COLUMN CWM_FILE.FILETYPE IS '文件后缀名，如果是HBASETABLE，则为OrientHDFS';
COMMENT ON COLUMN CWM_FILE.FILELOCATION IS '文件存放目录,如果是HBASETABLE，则为hdfs的路径';
COMMENT ON COLUMN CWM_FILE.FILESIZE IS '文件大小';
COMMENT ON COLUMN CWM_FILE.PARSE_RULE IS '备用';
COMMENT ON COLUMN CWM_FILE.UPLOAD_USER_ID IS '上传用户ID';
COMMENT ON COLUMN CWM_FILE.UPLOAD_DATE IS '上传时间';
COMMENT ON COLUMN CWM_FILE.DELETE_USER_ID IS '删除用户ID';
COMMENT ON COLUMN CWM_FILE.DELETE_DATE IS '删除时间';
COMMENT ON COLUMN CWM_FILE.DATAID IS '文件所属数据记录的ID';
COMMENT ON COLUMN CWM_FILE.FINALNAME IS '文件存放的最终文件名，如果类型为hdfs，则显示hbase的table名称';
COMMENT ON COLUMN CWM_FILE.EDITION IS '文件版本';
COMMENT ON COLUMN CWM_FILE.IS_VALID IS '是否有效';
COMMENT ON COLUMN CWM_FILE.FILESECRECY IS '文件密级';
COMMENT ON COLUMN CWM_FILE.UPLOAD_USER_MAC IS '上传客户端的MAC地址';
COMMENT ON COLUMN CWM_FILE.UPLOAD_STATUS IS '上传状态，0表示未完成，1表示上传完成';
COMMENT ON COLUMN CWM_FILE.FILE_FOLDER IS '文件目录信息';
COMMENT ON COLUMN CWM_FILE.IS_FOLD_FILE IS '是否包含文件目录信息，默认0，1表示该文件包含文件目录信息';
COMMENT ON COLUMN CWM_FILE.IS_WHOLE_SEARCH IS '文件是否进行全文检索，0为不能，1为能，默认为0';
COMMENT ON COLUMN CWM_FILE.IS_DATA_FILE IS '文件是否能进行数据处理，0为不能，1为能，默认为0';
COMMENT ON COLUMN CWM_FILE.CWM_FOLDER_ID IS '文件夹Id';
COMMENT ON COLUMN CWM_FILE.CONVER_STATE IS '转换状态';

  --创建文件类型分组表
CREATE TABLE CWM_FILE_GROUP
(
  ID             VARCHAR2(38 BYTE),
  GROUP_NAME     VARCHAR2(38 BYTE),
  GROUP_TYPE     VARCHAR2(38 BYTE),
  IS_SHOW      	 NUMBER(1)
);

--*******************************************--
--             报告生成相关                  --
--*******************************************--
CREATE TABLE CWM_REPORT
(
    ID                             VARCHAR2(50) NOT NULL,
    CONTENT                        BLOB
);
COMMENT ON COLUMN CWM_REPORT.ID IS '报告序列';
COMMENT ON COLUMN CWM_REPORT.CONTENT IS '报告内容';

CREATE TABLE CWM_REPORT_ITEMS
(
    ID                             VARCHAR2(38),
    REPORT_ID                      VARCHAR2(38),
    TABLE_ID                       VARCHAR2(38),
    COLUMN_NAME                    VARCHAR2(38),
    RELATIONS                      VARCHAR2(100),
    TYPE                           VARCHAR2(2)
);

CREATE TABLE CWM_REPORTS
(
    ID                             VARCHAR2(38) NOT NULL,
    SCHEMA_ID                      VARCHAR2(38),
    TABLE_ID                       VARCHAR2(200 BYTE),
    VIEWS_ID                       VARCHAR2(38),
    COLUMN_ID                      VARCHAR2(38),
    CONTENT                        CLOB,
    FILEPATH                       VARCHAR2(200),
    PID                            	VARCHAR2(38),
    TYPE                           	NUMBER(1,0),
    ORDERS                         NUMBER(1,0),
    CREATE_USER                 VARCHAR2(100),
    CREATE_TIME                  DATE,
    NAME                           	VARCHAR2(100),
    FILTERJSON                    VARCHAR2(4000),
    DATA_ENTRY 					VARCHAR2(38 BYTE),
    CONSTRAINT PK_CWM_REPORTS PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_REPORTS.FILTERJSON IS '过滤表达式';




--*******************************************--
--             动态建库相关                  --
--*******************************************--

CREATE TABLE CWM_SEQGENERATOR
(
    ID                             NUMBER NOT NULL,
    NAME                           VARCHAR2(100),
    CONTENT                        CLOB,
    ENABLE                         NUMBER(1,0),
    RETURN_TYPE                    VARCHAR2(50),
    CHANGED                        VARCHAR2(10),
    CONSTRAINT PK_CWM_SEQGENERATOR PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_SEQGENERATOR.ID IS '脚本ID';
COMMENT ON COLUMN CWM_SEQGENERATOR.NAME IS '脚本名称';
COMMENT ON COLUMN CWM_SEQGENERATOR.CONTENT IS '脚本内容';
COMMENT ON COLUMN CWM_SEQGENERATOR.CHANGED IS '是否被修改(TRUE表示已经被修改,需要重新编译,FALSE反之).';


CREATE TABLE CWM_RELATION_COLUMNS
(
    COLUMN_ID                      VARCHAR2(38) NOT NULL,
    RELATIONTYPE                   NUMBER(1,0),
    OWNERSHIP                      NUMBER(1,0),
    IS_FK                          NUMBER(1,0),
    REF_TABLE_ID                   VARCHAR2(38),
    REF_COLUMN_NAME                VARCHAR2(100),
    ID                             VARCHAR2(38),
    TABLE_ID                       VARCHAR2(38),
    SUB_COLUMN_ID                  VARCHAR2(38),
    CATEGORY                       VARCHAR2(100),
    ISLISTDISPLAY                  VARCHAR2(10),
    CONSTRAINT PK_CWM_RELATION_COLUMNS PRIMARY KEY (COLUMN_ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_RELATION_COLUMNS.RELATIONTYPE IS '1是一对一，2是一对多，3是多对一，4是多对多';
COMMENT ON COLUMN CWM_RELATION_COLUMNS.ISLISTDISPLAY IS '是否下拉列表显示，False或空都为弹出窗口显示，True为下拉列表显示';

CREATE TABLE CWM_RELATION_DATA
(
    ID                             VARCHAR2(38) NOT NULL,
    MAIN_TABLE_NAME                VARCHAR2(100),
    MAIN_DATA_ID                   VARCHAR2(38),
    SUB_TABLE_NAME                 VARCHAR2(100),
    SUB_DATA_ID                    VARCHAR2(38),
    CONSTRAINT PK_CWM_RELATION_DATA PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_RELATION_TABLE_ENUM
(
    TABLE_ID                       VARCHAR2(38),
    ID                             VARCHAR2(38) NOT NULL,
    TABLE_ENUM_ID                  VARCHAR2(38),
    ORDER_SIGN                     NUMBER(10,0),
    ORIGIN_TABLE_ID                VARCHAR2(38),
    TYPE                           VARCHAR2(10),
    FROM_TABLE_ID                  VARCHAR2(38),
    TO_TABLE_ID                    VARCHAR2(38),
    CONSTRAINT PK_CWM_RELATION_TABLE_ENUM PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_ENUM
(
    RESTRICTION_ID                 VARCHAR2(38) NOT NULL,
    VALUE                          		VARCHAR2(50) NOT NULL,
    DISPLAY_VALUE                  VARCHAR2(100) NOT NULL,
    IMAGE_URL                     	VARCHAR2(100),
    DESCRIPTION                    	VARCHAR2(1000),
    ID                             		VARCHAR2(38) NOT NULL,
    ORDER_SIGN                     	NUMBER(10,0),
    IS_OPEN                        	NUMBER(1,0),
    CONSTRAINT PK_CWM_ENUM PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_RESTRICTION
(
    ID                             VARCHAR2(38) NOT NULL,
    NAME                           VARCHAR2(30) NOT NULL,
    DISPLAY_NAME                   VARCHAR2(100) NOT NULL,
    TYPE                           NUMBER(1,0) NOT NULL,
    IS_MULTI_SELECTED              VARCHAR2(10),
    ERROR_INFO                     VARCHAR2(1000),
    DESCRIPTION                    VARCHAR2(1000),
    DISPLAY_TYPE                   NUMBER(1,0),
    MAX_LENGTH                     NUMBER(20,10),
    MIN_LENGTH                     NUMBER(20,10),
    SCHEMA_ID                      VARCHAR2(38),
    IS_VALID                       NUMBER(1,0) NOT NULL,
    DATATYPE                       VARCHAR2(20),
    ORDER_SIGN                     NUMBER(10,0),
    CITE                           VARCHAR2(100),
    CONSTRAINT PK_CWM_RESTRICTION PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_RESTRICTION.IS_VALID IS '是否有效，0表示无效，1表示有效';
COMMENT ON COLUMN CWM_RESTRICTION.DATATYPE IS '数据类型';

CREATE TABLE CWM_CONS_EXPRESSION
(
    TABLE_ID                       VARCHAR2(38),
    EXPRESSION                     NVARCHAR2(1000),
    RESULT                         NVARCHAR2(200),
    PRI                            NUMBER(10,0),
    ID                             VARCHAR2(38) NOT NULL,
    ORDER_SIGN                     NUMBER(10,0),
    CONSTRAINT PK_CWM_CONS_EXPRESSION PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);


CREATE TABLE CWM_SCHEMA
(
    ID                             VARCHAR2(38) NOT NULL,
    NAME                           VARCHAR2(50) NOT NULL,
    VERSION                        VARCHAR2(15) NOT NULL,
    DESCRIPTION                    VARCHAR2(200),
    MODIFIED_TIME                  DATE,
    IS_LOCK                        NUMBER(1,0),
    USERID                         VARCHAR2(100),
    LOCK_MODIFIED_TIME             DATE,
    IS_DELETE                      NUMBER(1,0) DEFAULT 1,
    SECRECY_SET                    VARCHAR2(100),
    TYPE                           VARCHAR2(1) DEFAULT 0
);
COMMENT ON COLUMN CWM_SCHEMA.SECRECY_SET IS '数据模型的密级设置信息，密级以,分割';
COMMENT ON COLUMN CWM_SCHEMA.TYPE IS '是否是共享数据模型，0表示不是共享数据模型，1表示是共享数据模型';


CREATE TABLE CWM_TAB_COLUMNS
(
    ID                             VARCHAR2(38) NOT NULL,
    S_NAME                         VARCHAR2(30) NOT NULL,
    DISPLAY_NAME                   VARCHAR2(100) NOT NULL,
    CATEGORY                       NUMBER(1,0) NOT NULL,
    DESCRIPTION                    VARCHAR2(2000),
    S_COLUMN_NAME                  VARCHAR2(100),
    TABLE_ID                       VARCHAR2(38),
    IS_WHO_SEARCH                  VARCHAR2(10),
    IS_FOR_SEARCH                  VARCHAR2(10),
    IS_INDEX                       VARCHAR2(10),
    OPERATE_SIGN                   VARCHAR2(10),
    PURPOSE                        VARCHAR2(10),
    CASESENSITIVE                  VARCHAR2(15),
    DEFAULT_VALUE                  VARCHAR2(30),
    IS_NULLABLE                    VARCHAR2(10),
    IS_ONLY                        VARCHAR2(10),
    IS_PK                          NUMBER(10,0),
    IS_AUTOINCREMENT               VARCHAR2(10),
    RESTRICTION_ID                 VARCHAR2(38),
    TYPE                           VARCHAR2(20),
    SEQUENCE_NAME                  VARCHAR2(100),
    MAX_LENGTH                     NUMBER(38,0),
    MIN_LENGTH                     NUMBER(38,0),
    IS_SHOW                        VARCHAR2(10),
    IS_WRAP                        VARCHAR2(10),
    PROPERTY_PARAGRAPH             VARCHAR2(30),
    PROPERTY_CATEGORY              VARCHAR2(30),
    LINAGE                         NUMBER(38,0),
    IS_VALID                       NUMBER(1,0) NOT NULL,
    IS_MUTI_UK                     NUMBER(10,0),
    IS_USED_PAIXU                  NUMBER(10,0),
    IS_NEED                        VARCHAR2(10),
    ORDER_SIGN                     NUMBER(10,0),
    CITE                           VARCHAR2(100),
    NUM_LENGTH                     NUMBER(38,0),
    NUM_PRECISION                  NUMBER(38,0),
    REF_VIEW_ID                    VARCHAR2(38),
    ARITH_ID                       VARCHAR2(100),
    ARITH_NAME                     VARCHAR2(100),
    ARITH_METHOD                   VARCHAR2(100),
    ARITH_TYPE                     VARCHAR2(100),
    MAP_COLUMN                     VARCHAR2(38),
    SEQ_VALUE                      NUMBER(38,0),
    EDITABLE                       VARCHAR2(10),
    SEQ_INTERVAL                   NUMBER(38,0),
    SELECTOR					   VARCHAR2(2000),
    UNIT						   VARCHAR2(200),
    CONSTRAINT PK_CWM_TAB_COLUMNS PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_TAB_COLUMNS.IS_VALID IS '是否有效，0表示无效，1表示有效';
COMMENT ON COLUMN CWM_TAB_COLUMNS.IS_NEED IS '关系属性是否比选';
COMMENT ON COLUMN CWM_TAB_COLUMNS.REF_VIEW_ID IS '数据统计视图ID，用户定义数据统计视图时的统计参数项中的统计属性保存成字段模式，放在column中';
COMMENT ON COLUMN CWM_TAB_COLUMNS.ARITH_ID IS '统计属性的算法ID';
COMMENT ON COLUMN CWM_TAB_COLUMNS.ARITH_NAME IS '统计属性算法的名称';
COMMENT ON COLUMN CWM_TAB_COLUMNS.ARITH_METHOD IS '统计属性算法的公式';
COMMENT ON COLUMN CWM_TAB_COLUMNS.ARITH_TYPE IS '统计属性的类别，是数据库内置算法还是自定义算法，0表示内置，1表示自定义算法';
COMMENT ON COLUMN CWM_TAB_COLUMNS.MAP_COLUMN IS '映射字段，当前字段属性映射的共享表中的字段信息';
COMMENT ON COLUMN CWM_TAB_COLUMNS.SEQ_VALUE IS '自增属性初始值,默认值为1';
COMMENT ON COLUMN CWM_TAB_COLUMNS.EDITABLE IS '数据操作,0为不限制，1为限制修改，2为限制创建修改';
COMMENT ON COLUMN CWM_TAB_COLUMNS.SEQ_INTERVAL IS '自增序列的自增间隔，默认为1，最小为1的正整数';
COMMENT ON COLUMN CWM_TAB_COLUMNS.SELECTOR IS '选择配置器，用户选择、单用户选择、部门选择、多部门选择';
COMMENT ON COLUMN CWM_TAB_COLUMNS.UNIT IS '数值类型的单位配置';


CREATE TABLE CWM_TABLE_COLUMN
(
    ID                             VARCHAR2(38) NOT NULL,
    TABLE_ID                       VARCHAR2(38),
    COLUMN_ID                      VARCHAR2(38),
    TYPE                           NUMBER(1,0),
    ORDER_SIGN                     NUMBER(10,0),
    CONSTRAINT PK_CWM_TABLE_UNIQUE PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_TABLE_COLUMN.TABLE_ID IS '数据类的ID';
COMMENT ON COLUMN CWM_TABLE_COLUMN.COLUMN_ID IS '字段的id，可能是普通属性也可能是关系属性';
COMMENT ON COLUMN CWM_TABLE_COLUMN.TYPE IS '0表示为主键显示值，1表示为唯一性组合约束，2表示为排序属性，3表示为数据表字段前后展现顺序';

CREATE TABLE CWM_TABLE_ENUM
(
    RESTRICTION_ID                 VARCHAR2(38) NOT NULL,
    COLUMN_ID                      VARCHAR2(38),
    EXPRESSION                     VARCHAR2(500),
    TABLE_ID                       VARCHAR2(38),
    TABLE_ENUM_SQL                 VARCHAR2(3000),
    ID                             VARCHAR2(38) NOT NULL,
    MIN_TABLE                      VARCHAR2(38),
    MAX_TABLE                      VARCHAR2(38),
    MIN_COLUMN                     VARCHAR2(38),
    MAX_COLUMN                     VARCHAR2(38),
    CONSTRAINT PK_CWM_TABLE_ENUM PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_TABLE_ENUM.TABLE_ENUM_SQL IS '表枚举约束的SQL语句';
COMMENT ON COLUMN CWM_TABLE_ENUM.MIN_TABLE IS '动态范围约束的动态最小值所在数据类，记录的是数据类的ID';
COMMENT ON COLUMN CWM_TABLE_ENUM.MAX_TABLE IS '动态范围约束的动态最大值所在数据类，记录的是数据类的ID';
COMMENT ON COLUMN CWM_TABLE_ENUM.MIN_COLUMN IS '动态范围约束的动态最小值所在字段，记录的是字段的ID';
COMMENT ON COLUMN CWM_TABLE_ENUM.MAX_COLUMN IS '动态范围约束的动态最大值所在字段，记录的是字段的ID';

CREATE TABLE CWM_TABLES
(
    ID                             VARCHAR2(38) NOT NULL,
    S_NAME                         VARCHAR2(30) NOT NULL,
    DISPLAY_NAME                   VARCHAR2(100) NOT NULL,
    S_TABLE_NAME                   VARCHAR2(100),
    PID                            VARCHAR2(38),
    SCHEMA_ID                      VARCHAR2(38) NOT NULL,
    PAIXU_FX                       VARCHAR2(10),
    IS_CONNECT_TABLE               VARCHAR2(10),
    IS_SHOW                        VARCHAR2(10),
    DETAIL_TEXT                    VARCHAR2(3000),
    DESCRIPTION                    VARCHAR2(2000),
    BIG_IMAGE                      VARCHAR2(100),
    NOR_IMAGE                      VARCHAR2(100),
    SMA_IMAGE                      VARCHAR2(100),
    CATEGORY                       VARCHAR2(20),
    IS_VALID                       NUMBER(1,0) NOT NULL,
    ORDER_SIGN                     NUMBER(10,0),
    CITE                           VARCHAR2(100),
    SECRECY                        VARCHAR2(100),
    SECRECYABLE                    VARCHAR2(30),
    MAP_TABLE                      VARCHAR2(38),
    SHARE_TYPE                     VARCHAR2(1) DEFAULT 0,
    SHAREABLE                      VARCHAR2(10),
	COL_SUM 					   NUMBER(4) default 2,
    CONSTRAINT PK_CWM_TABLES PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_TABLES.IS_VALID IS '是否有效，0表示无效，1表示有效';
COMMENT ON COLUMN CWM_TABLES.SECRECY IS '数据类的默认密级';
COMMENT ON COLUMN CWM_TABLES.SECRECYABLE IS '密级是否启用，True为启用，False为禁用，缺省为False';
COMMENT ON COLUMN CWM_TABLES.MAP_TABLE IS '映射数据类，数据类映射到共享数据模型中的数据类或系统表';
COMMENT ON COLUMN CWM_TABLES.SHARE_TYPE IS '共享类型，0表示普通数据类，1表示引用共享数据类（固化表），2表示引用系统表';
COMMENT ON COLUMN CWM_TABLES.SHAREABLE IS '被引用后的共享数据类的数据是否共享';
COMMENT ON COLUMN CWM_TABLES.COL_SUM is '详细界面显示的列数';


CREATE TABLE CWM_VIEW_PAIXU_COLUMN
(
    VIEW_ID                        VARCHAR2(38),
    ID                             VARCHAR2(38) NOT NULL,
    PAIXU_COLUMN_ID                VARCHAR2(38),
    ORDER_SIGN                     NUMBER(10,0),
    CONSTRAINT PK_CWM_VIEW_PAIXU_COLUMN PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_VIEW_RELATIONTABLE
(
    VIEW_ID                        VARCHAR2(38) NOT NULL,
    TABLE_ID                       VARCHAR2(38),
    ID                             VARCHAR2(38) NOT NULL,
    ORDER_SIGN                     NUMBER(10,0),
    ORIGIN_TABLE_ID                VARCHAR2(38),
    TYPE                           VARCHAR2(10),
    FROM_TABLE_ID                  VARCHAR2(38),
    TO_TABLE_ID                    VARCHAR2(38),
    ORIGIN_TO_VIEW_ID              VARCHAR2(38),
    CONSTRAINT PK_CWM_VIEW_RELATIONTABLE PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_VIEW_RETURN_COLUMN
(
    VIEW_ID                        VARCHAR2(38) NOT NULL,
    RETURN_COLUMN_ID               VARCHAR2(38) NOT NULL,
    ID                             VARCHAR2(38) NOT NULL,
    ORDER_SIGN                     NUMBER(10,0),
    CONSTRAINT PK_CWM_VIEW_RETURN_COLUMN PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_VIEWS
(
    ID                             VARCHAR2(38) NOT NULL,
    NAME                           VARCHAR2(30) NOT NULL,
    DISPLAY_NAME                   VARCHAR2(100) NOT NULL,
    DESCRIPTION                    VARCHAR2(1000),
    TABLE_ID                       VARCHAR2(38) NOT NULL,
    EXPRESSION                     VARCHAR2(1000),
    TYPE                           NUMBER(1,0) NOT NULL,
    SCHEMA_ID                      VARCHAR2(38),
    IS_VALID                       NUMBER(1,0) NOT NULL,
    VIEW_SQL                       VARCHAR2(3000),
    PAIXU_FX                       NUMBER(1,0),
    ORDER_SIGN                     NUMBER(10,0),
    CITE                           VARCHAR2(100),
    VIEW_NAME                      VARCHAR2(100),
    REF_VIEW_ID                    VARCHAR2(200),
    JOIN_TYPE                      NUMBER(1,0),
    CONSTRAINT PK_CWM_VIEWS PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_VIEWS.IS_VALID IS '是否有效，0表示无效，1表示有效';
COMMENT ON COLUMN CWM_VIEWS.VIEW_SQL IS '生成视图的SQL语句';
COMMENT ON COLUMN CWM_VIEWS.PAIXU_FX IS '排序方向，0表示为升序，1表示为降序';
COMMENT ON COLUMN CWM_VIEWS.JOIN_TYPE IS '0为内连接，1为左连接，2为右连接，3为全连接';



--*******************************************--
--             SEQUENCE 相关                 --
--*******************************************--

CREATE SEQUENCE HIBERNATE_SEQUENCE
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE CWM_COLUMN_SEQ
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

--resitriction
CREATE SEQUENCE CWM_RES_SEQ		
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE CWM_SCHEMA_SEQ
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE CWM_TABLE_COLUMN_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE 
NOCACHE
NOORDER ;

CREATE SEQUENCE CWM_TABLE_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE 
NOCACHE
NOORDER ;

CREATE SEQUENCE SEQ_CWM_RELATION_DATA
INCREMENT BY 1
START WITH 2
MAXVALUE 9999999999999999999999999
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;


CREATE SEQUENCE SEQ_CWM_REPORTS
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 0
NOCYCLE 
NOCACHE
NOORDER ;

CREATE SEQUENCE SEQ_TABLE_COLUMN
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE SEQ_CWM_FILE
INCREMENT BY 1
START WITH 1
NOMAXVALUE
MINVALUE 0
NOCYCLE 
NOCACHE
NOORDER ;

CREATE SEQUENCE SEQ_CWM_FOLDER
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
  
  --创建文件类型分组表序列
CREATE SEQUENCE SEQ_CWM_FILE_GROUP
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;