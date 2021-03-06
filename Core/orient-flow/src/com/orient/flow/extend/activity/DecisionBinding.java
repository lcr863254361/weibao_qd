package com.orient.flow.extend.activity;

import java.util.List;

import org.jbpm.jpdl.internal.activity.DecisionConditionActivity;
import org.jbpm.jpdl.internal.activity.DecisionHandlerActivity;
import org.jbpm.jpdl.internal.activity.JpdlBinding;
import org.jbpm.jpdl.internal.xml.JpdlParser;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExpressionCondition;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.wire.usercode.UserCodeCondition;
import org.jbpm.pvm.internal.wire.usercode.UserCodeReference;
import org.jbpm.pvm.internal.xml.Parse;
import org.w3c.dom.Element;

/**
 * @ClassName DecisionBinding 
 *  业务判断的绑定，可以指向不同的活动
 *  根据绑定的类来初始化不同的活动
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-6
 */

public class DecisionBinding extends JpdlBinding {

	public DecisionBinding() {
		super("decision");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object parseJpdl(Element element, Parse parse, JpdlParser parser) {
		if (element.hasAttribute("expr")) {
			DecisionExpressionActivity decisionExpressionActivity = new DecisionExpressionActivity();
			String expr = element.getAttribute("expr");
			decisionExpressionActivity.setExpression(expr);
			return decisionExpressionActivity;
		}

		Element handlerElement = XmlUtil.element(element, "handler");
		if (handlerElement != null) {
			DecisionHandlerActivity decisionHandlerActivity = new DecisionHandlerActivity();
			UserCodeReference decisionHandlerReference = parser
					.parseUserCodeReference(handlerElement, parse);
			decisionHandlerActivity
					.setDecisionHandlerReference(decisionHandlerReference);
			return decisionHandlerActivity;
		}

		boolean hasConditions = false;
		List<Element> transitionElements = XmlUtil.elements(element,
				"transition");
		ActivityImpl activity = parse.contextStackFind(ActivityImpl.class);
		List<TransitionImpl> transitions = (List) activity
				.getOutgoingTransitions();

		for (int i = 0; i < transitionElements.size(); i++) {
			TransitionImpl transition = transitions.get(i);
			Element transitionElement = transitionElements.get(i);

			Element conditionElement = XmlUtil.element(transitionElement,
					"condition");
			if (conditionElement != null) {
				hasConditions = true;

				if (conditionElement.hasAttribute("expr")) {
					ExpressionCondition expressionCondition = new ExpressionCondition();
					expressionCondition.setExpression(conditionElement
							.getAttribute("expr"));
					expressionCondition.setLanguage(XmlUtil.attribute(
							conditionElement, "lang"));
					transition.setCondition(expressionCondition);

				} else {
					Element conditionHandlerElement = XmlUtil.element(
							conditionElement, "handler");
					if (handlerElement != null) {
						UserCodeCondition userCodeCondition = new UserCodeCondition();

						UserCodeReference conditionReference = parser
								.parseUserCodeReference(
										conditionHandlerElement, parse);
						userCodeCondition
								.setConditionReference(conditionReference);

						transition.setCondition(userCodeCondition);
					}
				}
			}
		}

		if (hasConditions) {
			return new DecisionConditionActivity();
		} else {
			parse.addProblem(
					"decision '"
							+ element.getAttribute("name")
							+ "' must have one of: expr attribute, handler attribute, handler element or condition expressions",
					element);
		}

		return null;
	}
}