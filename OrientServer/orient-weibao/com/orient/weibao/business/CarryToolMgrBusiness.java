package com.orient.weibao.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.utils.ExcelImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CarryToolMgrBusiness extends BaseBusiness {

    @Autowired
    ExcelImport excelImport;
    @Autowired
    FileServerConfig fileServerConfig;

    public Map<String, Object> importCarryToolData(MultipartFile file, String isCabinOrOut) throws Exception {
        Map<String, Object> retVal = UtilFactory.newHashMap();
        IBusinessModel cabinCarryToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CABIN_CARRY_TOOL, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        String modelId = cabinCarryToolBM.getId();
        cabinCarryToolBM.setReserve_filter("AND C_CABIN_INOROUT_" + cabinCarryToolBM.getId() + "='" + isCabinOrOut + "'");
        List<Map> cabinCarryToolList = orientSqlEngine.getBmService().createModelQuery(cabinCarryToolBM).list();
        IBusinessModel[] models = {cabinCarryToolBM};
        String sql = "select max(C_SERIAL_NUMBER_" + modelId + ") from T_CABIN_CARRY_TOOL_" + PropertyConstant.WEI_BAO_SCHEMA_ID + " where C_CABIN_INOROUT_" + modelId + " = '" + isCabinOrOut + "'";
        int maxNumber = (int) metaDaoFactory.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.execute();
                ResultSet rs = preparedStatement.getResultSet();
                rs.next();
                return rs.getInt(1);
            }
        });
        //获取sheet页的数据
        List<List<Map<String, String>>> excelList = excelImport.importFile(file, models);
        if (excelList != null && excelList.size() > 0) {
            for (int i = 0; i < excelList.size(); i++) {
                for (Map carryToolMap : excelList.get(i)) {
                    String name = CommonTools.Obj2String(carryToolMap.get("C_NAME_" + cabinCarryToolBM.getId()));
                    boolean isExsit = false;
                    if (cabinCarryToolList != null && cabinCarryToolList.size() > 0) {
                        for (Map existCarryToolMap : cabinCarryToolList) {
                            String existKeyId = existCarryToolMap.get("ID").toString();
                            String existName = CommonTools.Obj2String(existCarryToolMap.get("C_NAME_" + cabinCarryToolBM.getId()));
                            if (name.equals(existName)) {
                                isExsit = true;
                                orientSqlEngine.getBmService().updateModelData(cabinCarryToolBM, carryToolMap, existKeyId);
                                break;
                            }
                        }
                    }
                    if (!isExsit) {
                        carryToolMap.put("C_CABIN_INOROUT_" + cabinCarryToolBM.getId(), isCabinOrOut);
                        maxNumber++;
                        carryToolMap.put("C_SERIAL_NUMBER_" + cabinCarryToolBM.getId(), maxNumber);
                        orientSqlEngine.getBmService().insertModelData(cabinCarryToolBM, carryToolMap);
                    }
                }
            }
            retVal.put("success", true);
            retVal.put("msg", "成功导入！");
        }
        return retVal;
    }

    public String exportCarryToolData(boolean exportAll, String toExportIds, String isCabinOrOut) {
        IBusinessModel cabinCarryToolBM = businessModelService.getBusinessModelBySName(PropertyConstant.CABIN_CARRY_TOOL, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        Object[] headers = {};
        if ("cabinIn".equals(isCabinOrOut)) {
            isCabinOrOut = "cabinIn";
            headers = new Object[]{"名称", "空气中重量(Kg)", "库存量", "与潜水器连接方式", "存放位置", "管理人"};
        } else {
            isCabinOrOut = "cabinOut";
            headers = new Object[]{"名称", "长度", "宽度", "高度", "空气中重量(Kg)", "排水体积(L)", "淡水中重量(Kg)", "库存量", "与潜水器连接方式", "存放位置", "管理人"};
        }
        Excel excel = new Excel();
        excel.row(0).value(headers);
        final int[] i = {1};
        if (exportAll) {
            cabinCarryToolBM.setReserve_filter("AND C_CABIN_INOROUT_" + cabinCarryToolBM.getId() + "='" + isCabinOrOut + "'");
            List<Map<String, Object>> carryToolList = orientSqlEngine.getBmService().createModelQuery(cabinCarryToolBM).orderAsc("TO_NUMBER(C_SERIAL_NUMBER_" + cabinCarryToolBM.getId()+")").list();
            packageExcel(carryToolList, excel, i, cabinCarryToolBM, isCabinOrOut);
        } else {
            if (!"".equals(toExportIds)) {
                cabinCarryToolBM.setReserve_filter("AND C_CABIN_INOROUT_" + cabinCarryToolBM.getId() + "='" + isCabinOrOut + "'" +
                        " and id in (" + toExportIds + ")");
                List<Map<String, Object>> carryToolList = orientSqlEngine.getBmService().createModelQuery(cabinCarryToolBM).orderAsc("TO_NUMBER(C_SERIAL_NUMBER_" + cabinCarryToolBM.getId()+")").list();
                packageExcel(carryToolList, excel, i, cabinCarryToolBM, isCabinOrOut);
            }
        }
        for (int j = 0; j < headers.length; j++) {
            excel.column(j).autoWidth();
        }
        String fileName = "";
        if ("cabinIn".equals(isCabinOrOut)) {
            fileName = "舱内携带的作业工具";
        } else {
            fileName = "舱外携带的作业工具";
        }
        String carryToolFolderPath = FtpFileUtil.EXPORT_ROOT + File.separator + "携带作业工具";
        String folder = FtpFileUtil.getRelativeUploadPath(carryToolFolderPath);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName + ".xls");
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        return fileServerConfig.getFtpHome() + folder + finalFileName;
    }

    private void packageExcel(List<Map<String, Object>> carryToolList, Excel excel, int i[], IBusinessModel cabinCarryToolBM, String isCabinOrOut) {
        if (carryToolList != null && carryToolList.size() > 0) {
            for (Map map : carryToolList) {
                excel.cell(i[0], 0).value(map.get("C_NAME_" + cabinCarryToolBM.getId()));
                if ("cabinIn".equals(isCabinOrOut)) {
                    excel.cell(i[0], 1).value(map.get("C_AIR_WEIGHT_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 2).value(map.get("C_STOCK_NUMBER_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 3).value(map.get("C_CONNECT_WAY_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 4).value(map.get("C_STORE_POSITION_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 5).value(map.get("C_MANAGER_" + cabinCarryToolBM.getId()));
                }else{
                    excel.cell(i[0], 1).value(map.get("C_LENGTH_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 2).value(map.get("C_WIDTH_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 3).value(map.get("C_HEIGHT_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 4).value(map.get("C_AIR_WEIGHT_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 5).value(map.get("C_DEWATER_VOLUME_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 6).value(map.get("C_FRESH_WATER_WEIGHT_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 7).value(map.get("C_STOCK_NUMBER_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 8).value(map.get("C_CONNECT_WAY_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 9).value(map.get("C_STORE_POSITION_" + cabinCarryToolBM.getId()));
                    excel.cell(i[0], 10).value(map.get("C_MANAGER_" + cabinCarryToolBM.getId()));
                }
                i[0]++;
            }
        }
    }
}
