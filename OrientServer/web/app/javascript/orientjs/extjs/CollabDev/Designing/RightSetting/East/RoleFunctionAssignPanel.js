Ext.define('OrientTdm.CollabDev.Designing.RightSetting.East.RoleFunctionAssignPanel', {
    alias: 'widget.roleFunctionAssignPanel',
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    requires: [
        'OrientTdm.CollabDev.Designing.RightSetting.Model.RoleUserTreeModel'
    ],
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
    },
    config: {
        roleId: null
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'checkchange', me.checkChangeListener, me);
    },
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.TreeStore', {
            model: 'OrientTdm.CollabDev.Designing.RightSetting.Model.RoleUserTreeModel',
            listeners: {
                beforeLoad: function (store) {
                    store.getProxy().setExtraParam('roleId', me.roleId);
                }
            },
            root: {
                text: 'root',
                id: '-1',
                expanded: true
            },
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/collabRightSetting/roleFunctionTreeNodes.rdm'
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
        return retVal;
    },
    createToolBarItems: function () {
        var me = this;
        if (me.localMode) {
            return [];
        } else {
            var retVal = [{
                text: '保存',
                iconCls: 'icon-saveSingle',
                xtype: 'button',
                width: 80,
                handler: Ext.bind(me.onSaveFunctionAssign, me)
            }];

            return retVal;
        }
    },
    checkChangeListener: function (node, checked) {
        var me = this;
        node.expand();
        node.eachChild(function (child) {
            child.set('checked', checked);
            me.fireEvent('checkchange', child, checked);
        });
        me.flowToParent(node, checked);
    },
    flowToParent: function (node, checked) {
        var me = this;
        //更新父节点的选中状态
        var parentNode = node.parentNode;
        if (parentNode) {
            if (checked == true) {
                parentNode.set('checked', checked);
                me.flowToParent(parentNode, checked);
            } else if (checked == false) {
                //判断是否所有子节点都未选中
                var allSonUnchecked = true;
                parentNode.eachChild(function (child) {
                    if (child.get('checked') == true) {
                        allSonUnchecked = false;
                    }
                });
                if (allSonUnchecked == true) {
                    parentNode.set('checked', checked);
                    me.flowToParent(parentNode, checked);
                }
            }

        }
    },
    onSaveFunctionAssign: function () {
        var me = this;
        var nodes = me.getChecked();

        var functionIds = '';
        for (var i = 0; i < nodes.length; i++) {
            functionIds += nodes[i].data.id + ',';
        }
        if (functionIds.length > 0) {
            functionIds = functionIds.substr(0, functionIds.length - 1);
        }
        var params = {
            roleId: me.roleId,
            functionIds: functionIds
        };

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabRightSetting/saveAssignFunctions.rdm', params, false, function (response) {
            var retV = response.decodedData;
            var success = retV.success;
            if (success) {
                OrientExtUtil.Common.tip('提示', '修改成功');
            } else {
                me.getStore().reload();
            }
        });
    }
});