/**
 * Created by User on 2020/12/8.
 */
Ext.define('OrientTdm.ProductStructMgr.CenterPanel.StructDeviceInstGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.structDeviceInstGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_STRUCT_DEVICE_INS,
        modelName: TDM_SERVER_CONFIG.STRUCT_DEVICE_INS,
    },
    initComponent: function () {
        var me = this;
        var customerFilter = me.customerFilter;
        var bindNode = me.bindNode;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: customerFilter,
            createUrl: serviceName + '/ProductStructrue/saveStructDeviceInstData.rdm',
            updateUrl: serviceName + '/ProductStructrue/updateStructDeviceInstData.rdm',
            afterInitComponent: function () {
                //构建完表格后 定制操作
                var addButton = this.dockedItems[0].down('button[text=新增]');
                Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                    //新增表单出现后 相关定制
                    var customPanel = button.orientBtnInstance.customPanel;
                    if (customPanel) {
                        //注入额外参数
                        customPanel.originalData = Ext.apply(customPanel.originalData || {}, {
                            T_STRUCT_SYSTEM_480_ID: bindNode.parentNode.parentNode.raw.idList[0],
                            T_STRUCT_DEVICE_480_ID: bindNode.raw.idList[0],
                        });
                    }
                });
            },
        });

        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            columns.push({
                xtype: 'actioncolumn',
                text: '检查事件',
                align: 'center',
                width: 300,
                items: [{
                    iconCls: 'icon-detail',
                    tooltip: '检查事件',
                    handler: function (grid, rowIndex) {
                        var data = grid.getStore().getAt(rowIndex);
                        var deviceInstId = data.raw.ID;
                        var number=data.raw['C_NUMBER_'+modelId];
                        var deviceName = Ext.decode(data.raw['T_STRUCT_DEVICE_'+me.schemaId+'_ID_display'])[0].name;
                        var deviceId=data.raw['T_STRUCT_DEVICE_'+me.schemaId+'_ID'];
                        var structSystemId = data.raw['T_STRUCT_SYSTEM_' + me.schemaId + '_ID'];
                        var deviceCycleCheckGridPanel = Ext.create('OrientTdm.ProductStructMgr.CenterPanel.DeviceInsCheckEventGridPanel', {
                            title: '【' + deviceName+'-'+number + '】检查事件',
                            deviceInstId: deviceInstId,
                            deviceName: deviceName,
                            deviceId:deviceId,
                            structSystemId: structSystemId,
                            iconCls: "icon-data-node",
                            bindNode: bindNode,
                        });
                        var centerPanel = Ext.getCmp('deviceInstDashboard').centerPanel;
                        //移除所有面板
                        centerPanel.items.each(function (item, index) {
                            if (0 != index) {
                                centerPanel.remove(item);
                            }
                        });
                        centerPanel.add(deviceCycleCheckGridPanel);
                        centerPanel.setActiveTab(1);
                    }
                }
                ]
            })
        }
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(), columns);

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});