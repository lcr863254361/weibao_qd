package com.orient.background.controller;

import com.orient.background.business.StatisticFilterSetUpBusiness;
import com.orient.sysmodel.domain.statistic.CfStatisticFilterEntity;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/StatisticFilterSetUp")
public class StatisticFilterSetUpController  extends BaseController{
    @Autowired
    StatisticFilterSetUpBusiness statisticFilterSetUpBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CfStatisticFilterEntity> list(Integer page, Integer limit, CfStatisticFilterEntity filter) {
        return statisticFilterSetUpBusiness.list(page,limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CfStatisticFilterEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        statisticFilterSetUpBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CfStatisticFilterEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        statisticFilterSetUpBusiness.updateBasicAttr(formValue,"id");
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        statisticFilterSetUpBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
