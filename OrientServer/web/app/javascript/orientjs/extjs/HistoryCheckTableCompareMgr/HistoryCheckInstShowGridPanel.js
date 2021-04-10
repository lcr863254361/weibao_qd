Ext.define('OrientTdm.HistoryCheckTableCompareMgr.HistoryCheckInstShowGridPanel', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alternateClassName: 'historyCheckInstShowGridPanel',

    initComponent: function () {
        var me = this;
        me.callParent(arguments);
        me.initEvents();
        this.addEvents('filterByFilter');
    },
    initEvents: function () {
        var me = this;
        me.callParent();
        me.mon(me, 'filterByFilter', me.filterByFilter, me);
    },
    createColumns: function () {
        var me = this;
        var retVal=me.checkInstHeader.columns;
        return retVal;
    },
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.Store', {
            fields: me.checkInstHeader.fields,
            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            proxy: {
                type: 'ajax',
                url: serviceName + "/formTemplate/getHistoryCheckInstContent.rdm",
                reader: {
                    type: 'json',
                    root: 'results',
                    totalProperty: 'totalProperty'
                },
                extraParams: {
                    cellId: me.cellId,
                    columnLength:me.checkInstHeader.columns.length,
                    checkTempName:me.checkTempName
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        this.store = retVal;
        return retVal;
    },
    filterByFilter: function (filter) {
        for (var proName in filter) {
            var proValue = filter[proName];
            if (!Ext.isEmpty(proValue)&&proName=== 'startDate' ) {
                proValue=proValue+" 00:00:00"
            }
            if (!Ext.isEmpty(proValue)&&proName=== 'endDate' ) {
                proValue=proValue+" 23:59:59"
            }
            this.getStore().getProxy().setExtraParam(proName, proValue);
        }
        this.getStore().loadPage(1);
    }
});