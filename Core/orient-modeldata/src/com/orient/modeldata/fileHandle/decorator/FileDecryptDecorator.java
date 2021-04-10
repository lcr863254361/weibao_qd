package com.orient.modeldata.fileHandle.decorator;

import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.business.ModelFileBusiness;
import com.orient.modeldata.fileHandle.bean.FileHandleTarget;
import com.orient.modeldata.util.FileEncrypt;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 交与文件服务 进行全文检索创建索引操作
 *
 * @author enjoy
 * @creare 2016-05-11 16:10
 */
@Component
public class FileDecryptDecorator extends ModelFileDecorator {
    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    ModelFileBusiness modelFileBusiness;

    @Override
    public void doHandleFile(FileHandleTarget fileHandleTarget) {
        super.doHandleFile(fileHandleTarget);
        if(FileEncrypt.NONE.equals(fileServerConfig.getDecrypt())) {
            return;
        }
        else {
            CwmFile cwmFile = fileHandleTarget.getFileDesc();
            File srcFile = new File(fileServerConfig.getFtpHome()+cwmFile.getFilelocation());
            File decFile = new File(fileServerConfig.getFtpHome()+cwmFile.getFilelocation()+".encrypt");
            //加密
            File temp = FileEncrypt.encrypt(srcFile, decFile, fileServerConfig.getDecrypt());
            if(temp == null) {
                throw new OrientBaseAjaxException("500", "文件加密失败");
            }
            srcFile.delete();

            cwmFile.setFilelocation(cwmFile.getFilelocation() + ".encrypt");
            modelFileBusiness.updateFile(cwmFile);
        }
    }
}
