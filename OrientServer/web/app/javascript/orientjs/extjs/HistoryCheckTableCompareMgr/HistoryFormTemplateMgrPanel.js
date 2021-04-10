Ext.define('OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateMgrPanel',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.historyFormTemplateMgrPanel',
    initComponent:function(){
        var me=this;
        var leftPanel = Ext.create('OrientTdm.HistoryCheckTableCompareMgr.HistoryFormTemplateCheckTree', {
            region: 'west',
            checkTypeId: me.checkTypeId,
            checkTypeName:me.checkTypeName,
            collapsible: false,
            autoScroll : true,
            width:290,
            minWidth: 290,
            maxWidth: 350,
            rootVisible: false,
            split:true
        });
        var centerTabPanel= Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
            layout:'border',
            region: 'center'
        });
        Ext.apply(this, {
            layout: 'border',
            items: [leftPanel,centerTabPanel],
            westPanel: leftPanel,
            centerPanel: centerTabPanel
        });
        me.callParent(arguments);
    }
});