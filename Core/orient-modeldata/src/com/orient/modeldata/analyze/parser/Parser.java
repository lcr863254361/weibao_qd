package com.orient.modeldata.analyze.parser;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-24 13:59
 */
public interface Parser {
    boolean readHeaders(int row);

    List<String> getHeaders();

    boolean readRecord();

    //添加判断当前行是否为空的接口 TeddyJohnson 2018.7.27
    boolean readCurrentRecord();

    List<String> getValues();
}
