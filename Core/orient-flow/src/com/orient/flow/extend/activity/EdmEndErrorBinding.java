package com.orient.flow.extend.activity;

import com.orient.utils.XmlUtil;
import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.pvm.internal.xml.Parse;
import org.w3c.dom.Element;

/**
 * EdmEndErrorBinding
 *
 * @author Seraph
 *         2016-08-23 上午11:30
 */
public class EdmEndErrorBinding extends EdmEndBinding {
    public EdmEndErrorBinding() {
        super("end-error");
    }

    public Object parseJpdl(Element element, Parse parse, JpdlParser parser) {
        boolean endProcessInstance = true;
        String ends = XmlUtil.attribute(element, "ends", false, parse);
        if ("execution".equalsIgnoreCase(ends)) {
            endProcessInstance = false;
        }

        String state = XmlUtil.attribute(element, "state", false, parse);

        EdmEndErrorActivity endActivity = new EdmEndErrorActivity();
        endActivity.setEndProcessInstance(endProcessInstance);
        endActivity.setState(state);

        return endActivity;
    }
}
