package com.orient.collab.business.projectCore.extensions.mng;

import com.orient.flow.extend.process.AbstractExtensionInfoExtractor;
import com.orient.flow.extend.process.Extension;

/**
 * CollabProcessInterceptorInfoExtractor
 *
 * @author Seraph
 *         2016-08-12 下午2:33
 */
public class CollabProcessInterceptorInfoExtractor extends AbstractExtensionInfoExtractor {

    @Override
    public Extension<CollabProcessMarker> extractFromBean(Object bean, String beanName) {
        Extension<CollabProcessMarker> extension = super.extractFromBean(bean, beanName);

        Class<?> beanClass = extension.getBeanClass();
        CollabProcessMarker marker = beanClass.getAnnotation(CollabProcessMarker.class);
        extension.setCustomAnnotation(marker);
        return extension;
    }
}
