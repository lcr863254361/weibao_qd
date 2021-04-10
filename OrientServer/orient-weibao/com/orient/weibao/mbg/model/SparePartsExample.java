package com.orient.weibao.mbg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SparePartsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SparePartsExample() {
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

        public Criteria andCKeyPart3209IsNull() {
            addCriterion("C_KEY_PART_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209IsNotNull() {
            addCriterion("C_KEY_PART_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209EqualTo(String value) {
            addCriterion("C_KEY_PART_3209 =", value, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209NotEqualTo(String value) {
            addCriterion("C_KEY_PART_3209 <>", value, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209GreaterThan(String value) {
            addCriterion("C_KEY_PART_3209 >", value, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_KEY_PART_3209 >=", value, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209LessThan(String value) {
            addCriterion("C_KEY_PART_3209 <", value, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209LessThanOrEqualTo(String value) {
            addCriterion("C_KEY_PART_3209 <=", value, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209Like(String value) {
            addCriterion("C_KEY_PART_3209 like", value, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209NotLike(String value) {
            addCriterion("C_KEY_PART_3209 not like", value, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209In(List<String> values) {
            addCriterion("C_KEY_PART_3209 in", values, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209NotIn(List<String> values) {
            addCriterion("C_KEY_PART_3209 not in", values, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209Between(String value1, String value2) {
            addCriterion("C_KEY_PART_3209 between", value1, value2, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCKeyPart3209NotBetween(String value1, String value2) {
            addCriterion("C_KEY_PART_3209 not between", value1, value2, "cKeyPart3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209IsNull() {
            addCriterion("C_GUIGE_MODE_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209IsNotNull() {
            addCriterion("C_GUIGE_MODE_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209EqualTo(String value) {
            addCriterion("C_GUIGE_MODE_3209 =", value, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209NotEqualTo(String value) {
            addCriterion("C_GUIGE_MODE_3209 <>", value, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209GreaterThan(String value) {
            addCriterion("C_GUIGE_MODE_3209 >", value, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_GUIGE_MODE_3209 >=", value, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209LessThan(String value) {
            addCriterion("C_GUIGE_MODE_3209 <", value, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209LessThanOrEqualTo(String value) {
            addCriterion("C_GUIGE_MODE_3209 <=", value, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209Like(String value) {
            addCriterion("C_GUIGE_MODE_3209 like", value, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209NotLike(String value) {
            addCriterion("C_GUIGE_MODE_3209 not like", value, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209In(List<String> values) {
            addCriterion("C_GUIGE_MODE_3209 in", values, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209NotIn(List<String> values) {
            addCriterion("C_GUIGE_MODE_3209 not in", values, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209Between(String value1, String value2) {
            addCriterion("C_GUIGE_MODE_3209 between", value1, value2, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCGuigeMode3209NotBetween(String value1, String value2) {
            addCriterion("C_GUIGE_MODE_3209 not between", value1, value2, "cGuigeMode3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209IsNull() {
            addCriterion("C_NUMBER_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCNumber3209IsNotNull() {
            addCriterion("C_NUMBER_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCNumber3209EqualTo(String value) {
            addCriterion("C_NUMBER_3209 =", value, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209NotEqualTo(String value) {
            addCriterion("C_NUMBER_3209 <>", value, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209GreaterThan(String value) {
            addCriterion("C_NUMBER_3209 >", value, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_NUMBER_3209 >=", value, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209LessThan(String value) {
            addCriterion("C_NUMBER_3209 <", value, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209LessThanOrEqualTo(String value) {
            addCriterion("C_NUMBER_3209 <=", value, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209Like(String value) {
            addCriterion("C_NUMBER_3209 like", value, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209NotLike(String value) {
            addCriterion("C_NUMBER_3209 not like", value, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209In(List<String> values) {
            addCriterion("C_NUMBER_3209 in", values, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209NotIn(List<String> values) {
            addCriterion("C_NUMBER_3209 not in", values, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209Between(String value1, String value2) {
            addCriterion("C_NUMBER_3209 between", value1, value2, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCNumber3209NotBetween(String value1, String value2) {
            addCriterion("C_NUMBER_3209 not between", value1, value2, "cNumber3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209IsNull() {
            addCriterion("C_DUTY_PERSON_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209IsNotNull() {
            addCriterion("C_DUTY_PERSON_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209EqualTo(String value) {
            addCriterion("C_DUTY_PERSON_3209 =", value, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209NotEqualTo(String value) {
            addCriterion("C_DUTY_PERSON_3209 <>", value, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209GreaterThan(String value) {
            addCriterion("C_DUTY_PERSON_3209 >", value, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_DUTY_PERSON_3209 >=", value, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209LessThan(String value) {
            addCriterion("C_DUTY_PERSON_3209 <", value, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209LessThanOrEqualTo(String value) {
            addCriterion("C_DUTY_PERSON_3209 <=", value, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209Like(String value) {
            addCriterion("C_DUTY_PERSON_3209 like", value, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209NotLike(String value) {
            addCriterion("C_DUTY_PERSON_3209 not like", value, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209In(List<String> values) {
            addCriterion("C_DUTY_PERSON_3209 in", values, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209NotIn(List<String> values) {
            addCriterion("C_DUTY_PERSON_3209 not in", values, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209Between(String value1, String value2) {
            addCriterion("C_DUTY_PERSON_3209 between", value1, value2, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDutyPerson3209NotBetween(String value1, String value2) {
            addCriterion("C_DUTY_PERSON_3209 not between", value1, value2, "cDutyPerson3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209IsNull() {
            addCriterion("C_DEVICE_NAME_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209IsNotNull() {
            addCriterion("C_DEVICE_NAME_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209EqualTo(String value) {
            addCriterion("C_DEVICE_NAME_3209 =", value, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209NotEqualTo(String value) {
            addCriterion("C_DEVICE_NAME_3209 <>", value, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209GreaterThan(String value) {
            addCriterion("C_DEVICE_NAME_3209 >", value, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_DEVICE_NAME_3209 >=", value, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209LessThan(String value) {
            addCriterion("C_DEVICE_NAME_3209 <", value, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209LessThanOrEqualTo(String value) {
            addCriterion("C_DEVICE_NAME_3209 <=", value, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209Like(String value) {
            addCriterion("C_DEVICE_NAME_3209 like", value, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209NotLike(String value) {
            addCriterion("C_DEVICE_NAME_3209 not like", value, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209In(List<String> values) {
            addCriterion("C_DEVICE_NAME_3209 in", values, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209NotIn(List<String> values) {
            addCriterion("C_DEVICE_NAME_3209 not in", values, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209Between(String value1, String value2) {
            addCriterion("C_DEVICE_NAME_3209 between", value1, value2, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceName3209NotBetween(String value1, String value2) {
            addCriterion("C_DEVICE_NAME_3209 not between", value1, value2, "cDeviceName3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209IsNull() {
            addCriterion("C_FUNCTION_JIANJIE_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209IsNotNull() {
            addCriterion("C_FUNCTION_JIANJIE_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209EqualTo(String value) {
            addCriterion("C_FUNCTION_JIANJIE_3209 =", value, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209NotEqualTo(String value) {
            addCriterion("C_FUNCTION_JIANJIE_3209 <>", value, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209GreaterThan(String value) {
            addCriterion("C_FUNCTION_JIANJIE_3209 >", value, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_FUNCTION_JIANJIE_3209 >=", value, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209LessThan(String value) {
            addCriterion("C_FUNCTION_JIANJIE_3209 <", value, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209LessThanOrEqualTo(String value) {
            addCriterion("C_FUNCTION_JIANJIE_3209 <=", value, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209Like(String value) {
            addCriterion("C_FUNCTION_JIANJIE_3209 like", value, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209NotLike(String value) {
            addCriterion("C_FUNCTION_JIANJIE_3209 not like", value, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209In(List<String> values) {
            addCriterion("C_FUNCTION_JIANJIE_3209 in", values, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209NotIn(List<String> values) {
            addCriterion("C_FUNCTION_JIANJIE_3209 not in", values, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209Between(String value1, String value2) {
            addCriterion("C_FUNCTION_JIANJIE_3209 between", value1, value2, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andCFunctionJianjie3209NotBetween(String value1, String value2) {
            addCriterion("C_FUNCTION_JIANJIE_3209 not between", value1, value2, "cFunctionJianjie3209");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdIsNull() {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID is null");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdIsNotNull() {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdEqualTo(String value) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID =", value, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdNotEqualTo(String value) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID <>", value, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdGreaterThan(String value) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID >", value, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdGreaterThanOrEqualTo(String value) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID >=", value, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdLessThan(String value) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID <", value, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdLessThanOrEqualTo(String value) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID <=", value, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdLike(String value) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID like", value, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdNotLike(String value) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID not like", value, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdIn(List<String> values) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID in", values, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdNotIn(List<String> values) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID not in", values, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdBetween(String value1, String value2) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID between", value1, value2, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andTProductStructure480IdNotBetween(String value1, String value2) {
            addCriterion("T_PRODUCT_STRUCTURE_480_ID not between", value1, value2, "tProductStructure480Id");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209IsNull() {
            addCriterion("C_DEVICE_CODE_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209IsNotNull() {
            addCriterion("C_DEVICE_CODE_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209EqualTo(String value) {
            addCriterion("C_DEVICE_CODE_3209 =", value, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209NotEqualTo(String value) {
            addCriterion("C_DEVICE_CODE_3209 <>", value, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209GreaterThan(String value) {
            addCriterion("C_DEVICE_CODE_3209 >", value, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_DEVICE_CODE_3209 >=", value, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209LessThan(String value) {
            addCriterion("C_DEVICE_CODE_3209 <", value, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209LessThanOrEqualTo(String value) {
            addCriterion("C_DEVICE_CODE_3209 <=", value, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209Like(String value) {
            addCriterion("C_DEVICE_CODE_3209 like", value, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209NotLike(String value) {
            addCriterion("C_DEVICE_CODE_3209 not like", value, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209In(List<String> values) {
            addCriterion("C_DEVICE_CODE_3209 in", values, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209NotIn(List<String> values) {
            addCriterion("C_DEVICE_CODE_3209 not in", values, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209Between(String value1, String value2) {
            addCriterion("C_DEVICE_CODE_3209 between", value1, value2, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCDeviceCode3209NotBetween(String value1, String value2) {
            addCriterion("C_DEVICE_CODE_3209 not between", value1, value2, "cDeviceCode3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209IsNull() {
            addCriterion("C_NOTE_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCNote3209IsNotNull() {
            addCriterion("C_NOTE_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCNote3209EqualTo(String value) {
            addCriterion("C_NOTE_3209 =", value, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209NotEqualTo(String value) {
            addCriterion("C_NOTE_3209 <>", value, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209GreaterThan(String value) {
            addCriterion("C_NOTE_3209 >", value, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_NOTE_3209 >=", value, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209LessThan(String value) {
            addCriterion("C_NOTE_3209 <", value, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209LessThanOrEqualTo(String value) {
            addCriterion("C_NOTE_3209 <=", value, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209Like(String value) {
            addCriterion("C_NOTE_3209 like", value, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209NotLike(String value) {
            addCriterion("C_NOTE_3209 not like", value, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209In(List<String> values) {
            addCriterion("C_NOTE_3209 in", values, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209NotIn(List<String> values) {
            addCriterion("C_NOTE_3209 not in", values, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209Between(String value1, String value2) {
            addCriterion("C_NOTE_3209 between", value1, value2, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCNote3209NotBetween(String value1, String value2) {
            addCriterion("C_NOTE_3209 not between", value1, value2, "cNote3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209IsNull() {
            addCriterion("C_MODEL_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCModel3209IsNotNull() {
            addCriterion("C_MODEL_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCModel3209EqualTo(String value) {
            addCriterion("C_MODEL_3209 =", value, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209NotEqualTo(String value) {
            addCriterion("C_MODEL_3209 <>", value, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209GreaterThan(String value) {
            addCriterion("C_MODEL_3209 >", value, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_MODEL_3209 >=", value, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209LessThan(String value) {
            addCriterion("C_MODEL_3209 <", value, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209LessThanOrEqualTo(String value) {
            addCriterion("C_MODEL_3209 <=", value, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209Like(String value) {
            addCriterion("C_MODEL_3209 like", value, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209NotLike(String value) {
            addCriterion("C_MODEL_3209 not like", value, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209In(List<String> values) {
            addCriterion("C_MODEL_3209 in", values, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209NotIn(List<String> values) {
            addCriterion("C_MODEL_3209 not in", values, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209Between(String value1, String value2) {
            addCriterion("C_MODEL_3209 between", value1, value2, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCModel3209NotBetween(String value1, String value2) {
            addCriterion("C_MODEL_3209 not between", value1, value2, "cModel3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209IsNull() {
            addCriterion("C_VERSION_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCVersion3209IsNotNull() {
            addCriterion("C_VERSION_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCVersion3209EqualTo(String value) {
            addCriterion("C_VERSION_3209 =", value, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209NotEqualTo(String value) {
            addCriterion("C_VERSION_3209 <>", value, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209GreaterThan(String value) {
            addCriterion("C_VERSION_3209 >", value, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_VERSION_3209 >=", value, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209LessThan(String value) {
            addCriterion("C_VERSION_3209 <", value, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209LessThanOrEqualTo(String value) {
            addCriterion("C_VERSION_3209 <=", value, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209Like(String value) {
            addCriterion("C_VERSION_3209 like", value, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209NotLike(String value) {
            addCriterion("C_VERSION_3209 not like", value, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209In(List<String> values) {
            addCriterion("C_VERSION_3209 in", values, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209NotIn(List<String> values) {
            addCriterion("C_VERSION_3209 not in", values, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209Between(String value1, String value2) {
            addCriterion("C_VERSION_3209 between", value1, value2, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCVersion3209NotBetween(String value1, String value2) {
            addCriterion("C_VERSION_3209 not between", value1, value2, "cVersion3209");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209IsNull() {
            addCriterion("C_CARRY_COUNT_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209IsNotNull() {
            addCriterion("C_CARRY_COUNT_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209EqualTo(Long value) {
            addCriterion("C_CARRY_COUNT_3209 =", value, "cCarryCount3209");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209NotEqualTo(Long value) {
            addCriterion("C_CARRY_COUNT_3209 <>", value, "cCarryCount3209");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209GreaterThan(Long value) {
            addCriterion("C_CARRY_COUNT_3209 >", value, "cCarryCount3209");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209GreaterThanOrEqualTo(Long value) {
            addCriterion("C_CARRY_COUNT_3209 >=", value, "cCarryCount3209");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209LessThan(Long value) {
            addCriterion("C_CARRY_COUNT_3209 <", value, "cCarryCount3209");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209LessThanOrEqualTo(Long value) {
            addCriterion("C_CARRY_COUNT_3209 <=", value, "cCarryCount3209");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209In(List<Long> values) {
            addCriterion("C_CARRY_COUNT_3209 in", values, "cCarryCount3209");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209NotIn(List<Long> values) {
            addCriterion("C_CARRY_COUNT_3209 not in", values, "cCarryCount3209");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209Between(Long value1, Long value2) {
            addCriterion("C_CARRY_COUNT_3209 between", value1, value2, "cCarryCount3209");
            return (Criteria) this;
        }

        public Criteria andCCarryCount3209NotBetween(Long value1, Long value2) {
            addCriterion("C_CARRY_COUNT_3209 not between", value1, value2, "cCarryCount3209");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209IsNull() {
            addCriterion("C_AIR_WEIGHT_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209IsNotNull() {
            addCriterion("C_AIR_WEIGHT_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209EqualTo(Double value) {
            addCriterion("C_AIR_WEIGHT_3209 =", value, "cAirWeight3209");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209NotEqualTo(Double value) {
            addCriterion("C_AIR_WEIGHT_3209 <>", value, "cAirWeight3209");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209GreaterThan(Double value) {
            addCriterion("C_AIR_WEIGHT_3209 >", value, "cAirWeight3209");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209GreaterThanOrEqualTo(Double value) {
            addCriterion("C_AIR_WEIGHT_3209 >=", value, "cAirWeight3209");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209LessThan(Double value) {
            addCriterion("C_AIR_WEIGHT_3209 <", value, "cAirWeight3209");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209LessThanOrEqualTo(Double value) {
            addCriterion("C_AIR_WEIGHT_3209 <=", value, "cAirWeight3209");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209In(List<Double> values) {
            addCriterion("C_AIR_WEIGHT_3209 in", values, "cAirWeight3209");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209NotIn(List<Double> values) {
            addCriterion("C_AIR_WEIGHT_3209 not in", values, "cAirWeight3209");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209Between(Double value1, Double value2) {
            addCriterion("C_AIR_WEIGHT_3209 between", value1, value2, "cAirWeight3209");
            return (Criteria) this;
        }

        public Criteria andCAirWeight3209NotBetween(Double value1, Double value2) {
            addCriterion("C_AIR_WEIGHT_3209 not between", value1, value2, "cAirWeight3209");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209IsNull() {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209IsNotNull() {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209EqualTo(Double value) {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 =", value, "cFreshWaterWeight3209");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209NotEqualTo(Double value) {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 <>", value, "cFreshWaterWeight3209");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209GreaterThan(Double value) {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 >", value, "cFreshWaterWeight3209");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209GreaterThanOrEqualTo(Double value) {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 >=", value, "cFreshWaterWeight3209");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209LessThan(Double value) {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 <", value, "cFreshWaterWeight3209");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209LessThanOrEqualTo(Double value) {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 <=", value, "cFreshWaterWeight3209");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209In(List<Double> values) {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 in", values, "cFreshWaterWeight3209");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209NotIn(List<Double> values) {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 not in", values, "cFreshWaterWeight3209");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209Between(Double value1, Double value2) {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 between", value1, value2, "cFreshWaterWeight3209");
            return (Criteria) this;
        }

        public Criteria andCFreshWaterWeight3209NotBetween(Double value1, Double value2) {
            addCriterion("C_FRESH_WATER_WEIGHT_3209 not between", value1, value2, "cFreshWaterWeight3209");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209IsNull() {
            addCriterion("C_DEWATER_VOLUME_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209IsNotNull() {
            addCriterion("C_DEWATER_VOLUME_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209EqualTo(Double value) {
            addCriterion("C_DEWATER_VOLUME_3209 =", value, "cDewaterVolume3209");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209NotEqualTo(Double value) {
            addCriterion("C_DEWATER_VOLUME_3209 <>", value, "cDewaterVolume3209");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209GreaterThan(Double value) {
            addCriterion("C_DEWATER_VOLUME_3209 >", value, "cDewaterVolume3209");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209GreaterThanOrEqualTo(Double value) {
            addCriterion("C_DEWATER_VOLUME_3209 >=", value, "cDewaterVolume3209");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209LessThan(Double value) {
            addCriterion("C_DEWATER_VOLUME_3209 <", value, "cDewaterVolume3209");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209LessThanOrEqualTo(Double value) {
            addCriterion("C_DEWATER_VOLUME_3209 <=", value, "cDewaterVolume3209");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209In(List<Double> values) {
            addCriterion("C_DEWATER_VOLUME_3209 in", values, "cDewaterVolume3209");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209NotIn(List<Double> values) {
            addCriterion("C_DEWATER_VOLUME_3209 not in", values, "cDewaterVolume3209");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209Between(Double value1, Double value2) {
            addCriterion("C_DEWATER_VOLUME_3209 between", value1, value2, "cDewaterVolume3209");
            return (Criteria) this;
        }

        public Criteria andCDewaterVolume3209NotBetween(Double value1, Double value2) {
            addCriterion("C_DEWATER_VOLUME_3209 not between", value1, value2, "cDewaterVolume3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209IsNull() {
            addCriterion("C_IS_CARRY_TYPE_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209IsNotNull() {
            addCriterion("C_IS_CARRY_TYPE_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209EqualTo(String value) {
            addCriterion("C_IS_CARRY_TYPE_3209 =", value, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209NotEqualTo(String value) {
            addCriterion("C_IS_CARRY_TYPE_3209 <>", value, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209GreaterThan(String value) {
            addCriterion("C_IS_CARRY_TYPE_3209 >", value, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_IS_CARRY_TYPE_3209 >=", value, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209LessThan(String value) {
            addCriterion("C_IS_CARRY_TYPE_3209 <", value, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209LessThanOrEqualTo(String value) {
            addCriterion("C_IS_CARRY_TYPE_3209 <=", value, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209Like(String value) {
            addCriterion("C_IS_CARRY_TYPE_3209 like", value, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209NotLike(String value) {
            addCriterion("C_IS_CARRY_TYPE_3209 not like", value, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209In(List<String> values) {
            addCriterion("C_IS_CARRY_TYPE_3209 in", values, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209NotIn(List<String> values) {
            addCriterion("C_IS_CARRY_TYPE_3209 not in", values, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209Between(String value1, String value2) {
            addCriterion("C_IS_CARRY_TYPE_3209 between", value1, value2, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCIsCarryType3209NotBetween(String value1, String value2) {
            addCriterion("C_IS_CARRY_TYPE_3209 not between", value1, value2, "cIsCarryType3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209IsNull() {
            addCriterion("C_CABIN_INOROUT_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209IsNotNull() {
            addCriterion("C_CABIN_INOROUT_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209EqualTo(String value) {
            addCriterion("C_CABIN_INOROUT_3209 =", value, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209NotEqualTo(String value) {
            addCriterion("C_CABIN_INOROUT_3209 <>", value, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209GreaterThan(String value) {
            addCriterion("C_CABIN_INOROUT_3209 >", value, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209GreaterThanOrEqualTo(String value) {
            addCriterion("C_CABIN_INOROUT_3209 >=", value, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209LessThan(String value) {
            addCriterion("C_CABIN_INOROUT_3209 <", value, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209LessThanOrEqualTo(String value) {
            addCriterion("C_CABIN_INOROUT_3209 <=", value, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209Like(String value) {
            addCriterion("C_CABIN_INOROUT_3209 like", value, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209NotLike(String value) {
            addCriterion("C_CABIN_INOROUT_3209 not like", value, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209In(List<String> values) {
            addCriterion("C_CABIN_INOROUT_3209 in", values, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209NotIn(List<String> values) {
            addCriterion("C_CABIN_INOROUT_3209 not in", values, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209Between(String value1, String value2) {
            addCriterion("C_CABIN_INOROUT_3209 between", value1, value2, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCCabinInorout3209NotBetween(String value1, String value2) {
            addCriterion("C_CABIN_INOROUT_3209 not between", value1, value2, "cCabinInorout3209");
            return (Criteria) this;
        }

        public Criteria andCWidth3209IsNull() {
            addCriterion("C_WIDTH_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCWidth3209IsNotNull() {
            addCriterion("C_WIDTH_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCWidth3209EqualTo(Double value) {
            addCriterion("C_WIDTH_3209 =", value, "cWidth3209");
            return (Criteria) this;
        }

        public Criteria andCWidth3209NotEqualTo(Double value) {
            addCriterion("C_WIDTH_3209 <>", value, "cWidth3209");
            return (Criteria) this;
        }

        public Criteria andCWidth3209GreaterThan(Double value) {
            addCriterion("C_WIDTH_3209 >", value, "cWidth3209");
            return (Criteria) this;
        }

        public Criteria andCWidth3209GreaterThanOrEqualTo(Double value) {
            addCriterion("C_WIDTH_3209 >=", value, "cWidth3209");
            return (Criteria) this;
        }

        public Criteria andCWidth3209LessThan(Double value) {
            addCriterion("C_WIDTH_3209 <", value, "cWidth3209");
            return (Criteria) this;
        }

        public Criteria andCWidth3209LessThanOrEqualTo(Double value) {
            addCriterion("C_WIDTH_3209 <=", value, "cWidth3209");
            return (Criteria) this;
        }

        public Criteria andCWidth3209In(List<Double> values) {
            addCriterion("C_WIDTH_3209 in", values, "cWidth3209");
            return (Criteria) this;
        }

        public Criteria andCWidth3209NotIn(List<Double> values) {
            addCriterion("C_WIDTH_3209 not in", values, "cWidth3209");
            return (Criteria) this;
        }

        public Criteria andCWidth3209Between(Double value1, Double value2) {
            addCriterion("C_WIDTH_3209 between", value1, value2, "cWidth3209");
            return (Criteria) this;
        }

        public Criteria andCWidth3209NotBetween(Double value1, Double value2) {
            addCriterion("C_WIDTH_3209 not between", value1, value2, "cWidth3209");
            return (Criteria) this;
        }

        public Criteria andCLength3209IsNull() {
            addCriterion("C_LENGTH_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCLength3209IsNotNull() {
            addCriterion("C_LENGTH_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCLength3209EqualTo(Double value) {
            addCriterion("C_LENGTH_3209 =", value, "cLength3209");
            return (Criteria) this;
        }

        public Criteria andCLength3209NotEqualTo(Double value) {
            addCriterion("C_LENGTH_3209 <>", value, "cLength3209");
            return (Criteria) this;
        }

        public Criteria andCLength3209GreaterThan(Double value) {
            addCriterion("C_LENGTH_3209 >", value, "cLength3209");
            return (Criteria) this;
        }

        public Criteria andCLength3209GreaterThanOrEqualTo(Double value) {
            addCriterion("C_LENGTH_3209 >=", value, "cLength3209");
            return (Criteria) this;
        }

        public Criteria andCLength3209LessThan(Double value) {
            addCriterion("C_LENGTH_3209 <", value, "cLength3209");
            return (Criteria) this;
        }

        public Criteria andCLength3209LessThanOrEqualTo(Double value) {
            addCriterion("C_LENGTH_3209 <=", value, "cLength3209");
            return (Criteria) this;
        }

        public Criteria andCLength3209In(List<Double> values) {
            addCriterion("C_LENGTH_3209 in", values, "cLength3209");
            return (Criteria) this;
        }

        public Criteria andCLength3209NotIn(List<Double> values) {
            addCriterion("C_LENGTH_3209 not in", values, "cLength3209");
            return (Criteria) this;
        }

        public Criteria andCLength3209Between(Double value1, Double value2) {
            addCriterion("C_LENGTH_3209 between", value1, value2, "cLength3209");
            return (Criteria) this;
        }

        public Criteria andCLength3209NotBetween(Double value1, Double value2) {
            addCriterion("C_LENGTH_3209 not between", value1, value2, "cLength3209");
            return (Criteria) this;
        }

        public Criteria andCHeight3209IsNull() {
            addCriterion("C_HEIGHT_3209 is null");
            return (Criteria) this;
        }

        public Criteria andCHeight3209IsNotNull() {
            addCriterion("C_HEIGHT_3209 is not null");
            return (Criteria) this;
        }

        public Criteria andCHeight3209EqualTo(Double value) {
            addCriterion("C_HEIGHT_3209 =", value, "cHeight3209");
            return (Criteria) this;
        }

        public Criteria andCHeight3209NotEqualTo(Double value) {
            addCriterion("C_HEIGHT_3209 <>", value, "cHeight3209");
            return (Criteria) this;
        }

        public Criteria andCHeight3209GreaterThan(Double value) {
            addCriterion("C_HEIGHT_3209 >", value, "cHeight3209");
            return (Criteria) this;
        }

        public Criteria andCHeight3209GreaterThanOrEqualTo(Double value) {
            addCriterion("C_HEIGHT_3209 >=", value, "cHeight3209");
            return (Criteria) this;
        }

        public Criteria andCHeight3209LessThan(Double value) {
            addCriterion("C_HEIGHT_3209 <", value, "cHeight3209");
            return (Criteria) this;
        }

        public Criteria andCHeight3209LessThanOrEqualTo(Double value) {
            addCriterion("C_HEIGHT_3209 <=", value, "cHeight3209");
            return (Criteria) this;
        }

        public Criteria andCHeight3209In(List<Double> values) {
            addCriterion("C_HEIGHT_3209 in", values, "cHeight3209");
            return (Criteria) this;
        }

        public Criteria andCHeight3209NotIn(List<Double> values) {
            addCriterion("C_HEIGHT_3209 not in", values, "cHeight3209");
            return (Criteria) this;
        }

        public Criteria andCHeight3209Between(Double value1, Double value2) {
            addCriterion("C_HEIGHT_3209 between", value1, value2, "cHeight3209");
            return (Criteria) this;
        }

        public Criteria andCHeight3209NotBetween(Double value1, Double value2) {
            addCriterion("C_HEIGHT_3209 not between", value1, value2, "cHeight3209");
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