package com.orient.modeldata.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.dataanalyze.offline.util.OriginUtil;
import com.orient.modeldata.dataanalyze.offline.util.PlotUtil;
import com.orient.modeldata.dataanalyze.online.bean.PostSolution;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.web.base.BaseBusiness;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

import static com.orient.utils.JsonUtil.getJavaCollection;

/**
 * 数据分析业务处理
 */
@Service
public class DataAnalysisBusiness extends BaseBusiness {
    @Autowired
    FileServerConfig fileServerConfig;

    public List<PostSolution> postCharting(String modelId, String xAxis, String yAxis, String ids) {
        List<PostSolution> solutions = new ArrayList<>();
        IBusinessModel mainModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        if(mainModel == null) {
            mainModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.View);
        }
        String solutionName = mainModel.getDisplay_name();

        List<IBusinessColumn> toChartColumn = new ArrayList<>();

        List<IBusinessColumn> columns = mainModel.getAllBcCols();
        for (IBusinessColumn column : columns) {
            if (column.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Integer)
                    || column.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_BigInteger)
                    || column.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Decimal)
                    || column.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Float)
                    || column.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Double)) {
                String s_column_name = column.getS_column_name();
                if(xAxis!=null && !"".equals(xAxis) && s_column_name.equals(xAxis)){
                    toChartColumn.add(0, column);
                    continue;
                }
                if(yAxis!=null && !"".equals(yAxis)){
                    if(CommonTools.ifInStr(s_column_name, yAxis)) {
                        toChartColumn.add(column);
                    }
                }
                else {
                    toChartColumn.add(column);
                }
            }
        }
        long rowCount = orientSqlEngine.getBmService().createModelQuery(mainModel).count();
        int columnCount = toChartColumn.size();
        StringBuffer sql = new StringBuffer();
        for (IBusinessColumn column : toChartColumn) {
            sql.delete(0, sql.length());
            sql.append("SELECT " + column.getS_column_name() + " FROM ");
            sql.append(mainModel.getS_table_name()).append(" WHERE 1=1 ");
            if (!CommonTools.isNullString(ids)) {
                sql.append(" AND ID IN ('" + ids.replace(",", "','") + "')");
            }
            List<Map<String, Object>> data = metaDaoFactory.getJdbcTemplate()
                    .queryForList(sql.toString());
            PostSolution sol = new PostSolution(String.valueOf(columnCount),
                    solutionName, String.valueOf(rowCount), data);
            solutions.add(sol);
        }
        return solutions;
    }

    public Map<String, Object> jsCharting(String modelId, String xAxis, String[] yAxis, String ids) {
        Map<String, Object> retMap = new HashMap<>();
        IBusinessModel mainModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        if(mainModel == null) {
            mainModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.View);
        }

        Set<String> columnNames = new HashSet<>(Arrays.asList(yAxis));
        columnNames.add(xAxis);
        columnNames.remove("");

        Map<String, String> columnMap = new HashMap<>();
        for (IBusinessColumn column : mainModel.getAllBcCols()) {
            String s_column_name = column.getS_column_name();
            if(columnNames.contains(s_column_name)) {
                columnMap.put(column.getDisplay_name(), s_column_name);
                continue;
            }
        }
        retMap.put("columnMap", columnMap);

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT " + CommonTools.set2String(columnNames) + " FROM ");
        sql.append(mainModel.getS_table_name()).append(" WHERE 1=1 ");
        if (!CommonTools.isNullString(ids)) {
            sql.append(" AND ID IN ('" + ids.replace(",", "','") + "')");
        }
        List<Map<String, Object>> data = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
        retMap.put("dataList", data);

        return retMap;
    }

    public String plotAnalysis(String modelId, String customFilter, String modelType) {
        String userName = UserContextUtil.getUserName();
        String result = "";
        try{
            String ftpHome = fileServerConfig.getFtpHome();
            IBusinessModel mainModel = null;
            if("0".equals(modelType)){
                mainModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
            }
            else {
                mainModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.View);
            }

            if (!StringUtils.isEmpty(customFilter)) {
                List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customFilter);
                for(CustomerFilter filter : customerFilters) {
                    mainModel.appendCustomerFilter(filter);
                }
            }

            IBusinessModelQuery modelQuery = orientSqlEngine.getBmService().createModelQuery(mainModel);
            List<Map> dataList = modelQuery.list();

            String fileName =  System.currentTimeMillis() + "";
            String userDir = ftpHome;// + userName + "\\";
            File userFile = new File(userDir);
            if (!userFile.exists()) {
                userFile.mkdir();
            }

            PlotUtil util = new PlotUtil(orientSqlEngine, businessModelService, mainModel, dataList, userDir+fileName);
            util.init();
            util.writeData();
            util.writeXml(false);
            util.destory();

            FileOperator.zip(userDir+fileName, userDir+fileName+".zip", "");
            FileOperator.delFoldsWithChilds(userDir+fileName);
            result = fileName+".zip";

        }
        catch (Exception e) {
            result = "[Error]" + e.getMessage();
        }
        return result;
    }

    public String originAnalysis(String modelId, String customFilter, String modelType) {
        String userName = UserContextUtil.getUserName();
        String result = "";
        try{
            String ftpHome = fileServerConfig.getFtpHome();
            IBusinessModel mainModel = null;
            if("0".equals(modelType)){
                mainModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
            }
            else {
                mainModel = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.View);
            }

            if (!StringUtils.isEmpty(customFilter)) {
                List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customFilter);
                for(CustomerFilter filter : customerFilters) {
                    mainModel.appendCustomerFilter(filter);
                }
            }

            IBusinessModelQuery modelQuery = orientSqlEngine.getBmService().createModelQuery(mainModel);
            List<Map> dataList = modelQuery.list();

            OriginUtil util = new OriginUtil(mainModel, dataList, ftpHome);
            util.readData();
            result = util.generateZipFile();
        }
        catch (Exception e) {
            result = "[Error]" + e.getMessage();
        }
        return result;
    }
}

