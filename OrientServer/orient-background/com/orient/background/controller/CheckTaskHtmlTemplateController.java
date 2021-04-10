package com.orient.background.controller;

import com.orient.background.business.CheckTaskHtmlTemplateBusiness;
import com.orient.sysmodel.domain.pvm.CwmTaskcheckHtmlEntity;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * xax
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/CheckTaskHtmlTemplate")
public class CheckTaskHtmlTemplateController {
    @Autowired
    CheckTaskHtmlTemplateBusiness checkTaskHtmlTemplateBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmTaskcheckHtmlEntity> list(Integer page, Integer limit, CwmTaskcheckHtmlEntity filter) {
        return checkTaskHtmlTemplateBusiness.list(page,limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmTaskcheckHtmlEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        checkTaskHtmlTemplateBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmTaskcheckHtmlEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        checkTaskHtmlTemplateBusiness.update(formValue);
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
        checkTaskHtmlTemplateBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
