package com.orient.sqlengine.extend.aspect;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.extend.ModelDataOperate;
import com.orient.sqlengine.extend.annotation.ModelOperateExtend;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/24 0024.
 * 针对模型数据操作的切面，内容如下：
 * 1.新增模型数据前
 * 2.新增模型数据后
 * 3.修改模型数据前
 * 4.修改模型数据后
 * 5.删除模型数据前
 * 6.删除模型数据后
 */
@Aspect
@Component
public class ModelDataOperateAspect {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ModelDataOperateAspect.class);

    @Pointcut("execution(* com.orient.sqlengine.api.IModelJdbcService.insertModelData(..)) ")
    public void insertModelData() {

    }

    @Pointcut("execution(* com.orient.sqlengine.api.IModelJdbcService.deleteCascade(..)) ")
    public void deleteCascadeModelData() {

    }

    @Pointcut("execution(* com.orient.sqlengine.api.IModelJdbcService.updateModelData(..)) ")
    public void updateModelData() {

    }

    @Pointcut("execution(* com.orient.sqlengine.api.IModelJdbcService.delete(..)) ")
    public void deleteModelData() {

    }

    /**
     * 新增模型数据环绕切面
     *
     * @param proceedingJoinPoint
     */
    @Around(value = "insertModelData()")
    public String doAroundInsert(ProceedingJoinPoint proceedingJoinPoint) {
        String result = "";
        //only aop the normal insert method
        Object[] args = proceedingJoinPoint.getArgs();
        Boolean aopFlag = args.length == 2;
        try {
            if (aopFlag) {
                IBusinessModel bm = (IBusinessModel) args[0];
                String schemaName = bm.getSchema().getName();
                String sModelName = bm.getName();
                List<ModelDataOperate> modelDataOperates = getAppropriateExtend(sModelName, schemaName);
                Map<String, String> dataMap = (Map<String, String>) args[1];
                modelDataOperates.forEach(modelDataOperate -> modelDataOperate.beforeAdd(bm, dataMap));
                result = (String) proceedingJoinPoint.proceed();
                final String finalResult = result;
                modelDataOperates.forEach(modelDataOperate -> modelDataOperate.afterAdd(bm, dataMap, finalResult));
            } else {
                return (String) proceedingJoinPoint.proceed();
            }
        } catch (Throwable throwable) {
            logger.error("模型数据拦截出错", throwable);
            if (throwable instanceof OrientBaseAjaxException) {
                OrientBaseAjaxException exception = (OrientBaseAjaxException) throwable;
                throw new OrientBaseAjaxException(exception.getErrorCode(), exception.getErrorMsg());
            } else
                throw new OrientBaseAjaxException("", "未知错误");
        }
        return result;
    }

    /**
     * 修改模型数据环绕切面
     *
     * @param proceedingJoinPoint
     */
    @Around(value = "updateModelData()")
    public Boolean doAroundUpdate(ProceedingJoinPoint proceedingJoinPoint) {
        Boolean retVal = false;
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            Boolean aopFlag = args.length == 3;
            if (aopFlag) {
                IBusinessModel bm = (IBusinessModel) args[0];
                String schemaName = bm.getSchema().getName();
                String sModelName = bm.getName();
                List<ModelDataOperate> modelDataOperates = getAppropriateExtend(sModelName, schemaName);
                Map<String, String> dataMap = (Map<String, String>) args[1];
                String dataId = (String) args[2];
                modelDataOperates.forEach(modelDataOperate -> modelDataOperate.beforeUpdate(bm, dataMap, dataId));
                retVal = (Boolean) proceedingJoinPoint.proceed();
                final Boolean finalRetVal = retVal;
                modelDataOperates.forEach(modelDataOperate -> modelDataOperate.afterUpdate(bm, dataMap, dataId, finalRetVal));
            } else {
                retVal = (Boolean) proceedingJoinPoint.proceed();
            }
        } catch (Throwable throwable) {
            logger.error("模型数据拦截出错", throwable);
            if (throwable instanceof OrientBaseAjaxException) {
                OrientBaseAjaxException exception = (OrientBaseAjaxException) throwable;
                throw new OrientBaseAjaxException(exception.getErrorCode(), exception.getErrorMsg());
            } else
                throw new OrientBaseAjaxException("", "未知错误");
        }
        return retVal;
    }

    /**
     * 删除模型数据环绕切面
     *
     * @param proceedingJoinPoint
     */
    @Around(value = "deleteModelData()")
    public void doAroundDelete(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            IBusinessModel bm = (IBusinessModel) args[0];
            String schemaName = bm.getSchema().getName();
            String sModelName = bm.getName();
            List<ModelDataOperate> modelDataOperates = getAppropriateExtend(sModelName, schemaName);
            String ids = (String) args[1];
            modelDataOperates.forEach(modelDataOperate -> modelDataOperate.beforeDelete(bm, ids));
            proceedingJoinPoint.proceed();
            modelDataOperates.forEach(modelDataOperate -> modelDataOperate.afterDelete(bm, ids));
        } catch (Throwable throwable) {
            logger.error("模型数据拦截出错", throwable);
            if (throwable instanceof OrientBaseAjaxException) {
                OrientBaseAjaxException exception = (OrientBaseAjaxException) throwable;
                throw new OrientBaseAjaxException(exception.getErrorCode(), exception.getErrorMsg());
            } else
                throw new OrientBaseAjaxException("", "未知错误");
        }

    }

    /**
     * 级联删除模型数据环绕切面
     *
     * @param proceedingJoinPoint
     */
    @Around(value = "deleteCascadeModelData()")
    public void doAroundDeleteCascade(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            IBusinessModel bm = (IBusinessModel) args[0];
            String schemaName = bm.getSchema().getName();
            String sModelName = bm.getName();
            List<ModelDataOperate> modelDataOperates = getAppropriateExtend(sModelName, schemaName);
            String ids = (String) args[1];
            modelDataOperates.forEach(modelDataOperate -> modelDataOperate.beforeDeleteCascade(bm, ids));
            proceedingJoinPoint.proceed();
            modelDataOperates.forEach(modelDataOperate -> modelDataOperate.afterDeleteCascade(bm, ids));
        } catch (Throwable throwable) {
            logger.error("模型数据拦截出错", throwable);
            if (throwable instanceof OrientBaseAjaxException) {
                OrientBaseAjaxException exception = (OrientBaseAjaxException) throwable;
                throw new OrientBaseAjaxException(exception.getErrorCode(), exception.getErrorMsg());
            } else
                throw new OrientBaseAjaxException("", "未知错误");
        }
    }

    /**
     * @param modelSName 待匹配的模型名称:T_TEST
     * @param schemaName 待匹配的模型库名称：数据模型
     * @return 符合条件的模型处理器
     */
    private List<ModelDataOperate> getAppropriateExtend(String modelSName, String schemaName) {
        Map<ModelDataOperate, Integer> toSortedData = new HashMap<>();
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(com.orient.sqlengine.extend.ModelDataOperate.class);
        for (String beanName : beanNames) {
            ModelDataOperate modelDataOperate = (ModelDataOperate) OrientContextLoaderListener.Appwac.getBean(beanName);
            Class operateClass = modelDataOperate.getClass();
            ModelOperateExtend operateClassAnnotation = (ModelOperateExtend) operateClass.getAnnotation(ModelOperateExtend.class);
            String[] modelNames = operateClassAnnotation.modelNames();
            String annotaSchemaName = operateClassAnnotation.schemaName();
            int order = operateClassAnnotation.order();
            List<String> modelNameList = CommonTools.arrayToList(modelNames);
            if (modelNameList.contains("*") || (annotaSchemaName.equals(schemaName) && modelNameList.contains(modelSName))) {
                toSortedData.put(modelDataOperate, order);
            }
        }
        List<ModelDataOperate> retVal = CommonTools.getSortedDataByValue(toSortedData);
        return retVal;
    }
}
