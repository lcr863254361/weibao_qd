Ext.define('OrientTdm.DestroyTaskMgr.DestroyTaskMgrDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.destroyTaskMgrDashboard',

    initComponent:function(){
        var me=this;

        var gridPanel=Ext.create('OrientTdm.DestroyTaskMgr.DestroyTaskManagerGrid',{

        });

        Ext.apply(me,{
            layout:'card',
            items:[gridPanel],
            activeItem:0
        });
        me.callParent(arguments);
    },
    navigation:function(panel,direction){
        var layout=panel.getLayout();
        layout[direction]();
    }
});