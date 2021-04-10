package com.orient.modeldata.business;

import com.orient.config.SystemMngConfig;
import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.file.CwmFileGroupEntity;
import com.orient.sysmodel.service.file.FileService;
import com.orient.sysmodel.service.file.IFileGroupService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.utils.StringUtil;
import com.orient.utils.restful.DestURI;
import com.orient.web.base.BaseBusiness;
import com.orient.web.form.model.FileModel;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 模型附件管理业务处理
 *
 * @author enjoy
 * @creare 2016-05-03 15:02
 */
@Component
public class ModelFileBusiness extends BaseBusiness {

    @Autowired
    FileService fileService;

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    IFileGroupService fileGroupService;


    /**
     * 保存上传的文件
     *
     * @param file    文件
     * @param modelId 所属模型ID
     * @param dataId  所属数据ID
     * @return
     */
    public static final String SCIENTIS_PREVIEW = "scientis_preview";

    public CwmFile saveScientistPicturePreviewPicFile(MultipartFile file, String modelId, String dataId, String fileCatalog, String desc, String secrecy) {
        CwmFile cwmFile = new CwmFile();
        String fileName = file.getOriginalFilename();
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        //保存文件
        String folder = "/"+SCIENTIS_PREVIEW+"/";
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String realFileStoragePath = fileServerConfig.getFtpHome() + folder + finalFileName;
        try {
            FileOperator.createFile(realFileStoragePath, file.getBytes());
            cwmFile.setDataid(dataId);
            cwmFile.setTableid(modelId);
            cwmFile.setFilename(fileName);
            cwmFile.setFinalname(finalFileName);
            cwmFile.setFilelocation(folder + finalFileName);
            cwmFile.setFilesize(file.getSize());
            cwmFile.setUploadStatus(CwmFile.UPLOAD_STATUS_SUCCESS);
            cwmFile.setUploadUserId(UserContextUtil.getUserId());
            cwmFile.setUploadDate(new Date());
            cwmFile.setFileCatalog(fileCatalog);
            cwmFile.setFiledescription(CommonTools.null2String(desc));
            cwmFile.setFilesecrecy(secrecy);
            fileService.createFile(cwmFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cwmFile;
    }

    /**
     * 保存上传的文件
     *
     * @param file    文件
     * @param modelId 所属模型ID
     * @param dataId  所属数据ID
     * @return
     */
    public CwmFile saveUploadFile(MultipartFile file, String modelId, String dataId, String fileCatalog, String desc, String secrecy) {
        CwmFile cwmFile = new CwmFile();
        String fileName = file.getOriginalFilename();
        String finalFileName = FtpFileUtil.getOnlyFileName(fileName);
        //保存文件
        String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.UPLOAD_ROOT);
        FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
        String realFileStoragePath = fileServerConfig.getFtpHome() + folder + finalFileName;
        try {
            FileOperator.createFile(realFileStoragePath, file.getBytes());
            cwmFile.setDataid(dataId);
            cwmFile.setTableid(modelId);
            cwmFile.setFilename(fileName);
            cwmFile.setFinalname(finalFileName);
            cwmFile.setFilelocation(folder + finalFileName);
            cwmFile.setFilesize(file.getSize());
            cwmFile.setUploadStatus(CwmFile.UPLOAD_STATUS_SUCCESS);
            cwmFile.setUploadUserId(UserContextUtil.getUserId());
            cwmFile.setUploadDate(new Date());
            cwmFile.setFileCatalog(fileCatalog);
            cwmFile.setFiledescription(CommonTools.null2String(desc));
            cwmFile.setFilesecrecy(secrecy);
            fileService.createFile(cwmFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cwmFile;
    }

    /**
     * 根据过滤条件 获取文件数量
     *
     * @param modelId     所属模型ID
     * @param dataId      所属数据ID
     * @param fileGroupId 文件所在分组
     * @return
     */
    public long count(String modelId, String dataId, String fileGroupId) {
        List<String> suffixFilter = new ArrayList<>();
        //过滤后缀名
        if (!StringUtil.isEmpty(fileGroupId)) {
            CwmFileGroupEntity fileGroupEntity = fileGroupService.getById(Long.valueOf(fileGroupId));
            if (null != fileGroupEntity.getGroupItemEntityList()) {
                fileGroupEntity.getGroupItemEntityList().forEach(fileGroupItemEntity -> {
                    suffixFilter.add(fileGroupItemEntity.getSuffix());
                });
            }
        }
        return fileService.findFileByModelAndDataAndGroupCount(modelId, dataId, suffixFilter);
    }

    /**
     * 分页查询附件信息
     *
     * @param modelId     所属模型ID
     * @param dataId      所属数据ID
     * @param fileGroupId 所属分组
     * @param page        第几页
     * @param limit       每页总数
     * @return
     */
    public List<FileModel> list(String modelId, String dataId, String fileGroupId, Integer page, Integer limit) {
        List<FileModel> retVal = new ArrayList<>();
        List<String> suffixFilter = new ArrayList<>();
        //过滤后缀名
        if (!StringUtil.isEmpty(fileGroupId)) {
            CwmFileGroupEntity fileGroupEntity = fileGroupService.getById(Long.valueOf(fileGroupId));
            if (null != fileGroupEntity.getGroupItemEntityList()) {
                fileGroupEntity.getGroupItemEntityList().forEach(fileGroupItemEntity -> {
                    suffixFilter.add(fileGroupItemEntity.getSuffix());
                });
            }
        }
        List<CwmFile> fileList = fileService.findFileByModelAndDataAndGroup(modelId, dataId, suffixFilter, page, limit);
        //拷贝信息
        fileList.forEach(cwmFile -> {
            FileModel fileModel = new FileModel();
            BeanUtils.copyProperties(fileModel, cwmFile);
            //绑定用户信息
            if (null != cwmFile.getUploadUserId()) {
                fileModel.setUploadUserName(roleEngine.getRoleModel(false).getUserById(cwmFile.getUploadUserId()).getAllName());
            }
            fileModel.setFilePath(cwmFile.getFilelocation());
            //设置缩略图名称
            String sFilePath = File.separator + StringUtils.substringBeforeLast(fileModel.getFinalname(), ".") + "_s." + StringUtils.substringAfterLast(fileModel.getFilePath(), ".");
            fileModel.setsFilePath(sFilePath);
            retVal.add(fileModel);
        });
        return retVal;
    }

    public void delete(String[] toDelIds) {

        for (String toDelId : toDelIds) {
            CwmFile file = fileService.findFileById(toDelId);
            //删除索引
            File toDelFile = new File(fileServerConfig.getFtpHome() + file.getFilelocation());
            new File(fileServerConfig.getFtpHome() + File.separator + "废弃文件").mkdirs();
            toDelFile.renameTo(new File(fileServerConfig.getFtpHome() + File.separator + "废弃文件" + File.separator + toDelFile.getName()));
            Map<String, String> queryString = new HashMap<String, String>() {{
                put("fileRelativePath", file.getFilelocation());
            }};
            DestURI destURI = new DestURI(SystemMngConfig.FILESERVICE_IP, Integer.valueOf(SystemMngConfig.FILESERVICE_PORT), SystemMngConfig.FILESERVICE_CONTEXT + "/lucene/index", queryString);
            //RestfulClient.getHttpRestfulClient().deleteRequest(destURI, AjaxResponseData.class, ContentType.APPLICATION_JSON);
            fileService.deleteFile(toDelId);

        }
    }

    public String getPreviewFileJsp(String fileId, HttpServletRequest request) {
        String readOnlyJspPath = "/app/views/file/officeDocPreview.jsp";
        CwmFile cwmFile = fileService.findFileById(fileId);
        StringBuffer retVal = new StringBuffer();
        if (cwmFile.getFiletype().toLowerCase().equals("pdf")) {
            //如果是pdf
            readOnlyJspPath = "/app/views/file/pdfDocPreview.jsp";
        }
        request.getSession().setAttribute("fileName", cwmFile.getFilelocation());
        request.getSession().setAttribute("fileType", cwmFile.getFiletype());
        return readOnlyJspPath + retVal.toString();
    }

    public Boolean checkConverCompleted(String fileId) {
        CwmFile cwmFile = fileService.findFileById(fileId);
        String converState = cwmFile.getConverState();
        Boolean retVal = !StringUtil.isEmpty(converState) && "1".equals(converState) ? true : false;
        return retVal;
    }

    public CwmFile getFileById(String fileId) {
        return fileService.findFileById(fileId);
    }

    public void updateFile(CwmFile fileDesc) {
        fileService.updateFile(fileDesc);
    }

    public List<FileModel> getFileInfoById(String fileId) {
        CwmFile cwmFile = fileService.findFileById(fileId);
        List<FileModel> retVal = new ArrayList<>();
        FileModel fileModel = new FileModel();
        BeanUtils.copyProperties(fileModel, cwmFile);
        //绑定用户信息
        if (null != cwmFile.getUploadUserId()) {
            fileModel.setUploadUserName(roleEngine.getRoleModel(false).getUserById(cwmFile.getUploadUserId()).getAllName());
        }
        fileModel.setFilePath(cwmFile.getFilelocation());
        //设置缩略图名称
        String sFilePath = StringUtils.substringBeforeLast(fileModel.getFilePath(), ".") + "_s." + StringUtils.substringAfterLast(fileModel.getFilePath(), ".");
        fileModel.setsFilePath(sFilePath);
        retVal.add(fileModel);
        return retVal;
    }

    public ModelAndView preview(String fileId) {
        ModelAndView retVal = new ModelAndView();
        retVal.addObject("fileId", fileId);
        retVal.setViewName("/app/views/file/PDFPreviewByJqueryMedia.jsp");
        return retVal;
    }
}
