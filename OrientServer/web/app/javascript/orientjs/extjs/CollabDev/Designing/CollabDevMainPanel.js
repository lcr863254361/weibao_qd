/**
 * 设计状态下协同研发主面板
 */
Ext.define('OrientTdm.CollabDev.Designing.WBSDecomposition.CollabDevMainPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    alias: 'widget.designingCollabDevMainPanel',
    config: {
        currentNode: null
    },
    requires: [
        'OrientTdm.CollabDev.Designing.WBSDecomposition.WBSDecomposePanel',
        'OrientTdm.CollabDev.Designing.PedigreeSetting.PedigreeSettingPanel',
        'OrientTdm.CollabDev.Designing.ResultSettings.ResultSettingsPanel',
        'OrientTdm.CollabDev.Designing.AuditSetting.AuditSettingsPanel',
        'OrientTdm.CollabDev.Designing.OtherSetting.OtherSettingsPanel',
        'OrientTdm.CollabDev.Designing.RightSetting.RightSettingsPanel'
    ],
    initComponent: function () {
        var _this = this;
        Ext.apply(_this, {
            items: [
                {
                    title: '简介'
                }
            ],
            listeners: {
                tabchange: _this._tabchange
            },
            deferredRender: false
        });
        _this.addEvents(
            /**
             * @event fourceRefresh
             * Fires when the project tree click a diffrent node
             * @param {Ext.data.NodeInterface} clickd node
             */
            'switchnode'
        );
        _this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.callParent();
        _this.mon(_this, 'switchnode', _this._fourceRefresh, _this);
    },
    _fourceRefresh: function (node) {
        var _this = this;
        var type = node.getData().type;
        if (TDM_SERVER_CONFIG.NODE_TYPE_PRJ === type) {
            _this.setCurrentNode(node);
            //judge whether son nodes had inited
            if (_this.items.length == 1) {
                //init
                _this.removeAll(true);
                var config = {
                    nodeId: node.getId(),
                    nodeVersion: node.get('version'),
                    bmDataId: node.get('bmDataId'),
                    status: node.get('status')
                };
                var wbsDecomposPanel = Ext.create('OrientTdm.CollabDev.Designing.WBSDecomposition.WBSDecomposePanel', config);
                var resultsSettingsPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.ResultSettingsPanel', config);
                var pedigreeSettingPanel = Ext.create('OrientTdm.CollabDev.Designing.PedigreeSetting.PedigreeSettingPanel', config);
                var rightSettingsPanel = Ext.create('OrientTdm.CollabDev.Designing.RightSetting.RightSettingsPanel', config);
                var auditSettingsPanel = Ext.create('OrientTdm.CollabDev.Designing.AuditSetting.AuditSettingsPanel', config);
                var otherSettingsPanel = Ext.create('OrientTdm.CollabDev.Designing.OtherSetting.OtherSettingsPanel', config);
                _this.add([wbsDecomposPanel, resultsSettingsPanel, pedigreeSettingPanel, rightSettingsPanel, auditSettingsPanel, otherSettingsPanel]);
                //bind event
                _this.items.each(function (item) {
                    item.relayEvents(_this, ['switchnode']);
                });
                _this.setActiveTab(0);
            }
        } else {
            return false;
        }
    },
    _tabchange: function (tabPanel, newItem) {
        if (newItem.needRefresh) {
            newItem.setNeedRefresh(false);
            newItem.fireEvent('refreshByNewNode');
        }
    }
});