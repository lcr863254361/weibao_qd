/**
 * Created by User on 2019/2/28.
 */
Ext.define('OrientTdm.TaskPrepareMgr.Center.TabPanel.PostPersonMgrPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.postPersonMgrPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_ATTEND_PERSON,
        modelName: TDM_SERVER_CONFIG.ATTEND_PERSON,
        modelId: '',
        postIds: '',
        signPostIds: ''
    },

    initComponent: function () {
        var me = this;
        modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
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
            id: 'selectPostPanel',
            queryUrl: serviceName + '/taskPrepareController/getAttendPersonData.rdm?taskId=' + taskId,
            afterInitComponent: function () {
                me.addColumn(this.columns, taskId, taskEndState);
            },
        });

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },

    addColumn: function (columns, taskId, taskEndState) {
        columns.push({
                text: '参与岗位',
                align: 'left',
                draggable: false,
                width: 400,
                sortable: false,
                menuDisabled: true,
                renderer: function (value, cellmeta, record, rowIndex, columnIndex, store) {
                    var id = cellmeta.record.internalId;
                    //var userName = "M_USERS_" + me.modelId + "_display";
                    postIds = record.raw["C_ATTEND_POST_" + modelId];
                    var userName = record.raw["C_ATTEND_POST_" + modelId + "_DISPLAY"];
                    console.log(postIds);
                    //console.log(userName_display);
                    if (userName == null || userName == '无' || userName == '') {
                        userName = '未设置';

                    }
                    if (taskEndState == '已结束') {
                        //return "<a href='javascript:void(0);' style='color:blue'>" + userName + " </a>";
                        return "<span style='color:black'>" + userName + " </span>";
                    } else {
                        return "<a href='javascript:void(0);' style='color:blue' onclick=\"showAttendUserName('" + id + "','" + postIds + "')\">" + userName + " </a>";
                    }
                }
            }
            // , {
            //     text: '签署岗位',
            //     align: 'left',
            //     draggable: false,
            //     width: 400,
            //     sortable: false,
            //     menuDisabled: true,
            //     renderer: function (value, cellmeta, record, rowIndex, columnIndex, store) {
            //         var id = cellmeta.record.internalId;
            //         //var userName = "M_USERS_" + me.modelId + "_display";
            //         signPostIds = record.raw["C_SIGN_POST_" + modelId];
            //         var signPostName = record.raw["C_SIGN_POST_" + modelId + "_DISPLAY"];
            //         console.log(signPostIds);
            //         //console.log(userName_display);
            //         if (signPostName == null || signPostName == '无' || signPostName == '') {
            //             signPostName = '未设置';
            //
            //         }
            //         if (taskEndState == '已结束') {
            //             //return "<a href='javascript:void(0);' style='color:blue'>" + signPostName + " </a>";
            //             return "<span style='color:black'>" + signPostName + " </span>";
            //         } else {
            //             return "<a href='javascript:void(0);' style='color:blue' onclick=\"showWinsignUserName('" + id + "','" + signPostIds + "')\">" + signPostName + " </a>";
            //         }
            //     }
            // }
        );

        showAttendUserName = function (id, postIds) {
            //弹出新增面板窗口
            var createWin = Ext.create('Ext.Window', {
                title: '选择人员参与岗位',
                id: 'attendWindow',
                plain: true,
                height: 0.5 * globalHeight,
                width: 0.5 * globalWidth,
                layout: 'fit',
                maximizable: true,
                modal: true,
                items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.PostPersonnelMgrPanel', {
                    attendKeyId: id,
                    taskId: taskId,
                    buttonShow: true,
                    postIds: postIds,
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
                            var allSelectedRecords = postGridPanel.ownerCt.allSelectedRecords;
                            //if (OrientExtUtil.GridHelper.getSelectedRecord(postGridPanel).length == 0) {
                            //    OrientExtUtil.Common.err(OrientLocal.prompt.error, '未选中任何记录');
                            //    return;
                            //}
                            var retVal = [];
                            Ext.each(allSelectedRecords, function (record) {
                                if (retVal.indexOf(record.get("id"))==-1) {
                                    retVal.push(record.get("id"));
                                }
                            });

                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/addPostData.rdm', {
                                    // selectId: OrientExtUtil.GridHelper.getSelectRecordIds(postGridPanel),
                                    selectId: retVal,
                                    taskId: taskId,
                                    attendKeyId: id
                                },
                                false, function (response) {
                                    if (response.decodedData.success) {
                                        //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                        //Ext.Msg.alert("提示", response.decodedData.msg);
                                        Ext.getCmp('attendWindow').close();
                                    } else {
                                        Ext.Msg.alert("提示", response.decodedData.msg);
                                    }
                                    Ext.getCmp('selectPostPanel').store.reload();    //刷新绑定面板
                                    var win = Ext.getCmp('selectPostWindow');
                                    var gridWin = win.down('panel').down('panel');
                                    gridWin.store.reload();
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
        showWinsignUserName = function (id, signPostIds) {
            var me = this;
            //弹出新增面板窗口
            var createWin = Ext.create('Ext.Window', {
                title: '选择人员签署岗位',
                plain: true,
                height: 0.5 * globalHeight,
                width: 0.5 * globalWidth,
                layout: 'fit',
                maximizable: true,
                id: 'signWindow',
                modal: true,
                items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.PostPersonnelMgrPanel', {
                    attendKeyId: id,
                    taskId: taskId,
                    buttonShow: true,
                    postIds: signPostIds,
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
                            //if (OrientExtUtil.GridHelper.getSelectedRecord(postGridPanel).length == 0) {
                            //    OrientExtUtil.Common.err(OrientLocal.prompt.error, '未选中任何记录');
                            //    return;
                            //}
                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/addPostData.rdm', {
                                    selectId: OrientExtUtil.GridHelper.getSelectRecordIds(postGridPanel),
                                    taskId: taskId,
                                    attendKeyId: id,
                                    signPostLogo: true
                                },
                                false, function (response) {
                                    if (response.decodedData.success) {
                                        //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                        //Ext.Msg.alert("提示", response.decodedData.msg);
                                        Ext.getCmp('signWindow').close();  //关闭窗口
                                    } else {
                                        Ext.Msg.alert("提示", response.decodedData.msg);
                                    }
                                    Ext.getCmp('selectPostPanel').store.reload();    //刷新绑定面板
                                    var win = Ext.getCmp('selectPostWindow');
                                    var gridWin = win.down('panel').down('panel');
                                    gridWin.store.reload();
                                    console.log(me);

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