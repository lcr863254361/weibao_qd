package com.orient.alarm.schedule.notice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.orient.alarm.model.AlarmContent;
import com.orient.alarm.model.AlarmInfo;
import com.orient.alarm.model.AlarmUser;
import com.orient.alarm.model.AlarmUserRelation;
import com.orient.utils.CommonTools;


public class MailServiceImpl
{
	Logger logger = Logger.getLogger(MailServiceImpl.class);
	
	private JavaMailSender mailSender;
	
	private String mailFrom;
	
	@SuppressWarnings("unchecked")
	public void sendMultiPartMail(AlarmInfo alarm) throws Exception {
		
		logger.info(">>>>> 邮件开始发送");
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		
		List<String> toCopyUsers = new ArrayList<String>(); 
		String toSendUser = null;
		
		Map<AlarmUser, AlarmUserRelation> alarmUserMap = alarm.getAlarmUserMap();
		
		for(Iterator<AlarmUser> iter = alarmUserMap.keySet().iterator(); iter.hasNext();) {
			AlarmUser user = iter.next();
			AlarmUserRelation userRelation = alarmUserMap.get(user);	
			
			if(AlarmUserRelation.TO_SEND_USER.equals(userRelation.getType())) {
				
				if("".equals(CommonTools.Obj2String(user.getEMail()))) {
					return;
				}

				toSendUser = user.getEMail();
				
			} else {
				
				if(!"".equals(CommonTools.Obj2String(user.getEMail()))) {
					toCopyUsers.add(user.getEMail());
				}
				
			}
		}
		
		
		helper.setFrom(mailFrom);
		
		//发送人
		helper.setTo(toSendUser);
		
		//抄送人
		if(toCopyUsers.size() > 0) {
			helper.setCc(toCopyUsers.toArray(new String[0]));
		}
		
		AlarmContent content = alarm.getAlarmContent();
		helper.setSubject(content.getTitle());
		
		helper.setText(NoticeUtil.getContent(content.getContent().getCharacterStream()), true);
		
		mailSender.send(message);
		
		logger.info(">>>>> 邮件发送完成");
	
	}

	public JavaMailSender getMailSender()
	{
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender)
	{
		this.mailSender = mailSender;
	}

	public String getMailFrom()
	{
		return mailFrom;
	}

	public void setMailFrom(String mailFrom)
	{
		this.mailFrom = mailFrom;
	}

}
