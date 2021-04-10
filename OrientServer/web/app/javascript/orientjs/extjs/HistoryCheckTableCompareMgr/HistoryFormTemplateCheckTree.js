/**
 * Created by User on 2020/12/14.
 */
Ext.define('OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateCheckTree', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.historyFormTemplateCheckTree',
    rootVisible: false,
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
    },

    createStore: function () {
        var me = this;

        var retVal;
        retVal = Ext.create('Ext.data.TreeStore', {
            autoSync: true,//'true'表示每当对一条Record记录完成修改后, 都将对Store与Proxy进行同步. 默认为'false'.
            proxy: {
                type: 'ajax',
                url: serviceName + '/formTemplate/getCheckTempTreeNodes.rdm?checkTypeId=' + me.checkTypeId,
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
                qtip: 'root',
                id: '-1',
                iconCls: 'icon-function',
                icon: 'app/images/function/数据建模.png',
                type: 'root',
                expanded: true
            },
            listeners: {
                beforeLoad: function (store, operation) {
                    var node = operation.node;
                    if (node.isRoot) {
                        store.getProxy().setExtraParam('id', '-1');
                        store.getProxy().setExtraParam('type', 'root');
                    } else {
                        if (node.raw.parentNode) {
                            node.raw.parentNode = null;
                        }
                        store.getProxy().setExtraParam('id', node.raw.dataId);
                        store.getProxy().setExtraParam('type', node.raw.type);
                    }
                }
            },
            sorters: [{
                sorterFn: function (node1, node2) {
                    if (node2.raw.position > node1.raw.position) {
                        return -1;
                    } else if (node2.raw.position < node1.raw.position) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }]
        });
        return retVal;
    },
    itemClickListener: function (tree, record, item) {
        var me = this;
        if (this.ownerCt.centerPanel) {
            switch (record.raw.type) {
                case 'root':
                    me.initRightPanel(record);
                    break;
                default :
                    break;
            }
        }
    },
    initRightPanel: function (record) {
        var me = this;
        var checkTempId = record.raw['dataId'];
        var checkTempName=record.raw['text'];
        if (!Ext.isEmpty(checkTempId)) {
            var formTemplateCenterPanel = me.up('historyFormTemplateMgrPanel').centerPanel;
            formTemplateCenterPanel.items.each(function (item, index) {
                formTemplateCenterPanel.remove(item);
            });
            var checkItemPanel = Ext.create('OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid', {
                region: 'center',
                padding: '0 0 0 5',
                isInst: false,
                checkTempId:checkTempId,
                checkTempName:checkTempName
            });

            formTemplateCenterPanel.add({
                title: '检查项管理',
                // iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [checkItemPanel]
            });

            formTemplateCenterPanel.setActiveTab(0);
            // this.ownerCt.centerPanel.items.items[0].items.items[0].getCheckListDetailPanel(checkTempId);
        }
    }
});