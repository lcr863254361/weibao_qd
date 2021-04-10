package com.orient.collab.business.strategy;

import com.orient.utils.UtilFactory;

import java.util.Map;

import static com.orient.collab.config.CollabConstants.FUNC_MODULE_PLAN_MNG;
import static com.orient.collab.config.CollabConstants.FUNC_MODULE_PROJECT_MNG;

/**
 * the function point strategy
 *
 * @author Seraph
 *         2016-07-30 下午3:00
 */
public enum FunctionModule {

    PROJECT_MNG(FUNC_MODULE_PROJECT_MNG),
    PLAN_MNG(FUNC_MODULE_PLAN_MNG);

    FunctionModule(String name){
        this.name = name;
    }
    private final String name;
    @Override
    public String toString(){
        return name;
    }

    private static final Map<String, FunctionModule> stringToEnum = UtilFactory.newHashMap();
    static{
        for(FunctionModule s : values()){
            stringToEnum.put(s.toString(), s);
        }
    }

    public static FunctionModule fromString(String name){
        return stringToEnum.get(name);
    }
}
