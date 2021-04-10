package com.orient.flow.extend.process;

import com.orient.flow.extend.annotation.CommentInfo;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * the base extractor
 *
 * @author Seraph
 * 2016-06-28 上午10:51
 */
public abstract class AbstractExtensionInfoExtractor implements ExtensionInfoExtractor {

    @Override
    public <T> Extension<T> extractFromBean(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();

        Extension.Builder<T> extensionBuilder = new Extension.Builder<>(beanName, beanClass);

        CommentInfo commentInfo = beanClass.getAnnotation(CommentInfo.class);
        if(commentInfo != null){
            extensionBuilder.displayName(commentInfo.displayName());
            extensionBuilder.description(commentInfo.description());
        }
        return extensionBuilder.build();
    }
}