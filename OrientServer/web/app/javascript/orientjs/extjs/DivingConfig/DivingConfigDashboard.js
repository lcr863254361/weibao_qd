/**
 * Created by User on 2019/1/12.
 */
Ext.define('OrientTdm.DivingConfig.DivingConfigDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.divingConfigDashboard',
    requires:[],

    initComponent:function(){
        var me=this;

        var centerPanel=Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel',{
            region: 'center',
            layout: 'fit',
            activeTabName : '',
            padding: '0 0 0 5'
        });
        var productTreeGridPanel=Ext.create('OrientTdm.DivingConfig.CenterPanel.DivingTypeConfigGrid',{
            region: 'center'
        });

        var skillDocumentMgr = Ext.create('OrientTdm.DivingConfig.CenterPanel.DivingConfigGrid', {
            region: 'center'
        });

        var currentCheckRecordMgr = Ext.create('OrientTdm.DivingConfig.CenterPanel.DivingWorkTypeConfigGrid', {
            region: 'center'
        });
        centerPanel.add({
            title: '无人潜水器类型配置',
            layout: 'border',
            items: [productTreeGridPanel]
        });

        centerPanel.add({
            title: '无人潜水器配置',
            layout: 'border',
            items: [skillDocumentMgr]
        });

        centerPanel.add({
            title: '无人潜水器作业种类配置',
            layout: 'border',
            items: [currentCheckRecordMgr]
        });
        centerPanel.setActiveTab(0);
        Ext.apply(this, {
            layout: 'border',
            items: [centerPanel],
            centerPanel: centerPanel
        });

        me.callParent(arguments);
    }
})