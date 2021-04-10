/**
 * 计划看板
 */
Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Plan.PlanViewDashboard', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.planViewDashboard',
    config: {},
    initComponent: function () {
        var me = this;

        var baseInfoForm = Ext.create('OrientTdm.CollabDev.Processing.Common.NodeBaseInfoPanel', {
            region: 'north',
            modelName: TDM_SERVER_CONFIG.PLAN,
            templateName: TDM_SERVER_CONFIG.TPL_VIEWBOARD_PLAN,
            dataId: me.bmDataId,
            height: 200
        });

        var planProgressMonitorPanel = Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Plan.PlanProgressMonitorPanel', {
            title: '进度监控',
            region: 'center',
            nodeId: me.nodeId
        });

        Ext.apply(me, {
            autoScroll: true,
            layout: 'border',
            items: [baseInfoForm, planProgressMonitorPanel]
        });

        me.callParent(arguments);
    },
    refresh: function () {
        var _this = this;
        //刷新基础信息面板
        var nodeBaseInfoPanel = _this.down('nodeBaseInfoPanel');
        nodeBaseInfoPanel.dataId = _this.bmDataId;
        nodeBaseInfoPanel.restructurePanel();
        //刷新进度监控面板,由于在滚动的时候同时点击导航树，
        //会导致点击的nodeId和上一个节点的nodeVersion传递到后台导致空指针的问题，所以把整个面板移除再加进来
        _this.remove(_this.down('planProgressMonitorPanel'));
        _this.add(Ext.create('OrientTdm.CollabDev.Processing.ViewBoard.Plan.PlanProgressMonitorPanel', {
            title: '进度监控',
            region: 'center',
            nodeId: _this.nodeId
        }));
    }
});