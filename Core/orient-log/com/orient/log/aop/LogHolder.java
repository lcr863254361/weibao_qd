package com.orient.log.aop;

import com.orient.sysmodel.domain.sys.SysLog;

import java.util.Map;

public class LogHolder {
	SysLog syslog;
	boolean needParse = false;
	Map<String, Object> parseDataModel;

	public boolean isNeedParse() {
		return this.needParse;
	}

	public void setNeedParse(boolean needParse) {
		this.needParse = needParse;
	}

	public Map<String, Object> getParseDataModel() {
		return this.parseDataModel;
	}

	public void setParseDataModel(Map<String, Object> parseDataModel) {
		this.parseDataModel = parseDataModel;
	}

	public SysLog getSyslog() {
		return syslog;
	}

	public void setSyslog(SysLog syslog) {
		this.syslog = syslog;
	}
}
