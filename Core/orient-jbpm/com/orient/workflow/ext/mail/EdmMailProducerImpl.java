package com.orient.workflow.ext.mail;

import java.io.File;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import org.jbpm.api.Execution;
import org.jbpm.pvm.internal.email.impl.MailProducerImpl;

/**
 * @ClassName EdmMailProducerImpl
 * JBPM邮件产生类。
 * 可以自定义挂接在邮件里的附件
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-5
 */

public class EdmMailProducerImpl extends MailProducerImpl{

	/** 
	* @Fields serialVersionUID :
	*/
	
	private static final long serialVersionUID = -2498457872253715852L;

	/** 
	* @Fields fileList : 附件列表
	*/
	private List<File> fileList =null;

	@Override
	protected void addAttachments(Execution execution, Multipart multiPart)
			throws MessagingException { 
		super.addAttachments(execution, multiPart);
		if(null!=fileList){
			for(File attachfile:fileList){
			   if(!attachfile.isFile()){
				   continue;
			   }
			   BodyPart attachmentPart = new MimeBodyPart();
			   DataHandler dh = new DataHandler(new FileDataSource(attachfile));
			   attachmentPart.setDataHandler(dh);
			   multiPart.addBodyPart(attachmentPart); 
				
			}
		}
		
		
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}
	
	
	
	

}
