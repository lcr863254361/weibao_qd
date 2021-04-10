/**
 * 离线数据左侧导航树
 */
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.PVMData.Panel.PVMNavigationTree', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.pvmNavigationTree',
    loadMask: true,
    requires: [
        'OrientTdm.Common.Extend.Form.Selector.ChooseModelPanel',
        'OrientTdm.BackgroundMgr.PVMTemplate.PVMTemplateDashBord',
        'OrientTdm.Collab.Data.PVMData.Common.PVMCombineDashBord',
        'OrientTdm.BackgroundMgr.PVMHtml.PVMHtmlDashBord',
        'OrientTdm.BackgroundMgr.PVMMulTemplate.MulPVMTmpDashBoard'
    ],
    config: {
        nodeId: null,
        localMode: false,
        localData: null,
        hisTaskDetail: null
    },
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            afterInitComponent: function () {
                this.viewConfig.listeners.refresh = function () {
                    //去除默认的选中事件
                };
            },
            lbar: me.createLeftBar()
        });
        me.callParent(arguments);
        me.addEvents('reconfigPVMData');
    },
    initEvents: function () {
        var me = this;
        me.mon(me, 'reconfigPVMData', me.reconfigPVMData, me);
        me.callParent();
    },
    createStore: function () {
        var me = this;
        var retVal;
        retVal = new Ext.data.TreeStore({
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: serviceName + '/CheckModel/getByPid.rdm',
                extraParams: {
                    nodeId: me.nodeId
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                }
            },
            root: {
                text: 'root',
                id: '-1',
                expanded: true
            },
            listeners: {
                load: function (store, record) {
                },
                scope: me
            }
        });

        return retVal;
    },
    createToolBarItems: function () {
        var me = this;
        var retVal = [{
            xtype: 'tbspacer'
        }, {
            xtype: 'trigger',
            width: 220,
            style: {
                margin: '0 0 0 22'
            },
            triggerCls: 'x-form-clear-trigger',
            onTriggerClick: function () {
                this.setValue('');
                me.clearFilter();
            },
            emptyText: '快速搜索',
            enableKeyEvents: true,
            listeners: {
                keyup: function (field, e) {
                    if (Ext.EventObject.ESC == e.getKey()) {
                        field.onTriggerClick();
                    } else {
                        me.filterByText(this.getRawValue(), "text");
                    }
                }
            }
        }];

        return retVal;
    },
    createFooBar: function () {
        return null;
    },
    createLeftBar: function () {
        var me = this;

        var retVal = [];
        retVal.push(
            {
                xtype: 'buttongroup',
                title: '新增',
                columns: 1,
                items: [
                    {
                        iconCls: 'icon-collabDev-model',
                        tooltip: '从模型新增',
                        itemId: 'createByModel',
                        scope: me,
                        handler: me._addModel
                    },
                    {
                        iconCls: 'icon-collabDev-simpleTemplate',
                        tooltip: '从模板新增',
                        itemId: 'createByTemplate',
                        scope: me,
                        handler: me._addByTemplate
                    },

                    {
                        iconCls: 'icon-collabDev-complexTemplate',
                        tooltip: '从复合模板新增',
                        itemId: 'createByMulTemplate',
                        scope: me,
                        handler: me._addByMulTemplate
                    },
                    {
                        iconCls: 'icon-collabDev-html',
                        tooltip: '从html新增',
                        itemId: 'createByHtml',
                        scope: me,
                        handler: me._addByHtmlTemplate
                    }
                ]
            },
            {
                xtype: 'buttongroup',
                title: '其他',
                columns: 1,
                items: [
                    {
                        iconCls: 'icon-collabDev-save',
                        tooltip: '保存编制',
                        itemId: 'saveEdit',
                        scope: me,
                        disabled: false,
                        handler: function () {
                            me._saveStatus();
                        }
                    },
                    {
                        iconCls: 'icon-collabDev-update',
                        tooltip: '重新编制',
                        itemId: 'reEdit',
                        scope: me,
                        disabled: false,
                        handler: function () {
                            me._restartSaveStatus();
                        }
                    },
                    {
                        iconCls: 'icon-collabDev-delete',
                        tooltip: '删除',
                        itemId: 'delete',
                        scope: me,
                        disabled: false,
                        handler: me._removeModel
                    },
                    {
                        iconCls: 'icon-collabDev-signature',
                        tooltip: '设置签署角色',
                        itemId: 'setCheckRole',
                        scope: me,
                        disabled: false,
                        handler: me._setAssigners
                    },
                    {
                        iconCls: 'icon-collabDev-remark',
                        tooltip: '设置备注',
                        itemId: 'setRemark',
                        scope: me,
                        disabled: false,
                        handler: function () {
                            me._setRemark();
                        }
                    }
                ]
            }
        );
        return retVal;
    },
    itemClickListener: function (tree, record, item) {
        var me = this;
        var modelName = record.get('text');
        var taskCheckModelId = record.raw.realId;
        var modelId = record.raw.checkmodelid;
        var status = record.raw.checktablestatus;
        var html = record.raw.html;
        //获取ID集合
        var panelId = 'status-' + status + '-' + taskCheckModelId;
        var tabPanel = me.up('resultSettingsPVMDataPanel').centerPanelComponent;
        var sonPanel = tabPanel.child('panel[itemId=' + panelId + ']');
        if (!sonPanel) {
            sonPanel = Ext.create('OrientTdm.CollabDev.Designing.ResultSettings.PVMData.Panel.PVMDataTabPanel', {
                itemId: panelId,
                taskCheckModelId: taskCheckModelId,
                status: status,
                title: modelName,
                modelId: modelId,
                rawData: record.raw,
                iconCls: record.get('iconCls')
            });
            tabPanel.add(sonPanel);
        } else {
            if (tabPanel.activeTab.itemId == panelId) {
                sonPanel.fireEvent('activate', sonPanel);
            }
        }
        tabPanel.setActiveTab(sonPanel);
    },
    _addModel: function () {
        var me = this;
        //弹出选择模型window
        var chooseModelPanel = Ext.create('OrientTdm.Common.Extend.Form.Selector.ChooseModelPanel', {
            multiSelect: false,
            selectedValue: '',
            containsView: false,
            excludeSchemaId: '240',//过滤掉协同模型
            afterInitComponent: function () {
                this.viewConfig.listeners.refresh = function () {
                    //去除默认的选中事件
                };
            },
            saveAction: function (saveData, callBack) {
                var flag = me._checkCanSave(saveData);
                if (flag == true) {
                    me._doCreate(saveData);
                    if (callBack) {
                        callBack.call(this);
                    }
                }
            }
        });
        var win = Ext.create('Ext.Window', {
                plain: true,
                title: '选择模型',
                height: 600,
                width: 400,
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [
                    chooseModelPanel
                ]
            }
        );
        win.show();
    },
    _removeModel: function () {
        var me = this;
        var selectedRecords = me.getSelectionModel().getSelection();
        if (selectedRecords.length == 0) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
        } else if (me._containsInvalidRecord(selectedRecords) == true) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.onlyCanRemoveModel);
        } else {
            OrientExtUtil.Common.confirm(OrientLocal.prompt['delete'], OrientLocal.prompt.confirmDelete, function (btn) {
                if (btn == 'yes') {
                    me._doRemove(selectedRecords);
                }
            });
        }
    },
    _containsInvalidRecord: function (records) {
        var flag = false;
        Ext.each(records, function (record) {
            if (record.get('id').indexOf('status') != -1) {
                flag = true;
            }
        });
        return flag;
    },
    _checkCanSave: function (saveData) {
        var me = this;
        //是否可以保存
        var flag = false;
        if (saveData.length == 0) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
        } else {
            var modelId = saveData[0].id;
            //校验是否已经存在
            var params = {
                checkmodelid: modelId,
                nodeId: me.nodeId
            };
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/canAdd.rdm', params, false, function (resp) {
                var retData = resp.decodedData.results;
                if (retData == 'true') {
                    flag = true;
                }
            });
        }
        return flag;
    },
    _canAddFromTemplates: function (templateIds) {
        var me = this;
        var flag = false;
        var params = {
            templateIds: templateIds,
            nodeId: me.nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/canAddFromTemplate.rdm', params, false, function (resp) {
            var retData = resp.decodedData.results;
            if (retData == true) {
                flag = true;
            }
        });
        return flag;
    },
    _canAddFromMulTemplates: function (templateIds) {
        var me = this;
        var flag = false;
        var params = {
            mulTemplateIds: templateIds,
            nodeId: me.nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/canAddFromMulTemplate.rdm', params, false, function (resp) {
            var retData = resp.decodedData.results;
            if (retData == true) {
                flag = true;
            }
        });
        return flag;
    },
    _canAddFromHtmlTemplates: function (htmlTemplateIds) {
        var me = this;
        var flag = false;
        var params = {
            htmlTemplateIds: htmlTemplateIds,
            nodeId: me.nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/canAddFromHtmlTemplate.rdm', params, false, function (resp) {
            var retData = resp.decodedData.results;
            if (retData == true) {
                flag = true;
            }
        });
        return flag;
    },
    _doCreate: function (saveData) {
        var me = this;
        var store = me.getStore();
        var modelId = saveData[0].id;
        var params = {
            checkmodelid: modelId,
            nodeId: me.nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/create.rdm', params, false, function (resp) {
            //保存成功后操作
            store.reload({
                node: me.getRootNode()
            });
        });
    },
    doSaveFromTemplate: function (templateIds, callBack) {
        var me = this;
        var store = me.getStore();
        var params = {
            templateIds: templateIds,
            nodeId: me.nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/createByTemplate.rdm', params, false, function (resp) {
            //保存成功后操作
            store.reload({
                node: me.getRootNode()
            });
            if (callBack) {
                callBack.call(me);
            }
        });
    },
    doSaveFromMulTemplate: function (mulTemplateIds, callBack) {
        var me = this;
        var store = me.getStore();
        var params = {
            mulTemplateIds: mulTemplateIds,
            nodeId: me.nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/createByMulTemplate.rdm', params, false, function (resp) {
            //保存成功后操作
            store.reload({
                node: me.getRootNode()
            });
            if (callBack) {
                callBack.call(me);
            }
        });

    },
    doSaveFromHtmlTemplate: function (htmlTemplateIds, callBack) {
        var me = this;
        var store = me.getStore();
        var params = {
            htmlTemplateIds: htmlTemplateIds,
            nodeId: me.nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/createByHtmlTemplate.rdm', params, false, function (resp) {
            //保存成功后操作
            store.reload({
                node: me.getRootNode()
            });
            if (callBack) {
                callBack.call(me);
            }
        });
    },
    _doRemove: function (records) {
        var me = this;
        var store = me.getStore();
        if (records[0].raw['checktablestatus'] > 2) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.allreadyDelived);
        } else {
            var toRemoveIds = [];
            Ext.each(records, function (record) {
                var checkModelId = record.raw.realId;
                toRemoveIds.push(checkModelId);
            });
            var params = {
                toDelIds: toRemoveIds
            };
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CheckModel/delete.rdm', params, false, function (resp) {
                //保存成功后操作
                store.reload({
                    node: me.getRootNode()
                });
                //移除右侧相关Tab面板
                Ext.each(records, function (record) {
                    var taskCheckModelId = record.raw.realId;
                    var status = record.raw.checktablestatus;
                    var panelId = 'status-' + status + '-' + taskCheckModelId;
                    var tabPanel = me.up('pvmDataDashBord').centerPanelComponent;
                    var sonPanel = tabPanel.child('panel[itemId=' + panelId + ']');
                    if (sonPanel) {
                        tabPanel.remove(sonPanel);
                    }
                });
            });
        }
    },
    _addByTemplate: function () {
        var me = this;
        //选择模板
        var win = Ext.create('Ext.Window', {
                plain: true,
                title: '选择模板',
                height: 600,
                width: 1024,
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [
                    {
                        xtype: 'pvmTemplateDashBord'
                    }
                ],
                buttons: [
                    {
                        text: '关闭',
                        iconCls: 'icon-close',
                        handler: function () {
                            win.close();
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: function () {
                            var templateGrid = win.down('pvmTemplateList');
                            //获取选中的数据集合
                            var templateIds = OrientExtUtil.GridHelper.getSelectRecordIds(templateGrid);
                            if (templateIds.length == 0) {
                                OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
                            } else {
                                //校验是否可以新增
                                var canAdd = me._canAddFromTemplates(templateIds);
                                if (canAdd == true) {
                                    me.doSaveFromTemplate(templateIds, function () {
                                        win.close();
                                    });
                                }
                            }
                        }
                    }
                ]
            }
        );
        win.show();
    },
    _addByHtmlTemplate: function () {
        var me = this;
        //选择模板
        var win = Ext.create('Ext.Window', {
                plain: true,
                title: '选择HTML模板',
                height: 600,
                width: 1024,
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [
                    {
                        xtype: 'pvmHtmlDashBord'
                    }
                ],
                buttons: [
                    {
                        text: '关闭',
                        iconCls: 'icon-close',
                        handler: function () {
                            win.close();
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: function () {
                            var templateGrid = win.down('pvmHtmlList');
                            //获取选中的数据集合
                            var templateIds = OrientExtUtil.GridHelper.getSelectRecordIds(templateGrid);
                            if (templateIds.length == 0) {
                                OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
                            } else {
                                //校验是否可以新增
                                var canAdd = me._canAddFromHtmlTemplates(templateIds);
                                if (canAdd == true) {
                                    me.doSaveFromHtmlTemplate(templateIds, function () {
                                        win.close();
                                    });
                                }
                            }
                        }
                    }
                ]
            }
        );
        win.show();
    },
    reconfigPVMData: function () {
        var me = this;
        var currentNode = me.getSelectionModel().getSelection()[0];
        me.fireEvent('itemclick', me, currentNode);
    },
    _setAssigners: function () {
        var me = this;
        var checkModel = OrientExtUtil.TreeHelper.getSelectNodes(me)[0];
        if (!checkModel) {
            OrientExtUtil.Common.info(OrientLocal.prompt.info, '请选中一个节点再设置签署角色');
            return;
        }
        var combineDashBoard = me.up('resultSettingsPVMDataPanel').down('#checkModelData').getActiveTab();
        combineDashBoard._setAssigners();
    },
    _setRemark: function () {
        var me = this;
        var checkModel = OrientExtUtil.TreeHelper.getSelectNodes(me)[0];
        if (!checkModel) {
            OrientExtUtil.Common.info(OrientLocal.prompt.info, '请选中一个节点再设置备注');
            return;
        }
        var item = Ext.create('OrientTdm.Common.Extend.Form.OrientForm', {
            successCallback: function () {
                checkModel.raw.remark = this.down('[name=remark]').getValue();
                this.up('window').close();
            },
            actionUrl: serviceName + '/CheckModel/saveRemark.rdm',
            items: [
                {
                    xtype: 'hidden',
                    name: 'id',
                    value: checkModel.getId()
                }, {
                    fieldLabel: '备注',
                    xtype: 'textarea',
                    name: 'remark',
                    allowBlank: false,
                    value: checkModel.raw.remark
                }
            ]
        });
        OrientExtUtil.WindowHelper.createWindow(item, {
            title: '设置备注',
            buttons: [
                {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        this.up('window').down('orientForm').fireEvent('saveOrientForm');
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-close',
                    handler: function () {
                        this.up('window').close();
                    }
                }
            ]
        }, 200, 100);
    },
    _saveStatus: function () {
        var me = this;
        var combineDashBoard = me.up('pvmDataDashBord').down('#checkModelData').getActiveTab();
        combineDashBoard.up('pvmDataDashBord').saveStatus(2, combineDashBoard.taskCheckModelId, combineDashBoard);
    },
    _restartSaveStatus: function () {
        var me = this;
        var combineDashBoard = me.up('pvmDataDashBord').down('#checkModelData').getActiveTab();
        combineDashBoard.up('pvmDataDashBord').saveStatus(1, combineDashBoard.taskCheckModelId, combineDashBoard);
    },

    _addByMulTemplate: function () {
        var me = this;
        //选择模板
        var win = Ext.create('Ext.Window', {
                plain: true,
                title: '选择综合模板',
                height: 600,
                width: 1024,
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [
                    {
                        xtype: 'pvmMulTmpDashBoard'
                    }
                ],
                buttons: [
                    {
                        text: '关闭',
                        iconCls: 'icon-close',
                        handler: function () {
                            win.close();
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: function () {
                            var templateGrid = win.down('mulPvmTmpList');
                            //获取选中的数据集合
                            var templateIds = OrientExtUtil.GridHelper.getSelectRecordIds(templateGrid);
                            if (templateIds.length == 0) {
                                OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
                            } else {
                                //校验是否可以新增
                                var canAdd = me._canAddFromMulTemplates(templateIds);
                                if (canAdd) {
                                    me.doSaveFromMulTemplate(templateIds, function () {
                                        win.close();
                                    });
                                }
                            }
                        }
                    }
                ]
            }
        );
        win.show();
    }
})
;