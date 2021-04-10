package com.orient.modeldata.analyze.processor;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-24 10:36
 */
public class DefalutValueProcessor extends Processor {
    private String defalutValue;

    @Override
    public void process(List<String> rawData, Map<String, Object> destMap) {
        destMap.put(this.getMappedColName(), defalutValue);
    }

    public String getDefalutValue() {
        return defalutValue;
    }

    public void setDefalutValue(String defalutValue) {
        this.defalutValue = defalutValue;
    }
}
