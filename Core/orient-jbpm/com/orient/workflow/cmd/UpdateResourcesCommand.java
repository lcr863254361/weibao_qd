package com.orient.workflow.cmd;

import java.lang.reflect.Field;
import java.util.Map;

import org.jbpm.api.JbpmException;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.lob.BlobStrategy;
import org.jbpm.pvm.internal.lob.BlobStrategyBlob;
import org.jbpm.pvm.internal.lob.Lob;
import org.jbpm.pvm.internal.repository.DeploymentImpl;
import org.jbpm.pvm.internal.repository.RepositoryCache;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.session.RepositorySession;

import com.orient.workflow.form.EdmFormCache;

/**
 * 直接更新数据库中的jbpm lob资源文件
 *@Function Name:  UpdateResourcesCommand
 *@Description:
 *@Date Created:  2015-7-17 上午10:58:24
 *@Author: changxk
 *@Last Modified:    ,  Date Modified:
 */
public class UpdateResourcesCommand implements Command<Boolean>{

	//serialVersionUID is
	private String pdId;
	private String deploymentId;
	private String resourceName;
	private byte[] bytes;
	
	private static final long serialVersionUID = 1L;

	public UpdateResourcesCommand(String pdId,String deploymentId,String resourceName,byte[] bytes){
		this.pdId = pdId;
		this.deploymentId = deploymentId;
		this.resourceName = resourceName;
		this.bytes = bytes;
	}
	
	@Override
	public Boolean execute(Environment environment) throws Exception {
		RepositorySession repositorySession = environment.get(RepositorySession.class);
		DeploymentImpl deploymentImpl = repositorySession.getDeployment(deploymentId);
		Field lobMapField = deploymentImpl.getClass().getDeclaredField("resources");
		lobMapField.setAccessible(true);
		Map<String,Lob> resources = (Map<String,Lob>)lobMapField.get(deploymentImpl);
		Lob jpdlLob = resources.get(resourceName);
		if(jpdlLob == null){
			throw new JbpmException("没有找到"+resourceName+"资源文件！");
		}
		BlobStrategy blobStrategy = new BlobStrategyBlob();
		blobStrategy.set(bytes, jpdlLob);
		DbSession session = environment.get(DbSession.class);
		session.update(jpdlLob);
		//清空部署的缓存，能够打开最新的流程定义
		RepositoryCache repositoryCache = environment.get(RepositoryCache.class);
		repositoryCache.remove(deploymentId);
		//清空EDM系统缓存
		EdmFormCache formCache = environment.get(EdmFormCache.class);
		formCache.remove(pdId);
		return true;
	}

}
