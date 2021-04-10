Ext.define('OrientTdm.TaskPrepareMgr.Center.TabPanel.AddPanel.AddDivingTaskFormPanel', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.addDivingTaskFormPanel',

    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.DIVING_TASK
    },

    initComponent: function () {
        var me = this;
        /*var saveUrl=serviceName + '/flowTempMgr/saveSSubFlowData.rdm?subFlowId=' + me.subFlowId+'&modelId='+me.modelId;*/
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
                                //name: 'C_TASK_NAME_'+modelId,
                                //xtype: "textfield",
                                //labelWidth: 80,
                                //fieldLabel: '任务名称',
                                //labelAlign: 'right',
                                //margin: '0 5 5 0',
                                //vtype: 'unique',
                                //validateOnChange: false,

                                //editable: true,
                                //hidden: false,
                                //flex: 1
                                xtype: 'SimpleColumnDesc',
                                columnDesc: {
                                    editAble: true,
                                    className: "SimpleColumnDesc",
                                    controlType: 0,
                                    isMultiUnique: true,
                                    isRequired: true,
                                    isUnique: true,
                                    modelId: modelId,
                                    sColumnName: 'C_TASK_NAME_' + modelId,
                                    selector: null,
                                    text: "任务名称",
                                    validRule: null,
                                }
                            },
                            {
                                //name: 'C_END_TIME_'+modelId,
                                //xtype: 'datefield',
                                //labelWidth: 80,
                                //fieldLabel: '结束时间',
                                //labelAlign: 'right',
                                //margin: '0 5 5 0',
                                //editable: false,
                                //format: 'Y-m-d',
                                //maxValue: new Date()
                                xtype: 'DateTimeColumnDesc',
                                columnDesc: {
                                    editAble: true,
                                    className: "DateTimeColumnDesc",
                                    controlType: 7,
                                    dateFmt: "yyyy-MM-dd HH:mm:ss",
                                    isMultiUnique: false,
                                    isRequired: false,
                                    isUnique: false,
                                    modelId: "226",
                                    sColumnName: 'C_END_TIME_' + modelId,
                                    //sModelName: "T_TEST_22",
                                    text: "结束时间",
                                    validRule: null
                                }
                            },
                            // {
                            //     xtype: 'SimpleColumnDesc',
                            //     columnDesc: {
                            //         editAble: true,
                            //         className: "SimpleColumnDesc",
                            //         controlType: 0,
                            //         isMultiUnique: true,
                            //         isRequired: false,
                            //         isUnique: true,
                            //         modelId: modelId,
                            //         sColumnName: 'C_TASK_TARGET_' + modelId,
                            //         selector: null,
                            //         text: "任务目标",
                            //         validRule: null
                            //     }
                            // }
                            // , {
                            //     xtype: 'NumberColumnDesc',
                            //     columnDesc: {
                            //         editAble: true,
                            //         className: "NumberColumnDesc",
                            //         controlType: 1,
                            //         isMultiUnique: true,
                            //         isRequired: false,
                            //         isUnique: false,
                            //         maxColumnId: null,
                            //         maxTableId: null,
                            //         maxValue: null,
                            //         minColumnId: null,
                            //         minTableId: null,
                            //         minValue: null,
                            //         numberLength: 100,
                            //         numberPrecision: 0,
                            //         sColumnName: 'C_JINGDU_' + modelId,
                            //         text: "经度",
                            //         unit: null,
                            //         validRule: null
                            //     }
                            // }
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
                                    //name: 'C_PLAN_START_TIME_'+modelId,
                                    //xtype: 'datefield',
                                    //labelWidth: 80,
                                    //fieldLabel: '开始时间',
                                    //margin: '0 5 5 0',
                                    //editable: false,
                                    //dateFmt: "yyyy-MM-dd HH:mm:ss",
                                    //maxValue: new Date()
                                    xtype: 'DateTimeColumnDesc',
                                    columnDesc: {
                                        editAble: true,
                                        className: "DateTimeColumnDesc",
                                        controlType: 7,
                                        dateFmt: "yyyy-MM-dd HH:mm:ss",
                                        isMultiUnique: false,
                                        isRequired: false,
                                        isUnique: false,
                                        sColumnName: 'C_PLAN_START_TIME_' + modelId,
                                        //sModelName: "T_TEST_22",
                                        text: "开始时间",
                                        validRule: null
                                    }
                                },
                                // {
                                //     xtype: 'NumberColumnDesc',
                                //     columnDesc: {
                                //         editAble: true,
                                //         className: "NumberColumnDesc",
                                //         controlType: 1,
                                //         isMultiUnique: true,
                                //         isRequired: false,
                                //         isUnique: false,
                                //         maxColumnId: null,
                                //         maxTableId: null,
                                //         maxValue: null,
                                //         minColumnId: null,
                                //         minTableId: null,
                                //         minValue: null,
                                //         numberLength: 100,
                                //         numberPrecision: 0,
                                //         sColumnName: 'C_PLAN_DIVING_DEPTH_' + modelId,
                                //         text: "计划下潜深度",
                                //         unit: null,
                                //         validRule: null
                                //     }
                                // },
                                // {
                                //     xtype: 'NumberColumnDesc',
                                //     columnDesc: {
                                //         editAble: true,
                                //         className: "NumberColumnDesc",
                                //         controlType: 1,
                                //         isMultiUnique: true,
                                //         isRequired: false,
                                //         isUnique: false,
                                //         maxColumnId: null,
                                //         maxTableId: null,
                                //         maxValue: null,
                                //         minColumnId: null,
                                //         minTableId: null,
                                //         minValue: null,
                                //         numberLength: 100,
                                //         numberPrecision: 0,
                                //         sColumnName: 'C_TASK_DEPTH_' + modelId,
                                //         text: "任务深度",
                                //         unit: null,
                                //         validRule: null
                                //     }
                                // }
                                ,
                                // {
                                //     //name: 'C_WEIDU_'+modelId,
                                //     //xtype: 'textfield',
                                //     //labelWidth: 80,
                                //     //fieldLabel: '纬度',
                                //     //margin: '0 5 5 0'
                                //     xtype: 'NumberColumnDesc',
                                //     columnDesc: {
                                //         editAble: true,
                                //         className: "NumberColumnDesc",
                                //         controlType: 1,
                                //         isMultiUnique: true,
                                //         isRequired: false,
                                //         isUnique: false,
                                //         maxColumnId: null,
                                //         maxTableId: null,
                                //         maxValue: null,
                                //         minColumnId: null,
                                //         minTableId: null,
                                //         minValue: null,
                                //         numberLength: 100,
                                //         numberPrecision: 0,
                                //         sColumnName: 'C_WEIDU_' + modelId,
                                //         text: "纬度",
                                //         unit: null,
                                //         validRule: null
                                //     }
                                // }
                                {
                                    name: 'C_TASK_TYPE_' + modelId,
                                    xtype: 'orientComboBox',
                                    labelWidth: 100,
                                    fieldLabel: '任务类型',
                                    labelAlign: 'right',
                                    margin: '0 5 5 0',
                                    editable: false,
                                    allowBlank: false,
                                    remoteUrl: serviceName + '/taskPrepareController/getEnumTasKType.rdm',
                                },
                                // {
                                //     name: 'C_FLOW_TEMP_TYPE_' + modelId,
                                //     xtype: 'orientComboBox',
                                //     labelWidth: 100,
                                //     fieldLabel: '流程模板',
                                //     labelAlign: 'right',
                                //     margin: '0 5 5 0',
                                //     editable: false,
                                //     allowBlank: false,
                                //     remoteUrl: serviceName + '/taskPrepareController/getEnumFlowTempType.rdm',
                                //     initFirstRecord:true
                                // }
                            ]
                        }
                    ]
                }
                ,
                {
                    name: 'C_FLOW_TEMP_TYPE_' + modelId,
                    xtype: 'orientComboBox',
                    labelWidth: 100,
                    fieldLabel: '流程模板',
                    labelAlign: 'right',
                    margin: '0 5 5 0',
                    editable: false,
                    allowBlank: false,
                    remoteUrl: serviceName + '/taskPrepareController/getEnumFlowTempType.rdm',
                    initFirstRecord:true
                }
            ]
        });

        this.callParent(arguments);
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
    }
});