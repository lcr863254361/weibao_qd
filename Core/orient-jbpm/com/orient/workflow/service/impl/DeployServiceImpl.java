package com.orient.workflow.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.jbpm.api.Deployment;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.repository.RepositoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.workflow.WorkFlowConstants;
import com.orient.workflow.ext.deploy.EdmDeploymentImpl;
import com.orient.workflow.service.DeployService;
import org.springframework.stereotype.Service;

@Service
public class DeployServiceImpl implements DeployService {

	public DeployServiceImpl() {

	}

	@Override
	public String deploy(String processDefinition) {
		RepositoryServiceImpl repositoryService = (RepositoryServiceImpl)processEngine.getRepositoryService();
		EdmDeploymentImpl deployment = new EdmDeploymentImpl( processEngine.getRepositoryService().createDeployment(),repositoryService.getCommandService());
		deployment.setName("test");
		Date date = new Date();
		deployment.setTimestamp(date.getTime());
		deployment.addResourceFromClasspath(processDefinition);
		EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		EnvironmentImpl env = null;
		try {
			env = environmentFactory.openEnvironment();
		} catch (Exception ex) {
			System.out.println("Exception test ===========" + ex);
		} finally {
			if (env != null)
				env.close();
		}
		return deployment.deploy();
	}

	@Override
	public String deploy(ZipInputStream zis) {
		NewDeployment deployment = processEngine.getRepositoryService()
				.createDeployment();
		deployment.addResourcesFromZipInputStream(zis);
		return deployment.deploy();
	}

	@Override
	@SuppressWarnings("unchecked")
	public String deploy(String filePath, String userName, final Map<String, String> values, boolean lock) {
		// 部署流程文件到数据库中
		RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) processEngine.getRepositoryService();
		final EdmDeploymentImpl newDeploymentImpl = new EdmDeploymentImpl(
				processEngine.getRepositoryService().createDeployment(),repositoryService.getCommandService());

		newDeploymentImpl.setName(userName);
		Date date = new Date();
		newDeploymentImpl.setTimestamp(date.getTime());
		processEngine.execute(new AddDeployPropertyCmd(newDeploymentImpl, values));

		return newDeploymentImpl.addResourcesFromZipFile(filePath).deploy();
	}

	public String deploy(String resourceName, InputStream inputStream, String userName, Map<String, String> values, boolean lock){
		RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) processEngine.getRepositoryService();
		final EdmDeploymentImpl newDeploymentImpl = new EdmDeploymentImpl(
				processEngine.getRepositoryService().createDeployment(),repositoryService.getCommandService());

		newDeploymentImpl.setName(userName);
		Date date = new Date();
		newDeploymentImpl.setTimestamp(date.getTime());
		processEngine.execute(new AddDeployPropertyCmd(newDeploymentImpl, values));

		return newDeploymentImpl.addResourceFromInputStream(resourceName, inputStream).deploy();
	}

	public boolean suspendDeployment(String deploymentId, boolean suspended) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE JBPM4_DEPLOYMENT SET STATE_='");
		try {
			if (suspended) {
				sql.append(Deployment.STATE_SUSPENDED);
			} else {
				sql.append(Deployment.STATE_ACTIVE);
			}
			sql.append("' WHERE DBID_= ").append(deploymentId);
			jdbcTemplate.update(sql.toString());
		} catch (Exception ex) {
			System.out.println("Exception test ===========" + ex);
			return false;
		}
		return true;
	}
	
	public void setService(IBusinessModelService service) {
		this.service = service;
	}
	
	public void setSqlEngine(ISqlEngine sqlEngine) {
		this.sqlEngine = sqlEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	private static class AddDeployPropertyCmd implements Command {

		AddDeployPropertyCmd(EdmDeploymentImpl newDeploymentImpl, Map<String, String> values){
			this.newDeploymentImpl = newDeploymentImpl;
			this.values = values;
		}
		@Override
		public Object execute(Environment env) {
			if (values != null) {
				Iterator<String> iterator = values.keySet().iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String value = values.get(key);
					newDeploymentImpl.setObjectProperty(WorkFlowConstants.USERPARA, key, value);
				}
			}
			return env;
		}

		private Map<String, String> values;
		private EdmDeploymentImpl newDeploymentImpl;

		private static final long serialVersionUID = -3214139418863388413L;
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private IBusinessModelService service;
	@Autowired
	private ISqlEngine sqlEngine;
}