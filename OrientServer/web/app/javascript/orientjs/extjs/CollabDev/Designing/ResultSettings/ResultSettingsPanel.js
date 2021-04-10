/**
 *
 */
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.ResultSettingsPanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.resultsSettingsPanel',
    config: {},
    requires: [
        'OrientTdm.CollabDev.Common.Navigation.DevPrjTreeBypurpose',
        'OrientTdm.CollabDev.Designing.ResultSettings.ResultSettingsContainer'
    ],
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            iconCls: 'icon-collabDev-resultsSetting',
            title: '2.交付物设置',
            layout: 'border',
            items: [
                {
                    xtype: 'container',
                    title: '导航区域',
                    region: 'west',
                    width: 240
                }, {
                    xtype: 'container',
                    region: 'center',
                    title: '设置区域'
                }
            ]
        });
        me.callParent(arguments);
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
            _this.add([
                {
                    xtype: 'devPrjTreeBypurpose',
                    prjNodeId: _this.getNodeId(),
                    prjNodeName: _this.getNodeName(),
                    prjNodeVersion: _this.getNodeVersion(),
                    region: 'west',
                    width: 220,
                    _itemClickListener: _this._itemClickListener,
                    purpose: 'expandToTask'
                }, {
                    xtype: 'resultSettingsContainer',
                    itemId: 'resultSettingsContainer',
                    region: 'center',
                    nodeId: _this.getNodeId(),
                    nodeVersion: _this.getNodeVersion(),
                    padding: '0 0 0 5'
                }
            ]);
        } else
            _this.down('devPrjTreeBypurpose').reloadByNewNode(_this.getNodeId(), _this.getNodeName(), _this.getNodeVersion());
    },
    _itemClickListener: function (tree, node) {
        var _this = this;
        var nodeId = node.getId();
        var mainPanel = _this.up('resultsSettingsPanel');
        var responsePanel = mainPanel.down('#resultSettingsContainer');
        var tabPanel = responsePanel.centerPanel;
        var checkBoxPannel = responsePanel.toBindPanel;
        //checkboxPanel的nodeId重新赋值
        checkBoxPannel.nodeId = nodeId;
        //重新构筑所有的tab页面
        tabPanel.restructureTabs(nodeId, _this.prjNodeVersion, checkBoxPannel);
    }
});