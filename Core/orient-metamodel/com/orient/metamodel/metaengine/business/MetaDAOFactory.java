package com.orient.metamodel.metaengine.business;

import com.orient.metamodel.metaengine.dao.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * DAO类的工厂,方便获取持久化的DAO
 *
 * @author mengbin
 * @Date Feb 10, 2012		10:34:57 AM
 */
public class MetaDAOFactory {

    /**
     * 业务库对象持久化DAO.
     */
    private SchemaDAO schemaDAO;

    /**
     * 关系属性对象持久化DAO.
     */
    private RelationColumnsDAO relationColumnsDAO;

    /**
     * 视图关联数据类对象持久化DAO.
     */
    private ViewRefColumnDAO viewRefColumnDAO;

    /**
     * 视图返回属性对象持久化DAO.
     */
    private ViewResultColumnDAO viewResultColumnDAO;

    /**
     * 表枚举约束对象持久化DAO.
     */
    private TableEnumDAO tableEnumDAO;

    /**
     * 数据约束通用对象持久化DAO.
     */
    private RestrictionDAO restrictionDAO;

    /**
     * 数据类实例对象持久化DAO.
     */
    private TableDAO tableDAO;

    /**
     * 枚举值对象持久化DAO.
     */
    private EnumDAO enumDAO;

    /**
     * 视图排序属性对象持久化DAO.
     */
    private ViewSortColumnDAO viewSortColumnDAO;

    /**
     * 数据类参数关联约束对象持久化DAO.
     */
    private ConsExpressionDAO consExpressionDAO;

    /**
     * 表枚举约束关联数据类对象持久化DAO.
     */
    private RelationTableEnumDAO relationTableEnumDAO;

    /**
     * 通用字段属性（包括普通属性、关系属性、统计属性）对象持久化DAO.
     */
    private ColumnDAO columnDAO;

    /**
     * 通用视图对象持久化DAO.
     */
    private ViewDAO viewDAO;

    /**
     * 数据类复合属性（主键、排序属性、唯一性约束、展现顺序）对象持久化DAO.
     */
    private TableColumnDAO tableColumnDAO;

    /**
     * 统计视图统计参数项对象持久化DAO.
     */
    private ArithViewAttributeDAO arithViewAttributeDAO;

    /**
     * 算法参数定义对象持久化DAO.
     */
    private ArithAttributeDAO arithAttributeDAO;

    /**
     * hibernate持久化模板.
     */
    private HibernateTemplate hibernateTemplate;

    /**
     * jdbc持久化模板.
     */
    private JdbcTemplate jdbcTemplate;

    public SchemaDAO getSchemaDAO() {
        return schemaDAO;
    }

    public void setSchemaDAO(SchemaDAO schemaDAO) {
        this.schemaDAO = schemaDAO;
    }

    public RelationColumnsDAO getRelationColumnsDAO() {
        return relationColumnsDAO;
    }

    public void setRelationColumnsDAO(RelationColumnsDAO relationColumnsDAO) {
        this.relationColumnsDAO = relationColumnsDAO;
    }

    public ViewRefColumnDAO getViewRefColumnDAO() {
        return viewRefColumnDAO;
    }

    public void setViewRefColumnDAO(ViewRefColumnDAO viewRefColumnDAO) {
        this.viewRefColumnDAO = viewRefColumnDAO;
    }

    public ViewResultColumnDAO getViewResultColumnDAO() {
        return viewResultColumnDAO;
    }

    public void setViewResultColumnDAO(ViewResultColumnDAO viewResultColumnDAO) {
        this.viewResultColumnDAO = viewResultColumnDAO;
    }

    public TableEnumDAO getTableEnumDAO() {
        return tableEnumDAO;
    }

    public void setTableEnumDAO(TableEnumDAO tableEnumDAO) {
        this.tableEnumDAO = tableEnumDAO;
    }

    public RestrictionDAO getRestrictionDAO() {
        return restrictionDAO;
    }

    public void setRestrictionDAO(RestrictionDAO restrictionDAO) {
        this.restrictionDAO = restrictionDAO;
    }

    public TableDAO getTableDAO() {
        return tableDAO;
    }

    public void setTableDAO(TableDAO tableDAO) {
        this.tableDAO = tableDAO;
    }

    public EnumDAO getEnumDAO() {
        return enumDAO;
    }

    public void setEnumDAO(EnumDAO enumDAO) {
        this.enumDAO = enumDAO;
    }

    public ViewSortColumnDAO getViewSortColumnDAO() {
        return viewSortColumnDAO;
    }

    public void setViewSortColumnDAO(ViewSortColumnDAO viewSortColumnDAO) {
        this.viewSortColumnDAO = viewSortColumnDAO;
    }

    public ConsExpressionDAO getConsExpressionDAO() {
        return consExpressionDAO;
    }

    public void setConsExpressionDAO(ConsExpressionDAO consExpressionDAO) {
        this.consExpressionDAO = consExpressionDAO;
    }

    public RelationTableEnumDAO getRelationTableEnumDAO() {
        return relationTableEnumDAO;
    }

    public void setRelationTableEnumDAO(RelationTableEnumDAO relationTableEnumDAO) {
        this.relationTableEnumDAO = relationTableEnumDAO;
    }

    public ColumnDAO getColumnDAO() {
        return columnDAO;
    }

    public void setColumnDAO(ColumnDAO columnDAO) {
        this.columnDAO = columnDAO;
    }

    public ViewDAO getViewDAO() {
        return viewDAO;
    }

    public void setViewDAO(ViewDAO viewDAO) {
        this.viewDAO = viewDAO;
    }

    public TableColumnDAO getTableColumnDAO() {
        return tableColumnDAO;
    }

    public void setTableColumnDAO(TableColumnDAO tableColumnDAO) {
        this.tableColumnDAO = tableColumnDAO;
    }

    public ArithViewAttributeDAO getArithViewAttributeDAO() {
        return arithViewAttributeDAO;
    }

    public void setArithViewAttributeDAO(ArithViewAttributeDAO arithViewAttributeDAO) {
        this.arithViewAttributeDAO = arithViewAttributeDAO;
    }

    public ArithAttributeDAO getArithAttributeDAO() {
        return arithAttributeDAO;
    }

    public void setArithAttributeDAO(ArithAttributeDAO arithAttributeDAO) {
        this.arithAttributeDAO = arithAttributeDAO;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

}

