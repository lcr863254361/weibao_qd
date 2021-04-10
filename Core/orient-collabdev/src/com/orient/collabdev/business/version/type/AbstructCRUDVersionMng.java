package com.orient.collabdev.business.version.type;

import com.orient.collabdev.business.version.status.IVersionModifyer;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-28 10:23 AM
 */
public abstract class AbstructCRUDVersionMng implements ICRUDVersionMng {

    protected IVersionModifyer versionModifyer;
}
