package com.orient.background.controller;

import com.orient.background.business.ModelBtnInstanceBusiness;
import com.orient.sysmodel.domain.form.ModelBtnInstanceEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 模型按钮Controller
 *
 * @author enjoy
 * @creare 2016-04-11 14:04
 */
@Controller
@RequestMapping("modelBtnInstance")
public class ModelBtnInstanceController {

    @Autowired
    ModelBtnInstanceBusiness modelBtnInstanceBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<ModelBtnInstanceEntity> list(Integer page, Integer limit, ModelBtnInstanceEntity filter) {
        return modelBtnInstanceBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(ModelBtnInstanceEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        modelBtnInstanceBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(ModelBtnInstanceEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        modelBtnInstanceBusiness.update(formValue);
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
        modelBtnInstanceBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getModelBtnInstanceData")
    @ResponseBody
    public AjaxResponseData<Map<String, ExtGridData<ModelBtnInstanceEntity>>> getModelBtnInstanceData(Long[] filter) {
        return new AjaxResponseData<>(modelBtnInstanceBusiness.getModelBtnInstanceData(filter));
    }
}
