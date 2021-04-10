package com.orient.weibao.mbg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InformLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InformLogExample() {
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

        public Criteria andCTableId3566IsNull() {
            addCriterion("C_TABLE_ID_3566 is null");
            return (Criteria) this;
        }

        public Criteria andCTableId3566IsNotNull() {
            addCriterion("C_TABLE_ID_3566 is not null");
            return (Criteria) this;
        }

        public Criteria andCTableId3566EqualTo(String value) {
            addCriterion("C_TABLE_ID_3566 =", value, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566NotEqualTo(String value) {
            addCriterion("C_TABLE_ID_3566 <>", value, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566GreaterThan(String value) {
            addCriterion("C_TABLE_ID_3566 >", value, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566GreaterThanOrEqualTo(String value) {
            addCriterion("C_TABLE_ID_3566 >=", value, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566LessThan(String value) {
            addCriterion("C_TABLE_ID_3566 <", value, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566LessThanOrEqualTo(String value) {
            addCriterion("C_TABLE_ID_3566 <=", value, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566Like(String value) {
            addCriterion("C_TABLE_ID_3566 like", value, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566NotLike(String value) {
            addCriterion("C_TABLE_ID_3566 not like", value, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566In(List<String> values) {
            addCriterion("C_TABLE_ID_3566 in", values, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566NotIn(List<String> values) {
            addCriterion("C_TABLE_ID_3566 not in", values, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566Between(String value1, String value2) {
            addCriterion("C_TABLE_ID_3566 between", value1, value2, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCTableId3566NotBetween(String value1, String value2) {
            addCriterion("C_TABLE_ID_3566 not between", value1, value2, "cTableId3566");
            return (Criteria) this;
        }

        public Criteria andCState3566IsNull() {
            addCriterion("C_STATE_3566 is null");
            return (Criteria) this;
        }

        public Criteria andCState3566IsNotNull() {
            addCriterion("C_STATE_3566 is not null");
            return (Criteria) this;
        }

        public Criteria andCState3566EqualTo(String value) {
            addCriterion("C_STATE_3566 =", value, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566NotEqualTo(String value) {
            addCriterion("C_STATE_3566 <>", value, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566GreaterThan(String value) {
            addCriterion("C_STATE_3566 >", value, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566GreaterThanOrEqualTo(String value) {
            addCriterion("C_STATE_3566 >=", value, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566LessThan(String value) {
            addCriterion("C_STATE_3566 <", value, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566LessThanOrEqualTo(String value) {
            addCriterion("C_STATE_3566 <=", value, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566Like(String value) {
            addCriterion("C_STATE_3566 like", value, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566NotLike(String value) {
            addCriterion("C_STATE_3566 not like", value, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566In(List<String> values) {
            addCriterion("C_STATE_3566 in", values, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566NotIn(List<String> values) {
            addCriterion("C_STATE_3566 not in", values, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566Between(String value1, String value2) {
            addCriterion("C_STATE_3566 between", value1, value2, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCState3566NotBetween(String value1, String value2) {
            addCriterion("C_STATE_3566 not between", value1, value2, "cState3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566IsNull() {
            addCriterion("C_UPLOAD_TIME_3566 is null");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566IsNotNull() {
            addCriterion("C_UPLOAD_TIME_3566 is not null");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566EqualTo(String value) {
            addCriterion("C_UPLOAD_TIME_3566 =", value, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566NotEqualTo(String value) {
            addCriterion("C_UPLOAD_TIME_3566 <>", value, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566GreaterThan(String value) {
            addCriterion("C_UPLOAD_TIME_3566 >", value, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566GreaterThanOrEqualTo(String value) {
            addCriterion("C_UPLOAD_TIME_3566 >=", value, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566LessThan(String value) {
            addCriterion("C_UPLOAD_TIME_3566 <", value, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566LessThanOrEqualTo(String value) {
            addCriterion("C_UPLOAD_TIME_3566 <=", value, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566Like(String value) {
            addCriterion("C_UPLOAD_TIME_3566 like", value, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566NotLike(String value) {
            addCriterion("C_UPLOAD_TIME_3566 not like", value, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566In(List<String> values) {
            addCriterion("C_UPLOAD_TIME_3566 in", values, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566NotIn(List<String> values) {
            addCriterion("C_UPLOAD_TIME_3566 not in", values, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566Between(String value1, String value2) {
            addCriterion("C_UPLOAD_TIME_3566 between", value1, value2, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCUploadTime3566NotBetween(String value1, String value2) {
            addCriterion("C_UPLOAD_TIME_3566 not between", value1, value2, "cUploadTime3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566IsNull() {
            addCriterion("C_TASK_ID_3566 is null");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566IsNotNull() {
            addCriterion("C_TASK_ID_3566 is not null");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566EqualTo(String value) {
            addCriterion("C_TASK_ID_3566 =", value, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566NotEqualTo(String value) {
            addCriterion("C_TASK_ID_3566 <>", value, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566GreaterThan(String value) {
            addCriterion("C_TASK_ID_3566 >", value, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566GreaterThanOrEqualTo(String value) {
            addCriterion("C_TASK_ID_3566 >=", value, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566LessThan(String value) {
            addCriterion("C_TASK_ID_3566 <", value, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566LessThanOrEqualTo(String value) {
            addCriterion("C_TASK_ID_3566 <=", value, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566Like(String value) {
            addCriterion("C_TASK_ID_3566 like", value, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566NotLike(String value) {
            addCriterion("C_TASK_ID_3566 not like", value, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566In(List<String> values) {
            addCriterion("C_TASK_ID_3566 in", values, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566NotIn(List<String> values) {
            addCriterion("C_TASK_ID_3566 not in", values, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566Between(String value1, String value2) {
            addCriterion("C_TASK_ID_3566 between", value1, value2, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCTaskId3566NotBetween(String value1, String value2) {
            addCriterion("C_TASK_ID_3566 not between", value1, value2, "cTaskId3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566IsNull() {
            addCriterion("C_UPLOAD_PERSON_3566 is null");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566IsNotNull() {
            addCriterion("C_UPLOAD_PERSON_3566 is not null");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566EqualTo(String value) {
            addCriterion("C_UPLOAD_PERSON_3566 =", value, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566NotEqualTo(String value) {
            addCriterion("C_UPLOAD_PERSON_3566 <>", value, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566GreaterThan(String value) {
            addCriterion("C_UPLOAD_PERSON_3566 >", value, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566GreaterThanOrEqualTo(String value) {
            addCriterion("C_UPLOAD_PERSON_3566 >=", value, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566LessThan(String value) {
            addCriterion("C_UPLOAD_PERSON_3566 <", value, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566LessThanOrEqualTo(String value) {
            addCriterion("C_UPLOAD_PERSON_3566 <=", value, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566Like(String value) {
            addCriterion("C_UPLOAD_PERSON_3566 like", value, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566NotLike(String value) {
            addCriterion("C_UPLOAD_PERSON_3566 not like", value, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566In(List<String> values) {
            addCriterion("C_UPLOAD_PERSON_3566 in", values, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566NotIn(List<String> values) {
            addCriterion("C_UPLOAD_PERSON_3566 not in", values, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566Between(String value1, String value2) {
            addCriterion("C_UPLOAD_PERSON_3566 between", value1, value2, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCUploadPerson3566NotBetween(String value1, String value2) {
            addCriterion("C_UPLOAD_PERSON_3566 not between", value1, value2, "cUploadPerson3566");
            return (Criteria) this;
        }

        public Criteria andCType3566IsNull() {
            addCriterion("C_TYPE_3566 is null");
            return (Criteria) this;
        }

        public Criteria andCType3566IsNotNull() {
            addCriterion("C_TYPE_3566 is not null");
            return (Criteria) this;
        }

        public Criteria andCType3566EqualTo(String value) {
            addCriterion("C_TYPE_3566 =", value, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566NotEqualTo(String value) {
            addCriterion("C_TYPE_3566 <>", value, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566GreaterThan(String value) {
            addCriterion("C_TYPE_3566 >", value, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566GreaterThanOrEqualTo(String value) {
            addCriterion("C_TYPE_3566 >=", value, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566LessThan(String value) {
            addCriterion("C_TYPE_3566 <", value, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566LessThanOrEqualTo(String value) {
            addCriterion("C_TYPE_3566 <=", value, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566Like(String value) {
            addCriterion("C_TYPE_3566 like", value, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566NotLike(String value) {
            addCriterion("C_TYPE_3566 not like", value, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566In(List<String> values) {
            addCriterion("C_TYPE_3566 in", values, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566NotIn(List<String> values) {
            addCriterion("C_TYPE_3566 not in", values, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566Between(String value1, String value2) {
            addCriterion("C_TYPE_3566 between", value1, value2, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCType3566NotBetween(String value1, String value2) {
            addCriterion("C_TYPE_3566 not between", value1, value2, "cType3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566IsNull() {
            addCriterion("C_TASK_NAME_3566 is null");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566IsNotNull() {
            addCriterion("C_TASK_NAME_3566 is not null");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566EqualTo(String value) {
            addCriterion("C_TASK_NAME_3566 =", value, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566NotEqualTo(String value) {
            addCriterion("C_TASK_NAME_3566 <>", value, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566GreaterThan(String value) {
            addCriterion("C_TASK_NAME_3566 >", value, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566GreaterThanOrEqualTo(String value) {
            addCriterion("C_TASK_NAME_3566 >=", value, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566LessThan(String value) {
            addCriterion("C_TASK_NAME_3566 <", value, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566LessThanOrEqualTo(String value) {
            addCriterion("C_TASK_NAME_3566 <=", value, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566Like(String value) {
            addCriterion("C_TASK_NAME_3566 like", value, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566NotLike(String value) {
            addCriterion("C_TASK_NAME_3566 not like", value, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566In(List<String> values) {
            addCriterion("C_TASK_NAME_3566 in", values, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566NotIn(List<String> values) {
            addCriterion("C_TASK_NAME_3566 not in", values, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566Between(String value1, String value2) {
            addCriterion("C_TASK_NAME_3566 between", value1, value2, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTaskName3566NotBetween(String value1, String value2) {
            addCriterion("C_TASK_NAME_3566 not between", value1, value2, "cTaskName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566IsNull() {
            addCriterion("C_TABLE_NAME_3566 is null");
            return (Criteria) this;
        }

        public Criteria andCTableName3566IsNotNull() {
            addCriterion("C_TABLE_NAME_3566 is not null");
            return (Criteria) this;
        }

        public Criteria andCTableName3566EqualTo(String value) {
            addCriterion("C_TABLE_NAME_3566 =", value, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566NotEqualTo(String value) {
            addCriterion("C_TABLE_NAME_3566 <>", value, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566GreaterThan(String value) {
            addCriterion("C_TABLE_NAME_3566 >", value, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566GreaterThanOrEqualTo(String value) {
            addCriterion("C_TABLE_NAME_3566 >=", value, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566LessThan(String value) {
            addCriterion("C_TABLE_NAME_3566 <", value, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566LessThanOrEqualTo(String value) {
            addCriterion("C_TABLE_NAME_3566 <=", value, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566Like(String value) {
            addCriterion("C_TABLE_NAME_3566 like", value, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566NotLike(String value) {
            addCriterion("C_TABLE_NAME_3566 not like", value, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566In(List<String> values) {
            addCriterion("C_TABLE_NAME_3566 in", values, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566NotIn(List<String> values) {
            addCriterion("C_TABLE_NAME_3566 not in", values, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566Between(String value1, String value2) {
            addCriterion("C_TABLE_NAME_3566 between", value1, value2, "cTableName3566");
            return (Criteria) this;
        }

        public Criteria andCTableName3566NotBetween(String value1, String value2) {
            addCriterion("C_TABLE_NAME_3566 not between", value1, value2, "cTableName3566");
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