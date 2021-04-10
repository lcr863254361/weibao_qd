/**
 * 显示计划版本集合的DataView
 */
Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Plan.PlanVersionBrowser', {
    extend: 'Ext.view.View',
    alias: 'widget.planVersionBrowser',
    uses: 'Ext.data.Store',
    singleSelect: true,
    overItemCls: 'x-view-over',
    itemSelector: 'div.thumb-wrap',
    requires: [
        'OrientTdm.CollabDev.Processing.ResultSettings.DevData.Model.TaskVersionModel',
        'OrientTdm.CollabDev.Processing.ViewBoard.Plan.WheelIterationModel'
    ],
    config: {
        nodeId: null
    },
    tpl: [
        '<tpl for=".">',
        '<div class="thumb-wrap">',
        '<div>v{version}</div>',
        '<div>修改人：{updateUser}</div>',
        '<div>修改时间：{updateTime}</div>',
        '<br/>',
        '</div>',
        '</tpl>'
    ],
    initComponent: function () {
        var me = this;

        me.store = Ext.create('Ext.data.Store', {
            autoLoad: true,
            model: 'OrientTdm.CollabDev.Processing.ResultSettings.DevData.Model.TaskVersionModel',
            proxy: {
                type: 'ajax',
                url: serviceName + '/nodeVersions/getDefaultPlanVersions.rdm',
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'message'
                },
                extraParams: {
                    nodeId: me.nodeId
                }
            }
        });

        me.callParent(arguments);
        me.store.sort();
    }
});