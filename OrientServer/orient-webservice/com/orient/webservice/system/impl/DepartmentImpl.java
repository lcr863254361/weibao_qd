package com.orient.webservice.system.impl;

import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.webservice.system.IDepartment;

import java.util.ArrayList;
import java.util.List;

public class DepartmentImpl implements IDepartment {

    private IRoleUtil roleEngine;

    public IRoleUtil getRoleEngine() {
        return roleEngine;
    }

    public void setRoleEngine(IRoleUtil roleEngine) {
        this.roleEngine = roleEngine;
    }

    @Override
    public List<String> getDepartment() {
        List<String> ret = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        List<Department> deptList = new ArrayList<Department>(roleEngine.getRoleModel(false).getDepartments().values());
        for (Department dept : deptList) {
            sb.setLength(0);
            String id = dept.getId();
            String pid = dept.getPid();
            String name = dept.getName();
            String desc = dept.getNotes();
            sb.append(id + ";::;");
            sb.append(pid + ";::;");
            sb.append(name + ";::;");
            sb.append(desc);
            ret.add(sb.toString());
        }
        return ret;
    }

}
