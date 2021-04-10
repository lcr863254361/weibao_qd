/**
 * Created by User on 2020/12/14.
 */
Ext.define('OrientTdm.HistoryCheckTableCompareMgr.CheckTypeManagerGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.checkTypeManagerGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.CHECK_TYPE,
        modelId: ''
    },
    initComponent: function () {
        var me = this;
        modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);

        var store = Ext.create('Ext.data.Store', {

            fields: ["id", "checkType"],
            proxy: {
                type: 'ajax',
                url: serviceName + "/formTemplate/queryCheckTypeList.rdm",
                reader: {
                    type: 'json',
                    root: 'results'
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        var columns = [
            {id: 'dataCheckHeaderId', text: '检查名称', dataIndex: 'checkType', flex: 1, align: 'center',
                renderer: function (value, meta, record) {
                    value = value || '';
                    meta.tdAttr = 'data-qtip="' + value + '"';
                    return value;
                }}
        ];
        var girdPanel = Ext.create('Ext.grid.Panel', {
            renderTo: Ext.getBody(),
            hideHeaders: true,
            store: store,
            columns: columns,
            buttonAlign: 'center'
            //selType: 'rowmodel',
            //plugins: [
            //    Ext.create('Ext.grid.plugin.RowEditing', {
            //        clicksToEdit: 1
            //    })
            //]
        });

        girdPanel.addListener('itemclick', function (grid, record, item, index, e) {
            var checkTypeId = record.data.id;
            var checkTypeName=record.data.checkType
            var formTemplateCenterPanel = me.up('historyCheckTableDashboard').centerPanel;
            formTemplateCenterPanel.items.each(function (item, index) {
                formTemplateCenterPanel.remove(item);
            });
            var formTemplateMgrPanel = Ext.create('OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateMgrPanel', {
                region: 'center',
                checkTypeId: checkTypeId,
                checkTypeName:checkTypeName
            });

            formTemplateCenterPanel.add({
                title: '检查表模板列表',
                layout: 'border',
                items: [formTemplateMgrPanel]
            });
            formTemplateCenterPanel.setActiveTab(0);
        });
        Ext.apply(me, {
            layout: 'fit',
            items: [girdPanel],
            modelGrid: girdPanel
        });
        me.callParent(arguments);
    }
});
