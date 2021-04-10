Ext.define('OrientTdm.HistoryTaskMgr.HistoryTaskTree', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.historyTaskTree',
    requires: [
        "OrientTdm.DataMgr.TBom.Model.TBomNodeModel"
    ],
    config: {
        belongFunctionId: '',
        schemaId: ''
    },
    initComponent: function () {
        var me = this;
        var params = {
            belongFunctionId: me.belongFunctionId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + "/modelTree/getSchemaCombobox.rdm", params, false, function (response) {
            var retV = response.decodedData;
            var success = retV.success;
            if (success) {
                var results = retV.results;
                me.schemaId = results instanceof Array ? results[0]["id"] : results["id"];
            }
        });
        Ext.apply(me, {
            hideHeaders: true,
            columns: [{
                xtype: 'treecolumn',
                dataIndex: 'text',
                flex: 1
            }],
            tbar: [{
                xtype: 'trigger',
                triggerCls: 'x-form-clear-trigger',
                onTriggerClick: function () {
                    this.setValue('');
                    me.clearFilter();
                },
                emptyText: '快速搜索(只能搜索已展开节点)',
                width: "78%",
                enableKeyEvents: true,
                listeners: {
                    keyup: function (field, e) {
                        if (Ext.EventObject.ESC == e.getKey()) {
                            field.onTriggerClick();
                        } else {
                            me.filterByText(this.getRawValue(), "text");
                        }
                    }
                }
            }, {
                iconCls: 'icon-refresh',
                text: '<span style="color: black; ">' + '刷新' + '</span>',
                itemId: 'refresh',
                scope: this,
                handler: this.doRefresh
            }]
        });
        me.callParent(arguments);
        me.addEvents("initTboms", "refreshCurrentNode");
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'select', me.selectItem, me);
        me.mon(me, 'initTboms', me.initTboms, me);
        me.mon(me, 'refreshCurrentNode', me.refreshCurrentNode, me);
    },
    // createToolBarItems: function () {
    //     var me = this;
    //     var retVal = [
    //         {
    //             iconCls: 'icon-import',
    //             text: '<span style="color: black; ">' + '导入航次航段' + '</span>',
    //             tooltip: '从Zip或Excel导入',
    //             scope: this,
    //             handler: this.uploadHangciHangduan
    //         },
    //         {
    //             iconCls: 'icon-import',
    //             text: '<span style="color: black; ">' + '导入潜次' + '</span>',
    //             tooltip: '从Zip或Excel导入',
    //             scope: this,
    //             handler: this.uploadDivingTask
    //         },
    //         {
    //             iconCls: 'icon-refresh',
    //             text: '<span style="color: black; ">' + '刷新' + '</span>',
    //             itemId: 'refresh',
    //             scope: this,
    //             handler: this.doRefresh
    //         }
    //     ];
    //     return retVal;
    // },
    createStore: function () {
        var me = this;
        var retVal = Ext.create("Ext.data.TreeStore", {
            model: 'OrientTdm.DataMgr.TBom.Model.TBomNodeModel',
            listeners: {
                beforeLoad: function (store, operation) {
                    var node = operation.node;
                    if (node.isRoot()) {
                        store.getProxy().setExtraParam("schemaId", me.schemaId);
                        store.getProxy().setExtraParam("belongFunctionId", me.belongFunctionId);
                        store.getProxy().setExtraParam("nodeAttr", "");
                    } else {
                        //截断父节点信息 防止嵌套层次太深
                        if (node.raw.parentNode) {
                            node.raw.parentNode = null;
                        }
                        store.getProxy().setExtraParam("schemaId", me.schemaId);
                        store.getProxy().setExtraParam("belongFunctionId", me.belongFunctionId);
                        store.getProxy().setExtraParam("nodeAttr", Ext.encode(node.raw));
                    }
                },
                load: function (store, records) {
                    var nodes = records.childNodes;
                    Ext.each(nodes, function (node) {
                        // 默认选中第一个节点
                        if (records.getDepth() == 0) {
                            me.selectItem(me, node);
                            node.set('expanded', true);
                        }
                    });
                }
            }
        });
        return retVal;
    },
    selectItem: function (tree, node) {
        if (this.ownerCt.centerPanel) {
            this.ownerCt.centerPanel.fireEvent("initModelDataByNode", node);
        }
    },
    doRefresh: function () {
        var selectedNode = this.getSelectionModel().getSelection()[0];
        this.getStore().load({
            node: selectedNode
        });
    },
    initTboms: function (schemaId) {
        var me = this;
        //移除所有节点
        var rootNode = this.getRootNode();
        //me.getStore().getProxy().setExtraParam("nodeAttr", Ext.encode(rootNode.childNodes[0].raw));
        rootNode.removeAll();
        //动态加载新的tbom
        me.schemaId = schemaId;
        me.getStore().getProxy().setExtraParam("belongFunctionId", me.belongFunctionId);
        me.getStore().load();
    },
    refreshCurrentNode: function (keepGridQueryFilter) {
        var me = this;
        var rootNode = this.getRootNode();
        var currentNode = me.getSelectionModel().getSelection()[0];
        var parentNode = currentNode.parentNode;
        this.getStore().load({
            node: parentNode,
            callback: function (nodes) {
                var catched = false;
                Ext.each(nodes, function (node) {
                    if (node.data.text == currentNode.data.text) {
                        catched = true;
                        node.expand();
                        me.getSelectionModel().select(node, false, true);//刷新时不触发选中节点事件
                        me.up().down('dataShowRegion').getActiveTab().down("orientModelGrid").fireEvent("refreshGridByTreeNode", node, keepGridQueryFilter);
                    }
                });
                if (!catched) {
                    parentNode.expand();
                    me.getSelectionModel().select(parentNode, false, true);//刷新时不触发选中节点事件
                    me.up().down("orientModelGrid").fireEvent("refreshGridByTreeNode", parentNode, keepGridQueryFilter);
                }
            }
        });
    },
    afterInitComponent: function () {
        this.viewConfig.listeners.refresh = function () {
            //去除默认的选中事件
        };
    }
});