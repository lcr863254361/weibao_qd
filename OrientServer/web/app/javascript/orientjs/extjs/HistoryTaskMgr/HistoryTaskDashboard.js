Ext.define('OrientTdm.HistoryTaskMgr.HistoryTaskDashboard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.historyTaskDashboard',
    requires: [
        "OrientTdm.HistoryTaskMgr.HistoryTaskTree",
        "OrientTdm.HistoryTaskMgr.Center.TaskMgrDataShowRegion"
    ],
    initComponent: function () {
        var me = this;
        var functionId = me.itemId;
        if (functionId) {
            //截取ID
            functionId = functionId.substr(functionId.indexOf("-") + 1, functionId.length);
        }
        // //创建中间面板
        // var centerPanel = Ext.create("OrientTdm.HistoryTaskMgr.Center.TaskMgrDataShowRegion", {
        //     region: 'center',
        //     padding: '0 0 0 5'
        // });
        // //Tbom
        // var tbomPanel = Ext.create("OrientTdm.HistoryTaskMgr.HistoryTaskTree", {
        //     width: 280,
        //     minWidth: 280,
        //     maxWidth: 300,
        //     belongFunctionId: functionId,
        //     region: 'west'
        // });
        // tbomPanel.expandAll();

        //创建中间面板
        var centerPanel = Ext.create("OrientTdm.DataMgr.Center.DataShowRegion", {
            region: 'center',
            padding: '0 0 0 5'
        });
        //Tbom
        var tbomPanel = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TBomTemplateTree", {
            width: 240,
            minWidth: 240,
            maxWidth: 300,
            split:true,
            border: false,
            belongFunctionId: functionId,
            region: 'west',
            isHistoryTaskSearch:true
        });
        tbomPanel.expandAll();
        var westPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
            title: '导入导出操作',
            region: 'west',
            animCollapse: true,
            width: 120,
            minWidth: 120,
            maxWidth: 120,
            split: true,
            collapsible: true,
            layout: {
                type: 'fit',
                animate: true
            },
            lbar: this.createToolbar()
        });
        Ext.apply(me, {
            layout: 'border',
            items: [centerPanel, westPanel, tbomPanel],
            westPanel: tbomPanel,
            centerPanel: centerPanel
        });
        me.callParent(arguments);
    },
    createToolbar: function () {
        this.createActions();
        this.items = [this.importHangciHangduanAction,this.importDivingTaskAction,this.addAction];
        return this.items;
    },
    /**
     * Create actions to share between toolbar and menu
     * @private
     */
    createActions: function () {
        this.importHangciHangduanAction = Ext.create('Ext.Action', {
            iconCls: 'icon-import',
            text: '<span style="color: black; ">' + '导入航次航段' + '</span>',
            tooltip: '从Zip或Excel导入',
            scope: this,
            handler: this.uploadHangciHangduan
        });
        this.importDivingTaskAction = Ext.create('Ext.Action', {
            iconCls: 'icon-import',
            text: '<span style="color: black; ">' + '导入潜次数据' + '</span>',
            tooltip: '从Zip或Excel导入',
            scope: this,
            handler: this.uploadDivingTask
        });
        this.addAction = Ext.create('Ext.Action', {
            scope: this,
            handler: this.exportHangciHangduan,
            text: '<span style="color: black; ">' +'导出航次航段' + '</span>',
            iconCls: 'icon-export'
        });

        // this.removeAction = Ext.create('Ext.Action', {
        //     itemId: 'remove',
        //     scope: this,
        //     handler: this.exportDivingTask,
        //     text: '<span style="color: black; ">' +'导出所有潜次' + '</span>',
        //     iconCls: 'icon-export'
        // });
    },

    exportHangciHangduan: function () {
        var exportAll = false;
        Ext.MessageBox.confirm('提示', '是否导出所有航次航段信息？', function (btn) {
            if (btn == 'yes') {
                exportAll = true;
                window.location.href = serviceName + '/historyTask/exportHangciHangduanData.rdm?exportAll=' + exportAll;
            }
        });
    },

    exportDivingTask: function () {

    },

    uploadHangciHangduan: function () {
        var me = this;
        var win = Ext.create('Ext.Window', {
            title: '导入航次航段',
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
                    fieldLabel: '导入航次航段(.zip或.xlsx或.xls)',
                    buttonConfig: {
                        iconCls: 'icon-upload'
                    },
                    listeners: {
                        'change': function (fb, v) {
                            if (v.substr(v.length - 3) != "zip"&&v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                OrientExtUtil.Common.info('提示', '请选择Zip压缩文件或Excel文件!');
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
                            url: serviceName + '/historyTask/importHangciHangduan.rdm',
                            waitMsg: '导入航次航段...',
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
    },
    uploadDivingTask: function () {
        var me = this;
        var win = Ext.create('Ext.Window', {
            title: '导入潜次',
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
                    fieldLabel: '导入潜次(.zip或.xlsx或.xls)',
                    buttonConfig: {
                        iconCls: 'icon-upload'
                    },
                    listeners: {
                        'change': function (fb, v) {
                            if (v.substr(v.length - 3) != "zip"&&v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                OrientExtUtil.Common.info('提示', '请选择zip压缩文件或Excel文件!');
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
                            //导入潜次
                            url: serviceName + '/historyTask/importDivingTaskData.rdm',
                            waitMsg: '导入潜次...',
                            success: function (form, action) {
                                OrientExtUtil.Common.tip('成功', action.result.msg);
                                win.close();
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