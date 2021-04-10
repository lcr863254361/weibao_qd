package com.orient.history.core.handlerchain.impl;

import com.orient.history.core.binddata.handler.IBindDataHandler;
import com.orient.history.core.expression.context.HisTaskHandlerContext;
import com.orient.history.core.expression.IHisTaskHandlerExpresssion;
import com.orient.history.core.util.HisTaskConstants;
import com.orient.history.core.util.HisTaskThreadLocalHolder;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
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
public class ConstructHandlerChain implements com.orient.history.core.handlerchain.ConstructHandlerChain {

    @Autowired
    IFlowDataRelationService flowDataRelationService;

    @Autowired
    IHisTaskHandlerExpresssion defaultHisTaskHandlerExpression;

    @Override
    public IBindDataHandler getHandlerChain(String taskId) {

        String frontRequestKey = taskId + HisTaskConstants.FRONT_REQUEST_KEY;
        HisTaskHandlerContext hisTaskHandlerContext = new HisTaskHandlerContext(new ArrayList<>());
        try {
            List<String> inputList = new ArrayList<String>() {{
                //默认过滤条件
                add("*");
            }};
            Field[] fields = IBindDataHandler.class.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    //静态变量
                    if(null != HisTaskThreadLocalHolder.get(taskId + HisTaskConstants.TASK_BIND_MID + field.get(null).toString())){
                        inputList.add(field.get(null).toString());
                    }
                }
            }
            //交于解释器 解释绑定数据处理类信息
            hisTaskHandlerContext.setInputList(inputList);
            defaultHisTaskHandlerExpression.expressHisTaskHandler(hisTaskHandlerContext);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return hisTaskHandlerContext.getBindDataHandler();
    }
}
