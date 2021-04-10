Ext.define('OrientTdm.DivingStatisticsMgr.StatisticsDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.statisticsDashboard',

    initComponent:function(){
        var me=this;
        // var queryPanel = Ext.create('OrientTdm.DivingStatisticsMgr.QueryConditionPanel', {
        //     region: 'north',
        //     height: 120
        // });
        // var centerPanel=Ext.create('OrientTdm.DivingStatisticsMgr.StatisticsManagerGrid',{
        //     region: 'center',
        //     padding: '0 0 0 5'
        // });

        // Ext.apply(me,{
        //     // layout:'card',
        //     layout: 'border',
        //     items: [queryPanel, centerPanel],
        //     queryPanel: queryPanel,
        //     centerPanel: centerPanel,
        //     // activeItem:0
        // });
        var statisticLineHead;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/divingStatisticsMgr/getStatisticsLineHead.rdm','', false, function (resp) {
            statisticLineHead = resp.decodedData.results;
        });
        var centerPanel=Ext.create('OrientTdm.DivingStatisticsMgr.DynamicStatisticsLineGrid',{
            region: 'center',
            padding: '0 0 0 5',
            statisticLineHead: statisticLineHead
        });
        Ext.apply(me,{
            layout: 'border',
            items: [centerPanel],
            centerPanel: centerPanel,
        });
        me.callParent(arguments);
    }
});