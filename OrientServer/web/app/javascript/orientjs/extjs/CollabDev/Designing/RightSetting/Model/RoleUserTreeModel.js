Ext.define('OrientTdm.CollabDev.Designing.RightSetting.Model.RoleUserTreeModel', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'roleId',
        'roleName',
        'userName',
        'userId',
        'deptName',
        'isLeaf',
        'children',
        'defaultRole',
        'text'
    ]
});