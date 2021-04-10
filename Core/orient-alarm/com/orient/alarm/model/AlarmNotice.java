package com.orient.alarm.model;

import java.math.BigDecimal;

/**
 * AlarmNotice entity. @author MyEclipse Persistence Tools
 */
public class AlarmNotice extends AbstractAlarmNotice implements java.io.Serializable {

    public AlarmNotice() {
    }

    public AlarmNotice(AlarmInfo alarmInfo, Integer noticetype, String triggertype, BigDecimal interval, BigDecimal repeat, String year, String month, String weekday, String time, String monthday) {
        super(alarmInfo, noticetype, triggertype, interval, repeat, year, month, weekday, time, monthday);        
    }
    
    /**
     * 是否邮件通知.
     *
     * <p>detailed comment</p>
     * @author [创建人] qjl <br/> 
     * 		   [创建时间] 2014-11-5 上午10:33:34 <br/> 
     * 		   [修改人] qjl <br/>
     * 		   [修改时间] 2014-11-5 上午10:33:34
     * @return
     */
    public boolean isMailNotice() {
    	if((this.getNoticetype() & 00000001) > 0) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * 是否短信通知.
     *
     * <p>detailed comment</p>
     * @author [创建人] qjl <br/> 
     * 		   [创建时间] 2014-11-5 上午10:34:01 <br/> 
     * 		   [修改人] qjl <br/>
     * 		   [修改时间] 2014-11-5 上午10:34:01
     * @return
     */
    public boolean isMessageNotice() {
    	if((this.getNoticetype() & 00000010) > 0) {
    		return true;
    	}
    	return false;
    }
   
}
