/**
 * Created by User on 2019/1/9.
 */
Ext.define('OrientTdm.PostMgr.PostManagerDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.postManagerDashboard',

    initComponent:function(){
        var me=this;

        var gridPanel=Ext.create('OrientTdm.PostMgr.PostManagerGrid',{

        });

        Ext.apply(me,{
            layout:'card',
            items:[gridPanel],
            activeItem:0
        });
        me.callParent(arguments);
    }
});