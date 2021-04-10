/**
 * Created by Seraph on 16/8/11.
 */
Ext.define('OrientTdm.Collab.MyTask.collabTask.CollabTaskListPanel', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.collabTaskListPanel',
    requires: [
        "OrientTdm.Collab.MyTask.collabTask.model.CollabTaskListModel",
        "OrientTdm.Collab.MyTask.util.PanelDisplayHelper"
    ],
    statics:{
        showDetail:function(itemId,recordId) {
            var record = Ext.getCmp(itemId).getStore().getById(recordId);
            var detailPanel = Ext.create("OrientTdm.Collab.MyTask.collabTask.CollabTaskDetailPanel", {
                title: "协同任务[" + record.data.name + "]详情",
                closable: true,
                rootData: record.data,
                rootDataId: record.data.id,
                rootModelName: 'CB_TASK'
            });

            MyTaskPanelDisplayHelper.showInMainTab(detailPanel);
        }
    },
    initComponent: function () {
        var me = this;

        Ext.apply(me, {
            features : [{
                id : 'group',
                ftype : 'grouping',
                groupHeaderTpl : '{name}',
                hideGroupedHeader: true,
                enableGroupingMenu : false
            }]
        });
        this.callParent(arguments);

        me.initEvents();
        //me.on("cellclick", me.cellClickListener, me);
        me.on("actionShowDetail",me.actionShowDetail,me);
        this.addEvents('filterByFilter');
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'filterByFilter', me.filterByFilter, me);
    },
    createStore: function () {
        var retVal = Ext.create("Ext.data.Store", {
            autoLoad: true,
            model: 'OrientTdm.Collab.MyTask.collabTask.model.CollabTaskListModel',
            groupField : 'group',
            proxy: {
                type: 'ajax',
                api: {
                    "read": serviceName + "/myTask/collabTasks/currentUser.rdm"
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'message'
                }
            }
        });

        this.store = retVal;
        return retVal;
    },
    createToolBarItems: function () {
        var me = this;

        var retVal = [];

        return retVal;
    },
    createColumns: function () {
        var me = this;

        return [
            {
                header: '分组',
                width: 180,
                sortable: true,
                dataIndex: 'group'
            },
            {
                header: '名称',
                flex : 1,
                sortable: true,
                dataIndex: 'name',
                renderer: Ext.bind(me.renderName, me)
            },
            {
                header: '开始时间',
                width: 180,
                sortable: true,
                dataIndex: 'actualStartDate'
            },
            {
                xtype: 'actioncolumn',
                header: '操作',
                width: 180,
                dataIndex: 'groupTask',
                items: [{
                    iconCls: 'icon-takeTask',
                    tooltip: '接受任务',
                    handler: function (grid, rowIndex, colIndex, item, e, record) {

                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/flow/task/take.rdm', {flowTaskId: record.data.flowTaskId}, false, function (response) {
                            var respJson = response.decodedData;

                            if (respJson.success) {
                                record.data.groupTask = false;
                                grid.fireEvent("actionShowDetail", me.getId(),record.getId());
                            }
                        });
                    },
                    isDisabled: function (view, rowIndex, colIndex, item, record) {
                        if (record.data.groupTask) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }]
            }
        ];
    },
    actionShowDetail:function(itemId,recordId) {
        var record = Ext.getCmp(itemId).getStore().getById(recordId);
        var detailPanel = Ext.create("OrientTdm.Collab.MyTask.collabTask.CollabTaskDetailPanel", {
            title: "协同任务[" + record.data.name + "]详情",
            closable: true,
            rootData: record.data,
            rootDataId: record.data.id,
            rootModelName: 'CB_TASK'
        });

        MyTaskPanelDisplayHelper.showInMainTab(detailPanel);
    },
    cellClickListener: function (table, td, cellIndex, record, tr, rowIndex, e, eopts) {
        if (cellIndex !== 1 && 'take' !== eopts) {
            return;
        }
        var detailPanel = Ext.create("OrientTdm.Collab.MyTask.collabTask.CollabTaskDetailPanel", {
            title: "协同任务[" + record.data.name + "]详情",
            closable: true,
            rootData: record.data,
            rootDataId: record.data.id,
            rootModelName: 'CB_TASK'
        });

        MyTaskPanelDisplayHelper.showInMainTab(detailPanel);
    },
    renderName: function (value, p, record) {
        var me = this;
        var recordId = record.getId();
        return '<span href="javascript:;" class="taskSpan" onclick="OrientTdm.Collab.MyTask.collabTask.CollabTaskListPanel.showDetail(\''+me.getId()+'\''+',\''+recordId+'\');">'+value+'</span>';
    },
    filterByFilter: function (filter) {
        for (var proName in filter) {
            var proValue = filter[proName];
            if (proName === 'startDate' || proName === 'endDate') {
                proValue = proValue.replace(/[年月日]/g, '-');
            }
            this.getStore().getProxy().setExtraParam(proName, proValue);
        }
        this.getStore().loadPage(1);
    }
});