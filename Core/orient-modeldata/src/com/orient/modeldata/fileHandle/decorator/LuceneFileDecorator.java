package com.orient.modeldata.fileHandle.decorator;

import com.orient.config.SystemMngConfig;
import com.orient.modeldata.fileHandle.bean.FileHandleTarget;
import com.orient.utils.restful.DestURI;
import com.orient.utils.restful.RestfulClient;
import com.orient.web.base.AjaxResponseData;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 交与文件服务 进行全文检索创建索引操作
 *
 * @author enjoy
 * @creare 2016-05-11 16:10
 */
@Component
public class LuceneFileDecorator extends ModelFileDecorator {

    @Override
    public void doHandleFile(FileHandleTarget fileHandleTarget) {
        super.doHandleFile(fileHandleTarget);
        String filePath = fileHandleTarget.getFileDesc().getFilelocation();
        //创建索引
        Map<String, String> paramters = new HashMap<>();
        paramters.put("fileRelativePath", filePath);
        DestURI destURI = new DestURI(SystemMngConfig.FILESERVICE_IP, Integer.valueOf(SystemMngConfig.FILESERVICE_PORT), SystemMngConfig.FILESERVICE_CONTEXT + "/lucene/index", paramters);
        RestfulClient.getHttpRestfulClient().postRequest(destURI, paramters, AjaxResponseData.class, ContentType.APPLICATION_JSON, ContentType.APPLICATION_JSON);
    }
}
