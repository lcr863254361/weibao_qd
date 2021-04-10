/**
 * Created by User on 2018/12/26.
 */
Ext.define('OrientTdm.PlatformTemplateMgr.Center.TabPanel.AttendPostMgrPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.attendPostMgrPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_REF_POST_NODE,
        modelName: TDM_SERVER_CONFIG.REF_POST_NODE,
        nodeId: '',
        taskId: '',
        //hangciId:''

    },
    id:'addPost_owner_1',
    initComponent: function () {
        var me = this;
        //nodeId = me.nodeId;
        //taskId=me.taskId;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, me.templateName);
        //hangciId=me.hangciId;
        //console.log(this.down("postManagerGrid"));
        //var postFilter = me.addCustomerFilterPost(modelId);
        //var nodeId=this.nodeId;
        //var nodeText=this.nodeText;
        var customerFilter=new CustomerFilter('T_FLOW_TEMP_TYPE_'+me.schemaId+'_ID',CustomerFilter.prototype.SqlOperation.Equal,'',me.flowTempTypeId);
        var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
            region: 'center',
            modelId: modelId,
            isView: 0,
            templateId: templateId,
            customerFilter:[customerFilter],

            queryUrl:serviceName+'/taskPrepareController/queryAttendPostData.rdm',
            //afterInitComponent: function () {
            //    console.log(this.columns);
            //    var columns=this.columns;
            //   columns[1].hidden=true;
            //    columns[2].hidden=true;
            //},
        });

        var toolbar=[{
            xtype:'button',
            iconCls:'icon-create',
            text:'添加',
            handler:function(){
                //动态获取上上上个页面传过来的参数
                var nodeId=modelGrid.store.proxy.extraParams['nodeId'];
                var nodeText=modelGrid.store.proxy.extraParams['nodeText'];

                //弹出新增面板窗口
                var createWin = Ext.create('Ext.Window', {
                    title: '新增参与岗位',
                    plain: true,
                    height: 0.5 * globalHeight,
                    width: 0.5 * globalWidth,
                    layout: 'fit',
                    maximizable: true,
                    modal: true,
                    items: [Ext.create('OrientTdm.TaskPrepareMgr.Center.TabPanel.PostPersonnelMgrPanel', {
                        nodeId: nodeId,
                        nodeText: nodeText,
                        buttonShow:true,
                        //hangciId:me.hangciId,
                        flowTempTypeId:me.flowTempTypeId,
                        postIds:'',
                        successCallback: function (resp, callBackArguments) {
                            me.fireEvent("refreshGrid");
                            if (callBackArguments) {
                                createWin.close();
                            }
                        }
                    })],
                    buttonAlign: 'center',
                    buttons: [
                        {
                            text: '保存',
                            iconCls: 'icon-save',
                            //scope: me,
                            handler: function(){
                                var postGridPanel =Ext.getCmp("postPersonnelMgrPanel2").items.items[0];
                                console.log(postGridPanel);
                                if (OrientExtUtil.GridHelper.getSelectedRecord(postGridPanel).length == 0) {
                                    OrientExtUtil.Common.err(OrientLocal.prompt.error, '未选中任何记录');
                                    return;
                                }
                                var selectRecords=OrientExtUtil.GridHelper.getSelectedRecord(postGridPanel);
                                var ids=[];
                                Ext.each(selectRecords,function(s){
                                    ids.push(s.data.id);
                                });
                                OrientExtUtil.AjaxHelper.doRequest(serviceName + '/taskPrepareController/bindPostTableTemp.rdm', {
                                        postId: ids,
                                        taskId:me.taskId,
                                        nodeId:nodeId,
                                        nodeText:nodeText,
                                        //hangciId:me.hangciId,
                                        flowTempTypeId:me.flowTempTypeId
                                    },
                                    false, function (response) {
                                        if (!response.decodedData.success) {
                                            //me.up('setsMgrStageTabPanel').next("panel[region=south]").child(xtype = 'grid').refreshGrid();
                                            Ext.Msg.alert("提示", response.decodedData.msg);
                                        }
                                        //else{
                                        //    Ext.Msg.alert("提示", response.decodedData.msg);
                                        //}
                                        console.log(me.items.items[0]);
                                        me.items.items[0].store.reload();    //刷新绑定面板
                                        createWin.close();  //关闭窗口
                                    })
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
                });
                createWin.show();
            }
        },{
            xtype:'button',
            iconCls: 'icon-delete',
            text: '删除',
            scope:me,
            handler:function(){
                if(!OrientExtUtil.GridHelper.hasSelected(modelGrid)){
                    return;
                }
                var selectRecords=OrientExtUtil.GridHelper.getSelectedRecord(modelGrid);
                var ids=[];
                Ext.each(selectRecords,function(s){
                    ids.push(s.data.id);
                });
                OrientExtUtil.AjaxHelper.doRequest(serviceName+'/taskPrepareController/deleteTempAttendPostData.rdm',{
                    id:ids.toString()
                },false,function(resp){
                    var ret=Ext.decode(resp.responseText);
                    if(ret.success){
                        modelGrid.fireEvent('refreshGrid');
                        // Ext.Msg.alert("提示","删除成功！")
                        OrientExtUtil.Common.tip('成功', "删除成功！");
                    }
                })
            }
        }];

        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0]);
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].add(toolbar);
        console.log(modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0]);
        // 隐藏产品配置的按钮
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].items.items[0].hide();
        modelGrid.getDockedItems('toolbar[dock="top"]')[0].setDisabled(true);

        Ext.apply(me, {
            layout: 'border',
            items: [modelGrid],
            modelGrid: modelGrid
        });
        me.callParent(arguments);
    },

    refreshPostfilePanelByNode: function (nodeId, nodeText) {
        //var me = this;
        this.nodeId = nodeId;
        this.nodeText = nodeText;

        console.log(this);
        //为了tab页切换过滤检查表数据或岗位数据，此时把nodeId和nodeText放入到teststageTabPanel中
        this.ownerCt.ownerCt.nodeId=this.nodeId
        this.ownerCt.ownerCt.nodeText=this.nodeText;

        //console.log(me.items.items[0].items.items[0].items.items[0].items.items[0]);

        //this.modelGrid.createUrl = serviceName+'/taskPrepareController/AddAttendPostData.rdm?taskId='+this.taskId+'&nodeId='+nodeId+'&nodeText='+nodeText;
        var postFilter = this.addCustomerFilterPost();
        //me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('customerFilter', Ext.encode([postFilter]));
        //me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('nodeId', me.nodeId);
        //me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().getProxy().setExtraParam('nodeText', me.nodeText);
        //me.items.items[0].items.items[0].items.items[0].items.items[0].getStore().load();
        //this.items.items[0].createUrl = serviceName+'/taskPrepareController/AddAttendPostData.rdm?taskId='+this.taskId+'&nodeId='+nodeId+'&nodeText='+nodeText;
        this.items.items[0].getStore().getProxy().setExtraParam('customerFilter', Ext.encode([postFilter]));
        this.items.items[0].getStore().getProxy().setExtraParam('nodeId', nodeId);
        this.items.items[0].getStore().getProxy().setExtraParam('nodeText', nodeText);
        this.items.items[0].getStore().load();
        console.log(this.modelGrid);
    },

    addCustomerFilterPost: function () {
        //var me = this;

        var refPostTableName = TDM_SERVER_CONFIG.REF_POST_NODE;
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var modelId = OrientExtUtil.ModelHelper.getModelId(refPostTableName, schemaId);
        var filter = {};
        filter.expressType = "ID";
        filter.modelName = refPostTableName;

        filter.idQueryCondition = {};
        refPostTableName = refPostTableName + "_" + schemaId;
        filter.idQueryCondition.params = [this.flowTempTypeId];
        filter.idQueryCondition.sql = "SELECT ID FROM " + refPostTableName + " WHERE T_FLOW_TEMP_TYPE_"+schemaId+"_ID=?";

        if (Ext.isEmpty(this.nodeId)) {
            //展示所有
            return filter;
        } else {
            var postSql = "C_NODE_ID_" + modelId + "='" + this.nodeId + "'";
            filter.idQueryCondition.sql += " AND (" + postSql;
            filter.idQueryCondition.sql += ")";
            return filter;
        }
    }
});