package com.orient.background.controller;

import com.orient.background.bean.CwmDocReportsEntityWrapper;
import com.orient.background.bean.ModelColumnHandler;
import com.orient.background.business.DocReportsBusiness;
import com.orient.sysmodel.domain.doc.CwmDocReportsEntity;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.model.BaseNode;
import com.zhuozhengsoft.pageoffice.PageOfficeLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/DocReports")
public class DocReportsController extends BaseController {
    @Autowired
    DocReportsBusiness docReportsBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmDocReportsEntityWrapper> list(Integer page, Integer limit, CwmDocReportsEntity filter) {
        return docReportsBusiness.listSpecial(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmDocReportsEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        docReportsBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmDocReportsEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        docReportsBusiness.update(formValue);
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
        docReportsBusiness.deleteSpecial(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getRelatedModels")
    @ResponseBody
    public ExtGridData<BaseNode> getRelatedModels(String mainModelId, String node) {
        ExtGridData<BaseNode> results = docReportsBusiness.getRelatedModels(mainModelId, node);
        return results;
    }

    @RequestMapping("listColumns")
    @ResponseBody
    public ExtGridData<ModelColumnHandler> listColumns(String modelId) {
        ExtGridData<ModelColumnHandler> results = docReportsBusiness.listColumns(modelId);
        return results;
    }

    @RequestMapping("saveSepcial")
    @ResponseBody
    public CommonResponseData saveSepcial(@RequestBody CwmDocReportsEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        docReportsBusiness.saveSpecial(formValue);
        return retVal;
    }

    @RequestMapping("updateSepcial")
    @ResponseBody
    public CommonResponseData updateSepcial(String reportName, Long reportId) {
        CommonResponseData retVal = new CommonResponseData();
        docReportsBusiness.updateSepcial(reportId, reportName);
        return retVal;
    }


    @RequestMapping("generateReport")
    public String generateReport(HttpServletRequest request, String dataId, Long reportId) {
        String docName = docReportsBusiness.generateReport(reportId, dataId);
        String fileJspPath = "/app/views/doctemplate/docTemplateView.jsp?reportName=" + docName;
        return "redirect:" + PageOfficeLink.openWindow(request, fileJspPath, "width=800px;height=800px;");
    }

    @RequestMapping("generateReportForIE")
    public String generateReportForIE(HttpServletRequest request, String dataId, Long reportId) {
        String docName = docReportsBusiness.generateReport(reportId, dataId);
        return "/app/views/doctemplate/docTemplateView.jsp?reportName="+ docName;
    }
}
