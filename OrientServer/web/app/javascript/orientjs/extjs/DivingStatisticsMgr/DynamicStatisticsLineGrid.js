Ext.define('OrientTdm.DivingStatisticsMgr.DynamicStatisticsLineGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alternateClassName: 'dynamicStatisticsLineGrid',
    config: {
        statisticLineHead: null,
        pageSize: 50
    },

    initComponent: function () {
        var me = this;
        me.statisticLineHead=me.statisticLineHead;
        me.pageSize = 50;
        me.callParent(arguments);
    },
    createColumns: function () {
        var me = this;
        var retVal = me.statisticLineHead.columns;
        return retVal;
    },
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.Store', {
            fields: me.statisticLineHead.fields,
            remoteSort: false,
            //设置为 true 则将所有的过滤操作推迟到服务器
            remoteFilter: true,
            proxy: {
                type: 'ajax',
                url: serviceName + "/divingStatisticsMgr/getRoleStatisticLineData.rdm",
                reader: {
                    type: 'json',
                    root: 'results',
                    totalProperty: 'totalProperty'
                },
                extraParams: {
                    columnJsonString: JSON.stringify(me.statisticLineHead.columns)
                }
            },
            sorters: [{
                direction: 'desc'
            }],
            autoLoad: true
        });
        this.store = retVal;
        return retVal;
    }
});