package com.orient.edm.sysCtrl;

/**
 * represent a TDM system control point,
 * this class need not to be registered as a Spring bean
 * but should register itself to {@link SystemManager}
 *
 * @author Seraph
 *         2016-10-12 下午4:24
 */
public interface SystemControlPoint {

    default void register(){
        SystemManager.getManager().registerSystemControlPoint(this);
    }

    void lock();

    void unlock();
}
