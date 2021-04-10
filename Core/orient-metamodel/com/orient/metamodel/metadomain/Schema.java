package com.orient.metamodel.metadomain;

import com.orient.metamodel.operationinterface.IRestriction;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.metamodel.operationinterface.IView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Schema entity
 */
public class Schema extends AbstractSchema implements ISchema {

    public Schema() {
    }

    public Schema(String name, String version) {
        super(name, version);
    }

    /**
     * minimal constructor.
     *
     * @param name             --名称
     * @param version          --版本号
     * @param modifiedTime     --修改时间
     * @param isLock           --是否锁定
     * @param username         --当前操作数据库的用户名
     * @param ip               --IP
     * @param LockModifiedTime --上锁解锁修改时间
     */
    public Schema(String name, String version, Date modifiedTime, Integer isLock, String username, String ip, Date LockModifiedTime) {
        super(name, version, modifiedTime, isLock, username, ip, LockModifiedTime);
    }

    /**
     * full constructor.
     *
     * @param name
     * @param version
     * @param description
     * @param modifiedTime
     * @param isLock
     * @param username
     * @param ip
     * @param LockModifiedTime
     * @param cwmTableses      --schema所有的table
     * @param cwmRestrictions  --schema所有的约束
     * @param cwmViewses       --schema所有的视图
     * @throws
     */
    public Schema(String name, String version, String description, Date modifiedTime, Integer isLock, String username, String ip, Date LockModifiedTime, Set cwmTableses, Set cwmRestrictions, Set cwmViewses) {
        super(name, version, description, modifiedTime, isLock, username, ip, LockModifiedTime, cwmTableses, cwmRestrictions, cwmViewses);
    }

    /**
     * 获取schema的唯一标识
     *
     * @return
     */
    @Override
    public String getIdentity() {
        if (super.getIdentity().isEmpty()) {
            super.setIdentity("schema=" + super.getName());
        }
        return super.getIdentity();
    }

    /**
     * 接口实现 ISchema 的特殊函数
     */
    @Override
    public List<ITable> getAllTables() {
        List<ITable> retList = new ArrayList<>();
        Set<Table> tableSet = this.getTables();
        for (Table table : tableSet) {
            retList.addAll(table.getAllTables());
        }
        return retList;
    }

    @Override
    public List<IView> getAllViews() {
        List<IView> retList = new ArrayList<>();
        Set<View> tableSet = this.getViews();
        for (IView view : tableSet) {
            retList.add(view);
        }
        return retList;
    }

    @Override
    public ITable getTableById(String Id) {
        List<ITable> retList = getAllTables();
        for (ITable table : retList) {
            if (table.getId().equalsIgnoreCase(Id)) {
                return table;
            }
        }
        return null;
    }

    @Override
    public ITable getTableByName(String name) {
        List<ITable> retList = getAllTables();
        for (ITable table : retList) {
            if (table.getName().equalsIgnoreCase(name)) {
                return table;
            }
        }
        return null;
    }

    @Override
    public ITable getTableByDisplayName(String displayName) {
        List<ITable> retList = getAllTables();
        for (ITable table : retList) {
            if (table.getDisplayName().equalsIgnoreCase(displayName)) {
                return table;
            }
        }
        return null;
    }

    @Override
    public IView getViewById(String Id) {
        Set<View> views = this.getViews();
        for (View view : views) {
            if (view.getId().equalsIgnoreCase(Id)) {
                return view;
            }
        }
        return null;
    }

    @Override
    public IView getViewByName(String name) {
        Set<View> views = this.getViews();
        for (View view : views) {
            if (view.getName().equalsIgnoreCase(name)) {
                return view;
            }
        }
        return null;
    }

    @Override
    public IRestriction getRestrictionByName(String name) {
        Set<Restriction> restrictions = this.getRestrictions();
        for (Restriction res : restrictions) {
            if (res.getIsValid().equals(Restriction.ISVALID_VALID)) {
                if (res.getName().equalsIgnoreCase(name)) {
                    return res;
                }
            }
        }
        return null;
    }

    @Override
    public IRestriction getRestrictionById(String Id) {
        Set<Restriction> restrictions = this.getRestrictions();
        for (Restriction res : restrictions) {
            if (res.getIsValid().equals(Restriction.ISVALID_VALID)) {
                if (res.getId().equalsIgnoreCase(Id)) {
                    return res;
                }
            }
        }
        return null;
    }

}
