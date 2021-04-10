package com.orient.sysman.bussiness;

import com.orient.sysmodel.domain.sys.CwmPortalEntity;
import com.orient.sysmodel.service.role.IRolePortalService;
import com.orient.sysmodel.service.sys.IPortalService;
import com.orient.sysmodel.service.sys.IUserPortalService;
import com.orient.web.base.BaseHibernateBusiness;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Portal Business
 *
 * @author enjoy
 * @createTime 2016-06-02 10:54
 */
@Component
public class PortalBusiness extends BaseHibernateBusiness<CwmPortalEntity> {

    @Autowired
    IPortalService portalService;

    @Autowired
    IUserPortalService userPortalService;

    @Autowired
    IRolePortalService rolePortalService;

    @Override
    public IPortalService getBaseService() {
        return portalService;
    }

    @Override
    public void delete(Long[] toDelIds) {
        //删除关联表数据
        userPortalService.list(Restrictions.in("portalId", toDelIds)).forEach(userPortal -> userPortalService.delete(userPortal));
        rolePortalService.list(Restrictions.in("portalId", toDelIds)).forEach(rolePortal->rolePortalService.delete(rolePortal));
        super.delete(toDelIds);
    }
}
