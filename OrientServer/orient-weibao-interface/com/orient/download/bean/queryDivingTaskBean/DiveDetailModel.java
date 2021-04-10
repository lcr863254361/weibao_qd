package com.orient.download.bean.queryDivingTaskBean;

import java.io.Serializable;

public class DiveDetailModel implements Serializable {
    private String taskId;
    private String divingTime;
    private String seaArea;
    private String depth;
    private String workTimeLong;
    private String waterTimeLong;
    private String zuoxianPerson;
    private String mainDriverPerson;
    private String youxianPerson;
    private String zuoxianCompany;
    private String youxianCompany;
    private String workContent;
    private String jingdu;
    private String weidu;
    //航次
    private String hangciName;
    //航段
    private String hangduanName;
    //东/西半球
    private String eastWestHalfsphere;
    //南/北半球
    private String southNorthHalfsphere;
    //时区
    private String timeZone;
    //布放类型（夜：当地19点至6点）
    private String bufangType;
    //水中时长
    private String waterHours;
    //作业时长
    private String homeWorkHours;
    //采样作业情况
    private String sampleSituation;
    //海底环境描述
    private String seafloorEnvironmentDesp;
    //潜水器水下行驶轨迹图
//    private String waterDownPictures;
    //下达布放指令时间
    private String bufangCommandTime;
    //人员进舱时间
    private String personComeinCabinTime;
    //舱口盖关闭时间
    private String hatchCloseTime;
    //压载铁限位销拆除时间
    private String ballastRemoveTime;
    //潜水器入水时间
    private String divingDEntryTime;
    //开始注水时间
    private String startFillWaterTime;
    //开始作业时间（抛弃终止下潜压载）
    private String startWorkTime;
    //作业开始深度（米）
    private String workStartDepth;
    //结束作业时间（抛弃上浮压载）
    private String endWorkTime;
    //作业结束深度（米）
    private String workEndDepth;
    //上浮到水面时间
    private String floatWaterTime;
    //潜水器出水时间
    private String divingDOutWaterTime;
    //回收到甲板时间
    private String recoverDeckTime;
    //舱口盖开启时间
    private String hatchOpenTime;
    //人员出舱时间
    private String personComeOutCabinTime;
    //一次布放小艇时间
    private String onceBufangSmallboatTime;
    //一次回收小艇时间
    private String onceRecoverSmallboatTime;
    //布放时小艇驾驶人员
    private String bufangSmallboatDriverPeople;
    //布放时小艇辅助人员
    private String bufangSmallboatAssistant;
    //脱缆人员
    private String tuolanPeople;
    //脱缆辅助人员
    private String tuolanAssistant;
    //布放时最大阵风风速（节）
    private String bufangMaxWindSpeed;
    //布放时最大平均风速（节）
    private String bufangAverageWindSpeed;
    //布放时浪高（米）
    private String bufangLangHeight;
    //布放时海况评估（级）
    private String  bufangSeaStateEstimate;
    //二次布放小艇时间
    private String twiceBufangSmallboatTime;
    //二次回收小艇时间
    private String twiceRecoverSmallboatTime;
    //回收时小艇驾驶人员
    private String recoverSmallboatDperson;
    //回收时小艇辅助人员
    private String recoverSmallboatAssistant;
    //挂缆人员
    private String gualanPeople;
    //挂缆辅助人员
    private String gualanAssistant;
    //回收时最大阵风风速（节）
    private String recoverMaxWindSpeed;
    //回收时最大平均风速（节）
    private String recoverMaxAverageWindSpeed;
    //回收时浪高（米）
    private String recoverLangHeight;
    //回收时海况评估（级）
    private String recoverSeaStateEstimate;
    //特别说明
    private String specialVersion;

    public String getHangciName() {
        return hangciName;
    }

    public void setHangciName(String hangciName) {
        this.hangciName = hangciName;
    }

    public String getHangduanName() {
        return hangduanName;
    }

    public void setHangduanName(String hangduanName) {
        this.hangduanName = hangduanName;
    }

    public String getEastWestHalfsphere() {
        return eastWestHalfsphere;
    }

    public void setEastWestHalfsphere(String eastWestHalfsphere) {
        this.eastWestHalfsphere = eastWestHalfsphere;
    }

    public String getSouthNorthHalfsphere() {
        return southNorthHalfsphere;
    }

    public void setSouthNorthHalfsphere(String southNorthHalfsphere) {
        this.southNorthHalfsphere = southNorthHalfsphere;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getBufangType() {
        return bufangType;
    }

    public void setBufangType(String bufangType) {
        this.bufangType = bufangType;
    }

    public String getWaterHours() {
        return waterHours;
    }

    public void setWaterHours(String waterHours) {
        this.waterHours = waterHours;
    }

    public String getHomeWorkHours() {
        return homeWorkHours;
    }

    public void setHomeWorkHours(String homeWorkHours) {
        this.homeWorkHours = homeWorkHours;
    }

    public String getSampleSituation() {
        return sampleSituation;
    }

    public void setSampleSituation(String sampleSituation) {
        this.sampleSituation = sampleSituation;
    }

    public String getSeafloorEnvironmentDesp() {
        return seafloorEnvironmentDesp;
    }

    public void setSeafloorEnvironmentDesp(String seafloorEnvironmentDesp) {
        this.seafloorEnvironmentDesp = seafloorEnvironmentDesp;
    }

//    public String getWaterDownPictures() {
//        return waterDownPictures;
//    }
//
//    public void setWaterDownPictures(String waterDownPictures) {
//        this.waterDownPictures = waterDownPictures;
//    }

    public String getBufangCommandTime() {
        return bufangCommandTime;
    }

    public void setBufangCommandTime(String bufangCommandTime) {
        this.bufangCommandTime = bufangCommandTime;
    }

    public String getPersonComeinCabinTime() {
        return personComeinCabinTime;
    }

    public void setPersonComeinCabinTime(String personComeinCabinTime) {
        this.personComeinCabinTime = personComeinCabinTime;
    }

    public String getHatchCloseTime() {
        return hatchCloseTime;
    }

    public void setHatchCloseTime(String hatchCloseTime) {
        this.hatchCloseTime = hatchCloseTime;
    }

    public String getBallastRemoveTime() {
        return ballastRemoveTime;
    }

    public void setBallastRemoveTime(String ballastRemoveTime) {
        this.ballastRemoveTime = ballastRemoveTime;
    }

    public String getDivingDEntryTime() {
        return divingDEntryTime;
    }

    public void setDivingDEntryTime(String divingDEntryTime) {
        this.divingDEntryTime = divingDEntryTime;
    }

    public String getStartFillWaterTime() {
        return startFillWaterTime;
    }

    public void setStartFillWaterTime(String startFillWaterTime) {
        this.startFillWaterTime = startFillWaterTime;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public String getWorkStartDepth() {
        return workStartDepth;
    }

    public void setWorkStartDepth(String workStartDepth) {
        this.workStartDepth = workStartDepth;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public String getWorkEndDepth() {
        return workEndDepth;
    }

    public void setWorkEndDepth(String workEndDepth) {
        this.workEndDepth = workEndDepth;
    }

    public String getFloatWaterTime() {
        return floatWaterTime;
    }

    public void setFloatWaterTime(String floatWaterTime) {
        this.floatWaterTime = floatWaterTime;
    }

    public String getDivingDOutWaterTime() {
        return divingDOutWaterTime;
    }

    public void setDivingDOutWaterTime(String divingDOutWaterTime) {
        this.divingDOutWaterTime = divingDOutWaterTime;
    }

    public String getRecoverDeckTime() {
        return recoverDeckTime;
    }

    public void setRecoverDeckTime(String recoverDeckTime) {
        this.recoverDeckTime = recoverDeckTime;
    }

    public String getHatchOpenTime() {
        return hatchOpenTime;
    }

    public void setHatchOpenTime(String hatchOpenTime) {
        this.hatchOpenTime = hatchOpenTime;
    }

    public String getPersonComeOutCabinTime() {
        return personComeOutCabinTime;
    }

    public void setPersonComeOutCabinTime(String personComeOutCabinTime) {
        this.personComeOutCabinTime = personComeOutCabinTime;
    }

    public String getOnceBufangSmallboatTime() {
        return onceBufangSmallboatTime;
    }

    public void setOnceBufangSmallboatTime(String onceBufangSmallboatTime) {
        this.onceBufangSmallboatTime = onceBufangSmallboatTime;
    }

    public String getOnceRecoverSmallboatTime() {
        return onceRecoverSmallboatTime;
    }

    public void setOnceRecoverSmallboatTime(String onceRecoverSmallboatTime) {
        this.onceRecoverSmallboatTime = onceRecoverSmallboatTime;
    }

    public String getBufangSmallboatDriverPeople() {
        return bufangSmallboatDriverPeople;
    }

    public void setBufangSmallboatDriverPeople(String bufangSmallboatDriverPeople) {
        this.bufangSmallboatDriverPeople = bufangSmallboatDriverPeople;
    }

    public String getBufangSmallboatAssistant() {
        return bufangSmallboatAssistant;
    }

    public void setBufangSmallboatAssistant(String bufangSmallboatAssistant) {
        this.bufangSmallboatAssistant = bufangSmallboatAssistant;
    }

    public String getTuolanPeople() {
        return tuolanPeople;
    }

    public void setTuolanPeople(String tuolanPeople) {
        this.tuolanPeople = tuolanPeople;
    }

    public String getTuolanAssistant() {
        return tuolanAssistant;
    }

    public void setTuolanAssistant(String tuolanAssistant) {
        this.tuolanAssistant = tuolanAssistant;
    }

    public String getBufangMaxWindSpeed() {
        return bufangMaxWindSpeed;
    }

    public void setBufangMaxWindSpeed(String bufangMaxWindSpeed) {
        this.bufangMaxWindSpeed = bufangMaxWindSpeed;
    }

    public String getBufangAverageWindSpeed() {
        return bufangAverageWindSpeed;
    }

    public void setBufangAverageWindSpeed(String bufangAverageWindSpeed) {
        this.bufangAverageWindSpeed = bufangAverageWindSpeed;
    }

    public String getBufangLangHeight() {
        return bufangLangHeight;
    }

    public void setBufangLangHeight(String bufangLangHeight) {
        this.bufangLangHeight = bufangLangHeight;
    }

    public String getBufangSeaStateEstimate() {
        return bufangSeaStateEstimate;
    }

    public void setBufangSeaStateEstimate(String bufangSeaStateEstimate) {
        this.bufangSeaStateEstimate = bufangSeaStateEstimate;
    }

    public String getTwiceBufangSmallboatTime() {
        return twiceBufangSmallboatTime;
    }

    public void setTwiceBufangSmallboatTime(String twiceBufangSmallboatTime) {
        this.twiceBufangSmallboatTime = twiceBufangSmallboatTime;
    }

    public String getTwiceRecoverSmallboatTime() {
        return twiceRecoverSmallboatTime;
    }

    public void setTwiceRecoverSmallboatTime(String twiceRecoverSmallboatTime) {
        this.twiceRecoverSmallboatTime = twiceRecoverSmallboatTime;
    }

    public String getRecoverSmallboatDperson() {
        return recoverSmallboatDperson;
    }

    public void setRecoverSmallboatDperson(String recoverSmallboatDperson) {
        this.recoverSmallboatDperson = recoverSmallboatDperson;
    }

    public String getRecoverSmallboatAssistant() {
        return recoverSmallboatAssistant;
    }

    public void setRecoverSmallboatAssistant(String recoverSmallboatAssistant) {
        this.recoverSmallboatAssistant = recoverSmallboatAssistant;
    }

    public String getGualanPeople() {
        return gualanPeople;
    }

    public void setGualanPeople(String gualanPeople) {
        this.gualanPeople = gualanPeople;
    }

    public String getGualanAssistant() {
        return gualanAssistant;
    }

    public void setGualanAssistant(String gualanAssistant) {
        this.gualanAssistant = gualanAssistant;
    }

    public String getRecoverMaxWindSpeed() {
        return recoverMaxWindSpeed;
    }

    public void setRecoverMaxWindSpeed(String recoverMaxWindSpeed) {
        this.recoverMaxWindSpeed = recoverMaxWindSpeed;
    }

    public String getRecoverMaxAverageWindSpeed() {
        return recoverMaxAverageWindSpeed;
    }

    public void setRecoverMaxAverageWindSpeed(String recoverMaxAverageWindSpeed) {
        this.recoverMaxAverageWindSpeed = recoverMaxAverageWindSpeed;
    }

    public String getRecoverLangHeight() {
        return recoverLangHeight;
    }

    public void setRecoverLangHeight(String recoverLangHeight) {
        this.recoverLangHeight = recoverLangHeight;
    }

    public String getRecoverSeaStateEstimate() {
        return recoverSeaStateEstimate;
    }

    public void setRecoverSeaStateEstimate(String recoverSeaStateEstimate) {
        this.recoverSeaStateEstimate = recoverSeaStateEstimate;
    }

    public String getSpecialVersion() {
        return specialVersion;
    }

    public void setSpecialVersion(String specialVersion) {
        this.specialVersion = specialVersion;
    }

    public String getDivingTime() {
        return divingTime;
    }

    public void setDivingTime(String divingTime) {
        this.divingTime = divingTime;
    }

    public String getSeaArea() {
        return seaArea;
    }

    public void setSeaArea(String seaArea) {
        this.seaArea = seaArea;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getWorkTimeLong() {
        return workTimeLong;
    }

    public void setWorkTimeLong(String workTimeLong) {
        this.workTimeLong = workTimeLong;
    }

    public String getWaterTimeLong() {
        return waterTimeLong;
    }

    public void setWaterTimeLong(String waterTimeLong) {
        this.waterTimeLong = waterTimeLong;
    }

    public String getZuoxianPerson() {
        return zuoxianPerson;
    }

    public void setZuoxianPerson(String zuoxianPerson) {
        this.zuoxianPerson = zuoxianPerson;
    }

    public String getMainDriverPerson() {
        return mainDriverPerson;
    }

    public void setMainDriverPerson(String mainDriverPerson) {
        this.mainDriverPerson = mainDriverPerson;
    }

    public String getYouxianPerson() {
        return youxianPerson;
    }

    public void setYouxianPerson(String youxianPerson) {
        this.youxianPerson = youxianPerson;
    }

    public String getZuoxianCompany() {
        return zuoxianCompany;
    }

    public void setZuoxianCompany(String zuoxianCompany) {
        this.zuoxianCompany = zuoxianCompany;
    }

    public String getYouxianCompany() {
        return youxianCompany;
    }

    public void setYouxianCompany(String youxianCompany) {
        this.youxianCompany = youxianCompany;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getJingdu() {
        return jingdu;
    }

    public void setJingdu(String jingdu) {
        this.jingdu = jingdu;
    }

    public String getWeidu() {
        return weidu;
    }

    public void setWeidu(String weidu) {
        this.weidu = weidu;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

}
