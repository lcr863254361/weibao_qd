package com.orient.workflow.ext.mail;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @ClassName EdmMailAuthenticator
 * 邮件服务器的验证类
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class EdmMailAuthenticator extends Authenticator {
    String userName = "user";      
    String password = "password";      
    
	public EdmMailAuthenticator(){      
    } 
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
           
    protected PasswordAuthentication getPasswordAuthentication(){
    	try {
    		Properties properties = new Properties();
    		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    		InputStream stream = classLoader.getResourceAsStream("jbpm.mail.properties");
    		properties.load(stream);
    		String user = (String) properties.get("mail.user");
    		String password = (String) properties.get("mail.password");
    		if(user != null && password != null)
    		return new PasswordAuthentication(user, password);    
		} catch (IOException e) {
			e.printStackTrace();
		}
        return new PasswordAuthentication(userName, password);      
    }    
    
    public static void main(String[] args){
    	try {
    		Properties properties = new Properties();
    		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    		InputStream stream = classLoader.getResourceAsStream("jbpm.mail.properties");
    		properties.load(stream);
    		properties.get("mail.user");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
