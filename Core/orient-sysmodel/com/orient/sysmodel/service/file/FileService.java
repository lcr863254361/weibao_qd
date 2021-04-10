/**
 * FileService.java
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

import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.domain.file.CwmFileGroup;

import java.util.List;

/**
 * ClassName:FileService
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @Date 2012-4-23		上午10:32:39
 * @see
 * @since Ver 1.1
 */
public interface FileService {

    /**
     * 新增文件
     *
     * @param file
     */
    void createFile(CwmFile file);

    /**
     * 更新文件
     *
     * @param file
     */
    void updateFile(CwmFile file);

    /**
     * 删除文件
     *
     * @param fileId 文件id
     */
    void deleteFile(String fileId);

    /**
     * 取得所有有效文件
     *
     * @return
     */
    List<CwmFile> findAllFiles();//区分文件是否删除

    /**
     * 根据密级取得该密级的有效文件
     *
     * @param secrecy 密级
     * @return
     */
    List<CwmFile> findFilesBySecrecy(String secrecy);//区分文件是否删除

    /**
     * 根据密级取得该密级内的有效文件
     *
     * @param secrecy 密级
     * @return
     */
    List<CwmFile> findFilesBelongSecrecy(String secrecy);//区分文件是否删除

    /**
     * 取得数据记录的附件
     *
     * @param tableId
     * @param dataId
     * @return
     */
    List<CwmFile> findFilesOfDate(String tableId, String dataId);//区分文件是否删除

    /**
     * 取得数据表的附件
     *
     * @param tableId
     * @return
     */
    List<CwmFile> findFilesOfTable(String tableId);//区分文件是否删除

    /**
     * 根据查询条件查找文件
     *
     * @param queryInfo 查询条件
     * @return
     */
    List<CwmFile> findFiles(CwmFile queryInfo);//区分文件是否删除

    /**
     * 仅根据文件路径查找文件
     *
     * @param fileLocation 文件路径
     * @return
     */
    List<CwmFile> findFilesByFileLocation(String fileLocation);

    /**
     * 取得文件
     *
     * @param fileId
     * @return CwmFile
     */
    CwmFile findFileById(String fileId);//区分文件是否删除

    /**
     * 根据名称和版本查找文件
     *
     * @param fname
     * @param fileEdition
     * @return List<CwmFile>
     */
    List<CwmFile> findFiles(String fname, String fileEdition);//区分文件是否删除

    /**
     * 检查文件名是否存在
     *
     * @param fname
     * @return boolean true:存在, false:不存在
     */
    boolean checkFileName(String fname);//不区分文件是否删除

    /**
     * 检查文件路径是否存在
     *
     * @param fileLocation
     * @return boolean true:存在, false:不存在
     */
    boolean checkFileLocation(String fileLocation);//不区分文件是否删除

    /**
     * 删除该schema下的所有文件以及文件记录
     *
     * @param schemaId
     * @param isValid
     * @return boolean true:删除成功, false:删除失败
     */
    void deleteFilesBySchemaId(Object schemaId, Object isValid);//区分文件是否删除

    List<CwmFile> findByProperty(String propertyName, Object value);

    String getFileContentType(String fileType);

    /**
     * 查找文件类型分组
     */
    List<CwmFileGroup> findFileGroups();

    List<CwmFile> findFileByGroup(String tableId, String dataId, String groupName);

    /**
     * 根据文件后缀识别文件的类型(分组名)
     */
    String identifyFileType(String fileType);

    boolean copyFileToServer(String fileName);

    long findFileByModelAndDataAndGroupCount(String modelId, String dataId, List<String> fileGroupFilter);

    List<CwmFile> findFileByModelAndDataAndGroup(String modelId, String dataId, List<String> suffixFilter, Integer page, Integer limit);

    List<CwmFile> findByFinalNames(List<String> finalNames);
}

