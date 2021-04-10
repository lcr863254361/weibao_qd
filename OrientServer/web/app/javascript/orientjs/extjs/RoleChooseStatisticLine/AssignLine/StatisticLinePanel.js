Ext.define("OrientTdm.RoleChooseStatisticLine.AssignLine.StatisticLinePanel", {
    extend: 'OrientTdm.RoleChooseStatisticLine.AssignLine.AssignLinePanel',
    alias: 'widget.roleSchemaPanel',
    iconCls:'icon-assignSchema',
    config: {
        roleId: ''
    },
    requires: [
        "OrientTdm.RoleChooseStatisticLine.AssignLine.AssignLineGrid"
    ],
    initComponent: function () {
        var me = this;
        var buttonPanel = me.createButtonPanel();
        var unSelectedGrid = Ext.create("OrientTdm.RoleChooseStatisticLine.AssignLine.AssignLineGrid", {
            roleId: me.roleId,
            title: '未分配列',
            assigned:'null'
        });
        var selectedGrid = Ext.create("OrientTdm.RoleChooseStatisticLine.AssignLine.AssignLineGrid", {
            roleId: me.roleId,
            title: '已分配列',
            assigned: true
        });
        Ext.apply(me, {
            title: '分配列',
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            items: [unSelectedGrid, buttonPanel, selectedGrid]
        });
        this.callParent(arguments);
    }
});