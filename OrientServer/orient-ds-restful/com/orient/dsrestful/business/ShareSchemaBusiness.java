package com.orient.dsrestful.business;

import com.orient.web.base.dsbean.CommonDataBean;
import com.orient.web.base.dsbean.CommonResponse;
import com.orient.dsrestful.domain.share.*;
import com.orient.dsrestful.enums.JudgeCanDeleteResponseEnum;
import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metadomain.MetaModel;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.web.base.BaseBusiness;
import com.orient.webservice.schema.Impl.ISchemaImpl;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * Created by GNY on 2018/3/27
 */
@Component
public class ShareSchemaBusiness extends BaseBusiness {

    @Autowired
    MetaDAOFactory metaDaoFactory;

    public ShareTableResponse getShareAndDefault() {
        ShareTableResponse retVal = new ShareTableResponse();
        ShareTableResult result = new ShareTableResult();
        List<ShareTable> systemTableList = new ArrayList<>();
        List<ShareSchema> shareTableList = new ArrayList<>();
        IMetaModel metaModel = metaEngine.getMeta(false);
        Map<String, Schema> schemaMap = metaModel.getSchemas();
        //获取共享数据模型
        schemaMap.forEach((schemaName, schema) -> {
            if (("1").equals(schema.getType())) { //如果是共享模型
                ShareSchema shareSchema = new ShareSchema(schema.getId(), schema.getName(), schema.getVersion());
                List<ShareTable> shareTables = new ArrayList<>();
                List<ITable> tableList = schema.getAllTables();
                tableList.forEach(table -> {
                    ShareTable shareTable = new ShareTable();
                    shareTable.setId(table.getId());
                    shareTable.setName(table.getName());
                    shareTable.setDisplayName(table.getDisplayName());
                    shareTables.add(shareTable);
                });
                shareSchema.setTableList(shareTables);
                shareTableList.add(shareSchema);
            }
        });
        result.setShareTableList(shareTableList);

        //获取系统模型
        InputStream is = ISchemaImpl.class.getResourceAsStream("sysTable/systable.properties");
        Properties p = new Properties();
        try {
            p.load(is);
            int size = Integer.valueOf(p.getProperty("sys_number"));
            for (int i = 0; i < size; i++) {
                String[] arrays = p.getProperty(Integer.toString(i)).split(";;;;;");
                ShareTable systemTable = new ShareTable();
                systemTable.setDisplayName(arrays[1]);
                systemTable.setName(arrays[0]);
                systemTableList.add(systemTable);
            }
        } catch (IOException e) {
            e.printStackTrace();
            retVal.setSuccess(false);
            retVal.setMsg("服务端异常");
            return retVal;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        result.setSystemTableList(systemTableList);
        retVal.setResult(result);
        return retVal;
    }

    public TableDetailResponse getTableDetail(List<String> idList) {
        TableDetailResponse retVal = new TableDetailResponse();
        String result;
        Schema schema = new Schema();
        schema.setId("schema");
        schema.setName("schema");
        schema.setRestrictions(null);
        schema.setViews(null);
        try {
            final Set<Table> tableSet = new HashSet<>(0);
            table(schema, tableSet, idList);// 读取数据表，以及其子数据类、普通属性、关系属性和参数关联约束
            schema.setTables(tableSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Writer writer;
        try {
            Mapping map = new Mapping();
            map.loadMapping(ISchemaImpl.class.getResource("/") + "map.xml");
            writer = new StringWriter();
            Marshaller marshaller = new Marshaller(writer);
            marshaller.setEncoding("GBK");
            marshaller.setMapping(map);
            marshaller.marshal(schema);
            result = writer.toString();
            retVal.setResult(result);
        } catch (IOException e) {//IO异常，打开失败，请联系管理员
            e.printStackTrace();
            retVal.setSuccess(false);
            retVal.setMsg("IO异常，打开失败，请联系管理员");
            return retVal;
        } catch (MarshalException | MappingException | ValidationException e) {//数据模型转换出错，请联系管理员
            e.printStackTrace();
            retVal.setSuccess(false);
            retVal.setMsg("数据模型转换出错，请联系管理员");
            return retVal;
        }
        return retVal;
    }

    private void table(Schema schema, Set<Table> tableSet, List<String> list) {
        int i = 0;
        for (String tableId : list) {
            if (tableId.matches("^-?\\d+$")) {
                getShareTable(schema, tableSet, i, tableId);
            } else {
                getSysTable(schema, tableSet, i, tableId);
            }
            i++;
        }
    }

    private void getSysTable(Schema schema, Set<Table> tableSet, int i, String tableId) {
        Table table = new Table();
        table.setId(tableId);
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
        table.setTableName(tableId);
        InputStream is = ISchemaImpl.class.getResourceAsStream("sysTable/" + tableId + ".properties");
        Properties p = new Properties();
        try {
            p.load(is);
            table.setId(p.getProperty("TABLE_ID"));
            table.setName("table=" + p.getProperty("TABLE_NAME"));
            table.setDisplayName(p.getProperty("TABLE_DISPLAY_NAME"));
            String[] columnArray = p.getProperty("COLUMN_SET").split(",");
            final Set<Column> columnSet = new HashSet<>(0);
            int j = 0;
            //为主键显示值使用
            Map<String, Column> map = new HashMap<>();
            Map<Integer, Column> skMap = new HashMap<>();
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
            Map<Integer, Column> pkColumnMap = new HashMap<>();
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

    private void getShareTable(Schema schema, Set<Table> tableSet, int i, String tableId) {
        IMetaModel metaModel = metaEngine.getMeta(false);
        Map<String, Schema> schemaMap = metaModel.getSchemas();
        Table table = null;
        for (Map.Entry<String, Schema> entry : schemaMap.entrySet()) {
            Schema tempSchema = entry.getValue();
            ITable tab = tempSchema.getTableById(tableId);
            if (tab != null) {
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

    public CommonResponse canDelete(JudgeCanDeleteRequest judgeCanDeleteRequest) {
        CommonResponse retVal = new CommonResponse();
        CommonDataBean result = new CommonDataBean();
        MetaModel model = metaEngine.getMeta(false);
        Schema schema = model.getSchema(judgeCanDeleteRequest.getSchemaName(), judgeCanDeleteRequest.getVersion());
        switch (judgeCanDeleteRequest.getType()) {
            case 0://判断数据类是否可以被删除
                int count = metaDaoFactory.getSchemaDAO().getCountByIslockAndTypeAndSchema(schema.getId());
                if (count > 0) {
                    result.setStatus(JudgeCanDeleteResponseEnum.TYPE_LOCKED_CAN_NOT_DELETE.toString());
                    retVal.setResult(result);
                    retVal.setSuccess(false);
                    retVal.setMsg("已经被上锁，无法删除");
                    return retVal;
                }
                count = metaDaoFactory.getSchemaDAO().getCountByMapTableAndIsvalid(judgeCanDeleteRequest.getTableName(), schema.getId());
                if (count <= 0) {
                    result.setStatus(JudgeCanDeleteResponseEnum.TYPE_CAN_DELETE.toString());
                    retVal.setResult(result);
                    return retVal;
                } else {
                    result.setStatus(JudgeCanDeleteResponseEnum.TYPE_HAS_DELETE.toString());
                    retVal.setResult(result);
                    retVal.setSuccess(false);
                    return retVal;
                }
            case 1://判断普通属性、统计属性是否可以删除
                int counts = metaDaoFactory.getSchemaDAO().getCountByIslockAndSchema(schema.getId());
                if (counts > 0) {
                    result.setStatus(JudgeCanDeleteResponseEnum.TYPE_LOCKED_CAN_NOT_DELETE.toString());
                    retVal.setResult(result);
                    retVal.setSuccess(false);
                    retVal.setMsg("已经被上锁，无法删除");
                    return retVal;
                }
                counts = metaDaoFactory.getSchemaDAO().getCountByMapColumnAndIsvalid(judgeCanDeleteRequest.getColumnName(), judgeCanDeleteRequest.getTableName(), schema.getId());
                if (counts <= 0) {
                    result.setStatus(JudgeCanDeleteResponseEnum.TYPE_CAN_DELETE.toString());
                    retVal.setResult(result);
                    return retVal;
                } else {
                    result.setStatus(JudgeCanDeleteResponseEnum.TYPE_HAS_DELETE.toString());
                    retVal.setResult(result);
                    retVal.setSuccess(false);
                    return retVal;
                }
            default:
                return null;
        }
    }

}
