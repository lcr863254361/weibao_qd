package com.orient.background.bean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2017-04-08 10:18 AM
 */
public class StatisticChartSyncBean implements Serializable {

    Long id;

    String title;

    String customHandler;

    Long belongStatisSetUpId;

    String belongStaticChartInstanceName;

    Long belongStaticChartInstanceId;

    String preProcessor;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomHandler() {
        return customHandler;
    }

    public void setCustomHandler(String customHandler) {
        this.customHandler = customHandler;
    }

    public Long getBelongStatisSetUpId() {
        return belongStatisSetUpId;
    }

    public void setBelongStatisSetUpId(Long belongStatisSetUpId) {
        this.belongStatisSetUpId = belongStatisSetUpId;
    }

    public String getBelongStaticChartInstanceName() {
        return belongStaticChartInstanceName;
    }

    public void setBelongStaticChartInstanceName(String belongStaticChartInstanceName) {
        this.belongStaticChartInstanceName = belongStaticChartInstanceName;
    }

    public Long getBelongStaticChartInstanceId() {
        return belongStaticChartInstanceId;
    }

    public void setBelongStaticChartInstanceId(Long belongStaticChartInstanceId) {
        this.belongStaticChartInstanceId = belongStaticChartInstanceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreProcessor() {
        return preProcessor;
    }

    public void setPreProcessor(String preProcessor) {
        this.preProcessor = preProcessor;
    }
}
