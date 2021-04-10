package com.orient.businessmodel.bean;

import java.util.Map;

/**
 * if inherit this class, the raw data will be set in while executing query
 *
 * @author Seraph
 *         2016-07-21 下午4:22
 */
public class BaseDsModel {

    private Map rawData;

    public Map getRawData() {
        return rawData;
    }

    public void setRawData(Map rawData) {
        this.rawData = rawData;
    }
}
