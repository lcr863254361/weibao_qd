/**
 * 交付物面板
 */
Ext.define('OrientTdm.CollabDev.Processing.ResultSettings.ResultSettingsBoard', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.processingResultSettingsBoard',
    config: {},
    requires: [
        'OrientTdm.CollabDev.Processing.ResultSettings.ResultSettingsTabPanel'
    ],
    initComponent: function () {
        var _this = this;

        Ext.apply(_this, {
            iconCls: 'icon-collabDev-results',
            title: '交付物',
            layout: 'fit',
            items: [
                {
                    xtype: 'container',
                    title: '占位面板'
                }
            ]
        });

        _this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.mon(_this, 'refreshByNewNode', _this.refreshByNewNode, _this);
        _this.callParent();
    },
    refreshByNewNode: function () {
        var _this = this;
        if (!_this.getInited()) {
            _this.setInited(true);
            _this.removeAll();

            var processingResultSettingsTabPanel = Ext.create('OrientTdm.CollabDev.Processing.ResultSettings.ResultSettingsTabPanel', {
                nodeId: _this.nodeId,
                nodeVersion: _this.nodeVersion,
                bmDataId: _this.bmDataId,
                type: _this.type
            });
            processingResultSettingsTabPanel.restructureTabs(_this.nodeId, _this.nodeVersion, _this.type, _this.bmDataId);
            _this.add([processingResultSettingsTabPanel]);
        } else {
            _this.down('processingResultSettingsTabPanel').restructureTabs(_this.nodeId, _this.nodeVersion, _this.type, _this.bmDataId);
        }
    }
});