Ext.define('OrientTdm.DivingStatisticsMgr.DynamicLineMgr.DynamicLineDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.dynamicLineDashboard',

    initComponent:function(){
        var me = this;
        var divingStatisticGridPanel = Ext.create('OrientTdm.DivingStatisticsMgr.DynamicLineMgr.DivingStatisticGridPanel', {
            region: 'center'
        });

        Ext.apply(me, {
            layout: 'border',
            items: [divingStatisticGridPanel],
            centerPanel: divingStatisticGridPanel,
        });
        me.callParent(arguments);
    }
});