Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Plan.SliderPlanVersionPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.sliderPlanVersionPanel',
    config: {
        nodeId: null
    },
    initComponent: function () {
        var me = this;

        Ext.apply(me, {
            id: 'planVersionSlidePanel',
            html: '<iframe width="100%" height="100%" marginwidth="0"  marginheight="0" frameborder="0" scrolling="no" src = "' + serviceName +
            '/nodeVersions/listPlanWheelIteration.rdm?nodeId=' + me.nodeId + '"></iframe>',
            layout: 'fit',
            bodyStyle: 'background:#404040',
            modal: true
        });

        me.addEvents(
            'refresh'
        );
        me.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.mon(me, 'refresh', me.refresh, me);
        me.callParent();
    },
    refreshPedigreeComponent: function (nodeVersion) {
        var me = this;
        var pedigreeComponent = me.up('planProgressMonitorPanel');
        pedigreeComponent.fireEvent('refresh', nodeVersion);
    },
    refresh: function (nodeId) {
        var me = this;
        me.body.update('<iframe width="100%" height="100%" marginwidth="0"  marginheight="0" frameborder="0" scrolling="no" src = "' + serviceName +
            '/nodeVersions/listPlanWheelIteration.rdm?nodeId=' + nodeId + '"></iframe>')
    }
});