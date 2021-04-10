package com.orient.modeldata.fileHandle.decorator;

import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.business.ModelFileBusiness;
import com.orient.modeldata.fileHandle.bean.FileHandleTarget;
import com.orient.modeldata.util.VideoTranscode;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.file.CwmFileGroupEntity;
import com.orient.sysmodel.service.file.IFileGroupService;
import com.orient.utils.FileOperator;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 视频处理装饰类
 *
 * @author enjoy
 * @creare 2016-05-11 16:10
 */
@Component
public class VideoModelFileDecorator extends ModelFileDecorator {
    @Autowired
    VideoTranscode videoTranscode;

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    IFileGroupService fileGroupService;

    @Autowired
    ModelFileBusiness modelFileBusiness;

    @Override
    public void doHandleFile(FileHandleTarget fileHandleTarget) {
        super.doHandleFile(fileHandleTarget);
        //初始数据库时 视频类型ID为-3
        CwmFileGroupEntity fileGroupEntity = fileGroupService.getById(-3l);
        final String[] videoFilter = {""};
        //获取系统视频类型集合
        if (null != fileGroupEntity) {
            fileGroupEntity.getGroupItemEntityList().forEach(fileGroupItemEntity -> videoFilter[0] += fileGroupItemEntity.getSuffix() + ",");
        }
        //信息准备
        CwmFile fileDesc = fileHandleTarget.getFileDesc();
        String filePath = fileServerConfig.getFtpHome() + fileHandleTarget.getFileDesc().getFilelocation();
        //视频类型文件处理
        if (StringUtil.contain(videoFilter[0], fileDesc.getFiletype()) && fileServerConfig.getEnableVideoPreview()) {
            preparePreviewData(filePath, fileDesc);
        }
    }

    /**
     * 生成视频在线点播所需数据
     *
     * @param filePath 视频所在路径
     * @param fileDesc 视频文件数据库描述
     */
    private void preparePreviewData(String filePath, CwmFile fileDesc) {
        String fileNamePre = filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."));
        String destPath = fileServerConfig.getMp4Home()+"/"+fileNamePre+".mp4";
        videoTranscode.toMp4(filePath, destPath);
        fileDesc.setConverState("1");
        modelFileBusiness.updateFile(fileDesc);
        String pngDestPath = fileServerConfig.getMp4Home()+"/"+fileNamePre+".png";
        videoTranscode.toPng(filePath, pngDestPath);
    }

}
