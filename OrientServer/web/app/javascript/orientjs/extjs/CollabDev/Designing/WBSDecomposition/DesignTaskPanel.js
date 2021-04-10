/**
 * 数据包(任务)分解grid
 */
Ext.define('OrientTdm.CollabDev.Designing.WBSDecomposition.DesignTaskPanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.designTaskPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.COLLAB_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_PACKET_DECOMPOSITION,
        modelName: TDM_SERVER_CONFIG.TASK,
        planNodeId: -1
    },
    initComponent: function () {
        var me = this;

        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);

        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            title: '工作包分解',
            region: 'center',
            modelId: modelId,
            templateId: templateId,
            isView: 0,
            showAnalysisBtns: false,
            //createUrl: serviceName + '/designTask/saveTask.rdm?parentNodeId=' + me.planNodeId,
            queryUrl: serviceName + '/designTask/queryTasks.rdm?parentNodeId=' + me.planNodeId,
            updateUrl: serviceName + '/designTask/updateTask.rdm',
            afterInitComponent: function () {
                var thisGrid = this;
                var toolbar = thisGrid.dockedItems[0];
                var addButton = toolbar.child('[text=新增]');
                if (addButton) {
                    addButton.disabled = true;

                    addButton.handler = Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                        //添加开始时间和结束时间的约束
                        var customPanel = button.orientBtnInstance.customPanel;
                        Ext.each(thisGrid.modelDesc.columns, function (column) {
                            if (column.className == 'DateColumnDesc') {
                                var sColumnName = column.sColumnName;
                                //找到页面元素中对应日期选择的field
                                var field = customPanel.down('DateColumnDesc[name=' + sColumnName + ']');
                                if (field) {
                                    field.vtype = 'daterange';
                                    if (sColumnName.indexOf('START') != -1) {
                                        //选中开始时间后，再选择结束时间时，添加置灰的日期校验
                                        field.addListener('select', function () {
                                            var endField = customPanel.down('DateColumnDesc[name=' + sColumnName.replace(/START/, 'END') + ']');
                                            var startDate = field.value;
                                            endField.setMinValue(startDate);
                                        });
                                    }
                                    else if (sColumnName.indexOf('END') != -1) {
                                        //选中结束时间后，再选择开始时间时，添加置灰的日期校验
                                        field.addListener('select', function () {
                                            var startField = customPanel.down('DateColumnDesc[name=' + sColumnName.replace(/END/, 'START') + ']');
                                            var endDate = field.value;
                                            startField.setMaxValue(endDate);
                                        });
                                    }
                                }
                            }
                        });
                    })
                }

                var modButton = toolbar.child('[text=修改]');
                if (modButton) {
                    modButton.disabled = true;
                    modButton.handler = Ext.Function.interceptAfter(modButton, 'handler', function (button) {
                        //添加开始时间和结束时间的约束
                        var customPanel = button.orientBtnInstance.customPanel;
                        Ext.each(thisGrid.modelDesc.columns, function (column) {
                            if (column.className == "DateColumnDesc") {
                                var sColumnName = column.sColumnName;
                                //找到页面元素中对应日期选择的field
                                var field = customPanel.down('DateColumnDesc[name=' + sColumnName + ']');
                                if (field) {
                                    field.vtype = 'daterange';
                                    if (sColumnName.indexOf('START') != -1) {
                                        field.endDateField = sColumnName.replace(/START/, 'END');
                                    }
                                    else if (sColumnName.indexOf('END') != -1) {
                                        field.startDateField = sColumnName.replace(/END/, 'START');
                                    }
                                }
                            }
                        });
                    })
                }
                var delButton = toolbar.child('[text=删除]');
                if (delButton) {
                    delButton.disabled = true;
                }
            }
        });

        Ext.apply(me, {
            layout: 'fit',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);

    },
    refreshDataByPlanNodeId: function (planNodeId) {
        var me = this;
        me.planNodeId = planNodeId;
        var store = me.modelGrid.getStore();
        store.proxy.api.create = serviceName + '/designTask/saveTask.rdm?parentNodeId=' + me.planNodeId;
        store.proxy.api.read = serviceName + '/designTask/queryTasks.rdm?parentNodeId=' + me.planNodeId;
        store.load();
    },
    enableButtons: function () {
        var me = this;
        var grid = me.modelGrid;
        var toolbar = grid.dockedItems.items[2];
        var addButton = toolbar.child('[text=新增]');
        var modifyButton = toolbar.child('[text=修改]');
        var delButton = toolbar.child('[text=删除]');
        if (addButton) {
            addButton.enable();
        }
        if (modifyButton) {
            modifyButton.enable();
        }
        if (delButton) {
            delButton.enable();
        }
    },
    disableButtons: function () {
        var me = this;
        var grid = me.modelGrid;
        var toolbar = grid.dockedItems.items[2];
        var addButton = toolbar.child('[text=新增]');
        var modifyButton = toolbar.child('[text=修改]');
        var delButton = toolbar.child('[text=删除]');
        if (addButton) {
            addButton.disable();
        }
        if (modifyButton) {
            modifyButton.disable();
        }
        if (delButton) {
            delButton.disable();
        }
    }
});