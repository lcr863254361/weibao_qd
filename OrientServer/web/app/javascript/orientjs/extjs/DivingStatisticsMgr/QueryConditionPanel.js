Ext.define('OrientTdm.DivingStatisticsMgr.QueryConditionPanel', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.queryConditionPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.WEI_BAO_SCHEMA_ID,
        modelName: TDM_SERVER_CONFIG.DIVING_STATISTICS,
        modelId: ''
    },
    initComponent: function () {
        var me = this;
        modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);

        Ext.apply(me, {
            bodyStyle: 'background:#fff',
            items: [
                {
                    xtype: 'fieldcontainer',
                    layout: 'hbox',
                    combineErrors: true,
                    defaults: {
                        flex: 1
                    },
                    items: [
                        {
                            name: 'C_PERSON_' + modelId,
                            xtype: 'textfield',
                            fieldLabel: '姓名',
                            margin: '0 5 0 0',
                            labelAlign: 'right',
                            fieldStyle: 'background-color:#EOEOEO'
                        },
                        {
                            name: 'C_COMPANY_' + modelId,
                            xtype: 'textfield',
                            fieldLabel: '右舷单位',
                            margin: '0 5 0 0',
                            labelAlign: 'right',
                            fieldStyle: 'background-color:#EOEOEO'
                        }, {
                            xtype: 'NumberColumnDesc',
                            columnDesc: {
                                editAble: true,
                                className: "NumberColumnDesc",
                                controlType: 1,
                                isMultiUnique: true,
                                isRequired: false,
                                isUnique: false,
                                maxColumnId: null,
                                maxTableId: null,
                                maxValue: null,
                                minColumnId: null,
                                minTableId: null,
                                minValue: null,
                                numberLength: 100,
                                numberPrecision: 0,
                                sColumnName: 'C_DEPTH_' + modelId,
                                text: "深度",
                                unit: null,
                                validRule: null
                            }
                        }
                    ]
                }, {
                    xtype: 'fieldcontainer',
                    layout: 'hbox',
                    combineErrors: true,
                    defaults: {
                        flex: 1
                    },
                    items: [
                        {
                            xtype: 'timefield',
                            fieldLabel: '水中时长',
                            labelAlign: 'right',
                            format: 'H:i',
                            name: 'C_WATER_TIME_LONG_' + modelId + "_START",
                            itemId: 'C_WATER_TIME_LONG_' + modelId + "_START",
                            endDateField: 'C_WATER_TIME_LONG_' + modelId + "_END",
                            vtype: 'daterange',
                            listeners: {
                                "focus": function () {
                                    this.expand();
                                },
                                "blur": function () {
                                    this.collapse();
                                }
                            }
                        }, {
                            xtype: 'timefield',
                            fieldLabel: "到",
                            labelWidth: 50,
                            margin: '0 5 0 25',
                            format: 'H:i',
                            labelAlign: 'right',
                            name: 'C_WATER_TIME_LONG_' + modelId + "_END",
                            itemId: 'C_WATER_TIME_LONG_' + modelId + "_END",
                            startDateField: 'C_WATER_TIME_LONG_' + modelId + "_START",
                            vtype: 'daterange',
                            listeners: {
                                "focus": function () {
                                    this.expand();
                                },
                                "blur": function () {
                                    this.collapse();
                                }
                            }
                        },
                        {
                            xtype: 'NumberColumnDesc',
                            columnDesc: {
                                editAble: true,
                                className: "NumberColumnDesc",
                                controlType: 1,
                                isMultiUnique: true,
                                isRequired: false,
                                isUnique: false,
                                maxColumnId: null,
                                maxTableId: null,
                                maxValue: null,
                                minColumnId: null,
                                minTableId: null,
                                minValue: null,
                                numberLength: 100,
                                numberPrecision: 0,
                                sColumnName: 'C_PERSON_TOTALWEIGHT_' + modelId,
                                text: "人员体重和",
                                unit: null,
                                validRule: null
                            }
                        }
                    ]
                }
            ],
            buttonAlign: 'center',
            buttons: [{
                xtype: 'button',
                text: '查询',
                width: 85,
                scale: 'medium',
                border: '0px!important;',
                style: 'background:#67CCCC;',
                scope: me,
                handler: function () {
                    me._doQuery();
                }
            },
                {
                    xtype: 'button',
                    text: '清空',
                    width: 85,
                    scale: 'medium',
                    border: '0px!important;',
                    style: 'background:#67CCCC;',
                    scope: me,
                    handler: function () {
                        me._doClear();
                    }
                }, {
                    xtype: 'button',
                    text: '导出',
                    width: 85,
                    scale: 'medium',
                    border: '0px!important;',
                    style: 'background:#67CCCC;',
                    scope: me,
                    handler: function () {
                        me.exportDivingData();
                    }
                }]
        });
        me.callParent(arguments);
    },
    _doQuery: function () {
        var me = this;
        var formValues = OrientExtUtil.FormHelper.generateFormData(me.getForm());
        var customerFilterArr = [];
        var startTime = formValues['C_WATER_TIME_LONG_' + modelId + "_START"];
        var endTime = formValues['C_WATER_TIME_LONG_' + modelId + "_END"];
        if (typeof formValues['C_WATER_TIME_LONG_' + modelId + "_START"] != 'undefined' && typeof formValues['C_WATER_TIME_LONG_' + modelId + "_END"] != 'undefined') {
            var dateFilterValue = formValues['C_WATER_TIME_LONG_' + modelId + "_START"] + "<!=!>" + formValues['C_WATER_TIME_LONG_' + modelId + "_END"];
            if (!Ext.isEmpty(dateFilterValue)) {
                var valueArr = dateFilterValue.split("<!=!>");
                var customerFilter = null;
                if (valueArr.length == 2) {
                    var startDate = valueArr[0];
                    var endDate = valueArr[1];
                    var startTime = startDate.replace(/:/, ".");
                    var endTime = endDate.replace(/:/, ".");
                    dateFilterValue = startTime + "<!=!>" + endTime;
                    if (!Ext.isEmpty(startDate) || !Ext.isEmpty(endDate)) {
                        customerFilter = new CustomerFilter("C_WATER_TIME_LONG_" + modelId, CustomerFilter.prototype.SqlOperation.BetweenAnd, "", dateFilterValue);
                        if (null != customerFilter) {
                            customerFilterArr.push(customerFilter);
                        }
                    }
                }
            }
        } else if (typeof startTime != 'undefined') {
            var startTime = startTime.replace(/:/, ".");
            var customerFilter = new CustomerFilter("C_WATER_TIME_LONG_" + modelId + "_START", CustomerFilter.prototype.SqlOperation.BetweenAnd, "", startTime);
            customerFilterArr.push(customerFilter);

        } else if (typeof endTime != 'undefined') {
            var endTime = endTime.replace(/:/, ".");
            var customerFilter = new CustomerFilter("C_WATER_TIME_LONG_" + modelId + "_END", CustomerFilter.prototype.SqlOperation.BetweenAnd, "", endTime);
            customerFilterArr.push(customerFilter);
        }

        //把查询面板里的条件转为CustomerFilter
        Ext.iterate(formValues, function (key, value) {
            if (key != ('C_WATER_TIME_LONG_' + modelId + '_START') && key != ('C_WATER_TIME_LONG_' + modelId + '_END')) {
                var customerFilter = new CustomerFilter(key, CustomerFilter.prototype.SqlOperation.Like, "", value);
                customerFilterArr.push(customerFilter);
            }
        });
        var grid = me.ownerCt.centerPanel;
        if (grid) {
            var params = {
                customerFilter: JSON.stringify(customerFilterArr)
            };
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/divingStatisticsMgr/queryDivingStatisticsData.rdm', params, true, function (response) {
                grid.loadData(response.decodedData.results);
            });
        }
    },
    _doClear: function () {
        var me = this;
        me.getForm().reset();
        var grid = me.ownerCt.centerPanel;
        if (grid) {
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/divingStatisticsMgr/queryDivingStatisticsData.rdm', {}, true, function (response) {
                grid.loadData(response.decodedData.results);
            })
        }
    },
    exportDivingData: function () {
        var me = this;
        var statisticsManagerGrid=this.ownerCt.down('statisticsManagerGrid');
        statisticsManagerGrid.getSelectedData();
    }
});