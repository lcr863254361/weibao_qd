package com.orient.modeldata.analyze.processor;

import java.sql.Timestamp;
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
public class SQLTimeProcessor extends Processor {
    public static String SQL_FRAGMENT = "/(24*3600*1000)+TO_TIMESTAMP('1970-01-01 08:00:00:000000', 'SYYYY-MM-DD HH24:MI:SS:FF6')";

    private SimpleDateFormat format;

    @Override
    public void process(List<String> rawData, Map<String, Object> destMap) {
        String val = rawData.get(this.getColNum());
        Timestamp timestamp = null;
        try {
            Date date = format.parse(val);
            timestamp = new Timestamp(date.getTime());
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
