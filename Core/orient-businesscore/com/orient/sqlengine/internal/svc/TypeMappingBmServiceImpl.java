package com.orient.sqlengine.internal.svc;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.annotation.BindModel;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.businessmodel.service.impl.QueryOrder;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sqlengine.api.IModelJdbcService;
import com.orient.sqlengine.api.ITypeMappingBmService;
import com.orient.sqlengine.cmd.api.EDMCommandService;
import com.orient.sqlengine.internal.SqlEngineHelper;
import com.orient.sqlengine.internal.business.cmd.AddBusinessModelDataCmd;
import com.orient.sqlengine.util.BusinessDataConverter;
import com.orient.utils.CommonTools;
import com.orient.utils.Pair;
import com.orient.utils.UtilFactory;
import org.reflections.Reflections;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * a default implementation of {@link com.orient.sqlengine.api.ITypeMappingBmService}
 *
 * @author Seraph 2016-07-21 下午4:37
 */
public class TypeMappingBmServiceImpl implements ITypeMappingBmService, ApplicationListener<ContextRefreshedEvent> {

    @Override
    public <T> String insert(T bean) {
        Pair<IBusinessModel, Boolean> bmInfo = this.getBmByAnnotation(bean.getClass());

        Map<String, String> dataMap = BusinessDataConverter.convertBeanToRealColMap(bmInfo.fst, bean, bmInfo.snd);
        AddBusinessModelDataCmd cmd = new AddBusinessModelDataCmd(bmInfo.fst, dataMap);
        return SqlEngineHelper.Obj2String(commandService.execute(cmd));
    }

    @Override
    public <T> boolean update(T bean) {
        Pair<IBusinessModel, Boolean> bmInfo = this.getBmByAnnotation(bean.getClass());

        Map<String, String> dataMap = BusinessDataConverter.convertBeanToRealColMap(bmInfo.fst, bean, bmInfo.snd);

        return this.modelJdbcService.updateModelData(bmInfo.fst, dataMap, dataMap.get("ID"));
    }

    @Override
    public <T> List<T> get(Class<T> beanClass, CustomerFilter... filters) {
        return this.get(beanClass, QueryOrder.INVALID_ORDER, filters);
    }

    @Override
    public <T> List<T> get(Class<T> beanClass, QueryOrder order, CustomerFilter... filters) {
        Pair<IBusinessModel, Boolean> bmInfo = this.getBmByAnnotation(beanClass);

        Map<String, String> colSNameToRealNameMap = BusinessDataConverter.getColumnSNameToRealNameMap(bmInfo.fst);
        Map<String, String> filterNameToRealColNameMap = UtilFactory.newHashMap();
        if (bmInfo.snd) {
            Set<String> sNames = colSNameToRealNameMap.keySet();
            for (String colSName : sNames) {
                filterNameToRealColNameMap.put(colSName.replace("_", ""), colSNameToRealNameMap.get(colSName));
            }
        }
        for (CustomerFilter filter : filters) {
            filter.setFilterName(filterNameToRealColNameMap.get(filter.getFilterName().toUpperCase()));
            recursionConverToRealName(filter, filterNameToRealColNameMap, "getChild", "getParent");
            bmInfo.fst.appendCustomerFilter(filter);
        }

        IBusinessModelQuery businessModelQuery = this.modelJdbcService.createModelQuery(bmInfo.fst);

        if (QueryOrder.INVALID_ORDER.equals(order)) {
            return businessModelQuery.list(beanClass, bmInfo.snd);
        }

        String orderRealColName = filterNameToRealColNameMap.get(order.getColName());
        if (!CommonTools.isNullString(orderRealColName)) {
            if (order.isAsc()) {
                businessModelQuery.orderAsc(orderRealColName);
            } else {
                businessModelQuery.orderDesc(orderRealColName);
            }
        }

        return businessModelQuery.list(beanClass, bmInfo.snd);
    }

    private void recursionConverToRealName(CustomerFilter mainFilter, Map<String, String> filterNameToRealColNameMap, String... methodNames) {
        Class targetClass = mainFilter.getClass();
        for (String methodName : methodNames) {
            Method m = ReflectionUtils.findMethod(targetClass, methodName, null);
            CustomerFilter filter = (CustomerFilter) ReflectionUtils.invokeMethod(m, mainFilter, null);
            while (null != filter) {
                filter.setFilterName(filterNameToRealColNameMap.get(filter.getFilterName().toUpperCase()));
                filter = (CustomerFilter) ReflectionUtils.invokeMethod(m, filter, null);
            }
        }
    }


    @Override
    public <T> T getById(Class<T> beanClass, String dataId) {
        Pair<IBusinessModel, Boolean> bmInfo = this.getBmByAnnotation(beanClass);

        IBusinessModel bm = bmInfo.fst;
        bm.setReserve_filter(" AND ID = '" + dataId + "'");
        List<T> list = this.modelJdbcService.createModelQuery(bm).list(beanClass, bmInfo.snd);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public <T> void delete(Class<T> beanClass, String dataIds, boolean cascade) {
        Pair<IBusinessModel, Boolean> bmInfo = this.getBmByAnnotation(beanClass);

        if (cascade) {
            this.modelJdbcService.deleteCascade(bmInfo.fst, dataIds);
        } else {
            this.modelJdbcService.delete(bmInfo.fst, dataIds);
        }
    }

    @Override
    public <T> String getModelId(Class<T> beanClass) {
        BindModel bindModel = beanClass.getAnnotation(BindModel.class);
        String bindBmName = bindModel.modelName();
        String bindSchemaName = bindModel.schemaName();
        Schema schema = this.metaEngine.getMeta(false).getLastVersionOfName(bindSchemaName);
        String schemaId = schema.getId();

        IBusinessModel bm = this.businessModelService.getBusinessModelBySName(bindBmName, schemaId, EnumInter.BusinessModelEnum.Table);
        return bm.getId();
    }

    @Override
    public Class getModelBindClass(String modelName, String schemaMark, boolean schemaMarkIsName) {
        String schemaName = schemaMark;
        if (!schemaMarkIsName) {
            ISchema schema = this.metaEngine.getMeta(false).getISchemaById(schemaMark);
            schemaName = schema.getName();
        }

        return this.modelBindClassMap.get(schemaName + "_" + modelName);
    }

    private Pair<IBusinessModel, Boolean> getBmByAnnotation(Class<?> beanCls) {

        BindModel bindModel = beanCls.getAnnotation(BindModel.class);
        String bindBmName = bindModel.modelName();
        String bindSchemaName = bindModel.schemaName();
        Schema schema = this.metaEngine.getMeta(false).getLastVersionOfName(bindSchemaName);
        String schemaId = schema.getId();

        IBusinessModel bm = this.businessModelService.getBusinessModelBySName(bindBmName, schemaId, EnumInter.BusinessModelEnum.Table);
        Pair<IBusinessModel, Boolean> bmInfo = new Pair(bm, bindModel.mapUnderscoreToCamelCase());

        return bmInfo;
    }

    public void setModelJdbcService(IModelJdbcService modelJdbcService) {
        this.modelJdbcService = modelJdbcService;
    }

    public void setBusinessModelService(IBusinessModelService businessModelService) {
        this.businessModelService = businessModelService;
    }

    public void setMetaEngine(MetaUtil metaEngine) {
        this.metaEngine = metaEngine;
    }

    public void setCommandService(EDMCommandService commandService) {
        this.commandService = commandService;
    }

    private IBusinessModelService businessModelService;
    private IModelJdbcService modelJdbcService;
    private MetaUtil metaEngine;
    private EDMCommandService commandService;

    private Map<String, Class> modelBindClassMap = new HashMap(32);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            return;
        }

        this.initModelBindClassMap();
    }

    private synchronized void initModelBindClassMap() {
        Reflections reflections = new Reflections("com.orient");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(BindModel.class);

        for (Class<?> model : annotated) {
            BindModel bindModel = model.getAnnotation(BindModel.class);
            String bindBmName = bindModel.modelName();
            String bindSchemaName = bindModel.schemaName();

            this.modelBindClassMap.put(bindSchemaName + "_" + bindBmName, model);
        }
    }

}
