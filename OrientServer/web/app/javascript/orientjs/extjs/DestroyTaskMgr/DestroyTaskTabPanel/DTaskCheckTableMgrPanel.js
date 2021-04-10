/**
 * Created by User on 2019/10/14.
 */
Ext.define('OrientTdm.DestroyTaskMgr.DestroyTaskTabPanel.DTaskCheckTableMgrPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.dTaskCheckTableMgrPanel',
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
        var isDestroyTask = me.isDestroyTask;
        var customerFilter = new CustomerFilter('T_DESTROY_FLOW_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.flowId);
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
            id: 'dTaskCheckTableTempGrid_own_1',
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
                            flowId: flowId,
                            isDestroyTask: isDestroyTask,
                            successCallback: function (resp, callBackArguments) {
                                me.fireEvent("refreshGrid");
                                if (callBackArguments) {
                                    createWin.close();
                                }
                            }
                        })]
                    })
                ;
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
        if (me.flowId && me.destroyTaskState === '未开始'||me.destroyTaskState === '进行中') {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(false);
        } else if (me.flowId && me.isDestroyTemp) {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(false);
        } else {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(true);
        }

        //增加检查表实例查看按钮
        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            columns.push(
                {
                    xtype: 'actioncolumn',
                    text: '关联产品结构',
                    align: 'center',
                    width: 100,
                    items: [{
                        iconCls: 'icon-detail',
                        tooltip: '产品结构树',
                        handler: function (grid, rowIndex) {
                            var data = grid.getStore().getAt(rowIndex);
                            var checkTableInstId = data.raw.ID;
                            var createWin = Ext.create('Ext.Window', {
                                title: '产品结构树',
                                plain: true,
                                height: 0.5 * globalHeight,
                                width: 0.5 * globalWidth,
                                layout: 'fit',
                                maximizable: true,
                                modal: true,

                                items: [Ext.create('OrientTdm.FormTemplateMgr.ProductStructureTree.ProductStructureTree', {
                                    checkTableInstId:checkTableInstId,
                                    isInst:true
                                })],
                                buttonAlign: 'center',
                                buttons: [
                                    {
                                        text: '保存',
                                        iconCls: 'icon-save',
                                        //scope: me,
                                        handler: function () {
                                            var tree =createWin.down('panel');
                                            var nodes = OrientExtUtil.TreeHelper.getSelectNodes(tree)[0];
                                            console.log(OrientExtUtil.TreeHelper.getSelectNodeIds(tree).length);
                                            var level = nodes.raw['level'];
                                            if (level == 2 || level == 3 || level == 4) {
                                                OrientExtUtil.Common.err(OrientLocal.prompt.error, '只能选择零件或零件子节点');
                                                return;
                                            }
                                            if (tree.getChecked().length > 1) {
                                                OrientExtUtil.Common.err(OrientLocal.prompt.error, '只能选择一个零件或零件子节点');
                                                return;
                                            }
                                            var treeId;
                                            if(tree.getChecked().length==0){
                                                treeId='';
                                            }else{
                                                treeId=tree.getChecked()[0].raw['id'];
                                            }
                                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/destroyRepairMgr/saveChooseProductTree.rdm', {
                                                treeId: treeId,
                                                checkTableInstId: checkTableInstId
                                            }, false, function (response) {
                                                if (response.decodedData.success) {
                                                    // OrientExtUtil.AjaxHelper.doRequest(serviceName + '/destroyRepairMgr/getChooseProductTree.rdm', {
                                                    //     checkTableInstId: checkTableInstId
                                                    // }, false, function (response) {
                                                    //     me.productName = response.decodedData.results;
                                                    //     var record = me.grid.getStore().getAt(me.rowIndex);
                                                    //     record.set("已选的节点", me.productName);
                                                    //     record.commit();
                                                    // });
                                                    createWin.close();
                                                } else {
                                                    Ext.Msg.alert("提示", response.decodedData.msg);
                                                }
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
                            createWin.down('panel').expandAll();
                            createWin.show();
                        }
                    }
                    ]
                }
                // , {
                //     text: '已选的产品结构',
                //     align: 'center',
                //     draggable: false,
                //     width: 100,
                //     sortable: false,
                //     menuDisabled: true,
                //     renderer: function (value, cellmeta, record, rowIndex, columnIndex, store) {
                //         var productName;
                //         OrientExtUtil.AjaxHelper.doRequest(serviceName + '/destroyRepairMgr/getChooseProductTree.rdm', {
                //             checkTableInstId: checkTableInstId
                //         }, false, function (response) {
                //             productName = response.decodedData.results
                //         });
                //         return productName;
                //     }
                // }
                , {
                    xtype: 'actioncolumn',
                    text: '查看',
                    align: 'center',
                    width: 200,
                    items: [{
                        iconCls: 'icon-detail',
                        tooltip: '表格实例查看',
                        handler: function (grid, rowIndex) {
                            var data = grid.getStore().getAt(rowIndex);
                            // var checkTempId = data.raw.ID;
                            // var productId = '';
                            // me.preview(checkTempId, true, productId);
                            var instanceId = data.raw.ID;
                            Ext.create('Ext.Window', {
                                    plain: true,
                                    title: '预览',
                                    height: globalHeight * 0.9,
                                    width: globalWidth * 0.9,
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
    // preview: function (checkTempId, withData, productId) {
    //     //表单预览
    //     TestProcessUtil.CommonHelper.checkListPreview(checkTempId, true, withData, productId);
    // }
});