/**
 * Created by User on 2018/12/26.
 */
Ext.define('OrientTdm.PlatformTemplateMgr.Center.TabPanel.CheckTableMgrPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.checkTableMgrPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CHECK_TEMP_INST,
        modelName: TDM_SERVER_CONFIG.CHECK_TEMP_INST,
        //hangciId:''
    },
    requires: ["OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil"],
    id: 'bindCheckTableTempGrid_2',

    initComponent: function () {
        var me = this;
        //me.nodeId = "";
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        //hangciId=me.hangciId;
        console.log(me);
        var customerFilter = new CustomerFilter('T_FLOW_TEMP_TYPE_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.flowTempTypeId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: [customerFilter],
            id: 'bindCheckTableTempGrid_own_2'
        });
        var toolbar = [{
            xtype: 'button',
            iconCls: 'icon-create',
            text: '添加',
            handler: function () {
                //var me = this;
                //动态获取上上上个页面传过来的参数
                var nodeId = modelGrid.store.proxy.extraParams['nodeId'];
                var nodeText = modelGrid.store.proxy.extraParams['nodeText'];
                //弹出新增面板窗口
                var createWin = Ext.create('Ext.Window', {
                    title: '新增检查表模板实例',
                    plain: true,
                    height: 0.5 * globalHeight,
                    width: 0.5 * globalWidth,
                    layout: 'fit',
                    maximizable: true,
                    modal: true,
                    items: [Ext.create('OrientTdm.PlatformTemplateMgr.Center.TabPanel.AddPanel.AddCheckTempGrid', {
                        nodeId: nodeId,
                        nodeText: nodeText,
                        //hangciId:hangciId,
                        flowTempTypeId: me.flowTempTypeId,
                        successCallback: function (resp, callBackArguments) {
                            me.fireEvent("refreshGrid");
                            if (callBackArguments) {
                                createWin.close();
                            }
                        }
                    })]
                });
                createWin.show();
            }
        }, {
            xtype: 'button',
            iconCls: 'icon-delete',
            text: '删除',
            scope: me,
            handler: function () {
                if (!OrientExtUtil.GridHelper.hasSelected(modelGrid)) {
                    return;
                }
                var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                var ids = [];
                Ext.each(selectRecords, function (s) {
                    ids.push(s.data.id);
                });
                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/deleteCheckTable.rdm', {
                    id: ids.toString()
                }, false, function (resp) {
                    var ret = Ext.decode(resp.responseText);
                    if (ret.success) {
                        modelGrid.fireEvent('refreshGrid');
                        // Ext.Msg.alert("提示","删除成功！")
                        OrientExtUtil.Common.tip('成功', "删除成功！");
                    }
                })
            }
        }];

        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0]);
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].add(toolbar);
        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0]);
        //隐藏产品配置的按钮
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].hide();
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(true);

        //增加检查表实例查看按钮
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
                            // var checkTempId = data.raw.ID;
                            // var productId = '';
                            // me.preview(checkTempId, true, productId);
                            var instanceId = data.raw.ID;
                            Ext.create('Ext.Window', {
                                    plain: true,
                                    title: '预览',
                                    height: 0.95*globalHeight,
                                    width: 0.95*globalWidth,
                                    layout: 'fit',
                                    constrain: true,   //限制窗口不超出浏览器边界
                                    maximizable: true,
                                    modal: true,
                                    autoScroll: true,
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

    refreshCheckfilePanelByNode: function (nodeId, nodeText) {

        this.nodeId = nodeId;
        this.nodeText = nodeText;
        console.log(this);
        //为了tab页切换过滤检查表数据或岗位数据，此时把nodeId和nodeText放入到teststageTabPanel中
        this.ownerCt.ownerCt.nodeId = this.nodeId
        this.ownerCt.ownerCt.nodeText = this.nodeText;

        //根据节点和任务id
        var checkFilter = this.addCustomerFilterTable();
        this.items.items[0].getStore().getProxy().setExtraParam('customerFilter', Ext.encode([checkFilter]));
        this.items.items[0].getStore().getProxy().setExtraParam('nodeId', nodeId);
        this.items.items[0].getStore().getProxy().setExtraParam('nodeText', nodeText);
        this.items.items[0].getStore().load();
    },

    addCustomerFilterTable: function () {

        var refPostTableName = TDM_SERVER_CONFIG.CHECK_TEMP_INST;
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var modelId = OrientExtUtil.ModelHelper.getModelId(refPostTableName, schemaId);
        var filter = {};
        filter.expressType = "ID";
        filter.modelName = refPostTableName;

        filter.idQueryCondition = {};
        filter.idQueryCondition.params = [this.flowTempTypeId];
        refPostTableName = refPostTableName + "_" + schemaId;
        filter.idQueryCondition.sql = "SELECT ID FROM " + refPostTableName + " WHERE T_FLOW_TEMP_TYPE_" + schemaId + "_ID=?";
        if (Ext.isEmpty(this.nodeId)) {
            //展示所有
            return filter;
        } else {
            var postSql = "C_NODE_ID_" + modelId + "='" + this.nodeId + "'";
            filter.idQueryCondition.sql += " AND (" + postSql;
            filter.idQueryCondition.sql += ")";
            return filter;
        }
    },
    preview: function (checkTempId, withData, productId) {
        //表单预览
        TestProcessUtil.CommonHelper.checkListPreview(checkTempId, true, withData, productId);
    }
});