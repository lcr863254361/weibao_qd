package com.orient.collabdev.business.version.event;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-11 2:29 PM
 */
public class CollabNodeDeleteEvent extends CollabVersionChangeEvent {

    public CollabNodeDeleteEvent(Object source, CollabNodeDeleteEventParam params) {
        super(source, params);
    }
}
