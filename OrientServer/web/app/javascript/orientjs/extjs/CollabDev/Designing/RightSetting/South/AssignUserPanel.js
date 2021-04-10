Ext.define("OrientTdm.CollabDev.Designing.RightSetting.South.AssignUserPanel", {
    extend: 'OrientTdm.SysMgr.RoleMgr.Common.AssignPanel',
    requires: [
        "OrientTdm.CollabDev.Designing.RightSetting.South.AssignUserGrid"
    ],
    config: {
        assignCallback: Ext.emptyFn
    },
    initComponent: function () {
        var me = this;

        //创建按钮操作区域
        var buttonPanel = me.createButtonPanel();
        var unSelectedGrid = Ext.create("OrientTdm.CollabDev.Designing.RightSetting.South.AssignUserGrid", {
            roleId: me.roleId,
            title: '未分配用户',
            assigned: false
        });
        var selectedGrid = Ext.create("OrientTdm.CollabDev.Designing.RightSetting.South.AssignUserGrid", {
            roleId: me.roleId,
            title: '已分配用户'
        });
        Ext.apply(me, {
            header: false,
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            items: [unSelectedGrid, buttonPanel, selectedGrid]
        });

        unSelectedGrid.getStore().load();
        selectedGrid.getStore().load();
        this.callParent(arguments);
        me.addEvents("showDetail");
    },
    initEvents: function () {
        var me = this;
        me.callParent(arguments);
        me.mon(me, 'showDetail', me.showDetail, me);
    },
    showDetail: function () {
        this.expand(true);
    }
});