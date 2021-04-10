package com.orient.modeldata.persistent;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.modeldata.persistent.validator.Validator;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-08 10:42
 */
public class PersistentContext {
    private String tableName;

    private List<String> columnNames;

    private List<Map<String, Object>> columnMapping;

    private List<Validator> validators = new ArrayList<>();

    private PersistentService persistentService;

    private IBusinessModelService businessModelService;

    public PersistentContext(String tableName, List<String> columnNames, List<Map<String, Object>> columnMapping) {
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.columnMapping = columnMapping;
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        this.persistentService = wac.getBean(PersistentService.class);
        this.businessModelService = wac.getBean(IBusinessModelService.class);
    }

    private void initValidators() {
        this.validators = new ArrayList<>();
        List<IBusinessColumn> bcList = getAllBusinessColumns(this.tableName);
        for(IBusinessColumn bc : bcList) {
            List<Validator> list = Validator.buildValidator(bc);
            this.validators.addAll(list);
        }
    }

    public String doValidate() {
        if(this.validators.size() == 0) {
            initValidators();
        }
        for(Map<String, Object> map : this.columnMapping) {
            for(Validator validator : this.validators) {
                String info = validator.validate(map);
                if(info != null) {
                    return info;
                }
            }
        }
        return null;
    }

    private List<IBusinessColumn> getAllBusinessColumns(String tableName) {
        String table = tableName.substring(0, tableName.lastIndexOf("_"));
        String schemaId = tableName.substring(tableName.lastIndexOf("_") + 1);
        IBusinessModel bm = businessModelService.getBusinessModelBySName(table, schemaId, EnumInter.BusinessModelEnum.Table);
        List<IBusinessColumn> cols = bm.getAllBcCols();
        return cols;
    }

    public int doPersistent() {
        String sql = this.sqlBuilder();
        return persistentService.batchInsert(sql, this.columnNames, this.columnMapping);
    }

    public String sqlBuilder() {
        String column = "ID";
        String holder = "SEQ_"+this.tableName+".nextval";

        for(int i=0; i<columnNames.size(); i++) {
            column = column+","+columnNames.get(i);
            holder = holder+",?";
        }

        return "insert into "+tableName+"(" + column + ") values (" + holder + ")";
    }
}
