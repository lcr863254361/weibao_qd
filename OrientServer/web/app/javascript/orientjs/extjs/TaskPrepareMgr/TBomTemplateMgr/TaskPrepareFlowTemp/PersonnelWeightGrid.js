Ext.define('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.PersonnelWeightGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.personnelWeightGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_PERSON_WEIGHT,
        modelName: TDM_SERVER_CONFIG.PERSON_WEIGHT
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var customerFilter = new CustomerFilter('T_HANGDUAN_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.flowId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],
            pageSize: 50,
            queryUrl: serviceName + "/taskPrepareController/queryPersonWeight.rdm?hangduanId=" + me.flowId,
            updateUrl: serviceName + "/taskPrepareController/updatePersonWeightData.rdm",
            afterInitComponent: function () {
                var toolbar = this.dockedItems[0];
                toolbar.add(
                    {
                        iconCls: 'icon-import',
                        text: '批量导入',
                        disabled: false,
                        itemId: 'import',
                        scope: me,
                        handler: me.importPersonWeightWin
                    },{
                        iconCls: 'icon-export',
                        text: '批量导出',
                        disabled: false,
                        itemId: 'export',
                        scope: me,
                        handler: me.exportPersonWeightWin
                    })
            }
        });
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },
    importPersonWeightWin: function () {
        var me = this;
        var win = Ext.create("Ext.Window", {
            title: '批量导入人员体重',
            plain: true,
            height: 110,
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
                        fieldLabel: '导入人员体重(.xls或xlsx)',
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
                                url: serviceName + '/historyTask/importPersonWeight.rdm?hangduanId=' + me.flowId,
                                waitMsg: '导入人员体重...',
                                success: function (form, action) {
                                    OrientExtUtil.Common.tip('成功', action.result.msg);
                                    win.close();
                                    if (action.result.invalidUser!=""){
                                        Ext.Msg.alert("提示", "发现【"+action.result.invalidUser+"】在系统用户中不存在，无法导入！");
                                    }
                                    me.down().fireEvent("refreshGrid");
                                },
                                failure:function(form,action){
                                    OrientExtUtil.Common.info('失败', action.result.msg, function () {
                                        win.close();
                                    });
                                }
                            });
                        }
                    }
                }
            ]
        });
        win.show();
    },
    exportPersonWeightWin:function () {
        var me = this;
        var grid = me.modelGrid;
        var selections = grid.getSelectionModel().getSelection();
        var toExportIds = OrientExtUtil.GridHelper.getSelectRecordIds(grid);
        var exportAll = false;
        if (selections.length === 0) {
            Ext.MessageBox.confirm('提示', '是否导出本航段下所有人员体重信息？', function (btn) {
                if (btn == 'yes') {
                    exportAll = true;
                    window.location.href = serviceName + '/historyTask/exportPersonWeightData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds + '&hangduanId=' + me.flowId;
                }
            });
        } else {
            window.location.href = serviceName + '/historyTask/exportPersonWeightData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds + '&hangduanId=' + me.flowId;
        }
    }
});