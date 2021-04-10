/**
 * Created by User on 2019/10/14.
 */
Ext.define('OrientTdm.DestroyRepairTemplateMgr.DestroyTabPanel.DTemplateAttendPostMgrPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.dTemplateAttendPostMgrPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_REF_POST_NODE,
        modelName: TDM_SERVER_CONFIG.REF_POST_NODE,
        nodeId: '',
        taskId: ''
    },
    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var flowId = me.flowId;
        var isDestroyTemp=me.isDestroyTemp;
        var customerFilter = new CustomerFilter('T_DESTROY_FLOW_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.flowId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],

            queryUrl: serviceName + "/taskPrepareController/queryAttendPostData.rdm?flowId=" + me.flowId

        });

        var toolbar = [{
            xtype: 'button',
            iconCls: 'icon-create',
            text: '添加',
            handler: function () {
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
                        buttonShow: true,
                        flowTempTypeId: me.flowTempTypeId,
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
                                        flowId: flowId
                                    },
                                    false, function (response) {
                                        if (!response.decodedData.success) {
                                            //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                            Ext.Msg.alert("提示", response.decodedData.msg);
                                        }
                                        //else{
                                        //    Ext.Msg.alert("提示", response.decodedData.msg);
                                        //}
                                        console.log(me.items.items[0]);
                                        me.items.items[0].store.reload();    //刷新绑定面板
                                        createWin.close();  //关闭窗口
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
                        // Ext.Msg.alert("提示","删除成功！")
                        OrientExtUtil.Common.tip('成功', "删除成功！");
                    }
                })
            }
        }];

        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0]);
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].add(toolbar);
        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0]);
        // 隐藏产品配置的按钮
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].hide();
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(false);

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});