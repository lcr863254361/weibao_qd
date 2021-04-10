/**
 * Created by User on 2019/1/21.
 */

Ext.define('OrientTdm.ProductStructureMgr.CurrentCheckRecordMgr', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.currentCheckRecordMgr',

    requires: ["OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil"],

    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CURRENT_CHECK_RECORD,
        modelName: TDM_SERVER_CONFIG.CHECK_TEMP_INST,
        deviceContent: '',
        nodeContent: ''
    },

    initComponent: function () {
        var me = this;
        var productId = me.productId;
        var nodeContent = me.nodeContent;
        var deviceContent;
        var deviceInstId = '';
        if (!Ext.isEmpty(productId)) {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ProductStructrue/getCurrentRefDevice.rdm', {
                    productId: productId
                },
                false, function (response) {

                    me.deviceContent = response.decodedData.results.select;
                    deviceInstId = response.decodedData.results.id;
                    //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                })
        };
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var modelGrid = Ext.create('OrientTdm.ProductStructureMgr.CellDataView.CellDataViewContent', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            productId: productId,
            deviceInstId: deviceInstId,
            deviceContent: me.deviceContent,
            nodeContent: me.nodeContent
        });

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            centerPanel: modelGrid
        });
        me.callParent(arguments);
    }
});
