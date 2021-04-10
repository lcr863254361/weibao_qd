package com.orient.weibao.controller;

import com.orient.background.event.SaveModelViewEvent;
import com.orient.background.eventParam.SaveModelViewEventParam;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysman.bean.SchemaBean;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.sysmodel.domain.user.User;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.modelDesc.column.ColumnDesc;
import com.orient.web.util.UserContextUtil;
import com.orient.weibao.bean.DivingStatisticsBean.DivingStatisticsModel;
import com.orient.weibao.bean.flowPost.FlowPostData;
import com.orient.weibao.business.DivingStatisticsBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/divingStatisticsMgr")
public class DivingStatisticsController extends BaseController {

    @Autowired
    DivingStatisticsBusiness divingStatisticsBusiness;

    /**
     * 导出潜次数据
     *
     * @param exportAll
     * @param toExportIds
     * @param response
     */
    @RequestMapping("exportDivingData")
    @ResponseBody
    public void exportDivingData(boolean exportAll, String toExportIds, HttpServletResponse response) {
        String filePath = divingStatisticsBusiness.exportDivingData(exportAll, toExportIds);
        try {
            response.setContentType("aplication/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("潜次统计.xls", "UTF-8"));
//            BufferedInputStream in = new BufferedInputStream(new FileInputStream("diving.xls"));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找潜次统计数据
     *
     * @return
     */
    @RequestMapping("queryDivingStatisticsData")
    @ResponseBody
    public ExtGridData<DivingStatisticsModel> queryDivingStatisticsData(String customerFilter, Integer page, Integer limit) {
        return divingStatisticsBusiness.queryDivingStatisticsData(customerFilter, page, limit);
    }


    @RequestMapping("getStatisticsLineList")
    @ResponseBody
    public ExtGridData<SchemaBean> getStatisticsLineList(String roleId, String assigned, Integer page, Integer limit) {
        ExtGridData<SchemaBean> retVal = divingStatisticsBusiness.getStatisticsLineList(roleId, assigned, page, limit);
        return retVal;
    }

    @RequestMapping("saveAssignLine")
    @ResponseBody
    public AjaxResponseData<Boolean> saveAssignLine(String roleId, String[] selectedIds, String direction) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();
        retVal.setSuccess(divingStatisticsBusiness.saveAssignLine(roleId, selectedIds, direction));
        return retVal;
    }

    @RequestMapping("getStatisticsLineHead")
    @ResponseBody
    public AjaxResponseData<FlowPostData> getStatisticsLineHead() {
        return divingStatisticsBusiness.getStatisticsLineHead();
    }

    @RequestMapping("getRoleStatisticLineData")
    @ResponseBody
    public ExtGridData getRoleStatisticLineData(Integer page, Integer limit, String columnJsonString) {
        ExtGridData str = divingStatisticsBusiness.getRoleStatisticLineData(page, limit, columnJsonString);
        return str;
    }

    @RequestMapping("getStatisticsModelColumn")
    @ResponseBody
    public AjaxResponseData<List<ColumnDesc>> getStatisticsModelColumn(String orientModelId) {
        List<ColumnDesc> retVal = divingStatisticsBusiness.getStatisticsModelColumn(orientModelId);
        return new AjaxResponseData(retVal);
    }

    @RequestMapping("updateStatisticsModelColumn")
    @ResponseBody
    public CommonResponseData updateStatisticsModelColumn(ModelFormViewEntity formValue) {
        CommonResponseData retVal = new CommonResponseData();
        divingStatisticsBusiness.updateStatisticsModelColumn(formValue);
        retVal.setMsg("修改成功");
        return retVal;
    }

    /**
     * 获取下潜统计数据
     * @param orientModelId
     * @param isView
     * @param page
     * @param limit
     * @param customerFilter
     * @param sort
     * @return
     */
    @RequestMapping("getDivingStatisticGridData")
    @ResponseBody
    public ExtGridData<Map> getDivingStatisticGridData(String orientModelId, String isView, Integer page, Integer limit, String customerFilter, String sort) {
        ExtGridData<Map> retVal = divingStatisticsBusiness.getDivingStatisticGridData(orientModelId, isView, page, limit, customerFilter, true, sort);
        return retVal;
    }

    /**
     * 导出下潜统计信息
     * @param exportAll
     * @param toExportIds
     * @param response
     */
    @RequestMapping("exportDivingStatisticsData")
    @ResponseBody
    public void exportDivingStatisticsData(boolean exportAll, String toExportIds, HttpServletResponse response) {
        String filePath = divingStatisticsBusiness.exportDivingStatisticsData(exportAll, toExportIds);
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        try {
            response.setContentType("aplication/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
