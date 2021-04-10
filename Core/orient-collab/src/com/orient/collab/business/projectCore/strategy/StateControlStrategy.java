package com.orient.collab.business.projectCore.strategy;

import com.orient.collab.business.projectCore.exception.StateIllegalException;
import com.orient.collab.model.Plan;
import com.orient.collab.model.StatefulModel;
import com.orient.collab.model.Task;
import com.orient.utils.UtilFactory;

import java.util.Map;

import static com.orient.collab.config.CollabConstants.*;

/**
 * the state control strategy
 *
 * @author Seraph
 *         2016-07-21 下午3:15
 */
public enum StateControlStrategy{

    Unstarted(STATUS_UNSTARTED),
    Processing(STATUS_PROCESSING),
    Completed(STATUS_FINISHED),
    Suspended(STATUS_SUSPENDED);

    StateControlStrategy(String stateName){
        this.stateName = stateName;
    }

    public void tryStart(StatefulModel sm) throws StateIllegalException{
        //TODO 在配置为没有project节点时,规则略有不同
        if(sm instanceof Plan || sm instanceof Task){
            if(Processing != this){
                throw new StateIllegalException("仅在'进行中'状态下可启动");
            }
        }else{
            if(Unstarted != this){
                throw new StateIllegalException("仅在'未开始'状态下可启动");
            }
        }
    }

    public void trySubmit(StatefulModel sm) throws StateIllegalException{
        if(Processing != this){
            throw new StateIllegalException("仅'进行中'状态下可提交");
        }
    }

    public void trySuspend(StatefulModel sm) throws StateIllegalException{
        if(Processing != this){
            throw new StateIllegalException("节点非'进行中',不可暂停");
        }
    }

    public void tryResume(StatefulModel sm) throws StateIllegalException{
        if(Suspended != this){
            throw new StateIllegalException("节点未暂停");
        }
    }

    public void tryDeleteFlow(StatefulModel sm) throws StateIllegalException{
        if(Suspended != this){
            throw new StateIllegalException("仅在暂停状态下可删除");
        }
    }

    @Override
    public String toString(){
        return stateName;
    }

    private final String stateName;

    // static
    public static StateControlStrategy fromString(String state){
        return stringToEnum.get(state);
    }

    private static final Map<String, StateControlStrategy> stringToEnum = UtilFactory.newHashMap();
    static {
        for(StateControlStrategy strategy : StateControlStrategy.values()){
            stringToEnum.put(strategy.toString(), strategy);
        }
    }

}
