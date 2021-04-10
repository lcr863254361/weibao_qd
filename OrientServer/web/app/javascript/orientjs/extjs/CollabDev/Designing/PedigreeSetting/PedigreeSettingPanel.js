/**
 *
 */
Ext.define('OrientTdm.CollabDev.Designing.PedigreeSetting.PedigreeSettingPanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.pedigreeSettingPanel',
    config: {},
    requires: [
        'OrientTdm.CollabDev.Common.Navigation.DevPrjTreeBypurpose',
        'OrientTdm.CollabDev.Common.Content.Pedigree.PedigreeComponent'
    ],
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            iconCls: 'icon-collabDev-datapedigreeSetting',
            title: '3.数据谱系设置',
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
                    _itemClickListener: _this._itemClickListener
                }, {
                    xtype: 'orientPanel',
                    itemId: 'responsePanel',
                    region: 'center',
                    layout: 'card',
                    padding: '0 0 0 5',
                    items: [
                        {
                            title: '谱系设置介绍'
                        }
                    ]
                }
            ]);
        } else
            _this.down('devPrjTreeBypurpose').reloadByNewNode(_this.getNodeId(), _this.getNodeName(), _this.getNodeVersion());
    },
    _itemClickListener: function (tree, node) {
        var _this = this;
        var mainPanel = _this.up('pedigreeSettingPanel');
        var responsePanel = mainPanel.down('#responsePanel');
        var nodeId = node.getId();
        //var nodeVersion = node.get('version');
        var nodeType = node.get('type');
        var layout = responsePanel.getLayout();
        if (TDM_SERVER_CONFIG.NODE_TYPE_PRJ === nodeType) {
            layout.setActiveItem(0);
        } else {
            //switch to pedigree component panel
            var childrenSize = responsePanel.items.length;
            if (childrenSize == 1) {
                //add new one
                var pedigreeComponent = Ext.create('OrientTdm.CollabDev.Common.Content.Pedigree.PedigreeComponent', {
                    startNodeId: nodeId,
                    editAble: true,
                    statusAble: false,
                    isAsync: true,
                    isShowColor: false
                });
                responsePanel.add(pedigreeComponent);
            } else {
                //refresh
                var pedigreeComponent = responsePanel.items.getAt(1);
                pedigreeComponent.fireEvent('doRefresh', nodeId);
            }
            layout.setActiveItem(1);
        }
    }
});