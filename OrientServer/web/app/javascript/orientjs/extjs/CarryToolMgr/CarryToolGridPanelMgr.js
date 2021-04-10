Ext.define('OrientTdm.CarryToolMgr.CarryToolGridPanelMgr', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientModelGrid',
    alias: 'widget.carryToolGridPanelMgr',
    initComponent: function () {
        var me = this;
        this.callParent(arguments);
    },
    afterInitComponent: function () {
        var me = this;
        var isCabinOrOut = me.isCabinOrOut;
        var modelId = me.modelId;
        //构建完表格后 定制操作
        var addButton = this.dockedItems[0].down('button[text=新增]');
        Ext.Function.interceptAfter(addButton, 'handler', function (button) {
            //新增表单出现后 相关定制
            var customPanel = button.orientBtnInstance.customPanel;
            if (customPanel) {
                //注入额外参数
                var object = new Object();
                object['C_CABIN_INOROUT_' + modelId] = isCabinOrOut;
                customPanel.originalData = Ext.apply(customPanel.originalData || {}, object);
            }
        });
        var toolbar = me.dockedItems[0];
        toolbar.add({
            text: '批量导入',
            handler: me._batchImportCarryTool,
            iconCls: "icon-import",
            scope: me
        }, {
            text: '批量导出',
            handler: me._batchExportCarryTool,
            iconCls: "icon-export",
            scope: me
        });
    },
    _batchImportCarryTool: function () {
        var me = this;
        var win = Ext.create("Ext.Window", {
            title: '批量导入',
            plain: true,
            height: 120,
            width: '70%',
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [
                {
                    xtype: 'form',
                    bodyPadding: 10,
                    layout: 'anchor',
                    defaults: {
                        anchor: '100%',
                        labelAlign: 'left',
                        msgTarget: 'side',
                        labelWidth: 90
                    },
                    items: [{
                        xtype: 'filefield',
                        buttonText: '',
                        fieldLabel: '批量导入(.xls或xlsx)',
                        buttonConfig: {
                            iconCls: 'icon-upload'
                        },
                        listeners: {
                            'change': function (fb, v) {
                                if (v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                    OrientExtUtil.Common.info('提示', '请选择Excel文件！');
                                    return;
                                }
                            }
                        }
                    }]
                }
            ],
            buttons: [
                {
                    text: '导入',
                    handler: function () {
                        var form = win.down("form").getForm();
                        if (form.isValid()) {
                            form.submit({
                                url: serviceName + '/carryToolMgr/importCarryToolData.rdm?isCabinOrOut=' + me.isCabinOrOut,
                                waitMsg: '导入工具...',
                                success: function (form, action) {
                                    OrientExtUtil.Common.tip('成功', action.result.msg);
                                    win.close();
                                    me.fireEvent("refreshGrid");
                                },
                                failure: function (form, action) {
                                    switch (action.failureType) {
                                        case Ext.form.action.Action.CLIENT_INVALID:
                                            OrientExtUtil.Common.err('失败', '文件路径存在错误');
                                            break;
                                        case Ext.form.action.Action.CONNECT_FAILURE:
                                            OrientExtUtil.Common.err('失败', '无法连接服务器');
                                            break;
                                        case Ext.form.action.Action.SERVER_INVALID:
                                            OrientExtUtil.Common.err('失败', action.result.msg);
                                    }
                                }
                            });
                        }
                    }
                }
            ]
        });
        win.show();
    },
    _batchExportCarryTool: function () {
        var me = this;
        var selections = me.getSelectionModel().getSelection();
        var toExportIds = OrientExtUtil.GridHelper.getSelectRecordIds(me);
        var exportAll = false;
        if (selections.length === 0) {
            var isCabinOrOut=me.isCabinOrOut;
            var cabinOrOut;
            if (isCabinOrOut=='cabinIn'){
                cabinOrOut="舱内";
            }else if (isCabinOrOut=='cabinOut') {
                cabinOrOut="舱外";
            }
            Ext.MessageBox.confirm('提示', '是否导出所有'+cabinOrOut+'携带的作业工具信息？', function (btn) {
                if (btn == 'yes') {
                    exportAll = true;
                    window.location.href = serviceName + '/carryToolMgr/exportCarryToolData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds + '&isCabinOrOut=' + me.isCabinOrOut;
                }
            });
        } else {
            window.location.href = serviceName + '/carryToolMgr/exportCarryToolData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds + '&isCabinOrOut=' + me.isCabinOrOut;
        }
    }
});