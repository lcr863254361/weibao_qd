package com.orient.edm.init;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Created by mengbin on 16/2/29.
 */
public class InitLoadStart {

    private List<Object> startLoadBeans;

    public List<Object> getStartLoadBeans() {
        return startLoadBeans;
    }

    public void setStartLoadBeans(List<Object> startLoadBeans) {
        this.startLoadBeans = startLoadBeans;
    }


    public boolean loadModules(ApplicationContext context) {

        for (Object obj : startLoadBeans) {
            String beanName = (String) obj;
            IContextLoadRun run = (IContextLoadRun) context.getBean(beanName);
            boolean bSuc = run.modelLoadRun((WebApplicationContext) context);
            if (!bSuc) {
                return false;
            }
        }
        return true;
    }

}
