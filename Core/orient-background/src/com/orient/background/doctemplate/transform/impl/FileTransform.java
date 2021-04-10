package com.orient.background.doctemplate.transform.impl;

import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.transform.IDocTransform;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.edm.init.FileServerConfig;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.service.file.FileService;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2016-12-07 9:17 PM
 */
@Component
public class FileTransform implements IDocTransform<IBusinessColumn> {

    @Autowired
    FileService fileService;

    @Autowired
    FileServerConfig fileServerConfig;

    @Override
    public DocHandlerData doTransform(IBusinessColumn target, List<Map> dataSource) {
        DocHandlerData<List<File>> retVal = new DocHandlerData<>();
        retVal.setOriginalData(dataSource);
        List<File> afterTransformData = new ArrayList<>();
        dataSource.forEach(map -> {
            String fileStr = CommonTools.Obj2String(map.get(target.getS_column_name()));
            if (!StringUtil.isEmpty(fileStr)) {
                List<Map> fileDesc = JsonUtil.json2List(fileStr);
                fileDesc.forEach(dataMap -> {
                    String fileId = CommonTools.Obj2String(dataMap.get("id"));
                    CwmFile cwmFileHome = fileService.findFileById(fileId);
                    String fileHome = fileServerConfig.getFtpHome();
                    String filePath = fileHome + cwmFileHome.getFilelocation();
                    File file = new File(filePath);
                    if (file.exists()) {
                        afterTransformData.add(file);
                    }
                });
            }
        });
        retVal.setAfterDataChange(afterTransformData);
        return retVal;
    }
}
