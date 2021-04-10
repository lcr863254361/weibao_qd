package com.orient.edm.sysCtrl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * manage the system's global control
 *
 * @author Seraph
 *         2016-10-12 下午4:56
 */
public class SystemManager {

    public static SystemManager getManager(){
        return systemManager;
    }

    private static final SystemManager systemManager = new SystemManager();

    private SystemManager(){

    }

    public void registerSystemControlPoint(SystemControlPoint systemControlPoint){
        this.controlPoints.add(systemControlPoint);
    }

    public void removeSystemControlPoint(SystemControlPoint systemControlPoint){
        this.controlPoints.remove(systemControlPoint);
    }

    public void lock(){
        controlPoints.stream().forEach(systemControlPoint -> systemControlPoint.lock());
    }

    public void unlock(){
        controlPoints.stream().forEach(systemControlPoint -> systemControlPoint.unlock());
    }

    private Queue<SystemControlPoint> controlPoints = new ConcurrentLinkedQueue<>();

}
