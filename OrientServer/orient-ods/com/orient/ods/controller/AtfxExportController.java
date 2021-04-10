package com.orient.ods.controller;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.ods.atfx.bean.MeasurementPathBean;
import com.orient.ods.atfx.business.AtfxFileExportBusiness;
import com.orient.ods.atfx.business.AtfxFileMangrBusiness;
import com.orient.ods.atfx.model.AtfxSession;
import com.orient.utils.FileOperator;
import com.orient.utils.JsonUtil;
import com.orient.web.base.*;
import com.orient.web.util.UserContextUtil;
import org.asam.ods.InstanceElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author mengbin
 * @create 2016-07-21 上午9:59
 */
@Controller
@RequestMapping("/AtfxExport")
public class AtfxExportController extends BaseController{



    @Autowired
    private ModelDataBusiness modelDataBusiness;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Autowired
    private AtfxFileMangrBusiness atfxFileMangrBusiness;

    @Autowired
    private AtfxFileExportBusiness atfxFileExportBusiness;

    @Autowired
    private FileServerConfig fileServerConfig;

    @RequestMapping("/exportODS")
    @ResponseBody
    public AjaxResponseData<String> exportODS(String modelId, String isView, String customerFilter,String pathBeans) {
        AjaxResponseData<String> retVal = new AjaxResponseData();
        try {

            List<MeasurementPathBean>  measurementPathBeen = JsonUtil.getJavaCollection(new MeasurementPathBean(),pathBeans);
            String temp = fileServerConfig.getTemp();
            String tempDir = "ODS_"+modelId+"_"+UserContextUtil.getCurrentUser().getUserName();
            String ODSZipDir = temp+ File.separator+tempDir;
            File dir = new File(ODSZipDir);
            if (dir.exists()){
                dir.delete();
            }
            FileOperator.createFolder(ODSZipDir);

            String fileName = ODSZipDir+File.separator+"OrientExportODS.atfx";
            AtfxSession atfxSession = atfxFileMangrBusiness.createExportFile(fileName);

            Map<IBusinessColumn,List<String>> gridData = modelDataBusiness.getNumricModelDataByModelId(modelId,isView,null,null,customerFilter);
            String userId = UserContextUtil.getUserId();
            EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
            IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, modelId, null, modelTypeEnum);

            InstanceElement testInstance = atfxFileExportBusiness.writeMeasurementPath(measurementPathBeen,atfxSession);
            atfxFileExportBusiness.writeMeasurement(testInstance,atfxSession,businessModel,gridData);
            atfxFileMangrBusiness.closeExportFile(atfxSession);
            String zipFile = ODSZipDir+".ods";

            FileOperator.zip(ODSZipDir, zipFile, tempDir);
            String zipFileName = FileOperator.getFileName(zipFile);

            retVal.setResults("/Temp" + File.separator + zipFileName);
        }
        catch (Exception e) {
            e.printStackTrace();
            return retVal;
        }
        return retVal;
    }

}
