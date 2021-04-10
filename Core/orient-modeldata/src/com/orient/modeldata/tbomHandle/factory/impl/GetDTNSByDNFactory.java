package com.orient.modeldata.tbomHandle.factory.impl;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.tbomHandle.factory.StragetyFactory;
import com.orient.modeldata.tbomHandle.tbomStrategy.TbomNodeStrategy;

/**
 * @author enjoy
 * @createTime 2016-05-22 11:12
 */
public class GetDTNSByDNFactory implements StragetyFactory{

    @Override
    public TbomNodeStrategy createTbomNodeStrategy() {
        return (TbomNodeStrategy)OrientContextLoaderListener.Appwac.getBean("getDTNSByDNStrategy");
    }
}
