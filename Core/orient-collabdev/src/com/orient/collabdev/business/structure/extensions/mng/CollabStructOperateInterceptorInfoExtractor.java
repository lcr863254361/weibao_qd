package com.orient.collabdev.business.structure.extensions.mng;

import com.orient.flow.extend.process.AbstractExtensionInfoExtractor;
import com.orient.flow.extend.process.Extension;

public class CollabStructOperateInterceptorInfoExtractor extends AbstractExtensionInfoExtractor {

    @Override
    public Extension<CollabDevStructMarker> extractFromBean(Object bean, String beanName) {
        Extension<CollabDevStructMarker> extension = super.extractFromBean(bean, beanName);
        Class<?> beanClass = extension.getBeanClass();
        CollabDevStructMarker marker = beanClass.getAnnotation(CollabDevStructMarker.class);
        extension.setCustomAnnotation(marker);
        return extension;
    }
}
