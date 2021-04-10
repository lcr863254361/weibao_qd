package com.orient.web.form.service;

import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.web.base.ExtComboboxData;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.form.model.FileModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by enjoy on 2016/3/21 0021.
 */
public interface IFormService {
    ExtComboboxResponseData<ExtComboboxData> getModelComboboxCollection(Integer startIndex, Integer maxResults, String filter, String id);

    FileModel getUploadFilePath(String fileId);

    ExtComboboxResponseData<ExtComboboxData> getEnumComboboxCollection(String restrictionId);

    CwmFile saveUploadFile(MultipartFile file, String fileCatalog);
}
