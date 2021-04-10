/**
 * Created by ZhangSheng on 2018/8/20 0030.
 */
Ext.define('OrientTdm.CollabDev.Processing.ShareData.ShareFileTree', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.shareFileTree',
    requires: [
        'OrientTdm.CollabDev.Processing.Model.ShareFolderPanelExtModel'
    ],
    config: {
        collabNodeId: ''
    },
    beforeInitComponent: Ext.emptyFn,
    afterInitComponent: Ext.emptyFn,
    initComponent: function () {
        var me = this;
        var leftBar = me.createLeftBar.call(me);
        Ext.apply(me, {
            title: '数据导航',
            displayField: 'name',
            hideHeaders: true,
            plugins: [{
                ptype: 'cellediting',
                pluginId: 'cellEditPlugin',
                listeners: {
                    beforeedit: function (editor, e) {
                        if (e.record.get("id") < 0) {
                            OrientExtUtil.Common.err(OrientLocal.prompt.error, "该共享文件夹无法修改");
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
            lbar: leftBar,
            columns: [{
                xtype: 'treecolumn',
                dataIndex: 'name',
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
            //width: 280,
            width: '100%',
            enableKeyEvents: true,
            listeners: {
                keyup: function (field, e) {
                    if (Ext.EventObject.ESC == e.getKey()) {
                        field.onTriggerClick();
                    } else {
                        me.filterByText(this.getRawValue(), "name");
                    }
                }
            }

        }];
        return retVal;
    },
    createStore: function () {
        var me = this;
        var retVal = new Ext.data.TreeStore({
            model: 'OrientTdm.CollabDev.Processing.Model.ShareFolderPanelExtModel',
            listeners: {
                //后台list方法传参
                beforeLoad: function (store, operation) {
                    store.getProxy().setExtraParam("collabNodeId", me.collabNodeId);
                },
                //后台create方法传参
                write: function (store) {
                    store.getProxy().setExtraParam("collabNodeId", me.collabNodeId);
                }
            }
        });
        this.store = retVal;
        return retVal
    },
    selectItem: function (tree, node) {
        var cbShareFolderId = node.getId();
        if (this.ownerCt.centerPanel) {
            this.ownerCt.centerPanel.fireEvent("filterByShareFolderId", cbShareFolderId);
        }
    },
    createLeftBar: function () {
        var me = this;
        var retVal = [];
        retVal.push(
            {
                xtype: 'buttongroup',
                itemId: 'operButtonGroup',
                title: '操作',
                columns: 1,
                items: [
                    {
                        iconCls: 'icon-collabDev-add',
                        tooltip: '新增',
                        itemId: 'create',
                        scope: me,
                        handler: me._onCreateClick
                    },
                    {
                        iconCls: 'icon-collabDev-update',
                        tooltip: '修改',
                        itemId: 'update',
                        scope: me,
                        handler: me._onUpdateClick
                    },
                    {
                        iconCls: 'icon-collabDev-delete',
                        tooltip: '删除',
                        itemId: 'delete',
                        scope: me,
                        handler: me._onDeleteClick
                    }
                ]
            });
        return retVal;
    },
    _onCreateClick: function () {
        var me = this;
        Ext.MessageBox.prompt('文件夹名称', '请输入文件夹名称:', function (btn, folderName) {
            if (btn == 'ok') {
                if (!Ext.isEmpty(folderName)) {
                    var records = me.getSelectionModel().getSelection();
                    if (records.length > 0) {
                        var selectedRecord = records[0];
                        var currId = selectedRecord.data.id;
                        var createNode = Ext.create(me.getStore().model, {
                            name: folderName,
                            pid: currId,
                            parentId: currId
                        });
                        createNode.save({
                            success: function () {
                                me.fireEvent('refreshTree', false);
                            },
                            failure: function (e, op) {
                                OrientExtUtil.Common.err(OrientLocal.prompt.error, op.error);
                            }
                        });
                    }
                    //不存在选中节点时，创建pid=0的根节点
                    else {
                        var createNode = Ext.create(me.getStore().model, {
                            name: folderName,
                            pid: '0',
                            parentId: '0'
                        });
                        createNode.save({
                            success: function () {
                                me.fireEvent("refreshTree", false);
                            },
                            failure: function (e, op) {
                                OrientExtUtil.Common.err(OrientLocal.prompt.error, op.error);
                            }
                        });
                    }
                } else {
                    OrientExtUtil.Common.tip(OrientLocal.prompt.info, '请输入文件夹名称');
                }
            }
            else if (btn == 'cancel') {
                return;
            }
        });
    },
    _onUpdateClick: function () {
        var me = this;
        var cellEditPlugin = this.getPlugin('cellEditPlugin');
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
    _onDeleteClick: function () {
        var me = this;
        var records = me.getSelectionModel().getSelection();
        if (records.length > 0) {
            var record = records[0];
            if (record.get('id') < 0) {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, "该共享文件夹无法修改");
                return;
            } else {
                Ext.Msg.confirm(OrientLocal.prompt.info, OrientLocal.prompt.deleteConfirm, function (btn) {
                    if (btn == 'yes') {
                        //先清空共享文件表格
                        if (this.ownerCt.centerPanel) {
                            var centerPanel = this.ownerCt.centerPanel;
                            centerPanel.filterByShareFolderId('0');
                        }
                        //再删除共享文件夹
                        record.remove();
                        me.store.sync();
                        me.view.select(0);
                        me.view.focus(false);
                    }
                }, me);
            }
        }
    },
    refreshShareFolderTree: function (nodeId) {
        var me = this;
        me.setCollabNodeId(nodeId);
        me.getStore().getProxy().setExtraParam('collabNodeId', me.collabNodeId);
        me.getStore().load();
        //默认选中第一个树节点
        //me.view.select(0);
        //var records = me.getSelectionModel().getSelection();
        //if (records.length > 0) {
        //    var record = records[0];
        //}
    }
});