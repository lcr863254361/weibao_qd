package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.model.StatefulModel;
import com.orient.sqlengine.api.ISqlEngine;

/**
 * try to change status
 *
 * @author Seraph
 *         2016-07-21 下午3:34
 */
public class ChangeActualStartDateCmd implements Command<Boolean> {

    public ChangeActualStartDateCmd(StatefulModel sm, String newDate, ISqlEngine sqlEngine){
        this.sm = sm;
        this.sm.setActualStartDate(newDate);
        this.sqlEngine = sqlEngine;
    }

    @Override
    public Boolean execute() throws Exception {
        return sqlEngine.getTypeMappingBmService().update(sm);
    }

    private StatefulModel sm;
    private ISqlEngine sqlEngine;
}