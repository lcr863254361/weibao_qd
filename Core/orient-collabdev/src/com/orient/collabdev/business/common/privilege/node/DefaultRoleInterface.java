package com.orient.collabdev.business.common.privilege.node;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-21 9:27 AM
 */
public interface DefaultRoleInterface {

    void create(String userIds, String nodeId);

    void replaceAssign(String nodeId, String originalAssign, String targetAssign, boolean recursive);
}
