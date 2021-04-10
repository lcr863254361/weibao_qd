/**
 * Created by liuyangchao on 2019/3/6.
 */
Ext.define('OrientTdm.GlobalMap.MapTaskPanel',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.mapTaskPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.DIVING_TASK,
        taskId: '',
        flowId: '',
        taskName: ''
    },
    requires: [

    ],
    initComponent:function(){
        var me=this;
        // var panel = Ext.create('OrientTdm.TaskPrepareMgr.MxGraphEditor.MxGraphEditorPanel', {
        //     region: 'center',
        //     taskId: me.taskId,
        //     //颜色表示进度 无工具栏
        //     isStatusSearch: me.isStatusSearch,
        //     flowId: me.flowId
        //     //treeNode: treeNode
        // });
        // var teststageTabPanel = Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.SetsMgrStageTabPanel', {
        //     layout: 'fit',
        //     height: '38%',
        //     region: 'south',
        //     animCollapse: true,
        //     collapsible: true,
        //     header: true,
        //     isStatusSearch: me.isStatusSearch,
        //     taskId: me.taskId,
        //     //nodeId: me.nodeId,
        //     //nodeText: me.nodeText
        // });
        //
        // Ext.apply(this,{
        //     layout:'border',
        //     items: [panel, teststageTabPanel],
        //     panel: panel,
        //     listeners:{
        //
        //     }
        // });

        // OrientExtUtil.AjaxHelper.doRequest(serviceName + '/CurrentTaskMgr/getDivingTaskTreeNodeId.rdm', {}, false, function (response) {
        //     me.taskId = response.decodedData.results.id;
        //     me.taskName=response.decodedData.results.text;
        // });

    //    var mxGraphEditorPanel = Ext.create('OrientTdm.TaskPrepareMgr.MxGraphEditor.MxGraphEditorPanel', {
    //        region: 'center',
    //        taskId: me.taskId,
    //        taskName:me.taskName,
    //        //颜色表示进度 无工具栏
    //        isStatusSearch: true
    //    });
    //
    //
    //    var showCheckTablePanel = Ext.create('OrientTdm.CurrentTaskMgr.ShowCheckTablePanel', {
    //        layout: 'fit',
    //        height: '38%',
    //        region: 'south',
    //        animCollapse: true,
    //        collapsible: true,
    //        header: true,
    //        taskId: me.taskId
    //    });
    //
    //    Ext.apply(this, {
    //        layout: 'border',
    //        items: [mxGraphEditorPanel, showCheckTablePanel],
    //        mxGraphEditorPanel: mxGraphEditorPanel,
    //        showCheckTablePanel: showCheckTablePanel
    //    });
    //
    //    me.callParent(arguments);
    //}

        var mxGraphEditorPanel = Ext.create('OrientTdm.TaskPrepareMgr.MxGraphEditor.MxGraphEditorPanel', {
            region: 'center',
            taskId: me.taskId,
            taskName:me.taskName,
            //颜色表示进度 无工具栏
            isStatusSearch: true,
            //isStatusMapSearch:true
        });

        var centerTabPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientTabPanel', {
            layout: 'fit',
            height: '38%',
            region: 'south',
            animCollapse: true,
            collapsible: true,
            header: true,
            id:'centerTabPanelWater'
            //nodeId: me.nodeId,
            //nodeText: me.nodeText
        });
        var showCheckTablePanel = Ext.create('OrientTdm.CurrentTaskMgr.ShowCheckTablePanel', {
            region: 'center',
            padding: '0 0 0 0',
            taskId: me.taskId
        });
        centerTabPanel.add( {
            layout: 'border',
            title: '检查表格',
            //iconCls: treeNode.get('iconCls'),
            items: [
                showCheckTablePanel
            ],
            showCheckTablePanel:showCheckTablePanel
        });
        centerTabPanel.setActiveTab(0);

        Ext.apply(this, {
            layout: 'border',
            items: [mxGraphEditorPanel, centerTabPanel],
            mxGraphEditorPanel: mxGraphEditorPanel,
            centerTabPanel: centerTabPanel
        });

        me.callParent(arguments);
    }


})