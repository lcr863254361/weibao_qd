/**
 * DynamicLoadBean.java
 * com.orient.edm.util
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * Mar 30, 2012 		zhu longchao
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.edm.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * ClassName:DynamicLoadBean
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author Mengbin
 * @Date Mar 30, 2012		11:02:19 AM
 * @see
 * @since Ver 1.1
 */

public class DynamicLoadBean implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
//		this.applicationContext = (ConfigurableApplicationContext)applicationContext;
        this.applicationContext = (ConfigurableApplicationContext) arg0;
    }

    private ConfigurableApplicationContext applicationContext = null;

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * 向spring的beanFactory动态地装载bean
     *
     * @param configLocationString 要装载的bean所在的xml配置文件位置。
     *                             spring配置中的contextConfigLocation，同样支持诸如"/WEB-INF/ApplicationContext-*.xml"的写法。
     */
    public void loadBean(String configLocationString) {
        ConfigurableApplicationContext context = getApplicationContext();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) getApplicationContext().getBeanFactory());
        beanDefinitionReader.setResourceLoader(getApplicationContext());
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(getApplicationContext()));
        try {
            String[] configLocations = new String[]{configLocationString};
            for (int i = 0; i < configLocations.length; i++)
                beanDefinitionReader.loadBeanDefinitions(getApplicationContext().getResources(configLocations[i]));
        } catch (BeansException | IOException e) {
            e.printStackTrace();
        }
    }

}

