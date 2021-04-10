/**
 * Created by User on 2020/3/16.
 */
Ext.define('OrientTdm.FormTemplateMgr.FormTemplatePanel.CheckHeaderEndMgrPanel.CheckEndPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.checkEndPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CHECK_CELL,
        modelName: TDM_SERVER_CONFIG.CHECK_CELL
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var checkTempId = me.checkTempId;
        // var customerFilter=new CustomerFilter('T_CHECK_TEMP_'+me.schemaId+'_ID',CustomerFilter.prototype.SqlOperation.Equal,'',me.checkTempId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            createUrl: serviceName + "/formTemplate/saveCheckHeaderOrEndData.rdm",
            customerFilter:[me.customerFilter],
            afterInitComponent: function () {
                //构建完表格后 定制操作
                var addButton = this.dockedItems[0].down('button[text=新增]');
                Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                    //新增表单出现后 相关定制
                    var customPanel = button.orientBtnInstance.customPanel;
                    if (customPanel) {
                        //注入额外参数
                        customPanel.originalData = Ext.apply(customPanel.originalData || {}, {
                            T_CHECK_TEMP_480_ID:checkTempId,
                            C_IS_HEADER_3219: false
                        });
                    }
                });
            }
        });
        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});