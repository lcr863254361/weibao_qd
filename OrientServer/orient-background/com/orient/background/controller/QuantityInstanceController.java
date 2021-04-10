package com.orient.background.controller;

import com.orient.background.bean.CfQuantityInstanceVO;
import com.orient.background.bean.ExportODSVO;
import com.orient.background.bean.ModelPathVO;
import com.orient.background.business.QuantityInstanceBusiness;
import com.orient.edm.init.FileServerConfig;
import com.orient.ods.atfx.bean.MeasurementPathBean;
import com.orient.ods.atfx.business.AtfxFileExportBusiness;
import com.orient.ods.atfx.business.AtfxFileMangrBusiness;
import com.orient.ods.atfx.model.AtfxSession;
import com.orient.sysmodel.domain.quantity.CfQuantityInstanceDO;
import com.orient.utils.FileOperator;
import com.orient.utils.JsonUtil;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.asam.ods.InstanceElement;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Controller
@RequestMapping("/QuantityInstance")
public class QuantityInstanceController extends BaseController {
    @Autowired
    QuantityInstanceBusiness quantityInstanceBusiness;

    @Autowired
    private AtfxFileMangrBusiness atfxFileMangrBusiness;

    @Autowired
    private AtfxFileExportBusiness atfxFileExportBusiness;

    @Autowired
    private FileServerConfig fileServerConfig;

    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<CfQuantityInstanceVO> list(Integer page, Integer limit, CfQuantityInstanceDO filter) {
        return quantityInstanceBusiness.listSpecial(page, limit, filter);
    }

    @RequestMapping("createByTemplate")
    @ResponseBody
    public CommonResponseData createByTemplate(Long modelId, Long dataId, Long[] templateIds) {
        CommonResponseData retVal = new CommonResponseData();
        quantityInstanceBusiness.createByTemplate(modelId, dataId, templateIds);
        retVal.setMsg("导入成功 ");
        return retVal;
    }

    @RequestMapping("createByManual")
    @ResponseBody
    public CommonResponseData createByManual(Long modelId, Long dataId, Long[] quantityIds) {
        CommonResponseData retVal = new CommonResponseData();
        quantityInstanceBusiness.createByManual(modelId, dataId, quantityIds);
        retVal.setMsg("导入成功 ");
        return retVal;
    }


    /**
     * 新增数据
     *
     * @param formValue
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public CommonResponseData create(CfQuantityInstanceDO formValue) {
        CommonResponseData retVal = new CommonResponseData();
        quantityInstanceBusiness.save(formValue);
        retVal.setMsg(formValue.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    @RequestMapping("update")
    @ResponseBody
    public CommonResponseData update(String formData) {
        CommonResponseData retVal = new CommonResponseData();
        CfQuantityInstanceVO quantityInstanceVO = JsonUtil.jsonToObj(new CfQuantityInstanceVO(), formData);
        quantityInstanceBusiness.updateSpecial(quantityInstanceVO);
        retVal.setMsg(quantityInstanceVO.getId() != null ? "保存成功!" : "保存失败!");
        return retVal;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(Long[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        quantityInstanceBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

    @RequestMapping("getModelPath")
    @ResponseBody
    public AjaxResponseData<List<ModelPathVO>> getModelPath(String startModelId, String endModelId) {
        AjaxResponseData<List<ModelPathVO>> retVal = new AjaxResponseData();
        List<ModelPathVO> results = quantityInstanceBusiness.getModelPath(startModelId, endModelId);
        retVal.setResults(results);
        return retVal;
    }

    @RequestMapping("exportTOODS")
    @ResponseBody
    public  AjaxResponseData<String> saveTOODS(@RequestBody ExportODSVO exportODSVO, HttpServletRequest request, HttpServletResponse response) {

        //1.prepare quantities
        List<CfQuantityInstanceDO> quantityInstanceDOs = quantityInstanceBusiness.list(null, null, null,
                Restrictions.eq("modelId", exportODSVO.getModelId()),
                Restrictions.eq("dataId", exportODSVO.getDataId())).getResults();
        List<MeasurementPathBean> measurementPathBeen = new ArrayList<>();
        //2.preapare measurepathbean
        ModelPathVO modelPathVO = exportODSVO.getModelPath();
        List<Map<String, String>> modelPathTree = quantityInstanceBusiness.extractModelPath(modelPathVO, exportODSVO.getDataId());
        modelPathTree.forEach(modelAndData -> {
            MeasurementPathBean measurementPathBean = new MeasurementPathBean();
            measurementPathBean.setModelId(modelAndData.get("modelId"));
            measurementPathBean.setDataId(modelAndData.get("dataId"));
            measurementPathBean.setDisplayName(modelAndData.get("displayName"));
            measurementPathBeen.add(measurementPathBean);
        });

        //3.prepare zip data
        String temp = fileServerConfig.getTemp();
        String tempDir = "ODS_" + exportODSVO.getModelId() + "_" + UserContextUtil.getCurrentUser().getUserName();
        String ODSZipDir = temp + File.separator + tempDir;
        File dir = new File(ODSZipDir);
        if (dir.exists()) {
            dir.delete();
        }
        FileOperator.createFolder(ODSZipDir);
        String fileName = ODSZipDir + File.separator + "OrientExportODS.atfx";
        AtfxSession atfxSession = atfxFileMangrBusiness.createExportFile(fileName);

        //4.write atfx file
        InstanceElement testInstance = atfxFileExportBusiness.writeMeasurementPath(measurementPathBeen, atfxSession);
        atfxFileExportBusiness.writeMeasurementAndQuantities(testInstance, atfxSession, quantityInstanceDOs, measurementPathBeen.get(0).getDisplayName());
        atfxFileMangrBusiness.closeExportFile(atfxSession);
        String zipFile = ODSZipDir + ".ods";
        try {
            FileOperator.zip(ODSZipDir, zipFile, tempDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String zipFileName = FileOperator.getFileName(zipFile);
        return new AjaxResponseData<>(File.separator + "Temp" + File.separator + zipFileName);
    }

}
