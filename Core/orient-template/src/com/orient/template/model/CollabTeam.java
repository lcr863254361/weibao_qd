package com.orient.template.model;

import com.orient.sysmodel.domain.collab.CollabFunction;
import com.orient.sysmodel.domain.collab.CollabRole;

import java.io.Serializable;
import java.util.List;

/**
 * used as root in collab team's template
 *
 * @author Seraph
 *         2016-09-30 下午1:01
 */
public class CollabTeam implements Serializable {

    private String belongedModelName;
    private String belongedDataId;

    private List<CollabRole> roles;
    private List<CollabFunction> functions;

    private static final long serialVersionUID =  1L;

    public CollabTeam(String belongedModelName, String belongedDataId){
        this.belongedModelName = belongedModelName;
        this.belongedDataId = belongedDataId;
    }

    public List<CollabRole> getRoles() {
        return roles;
    }

    public void setRoles(List<CollabRole> roles) {
        this.roles = roles;
    }

    public List<CollabFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(List<CollabFunction> functions) {
        this.functions = functions;
    }

    public String getBelongedModelName() {
        return belongedModelName;
    }

    public String getBelongedDataId() {
        return belongedDataId;
    }

}
