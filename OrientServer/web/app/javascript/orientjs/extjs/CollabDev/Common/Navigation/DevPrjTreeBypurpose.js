/**
 *
 */
Ext.define('OrientTdm.CollabDev.Common.Navigation.DevPrjTreeBypurpose', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.devPrjTreeBypurpose',
    config: {
        prjNodeId: '',
        prjNodeName: '',
        prjNodeVersion: '',
        purpose: 'expandToPlan'
    },
    requires: [],
    initComponent: function () {
        var _this = this;
        Ext.apply(_this, {
            displayField: 'name'
        });
        _this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.callParent();
        //子类实现_itemClickListener方法
        _this.mon(_this, 'select', _this._itemClickListener, _this);
    },
    createToolBarItems: function () {
        var _this = this;
        var retVal = [
            {
                xtype: 'trigger',
                width: 170,
                triggerCls: 'x-form-clear-trigger',
                onTriggerClick: function () {
                    this.setValue('');
                    _this.clearFilter();
                },
                emptyText: '快速搜索',
                enableKeyEvents: true,
                listeners: {
                    keyup: function (field, e) {
                        if (Ext.EventObject.ESC == e.getKey()) {
                            field.onTriggerClick();
                        } else {
                            _this.filterByText(this.getRawValue(), "name");
                        }
                    }
                }
            },
            {
                text: '',
                iconCls: 'icon-refresh',
                scope: _this,
                //刷新按钮添加鼠标防抖动保护
                handler: OrientExtUtil.EventHelper.debounce.call(_this,_this.doCustomerRefresh,200)
            }
        ];
        return retVal;
    },
    createStore: function () {
        var _this = this;
        var retVal = new Ext.data.TreeStore({
            model: 'OrientTdm.CollabDev.Common.Navigation.Model.CollabDevNodeModel',
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/collabNavigation/specialStructure.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                },
                extraParams: {
                    purpose: _this.getPurpose(),
                    startNodeId: _this.getPrjNodeId(),
                    startNodeVersion: _this.getPrjNodeVersion()
                }
            },
            listeners: {}
        });
        return retVal;
    },
    reloadByNewNode: function (prjNodeId, prjNodeName, prjNodeVersion) {
        var _this = this;
        _this.setPrjNodeId(prjNodeId);
        _this.setPrjNodeName(prjNodeName);
        _this.setPrjNodeVersion(prjNodeVersion);
        var proxy = _this.getStore().getProxy();
        proxy.setExtraParam("startNodeId", prjNodeId);
        proxy.setExtraParam("startNodeVersion", prjNodeVersion);
        _this.fireEvent('refreshTree', false);
    },
    doCustomerRefresh: function () {
        var _this = this;
        var selectedNode = OrientExtUtil.TreeHelper.getSelectNodes(_this)[0];
        _this.doRefresh(selectedNode);
    }
});