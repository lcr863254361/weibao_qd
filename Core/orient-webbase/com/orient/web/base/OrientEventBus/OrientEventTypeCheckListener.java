/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.web.base.OrientEventBus;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import com.orient.web.base.OrientEventBus.OrientEventCheck;

/**
 * Created by mengbin on 16/3/30.
 * Purpose: 监测 licenster 的 supportsEventType 方法,判断该事件是否是OrientEvent
 * Detail:
 */
public class OrientEventTypeCheckListener {

    public Object execute(ProceedingJoinPoint call) throws Throwable{
        Object result = null;
        Object obj = call.getTarget();
        Object[]  args = call.getArgs();
        MethodSignature signature = (MethodSignature)  call.getSignature();
        OrientEventCheck orientCheck  = signature.getMethod().getAnnotation(OrientEventCheck.class);
        if (orientCheck!=null){
            if(orientCheck.checked()==true){
                if (obj instanceof OrientEventListener){
                    OrientEventListener listener = (OrientEventListener) obj;
                    if (args.length>0&&args[0] instanceof Class){
                        Class cls = (Class) args[0];
                        boolean isOrientEvent = listener.isOrientEvent(cls);
                        if(isOrientEvent==false){
                            return false;
                        }
                    }
                }
            }
        }

        result = call.proceed();
        return result;
    }
}
