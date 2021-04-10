package com.orient.collabdev.business.processing;

import com.orient.collabdev.model.ShareFolderWrapper;
import com.orient.sysmodel.domain.collabdev.datashare.CollabShareFolder;
import com.orient.sysmodel.service.collabdev.ICollabShareFolderService;
import com.orient.utils.BeanUtils;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.CommonResponseData;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据共享区-共享文件夹的业务处理层
 *
 * @author ZhangSheng
 * @creare 2018-08-22 9:37
 * @modifiedby ZhangSheng 2918-09-10 9:49
 */
@Component
public class ShareFolderBusiness extends BaseHibernateBusiness<CollabShareFolder> {

    @Autowired
    ICollabShareFolderService shareFolderService;

    @Autowired
    ShareFileBusiness shareFileBusiness;

    @Override
    public ICollabShareFolderService getBaseService() {
        return shareFolderService;
    }

    /**
     * 获取共享文件夹的列表
     *
     * @param node
     * @return List<ShareFolderWrapper>
     */
    public List<ShareFolderWrapper> list(String node, String collabNodeId) {
        List<ShareFolderWrapper> retVal = new ArrayList<>();
        List<CollabShareFolder> queryList;
        if ("root".equals(node)) {
            //为根节点时默认加载外侧协同项目树节点collabNodeId对应的的节点
            queryList = shareFolderService.list(Restrictions.eq("nodeId", collabNodeId), Order.desc("folderOrder"));
        } else {
            queryList = shareFolderService.list(Restrictions.eq("pid", node), Order.desc("folderOrder"));
        }
        queryList.forEach(collabShareFolder -> {
            ShareFolderWrapper tmpBean = new ShareFolderWrapper(collabShareFolder);
            retVal.add(tmpBean);
        });
        return retVal;
    }

    /**
     * 新增共享文件夹
     *
     * @param collabShareFolders
     * @return CommonResponseData
     */
    public CommonResponseData create(List<ShareFolderWrapper> collabShareFolders, String collabNodeId) {
        CommonResponseData retVal = new CommonResponseData();
        collabShareFolders.forEach(shareFolderWrapper -> {
            String folderName = shareFolderWrapper.getName();
            //文件夹名称的唯一性校验
            if (shareFolderService.count(Restrictions.eq("name", folderName)) == 0) {
                CollabShareFolder collabShareFolder = new CollabShareFolder();
                BeanUtils.copyProperties(collabShareFolder, shareFolderWrapper);
                collabShareFolder.setPid(shareFolderWrapper.getPid());
                //只有为根节点时数据库中才存储NodeId
                if (shareFolderWrapper.getPid().equals("0")) {
                    collabShareFolder.setNodeId(collabNodeId);
                }
                shareFolderService.save(collabShareFolder);
            } else {
                retVal.setSuccess(false);
                retVal.setMsg("数据库中已经存在此文件夹");
            }
        });
        return retVal;
    }

    /**
     * 删除共享文件夹
     *
     * @param toDeleteData
     * @return CommonResponseData
     */
    public CommonResponseData delete(List<ShareFolderWrapper> toDeleteData) {
        CommonResponseData retVal = new CommonResponseData();
        toDeleteData.forEach(shareFolderWrapper -> {
            String delFolderId = shareFolderWrapper.getId();
            deleteSubFoldersAndFiles(delFolderId);
        });
        return retVal;
    }

    /**
     * 修改共享文件夹
     *
     * @param toUpdateData
     * @return CommonResponseData
     */
    public CommonResponseData update(List<ShareFolderWrapper> toUpdateData) {
        CommonResponseData retVal = new CommonResponseData();
        toUpdateData.forEach(shareFolderWrapper -> {
            String folderName = shareFolderWrapper.getName();
            if (shareFolderService.count(Restrictions.eq("name", folderName)) == 0) {
                CollabShareFolder collabShareFolder = new CollabShareFolder();
                BeanUtils.copyProperties(collabShareFolder, shareFolderWrapper);
                collabShareFolder.setPid(shareFolderWrapper.getPid());
                shareFolderService.update(collabShareFolder);
            } else {
                retVal.setSuccess(false);
                retVal.setMsg("数据库中已经存在此文件夹");
            }
        });
        return retVal;
    }

    /**
     * 递归删除文件夹下的所有子文件夹，同时删除子文件夹对应的数据文件
     *
     * @param delFolderId
     * @return void
     */
    public void deleteSubFolders(String delFolderId) {
        List<CollabShareFolder> subdelList = shareFolderService.list(Restrictions.eq("pid", delFolderId));
        subdelList.forEach(subdelItem -> {
            String subFolderId = subdelItem.getId();
            deleteSubFoldersAndFiles(subFolderId);
        });
    }

    /**
     * 删除子文件夹，及其对应的数据文件
     *
     * @param delFolderId
     * @return void
     */
    public void deleteSubFoldersAndFiles(String delFolderId) {
        //删除子文件夹的子文件夹
        deleteSubFolders(delFolderId);
        //删除子文件夹对应的数据文件
        shareFileBusiness.deleteShareFile(delFolderId);
        //删除子文件夹的数据库记录
        shareFolderService.delete(delFolderId);
    }
}
