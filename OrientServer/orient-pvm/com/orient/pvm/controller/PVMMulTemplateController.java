package com.orient.pvm.controller;
import com.orient.pvm.business.PVMMulTemplateBusiness;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.base.BaseController;
import com.orient.web.base.OrientEventBus.CommonResponseDataExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.orient.sysmodel.domain.pvm.CwmTaskmultiplecheckmodelEntity;

import java.util.Map;

/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/PVMMulTemplate")
public class PVMMulTemplateController  extends BaseController{
    @Autowired
    PVMMulTemplateBusiness pVMMulTemplateBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmTaskmultiplecheckmodelEntity> list(Integer page, Integer limit, CwmTaskmultiplecheckmodelEntity filter) {
        return pVMMulTemplateBusiness.list(page,limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseDataExt create(CwmTaskmultiplecheckmodelEntity formValue) {
        CommonResponseDataExt retVal = new CommonResponseDataExt();
        pVMMulTemplateBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        retVal.setData(formValue.getId());
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmTaskmultiplecheckmodelEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        pVMMulTemplateBusiness.update(formValue);
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
        pVMMulTemplateBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }


}
