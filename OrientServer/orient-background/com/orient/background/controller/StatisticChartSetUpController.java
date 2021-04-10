package com.orient.background.controller;

import com.orient.background.bean.StatisticChartSetUpWrapper;
import com.orient.background.bean.StatisticChartSyncData;
import com.orient.background.business.StatisticChartSetUpBusiness;
import com.orient.sysmodel.domain.statistic.CfStatisticChartEntity;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/StatisticChartSetUp")
public class StatisticChartSetUpController extends BaseController {
    @Autowired
    StatisticChartSetUpBusiness statisticChartSetUpBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<StatisticChartSetUpWrapper> list(Integer page, Integer limit, CfStatisticChartEntity filter, Long belongStatisSetUpId) {
        return statisticChartSetUpBusiness.listSpecial(page, limit, filter, belongStatisSetUpId);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CfStatisticChartEntity formValue, Long belongStatisSetUpId, Long belongStatisticChartInstanceId) {
        CommonResponseData retVal = new CommonResponseData();
        statisticChartSetUpBusiness.saveSpecial(formValue, belongStatisSetUpId, belongStatisticChartInstanceId);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CfStatisticChartEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        statisticChartSetUpBusiness.updateBasicAttr(formValue, "id");
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
        statisticChartSetUpBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("doSyncChartSetUp")
    @ResponseBody
    public CommonResponseData doSyncChartSet(@RequestBody StatisticChartSyncData statisticChartSyncData) {
        CommonResponseData retVal = new CommonResponseData();
        statisticChartSetUpBusiness.doSyncChartSet(statisticChartSyncData);
        return retVal;
    }

}
