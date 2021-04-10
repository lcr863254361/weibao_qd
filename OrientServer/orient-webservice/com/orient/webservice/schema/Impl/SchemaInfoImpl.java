package com.orient.webservice.schema.Impl;

import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.metadomain.Enum;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.dao.DataAccessException;

import java.util.*;

public class SchemaInfoImpl extends SchemaBean {

    public String getSchema() {
        IMetaModel metaModel = metaEngine.getMeta(false);
        Map<String, Schema> schemamap = metaModel.getSchemas();
        StringBuffer str = new StringBuffer();
        for (Map.Entry<String, Schema> entry : schemamap.entrySet()) {
            Schema schema = entry.getValue();
            String name = schema.getName();
            String version = schema.getVersion();
            String type = schema.getType();
            if (type == null) {
                type = "0";
            }
            String id = schema.getId();
            int is_lock = 0;
            if (schema.getIsLock() != null) {
                is_lock = schema.getIsLock().intValue();
            }
            str.append(name).append(",,;.").append(version).append(",,;.").append(type).append(",,;.").append(id).append(",,;.").append(is_lock).append("..;,");

        }
        String schemaStr = str.toString();
        if (schemaStr.length() < 4) {
            return "";
        }
        return schemaStr.substring(0, schemaStr.length() - 4);
    }

    public int deleteSchema(String name, String version) {
        int info = 0;
        IMetaModel metaModel = metaEngine.getMeta(false);
        ISchema schemaMap = metaModel.getISchema(name, version);
        if (schemaMap != null) {
            String schemaId = schemaMap.getId();
            Schema schema = metadaofactory.getSchemaDAO().findById(schemaId);
            try {
                //删除该schema下的所有附件以及附件记录
                fileService.deleteFilesBySchemaId(schemaId, new Long(1));
                //删除表
                for (Iterator it = schema.getTables().iterator(); it.hasNext(); ) {
                    Table table = (Table) it.next();
                    deleteTableAttribute(table);
                    metadaofactory.getTableDAO().delete(table);
                    if (table.getTableName().toUpperCase().indexOf("CWM_") < 0) {//系统表不删除
                        try {
                            metadaofactory.getJdbcTemplate().execute("drop table " + table.getTableName() + " purge ");

                        } catch (Exception e) {

                        }
                        try {
                            metadaofactory.getJdbcTemplate().execute("drop sequence seq_" + table.getTableName());
                        } catch (Exception e) {

                        }
                    }
                }
                //删除约束
                for (Iterator it = schema.getRestrictions().iterator(); it.hasNext(); ) {
                    Restriction restriction = (Restriction) it.next();
                    deleteRestrictions(restriction);
                    metadaofactory.getRestrictionDAO().delete(restriction);

                }
                //删除试图
                for (Iterator it = schema.getViews().iterator(); it.hasNext(); ) {
                    View view = (View) it.next();
                    deleteViewAttribute(view);
                    metadaofactory.getViewDAO().delete(view);
                    try {
                        metadaofactory.getJdbcTemplate().execute("drop view " + view.getViewName());
                    } catch (Exception e) {

                    }
                }
                //删除tbom
                tbomService.deleteTbom(schemaId);
                metaEngine.getMeta(false).deleteSchema(schemaId);
                metadaofactory.getSchemaDAO().delete(schema);
                String sql = "delete from cwm_sys_role_schema where schema_id ='" + schemaId + "'";
                metadaofactory.getJdbcTemplate().execute(sql);
            } catch (Exception e) {
                e.printStackTrace();
                info = -1;
            }
        } else {
            info = 1;
        }
        return info;
    }

    public String isExistData(String name, String version) {
        try {
            IMetaModel metaModel = metaEngine.getMeta(false);
            ISchema schemaMap = metaModel.getISchema(name, version);
            if (schemaMap != null) {
                String schemaId = schemaMap.getId();
                Schema schema = metadaofactory.getSchemaDAO().findById(schemaId);
                if (schema.getIsLock().equals(new Long(1))) {
                    //TODO 修改返回值
                    String username = schema.getUsername();
                    return "1" + username;
                }
                //引用的数据类不统计其是否包含的数据
                List<Table> list = metadaofactory.getTableDAO().findBySchemaidAndIsValidAndMapTable(schema, new Long(1));
                if (!list.isEmpty()) {
                    for (Iterator<Table> it = list.iterator(); it.hasNext(); ) {
                        Table map = it.next();
                        String tablename = map.getTableName();
                        try {
                            List<Map> a = metadaofactory.getTableDAO().getSqlResultByTableName(tablename);
                            if (!a.isEmpty()) {
                                if (metadaofactory.getTableDAO().getCountByTableName(tablename) != 0) {
                                    return "true";
                                }
                            } else {
                                return "warn";
                            }
                        } catch (Exception e) {
                            return "warn";
                        }
                    }
                }
            } else {
                return "2";
            }
            return "false";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String getArithmetic() {
//		List<Arith> list = metadaofactory.getArithDAO().findAll();
//		if(list.isEmpty()){
//			return null;
//		}else{
//			StringBuffer str=new StringBuffer();
//			for(Arith arithMap:list){
//				str.append(arithMap.getId()==null?"":arithMap.getId().toString()).append(",;.,,")
//				.append(arithMap.getName()==null?"":arithMap.getName().toString()).append(",;.,,")
//				.append(arithMap.getType()==null?"":arithMap.getType().toString()).append(",;.,,")
//				.append(arithMap.getCategory()==null?"":arithMap.getCategory().toString()).append(",;.,,")
//				.append(arithMap.getDescription()==null?"":arithMap.getDescription().toString()).append(",;.,,")
//				.append(arithMap.getParaNumber()==null?"":arithMap.getParaNumber().toString()).append(",;.,,")
//				.append(arithMap.getParaType()==null?"":arithMap.getParaType().toString()).append(",;.,,")
//				.append(arithMap.getDataType()==null?"":arithMap.getDataType().toString()).append(",;.,,")
//				.append(arithMap.getArithType()==null?"":arithMap.getArithType().toString()).append(",;.,,")
//				.append(arithMap.getLeastNumber()==null?"":arithMap.getLeastNumber().toString()).append(",;.,,")
//				.append(arithMap.getArithMethod()==null?"":arithMap.getArithMethod().toString()).append(".;,;;.");
//			}
//			return str.toString().substring(0, str.length()-6);
//		}
        return "";
    }

    public String deleteFile(String schemaId) {
        try {
            Schema schema = metadaofactory.getSchemaDAO().findById(schemaId);
            //删除该schema下的所有附件以及附件记录
            fileService.deleteFilesBySchemaId(schemaId, new Long(1));
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    public String unlockSchema(List<String> list) {
        try {
            for (String schemaId : list) {
                Schema schema = (Schema) metaEngine.getMeta(false).getISchemaById(schemaId);
                schema.setIsLock(0);
                metadaofactory.getSchemaDAO().attachDirty(schema);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    public String updateSeqValue(String id, int value) {
        String[] str = id.split(";;;;");
        String columnname = str[0];//字段名称
        String tablename = str[1];//数据类名称
        String schemaname = str[2];//数据模型名称
        String schemaversion = str[3];//数据模型版本号
        //获取字段实际名称
        IMetaModel metaModel = metaEngine.getMeta(false);
        ISchema schemaMap = metaModel.getISchema(schemaname, schemaversion);
        ITable tableMap = schemaMap.getTableByName(tablename);
        if (tableMap != null) {
            IColumn columnMap = tableMap.getColumnByName(columnname);
            String seqName = null;
            String cName = null;
            String columnId = null;
            if (columnMap == null) {
                return "2";//字段不存在，不需要重新初始化值
            } else {
                columnId = columnMap.getId().toString();
                seqName = "SEQ_" + tableMap.getTableName().toString();
                cName = columnMap.getColumnName().toString();
                //获得数据库连接对象
                String tsn = getTsn();
                int i = this.getCount(seqName, tsn);
                if (i == 0) {
                    return "3";//字段存在，但序列并没有创建。
                }
            }
            String tableId = tableMap.getId();
            //获取该字段在现有数据类中是否有数据
            List tableList = metadaofactory.getTableDAO().queryList(tableId);
            if (tableList.isEmpty()) {
                return "2";
            } else {
                List<String> resultList = new ArrayList<>();
                Object[] elementData = tableList.toArray();
                for (int index = 0; index < elementData.length; index++) {
                    Object[] val = (Object[]) elementData[index];
                    if (val[3] != null) {
                        String tname = val[3].toString();
                        int i = metadaofactory.getTableDAO().getCount(cName, tname);
                        if (i != 0) {
                            resultList.add(cName);
                        }
                    }
                }
                if (!resultList.isEmpty()) {
                    return "4";
                } else {
                    try {
                        this.dropSequence(seqName);
                        this.createSequence(seqName, value);
                        Column column = metadaofactory.getColumnDAO().findById(columnId);
                        column.setAutoAddDefault(Long.parseLong(String.valueOf(value)));
                        metadaofactory.getColumnDAO().attachDirty(column);
                    } catch (DataAccessException e) {
                        e.printStackTrace();
                        return "error";
                    }
                    return "0";
                }
            }
        } else {
            return "1";
        }
    }

    /**
     * 获取数据库当前连接的数据库用户.
     *
     * @return the tsn
     */
    public String getTsn() {
        BasicDataSource ds = (BasicDataSource) metadaofactory.getJdbcTemplate().getDataSource();
        String tsn = ds.getUsername().toUpperCase();//数据库用户名称
        return tsn;
    }

    /**
     * 获取sequence的个数.
     *
     * @return int
     */
    public int getCount(String sequence_name, String sequence_owner) {
        int count = 0;
        try {
            String searchsql = "SELECT COUNT(*) count FROM ALL_SEQUENCES WHERE SEQUENCE_NAME='" + sequence_name.toUpperCase() + "' AND SEQUENCE_OWNER='" + sequence_owner + "'";
            List result = metadaofactory.getJdbcTemplate().queryForList(searchsql);
            if (result.size() != 0) {
                Map map = (Map) result.get(0);
                count = Integer.parseInt(map.get("count").toString());
            }
            return count;
        } catch (RuntimeException re) {
            return count;
        }
    }

    /**
     * 删除sequence.
     *
     * @return void
     */
    public void dropSequence(String seqName) {
        String searchsql = "DROP SEQUENCE " + seqName;
        metadaofactory.getJdbcTemplate().execute(searchsql);
    }

    /**
     * 创建sequence.
     *
     * @return void
     */
    public void createSequence(String seqName, int value) {
        String searchsql = "CREATE SEQUENCE " + seqName + " INCREMENT BY 1 START WITH " + value + " NOMAXVALUE NOCACHE";
        metadaofactory.getJdbcTemplate().execute(searchsql);
    }

    /**
     * 创建sequence.
     *
     * @return void
     */
    public void createSequence(String seqName, int value, int interval) {
        String searchsql = "CREATE SEQUENCE " + seqName + " INCREMENT BY " + interval + " START WITH " + value + " NOMAXVALUE NOCACHE";
        metadaofactory.getJdbcTemplate().execute(searchsql);
    }

    public String updateSeqValue(String id, int value, int interval) {
        String[] str = id.split(";;;;");
        String columnname = str[0];//字段名称
        String tablename = str[1];//数据类名称
        String schemaname = str[2];//数据模型名称
        String schemaversion = str[3];//数据模型版本号
        //获取字段实际名称
        IMetaModel metaModel = metaEngine.getMeta(false);
        ISchema schemaMap = metaModel.getISchema(schemaname, schemaversion);
        ITable tableMap = schemaMap.getTableByName(tablename);
        if (tableMap != null) {
            IColumn columnMap = tableMap.getColumnByName(columnname);
            String seqName = null;
            String cName = null;
            String columnId = null;
            if (columnMap == null) {
                return "2";//字段不存在，不需要重新初始化值
            } else {
                columnId = columnMap.getId().toString();
                seqName = "SEQ_" + tableMap.getTableName().toString();
                cName = columnMap.getColumnName().toString();
                //获得数据库连接对象
                String tsn = getTsn();
                int i = this.getCount(seqName, tsn);
                if (i == 0) {
                    return "3";//字段存在，但序列并没有创建。
                }
            }
            String tableId = tableMap.getId();
            //获取该字段在现有数据类中是否有数据
            List tableList = metadaofactory.getTableDAO().queryList(tableId);
            if (tableList.isEmpty()) {
                return "2";
            } else {
                List<String> resultList = new ArrayList<String>();
                Object[] elementData = tableList.toArray();
                for (int index = 0; index < elementData.length; index++) {
                    Object[] val = (Object[]) elementData[index];
                    if (val[3] != null) {
                        String tname = val[3].toString();
                        int i = metadaofactory.getTableDAO().getCount(cName, tname);
                        if (i != 0) {
                            resultList.add(cName);
                        }
                    }
                }
                if (!resultList.isEmpty()) {
                    return "4";
                } else {
                    try {
                        this.dropSequence(seqName);
                        this.createSequence(seqName, value, interval);
                        Column column = metadaofactory.getColumnDAO().findById(columnId);
                        column.setAutoAddDefault(Long.parseLong(String.valueOf(value)));
                        column.setSeqInterval(Long.parseLong(String.valueOf(interval)));
                        metadaofactory.getColumnDAO().attachDirty(column);
                    } catch (DataAccessException e) {
                        e.printStackTrace();
                        return "error";
                    }
                    return "0";
                }
            }
        } else {
            return "1";//表不存在不需要重新初始化值
        }
    }

    private void deleteTableAttribute(Table table) {
        try {
            // 遍历该数据类的子数据类
            for (Iterator childit = table.getChildTables().iterator(); childit.hasNext(); ) {
                Table childTable = (Table) childit.next();
                this.metadaofactory.getTableDAO().delete(childTable);

            }
            //删除主键、排序
            /*List kList=this.getMetadaofactory().getTableColumnDAO().findByTableId(table);//删除所有的主键、唯一性约束和排序属性
			if(!kList.isEmpty()){
				for(Iterator kIt = kList.iterator(); kIt.hasNext();){
					TableColumn kColumn=(TableColumn)kIt.next();
					this.getMetadaofactory().getTableColumnDAO().delete(kColumn);
				}
			}*/
            //遍历数据类下的属性
            for (Iterator columnit = table.getCwmTabColumnses().iterator(); columnit.hasNext(); ) {
                Column column = (Column) columnit.next();
                this.metadaofactory.getColumnDAO().delete(column);

            }

            for (Iterator columnit = table.getCwmTabColumnses().iterator(); columnit.hasNext(); ) {
                //遍历column集合，找出所有的关系属性
                Column column = (Column) columnit.next();
                if (column.getCategory().equals(Column.CATEGORY_RELATION)) {    //关系属性
                    for (Iterator relationcolumnit = table.getCwmRelationColumnses().iterator(); relationcolumnit.hasNext(); ) {
                        RelationColumns relationcolumn = (RelationColumns) relationcolumnit.next();
                        if (relationcolumn.getCwmTabColumnsByColumnId().equals(column)) {
                            this.getMetadaofactory().getRelationColumnsDAO().delete(relationcolumn);
                        }
                    }
                }
            }

            //遍历所有的属性
            for (Iterator columnit = table.getCwmTabColumnses().iterator(); columnit.hasNext(); ) {
                Column column = (Column) columnit.next();

                if (!column.getArithAttribute().isEmpty()) {
                    //遍历算法的参数
                    for (Iterator aait = column.getArithAttribute().iterator(); aait.hasNext(); ) {
                        ArithAttribute ArithParam = (ArithAttribute) aait.next();
                        //ArithParam.setAcolumn(column);	//设置引用该算法的字段
                        this.getMetadaofactory().getArithAttributeDAO().delete(ArithParam);
                    }
                }

            }


        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteViewAttribute(View view) {
        try {
            if (!view.getColumns().isEmpty()) {
                for (Iterator columnit = view.getColumns().iterator(); columnit.hasNext(); ) {
                    Column column = (Column) columnit.next();
                    column.setView(view);
                    String IdentiyName = column.getIdentity();
                    String cName = column.getName();
                    //这里只需要保存统计视图的属性
                    if (column.getCategory().equals(Column.CATEGORY_ARITH)) {
                        column.setName(cName);
                        if (column.getPurpose() == null || column.getPurpose().length() == 0) {
                            column.setPurpose("新增,修改,详细,列表");
                        }
                        column.setColumnName(column.getName() + "_" + view.getId());
                        this.getMetadaofactory().getColumnDAO().delete(column);//删除视图的统计属性到CWM_TAB_COLUMNS
                    }
                    if (!column.getArithAttribute().isEmpty()) {
                        for (Iterator aait = column.getArithAttribute().iterator(); aait.hasNext(); ) {
                            ArithAttribute aa = (ArithAttribute) aait.next();
                            assert (aa.getColumn().getId() != "");
                            aa.setAcolumn(column);
                            this.getMetadaofactory().getArithAttributeDAO().delete(aa);
                        }
                    }
                }
            }
            if (!view.getCanshuxiang().isEmpty()) {
                for (Iterator it = view.getCanshuxiang().iterator(); it.hasNext(); ) {
                    ArithViewAttribute ava = (ArithViewAttribute) it.next();
                    ava.setView(view);
                    this.getMetadaofactory().getArithViewAttributeDAO().delete(ava);
                }
            }

            Set<ViewRefColumn> relationTableViewSet = view.getViewRelationTables();
            if (!relationTableViewSet.isEmpty()) {
                for (ViewRefColumn viewRelTable : relationTableViewSet) {
                    this.getMetadaofactory().getViewRefColumnDAO().delete(viewRelTable);
                }
            }

            // 将元数据库中和数据库中view相关的返回属性、排序属性删除，
            Set<ViewResultColumn> resultColumnSet = view.getReturnColumns();
            if (resultColumnSet.size() != 0) {
                for (ViewResultColumn vrc : resultColumnSet) {

                    this.getMetadaofactory().getViewResultColumnDAO().delete(vrc);
                }
            }

            Set<ViewSortColumn> sortColumnSet = view.getPaixuColumns();
            if (sortColumnSet.size() != 0) {
                for (ViewSortColumn vsc : sortColumnSet) {

                    this.getMetadaofactory().getViewSortColumnDAO().delete(vsc);
                }
            }


        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteRestrictions(Restriction restriction) {
        try {
            if (restriction.getType().intValue() == Restriction.TYPE_ENUM) {
                //枚举约束
                for (Iterator enumit = restriction.getCwmEnums().iterator(); enumit.hasNext(); ) {
                    Enum enu = (Enum) enumit.next();
                    enu.setRestrictionID(restriction.getId());
                    this.getMetadaofactory().getEnumDAO().delete(enu);// 保存约束值
                }
            } else if (restriction.getType().intValue() == Restriction.TYPE_TABLEENUM) {// 数据表枚举约束
                TableEnum te = restriction.getTableEnum();
                te.setRestrictionId(restriction.getId());
                //saveTableEnumDetail(te);
            } else if (restriction.getType().intValue() == Restriction.TYPE_DYNAMICRANGEENUM) {//动态范围约束
                TableEnum te = restriction.getTableEnum();
                te.setRestrictionId(restriction.getId());
                this.getMetadaofactory().getTableEnumDAO().delete(te);
                for (Iterator re = te.getRelationTableEnums().iterator(); re.hasNext(); ) {
                    RelationTableEnum rte = (RelationTableEnum) re.next();
                    this.getMetadaofactory().getRelationTableEnumDAO().delete(rte);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

