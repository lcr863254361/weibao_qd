Ext.define('OrientTdm.ProductStructMgr.CenterPanel.DeviceCycleCheckGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.deviceCycleCheckGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CYCLE_DEVICE,
        modelName: TDM_SERVER_CONFIG.CYCLE_DEVICE,
        structSystemId: ''
    },

    initComponent: function () {
        var me = this;
        var structSystemId = me.structSystemId;
        var bindNode = me.bindNode;
        var deviceId = me.deviceId;
        var deviceName = me.deviceName;
        var customerFilter = new CustomerFilter("T_STRUCT_DEVICE_" + me.schemaId + "_ID", CustomerFilter.prototype.SqlOperation.Equal, "", deviceId)
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
                //构建完表格后 定制操作
                var addButton = this.dockedItems[0].down('button[text=新增]');
                Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                    //新增表单出现后 相关定制
                    var customPanel = button.orientBtnInstance.customPanel;
                    if (customPanel) {
                        //注入额外参数
                        customPanel.originalData = Ext.apply(customPanel.originalData || {}, {
                            T_STRUCT_DEVICE_480_ID: deviceId,
                        });
                    }
                });
                var toolbar = me.dockedItems[0];
                toolbar.add({
                        iconCls: 'icon-back',
                        text: '返回上一页',
                        handler: function () {
                            var centerPanel = Ext.getCmp('productStructDashboard').centerPanel;
                            //移除所有面板
                            centerPanel.items.each(function (item, index) {
                                if (0 != index) {
                                    centerPanel.remove(item);
                                }
                            });
                            var filterColumnName = "T_STRUCT_SYSTEM_" + TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID + "_ID";//主表表名ID
                            var customerFilter = [new CustomerFilter(filterColumnName, CustomerFilter.prototype.SqlOperation.Equal, "", structSystemId)];//过滤条件
                            if (bindNode.raw.level == 3) {
                                var object = new Object();
                                object.filterName = 'C_TYPE_' + bindNode.raw.modelId;
                                object.operation = CustomerFilter.prototype.SqlOperation.Equal,
                                    object.expressType = "";
                                object.filterValue = bindNode.raw.text;
                                object.connection = 'And'
                                customerFilter.push(object)
                            }
                            var gridPanel = Ext.create('OrientTdm.ProductStructMgr.CenterPanel.StructDeviceGridPanel', {
                                title: '产品结构设备',
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
                    },
                    '->', {
                        xtype: 'tbtext',
                        text: '<span style="color: red">*双击行即可关联检查表</span>'
                    });
            }
        });
        //设置grid的点击事件
        modelGrid.on("itemclick", function (grid, record, item, index, e) {
            var deviceCycleId = record.raw.ID;
            var deviceCycleName = record.raw['C_NAME_' + modelId];
            var eastPanel = grid.up().next('orientTabPanel');
            var params = {
                title: '检查表模板',
                deviceCycleId: deviceCycleId,
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
                }
            };

            //动态创建预览模块
            var tempInstGrid = Ext.create("OrientTdm.ProductStructMgr.CenterPanel.CheckTableCenterPanel.CheckTableGridPanel", params);
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
        // var columns = modelGrid.columns;
        // if (Ext.isArray(columns)) {
        //     columns.push({
        //         xtype: 'actioncolumn',
        //         text: '关联检查表',
        //         align: 'center',
        //         width: 200,
        //         items: [{
        //             iconCls: 'icon-detail',
        //             tooltip: '关联检查表',
        //             handler: function (grid, rowIndex) {
        //                 var data = grid.getStore().getAt(rowIndex);
        //                 var deviceCycleId = data.raw.ID;
        //                 var deviceCycleName = data.raw['C_NAME_' + modelId];
        //                 var checkTableGrid = Ext.create('OrientTdm.ProductStructMgr.CenterPanel.CheckTableCenterPanel.CheckTableGridPanel', {
        //                     title: '【' + deviceCycleName + '】关联检查表',
        //                     iconCls: "icon-data-node",
        //                     deviceCycleId: deviceCycleId,
        //                     deviceCycleName: deviceCycleName,
        //                     structSystemId: structSystemId,
        //                     deviceId: deviceId,
        //                     deviceName:deviceName,
        //                     bindNode: bindNode
        //                 });
        //                 var centerPanel = Ext.getCmp('productStructDashboard').centerPanel;
        //                 //移除所有面板
        //                 centerPanel.items.each(function (item, index) {
        //                     if (0 != index) {
        //                         centerPanel.remove(item);
        //                     }
        //                 });
        //                 centerPanel.add(checkTableGrid);
        //                 centerPanel.setActiveTab(1);
        //             }
        //         }
        //         ]
        //     })
        // }
        //使用一个新的store/columns配置项进行重新配置生成grid
        // modelGrid.reconfigure(modelGrid.getStore(), columns);
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