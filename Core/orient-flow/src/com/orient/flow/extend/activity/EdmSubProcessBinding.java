package com.orient.flow.extend.activity;

import org.jbpm.jpdl.internal.activity.*;
import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.pvm.internal.el.Expression;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.wire.Descriptor;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.xml.WireParser;
import org.jbpm.pvm.internal.xml.Parse;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class EdmSubProcessBinding extends SubProcessBinding {

    public EdmSubProcessBinding() {
        super();
    }

    public Object parseJpdl(Element element, Parse parse, JpdlParser parser) {
        SubProcessActivity subProcessActivity = new EdmSubProcessActivity();

        String subProcessKey = XmlUtil.attribute(element, "sub-process-key");
        subProcessActivity.setSubProcessKey(subProcessKey);

        String subProcessId = XmlUtil.attribute(element, "sub-process-id");
        subProcessActivity.setSubProcessId(subProcessId);

        List<SubProcessInParameterImpl> inParameters = new ArrayList<SubProcessInParameterImpl>();
        for (Element inElement: XmlUtil.elements(element, "parameter-in")) {
            SubProcessInParameterImpl inParameter = new SubProcessInParameterImpl();
            parseParameterCustom(inElement, inParameter);
            inParameters.add(inParameter);

            if (inParameter.getSubVariableName()==null) {
                parse.addProblem("no 'subvar' specified for parameter-in", element);
            }
            if ( (inParameter.getExpression()==null)
                    && (inParameter.getVariableName()==null)
                    ) {
                parse.addProblem("no 'expr' or 'variable' specified for parameter-in '"+inParameter.getSubVariableName()+"'", element);
            }
            if ( (inParameter.getExpression()!=null)
                    && (inParameter.getVariableName()!=null)
                    ) {
                parse.addProblem("attributes 'expr' and 'variable' are mutually exclusive on parameter-in", element);
            }
        }
        subProcessActivity.setInParameters(inParameters);

        List<SubProcessOutParameterImpl> outParameters = new ArrayList<SubProcessOutParameterImpl>();
        for (Element outElement: XmlUtil.elements(element, "parameter-out")) {
            SubProcessOutParameterImpl outParameter = new SubProcessOutParameterImpl();
            parseParameterCustom(outElement, outParameter);
            outParameters.add(outParameter);

            if (outParameter.getVariableName()==null) {
                parse.addProblem("no 'variable' specified for parameter-in", element);
            }
            if ( (outParameter.getExpression()==null)
                    && (outParameter.getSubVariableName()==null)
                    ) {
                parse.addProblem("no 'expr' or 'subvar' specified for parameter-out '"+outParameter.getVariableName()+"'", element);
            }
            if ( (outParameter.getExpression()!=null)
                    && (outParameter.getSubVariableName()!=null)
                    ) {
                parse.addProblem("attributes 'expr' and 'subvar' are mutually exclusive on parameter-out '"+outParameter.getVariableName()+"'", element);
            }
        }
        subProcessActivity.setOutParameters(outParameters);

        Map<String, String> swimlaneMappings = parseSwimlaneMappings(element, parse);
        subProcessActivity.setSwimlaneMappings(swimlaneMappings);

        Map<Object, String> outcomeVariableMappings = new HashMap<Object, String>();

        String outcomeExpression = XmlUtil.attribute(element, "outcome");
        if (outcomeExpression!=null) {
            subProcessActivity.setOutcomeExpression( Expression.create(outcomeExpression, "JS"));

            for (Element transitionElement: XmlUtil.elements(element, "transition")) {
                Element outcomeValueElement = XmlUtil.element(transitionElement, "outcome-value");
                if (outcomeValueElement!=null) {
                    String transitionName = XmlUtil.attribute(transitionElement, "name");
                    if (transitionName==null) {
                        parse.addProblem("transitions with an outcome-value must have a name", transitionElement);
                    }
                    Element valueElement = XmlUtil.element(outcomeValueElement);
                    if (valueElement!=null) {
                        Descriptor descriptor = (Descriptor) WireParser.getInstance().parseElement(valueElement, parse);
                        Object value = WireContext.create(descriptor);
                        outcomeVariableMappings.put(value, transitionName);
                    } else {
                        parse.addProblem("outcome-value must contain exactly one element", outcomeValueElement);
                    }
                }
            }

        }

        return subProcessActivity;
    }

    void parseParameterCustom(Element element, SubProcessParameterImpl parameter) {
        String name = XmlUtil.attribute(element, "subvar");
        parameter.setSubVariableName(name);

        String expr = XmlUtil.attribute(element, "expr");
        if (expr!=null) {
            parameter.setExpression( Expression.create(expr, "JS"));
        }

        String variable = XmlUtil.attribute(element, "var");
        if (variable!=null) {
            parameter.setVariableName(variable);
        }
    }
}
