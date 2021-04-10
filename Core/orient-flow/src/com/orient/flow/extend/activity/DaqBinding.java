package com.orient.flow.extend.activity;

import org.jbpm.jpdl.internal.activity.JpdlBinding;
import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.pvm.internal.model.ScopeElementImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.xml.Parse;
import org.w3c.dom.Element;

public class DaqBinding extends JpdlBinding{

	private static final String TAG = "daq";
	
	public DaqBinding() {
		super(TAG); 
	}

	@Override
	public Object parseJpdl(Element element, Parse parse, JpdlParser parser) {
		DaqActivity daq = new DaqActivity();
		ScopeElementImpl scopeElement = parse.contextStackFind(ScopeElementImpl.class);
	    TaskDefinitionImpl taskDefinition = parser.parseTaskDefinition(element, parse, scopeElement);
		return daq;
	}

}
