/**
 * Created by User on 2020/03/07.
 */
Ext.define('OrientTdm.ScientistTaskPrepareMgr.DivingTaskManagerGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.divingTaskManagerGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.DIVING_TASK,
        modelId: ''
    },
    initComponent: function () {
        var me = this;
        modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);

        var store = Ext.create('Ext.data.Store', {

            fields: ["id", "taskName"],
            proxy: {
                type: 'ajax',
                url: serviceName + "/accountingForm/querDivingTaskList.rdm",
                reader: {
                    type: 'json',
                    root: 'results'
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        var columns = [
            {id: 'taskHeaderId', text: '任务名称', dataIndex: 'taskName', flex: 1, align: 'center',
                renderer: function (value, meta, record) {
                    value = value || '';
                    meta.tdAttr = 'data-qtip="' + value + '"';
                    return value;
                }}
        ];
        var girdPanel = Ext.create('Ext.grid.Panel', {
            renderTo: Ext.getBody(),
            hideHeaders: true,
            store: store,
            columns: columns,
            buttonAlign: 'center'
        });

        girdPanel.addListener('itemclick', function (grid, record, item, index, e) {
            var taskId = record.data.id;
            var taskName=record.raw['taskName'];
            var scientistPlanCenterPanel = me.up('scientistDivingPlanDashBoard').centerPanel;
            scientistPlanCenterPanel.items.each(function (item, index) {
                scientistPlanCenterPanel.remove(item);
            });

            // var scientistPlanTablePanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
            //     region: 'center',
            //     taskId: taskId,
            //     productId: "",
            //     flowId: '',
            //     taskEndState: me.taskEndState,
            //     items: [{
            //         layout: "fit",
            //         html: '<iframe  id="scientitsPlanTableIframe" frameborder="0" width="50%" height="80%" style="margin-left: 0px;" src="' + 'accountingForm/getScientistDivingPlanData.rdm?taskId=' + taskId
            //             + '"></iframe>'
            //     }]
            // });

            var scientistPlanTablePanel = Ext.create('OrientTdm.ScientistTaskPrepareMgr.ScientistPlanGridPanel', {
                region: 'center',
                taskId: taskId,
                taskName:taskName
            });

            scientistPlanCenterPanel.add(
                {
                    title: '科学家下潜计划表',
                    layout: 'border',
                    items: [scientistPlanTablePanel],
                    scientistPlanTablePanel: scientistPlanTablePanel
                }
            );

            // scientistPlanCenterPanel.add(
            //     {
            //         title: '科学家下潜计划表',
            //         layout: 'border',
            //         items: [scientistPlanTablePanel],
            //         scientistPlanTablePanel: scientistPlanTablePanel
            //     }
            // );
            // window.onresize = function () {
            //     changeFrameHeight();
            // };
            // changeFrameHeight();
            // function changeFrameHeight() {
            //     var cwin = document.getElementById('scientitsPlanTableIframe');
            //     console.log(cwin)
            //     cwin.width = document.documentElement.clientWidth - 250;
            //     cwin.height = document.documentElement.clientHeight - 140;
            //     scientistPlanCenterPanel.setHeight(cwin.height);
            //     scientistPlanCenterPanel.setWidth(cwin.width);
            // }
            scientistPlanCenterPanel.setActiveTab(0);
        });
        Ext.apply(me, {
            layout: 'fit',
            items: [girdPanel],
            modelGrid: girdPanel
        });
        me.callParent(arguments);
    }
});
