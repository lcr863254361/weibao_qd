/**
 *由于relayEvent需要容器在实例话的基础上方能生效，故tab面板采用非延迟加载，故面板采用代理模式，初始化时采用空白面板站位，在首次激活面板时加载真实内容
 */
Ext.define('OrientTdm.CollabDev.Designing.AuditSetting.AuditSettingsPanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.auditSettingsPanel',
    config: {},
    requires: [
        'OrientTdm.CollabDev.Common.Navigation.DevPrjTreeBypurpose',
        'OrientTdm.CollabDev.Designing.AuditSetting.AuditSettingsGrid'
    ],
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            iconCls: 'icon-collabDev-auditSetting',
            title: '5.审批设置',
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
                    xtype: 'auditSettingsGrid',
                    itemId: 'responsePanel',
                    region: 'center',
                    nodeId: _this.getNodeId(),
                    border: false
                }
            ]);
        } else
            _this.down('devPrjTreeBypurpose').reloadByNewNode(_this.getNodeId(), _this.getNodeName(), _this.getNodeVersion());
    },
    _itemClickListener: function (tree, node) {
        var _this = this;
        var mainPanel = _this.up('auditSettingsPanel');
        var responsePanel = mainPanel.down('#responsePanel');
        var nodeId = node.getId();
        //refresh
        responsePanel.fireEvent('doRefresh', nodeId);
    }
});