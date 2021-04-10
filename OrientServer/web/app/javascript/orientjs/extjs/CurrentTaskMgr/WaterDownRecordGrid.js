/**
 * Created by User on 2019/3/13.
 */
Ext.define('OrientTdm.CurrentTaskMgr.WaterDownRecordGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.waterDownRecordGrid',
    id:'waterDownRecordGridOwner',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_WATER_DOWN_RECORD,
        modelName: TDM_SERVER_CONFIG.WATER_DOWN_RECORD
    },
    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var taskId=me.taskId;
        ////根据节点和任务id
        //var filter = me.addCustomerFilterTable(modelId);
        console.log(me);
        //var nodeId=me.getStore().getProxy().getExtraData('nodeId');
        //var nodeText=me.getStore().getProxy().getExtraData('nodeText');
        var customerFilter=new CustomerFilter('T_DIVING_TASK_'+me.schemaId+'_ID',CustomerFilter.prototype.SqlOperation.Equal,'',taskId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter:[customerFilter],
            queryUrl: serviceName + "/CurrentTaskMgr/getWaterDownRecordData.rdm"
        });
        //增加水下记录单查看按钮
        var columns=modelGrid.columns;
        if(Ext.isArray(columns)){
            columns.push(
                {
                    xtype:'actioncolumn',
                    text:'查看',
                    align:'center',
                    width:200,
                    items:[{
                        iconCls:'icon-detail',
                        tooltip:'记录单查看',
                        handler:function(grid,rowIndex){
                            var data=grid.getStore().getAt(rowIndex);
                            var waterDownId=data.raw.ID;
                            var recordTitle=data.raw['C_RECORD_NAME_'+modelId];
                            var createWin = Ext.create('Ext.Window', {
                                title: recordTitle,
                                titleAlign:'center',
                                plain: true,
                                height: 0.5 * globalHeight,
                                width: 0.5 * globalWidth,
                                layout: 'fit',
                                maximizable: true,
                                modal: true,

                                items: [Ext.create('OrientTdm.CurrentTaskMgr.WaterDownDetail', {
                                    waterDownId:waterDownId
                                })],
                            });
                            createWin.show();
                        }
                    }]
                })
        }
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(),columns);
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});

