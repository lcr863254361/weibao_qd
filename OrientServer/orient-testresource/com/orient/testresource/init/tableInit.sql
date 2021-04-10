CREATE SEQUENCE "SEQ_T_DEV_TYPE_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_DEV_TYPE_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_NAME_#SEQ_CWM_TABLES-1#" VARCHAR2(200 CHAR) NULL, "C_DESC_#SEQ_CWM_TABLES-1#" VARCHAR2(200 CHAR) NULL, "T_DEV_TYPE_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_DEVICE_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_DEVICE_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_CODE_#SEQ_CWM_TABLES-2#" VARCHAR2(200 CHAR) NULL, "C_USER_#SEQ_CWM_TABLES-2#" VARCHAR2(200 CHAR) NULL, "C_DEPT_#SEQ_CWM_TABLES-2#" VARCHAR2(200 CHAR) NULL, "C_STATE_#SEQ_CWM_TABLES-2#" VARCHAR2(200 CHAR) NULL, "C_GG_#SEQ_CWM_TABLES-2#" VARCHAR2(200 CHAR) NULL, "C_NAME_#SEQ_CWM_TABLES-2#" VARCHAR2(200 CHAR) NULL, "C_STIME_#SEQ_CWM_TABLES-2#" TIMESTAMP(6)  NULL, "T_DEV_TYPE_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_SBSX_#SEQ_CWM_TABLES-2#" CLOB NULL, "T_SBBQ_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_SBBQ_#SEQ_CWM_TABLES-2#" VARCHAR2(200 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_HJFL_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_HJFL_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_MS_#SEQ_CWM_TABLES-18#" VARCHAR2(200 CHAR) NULL, "C_FLMC_#SEQ_CWM_TABLES-18#" VARCHAR2(200 CHAR) NULL, "T_HJFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_JLFL_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_JLFL_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "T_JLFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_FLMC_#SEQ_CWM_TABLES-9#" VARCHAR2(200 CHAR) NULL, "C_MS_#SEQ_CWM_TABLES-9#" VARCHAR2(200 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_JLSB_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_JLSB_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_LLSM_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "C_SYSM_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "T_JLSBFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_SCJYRQ_#SEQ_CWM_TABLES-5#" TIMESTAMP(6)  NULL, "C_XCJYRQ_#SEQ_CWM_TABLES-5#" TIMESTAMP(6)  NULL, "C_LJSY_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "C_BZ_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "C_JYRY_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "C_GLZT_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "T_DEV_TYPE_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_JLBH_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "T_DEVICE_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_JYZQ_#SEQ_CWM_TABLES-5#" NUMBER(10) NULL, "C_SCHEMA_ID_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "C_TAB_NAME_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "C_DATA_ID_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "T_JLFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_MC_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "C_SJB_#SEQ_CWM_TABLES-5#" VARCHAR2(200 CHAR) NULL, "C_JLSX_#SEQ_CWM_TABLES-5#" CLOB NULL)

CREATE SEQUENCE "SEQ_T_RYFL_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_RYFL_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "T_RYFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_FLMC_#SEQ_CWM_TABLES-10#" VARCHAR2(200 CHAR) NULL, "C_MS_#SEQ_CWM_TABLES-10#" VARCHAR2(200 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_SBBQ_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SBBQ_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_BQMC_#SEQ_CWM_TABLES-20#" VARCHAR2(200 CHAR) NULL, "C_MS_#SEQ_CWM_TABLES-20#" VARCHAR2(200 CHAR) NULL, "T_SBBQ_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_SBGZJL_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SBGZJL_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_GZLX_#SEQ_CWM_TABLES-21#" VARCHAR2(200 CHAR) NULL, "C_GZSJ_#SEQ_CWM_TABLES-21#" TIMESTAMP(6)  NULL, "C_GZMS_#SEQ_CWM_TABLES-21#" CLOB NULL, "T_DEVICE_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_FXR_#SEQ_CWM_TABLES-21#" VARCHAR2(200 CHAR) NULL, "C_JJQKSM_#SEQ_CWM_TABLES-21#" CLOB NULL)

CREATE SEQUENCE "SEQ_T_SBJLJL_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SBJLJL_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_JLBH_#SEQ_CWM_TABLES-7#" VARCHAR2(200 CHAR) NULL, "C_JLRY_#SEQ_CWM_TABLES-7#" VARCHAR2(200 CHAR) NULL, "C_JLHGZ_#SEQ_CWM_TABLES-7#" VARCHAR2(255 CHAR) NULL, "C_JLSJ_#SEQ_CWM_TABLES-7#" TIMESTAMP(6)  NULL, "T_JLSB_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_JLSX_#SEQ_CWM_TABLES-7#" CLOB NULL)

CREATE SEQUENCE "SEQ_T_SBSYJL_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SBSYJL_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_SYCS_#SEQ_CWM_TABLES-4#" NUMBER(10) NULL, "C_GHSJ_#SEQ_CWM_TABLES-4#" TIMESTAMP(6)  NULL, "T_DEVICE_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_JCSJ_#SEQ_CWM_TABLES-4#" TIMESTAMP(6)  NULL, "C_SYRW_#SEQ_CWM_TABLES-4#" VARCHAR2(200 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_SBWXJL_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SBWXJL_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_WTDW_#SEQ_CWM_TABLES-3#" VARCHAR2(200 CHAR) NULL, "T_DEVICE_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_WXSJ_#SEQ_CWM_TABLES-3#" TIMESTAMP(6)  NULL, "C_WHRY_#SEQ_CWM_TABLES-3#" VARCHAR2(200 CHAR) NULL, "C_WXDH_#SEQ_CWM_TABLES-3#" VARCHAR2(200 CHAR) NULL, "C_JJQK_#SEQ_CWM_TABLES-3#" VARCHAR2(200 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_SYHJ_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SYHJ_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_DQY_#SEQ_CWM_TABLES-19#" FLOAT NULL, "C_CYRY_#SEQ_CWM_TABLES-19#" VARCHAR2(200 CHAR) NULL, "C_WD_#SEQ_CWM_TABLES-19#" FLOAT NULL, "C_CYSJ_#SEQ_CWM_TABLES-19#" TIMESTAMP(6)  NULL, "C_SD_#SEQ_CWM_TABLES-19#" FLOAT NULL, "T_HJFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_SYJ_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SYJ_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_SCDW_#SEQ_CWM_TABLES-15#" VARCHAR2(200 CHAR) NULL, "T_SYJFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_YZJD_#SEQ_CWM_TABLES-15#" VARCHAR2(200 CHAR) NULL, "C_MC_#SEQ_CWM_TABLES-15#" VARCHAR2(200 CHAR) NULL, "C_SJDW_#SEQ_CWM_TABLES-15#" VARCHAR2(200 CHAR) NULL, "C_PC_#SEQ_CWM_TABLES-15#" VARCHAR2(200 CHAR) NULL, "C_BH_#SEQ_CWM_TABLES-15#" VARCHAR2(200 CHAR) NULL, "C_CJR_#SEQ_CWM_TABLES-15#" VARCHAR2(200 CHAR) NULL, "C_TH_#SEQ_CWM_TABLES-15#" VARCHAR2(200 CHAR) NULL, "C_CJSJ_#SEQ_CWM_TABLES-15#" TIMESTAMP(6)  NULL, "C_SYJSX_#SEQ_CWM_TABLES-15#" CLOB NULL)

CREATE SEQUENCE "SEQ_T_SYJFL_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SYJFL_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_MS_#SEQ_CWM_TABLES-14#" VARCHAR2(200 CHAR) NULL, "C_FLMC_#SEQ_CWM_TABLES-14#" VARCHAR2(200 CHAR) NULL, "T_SYJFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_SYRY_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SYRY_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_DH_#SEQ_CWM_TABLES-11#" VARCHAR2(200 CHAR) NULL, "C_MJ_#SEQ_CWM_TABLES-11#" VARCHAR2(200 CHAR) NULL, "C_SR_#SEQ_CWM_TABLES-11#" TIMESTAMP(6)  NULL, "C_ZW_#SEQ_CWM_TABLES-11#" VARCHAR2(200 CHAR) NULL, "C_XB_#SEQ_CWM_TABLES-11#" VARCHAR2(200 CHAR) NULL, "C_XTYH_#SEQ_CWM_TABLES-11#" VARCHAR2(200 CHAR) NULL, "C_SJ_#SEQ_CWM_TABLES-11#" VARCHAR2(200 CHAR) NULL, "C_YJ_#SEQ_CWM_TABLES-11#" VARCHAR2(200 CHAR) NULL, "C_XM_#SEQ_CWM_TABLES-11#" VARCHAR2(200 CHAR) NULL, "T_RYFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_BM_#SEQ_CWM_TABLES-11#" VARCHAR2(200 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_SYTD_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SYTD_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "T_SYTD_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_MC_#SEQ_CWM_TABLES-12#" VARCHAR2(200 CHAR) NULL, "C_MS_#SEQ_CWM_TABLES-12#" CLOB NULL)

CREATE SEQUENCE "SEQ_T_SYXT_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SYXT_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_SYDW_#SEQ_CWM_TABLES-22#" VARCHAR2(200 CHAR) NULL, "C_SYJ_#SEQ_CWM_TABLES-22#" VARCHAR2(200 CHAR) NULL, "C_SYTD_#SEQ_CWM_TABLES-22#" VARCHAR2(200 CHAR) NULL, "C_SYYP_#SEQ_CWM_TABLES-22#" VARCHAR2(200 CHAR) NULL, "C_XTMC_#SEQ_CWM_TABLES-22#" VARCHAR2(200 CHAR) NULL, "C_SB_#SEQ_CWM_TABLES-22#" VARCHAR2(200 CHAR) NULL, "C_ZRR_#SEQ_CWM_TABLES-22#" VARCHAR2(200 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_SYYP_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_SYYP_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "T_YPFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_JLDW_#SEQ_CWM_TABLES-17#" VARCHAR2(200 CHAR) NULL, "C_WHRQ_#SEQ_CWM_TABLES-17#" TIMESTAMP(6)  NULL, "C_BH_#SEQ_CWM_TABLES-17#" VARCHAR2(200 CHAR) NULL, "C_CFDD_#SEQ_CWM_TABLES-17#" VARCHAR2(200 CHAR) NULL, "C_WHR_#SEQ_CWM_TABLES-17#" VARCHAR2(200 CHAR) NULL, "C_MC_#SEQ_CWM_TABLES-17#" VARCHAR2(200 CHAR) NULL, "C_PC_#SEQ_CWM_TABLES-17#" VARCHAR2(200 CHAR) NULL, "C_KC_#SEQ_CWM_TABLES-17#" VARCHAR2(200 CHAR) NULL, "C_YPSX_#SEQ_CWM_TABLES-17#" CLOB NULL)

CREATE SEQUENCE "SEQ_T_TD_RY_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_TD_RY_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "T_SYRY_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "T_SYTD_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_CODE_#SEQ_CWM_TABLES-13#" VARCHAR2(200 CHAR) NULL)

CREATE SEQUENCE "SEQ_T_YPFL_#SEQ_CWM_SCHEMA-1#"
CREATE TABLE "T_YPFL_#SEQ_CWM_SCHEMA-1#" ("ID" VARCHAR2(38 CHAR) NOT NULL, "SYS_DATE_TIME" TIMESTAMP(6)  NULL, "SYS_USERNAME" VARCHAR2(100 CHAR) NULL, "SYS_IS_DELETE" NUMBER(10) NULL, "SYS_SECRECY" VARCHAR2(100 CHAR) NULL, "SYS_SCHEMA" VARCHAR2(38 CHAR) NULL, "SYS_OPERATE" VARCHAR2(100 CHAR) NULL, "SYS_FLOW" VARCHAR2(200 CHAR) NULL, "C_FLMC_#SEQ_CWM_TABLES-16#" VARCHAR2(200 CHAR) NULL, "T_YPFL_#SEQ_CWM_SCHEMA-1#_ID" VARCHAR2(38 CHAR) NULL, "C_MS_#SEQ_CWM_TABLES-16#" VARCHAR2(200 CHAR) NULL)

ALTER TABLE "T_DEV_TYPE_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_DEV_TYPE_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_DEVICE_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_DEVICE_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_HJFL_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_HJFL_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_JLFL_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_JLFL_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_JLSB_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_JLSB_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_RYFL_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_RYFL_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SBBQ_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SBBQ_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SBGZJL_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SBGZJL_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SBJLJL_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SBJLJL_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SBSYJL_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SBSYJL_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SBWXJL_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SBWXJL_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SYHJ_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SYHJ_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SYJ_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SYJ_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SYJFL_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SYJFL_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SYRY_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SYRY_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SYTD_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SYTD_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SYXT_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SYXT_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_SYYP_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_SYYP_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_TD_RY_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_TD_RY_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")
ALTER TABLE "T_YPFL_#SEQ_CWM_SCHEMA-1#" ADD CHECK ("ID" IS NOT NULL)
ALTER TABLE "T_YPFL_#SEQ_CWM_SCHEMA-1#" ADD PRIMARY KEY ("ID")