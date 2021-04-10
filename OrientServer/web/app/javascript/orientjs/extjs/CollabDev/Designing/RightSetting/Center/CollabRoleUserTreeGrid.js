Ext.define('OrientTdm.CollabDev.Designing.RightSetting.Center.CollabRoleUserTreeGrid', {
    alias: 'widget.teamRoleUserTreeGrid',
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    xtype: 'treeGrid',
    requires: [
        'OrientTdm.CollabDev.Designing.RightSetting.Model.RoleUserTreeModel',
        'OrientTdm.CollabDev.Designing.RightSetting.East.RoleFunctionAssignPanel',
        'OrientTdm.CollabDev.Designing.RightSetting.South.CollabRoleForm',
        'OrientTdm.CollabDev.Designing.RightSetting.South.AssignUserPanel'
    ],
    config: {
        nodeId: ''
    },
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
        me.mon(me, 'select', me._itemClickListener, me);

    },
    createToolBarItems: function () {
        var me = this;
        var retVal = [{
            text: '新增角色',
            iconCls: 'icon-create',
            handler: me.onAddRole,
            scope: me,
            width: 80
        }, {
            text: '编辑角色',
            iconCls: 'icon-update',
            handler: me.onEditRole,
            scope: me,
            width: 80,
            disabled: true,
            defaultRoleAble: false
        }, {
            text: '分配用户',
            iconCls: 'icon-assign',
            handler: me.onAssignUser,
            scope: me,
            width: 80
        }, {
            text: '删除角色',
            iconCls: 'icon-delete',
            handler: me.onDeleteRole,
            scope: me,
            width: 80,
            disabled: true,
            defaultRoleAble: false
        }];
        return retVal;
    },
    createStore: function () {
        var me = this;
        return Ext.create('Ext.data.TreeStore', {
            model: 'OrientTdm.CollabDev.Designing.RightSetting.Model.RoleUserTreeModel',
            listeners: {
                beforeLoad: function (store, operation) {
                    var node = operation.node;
                    store.getProxy().setExtraParam('nodeId', me.getNodeId());
                    if (node.isRoot()) {
                        store.getProxy().setExtraParam('parId', '-1');
                    } else {
                        if (node.raw.parentNode) {
                            node.raw.parentNode = null;
                        }
                        store.getProxy().setExtraParam('parId', node.data.roleId);
                    }
                }
            },
            root: {
                text: 'root',
                id: '-1',
                expanded: false
            },
            autoLoad: false,
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/collabRightSetting/roleUsers/tree.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                }
            }
        });
    },
    _itemClickListener: function (tree, record) {
        var me = this;
        var eastPanel = me.ownerCt.eastPanel;
        if (!record.data.leaf) {
            var functionAssignPanel = Ext.create('OrientTdm.CollabDev.Designing.RightSetting.East.RoleFunctionAssignPanel', {
                layout: 'border',
                region: 'center',
                bodyStyle: {
                    background: '#fff'
                },
                roleId: record.data.roleId
            });
            eastPanel.removeAll();
            eastPanel.add(functionAssignPanel);
            me.ownerCt.eastPanel.expand(true);
        } else {
            //eastPanel.removeAll();
        }

        var tb = me.getDockedItems('toolbar[dock="top"]')[0];
        if (Ext.isEmpty(tb)) {
            this.ownerCt.southPanel.removeAll();
            return;
        }

        var btns = tb.query('button');
        Ext.each(btns, function (btn) {
            btn.setDisabled(false);
        });

        if (record.data.defaultRole) {
            var defaultRoleBtns = tb.query('button[defaultRoleAble=false]');
            Ext.each(defaultRoleBtns, function (btn) {
                btn.setDisabled(true);
            });
        } else if (record.data.leaf) {
            Ext.each(btns, function (btn) {
                btn.setDisabled(true);
            });
        }

        this.ownerCt.southPanel.removeAll();
    },
    columns: [
        {xtype: 'treecolumn', text: '角色', dataIndex: 'roleName', flex: 1},
        {text: '用户名', dataIndex: 'userName'},
        {text: '部门', dataIndex: 'deptName'}
    ],
    onAddRole: function () {
        var me = this;
        var createWin = Ext.create('Ext.Window', {
            title: '新增角色',
            plain: true,
            height: 0.2 * globalHeight,
            width: 0.4 * globalWidth,
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [Ext.create('OrientTdm.CollabDev.Designing.RightSetting.South.CollabRoleForm', {
                url: serviceName + '/collabRightSetting/addRole.rdm',
                baseParams: {
                    nodeId: me.nodeId
                },
                successCallback: function (resp, callBackArguments) {
                    if (callBackArguments) {
                        createWin.close();
                    }
                    var rootNode = me.getRootNode();
                    me.refreshNode(rootNode.data.id, false);
                }
            })]
        });
        createWin.show();
    },
    onEditRole: function () {
        var me = this;

        var selection = me.getSelectionModel().getSelection();
        var curNodeData = selection[0].data;

        if (curNodeData.leaf) {
            OrientExtUtil.Common.tip('提示', '请选择角色节点');
            return;
        }

        var win = Ext.create('Ext.Window', {
            title: '编辑角色',
            plain: true,
            height: 0.2 * globalHeight,
            width: 0.4 * globalWidth,
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [Ext.create('OrientTdm.CollabDev.Designing.RightSetting.South.CollabRoleForm', {
                initRoleName: curNodeData.roleName,
                url: serviceName + '/collabRightSetting/modifyRole.rdm',
                baseParams: {
                    roleId: curNodeData.roleId,
                    oldRoleName: curNodeData.roleName
                },
                successCallback: function () {
                    win.close();
                    var rootNode = me.getRootNode();
                    me.refreshNode(rootNode.data.id, false);
                }
            })]
        });
        win.show();
    },
    onDeleteRole: function () {
        var me = this;
        var selection = me.getSelectionModel().getSelection();
        var curNodeData = selection[0].data;
        if (curNodeData.leaf) {
            OrientExtUtil.Common.tip('提示', '请选择角色节点');
            return;
        }

        Ext.Msg.confirm('提示', '是否删除?',
            function (btn, text) {
                if (btn == 'yes') {
                    var params = {
                        roleId: curNodeData.roleId
                    };
                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabRightSetting/deleteRole.rdm', params, false, function (response) {
                        var retV = response.decodedData;
                        var success = retV.success;
                        if (success) {
                            OrientExtUtil.Common.tip('提示', '删除成功');
                            var rootNode = me.getRootNode();
                            me.refreshNode(rootNode.data.id, false);
                        }
                    });

                }
            }
        );
    },
    onAssignUser: function () {
        var me = this;
        var selection = this.getSelectionModel().getSelection();
        var curNodeData = selection[0].data;

        if (curNodeData.leaf) {
            OrientExtUtil.Common.tip('提示', '请选择角色节点');
            return;
        }
        var assignUserPanel = Ext.create('OrientTdm.CollabDev.Designing.RightSetting.South.AssignUserPanel', {
            region: 'center',
            roleId: curNodeData.roleId,
            assignCallback: function (selectedIds, direction) {
                me.refreshNode(curNodeData.id, false);
            }
        });
        this.ownerCt.southPanel.removeAll();
        this.ownerCt.southPanel.add(assignUserPanel);
        this.ownerCt.southPanel.expand(true);
    },
    refreshNode: function (nodeId, refreshParent) {
        var rootNode = this.getRootNode();
        var currentNode;
        if (nodeId === '-1') {
            currentNode = rootNode;
        } else {
            currentNode = rootNode.findChild('id', nodeId);
        }
        var toRefreshNode = currentNode;
        if (refreshParent) {
            toRefreshNode = currentNode.parentNode;
        }
        this.store.load(
            {
                node: toRefreshNode,
                callback: function () {
                    toRefreshNode.expand();
                }
            }
        );
    }
});
