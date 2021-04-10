/**
 * Created by User on 2019/5/16.
 */
Ext.define('OrientTdm.ProductStructureMgr.ProductTreeNodeMgr.ProductTreeManagerGrid',{
    extend:'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias:'widget.productTreeManagerGrid',
    config:{
        schemaId:TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName:TDM_SERVER_CONFIG.TPL_PRODUCT_STRUCTURE,
        modelName:TDM_SERVER_CONFIG.PRODUCT_STRUCTURE,
        part:''
    },

    initComponent:function(){
        var me=this;
        var level=me.level;
        var modelId=OrientExtUtil.ModelHelper.getModelId(me.modelName,me.schemaId);
        var templateId=OrientExtUtil.ModelHelper.getTemplateId(modelId,me.templateName);
        var customerFilter=new CustomerFilter('C_PID_'+modelId,CustomerFilter.prototype.SqlOperation.Equal,'',me.productId);
        var modelGrid=Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid',{
            region:'center',
            modelId:modelId,
            isView:0,
            templateId:templateId,
            customerFilter:[customerFilter],
            createUrl: serviceName + "/ProductStructrue/addProductTreeNode.rdm?productId=" + me.productId+"&level="+level,
            updateUrl: serviceName + "/ProductStructrue/updateProductTreeNode.rdm?level="+level,
            //queryUrl: serviceName + "/spareParts/getSparePartsData.rdm?productId=" + me.productId+"&leaf="+me.leaf,

            afterInitComponent: function () {
                var toolbar = this.dockedItems[0];
                toolbar.add({
                    xtype:'button',
                    iconCls: 'icon-deleteCascade',
                    text: '级联删除',
                    scope:this,
                    handler:function() {
                        if (!OrientExtUtil.GridHelper.hasSelected(modelGrid)) {
                            return;
                        }
                        var selectRecords = OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                        var ids = [];
                        Ext.each(selectRecords, function (s) {
                            ids.push(s.data.id);
                        });
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ProductStructrue/delProductTreeData.rdm', {
                            id: ids.toString(),
                            //productId:me.productId

                        }, false, function (resp) {
                            var ret = Ext.decode(resp.responseText);
                            if (ret.success) {
                                modelGrid.fireEvent('refreshGrid');
                                //Ext.Msg.alert("提示", "删除成功！")
                                var treepanel = Ext.getCmp("addproductTree");
                                var store = treepanel.getStore();
                                var selectNode = Ext.getCmp("addproductTree").getSelectionModel().getSelection()[0];

                                if (Ext.isEmpty(selectNode))	//如果没有父节点，则pnode为根节点
                                {
                                    selectNode = store.getRootNode();
                                }
                                store.load({
                                    node: selectNode,
                                    callback: function (records) {
                                        if(records.length>0){
                                            selectNode.appendChild(records);	//添加子节点
                                            selectNode.set('leaf',false);
                                            selectNode.expand();
                                        }else{
                                            selectNode.set('leaf',true);
                                        }
                                    }
                                });
                            }
                        })
                    }
                });
            },
            afterOperate:function () {
                var treepanel = Ext.getCmp("addproductTree");
                var store = treepanel.getStore();
                var selectNode = Ext.getCmp("addproductTree").getSelectionModel().getSelection()[0];

                if (Ext.isEmpty(selectNode))	//如果没有父节点，则pnode为根节点
                {
                    selectNode = store.getRootNode();
                }
                store.load({
                    node: selectNode,
                    callback: function (records) {
                        selectNode.appendChild(records);	//添加子节点
                        selectNode.set('leaf',false);
                        selectNode.expand();
                    }
                });
            }
        });
        Ext.apply(me,{
            layout:'border',
            items:[modelGrid],
            modelGrid:modelGrid
        });
        me.callParent(arguments);
    }
});