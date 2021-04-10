/*
 * Title: AbstractTbom.java
 * Company: DHC
 * Author: XIUJUN XU
 * Date: Nov 23, 2009 11:06:50 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metadomain.View;
import com.orient.sysmodel.operationinterface.ITbom;

/**
 * The Class AbstractTbom.
 *
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public abstract class AbstractTbom extends com.orient.sysmodel.domain.BaseBean implements Serializable {

    /**
     * The id.
     */
    private String id;

    /**
     * The table.
     */
    //private String table;//节点的数据源--数据类的ID，它和视图只有一个有记录
    private Table table;//节点的数据源--数据类的ID，它和视图只有一个有记录

    /**
     * The view.
     */
    //private String view;//节点的数据源--数据视图的ID，它和数据类只有一个有记录
    private View view;//节点的数据源--数据视图的ID，它和数据类只有一个有记录

    /**
     * The name.
     */
    private String name;//节点的名称

    /**
     * The type.
     */
    private Long type;//节点的数据源类型，0为数据类,1为数据视图,2为统计视图

    /**
     * The description.
     */
    private String description;//对节点的描述

    /**
     * The detail text.
     */
    private String detailText;//详细文字，主要用于用户界面显示

    /**
     * The big img.
     */
    private String bigImg;//TBOM树节点的大图标

    /**
     * The sma img.
     */
    private String smaImg;//TBOM树节点的小图标

    /**
     * The nor img.
     */
    private String norImg;//TBOM树节点的中图标

    /**
     * The order.
     */
    private Long order;//顺序号,用于标记不同类型的TBOM树节点的前后顺序

    /**
     * The is valid.
     */
    private Long isValid;//标记该条记录是否有效，0表示无效，1表示有效

    /**
     * The is root.
     */
    private Long isRoot;//是否是TBOM树的根节点，0表示否，1表示是

    /**
     * The xmlid.
     */
    private String xmlid; //记录节点的XML文件ID，为打开TBOM树提供ID信息

    /**
     * The parenttbom.
     */
    private Tbom parenttbom;//父节点

    /**
     * The schemaid.
     */
    //private String schemaid;//所属数据模型ID
    private Schema schema;//所属数据模型ID

    /**
     * The column.
     */
    //private String column;//动态子节点的字段ID
    private Column column;//动态子节点的字段ID

    /**
     * The column name.
     */
    private String columnName;//子节点名称规则

    /**
     * The child tboms.
     */
    private Set childTboms = new HashSet(0);

    /**
     * The expression.
     */
    private String expression;//过滤表达式（供SQL使用）

    /**
     * The origin expression.
     */
    private String originExpression;//过滤表达式（供TBOM STUDIO使用）
    /**
     * url
     **/
    private String url;//链接地址

    private String showType;

    /**
     * The cwm relation tboms for relation id.
     */
    private Set cwmRelationTbomsForRelationId = new HashSet(0);

    /**
     * The cwm relation tboms for node id.
     */
    private Set cwmRelationTbomsForNodeId = new HashSet(0);

    /**
     * The relation file.
     */
    private Set relationFile = new HashSet(0);

    private Set tbomRoles = new HashSet(0);
    //private Set<DynamicTbom> dynamicTbom=new HashSet<DynamicTbom>();


    private SortedSet<DynamicTbom> dynamicTbom = new TreeSet<DynamicTbom>();//动态子节点信息


    private String schemaid;

    private String templateid;


    public SortedSet<DynamicTbom> getDynamicTbom() {
        return dynamicTbom;
    }

    public void setDynamicTbom(SortedSet<DynamicTbom> dynamicTbom) {
        this.dynamicTbom = dynamicTbom;
    }

    /**
     * Instantiates a new abstract tbom.
     */
    public AbstractTbom() {

    }

    /**
     * Instantiates a new abstract tbom.
     *
     * @param id               the id
     * @param table            the table
     * @param view             the view
     * @param name             the name
     * @param type             the type
     * @param description      the description
     * @param detailText       the detail text
     * @param bigImg           the big img
     * @param smaImg           the sma img
     * @param norImg           the nor img
     * @param order            the order
     * @param isRoot           the is root
     * @param xmlid            the xmlid
     * @param valid            the valid
     * @param string           the string
     * @param column           the column
     * @param columnName       the column name
     * @param originExpression the origin expression
     * @param expression       the expression
     */
    public AbstractTbom(String id, Table table, View view, String name,
                        Long type, String description, String detailText, String bigImg,
                        String smaImg, String norImg, Long order, Long isRoot, String xmlid,
                        Long valid, Schema schema, Column column, String columnName,
                        String expression, String originExpression, String url, String showType, String templateId) {
        super();
        this.id = id;
        this.table = table;
        this.view = view;
        this.name = name;
        this.type = type;
        this.description = description;
        this.detailText = detailText;
        this.bigImg = bigImg;
        this.smaImg = smaImg;
        this.norImg = norImg;
        this.order = order;
        this.isRoot = isRoot;
        this.xmlid = xmlid;
        this.isValid = valid;
        this.schema = schema;
        this.column = column;
        this.columnName = columnName;
        this.expression = expression;
        this.originExpression = originExpression;
        this.url = url;
        this.showType = showType;
        this.templateid = templateId;
    }

    /**
     * 自动创建TBOM树专用构造方法.
     *
     * @param table            数据类ID
     * @param name             结点名称
     * @param order            顺序
     * @param isRoot           是否根节点
     * @param xmlid            XML专用的ID
     * @param schemaid         所属数据模型的ID
     * @param expression       过滤表达式（web展现专用）
     * @param originExpression 过滤表达式（TBOM树展现专用）
     */
    public AbstractTbom(Table table, String name, Long order, Long isRoot,
                        String xmlid, Schema schema, String expression,
                        String originExpression, String url, String showType) {
        super();
        this.table = table;
        this.name = name;
        this.type = new Long(1);
        this.description = null;
        this.detailText = null;
        this.bigImg = null;
        this.smaImg = null;
        this.norImg = null;
        this.order = order;
        this.isRoot = isRoot;
        this.xmlid = xmlid;
        this.isValid = new Long(1);
        this.schema = schema;
        this.column = null;
        this.columnName = null;
        this.expression = expression;
        this.originExpression = originExpression;
        this.url = url;
        this.showType = showType;
    }


    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Set getTbomRoles() {
        return tbomRoles;
    }

    public void setTbomRoles(Set tbomRoles) {
        this.tbomRoles = tbomRoles;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Long getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(Long type) {
        this.type = type;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the detail text.
     *
     * @return the detail text
     */
    public String getDetailText() {
        return detailText;
    }

    /**
     * Sets the detail text.
     *
     * @param detailText the new detail text
     */
    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }

    /**
     * Gets the big img.
     *
     * @return the big img
     */
    public String getBigImg() {
        return bigImg;
    }

    /**
     * Sets the big img.
     *
     * @param bigImg the new big img
     */
    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    /**
     * Gets the sma img.
     *
     * @return the sma img
     */
    public String getSmaImg() {
        return smaImg;
    }

    /**
     * Sets the sma img.
     *
     * @param smaImg the new sma img
     */
    public void setSmaImg(String smaImg) {
        this.smaImg = smaImg;
    }

    /**
     * Gets the nor img.
     *
     * @return the nor img
     */
    public String getNorImg() {
        return norImg;
    }

    /**
     * Sets the nor img.
     *
     * @param norImg the new nor img
     */
    public void setNorImg(String norImg) {
        this.norImg = norImg;
    }

    /**
     * Gets the order.
     *
     * @return the order
     */
    public Long getOrder() {
        return order;
    }

    /**
     * Sets the order.
     *
     * @param order the new order
     */
    public void setOrder(Long order) {
        this.order = order;
    }

    /**
     * Gets the checks if is valid.
     *
     * @return the checks if is valid
     */
    public Long getIsValid() {
        return isValid;
    }

    /**
     * Sets the checks if is valid.
     *
     * @param isValid the new checks if is valid
     */
    public void setIsValid(Long isValid) {
        this.isValid = isValid;
    }

    /**
     * Gets the checks if is root.
     *
     * @return the checks if is root
     */
    public Long getIsRoot() {
        return isRoot;
    }

    /**
     * Sets the checks if is root.
     *
     * @param isRoot the new checks if is root
     */
    public void setIsRoot(Long isRoot) {
        this.isRoot = isRoot;
    }

    /**
     * Gets the parenttbom.
     *
     * @return the parenttbom
     */
    public Tbom getParenttbom() {
        return parenttbom;
    }

    /**
     * Sets the parenttbom.
     *
     * @param parenttbom the new parenttbom
     */
    public void setParenttbom(Tbom parenttbom) {
        this.parenttbom = parenttbom;
    }

    /**
     * Gets the child tboms.
     *
     * @return the child tboms
     */
    public Set<ITbom> getChildTboms() {
        return childTboms;
    }

    /**
     * Sets the child tboms.
     *
     * @param childTboms the new child tboms
     */
    public void setChildTboms(Set childTboms) {
        this.childTboms = childTboms;
    }

//	public Clob getContext() {
//		return context;
//	}
//
//	public void setContext(Clob context) {
//		this.context = context;
//	}

    /**
     * Gets the cwm relation tboms for relation id.
     *
     * @return the cwm relation tboms for relation id
     */
    public Set getCwmRelationTbomsForRelationId() {
        return cwmRelationTbomsForRelationId;
    }

    /**
     * Sets the cwm relation tboms for relation id.
     *
     * @param cwmRelationTbomsForRelationId the new cwm relation tboms for relation id
     */
    public void setCwmRelationTbomsForRelationId(Set cwmRelationTbomsForRelationId) {
        this.cwmRelationTbomsForRelationId = cwmRelationTbomsForRelationId;
    }

    /**
     * Gets the cwm relation tboms for node id.
     *
     * @return the cwm relation tboms for node id
     */
    public Set getCwmRelationTbomsForNodeId() {
        return cwmRelationTbomsForNodeId;
    }

    /**
     * Sets the cwm relation tboms for node id.
     *
     * @param cwmRelationTbomsForNodeId the new cwm relation tboms for node id
     */
    public void setCwmRelationTbomsForNodeId(Set cwmRelationTbomsForNodeId) {
        this.cwmRelationTbomsForNodeId = cwmRelationTbomsForNodeId;
    }

    /**
     * Gets the xmlid.
     *
     * @return the xmlid
     */
    public String getXmlid() {
        return xmlid;
    }

    /**
     * Sets the xmlid.
     *
     * @param xmlid the new xmlid
     */
    public void setXmlid(String xmlid) {
        this.xmlid = xmlid;
    }

    /**
     * Gets the relation file.
     *
     * @return the relation file
     */
    public Set getRelationFile() {
        return relationFile;
    }

    /**
     * Sets the relation file.
     *
     * @param relationFile the new relation file
     */
    public void setRelationFile(Set relationFile) {
        this.relationFile = relationFile;
    }


    /**
     * table
     *
     * @return the table
     * @since CodingExample Ver 1.0
     */

    public Table getTable() {
        return table;
    }

    /**
     * table
     *
     * @param table the table to set
     * @since CodingExample Ver 1.0
     */

    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * view
     *
     * @return the view
     * @since CodingExample Ver 1.0
     */

    public View getView() {
        return view;
    }

    /**
     * view
     *
     * @param view the view to set
     * @since CodingExample Ver 1.0
     */

    public void setView(View view) {
        this.view = view;
    }

    /**
     * schema
     *
     * @return the schema
     * @since CodingExample Ver 1.0
     */

    public Schema getSchema() {
        return schema;
    }

    /**
     * schema
     *
     * @param schema the schema to set
     * @since CodingExample Ver 1.0
     */

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    /**
     * column
     *
     * @return the column
     * @since CodingExample Ver 1.0
     */

    public Column getColumn() {
        return column;
    }

    /**
     * column
     *
     * @param column the column to set
     * @since CodingExample Ver 1.0
     */

    public void setColumn(Column column) {
        this.column = column;
    }

    /**
     * Gets the column name.
     *
     * @return the column name
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Sets the column name.
     *
     * @param columnName the new column name
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Gets the expression.
     *
     * @return the expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Sets the expression.
     *
     * @param expression the new expression
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * Gets the origin expression.
     *
     * @return the origin expression
     */
    public String getOriginExpression() {
        return originExpression;
    }

    /**
     * Sets the origin expression.
     *
     * @param originExpression the new origin expression
     */
    public void setOriginExpression(String originExpression) {
        this.originExpression = originExpression;
    }

    /**
     * Gets url
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * dynamicTbom
     *
     * @param   dynamicTbom    the dynamicTbom to set
     * @since CodingExample Ver 1.0
     */

    /**
     * dynamicTbom
     *
     * @return the dynamicTbom
     * @since CodingExample Ver 1.0
     */


    public String getSchemaid() {
        return schemaid;
    }


    public void setSchemaid(String schemaid) {
        this.schemaid = schemaid;
    }

    public String getTemplateid() {
        return templateid;
    }

    public void setTemplateid(String templateid) {
        this.templateid = templateid;
    }
}
