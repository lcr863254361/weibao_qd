/**
 * Created by User on 2019/2/14.
 */
Ext.define('OrientTdm.ProductStructureMgr.ShowCheckTablePanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.showCheckTablePanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_SHOW_CHECK_TEMP_INST,
        modelName: TDM_SERVER_CONFIG.CHECK_TEMP_INST
    },
    requires: ["OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil"],

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var productId = me.productId;
        console.log(me);
            var customerFilter = new CustomerFilter('C_PRODUCT_ID_'+modelId, CustomerFilter.prototype.SqlOperation.Equal, '', productId);
            var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
                region: 'center',
                modelId: modelId,
                isView: 0,
                templateId: templateId,
                customerFilter: [customerFilter],
                queryUrl: serviceName + "/CurrentTaskMgr/getCheckInstData.rdm",
                afterInitComponent:function() {
                    var me = this;
                    me.viewConfig.getRowClass = function (record, rowIndex, rowParams, store) {
                        if (record.data['C_CHECK_STATE_' + modelId] == '异常') {
                            return 'x-grid-record-red';
                        }
                    }
                }
            });
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
                                var instanceId=data.raw.ID;
                                Ext.create('Ext.Window', {
                                        plain: true,
                                        title: '预览',
                                        height: globalHeight * 0.9,
                                        width: globalWidth * 0.9,
                                        layout: 'fit',
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
        preview: function (checkTempId, withData, productId) {
            //表单预览
            TestProcessUtil.CommonHelper.checkListPreview(checkTempId, true, withData, productId);
        }
    });