/**
 * Created by User on 2021/2/23
 */
Ext.define('OrientTdm.DataAnalysis.HistoryNumberAnalysisMgr.HistoryNumberDashBoard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.historyNumberDashBoard',
    initComponent: function () {
        var me = this;
        var checkTypeModelId = OrientExtUtil.ModelHelper.getModelId("T_CHECK_TYPE", TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID, false);
        var checkTypeTemplateId = OrientExtUtil.ModelHelper.getTemplateId(checkTypeModelId, TDM_SERVER_CONFIG.TPL_CHECK_TYPE);
        var westPanel = Ext.create('OrientTdm.DataAnalysis.HistoryNumberAnalysisMgr.HistoryNumberFormTemplateTree', {
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
            isShowButton:false,
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
            topId:'-1'
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
        //隐藏新增、修改、删除按钮
        centerPanel.down('grid').down('[text=新增]').setVisible(false);
        centerPanel.down('grid').down('[text=修改]').setVisible(false);
        centerPanel.down('grid').down('[text=级联删除]').setVisible(false);

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
