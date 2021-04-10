Ext.define('OrientTdm.ScientistTaskPrepareMgr.ScientistPlanGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.scientistPlanGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_DIVING__PLAN_TABLE,
        modelName: TDM_SERVER_CONFIG.DIVING__PLAN_TABLE
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var taskId = me.taskId;
        var taskName = me.taskName;
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var customerFilter = new CustomerFilter('T_DIVING_TASK_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.taskId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            queryUrl: serviceName + "/accountingForm/getScientistPlanData.rdm?taskName=" + taskName,
            customerFilter: [customerFilter],
            afterInitComponent: function () {
                var me = this;
                var toolbar = me.dockedItems[0];
                toolbar.add({
                        iconCls: 'icon-create',
                        text: '快速新增',
                        handler: function () {
                            Ext.Ajax.request({
                                url: serviceName + '/accountingForm/easyAddDivingPlan.rdm',
                                params: {
                                    taskId: taskId,
                                    modelId: me.modelId
                                },
                                success: function (response) {
                                    var gridPanel = me.up("panel");
                                    gridPanel.down("panel").store.reload();
                                }
                            });
                        }
                    }, {
                        iconCls: 'icon-create',
                        text: '快速复制',
                        handler: function () {
                            var selections = modelGrid.getSelectionModel().getSelection();
                            if (selections.length === 0) {
                                OrientExtUtil.Common.tip(OrientLocal.prompt.info, "请选择一条记录再进行复制！");
                            } else if (selections.length > 1) {
                                OrientExtUtil.Common.tip(OrientLocal.prompt.info, "只能选择一条记录进行复制！");
                            } else {
                                    Ext.Ajax.request({
                                        url: serviceName + '/accountingForm/easyCopyDivingPlan.rdm',
                                        params: {
                                            taskId: taskId,
                                            divingPlanId: selections[0].raw.ID
                                        },
                                        success: function (response) {
                                            var gridPanel = me.up("panel");
                                            gridPanel.down("panel").store.reload();
                                        }
                                    });
                            }
                        }
                    },
                    {
                        iconCls: 'icon-delete',
                        text: '删除',
                        handler: function () {
                            if (!OrientExtUtil.GridHelper.hasSelected(modelGrid)) {
                                return;
                            }
                            var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                            var ids = [];
                            Ext.each(selectRecords, function (s) {
                                if (s.raw['C_TABLE_STATE_' + modelId] == '当前') {
                                    OrientExtUtil.Common.err(OrientLocal.prompt.info, '不能删除当前下潜计划!');
                                    return;
                                } else {
                                    ids.push(s.data.id);
                                }
                            });
                            if (ids.length > 0) {
                                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/accountingForm/deleteScientistPlanData.rdm', {
                                    id: ids.toString()
                                }, false, function (resp) {
                                    if (resp.decodedData.success) {
                                        // OrientExtUtil.Common.tip(OrientLocal.prompt.info, resp.decodedData.msg);
                                        var gridPanel = me.up("panel");
                                        gridPanel.down("panel").store.reload();
                                    } else {
                                        OrientExtUtil.Common.err(OrientLocal.prompt.info, resp.decodedData.msg);
                                    }
                                    // var ret = Ext.decode(resp.responseText);
                                })
                            }
                        },
                        scope: me
                    });
                //排序
                this.getStore().sort([{
                    "property": "C_SERIAL_NUMBER_" + modelId,
                    "direction": "ASC"
                }]);
            }
        });
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].setVisible(false);
        //增加查看按钮
        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            columns.push(
                {
                    xtype: 'actioncolumn',
                    text: '编辑表格',
                    align: 'center',
                    width: 300,
                    items: [
                        {
                            iconCls: 'icon-update',
                            tooltip: '编辑表格',
                            handler: function (grid, rowIndex) {
                                var me = this;
                                var data = grid.getStore().getAt(rowIndex);
                                var divingPlanId = data.raw.ID;
                                var taskId = grid.up('panel').up('panel').taskId;
                                var scientistPlanTablePanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                                    region: 'center',
                                    taskId: taskId,
                                    productId: "",
                                    flowId: '',
                                    taskEndState: me.taskEndState,
                                    items: [{
                                        layout: "fit",
                                        html: '<iframe  id="scientitsPlanTableIframe" frameborder="0" style="margin-left: 0px;" src="' + 'accountingForm/getScientistDivingPlanData.rdm?taskId=' + taskId + '&divingPlanId=' + divingPlanId + "&modelId=" + modelId
                                            + '"></iframe>'
                                    }]
                                });
                                var win = Ext.create('Ext.window.Window', {
                                    id: 'scientistPlanWin',
                                    title: '科学家填写下潜计划表',
                                    titleAlign: 'center',
                                    height: 0.9 * globalHeight,
                                    width: 0.9 * globalWidth,
                                    region: 'center',
                                    layout: 'fit',
                                    maximizable: true,
                                    minimizable: false,
                                    modal: true,
                                    constrain: true,   //限制窗口不超出浏览器边界
                                    listeners: {
                                        'afterrender': function () {
                                            window.onresize = function () {
                                                if (Ext.getCmp("scientistPlanWin") == undefined) {
                                                    return;
                                                }
                                                changeFrameHeight(scientistPlanTablePanel, win);
                                            };
                                            changeFrameHeight(scientistPlanTablePanel, win);
                                        },
                                        close: function () {
                                            //刷新grid
                                            modelGrid.store.load();
                                        },
                                        'maximize': function () {
                                            window.onresize = function () {
                                                if (Ext.getCmp("scientistPlanWin") == undefined) {
                                                    return;
                                                }
                                                maxWindow(scientistPlanTablePanel, win);
                                            };
                                            maxWindow(scientistPlanTablePanel, win);
                                        },
                                        'restore': function () {
                                            window.onresize = function () {
                                                if (Ext.getCmp("scientistPlanWin") == undefined) {
                                                    return;
                                                }
                                                changeFrameHeight(scientistPlanTablePanel, win);
                                            };
                                            changeFrameHeight(scientistPlanTablePanel, win);
                                        }
                                    },
                                    items: [scientistPlanTablePanel]
                                });
                                win.show();
                            }
                        }
                    ]
                },
                // {
                //     xtype: 'actioncolumn',
                //     text: '绘图',
                //     align: 'center',
                //     width: 300,
                //     items: [
                //         {
                //             iconCls: 'icon-update',
                //             tooltip: '绘图',
                //             handler: function (grid, rowIndex) {
                //                 var me = this;
                //                 var data = grid.getStore().getAt(rowIndex);
                //                 var divingPlanId = data.raw.ID;
                //                 var taskId = grid.up('panel').up('panel').taskId;
                //                 var scientistPlanTablePanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                //                     region: 'center',
                //                     taskId: taskId,
                //                     productId: "",
                //                     flowId: '',
                //                     taskEndState: me.taskEndState,
                //                     items: [{
                //                         layout: "fit",
                //                         html: '<iframe  id="scientitsPlanTableIframe" frameborder="0" width="80%" height="80%" style="margin-left: 0px;" src="' + 'accountingForm/getScientistPicturePreview.rdm?taskId=' + taskId + '&divingPlanId=' + divingPlanId
                //                             + '"></iframe>'
                //                     }]
                //                 });
                //
                //                 var win = Ext.create('Ext.window.Window', {
                //                     title: '绘图',
                //                     titleAlign: 'center',
                //                     height: 0.9 * globalHeight,
                //                     width: 0.9 * globalWidth,
                //                     layout: 'fit',
                //                     maximizable: true,
                //                     modal: true,
                //                     constrain: true,   //限制窗口不超出浏览器边界
                //                     items: [scientistPlanTablePanel]
                //                 });
                //                 win.show();
                //
                //                 window.onresize = function () {
                //                     changeFrameHeight();
                //                 };
                //                 changeFrameHeight();
                //
                //                 function changeFrameHeight() {
                //                     var cwin = document.getElementById('scientitsPlanTableIframe');
                //                     console.log(cwin)
                //                     cwin.width = document.documentElement.clientWidth - 250;
                //                     cwin.height = document.documentElement.clientHeight - 140;
                //                     win.setHeight(cwin.height);
                //                     win.setWidth(cwin.width);
                //                 }
                //             }
                //         }
                //     ]
                // },
                // {
                //     xtype: 'actioncolumn',
                //     text: '图片预览',
                //     align: 'center',
                //     width: 300,
                //     items: [
                //         {
                //             iconCls: 'icon-attach',
                //             tooltip: '图片预览',
                //             handler: function (grid, rowIndex) {
                //                 var me = this;
                //                 var data = grid.getStore().getAt(rowIndex);
                //                 var divingPlanId = data.raw.ID;
                //                 var taskId = grid.up('panel').up('panel').taskId;
                //                 var scientistPlanTablePanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                //                     region: 'center',
                //                     taskId: taskId,
                //                     productId: "",
                //                     flowId: '',
                //                     taskEndState: me.taskEndState,
                //                     items: [{
                //                         layout: "fit",
                //                         html: '<iframe  id="scientitsPlanTableIframe" frameborder="0" width="80%" height="80%" style="margin-left: 0px;" src="' + 'accountingForm/getScientistPicturePreviewFile.rdm?taskId=' + taskId + '&divingPlanId=' + divingPlanId
                //                             + '"></iframe>'
                //                     }]
                //                 });
                //
                //                 var win = Ext.create('Ext.window.Window', {
                //                     title: '图片预览',
                //                     titleAlign: 'center',
                //                     height: 0.9 * globalHeight,
                //                     width: 0.9 * globalWidth,
                //                     layout: 'fit',
                //                     maximizable: true,
                //                     modal: true,
                //                     constrain: true,   //限制窗口不超出浏览器边界
                //                     items: [scientistPlanTablePanel]
                //                 });
                //                 win.show();
                //
                //                 window.onresize = function () {
                //                     changeFrameHeight();
                //                 };
                //                 changeFrameHeight();
                //
                //                 function changeFrameHeight() {
                //                     var cwin = document.getElementById('scientitsPlanTableIframe');
                //                     console.log(cwin)
                //                     cwin.width = document.documentElement.clientWidth - 250;
                //                     cwin.height = document.documentElement.clientHeight - 140;
                //                     win.setHeight(cwin.height);
                //                     win.setWidth(cwin.width);
                //                 }
                //             }
                //         }
                //     ]
                // }
            )
        }
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(), columns);
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);

        function changeFrameHeight(scientistPlanTablePanel, win) {
            win.center();
            var cwin = document.getElementById('scientitsPlanTableIframe');
            cwin.width = win.getWidth();
            cwin.height = win.getHeight() - 38;
            scientistPlanTablePanel.setHeight(cwin.height);
            scientistPlanTablePanel.setWidth(cwin.width);
        }

        function maxWindow(scientistPlanTablePanel, win) {
            win.center();
            var cwin = document.getElementById('scientitsPlanTableIframe');
            cwin.width = document.documentElement.clientWidth;
            cwin.height = document.documentElement.clientHeight - 38;
            scientistPlanTablePanel.setHeight(cwin.height);
            scientistPlanTablePanel.setWidth(cwin.width);
        }
    }
});