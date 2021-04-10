package com.orient.background.controller;

import com.orient.background.bean.StatisticSetUpWrapper;
import com.orient.background.business.StatisticSetUpBusiness;
import com.orient.background.statistic.bean.StatisticResult;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.statistic.CfStatiscticEntity;
import com.orient.utils.StringUtil;
import com.orient.web.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/StatisticSetUp")
public class StatisticSetUpController extends BaseController {
    @Autowired
    StatisticSetUpBusiness statisticSetUpBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<StatisticSetUpWrapper> list(Integer page, Integer limit, CfStatiscticEntity filter) {
        return statisticSetUpBusiness.listSpecial(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public AjaxResponseData<Long> create(CfStatiscticEntity formValue) {
        AjaxResponseData retVal = new AjaxResponseData();
        statisticSetUpBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        retVal.setResults(formValue.getId());
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CfStatiscticEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        statisticSetUpBusiness.updateBasicAttr(formValue, "id");
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
        statisticSetUpBusiness.delete(toDelIds, true);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getStatisticPreProcessor")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getStatisticPreProcessor(Integer startIndex, Integer maxResults, String filter) {
        ExtComboboxResponseData<ExtComboboxData> retValue = new ExtComboboxResponseData<>();
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(com.orient.background.statistic.StatisticPreProcessor.class);
        List<ExtComboboxData> dataList = new ArrayList<>();
        for (String beanName : beanNames) {
            ExtComboboxData extComboboxData = new ExtComboboxData();
            extComboboxData.setId(beanName);
            extComboboxData.setValue(beanName);
            dataList.add(extComboboxData);
        }
        retValue.setResults(dataList);
        retValue.setTotalProperty(dataList.size());
        return retValue;
    }

    @RequestMapping("validateSql")
    @ResponseBody
    public CommonResponseData doValidateSql(String sql, String params, String preProcessor, HttpServletRequest request) {
        CommonResponseData retVal = new CommonResponseData();
        String error = statisticSetUpBusiness.doValidateSql(sql, params, preProcessor, request);
        retVal.setMsg(StringUtil.isEmpty(error) ? "校验通过" : "校验未通过异常为:" + error);
        return retVal;
    }

    @RequestMapping("doStatisic")
    @ResponseBody
    public AjaxResponseData<StatisticResult> getStatisic(Long statisticId, String params, HttpServletRequest request) {
        AjaxResponseData retVal = new AjaxResponseData();
        StatisticResult result = statisticSetUpBusiness.doStatisic(statisticId, params, request);
        retVal.setResults(result);
        return retVal;
    }
}
