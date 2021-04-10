package com.orient.collab.business.projectCore.constant;

import static com.orient.collab.business.projectCore.constant.ProjectCoreConstant.*;

/**
 * ProcessType
 *
 * @author Seraph
 *         2016-08-12 下午3:36
 */
public enum ProcessType {

    START(PROCESS_TYPE_START),
    SUBMIT(PROCESS_TYPE_SUBMIT),
    SUSPEND(PROCESS_TYPE_SUSPEND),
    RESUME(PROCESS_TYPE_RESUME),
    CLOSE(PROCESS_TYPE_CLOSE);

    ProcessType(String type){
        this.type = type;
    }

    private final String type;

    @Override
    public String toString(){
        return this.type;
    }
}
