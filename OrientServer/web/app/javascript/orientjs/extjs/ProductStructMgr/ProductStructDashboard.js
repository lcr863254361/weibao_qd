Ext.define('OrientTdm.ProductStructMgr.ProductStructDashboard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.productStructDashboard',
    requires: [
        "OrientTdm.DataMgr.TBom.TBomTree",
        // "OrientTdm.DataMgr.Center.DataShowRegion"
    ],
    id:'productStructDashboard',
    initComponent: function () {
        var me = this;
        var functionId = me.itemId;
        if (functionId) {
            //截取ID
            functionId = functionId.substr(functionId.indexOf("-") + 1, functionId.length);
        }
        //创建中间面板
        var centerPanel = Ext.create("OrientTdm.ProductStructMgr.StructDataShowRegion", {
            region: 'center',
            padding: '0 0 0 5'
        });
        //Tbom
        var tbomPanel = Ext.create("OrientTdm.ProductStructMgr.ProductStructTree", {
            width: 360,
            minWidth: 360,
            maxWidth: 400,
            split:true,
            belongFunctionId: functionId,
            region: 'west'
        });
        // tbomPanel.expandAll();
        // var westPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
        //     title: '导入导出操作',
        //     region: 'west',
        //     animCollapse: true,
        //     width: 135,
        //     minWidth: 135,
        //     maxWidth: 135,
        //     split: true,
        //     collapsible: true,
        //     layout: {
        //         type: 'fit',
        //         animate: true
        //     },
        //     lbar: this.createToolbar()
        // });
        // Ext.apply(me, {
        //     layout: 'border',
        //     items: [centerPanel, westPanel, tbomPanel],
        //     westPanel: tbomPanel,
        //     centerPanel: centerPanel
        // });
        Ext.apply(me, {
            layout: 'border',
            items: [centerPanel,tbomPanel],
            westPanel: tbomPanel,
            centerPanel: centerPanel
        });
        me.callParent(arguments);
    },
    createToolbar: function () {
        this.createActions();
        this.items = [this.importProductStructAction,this.exportProductStructAction];
        return this.items;
    },
    /**
     * Create actions to share between toolbar and menu
     * @private
     */
    createActions: function () {
        this.importProductStructAction = Ext.create('Ext.Action', {
            iconCls: 'icon-import',
            text: '<span style="color: black; ">' + '导入产品结构树' + '</span>',
            tooltip: '从Excel导入',
            scope: this,
            handler: this.importProductStructAction
        });
        this.exportProductStructAction = Ext.create('Ext.Action', {
            scope: this,
            handler: this.exportProductStructAction,
            text: '<span style="color: black; ">' +'导出产品结构树' + '</span>',
            iconCls: 'icon-export'
        });
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
                                me.westPanel.getStore().load({
                                    node: me.westPanel.getRootNode().childNodes[0],
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