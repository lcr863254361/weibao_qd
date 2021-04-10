package com.orient.download.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.arrayToList;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2019-03-30 17:31
 */
public class StringUtils {

    public static List<String> splitToList(String str, String regex) {
        if(isNull(str)) {
            return null;
        } else {
            ArrayList resultList = new ArrayList();
            List resultObject = arrayToList(str.split(regex));
            Iterator i$ = resultObject.iterator();
            while(i$.hasNext()) {
                Object obj = i$.next();
                resultList.add(obj.toString());
            }
            return resultList;
        }
    }

}

