/**
 * Created by User on 2020/3/16.
 */
Ext.define('OrientTdm.FormTemplateMgr.FormTemplatePanel.CheckHeaderEndMgrPanel.CheckHeaderPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.checkHeaderPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CHECK_CELL,
        modelName: TDM_SERVER_CONFIG.CHECK_CELL
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var checkTempId=me.checkTempId;
        // var customerFilter=new CustomerFilter('T_CHECK_TEMP_'+me.schemaId+'_ID',CustomerFilter.prototype.SqlOperation.Equal,'',me.checkTempId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter:[me.customerFilter],
            createUrl: serviceName + "/formTemplate/saveCheckHeaderOrEndData.rdm",
            updateUrl: serviceName + "/formTemplate/updateCheckHeaderOrEndData.rdm",
            afterInitComponent: function () {
                //构建完表格后 定制操作
                var addButton = this.dockedItems[0].down('button[text=新增]');
                var updateButton = this.dockedItems[0].down('button[text=修改]');
                var deleteButton = this.dockedItems[0].down('button[text=级联删除]');
                Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                    //新增表单出现后 相关定制
                    var customPanel = button.orientBtnInstance.customPanel;
                    if (customPanel) {
                        //注入额外参数
                        customPanel.originalData = Ext.apply(customPanel.originalData || {}, {
                            // T_CHECK_TEMP_480_ID: modelGrid.getStore().getProxy().extraParams.checkTempId,
                            T_CHECK_TEMP_480_ID:checkTempId,
                            C_IS_HEADER_3219: true
                        });
                    }
                });

                // if (deleteButton) {
                //     deleteButton.handler = Ext.bind(me.deleteHeaderData, me, [this], true);
                // }
            },
            // afterOperate: function (operator) {
            //     //新增、修改、删除后调用此方法
            //     var me=this;
            //     var checkTempId = modelGrid.getStore().getProxy().extraParams.checkTempId;
            //     var checkEndFilter=me.ownerCt.ownerCt.ownerCt.ownerCt.westPanel.addCustomerFilterHeader(checkTempId);
            //     modelGrid.refreshGridByCustomerFilter([checkEndFilter], true);
            // },
        });
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },
    deleteHeaderData: function (btn, event, modelGridPanel) {
        var me = this;
        var selections = modelGridPanel.getSelectionModel().getSelection();
        if (selections.length === 0) {
            OrientExtUtil.Common.tip(OrientLocal.prompt.info, OrientLocal.prompt.atleastSelectOne);
        } else {
            //执行删除询问
            Ext.MessageBox.confirm(OrientLocal.prompt.confirm, OrientLocal.prompt.deleteConfirm, function (btn) {
                if (btn == 'yes') {
                    //获取待删除数据ID
                    var toDelIds = OrientExtUtil.GridHelper.getSelectRecordIds(modelGridPanel);
                    Ext.getBody().mask("请稍后...", "x-mask-loading");
                    Ext.Ajax.request({
                        url: modelGridPanel.getStore().getProxy().api["delete"],
                        params: {
                            toDelIds: toDelIds,
                            modelId: modelGridPanel.modelId,
                            templateId: modelGridPanel.templateId
                        },
                        success: function (response) {
                            Ext.getBody().unmask();
                            var checkTempId = modelGridPanel.getStore().getProxy().extraParams.checkTempId;
                            var checkEndFilter=me.ownerCt.ownerCt.ownerCt.westPanel.addCustomerFilterHeader(checkTempId);
                            modelGridPanel.refreshGridByCustomerFilter([checkEndFilter], true);
                        }
                    });
                }
            });
        }
    }
});