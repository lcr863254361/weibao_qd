package com.orient.background.controller;

import com.orient.background.business.ModelBtnTypeBusiness;
import com.orient.sysmodel.domain.form.ModelBtnTypeEntity;
import com.orient.web.base.ExtComboboxData;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 模型表格按钮控制层
 *
 * @author enjoy
 * @creare 2016-04-09 10:46
 */
@Controller
@RequestMapping("/modelBtnType")
public class ModelBtnTypeController {

    @Autowired
    ModelBtnTypeBusiness modelBtnTypeBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<ModelBtnTypeEntity> list(Integer page, Integer limit,ModelBtnTypeEntity filter) {
        return modelBtnTypeBusiness.list(page, limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(ModelBtnTypeEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        modelBtnTypeBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(ModelBtnTypeEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        modelBtnTypeBusiness.update(formValue);
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
        modelBtnTypeBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getBtnTypeCombobox")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getBtnTypeCombobox(Integer startIndex, Integer maxResults, String filter, String id) {
        ExtComboboxResponseData<ExtComboboxData> retValue = modelBtnTypeBusiness.getModelComboboxCollection(startIndex, maxResults, filter, id);
        return retValue;
    }


}
