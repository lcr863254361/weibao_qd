Ext.define('OrientTdm.FormTemplateMgr.FormPanel.EditCheckTemplateFormPanel', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.editCheckTemplateFormPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.CHECK_TEMP,
        checkTempGrid:'',
        checkTempTypeId:''
    },
    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        // var updateUrl=serviceName+'/formTemplate/updateCheckTemp.rdm?modelId='+modelId+'&checkTempId='+me.checkTempId;
        var datas = [
            {checkType: '1', checkTypeName: '1'},
            {checkType: '2', checkTypeName: '2'},
            {checkType: '3', checkTypeName: '3'}
        ];
        var typeStore = {
            fields: ['checkType', 'checkTypeName'],
            proxy: {
                type: 'memory',
                data: datas,
                reader: 'json'
            },
            autoLoad: true
        };

        var isRepeatUploadDatas = [
            {isRepeatUploadValue: '否', isRepeatUploadDisplay: '否'},
            {isRepeatUploadValue: '是', isRepeatUploadDisplay: '是'}
        ];
        var isRepeatUploadStore = {
            fields: ['isRepeatUploadValue', 'isRepeatUploadDisplay'],
            proxy: {
                type: 'memory',
                data: isRepeatUploadDatas,
                reader: 'json'
            },
            autoLoad: true
        };

        Ext.apply(me, {
            items: [
                {
                    xtype: 'panel',
                    layout: 'hbox',
                    bodyStyle: 'border-width:0 0 0 0;background:transparent',
                    combineErrors: true,
                    defaults: {
                        flex: 1
                    },
                    layoutConfig: {
                        pack: "start",
                        align: "stretch"
                    },

                    items: [{
                        xtype: "fieldcontainer",
                        layout: 'anchor',
                        combineErrors: true,
                        defaults: {
                            flex: 1,
                            anchor: '100%'
                        },
                        layoutConfig: {
                            pack: "start",
                            align: "stretch"
                        },
                        items: [
                            {
                                name: 'C_NAME_' + modelId,
                                xtype: "textfield",
                                labelWidth: 80,
                                fieldLabel: '模板名称',
                                id: 'checkNameId',
                                labelAlign: 'right',
                                margin: '0 5 5 0',
                                // vtype:'unique',
                                // validataOnchange:false,
                                columnName: 'C_NAME_' + modelId,
                                allowBlank: false,
                                editable: true,
                                flex: 1
                            },
                            {
                                name: 'C_TEMP_TYPE_' + modelId,
                                // xtype: 'orientComboBox',
                                fieldLabel: '模板类型',
                                labelAlign: 'right',
                                id: 'tempTypeId',
                                labelWidth: 80,
                                // editable: false,
                                allowBlank: false,
                                // valueField:'value',
                                // displayField:'id',
                                // remoteUrl: serviceName + '/orientForm/getEnumCombobox.rdm?restrictionId=1215'
                                xtype: 'combo',
                                editable: false,
                                triggerAction: 'all',
                                displayField: 'checkTypeName',
                                valueField: 'checkType',
                                store: typeStore
                            },
                            {
                                name: 'C_IS_REPEAT_UPLOAD_' + modelId,
                                fieldLabel: '重复上传',
                                labelAlign: 'right',
                                id: 'tempRepeatId',
                                labelWidth: 80,
                                allowBlank: false,
                                xtype: 'combo',
                                editable: false,
                                triggerAction: 'all',
                                displayField: 'isRepeatUploadDisplay',
                                valueField: 'isRepeatUploadValue',
                                store: isRepeatUploadStore
                            }
                        ]
                    }]
                }
            ],
            buttonAlign: 'center',
            buttons: [

                {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        // me.fireEvent("saveOrientForm");
                        var form = me.getForm();
                        if (form.isValid()) {
                            me.formValue = OrientExtUtil.FormHelper.generateFormData(form);
                            //拼接模型参数
                            var params = {
                                modelId: modelId,
                                checkTempId: me.checkTempId,
                                formData: Ext.encode({
                                    fields: me.formValue
                                })
                            };
                            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/formTemplate/updateCheckTemp.rdm', params, false, function (resp) {
                                if (resp.decodedData.success) {
                                 // Ext.getCmp('refreshFormTemplateCheckTree').fireEvent("refreshTree", false);
                                    me.checkTempGrid.getSelectionModel().clearSelections();
                                    me.checkTempGrid.getStore().load();
                                    me.up('window').close();
                                }
                            });
                        } else {
                            OrientExtUtil.Common.err('错误', '表单存在错误!');
                        }
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-close',
                    handler: function () {
                        this.up('window').close();
                    }
                }
            ],
            // actionUrl:updateUrl
        });
        me.callParent(arguments);
    }
});