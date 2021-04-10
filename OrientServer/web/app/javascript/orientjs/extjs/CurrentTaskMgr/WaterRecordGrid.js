/**
 * Created by User on 2019/3/14.
 */
Ext.define('OrientTdm.CurrentTaskMgr.WaterRecordGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.showCheckTablePanel',
    id: 'waterRecordGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_WATER_RECORD,
        modelName: TDM_SERVER_CONFIG.CHECK_TEMP_INST
    },
    requires: ["OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil"],

    initComponent: function () {
        var me = this;
        //me.nodeId = "";
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var taskId = me.taskId;
        ////根据节点和任务id
        //var filter = me.addCustomerFilterTable(modelId);
        console.log(me);
        //var nodeId=me.getStore().getProxy().getExtraData('nodeId');
        //var nodeText=me.getStore().getProxy().getExtraData('nodeText');
        var customerFilter = new CustomerFilter('T_DIVING_TASK_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', taskId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],
            queryUrl: serviceName + "/CurrentTaskMgr/getCheckInstData.rdm"
        });
        //增加备品备件实例查看按钮
        var columns = modelGrid.columns;
        if (Ext.isArray(columns)) {
            columns.push(
                {
                    xtype: 'actioncolumn',
                    text: '查看',
                    align: 'center',
                    width: 200,
                    items: [{
                        iconCls: 'icon-detail',
                        tooltip: '表格实例查看',
                        handler: function (grid, rowIndex) {
                            var data = grid.getStore().getAt(rowIndex);
                            // var checkTempId=data.raw.ID;
                            // var productId='';
                            // me.preview(checkTempId,true,productId);
                            var instanceId = data.raw.ID;
                            Ext.create('Ext.Window', {
                                    plain: true,
                                    title: '预览',
                                    height: globalHeight * 0.9,
                                    width: globalWidth * 0.9,
                                    layout: 'fit',
                                    constrain: true,   //限制窗口不超出浏览器边界
                                    maximizable: true,
                                    modal: true,
                                    autoScroll: true,
                                    // draggable: false,  //不允许拖拽
                                    // resizable: false,   //不允许改变窗口大小
                                    items: [
                                        Ext.create('OrientTdm.CurrentTaskMgr.CheckTableCaseHtmlPanel', {
                                            instanceId: instanceId
                                        })
                                    ]
                                }
                            ).show();
                        }
                    }]
                })
        }
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(), columns);
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },

    refreshWaterPanelByNode: function (nodeId, nodeText) {
        var me = this;
        me.nodeId = nodeId;
        me.nodeText = nodeText;

        console.log(me.items.items[0]);

        //根据节点和任务id
        var checkFilter = me.addCustomerFilterTable();
        me.items.items[0].getStore().getProxy().setExtraParam('customerFilter', Ext.encode([checkFilter]));
        //me.items.items[0].getStore().getProxy().setExtraParam('nodeId', me.nodeId);
        //me.items.items[0].getStore().getProxy().setExtraParam('nodeText', me.nodeText);
        me.items.items[0].getStore().load();
    },

    addCustomerFilterTable: function () {
        var me = this;

        var refPostTableName = TDM_SERVER_CONFIG.CHECK_TEMP_INST;
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var modelId = OrientExtUtil.ModelHelper.getModelId(refPostTableName, schemaId);
        var filter = {};
        filter.expressType = "ID";
        filter.modelName = refPostTableName;

        filter.idQueryCondition = {};
        filter.idQueryCondition.params = [me.taskId];
        refPostTableName = refPostTableName + "_" + schemaId;
        filter.idQueryCondition.sql = "SELECT ID FROM " + refPostTableName + " WHERE T_DIVING_TASK_" + schemaId + "_ID=?";
        if (Ext.isEmpty(me.nodeId)) {
            //展示所有
            return filter;
        } else {
            var postSql = "(C_NODE_ID_" + modelId + "='" + me.nodeId + "')";
            filter.idQueryCondition.sql += "AND (" + postSql;
            filter.idQueryCondition.sql += ")";
            return filter;
        }
    },

    preview: function (checkTempId, withData, productId) {
        //表单预览
        TestProcessUtil.CommonHelper.checkListPreview(checkTempId, true, withData, productId);
    }
});