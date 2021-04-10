package com.orient.collabdev.business.common.pedigree.event;

import com.orient.web.base.OrientEventBus.OrientEvent;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 2:28 PM
 */
public class GetPedigreeEvent extends OrientEvent {


    public GetPedigreeEvent(Object source, GetPedigreeEventParam params) {
        super(source, params);
    }
}
