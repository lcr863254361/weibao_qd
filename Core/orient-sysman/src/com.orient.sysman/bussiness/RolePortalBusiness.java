package com.orient.sysman.bussiness;

import com.orient.sysmodel.domain.role.CwmSysRolePortalEntity;
import com.orient.sysmodel.service.role.IRolePortalService;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class RolePortalBusiness extends BaseHibernateBusiness<CwmSysRolePortalEntity> {

    @Autowired
    IRolePortalService rolePortalService;

    @Override
    public IRolePortalService getBaseService() {
        return rolePortalService;
    }
}
