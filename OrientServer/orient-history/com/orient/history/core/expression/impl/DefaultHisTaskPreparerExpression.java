package com.orient.history.core.expression.impl;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.history.core.engine.prepare.IPrepareIntermediator;
import com.orient.history.core.engine.prepare.annotation.PrepareIntermediator;
import com.orient.history.core.expression.IHisTaskHandlerExpresssion;
import com.orient.history.core.expression.context.Context;
import com.orient.history.core.expression.context.HisTaskPreparerContext;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
@Component
public class DefaultHisTaskPreparerExpression implements IHisTaskHandlerExpresssion {

    @Override
    public void expressHisTaskHandler(Context context) {
        HisTaskPreparerContext hisTaskPreparerContext = (HisTaskPreparerContext) context;
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(IPrepareIntermediator.class);
        List<Map<Integer, IPrepareIntermediator>> prepareIntermediators = new ArrayList<>();
        //过滤所有的数据预处理器
        for (String beanName : beanNames) {
            IPrepareIntermediator prepareIntermediator = (IPrepareIntermediator) OrientContextLoaderListener.Appwac.getBean(beanName);
            Class prepareIntermediatorClass = prepareIntermediator.getClass();
            PrepareIntermediator pior = (PrepareIntermediator) prepareIntermediatorClass.getAnnotation(PrepareIntermediator.class);
            String[] matchTypes = pior.types();
            Boolean matched = validateMatched(hisTaskPreparerContext.getInputList(), matchTypes);
            if (matched) {
                prepareIntermediators.add(new HashMap<Integer, IPrepareIntermediator>() {{
                    put(pior.order(), prepareIntermediator);
                }});
            }
        }
        //排序
        List<Map<Integer, IPrepareIntermediator>> sortedPrepareIntermediators = prepareIntermediators.stream().sorted((m1, m2) -> (Integer) m1.keySet().toArray()[0] - (Integer) m2.keySet().toArray()[0]).collect(Collectors.toList());
        //组成职责链接
        final IPrepareIntermediator[] lastPrepareIntermediator = {null};
        sortedPrepareIntermediators.forEach(map -> {
            IPrepareIntermediator currentPrepareIntermediator = map.entrySet().iterator().next().getValue();
            currentPrepareIntermediator.setLastPrepareIntermediator(lastPrepareIntermediator[0]);
            lastPrepareIntermediator[0] = currentPrepareIntermediator;
        });
        hisTaskPreparerContext.setPrepareIntermediator(lastPrepareIntermediator[0]);
    }

    /**
     * @param inputList  输入语句
     * @param matchTypes 处理器所要处理的数据类型
     * @return 判断任务绑定数据处理器是否匹配
     */
    private Boolean validateMatched(List<String> inputList, String[] matchTypes) {
        Boolean retVal = false;
        for (String matchType : matchTypes) {
            //转化
            matchType = getRealType(matchType);
            if (inputList.contains(matchType)) {
                retVal = true;
                break;
            }
        }
        return retVal;
    }

    private String getRealType(String type) {
        if (!StringUtil.isEmpty(type) && type.indexOf("$$") != -1) {
            String[] taskTypeDesc = type.split("\\u0024\\u0024");
            if (taskTypeDesc.length == 2) {
                try {
                    Class configClass = Class.forName(taskTypeDesc[0]);
                    type = CommonTools.Obj2String(configClass.getDeclaredField(taskTypeDesc[1]).get(configClass));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return type;
    }
}
