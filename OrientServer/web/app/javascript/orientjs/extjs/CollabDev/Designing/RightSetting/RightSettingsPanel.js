/**
 *
 */
Ext.define('OrientTdm.CollabDev.Designing.RightSetting.RightSettingsPanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.rightSettingsPanel',
    config: {},
    requires: [
        'OrientTdm.CollabDev.Designing.RightSetting.RightSettingMainPanel'
    ],
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            iconCls: 'icon-collabDev-rightSetting',
            title: '4.权限设置',
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
        if (_this.getInited() == false) {
            _this.setInited(true);
            _this.removeAll();
            _this.add([
                {
                    xtype: 'devPrjTreeBypurpose',
                    prjNodeId: _this.getNodeId(),
                    prjNodeName: _this.getNodeName(),
                    prjNodeVersion: _this.getNodeVersion(),
                    region: 'west',
                    width: 240,
                    _itemClickListener: _this._itemClickListener,
                    purpose: 'expandToTask'
                }, {
                    xtype: 'rightSettingMainPanel',
                    itemId: 'responsePanel',
                    region: 'center',
                    padding: '0 0 0 5',
                    nodeId: _this.getNodeId()
                }
            ]);
        } else
            _this.down('devPrjTreeBypurpose').reloadByNewNode(_this.getNodeId(), _this.getNodeName(), _this.getNodeVersion());
    },
    _itemClickListener: function (tree, node) {
        var _this = this;
        var mainPanel = _this.up('rightSettingsPanel');
        var responsePanel = mainPanel.down('#responsePanel');
        var nodeId = node.getId();
        //refresh
        responsePanel.fireEvent('doRefresh', nodeId);
    }
});