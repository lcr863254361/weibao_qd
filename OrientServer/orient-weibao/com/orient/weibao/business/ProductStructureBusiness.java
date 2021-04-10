package com.orient.weibao.business;

import com.google.common.base.Joiner;
import com.google.inject.internal.Join;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.edm.init.FileServerConfig;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.business.ModelDataBusiness;
import com.orient.modeldata.controller.ModelDataController;
import com.orient.modeldata.event.SaveModelDataEvent;
import com.orient.modeldata.eventParam.SaveModelDataEventParam;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.utils.*;
import com.orient.utils.ExcelUtil.Excel;
import com.orient.utils.ExcelUtil.reader.DataEntity;
import com.orient.utils.ExcelUtil.reader.FieldEntity;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.ExcelUtil.style.Align;
import com.orient.utils.ExcelUtil.style.BorderStyle;
import com.orient.utils.ExcelUtil.style.Color;
import com.orient.web.base.*;
import com.orient.web.springmvcsupport.exception.DSException;
import com.orient.web.util.UserContextUtil;
import com.orient.weibao.bean.*;
import com.orient.weibao.bean.HistoryTask.ImportHangduanBean;
import com.orient.weibao.bean.productStructBean.ProductStruct;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.enums.ProductStructureNodeType;
import com.orient.weibao.mbg.model.ProductStructure;
import com.orient.weibao.utils.ExcelImport;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-01-12 16:05
 */
@Service
public class ProductStructureBusiness extends BaseBusiness {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ModelDataBusiness modelDataBusiness;
    @Autowired
    FileServerConfig fileServerConfig;
    @Autowired
    ExcelImport excelImport;

    String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    public AjaxResponseData<List<ProductStructureTreeNode>> getProductTreeNodes(String id, String type, String level, String version) {
        List<ProductStructureTreeNode> retVal = new ArrayList<>();
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        Boolean belongVersion = Boolean.valueOf(version);

        switch (type) {
            case ProductStructureNodeType.TYPE_ROOT:

                productBM.setReserve_filter("AND C_PID_" + productBM.getId() + " = '" + id + "'");
                List<Map> productList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();
                if (productList.size() > 0) {
                    for (Map map : productList) {
                        ProductStructureTreeNode pstn = new ProductStructureTreeNode();
                        pstn.setText(CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId())));
                        String productName = CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId()));

                        if (productName.equals("母船") || productName.equals("其它")) {
                            //belongVersion判断
                            if (belongVersion == false) {
                                continue;
                            }
                        }
                        String productId = CommonTools.Obj2String(map.get("ID"));
                        pstn.setId(productId);
                        pstn.setDataId(productId);
                        pstn.setExpanded(false);
                        pstn.setType(ProductStructureNodeType.TYPE_ROOT);
                        pstn.setIcon("app/images/function/数据建模.png");
                        pstn.setIconCls("icon-function");
                        pstn.setpId(id);
                        int levell = Integer.parseInt(level);
                        pstn.setLevel(++levell);
                        pstn.setQtip(CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId())));

                        productBM.clearAllFilter();
                        productBM.setReserve_filter("AND C_PID_" + productBM.getId() + " = '" + productId + "'");
                        List<Map> subList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();
                        if (subList.size() == 0) {
                            pstn.setLeaf(true);
                        }
                        List<ProductStructureTreeNode> childsList = getChildrenNode(subList, String.valueOf(levell));
                        pstn.setResults(childsList);
                        retVal.add(pstn);
                    }
                }
                break;
            default:
                break;
        }
        return new AjaxResponseData<>(retVal);
    }

//        List<ProductStructureTreeNode> retVal = new ArrayList<>();
//        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
//        Boolean belongVersion = Boolean.valueOf(version);
//
//        switch (type) {
//            case ProductStructureNodeType.TYPE_ROOT:
//
//                productBM.setReserve_filter("AND C_PID_" + productBM.getId() + " = '" + id + "'");
//                List<Map> productList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();
//                if (productList.size() > 0) {
//                    for (Map map : productList) {
//                        ProductStructureTreeNode pstn = new ProductStructureTreeNode();
//                        pstn.setText(CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId())));
//                        String productName = CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId()));
//
//                        if (productName.equals("母船") || productName.equals("其它")) {
//                            //belongVersion判断
//                            if (belongVersion == false) {
//                                continue;
//                            }
//                        }
//                        String productId = CommonTools.Obj2String(map.get("ID"));
//                        pstn.setId(productId);
//                        pstn.setDataId(productId);
//                        pstn.setExpanded(false);
//                        pstn.setType(ProductStructureNodeType.TYPE_ROOT);
//                        pstn.setIcon("app/images/function/数据建模.png");
//                        pstn.setIconCls("icon-function");
//                        int levell = Integer.parseInt(level);
//                        pstn.setLevel(++levell);
//                        pstn.setQtip(CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId())));
//                        productBM.setReserve_filter("AND C_PID_" + productBM.getId() + " = '" + productId + "'");
//                        if (orientSqlEngine.getBmService().createModelQuery(productBM).list().size() == 0) {
//                            pstn.setLeaf(true);
//                        }
//                        retVal.add(pstn);
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//        return new AjaxResponseData<>(retVal);
//    }


    public List<ProductStructureTreeNode> getChildrenNode(List<Map> subList, String level) {
        List<ProductStructureTreeNode> lists = new ArrayList<>();
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        if (subList != null && subList.size() > 0) {
            for (Map subMap : subList) {
                ProductStructureTreeNode pstn = new ProductStructureTreeNode();
                String productId = CommonTools.Obj2String(subMap.get("ID"));
                pstn.setText(CommonTools.Obj2String(subMap.get("C_NAME_" + productBM.getId())));
                pstn.setId(productId);
                pstn.setDataId(productId);
                pstn.setExpanded(false);
                pstn.setType(ProductStructureNodeType.TYPE_ROOT);
                pstn.setIcon("app/images/function/数据建模.png");
                pstn.setIconCls("icon-function");
                int levell = Integer.parseInt(level);
                pstn.setLevel(++levell);
                pstn.setQtip(CommonTools.Obj2String(subMap.get("C_NAME_" + productBM.getId())));
                productBM.clearAllFilter();
                productBM.setReserve_filter("AND C_PID_" + productBM.getId() + " = '" + productId + "'");
                List<Map> childsList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();
                if (childsList.size() == 0) {
                    pstn.setLeaf(true);
                }
                pstn.setResults(getChildrenNode(childsList, String.valueOf(levell)));
                lists.add(pstn);
            }
        }
        return lists;
    }
//    public List<ProductStructureTreeNode> getSubTreeNodes(List<ProductStructureTreeNode> retVal,ProductStructureTreeNode shys,ProductStructureTreeNode ztfx,String numberId,String level){
//        List<ProductStructureTreeNode> list=retVal;
//        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
//        productBM.clearAllFilter();
//        productBM.setReserve_filter("AND C_PID_" + productBM.getId() + " = '" + numberId + "'");
//        List<Map> mapList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();
//        String productName="";
//        String subFlowId="";
//        String productId="";
//        int levell;
//        List<ProductStructureTreeNode> smzc = new ArrayList<>();
//        for (Map map:mapList){
//            ProductStructureTreeNode smzc = new ProductStructureTreeNode();
//            smzc.setText(CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId())));
//            productName=CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId()));
//            subFlowId = CommonTools.Obj2String(map.get("C_NUMBER_" + productBM.getId()));
//            productId = CommonTools.Obj2String(map.get("ID"));
//            smzc.setId(productId);
//            pnt.setDataId(subFlowId);
//            pnt.setExpanded(false);
//            pnt.setType(ProductStructureNodeType.TYPE_ROOT);
//            pnt.setIcon("app/images/function/数据建模.png");
//            pnt.setIconCls("icon-function");
//            levell = Integer.parseInt(level);
//            pnt.setLevel(++levell);
//            pnt.setQtip(CommonTools.Obj2String(map.get("C_NAME_" + productBM.getId())));
//            productBM.clearAllFilter();
//            productBM.setReserve_filter(" AND C_PID_" + productBM.getId() + "='" + subFlowId + "'");
//            if (orientSqlEngine.getBmService().createModelQuery(productBM).list().size() == 0) {
//                pstn.setLeaf(true);
//            }
//
//            resultTree.add(pnt);
//            pnt.setTreeNodeList(resultTree);
//            ret.setTreeNodeList();
//            list.add(pstn);
//
//            getSubTreeNodes(list,subFlowId,String.valueOf(levell));
//        }
//
//        return list;
//    }


    public Map<String, Object> importProductTree(TableEntity excelEntity) {

        Map<String, Object> retVal = UtilFactory.newHashMap();

        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);

        List<ImportProductTreeBean> importProductTreeBeanList = UtilFactory.newArrayList();

        productBM.setReserve_filter("and c_pid_" + productBM.getId() + "=-1");
        List<Map> productTreeList = orientSqlEngine.getBmService().createModelQuery(productBM).list();
        if (productTreeList != null && productTreeList.size() > 0) {
            List<DataEntity> dataEntities = excelEntity.getDataEntityList();
            for (DataEntity dataEntity : dataEntities) {
                String rowValue = dataEntity.getPkVal();
                if (rowValue == null) {
                    continue;
                }
                List<FieldEntity> fieldEntities = dataEntity.getFieldEntityList();
                ImportProductTreeBean importProductTreeBean = new ImportProductTreeBean();
                String productValue = "";
                if (fieldEntities != null) {
                    for (int i = 0; i < fieldEntities.size(); i++) {
                        FieldEntity fieldEntity = fieldEntities.get(i);
                        if (fieldEntity.getIsKey() == 1) {
                            continue;
                        }
                        String headerName = fieldEntities.get(i).getName();
                        productValue = fieldEntities.get(i).getValue();
                        if ("分系统".equals(headerName)) {
                            if (productValue == null || "".equals(productValue)) {
                                retVal.put("success", false);
                                retVal.put("msg", "第" + (Integer.parseInt(rowValue) + 1) + "行,分系统不可为空，请修正后导入！");
//                                throw new DSException("请检查","第"+(Integer.parseInt(rowValue)+1)+"行,分系统不可为空，请修正后导入！");
                                return retVal;
                            }
                            importProductTreeBean.setColI(productValue);
                        } else if ("部件".equals(headerName)) {
                            importProductTreeBean.setColII(productValue);
                        } else if ("零件".equals(headerName)) {
                            importProductTreeBean.setColIII(productValue);
                        } else {
                            retVal.put("success", false);
                            retVal.put("msg", "导入失败,表头格式只能为分系统、部件、零件！");
                            return retVal;
                        }
                    }
                    if (!"".equals(importProductTreeBean.getColI())) {
                        importProductTreeBeanList.add(importProductTreeBean);
                    }
                }
            }
            if (importProductTreeBeanList.size() > 0) {
                for (ImportProductTreeBean productTreeBean : importProductTreeBeanList) {
                    String colI = productTreeBean.getColI();
                    String colII = productTreeBean.getColII();
                    String colIII = productTreeBean.getColIII();
                    productBM.clearAllFilter();
                    productBM.setReserve_filter("AND C_NAME_" + productBM.getId() + "='" + colI + "'");
                    List<Map> productList = orientSqlEngine.getBmService().createModelQuery(productBM).list();
                    if (productList.size() != 0) {
                        String productId = (String) productList.get(0).get("ID");
                        //插入第二列
                        if (colII != null && !"".equals(colII)) {
                            String colIIid = insertProductNode(productId, colII, false);
                            if (colIII != null && !"".equals(colIII)) {
                                //插入第三列
                                insertProductNode(colIIid, colIII, true);
                            }
                        }
                    } else {
                        Map colIMap = UtilFactory.newHashMap();
                        String shysProductId = "";
                        for (Map productMap : productTreeList) {
                            String productName = CommonTools.Obj2String(productMap.get("C_NAME_" + productBM.getId()));
                            //获取固化的深海勇士Id
                            shysProductId = (String) productMap.get("ID");
                            if (productName.equals("母船") || productName.equals("其它")) {
                                continue;
                            }
                        }
                        //插入第一列
                        colIMap.put("C_NAME_" + productBM.getId(), colI);
                        colIMap.put("C_PID_" + productBM.getId(), shysProductId);
                        String colIid = orientSqlEngine.getBmService().insertModelData(productBM, colIMap);
                        //插入第二列
                        if (colII != null && !"".equals(colII)) {
                            String colIIid = insertProductNode(colIid, colII, false);
                            //插入第三列
                            if (colIII != null && !"".equals(colIII)) {
                                insertProductNode(colIIid, colIII, true);
                            }
                        }
                    }
//                else{
//                    if (productList.size()!=0){
//                        continue;
//                    }else{
//                        Map colIMap=UtilFactory.newHashMap();
//                        //只有第一列,比如母船、其它节点
//                        colIMap.put("C_NAME_"+productBM.getId(),colI);
//                        colIMap.put("C_PID_"+productBM.getId(),"-1");
//                        orientSqlEngine.getBmService().insertModelData(productBM,colIMap);
//                    }
//                }
                }
            }
        }
        retVal.put("success", true);
        retVal.put("msg", "导入成功！");
        return retVal;
    }

    public String insertProductNode(String productId, String name, boolean isPartNode) {
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        productBM.clearAllFilter();
        productBM.setReserve_filter("AND C_NAME_" + productBM.getId() + "='" + name + "'" +
                " AND C_PID_" + productBM.getId() + "='" + productId + "'");
        List<Map> productNodeList = orientSqlEngine.getBmService().createModelQuery(productBM).list();
        String id;
        if (productNodeList.size() > 0) {
            id = (String) productNodeList.get(0).get("ID");
            if (isPartNode) {
                Map productMap = productNodeList.get(0);
                productMap.put("C_TYPE_" + productBM.getId(), "part");
                orientSqlEngine.getBmService().updateModelData(productBM, productMap, id);
            }
        } else {
            Map colIIMap = new HashMap<>();
            if (isPartNode) {
                colIIMap.put("C_TYPE_" + productBM.getId(), "part");
            }
            colIIMap.put("C_NAME_" + productBM.getId(), name);
            colIIMap.put("C_PID_" + productBM.getId(), productId);
            id = orientSqlEngine.getBmService().insertModelData(productBM, colIIMap);
        }
        return id;
    }


    public Map<String, Object> updateProductTree(TableEntity excelEntity) {

        Map<String, Object> retVal = UtilFactory.newHashMap();

        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);

        List<Map> productList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();

        List<DataEntity> dataEntities = excelEntity.getDataEntityList();

        String pid = "";
        String name = "";
        String number = "";
        int i = 0;

        if (productList.size() > 0 && dataEntities.size() >= productList.size()) {
            for (DataEntity dataEntity : dataEntities) {
                List<FieldEntity> fieldEntities = dataEntity.getFieldEntityList();
                int fieldNum = fieldEntities.size();
                if (fieldNum > 3) {
                    fieldNum = 3;
                }
                if (productList.size() > 0 && i < productList.size()) {
                    Map productMap = new HashMap<>();
                    switch (fieldNum) {
                        case 3:
                            String pId = fieldEntities.get(3).getValue();
                            if (pId.contains(".")) {
                                String[] PID = pId.split("\\.");
                                pid = pId.split("\\.")[0];
                            }
                            if (StringUtil.isEmpty(pId)) {
                                retVal.put("success", false);
                                retVal.put("msg", "父PID不能为空，请修正后导入!");
                                return retVal;
                            }
                            if (!CommonTools.Obj2String(productList.get(i).get("C_PID_" + productBM.getId())).equals(pid)) {
                                productMap.put("C_PID_" + productBM.getId(), pid);
                            }
                        case 2:
                            name = fieldEntities.get(2).getValue();
                            if (StringUtil.isEmpty(name)) {
                                retVal.put("success", false);
                                retVal.put("msg", "名称不能为空，请修正后导入!");
                                return retVal;
                            }
                            if (!CommonTools.Obj2String(productList.get(i).get("C_NAME_" + productBM.getId())).equals(name)) {
                                productMap.put("C_NAME_" + productBM.getId(), name);
                            }
                        case 1:
                            String cnumber = fieldEntities.get(1).getValue();
                            if (cnumber.contains(".")) {
                                number = cnumber.split("\\.")[0];
                            }
                            if (StringUtil.isEmpty(cnumber)) {
                                retVal.put("success", false);
                                retVal.put("msg", "编号不能为空，请修正后导入!");
                                return retVal;
                            }
                            if (!CommonTools.Obj2String(productList.get(i).get("C_NUMBER_" + productBM.getId())).equals(number)) {
                                productMap.put("C_NUMBER_" + productBM.getId(), name);
                            }
                            String Id = fieldEntities.get(0).getValue();
                            String ID = CommonTools.Obj2String(productList.get(i).get("ID"));
                            if (CommonTools.Obj2String(productList.get(i).get("C_NUMBER_" + productBM.getId())).equals(number)) {
                                orientSqlEngine.getBmService().updateModelData(productBM, productMap, ID);
                            } else {
                                orientSqlEngine.getBmService().insertModelData(productBM, productMap);
                            }
                            break;
                        default:
                            retVal.put("success", false);
                            retVal.put("msg", "编号、名称、父PID不能为空，请修正后导入！");
                            return retVal;
                    }
                    i++;
                } else {
                    switch (fieldNum) {
                        case 3:
                            String pId = fieldEntities.get(3).getValue();
                            if (pId.contains(".")) {
                                String[] PID = pId.split("\\.");
                                pid = pId.split("\\.")[0];
                            }
                            if (StringUtil.isEmpty(pId)) {
                                retVal.put("success", false);
                                retVal.put("msg", "父PID不能为空，请修正后导入!");
                                return retVal;
                            }
                        case 2:
                            name = fieldEntities.get(2).getValue();
                            if (StringUtil.isEmpty(name)) {
                                retVal.put("success", false);
                                retVal.put("msg", "名称不能为空，请修正后导入!");
                                return retVal;
                            }
                        case 1:
                            String cnumber = fieldEntities.get(1).getValue();
                            if (cnumber.contains(".")) {
                                number = cnumber.split("\\.")[0];
                            }
                            if (StringUtil.isEmpty(cnumber)) {
                                retVal.put("success", false);
                                retVal.put("msg", "编号不能为空，请修正后导入!");
                                return retVal;
                            }

                            Map map = new HashMap<>();
                            map.put("C_PID_" + productBM.getId(), pid);
                            map.put("C_NAME_" + productBM.getId(), name);
                            map.put("C_NUMBER_" + productBM.getId(), number);

                            orientSqlEngine.getBmService().insertModelData(productBM, map);

                            break;
                        default:
                            retVal.put("success", false);
                            retVal.put("msg", "编号、名称、父PID不能为空，请修正后导入！");
                            return retVal;
                    }
                }
            }
        } else {
            int m = 0;
            for (int j = 0; j < productList.size(); j++) {
                if (dataEntities.size() > 0 && j < dataEntities.size()) {

                    List<FieldEntity> fieldEntities = dataEntities.get(m).getFieldEntityList();
                    int fieldNum = fieldEntities.size();
                    if (fieldNum > 3) {
                        fieldNum = 3;
                    }
                    Map productMap = new HashMap<>();
                    switch (fieldNum) {
                        case 3:
                            String pId = fieldEntities.get(3).getValue();
                            if (pId.contains(".")) {
                                String[] PID = pId.split("\\.");
                                pid = pId.split("\\.")[0];
                            }
                            if (StringUtil.isEmpty(pId)) {
                                retVal.put("success", false);
                                retVal.put("msg", "父PID不能为空，请修正后导入!");
                                return retVal;
                            }
                            if (!CommonTools.Obj2String(productList.get(j).get("C_PID_" + productBM.getId())).equals(pid)) {
                                productMap.put("C_PID_" + productBM.getId(), pid);
                            }
                        case 2:
                            name = fieldEntities.get(2).getValue();
                            if (StringUtil.isEmpty(name)) {
                                retVal.put("success", false);
                                retVal.put("msg", "名称不能为空，请修正后导入!");
                                return retVal;
                            }
                            if (!CommonTools.Obj2String(productList.get(j).get("C_NAME_" + productBM.getId())).equals(name)) {
                                productMap.put("C_NAME_" + productBM.getId(), name);
                            }
                        case 1:
                            String cnumber = fieldEntities.get(1).getValue();
                            if (cnumber.contains(".")) {
                                number = cnumber.split("\\.")[0];
                            }
                            if (StringUtil.isEmpty(cnumber)) {
                                retVal.put("success", false);
                                retVal.put("msg", "编号不能为空，请修正后导入!");
                                return retVal;
                            }
                            if (!CommonTools.Obj2String(productList.get(j).get("C_NUMBER_" + productBM.getId())).equals(number)) {
                                productMap.put("C_NUMBER_" + productBM.getId(), name);
                            }
                            String Id = fieldEntities.get(0).getValue();
                            String ID = CommonTools.Obj2String(productList.get(j).get("ID"));
                            if (CommonTools.Obj2String(productList.get(j).get("C_NUMBER_" + productBM.getId())).equals(number)) {
                                orientSqlEngine.getBmService().updateModelData(productBM, productMap, ID);
                            } else {
                                orientSqlEngine.getBmService().insertModelData(productBM, productMap);
                            }
                            break;
                        default:
                            retVal.put("success", false);
                            retVal.put("msg", "编号、名称、父PID不能为空，请修正后导入！");
                            return retVal;
                    }
                    m++;
                } else {
                    String productId = CommonTools.Obj2String(productList.get(j).get("ID"));
                    orientSqlEngine.getBmService().delete(productBM, productId);
                }
            }
        }
        retVal.put("success", true);
        retVal.put("msg", "更新成功！");
        return retVal;
    }

    public CommonResponseData saveDeviceInstData(String productId, String newDeviceInstId) {
        CommonResponseData retVal = new CommonResponseData();
        IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel oldDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.OLD_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);

        deviceInstBM.setReserve_filter(" AND C_PRODUCT_ID_" + deviceInstBM.getId() + "='" + productId + "'");
        List<Map> deviceInstList = orientSqlEngine.getBmService().createModelQuery(deviceInstBM).list();
        if (deviceInstList.size() > 0) {
            for (Map deviceInstMap : deviceInstList) {
                String deviceInstId = (String) deviceInstMap.get("ID");
                if (newDeviceInstId.contains(deviceInstId)) {
                    newDeviceInstId = newDeviceInstId.replace(deviceInstId, "");
                    continue;
                } else {
                    deviceInstMap.put("C_PRODUCT_ID_" + deviceInstBM.getId(), "");
                    String version = (String) deviceInstMap.get("C_VERSION_" + deviceInstBM.getId());
                    version = String.valueOf(Integer.parseInt(version) + 1);
                    deviceInstMap.put("C_VERSION_" + deviceInstBM.getId(), version);
                    orientSqlEngine.getBmService().updateModelData(deviceInstBM, deviceInstMap, deviceInstId);

                    //修改设备实例版本，也要修改设备版本
                    String deviceId = CommonTools.Obj2String(deviceInstMap.get("T_SPARE_PARTS_" + schemaId + "_ID"));
                    deviceBM.clearAllFilter();
                    deviceBM.setReserve_filter("AND ID='" + deviceId + "'");
                    List<Map<String, Object>> spareList = orientSqlEngine.getBmService().createModelQuery(deviceBM).list();
                    Map spareMap = spareList.get(0);
                    String deviceVersion = CommonTools.Obj2String(spareMap.get("C_VERSION_" + deviceBM.getId()));
                    deviceVersion = String.valueOf(Integer.parseInt(deviceVersion) + 1);
                    spareMap.put("C_VERSION_" + deviceBM.getId(), deviceVersion);
                    orientSqlEngine.getBmService().updateModelData(deviceBM, spareMap, deviceId);

                    //之前的旧设备插入到历史记录中
                    oldDeviceBM.setReserve_filter("AND C_OLD_DEVICE_ID_" + oldDeviceBM.getId() + "='" + deviceInstId + "'" +
                            " AND T_PRODUCT_STRUCTURE_" + schemaId + "_ID='" + productId + "'");
                    List<Map> oldDeviceList = orientSqlEngine.getBmService().createModelQuery(oldDeviceBM).list();
                    if (oldDeviceList.size() == 0) {
                        Map oldDeviceMap = UtilFactory.newHashMap();
                        oldDeviceMap.put("C_OLD_DEVICE_ID_" + oldDeviceBM.getId(), deviceInstId);
                        oldDeviceMap.put("T_PRODUCT_STRUCTURE_" + schemaId + "_ID", productId);
                        orientSqlEngine.getBmService().insertModelData(oldDeviceBM, oldDeviceMap);
                    }
                    //插入旧设备寿命周期
                    DeviceLife(deviceInstId, true);
                }
            }
        }
        //多选的设备插入到设备实例中
        String newDeviceInstIds[] = newDeviceInstId.split(",");
        if (newDeviceInstIds.length > 0) {
            for (String deviceInstId : newDeviceInstIds) {
                if (deviceInstId != null && !"".equals(deviceInstId)) {
                    deviceInstBM.setReserve_filter(" AND ID='" + deviceInstId + "'");
                    deviceInstList = orientSqlEngine.getBmService().createModelQuery(deviceInstBM).list();
                    if (deviceInstList.size() > 0) {
                        Set<String> deviceSet = new HashSet<>();
                        for (Map deviceMap : deviceInstList) {
                            String deviceId = CommonTools.Obj2String(deviceMap.get("T_SPARE_PARTS_" + schemaId + "_ID"));
                            deviceSet.add(deviceId);
                            deviceMap.put("C_PRODUCT_ID_" + deviceInstBM.getId(), productId);
                            String version = (String) deviceMap.get("C_VERSION_" + deviceInstBM.getId());
                            version = String.valueOf(Integer.parseInt(version) + 1);
                            deviceMap.put("C_VERSION_" + deviceInstBM.getId(), version);
                            orientSqlEngine.getBmService().updateModelData(deviceInstBM, deviceMap, deviceInstId);
                        }
                        //插入新设备寿命周期
                        DeviceLife(deviceInstId, false);
                        //更改设备状态
                        if (deviceSet.size() > 0) {
                            String deviceIds = Joiner.on(",").join(deviceSet);
                            deviceBM.clearAllFilter();
                            deviceBM.setReserve_filter("AND ID IN (" + deviceIds + ")");
                            List<Map<String, Object>> spareList = orientSqlEngine.getBmService().createModelQuery(deviceBM).list();
                            for (Map spareMap : spareList) {
                                String spareId = CommonTools.Obj2String(spareMap.get("ID"));
                                String deviceVersion = CommonTools.Obj2String(spareMap.get("C_VERSION_" + deviceBM.getId()));
                                deviceVersion = String.valueOf(Integer.parseInt(deviceVersion) + 1);
                                spareMap.put("C_VERSION_" + deviceBM.getId(), deviceVersion);
                                orientSqlEngine.getBmService().updateModelData(deviceBM, spareMap, spareId);
                            }
                        }
                    }
                }
            }
        }
        retVal.setSuccess(true);
        retVal.setMsg("更换成功！");
        return retVal;
    }

    //新旧设备寿命周期
    public void DeviceLife(String deviceId, Boolean isOldorNewDevice) {
        IBusinessModel deviceLifeBM = businessModelService.getBusinessModelBySName(PropertyConstant.DEVICE_LIFE_CYCLE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String updateDeviceDate = sdf.format(date);

        //旧的设备插入到设备实例寿命全周期中
        deviceLifeBM.clearAllFilter();
        deviceLifeBM.setReserve_filter("AND T_SPARE_PARTS_SHILI_" + schemaId + "_ID ='" + deviceId + "'" + " AND C_END_TIME_" + deviceLifeBM.getId() + " IS NULL");

//            List<Map<String, Object>> lifeCycleList = orientSqlEngine.getBmService().createModelQuery(deviceLifeBM).orderDesc("to_date(c_start_time_" + deviceLifeBM.getId() + ",'yyyy-mm-dd')").list();
        List<Map<String, Object>> lifeCycleList = orientSqlEngine.getBmService().createModelQuery(deviceLifeBM).list();
        if (lifeCycleList.size() > 0) {
            for (Map lifeCycleMap : lifeCycleList) {
                //获取最新的结束日期
                String endTime = CommonTools.Obj2String(lifeCycleMap.get("C_END_TIME_" + deviceLifeBM.getId()));
                if (endTime == null || "".equals(endTime)) {
                    String lifeId = CommonTools.Obj2String(lifeCycleMap.get("ID"));
                    lifeCycleMap.put("C_END_TIME_" + deviceLifeBM.getId(), updateDeviceDate);
                    orientSqlEngine.getBmService().updateModelData(deviceLifeBM, lifeCycleMap, lifeId);
                    String deviceInstId = (String) lifeCycleMap.get("T_SPARE_PARTS_SHILI_" + schemaId + "_ID");
                    Map lifeMap = new HashMap<>();
                    lifeMap.put("C_START_TIME_" + deviceLifeBM.getId(), updateDeviceDate);
                    if (isOldorNewDevice) {
                        lifeMap.put("C_DEVICE_STATE_" + deviceLifeBM.getId(), "冷备");
                    } else {
                        lifeMap.put("C_DEVICE_STATE_" + deviceLifeBM.getId(), "在用");
                    }
                    lifeMap.put("T_SPARE_PARTS_SHILI_" + schemaId + "_ID", deviceInstId);
                    SimpleDateFormat editDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    lifeMap.put("C_UPDATE_TIME_" + deviceLifeBM.getId(), editDate.format(new Date()));
                    orientSqlEngine.getBmService().insertModelData(deviceLifeBM, lifeMap);
                }
            }
        }
        //旧的设备的设备实例表中的状态发生变化
        deviceInstBM.clearAllFilter();
        deviceInstBM.setReserve_filter("AND ID IN (" + deviceId + ")");
        List<Map<String, Object>> spareInstList = orientSqlEngine.getBmService().createModelQuery(deviceInstBM).list();
        if (spareInstList.size() > 0) {
            Set<String> deviceSet = new HashSet<>();
            for (Map deviceInstMap : spareInstList) {
                String deviceID = CommonTools.Obj2String(deviceInstMap.get("T_SPARE_PARTS_" + schemaId + "_ID"));
                deviceSet.add(deviceID);
                String spareInstId = (String) deviceInstMap.get("ID");
                String version = (String) deviceInstMap.get("C_VERSION_" + deviceInstBM.getId());
                version = String.valueOf(Integer.parseInt(version) + 1);
                deviceInstMap.put("C_VERSION_" + deviceInstBM.getId(), version);
                if (isOldorNewDevice) {
                    deviceInstMap.put("C_STATE_" + deviceInstBM.getId(), "冷备");
                } else {
                    deviceInstMap.put("C_STATE_" + deviceInstBM.getId(), "在用");
                }
                orientSqlEngine.getBmService().updateModelData(deviceInstBM, deviceInstMap, spareInstId);
            }
            //更改设备状态
            if (deviceSet.size() > 0) {
                String deviceIds = Joiner.on(",").join(deviceSet);
                deviceBM.clearAllFilter();
                deviceBM.setReserve_filter("AND ID IN (" + deviceIds + ")");
                List<Map<String, Object>> spareList = orientSqlEngine.getBmService().createModelQuery(deviceBM).list();
                for (Map spareMap : spareList) {
                    String spareId = CommonTools.Obj2String(spareMap.get("ID"));
                    String deviceVersion = CommonTools.Obj2String(spareMap.get("C_VERSION_" + deviceBM.getId()));
                    deviceVersion = String.valueOf(Integer.parseInt(deviceVersion) + 1);
                    spareMap.put("C_VERSION_" + deviceBM.getId(), deviceVersion);
                    orientSqlEngine.getBmService().updateModelData(deviceBM, spareMap, spareId);
                }
            }
        }
    }


    public Map getCurrentRefDevice(String productId) {
        Map map = new HashMap<>();
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        deviceInstBM.setReserve_filter(" and c_product_id_" + deviceInstBM.getId() + "='" + productId + "'");
        List<Map> deviceInstList = orientSqlEngine.getBmService().createModelQuery(deviceInstBM).list();
//        String deviceInstId = CommonTools.Obj2String(productList.get(0).get("C_DEVICE_INST_ID_" + productBM.getId()));
        String mutiDeviceIds = "";
        String deviceNames = "";
        if (deviceInstList.size() == 0) {
            map.put("select", "未选择");
            map.put("id", "");
        } else {
//            String deviceInstSql = "select c_device_name_" + deviceInstBM.getId() + ",c_serial_number_" + deviceInstBM.getId() + " from T_SPARE_PARTS_SHILI_" + schemaId + " where id IN (" + deviceInstId + ")";
//            List<Map<String, Object>> deviceInstList = jdbcTemplate.queryForList(deviceInstSql);
//            if (deviceInstList.size() == 0) {
//                Map productMap = productList.get(0);
//                productMap.put("C_DEVICE_INST_ID_" + productBM.getId(), "");
//                orientSqlEngine.getBmService().updateModelData(productBM, productMap, productId);
//                rowInstBM.setReserve_filter(" AND C_PRODUCT_ID_" + rowInstBM.getId() + "='" + productId + "'" +
//                        " AND C_DEVICE_INST_ID_" + rowInstBM.getId() + " IN(" + deviceInstId + ")");
//                List<Map<String, Object>> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).list();
//                if (rowInstList.size() > 0) {
//                    for (Map rowInstMap : rowInstList) {
//                        String rowId = (String) rowInstMap.get("ID");
//                        rowInstMap.put("C_DEVICE_INST_ID_" + rowInstBM.getId(), "");
//                        orientSqlEngine.getBmService().updateModelData(rowInstBM, rowInstMap, rowId);
//                    }
//                }
//                map.put("select", "未选择");
//                map.put("id", "");
//            } else {
            for (Map deviceInstMap : deviceInstList) {
                String spareInstId = (String) deviceInstMap.get("ID");
                String deviceId = CommonTools.Obj2String(deviceInstMap.get("c_device_name_" + deviceInstBM.getId()));
                String number = CommonTools.Obj2String(deviceInstMap.get("c_serial_number_" + deviceInstBM.getId()));
                String deviceSql = "select c_device_name_" + deviceBM.getId() + " from T_SPARE_PARTS_" + schemaId + " where id='" + deviceId + "'";
                List<Map<String, Object>> deviceList = jdbcTemplate.queryForList(deviceSql);
                if (deviceList.size() > 0) {
                    String deviceName = CommonTools.Obj2String(deviceList.get(0).get("c_device_name_" + deviceBM.getId()));
                    deviceNames += deviceName + "-" + number + ",";
                    mutiDeviceIds += spareInstId + ",";
                }
            }
            deviceNames = deviceNames.substring(0, deviceNames.length() - 1);
            mutiDeviceIds = mutiDeviceIds.substring(0, mutiDeviceIds.length() - 1);
            map.put("id", mutiDeviceIds);
            map.put("select", deviceNames);
//            }
        }
        return map;
    }

    //初始化Column数组
    public List<Fields> initFields() {
        List<Fields> fieldsList = new ArrayList<>();
        //①设置Column的所有字段
        String[] nameArray = new String[]{"mainContent", "leftContent", "rightContent", "tableName", "tableState", "isException", "devicePosition", "deviceState", "checker", "uploadDate", "checkInstId"};
        String[] desArray = new String[]{"检查项目", "检查内容", "检查结果", "表单名称", "表单状态", "是否异常", "设备位置", "设备状态", "上传人", "记录时间", "详情"};
        for (int i = 0; i < nameArray.length; i++) {
            Fields field = new Fields(nameArray[i], desArray[i]);
            fieldsList.add(field);
        }
        return fieldsList;
    }

    public CellData queryCellDataView(String productId, String deviceInstId, int page, int size) {
        CellData cellData = new CellData();
        IBusinessModel cellIns_Ibm = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel headerIns_Ibm = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_HEADER_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInsData_Ibm = businessModelService.getBusinessModelBySName(PropertyConstant.CELL_INST_DATA, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTmpIns_Ibm = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceSL_Ibm = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowInsData_Ibm = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);

        if (deviceInstId != null && !"".equals(deviceInstId)) {
            rowInsData_Ibm.setReserve_filter(" AND C_PRODUCT_ID_" + rowInsData_Ibm.getId() + " = '" + productId + "'" +
                    " AND C_DEVICE_INST_ID_" + rowInsData_Ibm.getId() + " IN (" + deviceInstId + ")");
            List<Map<String, Object>> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInsData_Ibm).list();
            String rowInstId = "";
            if (rowInstList.size() > 0) {
                for (Map rowMap : rowInstList) {

                    String rowId = (String) rowMap.get("ID");
                    rowInstId += rowId + ",";

                }
                rowInstId = rowInstId.substring(0, rowInstId.length() - 1);

                cellIns_Ibm.setReserve_filter(" AND T_CHECK_ROW_INST_" + schemaId + "_ID IN (" + rowInstId + ")" +
                        " AND C_PRODUCT_ID_" + cellIns_Ibm.getId() + " = '" + productId
                        + "' AND C_CELL_TYPE_" + cellIns_Ibm.getId()
                        + " != '#0' AND C_CELL_TYPE_" + cellIns_Ibm.getId()
                        + " != '#3'");
//                    + " != '#3' AND C_CELL_TYPE_" + cellIns_Ibm.getId() + " != '#4'");
                List<Map<String, Object>> cellList = orientSqlEngine.getBmService().createModelQuery(cellIns_Ibm).orderAsc("TO_NUMBER(t_check_row_inst_" + schemaId + "_ID)").list();
                deviceSL_Ibm.setReserve_filter(" AND ID = '" + deviceInstId + "'");

                List<Fields> fieldsList = initFields();
                List<String[]> dataList = new ArrayList<>();
                List<ViewData> viewDataList = new ArrayList<>();
                if (cellList != null && cellList.size() > 0) {
                    for (int i = 0; i < cellList.size(); i++) {
                        String[] dataArray = new String[11];
                        Map<String, Object> cell = cellList.get(i);
                        //①主内容
                        String mainContent = (String) cell.get("C_CHECK_JOIN_" + cellIns_Ibm.getId());
                        String leftContent = "---";
                        String rightContent = "---";
                        String tableName = "---";
                        String tableState = "---";
                        String isException = "---";
                        String devicePosition = "---";
                        String deviceState = "---";
                        //+2
                        String uploadDate = "---";
                        String checker = "---";

                        String headerInsId = (String) cell.get("T_CHECK_HEADER_INST_" + schemaId + "_ID");
                        String cellInsId = (String) cell.get("ID");
                        String checkTmpInsId = (String) cell.get("T_CHECK_TEMP_INST_" + schemaId + "_ID");
                        //②左侧内容
                        headerIns_Ibm.setReserve_filter(" AND ID = '" + headerInsId + "'");
                        List<Map<String, Object>> headList = orientSqlEngine.getBmService().createModelQuery(headerIns_Ibm).list();
                        if (headList != null && headList.size() > 0) {
                            leftContent = (String) headList.get(0).get("C_NAME_" + headerIns_Ibm.getId());
                            if (leftContent.contains("#")) {
                                leftContent = leftContent.split("\\#")[1];
                            }
                        }
                        //③右侧内容
                        cellInsData_Ibm.setReserve_filter(" AND T_CHECK_CELL_INST_" + schemaId + "_ID" + " = '" + cellInsId + "'");
                        List<Map<String, Object>> cellDataList = orientSqlEngine.getBmService().createModelQuery(cellInsData_Ibm).list();
                        if (cellDataList != null && cellDataList.size() > 0) {
                            rightContent = (String) cellDataList.get(0).get("C_CONTENT_" + cellInsData_Ibm.getId());
                            if ("false".equals(rightContent)) {
                                rightContent = "否";
                            } else if ("true".equals(rightContent)) {
                                rightContent = "是";
                            }
                        }
                        //表单信息④-⑥
                        checkTmpIns_Ibm.setReserve_filter(" AND ID ='" + checkTmpInsId + "'");
                        List<Map<String, Object>> checkTmpInsList = orientSqlEngine.getBmService().createModelQuery(checkTmpIns_Ibm).list();
                        if (checkTmpInsList != null && checkTmpInsList.size() > 0) {
                            tableName = (String) checkTmpInsList.get(0).get("C_NAME_" + checkTmpIns_Ibm.getId());
                            tableState = (String) checkTmpInsList.get(0).get("C_CHECK_STATE_" + checkTmpIns_Ibm.getId());
//                        tableNode = (String) checkTmpInsList.get(0).get("C_NODE_TEXT_" + checkTmpIns_Ibm.getId());
                            isException = (String) checkTmpInsList.get(0).get("C_EXCEPTION_" + checkTmpIns_Ibm.getId());
                            uploadDate = (String) checkTmpInsList.get(0).get("c_check_time_" + checkTmpIns_Ibm.getId());
                            if (!StringUtils.isEmpty(uploadDate)) {
                                uploadDate = uploadDate.split(" ")[0];
                            }
                            String checkPersonId = CommonTools.Obj2String(checkTmpInsList.get(0).get("c_check_person_" + checkTmpIns_Ibm.getId()));
                            List<Map<String, Object>> userList = UtilFactory.newArrayList();
                            if (checkPersonId != null && !"".equals(checkPersonId)) {
                                StringBuilder sql = new StringBuilder();
                                sql.append("select id,all_name from cwm_sys_user where id in(").append(checkPersonId).append(")");
                                userList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                            }
                            if (userList.size() > 0) {
                                checker = CommonTools.Obj2String(userList.get(0).get("all_name"));
                            }
                        }
                        //⑦-⑧
                        List<Map<String, Object>> deviceSLList = orientSqlEngine.getBmService().createModelQuery(deviceSL_Ibm).list();
                        if (deviceSLList != null && deviceSLList.size() > 0) {
                            devicePosition = (String) deviceSLList.get(0).get("C_POSITION_" + deviceSL_Ibm.getId());
                            deviceState = (String) deviceSLList.get(0).get("C_STATE_" + deviceSL_Ibm.getId());
                        }

                        dataArray[0] = mainContent;
                        dataArray[1] = leftContent;
                        dataArray[2] = rightContent;
                        dataArray[3] = tableName;
                        dataArray[4] = tableState;
                        dataArray[5] = isException;
                        dataArray[6] = devicePosition;
                        dataArray[7] = deviceState;
                        dataArray[8] = checker;
                        dataArray[9] = uploadDate;
                        dataArray[10] = checkTmpInsId;
                        ViewData viewData = new ViewData();
                        viewData.setDevicePosition(devicePosition);
                        viewData.setDeviceState(deviceState);
                        viewData.setLeftContent(leftContent);
                        viewData.setMainContent(mainContent);
                        viewData.setRightContent(rightContent);
                        viewData.setTableName(tableName);
//                    viewData.setTableNode(tableNode);
                        viewData.setTableState(tableState);
                        viewData.setIsException(isException);
                        viewData.setChecker(checker);
                        viewData.setUploadDate(uploadDate);
                        viewData.setCheckInstId(checkTmpInsId);
                        viewDataList.add(viewData);
                        dataList.add(dataArray);
                    }
                }
                cellData.setColumn(fieldsList);
                cellData.setNewData(viewDataList);
                if (page == -1) {
                    cellData.setData(dataList);
                } else {
                    cellData.setData(PageUtil.page(dataList, page, size));
                }
                cellData.setMsg("success");
            }
        }
        return cellData;
    }

    public ExtGridData<Map> queryRefCheckInstData(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter, Boolean dataChange, String sort, String productId, String deviceInstId) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
//        if (!StringUtils.isEmpty(customerFilter)) {
//            Map clazzMap = new HashMap();
//            List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customerFilter, clazzMap);
//            customerFilters.forEach(cs -> businessModel.appendCustomerFilter(cs));
//        }
        String rawTableName = PropertyConstant.CHECK_ROW_INST;
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(rawTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        String rowModelId = rowInstBM.getId();

//        if (deviceInstId != null & !"".equals(deviceInstId)) {
//            rowInstBM.clearAllFilter();
//            rowInstBM.setReserve_filter(" AND C_PRODUCT_ID_" + rowModelId + "='" + productId + "'");
//            List<Map<String, Object>> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).orderAsc("TO_NUMBER(ID)").list();
//            if (rowInstList.size()>0) {
//                for (Map rowMap : rowInstList) {
//                    String rowInstId=CommonTools.Obj2String(rowMap.get("ID"));
//                  rowMap.put("C_DEVICE_INST_ID_"+rowInstBM.getId(),deviceInstId);
//                    orientSqlEngine.getBmService().updateModelData(rowInstBM, rowMap, rowInstId);
//                }
//            }
//        }

        String tempInstIds = "";
        if (StringUtil.isNotEmpty(productId) && StringUtil.isNotEmpty(deviceInstId)) {
            rowInstBM.setReserve_filter(" AND C_PRODUCT_ID_" + rowModelId + "='" + productId + "'" +
                    " AND C_DEVICE_INST_ID_" + rowModelId + "='" + deviceInstId + "'" + "");
//            rowInstBM.setReserve_filter(" AND C_PRODUCT_ID_" + rowModelId + "='" + productId + "'"+
//                    " AND C_DEVICE_INST_ID_"+rowModelId+"='"+deviceInstId+"'");
            List<Map<String, Object>> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).orderAsc("TO_NUMBER(ID)").list();
            if (rowInstList.size() > 0) {
                for (Map rowMap : rowInstList) {
                    String tempInstId = CommonTools.Obj2String(rowMap.get("T_CHECK_TEMP_INST_" + schemaId + "_ID"));
                    tempInstIds += tempInstId + ",";
                }
                if (StringUtil.isNotEmpty(tempInstIds)) {
                    tempInstIds = "(" + tempInstIds.substring(0, tempInstIds.length() - 1) + ")";
                    businessModel.setReserve_filter(" AND ID IN " + tempInstIds);
                    long count = orientSqlEngine.getBmService().createModelQuery(businessModel).count();
                    IBusinessModelQuery businessModelQuery = orientSqlEngine.getBmService().createModelQuery(businessModel);
                    if (null != page && null != pagesize) {
                        int start = (page - 1) * pagesize;
                        int end = page * pagesize > count ? (int) count : (page * pagesize);
                        businessModelQuery.page(start, end);
                    }
                    if (!StringUtil.isEmpty(sort)) {
                        List<ExtSorter> sorters = JsonUtil.getJavaCollection(new ExtSorter(), sort);
                        sorters.forEach(loopSort -> {
                            if ("ASC".equals(loopSort.getDirection())) {
                                businessModelQuery.orderAsc(loopSort.getProperty());
                            } else if ("DESC".equals(loopSort.getDirection())) {
                                businessModelQuery.orderDesc(loopSort.getProperty());
                            }
                        });
                    }
                    List<Map> dataList = businessModelQuery.list();
                    for (Map checkInstMap : dataList) {
                        String checkPersonId = CommonTools.Obj2String(checkInstMap.get("C_CHECK_PERSON_" + businessModel.getId()));
                        if (checkPersonId != null && !"".equals(checkPersonId)) {

                            StringBuilder sql = new StringBuilder();

                            sql.append("select id,all_name from cwm_sys_user where id in(").append(checkPersonId).append(")");

                            List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                            if (userList.size() > 0) {
                                String allName = CommonTools.Obj2String(userList.get(0).get("all_name"));
                                checkInstMap.put("C_CHECK_PERSON_" + businessModel.getId(), allName);
                            }
                        }
                    }
                    if (dataChange) {
                        businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
                        modelDataBusiness.customDataChange(businessModel, dataList);
                    }
                    retVal.setResults(dataList);
                    retVal.setTotalProperty(count);
                }
            }
        }
        return retVal;
    }

    public ExtGridData<Map> getHistoryCheckInstData(String orientModelId, String isView, Integer page, Integer pagesize, String customerFilter, Boolean dataChange, String sort, String productId) {
        ExtGridData<Map> retVal = new ExtGridData<>();
        String userId = UserContextUtil.getUserId();
        EnumInter.BusinessModelEnum modelTypeEnum = "1".equals(isView) ? EnumInter.BusinessModelEnum.View : EnumInter.BusinessModelEnum.Table;
        IBusinessModel businessModel = businessModelService.getBusinessModelById(userId, orientModelId, null, modelTypeEnum);
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
//        if (!StringUtils.isEmpty(customerFilter)) {
//            Map clazzMap = new HashMap();
//            List<CustomerFilter> customerFilters = getJavaCollection(new CustomerFilter(), customerFilter, clazzMap);
//            customerFilters.forEach(cs -> businessModel.appendCustomerFilter(cs));
//        }
        String rawTableName = PropertyConstant.CHECK_ROW_INST;
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(rawTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        String rowModelId = rowInstBM.getId();

        String tempInstIds = "";
        if (productId != null & !"".equals(productId)) {
            rowInstBM.setReserve_filter(" AND C_PRODUCT_ID_" + rowModelId + "='" + productId + "'" +
                    " AND C_DEVICE_STATE_" + rowModelId + "='" + "history" + "'");
            List<Map<String, Object>> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).orderAsc("TO_NUMBER(ID)").list();
            if (rowInstList.size() > 0) {
                for (Map rowMap : rowInstList) {
                    String deviceInstId = (String) rowMap.get("C_DEVICE_INST_ID_" + rowInstBM.getId());
                    String deviceInstSql = "select c_device_name_" + deviceInstBM.getId() + ",c_serial_number_" + deviceInstBM.getId() + " from T_SPARE_PARTS_SHILI_" + schemaId + " where id='" + deviceInstId + "'";
                    List<Map<String, Object>> deviceInstList = jdbcTemplate.queryForList(deviceInstSql);
                    if (deviceInstList.size() != 0) {
                        String tempInstId = CommonTools.Obj2String(rowMap.get("T_CHECK_TEMP_INST_" + schemaId + "_ID"));
                        tempInstIds += tempInstId + ",";
                    }
                }
                if (StringUtil.isNotEmpty(tempInstIds)) {
                    tempInstIds = "(" + tempInstIds.substring(0, tempInstIds.length() - 1) + ")";
                    businessModel.setReserve_filter(" AND ID IN " + tempInstIds);
                    long count = orientSqlEngine.getBmService().createModelQuery(businessModel).count();
                    IBusinessModelQuery businessModelQuery = orientSqlEngine.getBmService().createModelQuery(businessModel);
                    if (null != page && null != pagesize) {
                        int start = (page - 1) * pagesize;
                        int end = page * pagesize > count ? (int) count : (page * pagesize);
                        businessModelQuery.page(start, end);
                    }
                    if (!StringUtil.isEmpty(sort)) {
                        List<ExtSorter> sorters = JsonUtil.getJavaCollection(new ExtSorter(), sort);
                        sorters.forEach(loopSort -> {
                            if ("ASC".equals(loopSort.getDirection())) {
                                businessModelQuery.orderAsc(loopSort.getProperty());
                            } else if ("DESC".equals(loopSort.getDirection())) {
                                businessModelQuery.orderDesc(loopSort.getProperty());
                            }
                        });
                    }
                    List<Map> dataList = businessModelQuery.list();

                    if (dataChange) {
                        businessModelService.dataChangeModel(orientSqlEngine, businessModel, dataList, false);
                        modelDataBusiness.customDataChange(businessModel, dataList);
                    }
                    retVal.setResults(dataList);
                    retVal.setTotalProperty(count);
                }
            }
        }
        return retVal;
    }

    public ExtGridData<Map<String, Object>> getHistoryCheckData(String start, String limit, String productId) {
        List instList = new ArrayList<>();

        String rawTableName = PropertyConstant.CHECK_ROW_INST;
        String tempInstTableName = PropertyConstant.CHECK_TEMP_INST;
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(rawTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(tempInstTableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel oldDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.OLD_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        String rowModelId = rowInstBM.getId();

        int limitt = Integer.parseInt(limit);
        int startt = Integer.parseInt(start);

        StringBuilder tempInstIds = new StringBuilder();
//        String tempInstIds = "";
//        String deviceInstIds = "";
        StringBuilder deviceInstIds = new StringBuilder();
        int totalcount = 0;
        if (productId != null && !"".equals(productId)) {
//            Map CurrentDeviceIdMap = getCurrentRefDevice(productId);
            deviceInstBM.setReserve_filter("AND C_PRODUCT_ID_" + deviceInstBM.getId() + "='" + productId + "'");
            List<Map> spareInstList = orientSqlEngine.getBmService().createModelQuery(deviceInstBM).list();
            if (spareInstList.size() > 0) {
                StringBuilder currentDeviceIds = new StringBuilder();
                for (Map deviceInstMap : spareInstList) {
                    String currentDeviceId = (String) deviceInstMap.get("ID");
                    currentDeviceIds.append(currentDeviceId);
                    currentDeviceIds.append(",");
                }
                String currentDeviceId = currentDeviceIds.toString();
                currentDeviceId = currentDeviceId.substring(0, currentDeviceId.length() - 1);
                oldDeviceBM.setReserve_filter("AND C_OLD_DEVICE_ID_" + oldDeviceBM.getId() + " not in (" + currentDeviceId + ")" +
                        " AND T_PRODUCT_STRUCTURE_" + schemaId + "_ID='" + productId + "'");
                List<Map<String, Object>> oldDeviceList = orientSqlEngine.getBmService().createModelQuery(oldDeviceBM).list();
                if (oldDeviceList.size() > 0) {
                    for (Map oldMap : oldDeviceList) {
                        String oldDeviceId = (String) oldMap.get("C_OLD_DEVICE_ID_" + oldDeviceBM.getId());
                        rowInstBM.setReserve_filter(" AND C_PRODUCT_ID_" + rowModelId + "='" + productId + "'" +
                                " AND C_DEVICE_INST_ID_" + rowModelId + "='" + oldDeviceId + "'");
                        List<Map<String, Object>> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).orderAsc("TO_NUMBER(ID)").list();
                        if (rowInstList.size() > 0) {
                            for (Map rowMap : rowInstList) {
                                String tempInstId = CommonTools.Obj2String(rowMap.get("T_CHECK_TEMP_INST_" + schemaId + "_ID"));
                                String deviceInstId = CommonTools.Obj2String(rowMap.get("C_DEVICE_INST_ID_" + rowModelId));
                                String deviceInstSql = "select c_device_name_" + deviceInstBM.getId() + ",c_serial_number_" + deviceInstBM.getId() + " from T_SPARE_PARTS_SHILI_" + schemaId + " where id='" + deviceInstId + "'";
                                List<Map<String, Object>> deviceInstList = jdbcTemplate.queryForList(deviceInstSql);
                                if (deviceInstList.size() != 0) {
                                    tempInstIds.append(tempInstId);
                                    tempInstIds.append(",");
//                                tempInstIds += tempInstId + ",";
//                                deviceInstIds += tempInstId + "+" + deviceInstId + ",";
                                    deviceInstIds.append(tempInstId).append("+").append(deviceInstId).append(",");

                                }
                            }
                        }
                    }
                    String tempInstId = tempInstIds.toString();
                    String deviceInstId = deviceInstIds.toString();
                    if (StringUtil.isNotEmpty(tempInstId)) {
                        tempInstId = "(" + tempInstId.substring(0, tempInstId.length() - 1) + ")";
                        checkTempInstBM.setReserve_filter(" AND ID IN " + tempInstId);
//                    List<Map<String, Object>> checkTempInstList = orientSqlEngine.getBmService().createModelQuery(checkTempInstBM).orderAsc("TO_NUMBER(ID)").list();
                        List<Map<String, Object>> checkTempInstList = orientSqlEngine.getBmService().createModelQuery(checkTempInstBM).orderAsc("to_date(c_check_time_" + checkTempInstBM.getId() + ",'yyyy-mm-dd HH24:mi:ss')").list();
                        totalcount = checkTempInstList.size();
                        int end = startt + limitt;//防止尾页越界
                        if (checkTempInstList.size() < end) {
                            end = checkTempInstList.size();
                        }
                        deviceInstId = deviceInstId.substring(0, deviceInstId.length() - 1);
                        for (int i = startt; i < end; i++) {
                            Map instMap = new HashMap<>();
                            String tempId = CommonTools.Obj2String(checkTempInstList.get(i).get("ID"));
                            String deviceId[] = deviceInstId.split(",");
                            String device = "";
                            for (String str : deviceId) {
                                String templateInstId[] = str.split("\\+");
                                if (tempId.equals(templateInstId[0])) {
                                    device = templateInstId[1];
                                }
                            }
//                        String str = deviceId[i];
                            instMap.put("id", tempId);
                            String tableName = CommonTools.Obj2String(checkTempInstList.get(i).get("c_name_" + checkTempInstBM.getId()));
                            instMap.put("tableName", tableName);
                            String recordTime = CommonTools.Obj2String(checkTempInstList.get(i).get("c_check_time_" + checkTempInstBM.getId()));
                            instMap.put("recordTime", recordTime);
                            String checkPersonId = CommonTools.Obj2String(checkTempInstList.get(i).get("c_check_person_" + checkTempInstBM.getId()));
                            List<Map<String, Object>> userList = UtilFactory.newArrayList();
                            if (checkPersonId != null && !"".equals(checkPersonId)) {
                                StringBuilder sql = new StringBuilder();
                                sql.append("select id,all_name from cwm_sys_user where id in(").append(checkPersonId).append(")");
                                userList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                            }
                            String allName = "";
                            if (userList.size() > 0) {
                                allName = CommonTools.Obj2String(userList.get(0).get("all_name"));
                            }
                            instMap.put("uploader", allName);
                            String taskId = CommonTools.Obj2String(checkTempInstList.get(i).get("T_DIVING_TASK_" + schemaId + "_ID"));
                            divingTaskBM.setReserve_filter("AND ID='" + taskId + "'");
                            List<Map<String, Object>> divingTaskList = orientSqlEngine.getBmService().createModelQuery(divingTaskBM).list();
                            if (divingTaskList != null && divingTaskList.size() > 0) {
                                String taskName = CommonTools.Obj2String(divingTaskList.get(0).get("C_TASK_NAME_" + divingTaskBM.getId()));
                                instMap.put("belongTask", taskName);
                            }
                            String isException = CommonTools.Obj2String(checkTempInstList.get(i).get("c_exception_" + checkTempInstBM.getId()));
                            instMap.put("isException", isException);
                            deviceInstBM.setReserve_filter("and id='" + device + "'");
                            List<Map<String, Object>> deviceInstList = orientSqlEngine.getBmService().createModelQuery(deviceInstBM).list();
                            String deviceName = "";
                            if (deviceInstList.size() > 0) {
                                String spareId = CommonTools.Obj2String(deviceInstList.get(0).get("T_SPARE_PARTS_" + schemaId + "_ID"));
                                deviceBM.setReserve_filter(" and Id='" + spareId + "'");
                                List<Map<String, Object>> deviceList = orientSqlEngine.getBmService().createModelQuery(deviceBM).list();
                                String deviceNames = CommonTools.Obj2String(deviceList.get(0).get("C_DEVICE_NAME_" + deviceBM.getId()));
                                String deviceNumber = (String) deviceInstList.get(0).get("C_SERIAL_NUMBER_" + deviceInstBM.getId());
                                deviceName += deviceNames + "-" + deviceNumber;
                            }
                            instMap.put("deviceName", deviceName);
                            instList.add(instMap);
                        }
                    }
                }
            }
        }
        return new ExtGridData<>(instList, totalcount);
    }

    public AjaxResponseData addProductTreeNode(String modelId, String formData) {
        AjaxResponseData retVal = new AjaxResponseData();
        if (StringUtil.isEmpty(formData)) {
            retVal.setMsg("保存内容为空");
            retVal.setSuccess(false);
            return retVal;
        }
        Map formDataMap = JsonUtil.json2Map(formData);
        Map dataMap = (Map) formDataMap.get("fields");
        dataMap.put("C_NAME_" + modelId, dataMap.get("C_NAME_" + modelId));
        SaveModelDataEventParam eventParam = new SaveModelDataEventParam();
        eventParam.setModelId(modelId);
        eventParam.setDataMap(dataMap);
        eventParam.setCreateData(true);
        OrientContextLoaderListener.Appwac.publishEvent(new SaveModelDataEvent(ModelDataController.class, eventParam));
        retVal.setMsg("保存成功");
//        retVal.setResults(eventParam.getDataMap().get("ID"));
        Map permsMap = new HashMap<>();
        permsMap.put("ID", eventParam.getDataMap().get("ID"));
        permsMap.put("refreshGrid", true);
        retVal.setResults(permsMap);
        return retVal;
    }

    public void delProductTreeData(String id) {
        IBusinessModel productTreeBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        String modelId = productTreeBM.getId();
        productTreeBM.clearAllFilter();
        productTreeBM.setReserve_filter("AND C_PID_" + modelId + " IN(" + id + ")");
        List<Map> productList = orientSqlEngine.getBmService().createModelQuery(productTreeBM).list();

        StringBuilder subProductIds = new StringBuilder();
        if (productList.size() > 0) {
            for (Map map : productList) {
                String productId = (String) map.get("ID");
                subProductIds.append(productId);
                subProductIds.append(",");
            }
            String subProductId = subProductIds.toString();
            subProductId = subProductId.substring(0, subProductId.length() - 1);
            if (subProductId != null && !"".equals(subProductId)) {
                orientSqlEngine.getBmService().deleteCascade(productTreeBM, subProductId);
            }
            delProductTreeData(subProductId);
        }
        orientSqlEngine.getBmService().deleteCascade(productTreeBM, id);
    }

    public void exportAllProductNodes(HttpServletRequest request, HttpServletResponse response) {
        IBusinessModel productBM = businessModelService.getBusinessModelBySName(PropertyConstant.PRODUCT_STRUCTURE, schemaId, EnumInter.BusinessModelEnum.Table);
        productBM.setReserve_filter("AND C_PID_" + productBM.getId() + "=-1");
        List<Map> productList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();
        if (productList != null && productList.size() > 0) {
            Excel excel = new Excel();
            Object[] headers = new Object[]{"分系统", "部件", "零件"};
            excel.row(0).value(headers);
            final int[] i = {1};
            for (Map productMap : productList) {
                String productId = productMap.get("ID").toString();
                String productName = CommonTools.Obj2String(productMap.get("C_NAME_" + productBM.getId()));
                if (productName.equals("母船") || productName.equals("其它")) {
                    continue;
                }
                productBM.clearAllFilter();
                productBM.setReserve_filter("AND C_PID_" + productBM.getId() + "='" + productId + "'");
                List<Map<String, Object>> subProductList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();
                List<Map> resultMapList = getChildProductNode(productBM, subProductList);
                if (resultMapList != null && resultMapList.size() > 0) {
                    for (Map resultMap : resultMapList) {
                        String systemProductName = CommonTools.Obj2String(resultMap.get("C_NAME_" + productBM.getId()));
                        excel.cell(i[0], 0).value(systemProductName).warpText(true);
                        List<Map> subChildList = (List<Map>) resultMap.get("children");
                        if (subChildList != null && subChildList.size() > 0) {
                            for (Map bujianMap : subChildList) {
                                String bujianName = CommonTools.Obj2String(bujianMap.get("C_NAME_" + productBM.getId()));
                                excel.cell(i[0], 1).value(bujianName).warpText(true);
                                List<Map> partsList = (List<Map>) bujianMap.get("children");
                                if (partsList != null && partsList.size() > 0) {
                                    int partsListSize = partsList.size();
                                    for (Map partMap : partsList) {
                                        String partName = CommonTools.Obj2String(partMap.get("C_NAME_" + productBM.getId()));
                                        partsListSize--;
                                        if (partsListSize >= 0) {
                                            if (i[0] != 1) {
                                                excel.cell(i[0], 0).value(systemProductName).warpText(true);
                                                excel.cell(i[0], 1).value(bujianName).warpText(true);
                                            }
                                            excel.cell(i[0], 2).value(partName).warpText(true);
                                            i[0]++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    String subFolder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT);
                    String shysFolderPath = fileServerConfig.getFtpHome() + subFolder + "/产品结构" + File.separator + productName;
                    if (!new File(shysFolderPath).exists()) {
                        new File(shysFolderPath).mkdirs();
                    }
                    excel.saveExcel(shysFolderPath + File.separator + productName + ".xls");
                    for (int j = 0; j < 3; j++) {
                        excel.column(j).autoWidth().borderFull(BorderStyle.DASH_DOT, Color.BLACK).align(Align.CENTER);
                    }
                    try {
                        response.setContentType("aplication/octet-stream;charset=UTF-8");
                        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(productName + ".xls", "UTF-8"));
                        BufferedInputStream in = new BufferedInputStream(new FileInputStream(shysFolderPath + File.separator + productName + ".xls"));
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
        }
    }

    /**
     * 递归查询产品结构树
     *
     * @param productBM
     * @param subProductList
     * @return
     */
    private List<Map> getChildProductNode(IBusinessModel productBM, List<Map<String, Object>> subProductList) {
        List<Map> resultList = UtilFactory.newArrayList();
        if (subProductList != null && subProductList.size() > 0) {
            for (Map productMap : subProductList) {
                Map resultMap = UtilFactory.newHashMap();
                String subProductId = productMap.get("ID").toString();
                productBM.clearAllFilter();
                productBM.setReserve_filter("AND C_PID_" + productBM.getId() + " = '" + subProductId + "'");
                List<Map<String, Object>> childsList = orientSqlEngine.getBmService().createModelQuery(productBM).orderAsc("TO_NUMBER(ID)").list();
                resultMap = productMap;
                resultMap.put("children", getChildProductNode(productBM, childsList));
                resultList.add(resultMap);
            }
        }
        return resultList;
    }

    public Map<String, Object> importSyProductStructTree(HttpServletRequest request, String fileName) throws Exception {

        IBusinessModel structSystemBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_SYSTEM, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structPartBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_PART, schemaId, EnumInter.BusinessModelEnum.Table);

//        String sql = "select s.id as system_id,s.c_name_" + structSystemBM.getId() + ",d.id as device_id,d.c_name_" + structDeviceBM.getId() + ",d.c_type_" + structDeviceBM.getId() + ",p.id as part_id,p.c_name_" + structPartBM.getId() + ",p.c_type_" + structPartBM.getId() + " from " + PropertyConstant.STRUCT_SYSTEM + "_" + schemaId
//                + " s left join " + PropertyConstant.STRUCT_DEVICE + "_" + schemaId + " d on s.id=d." + PropertyConstant.STRUCT_SYSTEM + "_" + schemaId + "_ID left join "
//                + PropertyConstant.STRUCT_PART + "_" + schemaId + " p on d.id=p." + PropertyConstant.STRUCT_DEVICE + "_" + schemaId + "_ID";
//
//        List<Map<String, Object>> structTreeList = jdbcTemplate.queryForList(sql);

        Map<String, Object> retVal = new HashMap<>();

        File excelFile = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(excelFile);
        MultipartFile multipartFile = new MockMultipartFile(excelFile.getName(), excelFile.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        //获取sheet页的数据
        IBusinessModel[] models = {structSystemBM, structDeviceBM, structPartBM};
        List<List<Map<String, String>>> excelList = excelImport.importFile(multipartFile, models);
        if (excelList != null && excelList.size() > 0) {
            for (int i = 0; i < excelList.size(); i++) {
                if (excelList.get(i) != null && excelList.get(i).size() > 0) {
                    List<ProductStruct> productStructList = new LinkedList<>();
                    for (Map<String, String> dataMap : excelList.get(i)) {
                        ProductStruct structSystemEntity = new ProductStruct();
                        ProductStruct deviceEntity = new ProductStruct();
                        ProductStruct partEntity = new ProductStruct();
                        String structSystem = CommonTools.Obj2String(dataMap.get("C_NAME_" + structSystemBM.getId()));
                        String structDevice = CommonTools.Obj2String(dataMap.get("C_NAME_" + structDeviceBM.getId()));
                        String structDeviceType = CommonTools.Obj2String(dataMap.get("C_TYPE_" + structDeviceBM.getId()));
                        String structPart = CommonTools.Obj2String(dataMap.get("C_NAME_" + structPartBM.getId()));
                        String structPartType = CommonTools.Obj2String(dataMap.get("C_TYPE_" + structPartBM.getId()));
                        structSystemEntity.setName(structSystem);
                        deviceEntity.setName(structDevice);
                        deviceEntity.setType(structDeviceType);
                        partEntity.setName(structPart);
                        partEntity.setType(structPartType);
                        Set<ProductStruct> partsList = new HashSet<>();
                        if (StringUtil.isNotEmpty(partEntity.getName())) {
                            partsList.add(partEntity);
                        }
                        Set<ProductStruct> structDeviceList = new HashSet<>();
                        deviceEntity.setPartsList(partsList);
                        if (StringUtil.isNotEmpty(deviceEntity.getName())) {
                            structDeviceList.add(deviceEntity);
                        }
                        boolean flag = true;
                        //重新组合数据
                        if (productStructList != null && productStructList.size() > 0) {
                            for (ProductStruct productStruct : productStructList) {
                                if (!"".equals(structSystem) && structSystem.equals(productStruct.getName())) {
                                    flag = false;
                                    //封装设备
                                    boolean isDeviceFlag = true;
                                    Set<ProductStruct> hasStructDeviceList = productStruct.getStructDeviceList();
                                    if (hasStructDeviceList != null && hasStructDeviceList.size() > 0) {
                                        for (ProductStruct structDeviceEntity : hasStructDeviceList) {
                                            if (StringUtil.isNotEmpty(structDevice) && structDevice.equals(structDeviceEntity.getName())) {
                                                isDeviceFlag = false;
                                                Set<ProductStruct> partList = structDeviceEntity.getPartsList();
                                                if (StringUtil.isNotEmpty(structPart)) {
                                                    partList.add(partEntity);
                                                }
                                            }
                                        }
                                    }
                                    if (isDeviceFlag) {
                                        if (StringUtil.isNotEmpty(deviceEntity.getName())) {
                                            hasStructDeviceList.add(deviceEntity);
                                        }
                                    }
                                }
                            }
                        }
                        if (flag) {
                            if (StringUtil.isNotEmpty(structSystemEntity.getName())) {
                                structSystemEntity.setStructDeviceList(structDeviceList);
                                productStructList.add(structSystemEntity);
                            }
                        }
                    }

                    if (productStructList != null && productStructList.size() > 0) {
                        List<Map<String, Object>> structSystemList = orientSqlEngine.getBmService().createModelQuery(structSystemBM).orderAsc("TO_NUMBER(ID)").list();
                        List<Map<String, Object>> structDeviceList = orientSqlEngine.getBmService().createModelQuery(structDeviceBM).list();
                        List<Map<String, Object>> structPartList = orientSqlEngine.getBmService().createModelQuery(structPartBM).list();

                        for (ProductStruct structSystemEntity : productStructList) {
                            String systemName = structSystemEntity.getName();
                            for (Map structSystemMap : structSystemList) {
                                String hasSystemName = CommonTools.Obj2String(structSystemMap.get("C_NAME_" + structSystemBM.getId()));
                                String systemId = structSystemMap.get("ID").toString();
                                if (hasSystemName.equals(systemName)) {
                                    structSystemEntity.setId(systemId);
                                    break;
                                }
                            }
                            if (StringUtil.isEmpty(structSystemEntity.getId())) {
                                Map systemMap = UtilFactory.newHashMap();
                                systemMap.put("C_NAME_" + structSystemBM.getId(), structSystemEntity.getName());
                                systemMap.put("C_VERSION_" + structSystemBM.getId(), 0);
                                String newSystemId = orientSqlEngine.getBmService().insertModelData(structSystemBM, systemMap);
                                structSystemEntity.setId(newSystemId);
                            }

                            Set<ProductStruct> deviceList = structSystemEntity.getStructDeviceList();
                            if (deviceList != null && deviceList.size() > 0) {
                                for (ProductStruct deviceEntity : deviceList) {
                                    String structDeviceName = deviceEntity.getName();
                                    for (Map structDeviceMap : structDeviceList) {
                                        String hasDeviceName = CommonTools.Obj2String(structDeviceMap.get("C_NAME_" + structDeviceBM.getId()));
                                        String hasDeviceId = CommonTools.Obj2String(structDeviceMap.get("ID"));
                                        String hasDeviceType = CommonTools.Obj2String(structDeviceMap.get("c_type_" + structDeviceBM.getId()));
                                        String refStructSystemId = CommonTools.Obj2String(structDeviceMap.get("T_STRUCT_SYSTEM_" + schemaId + "_ID"));
                                        if (hasDeviceName.equals(structDeviceName) && refStructSystemId.equals(structSystemEntity.getId())) {
                                            deviceEntity.setId(hasDeviceId);
                                            if (!hasDeviceType.equals(deviceEntity.getType())) {
                                                structDeviceMap.remove("C_TYPE_" + structDeviceBM.getId());
                                                structDeviceMap.put("C_TYPE_" + structDeviceBM.getId(), deviceEntity.getType());
                                                orientSqlEngine.getBmService().updateModelData(structDeviceBM, structDeviceMap, hasDeviceId);
                                            }
                                            Set<ProductStruct> partsList = deviceEntity.getPartsList();
                                            if (partsList != null && partsList.size() > 0) {
                                                for (ProductStruct partEntity : partsList) {
                                                    String partsName = partEntity.getName();
                                                    boolean isInsertPart = true;
                                                    if (structPartList != null && structPartList.size() > 0) {
                                                        for (Map structPartMap : structPartList) {
                                                            String hasPartId = structPartMap.get("ID").toString();
                                                            String hasPartName = CommonTools.Obj2String(structPartMap.get("C_NAME_" + structPartBM.getId()));
                                                            String hasPartType = CommonTools.Obj2String(structPartMap.get("C_TYPE_" + structPartBM.getId()));
                                                            String refStructDeviceId = CommonTools.Obj2String(structPartMap.get("T_STRUCT_DEVICE_" + schemaId + "_ID"));
                                                            if (hasPartName.equals(partsName) && refStructDeviceId.equals(deviceEntity.getId())) {
                                                                isInsertPart = false;
                                                                if (!hasPartType.equals(partEntity.getType())) {
                                                                    structPartMap.remove("C_TYPE_" + structPartBM.getId());
                                                                    structPartMap.put("C_TYPE_" + structPartBM.getId(), partEntity.getType());
                                                                    orientSqlEngine.getBmService().updateModelData(structPartBM, structPartMap, hasPartId);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (isInsertPart) {
                                                        Map insetPartMap = UtilFactory.newHashMap();
                                                        insetPartMap.put("C_NAME_" + structPartBM.getId(), partEntity.getName());
                                                        insetPartMap.put("C_TYPE_" + structPartBM.getId(), partEntity.getType());
                                                        insetPartMap.put("T_STRUCT_DEVICE_" + schemaId + "_ID", deviceEntity.getId());
                                                        insetPartMap.put("T_STRUCT_SYSTEM_" + schemaId + "_ID", structSystemEntity.getId());
                                                        orientSqlEngine.getBmService().insertModelData(structPartBM, insetPartMap);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (StringUtil.isEmpty(deviceEntity.getId())) {
                                        Map insertStructDeviceMap = UtilFactory.newHashMap();
                                        insertStructDeviceMap.put("C_NAME_" + structDeviceBM.getId(), deviceEntity.getName());
                                        insertStructDeviceMap.put("C_TYPE_" + structDeviceBM.getId(), deviceEntity.getType());
                                        insertStructDeviceMap.put("T_STRUCT_SYSTEM_" + schemaId + "_ID", structSystemEntity.getId());
                                        String newStructDeviceId = orientSqlEngine.getBmService().insertModelData(structDeviceBM, insertStructDeviceMap);
                                        Set<ProductStruct> partsList = deviceEntity.getPartsList();
                                        if (partsList != null && partsList.size() > 0) {
                                            for (ProductStruct partEntity : partsList) {
                                                Map insetPartMap = UtilFactory.newHashMap();
                                                insetPartMap.put("C_NAME_" + structPartBM.getId(), partEntity.getName());
                                                insetPartMap.put("C_TYPE_" + structPartBM.getId(), partEntity.getType());
                                                insetPartMap.put("T_STRUCT_DEVICE_" + schemaId + "_ID", newStructDeviceId);
                                                insetPartMap.put("T_STRUCT_SYSTEM_" + schemaId + "_ID", structSystemEntity.getId());
                                                orientSqlEngine.getBmService().insertModelData(structPartBM, insetPartMap);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        retVal.put("success", true);
        retVal.put("msg", "导入成功！");
        return retVal;
    }

    public String exportSyProductStructTree(boolean exportAll) {
        IBusinessModel productSystemBM = businessModelService.getBusinessModelBySName
                (PropertyConstant.STRUCT_SYSTEM, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structDeviceBM = businessModelService.getBusinessModelBySName
                (PropertyConstant.STRUCT_DEVICE, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structPartBM = businessModelService.getBusinessModelBySName
                (PropertyConstant.STRUCT_PART, PropertyConstant.WEI_BAO_SCHEMA_ID, EnumInter.BusinessModelEnum.Table);

        Excel excel = new Excel();
        excel.setWorkingSheet(0).sheetName("产品结构树");
        if (exportAll) {
            String sql = "select s.id as system_id,s.c_name_" + productSystemBM.getId() + ",d.id as device_id,d.c_name_" + structDeviceBM.getId() + ",d.c_type_" + structDeviceBM.getId() + ",p.id as part_id,p.c_name_" + structPartBM.getId() + ",p.c_type_" + structPartBM.getId() + " from " + PropertyConstant.STRUCT_SYSTEM + "_" + schemaId
                    + " s left join " + PropertyConstant.STRUCT_DEVICE + "_" + schemaId + " d on s.id=d." + PropertyConstant.STRUCT_SYSTEM + "_" + schemaId + "_ID left join "
                    + PropertyConstant.STRUCT_PART + "_" + schemaId + " p on d.id=p." + PropertyConstant.STRUCT_DEVICE + "_" + schemaId + "_ID order by s.id,s.c_name_" + productSystemBM.getId() + " asc";
            List<Map<String, Object>> structTreeList = jdbcTemplate.queryForList(sql);
            exportProductStructInfoData(excel, structTreeList, productSystemBM, structDeviceBM, structPartBM);
        }
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.EXPORT_ROOT + File.separator + "产品结构树");
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String fileName = "导出产品结构树.xls";
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        excel.saveExcel(fileServerConfig.getFtpHome() + folder + finalFileName);
        return fileServerConfig.getFtpHome() + folder + finalFileName;
    }

    public void exportProductStructInfoData(Excel excel, List<Map<String, Object>> list, IBusinessModel productSystemBM, IBusinessModel structDeviceBM, IBusinessModel structPartBM) {
        Object[] headers = new Object[]{"系统名称", "设备分类", "设备名称", "零部件分类", "零部件名称"};
        excel.row(0).value(headers).borderFull(BorderStyle.DASH_DOT, Color.BLACK).align(Align.CENTER);
        final int[] i = {1};
        if (list != null && list.size() > 0) {
            for (Map map : list) {
                excel.cell(i[0], 0).value(StringUtil.decodeUnicode(CommonTools.Obj2String(map.get("C_NAME_" + productSystemBM.getId())))).warpText(true);
                excel.cell(i[0], 1).value(CommonTools.Obj2String(map.get("C_TYPE_" + structDeviceBM.getId()))).warpText(true);
                excel.cell(i[0], 2).value(CommonTools.Obj2String(map.get("C_NAME_" + structDeviceBM.getId()))).warpText(true);
                excel.cell(i[0], 3).value(CommonTools.Obj2String(map.get("C_TYPE_" + structPartBM.getId()))).warpText(true);
                excel.cell(i[0], 4).value(CommonTools.Obj2String(map.get("C_NAME_" + structPartBM.getId()))).warpText(true);
                i[0]++;
            }
        }
        for (int j = 0; j < headers.length; j++) {
            excel.column(j).autoWidth();
        }
    }
}