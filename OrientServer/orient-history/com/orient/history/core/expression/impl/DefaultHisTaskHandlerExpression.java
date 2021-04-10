package com.orient.history.core.expression.impl;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.history.core.annotation.HisTaskHandler;
import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.expression.IHisTaskHandlerExpresssion;
import com.orient.history.core.expression.context.Context;
import com.orient.history.core.expression.context.HisTaskHandlerContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
@Component
public class DefaultHisTaskHandlerExpression implements IHisTaskHandlerExpresssion {

    @Override
    public void expressHisTaskHandler(Context context) {
        HisTaskHandlerContext hisTaskHandlerContext = (HisTaskHandlerContext) context;
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(com.orient.history.core.binddata.handler.IBindDataHandler.class);
        List<Map<Integer, IBindDataHandler>> bindDataHandlers = new ArrayList<>();
        //过滤所有的绑定数据处理器
        for (String beanName : beanNames) {
            IBindDataHandler bindDataHandler = (IBindDataHandler) OrientContextLoaderListener.Appwac.getBean(beanName);
            Class bindDataHandlerClass = bindDataHandler.getClass();
            HisTaskHandler hisTaskHandler = (HisTaskHandler) bindDataHandlerClass.getAnnotation(HisTaskHandler.class);
            String[] matchTypes = hisTaskHandler.types();
            Boolean matched = validateMatched(hisTaskHandlerContext.getInputList(), matchTypes);
            if (matched) {
                bindDataHandlers.add(new HashMap<Integer, IBindDataHandler>() {{
                    put(hisTaskHandler.order(), bindDataHandler);
                }});
            }
        }
        //排序
        bindDataHandlers.stream().sorted((m1, m2) -> {
            return (Integer) m1.keySet().toArray()[0] - (Integer) m2.keySet().toArray()[0];
        });
        //组成职责链接
        final IBindDataHandler[] lastBindDataHandler = {null};
        bindDataHandlers.forEach(map -> {
            IBindDataHandler currentBindDataHandler = map.entrySet().iterator().next().getValue();
            currentBindDataHandler.setNextBindDataHandler(lastBindDataHandler[0]);
            lastBindDataHandler[0] = currentBindDataHandler;
        });
        hisTaskHandlerContext.setBindDataHandler(lastBindDataHandler[0]);
    }

    /**
     * @param inputList  输入语句
     * @param matchTypes 处理器所要处理的数据类型
     * @return 判断任务绑定数据处理器是否匹配
     */
    private Boolean validateMatched(List<String> inputList, String[] matchTypes) {
        Boolean retVal = false;
        for (String matchType : matchTypes) {
            if (inputList.contains(matchType)) {
                retVal = true;
                break;
            }
        }
        return retVal;
    }
}
