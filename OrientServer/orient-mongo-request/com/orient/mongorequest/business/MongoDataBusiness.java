package com.orient.mongorequest.business;

import com.orient.edm.init.FileServerConfig;
import com.orient.mongorequest.config.MongoConfig;
import com.orient.mongorequest.domain.MatrixColumn;
import com.orient.mongorequest.domain.MatrixFileDesc;
import com.orient.mongorequest.model.*;
import com.orient.mongorequest.storage.service.IMatrixColumnService;
import com.orient.mongorequest.storage.service.IMatrixFileDescService;
import com.orient.mongorequest.utils.GsonUtil;
import com.orient.mongorequest.utils.MatrixDataPlotUtil;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.service.file.FileService;
import com.orient.utils.*;
import com.orient.utils.restful.DestURI;
import com.orient.utils.restful.RestfulClient;
import com.orient.utils.restful.RestfulResponse;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.apache.http.entity.ContentType;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-05-30 16:55
 */
@Component
public class MongoDataBusiness extends BaseBusiness {

    private static final String MODEL_ID = "modelId";
    private static final String DATA_ID = "dataId";
    private static final String FILE_NAME = "fileName";
    private static final String COLUMN_ORDER = "columnOrder";
    private static final String BELONG_FILE_DESC = "belongMatrixFileDesc";

    @Autowired
    IMatrixColumnService matrixColumnService;

    @Autowired
    IMatrixFileDescService matrixFileDescService;

    @Autowired
    FileServerConfig fileServerConfig;

    public AjaxResponseData deleteData(String modelId, String dataId) {
        //删除oracle表数据
        matrixColumnService.list(Restrictions.eq(MODEL_ID, modelId), Restrictions.eq(DATA_ID, dataId))
                .forEach(matrixColumn -> matrixColumnService.delete(matrixColumn));
        //删除mongo服务上的数据
        DestURI mongoUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, MongoConfig.MONGO_DELETE_DATA_URL + "/" + modelId + "_" + dataId, new HashMap<>());
        RestfulResponse<CommonResponse> response = RestfulClient.getHttpRestfulClient().getRequest(mongoUri, CommonResponse.class, ContentType.APPLICATION_JSON);
        boolean isDeleteMongoDataSuccess = response.getResult().isSuccess();
        //删除文件服务上的数据
        DestURI fileDeleteUri = new DestURI(MongoConfig.FILE_SERVER_HOST, MongoConfig.FILE_SERVER_PORT, MongoConfig.FILE_DELETE_URL + "/" + modelId + "/" + dataId, new HashMap<>());
        RestfulResponse<CommonAjaxResponse> fileDeleteResponse = RestfulClient.getHttpRestfulClient().getRequest(fileDeleteUri, CommonAjaxResponse.class, ContentType.APPLICATION_JSON);
        boolean isDeleteFileSuccess = fileDeleteResponse.getResult().isSuccess();
        AjaxResponseData retVal = new AjaxResponseData();
        retVal.setSuccess(isDeleteMongoDataSuccess && isDeleteFileSuccess);
        return retVal;
    }

    public AjaxResponseData updateVersion(String modelId, String dataId, String tabName) {
        AjaxResponseData retVal = new AjaxResponseData();
        DestURI updateVersionUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, MongoConfig.MONGO_UPDATE_VERSION_URL + "/" + modelId + "_" + dataId + "_" + tabName, new HashMap<>());
        RestfulResponse<CommonResponse> response = RestfulClient.getHttpRestfulClient().getRequest(updateVersionUri, CommonResponse.class, ContentType.APPLICATION_JSON);
        retVal.setSuccess(response.getResult().isSuccess());
        retVal.setResults(response.getResult().getResult());//获取当前show版本的版本号
        return retVal;
    }

    public AjaxResponseData<List<DataVersion>> queryVersionList(String modelId, String dataId, String tabName) {
        AjaxResponseData<List<DataVersion>> retVal = new AjaxResponseData<>();
        DestURI queryVersionListUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, MongoConfig.MONGO_QUERY_VERSION_LIST_URL + "/" + modelId + "_" + dataId + "_" + tabName, new HashMap<>());
        RestfulResponse<CommonResponse> response = RestfulClient.getHttpRestfulClient().getRequest(queryVersionListUri, CommonResponse.class, ContentType.APPLICATION_JSON);
        List<Map<String, String>> result = (List<Map<String, String>>) response.getResult().getResult();
        List<DataVersion> datas = new ArrayList<>();
        result.forEach(map -> {
            DataVersion dataVersion = new DataVersion();
            dataVersion.setVersionId(CommonTools.Obj2String(map.get("version")));
            dataVersion.setVersionName("版本" + CommonTools.Obj2String(map.get("version")));
            dataVersion.setIsShow(CommonTools.Obj2String(map.get("isshow")));
            datas.add(dataVersion);
        });
        retVal.setResults(datas);
        return retVal;
    }

    public AjaxResponseData switchVersion(String modelId, String dataId, String tabName, String versionId) {
        AjaxResponseData retVal = new AjaxResponseData();
        DestURI updateVersionUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, MongoConfig.MONGO_SET_SHOW_VERSION_URL + modelId + "_" + dataId + "_" + tabName + "/" + versionId, new HashMap<>());
        RestfulResponse<CommonResponse> response = RestfulClient.getHttpRestfulClient().getRequest(updateVersionUri, CommonResponse.class, ContentType.APPLICATION_JSON);
        retVal.setSuccess(response.getResult().isSuccess());
        return retVal;
    }

    public AjaxResponseData<String> beginEdit(String modelId, String dataId, String tabName, String versionId) {
        AjaxResponseData<String> retVal = new AjaxResponseData<>();
        DestURI beginEditUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, MongoConfig.MONGO_BEGIN_EDIT_URL + modelId + "_" + dataId + "_" + tabName + "/" + versionId + "/" + UserContextUtil.getUserId(), new HashMap<>());
        RestfulResponse<EditRight> response = RestfulClient.getHttpRestfulClient().getRequest(beginEditUri, EditRight.class, ContentType.APPLICATION_JSON);
        EditRight editRight = response.getResult();
        String responseCode = editRight.getResponseCode();
        retVal.setResults(responseCode);
        switch (responseCode) {
            case "-2":
                retVal.setMsg("当前修改版本非最新，请切换到最新版本后再修改并更新保存");
                break;
            case "-1":
                retVal.setMsg("修改的表格不存在");
                break;
            case "0":
                retVal.setMsg("已经获取修改权限");
                break;
            case "1":
                Map<String, User> users = roleEngine.getRoleModel(false).getUsers();
                User user = users.get(editRight.getUserId());
                String allName = user.getAllName();
                if ((System.currentTimeMillis() - editRight.getLastModifyTime()) / 1000 / 60 / 60 / 24 >= 2) {
                    retVal.setMsg("距离" + allName + "上一次修改数据已经超过两天，但是其没有更新版本，请您在更新版本后再进行修改操作");
                } else {
                    retVal.setMsg(allName + "可能正在修改数据，请在其修改保存并升级版本后再进行修改操作");
                }
                break;
            default:
                break;
        }
        retVal.setAlertMsg(false);
        return retVal;
    }

    public AjaxResponseData modifyData(String modelId, String dataId, String tabName, String modifyDataList) {
        AjaxResponseData retVal = new AjaxResponseData();
        List<ChangeData> changeDataList = GsonUtil.toList(modifyDataList, ChangeData.class);
        if (changeDataList != null && changeDataList.size() > 0) {
            changeDataList.forEach(changeData -> {
                String column = changeData.getColumn();
                String changeValue = changeData.getChangeValue();
                String objId = changeData.getObjId();
                DestURI modifyDataUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, MongoConfig.MONGO_MODIFY_DATA_URL + modelId + "_" + dataId + "_" + tabName + "/" + objId + "/" + changeValue + "/" + column, new HashMap<>());
                RestfulResponse<CommonResponse> response = RestfulClient.getHttpRestfulClient().getRequest(modifyDataUri, CommonResponse.class, ContentType.APPLICATION_JSON);
            });
        }
        return retVal;
    }

    public AjaxResponseData<CanEditData> judgeCanEditData(String modelId, String dataId, String tabName) {
        AjaxResponseData<CanEditData> retVal = new AjaxResponseData<>();
        CanEditData result = new CanEditData();
        try {
            DestURI judgeIsEditingDataUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, MongoConfig.MONGO_JUDGE_CAN_EDIT_DATA_URL + modelId + "_" + dataId + "_" + tabName + "/" + UserContextUtil.getUserId(), new HashMap<>());
            RestfulResponse<CommonResponse> response = RestfulClient.getHttpRestfulClient().getRequest(judgeIsEditingDataUri, CommonResponse.class, ContentType.APPLICATION_JSON);
            retVal.setSuccess(response.getResult().isSuccess());//是否处于编辑数据中，如果处于 编辑数据中显示temp数据，否则显示show版本数据
            result.setVersionNumber(CommonTools.Obj2String(response.getResult().getResult()));
            result.setResponseCode(response.getResult().getResponseCode());
            retVal.setResults(result);
            retVal.setAlertMsg(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取当前用户是否处于数据编辑状态发生异常");
        }
        return retVal;
    }

    public List<String> getColumns(String modelId, String dataId, String tabName) {
        List<MatrixFileDesc> list = matrixFileDescService.list(Restrictions.eq(MODEL_ID, modelId), Restrictions.eq(DATA_ID, dataId), Restrictions.eq(FILE_NAME, tabName));
        if (list.size() > 0) {
            MatrixFileDesc matrixFileDesc = list.get(0);
            return matrixFileDesc.getMatrixColumns().stream()
                    .map(MatrixColumn::getColumnName)
                    .collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 返回列名，以逗号的形式分隔
     * 比如A,B,C
     *
     * @param modelId
     * @param dataId
     * @return
     */
    private String getColumnString(String modelId, String dataId, String tabName) {
        List<MatrixFileDesc> list = matrixFileDescService.list(Restrictions.eq(MODEL_ID, modelId), Restrictions.eq(DATA_ID, dataId), Restrictions.eq(FILE_NAME, tabName));
        if (list.size() > 0) {
            MatrixFileDesc matrixFileDesc = list.get(0);
            return matrixFileDesc.getMatrixColumns().stream()
                    .map(MatrixColumn::getColumnName)
                    .collect(Collectors.joining(","));
        }
        return null;
    }

    public ExtGridData getCurrentVersionGridData(String modelId, String dataId, String tabName, String showCols, String operation, Integer page, Integer limit, String filterJson, String sortJson) {
        ExtGridData retVal = new ExtGridData();
        //如果前端不传showCols，默认查询所有字段
        if (StringUtil.isEmpty(showCols)) {
            showCols = getColumnString(modelId, dataId, tabName);
        }
        //如果列名集合为""，说明还没有导入矩阵数据
        if ("".equals(showCols)) {
            ExtGridData result = new ExtGridData<>();
            result.setSuccess(false);
            result.setMsg("当前记录还没有绑定矩阵数据");
            result.setAlertMsg(false);
            result.setResults(new ArrayList<>());
            return result;
        }

        QueryResult queryResult = getQueryResult(modelId + "_" + dataId + "_" + tabName, showCols, operation, page, limit, filterJson, sortJson, MongoConfig.MONGO_QUERY_SHOW_VERSION_DATA_URL);
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) queryResult.getResult();
        dataList.forEach(map -> {
            map.forEach((key, value) -> {
                if (value instanceof Double) {
                    //将异常值改为NAN
                    if ((Double) value == Float.MAX_VALUE || (Double) value > 3.0e+38) {
                        value = "NAN";
                    } else {
                        value = String.format("%.14f", value);
                    }
                }
                map.put(key, value);
            });
        });
        retVal.setResults(dataList);
        retVal.setTotalProperty(queryResult.getTotalCount());
        return retVal;

    }

    public QueryResult getQueryResult(String tableName, String showCols, String operation, Integer page, Integer limit, String filterJson, String sortJson, String queryDataUrl) {
        if (StringUtil.isEmpty(tableName)) {
            return null;
        }
        if (StringUtil.isEmpty(operation)) {
            operation = "/11";
        }
        Map<String, String> map = new HashMap<>();
        map.put("showCols", showCols);
        map.put("start", ((page - 1) * limit) + "");
        map.put("limit", limit.toString());
        map.put("filterJson", StringUtil.isEmpty(filterJson) ? "" : filterJson);
        map.put("sortJson", StringUtil.isEmpty(sortJson) ? "" : sortJson);
        RestfulResponse<QueryResult> mongoResult = null;
        try {
            DestURI destURI = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, queryDataUrl + tableName + "/" + operation, map);
            mongoResult = RestfulClient.getHttpRestfulClient().getRequest(destURI, QueryResult.class, ContentType.APPLICATION_JSON);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询mongo数据出现异常");
        }
        if (mongoResult.getResult() != null) {
            return mongoResult.getResult();
        }
        return null;
    }

    public ExtGridData getTempVersionGridData(String modelId, String dataId, String tabName, String showCols, String operation, Integer page, Integer limit, String filterJson, String sortJson) {
        ExtGridData retVal = new ExtGridData();
        //如果前端不传showCols，默认查询所有字段
        if (StringUtil.isEmpty(showCols)) {
            showCols = getColumnString(modelId, dataId, tabName);
        }
        //如果列名集合为""，说明还没有导入矩阵数据
        if ("".equals(showCols)) {
            ExtGridData result = new ExtGridData<>();
            result.setSuccess(false);
            result.setMsg("当前记录还没有绑定矩阵数据");
            result.setAlertMsg(false);
            result.setResults(new ArrayList<>());
            return result;
        }
        QueryResult queryResult = getQueryResult(modelId + "_" + dataId + "_" + tabName, showCols, operation, page, limit, filterJson, sortJson, MongoConfig.MONGO_QUERY_TEMP_DATA_URL);
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) queryResult.getResult();
        dataList.forEach(map -> {
            map.forEach((key, value) -> {
                if (value instanceof Double) {
                    //将异常值改为NAN
                    if ((Double) value == Float.MAX_VALUE || (Double) value > 3.0e+38) {
                        value = "NAN";
                    } else {
                        value = String.format("%.14f", value);
                    }
                }
                map.put(key, value);
            });
        });
        retVal.setResults(dataList);
        retVal.setTotalProperty(queryResult.getTotalCount());
        return retVal;
    }

    public AjaxResponseData rollbackLastVersion(String modelId, String dataId, String tabName) {
        AjaxResponseData retVal = new AjaxResponseData();
        retVal.setAlertMsg(false);
        try {
            DestURI rollbackLastVersionUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, MongoConfig.MONGO_ROLLBACK_LAST_VERSION_URL + modelId + "_" + dataId + "_" + tabName + "/" + UserContextUtil.getUserId(), new HashMap<>());
            RestfulResponse<CommonResponse> response = RestfulClient.getHttpRestfulClient().getRequest(rollbackLastVersionUri, CommonResponse.class, ContentType.APPLICATION_JSON);
            retVal.setSuccess(response.getResult().isSuccess());
            retVal.setAlertMsg(false);
        } catch (Exception e) {
            e.printStackTrace();
            retVal.setSuccess(false);
            retVal.setMsg("数据回滚发生异常");
            return retVal;
        }
        return retVal;
    }

    public String dataAnalysis(String modelId, String dataId, String tabName, String filterJson, boolean canEditData, String[] dataIdFilter, HttpServletRequest request) {
        try {
            String dataIdFilters = "";
            if (dataIdFilter != null) {
                dataIdFilters = CommonTools.getArrayToString(dataIdFilter, ",");
            }
            String url = canEditData ? MongoConfig.MONGO_PLOT_TEMP_DATA_URL : MongoConfig.MONGO_PLOT_DATA_URL;
            Map<String, String> map = new HashMap<>();
            map.put("showCols", getColumnString(modelId, dataId, tabName));
            map.put("filterJson", "".equals(dataIdFilters) ? filterJson : dataIdFilters);
            map.put("sortJson", "");
            DestURI plotDataUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, url + modelId + "_" + dataId + "_" + tabName, map);
            RestfulResponse<ExportResponse> response = RestfulClient.getHttpRestfulClient().getRequest(plotDataUri, ExportResponse.class, ContentType.APPLICATION_JSON);
            //列数据的文件集合路径，把文件下载下来
            ExportResponse result = response.getResult();
            List<String> pathList = result.getPathList();
            String xmlPath = null;
            List<String> newDataFilePathList = new ArrayList<>();
            for (String path : pathList) {
                Map<String, String> params = new HashMap<>();
                params.put("filePath", path);
                DestURI downloadFileUri = new DestURI(MongoConfig.MONGO_HOST, MongoConfig.MONGO_PORT, MongoConfig.MONGO_DOWNLOAD_COLUMN_DATA_URL, params);
                RestfulResponse<InputStream> fileResponse = RestfulClient.getHttpRestfulClient().downloadStream(downloadFileUri);
                InputStream is = fileResponse.getResult();
                String dirPath = path.substring(path.lastIndexOf("/") - 13, path.lastIndexOf("/"));
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                newDataFilePathList.add(fileName);
                xmlPath = dirPath;
                String tempFolderPath = fileServerConfig.getFtpHome() + dirPath + File.separator + "数据表";
                File tempFolder = new File(tempFolderPath);
                if (!tempFolder.exists()) {
                    tempFolder.mkdirs();
                }
                FileOperator.createFile(tempFolderPath + File.separator + fileName, is);
            }
            //写xml描述文件
            MatrixDataPlotUtil plotUtil = new MatrixDataPlotUtil(fileServerConfig.getFtpHome() + xmlPath);
            plotUtil.writeXml(modelId + "_" + dataId, result.getRowCount(), result.getTypeList(), newDataFilePathList, result.getColList());
            //压缩成zip
            FileOperator.zip(fileServerConfig.getFtpHome() + xmlPath, fileServerConfig.getFtpHome() + xmlPath + ".zip", "");
            //删除原文件
            FileOperator.delFoldsWithChilds(fileServerConfig.getFtpHome() + xmlPath);
            //返回压缩文件的名字
            return xmlPath + ".zip";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getTabs(String modelId, String dataId) {
        List<MatrixFileDesc> list = matrixFileDescService.list(Restrictions.eq(MODEL_ID, modelId), Restrictions.eq(DATA_ID, dataId));
        return list.stream()
                .map(MatrixFileDesc::getFileName)
                .collect(Collectors.toList());
    }

}
