package com.orient.flow.extend.extensions;

import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.process.AbstractExtensionInfoExtractor;
import com.orient.flow.extend.process.Extension;
import org.springframework.beans.factory.config.BeanDefinitionHolder;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 * 2016-06-28 上午9:45
 */
public class FlowTaskExecutionEventListenerInfoExtractor extends AbstractExtensionInfoExtractor {

    @Override
    public Extension<FlowTaskExecutionEventMarker> extractFromBean(Object bean, String beanName) {
        Extension<FlowTaskExecutionEventMarker> extension = super.extractFromBean(bean, beanName);

        Class<?> beanClass = extension.getBeanClass();
        FlowTaskExecutionEventMarker marker = beanClass.getAnnotation(FlowTaskExecutionEventMarker.class);
        extension.setCustomAnnotation(marker);
        return extension;
    }
}
