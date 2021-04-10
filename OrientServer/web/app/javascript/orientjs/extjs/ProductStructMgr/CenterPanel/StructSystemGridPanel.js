Ext.define('OrientTdm.ProductStructMgr.CenterPanel.StructSystemGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.structSystemGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_STRUCT_SYSTEM,
        modelName: TDM_SERVER_CONFIG.STRUCT_SYSTEM,
    },

    initComponent: function () {
        var me = this;
        var isOnlyShow=me.isOnlyShow;
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
            createUrl: serviceName + "/ProductStructrue/saveStructSystemData.rdm",
            updateUrl: serviceName + '/ProductStructrue/updateStructSystemData.rdm',
            afterInitComponent: function () {

            },
            afterOperate:function () {
                var treepanel = Ext.getCmp("productStructDashboard").westPanel;
                var store = treepanel.getStore();
                var selectNode = treepanel.getSelectionModel().getSelection()[0];
                if (Ext.isEmpty(selectNode))	//如果没有父节点，则pnode为根节点
                {
                    selectNode = store.getRootNode();
                }else if (selectNode.raw.level==1){
                    selectNode=selectNode.parentNode;
                }
                store.load({
                    node: selectNode,
                    callback: function (records) {
                        selectNode.appendChild(records);	//添加子节点
                        selectNode.set('leaf',false);
                        selectNode.expand(true);
                    }
                });
            }
        });
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});