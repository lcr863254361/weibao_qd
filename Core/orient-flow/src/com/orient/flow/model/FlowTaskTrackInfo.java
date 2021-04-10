package com.orient.flow.model;

import com.orient.utils.UtilFactory;

import java.util.Date;
import java.util.List;

public class FlowTaskTrackInfo {

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndTimeValue() {
        return endTimeValue;
    }

    public void setEndTimeValue(String endTimeValue) {
        this.endTimeValue = endTimeValue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<TransitionInfo> getTransitionInfos() {
        return transitionInfos;
    }

    public void setTransitionInfos(List<TransitionInfo> transitionInfos) {
        this.transitionInfos = transitionInfos;
    }

    static public class TransitionInfo {
        public String getDestName() {
            return destName;
        }

        public void setDestName(String destName) {
            this.destName = destName;
        }

        public String getTransName() {
            return transName;
        }

        public void setTransName(String transName) {
            this.transName = transName;
        }

        public String getTransInfo() {
            return transInfo;
        }

        public void setTransInfo(String transInfo) {
            this.transInfo = transInfo;
        }

        private String destName = "";
        private String transName = "";
        private String transInfo = "";

    }

    private List<TransitionInfo> transitionInfos = UtilFactory.newArrayList();
    private String taskName = "";
    private String status = "";
    private Date startTime;
    private String endTimeValue;
    private int index = 0;
}
