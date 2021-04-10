--------------------动态表相关的表格-----------------
CREATE TABLE CWM_DYNAMIC_RELATION
(
    ID                             VARCHAR2(50),
    FILE_COL                       VARCHAR2(50),
    DB_COL                         VARCHAR2(10),
    COL_REMARK                     VARCHAR2(200),
    TABLE_NAME                     VARCHAR2(50),
    GK_ID                          VARCHAR2(50),
    JOB_ID                         VARCHAR2(50)
);
COMMENT ON COLUMN CWM_DYNAMIC_RELATION.ID IS '主键';
COMMENT ON COLUMN CWM_DYNAMIC_RELATION.FILE_COL IS '数据文件列名';
COMMENT ON COLUMN CWM_DYNAMIC_RELATION.DB_COL IS '列名所对应的动态表的字段';
COMMENT ON COLUMN CWM_DYNAMIC_RELATION.COL_REMARK IS '列名备注信息';
COMMENT ON COLUMN CWM_DYNAMIC_RELATION.TABLE_NAME IS '动态数据类对应的数据类';
COMMENT ON COLUMN CWM_DYNAMIC_RELATION.GK_ID IS '动态数据类对应数据类 的 记录的ID';

CREATE TABLE CWM_PARTITION_REMARK
(
    ID                             VARCHAR2(38),
    TABLE_NAME                     VARCHAR2(50),
    GK_ID                          VARCHAR2(50),
    IMP_TIME                       VARCHAR2(50),
    JOB_ID                         VARCHAR2(50),
    REMARK                         VARCHAR2(200),
    COLS                           VARCHAR2(4000)
);
COMMENT ON COLUMN CWM_PARTITION_REMARK.ID IS '主键';
COMMENT ON COLUMN CWM_PARTITION_REMARK.TABLE_NAME IS '动态表(被导入数据表)名称';
COMMENT ON COLUMN CWM_PARTITION_REMARK.GK_ID IS '动态数据类对应数据类 的 记录的ID';
COMMENT ON COLUMN CWM_PARTITION_REMARK.IMP_TIME IS '导入时间';
COMMENT ON COLUMN CWM_PARTITION_REMARK.JOB_ID IS 'JOB_ID';
COMMENT ON COLUMN CWM_PARTITION_REMARK.REMARK IS '导入备注';
COMMENT ON COLUMN CWM_PARTITION_REMARK.COLS IS '文件中的数据列名';


--*******************************************--
--             SEQUENCE 相关                 --
--*******************************************--
CREATE SEQUENCE SEQ_CWM_PARTITION_REMARK
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE 
CACHE 20
NOORDER ;