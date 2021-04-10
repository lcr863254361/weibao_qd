package com.orient.modeldata.analyze.processor;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-24 10:36
 */
public class StringProcessor extends Processor {
    @Override
    public void process(List<String> rawData, Map<String, Object> destMap) {
        String val = rawData.get(this.getColNum());
        destMap.put(this.getMappedColName(), val);
    }
}
