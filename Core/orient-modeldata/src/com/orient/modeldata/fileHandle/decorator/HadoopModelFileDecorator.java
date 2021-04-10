package com.orient.modeldata.fileHandle.decorator;

import com.orient.modeldata.fileHandle.bean.FileHandleTarget;
import com.orient.sysmodel.domain.file.CwmFile;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Hadoop文件处理
 *
 * @author enjoy
 * @creare 2016-07-15 16:03
 */
@Component
public class HadoopModelFileDecorator extends ModelFileDecorator {

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void doHandleFile(FileHandleTarget fileHandleTarget) {
        //调用父类处理方法
        super.doHandleFile(fileHandleTarget);
        CwmFile fileDesc = fileHandleTarget.getFileDesc();
        if ("hadoop".equals(fileDesc.getFileCatalog())) {
            logger.warn("Hadoop文件开始处理");
        }
    }
}
