package com.orient.workflow.ext.deploy;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import com.orient.utils.CommonTools;
import org.jbpm.api.NewDeployment;
import org.jbpm.pvm.internal.cmd.CommandService;
import org.jbpm.pvm.internal.repository.DeploymentImpl;
import org.jbpm.pvm.internal.repository.DeploymentProperty;
import org.jbpm.pvm.internal.stream.StreamInput;

import com.orient.workflow.form.impl.InitFormCmd;

/**
 * @ClassName EdmDeploymentImpl 扩展部署hibernate类，增加压缩包中中文文件的解压
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-2
 */

public class EdmDeploymentImpl extends DeploymentImpl implements NewDeployment {

	private DeploymentImpl deploymentImpl;

	public EdmDeploymentImpl() {
	}

	/** 
	 * 作为部署类的扩展
	 * @param @param deploymentImpl
	 * @param @param service 命令服务
	 * @throws 
	 */
	
	
	public EdmDeploymentImpl(NewDeployment deploymentImpl,
			CommandService service) {
		this.deploymentImpl = (DeploymentImpl) deploymentImpl;
		this.commandService = service;

	}

	@Override
	public NewDeployment addResourceFromClasspath(String resourceName) {
		deploymentImpl.addResourceFromClasspath(resourceName);
		return this;
	}

	@Override
	public NewDeployment addResourceFromFile(File file) {
		deploymentImpl.addResourceFromFile(file);
		return this;
	}

	@Override
	public NewDeployment addResourceFromInputStream(String resourceName,
			InputStream inputStream) {
		deploymentImpl.addResourceFromInputStream(resourceName, inputStream);
		return this;
	}

	@Override
	public NewDeployment addResourceFromString(String resourceName,
			String string) {
		deploymentImpl.addResourceFromString(resourceName, string);
		return this;
	}

	@Override
	public NewDeployment addResourceFromUrl(URL url) {
		deploymentImpl.addResourceFromUrl(url);
		return this;
	}

	@Override
	public NewDeployment addResourcesFromZipInputStream(
			ZipInputStream zipInputStream) {
		deploymentImpl.addResourcesFromZipInputStream(zipInputStream);
		return this;
	}

	/** 
	 * 设置流程部署的压缩包资源
	 * @Method: addResourcesFromZipFile 
	 * @param zipFilePath
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public NewDeployment addResourcesFromZipFile(String zipFilePath) {
		try {
			org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(
					zipFilePath);
			java.util.Enumeration e = zipFile.getEntries();
			org.apache.tools.zip.ZipEntry zipEntry = null;
			while (e.hasMoreElements()) {
				zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
				InputStream inputStream = zipFile.getInputStream(zipEntry);
				String resourceName = zipEntry.getName();
				if(zipEntry.getName().indexOf(File.separator) != -1)
				{
					resourceName = resourceName.substring(resourceName.lastIndexOf(File.separator) + 1,resourceName.length());
				}
				if(CommonTools.isNullString(resourceName) || resourceName.startsWith(".")){
					continue;
				}
				addResourceFromInputStream(resourceName, inputStream);
			}
			zipFile.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return this;
	}

	/**
	 * 部署流程文件的同时，缓存流程任务节点对应的表单内容
	 * @Method: deploy 
	 * @return 
	 * @see org.jbpm.pvm.internal.repository.DeploymentImpl#deploy() 
	 */
	@Override
	public String deploy() {
		// 部署流程
		String deploymentId = deploymentImpl.deploy();
//		// 实例化表单
//		this.commandService.execute(new InitFormCmd(deploymentImpl));
		return deploymentId;
	}
	
	public void initRDMFlowForm(){
		// 实例化表单
		this.commandService.execute(new InitFormCmd(deploymentImpl));
	}

	public void initAuditFlowForm(){
		
	}
	
	@Override
	public byte[] getBytes(String resourceName) {
		System.out.println(resourceName);
		return deploymentImpl.getBytes(resourceName);
	}

	@Override
	public String getId() {
		return deploymentImpl.getId();
	}

	@Override
	public String getName() {
		return deploymentImpl.getName();
	}

	@Override
	public String getState() {
		return deploymentImpl.getState();
	}

	@Override
	public long getTimestamp() {
		return deploymentImpl.getTimestamp();
	}

	@Override
	public void addObject(String objectName, Object object) {
		deploymentImpl.addObject(objectName, object);
	}

	@Override
	public NewDeployment addResourceFromStreamInput(String name,
			StreamInput streamInput) {
		deploymentImpl.addResourceFromStreamInput(name, streamInput);
		return this;
	}

	@Override
	public long getDbid() {
		return deploymentImpl.getDbid();
	}

	@Override
	public Set<DeploymentProperty> getObjectProperties() {
		return deploymentImpl.getObjectProperties();
	}

	@Override
	public Object getObjectProperty(String objectName, String key) {
		return deploymentImpl.getObjectProperty(objectName, key);
	}

	@Override
	public Map<String, Object> getObjects() {
		return deploymentImpl.getObjects();
	}

	@Override
	public String getProcessDefinitionId(String processDefinitionName) {
		return deploymentImpl.getProcessDefinitionId(processDefinitionName);
	}

	@Override
	public Set<String> getProcessDefinitionIds() {
		return deploymentImpl.getProcessDefinitionIds();
	}

	@Override
	public String getProcessDefinitionKey(String processDefinitionName) {
		return deploymentImpl.getProcessDefinitionKey(processDefinitionName);
	}

	@Override
	public Long getProcessDefinitionVersion(String processDefinitionName) {
		return deploymentImpl
				.getProcessDefinitionVersion(processDefinitionName);
	}

	@Override
	public String getProcessLanguageId(String processDefinitionName) {
		return deploymentImpl.getProcessLanguageId(processDefinitionName);
	}

	@Override
	public Set<String> getResourceNames() {
		return deploymentImpl.getResourceNames();
	}

	@Override
	public boolean hasObjectProperties(String objectName) {
		return deploymentImpl.hasObjectProperties(objectName);
	}

	@Override
	public void initResourceLobDbids() {
		deploymentImpl.initResourceLobDbids();
	}

	@Override
	public boolean isSuspended() {
		return deploymentImpl.isSuspended();
	}

	@Override
	public Object removeObjectProperty(String objectName, String key) {
		return deploymentImpl.removeObjectProperty(objectName, key);
	}

	@Override
	public void resume() {
		deploymentImpl.resume();
	}

	@Override
	public void setDbid(long dbid) {
		deploymentImpl.setDbid(dbid);
	}

	@Override
	public DeploymentImpl setName(String name) {
		deploymentImpl.setName(name);
		return this;
	}

	@Override
	public void setObjectProperty(String objectName, String key, Object value) {
		deploymentImpl.setObjectProperty(objectName, key, value);
	}

	@Override
	public void setProcessDefinitionId(String processDefinitionName,
			String processDefinitionId) {
		deploymentImpl.setProcessDefinitionId(processDefinitionName,
				processDefinitionId);
	}

	@Override
	public void setProcessDefinitionKey(String processDefinitionName,
			String processDefinitionKey) {
		deploymentImpl.setProcessDefinitionKey(processDefinitionName,
				processDefinitionKey);
	}

	@Override
	public void setProcessDefinitionVersion(String processDefinitionName,
			Long processDefinitionVersion) {
		deploymentImpl.setProcessDefinitionVersion(processDefinitionName,
				processDefinitionVersion);
	}

	@Override
	public void setProcessLanguageId(String processDefinitionName,
			String processLanguageId) {
		deploymentImpl.setProcessLanguageId(processDefinitionName,
				processLanguageId);
	}

	@Override
	public DeploymentImpl setTimestamp(long timestamp) {
		deploymentImpl.setTimestamp(timestamp);
		return this;
	}

	@Override
	public void suspend() {
		deploymentImpl.suspend();
	}

	@Override
	public String toString() {
		return deploymentImpl.toString();
	}

	@Override
	protected Object writeReplace() throws ObjectStreamException {
		return this;
	}

	public DeploymentImpl getDeploymentImpl() {
		return deploymentImpl;
	}

	public void setDeploymentImpl(DeploymentImpl deploymentImpl) {
		this.deploymentImpl = deploymentImpl;
	}

}
