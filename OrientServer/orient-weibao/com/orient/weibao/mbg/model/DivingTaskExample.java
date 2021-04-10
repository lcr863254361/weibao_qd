package com.orient.weibao.mbg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DivingTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DivingTaskExample() {
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

        public Criteria andCPlanDivingDepth3208IsNull() {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208IsNotNull() {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208EqualTo(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 =", value, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208NotEqualTo(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 <>", value, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208GreaterThan(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 >", value, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 >=", value, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208LessThan(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 <", value, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208LessThanOrEqualTo(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 <=", value, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208Like(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 like", value, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208NotLike(String value) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 not like", value, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208In(List<String> values) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 in", values, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208NotIn(List<String> values) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 not in", values, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208Between(String value1, String value2) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 between", value1, value2, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanDivingDepth3208NotBetween(String value1, String value2) {
            addCriterion("C_PLAN_DIVING_DEPTH_3208 not between", value1, value2, "cPlanDivingDepth3208");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208IsNull() {
            addCriterion("C_PLAN_START_TIME_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208IsNotNull() {
            addCriterion("C_PLAN_START_TIME_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208EqualTo(Date value) {
            addCriterion("C_PLAN_START_TIME_3208 =", value, "cPlanStartTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208NotEqualTo(Date value) {
            addCriterion("C_PLAN_START_TIME_3208 <>", value, "cPlanStartTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208GreaterThan(Date value) {
            addCriterion("C_PLAN_START_TIME_3208 >", value, "cPlanStartTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208GreaterThanOrEqualTo(Date value) {
            addCriterion("C_PLAN_START_TIME_3208 >=", value, "cPlanStartTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208LessThan(Date value) {
            addCriterion("C_PLAN_START_TIME_3208 <", value, "cPlanStartTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208LessThanOrEqualTo(Date value) {
            addCriterion("C_PLAN_START_TIME_3208 <=", value, "cPlanStartTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208In(List<Date> values) {
            addCriterion("C_PLAN_START_TIME_3208 in", values, "cPlanStartTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208NotIn(List<Date> values) {
            addCriterion("C_PLAN_START_TIME_3208 not in", values, "cPlanStartTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208Between(Date value1, Date value2) {
            addCriterion("C_PLAN_START_TIME_3208 between", value1, value2, "cPlanStartTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanStartTime3208NotBetween(Date value1, Date value2) {
            addCriterion("C_PLAN_START_TIME_3208 not between", value1, value2, "cPlanStartTime3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208IsNull() {
            addCriterion("C_TASK_NAME_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208IsNotNull() {
            addCriterion("C_TASK_NAME_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208EqualTo(String value) {
            addCriterion("C_TASK_NAME_3208 =", value, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208NotEqualTo(String value) {
            addCriterion("C_TASK_NAME_3208 <>", value, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208GreaterThan(String value) {
            addCriterion("C_TASK_NAME_3208 >", value, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_TASK_NAME_3208 >=", value, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208LessThan(String value) {
            addCriterion("C_TASK_NAME_3208 <", value, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208LessThanOrEqualTo(String value) {
            addCriterion("C_TASK_NAME_3208 <=", value, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208Like(String value) {
            addCriterion("C_TASK_NAME_3208 like", value, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208NotLike(String value) {
            addCriterion("C_TASK_NAME_3208 not like", value, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208In(List<String> values) {
            addCriterion("C_TASK_NAME_3208 in", values, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208NotIn(List<String> values) {
            addCriterion("C_TASK_NAME_3208 not in", values, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208Between(String value1, String value2) {
            addCriterion("C_TASK_NAME_3208 between", value1, value2, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCTaskName3208NotBetween(String value1, String value2) {
            addCriterion("C_TASK_NAME_3208 not between", value1, value2, "cTaskName3208");
            return (Criteria) this;
        }

        public Criteria andCState3208IsNull() {
            addCriterion("C_STATE_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCState3208IsNotNull() {
            addCriterion("C_STATE_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCState3208EqualTo(String value) {
            addCriterion("C_STATE_3208 =", value, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208NotEqualTo(String value) {
            addCriterion("C_STATE_3208 <>", value, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208GreaterThan(String value) {
            addCriterion("C_STATE_3208 >", value, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_STATE_3208 >=", value, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208LessThan(String value) {
            addCriterion("C_STATE_3208 <", value, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208LessThanOrEqualTo(String value) {
            addCriterion("C_STATE_3208 <=", value, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208Like(String value) {
            addCriterion("C_STATE_3208 like", value, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208NotLike(String value) {
            addCriterion("C_STATE_3208 not like", value, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208In(List<String> values) {
            addCriterion("C_STATE_3208 in", values, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208NotIn(List<String> values) {
            addCriterion("C_STATE_3208 not in", values, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208Between(String value1, String value2) {
            addCriterion("C_STATE_3208 between", value1, value2, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCState3208NotBetween(String value1, String value2) {
            addCriterion("C_STATE_3208 not between", value1, value2, "cState3208");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208IsNull() {
            addCriterion("C_END_TIME_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208IsNotNull() {
            addCriterion("C_END_TIME_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208EqualTo(Date value) {
            addCriterion("C_END_TIME_3208 =", value, "cEndTime3208");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208NotEqualTo(Date value) {
            addCriterion("C_END_TIME_3208 <>", value, "cEndTime3208");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208GreaterThan(Date value) {
            addCriterion("C_END_TIME_3208 >", value, "cEndTime3208");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208GreaterThanOrEqualTo(Date value) {
            addCriterion("C_END_TIME_3208 >=", value, "cEndTime3208");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208LessThan(Date value) {
            addCriterion("C_END_TIME_3208 <", value, "cEndTime3208");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208LessThanOrEqualTo(Date value) {
            addCriterion("C_END_TIME_3208 <=", value, "cEndTime3208");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208In(List<Date> values) {
            addCriterion("C_END_TIME_3208 in", values, "cEndTime3208");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208NotIn(List<Date> values) {
            addCriterion("C_END_TIME_3208 not in", values, "cEndTime3208");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208Between(Date value1, Date value2) {
            addCriterion("C_END_TIME_3208 between", value1, value2, "cEndTime3208");
            return (Criteria) this;
        }

        public Criteria andCEndTime3208NotBetween(Date value1, Date value2) {
            addCriterion("C_END_TIME_3208 not between", value1, value2, "cEndTime3208");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdIsNull() {
            addCriterion("T_HANGDUAN_480_ID is null");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdIsNotNull() {
            addCriterion("T_HANGDUAN_480_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdEqualTo(String value) {
            addCriterion("T_HANGDUAN_480_ID =", value, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdNotEqualTo(String value) {
            addCriterion("T_HANGDUAN_480_ID <>", value, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdGreaterThan(String value) {
            addCriterion("T_HANGDUAN_480_ID >", value, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdGreaterThanOrEqualTo(String value) {
            addCriterion("T_HANGDUAN_480_ID >=", value, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdLessThan(String value) {
            addCriterion("T_HANGDUAN_480_ID <", value, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdLessThanOrEqualTo(String value) {
            addCriterion("T_HANGDUAN_480_ID <=", value, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdLike(String value) {
            addCriterion("T_HANGDUAN_480_ID like", value, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdNotLike(String value) {
            addCriterion("T_HANGDUAN_480_ID not like", value, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdIn(List<String> values) {
            addCriterion("T_HANGDUAN_480_ID in", values, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdNotIn(List<String> values) {
            addCriterion("T_HANGDUAN_480_ID not in", values, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdBetween(String value1, String value2) {
            addCriterion("T_HANGDUAN_480_ID between", value1, value2, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andTHangduan480IdNotBetween(String value1, String value2) {
            addCriterion("T_HANGDUAN_480_ID not between", value1, value2, "tHangduan480Id");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208IsNull() {
            addCriterion("C_RESPONSIBLE_PERSON_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208IsNotNull() {
            addCriterion("C_RESPONSIBLE_PERSON_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208EqualTo(String value) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 =", value, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208NotEqualTo(String value) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 <>", value, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208GreaterThan(String value) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 >", value, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 >=", value, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208LessThan(String value) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 <", value, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208LessThanOrEqualTo(String value) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 <=", value, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208Like(String value) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 like", value, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208NotLike(String value) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 not like", value, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208In(List<String> values) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 in", values, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208NotIn(List<String> values) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 not in", values, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208Between(String value1, String value2) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 between", value1, value2, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCResponsiblePerson3208NotBetween(String value1, String value2) {
            addCriterion("C_RESPONSIBLE_PERSON_3208 not between", value1, value2, "cResponsiblePerson3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208IsNull() {
            addCriterion("C_TASK_TARGET_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208IsNotNull() {
            addCriterion("C_TASK_TARGET_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208EqualTo(String value) {
            addCriterion("C_TASK_TARGET_3208 =", value, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208NotEqualTo(String value) {
            addCriterion("C_TASK_TARGET_3208 <>", value, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208GreaterThan(String value) {
            addCriterion("C_TASK_TARGET_3208 >", value, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_TASK_TARGET_3208 >=", value, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208LessThan(String value) {
            addCriterion("C_TASK_TARGET_3208 <", value, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208LessThanOrEqualTo(String value) {
            addCriterion("C_TASK_TARGET_3208 <=", value, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208Like(String value) {
            addCriterion("C_TASK_TARGET_3208 like", value, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208NotLike(String value) {
            addCriterion("C_TASK_TARGET_3208 not like", value, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208In(List<String> values) {
            addCriterion("C_TASK_TARGET_3208 in", values, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208NotIn(List<String> values) {
            addCriterion("C_TASK_TARGET_3208 not in", values, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208Between(String value1, String value2) {
            addCriterion("C_TASK_TARGET_3208 between", value1, value2, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskTarget3208NotBetween(String value1, String value2) {
            addCriterion("C_TASK_TARGET_3208 not between", value1, value2, "cTaskTarget3208");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208IsNull() {
            addCriterion("C_TASK_DEPTH_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208IsNotNull() {
            addCriterion("C_TASK_DEPTH_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208EqualTo(Long value) {
            addCriterion("C_TASK_DEPTH_3208 =", value, "cTaskDepth3208");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208NotEqualTo(Long value) {
            addCriterion("C_TASK_DEPTH_3208 <>", value, "cTaskDepth3208");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208GreaterThan(Long value) {
            addCriterion("C_TASK_DEPTH_3208 >", value, "cTaskDepth3208");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208GreaterThanOrEqualTo(Long value) {
            addCriterion("C_TASK_DEPTH_3208 >=", value, "cTaskDepth3208");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208LessThan(Long value) {
            addCriterion("C_TASK_DEPTH_3208 <", value, "cTaskDepth3208");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208LessThanOrEqualTo(Long value) {
            addCriterion("C_TASK_DEPTH_3208 <=", value, "cTaskDepth3208");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208In(List<Long> values) {
            addCriterion("C_TASK_DEPTH_3208 in", values, "cTaskDepth3208");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208NotIn(List<Long> values) {
            addCriterion("C_TASK_DEPTH_3208 not in", values, "cTaskDepth3208");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208Between(Long value1, Long value2) {
            addCriterion("C_TASK_DEPTH_3208 between", value1, value2, "cTaskDepth3208");
            return (Criteria) this;
        }

        public Criteria andCTaskDepth3208NotBetween(Long value1, Long value2) {
            addCriterion("C_TASK_DEPTH_3208 not between", value1, value2, "cTaskDepth3208");
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

        public Criteria andCAttendPeople3208IsNull() {
            addCriterion("C_ATTEND_PEOPLE_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208IsNotNull() {
            addCriterion("C_ATTEND_PEOPLE_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208EqualTo(String value) {
            addCriterion("C_ATTEND_PEOPLE_3208 =", value, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208NotEqualTo(String value) {
            addCriterion("C_ATTEND_PEOPLE_3208 <>", value, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208GreaterThan(String value) {
            addCriterion("C_ATTEND_PEOPLE_3208 >", value, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_ATTEND_PEOPLE_3208 >=", value, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208LessThan(String value) {
            addCriterion("C_ATTEND_PEOPLE_3208 <", value, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208LessThanOrEqualTo(String value) {
            addCriterion("C_ATTEND_PEOPLE_3208 <=", value, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208Like(String value) {
            addCriterion("C_ATTEND_PEOPLE_3208 like", value, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208NotLike(String value) {
            addCriterion("C_ATTEND_PEOPLE_3208 not like", value, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208In(List<String> values) {
            addCriterion("C_ATTEND_PEOPLE_3208 in", values, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208NotIn(List<String> values) {
            addCriterion("C_ATTEND_PEOPLE_3208 not in", values, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208Between(String value1, String value2) {
            addCriterion("C_ATTEND_PEOPLE_3208 between", value1, value2, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCAttendPeople3208NotBetween(String value1, String value2) {
            addCriterion("C_ATTEND_PEOPLE_3208 not between", value1, value2, "cAttendPeople3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208IsNull() {
            addCriterion("C_END_STATE_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCEndState3208IsNotNull() {
            addCriterion("C_END_STATE_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCEndState3208EqualTo(String value) {
            addCriterion("C_END_STATE_3208 =", value, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208NotEqualTo(String value) {
            addCriterion("C_END_STATE_3208 <>", value, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208GreaterThan(String value) {
            addCriterion("C_END_STATE_3208 >", value, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_END_STATE_3208 >=", value, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208LessThan(String value) {
            addCriterion("C_END_STATE_3208 <", value, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208LessThanOrEqualTo(String value) {
            addCriterion("C_END_STATE_3208 <=", value, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208Like(String value) {
            addCriterion("C_END_STATE_3208 like", value, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208NotLike(String value) {
            addCriterion("C_END_STATE_3208 not like", value, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208In(List<String> values) {
            addCriterion("C_END_STATE_3208 in", values, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208NotIn(List<String> values) {
            addCriterion("C_END_STATE_3208 not in", values, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208Between(String value1, String value2) {
            addCriterion("C_END_STATE_3208 between", value1, value2, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCEndState3208NotBetween(String value1, String value2) {
            addCriterion("C_END_STATE_3208 not between", value1, value2, "cEndState3208");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208IsNull() {
            addCriterion("C_JINGDU_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208IsNotNull() {
            addCriterion("C_JINGDU_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208EqualTo(Double value) {
            addCriterion("C_JINGDU_3208 =", value, "cJingdu3208");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208NotEqualTo(Double value) {
            addCriterion("C_JINGDU_3208 <>", value, "cJingdu3208");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208GreaterThan(Double value) {
            addCriterion("C_JINGDU_3208 >", value, "cJingdu3208");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208GreaterThanOrEqualTo(Double value) {
            addCriterion("C_JINGDU_3208 >=", value, "cJingdu3208");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208LessThan(Double value) {
            addCriterion("C_JINGDU_3208 <", value, "cJingdu3208");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208LessThanOrEqualTo(Double value) {
            addCriterion("C_JINGDU_3208 <=", value, "cJingdu3208");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208In(List<Double> values) {
            addCriterion("C_JINGDU_3208 in", values, "cJingdu3208");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208NotIn(List<Double> values) {
            addCriterion("C_JINGDU_3208 not in", values, "cJingdu3208");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208Between(Double value1, Double value2) {
            addCriterion("C_JINGDU_3208 between", value1, value2, "cJingdu3208");
            return (Criteria) this;
        }

        public Criteria andCJingdu3208NotBetween(Double value1, Double value2) {
            addCriterion("C_JINGDU_3208 not between", value1, value2, "cJingdu3208");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208IsNull() {
            addCriterion("C_WEIDU_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208IsNotNull() {
            addCriterion("C_WEIDU_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208EqualTo(Double value) {
            addCriterion("C_WEIDU_3208 =", value, "cWeidu3208");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208NotEqualTo(Double value) {
            addCriterion("C_WEIDU_3208 <>", value, "cWeidu3208");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208GreaterThan(Double value) {
            addCriterion("C_WEIDU_3208 >", value, "cWeidu3208");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208GreaterThanOrEqualTo(Double value) {
            addCriterion("C_WEIDU_3208 >=", value, "cWeidu3208");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208LessThan(Double value) {
            addCriterion("C_WEIDU_3208 <", value, "cWeidu3208");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208LessThanOrEqualTo(Double value) {
            addCriterion("C_WEIDU_3208 <=", value, "cWeidu3208");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208In(List<Double> values) {
            addCriterion("C_WEIDU_3208 in", values, "cWeidu3208");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208NotIn(List<Double> values) {
            addCriterion("C_WEIDU_3208 not in", values, "cWeidu3208");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208Between(Double value1, Double value2) {
            addCriterion("C_WEIDU_3208 between", value1, value2, "cWeidu3208");
            return (Criteria) this;
        }

        public Criteria andCWeidu3208NotBetween(Double value1, Double value2) {
            addCriterion("C_WEIDU_3208 not between", value1, value2, "cWeidu3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208IsNull() {
            addCriterion("C_FLOW_TEMP_TYPE_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208IsNotNull() {
            addCriterion("C_FLOW_TEMP_TYPE_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208EqualTo(String value) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 =", value, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208NotEqualTo(String value) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 <>", value, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208GreaterThan(String value) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 >", value, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 >=", value, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208LessThan(String value) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 <", value, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208LessThanOrEqualTo(String value) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 <=", value, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208Like(String value) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 like", value, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208NotLike(String value) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 not like", value, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208In(List<String> values) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 in", values, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208NotIn(List<String> values) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 not in", values, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208Between(String value1, String value2) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 between", value1, value2, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCFlowTempType3208NotBetween(String value1, String value2) {
            addCriterion("C_FLOW_TEMP_TYPE_3208 not between", value1, value2, "cFlowTempType3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208IsNull() {
            addCriterion("C_SEA_AREA_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208IsNotNull() {
            addCriterion("C_SEA_AREA_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208EqualTo(String value) {
            addCriterion("C_SEA_AREA_3208 =", value, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208NotEqualTo(String value) {
            addCriterion("C_SEA_AREA_3208 <>", value, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208GreaterThan(String value) {
            addCriterion("C_SEA_AREA_3208 >", value, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_SEA_AREA_3208 >=", value, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208LessThan(String value) {
            addCriterion("C_SEA_AREA_3208 <", value, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208LessThanOrEqualTo(String value) {
            addCriterion("C_SEA_AREA_3208 <=", value, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208Like(String value) {
            addCriterion("C_SEA_AREA_3208 like", value, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208NotLike(String value) {
            addCriterion("C_SEA_AREA_3208 not like", value, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208In(List<String> values) {
            addCriterion("C_SEA_AREA_3208 in", values, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208NotIn(List<String> values) {
            addCriterion("C_SEA_AREA_3208 not in", values, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208Between(String value1, String value2) {
            addCriterion("C_SEA_AREA_3208 between", value1, value2, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andCSeaArea3208NotBetween(String value1, String value2) {
            addCriterion("C_SEA_AREA_3208 not between", value1, value2, "cSeaArea3208");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdIsNull() {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID is null");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdIsNotNull() {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdEqualTo(String value) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID =", value, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdNotEqualTo(String value) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID <>", value, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdGreaterThan(String value) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID >", value, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdGreaterThanOrEqualTo(String value) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID >=", value, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdLessThan(String value) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID <", value, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdLessThanOrEqualTo(String value) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID <=", value, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdLike(String value) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID like", value, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdNotLike(String value) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID not like", value, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdIn(List<String> values) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID in", values, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdNotIn(List<String> values) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID not in", values, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdBetween(String value1, String value2) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID between", value1, value2, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andTDivingPlanTable480IdNotBetween(String value1, String value2) {
            addCriterion("T_DIVING__PLAN_TABLE_480_ID not between", value1, value2, "tDivingPlanTable480Id");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208IsNull() {
            addCriterion("C_POSITION_TIME_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208IsNotNull() {
            addCriterion("C_POSITION_TIME_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208EqualTo(String value) {
            addCriterion("C_POSITION_TIME_3208 =", value, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208NotEqualTo(String value) {
            addCriterion("C_POSITION_TIME_3208 <>", value, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208GreaterThan(String value) {
            addCriterion("C_POSITION_TIME_3208 >", value, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_POSITION_TIME_3208 >=", value, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208LessThan(String value) {
            addCriterion("C_POSITION_TIME_3208 <", value, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208LessThanOrEqualTo(String value) {
            addCriterion("C_POSITION_TIME_3208 <=", value, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208Like(String value) {
            addCriterion("C_POSITION_TIME_3208 like", value, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208NotLike(String value) {
            addCriterion("C_POSITION_TIME_3208 not like", value, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208In(List<String> values) {
            addCriterion("C_POSITION_TIME_3208 in", values, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208NotIn(List<String> values) {
            addCriterion("C_POSITION_TIME_3208 not in", values, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208Between(String value1, String value2) {
            addCriterion("C_POSITION_TIME_3208 between", value1, value2, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCPositionTime3208NotBetween(String value1, String value2) {
            addCriterion("C_POSITION_TIME_3208 not between", value1, value2, "cPositionTime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208IsNull() {
            addCriterion("C_FLOAT_TO_WTIME_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208IsNotNull() {
            addCriterion("C_FLOAT_TO_WTIME_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208EqualTo(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3208 =", value, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208NotEqualTo(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3208 <>", value, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208GreaterThan(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3208 >", value, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3208 >=", value, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208LessThan(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3208 <", value, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208LessThanOrEqualTo(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3208 <=", value, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208Like(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3208 like", value, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208NotLike(String value) {
            addCriterion("C_FLOAT_TO_WTIME_3208 not like", value, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208In(List<String> values) {
            addCriterion("C_FLOAT_TO_WTIME_3208 in", values, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208NotIn(List<String> values) {
            addCriterion("C_FLOAT_TO_WTIME_3208 not in", values, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208Between(String value1, String value2) {
            addCriterion("C_FLOAT_TO_WTIME_3208 between", value1, value2, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCFloatToWtime3208NotBetween(String value1, String value2) {
            addCriterion("C_FLOAT_TO_WTIME_3208 not between", value1, value2, "cFloatToWtime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208IsNull() {
            addCriterion("C_PLAN_THROW_TIME_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208IsNotNull() {
            addCriterion("C_PLAN_THROW_TIME_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208EqualTo(String value) {
            addCriterion("C_PLAN_THROW_TIME_3208 =", value, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208NotEqualTo(String value) {
            addCriterion("C_PLAN_THROW_TIME_3208 <>", value, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208GreaterThan(String value) {
            addCriterion("C_PLAN_THROW_TIME_3208 >", value, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_PLAN_THROW_TIME_3208 >=", value, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208LessThan(String value) {
            addCriterion("C_PLAN_THROW_TIME_3208 <", value, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208LessThanOrEqualTo(String value) {
            addCriterion("C_PLAN_THROW_TIME_3208 <=", value, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208Like(String value) {
            addCriterion("C_PLAN_THROW_TIME_3208 like", value, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208NotLike(String value) {
            addCriterion("C_PLAN_THROW_TIME_3208 not like", value, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208In(List<String> values) {
            addCriterion("C_PLAN_THROW_TIME_3208 in", values, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208NotIn(List<String> values) {
            addCriterion("C_PLAN_THROW_TIME_3208 not in", values, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208Between(String value1, String value2) {
            addCriterion("C_PLAN_THROW_TIME_3208 between", value1, value2, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCPlanThrowTime3208NotBetween(String value1, String value2) {
            addCriterion("C_PLAN_THROW_TIME_3208 not between", value1, value2, "cPlanThrowTime3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208IsNull() {
            addCriterion("C_WATER_HOURS_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208IsNotNull() {
            addCriterion("C_WATER_HOURS_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208EqualTo(String value) {
            addCriterion("C_WATER_HOURS_3208 =", value, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208NotEqualTo(String value) {
            addCriterion("C_WATER_HOURS_3208 <>", value, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208GreaterThan(String value) {
            addCriterion("C_WATER_HOURS_3208 >", value, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_WATER_HOURS_3208 >=", value, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208LessThan(String value) {
            addCriterion("C_WATER_HOURS_3208 <", value, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208LessThanOrEqualTo(String value) {
            addCriterion("C_WATER_HOURS_3208 <=", value, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208Like(String value) {
            addCriterion("C_WATER_HOURS_3208 like", value, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208NotLike(String value) {
            addCriterion("C_WATER_HOURS_3208 not like", value, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208In(List<String> values) {
            addCriterion("C_WATER_HOURS_3208 in", values, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208NotIn(List<String> values) {
            addCriterion("C_WATER_HOURS_3208 not in", values, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208Between(String value1, String value2) {
            addCriterion("C_WATER_HOURS_3208 between", value1, value2, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCWaterHours3208NotBetween(String value1, String value2) {
            addCriterion("C_WATER_HOURS_3208 not between", value1, value2, "cWaterHours3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208IsNull() {
            addCriterion("C_TASK_TYPE_3208 is null");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208IsNotNull() {
            addCriterion("C_TASK_TYPE_3208 is not null");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208EqualTo(String value) {
            addCriterion("C_TASK_TYPE_3208 =", value, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208NotEqualTo(String value) {
            addCriterion("C_TASK_TYPE_3208 <>", value, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208GreaterThan(String value) {
            addCriterion("C_TASK_TYPE_3208 >", value, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208GreaterThanOrEqualTo(String value) {
            addCriterion("C_TASK_TYPE_3208 >=", value, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208LessThan(String value) {
            addCriterion("C_TASK_TYPE_3208 <", value, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208LessThanOrEqualTo(String value) {
            addCriterion("C_TASK_TYPE_3208 <=", value, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208Like(String value) {
            addCriterion("C_TASK_TYPE_3208 like", value, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208NotLike(String value) {
            addCriterion("C_TASK_TYPE_3208 not like", value, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208In(List<String> values) {
            addCriterion("C_TASK_TYPE_3208 in", values, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208NotIn(List<String> values) {
            addCriterion("C_TASK_TYPE_3208 not in", values, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208Between(String value1, String value2) {
            addCriterion("C_TASK_TYPE_3208 between", value1, value2, "cTaskType3208");
            return (Criteria) this;
        }

        public Criteria andCTaskType3208NotBetween(String value1, String value2) {
            addCriterion("C_TASK_TYPE_3208 not between", value1, value2, "cTaskType3208");
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