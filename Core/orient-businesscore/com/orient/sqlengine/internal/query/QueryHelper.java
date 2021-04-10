package com.orient.sqlengine.internal.query;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.BusinessModelEdge;
import com.orient.sqlengine.internal.SqlEngineHelper;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-10-22 10:26 AM
 */
public class QueryHelper {

    public static String getDataFilter(String data_filter) {
        data_filter = data_filter.replaceAll("<-DHC->", " AND ");
        if ("false".equals(data_filter)) {
            return "";
        } else if (!"".equals(data_filter)) {
            data_filter = data_filter.replace("( A[<-yes->]", "( ");
            data_filter = data_filter.replace("A[<-yes->]", " and ");
            return data_filter;
        } else {
            return "";
        }
    }

    public static String createIdSql(BusinessModelEdge edge, String idSql, List<String> dataList) {
        StringBuilder sb = new StringBuilder();
        IBusinessModel start = edge.getStart();
        IBusinessModel end = edge.getEnd();
        if (edge.isManyToMany()) {
            sb.append(" select  SUB_DATA_ID ");
            sb.append(" from CWM_RELATION_DATA ");
            sb.append(" where MAIN_TABLE_NAME = '").append(start.getS_table_name()).append("'");
            sb.append(" 	and SUB_TABLE_NAME = '").append(end.getS_table_name()).append("'");
            sb.append(" 	and MAIN_DATA_ID in (");
            sb.append(new RelationQuery(start, "ID", dataList).toSql());
            if (!SqlEngineHelper.isNullString(idSql)) {
                sb.append(" where ID in(").append(idSql).append(")");
            }
            sb.append(") ");
        } else {
            if (edge.getEdgeType() == 0) {
                String fkColName = end.getS_table_name() + "." + start.getS_table_name() + "_ID";
                sb.append(" select ID ");
                sb.append(" from ").append(end.getS_table_name());
                sb.append(" where ").append(fkColName).append(" in (");
                sb.append(new RelationQuery(start, "ID", dataList).toSql());
                if (!SqlEngineHelper.isNullString(idSql)) {
                    sb.append(" where ID in(").append(idSql).append(")");
                }
                sb.append(")");
            }
            if (edge.getEdgeType() == 1) {
                String fkColName = end.getS_table_name() + "_ID";
                sb.append(" select ID ");
                sb.append(" from ").append(end.getS_table_name());
                sb.append(" where ID in (");
                sb.append(new RelationQuery(start, fkColName, dataList).toSql());
                if (!SqlEngineHelper.isNullString(idSql)) {
                    sb.append(" where ID in(").append(idSql).append(")");
                }
                sb.append(")");
            }
            if (edge.getEdgeType() == 2) {//自身到自身的连接
                String fkColName = "ID";
                sb.append(" select ID ");
                sb.append(" from ").append(end.getS_table_name());
                sb.append(" where ID in (");
                sb.append(new RelationQuery(start, fkColName, dataList).toSql());
                if (!SqlEngineHelper.isNullString(idSql)) {
                    sb.append(" where ID in(").append(idSql).append(")");
                }
                sb.append(")");
            }
        }
        return sb.toString();

    }


    public static String getRelationFilter(IBusinessModel bm, List<String> dataList) {
        StringBuilder sb = new StringBuilder();
        List<List<BusinessModelEdge>> pedigreeList = bm.getPedigreeList();
        for (int i = 0; i < pedigreeList.size(); i++) {
            List<BusinessModelEdge> bmEdgeList = pedigreeList.get(i);
            String idSql = "";
            for (BusinessModelEdge bmEdge : bmEdgeList) {
                idSql = QueryHelper.createIdSql(bmEdge, idSql, dataList);
            }

            if (!SqlEngineHelper.isNullString(idSql)) {
                if (i != 0) {
                    sb.append(" or ");
                }
                sb.append("id in(").append(idSql).append(")");
            }
        }
        if (sb.toString().equals("")) {
            return "";
        }
        return "(" + sb.toString() + ")";
    }

    public static void appendTreeNodeFilter(IBusinessModel bm,StringBuilder sql){
        if (null != bm.getTreeNodeFilterModelBean()) {
            // tbom定义的静态过滤表达式
            if (!SqlEngineHelper.isNullString(bm.getTreeNodeFilterModelBean().getStatic_filter())) {
                sql.append(" AND (").append(bm.getTreeNodeFilterModelBean().getStatic_filter()).append(") ");
            }
            //动态节点的过滤表达式
            if (!SqlEngineHelper.isNullString(bm.getTreeNodeFilterModelBean().getExtNode_filter())) {
                sql.append(" AND (").append(bm.getTreeNodeFilterModelBean().getExtNode_filter()).append(") ");
            }
        }
    }
}
