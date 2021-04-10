/**
 * Created by User on 2019/1/28.
 */

Ext.define('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.HangciMgrGrid',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.hangciMgrGrid',
    config:{
        schemaId:TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName:TDM_SERVER_CONFIG.TPL_HANGCI,
        modelName:TDM_SERVER_CONFIG.HANGCI
    },

    initComponent:function(){
        var me=this;
        var modelId=OrientExtUtil.ModelHelper.getModelId(me.modelName,me.schemaId);
        var templateId=OrientExtUtil.ModelHelper.getTemplateId(modelId,me.templateName);
        var modelGrid=Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid',{
            region:'center',
            modelId:modelId,
            isView:0,
            templateId:templateId,
            createUrl: serviceName + "/taskPrepareController/saveHangciData.rdm",
            updateUrl: serviceName + "/taskPrepareController/updateHangciData.rdm",
            //afterInitComponent: function () {
            //    var me = this;
            //    var toolbar = me.dockedItems[0];
            //    toolbar.add({
            //        iconCls: 'icon-delete',
            //        text: '级联删除',
            //        handler:function(){
            //            if(!OrientExtUtil.GridHelper.hasSelected(modelGrid)){
            //                return;
            //            }
            //            var selectRecords=OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
            //            var ids=[];
            //            Ext.each(selectRecords,function(s){
            //                ids.push(s.data.id);
            //            });
            //            OrientExtUtil.AjaxHelper.doRequest(serviceName+'/taskPrepareController/deleteHangciData.rdm',{
            //                id:ids.toString()
            //            },false,function(resp){
            //                var ret=Ext.decode(resp.responseText);
            //                if(ret.success){
            //                    modelGrid.fireEvent('refreshGrid');
            //                    Ext.Msg.alert("提示","删除成功！")
            //                    Ext.getCmp("tBomTemplateTreeOwner1").doRefresh();
            //                }
            //            })
            //        },
            //        scope: me
            //    });
            //},
        });
        //刷新Grid时 同时刷新右侧树
        modelGrid.on("customRefreshGrid", function (useQueryFilter) {
            var westPanel=me.ownerCt.ownerCt.ownerCt.westPanel;
            if (westPanel) {
                westPanel.fireEvent("refreshCurrentNode", useQueryFilter);
            }
        });
        Ext.apply(me,{
            layout:'border',
            items:[modelGrid],
            modelGrid:modelGrid
        });
        // me.addListener('itemdblclick', me.doubleClickDetail, me);
        me.callParent(arguments);
    }
});