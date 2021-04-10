Ext.define('OrientTdm.HistoryTaskMgr.Center.TaskMgrDataShowRegion', {
    alias: 'widget.dataShowRegion',
    extend: 'OrientTdm.Common.Extend.Panel.OrientTabPanel',
    requires: [
        "OrientTdm.Common.Extend.Grid.OrientModelGrid"
    ],
    config: {
        belongFunctionId: ''
    },
    initComponent: function () {
        var me = this;
        Ext.apply(me,
            {
                items: [
                    {
                        title: '简介',
                        iconCls: 'icon-basicInfo',
                        //html: '<h1>数据管理...此处可也添加HTML，介绍功能点主要用途</h1>'
                        html: '<iframe width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" src = "' + serviceName +
                            '/app/views/introduction/DataManage.jsp?"></iframe>'
                    }
                ],
                activeItem: 0
            }
        );
        me.callParent(arguments);
        me.addEvents("initModelDataByNode", 'initModelDataBySchemaNode');
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'initModelDataByNode', me.initModelDataByNode, me);
        me.mon(me, 'initModelDataBySchemaNode', me._initModelDataBySchemaNode, me);
    },
    _initModelDataBySchemaNode: function (treeNode) {
        var modelId = treeNode.raw.modelId;
        var me = this;
        me.items.each(function (item, index) {
            if (0 != index) {
                me.remove(item);
            }
        });
        var initParams = {
            modelId: modelId,
            isView: 0,
            region: 'center'
        };
        var modelGrid = Ext.create("OrientTdm.Common.Extend.Grid.OrientModelGrid", initParams);
        me.add({
            layout: 'border',
            title: treeNode.get('text'),
            iconCls: treeNode.get('iconCls'),
            items: [
                modelGrid, {
                    xtype: 'orientPanel',
                    region: 'south',
                    collapsible: true,
                    collapseMode: 'mini',
                    header: false,
                    collapsed: true,
                    layout: 'fit',
                    listeners: {
                        beforeshow: function () {

                        }
                    }
                }
            ]
        });
        me.setActiveTab(1);
    },
    initModelDataByNode: function (treeNode) {
        var me = this;
        var tbomModels = treeNode.raw.tBomModels;
        me.items.each(function (item, index) {
            if (0 != index) {
                me.remove(item);
            }
        });
        if (tbomModels.length == 0) {
            //保留首页
        } else {
            //保留首页
            Ext.each(tbomModels, function (tbomMode) {
                var initParams = {
                    modelId: tbomMode.modelId,
                    isView: tbomMode.type,
                    customerFilter: [tbomMode.defaultFilter],
                    templateId: tbomMode.templateId,
                    bindNode: treeNode,
                    region: 'center',
                    usePage: tbomMode.usePage,
                    pageSize: tbomMode.pageSize
                };
                var modelGrid;
                if (tbomMode.modelName === '下潜任务' && treeNode.raw.level === 3) {
                    var taskGridPanel;
                    if (!Ext.isEmpty(tbomMode.templateJS)) {
                        taskGridPanel = Ext.create(tbomMode.templateJS, initParams);
                    } else {
                        var customerFilter = new CustomerFilter('C_STATE_' + tbomMode.modelId, CustomerFilter.prototype.SqlOperation.Equal, '', "已结束");
                        initParams.customerFilter.push(customerFilter);
                        taskGridPanel = Ext.create("OrientTdm.Common.Extend.Grid.OrientModelGrid", initParams);
                    }
                    modelGrid = Ext.create('Ext.panel.Panel', {
                        region: 'center',
                        layout: 'border',
                        items: [taskGridPanel]
                    });
                    // me.setActiveTab(2);
                } else if (tbomMode.modelName === '下潜任务' && treeNode.raw.level === 4) {
                    var taskGridPanel;
                    if (!Ext.isEmpty(tbomMode.templateJS)) {
                        taskGridPanel = Ext.create(tbomMode.templateJS, initParams);
                    } else {
                        var customerFilter = new CustomerFilter('C_STATE_' + tbomMode.modelId, CustomerFilter.prototype.SqlOperation.Equal, '', "已结束");
                        initParams.customerFilter.push(customerFilter);
                        taskGridPanel = Ext.create("OrientTdm.Common.Extend.Grid.OrientModelGrid", initParams);
                        var columns = taskGridPanel.columns;
                        if (Ext.isArray(columns)) {
                            columns.push(
                                {
                                    xtype: 'actioncolumn',
                                    text: '任务编制',
                                    align: 'center',
                                    width: 200,
                                    // id: 'actionColumn',
                                    items: [{
                                        iconCls: 'icon-NORMAL',
                                        tooltip: '任务编制',
                                        handler: function (grid, rowIndex) {
                                            var data = grid.getStore().getAt(rowIndex);
                                            me.taskId = data.raw.ID;
                                            me.taskName = data.raw['C_TASK_NAME_' + modelId];
                                            me.taskEndState = data.raw['C_STATE_' + modelId];
                                            me.seaArea = data.raw['C_SEA_AREA_' + modelId];
                                            me.weidu = data.raw['C_WEIDU_' + modelId];
                                            me.jingdu = data.raw['C_JINGDU_' + modelId];
                                            me.planDivingDepth = data.raw['C_PLAN_DIVING_DEPTH_' + modelId];//var centerPanel = grid.ownerCt.up("taskPrepareManagerDashboard").centerPanel;// var centerPanel = Ext.getCmp("taskPrepareCenterPanel");
                                            me.items.each(function (item, index) {
                                                me.remove(item);
                                            });
                                            var panel = Ext.create('OrientTdm.TaskPrepareMgr.MxGraphEditor.MxGraphEditorPanel', {
                                                region: 'center',
                                                taskId: me.taskId,
                                                //颜色表示进度 无工具栏
                                                isStatusSearch: me.isStatusSearch,
                                                flowId: me.flowId,
                                                taskEndState: me.taskEndState
                                                //treeNode: treeNode
                                            });
                                            var teststageTabPanel = Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.SetsMgrStageTabPanel', {
                                                layout: 'fit',
                                                height: '45%',
                                                region: 'south',
                                                animCollapse: true,
                                                collapsible: true,
                                                isStatusSearch: me.isStatusSearch,
                                                taskId: me.taskId,
                                                taskEndState: me.taskEndState
                                                //nodeId: me.nodeId,
                                                //nodeText: me.nodeText
                                            });
                                            me.add({
                                                title: '流程设计',
                                                //iconCls: record.raw['iconCls'],
                                                layout: 'border',
                                                items: [panel, teststageTabPanel],
                                                panel: panel,
                                                stageTabPanel: teststageTabPanel
                                            });
                                            var divingPlanTablePanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                                                region: 'center',
                                                taskId: me.taskId,
                                                productId: "",
                                                hangduanId: me.flowId,
                                                flowId: '',
                                                taskEndState: me.taskEndState,
                                                items: [{
                                                    layout: "fit",
                                                    html: '<iframe  id="planTableIframe" frameborder="0"  style="margin-left: 0px;" src="' + 'accountingForm/getDivingPlanTableData.rdm?taskId=' + me.taskId + '&hangduanId=' + me.flowId
                                                        + '"></iframe>'
                                                }]
                                            });

                                            me.add(
                                                {
                                                    title: '下潜计划表',
                                                    layout: 'border',
                                                    items: [divingPlanTablePanel],
                                                    divingPlanPanel: divingPlanTablePanel,
                                                    listeners: {
                                                        'beforeshow': function () {
                                                            if (Ext.get('planTableIframe')) {
                                                                window.onresize = function () {
                                                                    changeFrameHeight();
                                                                };
                                                                changeFrameHeight();

                                                                function changeFrameHeight() {
                                                                    var cwin = document.getElementById('planTableIframe');
                                                                    cwin.width = document.documentElement.clientWidth - 250;
                                                                    cwin.height = document.documentElement.clientHeight - 140;
                                                                    divingPlanTablePanel.setHeight(cwin.height);
                                                                    divingPlanTablePanel.setWidth(cwin.width);
                                                                }
                                                            }
                                                            //刷新iframe窗口
                                                            window.parent.document.getElementById('planTableIframe').contentWindow.document.location.reload();
                                                        }
                                                    }
                                                }
                                            );

                                            var divingDeviceTablePanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                                                region: 'center',
                                                taskId: me.taskId,
                                                productId: "",
                                                hangduanId: me.flowId,
                                                flowId: '',
                                                taskEndState: me.taskEndState,
                                                items: [{
                                                    layout: "fit",
                                                    // html:'<iframe  frameborder="0" width="800" height="800" style="margin-left: 300px" src="'+'app/javascript/orientjs/extjs/TaskPrepareMgr/Accounting/divingDeviceWeight.jsp?hangduanId='+me.flowId+ '&taskId=' + me.taskId
                                                    //     +'"></iframe>'
                                                    // }]

                                                    html: '<iframe  id="divingDeviceIframe" frameborder="0" width="50%" height="80%" style="margin-left: 0px" src="' + 'accountingForm/getDivingDeviceData.rdm?taskId=' + me.taskId + '&hangduanId=' + me.flowId
                                                        + '"></iframe>'
                                                }]
                                            });
                                            me.add(
                                                {
                                                    title: '潜水器重量',
                                                    layout: 'border',
                                                    items: [divingDeviceTablePanel],
                                                    divingDeviceTablePanel: divingDeviceTablePanel,
                                                    listeners: {
                                                        'beforeshow': function () {
                                                            if (Ext.get('divingDeviceIframe')) {
                                                                window.onresize = function () {
                                                                    changeFrameHeight();
                                                                };
                                                                changeFrameHeight();

                                                                function changeFrameHeight() {
                                                                    var cwin = document.getElementById('divingDeviceIframe');
                                                                    cwin.width = document.documentElement.clientWidth - 250;
                                                                    cwin.height = document.documentElement.clientHeight - 140;
                                                                    divingDeviceTablePanel.setHeight(cwin.height);
                                                                    divingDeviceTablePanel.setWidth(cwin.width);
                                                                }
                                                            }
                                                            //刷新iframe窗口
                                                            window.parent.document.getElementById('divingDeviceIframe').contentWindow.document.location.reload();
                                                        }
                                                    }
                                                }
                                            );
                                            var currentOutTemplateGridPanel = Ext.create('OrientTdm.TaskPrepareMgr.Accounting.CurrentOutTemplateGridPanel', {
                                                region: 'center',
                                                taskId: me.taskId,
                                                taskName: me.taskName,
                                                hangduanId: me.flowId
                                            });

                                            me.add(
                                                {
                                                    title: '配重输出模板',
                                                    layout: 'border',
                                                    items: [currentOutTemplateGridPanel],
                                                    currentOutTemplateGridPanel: currentOutTemplateGridPanel,
                                                    listeners: {
                                                        'beforeshow': function () {
                                                            currentOutTemplateGridPanel.down('grid').store.load();
                                                        }
                                                    }
                                                }
                                            );
                                            var chartPanel = Ext.create('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.ChartLinePanel', {
                                                region: 'center',
                                                height: 800,
                                                voyageId: data.raw.ID,
                                                layout: 'border',
                                                id: 'chartLinePanel',
                                                tbar: [
                                                    {
                                                        xtype: 'button',
                                                        text: '导入数据',
                                                        icon: 'app/images/icons/default/common/attachment.png',
                                                        handler: function () {
                                                            me.createTemplate(data.raw.ID)
                                                        }
                                                    }
                                                ]
                                            });
                                            me.add({
                                                title: "图表",
                                                layout: 'border',
                                                items: [chartPanel]
                                            });
                                            me.setActiveTab(0);
                                        }
                                    }]
                                });
                        }
                        //使用一个新的store/columns配置项进行重新配置生成grid
                        taskGridPanel.reconfigure(taskGridPanel.getStore(), columns);
                    }
                    modelGrid = Ext.create('Ext.panel.Panel', {
                        region: 'center',
                        layout: 'border',
                        items: [taskGridPanel]
                    });
                    // me.setActiveTab(2);
                } else if (!Ext.isEmpty(tbomMode.templateJS)) {
                    modelGrid = Ext.create(tbomMode.templateJS, initParams);
                } else {
                    modelGrid = Ext.create("OrientTdm.Common.Extend.Grid.OrientModelGrid", initParams);
                }
                //刷新时 同时刷新右侧树
                modelGrid.on("customRefreshGrid", function (useQueryFilter) {
                    if (me.ownerCt.westPanel) {
                        me.ownerCt.westPanel.fireEvent("refreshCurrentNode", useQueryFilter);
                    }
                });

                //tree信息和grid匹配以便新增时使用
                var treeInfo = treeNode.raw;
                if (treeInfo.nodeType != "static_node") {
                    var val = treeInfo.idList[0];
                    var modelId = treeInfo.modelId;
                    var modelDesc = modelGrid.modelDesc;
                    if (typeof modelDesc != "undefined" && modelDesc) {
                        var modelCols = modelDesc.columns;
                        var refCnt = 0;
                        for (var i = 0; i < modelCols.length; i++) {
                            if (modelCols[i].type != "C_Relation") {
                                continue;
                            }
                            var refModelId = modelCols[i].refModelId;
                            if (refModelId == modelId) {
                                var colName = modelCols[i].sColumnName;
                                var refValue = {
                                    name: treeInfo.text,
                                    id: val
                                };
                                var oriData = {};
                                oriData[colName + '_display'] = Ext.encode([refValue]);
                                oriData[colName] = val;
                                modelGrid.formInitData = oriData;
                                refCnt++;
                            }
                        }
                        if (refCnt > 1) {
                            modelGrid.formInitData = null;
                        }
                    }
                    else {
                        //没有实时加载modelDesc时
                    }
                }

                me.add({
                    layout: 'border',
                    title: tbomMode.modelName,
                    iconCls: treeNode.get('iconCls'),
                    items: [
                        modelGrid, {
                            xtype: 'orientPanel',
                            region: 'south',
                            collapsible: true,
                            collapseMode: 'mini',
                            header: false,
                            collapsed: true,
                            layout: 'fit',
                            listeners: {
                                beforeshow: function () {

                                }
                            }
                        }
                    ]
                });

                if (tbomMode.modelName === '下潜任务' && treeNode.raw.level === 3) {

                    me.hangduanId = treeNode.raw.idList[0];
                    me.hangduanText = treeNode.raw.text;

                    me.skillDocumentMgr = Ext.create('OrientTdm.ProductStructureMgr.SkillDocumentMgr', {
                        region: 'center',
                        hangduanId: me.hangduanId,
                        // isStatusSearch: true
                    });

                    me.personnelWeightGrid = Ext.create('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.PersonnelWeightGrid', {
                        region: 'center',
                        flowId: me.hangduanId,
                        isStatusSearch: true
                    });
                    me.add(
                        {
                            title: '航段信息管理',
                            layout: 'border',
                            items: [me.personnelWeightGrid],
                            personnelWeightGrid: me.personnelWeightGrid,
                            // activeItem: 3
                        }
                    );
                    me.add(
                        {
                            title: '航段文件',
                            layout: 'border',
                            items: [me.skillDocumentMgr],
                            skillDocumentMgr: me.skillDocumentMgr,
                            // activeItem: 2
                        }
                    );
                    // me.setActiveTab(2);
                }

                if (tbomMode.modelName === '下潜任务' && treeNode.raw.level === 4) {

                    me.taskId = treeNode.raw.idList[0];
                    me.taskText = treeNode.raw.text;

                    me.skillDocumentMgr = Ext.create('OrientTdm.ProductStructureMgr.SkillDocumentMgr', {
                        region: 'center',
                        taskId: me.taskId,
                        // isStatusSearch: true
                    });
                    me.add(
                        {
                            title: '潜次文件',
                            layout: 'border',
                            items: [me.skillDocumentMgr],
                            skillDocumentMgr: me.skillDocumentMgr,
                        }
                    );
                }

            });
            me.setActiveTab(1);
        }
    }
});