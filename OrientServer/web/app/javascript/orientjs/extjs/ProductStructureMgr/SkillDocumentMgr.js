/**
 * Created by User on 2019/1/14.
 */
Ext.define('OrientTdm.ProductStructureMgr.SkillDocumentMgr', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.skillDocumentMgr',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,   //得到460
        templateName: TDM_SERVER_CONFIG.TPL_SKILL_DOCUMENT,
        modelName: TDM_SERVER_CONFIG.SKILL_DOCUMENT,  //获取总表中图书管理的模型表名，先在PropertyConstant.java中进行配置数据库表名
        subFlowId: null
    },

    initComponent: function () {    //增加首页展现内容
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);  //modelName为T_LIBRARY,schemaId为460
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        if (me.flowId) {
            var customerFilter = new CustomerFilter('T_DESTROY_FLOW_' + me.schemaId + "_ID", CustomerFilter.prototype.SqlOperation.Equal, '', me.flowId);
        } else if (me.productId) {
            var customerFilter = new CustomerFilter('T_PRODUCT_STRUCTURE_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.productId);
        } else if (me.hangciId) {
            var customerFilter = new CustomerFilter('T_HANGCI_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.hangciId);
        } else if (me.hangduanId) {
            var customerFilter = new CustomerFilter('T_HANGDUAN_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.hangduanId);
        }else if (me.taskId) {
            var customerFilter = new CustomerFilter('T_DIVING_TASK_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.taskId);
        }
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],
            createUrl: serviceName + '/ProductStructrue/saveSkillDocument.rdm?productId=' + me.productId + "&flowId=" + me.flowId + "&hangciId=" + me.hangciId+"&hangduanId="+me.hangduanId+"&taskId="+me.taskId,
            // afterInitComponent: function () {
            //     var toolbar = this.dockedItems[0];
            //     toolbar.add({
            //             iconCls: 'icon-back',
            //             text: '返回上一页',
            //             scope: me,
            //             handler: function () {
            //                 // var centerPanel = Ext.getCmp("taskPrepareCenterPanel");
            //                 var centerPanel = me.ownerCt.ownerCt.ownerCt.centerPanel;
            //                 centerPanel.items.each(function (item, index) {
            //                     centerPanel.remove(item);
            //                 });
            //                 //设置技术安全表模板面板
            //                 var divingTaskMgrGrid = Ext.create("OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.DivingTaskMgrGrid", {
            //                     region: 'center',
            //                     //workItemTempId: node.raw.idList[0]
            //                     flowId: me.hangduanId,
            //                 });
            //                 centerPanel.add({
            //                     title: "下潜任务管理",
            //                     //iconCls: "icon-preview",
            //                     layout: 'border',
            //                     items: [divingTaskMgrGrid]
            //                 });
            //                 centerPanel.setActiveTab(0);
            //             }
            //         }
            //     )
            // },
            extraParams: {
                flowId: me.flowId
            }
        });
        if (me.flowId && me.destroyTaskState === '未开始') {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[4].setVisible(true);
        } else if (me.flowId && me.destroyTaskState === '进行中') {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(false);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[4].setVisible(true);
        } else if (me.flowId && me.destroyTaskState === '已结束') {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(true);
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[4].setVisible(false);
        } else if (me.isDestroyTemp) {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[4].setVisible(true);
        } else if (me.isProductStructure) {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[4].setVisible(true);
        } else if (me.isStatusSearch) {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setVisible(false);
        } else {
            modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(false);
        }
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);   //调用父类的方法，返回父类方法中的内容
    }

});