package com.orient.sysman.bussiness;

import com.orient.sysman.bean.FileGroupWrapper;
import com.orient.sysmodel.domain.file.CwmFileGroupEntity;
import com.orient.sysmodel.service.file.IFileGroupService;
import com.orient.utils.BeanUtils;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.CommonResponseData;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件分组业务处理类
 *
 * @author enjoy
 * @creare 2016-04-30 9:30
 */
@Component
public class FileGroupBusiness extends BaseHibernateBusiness<CwmFileGroupEntity> {

    @Autowired
    IFileGroupService fileGroupService;

    @Override
    public IFileGroupService getBaseService() {
        return fileGroupService;
    }

    public List<FileGroupWrapper> listByPiId(String node, Integer isCustomer) {
        List<FileGroupWrapper> retVal = new ArrayList<>();
        if ("root".equals(node)) {
            List<CwmFileGroupEntity> queryList;
            if (null == isCustomer) {
                queryList = fileGroupService.list(Order.desc("id"));
            } else {
                queryList = fileGroupService.list(Restrictions.ge("id", 0l), Order.desc("id"));
            }

            queryList.forEach(cwmFileGroupEntity -> {
                FileGroupWrapper tmpBean = new FileGroupWrapper(cwmFileGroupEntity);
                retVal.add(tmpBean);
            });
        }
        return retVal;
    }

    public CommonResponseData create(List<FileGroupWrapper> fileGroupWrappers) {
        CommonResponseData retVal = new CommonResponseData();
        //唯一性校验
        fileGroupWrappers.forEach(fileGroupWrapper -> {
            String groupName = fileGroupWrapper.getGroupName();
            if (fileGroupService.count(Restrictions.eq("groupName", groupName)) == 0) {
                CwmFileGroupEntity cwmFileGroupEntity = new CwmFileGroupEntity();
                BeanUtils.copyProperties(cwmFileGroupEntity, fileGroupWrapper);
                fileGroupService.save(cwmFileGroupEntity);
            } else {
                retVal.setSuccess(false);
                retVal.setMsg("数据库中已经存在此分组");
            }
        });
        return retVal;
    }

    public CommonResponseData delete(List<FileGroupWrapper> toDeleteData) {
        CommonResponseData retVal = new CommonResponseData();
        toDeleteData.forEach(fileGroupWrapper -> {
            fileGroupService.delete(fileGroupWrapper.getId());
        });
        return retVal;
    }

    public CommonResponseData update(List<FileGroupWrapper> toUpdateData) {
        CommonResponseData retVal = new CommonResponseData();
        toUpdateData.forEach(fileGroupWrapper -> {
            String groupName = fileGroupWrapper.getGroupName();
            if (fileGroupService.count(Restrictions.eq("groupName", groupName)) == 0) {
                CwmFileGroupEntity cwmFileGroupEntity = new CwmFileGroupEntity();
                BeanUtils.copyProperties(cwmFileGroupEntity, fileGroupWrapper);
                fileGroupService.update(cwmFileGroupEntity);
            } else {
                retVal.setSuccess(false);
                retVal.setMsg("数据库中已经存在此分组");
            }
        });
        return retVal;
    }
}
