/**
 * 项目运行中的计划甘特图面板，只查看，不能增删改，需要把所有事件屏蔽掉
 */
Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Project.ProjectProgressGanttPanel', {
    extend: 'Gnt.panel.Gantt',
    alias: 'widget.projectProgressGanttPanel',
    requires: [
        'Gnt.plugin.TaskEditor',
        'Gnt.column.StartDate',
        'Gnt.column.EndDate',
        'Gnt.column.Duration',
        'Gnt.column.PercentDone',
        'Sch.plugin.TreeCellEditing',
        'Sch.plugin.Pan',
        'OrientTdm.Collab.common.gantt.model.Plan',
        'OrientTdm.Collab.common.gantt.model.Dependency',
        'OrientTdm.Collab.common.gantt.Toolbar'
    ],
    config: {
        projectId: null,
        projectNodeId: null,
        projectNodeVersion: null,
        projectPlannedStartDate: null,
        projectPlannedEndDate: null
    },
    initComponent: function () {
        Ext.QuickTips.init();
        var me = this;

        var taskStore = Ext.create('Gnt.data.TaskStore', {
            model: 'OrientTdm.Collab.common.gantt.model.Plan',
            autoLoad: true,
            autoSync: true,
            listeners: {
                beforeload: function (store, operation) {
                    var node = operation.node;
                    if (node.isRoot()) {
                        store.getProxy().setExtraParam('parentNodeId', me.projectNodeId);
                        store.getProxy().setExtraParam('projectId', me.projectId);
                        store.getProxy().setExtraParam('projectNodeVersion', me.projectNodeVersion);
                    } else {
                        if (node.raw.parentNode) {
                            node.raw.parentNode = null;
                        }
                        store.getProxy().setExtraParam('parentNodeId', node.data.parentNodeId);
                        store.getProxy().setExtraParam('projectId', me.projectId);
                        store.getProxy().setExtraParam('projectNodeVersion', me.projectNodeVersion);
                    }
                },
                beforesync: function (options, eOpts) {
                    if (options.create) {
                        if (!options.create[0].data.parentId) { //新增计划
                            me.taskStore.getProxy().setExtraParam('parentNodeId', me.projectNodeId);
                            me.taskStore.getProxy().setExtraParam('projectId', me.projectId);
                        } else { //新增子计划
                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/designPlanGantt/getPlanNodeId.rdm', {planId: options.create[0].data.parentId}, false, function (resp) {
                                me.taskStore.getProxy().setExtraParam('parentNodeId', resp.decodedData.results);
                                me.taskStore.getProxy().setExtraParam('projectId', me.projectId);
                            });
                        }
                    }

                    var notSyncModels = me.taskStore.getNewRecords();
                    for (var i = 0; i < notSyncModels.length; i++) {
                        notSyncModels[i].data.newCreate = true;
                        //设置默认值
                        Ext.apply(notSyncModels[i].data, {
                            plannedStartDate: new Date(),
                            plannedEndDate: new Date(Date.now() + 24 * 3600 * 1000),
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

                },
                update: function (store, record, operation, eOpts) {
                },
                write: function (store, operation, eOpts) {
                    //fix assign column not change dirty cls after save task data
                    var records = operation.records;
                    Ext.each(records, function (record) {
                        record.commit();
                    });
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
                    read: serviceName + '/designPlanGantt/plans.rdm',
                    create: serviceName + '/designPlanGantt/createOrUpdate.rdm',
                    update: serviceName + '/designPlanGantt/createOrUpdate.rdm',
                    destroy: serviceName + '/designPlanGantt/deletePlans.rdm'
                },
                writer: {
                    type: 'json',
                    root: 'data',
                    encode: true,
                    allowSingle: false
                }
            }
        });


        var dependencyStore = Ext.create('Gnt.data.DependencyStore', {
            model: 'OrientTdm.Collab.common.gantt.model.Dependency',
            autoLoad: true,
            autoSync: true,
            listeners: {
                beforeload: function (store, operation) {
                    store.getProxy().setExtraParam('projectId', me.projectId);
                    store.getProxy().setExtraParam('projectNodeId', me.projectNodeId);
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
                    read: serviceName + '/planRelation/getPlanRelation.rdm',
                    create: serviceName + '/planRelation/savePlanRelation.rdm',
                    update: serviceName + '/planRelation/savePlanRelation.rdm',
                    destroy: serviceName + '/planRelation/deletePlanRelation.rdm'
                },
                writer: {
                    type: 'json',
                    root: 'data',
                    encode: true,
                    allowSingle: false
                }
            }
        });

       /* var resourceStore = Ext.create('Gnt.data.ResourceStore', {
            model: 'Gnt.model.Resource'
        });

        var assignmentStore = Ext.create('Gnt.data.AssignmentStore', {
            autoLoad: true,
            autoSync: true,
            // Must pass a reference to resource store
            resourceStore: resourceStore,
            proxy: {
                type: 'ajax',
                api: {
                    read: serviceName + '/designPlanGantt/resources.rdm',
                    create: serviceName + '/designPlanGantt/resources/saveAssignment.rdm',
                    update: serviceName + '/designPlanGantt/resources/saveAssignment.rdm'
                },
                method: 'GET',
                reader: {
                    type: 'json',
                    root: 'assignments'
                }
            },
            listeners: {
                load: function () {
                    resourceStore.loadData(this.proxy.reader.jsonData.resources);
                }
            }
        });
        assignmentStore.getProxy().setExtraParam('parentNodeId', me.projectNodeId);*/

        Ext.apply(me, {
            readOnly: true,
            region: 'center',
            height: 800,
            width: 800,
            rowHeight: 26,
            //leftLabelField: 'name',
            rightLabelField: 'name',
            highlightWeekends: true,
            showTodayLine: true,
            enableBaseline: true,
            baselineVisible: true,
            enableDependencyDragDrop: true,
            enableDragCreation: true,
            enableTaskDragDrop: true,
            weekendsAreWorkdays: true,
            skipWeekendsDuringDragDrop: false,
            loadMask: false,
            viewPreset: 'weekAndDayLetter',
            startDate: Ext.isEmpty(me.projectPlannedStartDate) ? new Date() : me.projectPlannedStartDate,
            endDate: Sch.util.Date.add(Ext.isEmpty(this.projectPlannedEndDate) ? new Date() : me.projectPlannedEndDate, Sch.util.Date.WEEK, 20),
            tooltipTpl: new Ext.XTemplate(
                '<ul class="taskTip" style="list-style-type: none;margin: 0">',
                '<li><strong>{name}</strong></li>',
                '<li><strong>计划开始日期:</strong>{[Ext.Date.format(values.plannedStartDate, "y-m-d")]}</li>',
                '<li><strong>计划结束日期:</strong>{[Ext.Date.format(values.plannedEndDate,"y-m-d")]}</li>',
                '<li><strong>负责人:</strong>{resourceName}</li>',
                '</ul>'
            ).compile(),
            // Setup your static columns
            columns: [
                {
                    text: '工作包名称',
                    xtype: 'namecolumn',
                    dataIndex: 'name',
                    width: 150
                },
                {
                    text: '计划开始日期',
                    xtype: 'datecolumn',
                    dataIndex: 'plannedStartDate',
                    width: 120,
                    format: 'Y-m-d'
                },
                {
                    text: '计划结束日期',
                    xtype: 'datecolumn',
                    dataIndex: 'plannedEndDate',
                    width: 120,
                    format: 'Y-m-d'
                },
                {
                    header: '负责人',
                    sortable: true,
                    width: 120,
                    dataIndex: 'resourceName',
                    tdCls: 'sch-assignment-cell'
                }
            ],
            taskStore: taskStore,
            dependencyStore: dependencyStore,
          /*  assignmentStore: assignmentStore,
            resourceStore: resourceStore,*/
            plugins: [
                Ext.create('Sch.plugin.Pan'),
                //Ext.create('Sch.plugin.TreeCellEditing', {clicksToEdit: 2}),
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
            tbar: Ext.create('OrientTdm.Collab.common.gantt.Toolbar', {gantt: me}),
            listeners: {
                timeheaderdblclick: function (column, startDate, endDate, e, eOpts) {

                }
            }
        });

        me.callParent(arguments);

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
    initEvents: function () {
        var _this = this;
        _this.callParent();
        _this.mon(_this, 'itemclick', _this._taskClick, _this);
        _this.mon(_this, 'celldblclick', _this._celldblclick, _this);
    },
    _taskClick: function (tree, node) {  //屏蔽单击事件
        return false;
    },
    _celldblclick: function (view, td, cellIndex, record, tr, rowIndex) { //屏蔽双击事件
        return false;
    }
});
