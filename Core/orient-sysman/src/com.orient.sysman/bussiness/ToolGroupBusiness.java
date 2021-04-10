package com.orient.sysman.bussiness;

import com.orient.sysman.bean.CwmSysToolsGroupEntityWrapper;
import com.orient.sysmodel.domain.sys.CwmSysToolsGroupEntity;
import com.orient.sysmodel.service.sys.IToolGroupService;
import com.orient.utils.BeanUtils;
import com.orient.web.base.BaseHibernateBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具分组管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class ToolGroupBusiness extends BaseHibernateBusiness<CwmSysToolsGroupEntity> {

    @Autowired
    IToolGroupService toolGroupService;

    @Override
    public IToolGroupService getBaseService() {
        return toolGroupService;
    }

    public List<CwmSysToolsGroupEntityWrapper> list(Integer page, Integer limit, CwmSysToolsGroupEntity filter, String node) {
        if ("root".equals(node)) {
            List<CwmSysToolsGroupEntity> cwmSysToolsGroupEntities = toolGroupService.listByPage(filter, page, limit);
            List<CwmSysToolsGroupEntityWrapper> retVal = new ArrayList<>();
            cwmSysToolsGroupEntities.forEach(cwmSysToolsGroupEntity -> {
                CwmSysToolsGroupEntityWrapper cwmSysToolsGroupEntityWrapper = new CwmSysToolsGroupEntityWrapper();
                BeanUtils.copyProperties(cwmSysToolsGroupEntityWrapper, cwmSysToolsGroupEntity);
                retVal.add(cwmSysToolsGroupEntityWrapper);
            });
            return retVal;
        } else
            return new ArrayList<>();

    }

    @Override
    public void delete(Long[] toDelIds) {
        for (Long toDelId : toDelIds) {
            //級聯刪除
            toolGroupService.delete(toDelId);
        }
    }
}
