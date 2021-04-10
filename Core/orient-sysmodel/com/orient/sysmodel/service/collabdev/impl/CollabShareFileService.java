package com.orient.sysmodel.service.collabdev.impl;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.collabdev.ICollabShareFileDao;
import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFile;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.collabdev.ICollabShareFileService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Description
 * @Author GNY
 * @Date 2018/7/27 20:07
 * @Version 1.0
 **/
@Service
public class CollabShareFileService extends BaseService<CollabShareFile> implements ICollabShareFileService {

    @Autowired
    ICollabShareFileDao collabShareFileDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.collabShareFileDao;
    }

}
