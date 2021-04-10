package com.orient.collab.business.projectCore.cmd.concrete;

import com.orient.collab.business.projectCore.cmd.Command;
import com.orient.collab.model.StatefulModel;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sqlengine.api.ISqlEngine;

/**
 * try to change status
 *
 * @author Seraph
 *         2016-07-21 下午3:34
 */
public class ChangeStateCmd implements Command<Boolean> {

    public ChangeStateCmd(StatefulModel sm, String newStatus, ISqlEngine sqlEngine){
        this.sm = sm;
        this.sm.setStatus(newStatus);
        this.sqlEngine = sqlEngine;
    }

    @Override
    public Boolean execute() throws Exception {
        return sqlEngine.getTypeMappingBmService().update(sm);
    }

    private StatefulModel sm;
    private ISqlEngine sqlEngine;
}