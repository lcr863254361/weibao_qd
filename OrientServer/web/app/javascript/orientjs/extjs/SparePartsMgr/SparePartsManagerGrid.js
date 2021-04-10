/**
 * Created by User on 2018/12/11.
 */
Ext.define('OrientTdm.SparePartsMgr.SparePartsManagerGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.sparePartsManagerGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_SPARE_PARTS,
        modelName: TDM_SERVER_CONFIG.SPARE_PARTS
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId=OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        var isCarryTool=me.isCarryTool;
        var isCabinOutOrIn=me.isCabinOutOrIn;
        var isShowLWH=me.isShowLWH;
         if (isCarryTool){
              templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, TDM_SERVER_CONFIG.TPL_SPARE_PARTS_CARRY_TOOL);
         }
        //var customerFilter=new CustomerFilter('T_PRODUCT_STRUCTURE_'+me.schemaId+'_ID',CustomerFilter.prototype.SqlOperation.Equal,'',me.productId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            id:'sparePartsMgrOwner',
            //customerFilter:[customerFilter],
            createUrl: serviceName + "/spareParts/saveSparePartsData.rdm?productId=" + me.productId+"&isCarryTool="+isCarryTool+"&isCabinOutOrIn="+isCabinOutOrIn,
            updateUrl: serviceName + '/spareParts/updateSparePartsData.rdm',
            queryUrl: serviceName + "/spareParts/getSparePartsData.rdm?productId=" + me.productId + "&leaf=" + me.leaf,

            afterInitComponent: function () {
                var toolbar = this.dockedItems[0];
                toolbar.add(
                    {
                        iconCls: 'icon-import',
                        text: '导入设备',
                        disabled: false,
                        itemId: 'import',
                        scope: me,
                        hidden:isCarryTool,
                        handler: me.importDeviceWin
                    }, {
                        iconCls: 'icon-export',
                        text: '导出设备',
                        disabled: false,
                        itemId: 'export',
                        scope: me,
                        hidden:isCarryTool,
                        handler: me.exportDeviceData
                    },
                    {
                        xtype: 'button',
                        iconCls: 'icon-delete',
                        text: '级联删除',
                        scope: this,
                        handler: function () {
                            if (!OrientExtUtil.GridHelper.hasSelected(modelGrid)) {
                                return;
                            }
                            var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                            var ids = [];
                            Ext.each(selectRecords, function (s) {
                                ids.push(s.data.id);
                            });
                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/spareParts/delSparePartsData.rdm', {
                                id: ids.toString(),
                                productId: me.productId

                            }, false, function (resp) {
                                var ret = Ext.decode(resp.responseText);
                                if (ret.success) {
                                    modelGrid.fireEvent('refreshGrid');
                                    //Ext.Msg.alert("提示", "删除成功！")
                                }
                            })
                        }
                    });
                // var addButton = toolbar.child('[text=新增]');
                // if (addButton) {
                //     Ext.Function.interceptAfter(addButton, 'handler', function (button) {
                //         var customPanel = button.orientBtnInstance.customPanel;
                //         if (customPanel) {
                //             var length = customPanel.down('[name=C_LENGTH_' + modelId + ']');
                //             var width = customPanel.down('[name=C_WIDTH_' + modelId + ']');
                //             var height = customPanel.down('[name=C_HEIGHT_' + modelId + ']');
                //             var columnDesc=customPanel.ownerCt.items.items[0].columnDescs;
                //             var newColumnDesc=[];
                //             if (columnDesc.length>0){
                //                 for (var i=0;i<columnDesc.length;i++){
                //                     if (columnDesc[i].sColumnName=='C_LENGTH_'+modelId||columnDesc[i].sColumnName=='C_WIDTH_'+modelId||columnDesc[i].sColumnName=='C_HEIGHT_'+modelId) {
                //                         continue;
                //                     }else{
                //                         newColumnDesc.push(columnDesc[i]);
                //                     }
                //                 }
                //                 customPanel.ownerCt.items.items[0].columnDescs=newColumnDesc;
                //                 customPanel.doLayout();
                //             }
                //         }
                //     });
                // }
            }
        });

        //增加备品备件实例查看按钮
        var columns = modelGrid.columns;
        // if (isCarryTool&&isCabinOutOrIn=='cabinIn') {
        //     for (var i=0;i<columns.length;i++){
        //         if (columns[i].dataIndex=='C_DEWATER_VOLUME_'+modelId||columns[i].dataIndex=='C_FRESH_WATER_WEIGHT_'+modelId){
        //             columns[i].hide();
        //         }
        //     }
        // }
        if (Ext.isArray(columns)) {
            if (isCarryTool&&!isShowLWH){
                var newColumns=[];
                for (var i=0;i<columns.length;i++){
                    if (columns[i].dataIndex=='C_LENGTH_'+modelId||columns[i].dataIndex=='C_WIDTH_'+modelId||columns[i].dataIndex=='C_HEIGHT_'+modelId){
                        continue;
                    }else{
                        newColumns.push(columns[i]);
                    }
                }
                columns=newColumns;
            }
            columns.push({
                xtype: 'actioncolumn',
                text: '台账查看',
                align: 'center',
                width: 200,
                items: [{
                    iconCls: 'icon-detail',
                    tooltip: '台账查看',
                    handler: function (grid, rowIndex) {
                        var data = grid.getStore().getAt(rowIndex);
                        var spareId = data.raw.ID;
                        var spareName = data.raw['C_DEVICE_NAME_' + modelId];
                        //设备型号
                        var deviceModel=data.raw['C_MODEL_'+modelId];
                        var currentPage=grid.store.currentPage;
                        var pageSize=grid.store.pageSize;
                        var sparePartsInstGrid = Ext.create('OrientTdm.SparePartsMgr.SparePartsInstGrid', {
                            spareId: spareId,
                            spareName: spareName,
                            productId: me.productId,
                            leaf: me.leaf,
                            currentPage:currentPage,
                            devicePageSize:pageSize,
                            deviceModel:deviceModel,
                            isCarryTool:me.isCarryTool,
                            isCabinOutOrIn:me.isCabinOutOrIn
                        });
                        var cardPanel = Ext.getCmp("spareCenterPanel");
                        //移除所有面板
                        cardPanel.items.each(function (item, index) {
                            cardPanel.remove(item);
                        });
                        cardPanel.add(sparePartsInstGrid);
                        //cardPanel.navigation(cardPanel, 'next');
                    }
                }
                ]
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

    importDeviceWin: function () {
        var me = this;
        var win = Ext.create("Ext.Window", {
            title: '批量导入设备',
            plain: true,
            height: 110,
            width: '70%',
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [
                {
                    xtype: 'form',
                    bodyPadding: 10,
                    layout: 'anchor',
                    defaults: {
                        anchor: '100%',
                        labelAlign: 'left',
                        msgTarget: 'side',
                        labelWidth: 90
                    },
                    items: [{
                        xtype: 'filefield',
                        buttonText: '',
                        fieldLabel: '导入设备(.xls或xlsx)',
                        buttonConfig: {
                            iconCls: 'icon-upload'
                        },
                        listeners: {
                            'change': function (fb, v) {
                                if (v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                    OrientExtUtil.Common.info('提示', '请选择Excel文件！');
                                    return;
                                }
                            }
                        }
                    }]
                }
            ],
            buttons: [
                {
                    text: '导入',
                    handler: function () {
                        var form = win.down("form").getForm();
                        if (form.isValid()) {
                            form.submit({
                                url: serviceName + '/spareParts/importDeviceData.rdm?productId=' + me.productId,
                                waitMsg: '导入设备...',
                                success: function (form, action) {
                                    OrientExtUtil.Common.tip('成功', action.result.msg);
                                    win.close();
                                    Ext.getCmp('sparePartsMgrOwner').fireEvent("refreshGrid");
                                },
                                failure: function (form, action) {
                                    switch (action.failureType) {
                                        case Ext.form.action.Action.CLIENT_INVALID:
                                            OrientExtUtil.Common.err('失败', '文件路径存在错误');
                                            break;
                                        case Ext.form.action.Action.CONNECT_FAILURE:
                                            OrientExtUtil.Common.err('失败', '无法连接服务器');
                                            break;
                                        case Ext.form.action.Action.SERVER_INVALID:
                                            OrientExtUtil.Common.err('失败', action.result.msg);
                                    }
                                }
                            });
                        }
                    }
                }
            ]
        });
        win.show();
    },
    exportDeviceData: function () {
        var me = this;
         //var exportButton=this;
        var grid = me.modelGrid;
        var selections = grid.getSelectionModel().getSelection();
        var toExportIds = OrientExtUtil.GridHelper.getSelectRecordIds(grid);
        var exportAll = false;
        if (selections.length === 0) {
            Ext.MessageBox.confirm('提示', '是否导出所有备品备件信息？', function (btn) {
                if (btn == 'yes') {
                    exportAll = true;
                    window.location.href = serviceName + '/spareParts/exportDeviceData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds + '&productId=' + me.productId;
                }
            });
        } else {
            window.location.href = serviceName + '/spareParts/exportDeviceData.rdm?exportAll=' + exportAll + '&toExportIds=' + toExportIds + '&productId=' + me.productId;
        }
    }
});