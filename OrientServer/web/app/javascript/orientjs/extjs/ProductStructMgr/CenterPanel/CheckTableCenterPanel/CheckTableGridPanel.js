/**
 * Created by User on 2020/11/27.
 */
Ext.define('OrientTdm.ProductStructMgr.CenterPanel.CheckTableCenterPanel.CheckTableGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.checkTableGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CYCLE_DEVICE_REF_CHECK_TEMP_INST,
        modelName: TDM_SERVER_CONFIG.CHECK_TEMP_INST
    },

    requires: ["OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil"],

    initComponent: function () {
        var me = this;
        var deviceCycleId = me.deviceCycleId;
        var structSystemId = me.structSystemId;
        var deviceId = me.deviceId;
        var deviceName = me.deviceName;
        var bindNode = me.bindNode;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var customerFilter = new CustomerFilter('T_CYCLE_DEVICE_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.deviceCycleId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],
            // listeners: {
            //     afterrender: function (grid) {
            //         var columns = grid.columns;
            //         Ext.each(columns, function (column) {
            //             if (column.text === '状态') {
            //                 column.hide();
            //             }
            //         })
            //     }
            // }
        });
        var toolbar = [{
            xtype: 'button',
            iconCls: 'icon-create',
            text: '添加',
            handler: function () {
                //弹出新增面板窗口
                var createWin = Ext.create('Ext.Window', {
                    title: '绑定检查表模板',
                    plain: true,
                    height: 0.5 * globalHeight,
                    width: 0.5 * globalWidth,
                    layout: 'fit',
                    maximizable: true,
                    modal: true,
                    items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.AddPanel.FormTempletCheckGrid', {
                        deviceCycleId: me.deviceCycleId,
                        checkTableModelGrid: modelGrid,
                        successCallback: function (resp, callBackArguments) {
                            me.fireEvent("refreshGrid");
                            if (callBackArguments) {
                                createWin.close();
                            }
                        }
                    })]
                });
                createWin.show();
            }
        }, {
            xtype: 'button',
            iconCls: 'icon-delete',
            text: '删除',
            scope: me,
            handler: function () {
                if (!OrientExtUtil.GridHelper.hasSelected(modelGrid)) {
                    return;
                }
                var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                var ids = [];
                Ext.each(selectRecords, function (s) {
                    ids.push(s.data.id);
                });
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/deleteCheckTable.rdm', {
                    id: ids.toString()
                }, false, function (resp) {
                    var ret = Ext.decode(resp.responseText);
                    if (ret.success) {
                        modelGrid.fireEvent('refreshGrid');
                        OrientExtUtil.Common.tip('成功', "删除成功！");
                    }
                })
            }
        },
        //     {
        //     xtype: 'button',
        //     iconCls: 'icon-back',
        //     text: '返回上一页',
        //     handler: function () {
        //         var centerPanel = Ext.getCmp('productStructDashboard').centerPanel;
        //         //移除所有面板
        //         centerPanel.items.each(function (item, index) {
        //             if (0 != index) {
        //                 centerPanel.remove(item);
        //             }
        //         });
        //         var filterColumnName = "T_CYCLE_DEVICE_" + TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID + "_ID";//主表表名ID
        //         var customerFilter = new CustomerFilter(filterColumnName, CustomerFilter.prototype.SqlOperation.Equal, "", deviceCycleId);//过滤条件
        //         var gridPanel = Ext.create('OrientTdm.ProductStructMgr.CenterPanel.DeviceCycleCheckGridPanel', {
        //             title: '【' + deviceName + '】周期检查',
        //             iconCls: "icon-data-node",
        //             structSystemId: structSystemId,
        //             customerFilter: [customerFilter],
        //             bindNode: bindNode,
        //             deviceId: deviceId,
        //             deviceName: deviceName
        //         });
        //         centerPanel.add(gridPanel);
        //         centerPanel.setActiveTab(1);
        //     },
        //     scope: me
        // }
        ];

        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0]);
        modelGrid.getDockedItems()[0].insert(0, toolbar);
        // modelGrid.getDockedItems('toolbar[dock="top"]')[0].add(toolbar);
        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0]);
        //隐藏产品配置的按钮
        // modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].hide();
        //增加检查表实例查看按钮
        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            columns.push(
                {
                    xtype: 'actioncolumn',
                    text: '查看',
                    align: 'center',
                    width: 0.05*globalWidth,
                    items: [{
                        iconCls: 'icon-detail',
                        tooltip: '表格实例查看',
                        handler: function (grid, rowIndex) {
                            var data = grid.getStore().getAt(rowIndex);
                            var instanceId = data.raw.ID;
                            Ext.create('Ext.Window', {
                                    plain: true,
                                    title: '预览',
                                    height: 0.98*globalHeight,
                                    width: 0.95*globalWidth,
                                    layout: 'fit',
                                    maximizable: true,
                                    modal: true,
                                    autoScroll: true,
                                    items: [
                                        Ext.create('OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel', {
                                            instanceId: instanceId
                                        })
                                    ]
                                }
                            ).show();
                        }
                    }]
                })
            // columns[1].hide();
        }
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(), columns);
        // modelGrid.columns[1].setVisible(false);

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },
    preview: function (checkTempId, withData, productId) {
        //表单预览
        TestProcessUtil.CommonHelper.checkListPreview(checkTempId, true, withData, productId);
    }
});