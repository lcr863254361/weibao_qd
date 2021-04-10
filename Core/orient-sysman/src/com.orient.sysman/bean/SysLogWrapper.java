package com.orient.sysman.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.orient.sysmodel.domain.sys.SysLog;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-02 16:20
 */
public class SysLogWrapper extends SysLog {

    //查询时使用的开始时间
    private Date opDate_s;

    //查询时使用的结束时间
    private Date opDate_e;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getOpDate_s() {
        return opDate_s;
    }

    public void setOpDate_s(Date opDate_s) {
        this.opDate_s = opDate_s;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getOpDate_e() {
        return opDate_e;
    }

    public void setOpDate_e(Date opDate_e) {
        this.opDate_e = opDate_e;
    }
}
