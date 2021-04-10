package com.orient.modeldata.fileHandle.decorator;

import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.business.ModelFileBusiness;
import com.orient.modeldata.fileHandle.bean.FileHandleTarget;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.utils.PathTools;
import com.orient.utils.image.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 文件处理类
 *
 * @author enjoy
 * @creare 2016-05-11 16:03
 */
@Component
public class ImageModelFileDecorator extends ModelFileDecorator {

    @Autowired
    ModelFileBusiness modelFileBusiness;

    @Autowired
    FileServerConfig fileServerConfig;

    @Override
    public void doHandleFile(FileHandleTarget fileHandleTarget) {
        //调用父类处理方法
        super.doHandleFile(fileHandleTarget);
        CwmFile fileDesc = fileHandleTarget.getFileDesc();
        String filePath = fileServerConfig.getFtpHome() + fileHandleTarget.getFileDesc().getFilelocation();
        //相关业务处理
        if (FileOperator.isImage(filePath)) {
            //复制至中间件preview目录
            String targetPath = PathTools.getRootPath() + File.separator + "preview" + File.separator +
                    "imagePreview" + File.separator + fileDesc.getFinalname();
            FileOperator.copyFile(filePath, targetPath);
            //图片类型文件处理
            zoomImage(filePath, fileDesc);
            //更新转化状态
            fileDesc.setConverState("1");
            modelFileBusiness.updateFile(fileDesc);
        }
    }

    /**
     * 生成缩略图
     *
     * @param filePath
     * @param fileDesc
     */
    private void zoomImage(String filePath, CwmFile fileDesc) {
        String imagePath = CommonTools.getPreviewImagePath();
        String copyPath = imagePath + File.separator + fileDesc.getFinalname();
        FileOperator.copyFile(filePath, copyPath);
        //生成缩略图
        try {
            ImageUtils.zoomImageScale(new File(copyPath), 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
