/**
 * WBS分解的gantt图
 */
Ext.define('OrientTdm.CollabDev.Designing.WBSDecomposition.DesignPlanGanttPanel', {
    extend: 'Gnt.panel.Gantt',
    alias: 'widget.designPlanGanttPanel',
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
        'OrientTdm.Collab.common.gantt.Toolbar',
        'OrientTdm.Collab.common.gantt.TaskContextMenu'
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
                        //store.getProxy().setExtraParam('projectNodeVersion', me.projectNodeVersion);
                    } else {
                        if (node.raw.parentNode) {
                            node.raw.parentNode = null;
                        }
                        store.getProxy().setExtraParam('parentNodeId', node.data.parentNodeId);
                        store.getProxy().setExtraParam('projectId', me.projectId);
                        //store.getProxy().setExtraParam('projectNodeVersion', me.projectNodeVersion);
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
                beforeload: function (store, operation, eOpts) {
                    store.getProxy().setExtraParam('projectId', me.projectId);
                    store.getProxy().setExtraParam('projectNodeId', me.projectNodeId);
                },
                remove: function (store, record, index, eOpts) {
                    //后台可能判断该关系不能删除，但是前台已经把连线删除了，需要重新请求后台来刷新连线区域
                    store.load();
                    me.fireEvent('cascade');
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

        /*  var resourceStore = Ext.create('Gnt.data.ResourceStore', {
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
            readOnly: false,
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
                    format: 'Y-m-d',
                    editor: new Ext.form.DateField({
                        format: 'Y-m-d'
                    })
                },
                {
                    text: '计划结束日期',
                    xtype: 'datecolumn',
                    dataIndex: 'plannedEndDate',
                    width: 120,
                    format: 'Y-m-d',
                    editor: new Ext.form.DateField({
                        format: 'Y-m-d'
                    })
                },
                {
                    header: '负责人',
                    dataIndex: 'resourceName',
                    width: 120
                }
            ],
            taskStore: taskStore,
            dependencyStore: dependencyStore,
            /* assignmentStore: assignmentStore,
             resourceStore: resourceStore,*/
            plugins: [
                Ext.create('OrientTdm.Collab.common.gantt.TaskContextMenu'),
                Ext.create('Sch.plugin.Pan'),
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
    _taskClick: function (tree, node) {
        var me = this;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/designPlanGantt/getPlanNodeId.rdm', {planId: node.getId()}, false, function (resp) {
            var planNodeId = resp.decodedData.results;
            var designTaskPanel = me.up('wbsDecomposePanel').down('#designTaskPanel');
            designTaskPanel.refreshDataByPlanNodeId(planNodeId);
            designTaskPanel.enableButtons();
        });
    },
    //双击弹窗选择负责人 ZhangSheng 2018.8.10
    _celldblclick: function (view, td, cellIndex, record, tr, rowIndex) {
        //var me = this;
        //双击单元格，实现弹出人员选择器窗口-非“计划负责人”列除外
        if (cellIndex != 3) return;
        var userid = record.get('principal');
        var item = Ext.create('OrientTdm.Common.Extend.Form.Selector.ChooseUserPanel', {
            selectedValue: userid,
            multiSelect: true,
            saveAction: function (selectedValue, callBack) {
                if (selectedValue.length > 0) {
                    //pluck：查找键为name的值返回数组（不改变数组大小）
                    //join方法：把数组中的所有元素放入一个字符串，separator可选，指定要使用的分隔符
                    record.set('resourceName', Ext.Array.pluck(selectedValue, 'name').join(','));
                    record.set('principal', Ext.Array.pluck(selectedValue, 'id').join(','));
                    callBack.call(item);
                } else {
                    OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.atleastSelectOne);
                }
            }
        });
        OrientExtUtil.WindowHelper.createWindow(item, {
            title: '设置人员'
        });
    },
    reloadDependencyData: function () {
        var me = this;
        var dependencyStore = me.dependencyStore;
        debugger;
        dependencyStore.load();
        me.fireEvent('cascade');
    }
});