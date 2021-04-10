/**
 * Created by User on 2021/1/25.
 */
Ext.define('OrientTdm.ProductStructMgr.ProductStructTree', {
    alias: 'widget.tbomTree',
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
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
            // bbar: [{
            //     xtype: 'trigger',
            //     triggerCls: 'x-form-clear-trigger',
            //     onTriggerClick: function () {
            //         this.setValue('');
            //         me.clearFilter();
            //     },
            //     emptyText: '快速搜索(只能搜索已展开节点)',
            //     width: "95%",
            //     enableKeyEvents: true,
            //     listeners: {
            //         keyup: function (field, e) {
            //             if (Ext.EventObject.ESC == e.getKey()) {
            //                 field.onTriggerClick();
            //             } else {
            //                 me.filterByText(this.getRawValue(), "text");
            //             }
            //         }
            //     }
            // }]
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
    //         Ext.create("OrientTdm.Common.Extend.Form.Field.OrientComboBox", {
    //             remoteUrl: serviceName + '/modelTree/getSchemaCombobox.rdm?belongFunctionId=' + me.belongFunctionId,
    //             displayField: 'name',
    //             hideLabel: true,
    //             initFirstRecord: true,
    //             width: 180,
    //             style: {
    //                 margin: '0 0 0 0'
    //             },
    //             listeners: {
    //                 select: function (combobox, record) {
    //                     var schemaId = record instanceof Array ? record[0].get("id") : record.get("id");
    //                     //为避免数据重复，不触发事件
    //                     if (me.schemaId != schemaId) {
    //                         me.fireEvent("initTboms", schemaId);
    //                     }
    //                 }
    //             }
    //         }),
    //         {xtype: 'tbfill'},
    //         {
    //             iconCls: 'icon-refresh',
    //             text: '刷新',
    //             itemId: 'refresh',
    //             scope: this,
    //             handler: this.doRefresh
    //         }
    //     ];
    //     return retVal;
    // },
    createToolBarItems: function () {
        var me = this;
        var retVal = [
            {
                xtype: 'trigger',
                triggerCls: 'x-form-clear-trigger',
                onTriggerClick: function () {
                    this.setValue('');
                    me.clearFilter();
                },
                emptyText: '快速搜索(只能搜索已展开节点)',
                width: me.isHideOperationButton==true?"80%":"50%",
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
            },
            {
                iconCls: 'icon-import',
                text: '<span style="color: black; ">' + '导入' + '</span>',
                tooltip: '从Excel导入',
                scope: this,
                handler: this.importProductStructAction,
                hidden:me.isHideOperationButton
            },
            {
                iconCls: 'icon-export',
                text: '<span style="color: black; ">' + '导出' + '</span>',
                tooltip: '导出产品结构树',
                scope: this,
                handler: this.exportProductStructAction,
                hidden:me.isHideOperationButton
            },
            {
                iconCls: 'icon-refresh',
                text: '<span style="color: black; ">' + '刷新' + '</span>',
                itemId: 'refresh',
                scope: this,
                handler: this.doRefresh
            }
        ];
        return retVal;
    },
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
    },
    exportProductStructAction: function () {
        var exportAll = false;
        Ext.MessageBox.confirm('提示', '是否导出所有产品结构树？', function (btn) {
            if (btn == 'yes') {
                exportAll = true;
                window.location.href = serviceName + '/ProductStructrue/exportSyProductStructTree.rdm?exportAll=' + exportAll;
            }
        });
    },
    importProductStructAction: function () {
        var me = this;
        var win = Ext.create('Ext.Window', {
            title: '导入产品结构树',
            plain: true,
            height: 110,
            width: '40%',
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [{
                xtype: 'form',
                bodyPadding: 10,
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    labelAlign: 'left',
                    msgTarget: 'side',
                    labelWidth: 100
                },
                items: [{
                    xtype: 'filefield',
                    buttonText: '',
                    fieldLabel: '导入产品结构树(.xlsx或.xls)',
                    buttonConfig: {
                        iconCls: 'icon-upload'
                    },
                    listeners: {
                        'change': function (fb, v) {
                            if (v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                OrientExtUtil.Common.info('提示', '请选择Excel文件!');
                                return;
                            }
                        }
                    }
                }]
            }],
            buttons: [{
                text: '导入',
                handler: function () {
                    var form = win.down("form").getForm();
                    if (form.isValid()) {
                        form.submit({
                            //导入航次航段
                            url: serviceName + '/ProductStructrue/importSyProductStructTree.rdm',
                            waitMsg: '导入产品结构树...',
                            success: function (form, action) {
                                OrientExtUtil.Common.tip('成功', action.result.msg);
                                win.close();
                                // me.westPanel.doRefresh(me.westPanel.getRootNode().childNodes[0]);
                                // me.westPanel.doRefresh();
                                // me.westPanel.expandAll();
                                //默认刷新展开第一个节点
                                me.getStore().load({
                                    node: me.getRootNode().childNodes[0],
                                    callback: function (records) {
                                        Ext.each(records, function (record) {
                                            if (record.get('expanded') == true) {
                                                record.set('expanded', false);
                                                record.expand();
                                            }
                                        });
                                    }
                                });
                            },
                            failure: function (form, action) {
                                OrientExtUtil.Common.info('失败', action.result.msg, function () {
                                    win.close();
                                });
                            }
                        });
                    }
                }
            }]
        });
        win.show();
    }
});