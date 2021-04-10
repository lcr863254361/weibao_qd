/**
 * Created by User on 2019/3/12.
 */
Ext.define('OrientTdm.SparePartsMgr.TroubleDeviceMgr.TroubleDeviceGrid',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.troubleDeviceGrid',
    config:{
        schemaId:TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName:TDM_SERVER_CONFIG.TPL_TROUBLE_DEVICE_INST,
        modelName:TDM_SERVER_CONFIG.TROUBLE_DEVICE_INST
    },

    initComponent:function(){
        var me=this;
        var modelId=OrientExtUtil.ModelHelper.getModelId(me.modelName,me.schemaId);
        var templateId=OrientExtUtil.ModelHelper.getTemplateId(modelId,me.templateName);
        //var customerFilter=new CustomerFilter('T_SPARE_PARTS_SHILI_'+me.schemaId+'_ID',CustomerFilter.prototype.SqlOperation.Equal,'',me.deviceInstId);
        var modelGrid=Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid',{
            region:'center',
            modelId:modelId,
            isView:0,
            templateId:templateId,
            //customerFilter:[customerFilter],
            ////createUrl: serviceName + "/spareParts/saveSparePartsData.rdm?productId=" + me.productId,
            queryUrl: serviceName + "/spareParts/getTroubleDeviceData.rdm?deviceInstId="+me.deviceInstId+"&isTrouble="+true,
            afterInitComponent:function(){
                //var me=this;
                var toolbar=this.dockedItems[0];
                toolbar.add(
                {
                        iconCls:'icon-back',
                        text:'返回上一页',
                        handler:function(){
                            //console.log(modelGrid);
                            //console.log(me.ownerCt.up('sparePartsManagerDashboard'));  //获取上层组件的上层组件
                            var cardPanel=Ext.getCmp("spareCenterPanel");
                            //移除所有面板
                            cardPanel.items.each(function (item, index) {
                                cardPanel.remove(item);
                            });
                            var gridPanel=Ext.create('OrientTdm.SparePartsMgr.SparePartsInstGrid',{
                                spareId:me.spareId,
                                spareName:me.spareName,
                                productId:me.productId,
                                leaf:me.leaf
                            });
                            cardPanel.add(gridPanel);
                            cardPanel.navigation(cardPanel,'next');
                        },
                        scope:me
                    });
            }
        });

        //增加故障单详情查看按钮
        var columns=modelGrid.columns;
        if(Ext.isArray(columns)){
            columns.push({
                xtype:'actioncolumn',
                text:'故障单详情',
                align:'center',
                width:200,
                items:[{
                    iconCls:'icon-detail',
                    tooltip:'故障单详情',
                    handler:function(grid,rowIndex){
                        var data=grid.getStore().getAt(rowIndex);
                        var troubleId=data.raw.ID;
                        var recordTitle=data.raw['C_DESCRIPTION_'+modelId];

                        var createWin = Ext.create('Ext.Window', {
                            title: recordTitle,
                            titleAlign:'center',
                            plain: true,
                            height: 0.5 * globalHeight,
                            width: 0.5 * globalWidth,
                            layout: 'fit',
                            maximizable: true,
                            modal: true,

                            items: [Ext.create('OrientTdm.SparePartsMgr.TroubleDeviceMgr.TroubleDeviceDetail', {
                                troubleId:troubleId
                            })],
                        });
                        createWin.show();

                        //var troubleDeviceDetail=Ext.create('OrientTdm.SparePartsMgr.TroubleDeviceMgr.TroubleDeviceDetail',{
                        //    troubleId:troubleId,
                        //    //spareName:spareName,
                        //    //productId:me.productId,
                        //    //leaf:me.leaf
                        //});
                        //var cardPanel=Ext.getCmp("spareCenterPanel");
                        ////移除所有面板
                        //cardPanel.items.each(function (item, index) {
                        //    cardPanel.remove(item);
                        //});
                        //cardPanel.add(troubleDeviceDetail);
                        //cardPanel.navigation(cardPanel,'next');
                    }
                }]
            })
        }
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(),columns);
        Ext.apply(me,{
            layout:'border',
            items:[modelGrid],
            modelGrid:modelGrid
        });
        me.callParent(arguments);
    }
});