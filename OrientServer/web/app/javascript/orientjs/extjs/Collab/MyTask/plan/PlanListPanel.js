/**
 * Created by Seraph on 16/7/25.
 */
Ext.define('OrientTdm.Collab.MyTask.plan.PlanListPanel', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    requires: [
        "OrientTdm.Collab.MyTask.plan.model.PlanListModel",
        "OrientTdm.Collab.MyTask.util.PanelDisplayHelper"
    ],
    statics:{
      showDetail:function(itemId,recordId) {
          var record = Ext.getCmp(itemId).getStore().getById(recordId);
          var thePanel = Ext.create("OrientTdm.Collab.MyTask.plan.PlanDetailPanel",{
              title: "计划["+record.data.name + "]详情",
              closable : true,
              rootDataId : record.data.id,
              rootModelName : 'CB_PLAN',
              rootData : record.data
          });

          MyTaskPanelDisplayHelper.showInMainTab(thePanel);
      }
    },
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            features : [{
                id : 'belongedProject',
                ftype : 'grouping',
                groupHeaderTpl : '{name}',
                hideGroupedHeader: true,
                enableGroupingMenu : false
            }]
        });
        this.callParent(arguments);

        me.initEvents();
        //me.on("itemclick", me.itemClickListener, me);
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
            model: 'OrientTdm.Collab.MyTask.plan.model.PlanListModel',
            groupField : 'belongedProject',
            proxy: {
                type: 'ajax',
                api: {
                    "read": serviceName + "/myTask/plans/currentUser.rdm"
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
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
                dataIndex: 'belongedProject'
            },
            {
                header: '名称',
                flex : 1,
                sortable: true,
                dataIndex: 'name',
                renderer: Ext.bind(me.renderName, me)
            },
            {
                header: '实际开始时间',
                width: 180,
                sortable: true,
                dataIndex: 'actualStartDate'
            },
            {
                header: '计划开始时间',
                width: 180,
                sortable: true,
                dataIndex: 'plannedStartDate'
            },
            {
                header: '计划结束时间',
                width: 180,
                sortable: true,
                dataIndex: 'plannedEndDate'
            }
        ];
    },
    itemClickListener: function (view, record, item, index, e, eOpts) {
        var thePanel = Ext.create("OrientTdm.Collab.MyTask.plan.PlanDetailPanel",{
            title: "计划["+record.data.name + "]详情",
            closable : true,
            rootDataId : record.data.id,
            rootModelName : 'CB_PLAN',
            rootData : record.data
        });

        MyTaskPanelDisplayHelper.showInMainTab(thePanel);
    },
    renderName: function(value, p, record) {
        var me = this;
        var recordId = record.getId();
        return '<span href="javascript:;" class="taskSpan" onclick="OrientTdm.Collab.MyTask.plan.PlanListPanel.showDetail(\''+me.getId()+'\''+',\''+recordId+'\');">'+value+'</span>';
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