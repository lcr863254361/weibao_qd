package com.orient.sysman.controller;

import com.orient.log.annotion.Action;
import com.orient.sysman.bean.PartOperationsWrapper;
import com.orient.sysman.bussiness.ModelRightsBusiness;
import com.orient.sysmodel.domain.role.PartOperations;
import com.orient.sysmodel.domain.role.PartOperationsDAO;
import com.orient.sysmodel.service.sys.IModelRightsService;
import com.orient.utils.CommonTools;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-04 18:44
 */
@Controller
@RequestMapping("/ModelRights")
public class ModelRightsController {
    @Autowired
    IModelRightsService modelRightsService;

    @Autowired
    ModelRightsBusiness modelRightsBusiness;

    @Action(ownermodel = "系统管理-表访问权限管理", detail = "查看模型权限")
    @RequestMapping("getModelRights")
    @ResponseBody
    public AjaxResponseData<PartOperationsWrapper> getModelRights(String modelId, String roleId) {
        return new AjaxResponseData(modelRightsBusiness.getModelRights(modelId, roleId));
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public AjaxResponseData<Long> create(PartOperationsWrapper formValue) {
        AjaxResponseData<Long> retVal = new AjaxResponseData();
        modelRightsBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        retVal.setResults(formValue.getId());
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(PartOperationsWrapper formValue) {
        CommonResponseData retVal = new CommonResponseData();
        modelRightsBusiness.update(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("createOrUpdate")
    @ResponseBody
    public AjaxResponseData<String> createOrUpdate(PartOperationsWrapper formValue, String roleInfos) {
        AjaxResponseData<String> retVal = new AjaxResponseData();

        String[] ris = new String[0];
        if(roleInfos != null) {
            ris = roleInfos.split(",");
        }

        List<Long> resultIds = new ArrayList<>();
        for(String roleInfo : ris) {
            formValue.setRoleInfo(roleInfo);
            PartOperations partOperations = modelRightsService.getModelRights(formValue.getTableId(), roleInfo);
            if(partOperations == null) {
                formValue.setId(null);
                modelRightsBusiness.save(formValue);
            }
            else {
                formValue.setId(partOperations.getId());
                modelRightsBusiness.update(formValue);
            }
            if(formValue.getId() != null) {
                resultIds.add(formValue.getId());
            }
            else {
                retVal.setMsg("保存失败");
                return retVal;
            }
        }
        retVal.setMsg("保存成功!");
        retVal.setResults(CommonTools.longList2String(resultIds));
        return retVal;
    }
}
