/**
 * Created by User on 2019/10/14.
 */
Ext.define('OrientTdm.DestroyRepairTemplateMgr.DestroyTabPanel.DTemplateCheckTableMgrPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.dTemplateCheckTableMgrPanel',
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
        var flowId = me.flowId;
        var destroyTaskState = me.destroyTaskState;
        var customerFilter = new CustomerFilter('T_DESTROY_FLOW_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.flowId);
        //根据节点和任务id
        //var filter = me.addCustomerFilterTable(modelId);
        console.log(me);
        var isDestroyTemp=me.isDestroyTemp;
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],
            id: 'destroyCheckTableTempGrid_own_1'
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
                        flowId: flowId,
                        isDestroyTemp:isDestroyTemp,
                        successCallback: function (resp, callBackArguments) {
                            me.fireEvent("refreshGrid");
                            if (callBackArguments) {
                                createWin.close();
                            }
                        }
                    })]
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
        }];
        //toolbar.setDisabled(true);

        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0]);
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].add(toolbar);
        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0]);
        //隐藏产品配置的按钮
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].hide();
        if (me.flowId && me.isDestroyTemp) {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(false);
        }

        //增加检查表实例查看按钮
        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            columns.push(
                {
                    xtype: 'actioncolumn',
                    text: '关联产品结构',
                    align: 'center',
                    width: 200,
                    items: [{
                        iconCls: 'icon-detail',
                        tooltip: '关联产品结构',
                        handler: function (grid, rowIndex) {
                            var data = grid.getStore().getAt(rowIndex);
                            var checkTempId = data.raw.ID;
                            var productId = '';
                            me.preview(checkTempId, true, productId);
                        }
                    }]
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
    },
    preview: function (checkTempId, withData, productId) {
        //表单预览
        TestProcessUtil.CommonHelper.checkListPreview(checkTempId, true, withData, productId,true);
    }
});