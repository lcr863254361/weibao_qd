Ext.define('OrientTdm.DailyWorkMgr.DailyWorkMgrDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.dailyWorkMgrDashboard',

    initComponent:function(){
        var me=this;

        var gridPanel=Ext.create('OrientTdm.DailyWorkMgr.DailyWorkMgrGridPanel',{

        });

        Ext.apply(me,{
            layout:'card',
            items:[gridPanel],
            activeItem:0
        });
        me.callParent(arguments);
    }
});