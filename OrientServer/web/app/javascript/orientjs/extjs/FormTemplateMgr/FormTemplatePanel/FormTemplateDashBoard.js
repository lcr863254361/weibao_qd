/**
 * Created by User on 2020/12/31
 */
Ext.define('OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateDashBoard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.formTemplateDashBoard',
    initComponent: function () {
        var me = this;
        var init = true;
        var checkTypeModelId = OrientExtUtil.ModelHelper.getModelId("T_CHECK_TYPE", TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID, false);
        var checkTypeTemplateId = OrientExtUtil.ModelHelper.getTemplateId(checkTypeModelId, TDM_SERVER_CONFIG.TPL_CHECK_TYPE);
        var westPanel = Ext.create('OrientTdm.HistoryCheckTableCompareMgr.HistotyFormTemplateMgr.HistoryFormTemplateTree', {
            layout: {
                type: 'fit',
                animate: true
            },
            animCollapse: true,
            collapsible: true,
            title: '导航树',
            width: 300,
            //minWidth: 280,
            maxWidth: 400,
            padding: '0 0 0 5',
            region: 'west',
            split:true,
            isShowButton:true,
            listeners: {
                // load: function (store, record, successful) {
                //     if (init && record.get("id") == "0") {
                //         init = false;
                //         westPanel.fireEvent("itemclick", westPanel, record);
                //         westPanel.getSelectionModel().select(record);
                //     }
                // }
            }
        });
        var grid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            // title: '表单模板类型',
            modelId: checkTypeModelId,
            templateId: checkTypeTemplateId,
            region: 'center',
            isView: '0',
            topId:'-1',
            afterOperate:function () {
                var treepanel =me.westPanel;
                var store = treepanel.getStore();
                var selectNode = treepanel.getSelectionModel().getSelection()[0];
                if (Ext.isEmpty(selectNode))	//如果没有父节点，则pnode为根节点
                {
                    selectNode = store.getRootNode();
                }
                store.load({
                    node: selectNode,
                    callback: function (records) {
                        Ext.each(records, function (record) {
                            if (record.get('expanded') == true) {
                                record.set('expanded', false);
                                record.expand();
                            }
                        });
                    }
                });
            }
        });
        var  centerPanel= Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
            region: 'center',
            layout: 'fit',
            activeTabName : '',
            padding: '0 0 0 5'
        });
        centerPanel.add({
            title: '表单模板分类',
            layout: 'border',
            items: [grid]
        });
        centerPanel.setActiveTab(0);
        Ext.apply(me, {
            layout: 'border',
            items: [westPanel, centerPanel],
            westPanel: westPanel,
            centerPanel: centerPanel
        });
        me.callParent(arguments);
    }
});
