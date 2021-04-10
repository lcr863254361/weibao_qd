package com.orient.sysman.bussiness;

import com.orient.config.ConfigInfo;
import com.orient.sysman.bean.DeptBean;
import com.orient.sysmodel.domain.user.Department;
import com.orient.sysmodel.service.user.DepartmentService;
import com.orient.web.base.BaseBusiness;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/25.
 */
@Service
@Transactional
public class DeptBussiness extends BaseBusiness {
    @Resource(name = "DepartmentService")
    DepartmentService deptService;

    public List<DeptBean> findByPid(String pid) {
        Map<Integer, Department> departs = roleEngine.getRoleModel(false).getDepartments();
        List<Department> depts = new ArrayList<>();
        departs.forEach((depId, depart) -> {
            if (pid.equals("未分组")) {

            } else if (pid.equals(depart.getPid())) {
                depts.add(depart);
            }
        });
        List<DeptBean> retList = new ArrayList<>();
        for (Department dept : depts) {
            int depth = getDepartDepth(dept);
            DeptBean db = new DeptBean();
            db.setId(dept.getId());
            db.setText(dept.getName());
            db.setPid(dept.getPid());
            db.setName(dept.getName());
            db.setFunction(dept.getFunction());
            db.setNotes(dept.getNotes());
            db.setIconCls("icon-dept");
            db.setOrder(dept.getOrder());

            List<DeptBean> children = findByPid(db.getId());
            db.setChildren(children);
            if (children.size() == 0) {
                db.setLeaf(true);
                db.setExpanded(false);
            } else {
                db.setLeaf(false);
                //只有根部门才需要展开下一层级
                db.setExpanded(ConfigInfo.ROOT_DEPARTMENT_ID.equals(dept.getId()));
            }
            retList.add(db);
        }
        Collections.sort(retList);
        return retList;
    }

    private int getDepartDepth(Department dept) {
        int depth = 1;
        while (!(dept.getPid().equals("-1"))) {
            depth++;
            dept = findDeptById(dept.getPid());
        }
        if (depth > 4) {
            depth = 4;
        }
        return depth;
    }

    /**
     * 根据chu
     *
     * @param id
     * @return
     */
    private Department findDeptById(String id) {
        Map<Integer, Department> departs = roleEngine.getRoleModel(false).getDepartments();
        Department dept = departs.get(Integer.parseInt(id));
        return dept;
    }

    public List<DeptBean> listByIdFilter(String ids) {
        Map<Integer, Department> departs = roleEngine.getRoleModel(false).getDepartments();
        List<DeptBean> depts = new ArrayList<>();
        departs.forEach((depId, department) -> {
            if (ids.contains(depId.toString())) {
                DeptBean db = new DeptBean();
                try {
                    PropertyUtils.copyProperties(db, department);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                db.setText(db.getName());
                db.setIconCls("icon-dept");
                depts.add(db);
            }
        });
        //增加一个未分组节点
        depts.add(addNoGroupDeptNode());
        Collections.sort(depts);
        return depts;
    }

    /**
     * 增加一个未分组部门节点
     *
     * @return
     */
    private DeptBean addNoGroupDeptNode() {
        DeptBean db = new DeptBean();
        db.setExpanded(true);
        db.setLeaf(false);
        db.setId("未分组");
        db.setText("未分组");
        db.setIconCls("icon-dept");
        return db;
    }

    /**
     * 层级展现部门列表
     */
    public List<DeptBean> treeFindByPid(String pid) {
        List<DeptBean> result = findByPid(pid);
        if (pid.equals("-1")) {
            result.add(addNoGroupDeptNode());
        }
        return result;
    }

    public void updateCache(Department tempDept) {
        Map<Integer, Department> departs = roleEngine.getRoleModel(false).getDepartments();
        departs.put(Integer.valueOf(tempDept.getId()), tempDept);
    }

    public void deleteFromCache(String toDelIds) {
        String[] depIds = toDelIds.split(",");
        Map<Integer, Department> departs = roleEngine.getRoleModel(false).getDepartments();
        for (String depId : depIds) {
            departs.remove(Integer.valueOf(depId));
        }
    }
}
