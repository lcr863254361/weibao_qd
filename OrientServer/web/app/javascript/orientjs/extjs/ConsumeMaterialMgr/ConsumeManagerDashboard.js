/**
 * Created by User on 2019/5/17.
 */
Ext.define('OrientTdm.ConsumeMaterialMgr.ConsumeManagerDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.consumeManagerDashboard',

    initComponent:function(){
        var me=this;

        var leftPanel=Ext.create('OrientTdm.ConsumeMaterialMgr.ConsumeTypeManagerGrid',{
            title: '<span style="color: blue; ">'+'耗材分类'+'</span>',
            titleAlign:'center',
            collapsible:false,
            autoScroll : true,
            width:250,
            minWidth:250,
            maxWidth:300,
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