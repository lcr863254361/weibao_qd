package com.orient.workflow.cmd;


import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.client.ClientExecution;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.session.DbSession;


public class ResumeProcessInstanceCommand implements Command<Boolean>{

	//serialVersionUID is
	
	private static final long serialVersionUID = -490490876813680168L;
	
	private String piId = "";
	
	public ResumeProcessInstanceCommand(String piId)
	{
		this.piId = piId;
	}
	
	@Override
	public Boolean execute(Environment environment) throws Exception {
		
		boolean retVal = false;
		ExecutionService executionService = environment.get(ExecutionService.class); 
		try{
			Execution execution = executionService.createProcessInstanceQuery().processInstanceId(piId).uniqueResult().getProcessInstance();
			ExecutionImpl executionimpl = (ExecutionImpl) execution;
			//executionimpl.resume();
			DbSession dbSession = environment.get(DbSession.class);
			dbSession.cascadeExecutionResume(executionimpl);
			ClientExecution ce = dbSession.findExecutionById(execution.getId());
			ce.setState(Execution.STATE_ACTIVE_ROOT);
			dbSession.update(ce);
			retVal = true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
}
