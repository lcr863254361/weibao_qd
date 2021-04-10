package com.orient.dsrestful.eventparam;

import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metadomain.Schema;
import com.orient.web.base.OrientEventBus.OrientEventParams;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-03-31 10:12
 */
public class SaveSchemaParam extends OrientEventParams {

    private Schema schema;
    private Map<String, Column> columnMap;
    private Map<String, List<Column>> restrictionRefColumnsMap;
    private Map<Integer, String> createViewSqlMap;

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Map<String, Column> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, Column> columnMap) {
        this.columnMap = columnMap;
    }

    public Map<String, List<Column>> getRestrictionRefColumnsMap() {
        return restrictionRefColumnsMap;
    }

    public void setRestrictionRefColumnsMap(Map<String, List<Column>> restrictionRefColumnsMap) {
        this.restrictionRefColumnsMap = restrictionRefColumnsMap;
    }

    public Map<Integer, String> getCreateViewSqlMap() {
        return createViewSqlMap;
    }

    public void setCreateViewSqlMap(Map<Integer, String> createViewSqlMap) {
        this.createViewSqlMap = createViewSqlMap;
    }

}
