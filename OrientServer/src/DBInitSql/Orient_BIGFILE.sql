--*******************************************--
--              大数据文件相关                    --
--*******************************************--

--任务处理意见
CREATE TABLE CWM_HADOOP_FILE(
  ID            NUMBER (*),
  CWM_FILE_ID        NUMBER (*)              NOT NULL,
  STATE       NUMBER (*)                     NOT NULL,
  FDSPATH    VARCHAR2(200 CHAR)                           ,
  MRJOBNAME   VARCHAR2(400 CHAR)
);

CREATE SEQUENCE SEQ_CWM_HADOOP_FILE
INCREMENT BY 1
START WITH 2
NOMAXVALUE
NOMINVALUE
NOCYCLE
CACHE 20
NOORDER