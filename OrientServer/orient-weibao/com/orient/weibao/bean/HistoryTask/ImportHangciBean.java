package com.orient.weibao.bean.HistoryTask;

import com.orient.utils.CommonTools;
import com.orient.utils.UtilFactory;

import java.sql.Date;
import java.util.List;

public class ImportHangciBean {
    private String hangciName="";
    private Date hangciStartDate = CommonTools.util2Sql(new java.util.Date());
    private Date hangciEndDate = CommonTools.util2Sql(new java.util.Date());
    private List<ImportHangduanBean> importHangduanBeanList = UtilFactory.newArrayList();

    public String getHangciName() {
        return hangciName;
    }

    public void setHangciName(String hangciName) {
        this.hangciName = hangciName;
    }

    public Date getHangciStartDate() {
        return hangciStartDate;
    }

    public void setHangciStartDate(Date hangciStartDate) {
        this.hangciStartDate = hangciStartDate;
    }

    public Date getHangciEndDate() {
        return hangciEndDate;
    }

    public void setHangciEndDate(Date hangciEndDate) {
        this.hangciEndDate = hangciEndDate;
    }

    public List<ImportHangduanBean> getImportHangduanBeanList() {
        return importHangduanBeanList;
    }

    public void setImportHangduanBeanList(List<ImportHangduanBean> importHangduanBeanList) {
        this.importHangduanBeanList = importHangduanBeanList;
    }
}
