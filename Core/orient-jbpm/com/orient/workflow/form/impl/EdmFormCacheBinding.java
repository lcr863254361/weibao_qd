package com.orient.workflow.form.impl;

import org.jbpm.pvm.internal.wire.binding.WireDescriptorBinding;
import org.jbpm.pvm.internal.wire.descriptor.ObjectDescriptor;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;

/**
 * @ClassName EdmFormCacheBinding
 * 表单缓存类的绑定类
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class EdmFormCacheBinding extends WireDescriptorBinding{

	public EdmFormCacheBinding(){
		super("edmform-cache");
	}
	@Override
	public Object parse(Element element, Parse parse, Parser parser) {
		 ObjectDescriptor objectDescriptor = new ObjectDescriptor(EdmFormCacheImpl.class);
		 return objectDescriptor;
	}

}
