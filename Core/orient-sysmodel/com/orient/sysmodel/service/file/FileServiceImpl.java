/**
 * FileServiceImpl.java
 * com.orient.sysmodel.service.file
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-4-23 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.service.file;

import com.orient.metamodel.metadomain.Enum;
import com.orient.metamodel.metaengine.dao.EnumDAO;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.file.CwmFileDAO;
import com.orient.sysmodel.domain.file.CwmFileGroup;
import com.orient.sysmodel.domain.file.CwmFileGroupDAO;
import com.orient.sysmodel.domain.syslog.CodeToName;
import com.orient.sysmodel.domain.syslog.CodeToNameDAO;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import com.orient.utils.PathTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:FileServiceImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @Date 2012-4-23		上午10:33:56
 * @see
 * @since Ver 1.1
 */
public class FileServiceImpl implements FileService {

    private CwmFileDAO dao;
    private CwmFileGroupDAO groupDao;
    private EnumDAO enumDao;
    private CodeToNameDAO codeToNameDao;


    public CodeToNameDAO getCodeToNameDao() {
        return codeToNameDao;
    }

    public void setCodeToNameDao(CodeToNameDAO codeToNameDao) {
        this.codeToNameDao = codeToNameDao;
    }

    /**
     * dao
     *
     * @return the dao
     * @since CodingExample Ver 1.0
     */

    public CwmFileDAO getDao() {
        return dao;
    }

    /**
     * dao
     *
     * @param dao the dao to set
     * @since CodingExample Ver 1.0
     */

    public void setDao(CwmFileDAO dao) {
        this.dao = dao;
    }

    public CwmFileGroupDAO getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(CwmFileGroupDAO groupDao) {
        this.groupDao = groupDao;
    }

    /**
     * enumDao
     *
     * @return the enumDao
     * @since CodingExample Ver 1.0
     */

    public EnumDAO getEnumDao() {
        return enumDao;
    }

    /**
     * enumDao
     *
     * @param enumDao the enumDao to set
     * @since CodingExample Ver 1.0
     */

    public void setEnumDao(EnumDAO enumDao) {
        this.enumDao = enumDao;
    }

    /**
     * @param file
     * @Method: createFile
     * <p>
     * 新增文件
     * @see com.orient.sysmodel.service.file.FileService#createFile(com.orient.sysmodel.domain.file.CwmFile)
     */
    public void createFile(CwmFile file) {
        //绑定后缀
        file.setFiletype(FileOperator.getSuffix(file.getFilename()));
        dao.save(file);
    }

    /**
     * @param file
     * @Method: updateFile
     * <p>
     * 更新文件
     * @see com.orient.sysmodel.service.file.FileService#updateFile(com.orient.sysmodel.domain.file.CwmFile)
     */
    public void updateFile(CwmFile file) {
        dao.attachDirty(file);
    }

    /**
     * @param fileId
     * @Method: deleteFile
     * <p>
     * 删除文件
     * @see com.orient.sysmodel.service.file.FileService#deleteFile(java.lang.String)
     */
    public void deleteFile(String fileId) {
        CwmFile file = dao.findById(fileId);
        dao.delete(file);
    }

    /**
     * @return
     * @Method: findAllFiles
     * <p>
     * 取得所有有效文件
     * @see com.orient.sysmodel.service.file.FileService#findAllFiles()
     */
    public List<CwmFile> findAllFiles() {
        //区分文件是否删除
        return dao.findAll();
        //return dao.findByIsValid(1);
    }

    /**
     * @param secrecy 机密、秘密、内部、非密
     * @return
     * @Method: findFilesBySecrecy
     * <p>
     * 根据密级取得该密级的有效文件
     * @see com.orient.sysmodel.service.file.FileService#findFilesBySecrecy(java.lang.String)
     */
    public List<CwmFile> findFilesBySecrecy(String secrecy) {

        String secrecyValue = "";
        List<Enum> enumList = enumDao.findByRestrictionId("u3");
        if (enumList != null && enumList.size() > 0) {
            for (int i = 0; i < enumList.size(); i++) {
                Enum enumItem = (Enum) enumList.get(i);
                if (enumItem.getDisplayValue().equalsIgnoreCase(secrecy) && enumItem.getIsopen() == 1) {
                    secrecyValue = enumItem.getValue();
                }
            }
        }

        //区分文件是否删除
        String hql = "from CwmFile where isValid=1 ";
        if (!secrecyValue.equalsIgnoreCase("")) {
            hql = hql + " and filesecrecy='" + secrecyValue + "'";
        } else {
            hql = hql + " and (filesecrecy='' or filesecrecy=null)";
        }

        return dao.getHqlResult(hql);
    }

    /**
     * @param secrecy
     * @return
     * @Method: findFilesBelongSecrecy
     * <p>
     * 根据密级取得该密级内的有效文件
     * @see com.orient.sysmodel.service.file.FileService#findFilesBelongSecrecy(java.lang.String)
     */
    public List<CwmFile> findFilesBelongSecrecy(String secrecy) {
        String secrecyValue = this.getSecrecyValue(secrecy);

        //区分文件是否删除
        String hql = "from CwmFile where isValid=1 ";
        if (!secrecyValue.equalsIgnoreCase("")) {
            secrecyValue = secrecyValue.replaceAll(",", "','");
            hql = hql + " and (filesecrecy in ('" + secrecyValue + "')  or filesecrecy='' or filesecrecy=null)";
        } else {
            hql = hql + " and (filesecrecy='' or filesecrecy=null)";
        }

        return dao.getHqlResult(hql);
    }

    private String getSecrecyValue(String secrecy) {
        String secrecyValue = "";
        if (secrecy.equalsIgnoreCase("机密")) {
            secrecy = "机密, 秘密, 内部, 非密";
        } else if (secrecy.equalsIgnoreCase("秘密")) {
            secrecy = "秘密, 内部, 非密";
        } else if (secrecy.equalsIgnoreCase("内部")) {
            secrecy = "内部, 非密";
        }

        List<Enum> enumList = enumDao.findByRestrictionId("u3");
        if (enumList != null && enumList.size() > 0) {
            for (int i = 0; i < enumList.size(); i++) {
                Enum enumItem = (Enum) enumList.get(i);
                if (secrecy.contains(enumItem.getDisplayValue()) && enumItem.getIsopen() == 1) {
                    secrecyValue = secrecyValue + enumItem.getValue() + ",";
                }
            }
        }

        if (!secrecyValue.equalsIgnoreCase("")) {
            secrecyValue = secrecyValue.substring(0, secrecyValue.length() - 1);
        }

        return secrecyValue;
    }

    /**
     * @param tableId
     * @param dataId
     * @return
     * @Method: findFilesOfDate
     * <p>
     * 取得数据记录的附件
     * @see com.orient.sysmodel.service.file.FileService#findFilesOfDate(java.lang.String, java.lang.String)
     */
    public List<CwmFile> findFilesOfDate(String tableId, String dataId) {
        //区分文件是否删除
        String hql = "from CwmFile where isValid=1 ";
        hql = hql + " and tableid='" + tableId + "' and dataid='" + dataId + "'";
        return dao.getHqlResult(hql);
    }

    /**
     * @param tableId
     * @return
     * @Method: findFilesOfTable
     * <p>
     * 取得数据表的附件
     * @see com.orient.sysmodel.service.file.FileService#findFilesOfTable(java.lang.String)
     */
    public List<CwmFile> findFilesOfTable(String tableId) {
        //区分文件是否删除
        String hql = "from CwmFile where isValid=1 ";
        hql = hql + " and tableid='" + tableId + "'";
        return dao.getHqlResult(hql);
    }

    public List<CwmFile> findFilesByFileLocation(String fileLocation) {
        return dao.findByFilelocation(fileLocation);
    }

    /**
     * @param queryInfo 查询条件
     * @return
     * @Method: findFiles
     * <p>
     * 根据查询条件查找文件
     * @see com.orient.sysmodel.service.file.FileService#findFiles(com.orient.sysmodel.domain.file.CwmFile)
     */
    public List<CwmFile> findFiles(CwmFile queryInfo) {
        //区分文件是否删除
        StringBuffer hql = new StringBuffer();
        hql.append("from CwmFile where isValid=1 ");

        if (!"".equals(queryInfo.getFilename())) {
            hql.append("AND filename LIKE '%").append(queryInfo.getFilename()).append("%' ");
        }
        if (!"".equals(queryInfo.getEdition())) {
            hql.append("AND edition = '").append(queryInfo.getEdition()).append("' ");
        }
        if (!"".equals(queryInfo.getFiledescription())) {
            hql.append("AND filedescription like '%").append(queryInfo.getFiledescription()).append("%' ");
        }
        if (!"".equals(queryInfo.getFilesecrecy())) {
            String secrecyValue = this.getSecrecyValue(queryInfo.getFilesecrecy());
            if (!secrecyValue.equalsIgnoreCase("")) {
                secrecyValue = secrecyValue.replaceAll(",", "','");
                hql.append(" and (filesecrecy in ('" + secrecyValue + "')  or filesecrecy='' or filesecrecy=null)");
            } else {
                hql.append(" and (filesecrecy='' or filesecrecy=null)");
            }

        }
        if (!"".equals(queryInfo.getUploadUserName())) {
            hql.append("AND uploadUser.id in ( select id from user where allName LIKE '%").append(queryInfo.getUploadUserName()).append("%') ");
        }
        if (!"".equals(queryInfo.getUploadDateS())) {
            hql.append("AND uploadDate >= to_date('").append(queryInfo.getUploadDateS()).append(
                    " 00:00:00','yyyy-mm-dd hh24:Mi:ss') ");
        }
        if (!"".equals(queryInfo.getUploadDateE())) {
            hql.append("AND uploadDate <= to_date('").append(queryInfo.getUploadDateE()).append(
                    " 23:59:59','yyyy-mm-dd hh24:Mi:ss') ");
        }

        return dao.getHqlResult(hql.toString());
    }

    /**
     * @param fileId
     * @return
     * @Method: findFileById
     * <p>
     * 取得文件
     * @see com.orient.sysmodel.service.file.FileService#findFileById(java.lang.String)
     */
    public CwmFile findFileById(String fileId) {
        //区分文件是否删除
        return dao.findById(fileId);
    }

    /**
     * @param fname
     * @param fileEdition
     * @return
     * @Method: findFiles
     * <p>
     * 根据名称和版本查找文件
     * @see com.orient.sysmodel.service.file.FileService#findFiles(java.lang.String, java.lang.String)
     */
    public List<CwmFile> findFiles(String fname, String fileEdition) {
        //区分文件是否删除
        String hql = "from CwmFile where isValid=1 ";
        hql = hql + " and filename='" + fname + "' and edition='" + fileEdition + "'";
        return dao.getHqlResult(hql);
    }

    /**
     * @param fname
     * @return true:存在, false:不存在
     * @Method: checkFileName
     * <p>
     * 检查文件名是否存在
     * @see com.orient.sysmodel.service.file.FileService#checkFileName(java.lang.String)
     */
    public boolean checkFileName(String fname) {
        //不区分文件是否删除
        List<CwmFile> fileList = dao.findByFilename(fname);
        if (fileList != null && fileList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param fileLocation
     * @return true:存在, false:不存在
     * @Method: checkFileLocation
     * <p>
     * 检查文件路径是否存在
     * @see com.orient.sysmodel.service.file.FileService#checkFileLocation(java.lang.String)
     */
    public boolean checkFileLocation(String fileLocation) {
        //不区分文件是否删除
        List<CwmFile> fileList = dao.findByFilelocation(fileLocation);
        if (fileList != null && fileList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteFilesBySchemaId(Object schemaId, Object isValid) {
        List<CwmFile> fileList = dao.findBySchemaidAndIsValid(schemaId, isValid);
        for (CwmFile cwmFile : fileList) {
            String fp = cwmFile.getFilelocation();
            String filePath = PathTools.getFtpUrl() + fp;
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            dao.delete(cwmFile);
        }
    }

    public List<CwmFile> findByProperty(String propertyName, Object value) {

        return dao.findByProperty(propertyName, value);
    }

    public String getFileContentType(String fileType) {

        StringBuffer sql = new StringBuffer();
        sql.append(" FROM CodeToName ");
        sql.append("WHERE id.name = '" + fileType + "' ORDER BY id.id ASC ");
        List<CodeToName> codes = codeToNameDao.getSqlResult(sql.toString());
        String contentType = "";

        if (codes != null && codes.size() > 0) {
            contentType = codes.get(0).getId().getRemark();
        } else {
            contentType = "application/x-msdownload";
        }

        return contentType;
    }

    public List<CwmFileGroup> findFileGroups() {
        List<CwmFileGroup> groups = groupDao.findByIsShow(1L);
        return groups;
    }

    public List<CwmFile> findFileByGroup(String tableId, String dataId, String groupName) {
        return dao.findByFileGroup(tableId, dataId, groupName);
    }

    public String identifyFileType(String fileType) {
        String ret = "";
        String hql = "FROM CwmFileGroup where groupType LIKE '%" + fileType + "%'";
        List<CwmFileGroup> list = (List<CwmFileGroup>) dao.getHibernateTemplate().find(hql);
        if (!list.isEmpty()) {
            ret = list.get(0).getGroupName();
        }
        return ret;
    }

    public boolean copyFileToServer(String fileName) {
        String uploadPath = PathTools.getFtpUrl();
        String rootPath = PathTools.getRootPath();
        File drectorfile = new File(rootPath + File.separator + "onLineFile" + File.separator + fileName);
        if (!drectorfile.isFile()) {
            try {
                PathTools.copyFile(uploadPath + File.separator + fileName, rootPath + File.separator + "onLineFile" + File.separator + fileName);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }


    @Override
    public long findFileByModelAndDataAndGroupCount(String modelId, String dataId, List<String> fileGroupFilter) {
        return dao.findFileByModelAndDataAndGroupCount(modelId, dataId, fileGroupFilter);
    }

    @Override
    public List<CwmFile> findFileByModelAndDataAndGroup(String modelId, String dataId, List<String> suffixFilter, Integer page, Integer limit) {
        return dao.findFileByModelAndDataAndGroup(modelId, dataId, suffixFilter, page, limit);
    }

    @Override
    public List<CwmFile> findByFinalNames(List<String> finalNames) {
        List<CwmFile> retVal = new ArrayList<>();
        if (!CommonTools.isEmptyList(finalNames)) {
            retVal = dao.findByFinalNames(finalNames);
        }
        return retVal;
    }
}

