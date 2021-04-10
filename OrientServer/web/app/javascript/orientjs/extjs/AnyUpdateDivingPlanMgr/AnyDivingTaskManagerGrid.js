/**
 * Created by User on 2020/03/21.
 */
Ext.define('OrientTdm.AnyUpdateDivingPlanMgr.AnyDivingTaskManagerGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.anyDivingTaskManagerGrid',
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
            {id: 'anyTaskId', text: '任务名称', dataIndex: 'taskName', flex: 1, align: 'center',
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
            var hangduanId=record.raw.hangduanId;
            var everyUpdatePlanCenterPanel = me.up('anyDivingPlanDashBoard').centerPanel;
            everyUpdatePlanCenterPanel.items.each(function (item, index) {
                everyUpdatePlanCenterPanel.remove(item);
            });

            var anyPlanTablePanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                region: 'center',
                taskId: taskId,
                productId: "",
                flowId: '',
                taskEndState: me.taskEndState,
                items: [{
                    layout: "fit",
                    html: '<iframe  id="anyDivingPlanTableIframe" frameborder="0" style="margin-left: 0px;" src="' +'accountingForm/getDivingPlanTableData.rdm?taskId=' + taskId + '&hangduanId=' + hangduanId+ '&anyUpdate=' + true
                        + '"></iframe>'
                }]
            });

            everyUpdatePlanCenterPanel.add(
                {
                    title: '下潜计划表',
                    layout: 'border',
                    items: [anyPlanTablePanel],
                    anyPlanTablePanel: anyPlanTablePanel
                }
            );
            window.onresize = function () {
                changeFrameHeight();
            };
            changeFrameHeight();
            function changeFrameHeight() {
                var cwin = document.getElementById('anyDivingPlanTableIframe');
                console.log(cwin);
                cwin.width = document.documentElement.clientWidth - 250;
                cwin.height = document.documentElement.clientHeight - 140;
                everyUpdatePlanCenterPanel.setHeight(cwin.height);
                everyUpdatePlanCenterPanel.setWidth(cwin.width);
            }
            everyUpdatePlanCenterPanel.setActiveTab(0);
        });
        Ext.apply(me, {
            layout: 'fit',
            items: [girdPanel],
            modelGrid: girdPanel
        });
        me.callParent(arguments);
    }
});
