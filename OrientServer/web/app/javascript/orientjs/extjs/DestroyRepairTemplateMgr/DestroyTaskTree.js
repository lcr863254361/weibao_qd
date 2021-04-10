Ext.define('OrientTdm.DestroyRepairTemplateMgr.DestroyTaskTree', {
    alias: 'widget.destroyTaskTree',
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    requires: [
        'OrientTdm.DestroyRepairTemplateMgr.model.DestroyTaskTreeNodeModel',
        'OrientTdm.Collab.common.util.SelectComponentField',
        'OrientTdm.Collab.ProjectMng.mainFrame.ProjectStatisticPanel'
    ],
    rootVisible: true,
    initComponent: function () {
        var me = this;
        me.rootNode = {
            text: '拆解模板',
            qtip: '拆解模板',
            dataId: '-1',
            id: '-1',
            iconCls: 'icon-function',
            icon: 'app/images/function/数据建模.png',
            type: 'root',
            expanded: true,
        };

        Ext.apply(me, {
            viewConfig: {
                listeners: {
                    itemcontextmenu: function (view, rec, node, index, e) {
                        e.stopEvent();
                        var menu = me.createMenu(rec);
                        menu.showAt(e.getXY());
                        return false;
                    }
                }
            },
            // lbar: me.createLeftBar()
        });
        me.callParent(arguments);

    },
    config: {},
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.TreeStore', {
            model: 'OrientTdm.DestroyRepairTemplateMgr.model.DestroyTaskTreeNodeModel',
            listeners: {
                beforeLoad: function (store, operation) {
                    var node = operation.node;
                    // store.getProxy().setExtraParam("functionModule", "projectMng");
                    if (node.isRoot()) {
                        // store.getProxy().setExtraParam('modelName', dir);
                        // store.getProxy().setExtraParam('dataId', '-1');
                        store.getProxy().setExtraParam('id', '-1');
                        store.getProxy().setExtraParam('type', "root");
                        store.getProxy().setExtraParam('level', '0');
                    }
                    else {
                        if (node.raw.parentNode) {
                            node.raw.parentNode = null;
                        }
                        store.getProxy().setExtraParam('modelName', Ext.encode(node.data.modelName));
                        store.getProxy().setExtraParam('id', node.data.dataId);
                        store.getProxy().setExtraParam('type', node.raw.type);
                        store.getProxy().setExtraParam('level', node.raw.level);
                    }
                }
            },
            root: me.rootNode
        });
        return retVal;
    },
    // createLeftBar: function () {
    //     var me = this;
    //
    //     var retVal = [{
    //         xtype: 'tbspacer'
    //     }, ' ', ' ', ' ', {
    //         iconCls: 'icon-update',
    //         handler: Ext.bind(me.onEditNode, me),
    //         name: 'update',
    //         tooltip: '修改',
    //         disabled: true
    //     }, {
    //         iconCls: 'icon-delete',
    //         handler: Ext.bind(me.onDeleteNode, me),
    //         name: 'delete',
    //         tooltip: '删除',
    //         disabled: true
    //     }];
    //
    //     return retVal;
    // },
    setButtonStatus: function (record) {
        var me = this;
        var lbar = this.down('toolbar[dock=left]');
        var btns = lbar.items.items;
        for (var i = 0; i < btns.length; i++) {
            btns[i].setDisabled(true);
        }
        if (record.data.modelName === TDM_SERVER_CONFIG.DESTROY_TYPE || record.data.modelName === TDM_SERVER_CONFIG.DESTROY_FLOW) {
            lbar.down('button[name=update]').setDisabled(false);
            lbar.down('button[name=createbyhand]').setDisabled(true);
            lbar.down('button[name=delete]').setDisabled(false);
        } else {
            lbar.down('button[name=createbyhand]').setDisabled(false);
        }
    },
    createMenu: function (record) {
        var me = this;

        var items = [{
            iconCls: 'icon-import',
            text: '导入',
            name: 'create',
            tooltip: '从Excel导入',
            scope: me,
            disabled: false,
            handler: me.importDestroyData
        }, {
            iconCls: 'icon-refresh',
            text: '刷新',
            scope: me,
            handler: me.doRefresh
        }];

        if (record.data.id == -1) {

            items.push({
                text: '新增拆解模板',
                iconCls: 'icon-add',
                handler: Ext.bind(me.onCreateNode, me, [{toCreateModelName: TDM_SERVER_CONFIG.DESTROY_TYPE}])
            });
        } else {
            items.push({
                iconCls: 'icon-delete',
                handler: Ext.bind(me.onDeleteNode, me),
                text: '删除'
            });
        }
        var menu = Ext.create('Ext.menu.Menu',
            {
                items: items
            });

        return menu;
    },
    createToolBarItems: function () {
        var me = this;

        var retVal = [
            {
                xtype: 'tbspacer'
            },
            {
                xtype: 'trigger',
                width: 200,
                style: {
                    margin: '0 0 0 0'
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
                            me.filterByText(this.getRawValue(), 'text');
                        }
                    }
                }
            }, ' ', {
                iconCls: 'icon-refresh',
                text: '刷新',
                itemId: 'refresh',
                scope: this,
                handler: this.doRefresh
            }];

        return retVal;
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'select', me.itemClickListener, me);
        me.mon(me, 'containerclick', me.containerclick, me);
    },
    itemClickListener: function (tree, record, item) {
        var params = {
            modelId: record.data.modelId,
            dataId: record.data.dataId
        };
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var me = this;
        // me.setButtonStatus(record);
        if (Ext.isEmpty(record.data.modelName)) {
            record.data.modelName = TDM_SERVER_CONFIG.DESTROY_TYPE;
            params.modelId = OrientExtUtil.ModelHelper.getModelId(record.data.modelName, schemaId);
            params.dataId = "";
        } else if ((record.data.modelName === TDM_SERVER_CONFIG.DESTROY_TYPE) && record.data.id == -1) {
            params.modelId = OrientExtUtil.ModelHelper.getModelId(record.data.modelName, schemaId);
            params.dataId = "";
        }
        if (record.data.modelName == TDM_SERVER_CONFIG.DESTROY_TYPE || record.data.modelName == TDM_SERVER_CONFIG.DESTROY_FLOW) {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelData/getGridModelDescAndData.rdm', params, false, function (response) {
                var modelDesc = response.decodedData.results.orientModelDesc;
                var modelData = response.decodedData.results.modelData;

                me._filterDisplayData(modelData, record.data.modelId);
                var centerPanel = me.ownerCt.centerPanel;
                centerPanel.removeAll(true);
                //如果节点是文件夹则去掉基本信息tab页，换成简介页
                var inforForm = null;
                if (modelDesc.dbName.indexOf(TDM_SERVER_CONFIG.DESTROY_TYPE) >= 0 && record.data.id == -1) {
                    inforForm = Ext.create('OrientTdm.DestroyRepairTemplateMgr.DestroyTypePanel.DestroyTypePanel', {
                        title: '项目统计信息',
                        dirId: record.data.dataId
                    });
                } else {
                    inforForm = Ext.create('OrientTdm.Common.Extend.Form.OrientDetailModelForm', {
                        iconCls: 'icon-baseinfo',
                        title: '基本信息',
                        bindModelName: modelDesc.dbName,
                        modelDesc: modelDesc,
                        originalData: modelData,
                        afterInitForm: me._specialHandler,
                        listeners: {
                            activate: function (ct) {
                                centerPanel.activeTabName = ct.title;
                            }
                        },
                        dockedItems: [{
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [{
                                itemId: 'modify',
                                text: '修改',
                                iconCls: 'icon-update',
                                handler: me.onEditNodeInner,
                                scope: me
                            }]
                        }]
                    });
                }

                var toActivePanel = inforForm;
                var itemsToAdd = [];
                itemsToAdd.push(inforForm);

                var extraInfo = {};
                extraInfo.projectPlannedStartDate = modelData["C_TASK_START_TIME_" + record.data.modelId];
                extraInfo.projectPlannedEndDate = modelData["C_TASK_END_TIME_" + record.data.modelId];
                if ((record.data.modelName === TDM_SERVER_CONFIG.DESTROY_TYPE) && record.data.id != -1) {
                    var ganttPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                        title: "甘特图",
                        iconCls: "icon-gantt",
                        layout: 'border',
                        region: 'center',
                        treeNodeData: record.data,
                        modelName: record.data.modelName,
                        dataId: record.data.dataId,
                        modelId: record.data.modelId,
                        extraInfo: extraInfo,
                        height: centerPanel.getHeight() - 28,
                        width: centerPanel.getWidth(),
                        listeners: {
                            afterrender: function (ct) {

                            },
                            activate: function (ct) {
                                centerPanel.activeTabName = ct.title;
                            }
                        }
                    });
                    itemsToAdd.push(ganttPanel);
                }
                var tabPanel = Ext.create("OrientTdm.DestroyRepairTemplateMgr.DestroyCenterPanel", {
                    region: 'center',
                    layout: 'border',
                    padding: '0 0 0 0',
                    activeItem: 0,
                    deferredRender: true,
                    items: itemsToAdd
                });

                centerPanel.add(tabPanel);
                tabPanel.setActiveTab(toActivePanel);
            });
        }
    },
    onCreateNode: function (param, fromRoot) {
        var selection = this.getSelectionModel().getSelection();
        var parentNode = fromRoot == true ? this.getRootNode() : selection[0];
        var curNodeData;
        if (selection.length == 0) {
            curNodeData = this.rootNode;
        } else {
            curNodeData = selection[0].data;
        }

        // if (curNodeData.modelName === 'CB_PROJECT' || (curNodeData.id == 'root' && param.toCreateModelName == 'CB_PROJECT')) {
        //     Ext.Msg.alert("提示", '项目节点只能建立在文件夹下');
        //     return;
        // }

        var me = this;
        var params = {};
        // OrientExtUtil.AjaxHelper.doRequest(serviceName + '/projectTree/nodeModelInfo.rdm', {modelName: param.toCreateModelName}, false, function (response) {
        //     params.modelId = response.decodedData.results.modelId;
        //     params.schemaId = response.decodedData.results.schemaId;
        // });
        params.schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        params.modelId = OrientExtUtil.ModelHelper.getModelId(param.toCreateModelName, params.schemaId);

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelData/getGridModelDesc.rdm', params, false, function (response) {
            var modelDesc = response.decodedData.results.orientModelDesc;
            //默认值
            var originalData = me.createDefaultData(parentNode, params.schemaId);
            var createForm = Ext.create('OrientTdm.Common.Extend.Form.OrientAddModelForm', {

                buttonAlign: 'center',
                originalData: originalData,
                buttons: [
                    {
                        itemId: 'save',
                        text: '保存',
                        scope: me,
                        iconCls: 'icon-save',
                        handler: function (btn) {
                            var me = this;
                            btn.up('form').fireEvent('saveOrientForm', {
                                modelId: params.modelId
                            });
                        }
                    },
                    {
                        itemId: 'back',
                        text: '取消',
                        scope: me,
                        iconCls: 'icon-close',
                        handler: function () {
                            win.close();
                        }
                    }
                ],
                successCallback: function () {
                    me._specialAfterSave(this, arguments);
                    win.close();
                    me.refreshNode(curNodeData.id, false);
                },
                bindModelName: modelDesc.dbName,
                actionUrl: serviceName + '/destroyRepairMgr/saveDestroyTypeData.rdm',
                modelDesc: modelDesc,
                afterInitForm: me._specialHandler
            });

            var win = new Ext.Window({
                title: '新增' + modelDesc.text,
                width: 0.4 * globalWidth,
                height: 0.8 * globalHeight,
                layout: 'fit',
                items: [
                    createForm
                ],
                listeners: {
                    'beforeshow': function () {
                        var items = createForm.items.items[0].items.length;
                        if (items < 3) {
                            win.setHeight(items * 210);
                        }
                        else if (items >= 3 && items <= 7) {
                            win.setHeight(items * 90);
                        }
                        else {
                            win.setHeight(items * 90);
                        }
                    }
                }
            });
            win.show();
        });
    },

    onEditNodeInner: function (button) {
        var me = this;
        //当前节点信息
        var selection = me.getSelectionModel().getSelection();
        var curNodeData = selection[0].data;
        //旧的表单相关信息
        var form = button.up('form');
        var modelDesc = form.modelDesc;
        var originalData = form.originalData;
        //响应面板
        var centerPanel;
        var tabPanel;
        if (modelDesc.dbName.indexOf('CB_TASK') != -1) {
            //任务面板重新获取
            centerPanel = me.up('planTaskBreakMainPanel').down('#taskRespRegion');
        } else {
            centerPanel = me.up('destroyTemplateDashboard').down('#destroyTypeRespRegion');
        }
        tabPanel = centerPanel.down('destroyCenterPanel');
        //旧的位置
        var layout = tabPanel.getLayout();
        var originalIndex = Ext.Array.indexOf(layout.getLayoutItems(), form);
        tabPanel.remove(form);
        //创建修改面板
        var modifyForm = Ext.create('OrientTdm.Common.Extend.Form.OrientModifyModelForm', {
            title: modelDesc.text + '基本信息',
            bindModelName: modelDesc.dbName,
            modelDesc: modelDesc,
            originalData: originalData,
            afterInitForm: me._specialHandler,
            actionUrl: serviceName + '/projectTree/updateModelData.rdm',
            listeners: {
                activate: function (ct) {
                    centerPanel.activeTabName = ct.title;
                }
            },
            successCallback: function () {
                me._specialAfterSave(this, arguments);
                me.refreshNode(curNodeData.id, true);
            },
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    itemId: 'save',
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function (btn) {
                        btn.up("form").fireEvent("saveOrientForm", {
                            modelId: modelDesc.modelId
                        });
                    },
                    scope: me
                }]
            }]
        });
        tabPanel.insert(originalIndex, modifyForm);
        tabPanel.setActiveTab(modifyForm);

    },
    onEditNode: function () {
        var selection = this.getSelectionModel().getSelection();
        var curNodeData = selection[0].data;

        var me = this;
        var params = {dataId: curNodeData.dataId};
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/projectTree/nodeModelInfo.rdm', {modelName: curNodeData.modelName}, false, function (response) {
            params.modelId = response.decodedData.results.modelId;
        });

        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelData/getGridModelDescAndData.rdm', params, false, function (response) {
            var modelDesc = response.decodedData.results.orientModelDesc;
            var modelData = response.decodedData.results.modelData;

            var form = Ext.create('OrientTdm.Common.Extend.Form.OrientModifyModelForm', {
                buttonAlign: 'center',
                buttons: [
                    {
                        itemId: 'save',
                        text: '保存',
                        iconCls: 'icon-save',
                        scope: me,
                        handler: function (btn) {
                            var me = this;
                            btn.up('form').fireEvent('saveOrientForm', {
                                modelId: params.modelId
                            });
                        }
                    },
                    {
                        itemId: 'back',
                        text: '取消',
                        iconCls: 'icon-close',
                        scope: me,
                        handler: function () {
                            win.close();
                        }
                    }
                ],
                successCallback: function () {
                    me._specialAfterSave(this, arguments);
                    win.close();
                    me.refreshNode(curNodeData.id, true);
                },
                bindModelName: modelDesc.dbName,
                actionUrl: serviceName + '/projectTree/updateModelData.rdm',
                modelDesc: modelDesc,
                originalData: modelData,
                afterInitForm: me._specialHandler
            });

            var win = new Ext.Window({
                title: '编辑' + modelDesc.text,
                width: 0.4 * globalWidth,
                height: 0.8 * globalHeight,
                layout: 'fit',
                items: [
                    form
                ]
                , listeners: {
                    'beforeshow': function () {
                        var items = form.items.items[0].items.length;
                        if (items < 3) {
                            win.setHeight(items * 160);
                        }
                        else if (items >= 3 && items <= 7) {
                            win.setHeight(items * 90);
                        }
                        else {
                            win.setHeight(items * 90);
                        }
                    }
                }
            });
            win.show();
        });
    },
    onDeleteNode: function () {
        var selection = this.getSelectionModel().getSelection();
        var curNodeData = selection[0].data;
        var me = this;
        Ext.Msg.confirm('提示', '是否删除【' + curNodeData.text + '】?',
            function (btn, text) {
                if (btn == 'yes') {
                    var params = {
                        modelName: curNodeData.modelName,
                        dataId: curNodeData.dataId
                    };
                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/destroyRepairMgr/deleteDestroyTypeData.rdm', params, false, function (response) {
                        var retV = response.decodedData;
                        var success = retV.success;
                        if (success) {
                            me.refreshNode(curNodeData.id, true);
                        }
                    });
                }
            }
        );
    },
    importDestroyData: function () {
        var me = this;
        var selectedNode = this.getSelectionModel().getSelection()[0];
        var destroyTypeId = selectedNode.raw['dataId'];
        var destroyName = selectedNode.raw['text'];
        var win = Ext.create('Ext.Window', {
            title: '新增拆解模板',
            plain: true,
            height: 100,
            width: '40%',
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [{
                xtype: 'form',
                bodyPadding: 10,
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    labelAlign: 'left',
                    msgTarget: 'side',
                    labelWidth: 90
                },
                items: [{
                    xtype: 'filefield',
                    buttonText: '',
                    fieldLabel: '导入拆解模板',
                    buttonConfig: {
                        iconCls: 'icon-upload'
                    },
                    listeners: {
                        'change': function (fb, v) {
                            if (v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                OrientExtUtil.Common.info('提示', '请选择Excel文件!');
                                return;
                            }
                        }
                    }
                }]
            }],
            buttons: [{
                text: '导入',
                handler: function () {
                    var form = win.down("form").getForm();
                    if (form.isValid()) {
                        form.submit({
                            //导入新的检查单模板
                            url: serviceName + '/destroyRepairMgr/importDestroyListFromExcel.rdm?destroyTypeId=' + destroyTypeId + '&destroyName=' + destroyName,
                            //url:serviceName+'/formTemplate/importCheckListFromExcel.rdm?checkTypeId='+me.checkTypeId,
                            waitMsg: '导入模板...',
                            success: function (form, action) {
                                OrientExtUtil.Common.info('成功', action.result.msg, function () {
                                    win.close();
                                    me.fireEvent("refreshTree", false);
                                });
                            },
                            failure: function (form, action) {
                                //alert("模板导入失败！")
                                OrientExtUtil.Common.info('失败', action.result.msg, function () {
                                    win.close();
                                    //me.fireEvent("refreshTree",false);
                                });
                            }
                        });
                    }
                }
            }]
        });
        win.show();
    },
    doRefresh: function () {
        var selectedNode = this.getSelectionModel().getSelection()[0];
        this.getStore().load({
            node: selectedNode
        });
    },
    refreshNode: function (nodeId, refreshParent) {
        var me = this;
        var rootNode = this.getRootNode();

        var currentNode;
        if (nodeId === '-1') {
            currentNode = rootNode;
        } else {
            currentNode = rootNode.findChild('id', nodeId, true) || rootNode;
        }

        var toRefreshNode = currentNode;
        if (refreshParent && currentNode.isRoot() == false) {
            toRefreshNode = currentNode.parentNode;
        }
        this.store.load({
            node: toRefreshNode,
            callback: function () {
                me.getSelectionModel().select(currentNode);
            }
        });
    },
    createDefaultData: function (fatherNode, schemaId) {
        fatherNode = fatherNode || this.getRootNode();
        var fatherModelKey = fatherNode.raw.modelName + '_' + schemaId + '_ID';
        var orderKey = "DISPLAY_ORDER_" + fatherNode.raw.modelId;
        var maxOrder = 0;
        fatherNode.expand(false, function () {
            fatherNode.eachChild(function (child) {
                var tmpOrder = child.raw.order || 0;
                maxOrder = Math.max(maxOrder, parseInt(tmpOrder));
            });
        });
        var retVal = {};
        retVal[fatherModelKey] = fatherNode.raw.dataId;
        retVal[orderKey] = maxOrder + 1;
        return retVal;
    },
    containerclick: function () {
        //点击空白区域 选中 根节点
        this.getSelectionModel().select(this.getRootNode(), false, true);
    },
    _specialHandler: function () {
        var me = this;
        var modelDesc = me.modelDesc;
        if (modelDesc.dbName.indexOf('CB_TASK') != -1) {
            //获取组件信息
            var originalData = me.originalData;
            if (!Ext.isEmpty(originalData.ID)) {
                //请求绑定信息
                var params = {
                    modelId: modelDesc.modelId,
                    dataId: originalData.ID
                };
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ComponentBind/list.rdm', params, false, function (resp) {
                    var results = resp.decodedData.results;
                    if (results && results.length > 0) {
                        originalData['component'] = results[0].belongComponent.id;
                        originalData['component_display'] = results[0].belongComponent.componentname;
                    }
                });
            }
            var editAble = me.xtype != 'orientDetailModelForm';
            //针对任务节点 增加组件选择信息
            me.add({
                xtype: 'fieldset',
                border: '1 1 1 1',
                collapsible: true,
                title: '组件信息',
                items: [
                    {
                        xtype: 'selectComponentField',
                        columnDesc: {
                            sColumnName: 'component',
                            text: '绑定组件',
                            editAble: editAble
                        }
                    }
                ]
            });
        }
    },
    _specialAfterSave: function (form, respArray) {
        var resp = respArray[0];
        var respData = resp.results;
        var dataId = respData.ID;
        if (dataId && form.down('hiddenfield[name=component]')) {
            var modelDesc = form.modelDesc;
            //绑定组件关系
            var params = {
                    modelId: modelDesc.modelId,
                    dataId: dataId,
                    componentId: form.down('hiddenfield[name=component]').getValue()
                }
            ;
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ComponentBind/create.rdm', params, false);
        }
    },
    _filterDisplayData: function (formData, modelId) {
        if ("未开始" === formData["STATUS_" + modelId]) {
            formData["ACTUAL_START_DATE_" + modelId] = "";
            formData["ACTUAL_END_DATE_" + modelId] = "";
        }
    }
});