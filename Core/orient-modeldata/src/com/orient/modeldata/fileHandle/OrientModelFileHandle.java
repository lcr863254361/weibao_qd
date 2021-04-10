package com.orient.modeldata.fileHandle;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.fileHandle.bean.FileDecoratorContainer;
import com.orient.modeldata.fileHandle.bean.FileHandleTarget;
import com.orient.modeldata.fileHandle.decorator.ModelFileDecorator;
import com.orient.modeldata.fileHandle.impl.ModelFileHandle;
import com.orient.sysmodel.domain.file.CwmFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模型文件处理
 *
 * @author enjoy
 * @creare 2016-05-11 16:26
 */
@Component
public class OrientModelFileHandle {

    @Autowired
    FileDecoratorContainer fileHandleContainer;

    @Autowired
    ModelFileHandle modelFileHandle;

    /**
     * 文件处理入口 可修改此类代码完成定制工作
     *
     * @param modelId  附件所属模型ID
     * @param dataId   附件所属数据ID
     * @param fileDesc 附件数据库描述
     */
    @Async
    @Transactional
    public void doHandleModelFile(String modelId, String dataId, CwmFile fileDesc) {
        FileHandleTarget fileHandleTarget = new FileHandleTarget(modelId, dataId, fileDesc);
        List<String> filehandleNames = fileHandleContainer.getDecoratorNames();
        final IModelFileHandle[] previewDecorator = {modelFileHandle};
        if (null != filehandleNames) {
            filehandleNames.forEach(fileDecoratorName -> {
                ModelFileDecorator fileDecorator = (ModelFileDecorator) OrientContextLoaderListener.Appwac.getBean(fileDecoratorName);
                if (null != previewDecorator[0]) {
                    fileDecorator.setDecorator(previewDecorator[0]);
                }
                previewDecorator[0] = fileDecorator;
            });
        }
        previewDecorator[0].doHandleFile(fileHandleTarget);
    }
}
