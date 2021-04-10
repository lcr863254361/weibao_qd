Ext.define('OrientTdm.RoleChooseStatisticLine.AllRoleInfo', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    alias: 'widget.RoleInfo',
    requires: [
        'OrientTdm.RoleChooseStatisticLine.AssignLine.StatisticLinePanel'
    ],
    initComponent: function () {
        var me = this;
        this.title = '下潜统计列信息';
        this.collapsible = true;
        Ext.apply(this, {
            items: []
        });
        me.callParent(arguments);
        me.addEvents('showRoleDetail');
    },
    initEvents: function () {
        var me = this;
        me.callParent(arguments);
        me.mon(me, 'showRoleDetail', me.showRoleDetail, me);
    },
    showRoleDetail: function (roleId) {
        var me = this;
        this.expand(true);
        if (Ext.isEmpty(me.roleId)) {
            //第一次加载
            me.roleId = roleId;
            this.removeAll(true);
            var assignSchemaTab = Ext.create('OrientTdm.RoleChooseStatisticLine.AssignLine.StatisticLinePanel', {
                roleId: roleId
            });
            this.add([assignSchemaTab]);
            this.setActiveTab(0);
        } else {
            //设置为新的roleId
            var sonPanels = this.query('assignPanel');
            Ext.each(sonPanels, function (sonPanel) {
                sonPanel.setRoleId(roleId);
            });
            this.getActiveTab().fireEvent('activate');
        }
    }
});