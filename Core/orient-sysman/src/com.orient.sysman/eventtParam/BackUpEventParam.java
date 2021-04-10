package com.orient.sysman.eventtParam;

import com.orient.sysmodel.domain.sys.CwmBackEntity;
import com.orient.web.base.OrientEventBus.OrientEventParams;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-29 18:03
 */
public class BackUpEventParam extends OrientEventParams {

    private CwmBackEntity backEntity;

    public BackUpEventParam(CwmBackEntity backEntity) {
        this.backEntity = backEntity;
    }

    public CwmBackEntity getBackEntity() {
        return backEntity;
    }

    public void setBackEntity(CwmBackEntity backEntity) {
        this.backEntity = backEntity;
    }
}
