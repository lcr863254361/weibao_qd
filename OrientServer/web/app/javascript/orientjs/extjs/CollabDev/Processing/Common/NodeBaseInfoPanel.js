/**
 * 节点基本信息通用面板
 */
Ext.define('OrientTdm.CollabDev.Processing.Common.NodeBaseInfoPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.nodeBaseInfoPanel',
    config: {
        modelName: null,
        dataId: null,
        templateId: null,
        templateName: null,
        schemaId: TDM_SERVER_CONFIG.COLLAB_SCHEMA_ID
    },
    requires: [],
    initComponent: function () {
        var _this = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(_this.modelName, _this.schemaId);
        _this.templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, _this.templateName);
        Ext.apply(_this, {
            layout: 'fit',
            items: [
                {
                    xtype: 'container',
                    title: '占位面板'
                }
            ]
        });
        _this.callParent(arguments);
        _this.restructurePanel();
    },
    restructurePanel: function () {
        var _this = this;
        var modelDesc = null;
        var originalData = null;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/collabNavigation/getGridModelDescAndData.rdm', {
            modelName: _this.modelName,
            dataId: _this.dataId,
            templateId: _this.templateId,
            isView: 0
        }, false, function (response) {
            modelDesc = response.decodedData.results.orientModelDesc;
            originalData = response.decodedData.results.modelData;
        });

        var detailForm = Ext.create('OrientTdm.Common.Extend.Form.OrientDetailModelForm', {
            createDefaultGroup: false,
            bindModelName: _this.modelName,
            originalData: originalData,
            modelDesc: modelDesc,
            width: '100%',
            rowNum: 1
        });
        _this.removeAll(true);
        _this.add(detailForm);
    }
});