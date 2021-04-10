/**
 * Created by User on 2018/12/13.
 */
Ext.define('OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateCheckTree', {
    extend: 'OrientTdm.Common.Extend.Tree.OrientTree',
    alias: 'widget.formTemplateCheckTree',
    rootVisible: false,
    id: 'refreshFormTemplateCheckTree',
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
        me.viewConfig.listeners={};
    },

    createStore: function () {
        var me = this;

        var retVal;
        retVal = Ext.create('Ext.data.TreeStore', {
            autoSync: true,//'true'表示每当对一条Record记录完成修改后, 都将对Store与Proxy进行同步. 默认为'false'.
            proxy: {
                type: 'ajax',
                url: serviceName + '/formTemplate/getCheckTempTreeNodes.rdm?checkTypeId=' + me.checkTypeId,
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                }
            },
            root: {
                text: 'root',
                qtip: 'root',
                id: '-1',
                iconCls: 'icon-function',
                icon: 'app/images/function/数据建模.png',
                type: 'root',
                expanded: true
            },
            listeners: {
                beforeLoad: function (store, operation) {
                    var node = operation.node;
                    if (node.isRoot) {
                        store.getProxy().setExtraParam('id', '-1');
                        store.getProxy().setExtraParam('type', 'root');
                    }
                }
            }
        });
        return retVal;
    },

    createToolBarItems: function () {
        var me = this;
        var retVal = ['', '', {
            iconCls: 'icon-import',
            text: '导入',
            name: 'create',
            tooltip: '从Excel导入',
            disabled: false,
            handler: Ext.bind(me.createTemplate, me)
        }, '', {
            iconCls: 'icon-update',
            text: '编辑',
            name: 'update',
            tooltip: '编辑',
            disabled: false,
            handler: Ext.bind(me.editTemplate, me)
        }, '', {
            iconCls: 'icon-delete',
            text: '删除',
            name: 'delete',
            tooltip: '删除',
            disabled: false,
            handler: Ext.bind(me.delTemplate, me)
        }, '', {
            iconCls: 'icon-export',
            text: '导出',
            name: 'create',
            tooltip: '从数据库中导出',
            disabled: false,
            handler: Ext.bind(me.exportTemplate, me)
        }];
        return retVal;
    },

    createTemplate: function () {
        var me = this;
        var win = Ext.create('Ext.Window', {
            title: '新增检查表模板',
            plain: true,
            height: 110,
            width: '40%',
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [{
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
                    fieldLabel: '导入检查表模板(.zip或xlsx)',
                    buttonConfig: {
                        iconCls: 'icon-upload'
                    },
                    listeners: {
                        'change': function (fb, v) {
                            /*if (v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                OrientExtUtil.Common.info('提示', '请选择Excel文件!');
                                return;
                            }*/
                            if (v.substr(v.length - 3) != "zip"&&v.substr(v.length - 3) != "xls" && v.substr(v.length - 4) != "xlsx") {
                                OrientExtUtil.Common.info('提示', '请选择zip压缩文件或Excel文件!');
                                return;
                            }
                        }
                    }
                }]
            }],
            buttons: [{
                text: '导入',
                handler: function () {
                    var form = win.down("form").getForm();
                    if (form.isValid()) {
                        form.submit({
                            //导入新的检查单模板
                              url: serviceName + '/formTemplate/multiImportCheckListFromExcel.rdm?checkTypeId=' + me.checkTypeId + '&checkTypeName=' + me.checkTypeName,
                            //url: serviceName + '/formTemplate/importCheckListFromExcel.rdm?checkTypeId=' + me.checkTypeId + '&checkTypeName=' + me.checkTypeName,
                            //url:serviceName+'/formTemplate/importCheckListFromExcel.rdm?checkTypeId='+me.checkTypeId,

                            waitMsg: '导入模板...',
                            success: function (form, action) {
                                OrientExtUtil.Common.info('成功', action.result.msg, function () {
                                    win.close();
                                    me.fireEvent("refreshTree", false);
                                });
                            },
                            failure: function (form, action) {
                                //alert("模板导入失败！")
                                OrientExtUtil.Common.info('失败', action.result.msg, function () {
                                    win.close();
                                    //me.fireEvent("refreshTree",false);
                                });
                            }
                        });
                    }
                }
            }]
        });
        win.show();
    },
    editTemplate: function () {
        var me = this;
        var selections = OrientExtUtil.TreeHelper.getSelectNodes(me);
        if (selections.length==0) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.atleastSelectOne);
            return;
        }
        var checkTempId = OrientExtUtil.TreeHelper.getSelectNodeIds(me)[0];
        var selectNode = OrientExtUtil.TreeHelper.getSelectNodes(me)[0];
        var checkName = selectNode.data['text'];
        var tempType = selectNode.raw['tempType'];
        var isRepeatUpload=selectNode.raw['isRepeatUpload'];
        var updateWin = Ext.create('Ext.Window', {
            title: '修改检查表模板',
            plain: true,
            height: 0.5 * globalHeight,
            width: 0.7 * globalWidth,
            layout: 'fit',
            maximizable: true,
            modal: true,
            items: [Ext.create('OrientTdm.FormTemplateMgr.FormPanel.EditCheckTemplateFormPanel', {
                bindModelName: "T_CHECK_TEMP_480",
                checkTempId: checkTempId,
                selectNode: selectNode
            })]
        });
        Ext.getCmp('checkNameId').setValue(checkName);
        Ext.getCmp('tempTypeId').setValue(tempType);
        Ext.getCmp('tempRepeatId').setValue(isRepeatUpload);
        updateWin.show();
    },
    delTemplate: function () {
        var me = this;
        var selections = OrientExtUtil.TreeHelper.getSelectNodes(me);
        if (selections.length==0) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.atleastSelectOne);
            return;
        }
        var checkTempId = OrientExtUtil.TreeHelper.getSelectNodeIds(me)[0];
        Ext.Msg.confirm("友情提示", "是否确认删除",
            function (btn) {
                if (btn == 'yes') {
                    OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/delCheckTempList.rdm', {
                        checkTempId: checkTempId
                    }, false, function (resp) {
                        //Ext.MessageBox.alert('提示','删除成功');
                        me.fireEvent('refreshTree', false);
                        console.log(me);
                        // me.ownerCt.centerPanel.items.items[0].store.load();
                        me.ownerCt.centerPanel.items.each(function (item, index) {
                            me.ownerCt.centerPanel.remove(item);
                        })
                    });
                }
            }
        )
    },
    exportTemplate: function () {
        var me=this;
        var checkTypeId=me.checkTypeId;
        var checkTypeName=me.checkTypeName;
        var selections = OrientExtUtil.TreeHelper.getSelectNodes(me);
        var storeResult=me.store.proxy.reader.rawData.results;
        if (storeResult.length==0){
            Ext.Msg.alert("提示", "【"+checkTypeName+"】下至少要有一条检查表数据才可以导出！");
            return;
        }
        if (selections.length === 0) {
        Ext.Msg.confirm("友情提示", "是否导出【"+checkTypeName+"】下的所有检查表格",
            function (btn) {
                if (btn == 'yes') {
                    window.location.href = serviceName + "/formTemplate/exportAllCheckTempByCheckTypeId.rdm?checkTypeId=" + checkTypeId+"&checkTypeName="+checkTypeName;
                }
            }
        )}else{
            var checkTempId = OrientExtUtil.TreeHelper.getSelectNodeIds(me);
            var checkName = [];
            Ext.each(selections, function (record) {
                checkName.push(record.get("text"));
            });
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/exportCheckTempFromOracle.rdm', {
                checkTempId: checkTempId,
                checkName: checkName
            }, true, function (resp) {
                window.location.href = serviceName + "/orientForm/downloadByName.rdm?fileName=" + resp.decodedData.results;
            })
        }
    },
    // afterInitComponent: function(tree, record, item) {
    //     var me = this;
    //     //默认显示第一个子节点
    //     // var node = tree.getRootNode().childNodes[0].childNodes[0];
    //     // tree.getSelectionModel().select(node);
    //     // tree.fireEvent('itemclick', tree, node);
    // },
    itemClickListener: function (tree, record, item) {
        var me = this;
        if (this.ownerCt.centerPanel) {
            switch (record.raw.type) {
                case 'root':
                    me.initRightPanel(record);
                    break;
                default :
                    break;
            }
        }
    },
    initRightPanel: function (record) {
        var me = this;
        var checkTempId = record.raw['dataId'];
        if (!Ext.isEmpty(checkTempId)) {
            var formTemplateCenterPanel = me.up('formTemplateMgrPanel').centerPanel;
            formTemplateCenterPanel.items.each(function (item, index) {
                formTemplateCenterPanel.remove(item);
            });

            var checkHeaderFilter = me.addCustomerFilterHeader(checkTempId);
            var checkEndFilter=me.addCustomerFilterEnd(checkTempId);

            var checkItemPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.FormTemplateHeadCellGrid', {
                region: 'center',
                padding: '0 0 0 5',
                isInst:false,
                isShowProduct:true,
                checkTempId:checkTempId
            });
            var checkHeaderEndPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.CheckHeaderEndMgrPanel.CheckHeaderPanel', {
                region: 'center',
                padding: '0 0 0 5',
                customerFilter:checkHeaderFilter,
                checkTempId:checkTempId
            });
            var checkEndPanel = Ext.create('OrientTdm.FormTemplateMgr.FormTemplatePanel.CheckHeaderEndMgrPanel.CheckEndPanel', {
                region: 'center',
                padding: '0 0 0 5',
                customerFilter:checkEndFilter,
                checkTempId:checkTempId
            });
            formTemplateCenterPanel.add({
                title: '检查项管理',
                // iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [checkItemPanel]
            });

            formTemplateCenterPanel.add({
                title: '检查表表头管理',
                // iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [checkHeaderEndPanel]
            });
            formTemplateCenterPanel.add({
                title: '检查表表尾管理',
                // iconCls: record.raw['iconCls'],
                layout: 'border',
                items: [checkEndPanel]
            });
            formTemplateCenterPanel.setActiveTab(0);

            //表头管理
            // this.ownerCt.centerPanel.items.items[1].items.items[0].items.items[0].getStore().getProxy().setExtraParam("customerFilter", Ext.encode([checkHeaderFilter]));
            // this.ownerCt.centerPanel.items.items[1].items.items[0].items.items[0].getStore().load();
            // this.ownerCt.centerPanel.items.items[1].items.items[0].items.items[0].getStore().getProxy().setExtraParam("checkTempId",checkTempId);

            //表尾管理
            // this.ownerCt.centerPanel.items.items[2].items.items[0].items.items[0].getStore().getProxy().setExtraParam("customerFilter", Ext.encode([checkEndFilter]));
            // this.ownerCt.centerPanel.items.items[2].items.items[0].items.items[0].getStore().load();
            // this.ownerCt.centerPanel.items.items[2].items.items[0].items.items[0].getStore().getProxy().setExtraParam("checkTempId",checkTempId);
            this.ownerCt.centerPanel.items.items[0].items.items[0].getCheckListDetailPanel(checkTempId);
        }
    },

    addCustomerFilterHeader: function (checkTempId) {
        var me = this;
        var filter = {};
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var refCheckTableName = TDM_SERVER_CONFIG.CHECK_CELL;
        var modelId= OrientExtUtil.ModelHelper.getModelId(refCheckTableName,schemaId);
        refCheckTableName = refCheckTableName + "_" + schemaId;
        filter.expressType = "ID";
        filter.modelName = refCheckTableName;
        filter.idQueryCondition = {};
        filter.idQueryCondition.params = [checkTempId];

        filter.idQueryCondition.sql = "SELECT ID FROM " + refCheckTableName + " WHERE T_CHECK_TEMP_" + schemaId + "_ID=?";

        var headerSql = "(C_IS_HEADER_" + modelId + "='" +"true" + "')";
        filter.idQueryCondition.sql += "AND (" + headerSql;
        filter.idQueryCondition.sql += ")";
        return filter;
    },
    addCustomerFilterEnd: function (checkTempId) {
        var me = this;
        var filter = {};
        var schemaId = TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID;
        var refCheckTableName = TDM_SERVER_CONFIG.CHECK_CELL;
        var modelId= OrientExtUtil.ModelHelper.getModelId(refCheckTableName,schemaId);
        refCheckTableName = refCheckTableName + "_" + schemaId;
        filter.expressType = "ID";
        filter.modelName = refCheckTableName;
        filter.idQueryCondition = {};
        filter.idQueryCondition.params = [checkTempId];

        filter.idQueryCondition.sql = "SELECT ID FROM " + refCheckTableName + " WHERE T_CHECK_TEMP_" + schemaId + "_ID=?";

        var headerSql = "(C_IS_HEADER_" + modelId + "='" +"false" + "')";
        filter.idQueryCondition.sql += "AND (" + headerSql;
        filter.idQueryCondition.sql += ")";
        return filter;
    }
});