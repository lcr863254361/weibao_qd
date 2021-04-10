package com.orient.background.controller;

import com.orient.background.bean.CwmComponentModelEntityWrapper;
import com.orient.background.business.ComponentBindBusiness;
import com.orient.sysmodel.domain.component.CwmComponentModelEntity;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 组件绑定管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/ComponentBind")
public class ComponentBindController  extends BaseController{
    @Autowired
    ComponentBindBusiness componentBindBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmComponentModelEntityWrapper> list(Integer page, Integer limit, CwmComponentModelEntity filter) {
        return componentBindBusiness.spcialList(page,limit,filter);
    }


    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmComponentModelEntity formValue, Long componentId) {
        CommonResponseData retVal = new CommonResponseData();
        componentBindBusiness.save(formValue,componentId);
//        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmComponentModelEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        componentBindBusiness.update(formValue);
//        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
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
        componentBindBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
