Ext.define('OrientTdm.RoleChooseStatisticLine.DivingLineAssignDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.RoleMain',

    initComponent:function(){
        var me = this;
        var centerItems = [];
        var roleList = Ext.create('OrientTdm.RoleChooseStatisticLine.AllRoleList', {
            region: 'center'
        });
        centerItems.push(roleList);
        var southPanel = Ext.create('OrientTdm.RoleChooseStatisticLine.AllRoleInfo', {
            region: 'south',
            height: 0.5 * globalHeight,
            collapsed: true
        });
        centerItems.push(southPanel);
        var centerPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
            layout: 'border',
            region: 'center',
            items: centerItems
        });

        Ext.apply(me, {
            layout: 'border',
            items: [centerPanel],
            centerPanel: roleList,
            southPanel: southPanel
        });
        me.callParent(arguments);
    }
});