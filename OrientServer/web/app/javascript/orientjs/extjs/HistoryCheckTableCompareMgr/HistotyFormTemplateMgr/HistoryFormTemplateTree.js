/**\
 *
 */
Ext.define('OrientTdm.HistoryCheckTableCompareMgr.HistotyFormTemplateMgr.HistoryFormTemplateTree', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.HistoryFormTemplateTree',
    config: {
        modelId: '',
        templateId: '',
        modelDesc: null,
        isShowButton: ''
    },
    beforeInitComponent: Ext.emptyFn,
    afterInitComponent: Ext.emptyFn,

    itemClickListener: function (tree, record, item) {
        var me = this;
        //var func = record.raw;
        var centerPanel = me.ownerCt.centerPanel;
        centerPanel.items.each(function (item, index) {
            centerPanel.remove(item);
        });
        centerPanel.topId = record.data.id;
        var topId = record.data.id;
        // if(topId != -1){
        //     centerPanel.down('[text=新增]').setVisible(true);
        // }else{
        //     centerPanel.down('[text=新增]').setVisible(false);
        // }
        if (topId != '-1' && record.data.parentId == -1) {
            var customerFilter = [{
                filterName: 'T_CHECK_TYPE_' + TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID + '_ID',
                filterValue: topId,
                operation: 'Equal'
            }];
            var modelId = OrientExtUtil.ModelHelper.getModelId("T_CHECK_TEMP", TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID);
            var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, TDM_SERVER_CONFIG.TPL_CHECK_TEMPLATE);
            var grid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
                modelId: modelId,
                templateId: templateId,
                region: 'center',
                isView: '0',
                topId: topId,
                customerFilter:customerFilter,
                afterInitComponent: function () {
                    var me = this;
                    //排序
                    this.getStore().sort([{
                        "property": "C_NAME_" + modelId,
                        "direction": "ASC"
                    }]);
                }
            });
            me.checkTypeId=topId;
            me.checkTypeName=record.data.text;
            if (me.isShowButton) {
                var toolbar = [{
                    xtype: 'button',
                    iconCls: 'icon-import',
                    text: '导入',
                    tooltip: '从Excel导入',
                    handler: Ext.bind(me.createTemplate, me)
                }, {
                    xtype: 'button',
                    iconCls: 'icon-update',
                    text: '编辑',
                    tooltip: '编辑',
                    handler: Ext.bind(me.editTemplate, me)
                },{
                    xtype: 'button',
                    iconCls: 'icon-delete',
                    text: '删除',
                    tooltip: '删除',
                    handler: Ext.bind(me.delTemplate, me)
                },{
                    xtype: 'button',
                    iconCls: 'icon-export',
                    text: '导出',
                    tooltip: '从数据库中导出',
                    handler: Ext.bind(me.exportTemplate, me)
                }];
                grid.getDockedItems()[0].insert(0, toolbar);
            }
            centerPanel.add({
                title: '表单模板',
                layout: 'border',
                items: [grid]
            });
            // centerPanel.down('grid').getStore().sort([{
            //     "property": "C_NAME_" + modelId,
            //     "direction": "ASC"
            // }]);
            // centerPanel.down('grid').getStore().getProxy().setExtraParam('customerFilter', Ext.encode(customerFilter));
            // centerPanel.down('grid').getStore().load();
        } else if (topId != '-1' && record.data.parentId != -1) {
            if (!me.isShowButton) {
                var checkTempName = record.raw.text;
                var checkItemPanel = Ext.create('OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateHeadCellGrid', {
                    region: 'center',
                    padding: '0 0 0 5',
                    isInst: false,
                    checkTempId: topId,
                    checkTempName: checkTempName
                });
                centerPanel.add({
                    title: '检查项管理',
                    // iconCls: record.raw['iconCls'],
                    layout: 'border',
                    items: [checkItemPanel]
                });
            } else {
                if (!Ext.isEmpty(topId)) {
                    var formTemplateCenterPanel = me.ownerCt.centerPanel;
                    formTemplateCenterPanel.items.each(function (item, index) {
                        formTemplateCenterPanel.remove(item);
                    });

                    var checkHeaderFilter = me.addCustomerFilterHeader(topId);
                    var checkEndFilter = me.addCustomerFilterEnd(topId);

                    var checkItemPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateHeadCellGrid', {
                        region: 'center',
                        padding: '0 0 0 5',
                        isInst: false,
                        isShowProduct: true,
                        checkTempId: topId
                    });
                    var checkHeaderEndPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.CheckHeaderEndMgrPanel.CheckHeaderPanel', {
                        region: 'center',
                        padding: '0 0 0 5',
                        customerFilter: checkHeaderFilter,
                        checkTempId: topId
                    });
                    var checkEndPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.CheckHeaderEndMgrPanel.CheckEndPanel', {
                        region: 'center',
                        padding: '0 0 0 5',
                        customerFilter: checkEndFilter,
                        checkTempId: topId
                    });
                    formTemplateCenterPanel.add({
                        title: '检查项管理',
                        layout: 'border',
                        items: [checkItemPanel]
                    });

                    formTemplateCenterPanel.add({
                        title: '检查表表头管理',
                        layout: 'border',
                        items: [checkHeaderEndPanel]
                    });
                    formTemplateCenterPanel.add({
                        title: '检查表表尾管理',
                        layout: 'border',
                        items: [checkEndPanel]
                    });
                    formTemplateCenterPanel.setActiveTab(0);
                    // this.ownerCt.centerPanel.items.items[0].items.items[0].getCheckListDetailPanel(topId);
                }
            }

        } else {
            var modelId = OrientExtUtil.ModelHelper.getModelId("T_CHECK_TYPE", TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID);
            var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, TDM_SERVER_CONFIG.TPL_CHECK_TYPE);
            var grid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
                modelId: modelId,
                templateId: templateId,
                region: 'center',
                isView: '0',
                topId: topId,
                afterOperate: function () {
                    var treepanel = me.ownerCt.westPanel;
                    var store = treepanel.getStore();
                    var selectNode = treepanel.getSelectionModel().getSelection()[0];
                    if (Ext.isEmpty(selectNode))	//如果没有父节点，则pnode为根节点
                    {
                        selectNode = store.getRootNode();
                    }
                    store.load({
                        node: selectNode,
                        callback: function (records) {
                            // selectNode.appendChild(records);	//添加子节点
                            // selectNode.set('leaf',false);
                            // selectNode.expand(true);
                            Ext.each(records, function (record) {
                                if (record.get('expanded') == true) {
                                    record.set('expanded', false);
                                    record.expand();
                                }
                            });
                        }
                    });
                }
            });
            centerPanel.add({
                title: '表单模板分类',
                layout: 'border',
                items: [grid]
            });
            if (!me.isShowButton) {
                centerPanel.down('grid').down('[text=新增]').setVisible(false);
                centerPanel.down('grid').down('[text=修改]').setVisible(false);
                centerPanel.down('grid').down('[text=级联删除]').setVisible(false);
            }
            var customerFilter = [{
                filterName: 'ID',
                operation: 'IsNotNull'
            }];
            centerPanel.down('grid').getStore().getProxy().setExtraParam('customerFilter', Ext.encode(customerFilter));
            centerPanel.down('grid').getStore().load();
        }
    },

    createFooBar: function () {
        return Ext.emptyFn;
    },
    createStore: function () {
        var retVal = new Ext.data.TreeStore({
            proxy: {
                type: 'ajax',
                url: serviceName + '/formTemplate/getHistoryFormTemplateTreeByPid.rdm',
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    root: 'results',
                    messageProperty: 'msg',
                }
            },
            root: {
                expanded: true,
                text: "表单模板",
                id: '-1',
                // iconCls:"icon-topType-button"
            }
        });
        return retVal;
    },
    // createFooBar: function () {
    //     var me = this;
    //     return Ext.create('Ext.toolbar.Toolbar', {
    //         weight: 1,
    //         dock: 'bottom',
    //         ui: 'footer',
    //         items: [{
    //             xtype: 'trigger',
    //             width: '100%',
    //             triggerCls: 'x-form-clear-trigger',
    //             onTriggerClick: function () {
    //                 this.setValue('');
    //                 me.clearFilter();
    //             },
    //             emptyText: '快速搜索',
    //             enableKeyEvents: true,
    //             listeners: {
    //                 keyup: function (field, e) {
    //                     if (Ext.EventObject.ESC == e.getKey()) {
    //                         field.onTriggerClick();
    //                     } else {
    //                         me.filterByText(this.getRawValue(), "text");
    //                     }
    //                 }
    //             }
    //         }]
    //     });
    // },
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            rootVisible: true,
            lines: true,
            useArrows: false,
            tbar: [{
                xtype: 'trigger',
                triggerCls: 'x-form-clear-trigger',
                onTriggerClick: function () {
                    this.setValue('');
                    me.clearFilter();
                },
                emptyText: '快速搜索(只能搜索已展开节点)',
                width: "78%",
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
            }, {
                iconCls: 'icon-refresh',
                text: '<span style="color: black; ">' + '刷新' + '</span>',
                itemId: 'refresh',
                scope: this,
                handler: this.doRefresh
            }]
        });
        me.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'select', me.itemClickListener, me);
    },
    doRefresh: function () {
        var selectedNode = this.getSelectionModel().getSelection()[0];
        this.getStore().load({
            node: selectedNode,
            callback: function (records) {
                Ext.each(records, function (record) {
                    if (record.get('expanded') == true) {
                        record.set('expanded', false);
                        record.expand();
                    }
                });
            }
        });
    },
    addCustomerFilterHeader: function (checkTempId) {
        var filter = {};
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var refCheckTableName = TDM_SERVER_CONFIG.CHECK_CELL;
        var modelId = OrientExtUtil.ModelHelper.getModelId(refCheckTableName, schemaId);
        refCheckTableName = refCheckTableName + "_" + schemaId;
        filter.expressType = "ID";
        filter.modelName = refCheckTableName;
        filter.idQueryCondition = {};
        filter.idQueryCondition.params = [checkTempId];

        filter.idQueryCondition.sql = "SELECT ID FROM " + refCheckTableName + " WHERE T_CHECK_TEMP_" + schemaId + "_ID=?";

        var headerSql = "(C_IS_HEADER_" + modelId + "='" + "true" + "')";
        filter.idQueryCondition.sql += "AND (" + headerSql;
        filter.idQueryCondition.sql += ")";
        return filter;
    },
    addCustomerFilterEnd: function (checkTempId) {
        var filter = {};
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var refCheckTableName = TDM_SERVER_CONFIG.CHECK_CELL;
        var modelId = OrientExtUtil.ModelHelper.getModelId(refCheckTableName, schemaId);
        refCheckTableName = refCheckTableName + "_" + schemaId;
        filter.expressType = "ID";
        filter.modelName = refCheckTableName;
        filter.idQueryCondition = {};
        filter.idQueryCondition.params = [checkTempId];

        filter.idQueryCondition.sql = "SELECT ID FROM " + refCheckTableName + " WHERE T_CHECK_TEMP_" + schemaId + "_ID=?";

        var headerSql = "(C_IS_HEADER_" + modelId + "='" + "false" + "')";
        filter.idQueryCondition.sql += "AND (" + headerSql;
        filter.idQueryCondition.sql += ")";
        return filter;
    },

    createTemplate: function () {
        var me = this;
        var win = Ext.create('Ext.Window', {
            title: '新增检查表模板',
            plain: true,
            height: 110,
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
                    fieldLabel: '导入检查表模板(.zip或xlsx)',
                    buttonConfig: {
                        iconCls: 'icon-upload'
                    },
                    listeners: {
                        'change': function (fb, v) {
                            /*if (v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                OrientExtUtil.Common.info('提示', '请选择Excel文件!');
                                return;
                            }*/
                            if (v.substr(v.length - 3) != "zip" && v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                OrientExtUtil.Common.info('提示', '请选择zip压缩文件或Excel文件!');
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
                            url: serviceName + '/formTemplate/multiImportCheckListFromExcel.rdm?checkTypeId=' + me.checkTypeId + '&checkTypeName=' + me.checkTypeName,
                            //url: serviceName + '/formTemplate/importCheckListFromExcel.rdm?checkTypeId=' + me.checkTypeId + '&checkTypeName=' + me.checkTypeName,
                            //url:serviceName+'/formTemplate/importCheckListFromExcel.rdm?checkTypeId='+me.checkTypeId,

                            waitMsg: '导入模板...',
                            success: function (form, action) {
                                OrientExtUtil.Common.info('成功', action.result.msg, function () {
                                    var grid=me.ownerCt.centerPanel.down('panel').down('grid');
                                    grid.getStore().load();
                                    me.doRefresh();
                                    win.close();
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
    editTemplate: function () {
        var me = this;
        var checkTempTypeId=me.ownerCt.centerPanel.topId;
        var grid=me.ownerCt.centerPanel.down('panel').down('grid');
        var selectedRecords = grid.getSelectionModel().getSelection();
        if (selectedRecords.length == 0) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
            return;
        }else if (selectedRecords.length > 1) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.onlyCanSelectOne);
            return;
        }else{
            var checkTempId=selectedRecords[0].raw.ID;
            var checkName = selectedRecords[0].raw['C_NAME_'+grid.modelId];
            var tempType = selectedRecords[0].raw['C_TEMP_TYPE_'+grid.modelId];
            var isRepeatUpload = selectedRecords[0].raw['C_IS_REPEAT_UPLOAD_'+grid.modelId];
            var updateWin = Ext.create('Ext.Window', {
                title: '修改检查表模板',
                plain: true,
                height: 0.5 * globalHeight,
                width: 0.7 * globalWidth,
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [Ext.create('OrientTdm.FormTemplateMgr.FormPanel.EditCheckTemplateFormPanel', {
                    bindModelName: "T_CHECK_TEMP_480",
                    checkTempId: checkTempId,
                    checkTempGrid:grid,
                    checkTempTypeId:checkTempTypeId
                })]
            });
            Ext.getCmp('checkNameId').setValue(checkName);
            Ext.getCmp('tempTypeId').setValue(tempType);
            Ext.getCmp('tempRepeatId').setValue(isRepeatUpload);
            updateWin.show();
        }
    },
    delTemplate: function () {
        var me = this;
        var grid=me.ownerCt.centerPanel.down('panel').down('grid');
        var selectedRecords = grid.getSelectionModel().getSelection();
        if (selectedRecords.length == 0) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
            return;
        }
        var checkTempId = [];
        Ext.each(selectedRecords, function (record) {
            checkTempId.push(record.raw.ID);
        });
        Ext.Msg.confirm("友情提示", "是否确认删除",
            function (btn) {
                if (btn == 'yes') {
                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/delCheckTempList.rdm', {
                        checkTempId: checkTempId.toString()
                    }, false, function (resp) {
                        grid.getStore().load();
                        me.doRefresh();
                    });
                }
            }
        )
    },
    exportTemplate: function () {
        var me = this;
        var checkTypeId = me.checkTypeId;
        var checkTypeName = me.checkTypeName;
        var grid=me.ownerCt.centerPanel.down('panel').down('grid');
        var selectedRecords = grid.getSelectionModel().getSelection();
        var storeResult = me.store.proxy.reader.rawData.results;
        if (storeResult.length == 0) {
            Ext.Msg.alert("提示", "【" + checkTypeName + "】下至少要有一条检查表数据才可以导出！");
            return;
        }
        if (selectedRecords.length === 0) {
            Ext.Msg.confirm("友情提示", "是否导出【" + checkTypeName + "】下的所有检查表格",
                function (btn) {
                    if (btn == 'yes') {
                        window.location.href = serviceName + "/formTemplate/exportAllCheckTempByCheckTypeId.rdm?checkTypeId=" + checkTypeId + "&checkTypeName=" + checkTypeName;
                    }
                }
            )
        } else {
            if (selectedRecords.length > 1) {
                OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.onlyCanSelectOne);
                return;
            }
            var checkTempId = selectedRecords[0].raw.ID;
            var checkName = selectedRecords[0].raw['C_NAME_'+grid.modelId];
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/exportCheckTempFromOracle.rdm', {
                checkTempId: checkTempId,
                checkName: checkName
            }, true, function (resp) {
                window.location.href = serviceName + "/orientForm/downloadByName.rdm?fileName=" + resp.decodedData.results;
            })
        }
    },
});