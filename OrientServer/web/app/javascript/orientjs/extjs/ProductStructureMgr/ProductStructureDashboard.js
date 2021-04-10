/**
 * Created by User on 2019/1/12.
 */
Ext.define('OrientTdm.ProductStructureMgr.ProductStructureDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.productStructureDashboard',
    requires:[],

    initComponent:function(){
        var me=this;

        var leftPanel=Ext.create('OrientTdm.ProductStructureMgr.ProductStructureTree',{
            title: '产品结构',
            collapsible: true,
            autoScroll : true,
            width:350,
            minWidth:350,
            maxWidth:400,
            region:'west',
            split:true
        });
        leftPanel.expandAll();

        var centerPanel=Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel',{
            region: 'center',
            layout: 'fit',
            activeTabName : '',
            padding: '0 0 0 5'
        });


        Ext.apply(this, {
            layout: 'border',
            items: [leftPanel, centerPanel],
            westPanel: leftPanel,
            centerPanel: centerPanel
        });

        me.callParent(arguments);
    }
})