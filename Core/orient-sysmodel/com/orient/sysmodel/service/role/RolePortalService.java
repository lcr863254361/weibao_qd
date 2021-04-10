package com.orient.sysmodel.service.role;

import com.orient.sysmodel.dao.IBaseDao;
import com.orient.sysmodel.dao.sys.IRolePortalDao;
import com.orient.sysmodel.domain.role.CwmSysRolePortalEntity;
import com.orient.sysmodel.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Service
public class RolePortalService extends BaseService<CwmSysRolePortalEntity> implements IRolePortalService {

    @Autowired
    IRolePortalDao rolePortalDao;

    @Override
    public IBaseDao getBaseDao() {
        return this.rolePortalDao;
    }
}
