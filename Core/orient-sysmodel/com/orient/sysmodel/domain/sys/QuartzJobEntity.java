package com.orient.sysmodel.domain.sys;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-30 9:44
 */
@Entity
@Table(name = "CWM_TIME_INFO")
public class QuartzJobEntity {
    private Long id;
    private String isstarttimeback;
    private String backname;
    private String isbackdata;
    private String isdayback;
    private String daybacktime;
    private String ismonthback;
    private String monthbackday;
    private String monthbacktime;
    private String isweekback;
    private String weekbackday;
    private String weekbacktime;
    private String backtype;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence",strategy = "sequence",parameters = {@org.hibernate.annotations.Parameter(name="sequence",value="SEQ_CWM_TIME_INFO")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ISSTARTTIMEBACK", nullable = true, length = 100)
    public String getIsstarttimeback() {
        return isstarttimeback;
    }

    public void setIsstarttimeback(String isstarttimeback) {
        this.isstarttimeback = isstarttimeback;
    }

    @Basic
    @Column(name = "BACKNAME", nullable = true, length = 200)
    public String getBackname() {
        return backname;
    }

    public void setBackname(String backname) {
        this.backname = backname;
    }

    @Basic
    @Column(name = "ISBACKDATA", nullable = true, length = 1)
    public String getIsbackdata() {
        return isbackdata;
    }

    public void setIsbackdata(String isbackdata) {
        this.isbackdata = isbackdata;
    }

    @Basic
    @Column(name = "ISDAYBACK", nullable = true, length = 1)
    public String getIsdayback() {
        return isdayback;
    }

    public void setIsdayback(String isdayback) {
        this.isdayback = isdayback;
    }

    @Basic
    @Column(name = "DAYBACKTIME", nullable = true, length = 20)
    public String getDaybacktime() {
        return daybacktime;
    }

    public void setDaybacktime(String daybacktime) {
        this.daybacktime = daybacktime;
    }

    @Basic
    @Column(name = "ISMONTHBACK", nullable = true, length = 1)
    public String getIsmonthback() {
        return ismonthback;
    }

    public void setIsmonthback(String ismonthback) {
        this.ismonthback = ismonthback;
    }

    @Basic
    @Column(name = "MONTHBACKDAY", nullable = true, length = 10)
    public String getMonthbackday() {
        return monthbackday;
    }

    public void setMonthbackday(String monthbackday) {
        this.monthbackday = monthbackday;
    }

    @Basic
    @Column(name = "MONTHBACKTIME", nullable = true, length = 20)
    public String getMonthbacktime() {
        return monthbacktime;
    }

    public void setMonthbacktime(String monthbacktime) {
        this.monthbacktime = monthbacktime;
    }

    @Basic
    @Column(name = "ISWEEKBACK", nullable = true, length = 1)
    public String getIsweekback() {
        return isweekback;
    }

    public void setIsweekback(String isweekback) {
        this.isweekback = isweekback;
    }

    @Basic
    @Column(name = "WEEKBACKDAY", nullable = true, length = 10)
    public String getWeekbackday() {
        return weekbackday;
    }

    public void setWeekbackday(String weekbackday) {
        this.weekbackday = weekbackday;
    }

    @Basic
    @Column(name = "WEEKBACKTIME", nullable = true, length = 20)
    public String getWeekbacktime() {
        return weekbacktime;
    }

    public void setWeekbacktime(String weekbacktime) {
        this.weekbacktime = weekbacktime;
    }

    @Basic
    @Column(name = "BACKTYPE", nullable = true, length = 1)
    public String getBacktype() {
        return backtype;
    }

    public void setBacktype(String backtype) {
        this.backtype = backtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuartzJobEntity that = (QuartzJobEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (isstarttimeback != null ? !isstarttimeback.equals(that.isstarttimeback) : that.isstarttimeback != null)
            return false;
        if (backname != null ? !backname.equals(that.backname) : that.backname != null) return false;
        if (isbackdata != null ? !isbackdata.equals(that.isbackdata) : that.isbackdata != null) return false;
        if (isdayback != null ? !isdayback.equals(that.isdayback) : that.isdayback != null) return false;
        if (daybacktime != null ? !daybacktime.equals(that.daybacktime) : that.daybacktime != null) return false;
        if (ismonthback != null ? !ismonthback.equals(that.ismonthback) : that.ismonthback != null) return false;
        if (monthbackday != null ? !monthbackday.equals(that.monthbackday) : that.monthbackday != null) return false;
        if (monthbacktime != null ? !monthbacktime.equals(that.monthbacktime) : that.monthbacktime != null)
            return false;
        if (isweekback != null ? !isweekback.equals(that.isweekback) : that.isweekback != null) return false;
        if (weekbackday != null ? !weekbackday.equals(that.weekbackday) : that.weekbackday != null) return false;
        if (weekbacktime != null ? !weekbacktime.equals(that.weekbacktime) : that.weekbacktime != null) return false;
        if (backtype != null ? !backtype.equals(that.backtype) : that.backtype != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isstarttimeback != null ? isstarttimeback.hashCode() : 0);
        result = 31 * result + (backname != null ? backname.hashCode() : 0);
        result = 31 * result + (isbackdata != null ? isbackdata.hashCode() : 0);
        result = 31 * result + (isdayback != null ? isdayback.hashCode() : 0);
        result = 31 * result + (daybacktime != null ? daybacktime.hashCode() : 0);
        result = 31 * result + (ismonthback != null ? ismonthback.hashCode() : 0);
        result = 31 * result + (monthbackday != null ? monthbackday.hashCode() : 0);
        result = 31 * result + (monthbacktime != null ? monthbacktime.hashCode() : 0);
        result = 31 * result + (isweekback != null ? isweekback.hashCode() : 0);
        result = 31 * result + (weekbackday != null ? weekbackday.hashCode() : 0);
        result = 31 * result + (weekbacktime != null ? weekbacktime.hashCode() : 0);
        result = 31 * result + (backtype != null ? backtype.hashCode() : 0);
        return result;
    }
}
