package com.orient.modeldata.analyze.processor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-24 10:36
 */
public class TimeProcessor extends Processor {
    private SimpleDateFormat format;

    @Override
    public void process(List<String> rawData, Map<String, Object> destMap) {
        String val = rawData.get(this.getColNum());
        String timestamp = null;
        try {
            Date date = format.parse(val);
            timestamp = date.getTime()+"";
        }
        catch (Exception e) {

        }
        destMap.put(this.getMappedColName(), timestamp);
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }
}
