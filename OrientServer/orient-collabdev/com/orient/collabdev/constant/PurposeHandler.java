package com.orient.collabdev.constant;

import com.orient.collabdev.model.CollabDevNodeVO;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-27 9:28 AM
 */
public interface PurposeHandler {

    void handleNode(List<CollabDevNodeVO> collabDevNodeVOS);

    boolean isLeaf();
}
