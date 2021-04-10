/**
 * Created by User on 2019/2/25.
 */

Ext.define('OrientTdm.ProductStructureMgr.HistoryCheckRecord.HistoryCheckRecordMgr', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.historyCheckRecordMgr',

    requires:["OrientTdm.ProductStructureMgr.CheckUtil.TestProcessUtil"],

    config:{
        schemaId:TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName:TDM_SERVER_CONFIG.TPL_CURRENT_CHECK_RECORD,
        modelName:TDM_SERVER_CONFIG.CHECK_TEMP_INST
    },

    initComponent:function(){
        var me=this;
        var productId=me.productId;
        var nodeContent=me.nodeContent;

        var modelId=OrientExtUtil.ModelHelper.getModelId(me.modelName,me.schemaId);
        var templateId=OrientExtUtil.ModelHelper.getTemplateId(modelId,me.templateName);
        var modelGrid=Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid',{
            region:'center',
            modelId:modelId,
            isView:0,
            templateId:templateId,
            queryUrl: serviceName + "/ProductStructrue/getHistoryCheckInstData.rdm?productId=" + productId,
            afterInitComponent:function(){
                var me=this;
                me.viewConfig.getRowClass = function (record, rowIndex, rowParams, store) {
                    if(record.data['C_EXCEPTION_'+modelId]=='是'){
                        return 'x-grid-record-red';
                    }
                }
            }
        });

        // console.log( modelGrid.getDockedItems('toolbar[dock="top"]')[0]);
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].setVisible(false);

        //增加备品备件实例查看按钮
        var columns=modelGrid.columns;
        if(Ext.isArray(columns)){
            columns.push(
                {
                    xtype:'actioncolumn',
                    text:'查看',
                    align:'center',
                    width:200,
                    items:[{
                        iconCls:'icon-detail',
                        tooltip:'表格实例查看',
                        handler:function(grid,rowIndex){
                            var data=grid.getStore().getAt(rowIndex);
                            var checkTempId=data.raw.ID;
                            me.preview(checkTempId,true,productId);
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
    },
    preview: function (checkTempId,withData,productId) {
        //表单预览
        TestProcessUtil.CommonHelper.checkListPreview(checkTempId, true, withData,productId);
    }
});
