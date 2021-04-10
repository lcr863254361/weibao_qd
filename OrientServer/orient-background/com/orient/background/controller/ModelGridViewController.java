package com.orient.background.controller;

import com.orient.background.bean.ModelAndTemplateVO;
import com.orient.background.bean.ModelGridViewEntityWrapper;
import com.orient.background.business.ModelGridViewBusiness;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.utils.FileOperator;
import com.orient.utils.StringUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 模型表格模板控制层
 *
 * @author enjoy
 * @creare 2016-04-06 9:54
 */
@Controller
@RequestMapping("/modelGridView")
public class ModelGridViewController {

    @Autowired
    ModelGridViewBusiness modelGridViewBusiness;

    /**
     * 展现数据表格
     *
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<ModelGridViewEntityWrapper> list(Integer page, Integer limit, ModelGridViewEntity filter) {
        return modelGridViewBusiness.listByCustomer(page, limit, filter);
    }

    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(ModelGridViewEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        modelGridViewBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(ModelGridViewEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        modelGridViewBusiness.update(formValue);
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
        modelGridViewBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * @return 清空由于删除模型引起的模型绑定为空的脏数据
     */
    @RequestMapping("doClearData")
    @ResponseBody
    public CommonResponseData doClearData() {
        CommonResponseData retVal = new CommonResponseData();
        modelGridViewBusiness.doClearData();
        retVal.setMsg("清理成功");
        return retVal;
    }


    @RequestMapping("export")
    public void doExport(String ids, HttpServletRequest request, HttpServletResponse response) {
        File toDownLoadFile = modelGridViewBusiness.doExport(ids);
        FileOperator.downLoadFile(request, response, toDownLoadFile.getAbsolutePath(), toDownLoadFile.getName());
    }

    @RequestMapping("import")
    @ResponseBody
    public CommonResponseData doImport(MultipartFile dataFile) {
        String errorMsg = modelGridViewBusiness.doImport(dataFile);
        return new CommonResponseData(StringUtil.isEmpty(errorMsg), errorMsg);
    }

    @RequestMapping("getModelIdAndTempIdByTemplateName")
    @ResponseBody
    public AjaxResponseData<ModelAndTemplateVO> getModelIdAndTempIdByTemplateName(String templateName) {
        if (StringUtil.isEmpty(templateName)) {
            throw new OrientBaseAjaxException("", "模板名称不可为空");
        }
        ModelAndTemplateVO modelAndTemplateVO = modelGridViewBusiness.getModelIdAndTempIdByTemplateName(templateName);
        return new AjaxResponseData<>(modelAndTemplateVO);
    }

}

