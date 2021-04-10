Ext.define('OrientTdm.CollabDev.Processing.CompositeQuery.Result.CompositeQueryResultContainer', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.compositeQueryResultContainer',
    requires: [],
    initComponent: function () {
        var _this = this;

        var filterTree = Ext.create('OrientTdm.CollabDev.Processing.CompositeQuery.Result.CompositeQueryFilterTree', {
            region: 'west',
            width: 280,
            minWidth: 280,
            maxWidth: 400
        });

        var grid = Ext.create('OrientTdm.CollabDev.Processing.CompositeQuery.Result.CompositeQueryResultGrid', {
            region: 'center'
        });

        Ext.apply(_this, {
            layout: 'border',
            items: [filterTree, grid],
            westPanel: filterTree,
            centerPanel: grid
        });

        _this.callParent(arguments);
    }
});