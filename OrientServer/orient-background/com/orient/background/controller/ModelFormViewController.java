package com.orient.background.controller;

/**
 * Created by enjoy on 2016/3/18 0018.
 */

import com.orient.background.bean.ModelFormViewEntityWrapper;
import com.orient.background.business.ModelFormViewBusiness;
import com.orient.background.event.GenTemplateEvent;
import com.orient.background.event.PreviewModelViewEvent;
import com.orient.background.event.SaveModelViewEvent;
import com.orient.background.eventParam.GenTemplateEventParam;
import com.orient.background.eventParam.PreviewModelViewEventParam;
import com.orient.background.eventParam.SaveModelViewEventParam;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.utils.StringUtil;
import com.orient.web.base.*;
import com.orient.web.modelDesc.column.ColumnDesc;
import com.orient.web.springmvcsupport.DateEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/modelFormView")
public class ModelFormViewController {

    @Autowired
    ModelFormViewBusiness modelFormViewBusiness;

    /**
     * 展现数据表格
     *
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<ModelFormViewEntityWrapper> list(Integer page, Integer limit, ModelFormViewEntity filter) {
        return modelFormViewBusiness.listByCustomer(page, limit, filter);
    }

    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(ModelFormViewEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        SaveModelViewEventParam eventParam = new SaveModelViewEventParam();
        eventParam.setModelFormViewEntity(formValue);
        OrientContextLoaderListener.Appwac.publishEvent(new SaveModelViewEvent(this, eventParam));
        retVal.setMsg("新增成功");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(ModelFormViewEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        SaveModelViewEventParam eventParam = new SaveModelViewEventParam();
        eventParam.setModelFormViewEntity(formValue);
        OrientContextLoaderListener.Appwac.publishEvent(new SaveModelViewEvent(this, eventParam));
        retVal.setMsg("修改成功");
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
        modelFormViewBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getModelFormViewCombobox")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getModelFormViewCombobox(Integer startIndex, Integer maxResults, String filter, String id) {
        ExtComboboxResponseData<ExtComboboxData> retValue = modelFormViewBusiness.getModelComboboxCollection(startIndex, maxResults, filter, id);
        return retValue;
    }

    /**
     * 预览模板 可新增数据
     *
     * @param formValue
     * @param reGenTemplate
     * @return
     */
    @RequestMapping("preview")
    @ResponseBody
    public AjaxResponseData<String> preview(ModelFormViewEntity formValue, Integer reGenTemplate, String dataId) {
        AjaxResponseData<String> retVal = new AjaxResponseData();
        PreviewModelViewEventParam eventParam = new PreviewModelViewEventParam();
        eventParam.setFormValue(formValue);
        eventParam.setDataId(dataId);
        eventParam.setReGenTemplate(reGenTemplate);
        OrientContextLoaderListener.Appwac.publishEvent(new PreviewModelViewEvent(this, eventParam));
        retVal.setMsg(StringUtil.isEmpty(eventParam.getOutHtml()) ? "准备数据失败" : "");
        retVal.setResults(eventParam.getOutHtml());
        return retVal;
    }

    @RequestMapping("previewByFormViewId")
    @ResponseBody
    public AjaxResponseData<String> previewByFormViewId(Long formViewId, String dataId) {
        AjaxResponseData<String> retVal = new AjaxResponseData();
        PreviewModelViewEventParam eventParam = new PreviewModelViewEventParam();
        eventParam.setFormValue(modelFormViewBusiness.findById(formViewId));
        eventParam.setDataId(dataId);
        eventParam.setReGenTemplate(0);
        OrientContextLoaderListener.Appwac.publishEvent(new PreviewModelViewEvent(this, eventParam));
        retVal.setMsg(StringUtil.isEmpty(eventParam.getOutHtml()) ? "准备数据失败" : "");
        retVal.setResults(eventParam.getOutHtml());
        return retVal;
    }


    /**
     * 获取模型的字段描述
     *
     * @return
     */
    @RequestMapping("getModelColumn")
    @ResponseBody
    public AjaxResponseData<List<ColumnDesc>> getModelColumn(String orientModelId) {
        List<ColumnDesc> retVal = modelFormViewBusiness.getModelColumn(orientModelId);
        return new AjaxResponseData(retVal);
    }

    /**
     * 根据选定的模型 以及 freemarker模板生成HTML
     *
     * @param bindTemplateId
     * @param bindModelID
     * @return
     */
    @RequestMapping("getFormViewHtml")
    @ResponseBody
    public AjaxResponseData<String> getFormViewHtml(Long bindTemplateId, Long bindModelID) {
        GenTemplateEventParam genTemplateeventParam = new GenTemplateEventParam();
        genTemplateeventParam.setModelId(bindModelID);
        genTemplateeventParam.setTemplateId(bindTemplateId);
        OrientContextLoaderListener.Appwac.publishEvent(new GenTemplateEvent(this, genTemplateeventParam));
        return new AjaxResponseData(genTemplateeventParam.getHtml());
    }

    /**
     * 返回字段的HTML展现
     *
     * @param modelId
     * @param templateId
     * @return
     */
    @RequestMapping("initColumnControls")
    @ResponseBody
    public AjaxResponseData<Map<String, String>> initColumnControls(Long modelId, Long templateId) {
        AjaxResponseData<Map<String, String>> retVal = new AjaxResponseData<>();
        Map<String, String> columnHtmlDesc = modelFormViewBusiness.initColumnControls(modelId, templateId);
        retVal.setResults(columnHtmlDesc);
        return retVal;
    }

    /**
     *
     * @return 清空由于删除模型引起的模型绑定为空的脏数据
     */
    @RequestMapping("doClearData")
    @ResponseBody
    public CommonResponseData doClearData() {
        CommonResponseData retVal = new CommonResponseData();
        modelFormViewBusiness.doClearData();
        retVal.setMsg("清理成功");
        return retVal;
    }

    /**
     * 时间格式处理
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateEditor());
    }
}

