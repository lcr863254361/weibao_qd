package com.orient.workflow.ext.mail;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.pvm.internal.email.impl.MailTemplate;
import org.jbpm.pvm.internal.email.impl.MailTemplateRegistry;
import org.jbpm.pvm.internal.email.spi.MailSession;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
 

/**
 * @ClassName SendTemplateEmail
 * 在流程运转过程中发送邮件 
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-5
 */

public class SendEdmEmail {
	
	public String Notification = "task-notification";
	
    private MailTemplate getMailTemplate(String templateName){
    	MailTemplateRegistry templateRegistry =
    		      EnvironmentImpl.getFromCurrent(MailTemplateRegistry.class);
    	MailTemplate template= null;
    	if(null!=templateRegistry){
    		template = templateRegistry.getTemplate(templateName);
    	}else{
    		template = new MailTemplate();
    	}
    	return template;
    }
    
    public void notification(Execution execution){
    	this.notification(execution, null);
    }
    /** 
     * 获取通知的邮件模版，发送邮件
     * @Method: notification 
     * @param execution
     * @param fileList 
     */
    public void notification(Execution execution,List<File> fileList){
    	String mailTemplate = "task-notification";
    	MailTemplate template = this.getMailTemplate(mailTemplate);
    	EdmMailProducerImpl producer = new EdmMailProducerImpl();
    	producer.setTemplate(template);
		producer.setFileList(fileList);
		Collection<Message> msgs = producer.produce(execution);
		EnvironmentImpl.getFromCurrent(MailSession.class).send(msgs);
    	
    }
    
    public void sendUserHtmlEmail(String toUser, 
			String subject, Execution execution,
			ExecutionService executionService) {
    	this.sendUserHtmlEmail(toUser, "", subject, execution, executionService);
    }
    
    public void sendUserHtmlEmail(String toUser, String task_url,
			String subject, Execution execution,
			ExecutionService executionService) {
    	this.sendUserHtmlEmail(toUser, task_url, subject, execution, executionService, null);
    }
    
	public void sendUserHtmlEmail(String toUser, String task_url,
			String subject, Execution execution,
			ExecutionService executionService,List<File> fileList) {
		MailTemplate template = this.getMailTemplate("html-template_user");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("username", toUser);
		variables.put("subject", subject);
		variables.put("task_url", task_url);
		executionService.setVariables(execution.getId(), variables);
		EdmMailProducerImpl producer = new EdmMailProducerImpl();
		producer.setTemplate(template);
		producer.setFileList(fileList);
		Collection<Message> msgs = producer.produce(execution);
		EnvironmentImpl.getFromCurrent(MailSession.class).send(msgs);
		

	}
}
