/**
 * Created by User on 2019/1/12.
 */
Ext.define('OrientTdm.FormTemplateMgr.ProductStructureTree.ProductStructureTreeDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.productStructureTreeDashboard',
    requires:[],

    initComponent:function(){
        var me=this;

        var leftPanel=Ext.create('OrientTdm.FormTemplateMgr.ProductStructureTree.ProductStructureTree',{
            collapsible: false,
            width:240,
            minWidth:240,
            maxWidth:350,
            region:'center',
            split:true
        });

        leftPanel.expandAll();

        Ext.apply(this, {
            layout: 'border',
            items: [leftPanel],
            westPanel: leftPanel
        });

        me.callParent(arguments);
    }
});