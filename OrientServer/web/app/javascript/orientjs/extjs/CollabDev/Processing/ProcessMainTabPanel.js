Ext.define('OrientTdm.CollabDev.Processing.ProcessMainTabPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    alias: 'widget.processMainTabPanel',
    config: {
        currentNode: null
    },
    requires: [
        'OrientTdm.CollabDev.Processing.ViewBoard.ViewBoardMainCardPanel',
        'OrientTdm.CollabDev.Processing.ResultSettings.ResultSettingsBoard',
        'OrientTdm.CollabDev.Processing.ShareData.ShareFileDashBord'
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
        _this.addEvents('switchnode');
        _this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.callParent();
        _this.mon(_this, 'switchnode', _this._fourceRefresh, _this);
    },
    _fourceRefresh: function (node) {
        var _this = this;
        _this.setCurrentNode(node);
        if (_this.items.length == 1) { //如果只有简介面板，则初始化三个看板，交付物和数据共享区页面
            //init
            _this.removeAll(true);
            var config = {
                nodeId: node.getId(),
                nodeVersion: node.get('version'),
                bmDataId: node.get('bmDataId'),
                type: node.get('type')
            };

            /**
             * 看板页面
             */
            var viewBoardMainCardPanel = Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.ViewBoardMainCardPanel', config);

            /**
             * 交付物页面
             */

            var resultSettingsBoard = Ext.create('OrientTdm.CollabDev.Processing.ResultSettings.ResultSettingsBoard', config);

            /**
             * 数据共享区页面
             */
            var shareFileDashBord = Ext.create('OrientTdm.CollabDev.Processing.ShareData.ShareFileDashBord', config);

            _this.add([viewBoardMainCardPanel, resultSettingsBoard, shareFileDashBord]);
            //bind event
            _this.items.each(function (item) {
                item.relayEvents(_this, ['switchnode']);
            });
            _this.setActiveTab(0);
        }
        //当看板tab页存在时，切换不同类型（项目、计划、任务）的节点，默认选中第一页看板页面
        // else if (_this.items.length == 3) {
        //     var item = _this.items.get(0);
        //     if (item.xtype == 'viewBoardMainCardPanel') {
        //         _this.setActiveTab(0);
        //     }
        // }
    },
    _tabchange: function (tabPanel, newItem) {
        if (newItem.needRefresh) {
            newItem.setNeedRefresh(false);
            newItem.fireEvent('refreshByNewNode');
        }
    }
});