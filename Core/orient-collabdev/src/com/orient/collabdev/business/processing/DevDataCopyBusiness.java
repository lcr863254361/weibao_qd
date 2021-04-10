package com.orient.collabdev.business.processing;

import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.business.version.DefaultCollabVersionMng;
import com.orient.devdataobj.business.HisDataObjectBusiness;
import com.orient.devdataobj.util.DataObjVersionUtil;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import com.orient.sysmodel.domain.taskdata.DataObjectOldVersionEntity;
import com.orient.sysmodel.service.taskdata.IDataObjectOldVersionService;
import com.orient.sysmodel.service.taskdata.IDataObjectService;
import com.orient.utils.BeanUtils;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.orient.collabdev.constant.CollabDevConstants.*;

/**
 * @Description 主要用于节点版本升级时，研发数据需要进行copy的一些操作
 * @Author GNY
 * @Date 2018/8/15 10:54
 * @Version 1.0
 **/
@Service
public class DevDataCopyBusiness extends StructureBusiness {

    @Autowired
    DefaultCollabVersionMng defaultCollabVersionMng;

    @Autowired
    IDataObjectService dataObjectService;

    @Autowired
    IDataObjectOldVersionService dataObjectOldVersionService;

    @Autowired
    HisDataObjectBusiness hisDataObjectBusiness;

    public void copyDevData(String nodeId, Integer oldNodeVersion, Integer newNodeVersion, String nodeType) {

        //查询共有的一级研发数据
        List<DataObjectEntity> dataObjectEntityList = dataObjectService.list(Restrictions.eq("nodeId", nodeId), Restrictions.eq("nodeVersion", oldNodeVersion),Restrictions.eq("parentdataobjectid",0),Restrictions.eq("isglobal",1));
        if (dataObjectEntityList.size() > 0) {
            Date modifyDate = new Date();
            dataObjectEntityList.forEach(dataObjectEntity -> {
                DataObjectEntity parentDataObjectEntity = new DataObjectEntity();   //复制一级研发数据
                BeanUtils.copyProperties(parentDataObjectEntity, dataObjectEntity);
                parentDataObjectEntity.setId(null);
                parentDataObjectEntity.setNodeVersion(newNodeVersion);              //设置新的节点版本号
                parentDataObjectEntity.setModifytime(modifyDate);
                parentDataObjectEntity.setModifierid(UserContextUtil.getCurrentUser().getId());
                resetVersion(parentDataObjectEntity, nodeType);
                dataObjectService.save(parentDataObjectEntity);
                Integer parentDataObjectEntityId = parentDataObjectEntity.getId();
                List<DataObjectEntity> familyList = new ArrayList<>();               //复制二级研发数据
                dataObjectService.getChildDataObjEntity(dataObjectEntity, familyList);
                if (familyList.size() > 0) {
                    familyList.forEach(each -> {
                        DataObjectEntity dest = new DataObjectEntity();
                        BeanUtils.copyProperties(dest, each);
                        if (dest.getParentdataobjectid() != 0) {
                            dest.setParentdataobjectid(parentDataObjectEntityId);
                        }
                        dest.setId(null);
                        dest.setNodeVersion(newNodeVersion);
                        parentDataObjectEntity.setModifytime(modifyDate);
                        parentDataObjectEntity.setModifierid(UserContextUtil.getCurrentUser().getId());
                        resetVersion(dest, nodeType);
                        dataObjectService.save(dest);
                    });
                }
            });
        }
    }

    private void resetVersion(DataObjectEntity parentDataObjectEntity, String nodeType) {
        switch (nodeType) {
            case NODE_TYPE_PRJ:
                parentDataObjectEntity.setVersion(DataObjVersionUtil.projectSubmitUpVersion(parentDataObjectEntity.getVersion()));
                break;
            case NODE_TYPE_PLAN:
                parentDataObjectEntity.setVersion(DataObjVersionUtil.jobSubmitUpVersion(parentDataObjectEntity.getVersion()));
                break;
            case NODE_TYPE_TASK:
                parentDataObjectEntity.setVersion(DataObjVersionUtil.dataTaskSubmit(parentDataObjectEntity.getVersion()));
                break;
            default:
                break;
        }
    }

}
