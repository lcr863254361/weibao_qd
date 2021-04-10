package com.orient.sysman.bussiness;

import com.orient.edm.init.FileServerConfig;
import com.orient.sysman.bean.SysLogWrapper;
import com.orient.sysman.scheduler.BackUpLogJob;
import com.orient.sysman.scheduler.BackUpScheduler;
import com.orient.sysmodel.domain.sys.QuartzJobEntity;
import com.orient.sysmodel.domain.sys.SysLog;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.sys.IBackUpJobService;
import com.orient.sysmodel.service.sys.ISysLogService;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * 日志管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class SysLogBusiness extends BaseHibernateBusiness<SysLog> {

    @Autowired
    ISysLogService sysLogService;

    @Autowired
    private FileServerConfig fileServerConfig;

    @Autowired
    IBackUpJobService backUpJobService;

    @Autowired
    BackUpScheduler backUpScheduler;

    @Override
    public ISysLogService getBaseService() {
        return sysLogService;
    }

    @Override
    public ExtGridData<SysLog> list(Integer page, Integer limit, SysLog filter, Criterion... criterions) {
        ExtGridData<SysLog> retVal = new ExtGridData<>();
        Criterion dateCriterion = getDateCriterion((SysLogWrapper) filter);
        Criterion criterion = getDefaultCriterion();
        PageBean pageBean = new PageBean();
        pageBean.addCriterion(dateCriterion);
        pageBean.addCriterion(criterion);
        pageBean.setRows(limit);
        pageBean.setPage(page);
        pageBean.setExampleFilter(filter);
        List<SysLog> queryResult = sysLogService.listByPage(pageBean);
        retVal.setTotalProperty(pageBean.getTotalCount());
        retVal.setResults(queryResult);
        return retVal;
    }

    /**
     * @return 三元日志默認過濾條件
     */
    public Criterion getDefaultCriterion() {

        List<String> managerCounts = new ArrayList<String>() {{
            add("system");
            add("right");
            add("check");
        }};
        Criterion criterion = Restrictions.in("opUserId", managerCounts);
        //三元日志
        String userName = UserContextUtil.getUserName();
        return "right".equals(userName.toLowerCase()) ? Restrictions.not(criterion) : "check".equals(userName.toLowerCase()) ? criterion : null;
    }

    /**
     * @param filter
     * @return 获取时间过滤条件
     */
    private Criterion getDateCriterion(SysLogWrapper filter) {
        Calendar calendar = Calendar.getInstance();
        if (null == filter.getOpDate_s()) {
            calendar.set(1970, 2, 1, 1, 1, 1);
            filter.setOpDate_s(calendar.getTime());
        }
        if (null == filter.getOpDate_e()) {
            calendar.set(2070, 2, 1, 1, 1, 1);
            filter.setOpDate_e(calendar.getTime());
        }
        return Restrictions.between("opDate", filter.getOpDate_s(), filter.getOpDate_e());
    }

    /**
     * 备份日志 创建txt文件
     * @param ids 为null时备份所有日志
     * @param request
     * @param response
     */
    public void createBackUpTxtFile(Long[] ids, HttpServletRequest request, HttpServletResponse response) {
        if(null == ids) {
            //备份所有日志
            String filePath = "";
            int pageNum = 100;
            int count = sysLogService.count();
            if(count > pageNum) {
                int pageSize = count % pageNum == 0 ? count / pageNum : count / pageNum + 1;
                String timeStap = CommonTools.FormatDate(new Date(), "yyyyMMddHHmmssS");
                PageBean pageBean = new PageBean();
                pageBean.setTotalCount(count);
                pageBean.setPageCount(pageSize);
                pageBean.setRows(pageNum);
                for(int i = 0; i < pageSize; i++) {
                    // 起始页
                    int start = i * pageNum;
                    int end = (i + 1) * pageNum;
                    if (end > count) end = count;
                    pageBean.setPage(i+1);
                    List<SysLog> logs = sysLogService.listByPage(pageBean);
                    filePath = exportTxt(logs,true,i,pageSize,timeStap);
                }
            }

            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            FileOperator.downLoadFile(request, response, filePath, fileName);
        } else {
            //根据选中的id备份日志
            List<SysLog> logs = sysLogService.getByIds(ids);
            String filePath = exportTxt(logs, false, -1, -1,"");
            String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
            FileOperator.downLoadFile(request,response,filePath,fileName);
        }
    }

    public String exportTxt(List<SysLog> logs,boolean isZip,int page,int pageSize,String timeStamp) {
        boolean isExportExcel = false;//创建txt文件
        // 准备文件
        List<Map> dataList = prepareFileInfo(logs);

        String timeStap = CommonTools.FormatDate(new Date(), "yyyyMMddHHmmssS");
        String fileName = "系统日志";
        String filePath = "";

//        int pageNum = 100;
//        int count = dataList.size();
//        if(count > pageNum) {
        if(isZip) {
            // 分页
//            int pageSize = count % pageNum == 0 ? count / pageNum : count / pageNum + 1;
//            for(int i = 0; i < pageSize; i++) {
//
//                // 起始页
//                int start = i * pageNum;
//                int end = (i + 1) * pageNum;
//                if(end > count) end = count;
                // 数据
                //List<Map> subList = dataList.subList(start, end);
                FileExportService fileExportService = new FileExportService(isExportExcel, dataList,timeStap, fileName + "_" + (page + 1),fileServerConfig,true);
                fileExportService.exportFile();

                if(page == pageSize - 1) {
                    try {
                        // 待压缩的文件夹
                        String fileDir = fileExportService.getRootPath();
                        fileName = fileName + ".zip";
                        filePath = new File(fileDir).getParent() + "/" + fileName + "_" + timeStap + ".zip";
                        // 创建压缩包
                        FileOperator.zip(fileDir, filePath, "");
                        FileOperator.delFoldsWithChilds(fileDir);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        } else {
            FileExportService fileExportService = new FileExportService(isExportExcel, dataList, timeStap, fileName + "_" + timeStap,fileServerConfig,false);
            if(fileExportService.exportFile()) {
                filePath = fileExportService.getRetFileName();
                fileName = fileName + filePath.substring(filePath.lastIndexOf("."));
            }
        }
        return filePath;
    }

    /**
     * 准备创建文件所用的信息
     * @param logs
     * @return
     */
    private List<Map> prepareFileInfo(List<SysLog> logs) {
        List<Map> dataList = new ArrayList<>();
        logs.forEach(log->{
            Map dataMap = new LinkedHashMap();
            dataMap.put("用户ID", log.getOpUserId());
            dataMap.put("用户IP", log.getOpIpAddress());
            dataMap.put("操作时间", CommonTools.FormatDate(log.getOpDate(), "yyyy-MM-dd HH:mm:ss"));
            dataMap.put("操作类型ID", log.getOpTypeId());
            dataMap.put("操作目标", log.getOpTarget());
            dataMap.put("操作记录", log.getOpRemark());
            dataMap.put("操作结果", log.getOpResult());
            dataList.add(dataMap);
        });
        return dataList;
    }

    /**
     * 创建定时备份
     * @param formValue
     */
    public void createBackUpJob(QuartzJobEntity formValue) {
        //启动定时任务
        String conExpr = extraConExpr(formValue);
        //进行中
        formValue.setBacktype("1");
        backUpJobService.save(formValue);
        //增加job
        backUpScheduler.updateJob(BackUpLogJob.class, formValue.getId().toString(), conExpr);
    }

    public String extraConExpr(QuartzJobEntity formValue) {
        String retVal = "";
        String[] times;
        if (!StringUtil.isEmpty(formValue.getIsdayback())) {
            times = formValue.getDaybacktime().split(":");
            retVal = times[2] + " " + times[1] + " " + times[0] + " * * ?";
        } else if (!StringUtil.isEmpty(formValue.getIsweekback())) {
            times = formValue.getWeekbacktime().split(":");
            retVal = times[2] + " " + times[1] + " " + times[0] + " ? * " + (Integer.parseInt(formValue.getWeekbackday()) % 7 + 1);
        } else if (!StringUtil.isEmpty(formValue.getIsmonthback())) {
            times = formValue.getMonthbacktime().split(":");
            retVal = times[2] + " " + times[1] + " " + times[0] + " " + formValue.getMonthbackday() + " * ?";
        }
        return retVal;
    }

}
