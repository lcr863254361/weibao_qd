package com.orient.workflow.ext.mail;

import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.env.TaskContext;
import org.jbpm.pvm.internal.history.HistoryEvent;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.orient.utils.CommonTools;
import com.orient.workflow.form.EdmFormCache;
import com.orient.workflow.form.model.XmlForm;
import com.orient.workflow.form.model.XmlFormReader;


/**
 * @ClassName EdmNotificationEvent
 * 任务通知的类，可以采用邮件通知，JMS的通知，短信通知等。
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */
public class EdmNotificationEvent extends HistoryEvent{

	
	/** 
	* @Fields serialVersionUID : 
	*/
	
	private static final long serialVersionUID = 1L;
	protected TaskImpl task;

	public EdmNotificationEvent(TaskImpl task){
		this.task = task;
	}
	
	@Override
	public void process() {
		EdmFormCache formCache =  EnvironmentImpl.getFromCurrent(EdmFormCache.class);
		XmlFormReader reader = formCache.get(execution.getProcessDefinition().getId());
		if(null==reader){
			return;
		}
		
		XmlForm form = reader.getFormByName(task.getName());
		if(null==form){
			return;
		}
		if(CommonTools.Obj2String(form.getMailType()).equals("1")){
			//邮件通知
		    EnvironmentImpl environment = EnvironmentImpl.getCurrent();
			TaskContext taskContext = new TaskContext(task);
			environment.setContext(taskContext);
			SendEdmEmail sendEmail = new SendEdmEmail();
			sendEmail.notification(execution);
		}
		if(CommonTools.Obj2String(form.getMailType()).equals("2")){
			//短信通知
			
		}
		if(CommonTools.Obj2String(form.getMailType()).equals("3")){
			//JMS通知
			
		}
		
	}

}
