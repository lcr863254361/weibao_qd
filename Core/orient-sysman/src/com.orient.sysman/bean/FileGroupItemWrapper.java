package com.orient.sysman.bean;

import com.orient.sysmodel.domain.file.CwmFileGroupItemEntity;

import java.io.Serializable;

/**
 * 文件分组项模型包装类
 *
 * @author enjoy
 * @creare 2016-05-03 9:15
 */
public class FileGroupItemWrapper extends CwmFileGroupItemEntity implements Serializable {

    private Long belongFileGroupId;

    public FileGroupItemWrapper() {

    }

    //属性拷贝
    public FileGroupItemWrapper(CwmFileGroupItemEntity fileGroupItemEntity) {
        this.setId(fileGroupItemEntity.getId());
        this.setName(fileGroupItemEntity.getName());
        this.setSuffix(fileGroupItemEntity.getSuffix());
        if (null != this.getBelongFileGroup()) {
            this.setBelongFileGroupId(this.getBelongFileGroup().getId());
        }
    }

    public Long getBelongFileGroupId() {
        return belongFileGroupId;
    }

    public void setBelongFileGroupId(Long belongFileGroupId) {
        this.belongFileGroupId = belongFileGroupId;
    }
}
