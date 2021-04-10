/**
 * 协同研发主面板:card布局，第一个面板为综合查询，第二个面板为节点具体内容展示面板
 */
Ext.define('OrientTdm.CollabDev.Processing.CollabDevMainPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.processingCollabDevMainPanel',
    config: {},
    requires: [
        'OrientTdm.CollabDev.Processing.CompositeQuery.CompositeQueryMainPanel',
        'OrientTdm.CollabDev.Processing.ProcessMainTabPanel'
    ],
    initComponent: function () {
        var _this = this;

        Ext.apply(_this, {
            layout: 'card',
            items: [
                {
                    xtype: 'compositeQueryMainPanel',
                    padding: '0 0 0 0'
                },
                {
                    xtype: 'processMainTabPanel'
                }
            ]
        });

        _this.addEvents('switchnode');
        _this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.mon(_this, 'switchnode', _this._fourceRefresh, _this);
    },
    switchCardPage: function (index) {
        var _this = this;
        var layout = _this.getLayout();
        layout.setActiveItem(index);
    },
    _fourceRefresh: function (node) {
        var _this = this;
        switch (node.data.type) {
            case TDM_SERVER_CONFIG.NODE_TYPE_DIR:  //如果是文件夹节点，切换到card布局的第一个面板
                _this.switchCardPage(0);
                break;
            default:  //如果非文件夹节点，切换到card布局的第二个面板
                _this.switchCardPage(1);
                var layout = _this.getLayout();
                layout.getActiveItem().fireEvent('switchnode', node);
                break;
        }
    }
});