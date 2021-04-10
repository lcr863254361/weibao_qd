/**
 * Created by enjoy on 2016/4/30 0030.
 */
Ext.define('OrientTdm.SysMgr.FileGroup.FileTypeGroupTree', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.fileTypeGroupTree',
    requires: [
        "OrientTdm.SysMgr.FileGroup.Model.FileTypeGroupExtModel"
    ],
    beforeInitComponent: Ext.emptyFn,
    afterInitComponent: Ext.emptyFn,
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            displayField: "groupName",
            hideHeaders: true,
            plugins: [{
                ptype: 'cellediting',
                pluginId: 'cellEditPlugin',
                listeners: {
                    beforeedit: function (editor, e) {
                        if (e.record.get("id") < 0) {
                            OrientExtUtil.Common.err(OrientLocal.prompt.error, "系统分组，无法修改");
                            return false;
                        }
                    },
                    edit: function (editor, e) {
                        if (e.record.dirty) {
                            e.record.save({
                                success: function (record, opt) {
                                    opt.records[0].commit();
                                },
                                failure: function (e, op) {
                                    op.records[0].reject();
                                    OrientExtUtil.Common.err(OrientLocal.prompt.error, op.error);
                                }
                            })
                        }
                    }
                }
            }],
            columns: [{
                xtype: 'treecolumn',
                dataIndex: 'groupName',
                flex: 1,
                field: {allowBlankfalse: false}
            }]
        });
        me.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'select', me.selectItem, me);
    },
    createToolBarItems: function () {
        var me = this;
        var retVal = [{
            xtype: 'trigger',
            triggerCls: 'x-form-clear-trigger',
            onTriggerClick: function () {
                this.setValue('');
                me.clearFilter();
            },
            emptyText: '快速搜索',
            width: 110,
            enableKeyEvents: true,
            listeners: {
                keyup: function (field, e) {
                    if (Ext.EventObject.ESC == e.getKey()) {
                        field.onTriggerClick();
                    } else {
                        me.filterByText(this.getRawValue(), "groupName");
                    }
                }
            }

        }, {
            iconCls: 'icon-create',
            text: '新增',
            itemId: 'create',
            scope: this,
            handler: this.onCreateClick
        }, {
            iconCls: 'icon-update',
            text: '修改',
            itemId: 'update',
            scope: this,
            handler: this.onUpdateClick
        }, {
            iconCls: 'icon-delete',
            text: '删除',
            disabled: false,
            itemId: 'delete',
            scope: this,
            handler: this.onDeleteClick
        }];
        return retVal;
    },
    createStore: function () {
        var retVal = new Ext.data.TreeStore({
            model: 'OrientTdm.SysMgr.FileGroup.Model.FileTypeGroupExtModel'
        });
        return retVal;
    },
    selectItem: function (tree, node) {
        var nodeId = node.getId();
        if (this.ownerCt.centerPanel) {
            this.ownerCt.centerPanel.fireEvent("filterByBelongGroupId", nodeId);
        }
    },
    onCreateClick: function () {
        var me = this;
        Ext.MessageBox.prompt('分组名称', '请输入分组名称:', function (btn, groupName) {
            if (!Ext.isEmpty(groupName)) {
                var createNode = Ext.create(me.getStore().model, {
                    groupName: groupName
                });
                createNode.save({
                    success: function () {
                        me.fireEvent("refreshTree",false);
                    },
                    failure: function (e, op) {
                        OrientExtUtil.Common.err(OrientLocal.prompt.error, op.error);
                    }
                });
            } else {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, "请输入分组名称");
            }
        });
    },
    onUpdateClick: function () {
        var me = this;
        var cellEditPlugin = this.getPlugin("cellEditPlugin");
        if (cellEditPlugin) {
            var records = me.getSelectionModel().getSelection();
            if (records.length > 0) {
                var selectedRecord = records[0];
                cellEditPlugin.startEdit(selectedRecord, 0);
            } else {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.atleastSelectOne);
            }
        }
    },
    onDeleteClick: function () {
        var me = this;
        var records = me.getSelectionModel().getSelection();
        if (records.length > 0) {
            var record = records[0];
            if (record.get("id") < 0) {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, "系统分组，无法修改");
                return;
            } else {
                Ext.Msg.confirm(OrientLocal.prompt.info, OrientLocal.prompt.deleteConfirm, function (btn) {
                    if (btn == 'yes') {
                        record.remove();
                        me.store.sync();
                        me.view.select(0);
                        me.view.focus(false);
                    }
                }, me);
            }
        }
    }
});