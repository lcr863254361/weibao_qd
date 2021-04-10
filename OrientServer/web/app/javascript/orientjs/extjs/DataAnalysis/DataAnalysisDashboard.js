/**
 * Created by User on 2018/12/12.
 */
Ext.define('OrientTdm.DataAnalysis.DataAnalysisDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.dataAnalysisDashboard',

    initComponent:function(){
        var me=this;

        var leftPanel=Ext.create('OrientTdm.DataAnalysis.CheckTypeManagerGrid',{
            title: '<span style="color: blue; ">'+'模板分类'+'</span>',
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
})