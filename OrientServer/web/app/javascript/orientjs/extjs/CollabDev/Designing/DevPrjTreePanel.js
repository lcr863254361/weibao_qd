Ext.define('OrientTdm.CollabDev.Designing.DevPrjTreePanel', {
    extend: 'OrientTdm.CollabDev.Common.Navigation.BasePrjTreePanel',
    alias: 'widget.devPrjTreePanel',
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
        //设置树节点select事件执行200ms防抖动，即在200ms内再点击节点的话无效
        _this.mon(_this, 'select',OrientExtUtil.EventHelper.debounce.call(_this,_this._itemClickListener,200), _this);
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
                    purpose: 'expandToPrj'
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