package com.orient.sysman.bussiness;

import com.orient.sysman.bean.FileGroupItemWrapper;
import com.orient.sysmodel.domain.file.CwmFileGroupItemEntity;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.file.IFileGroupItemService;
import com.orient.sysmodel.service.file.IFileGroupService;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件分组项业务处理层
 *
 * @author enjoy
 * @creare 2016-05-03 9:37
 */
@Component
public class FileGroupItemBusiness extends BaseHibernateBusiness<CwmFileGroupItemEntity> {

    @Autowired
    IFileGroupItemService fileGroupItemService;

    @Autowired
    IFileGroupService fileGroupService;

    @Override
    public IFileGroupItemService getBaseService() {
        return fileGroupItemService;
    }

    public ExtGridData<FileGroupItemWrapper> list(Long belongFileGroupId, Integer page, Integer limit, CwmFileGroupItemEntity filter) {
        Criterion relationCriterion = Restrictions.eq("belongFileGroup.id", belongFileGroupId);
        ExtGridData<FileGroupItemWrapper> retVal = new ExtGridData<>();
        PageBean pageBean = new PageBean();
        pageBean.setRows(limit);
        pageBean.setPage(page);
        pageBean.setExampleFilter(filter);
        pageBean.addCriterion(relationCriterion);
        List<CwmFileGroupItemEntity> queryResult = fileGroupItemService.listByPage(pageBean);
        //转化为包装类
        List<FileGroupItemWrapper> data = new ArrayList<>();
        queryResult.forEach(fileGroupItemEntity -> {
            FileGroupItemWrapper fileGroupItemWrapper = new FileGroupItemWrapper(fileGroupItemEntity);
            data.add(fileGroupItemWrapper);
        });
        retVal.setResults(data);
        retVal.setTotalProperty(pageBean.getTotalCount());
        return retVal;
    }

    public void save(FileGroupItemWrapper formValue) {
        CwmFileGroupItemEntity fileGroupItemEntity = new CwmFileGroupItemEntity();
        fileGroupItemEntity.setName(formValue.getName());
        fileGroupItemEntity.setSuffix(formValue.getSuffix());
        fileGroupItemEntity.setBelongFileGroup(fileGroupService.getById(formValue.getBelongFileGroupId()));
        fileGroupItemService.save(fileGroupItemEntity);
    }

    public void update(FileGroupItemWrapper formValue) {
        CwmFileGroupItemEntity fileGroupItemEntity = fileGroupItemService.getById(formValue.getId());
        fileGroupItemEntity.setName(formValue.getName());
        fileGroupItemEntity.setSuffix(formValue.getSuffix());
        fileGroupItemService.update(fileGroupItemEntity);
    }
}
