package com.orient.workflow.bean;

import com.orient.sysmodel.operationinterface.IUser;
import org.jbpm.api.ProcessDefinition;

import java.util.List;

public class AgencyWorkflow {

    private IUser user;
    private IUser configUser;
    private List<ProcessDefinition> pds;

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public IUser getConfigUser() {
        return configUser;
    }

    public void setConfigUser(IUser configUser) {
        this.configUser = configUser;
    }

    public List<ProcessDefinition> getPds() {
        return pds;
    }

    public void setPds(List<ProcessDefinition> pds) {
        this.pds = pds;
    }

}
