
Ext.define('OrientTdm.PlatformTemplateMgr.PlatformTemplateMgrDashBoard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.platformTemplateMgrDashBoard',
    requires: [
        "OrientTdm.PlatformTemplateMgr.Center.PlatformTemplateMgrDataShowRegion"
    ],
    initComponent: function () {
        var me = this;
        //me.platformId = '0';
        //me.platformText = '0';
        ////创建中间面板
        //var panel = Ext.create('OrientTdm.PlatformTemplateMgr.MxGraphEditor.MxGraphEditorPanel', {
        //    region: 'center',
        //    platformId: me.platformId,
        //    backButtonShow:false
        //});
        //var teststageTabPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
        //    layout: 'fit',
        //    height: '45%',
        //    region: 'south',
        //    animCollapse: true,
        //    collapsible: true,
        //    header: true,
        //    id:'teststageTabPanelowner'
        //    //nodeId: me.nodeId,
        //    //nodeText: me.nodeText
        //});
        //var attendPostMgrPanel = Ext.create("OrientTdm.PlatformTemplateMgr.Center.TabPanel.AttendPostMgrPanel", {
        //    region: 'center',
        //    padding: '0 0 0 0',
        //    readModel:false,
        //    hangciId: me.platformId
        //});
        //var checkTableMgrPanel = Ext.create("OrientTdm.PlatformTemplateMgr.Center.TabPanel.CheckTableMgrPanel", {
        //    region: 'center',
        //    padding: '0 0 0 0',
        //    hangciId: me.platformId
        //});
        //
        //teststageTabPanel.add({
        //    layout: 'border',
        //    title: '参与岗位',
        //    //iconCls: treeNode.get('iconCls'),
        //    items: [
        //        attendPostMgrPanel
        //    ],
        //    attendPostMgrPanel:attendPostMgrPanel
        //}, {
        //    layout: 'border',
        //    title: '检查表格',
        //    //iconCls: treeNode.get('iconCls'),
        //    items: [
        //        checkTableMgrPanel
        //    ],
        //    checkTableMgrPanel:checkTableMgrPanel
        //});
        //teststageTabPanel.setActiveTab(0);
        //Ext.apply(me, {
        //    layout: 'border',
        //    items: [panel,teststageTabPanel],
        //    panel: panel,
        //    stageTabPanel: teststageTabPanel
        //});
        //me.callParent(arguments);
        var leftPanel = Ext.create('OrientTdm.PlatformTemplateMgr.FlowTempTypeGrid', {
            title: '<span style="color: blue; ">'+'流程模板管理'+'</span>',
            titleAlign:'center',
            autoScroll : true,
            collapsible: false,
            width:250,
            minWidth:250,
            maxWidth:300,
            region: 'west',
            split: true
        });

        var centerPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
            region: 'center',
            layout: 'fit',
            padding: '0 0 0 5'
        });

        Ext.apply(this, {
            layout: 'border',
            items: [leftPanel, centerPanel],
            westPanel: leftPanel,
            centerPanel: centerPanel
        });
        me.callParent(arguments);
    }
});
