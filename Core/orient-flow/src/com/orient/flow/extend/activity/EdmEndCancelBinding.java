package com.orient.flow.extend.activity;

import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.pvm.internal.xml.Parse;
import org.w3c.dom.Element;

/**
 * EdmEndCancelBinding
 *
 * @author Seraph
 *         2016-08-23 上午11:29
 */
public class EdmEndCancelBinding extends EdmEndBinding {
    public EdmEndCancelBinding() {
        super("end-cancel");
    }

    public Object parseJpdl(Element element, Parse parse, JpdlParser parser) {
        EdmEndActivity endActivity = (EdmEndActivity)super.parseJpdl(element, parse, parser);
        endActivity.setState("cancel");
        return endActivity;
    }
}