package com.orient.collabdev.business.designing;

import com.orient.collabdev.constant.ResultSettingBindType;
import com.orient.collabdev.model.ResultBind;
import com.orient.sysmodel.domain.collabdev.data.CollabResultBind;
import com.orient.sysmodel.domain.component.CwmComponentEntity;
import com.orient.sysmodel.domain.component.CwmComponentModelEntity;
import com.orient.sysmodel.service.collabdev.ICollabResultBindSercive;
import com.orient.sysmodel.service.component.IComponentBindService;
import com.orient.sysmodel.service.component.IComponentService;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author GNY
 * @Date 2018/8/9 10:06
 * @Version 1.0
 **/
@Service
public class ResultSettingsBusiness extends BaseBusiness {

    @Autowired
    IComponentBindService componentBindService;

    @Autowired
    IComponentService componentService;

    @Autowired
    ICollabResultBindSercive resultBindSercive;

    public CommonResponseData modifyNodeBind(String nodeId, String devType, boolean checked) {
        CommonResponseData retVal = new CommonResponseData();
        List<CollabResultBind> bindList = resultBindSercive.list(Restrictions.eq("nodeId", nodeId));
        if (bindList.size() == 0) {
            CollabResultBind collabResultBind = new CollabResultBind();
            collabResultBind.setNodeId(nodeId);
            switch (ResultSettingBindType.fromString(devType)) {
                case DEV:
                    collabResultBind.setHasDevData(1);
                    collabResultBind.setHasModelData(0);
                    collabResultBind.setHasPVMData(0);
                    break;
                case COMPONENT:
                    collabResultBind.setHasDevData(0);
                    collabResultBind.setHasModelData(1);
                    collabResultBind.setHasPVMData(0);
                    break;
                case PVM:
                    collabResultBind.setHasDevData(0);
                    collabResultBind.setHasModelData(0);
                    collabResultBind.setHasPVMData(1);
                    break;
                default:
                    break;
            }
            resultBindSercive.save(collabResultBind);
        } else {
            CollabResultBind collabResultBind = bindList.get(0);
            switch (ResultSettingBindType.fromString(devType)) {
                case DEV:
                    collabResultBind.setHasDevData(checked ? 1 : 0);
                    break;
                case COMPONENT:
                    collabResultBind.setHasModelData(checked ? 1 : 0);
                    break;
                case PVM:
                    collabResultBind.setHasPVMData(checked ? 1 : 0);
                    break;
                default:
                    break;
            }
            resultBindSercive.update(collabResultBind);
        }
        retVal.setSuccess(true);
        return retVal;
    }

    public AjaxResponseData<ResultBind> getTabs(String nodeId) {
        AjaxResponseData<ResultBind> retVal = new AjaxResponseData<>();
        ResultBind resultBind = new ResultBind();
        resultBind.setNodeId(nodeId);
        List<CollabResultBind> bindList = resultBindSercive.list(Restrictions.eq("nodeId", nodeId));
        if (bindList.size() == 0) {
            resultBind.setHasDevData(false);
            resultBind.setHasModelData(false);
            resultBind.setHasPVMData(false);
        } else {
            CollabResultBind collabResultBind = bindList.get(0);
            resultBind.setHasDevData(collabResultBind.getHasDevData() == 1);
            resultBind.setHasModelData(collabResultBind.getHasModelData() == 1);
            resultBind.setHasPVMData(collabResultBind.getHasPVMData() == 1);
        }
        retVal.setResults(resultBind);
        return retVal;
    }

    public AjaxResponseData getCompomentIdByNodeId(String nodeId) {
        AjaxResponseData retVal = new AjaxResponseData();
        retVal.setAlertMsg(false);
        List<CwmComponentModelEntity> cwmComponentModelEntityList = componentBindService.list(Restrictions.eq("nodeId", nodeId));
        if (cwmComponentModelEntityList.size() == 0) {
            retVal.setResults("");
        } else {
            retVal.setResults(cwmComponentModelEntityList.get(0).getBelongComponent().getId() + "");
        }
        return retVal;
    }

    public CommonResponseData modifyNodeComponentBind(String nodeId, long componentId) {
        CommonResponseData retVal = new CommonResponseData();
        CwmComponentEntity cwmComponentEntity = componentService.list(Restrictions.eq("id", componentId)).get(0);
        List<CwmComponentModelEntity> cwmComponentModelEntityList = componentBindService.list(Restrictions.eq("nodeId", nodeId));
        if (cwmComponentModelEntityList.size() == 0) {
            CwmComponentModelEntity cwmComponentModelEntity = new CwmComponentModelEntity();
            cwmComponentModelEntity.setNodeId(nodeId);
            cwmComponentModelEntity.setBelongComponent(cwmComponentEntity);
            componentBindService.save(cwmComponentModelEntity);
        } else {
            CwmComponentModelEntity cwmComponentModelEntity = cwmComponentModelEntityList.get(0);
            cwmComponentModelEntity.setBelongComponent(cwmComponentEntity);
            componentBindService.update(cwmComponentModelEntity);
        }
        return retVal;
    }

}
