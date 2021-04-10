package com.orient.workflow.form.model;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.List;

import com.orient.edm.init.OrientContextLoaderListener;
import org.dom4j.Element;
import org.hibernate.Hibernate;

import com.orient.alarm.model.AlarmContent;
import com.orient.alarm.model.AlarmInfo;
import com.orient.alarm.model.AlarmNotice;
import com.orient.alarm.model.AlarmUser;
import com.orient.alarm.model.AlarmUserDAO;
import com.orient.alarm.model.AlarmUserRelation;
import com.orient.utils.CommonTools;

/**
 * 
 * parser the form xml 
 * @author spf
 *
 */
public class AlertInfo{
	
	private static final long serialVersionUID = 1L;

	private AlarmInfo alarmInfo = new AlarmInfo();
	

	
	public AlertInfo(Element alertInfoEle) {
		initAlarmInfo(alertInfoEle);
	}
	
	private void initAlarmInfo(Element alertInfoEle){
		
		String classname = CommonTools.Obj2String(alertInfoEle.attributeValue("classname"));
		alarmInfo.setClassname(classname);
		
		String params = CommonTools.Obj2String(alertInfoEle.attributeValue("params"));//5 days
		alarmInfo.setParams(params);
		
		String nlevelString = CommonTools.Obj2String(alertInfoEle.attributeValue("nlevel"));
		if(!nlevelString.equals("")){
			Long nlevel = Long.valueOf(nlevelString);
			alarmInfo.setNlevel(nlevel);
		}
		
		AlarmUserDAO userDAO = (AlarmUserDAO) OrientContextLoaderListener.Appwac.getBean("AlarmUserDAO");
		
		//发送人
		String toSendUser = CommonTools.Obj2String(alertInfoEle.attributeValue("send_to_user"));
		if(!"".equals(toSendUser)){
			alarmInfo.getAlarmUserMap().put(userDAO.findByUserName(toSendUser).get(0), new AlarmUserRelation(AlarmUserRelation.TO_SEND_USER));
		}
		
		String toCopyUser = CommonTools.Obj2String(alertInfoEle.attributeValue("copy_to_user"));
		if(!"".equals(toCopyUser)) {
			List<AlarmUser> toCopyUsers = userDAO.getAlarmUserByNames(toCopyUser);
			for(AlarmUser alarmUser : toCopyUsers) {
				alarmInfo.getAlarmUserMap().put(alarmUser, new AlarmUserRelation(AlarmUserRelation.TO_COPY_USER));
			}
			
		}
		

		Element alarmContentEle = alertInfoEle.element("alarmContent");
		AlarmContent alarmContent = initAlarmContent(alarmContentEle);
		alarmInfo.setAlarmContent(alarmContent);
		
		Element alarmNoticeEle = alertInfoEle.element("alarmNotice");
		AlarmNotice alarmNotice = initAlarmNotice(alarmNoticeEle);
		alarmInfo.setAlarmNotice(alarmNotice);
		
		alarmInfo.setIsAlarm(false);

	}
	
	private AlarmNotice initAlarmNotice(Element alarmNoticeEle){
		if(alarmNoticeEle == null){
			return new AlarmNotice();
		}
		
		AlarmNotice alarmNotice = new AlarmNotice();
		
		String triggerType = CommonTools.Obj2String(alarmNoticeEle.attributeValue("triggertype"));
		alarmNotice.setTriggertype(triggerType);
		
		String noticeTypeString = CommonTools.Obj2String(alarmNoticeEle.attributeValue("noticetype"));
		alarmNotice.setNoticetype(Integer.valueOf(noticeTypeString));
		
		String intervalString = CommonTools.Obj2String(alarmNoticeEle.attributeValue("interval"));
		if(!"".equals(intervalString)) {
			alarmNotice.setInterval(new BigDecimal(intervalString));
		}
		
		String month = CommonTools.Obj2String(alarmNoticeEle.attributeValue("month"));
		alarmNotice.setMonth(month);
		
		String monthday = CommonTools.Obj2String(alarmNoticeEle.attributeValue("monthday"));
		alarmNotice.setMonthday(monthday);
//		alarmNotice.setMonthday("6");

		String repeat = CommonTools.Obj2String(alarmNoticeEle.attributeValue("repeat"));
		if(!"".equals(repeat)) {
			alarmNotice.setRepeat(new BigDecimal(repeat));
		}
		
		String time = CommonTools.Obj2String(alarmNoticeEle.attributeValue("time"));
		alarmNotice.setTime(time); //若不需设置为*:*:*
		
		String weekday = CommonTools.Obj2String(alarmNoticeEle.attributeValue("weekday"));
		alarmNotice.setWeekday(weekday);
		
		String year = CommonTools.Obj2String(alarmNoticeEle.attributeValue("year"));
		alarmNotice.setYear(year);
		
		alarmNotice.setAlarmInfo(alarmInfo);
		
		return alarmNotice;
	}
	
	private AlarmContent initAlarmContent(Element alarmContentEle){
		if(alarmContentEle == null){
			return new AlarmContent();
		}
		
		AlarmContent alarmContent = new AlarmContent();
		
		String title = CommonTools.Obj2String(alarmContentEle.attributeValue("title"));
		alarmContent.setTitle(title);
		
		String content = CommonTools.Obj2String(alarmContentEle.attributeValue("content")); 
		Clob clob = Hibernate.createClob(content);
		alarmContent.setContent(clob);
		
		String url = CommonTools.Obj2String(alarmContentEle.attributeValue("url"));  
		alarmContent.setUrl(url);
		
		alarmContent.setAlarmInfo(alarmInfo);
		
		return alarmContent;
	}


	public AlarmInfo getAlarmInfo() {
		return alarmInfo;
	}


	public void setAlarmInfo(AlarmInfo alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

}
