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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class DepthDesityBusiness extends BaseBusiness {

    @Autowired
    ExcelImport excelImport;
    @Autowired
    FileServerConfig fileServerConfig;

    public Map<String, Object> importDepthDesityData(MultipartFile file, String depthDesityTypeId) throws Exception {
        Map<String, Object> retVal = UtilFactory.newHashMap();
        IBusinessModel depthDesityBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTH_DESITY, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        depthDesityBM.setReserve_filter("AND T_DEPTH_DESITY_TYPE_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + depthDesityTypeId + "'");
        List<Map> depthDesityList = orientSqlEngine.getBmService().createModelQuery(depthDesityBM).list();
        IBusinessModel[] models = {depthDesityBM};
        //获取sheet页的数据
        List<List<Map<String, String>>> excelList = excelImport.importFile(file, models);
        if (excelList != null && excelList.size() > 0) {
            for (int i = 0; i < excelList.size(); i++) {
                for (Map depthDesityMap : excelList.get(i)) {
                    String depth = CommonTools.Obj2String(depthDesityMap.get("C_DEPTH_" + depthDesityBM.getId()));
                    boolean isExsit = false;
                    if (depthDesityList != null && depthDesityList.size() > 0) {
                        for (Map existDepthDesityMap : depthDesityList) {
                            String existKeyId = existDepthDesityMap.get("ID").toString();
                            String existDepth = CommonTools.Obj2String(existDepthDesityMap.get("C_DEPTH_" + depthDesityBM.getId()));
                            if (depth.equals(existDepth)) {
                                isExsit = true;
                                orientSqlEngine.getBmService().updateModelData(depthDesityBM, depthDesityMap, existKeyId);
                                break;
                            }
                        }
                    }
                    if (!isExsit) {
                        depthDesityMap.put("T_DEPTH_DESITY_TYPE_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID", depthDesityTypeId);
                        orientSqlEngine.getBmService().insertModelData(depthDesityBM, depthDesityMap);
                    }
                }
            }
            retVal.put("success", true);
            retVal.put("msg", "成功导入！");
        }
        return retVal;
    }

    public String exportDepthDesityData(boolean exportAll, String toExportIds, String depthDesityTypeId, String seaAreaName) {
        IBusinessModel depthDesityBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEPTH_DESITY, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        Object[] headers = new Object[]{"深度", "密度", "压力"};
        Excel excel = new Excel();
        excel.row(0).value(headers);
        final int[] i = {1};
        if (exportAll) {
            depthDesityBM.setReserve_filter("AND T_DEPTH_DESITY_TYPE_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + depthDesityTypeId + "'");
            List<Map<String, Object>> depthDesityList = orientSqlEngine.getBmService().createModelQuery(depthDesityBM).orderAsc("TO_NUMBER(C_DEPTH_" + depthDesityBM.getId() + ")").list();
            packageExcel(depthDesityList, excel, i, depthDesityBM);
        } else {
            if (!"".equals(toExportIds)) {
                depthDesityBM.setReserve_filter("AND T_DEPTH_DESITY_TYPE_" + PropertyConstant.WEI_BAO_SCHEMA_ID + "_ID='" + depthDesityTypeId + "'" +
                        " and id in (" + toExportIds + ")");
                List<Map<String, Object>> depthDesityList = orientSqlEngine.getBmService().createModelQuery(depthDesityBM).orderAsc("TO_NUMBER(C_DEPTH_" + depthDesityBM.getId() + ")").list();
                packageExcel(depthDesityList, excel, i, depthDesityBM);
            }
        }
        for (int j = 0; j < headers.length; j++) {
            excel.column(j).autoWidth();
        }
        String fileName = seaAreaName;
        String carryToolFolderPath = FtpFileUtil.EXPORT_ROOT + File.separator + "深度密度数据";
        String folder = FtpFileUtil.getRelativeUploadPath(carryToolFolderPath);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName + ".xls");
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        return fileServerConfig.getFtpHome() + folder + finalFileName;
    }

    private void packageExcel(List<Map<String, Object>> depthDesityList, Excel excel, int i[], IBusinessModel depthDesityBM) {
        if (depthDesityList != null && depthDesityList.size() > 0) {
            for (Map map : depthDesityList) {
                excel.cell(i[0], 0).value(map.get("C_DEPTH_" + depthDesityBM.getId()));
                excel.cell(i[0], 1).value(map.get("C_DESITY_" + depthDesityBM.getId()));
                excel.cell(i[0], 2).value(map.get("C_PRESSURE_" + depthDesityBM.getId()));
                i[0]++;
            }
        }
    }
}
