/**
 * Created by User on 2018/12/26.
 */
Ext.define('OrientTdm.TaskPrepareMgr.Center.TabPanel.CheckTableMgrPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.checkTableMgrPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CHECK_TEMP_INST,
        modelName: TDM_SERVER_CONFIG.CHECK_TEMP_INST
    },

    requires: ["OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil"],

    initComponent: function () {
        var me = this;
        //me.nodeId = "";
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var taskId = me.taskId;
        var taskEndState = me.taskEndState;
        var isDeviceInsCheckEvent = me.isDeviceInsCheckEvent;
        var deviceInstEventId = me.deviceInstEventId;
        var customerFilter = {};
        if (isDeviceInsCheckEvent) {
            customerFilter = new CustomerFilter('T_DEVICE_INS_EVENT_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', deviceInstEventId);
        } else {
            customerFilter = new CustomerFilter('T_DIVING_TASK_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', taskId);
        }
        //根据节点和任务id
        //var filter = me.addCustomerFilterTable(modelId);
        console.log(me);
        //var nodeId=me.getStore().getProxy().getExtraData('nodeId');
        //var nodeText=me.getStore().getProxy().getExtraData('nodeText');
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],
            queryUrl: serviceName + "/CurrentTaskMgr/getCheckInstData.rdm",
            afterInitComponent: function () {
                var me = this;
                //排序
                this.getStore().sort([{
                    "property": "C_NAME_" + modelId,
                    "direction": "ASC"
                }]);
                me.viewConfig.getRowClass = function (record, rowIndex, rowParams, store) {
                    if (record.data['C_CHECK_STATE_' + modelId] == '异常') {
                        return 'x-grid-record-red';
                    }
                }
            }
            // id: 'bindCheckTableTempGrid_own_1'
            //customerFilter: [filter],
            //    afterInitComponent: function () {
            //        var me = this;
            //
            //        //var toolbar = me.dockedItems[0];
            //        //toolbar.add({
            //        //    iconCls: 'icon-create',
            //        //    text: '添加',
            //        //    handler: me.onCreateClick,
            //        //    scope: me
            //        //});
            //
            //    },
            //
        });
        var toolbar = [{
            xtype: 'button',
            iconCls: 'icon-create',
            text: '添加',
            hidden: isDeviceInsCheckEvent,
            handler: function () {
                //var me = this;
                //动态获取上上上个页面传过来的参数
                var nodeId = modelGrid.store.proxy.extraParams['nodeId'];
                var nodeText = modelGrid.store.proxy.extraParams['nodeText'];
                //弹出新增面板窗口
                var createWin = Ext.create('Ext.Window', {
                    title: '新增检查表模板实例',
                    plain: true,
                    height: 0.5 * globalHeight,
                    width: 0.5 * globalWidth,
                    layout: 'fit',
                    maximizable: true,
                    modal: true,
                    items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.AddPanel.FormTempletCheckGrid', {
                        taskId: taskId,
                        nodeId: nodeId,
                        nodeText: nodeText,
                        checkTableModelGrid: modelGrid,
                        successCallback: function (resp, callBackArguments) {
                            me.fireEvent("refreshGrid");
                            if (callBackArguments) {
                                createWin.close();
                            }
                        }
                    })],
                });
                createWin.show();
            }
        }, {
            xtype: 'button',
            iconCls: 'icon-delete',
            text: '删除',
            hidden: isDeviceInsCheckEvent,
            scope: me,
            handler: function () {
                if (!OrientExtUtil.GridHelper.hasSelected(modelGrid)) {
                    return;
                }
                var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                var ids = [];
                Ext.each(selectRecords, function (s) {
                    ids.push(s.data.id);
                });
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/deleteCheckTable.rdm', {
                    id: ids.toString()
                }, false, function (resp) {
                    var ret = Ext.decode(resp.responseText);
                    if (ret.success) {
                        modelGrid.fireEvent('refreshGrid');
                        // Ext.Msg.alert("提示","删除成功！")
                        OrientExtUtil.Common.tip('成功', "删除成功！");
                    }
                })
            }
        }, {
            xtype: 'button',
            iconCls: 'icon-back',
            text: '重做',
            scope: me,
            hidden: isDeviceInsCheckEvent,
            handler: function () {
                var nodeId = modelGrid.store.proxy.extraParams['nodeId'];
                var nodeText = modelGrid.store.proxy.extraParams['nodeText'];
                if (nodeId) {
                    Ext.MessageBox.confirm('提示', '确定重做【' + nodeText + '】流程节点下的表格？', function (btn) {
                        if (btn == 'yes') {
                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/againTable.rdm', {
                                taskId: taskId,
                                nodeId: nodeId,
                                nodeText: nodeText
                            }, false, function (resp) {
                                if (resp.decodedData.success) {
                                    modelGrid.fireEvent('refreshGrid');
                                    // OrientExtUtil.Common.tip('成功', resp.decodedData.msg);
                                } else {
                                    Ext.Msg.alert("提示", resp.decodedData.msg);
                                }
                            });
                        }
                    });
                } else {
                    Ext.Msg.alert("提示", "请先选中流程节点！")
                }
            }
        }, {
            xtype: 'button',
            iconCls: 'icon-update',
            text: '修改',
            scope: me,
            hidden: isDeviceInsCheckEvent,
            handler: function () {
                var selections = modelGrid.getSelectionModel().getSelection();
                if (selections.length === 0) {
                    OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
                } else {
                    var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                    var checkTableInstIds = [];
                    Ext.each(selectRecords, function (s) {
                        checkTableInstIds.push(s.data.id);
                    });
                    //弹出修改面板窗口
                    var updateWin = Ext.create('Ext.Window', {
                        title: '检查表绑定相关流程',
                        plain: true,
                        height: 0.5 * globalHeight,
                        width: 0.5 * globalWidth,
                        layout: 'fit',
                        maximizable: true,
                        modal: true,
                        items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.CheckTableRelationFlowGrid', {
                            taskId: taskId,
                            successCallback: function (resp, callBackArguments) {
                                me.fireEvent("refreshGrid");
                                if (callBackArguments) {
                                    updateWin.close();
                                }
                            }
                        })],
                        buttonAlign: 'center',
                        buttons: [
                            {
                                text: '保存',
                                iconCls: 'icon-save',
                                handler: function () {
                                    var checkTableRelationFlowGrid = updateWin.down('grid');
                                    var selections = checkTableRelationFlowGrid.getSelectionModel().getSelection();
                                    if (selections.length === 0) {
                                        OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
                                    } else if (selections.length > 1) {
                                        OrientExtUtil.Common.tip(OrientLocal.prompt.info, "只能关联一条流程！");
                                    } else {
                                        Ext.Ajax.request({
                                            url: serviceName + '/taskPrepareController/checkTablebindFlowData.rdm',
                                            params: {
                                                flowId: selections[0].raw.id,
                                                flowName: selections[0].raw.flowName,
                                                checkTableInstIds: checkTableInstIds.toString(),
                                                taskId: taskId
                                            }, success: function (response) {
                                                var ret = Ext.decode(response.responseText);
                                                if (ret.success) {
                                                    OrientExtUtil.Common.tip('成功', "保存成功！");
                                                    updateWin.close();
                                                }
                                            }
                                        });
                                    }
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
                    });
                    updateWin.show();
                }
            }
        }];
        //toolbar.setDisabled(true);

        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0]);
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].add(toolbar);
        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0]);
        //隐藏产品配置的按钮
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].hide();
        // modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(true);
        if (taskEndState == '已结束') {
            var toolbar = modelGrid.getDockedItems('toolbar[dock="top"]')[0];
            toolbar.removeAll();
        }

        //增加检查表实例查看按钮
        var columns = modelGrid.columns;
        // if (Ext.isArray(columns)) {
        //     columns.push(
        //         {
        //             xtype: 'actioncolumn',
        //             text: '查看',
        //             align: 'center',
        //             width: 200,
        //             items: [{
        //                 iconCls: 'icon-detail',
        //                 tooltip: '表格实例查看',
        //                 handler: function (grid, rowIndex) {
        //                     var data = grid.getStore().getAt(rowIndex);
        //                     // var checkTempId = data.raw.ID;
        //                     // var productId = '';
        //                     // me.preview(checkTempId, true, productId);
        //                     var instanceId = data.raw.ID;
        //                     //window.open("/CurrentTaskMgr/getCheckTableCaseHtml.rdm?instanceId="+instanceId)
        //
        //                     Ext.create('Ext.Window', {
        //                             plain: true,
        //                             title: '预览',
        //                             height: 0.95 * globalHeight,
        //                             width: 0.95 * globalWidth,
        //                             layout: 'fit',
        //                             maximizable: true,
        //                             modal: true,
        //                             autoScroll: true,
        //                             items: [
        //                                 Ext.create('OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel', {
        //                                     instanceId: instanceId
        //                                 })
        //                             ]
        //                         }
        //                     ).show();
        //                 }
        //             }]
        //         })
        // }

        //增加actionColumn
        var actionColumns = me._initActionColumns();
        Ext.Array.insert(columns, 0, actionColumns);
        //使用一个新的store/columns配置项进行重新配置生成grid
        if (isDeviceInsCheckEvent) {
            var newColumns = [];
            Ext.each(columns, function (column, index) {
                if (column.text !== '检查人' && column.text !== '检查时间') {
                    newColumns.push(column);
                }
            });
            columns = newColumns;
        }
        modelGrid.reconfigure(modelGrid.getStore(), columns);

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },
    //onCreateClick: function (modelGrid,taskId) {
    //    //var me = this;
    //    //动态获取上上上个页面传过来的参数
    //    var nodeId=modelGrid.store.proxy.extraParams['nodeId'];
    //    var nodeText=modelGrid.store.proxy.extraParams['nodeText'];
    //    //弹出新增面板窗口
    //    var createWin = Ext.create('Ext.Window', {
    //        title: '新增检查表模板实例',
    //        plain: true,
    //        height: 0.5 * globalHeight,
    //        width: 0.7 * globalWidth,
    //        layout: 'fit',
    //        maximizable: true,
    //        modal: true,
    //        items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.AddPanel.FormTempletCheckGrid', {
    //            taskId: taskId,
    //            nodeId:nodeId,
    //            nodeText:nodeText,
    //            successCallback: function (resp, callBackArguments) {
    //                me.fireEvent("refreshGrid");
    //                if (callBackArguments) {
    //                    createWin.close();
    //                }
    //            }
    //        })]
    //    });
    //    createWin.show();
    //}
    //addCustomerFilterTable: function (modelId) {
    //    var me = this;
    //
    //    var refPostTableName = TDM_SERVER_CONFIG.NODE_DESIGN;
    //    var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
    //
    //    var filter = {};
    //    filter.expressType = "ID";
    //    filter.modelName = refPostTableName;
    //
    //    filter.idQueryCondition = {};
    //    filter.idQueryCondition.params = [me.taskId];
    //    refPostTableName = refPostTableName + "_" + schemaId;
    //    filter.idQueryCondition.sql = "SELECT ID FROM " + refPostTableName + " WHERE T_DIVING_TASK_" + schemaId + "_ID=?";
    //    if (Ext.isEmpty(me.nodeId)) {
    //        //展示所有
    //        return filter;
    //    } else {
    //        var postSql = "(C_NODE_DESIGN_ID_"+modelId+"='" + me.nodeId + "')";
    //        filter.idQueryCondition.sql += "AND (" + postSql;
    //        filter.idQueryCondition.sql += ")";
    //        return filter;
    //    }
    //}

    _initActionColumns: function () {
        var me = this;
        var width = 0.05 * globalWidth;
        var retVal = {
            xtype: 'actioncolumn',
            text: '查看',
            align: 'center',
            width: width,
            items: [{
                iconCls: 'icon-detail',
                tooltip: '表格实例查看',
                handler: function (grid, rowIndex) {
                    var data = grid.getStore().getAt(rowIndex);
                    // var checkTempId = data.raw.ID;
                    // var productId = '';
                    // me.preview(checkTempId, true, productId);
                    var instanceId = data.raw.ID;
                    //window.open("/CurrentTaskMgr/getCheckTableCaseHtml.rdm?instanceId="+instanceId)

                    Ext.create('Ext.Window', {
                            plain: true,
                            title: '预览',
                            height: 0.95 * globalHeight,
                            width: 0.95 * globalWidth,
                            layout: 'fit',
                            maximizable: true,
                            modal: true,
                            autoScroll: true,
                            items: [
                                Ext.create('OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel', {
                                    instanceId: instanceId
                                })
                            ]
                        }
                    ).show();
                }
            }]
        };
        return null == retVal ? [] : [retVal];
    },

    refreshCheckfilePanelByNode: function (nodeId, nodeText) {
        var me = this;
        me.nodeId = nodeId;
        me.nodeText = nodeText;

        console.log(me.items.items[0]);

        //根据节点和任务id
        var checkFilter = me.addCustomerFilterTable();
        me.items.items[0].getStore().getProxy().setExtraParam('customerFilter', Ext.encode([checkFilter]));
        me.items.items[0].getStore().getProxy().setExtraParam('nodeId', me.nodeId);
        me.items.items[0].getStore().getProxy().setExtraParam('nodeText', me.nodeText);
        me.items.items[0].getStore().load();
    }
    ,

    addCustomerFilterTable: function () {
        var me = this;

        var refPostTableName = TDM_SERVER_CONFIG.CHECK_TEMP_INST;
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var modelId = OrientExtUtil.ModelHelper.getModelId(refPostTableName, schemaId);
        var filter = {};
        filter.expressType = "ID";
        filter.modelName = refPostTableName;

        filter.idQueryCondition = {};
        filter.idQueryCondition.params = [me.taskId];
        refPostTableName = refPostTableName + "_" + schemaId;
        filter.idQueryCondition.sql = "SELECT ID FROM " + refPostTableName + " WHERE T_DIVING_TASK_" + schemaId + "_ID=?";
        if (Ext.isEmpty(me.nodeId)) {
            //展示所有
            return filter;
        } else {
            var postSql = "(C_NODE_ID_" + modelId + "='" + me.nodeId + "')";
            filter.idQueryCondition.sql += "AND (" + postSql;
            filter.idQueryCondition.sql += ")";
            return filter;
        }
    },

    preview: function (checkTempId, withData, productId) {
        //表单预览
        TestProcessUtil.CommonHelper.checkListPreview(checkTempId, true, withData, productId);
    }
});