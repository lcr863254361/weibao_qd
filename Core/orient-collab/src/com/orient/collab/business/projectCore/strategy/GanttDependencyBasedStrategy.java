package com.orient.collab.business.projectCore.strategy;

import com.orient.collab.model.Plan;
import com.orient.utils.UtilFactory;
import java.util.Map;
import static com.orient.collab.config.CollabConstants.STATUS_FINISHED;
import static com.orient.collab.config.CollabConstants.STATUS_UNSTARTED;

/**
 * Gantt Plan Dependency Type
 * we have assertion here that a plan cannot change it's state only after it's restriction of dependency meets,
 * for example, if two plans have a FS dependency, then the method canToPlanEnd will always return true because
 * the to plan cannot start not util the from plan have ended and the from plan has no effect on the to plan
 * at all when it comes to end to plan.
 *
 * @author Seraph
 *         2016-07-18 下午5:18
 */
public enum GanttDependencyBasedStrategy {

    SS(0) {
        @Override
        public boolean canToPlanStart(Plan from, Plan to) {
            if(from.getStatus().equals("0")){
                return false;
            }
            return true;
        }

        @Override
        public boolean canToPlanEnd(Plan from, Plan to) {
            return true;
        }
    },
    SF(1) {
        @Override
        public boolean canToPlanStart(Plan from, Plan to) {
            return true;
        }

        @Override
        public boolean canToPlanEnd(Plan from, Plan to) {
            if(from.getStatus().equals("0")){
                return false;
            }

            return true;
        }
    },
    FS(2) {
        @Override
        public boolean canToPlanStart(Plan from, Plan to) {
            if(from.getStatus().equals("2")){
                return true;
            }
            return false;
        }

        @Override
        public boolean canToPlanEnd(Plan from, Plan to) {
            return true;
        }
    },
    FF(3) {
        @Override
        public boolean canToPlanStart(Plan from, Plan to) {
            return true;
        }

        @Override
        public boolean canToPlanEnd(Plan from, Plan to) {
            if(from.getStatus().equals("2")){
                return true;
            }
            return false;
        }
    };

    GanttDependencyBasedStrategy(int type){
        this.type = type;
    }

    abstract public boolean canToPlanStart(Plan from, Plan to);
    abstract public boolean canToPlanEnd(Plan from, Plan to);

    private final int type;
    private int getType(){
        return this.type;
    }

    // static
    public static GanttDependencyBasedStrategy fromType(int type){
        return typeToEnum.get(type);
    }

    private static final Map<Integer, GanttDependencyBasedStrategy> typeToEnum = UtilFactory.newHashMap();
    static {
        for(GanttDependencyBasedStrategy strategy : GanttDependencyBasedStrategy.values()){
            typeToEnum.put(strategy.getType(), strategy);
        }
    }
}
