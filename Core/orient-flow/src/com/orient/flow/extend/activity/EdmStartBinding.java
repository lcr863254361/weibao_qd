package com.orient.flow.extend.activity;

import org.jbpm.jpdl.internal.activity.JpdlBinding;
import org.jbpm.jpdl.internal.model.JpdlProcessDefinition;
import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.xml.Parse;
import org.w3c.dom.Element;


/**
 * the start activity binding
 *
 * @author [创建人]  spf <br/> 
 * 		   [创建时间] 2014-8-5 下午2:15:04 <br/> 
 * 		   [修改人] spf <br/>
 * 		   [修改时间] 2014-8-5 下午2:15:04
 * @see
 */
public class EdmStartBinding extends JpdlBinding {

  public EdmStartBinding() {
    super("start");
  }

  public Object parseJpdl(Element element, Parse parse, JpdlParser parser) {
    ActivityImpl startActivity = parse.contextStackFind(ActivityImpl.class);
    JpdlProcessDefinition processDefinition = parse.contextStackFind(JpdlProcessDefinition.class);
    
    if (processDefinition.getInitial()==null) {
      processDefinition.setInitial(startActivity);
      
    } else if (startActivity.getParentActivity()==null) {
      parse.addProblem("multiple start events not yet supported", element);
    }
    
    EdmStartActivity startActivityBehaviour = new EdmStartActivity();
    
    startActivityBehaviour.setFormResourceName(XmlUtil.attribute(element, "form"));
    
    return startActivityBehaviour;
  }

  public boolean isNameRequired() {
    return false;
  }
}
