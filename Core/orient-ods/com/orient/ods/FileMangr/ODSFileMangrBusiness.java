package com.orient.ods.FileMangr;

import com.orient.edm.init.FileServerConfig;
import com.orient.ods.config.ODSConfig;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.file.OdsFile;
import com.orient.sysmodel.service.file.FileService;
import com.orient.sysmodel.service.file.impl.ODSFileService;
import com.orient.utils.FileOperator;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mengbin on 16/7/15.
 * Purpose:
 * Detail:
 */
@Component
public class ODSFileMangrBusiness {

    @Autowired
    public FileServerConfig fileServerConfig;

    @Autowired
    public ODSFileService odsFileService;

    @Autowired
    public FileService fileService;

    public OdsFile unZipODS(CwmFile file) {

        String filelocation = file.getFilelocation();
        String fileName = file.getFilename();
        String finalName = file.getFinalname();
        int index = finalName.lastIndexOf(".");
        String filePath = fileServerConfig.getFtpHome() + File.separator + filelocation;

        String RootODSFileDir = fileServerConfig.getFtpHome() + File.separator + ODSConfig.ODSDIRROOT + File.separator + finalName.substring(0, index);
        FileOperator.createFolders(RootODSFileDir);
        try {
            boolean bSuc = FileOperator.unZip(filePath, RootODSFileDir);
            if (!bSuc) return null;


            //    String  curfileDir = RootODSFileDir+File.separator+name;
            File odsFileDir = new File(RootODSFileDir);
            if (!odsFileDir.exists() || !odsFileDir.isDirectory()) {
                return null;
            }


            List<String> childrenFilePath = new ArrayList<>();
            FileOperator.getChildFilePath(RootODSFileDir, childrenFilePath);
            for (String loopfileName : childrenFilePath) {
                String suffix = FileOperator.getSuffix(loopfileName);
                if (suffix.equalsIgnoreCase(ODSConfig.ODSFILESUFFIX) || suffix.equalsIgnoreCase("xml")) {
                    //找到对应的atfx文件
                    OdsFile odsFile = new OdsFile();
                    odsFile.setAtfxfilefullname(loopfileName);
                    odsFile.setCwmFileId(file.getFileid());
                    file.setConverState("1");
                    odsFileService.save(odsFile);
                    fileService.updateFile(file);
                    return odsFile;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return null;
    }

    /**
     * 根据cwmFileId 获取 ods文件的真实路径, 如果未转化,则转化
     *
     * @param fileId
     * @return 返回空说明失败,
     */
    public String getAtfxFilePath(String fileId) {

        //fileId = odsFileService.list(Order.desc("id")).get(0).getCwmFileId();

        List<Criterion> filters = new ArrayList<>();

        filters.add(Restrictions.eq("cwmFileId", fileId));
        List<OdsFile> odsFiles = odsFileService.list(filters.toArray(new Criterion[0]));
        if (!odsFiles.isEmpty()) {
            OdsFile odsFile = odsFiles.get(0);
            return odsFile.getAtfxfilefullname();

        }
        CwmFile file = fileService.findFileById(fileId);
        if (file.getConverState() == "0") {
            OdsFile odsFile = unZipODS(file);
            if (odsFile != null) {
                return odsFile.getAtfxfilefullname();
            } else {
                return "";
            }
        }
        return "";

    }
}
