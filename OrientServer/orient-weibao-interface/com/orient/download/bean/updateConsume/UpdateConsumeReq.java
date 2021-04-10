package com.orient.download.bean.updateConsume;

import com.orient.download.bean.sparePartsBean.ConsumeBean;
import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-06-05 9:52
 */
public class UpdateConsumeReq {
    private List<ConsumeBean>  consumeBeanList= UtilFactory.newArrayList();

    public List<ConsumeBean> getConsumeBeanList() {
        return consumeBeanList;
    }

    public void setConsumeBeanList(List<ConsumeBean> consumeBeanList) {
        this.consumeBeanList = consumeBeanList;
    }

    public int getConsumeListSize(){
        return this.consumeBeanList.size();
    }
}
