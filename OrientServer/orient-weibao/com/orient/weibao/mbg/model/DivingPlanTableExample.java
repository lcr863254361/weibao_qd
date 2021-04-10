package com.orient.weibao.mbg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DivingPlanTableExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DivingPlanTableExample() {
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

        public Criteria andCZuoxian3487IsNull() {
            addCriterion("C_ZUOXIAN_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487IsNotNull() {
            addCriterion("C_ZUOXIAN_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487EqualTo(String value) {
            addCriterion("C_ZUOXIAN_3487 =", value, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487NotEqualTo(String value) {
            addCriterion("C_ZUOXIAN_3487 <>", value, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487GreaterThan(String value) {
            addCriterion("C_ZUOXIAN_3487 >", value, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_ZUOXIAN_3487 >=", value, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487LessThan(String value) {
            addCriterion("C_ZUOXIAN_3487 <", value, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487LessThanOrEqualTo(String value) {
            addCriterion("C_ZUOXIAN_3487 <=", value, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487Like(String value) {
            addCriterion("C_ZUOXIAN_3487 like", value, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487NotLike(String value) {
            addCriterion("C_ZUOXIAN_3487 not like", value, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487In(List<String> values) {
            addCriterion("C_ZUOXIAN_3487 in", values, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487NotIn(List<String> values) {
            addCriterion("C_ZUOXIAN_3487 not in", values, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487Between(String value1, String value2) {
            addCriterion("C_ZUOXIAN_3487 between", value1, value2, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCZuoxian3487NotBetween(String value1, String value2) {
            addCriterion("C_ZUOXIAN_3487 not between", value1, value2, "cZuoxian3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487IsNull() {
            addCriterion("C_PLAN_THROW_TIME_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487IsNotNull() {
            addCriterion("C_PLAN_THROW_TIME_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487EqualTo(String value) {
            addCriterion("C_PLAN_THROW_TIME_3487 =", value, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487NotEqualTo(String value) {
            addCriterion("C_PLAN_THROW_TIME_3487 <>", value, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487GreaterThan(String value) {
            addCriterion("C_PLAN_THROW_TIME_3487 >", value, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_PLAN_THROW_TIME_3487 >=", value, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487LessThan(String value) {
            addCriterion("C_PLAN_THROW_TIME_3487 <", value, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487LessThanOrEqualTo(String value) {
            addCriterion("C_PLAN_THROW_TIME_3487 <=", value, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487Like(String value) {
            addCriterion("C_PLAN_THROW_TIME_3487 like", value, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487NotLike(String value) {
            addCriterion("C_PLAN_THROW_TIME_3487 not like", value, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487In(List<String> values) {
            addCriterion("C_PLAN_THROW_TIME_3487 in", values, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487NotIn(List<String> values) {
            addCriterion("C_PLAN_THROW_TIME_3487 not in", values, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487Between(String value1, String value2) {
            addCriterion("C_PLAN_THROW_TIME_3487 between", value1, value2, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3487NotBetween(String value1, String value2) {
            addCriterion("C_PLAN_THROW_TIME_3487 not between", value1, value2, "cPlanThrowTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487IsNull() {
            addCriterion("C_POSITION_TIME_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487IsNotNull() {
            addCriterion("C_POSITION_TIME_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487EqualTo(String value) {
            addCriterion("C_POSITION_TIME_3487 =", value, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487NotEqualTo(String value) {
            addCriterion("C_POSITION_TIME_3487 <>", value, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487GreaterThan(String value) {
            addCriterion("C_POSITION_TIME_3487 >", value, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_POSITION_TIME_3487 >=", value, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487LessThan(String value) {
            addCriterion("C_POSITION_TIME_3487 <", value, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487LessThanOrEqualTo(String value) {
            addCriterion("C_POSITION_TIME_3487 <=", value, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487Like(String value) {
            addCriterion("C_POSITION_TIME_3487 like", value, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487NotLike(String value) {
            addCriterion("C_POSITION_TIME_3487 not like", value, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487In(List<String> values) {
            addCriterion("C_POSITION_TIME_3487 in", values, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487NotIn(List<String> values) {
            addCriterion("C_POSITION_TIME_3487 not in", values, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487Between(String value1, String value2) {
            addCriterion("C_POSITION_TIME_3487 between", value1, value2, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3487NotBetween(String value1, String value2) {
            addCriterion("C_POSITION_TIME_3487 not between", value1, value2, "cPositionTime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487IsNull() {
            addCriterion("C_FLOAT_TO_WTIME_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487IsNotNull() {
            addCriterion("C_FLOAT_TO_WTIME_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487EqualTo(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3487 =", value, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487NotEqualTo(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3487 <>", value, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487GreaterThan(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3487 >", value, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3487 >=", value, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487LessThan(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3487 <", value, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487LessThanOrEqualTo(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3487 <=", value, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487Like(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3487 like", value, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487NotLike(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3487 not like", value, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487In(List<String> values) {
            addCriterion("C_FLOAT_TO_WTIME_3487 in", values, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487NotIn(List<String> values) {
            addCriterion("C_FLOAT_TO_WTIME_3487 not in", values, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487Between(String value1, String value2) {
            addCriterion("C_FLOAT_TO_WTIME_3487 between", value1, value2, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3487NotBetween(String value1, String value2) {
            addCriterion("C_FLOAT_TO_WTIME_3487 not between", value1, value2, "cFloatToWtime3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487IsNull() {
            addCriterion("C_MAIN_DRIVER_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487IsNotNull() {
            addCriterion("C_MAIN_DRIVER_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487EqualTo(String value) {
            addCriterion("C_MAIN_DRIVER_3487 =", value, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487NotEqualTo(String value) {
            addCriterion("C_MAIN_DRIVER_3487 <>", value, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487GreaterThan(String value) {
            addCriterion("C_MAIN_DRIVER_3487 >", value, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_MAIN_DRIVER_3487 >=", value, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487LessThan(String value) {
            addCriterion("C_MAIN_DRIVER_3487 <", value, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487LessThanOrEqualTo(String value) {
            addCriterion("C_MAIN_DRIVER_3487 <=", value, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487Like(String value) {
            addCriterion("C_MAIN_DRIVER_3487 like", value, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487NotLike(String value) {
            addCriterion("C_MAIN_DRIVER_3487 not like", value, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487In(List<String> values) {
            addCriterion("C_MAIN_DRIVER_3487 in", values, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487NotIn(List<String> values) {
            addCriterion("C_MAIN_DRIVER_3487 not in", values, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487Between(String value1, String value2) {
            addCriterion("C_MAIN_DRIVER_3487 between", value1, value2, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCMainDriver3487NotBetween(String value1, String value2) {
            addCriterion("C_MAIN_DRIVER_3487 not between", value1, value2, "cMainDriver3487");
            return (Criteria) this;
        }

        public Criteria andCDensity3487IsNull() {
            addCriterion("C_DENSITY_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCDensity3487IsNotNull() {
            addCriterion("C_DENSITY_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCDensity3487EqualTo(Double value) {
            addCriterion("C_DENSITY_3487 =", value, "cDensity3487");
            return (Criteria) this;
        }

        public Criteria andCDensity3487NotEqualTo(Double value) {
            addCriterion("C_DENSITY_3487 <>", value, "cDensity3487");
            return (Criteria) this;
        }

        public Criteria andCDensity3487GreaterThan(Double value) {
            addCriterion("C_DENSITY_3487 >", value, "cDensity3487");
            return (Criteria) this;
        }

        public Criteria andCDensity3487GreaterThanOrEqualTo(Double value) {
            addCriterion("C_DENSITY_3487 >=", value, "cDensity3487");
            return (Criteria) this;
        }

        public Criteria andCDensity3487LessThan(Double value) {
            addCriterion("C_DENSITY_3487 <", value, "cDensity3487");
            return (Criteria) this;
        }

        public Criteria andCDensity3487LessThanOrEqualTo(Double value) {
            addCriterion("C_DENSITY_3487 <=", value, "cDensity3487");
            return (Criteria) this;
        }

        public Criteria andCDensity3487In(List<Double> values) {
            addCriterion("C_DENSITY_3487 in", values, "cDensity3487");
            return (Criteria) this;
        }

        public Criteria andCDensity3487NotIn(List<Double> values) {
            addCriterion("C_DENSITY_3487 not in", values, "cDensity3487");
            return (Criteria) this;
        }

        public Criteria andCDensity3487Between(Double value1, Double value2) {
            addCriterion("C_DENSITY_3487 between", value1, value2, "cDensity3487");
            return (Criteria) this;
        }

        public Criteria andCDensity3487NotBetween(Double value1, Double value2) {
            addCriterion("C_DENSITY_3487 not between", value1, value2, "cDensity3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487IsNull() {
            addCriterion("C_NUMBER_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCNumber3487IsNotNull() {
            addCriterion("C_NUMBER_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCNumber3487EqualTo(String value) {
            addCriterion("C_NUMBER_3487 =", value, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487NotEqualTo(String value) {
            addCriterion("C_NUMBER_3487 <>", value, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487GreaterThan(String value) {
            addCriterion("C_NUMBER_3487 >", value, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_NUMBER_3487 >=", value, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487LessThan(String value) {
            addCriterion("C_NUMBER_3487 <", value, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487LessThanOrEqualTo(String value) {
            addCriterion("C_NUMBER_3487 <=", value, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487Like(String value) {
            addCriterion("C_NUMBER_3487 like", value, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487NotLike(String value) {
            addCriterion("C_NUMBER_3487 not like", value, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487In(List<String> values) {
            addCriterion("C_NUMBER_3487 in", values, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487NotIn(List<String> values) {
            addCriterion("C_NUMBER_3487 not in", values, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487Between(String value1, String value2) {
            addCriterion("C_NUMBER_3487 between", value1, value2, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCNumber3487NotBetween(String value1, String value2) {
            addCriterion("C_NUMBER_3487 not between", value1, value2, "cNumber3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487IsNull() {
            addCriterion("C_DIVING_TYPE_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487IsNotNull() {
            addCriterion("C_DIVING_TYPE_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487EqualTo(String value) {
            addCriterion("C_DIVING_TYPE_3487 =", value, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487NotEqualTo(String value) {
            addCriterion("C_DIVING_TYPE_3487 <>", value, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487GreaterThan(String value) {
            addCriterion("C_DIVING_TYPE_3487 >", value, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_DIVING_TYPE_3487 >=", value, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487LessThan(String value) {
            addCriterion("C_DIVING_TYPE_3487 <", value, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487LessThanOrEqualTo(String value) {
            addCriterion("C_DIVING_TYPE_3487 <=", value, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487Like(String value) {
            addCriterion("C_DIVING_TYPE_3487 like", value, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487NotLike(String value) {
            addCriterion("C_DIVING_TYPE_3487 not like", value, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487In(List<String> values) {
            addCriterion("C_DIVING_TYPE_3487 in", values, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487NotIn(List<String> values) {
            addCriterion("C_DIVING_TYPE_3487 not in", values, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487Between(String value1, String value2) {
            addCriterion("C_DIVING_TYPE_3487 between", value1, value2, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCDivingType3487NotBetween(String value1, String value2) {
            addCriterion("C_DIVING_TYPE_3487 not between", value1, value2, "cDivingType3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487IsNull() {
            addCriterion("C_YOUXIAN_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487IsNotNull() {
            addCriterion("C_YOUXIAN_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487EqualTo(String value) {
            addCriterion("C_YOUXIAN_3487 =", value, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487NotEqualTo(String value) {
            addCriterion("C_YOUXIAN_3487 <>", value, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487GreaterThan(String value) {
            addCriterion("C_YOUXIAN_3487 >", value, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_YOUXIAN_3487 >=", value, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487LessThan(String value) {
            addCriterion("C_YOUXIAN_3487 <", value, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487LessThanOrEqualTo(String value) {
            addCriterion("C_YOUXIAN_3487 <=", value, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487Like(String value) {
            addCriterion("C_YOUXIAN_3487 like", value, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487NotLike(String value) {
            addCriterion("C_YOUXIAN_3487 not like", value, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487In(List<String> values) {
            addCriterion("C_YOUXIAN_3487 in", values, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487NotIn(List<String> values) {
            addCriterion("C_YOUXIAN_3487 not in", values, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487Between(String value1, String value2) {
            addCriterion("C_YOUXIAN_3487 between", value1, value2, "cYouxian3487");
            return (Criteria) this;
        }

        public Criteria andCYouxian3487NotBetween(String value1, String value2) {
            addCriterion("C_YOUXIAN_3487 not between", value1, value2, "cYouxian3487");
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

        public Criteria andCDivingDate3487IsNull() {
            addCriterion("C_DIVING_DATE_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487IsNotNull() {
            addCriterion("C_DIVING_DATE_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487EqualTo(String value) {
            addCriterion("C_DIVING_DATE_3487 =", value, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487NotEqualTo(String value) {
            addCriterion("C_DIVING_DATE_3487 <>", value, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487GreaterThan(String value) {
            addCriterion("C_DIVING_DATE_3487 >", value, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_DIVING_DATE_3487 >=", value, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487LessThan(String value) {
            addCriterion("C_DIVING_DATE_3487 <", value, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487LessThanOrEqualTo(String value) {
            addCriterion("C_DIVING_DATE_3487 <=", value, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487Like(String value) {
            addCriterion("C_DIVING_DATE_3487 like", value, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487NotLike(String value) {
            addCriterion("C_DIVING_DATE_3487 not like", value, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487In(List<String> values) {
            addCriterion("C_DIVING_DATE_3487 in", values, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487NotIn(List<String> values) {
            addCriterion("C_DIVING_DATE_3487 not in", values, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487Between(String value1, String value2) {
            addCriterion("C_DIVING_DATE_3487 between", value1, value2, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCDivingDate3487NotBetween(String value1, String value2) {
            addCriterion("C_DIVING_DATE_3487 not between", value1, value2, "cDivingDate3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487IsNull() {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487IsNotNull() {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487EqualTo(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 =", value, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487NotEqualTo(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 <>", value, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487GreaterThan(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 >", value, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 >=", value, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487LessThan(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 <", value, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487LessThanOrEqualTo(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 <=", value, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487Like(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 like", value, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487NotLike(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 not like", value, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487In(List<String> values) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 in", values, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487NotIn(List<String> values) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 not in", values, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487Between(String value1, String value2) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 between", value1, value2, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3487NotBetween(String value1, String value2) {
            addCriterion("C_PLAN_DIVING_DEPTH_3487 not between", value1, value2, "cPlanDivingDepth3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487IsNull() {
            addCriterion("C_WEIDU_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487IsNotNull() {
            addCriterion("C_WEIDU_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487EqualTo(String value) {
            addCriterion("C_WEIDU_3487 =", value, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487NotEqualTo(String value) {
            addCriterion("C_WEIDU_3487 <>", value, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487GreaterThan(String value) {
            addCriterion("C_WEIDU_3487 >", value, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_WEIDU_3487 >=", value, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487LessThan(String value) {
            addCriterion("C_WEIDU_3487 <", value, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487LessThanOrEqualTo(String value) {
            addCriterion("C_WEIDU_3487 <=", value, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487Like(String value) {
            addCriterion("C_WEIDU_3487 like", value, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487NotLike(String value) {
            addCriterion("C_WEIDU_3487 not like", value, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487In(List<String> values) {
            addCriterion("C_WEIDU_3487 in", values, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487NotIn(List<String> values) {
            addCriterion("C_WEIDU_3487 not in", values, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487Between(String value1, String value2) {
            addCriterion("C_WEIDU_3487 between", value1, value2, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCWeidu3487NotBetween(String value1, String value2) {
            addCriterion("C_WEIDU_3487 not between", value1, value2, "cWeidu3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487IsNull() {
            addCriterion("C_SEA_AREA_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487IsNotNull() {
            addCriterion("C_SEA_AREA_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487EqualTo(String value) {
            addCriterion("C_SEA_AREA_3487 =", value, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487NotEqualTo(String value) {
            addCriterion("C_SEA_AREA_3487 <>", value, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487GreaterThan(String value) {
            addCriterion("C_SEA_AREA_3487 >", value, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_SEA_AREA_3487 >=", value, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487LessThan(String value) {
            addCriterion("C_SEA_AREA_3487 <", value, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487LessThanOrEqualTo(String value) {
            addCriterion("C_SEA_AREA_3487 <=", value, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487Like(String value) {
            addCriterion("C_SEA_AREA_3487 like", value, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487NotLike(String value) {
            addCriterion("C_SEA_AREA_3487 not like", value, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487In(List<String> values) {
            addCriterion("C_SEA_AREA_3487 in", values, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487NotIn(List<String> values) {
            addCriterion("C_SEA_AREA_3487 not in", values, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487Between(String value1, String value2) {
            addCriterion("C_SEA_AREA_3487 between", value1, value2, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3487NotBetween(String value1, String value2) {
            addCriterion("C_SEA_AREA_3487 not between", value1, value2, "cSeaArea3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487IsNull() {
            addCriterion("C_FILL_PERSON_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487IsNotNull() {
            addCriterion("C_FILL_PERSON_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487EqualTo(String value) {
            addCriterion("C_FILL_PERSON_3487 =", value, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487NotEqualTo(String value) {
            addCriterion("C_FILL_PERSON_3487 <>", value, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487GreaterThan(String value) {
            addCriterion("C_FILL_PERSON_3487 >", value, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_FILL_PERSON_3487 >=", value, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487LessThan(String value) {
            addCriterion("C_FILL_PERSON_3487 <", value, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487LessThanOrEqualTo(String value) {
            addCriterion("C_FILL_PERSON_3487 <=", value, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487Like(String value) {
            addCriterion("C_FILL_PERSON_3487 like", value, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487NotLike(String value) {
            addCriterion("C_FILL_PERSON_3487 not like", value, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487In(List<String> values) {
            addCriterion("C_FILL_PERSON_3487 in", values, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487NotIn(List<String> values) {
            addCriterion("C_FILL_PERSON_3487 not in", values, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487Between(String value1, String value2) {
            addCriterion("C_FILL_PERSON_3487 between", value1, value2, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCFillPerson3487NotBetween(String value1, String value2) {
            addCriterion("C_FILL_PERSON_3487 not between", value1, value2, "cFillPerson3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487IsNull() {
            addCriterion("C_JINGDU_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487IsNotNull() {
            addCriterion("C_JINGDU_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487EqualTo(String value) {
            addCriterion("C_JINGDU_3487 =", value, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487NotEqualTo(String value) {
            addCriterion("C_JINGDU_3487 <>", value, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487GreaterThan(String value) {
            addCriterion("C_JINGDU_3487 >", value, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_JINGDU_3487 >=", value, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487LessThan(String value) {
            addCriterion("C_JINGDU_3487 <", value, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487LessThanOrEqualTo(String value) {
            addCriterion("C_JINGDU_3487 <=", value, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487Like(String value) {
            addCriterion("C_JINGDU_3487 like", value, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487NotLike(String value) {
            addCriterion("C_JINGDU_3487 not like", value, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487In(List<String> values) {
            addCriterion("C_JINGDU_3487 in", values, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487NotIn(List<String> values) {
            addCriterion("C_JINGDU_3487 not in", values, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487Between(String value1, String value2) {
            addCriterion("C_JINGDU_3487 between", value1, value2, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCJingdu3487NotBetween(String value1, String value2) {
            addCriterion("C_JINGDU_3487 not between", value1, value2, "cJingdu3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487IsNull() {
            addCriterion("C_HOME_MAP_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487IsNotNull() {
            addCriterion("C_HOME_MAP_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487EqualTo(String value) {
            addCriterion("C_HOME_MAP_3487 =", value, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487NotEqualTo(String value) {
            addCriterion("C_HOME_MAP_3487 <>", value, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487GreaterThan(String value) {
            addCriterion("C_HOME_MAP_3487 >", value, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_HOME_MAP_3487 >=", value, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487LessThan(String value) {
            addCriterion("C_HOME_MAP_3487 <", value, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487LessThanOrEqualTo(String value) {
            addCriterion("C_HOME_MAP_3487 <=", value, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487Like(String value) {
            addCriterion("C_HOME_MAP_3487 like", value, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487NotLike(String value) {
            addCriterion("C_HOME_MAP_3487 not like", value, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487In(List<String> values) {
            addCriterion("C_HOME_MAP_3487 in", values, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487NotIn(List<String> values) {
            addCriterion("C_HOME_MAP_3487 not in", values, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487Between(String value1, String value2) {
            addCriterion("C_HOME_MAP_3487 between", value1, value2, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCHomeMap3487NotBetween(String value1, String value2) {
            addCriterion("C_HOME_MAP_3487 not between", value1, value2, "cHomeMap3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487IsNull() {
            addCriterion("C_SAVE_OR_SUBMIT_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487IsNotNull() {
            addCriterion("C_SAVE_OR_SUBMIT_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487EqualTo(String value) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 =", value, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487NotEqualTo(String value) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 <>", value, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487GreaterThan(String value) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 >", value, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 >=", value, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487LessThan(String value) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 <", value, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487LessThanOrEqualTo(String value) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 <=", value, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487Like(String value) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 like", value, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487NotLike(String value) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 not like", value, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487In(List<String> values) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 in", values, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487NotIn(List<String> values) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 not in", values, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487Between(String value1, String value2) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 between", value1, value2, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCSaveOrSubmit3487NotBetween(String value1, String value2) {
            addCriterion("C_SAVE_OR_SUBMIT_3487 not between", value1, value2, "cSaveOrSubmit3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487IsNull() {
            addCriterion("C_WORK_POINT_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487IsNotNull() {
            addCriterion("C_WORK_POINT_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487EqualTo(String value) {
            addCriterion("C_WORK_POINT_3487 =", value, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487NotEqualTo(String value) {
            addCriterion("C_WORK_POINT_3487 <>", value, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487GreaterThan(String value) {
            addCriterion("C_WORK_POINT_3487 >", value, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_WORK_POINT_3487 >=", value, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487LessThan(String value) {
            addCriterion("C_WORK_POINT_3487 <", value, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487LessThanOrEqualTo(String value) {
            addCriterion("C_WORK_POINT_3487 <=", value, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487Like(String value) {
            addCriterion("C_WORK_POINT_3487 like", value, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487NotLike(String value) {
            addCriterion("C_WORK_POINT_3487 not like", value, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487In(List<String> values) {
            addCriterion("C_WORK_POINT_3487 in", values, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487NotIn(List<String> values) {
            addCriterion("C_WORK_POINT_3487 not in", values, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487Between(String value1, String value2) {
            addCriterion("C_WORK_POINT_3487 between", value1, value2, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCWorkPoint3487NotBetween(String value1, String value2) {
            addCriterion("C_WORK_POINT_3487 not between", value1, value2, "cWorkPoint3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487IsNull() {
            addCriterion("C_TABLE_STATE_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCTableState3487IsNotNull() {
            addCriterion("C_TABLE_STATE_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCTableState3487EqualTo(String value) {
            addCriterion("C_TABLE_STATE_3487 =", value, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487NotEqualTo(String value) {
            addCriterion("C_TABLE_STATE_3487 <>", value, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487GreaterThan(String value) {
            addCriterion("C_TABLE_STATE_3487 >", value, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_TABLE_STATE_3487 >=", value, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487LessThan(String value) {
            addCriterion("C_TABLE_STATE_3487 <", value, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487LessThanOrEqualTo(String value) {
            addCriterion("C_TABLE_STATE_3487 <=", value, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487Like(String value) {
            addCriterion("C_TABLE_STATE_3487 like", value, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487NotLike(String value) {
            addCriterion("C_TABLE_STATE_3487 not like", value, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487In(List<String> values) {
            addCriterion("C_TABLE_STATE_3487 in", values, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487NotIn(List<String> values) {
            addCriterion("C_TABLE_STATE_3487 not in", values, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487Between(String value1, String value2) {
            addCriterion("C_TABLE_STATE_3487 between", value1, value2, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCTableState3487NotBetween(String value1, String value2) {
            addCriterion("C_TABLE_STATE_3487 not between", value1, value2, "cTableState3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487IsNull() {
            addCriterion("C_RECORD_RED_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487IsNotNull() {
            addCriterion("C_RECORD_RED_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487EqualTo(String value) {
            addCriterion("C_RECORD_RED_3487 =", value, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487NotEqualTo(String value) {
            addCriterion("C_RECORD_RED_3487 <>", value, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487GreaterThan(String value) {
            addCriterion("C_RECORD_RED_3487 >", value, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_RECORD_RED_3487 >=", value, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487LessThan(String value) {
            addCriterion("C_RECORD_RED_3487 <", value, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487LessThanOrEqualTo(String value) {
            addCriterion("C_RECORD_RED_3487 <=", value, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487Like(String value) {
            addCriterion("C_RECORD_RED_3487 like", value, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487NotLike(String value) {
            addCriterion("C_RECORD_RED_3487 not like", value, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487In(List<String> values) {
            addCriterion("C_RECORD_RED_3487 in", values, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487NotIn(List<String> values) {
            addCriterion("C_RECORD_RED_3487 not in", values, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487Between(String value1, String value2) {
            addCriterion("C_RECORD_RED_3487 between", value1, value2, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCRecordRed3487NotBetween(String value1, String value2) {
            addCriterion("C_RECORD_RED_3487 not between", value1, value2, "cRecordRed3487");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487IsNull() {
            addCriterion("C_SERIAL_NUMBER_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487IsNotNull() {
            addCriterion("C_SERIAL_NUMBER_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487EqualTo(Long value) {
            addCriterion("C_SERIAL_NUMBER_3487 =", value, "cSerialNumber3487");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487NotEqualTo(Long value) {
            addCriterion("C_SERIAL_NUMBER_3487 <>", value, "cSerialNumber3487");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487GreaterThan(Long value) {
            addCriterion("C_SERIAL_NUMBER_3487 >", value, "cSerialNumber3487");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487GreaterThanOrEqualTo(Long value) {
            addCriterion("C_SERIAL_NUMBER_3487 >=", value, "cSerialNumber3487");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487LessThan(Long value) {
            addCriterion("C_SERIAL_NUMBER_3487 <", value, "cSerialNumber3487");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487LessThanOrEqualTo(Long value) {
            addCriterion("C_SERIAL_NUMBER_3487 <=", value, "cSerialNumber3487");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487In(List<Long> values) {
            addCriterion("C_SERIAL_NUMBER_3487 in", values, "cSerialNumber3487");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487NotIn(List<Long> values) {
            addCriterion("C_SERIAL_NUMBER_3487 not in", values, "cSerialNumber3487");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487Between(Long value1, Long value2) {
            addCriterion("C_SERIAL_NUMBER_3487 between", value1, value2, "cSerialNumber3487");
            return (Criteria) this;
        }

        public Criteria andCSerialNumber3487NotBetween(Long value1, Long value2) {
            addCriterion("C_SERIAL_NUMBER_3487 not between", value1, value2, "cSerialNumber3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487IsNull() {
            addCriterion("C_PREVIEW_ID_3487 is null");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487IsNotNull() {
            addCriterion("C_PREVIEW_ID_3487 is not null");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487EqualTo(String value) {
            addCriterion("C_PREVIEW_ID_3487 =", value, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487NotEqualTo(String value) {
            addCriterion("C_PREVIEW_ID_3487 <>", value, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487GreaterThan(String value) {
            addCriterion("C_PREVIEW_ID_3487 >", value, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487GreaterThanOrEqualTo(String value) {
            addCriterion("C_PREVIEW_ID_3487 >=", value, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487LessThan(String value) {
            addCriterion("C_PREVIEW_ID_3487 <", value, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487LessThanOrEqualTo(String value) {
            addCriterion("C_PREVIEW_ID_3487 <=", value, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487Like(String value) {
            addCriterion("C_PREVIEW_ID_3487 like", value, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487NotLike(String value) {
            addCriterion("C_PREVIEW_ID_3487 not like", value, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487In(List<String> values) {
            addCriterion("C_PREVIEW_ID_3487 in", values, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487NotIn(List<String> values) {
            addCriterion("C_PREVIEW_ID_3487 not in", values, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487Between(String value1, String value2) {
            addCriterion("C_PREVIEW_ID_3487 between", value1, value2, "cPreviewId3487");
            return (Criteria) this;
        }

        public Criteria andCPreviewId3487NotBetween(String value1, String value2) {
            addCriterion("C_PREVIEW_ID_3487 not between", value1, value2, "cPreviewId3487");
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