package com.orient.download.bean.inform;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-06-13 16:57
 */
public class CurrentStateBean {
    //当前状态ID
    private String id;
    //当前状态的名称，比如下潜作业等
    private String name;
    private List<InformBean> informBeanList=UtilFactory.newArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InformBean> getInformBeanList() {
        return informBeanList;
    }

    public void setInformBeanList(List<InformBean> informBeanList) {
        this.informBeanList = informBeanList;
    }
}
