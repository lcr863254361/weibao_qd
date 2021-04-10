package com.orient.sqlengine.util;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.BaseDsModel;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;
import com.sun.faces.util.Util;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.castor.util.concurrent.ConcurrentHashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Seraph on 16/6/8
 */
public class BusinessDataConverter {

    /**
     * the bean properties only support String type now
     * @param bm
     * @param mapList
     * @param beanClass
     * @param mapUnderscoreToCamelCase
     * @param <E>
     * @return
     */
    public static <E> List<E> convertMapListToBeanList(IBusinessModel bm, List<Map<String, Object>> mapList, Class<E> beanClass, boolean mapUnderscoreToCamelCase){
        List<E> retV = UtilFactory.newArrayList();
        ObjectFactory objectFactory = new DefaultObjectFactory();
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

        Map<String, String> colRealNameToSNameMap = getColumnRealNameToSNameMap(bm);
        Set<String> colRealNames = colRealNameToSNameMap.keySet();

        for(Map<String, Object> dataMap : mapList){
            E bean = objectFactory.create(beanClass);
            retV.add(bean);

            MetaObject metaObject = MetaObject.forObject(bean, objectFactory, objectWrapperFactory);
            for(String colRealName:colRealNames){
                Object value = dataMap.get(colRealName);
                final String property = metaObject.findProperty(colRealNameToSNameMap.get(colRealName), mapUnderscoreToCamelCase);
                if (property != null && metaObject.hasSetter(property)) {

                    metaObject.setValue(property, value);
                }
            }

            if(BaseDsModel.class.isAssignableFrom(beanClass)){
                ((BaseDsModel)bean).setRawData(dataMap);
            }
        }
        return retV;
    }

    public static Map<String, String> convertBeanToRealColMap(IBusinessModel bm, Object bean, boolean rejectEmptyValue, boolean useCamelCaseMapping) {
        Map<String, String> retV = UtilFactory.newHashMap();
        Map<String, String> colSNameToRealNameMap = getColumnSNameToRealNameMap(bm);
        Set<String> colSNames = colSNameToRealNameMap.keySet();

        ObjectFactory objectFactory = new DefaultObjectFactory();
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
        MetaObject metaObject = MetaObject.forObject(bean, objectFactory, objectWrapperFactory);
        for(String sName : colSNames){
            String property = metaObject.findProperty(sName, useCamelCaseMapping);

            if(property !=null){
                String value = CommonTools.Obj2String(metaObject.getValue(property));
                if(property.equalsIgnoreCase("ID") && CommonTools.isNullString(value)){
                    continue;
                }

                if(rejectEmptyValue && CommonTools.isNullString(value)){
                    continue;
                }
                retV.put(colSNameToRealNameMap.get(sName), value);
            }
        }

        return retV;
    }

    public static Map<String, String> convertBeanToRealColMap(IBusinessModel bm, Object bean, boolean useCamelCaseMapping) {
        return convertBeanToRealColMap(bm, bean, false, useCamelCaseMapping);
    }

    /**
     * @param mainModel
     * @return
     */
    public static Map<String, String> getColumnRealNameToSNameMap(IBusinessModel mainModel){
        Map<String, String> mainRealNameToSNameMap = columnRealNameToSNameMapCache.get(mainModel.getId());
        if(mainRealNameToSNameMap!=null){
            return mainRealNameToSNameMap;
        }

        mainRealNameToSNameMap = UtilFactory.newHashMap();

        List<IBusinessColumn> allBcs = mainModel.getAllBcCols();
        for(IBusinessColumn bc: allBcs){
            IColumn col = bc.getCol();
            if(mainModel.getModelType() == EnumInter.BusinessModelEnum.Table){
                mainRealNameToSNameMap.put(bc.getS_column_name(), col.getName());
            }else{
                mainRealNameToSNameMap.put(bc.getS_column_name(), col.getRefMatrix().getName() + "." + col.getName());
            }
        }

        mainRealNameToSNameMap.put("ID", "ID");
        columnRealNameToSNameMapCache.put(mainModel.getId(), mainRealNameToSNameMap);
        return mainRealNameToSNameMap;
    }

    public static Map<String, String> getColumnSNameToRealNameMap(IBusinessModel mainModel){
        Map<String, String> columnSNameToRealNameMap = columnSNameToRealNameMapCache.get(mainModel.getId());
        if(columnSNameToRealNameMap!=null){
            return columnSNameToRealNameMap;
        }

        Map<String, String> sNameToRealNameMap = UtilFactory.newHashMap();

        List<IBusinessColumn> allBcs = mainModel.getAllBcCols();
        for(IBusinessColumn bc: allBcs){
            IColumn col = bc.getCol();
            sNameToRealNameMap.put(col.getName(), bc.getS_column_name());
        }

        sNameToRealNameMap.put("ID", "ID");
        columnSNameToRealNameMapCache.put(mainModel.getId(), sNameToRealNameMap);
        return sNameToRealNameMap;
    }

    private static final Map<String, Map<String, String>> columnSNameToRealNameMapCache = new ConcurrentHashMap();
    private static final Map<String, Map<String, String>> columnRealNameToSNameMapCache = new ConcurrentHashMap();
}
