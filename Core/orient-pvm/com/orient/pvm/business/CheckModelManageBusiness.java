package com.orient.pvm.business;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.sysmodel.domain.pvm.CwmSysCheckModelColumn;
import com.orient.sysmodel.service.pvm.ICheckModelManageService;
import com.orient.sysmodel.service.pvm.impl.CheckModelManageService;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.model.BaseNode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.orient.sysmodel.domain.pvm.CwmSysCheckmodelsetEntity;

/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class CheckModelManageBusiness extends BaseHibernateBusiness<CwmSysCheckmodelsetEntity> {

    @Autowired
    ICheckModelManageService checkModelManageService;

    @Override
    public ICheckModelManageService getBaseService() {
        return checkModelManageService;
    }

    /**
     * @param node 父节点Id
     * @return 获取所有schema下的所有含有检查字段模型的信息 采用树形方式展现
     */
    public List<BaseNode> getModelHasCheckNodes(String node) {
        List<BaseNode> retVal = new ArrayList<>();
        if ("-1".equals(node)) {
            //加载schema信息
            metaEngine.getMeta(false).getSchemas().forEach((schemaId, schema) -> {
                retVal.add(new BaseNode("schema-" + schema.getId(), schema.getName(), "icon-schema", false, true));
            });
        } else if (node.startsWith("schema")) {
            //加载通用分组节点
            String schemaId = node.substring(node.indexOf("-") + 1);
            retVal.add(new BaseNode("model-" + schemaId, "数据表", "icon-modelGroup", false, true));
        } else if (node.startsWith("model")) {
            //加载某schema下的表信息
            String schemaId = node.substring(node.indexOf("-") + 1);
            ISchema iSchema = metaEngine.getMeta(false).getISchemaById(schemaId);
            //表
            iSchema.getAllTables().forEach(iTable -> {
                boolean hasCheckColumn = false;

                List<IColumn> columns = iTable.getColumns();
                for(IColumn column:columns) {
                    if (column.getType().equals(IColumn.TYPE_CHECK)) {
                        hasCheckColumn = true;
                    }
                }

                if(hasCheckColumn) {
                    retVal.add(new BaseNode(iTable.getId(), iTable.getDisplayName(), "icon-model", true));
                }
            });
        }
        return retVal;
    }

    public ExtGridData<CwmSysCheckModelColumn> getModelCheckColumns(String modelId,String schemaid) {
        String schemaId = schemaid.substring(schemaid.indexOf("-") + 1);
        ISchema iSchema = metaEngine.getMeta(false).getISchemaById(schemaId);
        ITable iTable = iSchema.getTableById(modelId);
        List<IColumn> checkColumns = new ArrayList<>();
        List<String> checkColumnIds = new ArrayList<>();
        iTable.getColumns().forEach(iColumn -> {
            if(iColumn.getType().equals(IColumn.TYPE_CHECK)) {
                checkColumns.add(iColumn);
                checkColumnIds.add(iColumn.getId());
            }
        });
        //根据模型ID在中间表中查出记录
        List<CwmSysCheckmodelsetEntity> cwmSysCheckmodelsetEntities = checkModelManageService.list(Restrictions.eq("modelId", modelId));
        List<String> dataBaseRecordIds = new ArrayList<>();
        cwmSysCheckmodelsetEntities.forEach(entity->{
            dataBaseRecordIds.add(entity.getColumnId());
        });
        checkColumns.forEach(column->{
            if(!dataBaseRecordIds.contains(column.getId())) {//若中间表不含该检查字段记录,进行插入
                saveData(column,modelId);
            }
        });

        cwmSysCheckmodelsetEntities = checkModelManageService.list(Restrictions.eq("modelId", modelId));
        //保证数据库数据正确后进行数据的合并
        List<CwmSysCheckModelColumn> columns = new ArrayList<>();
        for(CwmSysCheckmodelsetEntity entity:cwmSysCheckmodelsetEntities) {
            if(!checkColumnIds.contains(entity.getColumnId())) {//若所有检查字段的id中不包含该记录的列id,则该记录为无效数据,跳过
                continue;
            }
            CwmSysCheckModelColumn column = new CwmSysCheckModelColumn();
            column.setCheckType(entity.getCheckType());
            column.setId(entity.getId());
            column.setModelId(entity.getModelId());
            column.setColumnId(entity.getColumnId());

            //设置绑定字段
            column.setIsRequired(entity.getIsRequired());
            if(column.getIsRequired()==null) {
                column.setIsRequiredName("非必填");
                column.setIsRequired(new BigDecimal(1));
            }else if("0".equals(column.getIsRequired().toString())){
                column.setIsRequiredName("必填");
            }else {
                column.setIsRequiredName("非必填");
            }

            //设置绑定照片
            column.setIsBindPhoto(entity.getIsBindPhoto());
            if(column.getIsBindPhoto()==null) {
                column.setIsBindPhotoName("不绑定");
                column.setIsBindPhoto(new BigDecimal(1));
            }else if("0".equals(column.getIsBindPhoto().toString())) {
                column.setIsBindPhotoName("绑定");
            }else {
                column.setIsBindPhotoName("不绑定");
            }


            for(IColumn iColumn:checkColumns) {
                if(iColumn.getId().equals(entity.getColumnId())) {
                    column.setColumnName(iColumn.getDisplayName());
                }
            }

            //设置默认格式
            if("0".equals(column.getCheckType().toString())) {
                column.setCheckTypeName("任意格式");
            }else if("1".equals(column.getCheckType().toString())) {
                column.setCheckTypeName("字符串");
            }else if("2".equals(column.getCheckType().toString())) {
                column.setCheckTypeName("勾选");
            }else if("3".equals(column.getCheckType().toString())) {
                column.setCheckTypeName("字符串加勾选");
            }
            columns.add(column);
        }

        ExtGridData<CwmSysCheckModelColumn> retVal = new ExtGridData<>();
        retVal.setTotalProperty(columns.size());
        retVal.setResults(columns);
        return retVal;
    }

    private void saveData(IColumn column,String modelId) {
        CwmSysCheckmodelsetEntity entity = new CwmSysCheckmodelsetEntity();
        entity.setColumnId(column.getId());
        entity.setModelId(modelId);
        entity.setCheckType(new BigDecimal(1));//默认格式第4种,字符串加勾选
        entity.setIsRequired(new BigDecimal(1));//默认为非必填项
        entity.setIsBindPhoto(new BigDecimal(1));//默认不绑定照片
        checkModelManageService.save(entity);
    }
}
