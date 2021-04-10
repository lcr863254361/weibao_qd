package com.orient.flow.extend.process;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * extract the extension informations form bean definition
 *
 * @author Seraph
 * 2016-06-28 上午9:16
 */
public interface ExtensionInfoExtractor {

    <T> Extension<T> extractFromBean(Object bean, String beanName);
}
