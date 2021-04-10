Ext.define('OrientTdm.CollabDev.Processing.DevPrjTreePanel', {
    extend: 'OrientTdm.CollabDev.Common.Navigation.BasePrjTreePanel',
    alias: 'widget.processingDevPrjTreePanel',
    requires: [
        'OrientTdm.CollabDev.Common.Navigation.Model.CollabDevNodeModel'
    ],
    config: {},
    initComponent: function () {
        var _this = this;
        _this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.callParent();
        _this.mon(_this, 'select', OrientExtUtil.EventHelper.debounce.call(_this, _this._itemClickListener, 200), _this);
        _this.mon(_this, 'containerclick', _this._containerclick, _this);
    },
    createStore: function () {
        var retVal = new Ext.data.TreeStore({
            model: 'OrientTdm.CollabDev.Common.Navigation.Model.CollabDevNodeModel',
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/collabNavigation/structure.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                },
                extraParams: {
                    purpose: 'forProcessing'
                }
            },
            listeners: {
                beforeLoad: function (store, operation) {
                    var node = operation.node;
                    store.getProxy().setExtraParam("type", node.type || '');
                }
            }
        });
        return retVal;
    }
});