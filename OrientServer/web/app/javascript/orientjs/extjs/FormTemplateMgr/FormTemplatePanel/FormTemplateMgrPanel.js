Ext.define('OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateMgrPanel',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.formTemplateMgrPanel',
    requires:[
        "OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTempletCheckTree",
        "OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTempletHeadCellGrid"
    ],
    initComponent:function(){
        var me=this;

        var leftPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateCheckTree', {
            region: 'west',
            checkTypeId: me.checkTypeId,
            checkTypeName:me.checkTypeName,
            collapsible: false,
            autoScroll : true,
            width:295,
            minWidth: 295,
            maxWidth: 350,
            rootVisible: false,
            split:true
        });
        // var checkItemPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateHeadCellGrid', {
        //     region: 'center',
        //     padding: '0 0 0 5',
        //     isInst:false,
        //     isShowProduct:true
        // });
        var centerTabPanel= Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
            layout:'border',
            region: 'center'
        });
        // var checkHeaderEndPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.CheckHeaderEndMgrPanel.CheckHeaderPanel', {
        //     region: 'center',
        //     padding: '0 0 0 5'
        // });

        // var checkEndPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.CheckHeaderEndMgrPanel.CheckEndPanel', {
        //     region: 'center',
        //     padding: '0 0 0 5'
        // });

        // centerTabPanel.add({
        //     title: '检查项管理',
        //     // iconCls: record.raw['iconCls'],
        //     layout: 'border',
        //     items: [checkItemPanel]
        // });

        // centerTabPanel.add({
        //     title: '检查表表头管理',
        //     // iconCls: record.raw['iconCls'],
        //     layout: 'border',
        //     items: [checkHeaderEndPanel]
        // });

        // centerTabPanel.add({
        //     title: '检查表表尾管理',
        //     // iconCls: record.raw['iconCls'],
        //     layout: 'border',
        //     items: [checkEndPanel]
        // });

        // centerTabPanel.setActiveTab(0);
        Ext.apply(this, {
            layout: 'border',
            items: [leftPanel,centerTabPanel],
            westPanel: leftPanel,
            centerPanel: centerTabPanel
        });
        me.callParent(arguments);
    },
    // afterInitComponent: function () {
    //     var panel = this;
    //     var tree = panel.items.items[0];
    //
    //     //默认显示第一个子节点
    //     var node = tree.getRootNode();
    //     tree.getSelectionModel().select(node);
    //     tree.fireEvent('itemclick', tree, node);
    // }
});