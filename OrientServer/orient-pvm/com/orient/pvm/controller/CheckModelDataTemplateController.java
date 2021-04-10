package com.orient.pvm.controller;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.pvm.bean.CheckModelDataTemplateWrapper;
import com.orient.pvm.business.CheckModelDataTemplateBusiness;
import com.orient.pvm.event.ImportCheckTemplateEvent;
import com.orient.pvm.eventparam.ImportCheckTemplateEventParam;
import com.orient.sysmodel.domain.pvm.CheckModelDataTemplate;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.util.ExcelUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/CheckModelDataTemplate")
public class CheckModelDataTemplateController extends BaseController {
    @Autowired
    CheckModelDataTemplateBusiness checkModelDataTemplateBusiness;


    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CheckModelDataTemplateWrapper> list(Integer page, Integer limit, CheckModelDataTemplate filter) {
        return checkModelDataTemplateBusiness.listSpecial(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CheckModelDataTemplate formValue, MultipartFile templateFile) {
        CommonResponseData retVal = new CommonResponseData();
        ImportCheckTemplateEventParam importCheckTemplateEventParam = new ImportCheckTemplateEventParam(formValue, templateFile);
        ImportCheckTemplateEvent importCheckTemplateEvent = new ImportCheckTemplateEvent(this, importCheckTemplateEventParam);
        OrientContextLoaderListener.Appwac.publishEvent(importCheckTemplateEvent);
        String msg = importCheckTemplateEventParam.getCheckResult();
        if (StringUtil.isEmpty(msg)) {
            retVal.setMsg("保存成功!");
        } else {
            retVal.setMsg(msg);
            retVal.setSuccess(false);
        }
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CheckModelDataTemplate formValue, MultipartFile templateFile) {
        return create(formValue, templateFile.getSize() == 0 ? null : templateFile);
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
        checkModelDataTemplateBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 下载示例模板的excel文件
     *
     * @param response
     * @param modelName 绑定模型名称
     * @param modelId   绑定模型ID
     * @throws Exception
     */
    @RequestMapping("downloadExampleExcel")
    public void downloadExampleExcel(HttpServletResponse response, String modelName, String modelId) throws Exception {
        String fileName = modelName + "模板";
        List<String> columns = checkModelDataTemplateBusiness.getColumns(modelId);
        //操作第一个表
        Excel excel = new Excel();
        excel.cell(0, 0).value("表格名称").addWidth(5000);
        excel.cell(1, 0).value(modelName);
        excel.cell(0, 1).value("签署人").addWidth(5000);
        excel.cell(0, 2).value("备注").addWidth(5000);

        //操作第二个表
        excel.setWorkingSheet(1);
        int len = columns.size();
        for (int i = 0; i < len; i++) {
            excel.cell(0, i).value(columns.get(i)).addWidth(1000);
        }
        ExcelUtil.downloadExcel(excel.getWorkBook(), fileName, response);
    }
}
