Ext.define('OrientTdm.CollabDev.Common.Navigation.NavigationPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.navigationPanel',
    config: {},
    requires: [
        'OrientTdm.CollabDev.Designing.DevPrjTreePanel',
        'OrientTdm.CollabDev.Processing.DevPrjTreePanel'
    ],
    initComponent: function () {
        var _this = this;
        Ext.apply(_this, {
            title: '项目导航',
            displayField: 'name',
            lbar: _this.createLeftBar(),
            layout: 'card',
            items: [
                {
                    xtype: 'processingDevPrjTreePanel'
                },
                {
                    xtype: 'devPrjTreePanel'
                }
            ]
        });
        _this.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.callParent();
    },
    createLeftBar: function () {
        var _this = this;
        var lbar = [{
            xtype: 'toolbar',
            width: 60,
            dock: 'left',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [
                {
                    xtype: 'button',
                    toggleHandler: Ext.Function.createInterceptor(_this._switchMasterStatus, _this._doBtnControl, _this),
                    scope: _this,
                    itemId: 'settingButton',
                    enableToggle: true,
                    cls: 'icon-collabDev-settings',
                    text: '',
                    tooltip: '设置'
                },
                {
                    xtype: 'button',
                    enableToggle: true,
                    toggleHandler: Ext.Function.createInterceptor(_this._focusPrj, _this._doBtnControl, _this),
                    scope: _this,
                    cls: 'icon-collabDev-focus',
                    text: '',
                    tooltip: '聚焦'
                },
                {
                    xtype: 'button',
                    enableToggle: true,
                    toggleHandler: Ext.Function.createInterceptor(_this._hiddenDonePrjs, _this._doBtnControl, _this),
                    scope: _this,
                    cls: 'icon-collabDev-finished',
                    text: '',
                    tooltip: '隐藏已经完成的项目'
                }, {
                    xtype: 'button',
                    scope: _this,
                    cls: 'icon-refresh',
                    text: '',
                    tooltip: '刷新',
                    handler: _this._doRefresh
                }, {
                    xtype: 'tbspacer'
                },{
                    xtype: 'buttongroup',
                    hidden: true,
                    itemId: 'structOperationGroup',
                    title: '操作',
                    columns: 1,
                    items: [
                        {
                            xtype: 'button',
                            iconCls: 'icon-collabDev-add',
                            text: '',
                            tooltip: '新增',
                            handler: _this._doCreate,
                            scope: _this
                        }, {
                            xtype: 'tbspacer'
                        },
                        {
                            xtype: 'button',
                            iconCls: 'icon-collabDev-update',
                            text: '',
                            tooltip: '修改',
                            handler: Ext.Function.createInterceptor(_this._doUpdate, _this._rootCheck, _this),
                            scope: _this
                        }, {
                            xtype: 'tbspacer'
                        },
                        {
                            xtype: 'button',
                            iconCls: 'icon-collabDev-delete',
                            text: '',
                            tooltip: '删除',
                            handler: Ext.Function.createInterceptor(_this._doDelete, _this._rootCheck, _this),
                            scope: _this
                        }, {
                            xtype: 'tbspacer'
                        },
                        {
                            xtype: 'button',
                            iconCls: 'icon-collabDev-copy',
                            text: '',
                            tooltip: '复制'
                        }, {
                            xtype: 'tbspacer'
                        },
                        {
                            xtype: 'button',
                            iconCls: 'icon-collabDev-paste',
                            text: '',
                            tooltip: '粘贴'
                        }, {
                            xtype: 'tbspacer'
                        },
                        {
                            xtype: 'button',
                            iconCls: 'icon-collabDev-import',
                            text: '',
                            tooltip: '导出'
                        }, {
                            xtype: 'tbspacer'
                        },
                        {
                            xtype: 'button',
                            iconCls: 'icon-collabDev-export',
                            text: '',
                            tooltip: '导入'
                        }
                    ]
                }
            ]
        }];
        return lbar;
    },
    _switchMasterStatus: function (button, state) {
        var _this = this;
        var centPanel = _this.up('collabDevDashbord').down('collabDevCenterPanel');
        _this.switchStage();
        centPanel.switchStage();
    },
    _focusPrj: function (button, state) {

    },
    _hiddenDonePrjs: function (button, state) {

    },
    _doBtnControl: function (button, state) {
        var itemId = button.getItemId();
        var lbar = button.ownerCt;
        var brotherItems = lbar.query('button[enableToggle=true]');
        Ext.each(brotherItems, function (loopItem) {
            var loopItemId = loopItem.getItemId();
            if (loopItemId != itemId && state) {
                loopItem.toggle(!state, true);
            }
        });
        var btnGroup = lbar.down('#structOperationGroup');
        if ('settingButton' == itemId && btnGroup) {
            if (state === false) {
                btnGroup.hide();
            } else {
                btnGroup.show();
            }
        } else {
            btnGroup.hide();
        }
    },
    _doCreate: function () {
        var _this = this;
        var currentNode = _this.getCurrentNode();
        var type = currentNode.getData().type;
        if ('dir' === type) {
            //open create project panel
            CollabDevModelCRUDHelper.createByModelName.call(_this.getCurrentTree(), TDM_SERVER_CONFIG.PROJECT, currentNode);
        } else if (Ext.isEmpty(type)) {
            //open create dir panel
            CollabDevModelCRUDHelper.createByModelName.call(_this.getCurrentTree(), TDM_SERVER_CONFIG.DIRECTORY, currentNode);
        } else {
            OrientExtUtil.Common.tip('提示', '请在WBS分解页面中继续添加子节点');
        }
    },
    _doUpdate: function () {
        var _this = this;
        var currentNode = _this.getCurrentNode();
        var type = currentNode.getData().type;
        var modelName = CollabDevModelCRUDHelper.getModelNameByType(type);
        CollabDevModelCRUDHelper.updateByModelNameNode.call(_this.getCurrentTree(), modelName, currentNode);
    },
    _doDelete: function () {
        var _this = this;
        var currentNode = _this.getCurrentNode();
        OrientExtUtil.Common.confirm('警告', '删除操作不可逆，请确认是否删除？', function (btn) {
            if (btn == 'yes') {
                var type = currentNode.getData().type;
                var modelName = CollabDevModelCRUDHelper.getModelNameByType(type);
                CollabDevModelCRUDHelper.deleteByModelNameNode.call(_this.getCurrentTree(), modelName, currentNode);
            }
        });

    },
    _rootCheck: function () {
        var _this = this;
        var currentNode = _this.getCurrentNode();
        if (currentNode.isRoot()) {
            OrientExtUtil.Common.tip('提示', '请选中需要操作的节点');
            return false;
        } else
            return true;
    },
    _doRefresh: function () {
        var activeItem = this.getCurrentTree();
        activeItem.doRefresh();
    },
    getCurrentNode: function () {
        var activeItem = this.getCurrentTree();
        var currentNode = OrientExtUtil.TreeHelper.getSelectNodes(activeItem)[0];
        return currentNode;
    },
    getCurrentTree: function () {
        var _this = this;
        var layout = _this.getLayout();
        var activeItem = layout.getActiveItem();
        return activeItem;

    },
    switchStage: function () {
        var _this = this;
        var layout = this.getLayout();
        var activeItem = layout.getActiveItem();
        var index = _this.items.indexOf(activeItem);
        layout.setActiveItem(index ^ 1);
    }
});