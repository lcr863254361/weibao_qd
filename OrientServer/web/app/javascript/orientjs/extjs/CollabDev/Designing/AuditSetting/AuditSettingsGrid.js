Ext.define('OrientTdm.CollabDev.Designing.AuditSetting.AuditSettingsGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.auditSettingsGrid',
    requires: [
        'OrientTdm.CollabDev.Designing.AuditSetting.Model.CBApprovalExtModel',
        'OrientTdm.CollabDev.Designing.AuditSetting.Common.AuditProcessDefinitionGrid',
        'OrientTdm.CollabDev.Designing.AuditSetting.AuditDetailSettingGrid'
    ],
    config: {
        nodeId: ''
    },
    usePage: false,
    beforeInitComponent: Ext.emptyFn,
    afterInitComponent: Ext.emptyFn,
    initComponent: function () {
        var me = this;
        me.cellEditing = new Ext.grid.plugin.CellEditing({
            clicksToEdit: 2,
            listeners: {
                edit: function (editor, e) {
                    if (e.record.dirty) {
                        me.saveRecord(function () {
                            e.record.commit();
                        });
                    }
                },
                validateedit: function (editor, e) {

                }
            }
        });
        Ext.apply(me, {
            plugins: [me.cellEditing]
        });
        me.callParent(arguments);
        this.addEvents('doRefresh');
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'doRefresh', me._doRefresh, me);
    },
    createToolBarItems: function () {
        var me = this;
        var retVal = [
            {
                iconCls: 'icon-CollabDev-add',
                text: '绑定',
                itemId: 'create',
                scope: me,
                handler: me._createBind
            }, {
                iconCls: 'icon-CollabDev-delete',
                text: '解绑',
                itemId: 'delete',
                scope: me,
                handler: me.onDeleteClick
            }
        ];
        return retVal;
    },
    createColumns: function () {
        var _this = this;
        return [
            {
                header: '模板名称',
                flex: 1,
                sortable: true,
                dataIndex: 'pdName'
            },
            {
                header: '触发事件（双击）',
                width: 150,
                sortable: true,
                dataIndex: 'triggerType',
                editor: {
                    xtype: 'combobox',
                    allowBlank: false,
                    store: {
                        xtype: 'store',
                        fields: ['name', 'value'],
                        data: [
                            {"value": "submit", "name": "提交"},
                            {"value": "modify", "name": "修改"}
                        ]
                    },
                    queryMode: 'local',
                    displayField: 'name',
                    valueField: 'value'
                },
                renderer: function (v) {
                    var retVal = v;
                    if ('submit' === v) {
                        return '提交';
                    } else if ('modify' === v) {
                        return '修改';
                    }
                    return retVal;
                }
            },
            {
                xtype: 'actioncolumn',
                text: '详细设置',
                align: 'center',
                width: 100,
                items: [
                    {
                        iconCls: 'icon-collabDev-opinion',
                        handler: function (grid, rowIndex) {
                            var id = _this.store.getAt(rowIndex).get('id');
                            var taskSettingPanel = Ext.create('OrientTdm.CollabDev.Designing.AuditSetting.AuditDetailSettingGrid', {
                                belongAuditBind: id
                            });
                            OrientExtUtil.WindowHelper.createWindow(taskSettingPanel, {
                                title: '任务设置'
                            }, 600, 850);
                        }
                    }
                ]
            }
        ];
    },
    createStore: function () {
        var _this = this;
        var retVal = Ext.create('Ext.data.Store', {
            model: 'OrientTdm.CollabDev.Designing.AuditSetting.Model.CBApprovalExtModel',
            autoLoad: false,
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/auditSettings/list.rdm',
                    'update': serviceName + '/auditSettings/update.rdm',
                    'delete': serviceName + '/auditSettings/delete.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                },
                writer: {
                    type: 'json',
                    allowSingle: true
                },
                extraParams: {
                    nodeId: _this.getNodeId()
                }
            }
        });
        this.store = retVal;
        return retVal;
    },
    _doRefresh: function (nodeId) {
        var _this = this;
        _this.setNodeId(nodeId);
        _this.getStore().getProxy().setExtraParam("nodeId", nodeId);
        _this.fireEvent('refreshGrid');
    },
    _createBind: function () {
        var _this = this;
        var selectAuditFlowPanel = Ext.create('OrientTdm.CollabDev.Designing.AuditSetting.Common.AuditProcessDefinitionGrid', {
            nodeId: _this.nodeId,
            saveCallBack: function () {
                _this.fireEvent('refreshGrid');
            }
        });
        OrientExtUtil.WindowHelper.createWindow(selectAuditFlowPanel, {
            title: '选择审批模型'
        });
    },
    saveRecord: function (successCallback) {
        var me = this;
        me.getStore().sync(
            {
                success: successCallback
            }
        );
    }
});