package com.orient.workflow.cmd;

import org.hibernate.criterion.Restrictions;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.hibernate.DbSessionImpl;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;
import org.jbpm.pvm.internal.session.DbSession;

public class DeleteHisProcessInstance implements Command<Object> {
    //serialVersionUID is
    private String piId;

    public DeleteHisProcessInstance(String piId) {
        this.piId = piId;
    }

    public Object execute(Environment env) throws Exception {
        DbSessionImpl dbSession = (DbSessionImpl) env.get(DbSession.class);
        HistoryProcessInstanceImpl historyProcessInstance = (HistoryProcessInstanceImpl) dbSession.getSession().createCriteria(HistoryProcessInstanceImpl.class).add(Restrictions.eq("processInstanceId", piId)).uniqueResult();
        dbSession.getSession().delete(historyProcessInstance);
        //dbSession.getSession().getTransaction().commit();
        return true;
    }

}
