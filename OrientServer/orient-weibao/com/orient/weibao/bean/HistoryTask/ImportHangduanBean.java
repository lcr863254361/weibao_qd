package com.orient.weibao.bean.HistoryTask;

import com.orient.utils.CommonTools;

import java.sql.Date;

public class ImportHangduanBean {
    private String hangduanName;
    private Date startDate= CommonTools.util2Sql(new java.util.Date());
    private Date wharfDate=CommonTools.util2Sql(new java.util.Date());
    private String gotoSeaDays;
    private String divingTimes;
    private String validDiving;
    private String supportProject;
    private String hangciQuality;
    private String hangciLeaderExpert;
    private String hangciLeader;
    private String hangciChiefExpert;
    private String hangciChiefScientist;
    private String hangciChiefSAssistant;
    private String firstDivingDepartor;
    private String secondMaster;
    private String thirdWaterSupport;
    private String hangciTopRecord;
    private String hangciResult;
    private String hangciReportVideo;
    private String hangciFile;
    private String hangciDivingMap;
    private String hangduanId;

    public String getHangduanId() {
        return hangduanId;
    }

    public void setHangduanId(String hangduanId) {
        this.hangduanId = hangduanId;
    }

    public String getHangduanName() {
        return hangduanName;
    }

    public void setHangduanName(String hangduanName) {
        this.hangduanName = hangduanName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getWharfDate() {
        return wharfDate;
    }

    public void setWharfDate(Date wharfDate) {
        this.wharfDate = wharfDate;
    }

    public String getGotoSeaDays() {
        return gotoSeaDays;
    }

    public void setGotoSeaDays(String gotoSeaDays) {
        this.gotoSeaDays = gotoSeaDays;
    }

    public String getDivingTimes() {
        return divingTimes;
    }

    public void setDivingTimes(String divingTimes) {
        this.divingTimes = divingTimes;
    }

    public String getValidDiving() {
        return validDiving;
    }

    public void setValidDiving(String validDiving) {
        this.validDiving = validDiving;
    }

    public String getSupportProject() {
        return supportProject;
    }

    public void setSupportProject(String supportProject) {
        this.supportProject = supportProject;
    }

    public String getHangciQuality() {
        return hangciQuality;
    }

    public void setHangciQuality(String hangciQuality) {
        this.hangciQuality = hangciQuality;
    }

    public String getHangciLeaderExpert() {
        return hangciLeaderExpert;
    }

    public void setHangciLeaderExpert(String hangciLeaderExpert) {
        this.hangciLeaderExpert = hangciLeaderExpert;
    }

    public String getHangciLeader() {
        return hangciLeader;
    }

    public void setHangciLeader(String hangciLeader) {
        this.hangciLeader = hangciLeader;
    }

    public String getHangciChiefExpert() {
        return hangciChiefExpert;
    }

    public void setHangciChiefExpert(String hangciChiefExpert) {
        this.hangciChiefExpert = hangciChiefExpert;
    }

    public String getHangciChiefScientist() {
        return hangciChiefScientist;
    }

    public void setHangciChiefScientist(String hangciChiefScientist) {
        this.hangciChiefScientist = hangciChiefScientist;
    }

    public String getHangciChiefSAssistant() {
        return hangciChiefSAssistant;
    }

    public void setHangciChiefSAssistant(String hangciChiefSAssistant) {
        this.hangciChiefSAssistant = hangciChiefSAssistant;
    }

    public String getFirstDivingDepartor() {
        return firstDivingDepartor;
    }

    public void setFirstDivingDepartor(String firstDivingDepartor) {
        this.firstDivingDepartor = firstDivingDepartor;
    }

    public String getSecondMaster() {
        return secondMaster;
    }

    public void setSecondMaster(String secondMaster) {
        this.secondMaster = secondMaster;
    }

    public String getThirdWaterSupport() {
        return thirdWaterSupport;
    }

    public void setThirdWaterSupport(String thirdWaterSupport) {
        this.thirdWaterSupport = thirdWaterSupport;
    }

    public String getHangciTopRecord() {
        return hangciTopRecord;
    }

    public void setHangciTopRecord(String hangciTopRecord) {
        this.hangciTopRecord = hangciTopRecord;
    }

    public String getHangciResult() {
        return hangciResult;
    }

    public void setHangciResult(String hangciResult) {
        this.hangciResult = hangciResult;
    }

    public String getHangciReportVideo() {
        return hangciReportVideo;
    }

    public void setHangciReportVideo(String hangciReportVideo) {
        this.hangciReportVideo = hangciReportVideo;
    }

    public String getHangciFile() {
        return hangciFile;
    }

    public void setHangciFile(String hangciFile) {
        this.hangciFile = hangciFile;
    }

    public String getHangciDivingMap() {
        return hangciDivingMap;
    }

    public void setHangciDivingMap(String hangciDivingMap) {
        this.hangciDivingMap = hangciDivingMap;
    }
}
