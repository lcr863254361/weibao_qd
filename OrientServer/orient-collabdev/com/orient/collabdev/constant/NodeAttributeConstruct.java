package com.orient.collabdev.constant;

import com.orient.collabdev.model.CollabDevNodeVO;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-27 9:28 AM
 */
public interface NodeAttributeConstruct {

    void setIconCls(CollabDevNodeVO collabDevNodeVO);

    void setIsLeaf(CollabDevNodeVO collabDevNodeVO, PurposeHelper purpose);

    void setExpand(CollabDevNodeVO collabDevNodeVO, PurposeHelper purpose);
}
