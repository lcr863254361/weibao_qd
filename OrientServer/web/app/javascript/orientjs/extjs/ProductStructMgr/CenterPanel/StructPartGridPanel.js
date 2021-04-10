Ext.define('OrientTdm.ProductStructMgr.CenterPanel.StructPartGridPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.structPartGridPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_STRUCT_PART,
        modelName: TDM_SERVER_CONFIG.STRUCT_PART,
    },

    initComponent: function () {
        var me = this;
        var isOnlyShow = me.isOnlyShow;
        var customerFilter = me.customerFilter;
        var bindNode = me.bindNode;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter: customerFilter,
            afterInitComponent: function () {
                //构建完表格后 定制操作
                var addButton = this.dockedItems[0].down('button[text=新增]');
                Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                    //新增表单出现后 相关定制
                    var customPanel = button.orientBtnInstance.customPanel;
                    if (customPanel) {
                        if (bindNode.raw.level == 5) {
                            var formValue = {};
                            var typeColumnName = "C_TYPE_" + modelId;
                            //object对象中动态设置值
                            formValue[typeColumnName] = bindNode.raw.text;
                            customPanel.getForm().setValues(formValue);
                            //注入额外参数
                            customPanel.originalData = Ext.apply(customPanel.originalData || {}, {
                                T_STRUCT_SYSTEM_480_ID: bindNode.parentNode.parentNode.parentNode.raw.idList[0],
                                T_STRUCT_DEVICE_480_ID: bindNode.parentNode.raw.idList[0],
                            });
                        } else {
                            //注入额外参数
                            customPanel.originalData = Ext.apply(customPanel.originalData || {}, {
                                T_STRUCT_SYSTEM_480_ID: bindNode.parentNode.parentNode.raw.idList[0],
                                T_STRUCT_DEVICE_480_ID: bindNode.raw.idList[0],
                            });
                        }
                    }
                });
            },
            afterOperate: function () {
                var treepanel = Ext.getCmp("productStructDashboard").westPanel;
                var store = treepanel.getStore();
                var selectNode = treepanel.getSelectionModel().getSelection()[0];
                if (selectNode.raw.level == 4) {
                    selectNode = selectNode.parentNode;
                    store.load({
                        node: selectNode,
                        callback: function (records) {
                            selectNode.appendChild(records);	//添加子节点
                            selectNode.set('leaf', false);
                            selectNode.expand(true);
                        }
                    });
                }

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