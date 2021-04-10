package com.orient.dsrestful.listener.updateSchema;

import com.orient.dsrestful.event.UpdateSchemaEvent;
import com.orient.dsrestful.eventparam.UpdateSchemaParam;
import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.metaengine.ErrorInfo;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 校验字段是否符合规则
 * Created by GNY on 2018/4/2
 */
@Component
public class VerifyTableColumnListener extends OrientEventListener {

    @Autowired
    MetaDAOFactory metaDAOFactory;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == UpdateSchemaEvent.class || UpdateSchemaEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        UpdateSchemaParam param = (UpdateSchemaParam) orientEvent.getParams();
        Schema newSchema = param.getNewSchema();
        Schema oldSchema = param.getOldSchema();
        newSchema.setId(oldSchema.getId());
        //1.校验字段修改是否符合规则
        for (Table table : newSchema.getTables()) {
            ErrorInfo errorInfo = verifyTableColumn(table);
            if (errorInfo.ErrorId != 0) {
                throw new OrientBaseAjaxException("-1", errorInfo.Warning);
            }
        }
    }

    /**
     * 查看Table的Column是否有修改
     *
     * @param table
     * @return ErrorInfo
     */
    public ErrorInfo verifyTableColumn(Table table) {
        ErrorInfo info = new ErrorInfo();
        if (table.getChildTables().size() != 0) {
            Set<Table> childTables = table.getChildTables();
            for (Table childTable : childTables) {
                info = verifyTableColumn(childTable);
                if (info.ErrorId != 0) {
                    return info;
                }
            }
        }
        for (Column column : table.getCwmTabColumnses()) {
            if (column.getPropertyCategory() != null && !column.getPropertyCategory().equals("0")) {
                if (column.getCategory().equals(Column.CATEGORY_COMMON)) {
                    info = verifyDetail(column);
                    if (info.ErrorId != 0) {
                        return info;
                    }
                }
            }
        }
        return info;
    }

    /**
     * 确认column的修改是否符合要求
     *
     * @param tabColumn
     * @return ErrorInfo 1: 有了数据不能设置为全文检索  2：无法修改数据类型  3：String数据的长度无法修改
     */
    private ErrorInfo verifyDetail(Column tabColumn) {
        ErrorInfo info = new ErrorInfo();
        Schema newSchema = tabColumn.getTable().getSchema();
        try {
            String sql = "SELECT ID,S_COLUMN_NAME,DISPLAY_NAME,S_NAME,TABLE_ID FROM CWM_TAB_COLUMNS " +
                    "WHERE S_NAME='" + tabColumn.getName() + "' AND TABLE_ID IN (SELECT ID FROM CWM_TABLES WHERE SCHEMA_ID='"
                    + newSchema.getId() + "' AND S_NAME='" + tabColumn.getTable().getName() + "') AND CATEGORY='" + Column.CATEGORY_COMMON + "' AND IS_VALID='1'";

            List<Map<String, Object>> dbColumnRecordList = metaDAOFactory.getJdbcTemplate().queryForList(sql);
            List<Column> dbColumnList = new ArrayList<>();
            if (!dbColumnRecordList.isEmpty()) {
                for (Map record : dbColumnRecordList) {
                    String columnId = (String) record.get("ID");
                    Column dbColumn = metaDAOFactory.getColumnDAO().findById(columnId);
                    if (dbColumn != null) {
                        dbColumnList.add(dbColumn);
                    }
                }
            }
            for (Column dbColumn : dbColumnList) {
                if (dbColumn.getIsValid().equals(Column.ISVAILD_VALID)) {
                    Table dbTable = dbColumn.getTable();
                    //查询业务表是否已经有数据
                    String SQL = "SELECT COUNT (*) A FROM " + dbTable.getTableName() + " WHERE " + dbColumn.getColumnName() + " IS NOT NULL";
                    if (metaDAOFactory.getJdbcTemplate().queryForObject(SQL, Integer.class) != 0) { //已经有数据
                        //业务表如果有数据，就不允许修改字段长度
                        info.Warning = "数据表'" + dbTable.getDisplayName() + "(" + dbTable.getName() + ")'的字段'" + dbColumn.getDisplayName() + "(" + dbColumn.getName() + ")'含有数据，无法修改该字符型字段的长度，请重新从数据库打开数据模型编辑并保存";
                        info.ErrorId = 3;
                        return info;
                       /* if (tabColumn.getPropertyCategory().equals("1")) {
                            info.Warning = "数据表'" + dbTable.getDisplayName() + "(" + dbTable.getName() + ")'的字段'" + dbColumn.getDisplayName() + "(" + dbColumn.getName() + ")'含有数据，无法修改该字段为全文检索属性，请重新从数据库打开数据模型编辑并保存";
                            info.ErrorId = 1;
                            return info;
                        } else if (tabColumn.getPropertyCategory().equals("2")) {
                            info.Warning = "数据表'" + dbTable.getDisplayName() + "(" + dbTable.getName() + ")'的字段'" + dbColumn.getDisplayName() + "(" + dbColumn.getName() + ")'含有数据，无法修改该字段的数据类型，请重新从数据库打开数据模型编辑并保存";
                            info.ErrorId = 2;
                            return info;
                        } else if (tabColumn.getPropertyCategory().equals("3") && tabColumn.getType().equals("String")) {

                        }*/
                    }
                }
            }
        } catch (Exception e) {
            info.ErrorId = -1;
            info.Warning = e.toString();
            return info;
        }
        return info;
    }

}
