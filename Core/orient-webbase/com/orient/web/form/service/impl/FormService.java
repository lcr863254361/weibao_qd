package com.orient.web.form.service.impl;

import com.orient.edm.init.FileServerConfig;
import com.orient.metamodel.metadomain.Enum;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.metaengine.dao.EnumDAO;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.service.file.FileService;
import com.orient.utils.FileOperator;
import com.orient.web.base.ExtComboboxData;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.form.dao.IFormDao;
import com.orient.web.form.model.FileModel;
import com.orient.web.form.service.IFormService;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by enjoy on 2016/3/21 0021.
 */
@Service
public class FormService implements IFormService {
    @Autowired
    IFormDao formDao;

    @Autowired
    EnumDAO enumDAO;

    @Autowired
    protected MetaUtil metaEngine;

    @Autowired
    protected FileService fileService;

    @Autowired
    private FileServerConfig fileServerConfig;

    @Override
    public ExtComboboxResponseData<ExtComboboxData> getModelComboboxCollection(Integer startIndex, Integer maxResults, String filter, String id) {
        ExtComboboxResponseData<ExtComboboxData> retVal = new ExtComboboxResponseData<>();
        Map<String, Schema> schemaMap = metaEngine.getMeta(false).getSchemas();
        List<ITable> tables = new ArrayList<>();
        for (Map.Entry<String, Schema> data : schemaMap.entrySet()) {
            Schema schema = data.getValue();
            tables.addAll(schema.getAllTables().stream().filter(iTable -> StringUtils.isEmpty(filter) ? true : iTable.getDisplayName().contains(filter) && (StringUtils.isEmpty(id) ? true : iTable.getId().equals(id))).collect(Collectors.toList()));
        }
        //分页
        ArrayList<ITable> subUsers = new ArrayList<>();
        Integer limit = tables.size();
        if (null != maxResults && maxResults > 0) {
            limit = Math.min(startIndex + maxResults, limit);
        }
        startIndex = null == startIndex ? 0 : startIndex;
        for (int i = startIndex; i < limit; i++) {
            subUsers.add(tables.get(i));
        }
        //组合返回值
        retVal.setTotalProperty(tables.size());
        subUsers.forEach(iTable -> {
            ExtComboboxData combobox = new ExtComboboxData();
            combobox.setId(iTable.getId());
            combobox.setValue(iTable.getDisplayName());
            retVal.getResults().add(combobox);
        });
        return retVal;
    }

    @Override
    public FileModel getUploadFilePath(String fileId) {
        FileModel retVal = new FileModel();
        CwmFile cwmFile = fileService.findFileById(fileId);
        if (null != cwmFile) {
            try {
                BeanUtils.copyProperties(retVal, cwmFile);
                retVal.setFilePath(fileServerConfig.getFtpHome() + cwmFile.getFilelocation());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return retVal;
    }

    @Override
    public ExtComboboxResponseData<ExtComboboxData> getEnumComboboxCollection(String restrictionId) {
        ExtComboboxResponseData<ExtComboboxData> retVal = new ExtComboboxResponseData<>();

        List<Enum> enums = enumDAO.findByRestrictionId(restrictionId);
        if (enums == null) {
            enums = new ArrayList<>();
        }

        for (Enum e : enums) {
            ExtComboboxData cb = new ExtComboboxData();
            cb.setId(e.getValue());
            cb.setValue(e.getDisplayValue());
            retVal.getResults().add(cb);
        }
        retVal.setTotalProperty(retVal.getResults().size());
        return retVal;
    }

    public String getDisplayValueById(String restrictionId, String enumId) {
        List<Enum> enums = enumDAO.findByRestrictionId(restrictionId);
        if (enums == null) {
            enums = new ArrayList<>();
        }

        for (Enum e : enums) {
            if (e.getValue().equals(enumId)) {
                return e.getDisplayValue();
            }
        }

        return null;
    }

    public String getValueByDisplayValue(String restrictionId, String displayValue) {
        List<Enum> enums = enumDAO.findByRestrictionId(restrictionId);
        if (enums == null) {
            enums = new ArrayList<>();
        }

        for (Enum e : enums) {
            if (e.getDisplayValue().equals(displayValue)) {
                return e.getValue();
            }
        }

        return null;
    }

    public List<Enum> getEnums(String restrictionId) {
        return enumDAO.findByRestrictionId(restrictionId);
    }

    @Override
    public CwmFile saveUploadFile(MultipartFile file, String fileCatalog) {
        CwmFile cwmFile = new CwmFile();
        String timeSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = file.getOriginalFilename();
        String finalFileName = timeSuffix + "_" + fileName;
        //保存文件
        String filePath = fileServerConfig.getFtpHome() + File.separator + "上传文件" + File.separator + timeSuffix.substring(0, 4) + File.separator + timeSuffix.substring(4, 6) + File.separator
                + timeSuffix.substring(6, 8) + File.separator + timeSuffix.substring(8);
        FileOperator.createFolds(filePath + File.separator);
        String fileLocation = "上传文件" + File.separator + timeSuffix.substring(0, 4) + File.separator + timeSuffix.substring(4, 6) + File.separator
                + timeSuffix.substring(6, 8) + File.separator + timeSuffix.substring(8);
        String realFileStoragePath = filePath + File.separator + finalFileName;//fileName
        try {
            FileOperator.createFile(realFileStoragePath, file.getBytes());
            cwmFile.setFilename(fileName);
            cwmFile.setFinalname(finalFileName);
            cwmFile.setFilelocation(File.separator + fileLocation + File.separator + finalFileName);
            cwmFile.setFilesize(file.getSize());
            cwmFile.setUploadStatus(CwmFile.UPLOAD_STATUS_SUCCESS);
            cwmFile.setUploadUserId(UserContextUtil.getUserId());
            cwmFile.setUploadDate(new Date());
            cwmFile.setFileCatalog(fileCatalog);
            fileService.createFile(cwmFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cwmFile;
    }
}
