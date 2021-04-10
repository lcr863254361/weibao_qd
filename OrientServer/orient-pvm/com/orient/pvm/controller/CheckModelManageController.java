package com.orient.pvm.controller;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.pvm.business.CheckModelManageBusiness;
import com.orient.sysmodel.domain.pvm.CwmSysCheckModelColumn;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.base.BaseController;
import com.orient.web.model.BaseNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.orient.sysmodel.domain.pvm.CwmSysCheckmodelsetEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/CheckModelManage")
public class CheckModelManageController  extends BaseController{
    @Autowired
    CheckModelManageBusiness checkModelManageBusiness;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CwmSysCheckmodelsetEntity> list(Integer page, Integer limit, CwmSysCheckmodelsetEntity filter) {
        return checkModelManageBusiness.list(page,limit,filter);
    }

    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CwmSysCheckmodelsetEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        checkModelManageBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(CwmSysCheckmodelsetEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        checkModelManageBusiness.update(formValue);
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
        checkModelManageBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * @param node
     * @return 采用树形结构展现所有schema下的所有含有检查字段的模型的信息
     */
    @RequestMapping("getModelHasCheckTree")
    @ResponseBody
    public AjaxResponseData<List<BaseNode>> getModelHasCheckTree(String node) {
        return new AjaxResponseData(checkModelManageBusiness.getModelHasCheckNodes(node));
    }

    /**
     *
     * @param modelId
     * @return  模型下所有检查字段的信息
     */
    @RequestMapping("CheckModelColumns")
    @ResponseBody
    public ExtGridData<CwmSysCheckModelColumn> initModelCheckColumns(String modelId,String schemaId) {
        return checkModelManageBusiness.getModelCheckColumns(modelId, schemaId);
    }

    @RequestMapping("updateCheckModelColumns")
    @ResponseBody
    public CommonResponseData updateCheckModelColumns(@RequestBody CwmSysCheckModelColumn formValue) {
        CommonResponseData retVal = new CommonResponseData();
        CwmSysCheckmodelsetEntity entity = new CwmSysCheckmodelsetEntity();
        entity.setModelId(formValue.getModelId());
        entity.setColumnId(formValue.getColumnId());
        entity.setId(formValue.getId());
        if(formValue.getIsRequiredName()==null) {
            entity.setIsRequired(new BigDecimal(1));//非必填
        } else if("必填".equals(formValue.getIsRequiredName())) {
            entity.setIsRequired(new BigDecimal(0));//必填
        } else if("非必填".equals(formValue.getIsRequiredName())) {
            entity.setIsRequired(new BigDecimal(1));//非必填
        }

        if(formValue.getIsBindPhotoName() == null) {
            entity.setIsBindPhoto(new BigDecimal(1));//不绑定
        } else if("绑定".equals(formValue.getIsBindPhotoName())) {
            entity.setIsBindPhoto(new BigDecimal(0));//绑定
        } else if("不绑定".equals(formValue.getIsRequiredName())) {
            entity.setIsBindPhoto(new BigDecimal(1));//不绑定
        }


        if("任意格式".equals(formValue.getCheckTypeName())) {
            entity.setCheckType(new BigDecimal(0));
        }else if("字符串".equals(formValue.getCheckTypeName())) {
            entity.setCheckType(new BigDecimal(1));
        }else if("勾选".equals(formValue.getCheckTypeName())) {
            entity.setCheckType(new BigDecimal(2));
        }else  if("字符串加勾选".equals(formValue.getCheckTypeName())) {
            entity.setCheckType(new BigDecimal(3));
        }
        checkModelManageBusiness.update(entity);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }
}
