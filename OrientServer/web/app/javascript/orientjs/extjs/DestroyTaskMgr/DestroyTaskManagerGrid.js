/**
 * Created by User on 2019/9/20.
 */
Ext.define('OrientTdm.DestroyTaskMgr.DestroyTaskManagerGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.destroyTaskManagerGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_DESTROY_TASK,
        modelName: TDM_SERVER_CONFIG.DESTROY_TASK
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            isDestroyButton: true,
            afterInitComponent: function () {
                // var toolbar = this.dockedItems[0];
                // toolbar.add(
                //     {
                //         text: '删除',
                //         iconCls: 'icon-delete',
                //         handler: me._deletePostTemp,
                //         scope: me
                //     }
                // );
                var me = this;
                me.viewConfig.getRowClass = function (record, rowIndex, rowParams, store) {
                    if (record.data['C_STATE_' + modelId] == '未开始') {
                        return 'x-grid-record-grey';
                    } else if (record.data['C_STATE_' + modelId] == '进行中') {
                        return 'x-grid-record-orange';
                    } else if (record.data['C_STATE_' + modelId] == '已结束') {
                        return 'x-grid-record-green';
                    }
                },
                    //排序
                    this.getStore().sort([{
                        "property": "ID",
                        "direction": "DESC"
                    }]);
            }
        });
        //增加备品备件实例查看按钮
        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            columns.push({
                xtype: 'actioncolumn',
                text: '流程进度',
                align: 'center',
                width: 200,
                items: [{
                    iconCls: 'icon-detail',
                    tooltip: '流程进度',
                    handler: function (grid, rowIndex) {
                        var data = grid.getStore().getAt(rowIndex);
                        var destroyTaskId = data.raw.ID;
                        var destroyTaskName = data.raw['C_NAME_' + modelId];
                        var destroyTaskState = data.raw['C_STATE_' + modelId];
                        Ext.create('Ext.Window', {
                                plain: true,
                                title: '查看【<span style="color: blue; ">' + destroyTaskName + '</span>】流程进度',
                                // titleAlign: 'center',
                                height: globalHeight * 0.9,
                                width: globalWidth * 0.9,
                                layout: 'fit',
                                maximizable: true,
                                modal: true,
                                autoScroll: true,
                                items: [
                                    Ext.create('OrientTdm.DestroyTaskMgr.DestroyTaskGanttMgr.DestroyTaskGanttPanel', {
                                        destroyTaskId: destroyTaskId,
                                        destroyTaskName: destroyTaskName,
                                        modelName: me.modelName,
                                        dataId: destroyTaskId,
                                        modelId: modelId,
                                        destroyTaskState: destroyTaskState
                                    })
                                ]
                            }
                        ).show();
                    }
                }
                ]
            }, {
                xtype: 'actioncolumn',
                text: '任务启动',
                align: 'center',
                width: 200,
                items: [{
                    iconCls: 'icon-startproject',
                    tooltip: '任务启动',
                    handler: function (grid, rowIndex) {
                        var data = grid.getStore().getAt(rowIndex);
                        var destroyTaskId = data.raw.ID;
                        var destroyTaskState = data.raw['C_STATE_' + modelId];
                        if (destroyTaskState === '未开始') {
                            Ext.MessageBox.confirm('提示', '是否启动任务？', function (btn) {
                                if (btn == 'yes') {
                                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/destroyRepairMgr/destroyTaskBegin.rdm', {
                                        destroyTaskId: destroyTaskId
                                    }, false, function (resp) {
                                        if (resp.decodedData.success) {
                                            //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                            // Ext.Msg.alert("提示", "成功启动拆解任务！");
                                            modelGrid.getStore().load();
                                        }
                                    });
                                }
                            });
                        } else if (destroyTaskState === '进行中') {
                            Ext.MessageBox.confirm('提示', '是否结束任务？', function (btn) {
                                if (btn == 'yes') {
                                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/destroyRepairMgr/destroyTaskBegin.rdm', {
                                        destroyTaskId: destroyTaskId
                                    }, false, function (resp) {
                                        if (resp.decodedData.success) {
                                            //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                            // Ext.Msg.alert("提示", resp.decodedData.msg);
                                            modelGrid.getStore().load();
                                        }
                                    });
                                }
                            });
                        } else if (destroyTaskState === '已结束') {
                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/destroyRepairMgr/destroyTaskBegin.rdm', {
                                destroyTaskId: destroyTaskId
                            }, false, function (resp) {
                                if (resp.decodedData.success) {
                                    //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                    // Ext.Msg.alert("提示", resp.decodedData.msg);
                                    modelGrid.getStore().load();
                                } else {
                                    // Ext.Msg.alert("提示", resp.decodedData.msg);
                                }
                            });
                        }
                    }
                }
                ]
            })
        }
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(), columns);
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});