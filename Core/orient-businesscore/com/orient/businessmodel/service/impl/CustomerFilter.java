package com.orient.businessmodel.service.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.Util.EnumInter.SqlConnection;
import com.orient.businessmodel.Util.EnumInter.SqlOperation;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.bean.IdQueryCondition;
import com.orient.metamodel.operationinterface.IRelationColumn;
import com.orient.utils.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhulc@cssrc.com.cn
 * @ClassName CustomerFilter
 * 自定义的过滤条件
 * @date Apr 28, 2012
 */

public class CustomerFilter implements Serializable {

    public static final String BETWEEN_AND_FILTER_SPLIT = "<!=!>";

    /**
     * @Fields filterName : 过滤的名称
     */
    private String filterName;
    /**
     * @Fields operation : 操作符
     */
    private SqlOperation operation;
    /**
     * @Fields filterValue : 过滤的值
     */
    private String filterValue;
    /**
     * @Fields connection : 过滤条件之间的连接方式，默认采用And
     */
    private SqlConnection connection;
    /**
     * @Fields parent : 该过滤对象的父对象
     */
    private CustomerFilter parent;
    /**
     * @Fields child : 该过滤对象的子对象
     */
    private CustomerFilter child;
    /**
     * @Fields visited : 该过滤对象在转成Sql语句时，是否已经被遍历过
     */
    private boolean visited = false;

    private String expressType = "ID";

    private IdQueryCondition idQueryCondition;

    public CustomerFilter() {

    }

    public CustomerFilter(IdQueryCondition idQueryCondition) {
        this.idQueryCondition = idQueryCondition;
    }

    public CustomerFilter(String filterName, SqlOperation operation,
                          String filterValue) {
        this(filterName, operation, filterValue, null);
    }

    public CustomerFilter(String filterName, SqlOperation operation,
                          String filterValue, SqlConnection connection) {
        this.filterName = filterName;
        this.operation = operation;
        this.filterValue = filterValue;
        this.connection = connection;
    }

    public CustomerFilter(String filterName, SqlOperation operation,
                          String filterValue, SqlConnection connection, String expressType) {
        this.filterName = filterName;
        this.operation = operation;
        this.filterValue = filterValue;
        this.connection = connection;
        this.expressType = expressType;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }


    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public CustomerFilter getParent() {
        return parent;
    }

    public void setParent(CustomerFilter parent) {
        this.parent = parent;
    }

    public SqlOperation getOperation() {
        return operation;
    }

    public void setOperation(SqlOperation operation) {
        this.operation = operation;
    }

    public SqlConnection getConnection() {
        return connection;
    }

    public void setConnection(SqlConnection connection) {
        this.connection = connection;
    }

    public CustomerFilter getChild() {
        return child;
    }

    public void setChild(CustomerFilter child) {
        this.child = child;
    }

    /**
     * 将过滤对象转成Sql格式的字符串，值用?代替
     * 实际值存入到dataList中
     *
     * @param dataList
     * @return String
     * @Method: toSql
     */
    public String toSql(List<String> dataList, IBusinessModel businessModel) {
        StringBuilder sb = new StringBuilder();
        if (null != idQueryCondition) {
            sb.append("ID IN (").append(idQueryCondition.getSql()).append(") ");
            dataList.addAll(idQueryCondition.getParams());
        } else {
            sb.append(filterName).append(" ");
            if (operation.toString().equalsIgnoreCase("in")) {
                sb.append(operation.toString());
                sb.append(" (");
                if ("expression".equals(expressType)) {
                    sb.append(filterValue);
                } else {
                    for (int i = 0; i < filterValue.split(",").length; i++) {
                        if (i == 0) {
                            sb.append("?");
                        } else {
                            sb.append(",?");
                        }
                        dataList.add(filterValue.split(",")[i]);
                    }
                }
                sb.append(")");

            } else if (operation.toString().equalsIgnoreCase("not in")) {
                sb.append(operation.toString());
                sb.append(" (");
                if ("expression".equals(expressType)) {
                    sb.append(filterValue);
                } else {
                    for (int i = 0; i < filterValue.split(",").length; i++) {
                        if (i == 0) {
                            sb.append("?");
                        } else {
                            sb.append(",?");
                        }
                        dataList.add(filterValue.split(",")[i]);
                    }
                }
                sb.append(")");
            } else if (operation.toString().equalsIgnoreCase("like")) {
                sb = new StringBuilder();
                //区分Text与普通文本的区别
                sb.append(createLikeSql(businessModel, dataList));
            } else if (operation.toString().equalsIgnoreCase("BetweenAnd")) {
                //针对不同字段类型 between不同操作
                sb = new StringBuilder();
                sb.append(createBetweenSql(businessModel, dataList));
            } else if (operation.toString().equalsIgnoreCase("contains")) {
                //包含某些字符
                sb = new StringBuilder();
                sb.append(createContainsSql(businessModel, dataList));
            } else {
                if (null == filterValue || "".equals(filterValue)) {
                    if(operation.toString().equalsIgnoreCase("Is Null")) {
                        sb.append("IS NULL ");
                    }
                    else if(operation.toString().equalsIgnoreCase("Is Not Null")) {
                        sb.append("IS NOT NULL ");
                    }
                } else {
                    if ("expression".equals(expressType)) {
                        sb.append(filterValue);
                    } else if (operation.toString().equalsIgnoreCase("=")) {
                        sb.append(createEquelSql(businessModel, dataList));
                    } else {
                        sb.append(operation.toString()).append(" ? ");
                        dataList.add(filterValue);
                    }
                }
            }
        }
        visited = true;
        if (child != null) {
            if (connection == null) {
                connection = SqlConnection.And;
            }
            sb.append(" ").append(connection.toString()).append(" ");
            sb.append(child.toSql(dataList, businessModel));
        }
        if (parent != null) {
            if (parent.connection == null) {
                parent.connection = SqlConnection.And;
            }
            sb.append(" ").append(parent.connection.toString()).append(" ");
            sb.append(parent.toSql(dataList, businessModel));
        }

        return sb.toString();
    }

    private String createEquelSql(IBusinessModel businessModel, List<String> dataList) {
        StringBuffer retVal = new StringBuffer();
        //获取字段类型
        IBusinessColumn businessColumn = businessModel.getBusinessColumnBySName(this.getFilterName());
        if (null != businessColumn) {
            EnumInter.BusinessModelEnum.BusinessColumnEnum colEnum = (EnumInter.BusinessModelEnum.BusinessColumnEnum) businessColumn.getColType();
            switch (colEnum) {
                case C_Date:
                    //日期
                    retVal.append("=").append(" to_date(?,'YYYY-MM-DD')");
                    break;
                case C_DateTime:
                    //时间
                    retVal.append("=").append(" to_date(?,'YYYY-MM-DD HH24:MI:SS')");
                    break;
                default:
                    retVal.append(operation.toString()).append(" ? ");
            }
        } else {
            retVal.append(operation.toString()).append(" ? ");
        }
        dataList.add(filterValue);
        return retVal.toString();
    }

    private String createLikeSql(IBusinessModel businessModel, List<String> dataList) {
        StringBuffer retVal = new StringBuffer();
        if (!StringUtil.isEmpty(filterValue)) {
            IBusinessColumn businessColumn = businessModel.getBusinessColumnBySName(this.getFilterName());
            EnumInter.BusinessModelEnum.BusinessColumnEnum colEnum = (EnumInter.BusinessModelEnum.BusinessColumnEnum) businessColumn.getColType();
            switch (colEnum) {
                case C_Text:
                    //clob查询
                    retVal.append("dbms_lob.instr(" + filterName + ",?,1,1) > 0");
                    dataList.add(filterValue);
                    break;
                default:
                    retVal.append(filterName).append(" ");
                    retVal.append(operation.toString());
                    retVal.append(" ? ");
                    dataList.add("%" + filterValue + "%");
            }
        }
        return retVal.toString();
    }

    private String createContainsSql(IBusinessModel businessModel, List<String> dataList) {
        StringBuffer retVal = new StringBuffer();
        retVal.append(" 1=1 ");
        if (!StringUtil.isEmpty(filterValue)) {
            retVal.append(" and (");
            //获取间隔符
            String spaceChar = filterValue.indexOf(";") > 0 ? ";" : ",";
            String[] valueArray = filterValue.split(spaceChar);
            for (String fvalue : valueArray) {
                retVal.append(" instr(").append(filterName).append(",?)>0 or");
                dataList.add(fvalue);
            }
            retVal.append(" 1=0 ) ");
        }
        return retVal.toString();
    }

    private String createBetweenSql(IBusinessModel businessModel, List<String> dataList) {
        StringBuffer retVal = new StringBuffer();
        //获取字段类型
        IBusinessColumn businessColumn = businessModel.getBusinessColumnBySName(this.getFilterName());
        EnumInter.BusinessModelEnum.BusinessColumnEnum colEnum = (EnumInter.BusinessModelEnum.BusinessColumnEnum) businessColumn.getColType();
        switch (colEnum) {
            case C_Integer:
                //整数型
                retVal.append(createNumberBetweenSql(dataList));
                break;
            case C_BigInteger:
                //数值型
                retVal.append(createNumberBetweenSql(dataList));
                break;
            case C_Decimal:
                //数值型
                retVal.append(createNumberBetweenSql(dataList));
                break;
            case C_Float:
                //数值型
                retVal.append(createNumberBetweenSql(dataList));
                break;
            case C_Relation:
                //关系属性
                retVal.append(createRelationBetweenSql(dataList, businessColumn));
                break;
            case C_Date:
                //日期
                retVal.append(createDateBetweenSql(dataList, "YYYY-MM-DD"));
                break;
            case C_DateTime:
                //时间
                retVal.append(createDateBetweenSql(dataList, "YYYY-MM-DD HH24:MI:SS"));
                break;
            default:
                if (filterValue.indexOf("<!=!>") < 0) {
                    retVal.append(">=").append("?");
                    dataList.add(filterValue);
                } else {
                    retVal.append("between ").append("?").append(" and ?");
                    String[] strArr = filterValue.split("<!=!>");
                    dataList.add(strArr[0]);
                    dataList.add(strArr[1]);
                }
        }
        return retVal.toString();
    }

    private String createRelationBetweenSql(List<String> dataList, IBusinessColumn businessColumn) {
        StringBuffer retVal = new StringBuffer();
        IRelationColumn relationColumn = businessColumn.getRelationColumnIF();
        int relationType = relationColumn.getRelationType();
        if (1 == relationType || 3 == relationType) {
            //1对1 或者多对1
            retVal.append(filterName).append(" IN ('");
            String spaceChar = filterValue.indexOf(";") > 0 ? ";" : ",";
            retVal.append(filterValue.replaceAll(spaceChar, "','"));
            retVal.append("')");
        } else if (4 == relationType) {
            //多对多 从中间表中获取
            retVal.append("ID IN (SELECT MAIN_DATA_ID FROM CWM_RELATION_DATA WHERE SUB_TABLE_NAME = ? AND SUB_DATA_ID IN ('");
            String spaceChar = filterValue.indexOf(";") > 0 ? ";" : ",";
            retVal.append(filterValue.replaceAll(spaceChar, "','"));
            retVal.append("'))");
            dataList.add(relationColumn.getRefTable().getTableName());
        }
        return retVal.toString();
    }

    private String createNumberBetweenSql(List<String> dataList) {
        StringBuffer retVal = new StringBuffer();
        retVal.append(filterName).append(" ");
        if (filterValue.indexOf("<!=!>") < 0) {
            retVal.append(">=").append("?");
            dataList.add(filterValue);
        } else {
            String[] valueArr = filterValue.split("<!=!>");
            String startNumber = valueArr[0];
            String endNumber = valueArr.length == 2 ? valueArr[1] : "";
            if (!StringUtil.isEmpty(startNumber) && StringUtil.isEmpty(endNumber)) {
                retVal.append(">= ?");
                dataList.add(startNumber);
            } else if (StringUtil.isEmpty(startNumber) && !StringUtil.isEmpty(endNumber)) {
                retVal.append("<= ?");
                dataList.add(endNumber);
            } else if (!StringUtil.isEmpty(startNumber) && !StringUtil.isEmpty(endNumber)) {
                retVal.append(">= ?").append(" AND ").append(this.getFilterName());
                retVal.append("<= ?");
                dataList.add(startNumber);
                dataList.add(endNumber);
            }
        }
        return retVal.toString();
    }

    private String createDateBetweenSql(List<String> dataList, String dateFormat) {
        StringBuffer retVal = new StringBuffer();
        retVal.append(filterName).append(" ");
        if (filterValue.indexOf("<!=!>") < 0) {
            retVal.append("=").append(" to_date(?,'" + dateFormat + "')");
            dataList.add(filterValue);
        } else {
            String[] valueArr = filterValue.split("<!=!>");
            String startDate = valueArr[0];
            String endDate = valueArr.length == 2 ? valueArr[1] : "";
            if (!StringUtil.isEmpty(startDate) && StringUtil.isEmpty(endDate)) {
                retVal.append(">=").append(" to_date(?,'" + dateFormat + "')");
                dataList.add(startDate);
            } else if (StringUtil.isEmpty(startDate) && !StringUtil.isEmpty(endDate)) {
                retVal.append("<=").append(" to_date(?,'" + dateFormat + "')");
                dataList.add(endDate);
            } else if (!StringUtil.isEmpty(startDate) && !StringUtil.isEmpty(endDate)) {
                retVal.append(">=").append(" to_date(?,'" + dateFormat + "') AND ").append(this.getFilterName());
                retVal.append("<=").append(" to_date(?,'" + dateFormat + "')");
                dataList.add(startDate);
                dataList.add(endDate);
            }
        }
        return retVal.toString();
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public IdQueryCondition getIdQueryCondition() {
        return idQueryCondition;
    }

    public void setIdQueryCondition(IdQueryCondition idQueryCondition) {
        this.idQueryCondition = idQueryCondition;
    }
}