package com.orient.utils;

import java.util.List;

/**
 * 分页
 *
 * @author enjoy
 * @createTime 2016-05-26 14:44
 */
public class PageUtil {

    /**
     * @param allData 所有数据
     * @param page    第几页 从1开始
     * @param limit   每页多少条
     * @return 分页后的集合
     */
    public static <T> List<T> page(List<T> allData, Integer page, Integer limit) {
        if (page != null && null != limit && page > 0) {
            int collectionSize = allData.size();
            int start = Math.min((page - 1) * limit, collectionSize);
            int end = Math.min((page) * limit, collectionSize);
            return allData.subList(start, end);
        } else
            return allData;
    }

}
