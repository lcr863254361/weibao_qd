package com.orient.mongorequest.config;

import com.orient.config.ConfigInfo;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-05-31 10:59
 */
public class MongoConfig extends ConfigInfo {

    public static String MONGO_HOST;
    public static int MONGO_PORT;
    public static String DATABASE;
    public static String MONGO_QUERY_SHOW_VERSION_DATA_URL;  //查询当前显示版本的数据的接口
    public static String MONGO_QUERY_TEMP_DATA_URL;          //查询temp版本的数据的接口
    public static String MONGO_INSERT_DATA_URL;              //导入mongo数据的接口
    public static String MONGO_DELETE_DATA_URL;              //删除mongo数据的接口
    public static String MONGO_UPDATE_VERSION_URL;           //更新版本的接口
    public static String MONGO_QUERY_VERSION_LIST_URL;       //获取当前表下所有版本号的接口
    public static String MONGO_SET_SHOW_VERSION_URL;         //设置显示版本的接口
    public static String MONGO_BEGIN_EDIT_URL;               //修改数据前调用的接口，判断当前是否有人在修改数据
    public static String MONGO_MODIFY_DATA_URL;              //修改数据的接口
    public static String MONGO_JUDGE_CAN_EDIT_DATA_URL;         //判断是否处于编辑状态
    public static String MONGO_ROLLBACK_LAST_VERSION_URL;        //数据回滚接口
    public static String MONGO_PLOT_DATA_URL;
    public static String MONGO_PLOT_TEMP_DATA_URL;
    public static String MONGO_DOWNLOAD_COLUMN_DATA_URL;      //下载列数据的接口

    public static String FILE_SERVER_HOST;
    public static int FILE_SERVER_PORT;
    public static String FILE_UPLOAD_URL;
    public static String FILE_DELETE_URL;

    static {
        MONGO_HOST = getPropertyValueConfigured("mongo.host");
        MONGO_PORT = Integer.parseInt(getPropertyValueConfigured("mongo.port"));
        DATABASE = getPropertyValueConfigured("mongo.database");
        MONGO_QUERY_SHOW_VERSION_DATA_URL = getPropertyValueConfigured("mongo.queryShowVersionData.url");
        MONGO_QUERY_TEMP_DATA_URL = getPropertyValueConfigured("mongo.queryTempData.url");
        MONGO_INSERT_DATA_URL = getPropertyValueConfigured("mongo.insertData.url");
        MONGO_DELETE_DATA_URL = getPropertyValueConfigured("mongo.deleteData.url");
        MONGO_UPDATE_VERSION_URL = getPropertyValueConfigured("mongo.updateVersion.url");
        MONGO_QUERY_VERSION_LIST_URL = getPropertyValueConfigured("mongo.queryVersionList.url");
        MONGO_SET_SHOW_VERSION_URL = getPropertyValueConfigured("mongo.setShowVersion.url");
        MONGO_BEGIN_EDIT_URL = getPropertyValueConfigured("mongo.beginEdit.url");
        MONGO_MODIFY_DATA_URL = getPropertyValueConfigured("mongo.modifyData.url");
        MONGO_JUDGE_CAN_EDIT_DATA_URL = getPropertyValueConfigured("mongo.judgeCanEditData.url");
        MONGO_ROLLBACK_LAST_VERSION_URL = getPropertyValueConfigured("mongo.rollbackLastVersion.url");
        MONGO_PLOT_DATA_URL = getPropertyValueConfigured("mongo.plotData.url");
        MONGO_PLOT_TEMP_DATA_URL = getPropertyValueConfigured("mongo.plotTempData.url");
        MONGO_DOWNLOAD_COLUMN_DATA_URL = getPropertyValueConfigured("mongo.download.columnDataFile.url");

        FILE_SERVER_HOST = getPropertyValueConfigured("fileServer.host");
        FILE_SERVER_PORT = Integer.parseInt(getPropertyValueConfigured("fileServer.post"));
        FILE_UPLOAD_URL = getPropertyValueConfigured("fileServer.upload.url");
        FILE_DELETE_URL = getPropertyValueConfigured("fileServer.delete.url");
    }

}
