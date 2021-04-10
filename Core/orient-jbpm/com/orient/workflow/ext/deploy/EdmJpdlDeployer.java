package com.orient.workflow.ext.deploy;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.internal.log.Log;
import org.jbpm.jpdl.internal.repository.JpdlDeployer;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.repository.DeploymentImpl;
import org.jbpm.pvm.internal.repository.ProcessDeployer;
import org.jbpm.pvm.internal.session.RepositorySession;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @ClassName EdmJpdlDeployer
 * 流程部署类，部署的检查等功能
 * 主要是为了引入我们自定义的EdmJpdlParser,方便解析的扩展
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class EdmJpdlDeployer extends ProcessDeployer {

	private static Log log = Log.getLog(JpdlDeployer.class.getName());
	private static Parser parser = new Parser();

	static EdmJpdlParser jpdlParser = new EdmJpdlParser();
	static final String jpdlExtension = ".jpdl.xml";

	public EdmJpdlDeployer() {
		super(jpdlExtension, jpdlParser);
	}

	public void updateResource(DeploymentImpl deployment, String resourceName,
			byte[] bytes) {
		if (resourceName.endsWith(".jpdl.xml")) {
			Document document = parser.createParse()
					.setInputStream(new ByteArrayInputStream(bytes)).execute()
					.getDocument();
			Element documentElement = document.getDocumentElement();
			String tagName = documentElement.getLocalName();

			if ("process-update".equals(tagName)) {
				updateJpdlProcessResource(deployment, resourceName, document);
				return;
			}
		}

		super.updateResource(deployment, resourceName, bytes);
	}

	public void updateJpdlProcessResource(DeploymentImpl deployment,
			String resourceName, Document updateDocument) {
		byte[] processBytes = deployment.getBytes(resourceName);
		Document processDocument = parser.createParse()
				.setInputStream(new ByteArrayInputStream(processBytes))
				.execute().checkErrors("jPDL process update document")
				.getDocument();
		Element processElement = processDocument.getDocumentElement();

		Element processUpdateElement = updateDocument.getDocumentElement();
		Element processUpdateDescriptionElement = XmlUtil.element(
				processUpdateElement, "description");
		if (processUpdateDescriptionElement != null) {
			Element processDescriptionElement = XmlUtil.element(processElement,
					"description");
			if (processDescriptionElement != null) {
				processElement.removeChild(processDescriptionElement);
			}
			Node clonedDescriptionElement = processUpdateDescriptionElement
					.cloneNode(true);
			processDocument.adoptNode(clonedDescriptionElement);
			processElement.appendChild(clonedDescriptionElement);
		}

		updateActivities(processDocument, processElement, processUpdateElement);

		try {
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// initialize StreamResult with File object to save to file
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(processDocument);
			transformer.transform(source, result);

			String updatedProcessXml = result.getWriter().toString();
			log.debug("updated process xml: \n" + updatedProcessXml);
			byte[] bytes = updatedProcessXml.getBytes();
			deployment.addResourceFromInputStream(resourceName,
					new ByteArrayInputStream(bytes));
		} catch (Exception e) {
			throw new JbpmException(
					"couldn't serialize updated process dom model", e);
		}
	}

	private void updateActivities(Document processDocument,
			Element activityContainerElement, Element updateContainerElement) {
		Set<String> activityNames = jpdlParser.getActivityTagNames();

		Map<String, Element> processActivityMap = getActivityMap(
				activityContainerElement, activityNames);
		Map<String, Element> updateActivityMap = getActivityMap(
				updateContainerElement, activityNames);

		for (String activityName : updateActivityMap.keySet()) {
			Element updateActivity = updateActivityMap.get(activityName);
			Element processActivity = processActivityMap.get(activityName);

			if (processActivity == null) {
				throw new JbpmException("unmatching update activity "
						+ activityName);
			}

			Node clonedUpdateActivity = updateActivity.cloneNode(true);
			processDocument.adoptNode(clonedUpdateActivity);
			activityContainerElement.insertBefore(clonedUpdateActivity,
					processActivity);
			activityContainerElement.removeChild(processActivity);
		}
	}

	protected Map<String, Element> getActivityMap(Element containerElement,
			Set<String> activityNames) {
		Map<String, Element> activityMap = new HashMap<String, Element>();

		for (Element element : XmlUtil.elements(containerElement)) {
			String tagName = element.getLocalName();
			if (activityNames.contains(tagName)) {
				String activityName = element.getAttribute("name");
				activityMap.put(activityName, element);
			}
		}

		return activityMap;
	}

	@Override
	protected void checkKey(ProcessDefinitionImpl processDefinition,
			DeploymentImpl deployment) {

		String processDefinitionName = processDefinition.getName();
		String processDefinitionKey = processDefinition.getKey();

		if (processDefinitionKey == null) {
			processDefinitionKey = processDefinitionName;
			processDefinition.setKey(processDefinitionKey);
		}

		RepositorySession repositorySession = EnvironmentImpl
				.getFromCurrent(RepositorySession.class);

		List<ProcessDefinition> existingProcesses = repositorySession
				.createProcessDefinitionQuery()
				.processDefinitionName(processDefinitionName).list();

		for (ProcessDefinition existingProcess : existingProcesses) {
			if (!processDefinitionKey.equals(existingProcess.getKey())) {
				deployment.addProblem("invalid key '" + processDefinitionKey
						+ "' in process " + processDefinition.getName()
						+ ".  Existing process has name '"
						+ processDefinitionName + "' and key '"
						+ processDefinitionKey + "'");
			}
		}

		existingProcesses = repositorySession.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).list();

		for (ProcessDefinition existingProcess : existingProcesses) {
			if (!processDefinitionName.equals(existingProcess.getName())) {
				deployment.addProblem("invalid name '" + processDefinitionName
						+ "' in process " + processDefinition.getName()
						+ ".  Existing process has name '"
						+ processDefinitionName + "' and key '"
						+ processDefinitionKey + "'");
			}
		}
	}

}
