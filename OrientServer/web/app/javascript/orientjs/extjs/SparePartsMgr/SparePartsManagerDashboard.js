/**
 * Created by User on 2018/12/11.
 */
Ext.define('OrientTdm.SparePartsMgr.SparePartsManagerDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.sparePartsManagerDashboard',
    initComponent:function(){
        var me=this;

        var leftPanel=Ext.create('OrientTdm.SparePartsMgr.ProductStructureTree',{
            collapsible: false,
            width:350,
            minWidth:350,
            maxWidth:400,
            region:'west',
            split:true
        });

        leftPanel.expandAll();

        var centerPanel=Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel',{
            region:'center',
            layout:'fit',
            padding:'0 0 0 5',
            id:'spareCenterPanel'
        });
        //centerPanel.setActiveTab(0);//Tab不活动
        // var gridPanel=Ext.create('OrientTdm.SparePartsMgr.SparePartsManagerDataView',{
        // });

        //var gridPanel=Ext.create('OrientTdm.SparePartsMgr.SparePartsManagerGrid',{
        //
        //});

        //Ext.apply(me,{
        //    layout:'card',
        //    items:[gridPanel],
        //    activeItem:0
        //});
        //me.callParent(arguments);
        Ext.apply(this,{
            layout:'border',
            items:[leftPanel,centerPanel],
            westPanel:leftPanel,
            centerPanel:centerPanel
        });
        me.callParent(arguments);
    }
    // navigation:function(panel,direction){
    //     var layout=panel.getLayout();
    //     layout[direction]();
    // }
});