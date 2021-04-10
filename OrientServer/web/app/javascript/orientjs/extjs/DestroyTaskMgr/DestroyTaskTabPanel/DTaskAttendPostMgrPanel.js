/**
 * Created by User on 2019/10/14.
 */
Ext.define('OrientTdm.DestroyTaskMgr.DestroyTaskTabPanel.DTaskAttendPostMgrPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.dTaskAttendPostMgrPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_REF_POST_NODE,
        modelName: TDM_SERVER_CONFIG.REF_POST_NODE,
        nodeId: '',
        taskId: '',
        attendPersonIds: '',
        signPersonIds: ''
    },
    id: 'dTaskAttendPost_all',

    initComponent: function () {
        var me = this;

        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var taskId = me.taskId;
        var taskEndState = me.taskEndState;
        var flowId = me.flowId;
        var destroyTaskState = me.destroyTaskState;
        var customerFilter = new CustomerFilter('T_DESTROY_FLOW_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.flowId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],
            id: 'dTaskAttendPost_own_2',
            queryUrl: serviceName + '/destroyRepairMgr/queryDestroyPostData.rdm?flowId=' + me.flowId,
            createUrl: serviceName + '/taskPrepareController/AddAttendPostData.rdm?taskId=' + me.taskId + '&nodeId=' + this.nodeId + '&nodeText=' + this.nodeText,

            afterInitComponent: function () {
                me.addColumn(this.columns, taskId, taskEndState, modelId, flowId);
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
                                        flowId: flowId
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
                                        modelGrid.store.reload();    //刷新绑定面板
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
                var ids = [];
                Ext.each(selectRecords, function (s) {
                    ids.push(s.data.id);
                });
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/deleteTempAttendPostData.rdm', {
                    id: ids.toString()
                }, false, function (resp) {
                    var ret = Ext.decode(resp.responseText);
                    if (ret.success) {
                        modelGrid.fireEvent('refreshGrid');
                        // Ext.Msg.alert("提示", "删除成功！")
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
        if (me.flowId && me.destroyTaskState === '未开始'||me.destroyTaskState === '进行中') {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(false);
        }else {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(true);
        }
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },
    addColumn: function (columns, taskId, destroyTaskState, modelId, flowId) {
        columns.push({
            text: '参与人员',
            align: 'left',
            draggable: false,
            width: 400,
            sortable: false,
            menuDisabled: true,
            renderer: function (value, cellmeta, record, rowIndex, columnIndex, store) {
                //var postId = cellmeta.record.internalId;
                var KeyId = record.raw['ID'];
                var postId = record.raw["C_POST_ID_" + modelId + "_DISPLAY"];
                //var userName = "M_USERS_" + me.modelId + "_display";
                attendPersonIds = record.raw["C_POST_PERSONNEL_" + modelId + "_ID"];
                var userName = record.raw["C_POST_PERSONNEL_" + modelId];
                //console.log(userName_display);
                if (userName == null || userName == '无' || userName == '') {
                    userName = '未设置';

                }
                if (destroyTaskState == '已结束') {
                    //return "<a href='javascript:void(0);' style='color:blue'>" + userName + " </a>";
                    return "<span style='color:black'>" + userName + " </span>";
                } else {
                    return "<a href='javascript:void(0);' style='color:blue' onclick=\"showWinDestroyPerson('" + postId + "','" + attendPersonIds + "','" + KeyId + "')\">" + userName + " </a>";
                }
            }
        });
        showWinDestroyPerson = function (postId, attendPersonIds, KeyId) {
            var me = this;
            //获取已经选中的值
            // var selectedValue = Ext.getCmp("destroyPersonId").getValue();
            var win = Ext.create('Ext.Window', {
                plain: true,
                height: 600,
                width: 800,
                layout: 'fit',
                maximizable: false,
                title: '选择用户',
                modal: true
            });

            var userSelectorPanel = Ext.create('OrientTdm.Common.Extend.Form.Selector.ChooseUserPanel', {
                multiSelect: true,
                selectedValue: attendPersonIds,
                saveAction: function (saveData, callBack) {
                    //保存信息
                    // Ext.getCmp('dTaskAttendPost_all')._setValue(saveData);
                    if (callBack) {
                        /*console.log(saveData);*/
                        var showValues = Ext.Array.pluck(saveData, 'name').join(',');
                        //获取当前form中的控件,并给控件中传值
                        // var allName = Ext.getCmp("selectDestroyPersonId");
                        // allName.setValue(showValues);

                        var selectPersonIds = Ext.Array.pluck(saveData, 'id').join(',');
                        // var allNameIds = Ext.getCmp("destroyPersonId");
                        // allNameIds.setValue(realIds);
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/destroyRepairMgr/saveAttendPersonData.rdm', {
                                id: KeyId,
                                selectPersonId: selectPersonIds,
                                postId: postId,
                                flowId: flowId,
                                // beforeSelectPersonIds: attendPersonIds,
                                // signPersonLogo: false
                            },
                            false, function (response) {
                                if (response.decodedData.success) {
                                    //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                    //Ext.Msg.alert("提示", response.decodedData.msg);
                                    win.close();
                                }
                                Ext.getCmp('dTaskAttendPost_own_2').store.reload();    //刷新绑定面板
                            });
                        callBack.call(this);
                    }
                },
                successCallback: function (resp, callBackArguments) {
                    me.fireEvent("refreshGrid");
                    if (callBackArguments) {
                        createWin.close();
                    }
                }
            });
            win.add(userSelectorPanel);
            win.show();
        };
    }
});