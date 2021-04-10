/**
 * Created by User on 2018/12/21.
 */
Ext.define('OrientTdm.TaskPrepareMgr.TaskPrepareManagerDashboard', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.taskPrepareManagerDashboard',
    id:'taskPrepareDashboardOwner1',
    requires: [
        "OrientTdm.TaskPrepareMgr.TBom.TBomTree",
        "OrientTdm.DataMgr.Center.DataShowRegion"
    ],
    initComponent: function () {
        var me = this;
        var functionId = me.itemId;
        if (functionId) {
            //截取ID
            functionId = functionId.substr(functionId.indexOf("-") + 1, functionId.length);
        }
        //创建中间面板
        var centerPanel = Ext.create("OrientTdm.DataMgr.Center.DataShowRegion", {
            region: 'center',
            padding: '0 0 0 5'
        });
        //Tbom
        var tbomPanel = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TBomTemplateTree", {
            width: 240,
            minWidth: 240,
            maxWidth: 300,
            border: false,
            belongFunctionId: functionId,
            region: 'west',
            isHistoryTaskSearch:false
        });

        tbomPanel.expandAll();

        Ext.apply(me, {
            layout: 'border',
            items: [centerPanel, tbomPanel],
            westPanel: tbomPanel,
            centerPanel: centerPanel
        });
        me.callParent(arguments);
    }
});