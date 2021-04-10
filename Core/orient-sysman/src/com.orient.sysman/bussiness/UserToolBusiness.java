package com.orient.sysman.bussiness;

import com.orient.sysman.bean.UserToolBean;
import com.orient.sysmodel.domain.sys.CwmSysToolsEntity;
import com.orient.sysmodel.domain.tools.CwmUserTool;
import com.orient.sysmodel.service.sys.IToolGroupService;
import com.orient.sysmodel.service.sys.impl.ToolService;
import com.orient.sysmodel.service.tools.IUserToolService;
import com.orient.web.base.BaseHibernateBusiness;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


/**
 * 用户工具管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class UserToolBusiness extends BaseHibernateBusiness<CwmUserTool> {
    @Autowired
    ToolService toolService;

    @Autowired
    IUserToolService userToolService;

    @Autowired
    IToolGroupService toolGroupService;

    @Override
    public IUserToolService getBaseService() {
        return userToolService;
    }

    public List<UserToolBean> getUserToolsByFilter(CwmSysToolsEntity filter, Long userId) {
        List<UserToolBean> retVal = new ArrayList<>();
        String toolNameFilter = "%" + (filter.getToolName()==null?"":filter.getToolName()) + "%";
        String toolTypeFilter = "%" + (filter.getToolType()==null?"":filter.getToolType()) + "%";
        List<CwmSysToolsEntity> sysTools = toolService.list(Restrictions.eq("belongGroup.id", filter.getGroupId()),
                Restrictions.ilike("toolName", toolNameFilter),
                Restrictions.or(Restrictions.isNull("toolType"), Restrictions.ilike("toolType", toolTypeFilter)));

        if(sysTools == null) {
            sysTools = new ArrayList<>();
        }
        for(CwmSysToolsEntity sysTool : sysTools) {
            CwmUserTool userTool = userToolService.get(Restrictions.eq("toolId", sysTool.getId()), Restrictions.eq("userId", userId));
            if(userTool != null) {
                UserToolBean userToolBean = new UserToolBean();
                try {
                    BeanUtils.copyProperties(userToolBean, sysTool);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userToolBean.setId(userTool.getId());
                userToolBean.setToolId(sysTool.getId());
                userToolBean.setUserId(userId);
                userToolBean.setToolPath(userTool.getToolPath());
                retVal.add(userToolBean);
            }
        }
        return retVal;
    }

    public List<CwmSysToolsEntity> getUncreatedUserToolsByGroupId(Long toolGroupId, Long userId) {
        List<CwmSysToolsEntity> retVal = new ArrayList<>();
        List<CwmSysToolsEntity> sysTools = toolService.list(Restrictions.eq("belongGroup.id", toolGroupId));
        if(sysTools == null) {
            sysTools = new ArrayList<>();
        }
        for(CwmSysToolsEntity sysTool : sysTools) {
            CwmUserTool userTool = userToolService.get(Restrictions.eq("toolId", sysTool.getId()), Restrictions.eq("userId", userId));
            if(userTool == null) {
                retVal.add(sysTool);
            }
        }
        return retVal;
    }

    public void createUserTools(ArrayList<Long> toolIds, Long userId) {
        for(Long toolId : toolIds) {
            CwmUserTool userTool = userToolService.get(Restrictions.eq("toolId", toolId), Restrictions.eq("userId", userId));
            if(userTool == null) {
                userTool = new CwmUserTool();
                userTool.setToolId(toolId);
                userTool.setUserId(userId);
                userToolService.save(userTool);
            }
        }
    }

    public void updateUserToolPath(CwmUserTool userTool) {
        CwmUserTool dbUserTool = userToolService.getById(userTool.getId());
        if(dbUserTool != null) {
            dbUserTool.setToolPath(userTool.getToolPath());
            userToolService.save(dbUserTool);
        }
    }


    public String getUserToolPathByName(String userId, String toolName) {
        CwmSysToolsEntity sysTool = toolService.get(Restrictions.eq("toolName", toolName));
        if(sysTool == null) {
            return null;
        }
        CwmUserTool userTool = userToolService.get(Restrictions.eq("userId", Long.valueOf(userId)), Restrictions.eq("toolId", sysTool.getId()));
        if(userTool == null) {
            return null;
        }
        return userTool.getToolPath();
    }
}
