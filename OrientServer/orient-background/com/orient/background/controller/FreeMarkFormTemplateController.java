package com.orient.background.controller;

import com.orient.background.business.FreemarkTemplateBusiness;
import com.orient.sysmodel.domain.form.FreemarkTemplateEntity;
import com.orient.web.base.ExtComboboxData;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by enjoy on 2016/3/15 0015.
 * Freemarker模板管理
 */
@Controller
@RequestMapping("/freeMarkFormTemplate")
public class FreeMarkFormTemplateController {

    @Autowired
    FreemarkTemplateBusiness freemarkTemplateBusiness;

    /**
     * 展现数据表格
     *
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<FreemarkTemplateEntity> list(Integer page, Integer limit,FreemarkTemplateEntity filter) {
        return freemarkTemplateBusiness.list(page,limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(FreemarkTemplateEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        freemarkTemplateBusiness.save(formValue);
        retVal.setMsg("新增成功");
        return retVal;
    }

    /**
     * 更新表格
     *
     * @param formValue
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(FreemarkTemplateEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        freemarkTemplateBusiness.update(formValue);
        retVal.setMsg("更新成功");
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
        freemarkTemplateBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 初始化系统模板
     *
     * @return
     */
    @RequestMapping("init")
    @ResponseBody
    public CommonResponseData init() {
        CommonResponseData retVal = new CommonResponseData();
        freemarkTemplateBusiness.initSystemTemplate();
        retVal.setMsg("初始化成功");
        return retVal;
    }

    /**
     * 选择Freemarker模板
     *
     * @param startIndex
     * @param maxResults
     * @param filter
     * @return
     */
    @RequestMapping("getFreemarkerTemplateCombobox")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getFreemarkerTemplateCombobox(Integer startIndex, Integer maxResults, String filter) {
        ExtComboboxResponseData<ExtComboboxData> retValue = freemarkTemplateBusiness.getFreemarkerTemplateCombobox(startIndex, maxResults, filter);
        return retValue;
    }
}
