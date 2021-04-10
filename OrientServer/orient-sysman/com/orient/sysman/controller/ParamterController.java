package com.orient.sysman.controller;
import com.orient.log.annotion.Action;
import com.orient.sysman.bussiness.ParamterBusiness;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.orient.sysmodel.domain.sys.Parameter;
/**
 * 参数管理
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/Paramter")
public class ParamterController {
    @Autowired
    ParamterBusiness paramterBusiness;

    @Action(ownermodel = "系统管理-系统参数管理", detail = "查看系统参数信息")
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<Parameter> list(Integer page, Integer limit, Parameter filter) {
        return paramterBusiness.list(page, limit, filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @Action(ownermodel = "系统管理-系统参数管理", detail = "新增系统参数信息")
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(Parameter formValue) {
        CommonResponseData retVal = new CommonResponseData();
        paramterBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @Action(ownermodel = "系统管理-系统参数管理", detail = "修改系统参数信息")
    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(Parameter formValue) {
        CommonResponseData retVal = new CommonResponseData();
        paramterBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @Action(ownermodel = "系统管理-系统参数管理", detail = "删除系统参数信息")
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        paramterBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }
}
