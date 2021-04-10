package com.orient.metamodel.metadomain;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;

import java.io.Serializable;

/**
 * @author mengbin@cssrc.com.cn
 * @date Feb 10, 2012
 */
public class BaseMetaBean implements Serializable {

    /**
     * 用于XML中的唯一标识
     */
    private String identity = "";

    /**
     * 获取POJO的对象
     *
     * @param beanName 要获取POJO的名称
     * @return Object
     */
    public Object getBean(String beanName) {
        return OrientContextLoaderListener.Appwac.getBean(beanName);
    }

    public MetaDAOFactory getMetaDAOFactory() {
        return (MetaDAOFactory) getBean("metadaofactory");
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

}
