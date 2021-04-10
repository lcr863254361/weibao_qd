
--------------------TBOM相关的表格-----------------
CREATE TABLE CWM_DYNAMIC_TBOM
(
    COLUMN_ID                      	VARCHAR2(500),
    DATA_SOURCE                   	VARCHAR2(500),
    ORDER_SIGN                     	VARCHAR2(10),
    TBOM_ID                        	VARCHAR2(38),
    TABLE_ID                       	VARCHAR2(38),
    VIEW_ID                        	VARCHAR2(38),
    ID                             		VARCHAR2(38),
    URL                            		VARCHAR2(500) DEFAULT '',
    DISPLAY 							VARCHAR2(10 BYTE),
    PID 									VARCHAR2(38 BYTE),
    EXP 									VARCHAR2(1000 BYTE),
    ORIGIN_EXP 						VARCHAR2(1000 BYTE)
);
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.COLUMN_ID IS '动态子节点的字段ID，关联CWM_TAB_COLUMNS表';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.DATA_SOURCE IS '动态子节点的数据源集合，以，分割';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.ORDER_SIGN IS '动态子节点的顺序';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.TBOM_ID IS '动态子节点所属的Tbom节点ID';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.TABLE_ID IS '动态子节点的字段所属数据类';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.VIEW_ID IS '动态子节点上级的（第一）数据源，当该数据源是视图时，本字段记录视图的ID';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.ID IS '主键ID';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.URL IS 'URL';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.DISPLAY IS '显示名';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.PID IS 'parentId';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.EXP IS '扩展';
COMMENT ON COLUMN CWM_DYNAMIC_TBOM.ORIGIN_EXP IS '老的扩展';

CREATE TABLE CWM_RELATION_TBOM
(
    NODE_ID                        VARCHAR2(38) NOT NULL,
    RELATION_ID                    VARCHAR2(38) NOT NULL,
    ID                             VARCHAR2(38) NOT NULL,
    CONSTRAINT PK_CWM_RELATION_TBOM PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);

CREATE TABLE CWM_TBOM
(
    ID                             VARCHAR2(38) NOT NULL,
    PID                            VARCHAR2(38),
    TABLE_ID                       VARCHAR2(38),
    VIEW_ID                        VARCHAR2(38),
    TYPE                           NUMBER(1,0),
    NAME                           VARCHAR2(100),
    DESCRIPTION                    VARCHAR2(2000),
    DETAIL_TEXT                    VARCHAR2(3000),
    BIG_IMAGE                      VARCHAR2(500),
    NOR_IMAGE                      VARCHAR2(500),
    SMA_IMAGE                      VARCHAR2(500),
    IS_ROOT                        NUMBER(1,0) NOT NULL,
    ORDER_SIGN                     NUMBER(10,0),
    IS_VALID                       NUMBER(1,0),
    XML_ID                         VARCHAR2(4000),
    SCHEMA_ID                      VARCHAR2(38),
    COLUMN_ID                      VARCHAR2(38),
    COLUMN_NAME                    VARCHAR2(100),
    EXP                            VARCHAR2(1000),
    ORIGIN_EXP                     VARCHAR2(1000),
    URL                            VARCHAR2(500),
    CONSTRAINT PK_CWM_TBOM PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_TBOM.TYPE IS '1为数据类,0为数据视图';
COMMENT ON COLUMN CWM_TBOM.IS_ROOT IS '是否是TBOM树的根节点，0表示否，1表示是';
COMMENT ON COLUMN CWM_TBOM.EXP IS '过滤表达式（供SQL使用）';
COMMENT ON COLUMN CWM_TBOM.ORIGIN_EXP IS '过滤表达式（供TBOM STUDIO使用）';
COMMENT ON COLUMN CWM_TBOM.URL IS '链接地址';

CREATE TABLE CWM_TBOM_DIR
(
    ID                             VARCHAR2(38) NOT NULL,
    NAME                           VARCHAR2(100),
    SCHEMA_ID                      VARCHAR2(38),
    IS_LOCK                        NUMBER(1,0),
    USERNAME                       VARCHAR2(50),
    MODIFIED_TIME                  DATE,
    LOCK_MODIFIED_TIME             DATE,
    IS_DELETE                      NUMBER(1,0),
    TYPE                           NUMBER(1,0),
	order_sign 					   NUMBER(10) DEFAULT 0,
    CONSTRAINT SYS_C0011306 PRIMARY KEY (ID) USING INDEX
        PCTFREE 10
        INITRANS 2
        MAXTRANS 255
);
COMMENT ON COLUMN CWM_TBOM_DIR.TYPE IS 'Tbom类型(0或者空:数据查看,1:文件查看).';

CREATE TABLE CWM_TBOM_FILE
(
    ID                             VARCHAR2(38) NOT NULL,
    TBOM_ID                        VARCHAR2(38),
    FILE_ID                        VARCHAR2(38),
    ORDER_SIGN                     NUMBER(10,0)
);

--增加tbom静态、动态节点的角色权限控制
-- 动态节点增加 SHOWTYPE
ALTER TABLE CWM_DYNAMIC_TBOM ADD (SHOWTYPE VARCHAR2(1 BYTE));
--SHOWTYPE 值： 

--0 按权限表反过滤
--1 按权限表过滤
--2 无
-- 创建动态节点的权限表
CREATE TABLE CWM_DYNAMIC_TBOM_ROLE
(
  NODE_ID      VARCHAR2(38 BYTE),
  ROLE_ID      VARCHAR2(38 BYTE),
  DATA_SOURCE  VARCHAR2(500 BYTE)
);

-- 静态节点增加 SHOWTYPE
ALTER TABLE CWM_TBOM ADD (SHOWTYPE VARCHAR2(1 BYTE));
--SHOWTYPE 值： 1 按权限表过滤
--       0 按权限表反过滤
-- 创建静态节点的权限表
CREATE TABLE CWM_TBOM_ROLE
(
  NODE_ID      VARCHAR2(38 BYTE),
  ROLE_ID      VARCHAR2(38 BYTE),
  DATA_SOURCE  VARCHAR2(500 BYTE)
);

--*******************************************--
--             SEQUENCE 相关                 --
--*******************************************--

--TBOMDir--
CREATE SEQUENCE CWM_DIR_SEQ
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE CWM_TBOM_SEQ
INCREMENT BY 1
START WITH 0
NOMAXVALUE
MINVALUE 0
NOCYCLE 
NOCACHE
NOORDER ;


--CWM_TBOM_FILE
CREATE SEQUENCE SEQ_RELATION_FILE
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

--CWM_RELATION_TBOM
CREATE SEQUENCE SEQ_RELATION_TBOM
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;

CREATE SEQUENCE SEQ_TBOM
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;