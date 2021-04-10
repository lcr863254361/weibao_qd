package com.orient.background.controller;

import com.orient.background.business.DocReportItemsBusiness;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/DocReportItems")
public class DocReportItemsController extends BaseController {
    @Autowired
    DocReportItemsBusiness docReportItemsBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmDocReportItemsEntity> list(Integer page, Integer limit, CwmDocReportItemsEntity filter) {
        return docReportItemsBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmDocReportItemsEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        docReportItemsBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmDocReportItemsEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        docReportItemsBusiness.update(formValue);
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
        docReportItemsBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("saveBookMarks")
    @ResponseBody
    public AjaxResponseData<Map<String, String>> saveBookMarks(String[] bookMarkNames) {
        AjaxResponseData<Map<String, String>> retVal = new AjaxResponseData();
        Map<String, String> results = docReportItemsBusiness.saveBookMarks(bookMarkNames);
        retVal.setResults(results);
        return retVal;
    }

}
