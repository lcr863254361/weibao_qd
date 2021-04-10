package com.orient.weibao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.config.ConfigInfo;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.event.UpdateModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.sysman.bean.FuncBean;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.JsonUtil;
import com.orient.utils.FileOperator;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.weibao.bean.CheckTypeTreeNode;
import com.orient.weibao.bean.FormProductTreeNode;
import com.orient.weibao.bean.ProductStructureTreeNode;
import com.orient.weibao.bean.flowPost.FlowPostData;
import com.orient.weibao.business.FormTemplateMgrBusiness;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.utils.WeibaoPropertyUtil;
import com.orient.weibao.utils.DeCompress;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.record.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2018-12-12 19:50
 */
@Controller
@RequestMapping("/formTemplate")
public class FormTemplateMgrController extends BaseController {

    @Autowired
    FormTemplateMgrBusiness formTemplateMgrBusiness;

    private Logger logger = LoggerFactory.getLogger(FormTemplateMgrController.class);
    @Autowired
    IBusinessModelService businessModelService;

    /**
     * 获取检查类型列表
     *
     * @param:
     * @return:
     */
    @RequestMapping("queryCheckTypeList")
    @ResponseBody
    public JSONObject queryCheckTypeList() {
        List<Map> checkTypeList = formTemplateMgrBusiness.queryCheckTypeList();
        JSONArray jsonArray = JSONArray.fromObject(checkTypeList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("results", jsonArray);
        return jsonObject;
    }

    /**
     * 保存检查类型数据
     *
     * @param: modelId
     * @param: formData
     * @return:
     */
    @RequestMapping("saveCheckTypeData")
    @ResponseBody
    public AjaxResponseData saveCheckTypeData(String modelId, String formData) {
        return formTemplateMgrBusiness.saveCheckTypeData(modelId, formData);
    }


    /**
     *  批量文件上传，目前支持excel格式以及zip格式
     **/

    @ResponseBody
    @RequestMapping("multiImportCheckListFromExcel")
    public Map<String, Object>  importMultiCheckListFromExcel(HttpServletRequest request, HttpServletResponse response)  throws Exception{
        String fileName = "";
        Map<String, Object> retVal = null;
        String location = WeibaoPropertyUtil.getPropertyValueConfigured("zip.upload.path","config.properties","C:");
        String encoding = WeibaoPropertyUtil.getPropertyValueConfigured("zip.encoding", "config.properties", "C:");
        String filePath = location+"\\data\\orient\\temp\\";
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                String originalFilename = fileName;
                String fileSufix = fileName.substring(fileName.lastIndexOf(".")+1);
                File dst = new File(fileName);
                if("zip".equals(fileSufix)){
                    File desZip = new File(filePath + fileName);
                    if(!desZip.exists()){
                        desZip.mkdirs();
                    }
                    file.transferTo(desZip);
                    String zipUrl = filePath + fileName;
                    DeCompress.DecompressUtil(zipUrl,filePath,encoding);
                    FileOperator fileOperator  = new FileOperator();
                    List<String> fileList = fileOperator.getAllFileWithPath(filePath);
                    int sumCount=0;
                    int newCount=0;
                    int repeatCount=0;
                    String repeatFileNames="";
                    for(String path:fileList){
                        String sufix = path.substring(path.lastIndexOf(".") + 1);
                        if("xls".equals(sufix)||"xlsx".equals(sufix)){
                            sumCount++;
                            retVal = handleExcel(request,path);
                            boolean isRepeat = (boolean) retVal.get("isRepeat");
                            if(retVal.containsKey("success")&&isRepeat){
                                repeatCount++;
                                if(path.contains("\\")||path.contains("/")){
                                    repeatFileNames = repeatFileNames+FileOperator.getFileName(path)+",";
                                }
                            }
                            if(retVal.containsKey("success")&&!isRepeat){
                                newCount++;
                            }
                        }
                    }
                    retVal.put("msg","导入成功，发现模板："+sumCount+"个，新增"+newCount+"个,替换"+repeatCount+"个,替换文件:"+repeatFileNames);
                    desZip.delete();
                }else if("xls".equals(fileSufix)||"xlsx".equals(fileSufix)){
                    file.transferTo(dst);
                    retVal =handleExcel(request,fileName);
                    boolean isRepeat = (boolean) retVal.get("isRepeat");
                    if(isRepeat){
                        retVal.put("msg",originalFilename+" 替换成功");
                    }
                }else {
                    retVal = new HashMap<>();
                    retVal.put("msg","目前仅支持.xls,.xlsx,.zip文件格式");
                }
            }
        }
        DeCompress.deleteDirectory(filePath);
        return  retVal;
    }

    public   Map<String, Object>   handleExcel(HttpServletRequest request,String fileName) throws Exception {

        ExcelReader excelReader = new ExcelReader();
        List<String> headers=null;
        File excelFile = new File(fileName);
        InputStream input = new FileInputStream(excelFile);
        boolean after2007 = fileName.substring(fileName.length() - 4).equals("xlsx");
        TableEntity excelEntity = excelReader.readFile(input, after2007);
        if(excelReader.getNumOfSheets()>1){
            headers = getHeaders(excelEntity);
        }else {
            headers = Arrays.asList(excelReader.getExcelReaderConfig().getColumns());
        }

        String checkTypeId = request.getParameter("checkTypeId");
        String checkTypeName=request.getParameter("checkTypeName");
        System.out.println("filename:"+fileName);
        if(fileName.contains("\\")||fileName.contains("/")){
            fileName = FileOperator.getFileName(fileName);
        }
        //插入检查表模板数据
        String tempId = formTemplateMgrBusiness.insertCheckListTemplate(fileName, checkTypeId,checkTypeName);
        excelFile.delete();

        //插入表头与单元格数据
        Map<String, Object> retVal = formTemplateMgrBusiness.importHeadCellList(excelEntity, headers, tempId,fileName);

        return  retVal;

    }


    /**
     * 导入检查表模板数据
     *
     * @param: request
     * @param: response
     * @return:
     */
    @RequestMapping("importCheckListFromExcel")
    @ResponseBody
    public void importCheckListFromExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = null;
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包含multipart类型的数据
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            if (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile((String) iter.next());
                fileName = file.getOriginalFilename();
                File dst = new File(fileName);
                try {
                    file.transferTo(dst);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ExcelReader excelReader = new ExcelReader();
        File excelFile = new File(fileName);
        InputStream input = new FileInputStream(excelFile);
        boolean after2007 = fileName.substring(fileName.length() - 4).equals("xlsx");
        TableEntity excelEntity = excelReader.readFile(input, after2007);
        List<String> headers = Arrays.asList(excelReader.getExcelReaderConfig().getColumns());

        String checkTypeId = request.getParameter("checkTypeId");
        String checkTypeName=request.getParameter("checkTypeName");

        //插入检查表模板数据
        String tempId = formTemplateMgrBusiness.insertCheckListTemplate(fileName, checkTypeId,checkTypeName);

        //插入表头与单元格数据
        Map<String, Object> retVal = formTemplateMgrBusiness.importHeadCellList(excelEntity, headers, tempId,fileName);

        try {
            response.setContentType("text/html");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), retVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        excelFile.delete();

    }

    /**
     * 获取检查模板树节点
     *
     * @param: id
     * @param: type
     * @param: checkTypeId
     * @return:
     */
    @RequestMapping("getCheckTempTreeNodes")
    @ResponseBody
    public AjaxResponseData<List<CheckTypeTreeNode>> getCheckTempTreeNodes(String id, String type, String checkTypeId) {
        return formTemplateMgrBusiness.getCheckTempTreeNodes(id, type, checkTypeId);
    }

    /**
     * 删除检查表模板及其关联的表头、行、单元格
     *
     * @param: checkTempId
     * @return:
     */
    @RequestMapping("delCheckTempList")
    @ResponseBody
    public AjaxResponseData delCheckList(String checkTempId) {
        AjaxResponseData retVal = new AjaxResponseData();
        formTemplateMgrBusiness.delCheckList(checkTempId);
        retVal.setSuccess(true);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 获取表头
     *
     * @param: checkTempId
     * @param: Inst
     * @return:
     */
    @RequestMapping("getTemplateHeadersById")
    @ResponseBody
    public AjaxResponseData<List<String>> getTemplateHeadersById(String checkTempId, boolean isInst) {
        AjaxResponseData retVal = new AjaxResponseData();
        List<String> checkList = formTemplateMgrBusiness.getTemplateHeadersById(checkTempId, isInst);
        retVal.setResults(checkList);
        return retVal;
    }

    /**
     * 获取单元格内容
     *
     * @param: checkTempId
     * @param: isInst
     * @param: withData
     * @return:
     */
    @RequestMapping("getTemplateCellData")
    @ResponseBody
    public ExtGridData<T> getCellContent(String checkTempId, boolean isInst, boolean withData, String productId) {
//        AjaxResponseData retVal = new AjaxResponseData();
        List<Map<String, String>> cellList;
        if (StringUtil.isNotEmpty(productId)) {
            cellList= formTemplateMgrBusiness.getCellContent(checkTempId, isInst, withData,productId);
        }else {
            cellList= formTemplateMgrBusiness.getCellContent(checkTempId, isInst, withData,"");
        }

//        retVal.setResults(cellList);
        return new ExtGridData(cellList,cellList.size());
    }



    /**
     * 获取单元格Id以及根据单元格ID判断是否存在图片
     * @param checkTempId
     * @param modelId
     * @return
     */
    @RequestMapping("getTemplateCellIds")
    @ResponseBody
    public AjaxResponseData<Map> getTemplateCellIds(String checkTempId,String modelId){
        AjaxResponseData retVal=new AjaxResponseData();
       Map cellIdsMap= formTemplateMgrBusiness.getTemplateCellIds(checkTempId,modelId);
        retVal.setSuccess(true);
        retVal.setResults(cellIdsMap);
        return retVal;
    }

    /**
     * 单个导出检查表模板
     *
     * @param: checkTempId
     * @param: checkName
     * @return:
     */
    @RequestMapping("exportCheckTempFromOracle")
    @ResponseBody
    public AjaxResponseData<String> exportCheckTempFromOracle(String checkTempId, String checkName) {
        AjaxResponseData retVal = new AjaxResponseData();
        String fileName = formTemplateMgrBusiness.exportCheckTempFromOracle(checkTempId, checkName,false,"");
        retVal.setResults(fileName);
        return retVal;
    }

    /**
     * 根据检查表类型批量下载对应的检查表模板
     * @param checkTypeId
     * @param checkTypeName
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("exportAllCheckTempByCheckTypeId")
    @ResponseBody
    public void exportAllCheckTempByCheckTypeId(String checkTypeId, String checkTypeName, HttpServletRequest request, HttpServletResponse response)throws Exception{
          formTemplateMgrBusiness.exportAllCheckTempByCheckTypeId(checkTypeId,checkTypeName,request,response);
    }


    /**
     * 删除检查类型
     *
     * @param: checkTypeId
     * @return:
     */
    @RequestMapping("delCheckTypeById")
    @ResponseBody
    public AjaxResponseData delCheckTypeById(String checkTypeId) {
        AjaxResponseData retVal = new AjaxResponseData();
        formTemplateMgrBusiness.delCheckTypeById(checkTypeId);
        retVal.setSuccess(true);
        retVal.setMsg("删除成功");
        return retVal;
    }

    /**
     * 修改检查类型数据
     *
     * @param: modelId
     * @param: checkTypeId
     * @param: formData
     * @return:
     */
    @RequestMapping("updateCheckTypeData")
    @ResponseBody
    public AjaxResponseData updateCheckTypeData(String modelId, String checkTypeId, String formData) {
        return formTemplateMgrBusiness.updateCheckTypeData(modelId, checkTypeId, formData);
    }

    @RequestMapping("getProductTreeNodes")
    @ResponseBody
    public AjaxResponseData<List<FormProductTreeNode>> getProductTreeNodes(String id, String type, String level, String version, String checkTableInstId, String rowNumber, String checkTempId, boolean isInst){
        return formTemplateMgrBusiness.getProductTreeNodes(id, type, level,version,checkTableInstId,rowNumber,checkTempId,isInst);
    }

    /**
     * 保存选择的产品结构树节点
     * @param treeId
     * @param rowNumber
     * @param checkTempId
     * @return
     */
    @RequestMapping("saveChooseProductTree")
    @ResponseBody
    public AjaxResponseData  saveChooseProductTree(String treeId,String rowNumber,String checkTempId,boolean isInst){
        return  formTemplateMgrBusiness.saveChooseProductTree(treeId, rowNumber, checkTempId,isInst);
    }

    /**
     * 获取已选的节点内容
     * @param rowNumber
     * @param checkTempId
     * @return
     */
//    @RequestMapping("getChooseProductTree")
//    @ResponseBody
//    public AjaxResponseData  getChooseProductTree(String rowNumber,String checkTempId,boolean isInst){
//        return  formTemplateMgrBusiness.getChooseProductTree(rowNumber,checkTempId,isInst);
//    }

    /**
     * 获取已经选择的产品结构节点
     * @param checkTempId
     * @param isInst
     * @return
     */
    @RequestMapping("getChooseProductNode")
    @ResponseBody
    public AjaxResponseData<Map> getChooseProductNode(String checkTempId,boolean isInst){
        return formTemplateMgrBusiness.getChooseProductNode(checkTempId,isInst);
    }

    /**
     * 修改检查表模板
     * @param modelId
     * @param checkTempId
     * @param formData
     * @return
     */
    @RequestMapping("updateCheckTemp")
    @ResponseBody
    public AjaxResponseData updateCheckTemp(String modelId, String checkTempId, String formData) {
        return formTemplateMgrBusiness.updateCheckTemp(modelId, checkTempId, formData);
    }

    @RequestMapping("saveCheckEndData")
    @ResponseBody
    public AjaxResponseData<String> saveCheckEndData(String modelId, String formData) {
        IBusinessModel checkCellBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("表单内容为空");
            retVal.setSuccess(false);
            return retVal;
        } else {
            Map formDataMap = JsonUtil.json2Map(formData);
            Map dataMap = (Map) formDataMap.get("fields");
            String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;
            SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
            eventParam.setModelId(modelId);
            eventParam.setDataMap(dataMap);
            eventParam.setCreateData(true);
            OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
            retVal.setMsg("保存成功");
            return retVal;
        }
    }

    public List<String> getHeaders(TableEntity entity) throws Exception {
        List<String> headers = new ArrayList<>();
        List<DataEntity> dataEntityList = entity.getDataEntityList();
        List<FieldEntity> fieldEntityList = dataEntityList.get(0).getFieldEntityList();
        for(FieldEntity fieldEntity:fieldEntityList){
            if(!"ID".equals(fieldEntity.getName()))
                headers.add(fieldEntity.getName());
        }
        return headers;
    }

    /**
     * 获取历史检查表实例表头
     * @param cellId
     * @param
     * @return
     */
    @RequestMapping("getHistoryCheckInstHead")
    @ResponseBody
    public AjaxResponseData<FlowPostData> getHistoryCheckInstHead(String cellId) {
        return formTemplateMgrBusiness.getHistoryCheckInstHead(cellId);
    }

    /**
     * 获取历史检查实例内容
     * @param page
     * @param limit
     * @param cellId
     * @param columnLength
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @RequestMapping("getHistoryCheckInstContent")
    @ResponseBody
    public ExtGridData getHistoryCheckInstContent(Integer page, Integer limit, String cellId,Integer columnLength, String checkTempName,@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws Exception{
        ExtGridData str = formTemplateMgrBusiness.getHistoryCheckInstContent(page, limit, cellId,columnLength,checkTempName,startDate,endDate);
        return str;
    }

    /**
     * 导出历史检查项数据
     * @param exportAll
     * @param filters
     * @param cellId
     * @param columns
     * @param checkTempName
     * @param response
     */
    @RequestMapping("exportHistoryCheckItemData")
    @ResponseBody
    public void exportHistoryCheckItemData(boolean exportAll, String filters,String cellId,String columns,String checkTempName,HttpServletResponse response) {
        String filePath = formTemplateMgrBusiness.exportHistoryCheckItemData(exportAll, filters,cellId,columns,checkTempName);
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

    /**
     * 获取历史检查表模板树
     * @param node
     * @return
     */
    @RequestMapping("getHistoryFormTemplateTreeByPid")
    @ResponseBody
    public AjaxResponseData<List<FuncBean>> getHistoryFormTemplateTreeByPid(String node) {
        return new AjaxResponseData<>(formTemplateMgrBusiness.getHistoryFormTemplateTreeByPid(node, null, PropertyConstant.WEI_BAO_SCHEMA_ID));
    }

    /**
     * 保存检查表格头尾数据
     * @param modelId
     * @param formData
     * @return
     */
    @RequestMapping("saveCheckHeaderOrEndData")
    @ResponseBody
    public AjaxResponseData<String> saveCheckHeaderOrEndData(String modelId, String formData) {
         return formTemplateMgrBusiness.saveCheckHeaderOrEndData(modelId,formData);
    }

}
