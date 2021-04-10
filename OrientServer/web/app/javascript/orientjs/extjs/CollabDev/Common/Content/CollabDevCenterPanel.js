Ext.define('OrientTdm.CollabDev.Common.Content.CollabDevCenterPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.collabDevCenterPanel',
    requires: [
        'OrientTdm.CollabDev.Designing.CollabDevMainPanel',
        'OrientTdm.CollabDev.Processing.CollabDevMainPanel'
    ],
    initComponent: function () {
        var _this = this;
        Ext.apply(_this, {
            layout: 'card',
            items: [
                {
                    xtype: 'processingCollabDevMainPanel'
                    //title: '运行面板'
                },
                {
                    xtype: 'designingCollabDevMainPanel'
                }
            ]
        });
        this.addEvents(
            /**
             * @event switchNode
             * Fires when the project tree click a diffrent node
             * @param {Ext.data.NodeInterface} clickd node
             */
            'switchnode'
        );
        _this.callParent(arguments);
    },
    switchStage: function () {
        var _this = this;
        var layout = this.getLayout();
        var activeItem = layout.getActiveItem();
        var index = _this.items.indexOf(activeItem);
        layout.setActiveItem(index ^ 1);
    },
    initEvents: function () {
        var _this = this;
        _this.callParent();
        _this.mon(_this, 'switchnode', _this._switchNode, _this);
    },
    _switchNode: function (node) {
        //get current active panel and fire refresh event
        var layout = this.getLayout();
        var activeItem = layout.getActiveItem();
        activeItem.fireEvent('switchnode', node);
    }
});