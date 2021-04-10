package com.orient.workflow.service;

import java.util.Map;
import java.util.zip.ZipInputStream;

// 
/**
 * The Interface DeployService.
 */
public interface DeployService {

	/**
	 * Deploy.
	 * 
	 * @param processDefinition the process definition
	 * 
	 * @return the string
	 */
	public abstract String deploy(String processDefinition);

	/**
	 * Deploy.
	 * 
	 * @param zis the zis
	 * 
	 * @return the string
	 */
	public abstract String deploy(ZipInputStream zis);
	
	/**
	 * Deploy.
	 * 
	 * @param filePath the file path
	 * @param user the user
	 * @param value the value
	 * @param lock the lock
	 * 
	 * @return the string
	 */
	public abstract String deploy(String filePath,String user,Map<String,String> value,boolean lock);
	
	/**
	 * ���deploymentId�����̲����ļ�
	 * 
	 * @param deploymentId ���̲���id
	 * @param suspended true ������ false �����
	 * 
	 * @return true, if successful
	 */
	public boolean suspendDeployment(String deploymentId,boolean suspended);
}