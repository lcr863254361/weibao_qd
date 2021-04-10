package com.orient.background.controller;

import com.orient.background.bean.StaticticChartTypeWrapper;
import com.orient.background.business.StatisticChartTypeBusiness;
import com.orient.sysmodel.domain.statistic.CfChartTypeEntity;
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
@RequestMapping("/StatisticChartType")
public class StatisticChartTypeController extends BaseController {
    @Autowired
    StatisticChartTypeBusiness statisticChartTypeBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<StaticticChartTypeWrapper> list(Integer page, Integer limit, CfChartTypeEntity filter, String node) {
        if ("root".equals(node)) {
            return statisticChartTypeBusiness.listSpecial(page, limit, filter);
        } else
            return null;

    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(@RequestBody CfChartTypeEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        statisticChartTypeBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(@RequestBody CfChartTypeEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        statisticChartTypeBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(@RequestBody CfChartTypeEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        Long[] ids = new Long[]{formValue.getId()};
        statisticChartTypeBusiness.deleteSpecial(ids);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
