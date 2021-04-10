Ext.define('OrientTdm.DepthDesityMgr.DepthDesityGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientModelGrid',
    alias: 'widget.depthDesityGrid',
    initComponent: function () {
        var me = this;
        this.callParent(arguments);
    },
    afterInitComponent: function () {
        var me = this;
        var toolbar = me.dockedItems[0];

        toolbar.add({
            text: '批量导入',
            handler: me._batchImportDepthDesity,
            iconCls: "icon-import",
            scope: me
        },{
            text: '批量导出',
            handler: me._batchExportDepthDesity,
            iconCls: "icon-export",
            scope: me
        });
    },
    _batchImportDepthDesity: function () {
        var me = this;
        var win = Ext.create("Ext.Window", {
            title: '批量导入深度密度',
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
                        fieldLabel: '批量导入深度密度(.xls或xlsx)',
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
                                url: serviceName + '/depthDesity/importDepthDesityData.rdm?depthDesityTypeId=' + me.depthDesityTypeId,
                                waitMsg: '导入深度密度...',
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
    _batchExportDepthDesity:function () {
        var me = this;
        var seaAreaName=me.seaAreaName;
        var selections = me.getSelectionModel().getSelection();
        var toExportIds = OrientExtUtil.GridHelper.getSelectRecordIds(me);
        var exportAll = false;
        if (selections.length === 0) {
            Ext.MessageBox.confirm('提示', '是否导出所有'+seaAreaName+'的深度密度信息？', function (btn) {
                if (btn == 'yes') {
                    exportAll = true;
                    window.location.href = serviceName + '/depthDesity/exportDepthDesityData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds + '&depthDesityTypeId=' + me.depthDesityTypeId+'&seaAreaName='+me.seaAreaName;
                }
            });
        } else {
            window.location.href = serviceName + '/depthDesity/exportDepthDesityData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds + '&depthDesityTypeId=' + me.depthDesityTypeId+'&seaAreaName='+me.seaAreaName;
        }
    }
});