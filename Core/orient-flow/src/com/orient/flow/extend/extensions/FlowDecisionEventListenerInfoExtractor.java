package com.orient.flow.extend.extensions;

import com.orient.flow.extend.annotation.FlowDecisionEventMarker;
import com.orient.flow.extend.process.AbstractExtensionInfoExtractor;
import com.orient.flow.extend.process.Extension;

/**
 *
 */
public class FlowDecisionEventListenerInfoExtractor extends AbstractExtensionInfoExtractor {

    @Override
    public Extension<FlowDecisionEventMarker> extractFromBean(Object bean, String beanName) {
        Extension<FlowDecisionEventMarker> extension = super.extractFromBean(bean, beanName);
        Class<?> beanClass = extension.getBeanClass();
        FlowDecisionEventMarker marker = beanClass.getAnnotation(FlowDecisionEventMarker.class);
        extension.setCustomAnnotation(marker);
        return extension;
    }
}
