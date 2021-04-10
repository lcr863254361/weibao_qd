package com.orient.collabdev.business.version.event;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-11 2:29 PM
 */
public class CollabNodeCreateEvent extends CollabVersionChangeEvent {

    public CollabNodeCreateEvent(Object source, CollabNodeCreateEventParam params) {
        super(source, params);
    }
}
