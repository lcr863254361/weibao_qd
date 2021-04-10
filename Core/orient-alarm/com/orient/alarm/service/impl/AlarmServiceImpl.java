package com.orient.alarm.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.orient.edm.init.OrientContextLoaderListener;
import org.quartz.JobKey;

import com.orient.alarm.model.AlarmInfo;
import com.orient.alarm.model.AlarmInfoDAO;
import com.orient.alarm.model.AlarmInfoHist;
import com.orient.alarm.model.AlarmInfoHistDAO;
import com.orient.alarm.model.AlarmUser;
import com.orient.alarm.model.AlarmUserDAO;
import com.orient.alarm.model.AlarmUserRelation;
import com.orient.alarm.schedule.AlarmRule;
import com.orient.alarm.schedule.AlarmScheduler;
import com.orient.alarm.schedule.job.AlarmCheckJob;
import com.orient.alarm.service.AlarmService;
import com.orient.utils.CommonTools;


/**
 * simple introduction.
 *
 * <p>detailed comment</p>
 * @author [创建人] qjl <br/> 
 * 		   [创建时间] 2014-11-16 上午11:13:54 <br/> 
 * 		   [修改人] qjl <br/>
 * 		   [修改时间] 2014-11-16 上午11:13:54
 * @see
 */
public class AlarmServiceImpl implements AlarmService
{

	private AlarmInfoDAO alarmInfoDAO;
	
	private AlarmInfoHistDAO alarmInfoHistDAO;
	
	private AlarmUserDAO alarmUserDAO;
	
	
	/* (non-Javadoc)
	 * @see com.orient.alarm.impl.AlarmService#register(com.orient.alarm.model.AlarmInfo)
	 */
	@Override
	public String register(AlarmInfo alarmInfo) {
		
		alarmInfoDAO.save(alarmInfo);
		
		return alarmInfo.getId();
	
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.orient.alarm.impl.AlarmService#cancel(java.lang.String)
	 */
	@Override
	public boolean cancel(String sid) {
		
		try
		{
			AlarmInfo alarmInfo = alarmInfoDAO.findById(sid);
			
			alarmInfoDAO.delete(alarmInfo);
			
			//移除通知发送任务
			AlarmScheduler.getInstance().deleteJob(new JobKey(AlarmCheckJob.ALARM_NOTICE_JOB+"_"+sid, AlarmCheckJob.ALARM_NOTICE_GROUP));
				
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see com.orient.alarm.service.AlarmService#checkAlarm()
	 */
	public List<AlarmInfo> checkAlarm() {
		
		//未触发预警信息
		List<AlarmInfo> unTriggerAlarms = alarmInfoDAO.getUnTriggerAlarms();

		for(Iterator<AlarmInfo> iter = unTriggerAlarms.iterator(); iter.hasNext();) {
			
			AlarmInfo alarmInfo = iter.next();
			
			AlarmRule alarmRule = (AlarmRule) OrientContextLoaderListener.Appwac.getBean(alarmInfo.getClassname());
			
			boolean isAlarm = alarmRule.isAlarm(alarmInfo.getParams());
			
			if(!isAlarm) {
				iter.remove();
			}
		}
		
		return unTriggerAlarms;
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.orient.alarm.service.AlarmService#getMyAlarmHist(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<AlarmInfoHist> getMyAlarmHist(String userId) {
		
		List<AlarmInfoHist> alarmHist = alarmInfoHistDAO.getMyAlarmHist(userId);
		
		return alarmHist;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void modifyCopyer(String alarmId, List<String> copyers)
	{
		// TODO Auto-generated method stub
		
		AlarmInfo alarmInfo = alarmInfoDAO.findById(alarmId);
		
		Map<AlarmUser, AlarmUserRelation> alarmUserMap = alarmInfo.getAlarmUserMap();
		
		for(Iterator<AlarmUser> iter = alarmUserMap.keySet().iterator(); iter.hasNext();) {
			
			AlarmUser alarmUser = iter.next();
			AlarmUserRelation alarmUserRelation = alarmUserMap.get(alarmUser);
			
			if(AlarmUserRelation.TO_COPY_USER.equals(alarmUserRelation.getType())) {
				iter.remove();
			}
		
		}
		
		List<AlarmUser> copyUsers = alarmUserDAO.getAlarmUserByIds(CommonTools.list2String(copyers));
		
		//移除与通知人重复的抄送人
		for(ListIterator<AlarmUser> listIter = copyUsers.listIterator(); listIter.hasNext();) {
			
			AlarmUser copyUser = listIter.next();
			
			for(Iterator<AlarmUser> iter = alarmUserMap.keySet().iterator(); iter.hasNext();) {
				AlarmUser sendUser = iter.next();
				if(copyUser.getId().equals(sendUser.getId())) {
					listIter.remove();
				}
			}
			
		}

		for(AlarmUser alarmUser : copyUsers) {
			alarmUserMap.put(alarmUser, new AlarmUserRelation(AlarmUserRelation.TO_COPY_USER));
		}
		
		alarmInfoDAO.attachDirty(alarmInfo);
		
	}
	
	@Override
	public List<AlarmInfo> getMyAlarm(String userId)
	{
		// TODO Auto-generated method stub
		List<AlarmInfo> myAlarmList = alarmInfoHistDAO.getMyAlarm(userId);
		
		return myAlarmList;
	
	}
	
	public AlarmInfoDAO getAlarmInfoDAO()
	{
		return alarmInfoDAO;
	}

	public void setAlarmInfoDAO(AlarmInfoDAO alarmInfoDAO)
	{
		this.alarmInfoDAO = alarmInfoDAO;
	}



	public AlarmInfoHistDAO getAlarmInfoHistDAO()
	{
		return alarmInfoHistDAO;
	}



	public void setAlarmInfoHistDAO(AlarmInfoHistDAO alarmInfoHistDAO)
	{
		this.alarmInfoHistDAO = alarmInfoHistDAO;
	}



	public AlarmUserDAO getAlarmUserDAO()
	{
		return alarmUserDAO;
	}



	public void setAlarmUserDAO(AlarmUserDAO alarmUserDAO)
	{
		this.alarmUserDAO = alarmUserDAO;
	}
	
	
	
	
}
