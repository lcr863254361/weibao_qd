Ext.define('OrientTdm.CarryToolMgr.CabinOutCarryToolDashBoard',{
    extend : 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias : 'widget.cabinOutCarryToolDashBoard',
    config : {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CABIN_OUT_TOOL,
        modelName: TDM_SERVER_CONFIG.CABIN_CARRY_TOOL,
    },
    require : [
    ],
    initComponent : function(){
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var customerFilter = [{
            filterName: 'C_CABIN_INOROUT_' + modelId,
            filterValue: 'cabinOut',
            operation: 'Like'
        }];
        var dataGrid = Ext.create('OrientTdm.CarryToolMgr.CarryToolGridPanelMgr',{
            region : 'center',
            modelId : modelId,
            templateId : templateId,
            customerFilter : customerFilter,
            isView : '0',
            isCabinOrOut:'cabinOut',
            modelId:modelId
        });
        Ext.apply(me, {
            layout : 'border',
            items : [dataGrid]
        });
        me.callParent(arguments);
    }
});