Ext.define('OrientTdm.ProductStructMgr.CenterPanel.DeviceInsCheckEventGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.deviceInsCheckEventGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_DEVICE_INS_EVENT,
        modelName: TDM_SERVER_CONFIG.DEVICE_INS_EVENT,
        structSystemId: '',
    },

    initComponent: function () {
        var me = this;
        var structSystemId = me.structSystemId;
        var bindNode = me.bindNode;
        var deviceId = me.deviceId;
        var deviceName = me.deviceName;
        var deviceInstId = me.deviceInstId;
        var customerFilter = new CustomerFilter("T_STRUCT_DEVICE_INS_" + me.schemaId + "_ID", CustomerFilter.prototype.SqlOperation.Equal, "", deviceInstId)
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            schemaId: me.schemaId,
            customerFilter: [customerFilter],
            afterInitComponent: function () {
                var me = this;
                var toolbar = me.dockedItems[0];
                toolbar.add({
                    iconCls: 'icon-back',
                    text: '返回上一页',
                    handler: function () {
                        var centerPanel = Ext.getCmp('deviceInstDashboard').centerPanel;
                        //移除所有面板
                        centerPanel.items.each(function (item, index) {
                            if (0 != index) {
                                centerPanel.remove(item);
                            }
                        });
                        var filterColumnName = "T_STRUCT_DEVICE_" + TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID + "_ID";//主表表名ID
                        var customerFilter = [new CustomerFilter(filterColumnName, CustomerFilter.prototype.SqlOperation.Equal, "", deviceId)];//过滤条件
                        var gridPanel = Ext.create('OrientTdm.ProductStructMgr.CenterPanel.StructDeviceInstGridPanel', {
                            title: '产品结构设备实例',
                            iconCls: "icon-data-node",
                            customerFilter: customerFilter,
                            deviceId: deviceId,
                            bindNode: bindNode
                        });
                        //自己手写的后台方法有效
                        // gridPanel.modelGrid.getStore().getProxy().setExtraParam("T_STRUCT_SYSTEM_" + TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID + '_ID', structSystemId);
                        centerPanel.add(gridPanel);
                        centerPanel.setActiveTab(1);
                    },
                    scope: me
                });
            }
        });
        //设置grid的点击事件
        modelGrid.on("itemclick", function (grid, record, item, index, e) {
            var deviceInstEventId = record.data.id;
            var eastPanel = grid.up().next('orientTabPanel');
            var params = {
                title: '上传的检查表',
                deviceInstEventId: deviceInstEventId,
                selType: "rowmodel",  //不添加复选框
                createToolBarItems: function () {
                    return [];
                },
                iconCls: "icon-data-node",
                listeners: {
                    // afterrender: function (grid) {
                    //     var columns = grid.modelGrid.columns;
                    //     Ext.each(columns, function (column) {
                    //         if (column.text === '状态'||column.text === '检查人'||column.text === '检查时间') {
                    //             column.hide();
                    //         }
                    //     })
                    // }
                },
                isDeviceInsCheckEvent:true
            };

            //动态创建预览模块
            var tempInstGrid = Ext.create("OrientTdm.TaskPrepareMgr.Center.TabPanel.CheckTableMgrPanel", params);

            if (!eastPanel) {
                eastPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
                    region: 'south',
                    padding: '0 0 0 0',
                    layout: 'border',
                    deferredRender: false
                });
                grid.up().add(eastPanel);
                grid.up().doLayout();
            }
            eastPanel.expand(true);
            eastPanel.removeAll();
            eastPanel.add(tempInstGrid);
            eastPanel.doLayout();
            eastPanel.show();
        });
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid, {
                xtype: 'orientTabPanel',
                region: 'east',
                collapsible: true,
                width: 0.3 * globalWidth,
                collapseMode: 'mini',
                header: false,
                //height: 0.4 * globalHeight,
                collapsed: true,
                layout: 'fit',
                listeners: {
                    beforeshow: function () {

                    }
                }
            }],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});