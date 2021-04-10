/**
 * Created by User on 2018/12/26.
 */
Ext.define('OrientTdm.TaskPrepareMgr.Center.TabPanel.AttendPostMgrPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.attendPostMgrPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_REF_POST_NODE,
        modelName: TDM_SERVER_CONFIG.REF_POST_NODE,
        nodeId: '',
        taskId: '',
        attendPersonIds: '',
        signPersonIds: ''
    },
    id: 'addPost_own_1',

    initComponent: function () {
        var me = this;
        //nodeId = me.nodeId;
        //taskId=me.taskId;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var taskId = me.taskId;
        var taskEndState = me.taskEndState;
        var customerFilter = new CustomerFilter('T_DIVING_TASK_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.taskId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],
            id: 'addPost_own_2',
            queryUrl: serviceName + '/taskPrepareController/queryAttendPostData.rdm?taskId=' + taskId,
            createUrl: serviceName + '/taskPrepareController/AddAttendPostData.rdm?taskId=' + me.taskId + '&nodeId=' + this.nodeId + '&nodeText=' + this.nodeText,

            //nodeId:modelGrid.store.proxy.extraParams['nodeId'],
            //customerFilter: [postFilter],
            //afterInitComponent: function () {
            //    var me = this;
            //    var toolbar = me.dockedItems[0];
            //    toolbar.add({
            //        iconCls: 'icon-create',
            //        text: '添加',
            //        handler: me.onCreateClick,
            //        scope: me
            //    });
            //    toolbar.setDisabled(true);
            //},
            afterInitComponent: function () {
                me.addColumn(this.columns, taskId, taskEndState, modelId);
            },
        });

        var toolbar = [{
            xtype: 'button',
            iconCls: 'icon-create',
            text: '添加',
            handler: function () {
                //动态获取上上上个页面传过来的参数
                var nodeId = modelGrid.store.proxy.extraParams['nodeId'];
                var nodeText = modelGrid.store.proxy.extraParams['nodeText'];

                //弹出新增面板窗口
                var createWin = Ext.create('Ext.Window', {
                    title: '新增参与岗位',
                    plain: true,
                    height: 0.5 * globalHeight,
                    width: 0.5 * globalWidth,
                    layout: 'fit',
                    maximizable: true,
                    modal: true,
                    items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.PostPersonnelMgrPanel', {
                        taskId: me.taskId,
                        nodeId: nodeId,
                        nodeText: nodeText,
                        buttonShow: true,
                        postIds: '',
                        successCallback: function (resp, callBackArguments) {
                            me.fireEvent("refreshGrid");
                            if (callBackArguments) {
                                createWin.close();
                            }
                        }
                    })],
                    buttonAlign: 'center',
                    buttons: [
                        {
                            text: '保存',
                            iconCls: 'icon-save',
                            //scope: me,
                            handler: function () {
                                var postGridPanel = Ext.getCmp("postPersonnelMgrPanel2").items.items[0];
                                console.log(postGridPanel);
                                if (OrientExtUtil.GridHelper.getSelectedRecord(postGridPanel).length == 0) {
                                    OrientExtUtil.Common.err(OrientLocal.prompt.error, '未选中任何记录');
                                    return;
                                }
                                var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(postGridPanel);
                                var ids = [];
                                Ext.each(selectRecords, function (s) {
                                    ids.push(s.data.id);
                                });
                                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/bindPostTableTemp.rdm', {
                                        postId: ids,
                                        taskId: me.taskId,
                                        nodeId: nodeId,
                                        nodeText: nodeText
                                    },
                                    false, function (response) {
                                        if (!response.decodedData.success) {
                                            //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                            //Ext.Msg.alert("提示", response.decodedData.msg);
                                            Ext.Msg.alert("提示", response.decodedData.msg);
                                        }
                                        //console.log(
                                        //    Ext.getCmp('bindCheckTableTempGrid_own_1')
                                        //);
                                        //console.log(Ext.ComponentQuery.query('bindCheckTableTempGrid')[0].modelGrid);
                                        //Ext.ComponentQuery.query('bindCheckTableTempGrid')[0].modelGrid.store.reload(); //刷新绑定面板
                                        Ext.getCmp('addPost_own_2').store.reload();    //刷新绑定面板
                                        // me.up('window').close();  //关闭窗口
                                        createWin.close();
                                    })
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
                createWin.show();
            }
        }, {
            xtype: 'button',
            iconCls: 'icon-delete',
            text: '删除',
            scope: me,
            handler: function () {
                if (!OrientExtUtil.GridHelper.hasSelected(modelGrid)) {
                    return;
                }
                var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                var params = [];
                Ext.each(selectRecords, function (s) {
                    var postKeyId = s.data.id;
                    var postId = s.raw['C_POST_ID_' + modelId + '_DISPLAY'];
                    var object = {};
                    object.taskId = taskId;
                    object.attendPostKeyId = postKeyId;
                    object.postId = postId;
                    params.push(object);
                });
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/deleteAttendPostData.rdm', {
                    params: JSON.stringify(params)
                }, false, function (resp) {
                    var ret = Ext.decode(resp.responseText);
                    if (ret.success) {
                        modelGrid.fireEvent('refreshGrid');
                        // Ext.Msg.alert("提示", "删除成功！")
                        Ext.getCmp('selectPostPanel').store.reload();
                        OrientExtUtil.Common.tip('成功', "删除成功！");
                    }
                })
            }
        }];

        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0]);
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].add(toolbar);
        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0]);
        //隐藏产品配置的按钮
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].hide();
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(true);

        if (taskEndState=='已结束') {
            var toolbar =modelGrid.getDockedItems('toolbar[dock="top"]')[0];
            toolbar.removeAll();
        }

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },

    refreshPostfilePanelByNode: function (nodeId, nodeText) {
        //var me = this;
        this.nodeId = nodeId;
        this.nodeText = nodeText;
        this.ownerCt.ownerCt.ownerCt.items.items[0].nodeId=this.nodeId;
        this.ownerCt.ownerCt.ownerCt.items.items[0].nodeText=this.nodeText;
        //console.log(me.items.items[0].items.items[0].items.items[0].items.items[0]);

        //this.modelGrid.createUrl = serviceName+'/taskPrepareController/AddAttendPostData.rdm?taskId='+this.taskId+'&nodeId='+nodeId+'&nodeText='+nodeText;
        var postFilter = this.addCustomerFilterPost();
        //me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('customerFilter', Ext.encode([postFilter]));
        //me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('nodeId', me.nodeId);
        //me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('nodeText', me.nodeText);
        //me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().load();
        //this.items.items[0].createUrl = serviceName+'/taskPrepareController/AddAttendPostData.rdm?taskId='+this.taskId+'&nodeId='+nodeId+'&nodeText='+nodeText;
        this.items.items[0].getStore().getProxy().setExtraParam('customerFilter', Ext.encode([postFilter]));
        this.items.items[0].getStore().getProxy().setExtraParam('nodeId', nodeId);
        this.items.items[0].getStore().getProxy().setExtraParam('nodeText', nodeText);
        this.items.items[0].getStore().getProxy().api.create = serviceName + '/taskPrepareController/AddAttendPostData.rdm?taskId=' + this.taskId + '&nodeId=' + nodeId + '&nodeText=' + nodeText;
        this.items.items[0].getStore().load();
        console.log(this.modelGrid);
    },

    addCustomerFilterPost: function () {
        //var me = this;
        var refPostTableName = TDM_SERVER_CONFIG.REF_POST_NODE;
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var modelId = OrientExtUtil.ModelHelper.getModelId(refPostTableName, schemaId);
        var filter = {};
        filter.expressType = "ID";
        filter.modelName = refPostTableName;

        filter.idQueryCondition = {};
        refPostTableName = refPostTableName + "_" + schemaId;
        if (Ext.isEmpty(this.taskId)) {
            filter.idQueryCondition.sql = "SELECT ID FROM " + refPostTableName + " WHERE 1==1 ";
        } else {
            filter.idQueryCondition.params = [this.taskId];

            filter.idQueryCondition.sql = "SELECT ID FROM " + refPostTableName + " WHERE T_DIVING_TASK_" + schemaId + "_ID=?";
        }
        if (Ext.isEmpty(this.nodeId)) {
            //展示所有
            return filter;
        } else {
            var postSql = "(C_NODE_ID_" + modelId + "='" + this.nodeId + "')";
            filter.idQueryCondition.sql += "AND (" + postSql;
            filter.idQueryCondition.sql += ")";
            return filter;
        }
    },
    //addCustomerFilterPost: function (modelId) {
    //    var me = this;
    //
    //    var refPostTableName = TDM_SERVER_CONFIG.REF_POST_NODE;
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
    //        var postSql = "(C_NODE_DESIGN_ID_" + modelId + "='" + me.nodeId + "')";
    //        filter.idQueryCondition.sql += "AND (" + postSql;
    //        filter.idQueryCondition.sql += ")";
    //        return filter;
    //    }
    //}
    addColumn: function (columns, taskId, taskEndState, modelId) {
        columns.push({
                text: '参与人员',
                align: 'left',
                draggable: false,
                width: 400,
                sortable: false,
                menuDisabled: true,
                renderer: function (value, cellmeta, record, rowIndex, columnIndex, store) {
                    //var postId = cellmeta.record.internalId;
                    var postId = record.raw["C_POST_ID_" + modelId + "_DISPLAY"];
                    //var userName = "M_USERS_" + me.modelId + "_display";
                    attendPersonIds = record.raw["C_POST_PERSONNEL_" + modelId + "_ID"];
                    var userName = record.raw["C_POST_PERSONNEL_" + modelId];
                    //console.log(userName_display);
                    if (userName == null || userName == '无' || userName == '') {
                        userName = '未设置';
                    }
                    if (taskEndState == '已结束') {
                        //return "<a href='javascript:void(0);' style='color:blue'>" + userName + " </a>";
                        return "<span style='color:black'>" + userName + " </span>";
                    } else {
                        return "<a href='javascript:void(0);' style='color:blue' onclick=\"showWinUserName('" + postId + "','" + attendPersonIds + "')\">" + userName + " </a>";
                    }
                }
            }
            //     {
            //     text: '签署人员',
            //     align: 'left',
            //     draggable: false,
            //     width: 400,
            //     sortable: false,
            //     menuDisabled: true,
            //     renderer: function (value, cellmeta, record, rowIndex, columnIndex, store) {
            //         //var postId = cellmeta.record.internalId;
            //         var postId = record.raw["C_POST_ID_" + modelId + "_DISPLAY"];
            //         //var userName = "M_USERS_" + me.modelId + "_display";
            //         signPersonIds = record.raw["C_SIGN_PERSON_" + modelId + "_ID"];
            //         var signPostName = record.raw["C_SIGN_PERSON_" + modelId];
            //         //console.log(userName_display);
            //         if (signPostName == null || signPostName == '无' || signPostName == '') {
            //             signPostName = '未设置';
            //
            //         }
            //         if (taskEndState == '已结束') {
            //             //return "<a href='javascript:void(0);' style='color:blue'>" + signPostName + " </a>";
            //             return "<span style='color:black'>" + signPostName + " </span>";
            //         } else {
            //             return "<a href='javascript:void(0);' style='color:blue' onclick=\"showWinsignPostName('" + postId + "','" + signPersonIds + "')\">" + signPostName + " </a>";
            //         }
            //     }
            // }

        );

        showWinUserName = function (postId, attendPersonIds) {
            //弹出新增面板窗口
            var createWin = Ext.create('Ext.Window', {
                title: '选择参与人员',
                id: 'attendWindow',
                plain: true,
                height: 0.5 * globalHeight,
                width: 0.5 * globalWidth,
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.AddPanel.PostSelectPersonGrid', {
                    postId: postId,
                    taskId: taskId,
                    buttonShow: true,
                    selectPersonIds: attendPersonIds,
                    successCallback: function (resp, callBackArguments) {
                        me.fireEvent("refreshGrid");
                        if (callBackArguments) {
                            createWin.close();
                        }
                    }
                })],
                buttonAlign: 'center',
                buttons: [
                    {
                        text: '保存',
                        iconCls: 'icon-save',
                        //scope: me,
                        handler: function () {
                            var postGridPanel = Ext.getCmp("postSelectPersonGridOwner").items.items[0];
                            console.log(postGridPanel);
                            var allSelectedRecords = postGridPanel.ownerCt.allSelectedRecords;
                            var retVal = [];
                            Ext.each(allSelectedRecords, function (record) {
                                if (retVal.indexOf(record.get("id")) == -1) {
                                    retVal.push(record.get("id"));
                                }
                            });
                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/selectPersonData.rdm', {
                                    // selectPersonId: OrientExtUtil.GridHelper.getSelectRecordIds(postGridPanel),
                                    selectPersonId: retVal,
                                    taskId: taskId,
                                    postId: postId,
                                    beforeSelectPersonIds: attendPersonIds,
                                    signPersonLogo: false
                                },
                                false, function (response) {
                                    if (response.decodedData.success) {
                                        //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                        //Ext.Msg.alert("提示", response.decodedData.msg);
                                        Ext.getCmp('attendWindow').close();
                                    } else {
                                        Ext.Msg.alert("提示", response.decodedData.msg);
                                    }
                                    Ext.getCmp('addPost_own_2').store.reload();    //刷新绑定面板
                                    Ext.getCmp('selectPostPanel').store.reload();
                                    //var win = Ext.getCmp('selectPostWindow');
                                    //var gridWin = win.down('panel').down('panel');
                                    //gridWin.store.reload();
                                });
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
            createWin.show();
        };
        showWinsignPostName = function (postId, signPersonIds) {
            var me = this;
            //弹出新增面板窗口
            var createWin = Ext.create('Ext.Window', {
                title: '选择签署人员',
                plain: true,
                height: 0.5 * globalHeight,
                width: 0.5 * globalWidth,
                layout: 'fit',
                maximizable: true,
                id: 'signWindow',
                modal: true,
                items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.AddPanel.PostSelectPersonGrid', {
                    postId: postId,
                    taskId: taskId,
                    buttonShow: true,
                    selectPersonIds: signPersonIds,
                    successCallback: function (resp, callBackArguments) {
                        me.fireEvent("refreshGrid");
                        if (callBackArguments) {
                            createWin.close();
                        }
                    }
                })],
                buttonAlign: 'center',
                buttons: [
                    {
                        text: '保存',
                        iconCls: 'icon-save',
                        //scope: me,
                        handler: function () {
                            var postGridPanel = Ext.getCmp("postSelectPersonGridOwner").items.items[0];
                            console.log(postGridPanel);
                            //if (OrientExtUtil.GridHelper.getSelectedRecord(postGridPanel).length == 0) {
                            //    OrientExtUtil.Common.err(OrientLocal.prompt.error, '未选中任何记录');
                            //    return;
                            //}
                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/selectPersonData.rdm', {
                                    selectPersonId: OrientExtUtil.GridHelper.getSelectRecordIds(postGridPanel),
                                    taskId: taskId,
                                    postId: postId,
                                    beforeSelectPersonIds: signPersonIds,
                                    signPersonLogo: true
                                },
                                false, function (response) {
                                    if (response.decodedData.success) {
                                        //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                        //Ext.Msg.alert("提示", response.decodedData.msg);
                                        Ext.getCmp('signWindow').close();  //关闭窗口
                                    } else {
                                        Ext.Msg.alert("提示", response.decodedData.msg);
                                    }
                                    Ext.getCmp('addPost_own_2').store.reload();    //刷新绑定面板
                                    Ext.getCmp('selectPostPanel').store.reload();
                                    //var win = Ext.getCmp('selectPostWindow');
                                    //var gridWin = win.down('panel').down('panel');
                                    //gridWin.store.reload();
                                    //console.log(me);

                                });

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
            createWin.show();
        };
    }
});