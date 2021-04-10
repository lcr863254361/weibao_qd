Ext.define('OrientTdm.DestroyTaskMgr.DestroyTaskGanttMgr.DestroyTaskGanttPanel', {
        extend: 'Gnt.panel.Gantt',
        alias: 'widget.destroyTaskGanttPanel',
        requires: [
            'Gnt.plugin.TaskEditor',
            'Gnt.column.StartDate',
            'Gnt.column.EndDate',
            'Gnt.column.Duration',
            'Gnt.column.PercentDone',
            // 'Gnt.column.ResourceAssignment',
            'OrientTdm.Collab.common.gantt.assignment.ResourceAssignmentColumn',
            'Sch.plugin.TreeCellEditing',
            'Sch.plugin.Pan',
            'OrientTdm.DestroyRepairTemplateMgr.Gantt.FlowGanttFields',
            'OrientTdm.Collab.common.gantt.model.Dependency',
            'OrientTdm.Collab.common.gantt.Toolbar',
            'OrientTdm.DestroyRepairTemplateMgr.Gantt.DestroyFlowContextMenu'
        ],
        config: {
            localMode: false,
            localData: null,
            modelName: null,
            dataId: null,
            controlStatus: null,
            readOnly: null,
            enableControl: null,
            //保存历史任务时 是否需要序列化至数据库
            isHistoryAble: true,
            //历史任务描述
            hisTaskDetail: null,
            projectPlannedStartDate: null,
            projectPlannedEndDate: null
        },
        initComponent: function () {
            Ext.QuickTips.init();
            var me = this;
             var destroyTaskState=me.destroyTaskState;
            var taskStore = Ext.create("Gnt.data.TaskStore", {
                model: 'OrientTdm.DestroyRepairTemplateMgr.Gantt.FlowGanttFields',
                autoLoad: true,
                autoSync: true,
                listeners: {
                    beforeLoad: function (store, operation) {
                        var node = operation.node;
                        if (node.isRoot()) {
                            store.getProxy().setExtraParam("parModelName", me.modelName);
                            store.getProxy().setExtraParam("parDataId", me.dataId);
                        }
                    },
                    beforesync: function (options, eOpts) {
                        me.taskStore.getProxy().setExtraParam("rootModelName", me.modelName);
                        me.taskStore.getProxy().setExtraParam("rootDataId", me.dataId);
                        me.taskStore.getProxy().setExtraParam("isDestroyTask", true);

                        var notSyncModels = me.taskStore.getNewRecords();
                        for (var i = 0; i < notSyncModels.length; i++) {
                            notSyncModels[i].data.newCreate = true;
                            //设置默认值
                            Ext.apply(notSyncModels[i].data, {
                                startDate: new Date(),
                                endDate: new Date(Date.now() + 24 * 3600 * 1000),
                                Duration: 1,
                                principal: window.userId,
                                resourceName: window.userAllName
                            });

                            if (!Ext.isEmpty(notSyncModels[i].previousSibling)) {
                                notSyncModels[i].data.preSib = notSyncModels[i].previousSibling.data.Id;
                            }

                            if (!Ext.isEmpty(notSyncModels[i].nextSibling)) {
                                notSyncModels[i].data.nextSib = notSyncModels[i].nextSibling.data.Id;
                            }
                        }
                    }
                },
                root: {
                    text: 'Root',
                    id: '-1',
                    expanded: true
                },
                proxy: {
                    type: 'ajax',
                    method: 'POST',
                    reader: {
                        type: 'json'
                    },
                    api: {
                        read: serviceName + '/destroyRepairMgr/getGanttDestroyFlow.rdm',
                        create: serviceName + '/destroyRepairMgr/saveOrUpdateDestroyFlow.rdm',
                        destroy: serviceName + '/destroyRepairMgr/deleteDestroyFlow.rdm',
                        update: serviceName + '/destroyRepairMgr/saveOrUpdateDestroyFlow.rdm'
                    },
                    writer: {
                        type: 'json',
                        root: 'data',
                        encode: true,
                        allowSingle: false
                    }
                }
            });


            var readOnly = false;

            Ext.apply(this, {
                readOnly: readOnly,
                region: 'center',
                height: 800,
                width: 800,
                rowHeight: 26,
                leftLabelField: 'Name',
                highlightWeekends: false,
                showTodayLine: true,
                enableBaseline: true,
                baselineVisible: true,
                enableDependencyDragDrop: true,
                enableDragCreation: true,
                enableTaskDragDrop: true,
                loadMask: false,
                viewPreset: 'weekAndDayLetter',
                startDate: Ext.isEmpty(me.startDate) ? new Date() : me.startDate,
                endDate: Sch.util.Date.add(Ext.isEmpty(me.endDate) ? new Date() : me.endDate, Sch.util.Date.WEEK, 20),

                // Setup your static columns
                columns: [
                    {
                        header: '设备名称',
                        sortable: true,
                        dataIndex: 'name',
                        locked: true,
                        width: 200,
                        editor: new Ext.form.TextField()
                    },
                    {
                        text: '开始时间',
                        xtype: 'startdatecolumn'
                    },
                    {
                        text: '持续时间',
                        xtype: 'durationcolumn'
                    },
                    {
                        xtype: 'percentdonecolumn',
                        header: '完成百分比',
                        width: 100,
                         editable:false
                    }
                ],
                taskStore: taskStore,
                plugins: [
                    Ext.create("OrientTdm.DestroyRepairTemplateMgr.Gantt.DestroyFlowContextMenu"),
                    Ext.create("Sch.plugin.Pan"),
                    Ext.create('Sch.plugin.TreeCellEditing', {clicksToEdit: 2}),
                    //Ext.create('Gnt.plugin.TaskEditor'),

                    // Lazy style definition using 'ptype'
                    {
                        ptype: 'scheduler_lines',
                        innerTpl: '<span class="line-text">{Text}</span>',
                        store: new Ext.data.JsonStore({
                            fields: ['Date', 'Text', 'Cls'],
                            data: [
                                {
                                    Date: new Date(2010, 0, 13),
                                    Text: 'Project kickoff',
                                    Cls: 'kickoff' // A CSS class
                                }
                            ]
                        })
                    }
                ],
                // Define an HTML template for the tooltip
                // tooltipTpl : new Ext.XTemplate(
                //     '<h4 class="tipHeader">{name}</h4>',
                //     '<table class="taskTip">',
                //     '<tr><td>Start:</td> <td align="right">{[Ext.Date.format(values.startDate, "y-m-d")]}</td></tr>',
                //     '<tr><td>End:</td> <td align="right">{[Ext.Date.format(values.endDate, "y-m-d")]}</td></tr>',
                //     '<tr><td>Progress:</td><td align="right">{PercentDone}%</td></tr>',
                //     '</table>'
                // ).compile(),
                // tooltipTpl: new Ext.XTemplate(
                //     '<ul class="taskTip">',
                //     '<li><strong>{name}</strong></li>', '<li><strong>开始时间:</strong>' +
                //     '{[Ext.Date.format(values.startDate, "y-m-d")]}</li>',
                //     '<li><strong>结束时间:</strong>{[Ext.Date.format(values.endDate,"y-m-d")]}</li>',
                //     '<li><strong>完成比:</strong>{PercentDone}%</li>',
                //     '</ul>'
                // ).compile(),
                tbar: Ext.create("OrientTdm.Collab.common.gantt.Toolbar", {gantt: me}),
                listeners: {
                    //双击时间头触发此方法
                    // timeheaderdblclick: function (column, startDate, endDate, e, eOpts) {
                    //     alert('You ve clicked on  task');
                    // },
                    // 进度条单击点击事件
                    taskclick: function (ganttPanel, task) {
                        // alert('You ve clicked on ' + task.get('Name') + ' task');
                        var flowId = task.data['Id'];
                        me.flowInstClick(flowId,destroyTaskState);
                    },

                    scheduleclick: function (scheduler, clickedDate, rowIndex, e, options) {
                        var flowId = scheduler.dataSource.data.items[rowIndex].data['Id'];
                        me.flowInstClick(flowId,destroyTaskState);
                    },
                    //行双击事件
                    beforeedit:function (editor,e) {
                        console.log(e);
                        var flowId  = e.record.data['Id'];
                        me.flowInstClick(flowId,destroyTaskState);
                        //完成百分比不可编辑
                        if (e.field==="PercentDone"){
                            return false;
                        }
                        if (destroyTaskState==='进行中'||destroyTaskState==='已结束') {
                            return false;
                        }
                    },
                    //行单击事件
                    itemclick:function (view,record,item,index,e) {
                        console.log(record);
                        var flowId  = record.data['Id'];
                         me.flowInstClick(flowId,destroyTaskState);
                    },
                    //鼠标右击事件
                    itemcontextmenu:function (view,record,item,index,e,eOpts) {
                        var destroyFlowId  = record.data['Id'];
                        //禁用浏览器的右键响应事件
                        e.preventDefault();
                        e.stopEvent();
                        Ext.Msg.confirm("友情提示", "是否确认删除?",
                            function (btn) {
                                if (btn == 'yes') {
                                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/destroyRepairMgr/delDestroyFlowById.rdm', {
                                        destroyFlowId: destroyFlowId
                                    }, false, function (resp) {
                                        taskStore.load();
                                    });
                                }
                            });
                    }
                }
            });

            this.callParent(arguments);

            me.taskStore.on({
                'filter-set': function () {
                    this.down('[iconCls=icon-collapseall]').disable();
                    this.down('[iconCls=icon-expandall]').disable();
                },
                'filter-clear': function () {
                    this.down('[iconCls=icon-collapseall]').enable();
                    this.down('[iconCls=icon-expandall]').enable();
                },
                scope: this
            })
        },
        flowInstClick:function (flowId,destroyTaskState) {
            var me=this;
            me.remove(me.items.items[3]);
            var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
                customerFilterArr = [];
            var customerFilter = new CustomerFilter('T_DESTROY_FLOW_' + schemaId + "_ID", CustomerFilter.prototype.SqlOperation.Equal, '', flowId);
            customerFilterArr.push(customerFilter);
            var itemsToAdd = [];
            var destroyCheckTablePanel = Ext.create("OrientTdm.DestroyTaskMgr.DestroyTaskTabPanel.DTaskCheckTableMgrPanel", {
                title: '检查表格',
                region: 'center',
                padding: '0 0 0 0',
                flowId: flowId,
                taskId: '',
                destroyTaskState:destroyTaskState,
                isDestroyTask:true
            });
            itemsToAdd.push(destroyCheckTablePanel);
            var destroyPostMgrPanel = Ext.create("OrientTdm.DestroyTaskMgr.DestroyTaskTabPanel.DTaskAttendPostMgrPanel", {
                title: '参与岗位',
                region: 'center',
                padding: '0 0 0 0',
                readModel:false,
                taskId: '',
                flowId: flowId,
                destroyTaskState:destroyTaskState
            });
            itemsToAdd.push(destroyPostMgrPanel);
            var destroySkillPanel = Ext.create('OrientTdm.ProductStructureMgr.SkillDocumentMgr', {
                // gantt: me,
                title: '技术资料',
                flowId: flowId,
                productId: "",
                destroyTaskState:destroyTaskState,
                taskId:""
            });
            itemsToAdd.push(destroySkillPanel);
            var skillAndTableTabPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
                layout: 'fit',
                height: '45%',
                region: 'south',
                animCollapse: true,
                collapsible: true,
                header: true,
                items: itemsToAdd
            });
            me.remove(me.items.items[3]);
            me.add(skillAndTableTabPanel);
        }
    }
);