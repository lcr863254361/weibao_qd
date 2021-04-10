package com.orient.background.doctemplate.transform;

import com.orient.background.doctemplate.bean.DocHandlerData;

import java.util.List;
import java.util.Map;

/**
 * transform to stander data
 *
 * @author panduanduan
 * @create 2016-12-07 8:41 PM
 */
public interface IDocTransform <T>{

    DocHandlerData doTransform(T target,List<Map> dataSource);
}
