Ext.define('OrientTdm.DestroyTaskMgr.AddDestroyTaskPanel.AddDestroyTaskFormPanel', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.addDestroyTaskFormPanel',

    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.DESTROY_TASK
    },

    initComponent: function () {
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        Ext.apply(this, {
            items: [
                {
                    xtype: 'panel',
                    layout: 'hbox',
                    bodyStyle: 'border-width:0 0 0 0; background:transparent',
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
                                xtype: 'SimpleColumnDesc',
                                columnDesc: {
                                    editAble: true,
                                    className: "SimpleColumnDesc",
                                    controlType: 0,
                                    isMultiUnique: true,
                                    isRequired: true,
                                    isUnique: true,
                                    modelId: modelId,
                                    sColumnName: 'C_NAME_' + modelId,
                                    selector: null,
                                    text: "任务名称",
                                    validRule: null,
                                }
                            },
                            {
                                xtype: 'fieldcontainer',
                                layout: 'hbox',
                                combineErrors: true,
                                name: 'unitSelector',
                                items: [
                                    {
                                        fieldLabel: '负责人',
                                        name: 'c_task_dutor_'+modelId,
                                        labelAlign:'right',
                                        xtype: 'textfield',
                                        id:'destroyPersonnelId',
                                        flex:1,
                                        allowBlank: true,
                                        listeners : {
                                            focus : function (field) {
                                                //获取已经选中的值
                                                var selectedValue = Ext.getCmp("destroyNameId").getValue();
                                                var win = Ext.create('Ext.Window', {
                                                    plain: true,
                                                    height: 600,
                                                    width: 800,
                                                    layout: 'fit',
                                                    maximizable: false,
                                                    title: '选择用户',
                                                    modal: true
                                                });

                                                var userSelectorPanel = Ext.create('OrientTdm.Common.Extend.Form.Selector.ChooseUserPanel', {
                                                    multiSelect: true,
                                                    selectedValue:selectedValue,
                                                    saveAction: function (saveData, callBack) {
                                                        var showValues = Ext.Array.pluck(saveData, 'name').join(',');
                                                        // var realValues = Ext.Array.pluck(saveData, 'userName').join(',');
                                                        var realIds = Ext.Array.pluck(saveData, 'id').join(',');

                                                        field.setValue(showValues);
                                                        // field.userName = realValues;
                                                        var allNameIds=Ext.getCmp("destroyNameId");
                                                        allNameIds.setValue(realIds);
                                                        if (callBack) {
                                                            callBack.call(this);
                                                        }
                                                        win.close();
                                                    }
                                                });

                                                win.add(userSelectorPanel);
                                                win.show();
                                            }
                                        }
                                    },
                                    {
                                        xtype: 'button',
                                        iconCls: 'icon-select',
                                        scope: me,
                                        itemId: 'selectBtn',
                                        width: '22px',
                                        handler: me._openSelectorWin
                                    },
                                    {
                                        xtype: 'button',
                                        iconCls: 'icon-clear',
                                        scope: me,
                                        width: '22px',
                                        itemId: 'clearBtn',
                                        handler: me._clearValue
                                    },
                                    {
                                        id:'destroyNameId',
                                        name: 'destroyName_Id',
                                        xtype: 'textfield',
                                        Width: '0px',
                                        fieldLabel: '岗位人员ID',
                                        editable: false,
                                        margin: '0 5 5 0',
                                        validateOnChange: false,
                                        allowBlank: true,
                                        anchor:'0%',
                                        hidden:true,
                                        readOnly:true,
                                        flex:0
                                    }
                                ]
                            }
                        ]
                    },
                        {
                            xtype: 'fieldcontainer',
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
                                    name: 'C_DESTROY_TEMP_' + modelId,
                                    xtype: 'orientComboBox',
                                    labelWidth: 100,
                                    fieldLabel: '拆解模板',
                                    labelAlign: 'right',
                                    margin: '0 5 5 0',
                                    editable: false,
                                    allowBlank: true,
                                    remoteUrl: serviceName + '/destroyRepairMgr/getEnumDestroyType.rdm'
                                }
                            ]
                        }
                    ]
                },

            ]
        });

        this.callParent(arguments);
    },
    _openSelectorWin: function () {
        var me = this;
        //获取已经选中的值
        var selectedValue = Ext.getCmp("destroyNameId").getValue();
        var createWin = Ext.create('Ext.Window', {
            title: '用户面板',
            plain: true,
            height: 600,
            width: 800,
            layout: 'fit',
            maximizable: true,
            modal: true,

            items: [Ext.create(OrientTdm.Common.Extend.Form.Selector.ChooseUserPanel, {
                multiSelect: true,
                selectedValue:selectedValue,

                saveAction: function (saveData, callBack) {
                    //保存信息
                    me._setValue(saveData);
                    if (callBack) {
                        /*console.log(saveData);*/
                        var showValues = Ext.Array.pluck(saveData, 'name').join(',');
                        //获取当前form中的控件,并给控件中传值
                        var allName=Ext.getCmp("destroyPersonnelId");
                        allName.setValue(showValues);

                        var realIds = Ext.Array.pluck(saveData, 'id').join(',');
                        var allNameIds=Ext.getCmp("destroyNameId");
                        allNameIds.setValue(realIds);

                        callBack.call(this);
                    }
                },

                successCallback: function (resp, callBackArguments) {
                    me.fireEvent("refreshGrid");
                    if (callBackArguments) {
                        createWin.close();
                    }
                }
            })]
        });
        createWin.show();
    },
    _clearValue: function () {
        var me = this;
        Ext.getCmp("destroyPersonnelId").setValue('');
        Ext.getCmp("destroyNameId").setValue('');
    },
    customValidate: function () {
        var me = this;
        var retVal = true;
        var formData = OrientExtUtil.FormHelper.getModelData(this);
        var modelId = this.modelDesc.modelId;
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelData/validateAll.rdm', {
            formData: formData,
            modelId: modelId
        }, false, function (resp) {
            var respData = resp.decodedData;
            if (respData.results != null && respData.results.length > 0) {
                retVal = false;
                var errorMsg = Ext.Array.pluck(respData.results, "errorMsg").join('</br>');
                OrientExtUtil.Common.err(OrientLocal.prompt.error, errorMsg, function () {
                    //清除错误
                    var columnDesc = me.modelDesc.createColumnDesc;
                    for (var i = 0; i < columnDesc.length; i++) {
                        var columns = me.modelDesc.columns;
                        for (var j = 0; j < columns.length; j++) {
                            if (columnDesc[i] == columns[j].id) {
                                var field = me.down('[name=' + columns[j].sColumnName + ']');
                                if (field) {
                                    field.clearInvalid();
                                }
                                break;
                            }
                        }
                    }
                    //markInvalid
                    Ext.each(respData.results, function (error) {
                        var errColumnName = error.columnSName;
                        if (!Ext.isEmpty(errColumnName)) {
                            var field = me.down('[name=' + errColumnName + ']');
                            if (field.markInvalid) {
                                field.markInvalid(error.errorMsg);
                            }
                        }
                    });
                });
            }
        });
        return retVal;
    },
    _setValue: function (data, raw) {
        raw = raw || data;
        //赋值
        var showValues = Ext.Array.pluck(data, 'name').join(',');
        var realValues = Ext.Array.pluck(data, 'id').join(',');
        var me = this;
        /* me.down('textfield[name=' + me.columnDesc.sColumnName + '_display]').setValue(showValues);*/
        me.fireEvent('afterChange', raw);
    },
});