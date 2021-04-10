/**
 * Created by User on 2019/1/28.
 */
Ext.define('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TBomTemplateTree', {
    extend: 'OrientTdm.DataMgr.TBom.TBomTree',
    alias: 'widget.tBomTemplateTree',
    initComponent: function () {
        var me = this;
        me.isHistoryTaskSearch=me.isHistoryTaskSearch;
        me.callParent(arguments);
    },

    selectItem: function (tree, node) {
        var me = this;

        if (me.ownerCt.centerPanel) {
            switch (node.raw.level) {
                case 1:
                    me.initHangciCenterPanel(node);
                    break;
                case 2:
                    me.initHangduanCenterPanel(node);
                    break;
                case 3:
                    me.initDivingTaskCenterPanel(node);
                default :
                    //me.ownerCt.centerPanel.fireEvent("initModelDataByNode", node);
                    break;
            }
        }
    },

    initHangciCenterPanel: function (node) {
        var me = this;
        var centerPanel = this.ownerCt.centerPanel;

        // var centerPanel = me.up("taskPrepareManagerDashboard").centerPanel;
        //移除所有面板
        centerPanel.items.each(function (item, index) {
            centerPanel.remove(item);
        });

        //设置航次面板
        var hangciMgrGrid = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.HangciMgrGrid", {
            region: 'center',
            //workItemTempId: node.raw.idList[0]
        });

        centerPanel.add({
            title: "航次管理",
            //iconCls: "icon-preview",
            layout: 'border',
            items: [hangciMgrGrid]
        });
        centerPanel.setActiveTab(0);
    },

    initHangduanCenterPanel: function (node) {
        var me = this;
        var centerPanel = this.ownerCt.centerPanel;
        //移除所有面板
        centerPanel.items.each(function (item, index) {
            centerPanel.remove(item);
        });

        var hangduanMgrGrid = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.HangduanMgrGrid", {
            region: 'center',
            //workItemTempId: node.raw.idList[0]
            flowId: node.raw.idList[0]
        });
        centerPanel.add({
            title: "航段管理",
            //iconCls: "icon-preview",
            layout: 'border',
            items: [hangduanMgrGrid]
        });
        var taskSkillPanel = Ext.create('OrientTdm.ProductStructureMgr.SkillDocumentMgr', {
            // title: '技术资料',
            region: 'center',
            hangciId:node.raw.idList[0],
            productId: "",
            flowId: ""
        });
        centerPanel.add(
            {
                title: '技术资料',
                layout: 'border',
                items: [taskSkillPanel],
                taskSkillPanel: taskSkillPanel
            }
        );
        centerPanel.setActiveTab(0);
    },

    initDivingTaskCenterPanel: function (node) {
        var me = this;
        var centerPanel = this.ownerCt.centerPanel;
        //移除所有面板
        centerPanel.items.each(function (item, index) {
            centerPanel.remove(item);
        });

        //设置下潜任务面板
        var divingTaskMgrGrid = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.DivingTaskMgrGrid", {
            region: 'center',
            //workItemTempId: node.raw.idList[0]
            flowId: node.raw.idList[0],
            node: node,
            isScientist: this.isScientist,
            isHistoryTaskSearch:me.isHistoryTaskSearch
        });
        centerPanel.add({
            title: "下潜任务管理",
            //iconCls: "icon-preview",
            layout: 'border',
            items: [divingTaskMgrGrid]
        });
        var personnelWeightGrid = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.PersonnelWeightGrid", {
            region: 'center',
            flowId: node.raw.idList[0],
            node: node,
        });
        centerPanel.add({
            title: "航段信息管理",
            //iconCls: "icon-preview",
            layout: 'border',
            items: [personnelWeightGrid]
        });
        var params = {
            hangduanId: node.raw.idList[0]
        };
        var flowPostData;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/getFlowPostHead.rdm', params, false, function (resp) {
            flowPostData = resp.decodedData.results;
        });
        var flowPostMgrGrid = Ext.create('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.FlowPost.FlowPostMgrDashbord', {
            hangduanId: node.raw.idList[0],
            flowPostData: flowPostData,
            region: 'center'
        });
        centerPanel.add({
            title: "流动岗位管理",
            //iconCls: "icon-preview",
            layout: 'border',
            items: [flowPostMgrGrid],
            listeners: {
                'beforeshow': function () {
                    flowPostMgrGrid.store.load();
                }
            }
        });
        var params = {
            hangduanId: node.raw.idList[0]
        };
        var flowPostData;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/getAttendTimesFlowPostHead.rdm', params, false, function (resp) {
            flowPostData = resp.decodedData.results;
        });
        var attendTimesStatisticsMgr = Ext.create('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.FlowPost.AttendTimesStatisticsMgr', {
            hangduanId: node.raw.idList[0],
            flowPostData: flowPostData,
            region: 'center'
        });

        centerPanel.add({
            title: "参与次数统计",
            //iconCls: "icon-preview",
            layout: 'border',
            items: [attendTimesStatisticsMgr],
            listeners: {
                'beforeshow': function () {
                    attendTimesStatisticsMgr.store.load();
                }
            }
        });

        var taskSkillPanel = Ext.create('OrientTdm.ProductStructureMgr.SkillDocumentMgr', {
            // title: '技术资料',
            region: 'center',
            hangduanId: node.raw.idList[0],
            productId: "",
            hangciId:"",
            flowId: ""
        });
        centerPanel.add(
            {
                title: '航段文件',
                layout: 'border',
                items: [taskSkillPanel],
                taskSkillPanel: taskSkillPanel
            }
        );
        centerPanel.setActiveTab(0);
    }
});