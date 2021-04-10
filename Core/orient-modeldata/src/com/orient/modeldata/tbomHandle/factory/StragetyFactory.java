package com.orient.modeldata.tbomHandle.factory;

import com.orient.modeldata.tbomHandle.tbomStrategy.TbomNodeStrategy;

/**
 * 创建Tbom策略工厂接口
 * Created by enjoy on 2016/5/22 0022.
 */
public interface StragetyFactory {

    TbomNodeStrategy createTbomNodeStrategy();
}
