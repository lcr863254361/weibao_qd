Ext.define('OrientTdm.CollabDev.Designing.RightSetting.RightSettingMainPanel', {
    alias: 'widget.rightSettingMainPanel',
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    requires: [
        'OrientTdm.CollabDev.Designing.RightSetting.Center.CollabRoleUserTreeGrid'
    ],
    config: {
        nodeId: ''
    },
    initComponent: function () {
        var me = this;
        var centerPanel = Ext.create("OrientTdm.CollabDev.Designing.RightSetting.Center.CollabRoleUserTreeGrid", {
            region: 'center',
            nodeId: me.nodeId
        });

        var rightPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
            title: '功能点分配',
            width: 250,
            minWidth: 250,
            maxWidth: 400,
            layout: 'border',
            collapsed: true,
            collapsible: true,
            region: 'east',
            border: true
        });

        var southPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
            region: 'south',
            height: 0.4 * globalHeight,
            layout: 'fit',
            collapsed: true,
            collapsible: true
        });

        Ext.apply(this, {
            layout: 'border',
            items: [rightPanel, centerPanel, southPanel],
            eastPanel: rightPanel,
            centerPanel: centerPanel,
            southPanel: southPanel
        });
        this.addEvents('doRefresh');
        this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.mon(_this, 'doRefresh', _this.refreshByNewNode, _this);
        _this.callParent();
    },
    refreshByNewNode: function (nodeId) {
        var _this = this;
        var centerPanel = _this.down('teamRoleUserTreeGrid');
        centerPanel.setNodeId(nodeId);
        centerPanel.refreshNode('-1');
        _this.southPanel.collapse()
    }
});