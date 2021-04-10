package com.orient.download.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.download.bean.checkHeadRowCellBean.*;
import com.orient.download.bean.checkHeadRowCellBean.HeadBean;
import com.orient.download.bean.currentTaskBean.*;
import com.orient.download.bean.dailyWorkEntity.DivingTaskEntity;
import com.orient.download.bean.inform.BaseEntity;
import com.orient.download.bean.inform.CurrentStateBean;
import com.orient.download.bean.inform.InformBean;
import com.orient.download.bean.productStructEntity.StructDeviceCycleCheckEntity;
import com.orient.download.bean.productStructEntity.StructDeviceEntity;
import com.orient.download.bean.productStructEntity.StructDeviceInstEntity;
import com.orient.download.bean.productStructEntity.StructSystemEntity;
import com.orient.download.bean.queryDivingTaskBean.DiveDetailModel;
import com.orient.download.bean.queryDivingTaskBean.DiveNumModel;
import com.orient.download.bean.queryDivingTaskBean.DivingQueryModel;
import com.orient.download.bean.queryDivingTaskBean.TableSimpleModel;
import com.orient.download.bean.sparePartsBean.*;
import com.orient.download.controller.UrlFilesToZip;
import com.orient.download.enums.HttpResponse;
import com.orient.download.enums.HttpResponseStatus;
import com.orient.edm.init.FileServerConfig;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.file.CwmFileDAO;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.service.file.FileService;
import com.orient.sysmodel.service.sys.IBackUpJobService;
import com.orient.sysmodel.service.user.UserService;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.utils.image.ImageUtils;
import com.orient.web.base.BaseBusiness;
import com.orient.web.util.UserContextUtil;
import com.orient.weibao.constants.PropertyConstant;
import com.orient.weibao.mbg.model.DivingTaskExample;
import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.FileOutputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jgroups.blocks.Link;
import org.json.JSONArray;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xml.sax.InputSource;
import sun.awt.geom.AreaOp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-20 15:56
 */
@Service
public class TestDownloadBusiness extends BaseBusiness {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UrlFilesToZip.class);
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private FileServerConfig fileServerConfig;
    @Autowired
    private FileService fileService;
    @Autowired
    UserService UserService;

    private String schemaId = PropertyConstant.WEI_BAO_SCHEMA_ID;

    private static final Logger log = Logger.getLogger(FileOperator.class);

    public List<Map<String, String>> commonGetList(IBusinessModel businessModel) {
        return orientSqlEngine.getBmService().createModelQuery(businessModel).list();
    }

    public HttpResponse<List<SysUserBean>> getSysUser() {
        HttpResponse retVal = new HttpResponse();
//        StringBuilder sql = new StringBuilder();
//        sql.append("select id,all_name,user_name,password from cwm_sys_user");
//        List<Map<String, Object>> list = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
        List<SysUserBean> userBeanList = new ArrayList<>();
        Map<String, User> allUsers = roleEngine.getRoleModel(false).getUsers();
        if (allUsers.values() != null && allUsers.values().size() > 0) {
            for (IUser user : allUsers.values()) {
                String personClassify = CommonTools.Obj2String(user.getPersonClassify());
                SysUserBean userBean = new SysUserBean();
                userBean.setId(Integer.parseInt(String.valueOf(user.getId())));
                userBean.setPersonnelName(CommonTools.Obj2String(user.getAllName()));
                userBean.setUsername(CommonTools.Obj2String(user.getUserName()));
                userBean.setLoginPassword(CommonTools.Obj2String(user.getPassword()));
                //1代表潜航员类型
                if ("1".equals(personClassify)) {
                    userBean.setPersonClassify(personClassify);
                }
                userBeanList.add(userBean);
            }
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
            retVal.setData(userBeanList);
        }
//        if (list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                SysUserBean userBean = new SysUserBean();
//                userBean.setId(Integer.parseInt(String.valueOf(list.get(i).get("ID"))));
//                userBean.setPersonnelName(CommonTools.Obj2String(list.get(i).get("ALL_NAME")));
//                userBean.setUsername(CommonTools.Obj2String(list.get(i).get("USER_NAME")));
//                userBean.setLoginPassword(CommonTools.Obj2String(list.get(i).get("PASSWORD")));
//                userBeanList.add(userBean);
//            }
//            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
//            retVal.setData(userBeanList);
//        }
        else {
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
//            retVal.setMsg("服务端数据有误！");
            retVal.setData(new ArrayList<>());
        }
        return retVal;
    }


    public HttpResponse<DivingTaskBean> getCurrentTask() {

        HttpResponse retVal = new HttpResponse<>();

        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel flowBM = businessModelService.getBusinessModelBySName(PropertyConstant.NODE_DESIGN, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel refPostBM = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel attendBM = businessModelService.getBusinessModelBySName(PropertyConstant.ATTEND_PERSON, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkCellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkCellInstDataBM = businessModelService.getBusinessModelBySName(PropertyConstant.CELL_INST_DATA, schemaId, EnumInter.BusinessModelEnum.Table);

        divingTaskBM.setReserve_filter("AND C_STATE_" + divingTaskBM.getId() + "='" + "进行中" + "'");
        List<Map<String, String>> divingTaskList = commonGetList(divingTaskBM);
        DivingTaskBean divingTaskBean = new DivingTaskBean();
        if (divingTaskList.size() > 0) {
            for (Map taskMap : divingTaskList) {
                String taskId = CommonTools.Obj2String(taskMap.get("ID"));
                divingTaskBean.setId(taskId);
                divingTaskBean.setTaskName(CommonTools.Obj2String(taskMap.get("C_TASK_NAME_" + divingTaskBM.getId())));
                divingTaskBean.setLeaderId(CommonTools.Obj2String(taskMap.get("C_RESPONSIBLE_PERSON_" + divingTaskBean.getId())));
                flowBM.clearAllFilter();
                flowBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'");
                //根据时间获取最新的XML
//                Map<String, String> flowMap = (Map) orientSqlEngine.getBmService().createModelQuery(flowBM).orderDesc("C_EDIT_TIME_" + flowBM.getId()).list().get(0);
                Map<String, String> flowMap = (Map) orientSqlEngine.getBmService().createModelQuery(flowBM).list().get(0);
                if (!flowMap.isEmpty()) {
                    List<FlowBean> flowBeanList = new ArrayList<>();
                    String xmlData = flowMap.get("C_XML_" + flowBM.getId());
                    //利用dom4j解析xml
                    InputSource inputSource = new InputSource(new StringReader(xmlData));
                    inputSource.setEncoding("UTF-8");
                    SAXReader reader = new SAXReader();
                    Document document;
                    try {
                        document = reader.read(inputSource);
                        //获取根节点
                        String xpath = "//Roundrect[@id]";
                        List<Element> elementList = document.selectNodes(xpath);
//                        Element rootElement=document.getRootElement();
//                        Element rootjd=rootElement.element("root");
//                        Iterator iterator=rootjd.elementIterator("Roundrect");
                        for (Iterator iterator = elementList.iterator(); iterator.hasNext(); ) {

                            List<CheckTempInstBean> checkTempInstBeanList = new ArrayList<>();
                            List<PostBean> postBeanList = new ArrayList<>();
                            FlowBean flowBean = new FlowBean();
                            Element element = (Element) iterator.next();
//                            Element mxCellElement=element.element("mxCell");
                            String nodeId = element.attributeValue("id");
                            String nodeContent = element.attributeValue("label");
                            flowBean.setId(nodeId);
                            flowBean.setName(nodeContent);
                            checkTempInstBM.clearAllFilter();
                            checkTempInstBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                                    " AND C_NODE_ID_" + checkTempInstBM.getId() + "='" + nodeId + "'" +
                                    " AND C_CHECK_STATE_" + checkTempInstBM.getId() + "='" + "未完成" + "'"
                            );
                            List<Map<String, String>> checkTempInstList = commonGetList(checkTempInstBM);
                            if (checkTempInstList.size() > 0) {
                                for (Map<String, String> checkMap : checkTempInstList) {
                                    CheckTempInstBean checkTempInstBean = new CheckTempInstBean();
                                    String checkTempInstId = checkMap.get("ID").toString();
//                                    checkCellInstBM.clearAllFilter();
//                                    checkCellInstBM.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + checkTempInstId + "'" + " AND C_IS_HEADER_" + checkCellInstBM.getId() + "='false'");
//                                    List<Map<String, String>> tailList = commonGetList(checkCellInstBM);
//                                    boolean isSendCheckTable = true;
//                                    if (tailList != null && tailList.size() > 0) {
//                                        for (Map tailMap : tailList) {
//                                            String cellInstId = tailMap.get("ID").toString();
//                                            String tailContent = CommonTools.Obj2String(tailMap.get("C_CONTENT_" + checkCellInstBM.getId()));
//                                            if ("结束时间".equals(tailContent)) {
//                                                checkCellInstDataBM.clearCustomFilter();
//                                                checkCellInstDataBM.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + checkTempInstId + "'" + " AND T_CHECK_CELL_INST_" + schemaId + "_ID='" + cellInstId + "'");
//                                                List<Map<String, String>> checkCellInstDataList = commonGetList(checkCellInstDataBM);
//                                                if (checkCellInstDataList != null && checkCellInstDataList.size() > 0) {
//                                                    String value = CommonTools.Obj2String(checkCellInstDataList.get(0).get("C_CONTENT_" + checkCellInstDataBM.getId()));
//                                                    if (!"".equals(value)) {
//                                                        isSendCheckTable = false;
//                                                        break;
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
                                    String checkTableIsRepeatUpload = CommonTools.Obj2String(checkMap.get("C_IS_REPEAT_UPLOAD_" + checkTempInstBM.getId()));
                                    checkTableIsRepeatUpload = "".equals(checkTableIsRepeatUpload) ? "否" : checkTableIsRepeatUpload;
//                                    String checkState = CommonTools.Obj2String(checkMap.get("C_CHECK_STATE_" + checkTempInstBM.getId()));
//                                    if ("否".equals(checkTableIsRepeatUpload) && "已上传".equals(checkState)) {
//                                        continue;
//                                    }
//                                    if (isSendCheckTable) {
                                    checkTempInstBean.setId(checkTempInstId);
                                    checkTempInstBean.setCheckName(checkMap.get("C_NAME_" + checkTempInstBM.getId()));
                                    checkTempInstBean.setState(checkMap.get("C_CHECK_STATE_" + checkTempInstBM.getId()));
                                    checkTempInstBean.setCheckTableType(checkMap.get("C_INS_TYPE_" + checkTempInstBM.getId()));
                                    checkTempInstBean.setIsRepeatUpload(checkTableIsRepeatUpload);
                                    checkTempInstBeanList.add(checkTempInstBean);
//                                    }
                                }
                            }
                            flowBean.setCheckTempInstBeanList(checkTempInstBeanList);
                            refPostBM.clearAllFilter();
                            refPostBM.setReserve_filter("AND T_DIVING_TASK_" + schemaId + "_ID='" + taskId + "'" +
                                            " AND C_NODE_ID_" + refPostBM.getId() + "='" + nodeId + "'"
//                                    " AND C_NODE_TEXT_" + refPostBM.getId() + "='" + nodeContent + "'"
                            );
                            List<Map<String, String>> postList = commonGetList(refPostBM);
                            if (postList.size() > 0) {
                                for (Map<String, String> refPostMap : postList) {
                                    PostBean refPostBean = new PostBean();
                                    refPostBean.setId(refPostMap.get("ID"));
//                                    postBean.setPostName(postMap.get("C_POST_ID_" + postBM.getId()));
                                    String postId = refPostMap.get("C_POST_ID_" + refPostBM.getId());
                                    postBM.clearAllFilter();
                                    postBM.setReserve_filter("AND ID='" + postId + "'");
                                    Map<String, String> postMap = commonGetList(postBM).get(0);
                                    if (!postMap.isEmpty()) {
                                        refPostBean.setPostName(postMap.get("C_POST_NAME_" + postBM.getId()));
                                    }
                                    attendBM.clearCustomFilter();
                                    attendBM.appendCustomerFilter(new CustomerFilter("C_ATTEND_POST_" + attendBM.getId(), EnumInter.SqlOperation.Like, postId));
                                    attendBM.appendCustomerFilter(new CustomerFilter("T_DIVING_TASK_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, taskId));
                                    List<Map<String, Object>> attendList = orientSqlEngine.getBmService().createModelQuery(attendBM).list();
                                    String personIds = "";
                                    for (Map map : attendList) {
                                        String attendPostId = (String) map.get("C_ATTEND_POST_" + attendBM.getId());
                                        if (attendPostId.contains(",")) {
                                            String attendPostIds[] = attendPostId.split(",");
                                            for (String str : attendPostIds) {
                                                if (str.equals(postId)) {
                                                    String personId = CommonTools.Obj2String(map.get("C_ATTEND_PERSON_" + attendBM.getId()));
                                                    personIds += personId + ",";
                                                }
                                            }
                                        } else if (attendPostId.indexOf(",") == -1 && StringUtil.isNotEmpty(attendPostId)) {
                                            if (attendPostId.equals(postId)) {
                                                String personId = CommonTools.Obj2String(map.get("C_ATTEND_PERSON_" + attendBM.getId()));
                                                personIds += personId + ",";
                                            }
                                        }
                                    }
                                    if (StringUtil.isNotEmpty(personIds)) {
                                        personIds = personIds.substring(0, personIds.length() - 1);
                                        StringBuilder sql = new StringBuilder();
                                        sql.append("select id,all_name from cwm_sys_user where id in(").append(personIds).append(")");
                                        List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(sql.toString());
                                        String allNames = "";
                                        if (userList != null && userList.size() > 0) {
                                            for (Map userMap : userList) {
                                                String allName = CommonTools.Obj2String(userMap.get("all_name"));
                                                allNames += allName + ",";
                                            }
                                            allNames = allNames.substring(0, allNames.length() - 1);
                                            refPostBean.setFillPerson(allNames);
                                        }
                                    }
                                    postBeanList.add(refPostBean);
                                }
                            }
                            flowBean.setPostBeanList(postBeanList);
                            flowBeanList.add(flowBean);

                        }
                        List<String> endResult = new ArrayList<>();
                        //获取连接线
                        String connectLineXml = "//Connector[@id]";
                        List<String> connectLineList = UtilFactory.newArrayList();
                        List<Element> connectList = document.selectNodes(connectLineXml);
                        for (Iterator iterator = connectList.iterator(); iterator.hasNext(); ) {
                            Element element = (Element) iterator.next();
                            Element mxCellElement = element.element("mxCell");
                            String source = mxCellElement.attributeValue("source");
                            String target = mxCellElement.attributeValue("target");
                            String combineST = source + "," + target;
                            connectLineList.add(combineST);
                        }
                        if (connectLineList.size() > 0) {
                            Iterator<String> iterator = connectLineList.iterator();
                            while (iterator.hasNext()) {
                                String line = iterator.next();
                                String arrayLine[] = line.split(",");
                                List<String> lineList = new ArrayList<>(Arrays.asList(arrayLine));
                                iterator.remove();
                                for (String dian : lineList) {
                                    if (!endResult.contains(dian)) {
                                        endResult.add(dian);
                                    }
                                    connectLineList = everyLine(endResult, dian, connectLineList);
                                }
                                iterator = connectLineList.iterator();
                            }
//                            for (String line : connectLineList) {
//                                String arrayLine[]=line.split(",");
//                                List<String> lineList = new ArrayList<>(Arrays.asList(arrayLine));
//                                connectLineList.remove(line);
//                                for (String dian : lineList) {
//                                    if (!endResult.contains(dian)){
//                                        endResult.add(dian);
//                                    }
//                                    everyLine(endResult,dian, connectLineList);
//                                }
//                                if(connectLineList.size()==0){
//                                    break;
//                                }
//                            }
                        }
                        if (endResult.size() > 0) {
                            List<FlowBean> newFlowBeanList = UtilFactory.newArrayList();
                            for (String sort : endResult) {
                                if (flowBeanList.size() > 0) {
                                    for (FlowBean flowBean : flowBeanList) {
                                        String flowId = flowBean.getId();
                                        if (sort.equals(flowId)) {
                                            newFlowBeanList.add(flowBean);
                                            break;
                                        }
                                    }
                                }
                            }
                            flowBeanList = newFlowBeanList;
                        }
                        divingTaskBean.setFlowBeanList(flowBeanList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                retVal.setData(divingTaskBean);
                retVal.setResult((HttpResponseStatus.SUCCESS.toString()));
            }
        } else {
            divingTaskBean.setId("-1");
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
            retVal.setMsg("当前任务无数据！");
            retVal.setData(divingTaskBean);
            return retVal;
        }
        return retVal;
    }

    private List everyLine(List<String> endResult, String dian, List<String> connectLineList) {
        //所有线
        if (connectLineList.size() > 0) {
            //找源，前面的
            for (String allLine : connectLineList) {
                String arrayLine[] = allLine.split(",");
                List<String> allLineList = new ArrayList(Arrays.asList(arrayLine));
                if (dian.equals(allLineList.get(1))) {
                    if (!endResult.contains(allLineList.get(0))) {
                        endResult.add(endResult.indexOf(dian), allLineList.get(0));
                    }
                    String everyDian = allLineList.get(0);
                    connectLineList.remove(allLine);
                    everyLine(endResult, everyDian, connectLineList);
                    break;
                }
//                else if (dian.equals(allLineList.get(0))) {
//                    if (!endResult.contains(allLineList.get(1))){
//                        endResult.add(endResult.indexOf(dian)+1,allLineList.get(1));
//                    }
//                    connectLineList.remove(allLine);
//                    String everyDian = allLineList.get(1);
//                    everyLine(endResult, everyDian, connectLineList);
//                    break;
//                }
            }
            //找后面的，尾
            for (String allLine : connectLineList) {
                String arrayLine[] = allLine.split(",");
                List<String> allLineList = new ArrayList(Arrays.asList(arrayLine));
                if (dian.equals(allLineList.get(0))) {
                    if (!endResult.contains(allLineList.get(1))) {
                        endResult.add(endResult.indexOf(dian) + 1, allLineList.get(1));
                    }
                    connectLineList.remove(allLine);
                    String everyDian = allLineList.get(1);
                    everyLine(endResult, everyDian, connectLineList);
                    break;
                }
            }
        }
        return connectLineList;
    }


    public CheckListTableBean getTableById(String checkTableInstId, boolean isTableComplete) {

        CheckListTableBean checkListTableBean = new CheckListTableBean();
        IBusinessModel tableBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        tableBM.appendCustomerFilter(new CustomerFilter("ID", EnumInter.SqlOperation.Equal, checkTableInstId));
        List<Map> tableDataList = orientSqlEngine.getBmService().createModelQuery(tableBM).list();
        if (tableDataList.size() == 0) {
            return checkListTableBean;
        }
        /*获取检查表名称*/
        Map<String, Object> tableDataMap = tableDataList.get(0);
        checkListTableBean = getCellData(checkListTableBean, tableDataMap, checkTableInstId, isTableComplete);
        return checkListTableBean;
    }


    public CheckListTableBean getCellData(CheckListTableBean checkListTableBean, Map<String, Object> tableDataMap, String checkTableInstId, boolean isTableComplete) {
        String tableName = PropertyConstant.CHECK_TEMP_INST;
        String headerName = PropertyConstant.CHECK_HEADER_INST;
        String rowName = PropertyConstant.CHECK_ROW_INST;
        String cellName = PropertyConstant.CHECK_CELL_INST;
        String uploadCellName = PropertyConstant.CELL_INST_DATA;

        IBusinessModel tableBM = businessModelService.getBusinessModelBySName(tableName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel headerBM = businessModelService.getBusinessModelBySName(headerName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel rowBM = businessModelService.getBusinessModelBySName(rowName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellBM = businessModelService.getBusinessModelBySName(cellName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel uploadCellBM = businessModelService.getBusinessModelBySName(uploadCellName, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel troubleBM = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);

        String tableModelId = tableBM.getId();
        String headerModelId = headerBM.getId();

        checkListTableBean.setId(CommonTools.Obj2String(tableDataMap.get("ID")));
        checkListTableBean.setName(CommonTools.Obj2String(tableDataMap.get("C_NAME_" + tableModelId)));
        checkListTableBean.setState(CommonTools.Obj2String(tableDataMap.get("C_CHECK_STATE_" + tableModelId)));

        String tableIsRepeatUpload = CommonTools.Obj2String(tableDataMap.get("C_IS_REPEAT_UPLOAD_" + tableModelId));

        headerBM.appendCustomerFilter(new CustomerFilter(tableName + "_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, checkTableInstId));
        List<Map> headerDataList = orientSqlEngine.getBmService().createModelQuery(headerBM).list();
        if (headerDataList.size() == 0) {
            return checkListTableBean;
        }
        Collections.reverse(headerDataList);

        rowBM.appendCustomerFilter(new CustomerFilter(tableName + "_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, checkTableInstId));
        List<Map> rowDataList = orientSqlEngine.getBmService().createModelQuery(rowBM).list();
        Collections.reverse(rowDataList);

        cellBM.appendCustomerFilter(new CustomerFilter(tableName + "_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, checkTableInstId));
        cellBM.appendCustomerFilter(new CustomerFilter("C_IS_HEADER_" + cellBM.getId(), EnumInter.SqlOperation.IsNull, ""));
        List<Map> cellDataList = orientSqlEngine.getBmService().createModelQuery(cellBM).list();
        Collections.reverse(cellDataList);

        uploadCellBM.clearCustomFilter();
        uploadCellBM.appendCustomerFilter(new CustomerFilter("T_CHECK_TEMP_INST_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, tableDataMap.get("ID").toString()));
        List<Map<String, Object>> uploadCellContentList = orientSqlEngine.getBmService().createModelQuery(uploadCellBM).orderAsc("TO_NUMBER(T_CHECK_CELL_INST_" + schemaId + "_ID)").list();

        /*获取表头*/
        List<String> headerIdList = UtilFactory.newArrayList();
        for (int i = 0; i < headerDataList.size(); i++) {
            Map<String, String> headerDataMap = headerDataList.get(i);
            HeadBean headBean = new HeadBean();
            String headerContent = CommonTools.Obj2String(headerDataMap.get("C_NAME_" + headerModelId));
            headBean.setName(headerContent);
            headBean.setDbId(headerDataMap.get("ID"));
            headBean.setColIndex(String.valueOf(i));
            checkListTableBean.addHeader(headBean);
            headerIdList.add(headerDataMap.get("ID"));
        }

        //rows
        if (rowDataList.size() != 0) {
            for (int j = 0; j < rowDataList.size(); j++) {
                RowBean rowBean = new RowBean();
                Map<String, String> rowDataMap = rowDataList.get(j);
                String rowId = rowDataMap.get("ID");
                String rowNumber = rowDataMap.get("C_ROW_NUMBER_" + rowBM.getId());
                String rowPersonId = rowDataMap.get("C_UPLOADER_" + rowBM.getId());
                rowBean.setDbId(rowId);
                rowBean.setServerUploaderId(rowPersonId);
//                rowBean.setOrder(String.valueOf(j));
                rowBean.setOrder(rowNumber);
                for (int k = 0; k < headerIdList.size(); k++) {
                    String headerId = headerIdList.get(k);
                    CellDataBean cellBean = new CellDataBean();
                    String rowcol = rowName + "_" + schemaId + "_ID";
                    String headerCol = headerName + "_" + schemaId + "_ID";

                    for (Map map : cellDataList) {
                        if (map.get(rowcol).equals(rowId) && map.get(headerCol).equals(headerId)) {
                            String content = CommonTools.Obj2String(map.get("C_CONTENT_" + cellBM.getId()));
                            //普通字段
                            if (!checkListTableBean.getHeadBeanArrayList().get(k).isCheck()) {
                                String indexHeaderId = headerIdList.get(0);
                                if (map.get(headerCol).equals(indexHeaderId)) {
                                    rowBean.setIndexName(content);
                                }
                                cellBean.setContent(content);
                                cellBean.setType("0");
                            } else {   //特殊字段
                                if (content.contains("拍照")) {
                                    cellBean.setType("3");
                                } else if (content.contains("填写")) {
                                    cellBean.setType("2");
                                } else if (content.contains("对勾")) {
                                    cellBean.setType("1");     //对勾
                                } else if (content.equals("") || content.equals("---")) {
                                    cellBean.setType("5");  //防止特殊字段为空
                                } else if (content.equals("数字")) {
                                    cellBean.setType("4");  //判断特殊字段是数字，使终端出现数字键盘
                                } else if (content.equals("时间")) {
                                    cellBean.setType("6");  //判断特殊字段是时间，使终端出现时间键盘
                                } else if (content.equals("签署")) {
                                    cellBean.setType("8");  //判断特殊字段是签署，使终端出现签署
                                } else if (content.equals("故障")) {
                                    cellBean.setType("9");  //判断特殊字段是故障，使终端出现故障
                                } else if (content.equals("是否无")) {
                                    cellBean.setType("10");  //判断特殊字段是是否无，使终端出现是否无对勾
                                } else if (content.equals("经纬度")) {
                                    cellBean.setType("11");  //判断特殊字段是经纬度，使终端出现经纬度
                                }
                                String cellID = (String) map.get("ID");
                                if ("是".equals(tableIsRepeatUpload) || isTableComplete) {
//                                    uploadCellBM.clearCustomFilter();
//                                    uploadCellBM.appendCustomerFilter(new CustomerFilter("T_CHECK_CELL_INST_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, cellID));
//                                    uploadCellBM.appendCustomerFilter(new CustomerFilter("T_CHECK_TEMP_INST_" + schemaId + "_ID", EnumInter.SqlOperation.Equal, tableDataMap.get("ID").toString()));
//                                    List<Map<String, String>> data = commonGetList(uploadCellBM);
                                    if (uploadCellContentList != null && uploadCellContentList.size() != 0) {
                                        for (Map cellContentMap : uploadCellContentList) {
                                            if (cellID.equals(CommonTools.Obj2String(cellContentMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID")))) {
                                                String cellValue = CommonTools.Obj2String(cellContentMap.get("C_CONTENT_" + uploadCellBM.getId()));
                                                cellBean.setContent(cellValue);
                                                break;
                                            }
                                        }
                                    }
                                    //2021.2.2下发故障单元格内容给终端
//                                    if ("故障".equals(content)) {
//                                        troubleBM.clearAllFilter();
//                                        troubleBM.setReserve_filter(" AND C_CELL_ID_" + troubleBM.getId() + "='" + cellID + "' AND C_DEVICE_STATE_" + troubleBM.getId() + "='单元格故障'");
//                                        List<Map> troubleList = orientSqlEngine.getBmService().createModelQuery(troubleBM).list();
//                                        if (troubleList != null && troubleList.size() > 0) {
//                                            String troubleCellDesp = CommonTools.Obj2String(troubleList.get(0).get("C_DESCRIPTION_" + troubleBM.getId()));
//                                            cellBean.setTroubleCellDesp(troubleCellDesp);
//                                        }
//                                    }
                                }
                            }
                            cellBean.setColIndex(String.valueOf(k));
                            cellBean.setId(CommonTools.Obj2String(map.get("ID")));
                            rowBean.addCell(cellBean);
                            break;
                        }
                    }
                }
                checkListTableBean.addRow(rowBean);
            }
        }
        //表头表尾内容
        cellBM.clearAllFilter();
        cellBM.setReserve_filter("AND " + tableName + "_" + schemaId + "_ID='" + checkTableInstId + "'" + " AND C_IS_HEADER_" + cellBM.getId() + "='" + "true" + "'");
        List<Map> cellHeaderList = orientSqlEngine.getBmService().createModelQuery(cellBM).orderAsc("TO_NUMBER(ID)").list();
        if (cellHeaderList.size() > 0) {
            for (Map cellMap : cellHeaderList) {
                String id = CommonTools.Obj2String(cellMap.get("ID"));
                String name = CommonTools.Obj2String(cellMap.get("C_CONTENT_" + cellBM.getId()));
                String cellType = CommonTools.Obj2String(cellMap.get("C_CELL_TYPE_" + cellBM.getId()));
                String isHeaderOrEnd = CommonTools.Obj2String(cellMap.get("C_IS_HEADER_" + cellBM.getId()));
                FrontContentBean frontContentBean = new FrontContentBean();
                frontContentBean.setId(id);
                frontContentBean.setCellType(cellType.substring(1, cellType.length()));
                frontContentBean.setName(name);
                if ("是".equals(tableIsRepeatUpload) || isTableComplete) {
                    if (uploadCellContentList != null && uploadCellContentList.size() != 0) {
                        for (Map cellContentMap : uploadCellContentList) {
                            if (id.equals(CommonTools.Obj2String(cellContentMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID")))) {
                                String cellValue = CommonTools.Obj2String(cellContentMap.get("C_CONTENT_" + uploadCellBM.getId()));
                                frontContentBean.setContent(cellValue);
                                break;
                            }
                        }
                    }
                    //2021.2.2下发故障单元格内容给终端
//                    if ("#9".equals(cellType)) {
//                        troubleBM.clearAllFilter();
//                        troubleBM.setReserve_filter(" AND C_CELL_ID_" + troubleBM.getId() + "='" + id + "' AND C_DEVICE_STATE_" + troubleBM.getId() + "='单元格故障'");
//                        List<Map> troubleList = orientSqlEngine.getBmService().createModelQuery(troubleBM).list();
//                        if (troubleList != null && troubleList.size() > 0) {
//                            String troubleCellDesp = CommonTools.Obj2String(troubleList.get(0).get("C_DESCRIPTION_" + troubleBM.getId()));
//                            frontContentBean.setTroubleCellDesp(troubleCellDesp);
//                        }
//                    }
                }
                checkListTableBean.addFrontContent(frontContentBean);
            }
        }
        cellBM.clearAllFilter();
        cellBM.setReserve_filter("AND " + tableName + "_" + schemaId + "_ID='" + checkTableInstId + "'" + " AND C_IS_HEADER_" + cellBM.getId() + "='" + "false" + "'");
        List<Map> cellEndList = orientSqlEngine.getBmService().createModelQuery(cellBM).orderAsc("TO_NUMBER(ID)").list();
        if (cellEndList.size() > 0) {
            for (Map cellMap : cellEndList) {
                String id = CommonTools.Obj2String(cellMap.get("ID"));
                String name = CommonTools.Obj2String(cellMap.get("C_CONTENT_" + cellBM.getId()));
                String cellType = CommonTools.Obj2String(cellMap.get("C_CELL_TYPE_" + cellBM.getId()));
                EndContentBean endContentBean = new EndContentBean();
                endContentBean.setId(id);
                endContentBean.setName(name);
                endContentBean.setCellType(cellType.substring(1, cellType.length()));
                if ("是".equals(tableIsRepeatUpload) || isTableComplete) {
                    if (uploadCellContentList != null && uploadCellContentList.size() != 0) {
                        for (Map cellContentMap : uploadCellContentList) {
                            if (id.equals(CommonTools.Obj2String(cellContentMap.get("T_CHECK_CELL_INST_" + schemaId + "_ID")))) {
                                String cellValue = CommonTools.Obj2String(cellContentMap.get("C_CONTENT_" + uploadCellBM.getId()));
                                endContentBean.setContent(cellValue);
                                break;
                            }
                        }
                    }
                    //2021.2.2下发故障单元格内容给终端
//                    if ("#9".equals(cellType)) {
//                        troubleBM.clearAllFilter();
//                        troubleBM.setReserve_filter(" AND C_CELL_ID_" + troubleBM.getId() + "='" + id + "' AND C_DEVICE_STATE_" + troubleBM.getId() + "='单元格故障'");
//                        List<Map> troubleList = orientSqlEngine.getBmService().createModelQuery(troubleBM).list();
//                        if (troubleList != null && troubleList.size() > 0) {
//                            String troubleCellDesp = CommonTools.Obj2String(troubleList.get(0).get("C_DESCRIPTION_" + troubleBM.getId()));
//                            endContentBean.setTroubleCellDesp(troubleCellDesp);
//                        }
//                    }
                }
                checkListTableBean.addEndContent(endContentBean);
            }
        }
        return checkListTableBean;
    }


    public HttpResponse<List<DeviceModel>> getDeviceInstList() {
        HttpResponse retVal = new HttpResponse();
        IBusinessModel deviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map<String, String>> deviceList = commonGetList(deviceBM);
        IBusinessModel deviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map<String, String>> deviceInstList = commonGetList(deviceInstBM);
        List<DeviceModel> deviceModelList = UtilFactory.newArrayList();
        if (deviceList.size() > 0) {
            for (Map deviceMap : deviceList) {
                DeviceModel deviceModel = new DeviceModel();
                String deviceName = CommonTools.Obj2String(deviceMap.get("C_DEVICE_NAME_" + deviceBM.getId()));
                String deviceId = CommonTools.Obj2String(deviceMap.get("ID"));
                String model = CommonTools.Obj2String(deviceMap.get("C_MODEL_" + deviceBM.getId()));
                deviceModel.setId(deviceId);
                deviceModel.setName(deviceName);
                deviceModel.setDeviceVersion(CommonTools.Obj2String(deviceMap.get("C_VERSION_" + deviceBM.getId())));
                if (deviceInstList.size() > 0) {
                    List<DeviceInstBean> deviceInstBeanList = new ArrayList<>();
                    for (Map deviceInstMap : deviceInstList) {
                        DeviceInstBean deviceInstBean = new DeviceInstBean();
                        String refDeviceId = CommonTools.Obj2String(deviceInstMap.get("T_SPARE_PARTS_" + schemaId + "_ID"));
                        if (deviceId.equals(refDeviceId)) {
                            deviceInstBean.setId(CommonTools.Obj2String(deviceInstMap.get("ID")));
                            deviceInstBean.setName(deviceName);
                            deviceInstBean.setBianhao(CommonTools.Obj2String(deviceInstMap.get("C_SERIAL_NUMBER_" + deviceInstBM.getId())));
                            deviceInstBean.setState(CommonTools.Obj2String(deviceInstMap.get("C_STATE_" + deviceInstBM.getId())));
                            deviceInstBean.setLocation(CommonTools.Obj2String(deviceInstMap.get("C_POSITION_" + deviceInstBM.getId())));
                            deviceInstBean.setLiezhuangTime(CommonTools.Obj2String(deviceInstMap.get("C_LIEZHUANG_TIME_" + deviceInstBM.getId())));
                            deviceInstBean.setDeviceId(deviceId);
                            deviceInstBean.setVersion(CommonTools.Obj2String(deviceInstMap.get("C_VERSION_" + deviceInstBM.getId())));
                            deviceInstBean.setModel(model);
                            deviceInstBeanList.add(deviceInstBean);
                        }
                    }
                    deviceModel.setDeviceCaseModels(deviceInstBeanList);
                }
                deviceModelList.add(deviceModel);
            }
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
            retVal.setData(deviceModelList);
        } else {
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
            retVal.setData(new ArrayList<>());
//            retVal.setMsg("服务端数据有误！");
        }
        return retVal;
    }

    public HttpResponse<List<DeviceBean>> getConsumeList() {
        HttpResponse retVal = new HttpResponse();
        IBusinessModel deviceModel = businessModelService.getBusinessModelBySName(PropertyConstant.SPARE_PARTS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel consumeModel = businessModelService.getBusinessModelBySName(PropertyConstant.CONSUME_DETAIL, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map<String, Object>> consumeList = orientSqlEngine.getBmService().createModelQuery(consumeModel).list();

        StringBuilder sql = new StringBuilder();
        sql.append("select MAIN_DATA_ID,SUB_DATA_ID from CWM_RELATION_DATA").append(" where 1=1").append(" AND MAIN_TABLE_NAME=?").append(" AND SUB_TABLE_NAME=?");
        List<Map<String, Object>> consumeIdsLsit = jdbcTemplate.queryForList(sql.toString(), "T_SPARE_PARTS_" + schemaId, "T_CONSUME_DETAIL_" + schemaId);

        List<Map> deviceList = orientSqlEngine.getBmService().createModelQuery(deviceModel).list();
        if (deviceList.size() > 0) {
            List<DeviceBean> deviceBeanList = UtilFactory.newArrayList();
            for (Map deviceMap : deviceList) {
                DeviceBean deviceBean = new DeviceBean();
                String deviceId = (String) deviceMap.get("ID");
                deviceBean.setId(deviceId);
                deviceBean.setDeviceName((String) deviceMap.get("C_DEVICE_NAME_" + deviceModel.getId()));
                deviceBean.setVersion((String) deviceMap.get("C_VERSION_" + deviceModel.getId()));
                if (consumeIdsLsit.size() > 0) {
                    List<ConsumeBean> consumeBeanList = UtilFactory.newArrayList();
                    for (Map relMap : consumeIdsLsit) {
                        String deviceRekId = (String) relMap.get("MAIN_DATA_ID");
                        String consumeRekId = (String) relMap.get("SUB_DATA_ID");
                        if ((consumeRekId != null && !"".equals(consumeRekId)) && deviceId.equals(deviceRekId)) {
                            if (consumeList.size() > 0) {
                                for (Map consumeMap : consumeList) {
                                    ConsumeBean consumeBean = new ConsumeBean();
                                    String consumeId = (String) consumeMap.get("ID");
                                    if (consumeRekId.equals(consumeId)) {
                                        consumeBean.setId((String) consumeMap.get("ID"));
                                        consumeBean.setName((String) consumeMap.get("C_NAME_" + consumeModel.getId()));
                                        consumeBean.setModel((String) consumeMap.get("C_MODEL_" + consumeModel.getId()));
                                        String consumeNumer = (String) consumeMap.get("C_NUMBER_" + consumeModel.getId());
                                        if (consumeNumer == null || "".equals(consumeNumer)) {
                                            consumeNumer = "0";
                                        }
                                        consumeBean.setNumber(Integer.parseInt(consumeNumer));
                                        consumeBean.setVersion((String) consumeMap.get("C_VERSION_" + consumeModel.getId()));
                                        consumeBeanList.add(consumeBean);
                                    }
                                }

                            }
                        }
                    }
                    if (consumeBeanList.size() > 0) {
                        deviceBean.setConsumeBeanList(consumeBeanList);
                        deviceBeanList.add(deviceBean);
                    }
                }
            }
            retVal.setData(deviceBeanList);
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
        } else {
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
            retVal.setData(new ArrayList<>());
//            retVal.setMsg("服务端无数据！");
        }
        return retVal;
    }

    public void downLoadImages(String cellId, String type, HttpServletResponse response) throws Exception {
        OutputStream os = null;

        IBusinessModel bm = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        String folderName = "imagesForChecklist";

        if ("2".equals(type)) {
            bm = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
            bm.setReserve_filter("AND C_CELL_ID_" + bm.getId() + "='" + cellId + "'" + " AND C_DEVICE_STATE_" + bm.getId() + "='单元格故障'");
            List<Map> troubleList = orientSqlEngine.getBmService().createModelQuery(bm).list();
            if (troubleList.size() > 0) {
                //故障主键ID
                cellId = troubleList.get(0).get("ID").toString();
            }
            folderName = "imagesForTDevice";
        }
        String modelId = bm.getId();

        String imageFolderPath = fileServerConfig.getFtpHome() + File.separator + folderName;

        String outputFileNamePath = fileServerConfig.getFtpHome() + File.separator + folderName + File.separator + new Date().getTime() + ".zip";

        String sql = "select * from cwm_file where DATAID='" + cellId + "' and TABLEID='" + modelId + "'";
        if ("2".equals(type)) {
            sql = "select * from cwm_file where DATAID='" + cellId + "' and TABLEID='" + modelId + "' and FILETYPE='jpg'";
        }
        List<Map<String, Object>> fileList = jdbcTemplate.queryForList(sql);

        if (fileList.size() > 0) {
            List<Map> fileNames = new ArrayList<>();
            for (int i = 0; i < fileList.size(); i++) {
                Map fileMap = UtilFactory.newHashMap();
                String finalName = (String) fileList.get(i).get("FINALNAME");
                String fileName = (String) fileList.get(i).get("FILENAME");
                String imageFile = imageFolderPath + File.separator + finalName;
                imageFile = FileOperator.toStanderds(imageFile);
                Boolean isFileExist = FileOperator.isFileExist(imageFile);
                if (isFileExist) {
                    fileMap.put("path", imageFile);
                    fileMap.put("fileName", fileName);
                    fileNames.add(fileMap);
                }
            }
            String destFileName = "" + new Date().getTime();
            try {
                String filename = new String((destFileName + ".zip").getBytes("UTF-8"), "ISO8859-1");//控制文件名编码
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(bos);
                UrlFilesToZip s = new UrlFilesToZip();
                int idx = 1;
                String postfix = "";
                for (Map imageMap : fileNames) {
                    String path = CommonTools.Obj2String(imageMap.get("path"));
                    String fileName = CommonTools.Obj2String(imageMap.get("fileName"));
//                    if (!(oneFile == null || oneFile.indexOf(".") == -1)) {
//                        //如果图片地址为null或者地址中没有"."就返回""
//                        postfix = oneFile.substring(oneFile.lastIndexOf(".") + 1).trim().toLowerCase();
//                    }
//                    if (postfix != null && !postfix.isEmpty()) {
//                        postfix = "." + postfix;
//                    }


                    //destFileName + idx+postfix :  图片名称
                    //destFileName ： 压缩包名称
//                    zos.putNextEntry(new java.util.zip.ZipEntry(destFileName + idx + postfix));
                    zos.putNextEntry(new java.util.zip.ZipEntry(fileName));
//                    byte[] bytes = s.getImageFromURL(oneFile);
                    File file = new File(path);
                    InputStream in = new FileInputStream(file);
                    byte[] bytes = s.readInputStream(in);
                    zos.write(bytes, 0, bytes.length);
                    zos.closeEntry();
                    idx++;
                }
                zos.close();
                response.setContentType("application/octet-stream; charset=utf-8");
//                 response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
                os = response.getOutputStream();
                os.write(bos.toByteArray());
                bos.close();
                os.flush();
                os.close();
//                os.close();
            } catch (FileNotFoundException ex) {
                logger.error("FileNotFoundException", ex);
            } catch (Exception ex) {
                logger.error("Exception", ex);
            }
//            return os;
        }
//        return os;
    }

    public BaseEntity<CurrentStateBean> getInformList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        response.setCharacterEncoding("utf-8");
        CurrentStateBean currentStateBean = new CurrentStateBean();
        IBusinessModel informStateBM = businessModelService.getBusinessModelBySName(PropertyConstant.INFORM_STATE, schemaId, EnumInter.BusinessModelEnum.Table);
        informStateBM.setReserve_filter("AND C_STATE_" + informStateBM.getId() + "='" + "current" + "'");
        List<Map> informStateList = orientSqlEngine.getBmService().createModelQuery(informStateBM).orderAsc("TO_NUMBER(ID)").list();
        BaseEntity retVal = new BaseEntity();
        IBusinessModel informBM = businessModelService.getBusinessModelBySName(PropertyConstant.INFORM_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        List<Map> informList = orientSqlEngine.getBmService().createModelQuery(informBM).orderAsc("TO_NUMBER(ID)").list();

        if (informStateList.size() > 0) {
            Map informStateMap = informStateList.get(0);
            currentStateBean.setId((String) informStateMap.get("ID"));
            currentStateBean.setName((String) informStateMap.get("C_NAME_" + informStateBM.getId()));
            if (informList.size() > 0) {
                List<InformBean> informBeanList = UtilFactory.newArrayList();
                for (Map informMap : informList) {
                    InformBean informBean = new InformBean();
                    informBean.setId((String) informMap.get("ID"));
                    informBean.setDepartment((String) informMap.get("C_DEPARTMENT_" + informBM.getId()));
                    String informContent = (String) informMap.get("C_INFORM_" + informBM.getId());
                    if (informContent != null && !"".equals(informContent)) {
                        String Content = informContent.replace("\n", "</br>");
                        informBean.setInformContent(Content);
                    } else {
                        informBean.setInformContent(informContent);
                    }
                    informBean.setDate((String) informMap.get("C_PUBLISH_TIME_" + informBM.getId()));
                    String userId = UserContextUtil.getUserId();
                    informBean.setPublisher(userId);
                    informBeanList.add(informBean);
                }
                currentStateBean.setInformBeanList(informBeanList);
//            response.setContentType("text/html");
//            response.getWriter().write(retVal.toString());
                response.addHeader("Access-Control-Allow-Origin", "*");
                retVal.setResult(currentStateBean);
                retVal.setMsg("成功！");
                retVal.setCode(200);
            }
        } else {
            retVal.setResult(new ArrayList<>());
//            retVal.setMsg("服务端无数据！");
        }
        return retVal;
    }

    public HttpResponse<DivingTaskBean> getDestroyCurrentTask() {
        HttpResponse retVal = new HttpResponse<>();
        IBusinessModel destroyTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel flowBM = businessModelService.getBusinessModelBySName(PropertyConstant.DESTROY_FLOW, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel refPostBM = businessModelService.getBusinessModelBySName(PropertyConstant.REF_POST_NODE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel postBM = businessModelService.getBusinessModelBySName(PropertyConstant.POST_MGR, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel fileBM = businessModelService.getBusinessModelBySName(PropertyConstant.SKILL_DOCUMENT, schemaId, EnumInter.BusinessModelEnum.Table);
        destroyTaskBM.setReserve_filter("AND C_STATE_" + destroyTaskBM.getId() + "='" + "进行中" + "'");
        List<Map<String, String>> destroyTaskList = commonGetList(destroyTaskBM);

        if (destroyTaskList.size() > 0) {
            for (Map taskMap : destroyTaskList) {
                DivingTaskBean destroyTaskBean = new DivingTaskBean();
                String taskId = CommonTools.Obj2String(taskMap.get("ID"));
                destroyTaskBean.setId(taskId);
                destroyTaskBean.setTaskName(CommonTools.Obj2String(taskMap.get("C_NAME_" + destroyTaskBM.getId())));
                flowBM.clearAllFilter();
                flowBM.setReserve_filter("AND T_DESTROY_TASK_" + schemaId + "_ID='" + taskId + "'");
                List<Map<String, String>> flowList = orientSqlEngine.getBmService().createModelQuery(flowBM).list();
                List<FlowBean> flowBeanList = new ArrayList<>();
                if (flowList.size() > 0) {
                    for (Map flowMap : flowList) {
                        FlowBean flowBean = new FlowBean();
                        String flowId = CommonTools.Obj2String(flowMap.get("ID"));
                        flowBean.setId(CommonTools.Obj2String(flowMap.get("ID")));
                        flowBean.setName(CommonTools.Obj2String(flowMap.get("C_NAME_" + flowBM.getId())));
                        String flowStartDate = CommonTools.Obj2String(flowMap.get("C_FLOW_START_TIME_" + flowBM.getId()));
                        String flowEndDate = CommonTools.Obj2String(flowMap.get("C_FLOW_END_TIME_" + flowBM.getId()));
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            if (!"".equals(flowStartDate)) {
                                flowStartDate = simpleDateFormat.format(simpleDateFormat.parse(flowStartDate));
                            }
                            if (!"".equals(flowEndDate)) {
                                flowEndDate = simpleDateFormat.format(simpleDateFormat.parse(flowEndDate));
                            }
                            flowBean.setBeginDate(flowStartDate);
                            flowBean.setEndDate(flowEndDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        List<CheckTempInstBean> checkTempInstBeanList = new ArrayList<>();
                        checkTempInstBM.clearAllFilter();
                        checkTempInstBM.setReserve_filter("AND T_DESTROY_FLOW_" + schemaId + "_ID='" + flowId + "'");
                        List<Map<String, String>> destroyCheckTempInstList = commonGetList(checkTempInstBM);
                        if (destroyCheckTempInstList.size() > 0) {
                            for (Map<String, String> destroyCheckMap : destroyCheckTempInstList) {
                                CheckTempInstBean destroyCheckTempInstBean = new CheckTempInstBean();
                                destroyCheckTempInstBean.setId(destroyCheckMap.get("ID"));
                                destroyCheckTempInstBean.setCheckName(destroyCheckMap.get("C_NAME_" + checkTempInstBM.getId()));
                                destroyCheckTempInstBean.setState(destroyCheckMap.get("C_CHECK_STATE_" + checkTempInstBM.getId()));
                                destroyCheckTempInstBean.setCheckTableType(destroyCheckMap.get("C_INS_TYPE_" + checkTempInstBM.getId()));
                                checkTempInstBeanList.add(destroyCheckTempInstBean);
                            }
                        }
                        flowBean.setCheckTempInstBeanList(checkTempInstBeanList);
                        refPostBM.clearAllFilter();
                        refPostBM.setReserve_filter("AND T_DESTROY_FLOW_" + schemaId + "_ID='" + flowId + "'");
                        List<Map<String, String>> postList = commonGetList(refPostBM);
                        List<PostBean> postBeanList = new ArrayList<>();
                        if (postList.size() > 0) {
                            for (Map<String, String> refPostMap : postList) {
                                PostBean refPostBean = new PostBean();
                                refPostBean.setId(refPostMap.get("ID"));
                                String postId = refPostMap.get("C_POST_ID_" + refPostBM.getId());
                                String attendPersonIds = refPostMap.get("C_POST_PERSONNEL_" + refPostBM.getId());
                                postBM.clearAllFilter();
                                postBM.setReserve_filter("AND ID='" + postId + "'");
                                Map<String, String> postMap = commonGetList(postBM).get(0);
                                if (!postMap.isEmpty()) {
                                    refPostBean.setPostName(postMap.get("C_POST_NAME_" + postBM.getId()));
                                }
                                if (StringUtil.isNotEmpty(attendPersonIds)) {
                                    StringBuilder userSql = new StringBuilder();
                                    userSql.append("select id,all_name from cwm_sys_user where id in(").append(attendPersonIds).append(")");
                                    List<Map<String, Object>> userList = metaDaoFactory.getJdbcTemplate().queryForList(userSql.toString());
                                    String allNames = "";
                                    if (userList.size() > 0) {
                                        for (Map userMap : userList) {
                                            String allName = CommonTools.Obj2String(userMap.get("all_name"));
                                            allNames += allName + ",";
                                        }
                                        allNames = allNames.substring(0, allNames.length() - 1);
                                        refPostBean.setFillPerson(allNames);
                                    }
                                }
                                postBeanList.add(refPostBean);
                            }
                        }
                        flowBean.setPostBeanList(postBeanList);

                        fileBM.clearAllFilter();
                        fileBM.setReserve_filter("AND T_DESTROY_FLOW_" + schemaId + "_ID='" + flowId + "'");
                        List<Map<String, String>> destroyFiletList = commonGetList(fileBM);
                        List<String> fileList = new ArrayList<>();
                        if (destroyFiletList.size() > 0) {
                            for (Map fileMap : destroyFiletList) {
                                String fileId = CommonTools.Obj2String(fileMap.get("ID"));
                                fileList.add(fileId);
                            }
                        }
                        flowBean.setFileIds(fileList);
                        flowBeanList.add(flowBean);
                    }
                }
                destroyTaskBean.setFlowBeanList(flowBeanList);
                retVal.setData(destroyTaskBean);
                retVal.setResult((HttpResponseStatus.SUCCESS.toString()));
            }
        }
        return retVal;
    }

    /**
     * 下载文件
     *
     * @param destroyFileId
     * @param response
     * @return
     */
    public void downloadFile(String destroyFileId, HttpServletResponse response) {
        IBusinessModel fileBM = businessModelService.getBusinessModelBySName(PropertyConstant.SKILL_DOCUMENT, schemaId, EnumInter.BusinessModelEnum.Table);
        fileBM.setReserve_filter("AND ID='" + destroyFileId + "'");
        List<Map<String, String>> destroyFiletList = commonGetList(fileBM);
        if (destroyFiletList.size() > 0) {
            for (Map fileMap : destroyFiletList) {
                String destroyFileName = CommonTools.Obj2String(fileMap.get("C_FILE_NAME_" + fileBM.getId()));
                try {
                    JSONArray jsonArray = new JSONArray(destroyFileName);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String fileId = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        CwmFile cwmFile = fileService.findFileById(fileId);
                        if (cwmFile != null) {
                            String fileName = cwmFile.getFilename();
                            //通知浏览器解析时使用的码表
                            response.setContentType("aplication/octet-stream;charset=UTF-8");
                            //通知浏览器当前服务器发送的数据格式
                            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                            File file = new File(fileServerConfig.getFtpHome() + cwmFile.getFilelocation());
                            if (!file.exists()) {
                                return;
                            } else {
                                BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileServerConfig.getFtpHome() + cwmFile.getFilelocation()));
                                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                                byte[] buffer = new byte[2048];
                                int len = 0;
                                while ((len = in.read(buffer)) != -1) {
                                    out.write(buffer, 0, len);
                                }
                                in.close();
                                out.flush();
                                out.close();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HttpResponse getIncreaseTableCellId(String checkTableId, String headerId, String rowNumber) {
        HttpResponse httpResponse = new HttpResponse();
        String cellInstId = "";
        IBusinessModel rowInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_ROW_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel cellInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        rowInstBM.setReserve_filter("AND C_ROW_NUMBER_" + rowInstBM.getId() + "='" + rowNumber + "'" +
                " AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + checkTableId + "'");
        List<Map<String, Object>> rowInstList = orientSqlEngine.getBmService().createModelQuery(rowInstBM).list();
        if (rowInstList.size() > 0) {
            String rowInstId = CommonTools.Obj2String(rowInstList.get(0).get("ID"));
            cellInstBM.setReserve_filter("AND T_CHECK_TEMP_INST_" + schemaId + "_ID='" + checkTableId + "'" +
                    " AND T_CHECK_HEADER_INST_" + schemaId + "_ID='" + headerId + "'" +
                    " AND T_CHECK_ROW_INST_" + schemaId + "_ID='" + rowInstId + "'");
            List<Map<String, Object>> cellInstList = orientSqlEngine.getBmService().createModelQuery(cellInstBM).list();
            if (cellInstList.size() > 0) {
                cellInstId = CommonTools.Obj2String(cellInstList.get(0).get("ID"));
            }
            httpResponse.setResult(HttpResponseStatus.SUCCESS.toString());
            httpResponse.setData(cellInstId);
        }
        return httpResponse;
    }

    public HttpResponse<DivingQueryModel> getEndTaskList() {
        HttpResponse retVal = new HttpResponse();
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingStatisticBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_STATISTICS, schemaId, EnumInter.BusinessModelEnum.Table);
        Map<String, User> allUsers = roleEngine.getRoleModel(false).getUsers();
        DivingQueryModel divingQueryModel = new DivingQueryModel();
        StringBuilder statisticSql = new StringBuilder();
        statisticSql.append("select s.*,c.id as checkInstId,c.C_NAME_" + checkTempInstBM.getId()).append(",c.C_CHECK_PERSON_" + checkTempInstBM.getId()).append(",c.C_CHECK_TIME_" + checkTempInstBM.getId())
                .append(" from T_DIVING_STATISTICS_" + schemaId).append(" s left join T_CHECK_TEMP_INST_" + schemaId)
                .append(" c on s.T_DIVING_TASK_" + schemaId + "_ID=c.T_DIVING_TASK_" + schemaId + "_ID order by s.C_DIVING_TASK_" + divingStatisticBM.getId() + " asc");
        List<Map<String, Object>> divingStatisticList = jdbcTemplate.queryForList(statisticSql.toString());
        List<DiveNumModel> diveNumModelList = UtilFactory.newArrayList();
        if (divingStatisticList!=null&&divingStatisticList.size() > 0) {
            DiveNumModel diveNumModel = new DiveNumModel();
            DiveDetailModel diveDetailModel = new DiveDetailModel();
            List<TableSimpleModel> tableSimpleModelList = new LinkedList<>();
            for (Map statisticMap : divingStatisticList) {
                String divingTaskName = CommonTools.Obj2String(statisticMap.get("C_DIVING_TASK_" + divingStatisticBM.getId()));
                String divingTime = CommonTools.Obj2String(statisticMap.get("C_DIVING_TIME_" + divingStatisticBM.getId()));
                String seaArea = CommonTools.Obj2String(statisticMap.get("C_SEA_AREA_" + divingStatisticBM.getId()));
                String depth = CommonTools.Obj2String(statisticMap.get("C_DEPTH_" + divingStatisticBM.getId()));
                String workTimeLong = CommonTools.Obj2String(statisticMap.get("C_WORK_TIME_LONG_" + divingStatisticBM.getId()));
                String waterTimeLong = CommonTools.Obj2String(statisticMap.get("C_WATER_TIME_LONG_" + divingStatisticBM.getId()));
                String zuoxianCompany = CommonTools.Obj2String(statisticMap.get("C_ZX_COMPANY_" + divingStatisticBM.getId()));
                String youxianCompany = CommonTools.Obj2String(statisticMap.get("C_COMPANY_" + divingStatisticBM.getId()));
                String workContent = CommonTools.Obj2String(statisticMap.get("C_WORK_CONTENT_" + divingStatisticBM.getId()));
                String jingdu = CommonTools.Obj2String(statisticMap.get("C_LONGITUDE_" + divingStatisticBM.getId()));
                String weidu = CommonTools.Obj2String(statisticMap.get("C_LATITUDE_" + divingStatisticBM.getId()));
                String zmyPerson = CommonTools.Obj2String(statisticMap.get("C_PERSON_" + divingStatisticBM.getId()));
                String hangciName = CommonTools.Obj2String(statisticMap.get("C_HANGCI_" + divingStatisticBM.getId()));
                String hangduanName = CommonTools.Obj2String(statisticMap.get("C_HANGDUAN_" + divingStatisticBM.getId()));
                String eastWestHalfsphere = CommonTools.Obj2String(statisticMap.get("eastWestHalfsphere".toUpperCase() + "_" + divingStatisticBM.getId()));
                String southNorthHalfsphere = CommonTools.Obj2String(statisticMap.get("southNorthHalfsphere".toUpperCase() + "_" + divingStatisticBM.getId()));
                String timeZone = CommonTools.Obj2String(statisticMap.get("timeZone".toUpperCase() + "_" + divingStatisticBM.getId()));
                String bufangType = CommonTools.Obj2String(statisticMap.get("bufangType".toUpperCase() + "_" + divingStatisticBM.getId()));
                String sampleSituation = CommonTools.Obj2String(statisticMap.get("sampleSituation".toUpperCase() + "_" + divingStatisticBM.getId()));
                String seafloorEnvironmentDesp = CommonTools.Obj2String(statisticMap.get("seafloorEnvironmentDesp".toUpperCase() + "_" + divingStatisticBM.getId()));
                String waterDownPictures = CommonTools.Obj2String(statisticMap.get("waterDownPictures".toUpperCase() + "_" + divingStatisticBM.getId()));
                String bufangCommandTime = CommonTools.Obj2String(statisticMap.get("bufangCommandTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String personComeinCabinTime = CommonTools.Obj2String(statisticMap.get("personComeinCabinTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String hatchCloseTime = CommonTools.Obj2String(statisticMap.get("hatchCloseTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String ballastRemoveTime = CommonTools.Obj2String(statisticMap.get("ballastRemoveTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String divingDEntryTime = CommonTools.Obj2String(statisticMap.get("divingDEntryTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String startFillWaterTime = CommonTools.Obj2String(statisticMap.get("startFillWaterTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String startWorkTime = CommonTools.Obj2String(statisticMap.get("startWorkTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String workStartDepth = CommonTools.Obj2String(statisticMap.get("workStartDepth".toUpperCase() + "_" + divingStatisticBM.getId()));
                String endWorkTime = CommonTools.Obj2String(statisticMap.get("endWorkTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String workEndDepth = CommonTools.Obj2String(statisticMap.get("workEndDepth".toUpperCase() + "_" + divingStatisticBM.getId()));
                String floatWaterTime = CommonTools.Obj2String(statisticMap.get("floatWaterTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String divingDOutWaterTime = CommonTools.Obj2String(statisticMap.get("divingDOutWaterTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String recoverDeckTime = CommonTools.Obj2String(statisticMap.get("recoverDeckTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String hatchOpenTime = CommonTools.Obj2String(statisticMap.get("hatchOpenTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String personComeOutCabinTime = CommonTools.Obj2String(statisticMap.get("personComeOutCabinTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String onceBufangSmallboatTime = CommonTools.Obj2String(statisticMap.get("onceBufangSmallboatTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String onceRecoverSmallboatTime = CommonTools.Obj2String(statisticMap.get("onceRecoverSmallboatTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String bufangSmallboatDriverPeople = CommonTools.Obj2String(statisticMap.get("bufangSmallboatDriverPeople".toUpperCase() + "_" + divingStatisticBM.getId()));
                String bufangSmallboatAssistant = CommonTools.Obj2String(statisticMap.get("bufangSmallboatAssistant".toUpperCase() + "_" + divingStatisticBM.getId()));
                String tuolanPeople = CommonTools.Obj2String(statisticMap.get("tuolanPeople".toUpperCase() + "_" + divingStatisticBM.getId()));
                String tuolanAssistant = CommonTools.Obj2String(statisticMap.get("tuolanAssistant".toUpperCase() + "_" + divingStatisticBM.getId()));
                String bufangMaxWindSpeed = CommonTools.Obj2String(statisticMap.get("bufangMaxWindSpeed".toUpperCase() + "_" + divingStatisticBM.getId()));
                String bufangAverageWindSpeed = CommonTools.Obj2String(statisticMap.get("bufangAverageWindSpeed".toUpperCase() + "_" + divingStatisticBM.getId()));
                String bufangLangHeight = CommonTools.Obj2String(statisticMap.get("bufangLangHeight".toUpperCase() + "_" + divingStatisticBM.getId()));
                String bufangSeaStateEstimate = CommonTools.Obj2String(statisticMap.get("bufangSeaStateEstimate".toUpperCase() + "_" + divingStatisticBM.getId()));
                String twiceBufangSmallboatTime = CommonTools.Obj2String(statisticMap.get("twiceBufangSmallboatTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String twiceRecoverSmallboatTime = CommonTools.Obj2String(statisticMap.get("twiceRecoverSmallboatTime".toUpperCase() + "_" + divingStatisticBM.getId()));
                String recoverSmallboatDperson = CommonTools.Obj2String(statisticMap.get("recoverSmallboatDperson".toUpperCase() + "_" + divingStatisticBM.getId()));
                String recoverSmallboatAssistant = CommonTools.Obj2String(statisticMap.get("recoverSmallboatAssistant".toUpperCase() + "_" + divingStatisticBM.getId()));
                String gualanPeople = CommonTools.Obj2String(statisticMap.get("gualanPeople".toUpperCase() + "_" + divingStatisticBM.getId()));
                String gualanAssistant = CommonTools.Obj2String(statisticMap.get("gualanAssistant".toUpperCase() + "_" + divingStatisticBM.getId()));
                String recoverMaxWindSpeed = CommonTools.Obj2String(statisticMap.get("recoverMaxWindSpeed".toUpperCase() + "_" + divingStatisticBM.getId()));
                String recoverMaxAverageWindSpeed = CommonTools.Obj2String(statisticMap.get("recoverMaxAverageWindSpeed".toUpperCase() + "_" + divingStatisticBM.getId()));
                String recoverLangHeight = CommonTools.Obj2String(statisticMap.get("recoverLangHeight".toUpperCase() + "_" + divingStatisticBM.getId()));
                String recoverSeaStateEstimate = CommonTools.Obj2String(statisticMap.get("recoverSeaStateEstimate".toUpperCase() + "_" + divingStatisticBM.getId()));
                String specialVersion = CommonTools.Obj2String(statisticMap.get("specialVersion".toUpperCase() + "_" + divingStatisticBM.getId()));
                String taskId = CommonTools.Obj2String(statisticMap.get("T_DIVING_TASK_" + schemaId + "_ID"));
                if (!"".equals(diveNumModel.getName())&&diveNumModel.getName()!=null) {
                    if (!divingTaskName.equals(diveNumModel.getName())) {
                        diveNumModel.setTableSimpleModels(tableSimpleModelList);
                        diveNumModelList.add(diveNumModel);
                        tableSimpleModelList = new LinkedList<>();
                        diveNumModel = new DiveNumModel();
                        diveDetailModel = new DiveDetailModel();
                    }
                }
                diveNumModel.setId(taskId);
                diveNumModel.setName(divingTaskName);
                diveDetailModel.setTaskId(taskId);
                diveDetailModel.setDivingTime(divingTime);
                diveDetailModel.setSeaArea(seaArea);
                diveDetailModel.setDepth(depth);
                diveDetailModel.setWorkTimeLong(workTimeLong);
                diveDetailModel.setWaterTimeLong(waterTimeLong);
                diveDetailModel.setZuoxianCompany(zuoxianCompany);
                diveDetailModel.setYouxianCompany(youxianCompany);
                diveDetailModel.setWorkContent(workContent);
                diveDetailModel.setJingdu(jingdu);
                diveDetailModel.setWeidu(weidu);
                String zmyPersonArray[] = zmyPerson.split("/");
                List<String> zmyPersonList = Arrays.asList(zmyPersonArray);
                if (zmyPersonList != null && zmyPersonList.size() > 0) {
                    for (int i = 0; i < zmyPersonList.size(); i++) {
                        if (i == 0) {
                            diveDetailModel.setZuoxianPerson(zmyPersonList.get(i));
                        } else if (i == 1) {
                            diveDetailModel.setMainDriverPerson(zmyPersonList.get(i));
                        } else if (i == 2) {
                            diveDetailModel.setYouxianPerson(zmyPersonList.get(i));
                        }
                    }
                }
                diveDetailModel.setHangciName(hangciName);
                diveDetailModel.setHangduanName(hangduanName);
                diveDetailModel.setEastWestHalfsphere(eastWestHalfsphere);
                diveDetailModel.setSouthNorthHalfsphere(southNorthHalfsphere);
                diveDetailModel.setTimeZone(timeZone);
                diveDetailModel.setBufangType(bufangType);
                diveDetailModel.setSampleSituation(sampleSituation);
                diveDetailModel.setSeafloorEnvironmentDesp(seafloorEnvironmentDesp);
                diveDetailModel.setBufangCommandTime(bufangCommandTime);
                diveDetailModel.setPersonComeinCabinTime(personComeinCabinTime);
                diveDetailModel.setHatchCloseTime(hatchCloseTime);
                diveDetailModel.setBallastRemoveTime(ballastRemoveTime);
                diveDetailModel.setDivingDEntryTime(divingDEntryTime);
                diveDetailModel.setStartFillWaterTime(startFillWaterTime);
                diveDetailModel.setStartWorkTime(startWorkTime);
                diveDetailModel.setWorkStartDepth(workStartDepth);
                diveDetailModel.setEndWorkTime(endWorkTime);
                diveDetailModel.setWorkEndDepth(workEndDepth);
                diveDetailModel.setFloatWaterTime(floatWaterTime);
                diveDetailModel.setDivingDOutWaterTime(divingDOutWaterTime);
                diveDetailModel.setRecoverDeckTime(recoverDeckTime);
                diveDetailModel.setHatchOpenTime(hatchOpenTime);
                diveDetailModel.setPersonComeOutCabinTime(personComeOutCabinTime);
                diveDetailModel.setOnceBufangSmallboatTime(onceBufangSmallboatTime);
                diveDetailModel.setOnceRecoverSmallboatTime(onceRecoverSmallboatTime);
                diveDetailModel.setBufangSmallboatDriverPeople(bufangSmallboatDriverPeople);
                diveDetailModel.setBufangSmallboatAssistant(bufangSmallboatAssistant);
                diveDetailModel.setTuolanPeople(tuolanPeople);
                diveDetailModel.setTuolanAssistant(tuolanAssistant);
                diveDetailModel.setBufangMaxWindSpeed(bufangMaxWindSpeed);
                diveDetailModel.setBufangAverageWindSpeed(bufangAverageWindSpeed);
                diveDetailModel.setBufangLangHeight(bufangLangHeight);
                diveDetailModel.setBufangSeaStateEstimate(bufangSeaStateEstimate);
                diveDetailModel.setTwiceBufangSmallboatTime(twiceBufangSmallboatTime);
                diveDetailModel.setTwiceRecoverSmallboatTime(twiceRecoverSmallboatTime);
                diveDetailModel.setRecoverSmallboatDperson(recoverSmallboatDperson);
                diveDetailModel.setRecoverSmallboatAssistant(recoverSmallboatAssistant);
                diveDetailModel.setGualanPeople(gualanPeople);
                diveDetailModel.setGualanAssistant(gualanAssistant);
                diveDetailModel.setRecoverMaxWindSpeed(recoverMaxWindSpeed);
                diveDetailModel.setRecoverMaxAverageWindSpeed(recoverMaxAverageWindSpeed);
                diveDetailModel.setRecoverLangHeight(recoverLangHeight);
                diveDetailModel.setRecoverSeaStateEstimate(recoverSeaStateEstimate);
                diveDetailModel.setSpecialVersion(specialVersion);
                diveNumModel.setDiveDetailModel(diveDetailModel);
                TableSimpleModel tableSimpleModel = new TableSimpleModel();
                String checkInstId = CommonTools.Obj2String(statisticMap.get("checkInstId"));
                String checkInstName = CommonTools.Obj2String(statisticMap.get("C_NAME_" + checkTempInstBM.getId()));
                String submitPerson = CommonTools.Obj2String(statisticMap.get("C_CHECK_PERSON_" + checkTempInstBM.getId()));
                String submitUserNames="";
                if (!"".equals(submitPerson)) {
                    String personIds[] = submitPerson.split(",");
                    for (String personId : personIds) {
                        if (allUsers.values() != null && allUsers.values().size() > 0) {
                            for (IUser user : allUsers.values()) {
                                String userId = user.getId();
                                String userName = user.getAllName();
                                if (personId.equals(userId)) {
                                    submitUserNames+=userName+",";
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!"".equals(submitUserNames)){
                    submitUserNames=submitUserNames.substring(0,submitUserNames.length()-1);
                }
                tableSimpleModel.setSubmitPerson(submitUserNames);
                String submitDate = CommonTools.Obj2String(statisticMap.get("C_CHECK_TIME_" + checkTempInstBM.getId()));
                tableSimpleModel.setId(checkInstId);
                tableSimpleModel.setName(checkInstName);
                tableSimpleModel.setDate(submitDate);
                tableSimpleModelList.add(tableSimpleModel);
            }
            divingQueryModel.setDiveNumModels(diveNumModelList);
            retVal.setData(divingQueryModel);
            retVal.setResult((HttpResponseStatus.SUCCESS.toString()));
        }
        return retVal;
    }

    public HttpResponse<TroubleDeviceBean> queryCellTrouble(String cellId) {
        HttpResponse retVal = new HttpResponse();
        TroubleDeviceBean troubleDeviceBean = new TroubleDeviceBean();
        IBusinessModel troubleBM = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        troubleBM.setReserve_filter("AND C_CELL_ID_" + troubleBM.getId() + "='" + cellId + "'");
        List<Map> troubleList = orientSqlEngine.getBmService().createModelQuery(troubleBM).list();
        if (troubleList.size() > 0) {
            Map troubleMap = troubleList.get(0);
            String troubleId = troubleMap.get("ID").toString();
            String clientId = CommonTools.Obj2String(troubleMap.get("C_NUMBER_ID_" + troubleBM.getId()));
            troubleDeviceBean.setId(clientId);
            troubleDeviceBean.setRefId(cellId);
            troubleDeviceBean.setDesc(CommonTools.Obj2String(troubleMap.get("C_DESCRIPTION_" + troubleBM.getId())));
            troubleDeviceBean.setDate(CommonTools.Obj2String(troubleMap.get("C_TROUBLE_DATE_" + troubleBM.getId())));
            StringBuilder cwmFileSql = new StringBuilder();
            cwmFileSql.append("select * from CWM_FILE").append(" WHERE TABLEID='").append(troubleBM.getId()).append("' AND DATAID='" + troubleId + "'");
            List<Map<String, Object>> cwmFileList = jdbcTemplate.queryForList(cwmFileSql.toString());
            int containPhotoCount = 0;
            int containAudioCount = 0;
            if (cwmFileList.size() > 0) {
                for (Map cwmFileMap : cwmFileList) {
                    String fileType = CommonTools.Obj2String(cwmFileMap.get("FILETYPE"));
                    if ("jpg".equals(fileType)) {
                        containPhotoCount++;
                    }
                    if ("amr".equals(fileType)) {
                        containAudioCount++;
                    }
                }
            }
            if (containPhotoCount > 0) {
                troubleDeviceBean.setContainPhoto(true);
            }
            if (containAudioCount > 0) {
                troubleDeviceBean.setContainAudio(true);
            }
            retVal.setData(troubleDeviceBean);
            retVal.setResult((HttpResponseStatus.SUCCESS.toString()));
        }
        return retVal;
    }

    public void downloadSingleFile(String cellId, String type, HttpServletResponse response) throws Exception {
        IBusinessModel bm = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_CELL_INST, schemaId, EnumInter.BusinessModelEnum.Table);
        String folderName = "imagesForChecklist";
        if ("2".equals(type)) {
            bm = businessModelService.getBusinessModelBySName(PropertyConstant.TROUBLE_DEVICE_INST, schemaId, EnumInter.BusinessModelEnum.Table);
            bm.setReserve_filter("AND C_CELL_ID_" + bm.getId() + "='" + cellId + "'" + " AND C_DEVICE_STATE_" + bm.getId() + "='单元格故障'");
            List<Map> troubleList = orientSqlEngine.getBmService().createModelQuery(bm).list();
            if (troubleList.size() > 0) {
                //故障主键ID
                cellId = troubleList.get(0).get("ID").toString();
            }
            folderName = "voiceFileForTDevice";
        }
        String modelId = bm.getId();
        String imageFolderPath = fileServerConfig.getFtpHome() + File.separator + folderName;
        String sql = "select * from cwm_file where DATAID='" + cellId + "' and TABLEID='" + modelId + "' order by UPLOAD_DATE DESC";
        if ("2".equals(type)) {
            sql = "select * from cwm_file where DATAID='" + cellId + "' and TABLEID='" + modelId + "' and FILETYPE='amr' order by UPLOAD_DATE DESC";
        }
        List<Map<String, Object>> fileList = jdbcTemplate.queryForList(sql);
        if (fileList != null && fileList.size() > 0) {
            Map fileMap = fileList.get(0);
            String fileName = (String) fileMap.get("FINALNAME");
            String imageFile = imageFolderPath + File.separator + fileName;
            imageFile = FileOperator.toStanderds(imageFile);
            Boolean isFileExist = FileOperator.isFileExist(imageFile);
            if (isFileExist) {
                //通知浏览器解析时使用的码表
                response.setContentType("aplication/octet-stream;charset=UTF-8");
                //通知浏览器当前服务器发送的数据格式
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentLength((int) new File(imageFile).length());
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(imageFile));
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                byte[] buffer = new byte[2048];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                in.close();
                out.flush();
                out.close();
            }
        }
    }

    public HttpResponse<List<StructSystemEntity>> getProductStructList() {
        HttpResponse retVal = new HttpResponse();
        IBusinessModel structSystemBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_SYSTEM, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structDeviceBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structDeviceInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.STRUCT_DEVICE_INS, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel structDeviceCycleBM = businessModelService.getBusinessModelBySName(PropertyConstant.CYCLE_DEVICE, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel checkTempInstBM = businessModelService.getBusinessModelBySName(PropertyConstant.CHECK_TEMP_INST, schemaId, EnumInter.BusinessModelEnum.Table);

        List<Map<String, String>> structSystemList = commonGetList(structSystemBM);
        List<Map<String, String>> structDeviceList = commonGetList(structDeviceBM);
        List<Map<String, String>> structDeviceInstList = commonGetList(structDeviceInstBM);
        List<Map<String, String>> structDeviceCycleList = commonGetList(structDeviceCycleBM);

        List<StructSystemEntity> structSystemEntityList = UtilFactory.newArrayList();
        if (structSystemList != null && structSystemList.size() > 0) {
            for (Map systemMap : structSystemList) {
                StructSystemEntity structSystemEntity = new StructSystemEntity();
                String systemId = CommonTools.Obj2String(systemMap.get("ID"));
                structSystemEntity.setId(systemMap.get("ID").toString());
                structSystemEntity.setName(CommonTools.Obj2String(systemMap.get("C_NAME_" + structSystemBM.getId())));
                structSystemEntity.setVersion(CommonTools.Obj2String(systemMap.get("C_VERSION_" + structSystemBM.getId())));
                if (structDeviceList != null && structDeviceList.size() > 0) {
                    LinkedList<StructDeviceEntity> structDeviceEntityList = new LinkedList<>();
                    for (Map deviceMap : structDeviceList) {
                        StructDeviceEntity structDeviceEntity = new StructDeviceEntity();
                        String deviceId = CommonTools.Obj2String(deviceMap.get("ID"));
                        String deviceName = CommonTools.Obj2String(deviceMap.get("C_NAME_" + structDeviceBM.getId()));
                        String refSystemId = CommonTools.Obj2String(deviceMap.get("T_STRUCT_SYSTEM_" + schemaId + "_ID"));
                        if (systemId.equals(refSystemId)) {
                            structDeviceEntity.setName(deviceName);
                            structDeviceEntity.setId(deviceId);
                            LinkedList<StructDeviceInstEntity> structDeviceInstEntities = new LinkedList<>();
                            if (structDeviceInstList != null && structDeviceInstList.size() > 0) {
                                for (Map deviceInstMap : structDeviceInstList) {
                                    StructDeviceInstEntity structDeviceInstEntity = new StructDeviceInstEntity();
                                    String deviceInstId = deviceInstMap.get("ID").toString();
                                    String refDeviceId = CommonTools.Obj2String(deviceInstMap.get("T_STRUCT_DEVICE_" + schemaId + "_ID"));
                                    if (deviceId.equals(refDeviceId)) {
                                        structDeviceInstEntity.setId(deviceInstId);
                                        structDeviceInstEntity.setName(deviceName);
                                        structDeviceInstEntity.setVersion(CommonTools.Obj2String(deviceInstMap.get("C_VERSION_" + structDeviceInstBM.getId())));
                                        structDeviceInstEntity.setNumber(CommonTools.Obj2String(deviceInstMap.get("C_NUMBER_" + structDeviceInstBM.getId())));
                                        structDeviceInstEntity.setDeviceId(deviceId);
                                        structDeviceInstEntities.add(structDeviceInstEntity);
                                    }
                                }
                                structDeviceEntity.setStructDeviceInstEntityList(structDeviceInstEntities);
                            }
                            if (structDeviceCycleList != null && structDeviceCycleList.size() > 0) {
                                LinkedList<StructDeviceCycleCheckEntity> structDeviceCycleCheckEntities = new LinkedList<>();
                                for (Map deviceCycleMap : structDeviceCycleList) {
                                    StructDeviceCycleCheckEntity structDeviceCycleCheckEntity = new StructDeviceCycleCheckEntity();
                                    String deviceCycleId = deviceCycleMap.get("ID").toString();
                                    String cycleName = CommonTools.Obj2String(deviceCycleMap.get("C_NAME_" + structDeviceCycleBM.getId()));
                                    String checkCycle=CommonTools.Obj2String(deviceCycleMap.get("C_CHECK_CYCLE_"+structDeviceCycleBM.getId()));
                                    String refDeviceId = CommonTools.Obj2String(deviceCycleMap.get("T_STRUCT_DEVICE_" + schemaId + "_ID"));
                                    if (deviceId.equals(refDeviceId)) {
                                        CheckTempInstBean checkTempInstBean = new CheckTempInstBean();
                                        structDeviceCycleCheckEntity.setName(cycleName);
                                        structDeviceCycleCheckEntity.setId(deviceCycleId);
                                        structDeviceCycleCheckEntity.setCheckCycle(checkCycle);
                                        structDeviceCycleCheckEntity.setDeviceId(deviceId);
                                        checkTempInstBM.clearAllFilter();
                                        checkTempInstBM.setReserve_filter("AND T_CYCLE_DEVICE_" + schemaId + "_ID='" + deviceCycleId + "'");
                                        List<Map<String, String>> checkTempInstList = commonGetList(checkTempInstBM);
                                        if (checkTempInstList != null && checkTempInstList.size() > 0) {
                                            LinkedList<CheckTempInstBean> checkTempInstBeans = new LinkedList<>();
                                            for (Map checkInstMap : checkTempInstList) {
                                                checkTempInstBean.setId(CommonTools.Obj2String(checkInstMap.get("ID")));
                                                checkTempInstBean.setCheckName(CommonTools.Obj2String(checkInstMap.get("C_NAME_" + checkTempInstBM.getId())));
//                                                checkTempInstBean.setState(CommonTools.Obj2String(checkInstMap.get("C_CHECK_STATE_" + checkTempInstBM.getId())));
                                                checkTempInstBean.setCheckTableType(CommonTools.Obj2String(checkInstMap.get("C_INS_TYPE_" + checkTempInstBM.getId())));
                                                String isRepeatUpload = CommonTools.Obj2String(checkInstMap.get("C_IS_REPEAT_UPLOAD_" + checkTempInstBM.getId()));
                                                isRepeatUpload = "".equals(isRepeatUpload) ? "否" : isRepeatUpload;
                                                checkTempInstBean.setIsRepeatUpload(isRepeatUpload);
                                                checkTempInstBeans.add(checkTempInstBean);
                                            }
                                            structDeviceCycleCheckEntity.setCheckTempInstBeanList(checkTempInstBeans);
                                        }
                                        structDeviceCycleCheckEntities.add(structDeviceCycleCheckEntity);
                                    }
                                }
                                structDeviceEntity.setStructDeviceCycleCheckEntityList(structDeviceCycleCheckEntities);
                            }
                            structDeviceEntityList.add(structDeviceEntity);
                        }
                    }
                    structSystemEntity.setStructDeviceList(structDeviceEntityList);
                }
                structSystemEntityList.add(structSystemEntity);
            }
            retVal.setData(structSystemEntityList);
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
        } else {
            retVal.setData(new ArrayList<>());
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
        }
        return retVal;
    }

    public HttpResponse<List<DivingTaskEntity>> getCurrentHdDivingTaskList() {
        IBusinessModel hangduanBM = businessModelService.getBusinessModelBySName(PropertyConstant.HANGDUAN, schemaId, EnumInter.BusinessModelEnum.Table);
        IBusinessModel divingTaskBM = businessModelService.getBusinessModelBySName(PropertyConstant.DIVING_TASK, schemaId, EnumInter.BusinessModelEnum.Table);
        HttpResponse retVal = new HttpResponse<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select t.ID,t.C_TASK_NAME_" + divingTaskBM.getId())
                .append(" from T_DIVING_TASK_" + schemaId).append(" t inner join T_HANGDUAN_" + schemaId + " d on t.t_hangduan_" + schemaId + "_id=d.id")
                .append(" where d.C_IS_START_" + hangduanBM.getId() + "='1' order by t.C_TASK_NAME_" + divingTaskBM.getId() + " asc");
        List<Map<String, Object>> divingTaskList = jdbcTemplate.queryForList(sql.toString());
        if (divingTaskList != null && divingTaskList.size() > 0) {
            List<DivingTaskEntity> divingTaskEntityList = new LinkedList<>();
            for (Map taskMap : divingTaskList) {
                DivingTaskEntity divingTaskEntity = new DivingTaskEntity();
                divingTaskEntity.setId(CommonTools.Obj2String(taskMap.get("ID").toString()));
                divingTaskEntity.setName(CommonTools.Obj2String(taskMap.get("C_TASK_NAME_" + divingTaskBM.getId())));
                divingTaskEntityList.add(divingTaskEntity);
            }
            retVal.setData(divingTaskEntityList);
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
        } else {
            retVal.setData(new ArrayList<>());
            retVal.setResult(HttpResponseStatus.SUCCESS.toString());
        }
        return retVal;
    }
}