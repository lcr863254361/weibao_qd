/**
 * Created by User on 2019/1/5.
 */
Ext.define('OrientTdm.PlatformTemplateMgr.Center.TabPanel.AddPanel.AddCheckTempGrid',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.addCheckTempGrid',
    config:{
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_CHECK_TEMP,
        modelName: TDM_SERVER_CONFIG.CHECK_TEMP
    },

    initComponent:function(){    //增加首页展现内容
        var me=this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        //var customerFilter = new CustomerFilter('T_ORGANIZE_SSFLOW_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.ssubFlowId);//过滤条件
        var modelGrid=Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid',{
            region:'center',
            modelId: modelId,
            isView:0,
            templateId:templateId,
            //customerFilter:[customerFilter],
            afterInitComponent: function () {
                var toolbar = this.dockedItems[0];
                toolbar.add(
                    {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: me._bindCheckTableTemp,
                        scope: me
                    });
                //排序
                this.getStore().sort([{
                    "property": "T_CHECK_TYPE_" + me.schemaId + "_ID",
                    "direction": "ASC"
                },{
                    "property": "C_NAME_"+modelId,
                    "direction": "ASC"
                }]);
            },
            //视图初始化
            createToolBarItems: function () {
                var me = this;
                var retVal = [];

                retVal.push({
                    xtype: 'trigger',
                    width: 200,
                    style: {
                        margin: '0 0 0 0'
                    },
                    triggerCls: 'x-form-clear-trigger',
                    onTriggerClick: function () {
                        this.setValue('');
                        me.clearFilter();
                    },
                    emptyText: '快速搜索',
                    enableKeyEvents: true,
                    listeners: {
                        keyup: function (field, e) {
                            if (Ext.EventObject.ESC == e.getKey()) {
                                field.onTriggerClick();
                            } else {
                                me.up('panel').refreshGridByText(this.getRawValue());
                            }
                        }
                    }
                });
                //
                retVal = Ext.Array.merge(retVal, me.customerButton);
                //获取模型操作描述
                var btns = me.modelDesc.btns;
                Ext.each(btns, function (btn) {
                    retVal.push({
                        iconCls: 'icon-' + btn.code,
                        text: btn.name,
                        scope: me,
                        btnDesc: btn,
                        handler: Ext.bind(me.onGridToolBarItemClicked, me)
                    });
                });
                return retVal;
            }
        });

        Ext.apply(me,{
            layout:'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);   //调用父类的方法，返回父类方法中的内容
    },

    _bindCheckTableTemp: function () {
        var me = this;
        var modelGrid = me.modelGrid;
        if (OrientExtUtil.GridHelper.getSelectedRecord(modelGrid).length == 0) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, '未选中任何记录');
            return;
        }
        var selectRecords=OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
        var ids=[];
        Ext.each(selectRecords,function(s){
            ids.push(s.data.id);
        });
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/bindCheckTableTemp.rdm', {
                checkId: ids,
                nodeId:me.nodeId,
                nodeText:me.nodeText,
                flowTempTypeId:me.flowTempTypeId
            },
            false, function (response) {
                if (!response.decodedData.success) {
                    //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                    Ext.Msg.alert("提示", response.decodedData.msg);
                }
                //console.log(
                //    Ext.getCmp('bindCheckTableTempGrid_own_1')
                //);
                //console.log(Ext.ComponentQuery.query('bindCheckTableTempGrid')[0].modelGrid);
                //Ext.ComponentQuery.query('bindCheckTableTempGrid')[0].modelGrid.store.reload(); //刷新绑定面板
                Ext.getCmp('bindCheckTableTempGrid_own_2').store.reload();    //刷新绑定面板
                me.up('window').close();  //关闭窗口
            })
    },
    refreshGridByText: function (text) {
        var me = this;
        me=me.down('grid');
        var customerFilter = [];
        if (text != null && text.trim() != '') {
            customerFilter.push({
                filterName: 'C_NAME_'+me.modelId,
                filterValue: text,
                operation: CustomerFilter.prototype.SqlOperation.Like
            });
        }
        customerFilter = Ext.Array.merge(customerFilter, me.getCustomerFilter());
        //获取查询条件
        var modelDesc = me.modelDesc;
        //从表格对象中获取自定义过滤条件
        var gridStore = me.getStore();
        var proxy = gridStore.getProxy();
        proxy.setExtraParam("customerFilter", Ext.isEmpty(customerFilter) ? "" : Ext.encode(customerFilter));
        me.getSelectionModel().clearSelections();
        var lastOptions = gridStore.lastOptions;
        gridStore.reload(lastOptions);
    }
});