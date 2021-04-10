package com.orient.modeldata.analyze.processor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-24 10:36
 */
public class SQLTimestampProcessor extends Processor {
    public static String SQL_FRAGMENT = "/(24*3600*1000)+TO_TIMESTAMP('1970-01-01 08:00:00:000000', 'SYYYY-MM-DD HH24:MI:SS:FF6')";

    @Override
    public void process(List<String> rawData, Map<String, Object> destMap) {
        String val = rawData.get(this.getColNum());
        Timestamp timestamp = null;
        try {
            long ts = Long.valueOf(val);
            timestamp = new Timestamp(ts);
        }
        catch(Exception e) {

        }
        destMap.put(this.getMappedColName(), timestamp);
    }
}
