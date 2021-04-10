package com.orient.workflow.cmd;

import org.hibernate.Session;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class GetHisSubTask implements Command<List<HistoryTaskImpl>> {

    private String dbId;

    public GetHisSubTask(String dbId) {
        this.dbId = dbId;
    }

    @Override
    public List<HistoryTaskImpl> execute(Environment environment) throws Exception {
        Session session = environment.get(Session.class);
        List<HistoryTaskImpl> retVal = session.createSQLQuery("SELECT * FROM JBPM4_HIST_TASK WHERE SUPERTASK_ = " + dbId).addEntity(HistoryTaskImpl.class).list();
        return retVal;
    }
}
