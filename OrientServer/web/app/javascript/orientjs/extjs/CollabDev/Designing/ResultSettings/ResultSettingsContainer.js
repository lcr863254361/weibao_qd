/**
 * 上面是checkbox,下面是tab页
 */
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.ResultSettingsContainer', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.resultSettingsContainer',
    config: {
        nodeId: '',
        nodeVersion: ''
    },
    initComponent: function () {
        var _this = this;

        var bindPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.ResultSettingsBindPanel', {
            region: 'north',
            height: 50,
            nodeId: _this.nodeId,
            nodeVersion: _this.nodeVersion
        });

        var mainPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.ResultSettingsMainPanel', {
            region: 'center',
            nodeId: _this.nodeId,
            nodeVersion: _this.nodeVersion
        });

        Ext.apply(_this, {
            layout: 'border',
            items: [bindPanel, mainPanel],
            toBindPanel: bindPanel,
            centerPanel: mainPanel
        });

        _this.callParent(arguments);
    }
});