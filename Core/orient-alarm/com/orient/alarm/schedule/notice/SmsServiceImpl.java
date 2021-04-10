package com.orient.alarm.schedule.notice;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.edm.nio.util.CommonTools;
import com.orient.alarm.model.AlarmContent;
import com.orient.alarm.model.AlarmInfo;
import com.orient.alarm.model.AlarmUser;
			 
public class SmsServiceImpl
{
	/**
	 * 中国网建用户名
	 */
	private String uid;
	
	/**
	 * 接口安全密钥
	 */
	private String key;
	
	@SuppressWarnings("unchecked")
	public void sendMessage(AlarmInfo alarmInfo) throws Exception {
		
		AlarmContent content = alarmInfo.getAlarmContent();
		Set<AlarmUser> users = alarmInfo.getAlarmUsers();
		
		String mobiles = "";
		for(Iterator<AlarmUser> iter = users.iterator(); iter.hasNext();) {
			
			AlarmUser user = iter.next();
			if(!"".equals(CommonTools.Obj2String(user.getMobile()))) {
				mobiles += user.getMobile() + ",";
			}
			
		}
		
		if(!"".equals(mobiles)) {
			
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn"); 
			post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码
			
			NameValuePair[] data = { 
					new NameValuePair("Uid", uid),
					new NameValuePair("Key", key),
					new NameValuePair("smsMob", mobiles.substring(0, mobiles.length())),
					new NameValuePair("smsText", content.getTitle()+":"
							+NoticeUtil.getContent(content.getContent().getCharacterStream()))};
			
			post.setRequestBody(data);

			client.executeMethod(post);

			int statusCode = post.getStatusCode();
			System.out.println("statusCode:"+statusCode);
			
			int response = Integer.parseInt(new String(post.getResponseBodyAsString().getBytes("utf-8"))); 
			post.releaseConnection();

			if(response <= 0) {
				throw new Exception("短信发送失败");
			}
			
		}
		
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}
	
	
}
