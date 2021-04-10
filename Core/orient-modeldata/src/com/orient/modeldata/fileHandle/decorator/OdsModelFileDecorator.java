package com.orient.modeldata.fileHandle.decorator;

import com.orient.modeldata.fileHandle.bean.FileHandleTarget;
import com.orient.ods.FileMangr.ODSFileMangrBusiness;
import com.orient.sysmodel.domain.file.CwmFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ODS文件处理
 *
 * @author enjoy
 * @creare 2016-07-15 16:03
 */
@Component
public class OdsModelFileDecorator extends ModelFileDecorator {

    Logger logger = Logger.getLogger(this.getClass());
    @Autowired

    public ODSFileMangrBusiness odsFileMangrBusiness;

    @Override
    public void doHandleFile(FileHandleTarget fileHandleTarget) {
        //调用父类处理方法
        super.doHandleFile(fileHandleTarget);
        CwmFile fileDesc = fileHandleTarget.getFileDesc();
        if("ods".equals(fileDesc.getFileCatalog())){
            logger.warn("ODS文件开始处理");
            odsFileMangrBusiness.unZipODS(fileDesc);
        }
    }
}
