package com.orient.flow.extend.activity;

import org.w3c.dom.Element;
import org.jbpm.jpdl.internal.activity.JpdlBinding; 
import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.pvm.internal.model.ScopeElementImpl;
import org.jbpm.pvm.internal.task.TaskDefinitionImpl;
import org.jbpm.pvm.internal.xml.Parse;

/**
 * @ClassName EdmTaskBinding
 * 流程活动的解析
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class EdmTaskBinding extends JpdlBinding {

	  private static final String TAG = "task";

	  public EdmTaskBinding() {
	    super(TAG);
	  }

	  public Object parseJpdl(Element element, Parse parse, JpdlParser parser) {
		EdmTaskActivity taskActivity = new EdmTaskActivity();

	    ScopeElementImpl scopeElement = parse.contextStackFind(ScopeElementImpl.class);
	    TaskDefinitionImpl taskDefinition = parser.parseTaskDefinition(element, parse, scopeElement);
	    
	    taskActivity.setTaskDefinition(taskDefinition);
	    
	    return taskActivity;
	  }
}
