package com.orient.flow.extend.activity;

import org.jbpm.jpdl.internal.activity.JpdlBinding;
import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.pvm.internal.xml.Parse;
import org.w3c.dom.Element;

import com.orient.utils.XmlUtil;



/**
 * the edm end binding
 *
 * @author [创建人]  spf <br/> 
 * 		   [创建时间] 2014-8-7 上午9:02:35 <br/> 
 * 		   [修改人] spf <br/>
 * 		   [修改时间] 2014-8-7 上午9:02:35
 * @see
 */
public class EdmEndBinding  extends JpdlBinding {

  public EdmEndBinding() {
    super("end");
  }

  protected EdmEndBinding(String tag) {
    super(tag);
  }

  public Object parseJpdl(Element element, Parse parse, JpdlParser parser) {
    
    boolean endProcessInstance = true;
    String ends = XmlUtil.attribute(element, "ends", false, parse);
    if ("execution".equalsIgnoreCase(ends)) {
      endProcessInstance = false;
    }
    
    String state = XmlUtil.attribute(element, "state", false, parse);
    
    EdmEndActivity endActivity = new EdmEndActivity();
    endActivity.setEndProcessInstance(endProcessInstance);
    endActivity.setState(state);
    
    return endActivity;
  }
}
