Ext.define('OrientTdm.DataAnalysis.FormTemplateMgrDataAnalysisPanel',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.formTemplateMgrDataAnalysisPanel',
    requires:[
        "OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTempletCheckTree",
        "OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTempletHeadCellGrid"
    ],
    initComponent:function(){
        var me=this;

        var leftPanel = Ext.create('OrientTdm.DataAnalysis.FormTemplateCheckTree', {
            region: 'west',
            checkTypeId: me.checkTypeId,
            checkTypeName:me.checkTypeName,
            collapsible: false,
            autoScroll : true,
            width:290,
            minWidth: 290,
            maxWidth: 350,
            rootVisible: false,
            split:true
        });
        var centerTabPanel= Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
            layout:'border',
            region: 'center'
        });
        Ext.apply(this, {
            layout: 'border',
            items: [leftPanel,centerTabPanel],
            westPanel: leftPanel,
            centerPanel: centerTabPanel
        });
        me.callParent(arguments);
    }
});