--web office
CREATE TABLE Document (
  DocumentID NUMBER(8) NOT NULL,
  RecordID varchar (16) NULL ,
  Template varchar (16) NULL ,
  Subject varchar (254) NULL ,
  Author varchar (64) NULL ,
  FileDate DATE NULL ,
  FileType varchar (50) NULL ,
  HtmlPath varchar (128) NULL ,
  Status varchar (4) NULL ,
  Copies NUMBER(8) NULL ,
  DLCount NUMBER(8) NULL ,
  PRIMARY KEY (DocumentID)
) ;

CREATE SEQUENCE SEQ_Document
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;


CREATE TABLE Document_File (
  FileID NUMBER(8) NOT NULL,
  RecordID varchar (16) NULL ,
  FileName varchar (254) NULL ,
  FileType varchar (4) NULL ,
  FileSize NUMBER(8) NULL ,
  FileDate DATE NULL ,
  FileBody BLOB NULL ,
  FilePath varchar (128) NULL ,
  UserName varchar (64) NULL ,
  Descript varchar (255) NULL ,
  PRIMARY KEY (FileID)
) ;

CREATE SEQUENCE SEQ_Document_File
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

CREATE TABLE Version_File (
  FileID NUMBER(8) NOT NULL,
  RecordID varchar (16) NULL ,
  FileName varchar (254) NULL ,
  FileType varchar (4) NULL ,
  FileSize NUMBER(8) NULL ,
  FileDate DATE NULL ,
  FileBody BLOB NULL ,
  FilePath varchar (128) NULL ,
  UserName varchar (64) NULL ,
  Descript varchar (255) NULL ,
  PRIMARY KEY (FileID)
) ;

CREATE SEQUENCE SEQ_Version_File
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;


CREATE TABLE Document_Signature (
  SignatureID NUMBER(8) NOT NULL,
  RecordID varchar (50) NULL ,
  MarkName varchar (64) NULL ,
  UserName varchar (64) NULL ,
  DateTime DATE NULL ,
  HostName varchar (50) NULL ,
  MarkGuid varchar (128) NULL ,
  PRIMARY KEY (SignatureID)
) ;

CREATE SEQUENCE SEQ_Document_Signature
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

CREATE TABLE Signature(
  SignatureID NUMBER(8) NOT NULL,
  UserName VARCHAR (64) NULL ,
  PassWord VARCHAR (64) NULL ,
  MarkName VARCHAR (250) NULL ,
  MarkType VARCHAR (50) NULL ,
  MarkBody BLOB NULL ,
  MarkPath VARCHAR (250) NULL ,
  MarkSize NUMBER(8) NULL ,
  MarkDate DATE NULL ,
  PRIMARY KEY  (SignatureID)
) ;

CREATE SEQUENCE SEQ_Signature
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

CREATE TABLE BookMarks (
  BookMarkID NUMBER(8) NOT NULL,
  BookMarkName varchar(64) NULL ,
  BookMarkDesc varchar(128) NULL ,
  BookMarkText varchar(200) NULL ,
  PRIMARY KEY (BookMarkID)
) ;

CREATE SEQUENCE SEQ_BookMarks
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

CREATE TABLE Template_File (
  TemplateID NUMBER(8) NOT NULL ,
  RecordID varchar (16) NOT NULL ,
  FileName varchar (254) NULL ,
  FileType varchar (50) NULL ,
  FileSize NUMBER(8) NULL ,
  FileDate DATE NULL ,
  FileBody BLOB NULL ,
  FilePath varchar (255) NULL ,
  UserName varchar (64) NULL ,
  Descript varchar (255) NULL ,
  PRIMARY KEY (TemplateID)
) ;

CREATE SEQUENCE SEQ_Template_File
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;

CREATE TABLE Template_BookMarks (
  BookMarkID NUMBER(8) NOT NULL,
  RecordID varchar (50) NULL ,
  BookMarkName varchar (120) NULL ,
  PRIMARY KEY (BookMarkID)
) ;

CREATE SEQUENCE SEQ_Template_BookMarks
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;


Insert Into BookMarks (BookMarkID,BookMarkName,BookMarkDesc,BookMarkText) values (1,'Caption','[请输入标题]','关于信息化建设的题案');
Insert Into BookMarks (BookMarkID,BookMarkName,BookMarkDesc,BookMarkText) values (2,'Type','[请输入类型]','科普');
Insert Into BookMarks (BookMarkID,BookMarkName,BookMarkDesc,BookMarkText) values (3,'Item','[请输入专题]','专题');
Insert Into BookMarks (BookMarkID,BookMarkName,BookMarkDesc,BookMarkText) values (4,'KeyWord','[请输入关键字]','电脑、拓扑');
Insert Into BookMarks (BookMarkID,BookMarkName,BookMarkDesc,BookMarkText) values (5,'Author','[请输入作者]','作者');
Insert Into BookMarks (BookMarkID,BookMarkName,BookMarkDesc,BookMarkText) values (6,'Sec','[公文密机]','绝密');
Insert Into BookMarks (BookMarkID,BookMarkName,BookMarkDesc,BookMarkText) values (7,'Subject','[公文主题词]','计算机、信息、网络');
Insert Into BookMarks (BookMarkID,BookMarkName,BookMarkDesc,BookMarkText) values (8,'Content','[公文内容]','公文内容：请详细填写');
Insert Into BookMarks (BookMarkID,BookMarkName,BookMarkDesc,BookMarkText) values (9,'Title','[公文标题]','江西省南昌市XXXX公文');
Insert Into BookMarks (BookMarkID,BookMarkName,BookMarkDesc,BookMarkText) values (10,'Description','[公文说明或描叙]','内部公文');

/*用户演示页面数据表*/
create table htmldocument
(
   AutoNo        NUMBER(8)        not null,
   DocumentID    varchar(50),
   XYBH          varchar(64),
   BMJH          varchar(20),
   JF            varchar(128),
   YF            varchar(128),
   HZNR          BLOB,
   QLZR          BLOB,
   CPMC          varchar(254),
   DGSL          varchar(254),
   DGRQ          varchar(254),
   primary key (AutoNo)
);

CREATE SEQUENCE SEQ_htmldocument
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;
  
/*系统印章数据表*/
create table htmlsignature
(
   DocumentID      varchar(254),
   Signature       BLOB,
   SignatureID     varchar(64)
);

/* 日志数据表*/
create table htmlhistory
(
   AutoNo        NUMBER(8)          not null,
   DocumentID    varchar(50),
   SignatureID   varchar(50),
   SignatureName varchar(50),
   SignatureUnit varchar(50),
   SignatureUser varchar(50),
   KeySN         varchar(50),
   SignatureSN   varchar(200),
   SignatureGUID varchar(50),
   IP            varchar(50),
   LogType       varchar(20),
   LogTime       varchar(20),
   primary key (AutoNo)
);

CREATE SEQUENCE SEQ_htmlhistory
  START WITH 7
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  NOCACHE
  NOORDER;