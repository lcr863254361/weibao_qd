/**
 * Created by liuyangchao on 2019/3/7.
 */
Ext.define('OrientTdm.GlobalMap.MapDashFunction',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.mapDashFunction',
    initComponent:function(){
        var me=this;
        var centerPanel=Ext.create('OrientTdm.GlobalMap.MapDashBord',{
            region:'center',
            layout:'fit',
            padding:'0 0 0 5',
        });

        Ext.apply(this,{
            title: '全景地图',
            layout:'border',
            items:[centerPanel],
        });
        me.callParent(arguments);
    }


})