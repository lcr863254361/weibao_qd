package com.orient.history.core.handlerchain.impl;

import com.orient.history.core.engine.prepare.IPrepareIntermediator;
import com.orient.history.core.expression.IHisTaskHandlerExpresssion;
import com.orient.history.core.expression.context.HisTaskPreparerContext;
import com.orient.history.core.handlerchain.ConstructPreparerChain;
import com.orient.history.core.util.HisTaskTypeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
@Component
public class ConstructPrepareChain implements ConstructPreparerChain {

    @Autowired
    IHisTaskHandlerExpresssion defaultHisTaskPreparerExpression;

    @Override
    public IPrepareIntermediator getPrepareChain(String taskType) {

        HisTaskPreparerContext context = new HisTaskPreparerContext(new ArrayList<>());
        List<String> inputList = new ArrayList<String>() {{
            //默认过滤条件
            add("*");
        }};
        try {
            Field[] fields = HisTaskTypeConstants.class.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    String value = field.get(null).toString();
                    if(taskType.equalsIgnoreCase(value))
                    inputList.add(value);
                }
            }
            context.setInputList(inputList);
            defaultHisTaskPreparerExpression.expressHisTaskHandler(context);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return context.getPrepareIntermediator();
    }
}
