package com.orient.weibao.mbg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DivingStatisiticsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DivingStatisiticsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeIsNull() {
            addCriterion("SYS_DATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeIsNotNull() {
            addCriterion("SYS_DATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeEqualTo(Date value) {
            addCriterion("SYS_DATE_TIME =", value, "sysDateTime");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeNotEqualTo(Date value) {
            addCriterion("SYS_DATE_TIME <>", value, "sysDateTime");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeGreaterThan(Date value) {
            addCriterion("SYS_DATE_TIME >", value, "sysDateTime");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("SYS_DATE_TIME >=", value, "sysDateTime");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeLessThan(Date value) {
            addCriterion("SYS_DATE_TIME <", value, "sysDateTime");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeLessThanOrEqualTo(Date value) {
            addCriterion("SYS_DATE_TIME <=", value, "sysDateTime");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeIn(List<Date> values) {
            addCriterion("SYS_DATE_TIME in", values, "sysDateTime");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeNotIn(List<Date> values) {
            addCriterion("SYS_DATE_TIME not in", values, "sysDateTime");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeBetween(Date value1, Date value2) {
            addCriterion("SYS_DATE_TIME between", value1, value2, "sysDateTime");
            return (Criteria) this;
        }

        public Criteria andSysDateTimeNotBetween(Date value1, Date value2) {
            addCriterion("SYS_DATE_TIME not between", value1, value2, "sysDateTime");
            return (Criteria) this;
        }

        public Criteria andSysUsernameIsNull() {
            addCriterion("SYS_USERNAME is null");
            return (Criteria) this;
        }

        public Criteria andSysUsernameIsNotNull() {
            addCriterion("SYS_USERNAME is not null");
            return (Criteria) this;
        }

        public Criteria andSysUsernameEqualTo(String value) {
            addCriterion("SYS_USERNAME =", value, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameNotEqualTo(String value) {
            addCriterion("SYS_USERNAME <>", value, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameGreaterThan(String value) {
            addCriterion("SYS_USERNAME >", value, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("SYS_USERNAME >=", value, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameLessThan(String value) {
            addCriterion("SYS_USERNAME <", value, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameLessThanOrEqualTo(String value) {
            addCriterion("SYS_USERNAME <=", value, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameLike(String value) {
            addCriterion("SYS_USERNAME like", value, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameNotLike(String value) {
            addCriterion("SYS_USERNAME not like", value, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameIn(List<String> values) {
            addCriterion("SYS_USERNAME in", values, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameNotIn(List<String> values) {
            addCriterion("SYS_USERNAME not in", values, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameBetween(String value1, String value2) {
            addCriterion("SYS_USERNAME between", value1, value2, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysUsernameNotBetween(String value1, String value2) {
            addCriterion("SYS_USERNAME not between", value1, value2, "sysUsername");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteIsNull() {
            addCriterion("SYS_IS_DELETE is null");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteIsNotNull() {
            addCriterion("SYS_IS_DELETE is not null");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteEqualTo(Long value) {
            addCriterion("SYS_IS_DELETE =", value, "sysIsDelete");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteNotEqualTo(Long value) {
            addCriterion("SYS_IS_DELETE <>", value, "sysIsDelete");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteGreaterThan(Long value) {
            addCriterion("SYS_IS_DELETE >", value, "sysIsDelete");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteGreaterThanOrEqualTo(Long value) {
            addCriterion("SYS_IS_DELETE >=", value, "sysIsDelete");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteLessThan(Long value) {
            addCriterion("SYS_IS_DELETE <", value, "sysIsDelete");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteLessThanOrEqualTo(Long value) {
            addCriterion("SYS_IS_DELETE <=", value, "sysIsDelete");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteIn(List<Long> values) {
            addCriterion("SYS_IS_DELETE in", values, "sysIsDelete");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteNotIn(List<Long> values) {
            addCriterion("SYS_IS_DELETE not in", values, "sysIsDelete");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteBetween(Long value1, Long value2) {
            addCriterion("SYS_IS_DELETE between", value1, value2, "sysIsDelete");
            return (Criteria) this;
        }

        public Criteria andSysIsDeleteNotBetween(Long value1, Long value2) {
            addCriterion("SYS_IS_DELETE not between", value1, value2, "sysIsDelete");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyIsNull() {
            addCriterion("SYS_SECRECY is null");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyIsNotNull() {
            addCriterion("SYS_SECRECY is not null");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyEqualTo(String value) {
            addCriterion("SYS_SECRECY =", value, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyNotEqualTo(String value) {
            addCriterion("SYS_SECRECY <>", value, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyGreaterThan(String value) {
            addCriterion("SYS_SECRECY >", value, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyGreaterThanOrEqualTo(String value) {
            addCriterion("SYS_SECRECY >=", value, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyLessThan(String value) {
            addCriterion("SYS_SECRECY <", value, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyLessThanOrEqualTo(String value) {
            addCriterion("SYS_SECRECY <=", value, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyLike(String value) {
            addCriterion("SYS_SECRECY like", value, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyNotLike(String value) {
            addCriterion("SYS_SECRECY not like", value, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyIn(List<String> values) {
            addCriterion("SYS_SECRECY in", values, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyNotIn(List<String> values) {
            addCriterion("SYS_SECRECY not in", values, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyBetween(String value1, String value2) {
            addCriterion("SYS_SECRECY between", value1, value2, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSecrecyNotBetween(String value1, String value2) {
            addCriterion("SYS_SECRECY not between", value1, value2, "sysSecrecy");
            return (Criteria) this;
        }

        public Criteria andSysSchemaIsNull() {
            addCriterion("SYS_SCHEMA is null");
            return (Criteria) this;
        }

        public Criteria andSysSchemaIsNotNull() {
            addCriterion("SYS_SCHEMA is not null");
            return (Criteria) this;
        }

        public Criteria andSysSchemaEqualTo(String value) {
            addCriterion("SYS_SCHEMA =", value, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaNotEqualTo(String value) {
            addCriterion("SYS_SCHEMA <>", value, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaGreaterThan(String value) {
            addCriterion("SYS_SCHEMA >", value, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaGreaterThanOrEqualTo(String value) {
            addCriterion("SYS_SCHEMA >=", value, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaLessThan(String value) {
            addCriterion("SYS_SCHEMA <", value, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaLessThanOrEqualTo(String value) {
            addCriterion("SYS_SCHEMA <=", value, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaLike(String value) {
            addCriterion("SYS_SCHEMA like", value, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaNotLike(String value) {
            addCriterion("SYS_SCHEMA not like", value, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaIn(List<String> values) {
            addCriterion("SYS_SCHEMA in", values, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaNotIn(List<String> values) {
            addCriterion("SYS_SCHEMA not in", values, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaBetween(String value1, String value2) {
            addCriterion("SYS_SCHEMA between", value1, value2, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysSchemaNotBetween(String value1, String value2) {
            addCriterion("SYS_SCHEMA not between", value1, value2, "sysSchema");
            return (Criteria) this;
        }

        public Criteria andSysOperateIsNull() {
            addCriterion("SYS_OPERATE is null");
            return (Criteria) this;
        }

        public Criteria andSysOperateIsNotNull() {
            addCriterion("SYS_OPERATE is not null");
            return (Criteria) this;
        }

        public Criteria andSysOperateEqualTo(String value) {
            addCriterion("SYS_OPERATE =", value, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateNotEqualTo(String value) {
            addCriterion("SYS_OPERATE <>", value, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateGreaterThan(String value) {
            addCriterion("SYS_OPERATE >", value, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateGreaterThanOrEqualTo(String value) {
            addCriterion("SYS_OPERATE >=", value, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateLessThan(String value) {
            addCriterion("SYS_OPERATE <", value, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateLessThanOrEqualTo(String value) {
            addCriterion("SYS_OPERATE <=", value, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateLike(String value) {
            addCriterion("SYS_OPERATE like", value, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateNotLike(String value) {
            addCriterion("SYS_OPERATE not like", value, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateIn(List<String> values) {
            addCriterion("SYS_OPERATE in", values, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateNotIn(List<String> values) {
            addCriterion("SYS_OPERATE not in", values, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateBetween(String value1, String value2) {
            addCriterion("SYS_OPERATE between", value1, value2, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysOperateNotBetween(String value1, String value2) {
            addCriterion("SYS_OPERATE not between", value1, value2, "sysOperate");
            return (Criteria) this;
        }

        public Criteria andSysFlowIsNull() {
            addCriterion("SYS_FLOW is null");
            return (Criteria) this;
        }

        public Criteria andSysFlowIsNotNull() {
            addCriterion("SYS_FLOW is not null");
            return (Criteria) this;
        }

        public Criteria andSysFlowEqualTo(String value) {
            addCriterion("SYS_FLOW =", value, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowNotEqualTo(String value) {
            addCriterion("SYS_FLOW <>", value, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowGreaterThan(String value) {
            addCriterion("SYS_FLOW >", value, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowGreaterThanOrEqualTo(String value) {
            addCriterion("SYS_FLOW >=", value, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowLessThan(String value) {
            addCriterion("SYS_FLOW <", value, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowLessThanOrEqualTo(String value) {
            addCriterion("SYS_FLOW <=", value, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowLike(String value) {
            addCriterion("SYS_FLOW like", value, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowNotLike(String value) {
            addCriterion("SYS_FLOW not like", value, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowIn(List<String> values) {
            addCriterion("SYS_FLOW in", values, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowNotIn(List<String> values) {
            addCriterion("SYS_FLOW not in", values, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowBetween(String value1, String value2) {
            addCriterion("SYS_FLOW between", value1, value2, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andSysFlowNotBetween(String value1, String value2) {
            addCriterion("SYS_FLOW not between", value1, value2, "sysFlow");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446IsNull() {
            addCriterion("C_SEA_AREA_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446IsNotNull() {
            addCriterion("C_SEA_AREA_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446EqualTo(String value) {
            addCriterion("C_SEA_AREA_3446 =", value, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446NotEqualTo(String value) {
            addCriterion("C_SEA_AREA_3446 <>", value, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446GreaterThan(String value) {
            addCriterion("C_SEA_AREA_3446 >", value, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_SEA_AREA_3446 >=", value, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446LessThan(String value) {
            addCriterion("C_SEA_AREA_3446 <", value, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446LessThanOrEqualTo(String value) {
            addCriterion("C_SEA_AREA_3446 <=", value, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446Like(String value) {
            addCriterion("C_SEA_AREA_3446 like", value, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446NotLike(String value) {
            addCriterion("C_SEA_AREA_3446 not like", value, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446In(List<String> values) {
            addCriterion("C_SEA_AREA_3446 in", values, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446NotIn(List<String> values) {
            addCriterion("C_SEA_AREA_3446 not in", values, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446Between(String value1, String value2) {
            addCriterion("C_SEA_AREA_3446 between", value1, value2, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3446NotBetween(String value1, String value2) {
            addCriterion("C_SEA_AREA_3446 not between", value1, value2, "cSeaArea3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446IsNull() {
            addCriterion("C_WORK_TIME_LONG_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446IsNotNull() {
            addCriterion("C_WORK_TIME_LONG_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446EqualTo(String value) {
            addCriterion("C_WORK_TIME_LONG_3446 =", value, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446NotEqualTo(String value) {
            addCriterion("C_WORK_TIME_LONG_3446 <>", value, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446GreaterThan(String value) {
            addCriterion("C_WORK_TIME_LONG_3446 >", value, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_WORK_TIME_LONG_3446 >=", value, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446LessThan(String value) {
            addCriterion("C_WORK_TIME_LONG_3446 <", value, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446LessThanOrEqualTo(String value) {
            addCriterion("C_WORK_TIME_LONG_3446 <=", value, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446Like(String value) {
            addCriterion("C_WORK_TIME_LONG_3446 like", value, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446NotLike(String value) {
            addCriterion("C_WORK_TIME_LONG_3446 not like", value, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446In(List<String> values) {
            addCriterion("C_WORK_TIME_LONG_3446 in", values, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446NotIn(List<String> values) {
            addCriterion("C_WORK_TIME_LONG_3446 not in", values, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446Between(String value1, String value2) {
            addCriterion("C_WORK_TIME_LONG_3446 between", value1, value2, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWorkTimeLong3446NotBetween(String value1, String value2) {
            addCriterion("C_WORK_TIME_LONG_3446 not between", value1, value2, "cWorkTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446IsNull() {
            addCriterion("C_HANGCI_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCHangci3446IsNotNull() {
            addCriterion("C_HANGCI_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCHangci3446EqualTo(String value) {
            addCriterion("C_HANGCI_3446 =", value, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446NotEqualTo(String value) {
            addCriterion("C_HANGCI_3446 <>", value, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446GreaterThan(String value) {
            addCriterion("C_HANGCI_3446 >", value, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_HANGCI_3446 >=", value, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446LessThan(String value) {
            addCriterion("C_HANGCI_3446 <", value, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446LessThanOrEqualTo(String value) {
            addCriterion("C_HANGCI_3446 <=", value, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446Like(String value) {
            addCriterion("C_HANGCI_3446 like", value, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446NotLike(String value) {
            addCriterion("C_HANGCI_3446 not like", value, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446In(List<String> values) {
            addCriterion("C_HANGCI_3446 in", values, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446NotIn(List<String> values) {
            addCriterion("C_HANGCI_3446 not in", values, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446Between(String value1, String value2) {
            addCriterion("C_HANGCI_3446 between", value1, value2, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCHangci3446NotBetween(String value1, String value2) {
            addCriterion("C_HANGCI_3446 not between", value1, value2, "cHangci3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446IsNull() {
            addCriterion("C_SMALL_BOAT_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446IsNotNull() {
            addCriterion("C_SMALL_BOAT_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446EqualTo(String value) {
            addCriterion("C_SMALL_BOAT_3446 =", value, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446NotEqualTo(String value) {
            addCriterion("C_SMALL_BOAT_3446 <>", value, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446GreaterThan(String value) {
            addCriterion("C_SMALL_BOAT_3446 >", value, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_SMALL_BOAT_3446 >=", value, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446LessThan(String value) {
            addCriterion("C_SMALL_BOAT_3446 <", value, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446LessThanOrEqualTo(String value) {
            addCriterion("C_SMALL_BOAT_3446 <=", value, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446Like(String value) {
            addCriterion("C_SMALL_BOAT_3446 like", value, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446NotLike(String value) {
            addCriterion("C_SMALL_BOAT_3446 not like", value, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446In(List<String> values) {
            addCriterion("C_SMALL_BOAT_3446 in", values, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446NotIn(List<String> values) {
            addCriterion("C_SMALL_BOAT_3446 not in", values, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446Between(String value1, String value2) {
            addCriterion("C_SMALL_BOAT_3446 between", value1, value2, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCSmallBoat3446NotBetween(String value1, String value2) {
            addCriterion("C_SMALL_BOAT_3446 not between", value1, value2, "cSmallBoat3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446IsNull() {
            addCriterion("C_COMPANY_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCCompany3446IsNotNull() {
            addCriterion("C_COMPANY_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCCompany3446EqualTo(String value) {
            addCriterion("C_COMPANY_3446 =", value, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446NotEqualTo(String value) {
            addCriterion("C_COMPANY_3446 <>", value, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446GreaterThan(String value) {
            addCriterion("C_COMPANY_3446 >", value, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_COMPANY_3446 >=", value, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446LessThan(String value) {
            addCriterion("C_COMPANY_3446 <", value, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446LessThanOrEqualTo(String value) {
            addCriterion("C_COMPANY_3446 <=", value, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446Like(String value) {
            addCriterion("C_COMPANY_3446 like", value, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446NotLike(String value) {
            addCriterion("C_COMPANY_3446 not like", value, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446In(List<String> values) {
            addCriterion("C_COMPANY_3446 in", values, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446NotIn(List<String> values) {
            addCriterion("C_COMPANY_3446 not in", values, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446Between(String value1, String value2) {
            addCriterion("C_COMPANY_3446 between", value1, value2, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCCompany3446NotBetween(String value1, String value2) {
            addCriterion("C_COMPANY_3446 not between", value1, value2, "cCompany3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446IsNull() {
            addCriterion("C_DIVING_TASK_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446IsNotNull() {
            addCriterion("C_DIVING_TASK_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446EqualTo(String value) {
            addCriterion("C_DIVING_TASK_3446 =", value, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446NotEqualTo(String value) {
            addCriterion("C_DIVING_TASK_3446 <>", value, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446GreaterThan(String value) {
            addCriterion("C_DIVING_TASK_3446 >", value, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_DIVING_TASK_3446 >=", value, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446LessThan(String value) {
            addCriterion("C_DIVING_TASK_3446 <", value, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446LessThanOrEqualTo(String value) {
            addCriterion("C_DIVING_TASK_3446 <=", value, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446Like(String value) {
            addCriterion("C_DIVING_TASK_3446 like", value, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446NotLike(String value) {
            addCriterion("C_DIVING_TASK_3446 not like", value, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446In(List<String> values) {
            addCriterion("C_DIVING_TASK_3446 in", values, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446NotIn(List<String> values) {
            addCriterion("C_DIVING_TASK_3446 not in", values, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446Between(String value1, String value2) {
            addCriterion("C_DIVING_TASK_3446 between", value1, value2, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTask3446NotBetween(String value1, String value2) {
            addCriterion("C_DIVING_TASK_3446 not between", value1, value2, "cDivingTask3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446IsNull() {
            addCriterion("C_UNDER_WATER_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446IsNotNull() {
            addCriterion("C_UNDER_WATER_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446EqualTo(String value) {
            addCriterion("C_UNDER_WATER_3446 =", value, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446NotEqualTo(String value) {
            addCriterion("C_UNDER_WATER_3446 <>", value, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446GreaterThan(String value) {
            addCriterion("C_UNDER_WATER_3446 >", value, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_UNDER_WATER_3446 >=", value, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446LessThan(String value) {
            addCriterion("C_UNDER_WATER_3446 <", value, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446LessThanOrEqualTo(String value) {
            addCriterion("C_UNDER_WATER_3446 <=", value, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446Like(String value) {
            addCriterion("C_UNDER_WATER_3446 like", value, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446NotLike(String value) {
            addCriterion("C_UNDER_WATER_3446 not like", value, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446In(List<String> values) {
            addCriterion("C_UNDER_WATER_3446 in", values, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446NotIn(List<String> values) {
            addCriterion("C_UNDER_WATER_3446 not in", values, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446Between(String value1, String value2) {
            addCriterion("C_UNDER_WATER_3446 between", value1, value2, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCUnderWater3446NotBetween(String value1, String value2) {
            addCriterion("C_UNDER_WATER_3446 not between", value1, value2, "cUnderWater3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446IsNull() {
            addCriterion("C_DEPTH_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCDepth3446IsNotNull() {
            addCriterion("C_DEPTH_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCDepth3446EqualTo(String value) {
            addCriterion("C_DEPTH_3446 =", value, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446NotEqualTo(String value) {
            addCriterion("C_DEPTH_3446 <>", value, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446GreaterThan(String value) {
            addCriterion("C_DEPTH_3446 >", value, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_DEPTH_3446 >=", value, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446LessThan(String value) {
            addCriterion("C_DEPTH_3446 <", value, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446LessThanOrEqualTo(String value) {
            addCriterion("C_DEPTH_3446 <=", value, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446Like(String value) {
            addCriterion("C_DEPTH_3446 like", value, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446NotLike(String value) {
            addCriterion("C_DEPTH_3446 not like", value, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446In(List<String> values) {
            addCriterion("C_DEPTH_3446 in", values, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446NotIn(List<String> values) {
            addCriterion("C_DEPTH_3446 not in", values, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446Between(String value1, String value2) {
            addCriterion("C_DEPTH_3446 between", value1, value2, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCDepth3446NotBetween(String value1, String value2) {
            addCriterion("C_DEPTH_3446 not between", value1, value2, "cDepth3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446IsNull() {
            addCriterion("C_PERSON_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCPerson3446IsNotNull() {
            addCriterion("C_PERSON_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCPerson3446EqualTo(String value) {
            addCriterion("C_PERSON_3446 =", value, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446NotEqualTo(String value) {
            addCriterion("C_PERSON_3446 <>", value, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446GreaterThan(String value) {
            addCriterion("C_PERSON_3446 >", value, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_PERSON_3446 >=", value, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446LessThan(String value) {
            addCriterion("C_PERSON_3446 <", value, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446LessThanOrEqualTo(String value) {
            addCriterion("C_PERSON_3446 <=", value, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446Like(String value) {
            addCriterion("C_PERSON_3446 like", value, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446NotLike(String value) {
            addCriterion("C_PERSON_3446 not like", value, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446In(List<String> values) {
            addCriterion("C_PERSON_3446 in", values, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446NotIn(List<String> values) {
            addCriterion("C_PERSON_3446 not in", values, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446Between(String value1, String value2) {
            addCriterion("C_PERSON_3446 between", value1, value2, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCPerson3446NotBetween(String value1, String value2) {
            addCriterion("C_PERSON_3446 not between", value1, value2, "cPerson3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446IsNull() {
            addCriterion("C_WORK_CONTENT_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446IsNotNull() {
            addCriterion("C_WORK_CONTENT_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446EqualTo(String value) {
            addCriterion("C_WORK_CONTENT_3446 =", value, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446NotEqualTo(String value) {
            addCriterion("C_WORK_CONTENT_3446 <>", value, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446GreaterThan(String value) {
            addCriterion("C_WORK_CONTENT_3446 >", value, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_WORK_CONTENT_3446 >=", value, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446LessThan(String value) {
            addCriterion("C_WORK_CONTENT_3446 <", value, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446LessThanOrEqualTo(String value) {
            addCriterion("C_WORK_CONTENT_3446 <=", value, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446Like(String value) {
            addCriterion("C_WORK_CONTENT_3446 like", value, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446NotLike(String value) {
            addCriterion("C_WORK_CONTENT_3446 not like", value, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446In(List<String> values) {
            addCriterion("C_WORK_CONTENT_3446 in", values, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446NotIn(List<String> values) {
            addCriterion("C_WORK_CONTENT_3446 not in", values, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446Between(String value1, String value2) {
            addCriterion("C_WORK_CONTENT_3446 between", value1, value2, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWorkContent3446NotBetween(String value1, String value2) {
            addCriterion("C_WORK_CONTENT_3446 not between", value1, value2, "cWorkContent3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446IsNull() {
            addCriterion("C_WATER_TIME_LONG_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446IsNotNull() {
            addCriterion("C_WATER_TIME_LONG_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446EqualTo(String value) {
            addCriterion("C_WATER_TIME_LONG_3446 =", value, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446NotEqualTo(String value) {
            addCriterion("C_WATER_TIME_LONG_3446 <>", value, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446GreaterThan(String value) {
            addCriterion("C_WATER_TIME_LONG_3446 >", value, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_WATER_TIME_LONG_3446 >=", value, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446LessThan(String value) {
            addCriterion("C_WATER_TIME_LONG_3446 <", value, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446LessThanOrEqualTo(String value) {
            addCriterion("C_WATER_TIME_LONG_3446 <=", value, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446Like(String value) {
            addCriterion("C_WATER_TIME_LONG_3446 like", value, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446NotLike(String value) {
            addCriterion("C_WATER_TIME_LONG_3446 not like", value, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446In(List<String> values) {
            addCriterion("C_WATER_TIME_LONG_3446 in", values, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446NotIn(List<String> values) {
            addCriterion("C_WATER_TIME_LONG_3446 not in", values, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446Between(String value1, String value2) {
            addCriterion("C_WATER_TIME_LONG_3446 between", value1, value2, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCWaterTimeLong3446NotBetween(String value1, String value2) {
            addCriterion("C_WATER_TIME_LONG_3446 not between", value1, value2, "cWaterTimeLong3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446IsNull() {
            addCriterion("C_DIVING_TIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446IsNotNull() {
            addCriterion("C_DIVING_TIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446EqualTo(String value) {
            addCriterion("C_DIVING_TIME_3446 =", value, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446NotEqualTo(String value) {
            addCriterion("C_DIVING_TIME_3446 <>", value, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446GreaterThan(String value) {
            addCriterion("C_DIVING_TIME_3446 >", value, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_DIVING_TIME_3446 >=", value, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446LessThan(String value) {
            addCriterion("C_DIVING_TIME_3446 <", value, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446LessThanOrEqualTo(String value) {
            addCriterion("C_DIVING_TIME_3446 <=", value, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446Like(String value) {
            addCriterion("C_DIVING_TIME_3446 like", value, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446NotLike(String value) {
            addCriterion("C_DIVING_TIME_3446 not like", value, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446In(List<String> values) {
            addCriterion("C_DIVING_TIME_3446 in", values, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446NotIn(List<String> values) {
            addCriterion("C_DIVING_TIME_3446 not in", values, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446Between(String value1, String value2) {
            addCriterion("C_DIVING_TIME_3446 between", value1, value2, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andCDivingTime3446NotBetween(String value1, String value2) {
            addCriterion("C_DIVING_TIME_3446 not between", value1, value2, "cDivingTime3446");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdIsNull() {
            addCriterion("T_DIVING_TASK_480_ID is null");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdIsNotNull() {
            addCriterion("T_DIVING_TASK_480_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdEqualTo(String value) {
            addCriterion("T_DIVING_TASK_480_ID =", value, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdNotEqualTo(String value) {
            addCriterion("T_DIVING_TASK_480_ID <>", value, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdGreaterThan(String value) {
            addCriterion("T_DIVING_TASK_480_ID >", value, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdGreaterThanOrEqualTo(String value) {
            addCriterion("T_DIVING_TASK_480_ID >=", value, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdLessThan(String value) {
            addCriterion("T_DIVING_TASK_480_ID <", value, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdLessThanOrEqualTo(String value) {
            addCriterion("T_DIVING_TASK_480_ID <=", value, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdLike(String value) {
            addCriterion("T_DIVING_TASK_480_ID like", value, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdNotLike(String value) {
            addCriterion("T_DIVING_TASK_480_ID not like", value, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdIn(List<String> values) {
            addCriterion("T_DIVING_TASK_480_ID in", values, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdNotIn(List<String> values) {
            addCriterion("T_DIVING_TASK_480_ID not in", values, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdBetween(String value1, String value2) {
            addCriterion("T_DIVING_TASK_480_ID between", value1, value2, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingTask480IdNotBetween(String value1, String value2) {
            addCriterion("T_DIVING_TASK_480_ID not between", value1, value2, "tDivingTask480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdIsNull() {
            addCriterion("T_HANGCI_480_ID is null");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdIsNotNull() {
            addCriterion("T_HANGCI_480_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdEqualTo(String value) {
            addCriterion("T_HANGCI_480_ID =", value, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdNotEqualTo(String value) {
            addCriterion("T_HANGCI_480_ID <>", value, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdGreaterThan(String value) {
            addCriterion("T_HANGCI_480_ID >", value, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdGreaterThanOrEqualTo(String value) {
            addCriterion("T_HANGCI_480_ID >=", value, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdLessThan(String value) {
            addCriterion("T_HANGCI_480_ID <", value, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdLessThanOrEqualTo(String value) {
            addCriterion("T_HANGCI_480_ID <=", value, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdLike(String value) {
            addCriterion("T_HANGCI_480_ID like", value, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdNotLike(String value) {
            addCriterion("T_HANGCI_480_ID not like", value, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdIn(List<String> values) {
            addCriterion("T_HANGCI_480_ID in", values, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdNotIn(List<String> values) {
            addCriterion("T_HANGCI_480_ID not in", values, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdBetween(String value1, String value2) {
            addCriterion("T_HANGCI_480_ID between", value1, value2, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andTHangci480IdNotBetween(String value1, String value2) {
            addCriterion("T_HANGCI_480_ID not between", value1, value2, "tHangci480Id");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446IsNull() {
            addCriterion("C_YOUXIAN_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446IsNotNull() {
            addCriterion("C_YOUXIAN_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446EqualTo(String value) {
            addCriterion("C_YOUXIAN_3446 =", value, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446NotEqualTo(String value) {
            addCriterion("C_YOUXIAN_3446 <>", value, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446GreaterThan(String value) {
            addCriterion("C_YOUXIAN_3446 >", value, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_YOUXIAN_3446 >=", value, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446LessThan(String value) {
            addCriterion("C_YOUXIAN_3446 <", value, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446LessThanOrEqualTo(String value) {
            addCriterion("C_YOUXIAN_3446 <=", value, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446Like(String value) {
            addCriterion("C_YOUXIAN_3446 like", value, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446NotLike(String value) {
            addCriterion("C_YOUXIAN_3446 not like", value, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446In(List<String> values) {
            addCriterion("C_YOUXIAN_3446 in", values, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446NotIn(List<String> values) {
            addCriterion("C_YOUXIAN_3446 not in", values, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446Between(String value1, String value2) {
            addCriterion("C_YOUXIAN_3446 between", value1, value2, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCYouxian3446NotBetween(String value1, String value2) {
            addCriterion("C_YOUXIAN_3446 not between", value1, value2, "cYouxian3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446IsNull() {
            addCriterion("C_MAIN_DRIVER_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446IsNotNull() {
            addCriterion("C_MAIN_DRIVER_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446EqualTo(String value) {
            addCriterion("C_MAIN_DRIVER_3446 =", value, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446NotEqualTo(String value) {
            addCriterion("C_MAIN_DRIVER_3446 <>", value, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446GreaterThan(String value) {
            addCriterion("C_MAIN_DRIVER_3446 >", value, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_MAIN_DRIVER_3446 >=", value, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446LessThan(String value) {
            addCriterion("C_MAIN_DRIVER_3446 <", value, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446LessThanOrEqualTo(String value) {
            addCriterion("C_MAIN_DRIVER_3446 <=", value, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446Like(String value) {
            addCriterion("C_MAIN_DRIVER_3446 like", value, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446NotLike(String value) {
            addCriterion("C_MAIN_DRIVER_3446 not like", value, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446In(List<String> values) {
            addCriterion("C_MAIN_DRIVER_3446 in", values, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446NotIn(List<String> values) {
            addCriterion("C_MAIN_DRIVER_3446 not in", values, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446Between(String value1, String value2) {
            addCriterion("C_MAIN_DRIVER_3446 between", value1, value2, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3446NotBetween(String value1, String value2) {
            addCriterion("C_MAIN_DRIVER_3446 not between", value1, value2, "cMainDriver3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446IsNull() {
            addCriterion("C_ZUOXIAN_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446IsNotNull() {
            addCriterion("C_ZUOXIAN_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446EqualTo(String value) {
            addCriterion("C_ZUOXIAN_3446 =", value, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446NotEqualTo(String value) {
            addCriterion("C_ZUOXIAN_3446 <>", value, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446GreaterThan(String value) {
            addCriterion("C_ZUOXIAN_3446 >", value, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_ZUOXIAN_3446 >=", value, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446LessThan(String value) {
            addCriterion("C_ZUOXIAN_3446 <", value, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446LessThanOrEqualTo(String value) {
            addCriterion("C_ZUOXIAN_3446 <=", value, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446Like(String value) {
            addCriterion("C_ZUOXIAN_3446 like", value, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446NotLike(String value) {
            addCriterion("C_ZUOXIAN_3446 not like", value, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446In(List<String> values) {
            addCriterion("C_ZUOXIAN_3446 in", values, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446NotIn(List<String> values) {
            addCriterion("C_ZUOXIAN_3446 not in", values, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446Between(String value1, String value2) {
            addCriterion("C_ZUOXIAN_3446 between", value1, value2, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3446NotBetween(String value1, String value2) {
            addCriterion("C_ZUOXIAN_3446 not between", value1, value2, "cZuoxian3446");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446IsNull() {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446IsNotNull() {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446EqualTo(Double value) {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 =", value, "cPersonTotalweight3446");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446NotEqualTo(Double value) {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 <>", value, "cPersonTotalweight3446");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446GreaterThan(Double value) {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 >", value, "cPersonTotalweight3446");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446GreaterThanOrEqualTo(Double value) {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 >=", value, "cPersonTotalweight3446");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446LessThan(Double value) {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 <", value, "cPersonTotalweight3446");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446LessThanOrEqualTo(Double value) {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 <=", value, "cPersonTotalweight3446");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446In(List<Double> values) {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 in", values, "cPersonTotalweight3446");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446NotIn(List<Double> values) {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 not in", values, "cPersonTotalweight3446");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446Between(Double value1, Double value2) {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 between", value1, value2, "cPersonTotalweight3446");
            return (Criteria) this;
        }

        public Criteria andCPersonTotalweight3446NotBetween(Double value1, Double value2) {
            addCriterion("C_PERSON_TOTALWEIGHT_3446 not between", value1, value2, "cPersonTotalweight3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446IsNull() {
            addCriterion("C_ZX_COMPANY_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446IsNotNull() {
            addCriterion("C_ZX_COMPANY_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446EqualTo(String value) {
            addCriterion("C_ZX_COMPANY_3446 =", value, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446NotEqualTo(String value) {
            addCriterion("C_ZX_COMPANY_3446 <>", value, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446GreaterThan(String value) {
            addCriterion("C_ZX_COMPANY_3446 >", value, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_ZX_COMPANY_3446 >=", value, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446LessThan(String value) {
            addCriterion("C_ZX_COMPANY_3446 <", value, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446LessThanOrEqualTo(String value) {
            addCriterion("C_ZX_COMPANY_3446 <=", value, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446Like(String value) {
            addCriterion("C_ZX_COMPANY_3446 like", value, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446NotLike(String value) {
            addCriterion("C_ZX_COMPANY_3446 not like", value, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446In(List<String> values) {
            addCriterion("C_ZX_COMPANY_3446 in", values, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446NotIn(List<String> values) {
            addCriterion("C_ZX_COMPANY_3446 not in", values, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446Between(String value1, String value2) {
            addCriterion("C_ZX_COMPANY_3446 between", value1, value2, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCZxCompany3446NotBetween(String value1, String value2) {
            addCriterion("C_ZX_COMPANY_3446 not between", value1, value2, "cZxCompany3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446IsNull() {
            addCriterion("C_LONGITUDE_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446IsNotNull() {
            addCriterion("C_LONGITUDE_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446EqualTo(String value) {
            addCriterion("C_LONGITUDE_3446 =", value, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446NotEqualTo(String value) {
            addCriterion("C_LONGITUDE_3446 <>", value, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446GreaterThan(String value) {
            addCriterion("C_LONGITUDE_3446 >", value, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_LONGITUDE_3446 >=", value, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446LessThan(String value) {
            addCriterion("C_LONGITUDE_3446 <", value, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446LessThanOrEqualTo(String value) {
            addCriterion("C_LONGITUDE_3446 <=", value, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446Like(String value) {
            addCriterion("C_LONGITUDE_3446 like", value, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446NotLike(String value) {
            addCriterion("C_LONGITUDE_3446 not like", value, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446In(List<String> values) {
            addCriterion("C_LONGITUDE_3446 in", values, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446NotIn(List<String> values) {
            addCriterion("C_LONGITUDE_3446 not in", values, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446Between(String value1, String value2) {
            addCriterion("C_LONGITUDE_3446 between", value1, value2, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLongitude3446NotBetween(String value1, String value2) {
            addCriterion("C_LONGITUDE_3446 not between", value1, value2, "cLongitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446IsNull() {
            addCriterion("C_LATITUDE_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446IsNotNull() {
            addCriterion("C_LATITUDE_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446EqualTo(String value) {
            addCriterion("C_LATITUDE_3446 =", value, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446NotEqualTo(String value) {
            addCriterion("C_LATITUDE_3446 <>", value, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446GreaterThan(String value) {
            addCriterion("C_LATITUDE_3446 >", value, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_LATITUDE_3446 >=", value, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446LessThan(String value) {
            addCriterion("C_LATITUDE_3446 <", value, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446LessThanOrEqualTo(String value) {
            addCriterion("C_LATITUDE_3446 <=", value, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446Like(String value) {
            addCriterion("C_LATITUDE_3446 like", value, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446NotLike(String value) {
            addCriterion("C_LATITUDE_3446 not like", value, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446In(List<String> values) {
            addCriterion("C_LATITUDE_3446 in", values, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446NotIn(List<String> values) {
            addCriterion("C_LATITUDE_3446 not in", values, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446Between(String value1, String value2) {
            addCriterion("C_LATITUDE_3446 between", value1, value2, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCLatitude3446NotBetween(String value1, String value2) {
            addCriterion("C_LATITUDE_3446 not between", value1, value2, "cLatitude3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446IsNull() {
            addCriterion("C_SAMPLE_SITUATION_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446IsNotNull() {
            addCriterion("C_SAMPLE_SITUATION_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446EqualTo(String value) {
            addCriterion("C_SAMPLE_SITUATION_3446 =", value, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446NotEqualTo(String value) {
            addCriterion("C_SAMPLE_SITUATION_3446 <>", value, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446GreaterThan(String value) {
            addCriterion("C_SAMPLE_SITUATION_3446 >", value, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_SAMPLE_SITUATION_3446 >=", value, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446LessThan(String value) {
            addCriterion("C_SAMPLE_SITUATION_3446 <", value, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446LessThanOrEqualTo(String value) {
            addCriterion("C_SAMPLE_SITUATION_3446 <=", value, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446Like(String value) {
            addCriterion("C_SAMPLE_SITUATION_3446 like", value, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446NotLike(String value) {
            addCriterion("C_SAMPLE_SITUATION_3446 not like", value, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446In(List<String> values) {
            addCriterion("C_SAMPLE_SITUATION_3446 in", values, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446NotIn(List<String> values) {
            addCriterion("C_SAMPLE_SITUATION_3446 not in", values, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446Between(String value1, String value2) {
            addCriterion("C_SAMPLE_SITUATION_3446 between", value1, value2, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andCSampleSituation3446NotBetween(String value1, String value2) {
            addCriterion("C_SAMPLE_SITUATION_3446 not between", value1, value2, "cSampleSituation3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446IsNull() {
            addCriterion("TUOLANPEOPLE_3446 is null");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446IsNotNull() {
            addCriterion("TUOLANPEOPLE_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446EqualTo(String value) {
            addCriterion("TUOLANPEOPLE_3446 =", value, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446NotEqualTo(String value) {
            addCriterion("TUOLANPEOPLE_3446 <>", value, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446GreaterThan(String value) {
            addCriterion("TUOLANPEOPLE_3446 >", value, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446GreaterThanOrEqualTo(String value) {
            addCriterion("TUOLANPEOPLE_3446 >=", value, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446LessThan(String value) {
            addCriterion("TUOLANPEOPLE_3446 <", value, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446LessThanOrEqualTo(String value) {
            addCriterion("TUOLANPEOPLE_3446 <=", value, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446Like(String value) {
            addCriterion("TUOLANPEOPLE_3446 like", value, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446NotLike(String value) {
            addCriterion("TUOLANPEOPLE_3446 not like", value, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446In(List<String> values) {
            addCriterion("TUOLANPEOPLE_3446 in", values, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446NotIn(List<String> values) {
            addCriterion("TUOLANPEOPLE_3446 not in", values, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446Between(String value1, String value2) {
            addCriterion("TUOLANPEOPLE_3446 between", value1, value2, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andTuolanpeople3446NotBetween(String value1, String value2) {
            addCriterion("TUOLANPEOPLE_3446 not between", value1, value2, "tuolanpeople3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446IsNull() {
            addCriterion("RECOVERSEASTATEESTIM_3446 is null");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446IsNotNull() {
            addCriterion("RECOVERSEASTATEESTIM_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446EqualTo(String value) {
            addCriterion("RECOVERSEASTATEESTIM_3446 =", value, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446NotEqualTo(String value) {
            addCriterion("RECOVERSEASTATEESTIM_3446 <>", value, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446GreaterThan(String value) {
            addCriterion("RECOVERSEASTATEESTIM_3446 >", value, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446GreaterThanOrEqualTo(String value) {
            addCriterion("RECOVERSEASTATEESTIM_3446 >=", value, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446LessThan(String value) {
            addCriterion("RECOVERSEASTATEESTIM_3446 <", value, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446LessThanOrEqualTo(String value) {
            addCriterion("RECOVERSEASTATEESTIM_3446 <=", value, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446Like(String value) {
            addCriterion("RECOVERSEASTATEESTIM_3446 like", value, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446NotLike(String value) {
            addCriterion("RECOVERSEASTATEESTIM_3446 not like", value, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446In(List<String> values) {
            addCriterion("RECOVERSEASTATEESTIM_3446 in", values, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446NotIn(List<String> values) {
            addCriterion("RECOVERSEASTATEESTIM_3446 not in", values, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446Between(String value1, String value2) {
            addCriterion("RECOVERSEASTATEESTIM_3446 between", value1, value2, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andRecoverseastateestim3446NotBetween(String value1, String value2) {
            addCriterion("RECOVERSEASTATEESTIM_3446 not between", value1, value2, "recoverseastateestim3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446IsNull() {
            addCriterion("TIMEZONE_3446 is null");
            return (Criteria) this;
        }

        public Criteria andTimezone3446IsNotNull() {
            addCriterion("TIMEZONE_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andTimezone3446EqualTo(String value) {
            addCriterion("TIMEZONE_3446 =", value, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446NotEqualTo(String value) {
            addCriterion("TIMEZONE_3446 <>", value, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446GreaterThan(String value) {
            addCriterion("TIMEZONE_3446 >", value, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446GreaterThanOrEqualTo(String value) {
            addCriterion("TIMEZONE_3446 >=", value, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446LessThan(String value) {
            addCriterion("TIMEZONE_3446 <", value, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446LessThanOrEqualTo(String value) {
            addCriterion("TIMEZONE_3446 <=", value, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446Like(String value) {
            addCriterion("TIMEZONE_3446 like", value, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446NotLike(String value) {
            addCriterion("TIMEZONE_3446 not like", value, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446In(List<String> values) {
            addCriterion("TIMEZONE_3446 in", values, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446NotIn(List<String> values) {
            addCriterion("TIMEZONE_3446 not in", values, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446Between(String value1, String value2) {
            addCriterion("TIMEZONE_3446 between", value1, value2, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andTimezone3446NotBetween(String value1, String value2) {
            addCriterion("TIMEZONE_3446 not between", value1, value2, "timezone3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446IsNull() {
            addCriterion("WORKSTARTDEPTH_3446 is null");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446IsNotNull() {
            addCriterion("WORKSTARTDEPTH_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446EqualTo(String value) {
            addCriterion("WORKSTARTDEPTH_3446 =", value, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446NotEqualTo(String value) {
            addCriterion("WORKSTARTDEPTH_3446 <>", value, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446GreaterThan(String value) {
            addCriterion("WORKSTARTDEPTH_3446 >", value, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446GreaterThanOrEqualTo(String value) {
            addCriterion("WORKSTARTDEPTH_3446 >=", value, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446LessThan(String value) {
            addCriterion("WORKSTARTDEPTH_3446 <", value, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446LessThanOrEqualTo(String value) {
            addCriterion("WORKSTARTDEPTH_3446 <=", value, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446Like(String value) {
            addCriterion("WORKSTARTDEPTH_3446 like", value, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446NotLike(String value) {
            addCriterion("WORKSTARTDEPTH_3446 not like", value, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446In(List<String> values) {
            addCriterion("WORKSTARTDEPTH_3446 in", values, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446NotIn(List<String> values) {
            addCriterion("WORKSTARTDEPTH_3446 not in", values, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446Between(String value1, String value2) {
            addCriterion("WORKSTARTDEPTH_3446 between", value1, value2, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkstartdepth3446NotBetween(String value1, String value2) {
            addCriterion("WORKSTARTDEPTH_3446 not between", value1, value2, "workstartdepth3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446IsNull() {
            addCriterion("BALLASTREMOVETIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446IsNotNull() {
            addCriterion("BALLASTREMOVETIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446EqualTo(String value) {
            addCriterion("BALLASTREMOVETIME_3446 =", value, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446NotEqualTo(String value) {
            addCriterion("BALLASTREMOVETIME_3446 <>", value, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446GreaterThan(String value) {
            addCriterion("BALLASTREMOVETIME_3446 >", value, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446GreaterThanOrEqualTo(String value) {
            addCriterion("BALLASTREMOVETIME_3446 >=", value, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446LessThan(String value) {
            addCriterion("BALLASTREMOVETIME_3446 <", value, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446LessThanOrEqualTo(String value) {
            addCriterion("BALLASTREMOVETIME_3446 <=", value, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446Like(String value) {
            addCriterion("BALLASTREMOVETIME_3446 like", value, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446NotLike(String value) {
            addCriterion("BALLASTREMOVETIME_3446 not like", value, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446In(List<String> values) {
            addCriterion("BALLASTREMOVETIME_3446 in", values, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446NotIn(List<String> values) {
            addCriterion("BALLASTREMOVETIME_3446 not in", values, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446Between(String value1, String value2) {
            addCriterion("BALLASTREMOVETIME_3446 between", value1, value2, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andBallastremovetime3446NotBetween(String value1, String value2) {
            addCriterion("BALLASTREMOVETIME_3446 not between", value1, value2, "ballastremovetime3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446IsNull() {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 is null");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446IsNotNull() {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446EqualTo(String value) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 =", value, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446NotEqualTo(String value) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 <>", value, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446GreaterThan(String value) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 >", value, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446GreaterThanOrEqualTo(String value) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 >=", value, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446LessThan(String value) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 <", value, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446LessThanOrEqualTo(String value) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 <=", value, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446Like(String value) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 like", value, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446NotLike(String value) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 not like", value, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446In(List<String> values) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 in", values, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446NotIn(List<String> values) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 not in", values, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446Between(String value1, String value2) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 between", value1, value2, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andSouthnorthhalfsphere3446NotBetween(String value1, String value2) {
            addCriterion("SOUTHNORTHHALFSPHERE_3446 not between", value1, value2, "southnorthhalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446IsNull() {
            addCriterion("WORKENDDEPTH_3446 is null");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446IsNotNull() {
            addCriterion("WORKENDDEPTH_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446EqualTo(String value) {
            addCriterion("WORKENDDEPTH_3446 =", value, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446NotEqualTo(String value) {
            addCriterion("WORKENDDEPTH_3446 <>", value, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446GreaterThan(String value) {
            addCriterion("WORKENDDEPTH_3446 >", value, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446GreaterThanOrEqualTo(String value) {
            addCriterion("WORKENDDEPTH_3446 >=", value, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446LessThan(String value) {
            addCriterion("WORKENDDEPTH_3446 <", value, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446LessThanOrEqualTo(String value) {
            addCriterion("WORKENDDEPTH_3446 <=", value, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446Like(String value) {
            addCriterion("WORKENDDEPTH_3446 like", value, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446NotLike(String value) {
            addCriterion("WORKENDDEPTH_3446 not like", value, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446In(List<String> values) {
            addCriterion("WORKENDDEPTH_3446 in", values, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446NotIn(List<String> values) {
            addCriterion("WORKENDDEPTH_3446 not in", values, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446Between(String value1, String value2) {
            addCriterion("WORKENDDEPTH_3446 between", value1, value2, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andWorkenddepth3446NotBetween(String value1, String value2) {
            addCriterion("WORKENDDEPTH_3446 not between", value1, value2, "workenddepth3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446IsNull() {
            addCriterion("BUFANGLANGHEIGHT_3446 is null");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446IsNotNull() {
            addCriterion("BUFANGLANGHEIGHT_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446EqualTo(String value) {
            addCriterion("BUFANGLANGHEIGHT_3446 =", value, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446NotEqualTo(String value) {
            addCriterion("BUFANGLANGHEIGHT_3446 <>", value, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446GreaterThan(String value) {
            addCriterion("BUFANGLANGHEIGHT_3446 >", value, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446GreaterThanOrEqualTo(String value) {
            addCriterion("BUFANGLANGHEIGHT_3446 >=", value, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446LessThan(String value) {
            addCriterion("BUFANGLANGHEIGHT_3446 <", value, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446LessThanOrEqualTo(String value) {
            addCriterion("BUFANGLANGHEIGHT_3446 <=", value, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446Like(String value) {
            addCriterion("BUFANGLANGHEIGHT_3446 like", value, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446NotLike(String value) {
            addCriterion("BUFANGLANGHEIGHT_3446 not like", value, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446In(List<String> values) {
            addCriterion("BUFANGLANGHEIGHT_3446 in", values, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446NotIn(List<String> values) {
            addCriterion("BUFANGLANGHEIGHT_3446 not in", values, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446Between(String value1, String value2) {
            addCriterion("BUFANGLANGHEIGHT_3446 between", value1, value2, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andBufanglangheight3446NotBetween(String value1, String value2) {
            addCriterion("BUFANGLANGHEIGHT_3446 not between", value1, value2, "bufanglangheight3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446IsNull() {
            addCriterion("TWICERECOVERBOATTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446IsNotNull() {
            addCriterion("TWICERECOVERBOATTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446EqualTo(String value) {
            addCriterion("TWICERECOVERBOATTIME_3446 =", value, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446NotEqualTo(String value) {
            addCriterion("TWICERECOVERBOATTIME_3446 <>", value, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446GreaterThan(String value) {
            addCriterion("TWICERECOVERBOATTIME_3446 >", value, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446GreaterThanOrEqualTo(String value) {
            addCriterion("TWICERECOVERBOATTIME_3446 >=", value, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446LessThan(String value) {
            addCriterion("TWICERECOVERBOATTIME_3446 <", value, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446LessThanOrEqualTo(String value) {
            addCriterion("TWICERECOVERBOATTIME_3446 <=", value, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446Like(String value) {
            addCriterion("TWICERECOVERBOATTIME_3446 like", value, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446NotLike(String value) {
            addCriterion("TWICERECOVERBOATTIME_3446 not like", value, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446In(List<String> values) {
            addCriterion("TWICERECOVERBOATTIME_3446 in", values, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446NotIn(List<String> values) {
            addCriterion("TWICERECOVERBOATTIME_3446 not in", values, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446Between(String value1, String value2) {
            addCriterion("TWICERECOVERBOATTIME_3446 between", value1, value2, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicerecoverboattime3446NotBetween(String value1, String value2) {
            addCriterion("TWICERECOVERBOATTIME_3446 not between", value1, value2, "twicerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446IsNull() {
            addCriterion("TWICEBUFANGBOATTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446IsNotNull() {
            addCriterion("TWICEBUFANGBOATTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446EqualTo(String value) {
            addCriterion("TWICEBUFANGBOATTIME_3446 =", value, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446NotEqualTo(String value) {
            addCriterion("TWICEBUFANGBOATTIME_3446 <>", value, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446GreaterThan(String value) {
            addCriterion("TWICEBUFANGBOATTIME_3446 >", value, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446GreaterThanOrEqualTo(String value) {
            addCriterion("TWICEBUFANGBOATTIME_3446 >=", value, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446LessThan(String value) {
            addCriterion("TWICEBUFANGBOATTIME_3446 <", value, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446LessThanOrEqualTo(String value) {
            addCriterion("TWICEBUFANGBOATTIME_3446 <=", value, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446Like(String value) {
            addCriterion("TWICEBUFANGBOATTIME_3446 like", value, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446NotLike(String value) {
            addCriterion("TWICEBUFANGBOATTIME_3446 not like", value, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446In(List<String> values) {
            addCriterion("TWICEBUFANGBOATTIME_3446 in", values, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446NotIn(List<String> values) {
            addCriterion("TWICEBUFANGBOATTIME_3446 not in", values, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446Between(String value1, String value2) {
            addCriterion("TWICEBUFANGBOATTIME_3446 between", value1, value2, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andTwicebufangboattime3446NotBetween(String value1, String value2) {
            addCriterion("TWICEBUFANGBOATTIME_3446 not between", value1, value2, "twicebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446IsNull() {
            addCriterion("RECOVERBOATASSISTANT_3446 is null");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446IsNotNull() {
            addCriterion("RECOVERBOATASSISTANT_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446EqualTo(String value) {
            addCriterion("RECOVERBOATASSISTANT_3446 =", value, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446NotEqualTo(String value) {
            addCriterion("RECOVERBOATASSISTANT_3446 <>", value, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446GreaterThan(String value) {
            addCriterion("RECOVERBOATASSISTANT_3446 >", value, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446GreaterThanOrEqualTo(String value) {
            addCriterion("RECOVERBOATASSISTANT_3446 >=", value, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446LessThan(String value) {
            addCriterion("RECOVERBOATASSISTANT_3446 <", value, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446LessThanOrEqualTo(String value) {
            addCriterion("RECOVERBOATASSISTANT_3446 <=", value, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446Like(String value) {
            addCriterion("RECOVERBOATASSISTANT_3446 like", value, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446NotLike(String value) {
            addCriterion("RECOVERBOATASSISTANT_3446 not like", value, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446In(List<String> values) {
            addCriterion("RECOVERBOATASSISTANT_3446 in", values, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446NotIn(List<String> values) {
            addCriterion("RECOVERBOATASSISTANT_3446 not in", values, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446Between(String value1, String value2) {
            addCriterion("RECOVERBOATASSISTANT_3446 between", value1, value2, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatassistant3446NotBetween(String value1, String value2) {
            addCriterion("RECOVERBOATASSISTANT_3446 not between", value1, value2, "recoverboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446IsNull() {
            addCriterion("STARTWORKTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446IsNotNull() {
            addCriterion("STARTWORKTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446EqualTo(String value) {
            addCriterion("STARTWORKTIME_3446 =", value, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446NotEqualTo(String value) {
            addCriterion("STARTWORKTIME_3446 <>", value, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446GreaterThan(String value) {
            addCriterion("STARTWORKTIME_3446 >", value, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446GreaterThanOrEqualTo(String value) {
            addCriterion("STARTWORKTIME_3446 >=", value, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446LessThan(String value) {
            addCriterion("STARTWORKTIME_3446 <", value, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446LessThanOrEqualTo(String value) {
            addCriterion("STARTWORKTIME_3446 <=", value, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446Like(String value) {
            addCriterion("STARTWORKTIME_3446 like", value, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446NotLike(String value) {
            addCriterion("STARTWORKTIME_3446 not like", value, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446In(List<String> values) {
            addCriterion("STARTWORKTIME_3446 in", values, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446NotIn(List<String> values) {
            addCriterion("STARTWORKTIME_3446 not in", values, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446Between(String value1, String value2) {
            addCriterion("STARTWORKTIME_3446 between", value1, value2, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andStartworktime3446NotBetween(String value1, String value2) {
            addCriterion("STARTWORKTIME_3446 not between", value1, value2, "startworktime3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446IsNull() {
            addCriterion("BUFANGTYPE_3446 is null");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446IsNotNull() {
            addCriterion("BUFANGTYPE_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446EqualTo(String value) {
            addCriterion("BUFANGTYPE_3446 =", value, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446NotEqualTo(String value) {
            addCriterion("BUFANGTYPE_3446 <>", value, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446GreaterThan(String value) {
            addCriterion("BUFANGTYPE_3446 >", value, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446GreaterThanOrEqualTo(String value) {
            addCriterion("BUFANGTYPE_3446 >=", value, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446LessThan(String value) {
            addCriterion("BUFANGTYPE_3446 <", value, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446LessThanOrEqualTo(String value) {
            addCriterion("BUFANGTYPE_3446 <=", value, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446Like(String value) {
            addCriterion("BUFANGTYPE_3446 like", value, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446NotLike(String value) {
            addCriterion("BUFANGTYPE_3446 not like", value, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446In(List<String> values) {
            addCriterion("BUFANGTYPE_3446 in", values, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446NotIn(List<String> values) {
            addCriterion("BUFANGTYPE_3446 not in", values, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446Between(String value1, String value2) {
            addCriterion("BUFANGTYPE_3446 between", value1, value2, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andBufangtype3446NotBetween(String value1, String value2) {
            addCriterion("BUFANGTYPE_3446 not between", value1, value2, "bufangtype3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446IsNull() {
            addCriterion("ONCERECOVERBOATTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446IsNotNull() {
            addCriterion("ONCERECOVERBOATTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446EqualTo(String value) {
            addCriterion("ONCERECOVERBOATTIME_3446 =", value, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446NotEqualTo(String value) {
            addCriterion("ONCERECOVERBOATTIME_3446 <>", value, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446GreaterThan(String value) {
            addCriterion("ONCERECOVERBOATTIME_3446 >", value, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446GreaterThanOrEqualTo(String value) {
            addCriterion("ONCERECOVERBOATTIME_3446 >=", value, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446LessThan(String value) {
            addCriterion("ONCERECOVERBOATTIME_3446 <", value, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446LessThanOrEqualTo(String value) {
            addCriterion("ONCERECOVERBOATTIME_3446 <=", value, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446Like(String value) {
            addCriterion("ONCERECOVERBOATTIME_3446 like", value, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446NotLike(String value) {
            addCriterion("ONCERECOVERBOATTIME_3446 not like", value, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446In(List<String> values) {
            addCriterion("ONCERECOVERBOATTIME_3446 in", values, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446NotIn(List<String> values) {
            addCriterion("ONCERECOVERBOATTIME_3446 not in", values, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446Between(String value1, String value2) {
            addCriterion("ONCERECOVERBOATTIME_3446 between", value1, value2, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncerecoverboattime3446NotBetween(String value1, String value2) {
            addCriterion("ONCERECOVERBOATTIME_3446 not between", value1, value2, "oncerecoverboattime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446IsNull() {
            addCriterion("RECOVERDECKTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446IsNotNull() {
            addCriterion("RECOVERDECKTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446EqualTo(String value) {
            addCriterion("RECOVERDECKTIME_3446 =", value, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446NotEqualTo(String value) {
            addCriterion("RECOVERDECKTIME_3446 <>", value, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446GreaterThan(String value) {
            addCriterion("RECOVERDECKTIME_3446 >", value, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446GreaterThanOrEqualTo(String value) {
            addCriterion("RECOVERDECKTIME_3446 >=", value, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446LessThan(String value) {
            addCriterion("RECOVERDECKTIME_3446 <", value, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446LessThanOrEqualTo(String value) {
            addCriterion("RECOVERDECKTIME_3446 <=", value, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446Like(String value) {
            addCriterion("RECOVERDECKTIME_3446 like", value, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446NotLike(String value) {
            addCriterion("RECOVERDECKTIME_3446 not like", value, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446In(List<String> values) {
            addCriterion("RECOVERDECKTIME_3446 in", values, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446NotIn(List<String> values) {
            addCriterion("RECOVERDECKTIME_3446 not in", values, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446Between(String value1, String value2) {
            addCriterion("RECOVERDECKTIME_3446 between", value1, value2, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andRecoverdecktime3446NotBetween(String value1, String value2) {
            addCriterion("RECOVERDECKTIME_3446 not between", value1, value2, "recoverdecktime3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446IsNull() {
            addCriterion("BUFANGMAXWINDSPEED_3446 is null");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446IsNotNull() {
            addCriterion("BUFANGMAXWINDSPEED_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446EqualTo(String value) {
            addCriterion("BUFANGMAXWINDSPEED_3446 =", value, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446NotEqualTo(String value) {
            addCriterion("BUFANGMAXWINDSPEED_3446 <>", value, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446GreaterThan(String value) {
            addCriterion("BUFANGMAXWINDSPEED_3446 >", value, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446GreaterThanOrEqualTo(String value) {
            addCriterion("BUFANGMAXWINDSPEED_3446 >=", value, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446LessThan(String value) {
            addCriterion("BUFANGMAXWINDSPEED_3446 <", value, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446LessThanOrEqualTo(String value) {
            addCriterion("BUFANGMAXWINDSPEED_3446 <=", value, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446Like(String value) {
            addCriterion("BUFANGMAXWINDSPEED_3446 like", value, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446NotLike(String value) {
            addCriterion("BUFANGMAXWINDSPEED_3446 not like", value, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446In(List<String> values) {
            addCriterion("BUFANGMAXWINDSPEED_3446 in", values, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446NotIn(List<String> values) {
            addCriterion("BUFANGMAXWINDSPEED_3446 not in", values, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446Between(String value1, String value2) {
            addCriterion("BUFANGMAXWINDSPEED_3446 between", value1, value2, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangmaxwindspeed3446NotBetween(String value1, String value2) {
            addCriterion("BUFANGMAXWINDSPEED_3446 not between", value1, value2, "bufangmaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446IsNull() {
            addCriterion("PERSONOUTCABINTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446IsNotNull() {
            addCriterion("PERSONOUTCABINTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446EqualTo(String value) {
            addCriterion("PERSONOUTCABINTIME_3446 =", value, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446NotEqualTo(String value) {
            addCriterion("PERSONOUTCABINTIME_3446 <>", value, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446GreaterThan(String value) {
            addCriterion("PERSONOUTCABINTIME_3446 >", value, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446GreaterThanOrEqualTo(String value) {
            addCriterion("PERSONOUTCABINTIME_3446 >=", value, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446LessThan(String value) {
            addCriterion("PERSONOUTCABINTIME_3446 <", value, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446LessThanOrEqualTo(String value) {
            addCriterion("PERSONOUTCABINTIME_3446 <=", value, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446Like(String value) {
            addCriterion("PERSONOUTCABINTIME_3446 like", value, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446NotLike(String value) {
            addCriterion("PERSONOUTCABINTIME_3446 not like", value, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446In(List<String> values) {
            addCriterion("PERSONOUTCABINTIME_3446 in", values, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446NotIn(List<String> values) {
            addCriterion("PERSONOUTCABINTIME_3446 not in", values, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446Between(String value1, String value2) {
            addCriterion("PERSONOUTCABINTIME_3446 between", value1, value2, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andPersonoutcabintime3446NotBetween(String value1, String value2) {
            addCriterion("PERSONOUTCABINTIME_3446 not between", value1, value2, "personoutcabintime3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446IsNull() {
            addCriterion("GUALANASSISTANT_3446 is null");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446IsNotNull() {
            addCriterion("GUALANASSISTANT_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446EqualTo(String value) {
            addCriterion("GUALANASSISTANT_3446 =", value, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446NotEqualTo(String value) {
            addCriterion("GUALANASSISTANT_3446 <>", value, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446GreaterThan(String value) {
            addCriterion("GUALANASSISTANT_3446 >", value, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446GreaterThanOrEqualTo(String value) {
            addCriterion("GUALANASSISTANT_3446 >=", value, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446LessThan(String value) {
            addCriterion("GUALANASSISTANT_3446 <", value, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446LessThanOrEqualTo(String value) {
            addCriterion("GUALANASSISTANT_3446 <=", value, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446Like(String value) {
            addCriterion("GUALANASSISTANT_3446 like", value, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446NotLike(String value) {
            addCriterion("GUALANASSISTANT_3446 not like", value, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446In(List<String> values) {
            addCriterion("GUALANASSISTANT_3446 in", values, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446NotIn(List<String> values) {
            addCriterion("GUALANASSISTANT_3446 not in", values, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446Between(String value1, String value2) {
            addCriterion("GUALANASSISTANT_3446 between", value1, value2, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andGualanassistant3446NotBetween(String value1, String value2) {
            addCriterion("GUALANASSISTANT_3446 not between", value1, value2, "gualanassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446IsNull() {
            addCriterion("BUFANGCOMMANDTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446IsNotNull() {
            addCriterion("BUFANGCOMMANDTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446EqualTo(String value) {
            addCriterion("BUFANGCOMMANDTIME_3446 =", value, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446NotEqualTo(String value) {
            addCriterion("BUFANGCOMMANDTIME_3446 <>", value, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446GreaterThan(String value) {
            addCriterion("BUFANGCOMMANDTIME_3446 >", value, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446GreaterThanOrEqualTo(String value) {
            addCriterion("BUFANGCOMMANDTIME_3446 >=", value, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446LessThan(String value) {
            addCriterion("BUFANGCOMMANDTIME_3446 <", value, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446LessThanOrEqualTo(String value) {
            addCriterion("BUFANGCOMMANDTIME_3446 <=", value, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446Like(String value) {
            addCriterion("BUFANGCOMMANDTIME_3446 like", value, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446NotLike(String value) {
            addCriterion("BUFANGCOMMANDTIME_3446 not like", value, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446In(List<String> values) {
            addCriterion("BUFANGCOMMANDTIME_3446 in", values, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446NotIn(List<String> values) {
            addCriterion("BUFANGCOMMANDTIME_3446 not in", values, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446Between(String value1, String value2) {
            addCriterion("BUFANGCOMMANDTIME_3446 between", value1, value2, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andBufangcommandtime3446NotBetween(String value1, String value2) {
            addCriterion("BUFANGCOMMANDTIME_3446 not between", value1, value2, "bufangcommandtime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446IsNull() {
            addCriterion("STARTFILLWATERTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446IsNotNull() {
            addCriterion("STARTFILLWATERTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446EqualTo(String value) {
            addCriterion("STARTFILLWATERTIME_3446 =", value, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446NotEqualTo(String value) {
            addCriterion("STARTFILLWATERTIME_3446 <>", value, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446GreaterThan(String value) {
            addCriterion("STARTFILLWATERTIME_3446 >", value, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446GreaterThanOrEqualTo(String value) {
            addCriterion("STARTFILLWATERTIME_3446 >=", value, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446LessThan(String value) {
            addCriterion("STARTFILLWATERTIME_3446 <", value, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446LessThanOrEqualTo(String value) {
            addCriterion("STARTFILLWATERTIME_3446 <=", value, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446Like(String value) {
            addCriterion("STARTFILLWATERTIME_3446 like", value, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446NotLike(String value) {
            addCriterion("STARTFILLWATERTIME_3446 not like", value, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446In(List<String> values) {
            addCriterion("STARTFILLWATERTIME_3446 in", values, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446NotIn(List<String> values) {
            addCriterion("STARTFILLWATERTIME_3446 not in", values, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446Between(String value1, String value2) {
            addCriterion("STARTFILLWATERTIME_3446 between", value1, value2, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andStartfillwatertime3446NotBetween(String value1, String value2) {
            addCriterion("STARTFILLWATERTIME_3446 not between", value1, value2, "startfillwatertime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446IsNull() {
            addCriterion("HATCHOPENTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446IsNotNull() {
            addCriterion("HATCHOPENTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446EqualTo(String value) {
            addCriterion("HATCHOPENTIME_3446 =", value, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446NotEqualTo(String value) {
            addCriterion("HATCHOPENTIME_3446 <>", value, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446GreaterThan(String value) {
            addCriterion("HATCHOPENTIME_3446 >", value, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446GreaterThanOrEqualTo(String value) {
            addCriterion("HATCHOPENTIME_3446 >=", value, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446LessThan(String value) {
            addCriterion("HATCHOPENTIME_3446 <", value, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446LessThanOrEqualTo(String value) {
            addCriterion("HATCHOPENTIME_3446 <=", value, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446Like(String value) {
            addCriterion("HATCHOPENTIME_3446 like", value, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446NotLike(String value) {
            addCriterion("HATCHOPENTIME_3446 not like", value, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446In(List<String> values) {
            addCriterion("HATCHOPENTIME_3446 in", values, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446NotIn(List<String> values) {
            addCriterion("HATCHOPENTIME_3446 not in", values, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446Between(String value1, String value2) {
            addCriterion("HATCHOPENTIME_3446 between", value1, value2, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andHatchopentime3446NotBetween(String value1, String value2) {
            addCriterion("HATCHOPENTIME_3446 not between", value1, value2, "hatchopentime3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446IsNull() {
            addCriterion("C_HANGDUAN_3446 is null");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446IsNotNull() {
            addCriterion("C_HANGDUAN_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446EqualTo(String value) {
            addCriterion("C_HANGDUAN_3446 =", value, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446NotEqualTo(String value) {
            addCriterion("C_HANGDUAN_3446 <>", value, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446GreaterThan(String value) {
            addCriterion("C_HANGDUAN_3446 >", value, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446GreaterThanOrEqualTo(String value) {
            addCriterion("C_HANGDUAN_3446 >=", value, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446LessThan(String value) {
            addCriterion("C_HANGDUAN_3446 <", value, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446LessThanOrEqualTo(String value) {
            addCriterion("C_HANGDUAN_3446 <=", value, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446Like(String value) {
            addCriterion("C_HANGDUAN_3446 like", value, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446NotLike(String value) {
            addCriterion("C_HANGDUAN_3446 not like", value, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446In(List<String> values) {
            addCriterion("C_HANGDUAN_3446 in", values, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446NotIn(List<String> values) {
            addCriterion("C_HANGDUAN_3446 not in", values, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446Between(String value1, String value2) {
            addCriterion("C_HANGDUAN_3446 between", value1, value2, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andCHangduan3446NotBetween(String value1, String value2) {
            addCriterion("C_HANGDUAN_3446 not between", value1, value2, "cHangduan3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446IsNull() {
            addCriterion("WATERDOWNPICTURES_3446 is null");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446IsNotNull() {
            addCriterion("WATERDOWNPICTURES_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446EqualTo(String value) {
            addCriterion("WATERDOWNPICTURES_3446 =", value, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446NotEqualTo(String value) {
            addCriterion("WATERDOWNPICTURES_3446 <>", value, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446GreaterThan(String value) {
            addCriterion("WATERDOWNPICTURES_3446 >", value, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446GreaterThanOrEqualTo(String value) {
            addCriterion("WATERDOWNPICTURES_3446 >=", value, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446LessThan(String value) {
            addCriterion("WATERDOWNPICTURES_3446 <", value, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446LessThanOrEqualTo(String value) {
            addCriterion("WATERDOWNPICTURES_3446 <=", value, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446Like(String value) {
            addCriterion("WATERDOWNPICTURES_3446 like", value, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446NotLike(String value) {
            addCriterion("WATERDOWNPICTURES_3446 not like", value, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446In(List<String> values) {
            addCriterion("WATERDOWNPICTURES_3446 in", values, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446NotIn(List<String> values) {
            addCriterion("WATERDOWNPICTURES_3446 not in", values, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446Between(String value1, String value2) {
            addCriterion("WATERDOWNPICTURES_3446 between", value1, value2, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andWaterdownpictures3446NotBetween(String value1, String value2) {
            addCriterion("WATERDOWNPICTURES_3446 not between", value1, value2, "waterdownpictures3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446IsNull() {
            addCriterion("HATCHCLOSETIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446IsNotNull() {
            addCriterion("HATCHCLOSETIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446EqualTo(String value) {
            addCriterion("HATCHCLOSETIME_3446 =", value, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446NotEqualTo(String value) {
            addCriterion("HATCHCLOSETIME_3446 <>", value, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446GreaterThan(String value) {
            addCriterion("HATCHCLOSETIME_3446 >", value, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446GreaterThanOrEqualTo(String value) {
            addCriterion("HATCHCLOSETIME_3446 >=", value, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446LessThan(String value) {
            addCriterion("HATCHCLOSETIME_3446 <", value, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446LessThanOrEqualTo(String value) {
            addCriterion("HATCHCLOSETIME_3446 <=", value, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446Like(String value) {
            addCriterion("HATCHCLOSETIME_3446 like", value, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446NotLike(String value) {
            addCriterion("HATCHCLOSETIME_3446 not like", value, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446In(List<String> values) {
            addCriterion("HATCHCLOSETIME_3446 in", values, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446NotIn(List<String> values) {
            addCriterion("HATCHCLOSETIME_3446 not in", values, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446Between(String value1, String value2) {
            addCriterion("HATCHCLOSETIME_3446 between", value1, value2, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andHatchclosetime3446NotBetween(String value1, String value2) {
            addCriterion("HATCHCLOSETIME_3446 not between", value1, value2, "hatchclosetime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446IsNull() {
            addCriterion("ENDWORKTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446IsNotNull() {
            addCriterion("ENDWORKTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446EqualTo(String value) {
            addCriterion("ENDWORKTIME_3446 =", value, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446NotEqualTo(String value) {
            addCriterion("ENDWORKTIME_3446 <>", value, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446GreaterThan(String value) {
            addCriterion("ENDWORKTIME_3446 >", value, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446GreaterThanOrEqualTo(String value) {
            addCriterion("ENDWORKTIME_3446 >=", value, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446LessThan(String value) {
            addCriterion("ENDWORKTIME_3446 <", value, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446LessThanOrEqualTo(String value) {
            addCriterion("ENDWORKTIME_3446 <=", value, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446Like(String value) {
            addCriterion("ENDWORKTIME_3446 like", value, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446NotLike(String value) {
            addCriterion("ENDWORKTIME_3446 not like", value, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446In(List<String> values) {
            addCriterion("ENDWORKTIME_3446 in", values, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446NotIn(List<String> values) {
            addCriterion("ENDWORKTIME_3446 not in", values, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446Between(String value1, String value2) {
            addCriterion("ENDWORKTIME_3446 between", value1, value2, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andEndworktime3446NotBetween(String value1, String value2) {
            addCriterion("ENDWORKTIME_3446 not between", value1, value2, "endworktime3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446IsNull() {
            addCriterion("SEAFLOORENVIRONMENTD_3446 is null");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446IsNotNull() {
            addCriterion("SEAFLOORENVIRONMENTD_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446EqualTo(String value) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 =", value, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446NotEqualTo(String value) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 <>", value, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446GreaterThan(String value) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 >", value, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446GreaterThanOrEqualTo(String value) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 >=", value, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446LessThan(String value) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 <", value, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446LessThanOrEqualTo(String value) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 <=", value, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446Like(String value) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 like", value, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446NotLike(String value) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 not like", value, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446In(List<String> values) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 in", values, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446NotIn(List<String> values) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 not in", values, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446Between(String value1, String value2) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 between", value1, value2, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andSeafloorenvironmentd3446NotBetween(String value1, String value2) {
            addCriterion("SEAFLOORENVIRONMENTD_3446 not between", value1, value2, "seafloorenvironmentd3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446IsNull() {
            addCriterion("RECOVERLANGHEIGHT_3446 is null");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446IsNotNull() {
            addCriterion("RECOVERLANGHEIGHT_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446EqualTo(String value) {
            addCriterion("RECOVERLANGHEIGHT_3446 =", value, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446NotEqualTo(String value) {
            addCriterion("RECOVERLANGHEIGHT_3446 <>", value, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446GreaterThan(String value) {
            addCriterion("RECOVERLANGHEIGHT_3446 >", value, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446GreaterThanOrEqualTo(String value) {
            addCriterion("RECOVERLANGHEIGHT_3446 >=", value, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446LessThan(String value) {
            addCriterion("RECOVERLANGHEIGHT_3446 <", value, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446LessThanOrEqualTo(String value) {
            addCriterion("RECOVERLANGHEIGHT_3446 <=", value, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446Like(String value) {
            addCriterion("RECOVERLANGHEIGHT_3446 like", value, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446NotLike(String value) {
            addCriterion("RECOVERLANGHEIGHT_3446 not like", value, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446In(List<String> values) {
            addCriterion("RECOVERLANGHEIGHT_3446 in", values, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446NotIn(List<String> values) {
            addCriterion("RECOVERLANGHEIGHT_3446 not in", values, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446Between(String value1, String value2) {
            addCriterion("RECOVERLANGHEIGHT_3446 between", value1, value2, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andRecoverlangheight3446NotBetween(String value1, String value2) {
            addCriterion("RECOVERLANGHEIGHT_3446 not between", value1, value2, "recoverlangheight3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446IsNull() {
            addCriterion("DIVINGDOUTWATERTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446IsNotNull() {
            addCriterion("DIVINGDOUTWATERTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446EqualTo(String value) {
            addCriterion("DIVINGDOUTWATERTIME_3446 =", value, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446NotEqualTo(String value) {
            addCriterion("DIVINGDOUTWATERTIME_3446 <>", value, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446GreaterThan(String value) {
            addCriterion("DIVINGDOUTWATERTIME_3446 >", value, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446GreaterThanOrEqualTo(String value) {
            addCriterion("DIVINGDOUTWATERTIME_3446 >=", value, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446LessThan(String value) {
            addCriterion("DIVINGDOUTWATERTIME_3446 <", value, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446LessThanOrEqualTo(String value) {
            addCriterion("DIVINGDOUTWATERTIME_3446 <=", value, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446Like(String value) {
            addCriterion("DIVINGDOUTWATERTIME_3446 like", value, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446NotLike(String value) {
            addCriterion("DIVINGDOUTWATERTIME_3446 not like", value, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446In(List<String> values) {
            addCriterion("DIVINGDOUTWATERTIME_3446 in", values, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446NotIn(List<String> values) {
            addCriterion("DIVINGDOUTWATERTIME_3446 not in", values, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446Between(String value1, String value2) {
            addCriterion("DIVINGDOUTWATERTIME_3446 between", value1, value2, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdoutwatertime3446NotBetween(String value1, String value2) {
            addCriterion("DIVINGDOUTWATERTIME_3446 not between", value1, value2, "divingdoutwatertime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446IsNull() {
            addCriterion("DIVINGDENTRYTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446IsNotNull() {
            addCriterion("DIVINGDENTRYTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446EqualTo(String value) {
            addCriterion("DIVINGDENTRYTIME_3446 =", value, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446NotEqualTo(String value) {
            addCriterion("DIVINGDENTRYTIME_3446 <>", value, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446GreaterThan(String value) {
            addCriterion("DIVINGDENTRYTIME_3446 >", value, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446GreaterThanOrEqualTo(String value) {
            addCriterion("DIVINGDENTRYTIME_3446 >=", value, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446LessThan(String value) {
            addCriterion("DIVINGDENTRYTIME_3446 <", value, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446LessThanOrEqualTo(String value) {
            addCriterion("DIVINGDENTRYTIME_3446 <=", value, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446Like(String value) {
            addCriterion("DIVINGDENTRYTIME_3446 like", value, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446NotLike(String value) {
            addCriterion("DIVINGDENTRYTIME_3446 not like", value, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446In(List<String> values) {
            addCriterion("DIVINGDENTRYTIME_3446 in", values, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446NotIn(List<String> values) {
            addCriterion("DIVINGDENTRYTIME_3446 not in", values, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446Between(String value1, String value2) {
            addCriterion("DIVINGDENTRYTIME_3446 between", value1, value2, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andDivingdentrytime3446NotBetween(String value1, String value2) {
            addCriterion("DIVINGDENTRYTIME_3446 not between", value1, value2, "divingdentrytime3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446IsNull() {
            addCriterion("GUALANPEOPLE_3446 is null");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446IsNotNull() {
            addCriterion("GUALANPEOPLE_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446EqualTo(String value) {
            addCriterion("GUALANPEOPLE_3446 =", value, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446NotEqualTo(String value) {
            addCriterion("GUALANPEOPLE_3446 <>", value, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446GreaterThan(String value) {
            addCriterion("GUALANPEOPLE_3446 >", value, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446GreaterThanOrEqualTo(String value) {
            addCriterion("GUALANPEOPLE_3446 >=", value, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446LessThan(String value) {
            addCriterion("GUALANPEOPLE_3446 <", value, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446LessThanOrEqualTo(String value) {
            addCriterion("GUALANPEOPLE_3446 <=", value, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446Like(String value) {
            addCriterion("GUALANPEOPLE_3446 like", value, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446NotLike(String value) {
            addCriterion("GUALANPEOPLE_3446 not like", value, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446In(List<String> values) {
            addCriterion("GUALANPEOPLE_3446 in", values, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446NotIn(List<String> values) {
            addCriterion("GUALANPEOPLE_3446 not in", values, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446Between(String value1, String value2) {
            addCriterion("GUALANPEOPLE_3446 between", value1, value2, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andGualanpeople3446NotBetween(String value1, String value2) {
            addCriterion("GUALANPEOPLE_3446 not between", value1, value2, "gualanpeople3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446IsNull() {
            addCriterion("EASTWESTHALFSPHERE_3446 is null");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446IsNotNull() {
            addCriterion("EASTWESTHALFSPHERE_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446EqualTo(String value) {
            addCriterion("EASTWESTHALFSPHERE_3446 =", value, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446NotEqualTo(String value) {
            addCriterion("EASTWESTHALFSPHERE_3446 <>", value, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446GreaterThan(String value) {
            addCriterion("EASTWESTHALFSPHERE_3446 >", value, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446GreaterThanOrEqualTo(String value) {
            addCriterion("EASTWESTHALFSPHERE_3446 >=", value, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446LessThan(String value) {
            addCriterion("EASTWESTHALFSPHERE_3446 <", value, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446LessThanOrEqualTo(String value) {
            addCriterion("EASTWESTHALFSPHERE_3446 <=", value, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446Like(String value) {
            addCriterion("EASTWESTHALFSPHERE_3446 like", value, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446NotLike(String value) {
            addCriterion("EASTWESTHALFSPHERE_3446 not like", value, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446In(List<String> values) {
            addCriterion("EASTWESTHALFSPHERE_3446 in", values, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446NotIn(List<String> values) {
            addCriterion("EASTWESTHALFSPHERE_3446 not in", values, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446Between(String value1, String value2) {
            addCriterion("EASTWESTHALFSPHERE_3446 between", value1, value2, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andEastwesthalfsphere3446NotBetween(String value1, String value2) {
            addCriterion("EASTWESTHALFSPHERE_3446 not between", value1, value2, "eastwesthalfsphere3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446IsNull() {
            addCriterion("PERSONCOMEINCABINT_3446 is null");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446IsNotNull() {
            addCriterion("PERSONCOMEINCABINT_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446EqualTo(String value) {
            addCriterion("PERSONCOMEINCABINT_3446 =", value, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446NotEqualTo(String value) {
            addCriterion("PERSONCOMEINCABINT_3446 <>", value, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446GreaterThan(String value) {
            addCriterion("PERSONCOMEINCABINT_3446 >", value, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446GreaterThanOrEqualTo(String value) {
            addCriterion("PERSONCOMEINCABINT_3446 >=", value, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446LessThan(String value) {
            addCriterion("PERSONCOMEINCABINT_3446 <", value, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446LessThanOrEqualTo(String value) {
            addCriterion("PERSONCOMEINCABINT_3446 <=", value, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446Like(String value) {
            addCriterion("PERSONCOMEINCABINT_3446 like", value, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446NotLike(String value) {
            addCriterion("PERSONCOMEINCABINT_3446 not like", value, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446In(List<String> values) {
            addCriterion("PERSONCOMEINCABINT_3446 in", values, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446NotIn(List<String> values) {
            addCriterion("PERSONCOMEINCABINT_3446 not in", values, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446Between(String value1, String value2) {
            addCriterion("PERSONCOMEINCABINT_3446 between", value1, value2, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andPersoncomeincabint3446NotBetween(String value1, String value2) {
            addCriterion("PERSONCOMEINCABINT_3446 not between", value1, value2, "personcomeincabint3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446IsNull() {
            addCriterion("BUFANGSEASTATEESTIMA_3446 is null");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446IsNotNull() {
            addCriterion("BUFANGSEASTATEESTIMA_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446EqualTo(String value) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 =", value, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446NotEqualTo(String value) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 <>", value, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446GreaterThan(String value) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 >", value, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446GreaterThanOrEqualTo(String value) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 >=", value, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446LessThan(String value) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 <", value, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446LessThanOrEqualTo(String value) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 <=", value, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446Like(String value) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 like", value, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446NotLike(String value) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 not like", value, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446In(List<String> values) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 in", values, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446NotIn(List<String> values) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 not in", values, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446Between(String value1, String value2) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 between", value1, value2, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangseastateestima3446NotBetween(String value1, String value2) {
            addCriterion("BUFANGSEASTATEESTIMA_3446 not between", value1, value2, "bufangseastateestima3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446IsNull() {
            addCriterion("BUFANGAVERAGEWSPEED_3446 is null");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446IsNotNull() {
            addCriterion("BUFANGAVERAGEWSPEED_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446EqualTo(String value) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 =", value, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446NotEqualTo(String value) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 <>", value, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446GreaterThan(String value) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 >", value, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446GreaterThanOrEqualTo(String value) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 >=", value, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446LessThan(String value) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 <", value, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446LessThanOrEqualTo(String value) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 <=", value, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446Like(String value) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 like", value, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446NotLike(String value) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 not like", value, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446In(List<String> values) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 in", values, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446NotIn(List<String> values) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 not in", values, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446Between(String value1, String value2) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 between", value1, value2, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andBufangaveragewspeed3446NotBetween(String value1, String value2) {
            addCriterion("BUFANGAVERAGEWSPEED_3446 not between", value1, value2, "bufangaveragewspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446IsNull() {
            addCriterion("RECOVERMAXWINDSPEED_3446 is null");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446IsNotNull() {
            addCriterion("RECOVERMAXWINDSPEED_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446EqualTo(String value) {
            addCriterion("RECOVERMAXWINDSPEED_3446 =", value, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446NotEqualTo(String value) {
            addCriterion("RECOVERMAXWINDSPEED_3446 <>", value, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446GreaterThan(String value) {
            addCriterion("RECOVERMAXWINDSPEED_3446 >", value, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446GreaterThanOrEqualTo(String value) {
            addCriterion("RECOVERMAXWINDSPEED_3446 >=", value, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446LessThan(String value) {
            addCriterion("RECOVERMAXWINDSPEED_3446 <", value, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446LessThanOrEqualTo(String value) {
            addCriterion("RECOVERMAXWINDSPEED_3446 <=", value, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446Like(String value) {
            addCriterion("RECOVERMAXWINDSPEED_3446 like", value, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446NotLike(String value) {
            addCriterion("RECOVERMAXWINDSPEED_3446 not like", value, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446In(List<String> values) {
            addCriterion("RECOVERMAXWINDSPEED_3446 in", values, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446NotIn(List<String> values) {
            addCriterion("RECOVERMAXWINDSPEED_3446 not in", values, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446Between(String value1, String value2) {
            addCriterion("RECOVERMAXWINDSPEED_3446 between", value1, value2, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaxwindspeed3446NotBetween(String value1, String value2) {
            addCriterion("RECOVERMAXWINDSPEED_3446 not between", value1, value2, "recovermaxwindspeed3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446IsNull() {
            addCriterion("TUOLANASSISTANT_3446 is null");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446IsNotNull() {
            addCriterion("TUOLANASSISTANT_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446EqualTo(String value) {
            addCriterion("TUOLANASSISTANT_3446 =", value, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446NotEqualTo(String value) {
            addCriterion("TUOLANASSISTANT_3446 <>", value, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446GreaterThan(String value) {
            addCriterion("TUOLANASSISTANT_3446 >", value, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446GreaterThanOrEqualTo(String value) {
            addCriterion("TUOLANASSISTANT_3446 >=", value, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446LessThan(String value) {
            addCriterion("TUOLANASSISTANT_3446 <", value, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446LessThanOrEqualTo(String value) {
            addCriterion("TUOLANASSISTANT_3446 <=", value, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446Like(String value) {
            addCriterion("TUOLANASSISTANT_3446 like", value, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446NotLike(String value) {
            addCriterion("TUOLANASSISTANT_3446 not like", value, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446In(List<String> values) {
            addCriterion("TUOLANASSISTANT_3446 in", values, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446NotIn(List<String> values) {
            addCriterion("TUOLANASSISTANT_3446 not in", values, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446Between(String value1, String value2) {
            addCriterion("TUOLANASSISTANT_3446 between", value1, value2, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andTuolanassistant3446NotBetween(String value1, String value2) {
            addCriterion("TUOLANASSISTANT_3446 not between", value1, value2, "tuolanassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446IsNull() {
            addCriterion("BUFANGBOATASSISTANT_3446 is null");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446IsNotNull() {
            addCriterion("BUFANGBOATASSISTANT_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446EqualTo(String value) {
            addCriterion("BUFANGBOATASSISTANT_3446 =", value, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446NotEqualTo(String value) {
            addCriterion("BUFANGBOATASSISTANT_3446 <>", value, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446GreaterThan(String value) {
            addCriterion("BUFANGBOATASSISTANT_3446 >", value, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446GreaterThanOrEqualTo(String value) {
            addCriterion("BUFANGBOATASSISTANT_3446 >=", value, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446LessThan(String value) {
            addCriterion("BUFANGBOATASSISTANT_3446 <", value, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446LessThanOrEqualTo(String value) {
            addCriterion("BUFANGBOATASSISTANT_3446 <=", value, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446Like(String value) {
            addCriterion("BUFANGBOATASSISTANT_3446 like", value, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446NotLike(String value) {
            addCriterion("BUFANGBOATASSISTANT_3446 not like", value, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446In(List<String> values) {
            addCriterion("BUFANGBOATASSISTANT_3446 in", values, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446NotIn(List<String> values) {
            addCriterion("BUFANGBOATASSISTANT_3446 not in", values, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446Between(String value1, String value2) {
            addCriterion("BUFANGBOATASSISTANT_3446 between", value1, value2, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatassistant3446NotBetween(String value1, String value2) {
            addCriterion("BUFANGBOATASSISTANT_3446 not between", value1, value2, "bufangboatassistant3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446IsNull() {
            addCriterion("RECOVERMAVERAGEWINDS_3446 is null");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446IsNotNull() {
            addCriterion("RECOVERMAVERAGEWINDS_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446EqualTo(String value) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 =", value, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446NotEqualTo(String value) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 <>", value, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446GreaterThan(String value) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 >", value, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446GreaterThanOrEqualTo(String value) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 >=", value, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446LessThan(String value) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 <", value, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446LessThanOrEqualTo(String value) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 <=", value, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446Like(String value) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 like", value, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446NotLike(String value) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 not like", value, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446In(List<String> values) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 in", values, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446NotIn(List<String> values) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 not in", values, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446Between(String value1, String value2) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 between", value1, value2, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andRecovermaveragewinds3446NotBetween(String value1, String value2) {
            addCriterion("RECOVERMAVERAGEWINDS_3446 not between", value1, value2, "recovermaveragewinds3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446IsNull() {
            addCriterion("FLOATWATERTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446IsNotNull() {
            addCriterion("FLOATWATERTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446EqualTo(String value) {
            addCriterion("FLOATWATERTIME_3446 =", value, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446NotEqualTo(String value) {
            addCriterion("FLOATWATERTIME_3446 <>", value, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446GreaterThan(String value) {
            addCriterion("FLOATWATERTIME_3446 >", value, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446GreaterThanOrEqualTo(String value) {
            addCriterion("FLOATWATERTIME_3446 >=", value, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446LessThan(String value) {
            addCriterion("FLOATWATERTIME_3446 <", value, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446LessThanOrEqualTo(String value) {
            addCriterion("FLOATWATERTIME_3446 <=", value, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446Like(String value) {
            addCriterion("FLOATWATERTIME_3446 like", value, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446NotLike(String value) {
            addCriterion("FLOATWATERTIME_3446 not like", value, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446In(List<String> values) {
            addCriterion("FLOATWATERTIME_3446 in", values, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446NotIn(List<String> values) {
            addCriterion("FLOATWATERTIME_3446 not in", values, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446Between(String value1, String value2) {
            addCriterion("FLOATWATERTIME_3446 between", value1, value2, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andFloatwatertime3446NotBetween(String value1, String value2) {
            addCriterion("FLOATWATERTIME_3446 not between", value1, value2, "floatwatertime3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446IsNull() {
            addCriterion("SPECIALVERSION_3446 is null");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446IsNotNull() {
            addCriterion("SPECIALVERSION_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446EqualTo(String value) {
            addCriterion("SPECIALVERSION_3446 =", value, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446NotEqualTo(String value) {
            addCriterion("SPECIALVERSION_3446 <>", value, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446GreaterThan(String value) {
            addCriterion("SPECIALVERSION_3446 >", value, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446GreaterThanOrEqualTo(String value) {
            addCriterion("SPECIALVERSION_3446 >=", value, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446LessThan(String value) {
            addCriterion("SPECIALVERSION_3446 <", value, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446LessThanOrEqualTo(String value) {
            addCriterion("SPECIALVERSION_3446 <=", value, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446Like(String value) {
            addCriterion("SPECIALVERSION_3446 like", value, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446NotLike(String value) {
            addCriterion("SPECIALVERSION_3446 not like", value, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446In(List<String> values) {
            addCriterion("SPECIALVERSION_3446 in", values, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446NotIn(List<String> values) {
            addCriterion("SPECIALVERSION_3446 not in", values, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446Between(String value1, String value2) {
            addCriterion("SPECIALVERSION_3446 between", value1, value2, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andSpecialversion3446NotBetween(String value1, String value2) {
            addCriterion("SPECIALVERSION_3446 not between", value1, value2, "specialversion3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446IsNull() {
            addCriterion("RECOVERBOATDPERSON_3446 is null");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446IsNotNull() {
            addCriterion("RECOVERBOATDPERSON_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446EqualTo(String value) {
            addCriterion("RECOVERBOATDPERSON_3446 =", value, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446NotEqualTo(String value) {
            addCriterion("RECOVERBOATDPERSON_3446 <>", value, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446GreaterThan(String value) {
            addCriterion("RECOVERBOATDPERSON_3446 >", value, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446GreaterThanOrEqualTo(String value) {
            addCriterion("RECOVERBOATDPERSON_3446 >=", value, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446LessThan(String value) {
            addCriterion("RECOVERBOATDPERSON_3446 <", value, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446LessThanOrEqualTo(String value) {
            addCriterion("RECOVERBOATDPERSON_3446 <=", value, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446Like(String value) {
            addCriterion("RECOVERBOATDPERSON_3446 like", value, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446NotLike(String value) {
            addCriterion("RECOVERBOATDPERSON_3446 not like", value, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446In(List<String> values) {
            addCriterion("RECOVERBOATDPERSON_3446 in", values, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446NotIn(List<String> values) {
            addCriterion("RECOVERBOATDPERSON_3446 not in", values, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446Between(String value1, String value2) {
            addCriterion("RECOVERBOATDPERSON_3446 between", value1, value2, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andRecoverboatdperson3446NotBetween(String value1, String value2) {
            addCriterion("RECOVERBOATDPERSON_3446 not between", value1, value2, "recoverboatdperson3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446IsNull() {
            addCriterion("ONCEBUFANGBOATTIME_3446 is null");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446IsNotNull() {
            addCriterion("ONCEBUFANGBOATTIME_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446EqualTo(String value) {
            addCriterion("ONCEBUFANGBOATTIME_3446 =", value, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446NotEqualTo(String value) {
            addCriterion("ONCEBUFANGBOATTIME_3446 <>", value, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446GreaterThan(String value) {
            addCriterion("ONCEBUFANGBOATTIME_3446 >", value, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446GreaterThanOrEqualTo(String value) {
            addCriterion("ONCEBUFANGBOATTIME_3446 >=", value, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446LessThan(String value) {
            addCriterion("ONCEBUFANGBOATTIME_3446 <", value, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446LessThanOrEqualTo(String value) {
            addCriterion("ONCEBUFANGBOATTIME_3446 <=", value, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446Like(String value) {
            addCriterion("ONCEBUFANGBOATTIME_3446 like", value, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446NotLike(String value) {
            addCriterion("ONCEBUFANGBOATTIME_3446 not like", value, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446In(List<String> values) {
            addCriterion("ONCEBUFANGBOATTIME_3446 in", values, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446NotIn(List<String> values) {
            addCriterion("ONCEBUFANGBOATTIME_3446 not in", values, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446Between(String value1, String value2) {
            addCriterion("ONCEBUFANGBOATTIME_3446 between", value1, value2, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andOncebufangboattime3446NotBetween(String value1, String value2) {
            addCriterion("ONCEBUFANGBOATTIME_3446 not between", value1, value2, "oncebufangboattime3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446IsNull() {
            addCriterion("BUFANGBOATDRIVER_3446 is null");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446IsNotNull() {
            addCriterion("BUFANGBOATDRIVER_3446 is not null");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446EqualTo(String value) {
            addCriterion("BUFANGBOATDRIVER_3446 =", value, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446NotEqualTo(String value) {
            addCriterion("BUFANGBOATDRIVER_3446 <>", value, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446GreaterThan(String value) {
            addCriterion("BUFANGBOATDRIVER_3446 >", value, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446GreaterThanOrEqualTo(String value) {
            addCriterion("BUFANGBOATDRIVER_3446 >=", value, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446LessThan(String value) {
            addCriterion("BUFANGBOATDRIVER_3446 <", value, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446LessThanOrEqualTo(String value) {
            addCriterion("BUFANGBOATDRIVER_3446 <=", value, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446Like(String value) {
            addCriterion("BUFANGBOATDRIVER_3446 like", value, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446NotLike(String value) {
            addCriterion("BUFANGBOATDRIVER_3446 not like", value, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446In(List<String> values) {
            addCriterion("BUFANGBOATDRIVER_3446 in", values, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446NotIn(List<String> values) {
            addCriterion("BUFANGBOATDRIVER_3446 not in", values, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446Between(String value1, String value2) {
            addCriterion("BUFANGBOATDRIVER_3446 between", value1, value2, "bufangboatdriver3446");
            return (Criteria) this;
        }

        public Criteria andBufangboatdriver3446NotBetween(String value1, String value2) {
            addCriterion("BUFANGBOATDRIVER_3446 not between", value1, value2, "bufangboatdriver3446");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}