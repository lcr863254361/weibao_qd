package com.orient.workflow.ext.deploy;

import org.jbpm.pvm.internal.wire.binding.WireDescriptorBinding;
import org.jbpm.pvm.internal.wire.descriptor.ObjectDescriptor;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;

/**
 * @ClassName EdmJpdlDeployerBinding
 * 部署绑定类
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class EdmJpdlDeployerBinding extends WireDescriptorBinding {

	public EdmJpdlDeployerBinding() {
		super("jpdl-deployer");
	}

	public Object parse(Element element, Parse parse, Parser parser) {
		return new ObjectDescriptor(EdmJpdlDeployer.class);
	}

}
