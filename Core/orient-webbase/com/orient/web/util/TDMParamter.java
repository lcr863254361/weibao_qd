package com.orient.web.util;

import com.orient.edm.init.IContextLoadRun;
import com.orient.sysmodel.domain.sys.Parameter;
import com.orient.sysmodel.service.sys.IParamterService;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-29 15:02
 */
@Component("TDMParamter")
public class TDMParamter implements IContextLoadRun {

    IParamterService paramterService;

    private List<String> configClasses = new ArrayList<>();

    private Map<String, Object> configMap = new HashMap<>();

    public TDMParamter() {
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean modelLoadRun(WebApplicationContext contextLoad) {
        this.initParamter();
        return true;
    }

    private void initParamter() {
        Integer pageSize = Integer.valueOf(paramterService.get(Restrictions.eq("name", "PageNumber")).getValue());
        setPageSize(pageSize);

        Parameter logOn = paramterService.get(Restrictions.eq("name", "LogOn"));//.getValue();
        if (logOn != null) {
            String result = logOn.getValue();
            if ("0".equals(result)) {
                setIsLogOn(true);
            } else if ("1".equals(result)) {
                setIsLogOn(false);
            }
        } else {
            setIsLogOn(false);
        }

        configClasses.forEach(configClassName -> {
            try {
                Class configClass = Class.forName(configClassName);
                Field[] fields = configClass.getDeclaredFields();
                for (Field field : fields) {
                    configMap.put(field.getName(), field.get(configClass));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    //日志开关
    private static boolean isLogOn;

    private static void setIsLogOn(boolean isLogOn) {
        TDMParamter.isLogOn = isLogOn;
    }

    public static boolean getIsLogOn() {
        return isLogOn;
    }

    private static Integer pageSize = 25;

    private static void setPageSize(Integer pageSize) {
        TDMParamter.pageSize = pageSize;
    }

    public static Integer getPageSize() {
        return pageSize;
    }

    public Map<String, Object> getConfigMap() {
        return configMap;
    }


    public IParamterService getParamterService() {
        return paramterService;
    }

    public void setParamterService(IParamterService paramterService) {
        this.paramterService = paramterService;
    }

    public List<String> getConfigClasses() {
        return configClasses;
    }

    public void setConfigClasses(List<String> configClasses) {
        this.configClasses = configClasses;
    }
}
