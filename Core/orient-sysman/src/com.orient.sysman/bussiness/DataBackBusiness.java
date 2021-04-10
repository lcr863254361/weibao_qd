package com.orient.sysman.bussiness;

import com.orient.sysman.util.RestoreCommandUtil;
import com.orient.sysmodel.domain.sys.CwmBackEntity;
import com.orient.sysmodel.domain.sys.QuartzJobEntity;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.sys.IDataBackService;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 系统数据备份恢复
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class DataBackBusiness extends BaseHibernateBusiness<CwmBackEntity> {

    @Autowired
    IDataBackService dataBackService;

    @Override
    public IDataBackService getBaseService() {
        return dataBackService;
    }

    public void save(CwmBackEntity formValue) {
        //注入默认属性
        //备份人
        String userName = null;
        try {
            userName = UserContextUtil.getUserAllName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long type = null == userName ? 1l : 0l;
        userName = userName == null ? "系统" : userName;
        formValue.setUserId(userName);
        //备份类型
        formValue.setType(type);
        //备份时间
        formValue.setBackDate(new Date());
        //保存数据
        dataBackService.save(formValue);
    }

    /**
     * 恢复数据库
     *
     * @param backUpId
     */
    public void doRecovery(Long backUpId) {
        //获取备份信息
        CwmBackEntity backEntity = dataBackService.getById(backUpId);
        //获取备份目录
        String backDir = backEntity.getFilePath();
        //恢复数据
        String restoreBatPath = backDir + File.separator + "restore.bat";
        RestoreCommandUtil rc = new RestoreCommandUtil(restoreBatPath);
        rc.execRestore();
        //刷新容器
    }


    public ExtGridData<CwmBackEntity> list(Integer page, Integer limit, CwmBackEntity filter) {
        ExtGridData<CwmBackEntity> retVal = new ExtGridData<>();
        PageBean pageBean = new PageBean();
        pageBean.setRows(null == limit ? Integer.MAX_VALUE : limit);
        pageBean.setPage(null == page ? -1 : page);
        pageBean.setExampleFilter(filter);
        pageBean.addOrder(Order.desc("backDate"));
        List<CwmBackEntity> queryResult = getBaseService().listByPage(pageBean);
        retVal.setTotalProperty(pageBean.getTotalCount());
        retVal.setResults(queryResult);
        return retVal;
    }
}
