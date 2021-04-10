/**
 *综合查询面板
 */
Ext.define('OrientTdm.CollabDev.Processing.CompositeQuery.CompositeQueryMainPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.compositeQueryMainPanel',
    requires: [],
    initComponent: function () {
        var _this = this;

        var conditionPanel = Ext.create('OrientTdm.CollabDev.Processing.CompositeQuery.Query.CompositeQueryConditionPanel', {
            title: '综合查询',
            region: 'north',
            height: 100,
            minHeight: 100,
            maxHeight: 400
        });

        var queryResultContainer = Ext.create('OrientTdm.CollabDev.Processing.CompositeQuery.Result.CompositeQueryResultContainer', {
            title: '查询结果',
            region: 'center'
        });

        Ext.apply(_this, {
            layout: 'border',
            items: [conditionPanel, queryResultContainer],
            northPanel: conditionPanel,
            centerPanel: queryResultContainer
        });

        _this.callParent(arguments);

    }
});