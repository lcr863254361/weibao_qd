Ext.define('OrientTdm.CollabDev.Processing.TreeGrid.AllNodeBaseInfoTreeGrid', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTreeGrid',
    alias: 'widget.allNodeBaseInfoTreeGrid',
    requires: [
        'OrientTdm.CollabDev.Processing.TreeGrid.BaseInfoModel'
    ],
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            viewConfig: {
                listeners: {
                    itemcontextmenu: function (view, record, node, index, e) {
                        e.stopEvent();
                        var menu = me.createMenu(record);
                        menu.showAt(e.getXY());
                        return false;
                    }
                }
            }
        });
        me.callParent(arguments);
    },
    createToolBarItems: function () {
        var _this = this;
        var retVal = [];
        retVal.push({
            iconCls: 'icon-refresh',
            tooltip: '刷新',
            handler: function () {
                _this.refreshTree(false);
            }
        });
        return retVal;
    },
    createStore: function () {
        return Ext.create('Ext.data.TreeStore', {
            model: 'OrientTdm.CollabDev.Processing.TreeGrid.BaseInfoModel',
            proxy: {
                type: 'ajax',
                url: serviceName + '/nodeBaseInfo.rdm',
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                }
            },
            root: {
                text: 'root',
                id: '-1',
                expanded: true
            },
            listeners: {
                beforeLoad: function (store, operation) {
                    var node = operation.node;
                    if (node.isRoot()) {
                        store.getProxy().setExtraParam('node', '-1');
                        store.getProxy().setExtraParam('type', "root");
                    } else {
                        //截断父节点信息 防止嵌套层次太深
                        if (node.raw.parentNode) {
                            node.raw.parentNode = null;
                        }
                        store.getProxy().setExtraParam('node', node.raw.id);
                        store.getProxy().setExtraParam('type', node.raw.type);
                    }
                }
            }
        })
    },
    createColumns: function () {
        return [
            {xtype: 'treecolumn', text: '项目名称', flex: 1, dataIndex: 'projectName'},
            {text: '节点名称', flex: 1, dataIndex: 'name'},
            {text: '节点类型', flex: 1, dataIndex: 'type'},
            {text: '节点版本', flex: 1, dataIndex: 'version'},
            {text: '运行状态', flex: 1, dataIndex: 'status'},
            {text: '技术状态', flex: 1, dataIndex: 'techStatus'}
        ]
    }
});