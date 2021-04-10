
Ext.define('OrientTdm.AnyUpdateDivingPlanMgr.AnyDivingPlanDashBoard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.anyDivingPlanDashBoard',

    initComponent: function () {
        var me=this;
        var leftPanel=Ext.create('OrientTdm.AnyUpdateDivingPlanMgr.AnyDivingTaskManagerGrid',{
            title: '<span style="color: blue; ">'+'潜次列表'+'</span>',
            titleAlign:'center',
            collapsible:false,
            width:200,
            minWidth:200,
            maxWidth:250,
            region:'west',
            split:true
        });

        var centerPanel=Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel',{
            region:'center',
            layout:'fit',
            padding:'0 0 0 5'
        });
        centerPanel.setActiveTab(0);//Tab不活动

        Ext.apply(this,{
            layout:'border',
            items:[leftPanel,centerPanel],
            westPanel:leftPanel,
            centerPanel:centerPanel
        });
        me.callParent(arguments);
    }
});
