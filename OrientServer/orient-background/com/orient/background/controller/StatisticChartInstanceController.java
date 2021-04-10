package com.orient.background.controller;

import com.orient.background.bean.StatisticChartInstanceWrapper;
import com.orient.background.business.StatisticChartInstanceBusiness;
import com.orient.sysmodel.domain.statistic.CfChartInstanceEntity;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/StatisticChartInstance")
public class StatisticChartInstanceController extends BaseController {
    @Autowired
    StatisticChartInstanceBusiness statisticChartInstanceBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<StatisticChartInstanceWrapper> list(Integer page, Integer limit, CfChartInstanceEntity filter, Long belongChartTypeId) {
        return statisticChartInstanceBusiness.listSpecial(page, limit, filter, belongChartTypeId);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CfChartInstanceEntity formValue, Long belongChartTypeId) {
        CommonResponseData retVal = new CommonResponseData();
        statisticChartInstanceBusiness.saveSpecial(formValue, belongChartTypeId);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CfChartInstanceEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        statisticChartInstanceBusiness.update(formValue);
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
        statisticChartInstanceBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
