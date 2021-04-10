package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabShareFolderDao;
import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFolder;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabShareFolderService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:07
 * @Version 1.0
 **/
@Service
public class CollabShareFolderService extends BaseService<CollabShareFolder> implements ICollabShareFolderService {

    @Autowired
    ICollabShareFolderDao collabShareFolderDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabShareFolderDao;
    }

}
