package com.orient.collabdev.business.processing;

import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.util.FtpFileUtil;
import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFile;
import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFolder;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.collabdev.ICollabShareFileService;
import com.orient.sysmodel.service.collabdev.ICollabShareFolderService;
import com.orient.sysmodel.service.file.FileService;
import com.orient.utils.FileOperator;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 数据内容-共享文件的业务处理层
 *
 * @author ZhangSheng
 * @create 2018-08-20 09:37
 * @modified 2018-09-05 09:22
 */
@Component
public class ShareFileBusiness extends BaseHibernateBusiness<CollabShareFile> {

    @Autowired
    ICollabShareFileService shareFileService;

    @Autowired
    ICollabShareFolderService shareFolderService;

    @Autowired
    FileService fileService;

    @Autowired
    FileServerConfig fileServerConfig;

    @Override
    public ICollabShareFileService getBaseService() {
        return shareFileService;
    }

    /**
     * 获取数据文件列表
     *
     * @param cbShareFolderId
     * @return ExtGridData<CollabShareFile>
     */
    public ExtGridData<CollabShareFile> list(String cbShareFolderId, Integer page, Integer limit) {

        ExtGridData<CollabShareFile> retVal = new ExtGridData<>();

        //先根据传入的cbShareFolderId查询对应的共享文件夹记录
        List<CollabShareFolder> queryList = shareFolderService.list(Restrictions.eq("id", cbShareFolderId), Order.desc("id"));
        queryList.forEach(collabShareFolder -> {
            //再根据belongFolder属性查询对应的共享文件记录
            Criterion relationCriterion = Restrictions.eq("belongFolder", collabShareFolder);
            PageBean pageBean = new PageBean();
            pageBean.setRows(limit);
            pageBean.setPage(page);
//        pageBean.setExampleFilter(filter);
            pageBean.addCriterion(relationCriterion);
            List<CollabShareFile> queryResult = shareFileService.listByPage(pageBean);
            retVal.setResults(queryResult);
            retVal.setTotalProperty(pageBean.getTotalCount());
        });

        return retVal;
    }

    /**
     * 修改数据文件名称
     *
     * @param formValue
     * @return void
     */
    public void update(CollabShareFile formValue) {
        CollabShareFile collabShareFile = shareFileService.getById(formValue.getId());
        collabShareFile.setName(formValue.getName());
        //collabShareFile.setFileType(formValue.getFileType());
        collabShareFile.setUpdateTime(formValue.getUpdateTime());
        //collabShareFile.setBelongFolder(formValue.getBelongFolder());
        shareFileService.update(collabShareFile);
    }

    /**
     * 批量删除数据文件
     *
     * @param toDelIds
     * @return void
     */
    public void delete(String[] toDelIds) {
        for (int i = 0; i < toDelIds.length; i++) {
            List<CollabShareFile> queryList = shareFileService.list(Restrictions.eq("id", toDelIds[i]));
            if (queryList.size() > 0) {
                CollabShareFile collabShareFile = queryList.get(0);
                //先删除共享文件
                String relativeFileLocation = collabShareFile.getFileLocation();
                String absolutePath = fileServerConfig.getFtpHome() + relativeFileLocation;
                File file = new File(absolutePath);
                if (file.exists()) {
                    file.delete();
                } else {
                    //去除文件不存在时，不可删除的限制--ZhangSheng 2018.9.5
                    //throw new OrientBaseAjaxException("", "共享文件不存在");
                }
                //再删除共享文件的数据库记录
                shareFileService.delete(collabShareFile);
            } else
                continue;
        }
    }

    /**
     * 删除共享文件夹对应的数据文件
     *
     * @param cbShareFolderId
     * @return void
     */
    public void deleteShareFile(String cbShareFolderId) {
        CollabShareFolder collabShareFolder = shareFolderService.list(Restrictions.eq("id", cbShareFolderId)).get(0);
        List<CollabShareFile> queryList = shareFileService.list(Restrictions.eq("belongFolder", collabShareFolder));
        queryList.forEach(collabShareFile -> {
            //删除共享文件
            String relativeFileLocation = collabShareFile.getFileLocation();
            String absolutePath = fileServerConfig.getFtpHome() + relativeFileLocation;
            File file = new File(absolutePath);
            if (file.exists()) {
                file.delete();
            }
        });
    }

    /**
     * 上传数据文件
     *
     * @param cbShareFolderId
     * @return void
     */
    public void uploadShareFile(String cbShareFolderId, HttpServletRequest request, HttpServletResponse response) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            DefaultMultipartHttpServletRequest multipartHttpServletRequest = (DefaultMultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> fileMultiValueMap = multipartHttpServletRequest.getMultiFileMap();
            //先存储共享文件
            fileMultiValueMap.forEach((key, value) -> value.forEach(multipartFile -> {
                long size = multipartFile.getSize();
                String fileName = multipartFile.getOriginalFilename();
                if (size > 0) {
                    String folder = FtpFileUtil.getRelativeUploadPath(FtpFileUtil.UPLOAD_ROOT);
                    FtpFileUtil.makeDir(fileServerConfig.getFtpHome(), folder);
                    String suffix = FileOperator.getSuffix(fileName);
                    String finalName = UUIDGenerator.getUUID() + "." + suffix;
                    String realFileStoragePath = fileServerConfig.getFtpHome() + folder + finalName;
                    try {
                        FileOperator.createFile(realFileStoragePath, multipartFile.getBytes());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //再在CB_SHARE_FILE数据库中插入对应的文件记录
                    List<CollabShareFolder> queryList = shareFolderService.list(Restrictions.eq("id", cbShareFolderId));
                    if (queryList.size() > 0) {
                        CollabShareFile collabShareFile = new CollabShareFile();
                        CollabShareFolder collabShareFolder = queryList.get(0);
                        collabShareFile.setBelongFolder(collabShareFolder);
                        collabShareFile.setName(fileName);
                        //获取当前用户的中文名
                        collabShareFile.setCreateUser(UserContextUtil.getUserAllName());
                        collabShareFile.setVersion(1);
                        collabShareFile.setFileType(suffix);
                        collabShareFile.setFileLocation(folder + finalName);
                        //获取当前的日期和时间
                        Date timeNow = new Date();
                        collabShareFile.setCreateTime(timeNow);
                        collabShareFile.setUpdateTime(timeNow);
                        shareFileService.save(collabShareFile);
                    } else
                        throw new OrientBaseAjaxException("", "共享文件夹不存在");
                }
            }));
        } else
            throw new OrientBaseAjaxException("", "请求中不包含文件信息");
    }

    /**
     * 下载数据文件
     *
     * @param shareFileId
     * @return void
     */
    public void downloadShareFile(String shareFileId, HttpServletRequest request, HttpServletResponse response) {

        List<CollabShareFile> queryList = shareFileService.list(Restrictions.eq("id", shareFileId));
        if (queryList.size() > 0) {
            CollabShareFile collabShareFile = queryList.get(0);
            String relativeFileLocation = collabShareFile.getFileLocation();
            String fileName = collabShareFile.getName();
            String absolutePath = fileServerConfig.getFtpHome() + relativeFileLocation;
            FileOperator.downLoadFile(request, response, absolutePath, fileName);
        }
    }
}
