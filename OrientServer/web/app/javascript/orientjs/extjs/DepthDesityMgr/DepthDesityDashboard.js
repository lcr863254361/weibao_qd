/**
 * Created by User on 2020/2/26.
 */
Ext.define('OrientTdm.DepthDesityMgr.DepthDesityDashboard',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.depthDesityDashboard',

    initComponent:function(){
        // var me=this;
        // var depthDesityGridPanel=Ext.create('OrientTdm.DepthDesityMgr.DepthDesityGridPanel',{
        //     region: 'center'
        // });
        // Ext.apply(this, {
        //     layout: 'border',
        //     items: [depthDesityGridPanel]
        // });
        // me.callParent(arguments);
        var me = this;
        var functionId = me.itemId;
        if (functionId) {
            //截取ID
            functionId = functionId.substr(functionId.indexOf("-") + 1, functionId.length);
        }
        //创建中间面板
        var centerPanel = Ext.create("OrientTdm.DepthDesityMgr.Center.DepthDesityShowRegion", {
            region: 'center',
            padding: '0 0 0 5'
        });
        //Tbom
        var tbomPanel = Ext.create("OrientTdm.DataMgr.TBom.TBomTree", {
            width: 280,
            minWidth: 280,
            maxWidth: 400,
            belongFunctionId: functionId,
            region: 'west'
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
})