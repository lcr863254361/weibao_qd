package com.orient.flow.extend;

import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.flow.extend.process.Extension;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 * 2016-06-27 下午2:32
 */
public interface ExtensionManager {

     List<Extension> getExtensions(Class<?> extensionType);
}
