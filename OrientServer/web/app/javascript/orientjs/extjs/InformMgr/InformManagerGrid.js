/**
 * Created by User on 2019/5/27.
 */
Ext.define('OrientTdm.InformMgr.InformManagerGrid', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.informManagerGrid',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_INFORM_MGR,
        modelName: TDM_SERVER_CONFIG.INFORM_MGR,
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        //var customerFilter = new CustomerFilter('T_CONSUME_MATERIAL_' + me.schemaId + '_ID', CustomerFilter.prototype.SqlOperation.Equal, '', me.consumeTypeId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            createUrl: serviceName + "/informMgr/saveInformMgrData.rdm",
            updateUrl:serviceName+"/informMgr/updateInformMgrData.rdm",
            //customerFilter: [customerFilter],
        });

        //增加更新公告按钮
        var columns=modelGrid.columns;
        if(Ext.isArray(columns)){
            columns.push({
                    xtype:'actioncolumn',
                    text:'更新通知',
                    align:'center',
                    width:400,
                   items:[{
                        iconCls:'icon-update',
                        tooltip:'更新通知',
                        handler:function(grid,rowIndex){
                            var data=grid.getStore().getAt(rowIndex);
                            var informId=data.raw.ID;
                            var departMentName=data.raw['C_DEPARTMENT_'+modelId];
                            var informContent=data.raw['C_INFORM_'+modelId];
                            var updateInformForm=Ext.create('OrientTdm.InformMgr.FormPanel.UpdateInformForm',{
                                informId:informId,
                                departMentName:departMentName,
                                informContent:informContent
                            });
                            var updateWin = Ext.create('Ext.Window', Ext.apply({
                                title: '更新【<span style="color: blue; ">'+departMentName+'</span>】通知',
                                titleAlign:'center',
                                plain: true,//为真时，用一个透明的背景渲染窗体主体以便于它将融入框架元素
                                height: 0.5 * globalHeight,
                                width: 0.5 * globalWidth,
                                layout: 'fit',
                                maximizable: true, //为真时显示'maximize'工具按钮并且允许用户最大化窗口，为假时隐藏最大化按钮并且不允许最大化冲口。
                                modal: true, //为真 当显示时，制作窗口模板并且掩饰他背后的一切，
                                items: [
                                    updateInformForm
                                ],
                                buttonAlign: 'center',
                                buttons: [
                                    {
                                        text: '保存',
                                        iconCls: 'icon-save',
                                        //scope: me,
                                        handler: function () {
                                           var newInformContent=Ext.getCmp('informContentId').getValue();
                                            if(informContent===newInformContent){
                                                OrientExtUtil.Common.err(OrientLocal.prompt.error, '不能重复保存！');
                                                return;
                                            }
                                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/informMgr/updateInformContent.rdm', {
                                                informId:informId,
                                                informContent:informContent,
                                                newInformContent:newInformContent
                                            },false,function (response){
                                                var result=response.decodedData.success;
                                                if(result){
                                                    //Ext.Msg.alert("提示", response.decodedData.msg);
                                                    updateWin.close();
                                                     modelGrid.store.load();
                                                }
                                            });
                                            }
                                    },
                                    {
                                        text: '关闭',
                                        iconCls: 'icon-close',
                                        handler: function () {
                                            this.up('window').close();
                                        }
                                    }
                                ]
                            }));

                            updateWin.show();
                        }
                    }
                    ]
                }
            )
        }
        //使用一个新的store/columns配置项进行重新配置生成grid
        modelGrid.reconfigure(modelGrid.getStore(),columns);

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    }
});