Ext.define('OrientTdm.DeviceInstDataMgr.DeviceInsCheckTableGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.deviceInsCheckTableGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CYCLE_DEVICE_REF_CHECK_TEMP_INST,
        modelName: TDM_SERVER_CONFIG.CHECK_TEMP_INST
    },

    requires: ["OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil"],

    initComponent: function () {
        var me = this;
        var deviceInstEventId = me.deviceInstEventId;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var customerFilter = new CustomerFilter('T_DEVICE_INS_EVENT_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', deviceInstEventId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter]
        });
        //增加检查表实例查看按钮
        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            columns.push(
                {
                    xtype: 'actioncolumn',
                    text: '查看',
                    align: 'center',
                    width: 'auto',
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