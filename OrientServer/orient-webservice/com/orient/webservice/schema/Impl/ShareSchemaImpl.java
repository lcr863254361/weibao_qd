package com.orient.webservice.schema.Impl;

import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ITable;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

public class ShareSchemaImpl extends SchemaBean {

    public Map<String, String> getShareAndDefault() {
        IMetaModel metaModel = metaEngine.getMeta(false);
        Map<String, Schema> schemamap = metaModel.getSchemas();

        //获取共享数据模型
        Map<String, String> infoMap = new HashMap<String, String>();
        for (Map.Entry<String, Schema> entry : schemamap.entrySet()) {
            Schema schema = entry.getValue();
            if (("1").equals(schema.getType())) {
                String id = schema.getId();
                String name = schema.getName();
                String version = schema.getVersion();
                List<ITable> tableList = schema.getAllTables();
                StringBuffer tl = new StringBuffer();
                for (ITable table : tableList) {
                    tl.append(table.getId()).append(";;;;;").append(table.getParentTable() != null ? table.getParentTable().getId() : "").append(";;;;;").append(table.getDisplayName()).append(";;;;;").append(table.getOrder()).append(":::::");
                }
                infoMap.put(id + ",,," + name + ",,," + version, tl.substring(0, tl.length() - 5));
            }
        }

        //获取系统模型
        InputStream is = ISchemaImpl.class.getResourceAsStream("sysTable/systable.properties");
        Properties p = new Properties();
        try {
            p.load(is);
            int size = Integer.valueOf(p.getProperty("sys_number"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < size; i++) {
                sb.append(p.getProperty(Integer.toString(i)));
                if (i < size - 1) {
                    sb.append(":::::");
                }
            }
            infoMap.put("", sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return infoMap;

    }

    public String getTableDetail(String idArray) {
        Schema schema = new Schema();
        schema.setId("schema");
        schema.setName("schema");
        List<String> list = new ArrayList<String>();
        for (String id : idArray.split(",")) {
            list.add(id);
        }
        schema.setRestrictions(null);
        schema.setViews(null);
        try {
            final Set<Table> tableSet = new HashSet<>(0);
            table(schema, tableSet, list);// 读取数据表，以及其子数据类、普通属性、关系属性和参数关联约束
            schema.setTables(tableSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Writer a = null;
        String value = null;
        try {
            Mapping map = new Mapping();
            map.loadMapping(ISchemaImpl.class.getResource("/") + "map.xml");
            a = new StringWriter();
            Marshaller marshaller = new Marshaller(a);
            marshaller.setEncoding("GBK");
            marshaller.setMapping(map);
            marshaller.marshal(schema);
            value = a.toString();
        } catch (IOException ex) {//IO异常，打开失败，请联系管理员
            ex.printStackTrace(System.err);
            return null;
        } catch (MarshalException ex) {//数据模型转换出错，请联系管理员
            ex.printStackTrace(System.err);
            return null;
        } catch (MappingException ex) {//数据模型转换出错，请联系管理员
            ex.printStackTrace(System.err);
            return null;
        } catch (ValidationException ex) {//数据模型转换出错，请联系管理员
            ex.printStackTrace(System.err);
            return null;
        }
        return value;

    }

    private void table(Schema schema, Set<Table> tableSet, List<String> list) {
        int i = 0;
        for (String tableid : list) {
            if (tableid.matches("^-?\\d+$")) {
                getShareTable(schema, tableSet, i, tableid);
            } else {
                getSysTable(schema, tableSet, i, tableid);
            }
            i++;
        }
    }

    private void getSysTable(Schema schema, Set<Table> tableSet, int i, String tableid) {
        Table table = new Table();
        table.setId(tableid);
        table.setCwmTableEnums(null);
        table.setCwmViewRelationtables(null);
        table.setCwmRelationTableEnums(null);
        table.setCwmRelationColumnses(null);
        table.setCwmTabColumnses(null);
        table.setCwmConsExpressions(null);
        table.setChildTables(null);
        table.setCwmViewses(null);
        //table.setTableSet(null);
        table.setParentTable(null);
        table.setExistData("False");
        table.setSchema(schema);
        table.setOrder(new Long(i));
        table.setPaiXu("ASC");
        table.setIsShow("True");
        table.setIsValid(new Long(1));
        table.setUseSecrecy("False");
        table.setCite("");
        table.setShareable("True");
        table.setTableName(tableid);
        InputStream is = ISchemaImpl.class.getResourceAsStream("sysTable/" + tableid + ".properties");
        Properties p = new Properties();
        try {
            p.load(is);
            table.setId(p.getProperty("TABLE_ID"));
            table.setName("table=" + p.getProperty("TABLE_NAME"));
            table.setDisplayName(p.getProperty("TABLE_DISPLAY_NAME"));
            String[] columnArray = p.getProperty("COLUMN_SET").split(",");
            final Set<Column> columnSet = new HashSet(0);
            int j = 0;
            //为主键显示值使用
            Map<String, Column> map = new HashMap<String, Column>();
            Map<Integer, Column> skMap = new HashMap<Integer, Column>();
            for (String name : columnArray) {
                Column column = new Column();
                String[] columnDetail = p.getProperty(name).split(",,,");
                column.setId(name);
                column.setName(p.getProperty("TABLE_NAME") + "=" + name);
                column.setDisplayName(columnDetail[0]);
                column.setCwmTableEnums(null);
                column.setCwmViewPaixuColumns(null);
                column.setCwmViewReturnColumns(null);
                column.setRelationColumn(null);
                column.setColumnSet(null);
                column.setTable(table);
                column.setExistData("False");
                column.setView(null);
                column.setArithAttribute(null);
                column.setCategory(new Long(1));
                column.setIsAllSearch("False");
                column.setIsForSearch("False");
                column.setIsIndex("False");
                column.setOperateSign("like");
                column.setPurpose("新增,修改,详细,列表");
                column.setCasesensitive("CaseInsensitive");
                column.setIsNull("False");
                column.setIsOnly("False");
                column.setCite("");
                column.setType(columnDetail[1]);
                column.setIsAutoIncrement("False");
                column.setIsShow("True");
                column.setIsWrap("False");
                column.setLinage(new Long(1));
                column.setIsValid(new Long(1));
                column.setAutoAddDefault(new Long(1));
                column.setEditable("False");
                if (columnDetail[1].equals("String")) {
                    column.setMaxLength(Long.valueOf(columnDetail[2]));
                } else {
                    column.setMaxLength(new Long(100));
                }
                column.setMinLength(new Long(0));
                column.setOrder(new Long(j));
                column.setColumnName(name);
                columnSet.add(column);
                skMap.put(j, column);
                map.put(name, column);
                j++;
            }
            table.setCwmTabColumnses(columnSet);
            //添加主键显示值
            String[] priArray = p.getProperty("PRI_SET").split(",");
            Map<Integer, Column> pkColumnMap = new HashMap<Integer, Column>();
            for (int m = 0; m < priArray.length; m++) {
                pkColumnMap.put(m, map.get(priArray[m]));
            }
            table.setPkColumns(pkColumnMap);
            table.setUkColumns(null);
            table.setSkColumns(null);
            table.setZxColumns(skMap);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        tableSet.add(table);
    }

    private void getShareTable(Schema schema, Set<Table> tableSet, int i,
                               String tableid) {
        IMetaModel metaModel = metaEngine.getMeta(false);
        Map<String, Schema> schemamap = metaModel.getSchemas();
        Table table = null;
        for (Map.Entry<String, Schema> entry : schemamap.entrySet()) {
            Schema tempSchema = entry.getValue();
            ITable tab = tempSchema.getTableById(tableid);
            if (null != tab) {
                table = (Table) tab;
                continue;
            }

        }

        String name = table.getName();
        table.setName("table=" + name);
        /*table.setCwmTableEnums(null);
		table.setCwmViewRelationtables(null);
		table.setCwmRelationTableEnums(null);
		table.setCwmRelationColumnses(null);
		table.setCwmTabColumnses(null);
		table.setCwmConsExpressions(null);
		table.setChildTables(null);
		table.setCwmViewses(null);*/
        //table.setTableSet(null);
        //table.setParentTable(null);
        table.setExistData("False");
        table.setSchema(schema);

        // 读取普通属性和关系属性
        //final Set<Column> columnSet = new HashSet(0);
        //column(table, columnSet,name);
        //table.setCwmTabColumnses(columnSet);
        //读取数据类的主键、唯一性约束和排序属性
        //tableMultiAttribute(table);

        //table.setId(tableid);
        table.setOrder(new Long(i));
        tableSet.add(table);
    }

    private void column(final Table table, final Set<Column> columnSet, final String tablename) {
        List<IColumn> list = null;
        try {
            list = metadaofactory.getColumnDAO().findColumnsByIsvalidAndCategory(new Long(1), new Long(2));
            for (IColumn columnMap : list) {
                Column column = metadaofactory.getColumnDAO().findById(columnMap.getId());
                if (table.getId().equals(column.getTable().getId())) {
                    column.setRestriction(null);
                    String a = column.getName();
                    if (column.getCategory().equals(new Long(3))) {
                        column.setName(tablename + "=3" + a);
                    } else {
                        column.setName(tablename + "=" + a);
                    }
                    column.setCwmTableEnums(null);
                    column.setCwmViewPaixuColumns(null);
                    column.setCwmViewReturnColumns(null);
                    column.setRelationColumn(null);
                    column.setColumnSet(null);
                    column.setTable(table);
                    column.setExistData("False");
                    column.setView(null);
                    column.setArithAttribute(new HashSet<ArithAttribute>());
                    if (column.getCategory().equals(new Long(3))) {
                        final Set<ArithAttribute> aaset = new HashSet<ArithAttribute>();
                        List<ArithAttribute> arithAttributeList = metadaofactory.getArithAttributeDAO().findByArithColumnId(column.getId());
                        for (ArithAttribute arithAttribute : arithAttributeList) {
                            arithAttribute.setAcolumn(column);
                            if (arithAttribute.getColumn() != null) {
                                Column refcolumn = metadaofactory.getColumnDAO().findById(arithAttribute.getColumn().getId());
                                if (!refcolumn.getCategory().equals(new Long(3))) {
                                    refcolumn.setName(tablename + "=" + refcolumn.getName());
                                } else {
                                    refcolumn.setName(tablename + "=3" + refcolumn.getName());
                                }
                                arithAttribute.setColumn(refcolumn);
                            } else {
                                arithAttribute.setColumn(null);
                            }
                            aaset.add(arithAttribute);

                        }
                        column.getArithAttribute().addAll(aaset);
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void tableMultiAttribute(final Table table) {
        // 读取数据类的主键显示值
        final Map<Integer, Column> pkColumnMap = new HashMap<Integer, Column>();
        List<TableColumn> tableColumnList = metadaofactory.getTableColumnDAO().findByTableAndType(table.getId(), new Long(0));
        for (TableColumn tableColumn : tableColumnList) {
            Column pkColumn = metadaofactory.getColumnDAO().findById(tableColumn.getColumn().getId());
            IColumn columnMap = metadaofactory.getColumnDAO().findById(pkColumn.getId());
            Column column = metadaofactory.getColumnDAO().findById(columnMap.getId());
            ITable tableMap = metadaofactory.getTableDAO().findById(column.getTable().getId());
            Table stable = metadaofactory.getTableDAO().findById(tableMap.getId());
            String name = pkColumn.getName();
            pkColumn.setName(stable.getName() + "=" + name);
            int order = Integer.valueOf(column.getOrder().toString());
            pkColumnMap.put(order, pkColumn);
        }
        table.setPkColumns(pkColumnMap);

        // 读取数据类的组合唯一性约束
        final Map<Integer, Column> ukColumnMap = new HashMap<Integer, Column>();
        tableColumnList = metadaofactory.getTableColumnDAO().findByTableAndType(table.getId(), 1);
        for (TableColumn tableColumn : tableColumnList) {
            Column ukColumn = metadaofactory.getColumnDAO().findById(tableColumn.getColumn().getId());
            IColumn columnMap = metadaofactory.getColumnDAO().findById(ukColumn.getId());
            Column column = metadaofactory.getColumnDAO().findById(columnMap.getId());
            ITable tableMap = metadaofactory.getTableDAO().findById(column.getTable().getId());
            Table stable = metadaofactory.getTableDAO().findById(tableMap.getId());
            String name = ukColumn.getName();
            ukColumn.setName(stable.getName() + "=" + name);
            int order = Integer.valueOf(column.getOrder().toString());
            ukColumnMap.put(order, ukColumn);
        }
        table.setUkColumns(ukColumnMap);

        // 读取数据类的排序属性
        final Map<Integer, Column> skColumnMap = new HashMap<Integer, Column>();
        tableColumnList = metadaofactory.getTableColumnDAO().findByTableAndType(table.getId(), 2);
        for (TableColumn tableColumn : tableColumnList) {
            Column skColumn = metadaofactory.getColumnDAO().findById(tableColumn.getColumn().getId());
            IColumn columnMap = metadaofactory.getColumnDAO().findById(skColumn.getId());
            Column column = metadaofactory.getColumnDAO().findById(columnMap.getId());
            ITable tableMap = metadaofactory.getTableDAO().findById(column.getTable().getId());
            Table stable = metadaofactory.getTableDAO().findById(tableMap.getId());
            String name = skColumn.getName();
            skColumn.setName(stable.getName() + "=" + name);
            int order = Integer.valueOf(column.getOrder().toString());
            ukColumnMap.put(order, skColumn);
        }
        table.setSkColumns(skColumnMap);

        // 读取数据类的属性展现顺序属性
        final Map<Integer, Column> zxColumnMap = new HashMap<Integer, Column>();

        tableColumnList = metadaofactory.getTableColumnDAO().findByTableAndType(table.getId(), 3);
        for (TableColumn tableColumn : tableColumnList) {
            Column zxColumn = metadaofactory.getColumnDAO().findById(tableColumn.getColumn().getId());
            IColumn columnMap = metadaofactory.getColumnDAO().findById(zxColumn.getId());
            Column column = metadaofactory.getColumnDAO().findById(columnMap.getId());
            ITable tableMap = metadaofactory.getTableDAO().findById(column.getTable().getId());
            Table stable = metadaofactory.getTableDAO().findById(tableMap.getId());
            String name = zxColumn.getName();
            zxColumn.setName(stable.getName() + "=" + name);
            int order = Integer.valueOf(column.getOrder().toString());
            ukColumnMap.put(order, zxColumn);
        }
        table.setZxColumns(zxColumnMap);
    }

    public String canDelete(String id, int type) {
        MetaModel model = metaEngine.getMeta(false);
        if (type == 0) {//判断数据类是否可以被删除
            String[] str = id.split(";;;;");
            Schema schema = model.getSchema(str[1], str[2]);
            int count = metadaofactory.getSchemaDAO().getCountByIslockAndTypeAndSchema(schema.getId());
            if (count > 0) {
                return "2";
            }
            count = metadaofactory.getSchemaDAO().getCountByMapTableAndIsvalid(str[0], schema.getId());
            if (count <= 0) {
                return "0";
            } else {
                return "1";
            }
        } else if (type == 1) {//判断普通属性、统计属性是否可以删除
            String[] str = id.split(";;;;");
            Schema schema = model.getSchema(str[2], str[3]);
            int count = metadaofactory.getSchemaDAO().getCountByIslockAndSchema(schema.getId());
            if (count > 0) {
                return "2";
            }
            count = metadaofactory.getSchemaDAO().getCountByMapColumnAndIsvalid(str[0], str[1], schema.getId());
            if (count <= 0) {
                return "0";
            } else {
                return "1";
            }
        }
        return "0";
    }
}
